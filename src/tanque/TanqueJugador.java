package tanque;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class TanqueJugador {
    private int x;
    private int y;
    private int velocidad;
    private final int VELOCIDAD_DISPARO = 500; // Tiempo de recarga entre disparos (en milisegundos)
    private long tiempoUltimoDisparo; // Hora del último disparo
    private boolean visible;

    public TanqueJugador() {
        x = 300; // Posición inicial en x
        y = 400; // Posición inicial en y
        velocidad = 0; // Velocidad inicial
        visible = true;
        tiempoUltimoDisparo = 0;
    }

    public void mover() {
        x += velocidad;

        if (x < 0) {
            x = 0;
        }

        if (x > 550) {
            x = 550;
        }
    }

    public void disparar(ArrayList<Bala> balas) {
        long tiempoActual = System.currentTimeMillis();
        if (tiempoActual - tiempoUltimoDisparo >= VELOCIDAD_DISPARO) {
            Bala bala = new Bala(x + 15, y);
            balas.add(bala);
            tiempoUltimoDisparo = tiempoActual;
        }
    }

    public void dibujar(Graphics g) {
        if (visible) {
            // Dibuja el cuerpo del tanque
            g.setColor(Color.green); // Color del cuerpo
            g.fillRect(x, y, 40, 20); // Cuerpo del tanque

            // Dibuja el cañón del tanque en negro
            g.setColor(Color.black);
            g.fillRect(x + 18, y - 10, 4, 10);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moverIzquierda() {
        velocidad = -2;
    }

    public void moverDerecha() {
        velocidad = 2;
    }

    public void detener() {
        velocidad = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 20); // Ajusta el tamaño del rectángulo según el tamaño del jugador
    }
}

