package pacman;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Pacman {
    private int x, y;
    private Direction direction = Direction.LEFT;
    private int score = 0;
    private int halfHearts = MAX_HALF_HEARTS;
    private int shieldHalfHearts = 0;
    private static final int SIZE = 20;
    private static final int STEP = 4;
    private static final int MAX_HALF_HEARTS = 10;
    private static final int GOLD_SHIELD_HALF_HEARTS = 4;
    private static final long INVINCIBILITY_DURATION_MS = 1500;
    private static final float INVINCIBILITY_ALPHA = 0.5f;
    private long lastCollisionTimeMs = -INVINCIBILITY_DURATION_MS; // start non-invincible

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (isInvincible()) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, INVINCIBILITY_ALPHA));
        }
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(x, y, SIZE, SIZE, direction.getAngle(), 300);
        g2d.dispose();
    }

    public void move() {
        switch (direction) {
            case LEFT:
                int newLeftX = Board.wrapXIfAllowed(x - STEP, y, SIZE);
                if (!Board.isWallCollision(newLeftX, y, SIZE)) x = newLeftX;
                break;
            case RIGHT:
                int newRightX = Board.wrapXIfAllowed(x + STEP, y, SIZE);
                if (!Board.isWallCollision(newRightX, y, SIZE)) x = newRightX;
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
        halfHearts = Math.min(MAX_HALF_HEARTS, halfHearts + halfHeartsAmount);
    }

    public void applyGoldHeart() {
        if (halfHearts < MAX_HALF_HEARTS) {
            halfHearts = MAX_HALF_HEARTS;
        } else {
            shieldHalfHearts = GOLD_SHIELD_HALF_HEARTS;
        }
    }

    public boolean applyCollisionDamage(int damageHalfHearts) {
        if (isInvincible() || damageHalfHearts <= 0) {
            return false;
        }
        applyDamage(damageHalfHearts);
        lastCollisionTimeMs = System.currentTimeMillis();
        return true;
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

    public boolean isInvincible() {
        return System.currentTimeMillis() - lastCollisionTimeMs < INVINCIBILITY_DURATION_MS;
    }

    /** Package-private for tests. */
    void setLastCollisionTimeMsForTest(long timestampMs) {
        this.lastCollisionTimeMs = timestampMs;
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

    public boolean isAtMaxHealth() {
        return halfHearts >= MAX_HALF_HEARTS;
    }
}
