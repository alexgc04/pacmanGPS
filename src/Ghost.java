import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private Direction direction;
    private Color color;
    private Random random = new Random();
    private Board board;
    
    private static final int SPRITE_SIZE = 20;

    public Ghost(int x, int y, Color color, Board board) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.board = board;
        this.direction = Direction.values()[random.nextInt(4)];
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 20, 20);
    }

    public void move() {
        if (random.nextInt(10) == 0) {
            direction = Direction.values()[random.nextInt(4)];
        }
        
        int newX = x;
        int newY = y;
        
        switch (direction) {
            case LEFT: newX -= 4; break;
            case RIGHT: newX += 4; break;
            case UP: newY -= 4; break;
            case DOWN: newY += 4; break;
        }
        
        // Verificar colisión con paredes antes de mover
        if (!board.isWall(newX, newY) && !board.isWall(newX + SPRITE_SIZE - 1, newY) &&
            !board.isWall(newX, newY + SPRITE_SIZE - 1) && !board.isWall(newX + SPRITE_SIZE - 1, newY + SPRITE_SIZE - 1)) {
            x = newX;
            y = newY;
        } else {
            // Si hay una pared, cambiar de dirección
            direction = Direction.values()[random.nextInt(4)];
        }
    }
}