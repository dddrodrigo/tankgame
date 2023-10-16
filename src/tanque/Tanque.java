package tanque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Tanque extends JPanel implements ActionListener, KeyListener {
    private TanqueJugador tanque;
    private ArrayList<TanqueEnemigo> enemigos;
    private ArrayList<Bala> balas;
    private ArrayList<BalaEnemigo> balasEnemigos; // Nueva lista para las balas de los enemigos
    private javax.swing.Timer timer;
    private boolean gameStarted;
    private int nivel;
    private int enemigosEnNivel;

    public Tanque() {
        tanque = new TanqueJugador();
        enemigos = new ArrayList<>();
        balas = new ArrayList<>();
        balasEnemigos = new ArrayList<>(); // Inicializa la lista de balas de enemigos
        timer = new javax.swing.Timer(10, this);
        gameStarted = false;
        nivel = 1;
        enemigosEnNivel = 7;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            tanque.mover();
            for (TanqueEnemigo enemigo : enemigos) {
                enemigo.mover();
                if (enemigo.puedeDisparar()) {
                    int balaX = enemigo.getX() + 10;
                    int balaY = enemigo.getY() + 20;
                    BalaEnemigo bala = new BalaEnemigo(balaX, balaY);
                    balasEnemigos.add(bala); // Usa la lista de balas de enemigos
                }
            }
            for (Bala bala : balas) {
                bala.mover();
            }
            for (BalaEnemigo bala : balasEnemigos) {
                bala.mover();
            }
            verificarColisiones();

            if (enemigos.isEmpty()) {
                nivel++;
                enemigosEnNivel += 2;
                spawnEnemigos(enemigosEnNivel);
            }

            repaint();
        }
    }

    private void verificarColisiones() {
        ArrayList<Bala> balasPorEliminar = new ArrayList<>();
        ArrayList<TanqueEnemigo> enemigosPorEliminar = new ArrayList<>();

        for (Bala bala : balas) {
            for (TanqueEnemigo enemigo : enemigos) {
                if (bala.intersecta(enemigo)) {
                    balasPorEliminar.add(bala);
                    enemigosPorEliminar.add(enemigo);
                }
            }
        }

        balas.removeAll(balasPorEliminar);
        enemigos.removeAll(enemigosPorEliminar);
    }

    private void spawnEnemigos(int cantidad) {
        Random rand = new Random();
        enemigos.clear();

        for (int i = 0; i < cantidad; i++) {
            int x = rand.nextInt(600);
            int y = rand.nextInt(100);
            enemigos.add(new TanqueEnemigo(x, y));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameStarted) {
            tanque.dibujar(g);
            for (TanqueEnemigo enemigo : enemigos) {
                enemigo.dibujar(g);
            }
            for (Bala bala : balas) {
                bala.dibujar(g);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Nivel: " + nivel, getWidth() - 100, 20);
            g.drawString("Enemigos restantes: " + enemigos.size(), getWidth() - 200, 40);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Tanque Shooter", 200, 100);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Presiona SPACE para comenzar", 200, 300);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameStarted) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                tanque.moverIzquierda();
            }

            if (key == KeyEvent.VK_RIGHT) {
                tanque.moverDerecha();
            }

            if (key == KeyEvent.VK_SPACE) {
                tanque.disparar(balas);
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameStarted = true;
                nivel = 1;
                enemigosEnNivel = 7;
                spawnEnemigos(enemigosEnNivel);
                timer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tanque Game");
        Tanque game = new Tanque();
        frame.add(game);
        frame.setSize(600, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !game.gameStarted) {
                    game.gameStarted = true;
                    game.nivel = 1;
                    game.enemigosEnNivel = 7;
                    game.spawnEnemigos(game.enemigosEnNivel);
                    game.timer.start();
                }
            }
        });
    }
}