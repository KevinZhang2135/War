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

    public Screen() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.keyHandler = new KeyHandler();
        this.addKeyListener(this.keyHandler);
        this.setFocusable(true); // focuses JPanel to receive key input

        this.imageHandler = new ImageHandler("sprites");
        this.sprites = new ArrayList<Sprite>();

        String[] suits = { "clubs", "diamonds", "spades", "hearts" };
        LinkedList<String> ranks = new LinkedList<>();

        for (int i = 2; i <= 10; i++) {
            ranks.add(i + "");
        }

        ranks.add(new String[] { "ace", "jack", "king", "queen" });

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.size(); j++) {
                String filename = suits[i] + "_" + ranks.get(j).data + ".png";
                System.out.println(filename);
                int[] coords = new int[2];
                coords[0] = j * TILE_SIZE * 3 / 4;
                coords[1] = i * TILE_SIZE;

                Sprite sprite = new Sprite(
                        coords,
                        new Dimension(this.TILE_SIZE * 3 / 4, this.TILE_SIZE),
                        this.sprites);

                sprite.images.add(this.imageHandler.getImage(filename));

            }
        }

        Sprite sprite = new Sprite(
                new int[] { 0, this.TILE_SIZE * 4 },
                new Dimension(this.TILE_SIZE * 3 / 4, this.TILE_SIZE),
                this.sprites);

        sprite.images.add(this.imageHandler.getImage("card_back.png"));

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