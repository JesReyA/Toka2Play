// 1. CONFIGURACIÓN Y DOM

let stompClient = null; // Esto "crea" el control remoto, aunque esté apagado al inicio

const canvas = document.getElementById("snakeGame");
const ctx = canvas.getContext("2d");
const gridSize = 20;
const tileCount = canvas.width / gridSize;

// 2. ESTADO DEL JUEGO
let score = 0;
let currentLevel = 0;
let fps = 9;
let snakeColor = "#2ecc71";
let lastTimestamp = 0;
let gameRunning = true;
let canChangeDirection = true; // Parche para evitar muertes injustas

let dx = 0;
let dy = 0;
let snake = [{ x: 10, y: 10 }];
let food = { x: 5, y: 5 };

// 3. PERSISTENCIA (Récord Máximo)
let highScore = localStorage.getItem("maxScore") || 0;
document.getElementById('highScoreVal').innerText = highScore;

const colors = {
    verde: "#2ecc71", morado: "#9b59b6", azul: "#3498db", rosa: "#e91e63", blanco: "#ffffff"
};

// 4. BUCLE PRINCIPAL
function main(currentTimestamp) {
    if (gameRunning) {
        window.requestAnimationFrame(main);
        const msPassed = currentTimestamp - lastTimestamp;

        if (msPassed < 1000 / fps) return;

        lastTimestamp = currentTimestamp;
        draw();
    }
}

// 5. RENDERIZADO
function draw() {
    updateSnake();
    if (checkGameOver()) return showGameOver();

    // Fondo
    ctx.fillStyle = "#2c3e50";
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    // Comida
    ctx.fillStyle = "#e74c3c";
    ctx.fillRect(food.x * gridSize, food.y * gridSize, gridSize - 2, gridSize - 2);

    // Serpiente
    ctx.fillStyle = snakeColor;
    snake.forEach(part => {
        ctx.fillRect(part.x * gridSize, part.y * gridSize, gridSize - 2, gridSize - 2);
    });

    // Abrir el candado de dirección después de haber movido y dibujado
    canChangeDirection = true;
}

// 6. LÓGICA
function updateSnake() {
    if (dx === 0 && dy === 0) return;

    const head = { x: snake[0].x + dx, y: snake[0].y + dy };
    snake.unshift(head);

    if (head.x === food.x && head.y === food.y) {
        score += 10;
        checkLevelUp();
        document.getElementById('scoreVal').innerText = score;
        placeFood();
        reportarPuntaje("snake", score);
    } else {
        snake.pop();
    }

}

function checkLevelUp() {
    if (score > 0 && score % 50 === 0 && currentLevel < 15) {
        currentLevel++;
        fps++;
        document.getElementById('levelVal').innerText = currentLevel;
    }
}

function changeDir(newDir) {
    // Si el juego está pausado o ya cambiamos dirección en este frame, ignorar
    if (!gameRunning || !canChangeDirection) return;

    switch(newDir) {
        case 'up':    if(dy !== 1)  { dx = 0; dy = -1; canChangeDirection = false; } break;
        case 'down':  if(dy !== -1) { dx = 0; dy = 1;  canChangeDirection = false; } break;
        case 'left':  if(dx !== 1)  { dx = -1; dy = 0; canChangeDirection = false; } break;
        case 'right': if(dx !== -1) { dx = 1; dy = 0;  canChangeDirection = false; } break;
    }
}

function selectColor(colorName) {
    if (colors[colorName]) snakeColor = colors[colorName];
}

function placeFood() {
    food.x = Math.floor(Math.random() * tileCount);
    food.y = Math.floor(Math.random() * tileCount);
    // Evitar que la comida salga sobre el cuerpo
    if (snake.some(p => p.x === food.x && p.y === food.y)) placeFood();
}

// 7. COLISIONES Y MODAL
function checkGameOver() {
    const head = snake[0];
    // Paredes
    if (head.x < 0 || head.x >= tileCount || head.y < 0 || head.y >= tileCount) return true;
    // Cuerpo
    for (let i = 1; i < snake.length; i++) {
        if (head.x === snake[i].x && head.y === snake[i].y) return true;
    }
    return false;
}

function showGameOver() {
    gameRunning = false;

    if (score > highScore) {
        highScore = score;
        localStorage.setItem("maxScore", highScore);
        document.getElementById('highScoreVal').innerText = highScore;
    }

    document.getElementById('finalScoreText').innerText = `Puntaje obtenido: ${score}`;
    document.getElementById('gameOverModal').style.display = "flex";
}

function closeModal() {
    score = 0;
    currentLevel = 0;
    fps = 9;
    snake = [{ x: 10, y: 10 }];
    dx = 0; dy = 0;
    gameRunning = true;
    canChangeDirection = true;

    document.getElementById('scoreVal').innerText = score;
    document.getElementById('levelVal').innerText = currentLevel;
    document.getElementById('gameOverModal').style.display = "none";

    placeFood();
    window.requestAnimationFrame(main);
}

// 8. EVENTOS
window.addEventListener("keydown", e => {
    switch(e.key) {
        case "ArrowUp":    changeDir('up'); break;
        case "ArrowDown":  changeDir('down'); break;
        case "ArrowLeft":  changeDir('left'); break;
        case "ArrowRight": changeDir('right'); break;
    }
});

// Inicio
window.requestAnimationFrame(main);


//funcion para reportar puntaje
function reportarPuntaje(nombreJuego, puntos) {
    if (stompClient && stompClient.connected) {
        const payload = {
            usuario: window.usuarioActual || "Invitado",
            puntos: puntos
        };

        // El endpoint cambia dinámicamente según el juego
        // Ejemplo: /app/snake/puntuar o /app/arkanoid/puntuar
        stompClient.send(`/app/${nombreJuego}/puntuar`, {}, JSON.stringify(payload));

        console.log(`Puntos de ${nombreJuego} enviados: ${puntos}`);
    }
}

function conectar() {
    const socket = new SockJS('/ws-toka'); // El endpoint de tu WebSocketConfig.java
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Conectado al servidor de Toka2Play: ' + frame);

        // Opcional: suscribirse para ver los puntos de otros
        stompClient.subscribe('/topic/scores/tokasnakegame', function (mensaje) {
            const data = JSON.parse(mensaje.body);
            console.log("Actualización global recibida:", data);
        });
    }, function (error) {
        console.error('Error de conexión:', error);
    });
}

conectar();