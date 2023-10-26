import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed, spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            this.leftPressed = true;
        }

        if (key == KeyEvent.VK_D) {
            this.rightPressed = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            this.spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            this.leftPressed = false;
        }

        if (key == KeyEvent.VK_D) {
            this.rightPressed = false;
        }

        if (key == KeyEvent.VK_SPACE) {
            this.spacePressed = false;
        }
    }
}