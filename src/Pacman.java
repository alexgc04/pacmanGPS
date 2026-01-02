import java.awt.*;
import java.awt.event.*;

public class Pacman {
    private int x, y;
    private Direction direction = Direction.LEFT;
    private int score = 0;
    private int halfHearts = 10;
    private int shieldHalfHearts = 0;
    private static final int SIZE = 20;
    private static final int STEP = 4;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x, y, SIZE, SIZE, direction.getAngle(), 300);
    }

    public void move() {
        switch (direction) {
            case LEFT:
                if (!Board.isWallCollision(x - STEP, y, SIZE)) x -= STEP;
                break;
            case RIGHT:
                if (!Board.isWallCollision(x + STEP, y, SIZE)) x += STEP;
                break;
            case UP:
                if (!Board.isWallCollision(x, y - STEP, SIZE)) y -= STEP;
                break;
            case DOWN:
                if (!Board.isWallCollision(x, y + STEP, SIZE)) y += STEP;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: direction = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: direction = Direction.RIGHT; break;
            case KeyEvent.VK_UP: direction = Direction.UP; break;
            case KeyEvent.VK_DOWN: direction = Direction.DOWN; break;
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        score += value;
    }

    public void heal(int halfHeartsAmount) {
        halfHearts = Math.min(10, halfHearts + halfHeartsAmount);
    }

    public void applyGoldHeart() {
        if (halfHearts < 10) {
            halfHearts = 10;
        } else {
            shieldHalfHearts = 4;
        }
    }

    public void applyDamage(int damageHalfHearts) {
        int remaining = damageHalfHearts;
        if (shieldHalfHearts > 0) {
            int used = Math.min(shieldHalfHearts, remaining);
            shieldHalfHearts -= used;
            remaining -= used;
        }
        if (remaining > 0) {
            halfHearts = Math.max(0, halfHearts - remaining);
        }
    }

    public boolean isDead() {
        return halfHearts <= 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public Point getCenter() {
        return new Point(x + SIZE / 2, y + SIZE / 2);
    }

    public int getHalfHearts() {
        return halfHearts;
    }

    public int getShieldHalfHearts() {
        return shieldHalfHearts;
    }
}
