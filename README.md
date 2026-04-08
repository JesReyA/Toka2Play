# Toka2Play
TOKA2PLAY es una Mini App de entretenimiento, diseñada para vivir nativamente dentro de la Super App Toka. Contiene un catálogo de minijuegos, trivias sociales a gran escala y en comunidades pequeñas con posibilidades de  conectar en una red de amigos. 

FLUJO DE TRABAJO DE APIS
Conexión: El cliente abre el túnel en /ws-toka en src/main/java/com/bytebalance/toka2play/config/WebSocketConfig.java.

Suscripción: El cliente se queda "escuchando" en /topic/scores/juego dentro de los archivos JS.

Publicación: Un jugador envía su puntaje a /app/juego/puntuar en src/main/java/com/bytebalance/toka2play/controllers.

Procesamiento: Spring recibe el mensaje, el Controller llama al Service en src/main/java/com/bytebalance/toka2play/services.

Difusión: El Service envía el nuevo puntaje a /topic/scores/juego dentro de los archivos JS.

Actualización: TODOS los clientes suscritos reciben el mensaje en JSON y su pantalla se actualiza al instante 
(ni la mas minima idea de como hacer eso).