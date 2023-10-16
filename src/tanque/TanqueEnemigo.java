package tanque;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class TanqueEnemigo {
    private int x;
    private int y;
    private int velocidad;
    private boolean visible;
    private long lastShootTime;
    private long tiempoEntreDisparos = 10000; // Tiempo entre disparos (en milisegundos)

    public TanqueEnemigo(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidad = 1;
        this.visible = true;
        this.lastShootTime = System.currentTimeMillis();
    }

    public void mover() {
        x += velocidad;

        if (x >= 580 || x <= 0) {
            velocidad = -velocidad;
            y += 10; // Cambia 10 a la cantidad que desees que se desplace hacia abajo
        }
    }

    public void dibujar(Graphics g) {
        if (visible) {
            // Dibuja el cuerpo del tanque
            g.setColor(Color.red);
            g.fillRect(x, y, 20, 20);
            
            // Dibuja la torreta del tanque
            g.fillRect(x + 7, y - 10, 6, 10);
            
            // Dibuja el cañón del tanque apuntando hacia abajo
            g.setColor(Color.black);
            g.fillRect(x + 9, y, 2, 10);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    public boolean puedeDisparar() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastShootTime) >= tiempoEntreDisparos;
    }

    public void disparar(ArrayList<BalaEnemigo> balasEnemigas) {
        if (puedeDisparar()) {
            int balaX = x + 10; // Ajusta la posición de inicio de la bala
            int balaY = y + 20; // Ajusta la posición de inicio de la bala
            BalaEnemigo bala = new BalaEnemigo(balaX, balaY);
            balasEnemigas.add(bala);
            lastShootTime = System.currentTimeMillis(); // Actualizar el tiempo del último disparo
        }
    }
}