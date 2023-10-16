package tanque;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BalaEnemigo {
    private int x;
    private int y;
    private final int VELOCIDAD = 5;
    private boolean colisionada;

    public BalaEnemigo(int x, int y) {
        this.x = x;
        this.y = y;
        colisionada = false;
    }

    public void mover() {
        y += VELOCIDAD; // La bala enemiga se mueve hacia abajo
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, 10, 10); // Ajusta el tamaño de la bala aquí
    }

    public boolean intersecta(TanqueJugador jugador) {
        if (colisionada) {
            return false; // Si la bala enemiga ya colisionó, no puede volver a hacerlo.
        }

        Rectangle balaRect = new Rectangle(x, y, 10, 10);
        Rectangle jugadorRect = jugador.getBounds();
        boolean colision = balaRect.intersects(jugadorRect);

        if (colision) {
            colisionada = true; // Marcar la bala enemiga como colisionada
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
        return y > Tanque.HEIGHT; // Considera que las balas enemigas pueden estar fuera del área visible
    }

    public boolean isColisionada() {
        return colisionada;
    }
}