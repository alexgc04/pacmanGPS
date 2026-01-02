import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 20;
    private static final String[] MAP_LAYOUT = {
        "####################",
        "#..................#",
        "#.######..######...#",
        "#.######..######...#",
        "#..................#",
        "#..####...##...#####",
        "#..####...##...#####",
        "#..................#",
        "#.######..##..######",
        "#..................#",
        "#...############...#",
        "#..................#",
        "#.######..##..######",
        "#..................#",
        "#..####...##...#####",
        "#..................#",
        "#..####...##...#####",
        "#..................#",
        "#..................#",
        "####################"
    };
    private static final int ROWS = MAP_LAYOUT.length;
    private static final int COLS = MAP_LAYOUT[0].length();
    private Timer timer;
    private Pacman pacman;
    private Ghost[] ghosts;
    // Aquí puedes definir el mapa y la lógica de puntos

    public Board() {
        setFocusable(true);
        setBackground(Color.BLACK);
        pacman = new Pacman(180, 300);
        Point redSpawn = findNearestPointSpawn(new Point(180, 180));
        Point pinkSpawn = findNearestPointSpawn(new Point(60, 60));
        Point cyanSpawn = findNearestPointSpawn(new Point(300, 60));
        ghosts = new Ghost[] {
            new Ghost(redSpawn.x, redSpawn.y, Color.RED),
            new Ghost(pinkSpawn.x, pinkSpawn.y, Color.PINK),
            new Ghost(cyanSpawn.x, cyanSpawn.y, Color.CYAN)
        };
        timer = new Timer(40, this);
        timer.start();
        addKeyListener(new PacmanKeyAdapter());
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
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                char cell = MAP_LAYOUT[row].charAt(col);
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;
                if (cell == '#') {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                } else if (cell == '.') {
                    g.setColor(Color.WHITE);
                    g.fillOval(x + TILE_SIZE / 2 - 3, y + TILE_SIZE / 2 - 3, 6, 6);
                }
            }
        }
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + pacman.getScore(), 10, 410);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
        // Aquí puedes agregar colisiones y lógica de puntos
        repaint();
    }

    private Point findNearestPointSpawn(Point preferredPosition) {
        int startCol = preferredPosition.x / TILE_SIZE;
        int startRow = preferredPosition.y / TILE_SIZE;
        if (isPointCell(startCol, startRow)) {
            return toPosition(startCol, startRow);
        }
        int maxRadius = Math.max(ROWS, COLS);
        for (int radius = 1; radius <= maxRadius; radius++) {
            for (int row = startRow - radius; row <= startRow + radius; row++) {
                for (int col = startCol - radius; col <= startCol + radius; col++) {
                    if (Math.abs(row - startRow) + Math.abs(col - startCol) != radius) {
                        continue;
                    }
                    if (isPointCell(col, row)) {
                        return toPosition(col, row);
                    }
                }
            }
        }
        return preferredPosition;
    }

    private boolean isPointCell(int col, int row) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS && MAP_LAYOUT[row].charAt(col) == '.';
    }

    private Point toPosition(int col, int row) {
        return new Point(col * TILE_SIZE, row * TILE_SIZE);
    }

    private class PacmanKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            pacman.keyPressed(e);
        }
    }
}
