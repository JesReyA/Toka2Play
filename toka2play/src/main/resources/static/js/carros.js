const master = document.getElementById('master-container');

// Marcador de puntos
const score = document.createElement('div');
score.classList.add('score');
score.innerText = "Puntaje: 0";
master.appendChild(score);

// Pantalla de inicio
const startScreen = document.createElement('div');
startScreen.classList.add('startScreen');
startScreen.innerHTML = "Presiona para Iniciar <br> Usa las flechas o toca la pantalla <br> Evita los autos enemigos";
master.appendChild(startScreen);

// Área de la carretera
const gameArea = document.createElement('div');
gameArea.classList.add('road');
master.appendChild(gameArea);

let player = { speed: 5, score: 0, start: false, x: 0, y: 0 };
let keys = { ArrowUp: false, ArrowDown: false, ArrowLeft: false, ArrowRight: false };

startScreen.addEventListener('click', start);

document.addEventListener('keydown', (e) => {
    if (keys.hasOwnProperty(e.key)) keys[e.key] = true;
});

document.addEventListener('keyup', (e) => {
    if (keys.hasOwnProperty(e.key)) keys[e.key] = false;
});

function isCollide(a, b) {
    let aRect = a.getBoundingClientRect();
    let bRect = b.getBoundingClientRect();
    let offset = 12;
    return !(
        (aRect.bottom - offset < bRect.top + offset) ||
                (aRect.top + offset > bRect.bottom - offset) ||
                (aRect.right - offset < bRect.left + offset) ||
                (aRect.left + offset > bRect.right - offset)
            );
        }

function moveLines() {
    let lines = document.querySelectorAll('.lines');
    let roadHeight = gameArea.offsetHeight;
    lines.forEach(line => {
        if (line.y >= roadHeight) {
            line.y -= (roadHeight + 100);
        }
        line.y += player.speed;
        line.style.top = line.y + "px";
    });
}

function moveEnemies(car) {
    let enemies = document.querySelectorAll('.enemy');
    let roadRect = gameArea.getBoundingClientRect();

    enemies.forEach(enemy => {
        if (isCollide(car, enemy)) {
            endGame();
        }

        if (enemy.y >= roadRect.height) {
            enemy.y = -300;
            // Ajuste dinámico para que no se salgan del ancho
            let availableWidth = gameArea.offsetWidth - 50;
            enemy.style.left = Math.floor(Math.random() * availableWidth ) + "px";
        }
        enemy.y += player.speed;
        enemy.style.top = enemy.y + "px";
    });
}

function gamePlay() {
    if (player.start) {
        let car = document.querySelector('.car');
        let road = gameArea.getBoundingClientRect();

        moveLines();
        moveEnemies(car);

        if (keys.ArrowUp && player.y > (road.top + 70)) player.y -= player.speed;
        if (keys.ArrowDown && player.y < (road.bottom - 90)) player.y += player.speed;
        if (keys.ArrowLeft && player.x > 0) player.x -= player.speed;
        if (keys.ArrowRight && player.x < (road.width - 40)) player.x += player.speed;

        car.style.top = player.y + "px";
        car.style.left = player.x + "px";

        player.score++;
        score.innerText = "Puntaje: " + player.score;

        window.requestAnimationFrame(gamePlay);
    }
}

function start() {
    startScreen.classList.add('hide');
    gameArea.innerHTML = "";
    player.start = true;
    player.score = 0;

    let roadRect = gameArea.getBoundingClientRect();

    // Crear líneas
    for (let x = 0; x < 5; x++) {
        let line = document.createElement('div');
        line.classList.add('lines');
        line.y = (x * 150);
        line.style.top = line.y + "px";
        gameArea.appendChild(line);
    }

    // Crear coche
    let car = document.createElement('div');
    car.classList.add('car');
    gameArea.appendChild(car);

    // Posición inicial responsiva
    player.x = (roadRect.width / 2) - 25;
    player.y = roadRect.height - 150;

// Crear enemigos
    for (let x = 0; x < 3; x++) {
        let enemy = document.createElement('div');
        enemy.classList.add('enemy');
        enemy.y = ((x + 1) * 300) * -1;
        enemy.style.top = enemy.y + "px";


        let availableWidth = gameArea.offsetWidth - 50;
        enemy.style.left = Math.floor(Math.random() * availableWidth) + "px";

        gameArea.appendChild(enemy);
    }

    window.requestAnimationFrame(gamePlay);
}

function endGame() {
    player.start = false;
    startScreen.classList.remove('hide');
    startScreen.innerHTML = `
        <h2 style="margin-bottom: 10px;">¡CHOCASTE!</h2>
        <p>Puntuación Final: ${player.score}</p>
        <p style="margin-top: 15px; font-size: 0.8rem;">Haz clic para reintentar</p>
    `;

    fetch('/api/leaderboard/save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ idJuego: 4, idUsuario: 3, puntaje: player.score })
    }).catch(e => console.error(e));
}

// Control táctil para móvil
gameArea.addEventListener('touchstart', (e) => {
    let touchX = e.touches[0].clientX;
    if (touchX < window.innerWidth / 2) {
        keys.ArrowLeft = true;
        setTimeout(() => keys.ArrowLeft = false, 150);
    } else {
        keys.ArrowRight = true;
        setTimeout(() => keys.ArrowRight = false, 150);
    }
});