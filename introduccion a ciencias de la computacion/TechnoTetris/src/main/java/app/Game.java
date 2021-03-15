
package app;

import tetris.Tablero;
import tetris.juego.*;
import tetris.tetronimos.*;

import processing.core.PFont;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.sound.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Game extends PApplet {

    private HashMap<String, PImage> bloques;
    private static Tablero partida;
    private static Bolsa cola;
    private static Tetronimo pieza, canguro = null;
    private static Tetronimo siguientePieza;
    private static String name = "";
    private static int alto, ancho, control, tamCuadrado, backgroundFrame = 1, nivel = 1, cambiarFase = 10,
            backgroundFrameScore = 1, endGame = 1;
    private static long reloj, score, bloqueo = 0, endGameCountDown = 0, piezasCaidas = 1;
    private static boolean nextp = true, hardDrop = false, piezaGuardada = false, cambio = true, guardarScore = true,
            endGameCountDownStart = true, newGame = false;

    private static SoundFile tetris;

    public static void main(String[] args) {
        PApplet.main("app.Game");
    }

    @Override
    public void settings() {
        alto = displayHeight * 3 / 4;
        ancho = displayHeight * 4 / 5;
        size(alto, ancho);
    }

    @Override
    public void setup() {
        frameRate(60);

        bloques = new HashMap<>();
        partida = Tablero.obtenerPartida();
        cola = partida.obtenerBolsa();
        pieza = cola.sacar();

        bloques.put("bloqueAMARILLO", loadImage(this.getClass().getResource("/bloques/AMARILLO.png").getPath()));
        bloques.put("bloqueAZUL", loadImage(this.getClass().getResource("/bloques/AZUL.png").getPath()));
        bloques.put("bloqueBORDE", loadImage(this.getClass().getResource("/bloques/BORDE.png").getPath()));
        bloques.put("bloqueCYAN", loadImage(this.getClass().getResource("/bloques/CYAN.png").getPath()));
        bloques.put("bloqueMAGENTA", loadImage(this.getClass().getResource("/bloques/MAGENTA.png").getPath()));
        bloques.put("bloqueNARANJA", loadImage(this.getClass().getResource("/bloques/NARANJA.png").getPath()));
        bloques.put("bloqueROJO", loadImage(this.getClass().getResource("/bloques/ROJO.png").getPath()));
        bloques.put("bloqueVERDE", loadImage(this.getClass().getResource("/bloques/VERDE.png").getPath()));
        bloques.put("GHOST", loadImage(this.getClass().getResource("/bloques/GHOST.png").getPath()));

        tetris = new SoundFile(this, this.getClass().getResource("/sonidos/Techno-Trance-Tetris.mp3").getPath());
    }

    @Override
    public void draw() {
        endGame = this.inGame();

        if (frameCount == 1) {
            tetris.loop();
        }
        if (endGame == 0 || newGame) {
            endGameEvent();

        }

    }

    private int inGame() {

        tamCuadrado = (alto) / 22;
        Bloque[][] aux;
        int continueGame = 1;

        PImage phto = loadImage(this.getClass().getResource("/fondo/background/" + backgroundFrame + ".gif").getPath());
        PFont fuente = createFont(this.getClass().getResource("/fuente/INVASION2000.TTF").getPath(), 32);
        PImage scorebkg = loadImage(this.getClass().getResource("/fondo/6.jpg").getPath());
        PImage info = loadImage(this.getClass().getResource("/fondo/5.jpg").getPath());

        reloj = millis() / 750;

        if (frameCount % (10 - nivel) == 0) {
            nextp = pieza.caer();
            control += nextp ? 1 : 0;

        }

        siguientePieza = cola.siguiente();
        if (!nextp) {
            bloqueo = bloqueo == 0 ? reloj : bloqueo;
            if ((reloj - bloqueo > 1) || hardDrop) {

                if (control != 0 && !deadline()) {
                    piezasCaidas++;
                    nivel += (piezasCaidas % cambiarFase == 0 && nivel < 9) ? 1 : 0;
                    cambiarFase += (piezasCaidas % cambiarFase == 0) ? cambiarFase / 2 : 0;
                    control = 0;
                    hardDrop = false;

                    score += calculaPuntaje(partida.limpiarFilas()) * nivel;
                    partida.gravedad();
                    pieza = cola.sacar();
                    partida.actualizarBolsa(cola);
                    bloqueo = 0;
                    cambio = true;
                } else {
                    continueGame = 0;
                    tint(255, 100);
                }

            }

        }

        // fondo y tablero
        if (frameCount % 6 == 0) {
            backgroundFrame = backgroundFrame < 49 ? backgroundFrame + 1 : 1;
        }

        phto.resize(displayHeight * 3 / 4, displayHeight * 4 / 5);

        background(phto);

        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                if (i == 0 || i == 21 || j == 0 || j == 11) {
                    image(bloques.get("bloqueBORDE"), tamCuadrado + (j * tamCuadrado),
                            alto - (tamCuadrado + (i * tamCuadrado)), tamCuadrado, tamCuadrado);
                } else {
                    if (partida.obtenerBloque(i - 1, j - 1).obtenerColor() != Color.VACIO) {
                        image(bloques.get("bloque" + partida.obtenerBloque(i - 1, j - 1).obtenerColor()),
                                tamCuadrado + (j * tamCuadrado), alto - (tamCuadrado + (i * tamCuadrado)), tamCuadrado,
                                tamCuadrado);
                    }

                }
            }
        }

        tint(255, 100);
        info.resize(ancho / 3, alto / 4);
        // siguiente pieza
        image(info, tamCuadrado * 13 + tamCuadrado / 2, tamCuadrado * 2);
        // guardada
        image(info, tamCuadrado * 13 + tamCuadrado / 2, tamCuadrado * 9);
        // score
        scorebkg.resize(ancho / 3, (alto / 4) + tamCuadrado);
        image(scorebkg, tamCuadrado * 13 + tamCuadrado / 2, tamCuadrado * 16);
        if (continueGame == 1) {
            noTint();
        }

        // piezas
        aux = siguientePieza.map();
        // SIGUIENTE
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux[i].length; j++) {
                if (aux[i][j].obtenerColor() != Color.VACIO) {

                    image(bloques.get("bloque" + aux[i][j].obtenerColor()), tamCuadrado * (16 + j),
                            tamCuadrado * (3 + i), tamCuadrado, tamCuadrado);
                }
            }
        }
        // GUARDADA
        if (canguro != null) {
            aux = canguro.map();
            for (int i = 0; i < aux.length; i++) {
                for (int j = 0; j < aux[i].length; j++) {
                    if (aux[i][j].obtenerColor() != Color.VACIO) {

                        image(bloques.get("bloque" + aux[i][j].obtenerColor()), tamCuadrado * (16 + j),
                                tamCuadrado * (10 + i), tamCuadrado, tamCuadrado);
                    }
                }
            }
        }

        // texto

        textFont(fuente);
        fill(223, 246, 58);
        textSize(tamCuadrado / 10 * 9);
        text("Score", tamCuadrado * 16, (tamCuadrado * 16) + tamCuadrado / 2);
        text(score, tamCuadrado * 16, (tamCuadrado * 17) + tamCuadrado / 2);
        text("Nivel", tamCuadrado * 16, (tamCuadrado * 18) + tamCuadrado / 2);
        text(nivel, tamCuadrado * 17, (tamCuadrado * 19) + tamCuadrado / 2);
        textSize(tamCuadrado / 10 * 8);
        text("Tetronimos Caidos", tamCuadrado * 14, (tamCuadrado * 20) + tamCuadrado / 2);
        textSize(tamCuadrado / 10 * 9);
        text((int) piezasCaidas, tamCuadrado * 17, (tamCuadrado * 21 ) + tamCuadrado / 2);

        text("Siguiente:", tamCuadrado * 15, tamCuadrado * 2);
        text("En espera:", tamCuadrado * 15, tamCuadrado * 9);

        return continueGame;
    }

    public static boolean deadline() {
        boolean dead = false;

        for (int i = 0; !dead && i < 10; i++) {
            if (partida.obtenerBloque(20, i).obtenerColor() != Color.VACIO) {
                dead = true;
            }
        }
        return dead;
    }

    @Override
    public void keyPressed() {
        boolean info;
        if (endGame == 1) {
            switch (keyCode) {
            case 32:

                while (pieza.caer()) {
                    control = 10;
                    score += 2 * nivel;
                    hardDrop = true;
                }

                break;
            case 40:
                info = pieza.caer();
                if (info) {
                    score += 1 * nivel;
                }
                control += info ? 1 : 0;
                break;
            case 39:
                info = pieza.moverDer();
                break;
            case 38:
                if (!pieza.rotarUp()) {
                    pieza.rotarUp();
                }
                break;
            case 37:
                info = pieza.moverIzq();
                break;
            case 67:
                cambio();
                break;
            case 90:

                if (!pieza.rotarZ()) {
                    pieza.rotarZ();
                }
                break;
            default:
            }
        } else if (endGame == 0) {
            if (keyCode == 8) {
                name = name.length() > 0 ? name.substring(0, name.length() - 1) : name;
            }
            if (name.length() <= 5 && keyPressed) {
                name += Character.isLetter(key) ? key : "";
            }
            if (keyCode == 10) {
                if (guardarScore) {
                    Puntuacion sScore = new Puntuacion(score, name);
                    TableroPuntuaciones prueba = new TableroPuntuaciones();

                    prueba.guardarPuntuacion(sScore);
                    guardarScore = false;
                }
            }
        } else if (endGame == 3) {
            if (newGame && keyCode == 10) {
                newGameEvent();
                partida.nuevoTablero();
            }

        }

    }

    private static void cambio() {
        if (cambio) {
            if (!piezaGuardada) {
                partida.borraTetronimo(pieza);
                canguro = Tetronimo.dispensadora(pieza.obtenerClase());
                pieza = cola.sacar();
                piezaGuardada = true;
                cambio = false;
            } else {
                Tetronimo aux;
                partida.borraTetronimo(pieza);
                aux = pieza;
                pieza = canguro;
                canguro = Tetronimo.dispensadora(aux.obtenerClase());
                cambio = false;
            }
        }

    }

    private void endGameEvent() {

        int tamCuadrado = (alto) / 22;
        PImage scorebkg = loadImage(
                this.getClass().getResource("/fondo/scorebkg/" + backgroundFrameScore + ".gif").getPath());
        PFont fuente = createFont(this.getClass().getResource("/fuente/INVASION2000.TTF").getPath(), 32);
        scorebkg.resize(alto, ancho);
        if (frameCount % 2 == 0) {
            backgroundFrameScore = backgroundFrameScore < 36 ? backgroundFrameScore + 1 : 1;
        }
        if (endGameCountDownStart) {
            endGameCountDown = reloj;
            endGameCountDownStart = false;
        }

        if (reloj - endGameCountDown < 4) {
            textFont(fuente);
            fill(232, 232, 2);
            textSize((2 * tamCuadrado) + tamCuadrado / 5 * 4);
            text("*GAME OVER*", tamCuadrado, tamCuadrado * 11);
        } else {
            textFont(fuente);
            fill(234, 190, 63);
            textSize((2 * tamCuadrado) + tamCuadrado / 5 * 4);

            noTint();

            if (guardarScore) {
                image(scorebkg, 0, 0);
                text("*SCORE*", (tamCuadrado * 5) + (tamCuadrado / 2), tamCuadrado * 3);
                textSize(((2 * tamCuadrado) + tamCuadrado / 5 * 4) / 10 * 7);
                text(score, tamCuadrado * 5, tamCuadrado * 5);
                text("DIGITA TU NOMBRE", tamCuadrado, tamCuadrado * 12);
                if (frameCount % 8 != 0) {
                    fill(255, 255, 255);
                    text(name, tamCuadrado * 7, tamCuadrado * 14);
                }
            } else {
                tint(202, 175, 233);
                image(scorebkg, 0, 0);
                noTint();
                textSize(((2 * tamCuadrado) + tamCuadrado / 5 * 4) / 2);
                text("*MEJORES PUNTAJES*", (tamCuadrado * 2) + (tamCuadrado / 2), tamCuadrado * 3);
                textSize(((2 * tamCuadrado) + tamCuadrado / 5 * 4) / 10 * 3);
                TableroPuntuaciones scoreBoard = new TableroPuntuaciones();
                scoreBoard = scoreBoard.mejoresDiez();
                text(scoreBoard.toString(), tamCuadrado * 4, tamCuadrado * 5);
                endGame = 3;
                newGame = true;
                if (frameCount % 15 != 0) {
                    text("Presione enter para continuar", tamCuadrado * 4, tamCuadrado * 21);
                    text("esc para salir", tamCuadrado * 8, tamCuadrado * 23);
                }

            }

        }

    }

    private static void newGameEvent() {
        // reinicia todas las variables
        newGame = false;
        score = 0;
        canguro = null;
        name = "";
        backgroundFrame = 1;
        nivel = 1;
        cambiarFase = 10;
        backgroundFrameScore = 1;
        endGame = 1;
        bloqueo = 0;
        endGameCountDown = 0;
        piezasCaidas = 1;
        nextp = true;
        hardDrop = false;
        piezaGuardada = false;
        cambio = true;
        guardarScore = true;
        endGameCountDownStart = true;
    }

    private static int calculaPuntaje(int lineas) {
        int points;
        switch (lineas) {
        case 1:
            points = 100;
            break;
        case 2:
            points = 300;
            break;
        case 3:
            points = 500;
            break;
        case 4:
            points = 800;
            break;
        default:
            points = 0;
        }

        return points;
    }

    @Override
    public void exit() {
        TableroPuntuaciones cierre = new TableroPuntuaciones();
        dispose();
        System.exit(0);
    }

}