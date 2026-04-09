const TokaApi = (() => {
    // IMPORTANTE: Asegúrate de reemplazar estos valores si tienes los oficiales
    const API_BASE_URL = "https://talentland-toka.eastus2.cloudapp.azure.com";
    const APP_ID = "3500020265516634"; // Reemplaza con tu App ID de 16 caracteres
    const MERCHANT_CODE = "00000"; // Reemplaza con tu Merchant Code de 5 caracteres

    let currentToken = null;
    let currentUserId = null;

    // Helper: Wrapper for Alipay JSAPI
    const getAuthCode = (method, scopes) => {
        return new Promise((resolve, reject) => {
            // Protección: Si Alipay tarda más de 5 segundos, rompemos la promesa para no dejar la pantalla cargando por siempre
            const timeout = setTimeout(() => reject(`Timeout pidiendo el código: ${method}`), 5000);

            if (window.AlipayJSBridge) {
                window.AlipayJSBridge.call(`getUser${method}AuthCode`, {
                    usage: 'Se utilizará para identificar tu perfil y crear tus transacciones',
                    scopes: scopes,
                    success: res => {
                        clearTimeout(timeout);
                        resolve(res.authcode || res.authCode);
                    },
                    fail: err => {
                        clearTimeout(timeout);
                        reject(err);
                    }
                });
            } else {
                // Mock behavior for desktop browser preview
                clearTimeout(timeout);
                console.warn(`AlipayJSBridge no encontrado. Retornando Mock AuthCode para ${method}.`);
                resolve(`mock_${method}_code`);
            }
        });
    };

    // 1. Digital Identity Auth Code
    const getDigitalIdentityAuthCode = () => {
        return getAuthCode('DigitalIdentity', ['USER_ID', 'USER_AVATAR', 'USER_NICKNAME']);
    };

    // 4. Personal Information Auth Code
    const getPersonalInformationAuthCode = () => {
        return getAuthCode('PersonalInformation', [
            'USER_NAME', 'USER_FIRST_SURNAME', 'USER_SECOND_SURNAME',
            'USER_GENDER', 'USER_BIRTHDAY', 'USER_STATE_OF_BIRTH', 'USER_NATIONALITY'
        ]);
    };

    // /v1/user/authenticate
    const authenticate = async (authCode) => {
        if (authCode.startsWith("mock")) {
            currentUserId = "mock_user_123";
            currentToken = "mock_jwt_token";
            return { userId: currentUserId, accessToken: currentToken };
        }

        try {
            const response = await fetch(`${API_BASE_URL}/v1/user/authenticate`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-App-Id': APP_ID
                },
                body: JSON.stringify({ authcode: authCode })
            });
            const result = await response.json();
            if (result.success) {
                currentUserId = result.data.userId;
                currentToken = result.data.accessToken;
                return result.data;
            } else {
                throw new Error(result.message);
            }
        } catch (error) {
            console.error("Error en authenticate:", error);
            throw error;
        }
    };

    // /v1/user/info
    const getUserInfo = async (authCodes) => {
        if (authCodes[0] && authCodes[0].startsWith("mock")) {
            return {
                nickName: "Toka Fan",
                avatar: "https://i.pravatar.cc/150?img=11",
                firstName: "Usuario",
                lastName: "Mock"
            };
        }

        try {
            const response = await fetch(`${API_BASE_URL}/v1/user/info`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-App-Id': APP_ID,
                    'Authorization': `Bearer ${currentToken}`
                },
                body: JSON.stringify({ authCodes: authCodes })
            });
            const result = await response.json();
            if (result.success) {
                return result.data;
            } else {
                throw new Error(result.message);
            }
        } catch (error) {
            console.error("Error en getUserInfo:", error);
            throw error;
        }
    };

    // /v1/payment/create
    const createPayment = async (amount, currency = "MXN", title = "Compra en Toka Store") => {
        if (!currentToken || currentToken === "mock_jwt_token") {
            console.log("Mock Payment Iniciado...");
            return { paymentUrl: "mock_url", paymentId: "mock_pay_123" };
        }

        try {
            const response = await fetch(`${API_BASE_URL}/v1/payment/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-App-Id': APP_ID,
                    'Authorization': `Bearer ${currentToken}`,
                    'Alipay-MerchantCode': MERCHANT_CODE
                },
                body: JSON.stringify({
                    userId: currentUserId,
                    orderTitle: title,
                    orderAmount: {
                        value: amount.toString(),
                        currency: currency
                    }
                })
            });
            const result = await response.json();
            if (result.success) {
                return result.data;
            } else {
                throw new Error(result.message);
            }
        } catch (error) {
            console.error("Error en createPayment:", error);
            throw error;
        }
    };

    // Flujo Transaccional
    const processPayment = async (amount, currency = "MXN", title = "Compra de Toka Tokens") => {
        try {
            // 1. Pedirle al backend la orden y la URL
            const paymentData = await createPayment(amount, currency, title);

            // 2. Levantar la JSAPI de pago de Alipay
            if (window.AlipayJSBridge && paymentData.paymentUrl !== "mock_url") {
                window.AlipayJSBridge.call('pay', {
                    paymentUrl: paymentData.paymentUrl,
                    success: (res) => {
                        console.log("Pago completado exitosamente", res);
                        alert("¡Gracias por tu compra! (Este es el flujo real)");
                    },
                    fail: (err) => {
                        console.error("Pago fallido o cancelado", JSON.stringify(err));
                    }
                });
            } else {
                // Flujo Local de prueba
                alert(`[ENTORNO DE PRUEBA] Solicitud de pago Alipay iniciada.\n\nMonto: $${amount} ${currency}\nTítulo: ${title}\n(Operación simulada con éxito).`);
            }
        } catch (err) {
            console.error("Error en el flujo de pago:", err);
            alert("No pudimos conectar con los servicios de pago. Intenta más tarde.");
        }
    };

    // Método principal: Inicializar al jugador (Conseguir Identidad y poblar UI)
    const initializeUser = async () => {
        try {
            console.log("Obteniendo Auth Codes de Alipay...");
            // Pedimos en paralelo el Digital Identity y el de Información Personal
            const [digitalIdentityCode, personalInfoCode] = await Promise.all([
                getDigitalIdentityAuthCode(),
                getPersonalInformationAuthCode()
            ]);

            console.log("Autenticando en Toka backend...");
            await authenticate(digitalIdentityCode);

            console.log("Extrayendo User Info del backend...");
            const userInfo = await getUserInfo([digitalIdentityCode, personalInfoCode]);

            // =========== MODIFICAMOS EL DOM EXCLUSIVAMENTE CON LOS DATOS DE ALIPAY ===========
            const nameEl = document.getElementById('user-nickname');
            const avatarEls = document.querySelectorAll('.profile-pic'); // Múltiples avatares 

            if (nameEl && userInfo.nickName) {
                nameEl.textContent = `Hola ${userInfo.nickName}`;
            }

            if (avatarEls.length > 0 && userInfo.avatar) {
                avatarEls.forEach(img => {
                    img.src = userInfo.avatar;
                });
            }

            console.log("Toka2Play Inicializado exitosamente.", userInfo);
        } catch (err) {
            console.error("Fallo la inicialización de Alipay Identity:", err);
        } finally {
            // Apagar la pantalla de carga suavemente
            const loadingScreen = document.getElementById('toka-loading-screen');
            if(loadingScreen) {
                loadingScreen.classList.add('hidden');
            }
        }
    };

    return {
        initializeUser,
        // Funciones publicas para que index.html las pueda invocar
        buyTokaTokens: () => processPayment(100, "MXN", "Compra de 1,000 Toka Tokens")
    };
})();

// Cuando todo el documento carga
document.addEventListener("DOMContentLoaded", () => {
    // Validamos si ya existe el Alipay JS Bridge en el entorno
    if (window.AlipayJSBridge) {
        TokaApi.initializeUser();
    } else {
        // En Alipay a veces el Bridge se inyecta asincronamente
        document.addEventListener('AlipayJSBridgeReady', TokaApi.initializeUser, false);

        // Timeout como fallback: Si tras 800ms no hay Bridge, inicializar en modo Mock (PC Test)
        setTimeout(() => {
            if (!window.AlipayJSBridge) TokaApi.initializeUser();
        }, 800);
    }

    // SALVAVIDAS GLOBAL: Asegurar que sin importar qué pase, después de 4 segundos se esconda la carga
    setTimeout(() => {
        const loadingScreen = document.getElementById('toka-loading-screen');
        if(loadingScreen && !loadingScreen.classList.contains('hidden')) {
            console.warn("La pantalla de carga fue escondida forzosamente por tiempo agotado.");
            loadingScreen.classList.add('hidden');
        }
    }, 4500);
});
