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

    private Deck playerDeck, computerDeck, contestedDeck;

    public Screen() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.keyHandler = new KeyHandler();
        this.addKeyListener(this.keyHandler);
        this.setFocusable(true); // focuses JPanel to receive key input

        // image and camera sprite group
        this.imageHandler = new ImageHandler("sprites");

        // initializes decks
        this.playerDeck = new Deck(
            new int[] { 0, 0 },
            this.TILE_SIZE,
            this.imageHandler);
                
        this.computerDeck = this.playerDeck.copy();
        this.computerDeck.setCoords(500, 500);

        this.contestedDeck = this.playerDeck.copy();
        this.contestedDeck.setCoords(1000, 1000);
        this.playerDeck.fill();

    }

    // automatically creates "repaint" method in the class
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to Graphics2D
        // Graphics 2D class extends the Graphics class to provide more sophisticated
        // control over geometry, coordinate transformations, color, and layout

        for (int i = 0; i < playerDeck.size(); i++) {
            Sprite sprite = (Sprite) playerDeck.get(i);
            sprite.draw(sprite.x, sprite.y, g2);
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