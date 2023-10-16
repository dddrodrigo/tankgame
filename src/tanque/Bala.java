package tanque;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bala {
    private int x;
    private int y;
    private final int VELOCIDAD = 5;
    private boolean colisionada;

    public Bala(int x, int y) {
        this.x = x;
        this.y = y;
        colisionada = false;
    }

    public void mover() {
        y -= VELOCIDAD; // Cambia el signo para que la bala se mueva hacia arriba
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, 10, 10); // Ajusta el tamaño de la bala aquí
    }

    public boolean intersecta(TanqueEnemigo enemigo) {
        if (colisionada) {
            return false; // Si la bala ya colisionó, no puede volver a hacerlo.
        }

        Rectangle balaRect = new Rectangle(x, y, 10, 10);
        Rectangle enemigoRect = enemigo.getBounds();
        boolean colision = balaRect.intersects(enemigoRect);

        if (colision) {
            colisionada = true; // Marcar la bala como colisionada
        }

        return colision;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFueraDePantalla() {
        return y < 0;
    }

    public boolean isColisionada() {
        return colisionada;
    }
}