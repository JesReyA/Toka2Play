



function getAuthCode () {
    my.call(`getUser${method}AuthCode`, {
        usage: 'Se mostrará en el pop-up de autorización del usuario',
        scopes: "la",
        success: res => {
            //Código si el método se llama exitosamente.
        },
        fail: res => {
            //Código si el método falla.
        }
    });
}