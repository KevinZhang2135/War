import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Collections;

public class Screen extends JPanel implements Runnable {
    private final int SCREEN_WIDTH = 1200;
    private final int SCREEN_HEIGHT = 600;
    private final int TILE_SIZE = 100;
    private int FPS = 60;

    private Thread gameThread;
    private KeyHandler keyHandler;
    private ImageHandler imageHandler;

    private ArrayList<Sprite> sprites;
    private Sprite background;

    public Screen() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.keyHandler = new KeyHandler();
        this.addKeyListener(this.keyHandler);
        this.setFocusable(true); // focuses JPanel to receive key input

        this.imageHandler = new ImageHandler("sprites");
        this.sprites = new ArrayList<Sprite>();

        // this.background = new Sprite(
        //         new int[] { 0, 0 },
        //         new Dimension(SCREEN_HEIGHT * 2, SCREEN_HEIGHT),
        //         this.sprites);

        // this.background.spriteLayer = -1;
        // this.background.images.add(this.imageHandler.getImage("background.png"));

    }

    // automatically creates "repaint" method in the class
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to Graphics2D
        // Graphics 2D class extends the Graphics class to provide more sophisticated
        // control over geometry, coordinate transformations, color, and layout

        // controls camera scroll
        double offsetx = 0;
        double offsety = 0;

        this.background.draw(this.background.x, this.background.y, g2);
        Collections.sort(this.sprites, (Sprite s1, Sprite s2) -> s1.spriteLayer - s2.spriteLayer);
        for (Sprite sprite : this.sprites) {
            int offsetPosx = (int) (sprite.x - offsetx);
            int offsetPosy = (int) (sprite.y - offsety);
            sprite.draw(offsetPosx, offsetPosy, g2);
        }

        g2.dispose(); // cleans up graphics content and releases any system resources it uses

    }

    public void update() {
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start(); // automatically calls run method
    }

    // implements the thread method run which runs within the thread
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime;

        while (this.gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // sets fps of screen
            if (delta >= 1) {
                // redraws sprites and images
                this.repaint();

                // updates
                this.update();

                delta--;
            }
        }
    }
}