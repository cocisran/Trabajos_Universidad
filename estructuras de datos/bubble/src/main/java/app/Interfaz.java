package main.java.app;

import main.java.sub.lista.*;

import java.util.Random;

import processing.core.PFont;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.core.PGraphics;

public class Interfaz extends PApplet {
    int largo, ancho, pos = 0;
    int[] lineas;
    Random gen = new Random();

    public static void main(String[] args) {
        PApplet.main("main.java.app.Interfaz");
    }

    @Override
    public void settings() {
        ancho = displayWidth * 7 / 8;
        largo = displayHeight * 7 / 8;
        size(ancho, largo);
    }

    @Override
    public void setup() {
        lineas = new int[240];
        for (int i = 0; i < lineas.length; i++) {
            lineas[i] = gen.nextInt(height * 4 / 5);

        }
        frameRate(25);

    }

    @Override
    public void draw() {

        int j = 0;
        background(255);
        textSize(height / 20);
        text("BUBBLE SORT", width / 3, height / 10);

        if (pos < lineas.length - 1) {
            for (int i = 0; i < lineas.length - pos - 1; i++) {
                if (lineas[i] > lineas[i + 1]) {
                    int aux = lineas[i];
                    lineas[i] = lineas[i + 1];
                    lineas[i + 1] = aux;
                    for (int ib = 0; ib < lineas.length; ib++) {

                        if (ib != i) {
                            stroke(255, 255, 255);
                        }

                        fill(169, 169, 169);
                        rect(j, height - lineas[ib], width / 200, height);
                        fill(255, 0, 0);
                        stroke(0, 0, 0);
                        rect(j, (height - lineas[ib]) - width / 200, width / 200, width / 200);
                        j += width / 200;
                    }
                }
            }
            pos++;
        } else {

            for (int ib = 0; ib < lineas.length; ib++) {
                stroke(255, 255, 255);
                fill(169, 169, 169);
                rect(j, height - lineas[ib], width / 200, height);
                stroke(0, 0, 0);
                fill(255, 0, 0);
                rect(j, (height - lineas[ib]) - width / 200, width / 200, width / 200);
                j += width / 200;
            }
            if (frameCount % 150 == 0) {
                pos = 0;
                for (int i = 0; i < lineas.length; i++) {
                    lineas[i] = gen.nextInt(height * 4 / 5);
                }
            }
        }

    }

    @Override
    public void mouseClicked() {

    }

    @Override
    public void keyPressed() {

    }
}
