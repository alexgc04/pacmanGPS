import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Pacman pacman;
    private Ghost[] ghosts;
    
    // Tamaño de cada celda del tablero
    private static final int BLOCK_SIZE = 20;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    
    // Nivel actual (0, 1, o 2 para los 3 niveles)
    private int currentLevel = 0;
    
    // Diseños de los 3 niveles
    // 0 = espacio vacío con punto, 1 = pared, 2 = espacio vacío sin punto
    private int[][][] levels = {
        // Nivel 1 - Laberinto clásico
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
            {1,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,1},
            {1,1,1,1,0,1,1,1,2,1,1,2,1,1,1,0,1,1,1,1},
            {1,1,1,1,0,1,2,2,2,2,2,2,2,2,1,0,1,1,1,1},
            {1,1,1,1,0,1,2,1,1,2,2,1,1,2,1,0,1,1,1,1},
            {2,2,2,2,0,2,2,1,2,2,2,2,1,2,2,0,2,2,2,2},
            {1,1,1,1,0,1,2,1,1,1,1,1,1,2,1,0,1,1,1,1},
            {1,1,1,1,0,1,2,2,2,2,2,2,2,2,1,0,1,1,1,1},
            {1,1,1,1,0,1,2,1,1,1,1,1,1,2,1,0,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,1,0,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,1,1},
            {1,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        },
        // Nivel 2 - Laberinto con pasillos
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,1,1,1,1,1,0,0,1,1,1,1,1,0,1,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,1,0,1,1,1,1,1,0,0,1,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        },
        // Nivel 3 - Laberinto complejo
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
            {1,0,1,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,0,1},
            {1,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
            {1,1,1,0,1,1,0,1,1,2,2,1,1,0,1,1,0,1,1,1},
            {1,0,0,0,0,0,0,1,2,2,2,2,1,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,2,2,2,2,1,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,1,2,2,2,2,1,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1},
            {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
            {1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
            {1,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        }
    };
    
    // Array que mantiene el estado de los puntos (true = punto presente, false = comido)
    private boolean[][] points;
    private int totalPoints;
    private int pointsEaten;

    public Board() {
        setFocusable(true);
        setBackground(Color.BLACK);
        initLevel();
        timer = new Timer(40, this);
        timer.start();
        addKeyListener(new PacmanKeyAdapter());
    }
    
    private void initLevel() {
        // Inicializar Pacman en posición segura
        pacman = new Pacman(180, 300, this);
        
        // Inicializar fantasmas
        ghosts = new Ghost[] {
            new Ghost(180, 180, Color.RED, this),
            new Ghost(60, 60, Color.PINK, this),
            new Ghost(300, 60, Color.CYAN, this)
        };
        
        // Inicializar puntos basados en el nivel actual
        points = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        totalPoints = 0;
        pointsEaten = 0;
        
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (levels[currentLevel][i][j] == 0) {
                    points[i][j] = true;
                    totalPoints++;
                } else {
                    points[i][j] = false;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        pacman.draw(g);
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
    }

    private void drawBoard(Graphics g) {
        // Dibujar paredes
        g.setColor(Color.BLUE);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (levels[currentLevel][i][j] == 1) {
                    g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
        
        // Dibujar puntos
        g.setColor(Color.YELLOW);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (points[i][j]) {
                    g.fillOval(j * BLOCK_SIZE + 7, i * BLOCK_SIZE + 7, 6, 6);
                }
            }
        }
        
        // Dibujar información del juego
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + pacman.getScore(), 10, 410);
        g.drawString("Level: " + (currentLevel + 1), 150, 410);
        g.drawString("Points: " + pointsEaten + "/" + totalPoints, 250, 410);
    }
    
    // Verificar si una posición tiene una pared
    public boolean isWall(int x, int y) {
        int col = x / BLOCK_SIZE;
        int row = y / BLOCK_SIZE;
        
        if (row < 0 || row >= BOARD_HEIGHT || col < 0 || col >= BOARD_WIDTH) {
            return true; // Fuera de límites se considera pared
        }
        
        return levels[currentLevel][row][col] == 1;
    }
    
    // Verificar colisión con puntos
    private void checkPointCollision() {
        int pacmanCol = pacman.getX() / BLOCK_SIZE;
        int pacmanRow = pacman.getY() / BLOCK_SIZE;
        
        if (pacmanRow >= 0 && pacmanRow < BOARD_HEIGHT && 
            pacmanCol >= 0 && pacmanCol < BOARD_WIDTH) {
            if (points[pacmanRow][pacmanCol]) {
                points[pacmanRow][pacmanCol] = false;
                pacman.addScore(10);
                pointsEaten++;
                
                // Verificar si se comieron todos los puntos
                if (pointsEaten >= totalPoints) {
                    nextLevel();
                }
            }
        }
    }
    
    // Avanzar al siguiente nivel
    private void nextLevel() {
        currentLevel++;
        if (currentLevel >= levels.length) {
            currentLevel = 0; // Volver al primer nivel
            JOptionPane.showMessageDialog(this, 
                "¡Felicidades! Has completado todos los niveles.\nScore: " + pacman.getScore(),
                "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
        }
        initLevel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
        checkPointCollision();
        repaint();
    }

    private class PacmanKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            pacman.keyPressed(e);
        }
    }
}