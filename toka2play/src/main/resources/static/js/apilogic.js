/**
 * apilogic.js
 * Lógica para la obtención de códigos de autorización (Auth Codes) de Alipay.
 * Basado en la documentación de servicios de Alipay/Toka.
 */

/* global AlipayJSBridge, my */

const alipayService = {

    // 1. Función General (El motor que hace la llamada a Alipay)
    getAuthCode(method, scopes) {
        // Detecta el entorno: H5 (web) usa window.AlipayJSBridge, MiniApp usa my [cite: 61-62]
        const bridge = window.AlipayJSBridge || (typeof my !== 'undefined' ? my : null);

        if (!bridge) {
            console.error("Error: No se detectó el SDK de Alipay. Asegúrate de importar el script de Marmot Cloud.");
            return;
        }

        // Construye el nombre del método dinámicamente [cite: 22]
        const apiMethod = `getUser${method}AuthCode`;

        bridge.call(apiMethod, {
            usage: 'Autoriza el acceso a tu información para continuar con la experiencia.', // [cite: 23]
            scopes: scopes, // [cite: 24]
            success: (res) => {
                // Al tener éxito, el bridge devuelve un objeto con el authCode [cite: 56]
                if (res && res.authCode) {
                    console.log(`Éxito obteniendo AuthCode (${method}):`, res.authCode);
                    this.enviarAlBackend(res.authCode);
                }
            },
            fail: (err) => {
                console.error(`Error al llamar a ${apiMethod}:`, err);
            }
        });
    },

    // 2. Funciones específicas para cada tipo de información [cite: 33-55]

    // Obtener Identidad Digital: Foto, Nickname [cite: 34]
    getDigitalIdentityAuthCode() {
        this.getAuthCode('DigitalIdentity', ['USER_ID', 'USER_AVATAR', 'USER_NICKNAME']);
    },

    // Obtener Información de Contacto: Teléfono y Correo [cite: 37]
    getContactInformationAuthCode() {
        this.getAuthCode('ContactInformation', ['PLAINTEXT_MOBILE_PHONE', 'PLAINTEXT_EMAIL_ADDRESS']);
    },

    // Obtener Dirección [cite: 41]
    getAddressInformationAuthCode() {
        this.getAuthCode('AddressInformation', ['USER_ADDRESS']);
    },

    // Obtener Información Personal Completa [cite: 43-51]
    getPersonalInformationAuthCode() {
        this.getAuthCode('PersonalInformation', [
            'USER_NAME',
            'USER_FIRST_SURNAME',
            'USER_SECOND_SURNAME',
            'USER_GENDER',
            'USER_BIRTHDAY',
            'USER_STATE_OF_BIRTH',
            'USER_NATIONALITY'
        ]);
    },

    // Obtener Estatus de Validación de Identidad (KYC) [cite: 55]
    getKYCStatusAuthCode() {
        this.getAuthCode('KYCStatus', ['USER_KYC_STATUS']);
    },

    // 3. Comunicación con tu Backend (Java)
    enviarAlBackend(authCode) {
        // El authCode de 6 caracteres es de un solo uso y corta vida [cite: 280]
        // Debes enviarlo a tu endpoint: POST /v1/user/authenticate [cite: 95]

        fetch('/v1/user/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-App-Id': 'TU_APP_ID_DE_16_CHARS' // Debe ser de exactamente 16 caracteres [cite: 83]
            },
            body: JSON.stringify({ authcode: authCode }) // [cite: 101-104]
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    console.log("Autenticación exitosa. Token recibido:", data.data.accessToken);
                    // Aquí guardas el token para futuras peticiones
                } else {
                    console.error("Error en servidor:", data.message);
                }
            })
            .catch(error => console.error("Error de red:", error));
    }
};