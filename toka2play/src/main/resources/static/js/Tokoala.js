        // Configuración
        let board;
        let boardWidth = 360;
        let boardHeight = 576;
        let context;

        let tokoalaWidth = 46;
        let tokoalaHeight = 46;
        let tokoalaX = boardWidth / 2 - tokoalaWidth / 2;
        let tokoalaY = boardHeight * 7/8 - tokoalaHeight;
        let tokoalaDerechaImg, tokoalaIzquierdaImg;

        let tokoala = {
            img: null,
            x: tokoalaX,
            y: tokoalaY,
            width: tokoalaWidth,
            height: tokoalaHeight
        };


        let velocidadX = 0;
        let velocidadY = 0;
        let velocidadInicialY = -6;
        let gravedad = 0.1;


        let plataformaArray = [];
        let plataformaWidth = 60;
        let plataformaHeight = 18;
        let plataformaImg;
        let plataformaRotaImg;

        // Estado del juego
        let score = 0;
        let maxScore = 0;
        let gameOver = false;
        let isPaused = false;
        let cameraThreshold = boardHeight / 2;
        let controlMode = 'keyboard';

        window.onload = function() {
            board = document.getElementById("board");
            board.height = boardHeight;
            board.width = boardWidth;
            context = board.getContext("2d");

            tokoalaDerechaImg = new Image();
            tokoalaDerechaImg.src = "/images/tokoala-derecha.png";

            tokoalaIzquierdaImg = new Image();
            tokoalaIzquierdaImg.src = "/images/tokoala-izquierda.png";

            plataformaImg = new Image();
            plataformaImg.src = "/images/plataforma.png";

            plataformaRotaImg = new Image();
            plataformaRotaImg.src = "/images/plataforma-rota.png";

            // Esperar a que TODAS las imágenes carguen antes de iniciar
            let imagenes = [tokoalaDerechaImg, tokoalaIzquierdaImg, plataformaImg, plataformaRotaImg];
            let cargadas = 0;

            imagenes.forEach(img => {
                img.onload = () => {
                    cargadas++;
                    if (cargadas === imagenes.length) {
                        tokoala.img = tokoalaDerechaImg;
                        velocidadY = velocidadInicialY;
                        placePlataformas();
                        requestAnimationFrame(update);
                    }
                };
                img.onerror = () => {
                    console.error("No se pudo cargar la imagen: " + img.src);
                };
            });

            document.addEventListener("keydown", moveTokoala);
            board.addEventListener("touchstart", handleTouch);
        };

        function update() {
            if (isPaused) return;

            // Derrota
            if (gameOver) {
                dibujarPantallaDerrota();
                return;
            }

            requestAnimationFrame(update);
            context.clearRect(0, 0, board.width, board.height);

            // Movimientos
            tokoala.x += velocidadX;
            if (tokoala.x > boardWidth) tokoala.x = 0;
            else if (tokoala.x + tokoala.width < 0) tokoala.x = boardWidth;

            velocidadY += gravedad;
            tokoala.y += velocidadY;

            // Cámara fija
            if (tokoala.y < cameraThreshold) {
                let offset = cameraThreshold - tokoala.y;
                tokoala.y = cameraThreshold;
                for (let i = 0; i < plataformaArray.length; i++) {
                    plataformaArray[i].y += offset;
                }
            }

            // Detección de caída
            if (tokoala.y > boardHeight && !gameOver) {
                gameOver = true;
                fetch('/api/leaderboard/save', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ idJuego: 2, idUsuario: 1, puntaje: score })
                }).catch(e => console.error(e));
            }

            context.drawImage(tokoala.img, tokoala.x, tokoala.y, tokoala.width, tokoala.height);

        //dibujar plataformas
            for (let i = 0; i < plataformaArray.length; i++) {
                let plataforma = plataformaArray[i];

                if (detectCollision(tokoala, plataforma) && velocidadY >= 0 &&!plataforma.broken) {
                    if (plataforma.type === "broken") {
                         if (!plataforma.broken){
                         plataforma.broken = true;
                         }
                    } else {
                        velocidadY = velocidadInicialY;
                    }
                }
                if (plataforma.broken) plataforma.y += 5;

                if (plataforma.type === "broken") {
                    context.drawImage(plataformaRotaImg, plataforma.x,
                    plataforma.y, plataforma.width, plataforma.height);
                } else {
                    context.drawImage(plataformaImg, plataforma.x,
                    plataforma.y, plataforma.width, plataforma.height);
                }
            }


            plataformaArray = plataformaArray.filter(p =>{
            if(p.y >= boardHeight){
            if(p.type === "normal"){
                newPlataforma ();
                score +=10;
            }return false;
        }return true;
            });
            let normalesVisibles = plataformaArray.filter( p => p.type === "normal" && !p.broken).length;

    while(normalesVisibles < 8) {
        let randomX = Math.floor(Math.random() * (boardWidth - plataformaWidth));
        plataformaArray.push({
            x: randomX,
            y: -plataformaHeight,
            width: plataformaWidth,
            height: plataformaHeight,
            type: "normal",
            broken: false
        });
        normalesVisibles++;
    }
            // UI puntaje
            context.fillStyle = "black";
            context.font = "20px sans-serif";
            context.textAlign = "left";
            context.fillText("Score: " + score, 5, 20);
            if (score > maxScore) maxScore = score;
            context.fillText("High Score: " + maxScore, 5, 45);
        }

        function dibujarPantallaDerrota() {

            context.fillStyle = "rgba(0, 0, 0, 0.7)";
            context.fillRect(0, 0, boardWidth, boardHeight);


            context.fillStyle = "white";
            context.textAlign = "center";
            context.font = "bold 28px sans-serif";
            context.fillText("¡HAS CAÍDO!", boardWidth / 2,
            boardHeight / 2);

            context.font = "18px sans-serif";
            context.fillText("Score final: " + score, boardWidth / 2,
            boardHeight / 2 + 40);
            context.fillText("Presiona ESPACIO para reiniciar",
            boardWidth / 2, boardHeight / 2 + 80);
        }

        function moveTokoala(e) {
            if (e.code == "KeyP") togglePause();
            if (isPaused) return;

            if (e.code == "ArrowRight" || e.code == "KeyD") {
                velocidadX = 2.5;
                tokoala.img = tokoalaDerechaImg;
            } else if (e.code == "ArrowLeft" || e.code == "KeyA") {
                velocidadX = -2.5;
                tokoala.img = tokoalaIzquierdaImg;
            } else if (e.code == "Space" && gameOver) {
                resetGame();
            }
        }

        function togglePause() {
            isPaused = !isPaused;
            if (!isPaused) requestAnimationFrame(update);
        }

        function resetGame() {
            tokoala = { img: tokoalaDerechaImg, x: tokoalaX, y: tokoalaY,
             width: tokoalaWidth, height: tokoalaHeight };
            velocidadX = 0;
            velocidadY = velocidadInicialY;
            score = 0;
            gameOver = false;
            placePlataformas();
            requestAnimationFrame(update);
        }

        function placePlataformas() {
            plataformaArray = [];
            let base = { x: boardWidth/2, y: boardHeight - 50, width: plataformaWidth, height: plataformaHeight, type: "normal", broken: false };
            plataformaArray.push(base);
            for (let i = 0; i < 10; i++) {
                newPlataforma(boardHeight - 50*i - 80);
            }
        }

        function newPlataforma(yPos = -plataformaHeight) {
            let randomX = Math.floor(Math.random() * (boardWidth - plataformaWidth));
            let esRota = Math.random() < 0.2;
            plataformaArray.push({
                x: randomX,
                y: yPos,
                width: plataformaWidth,
                height: plataformaHeight,
                type: esRota ? "broken" : "normal",
                broken: false
            });
        }

        function detectCollision(a, b) {
            return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
        }
//Modo tactil
        function setControlMode(mode) {
            controlMode = mode;
        }

        function handleTouch(e) {
            if (gameOver) { resetGame(); return; }
            if (controlMode === 'buttons') {
                let touchX = e.touches[0].clientX;
                if (touchX > window.innerWidth / 2) {
                    velocidadX = 2.5;
                    tokoala.img = tokoalaDerechaImg;
                } else {
                    velocidadX = -2.5;
                    tokoala.img = tokoalaIzquierdaImg;
                }
            }
        }