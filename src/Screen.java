import javax.swing.JPanel;
import java.lang.Math;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Screen extends JPanel implements Runnable {
    // constants
    private final int SCREEN_WIDTH = 1200;
    private final int SCREEN_HEIGHT = 600;
    private final int TILE_SIZE = 200;
    private final int FPS = 60;
    private final long SPACEKEYCOOLDOWN = FPS * 2000000;

    // game assets, system resources, and input
    private Thread gameThread;
    private KeyHandler keyHandler;
    private ImageHandler imageHandler;

    // game counters and mechanics
    private boolean cardsFlipped;
    private long spaceKeyTime;
    private int warTurns;
    private double screenShakeOffset;

    // decks and sprites
    private Deck playerDeck, computerDeck;

    public Screen() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.keyHandler = new KeyHandler();
        this.addKeyListener(this.keyHandler);
        this.setFocusable(true); // focuses JPanel to receive key input

        // image and camera sprite group
        this.imageHandler = new ImageHandler("sprites");
        this.warTurns = -1;

        // initializes decks
        int deckY = SCREEN_HEIGHT / 2 - TILE_SIZE / 2;
        this.playerDeck = new Deck(
                new Point(SCREEN_WIDTH / 4 - TILE_SIZE * 3 / 8, deckY ),
                TILE_SIZE,
                this.imageHandler);

        this.computerDeck = this.playerDeck.copy();
        this.computerDeck.setCoords(SCREEN_WIDTH * 3 / 4 - TILE_SIZE * 3 / 8, deckY);

        // splits cards between player and computer decks
        this.playerDeck.fill();
        this.playerDeck.shuffle();
        this.playerDeck.splitDeck(this.computerDeck);

    }

    /**
     * Displays a deck a cards onto graphics screen
     * 
     * @param deck
     * @param g2
     */
    public void displayDeck(Deck deck, Graphics2D g2) {
        if (deck == null) {
            throw new IllegalArgumentException();
        }

        // displays at most the top three cards of the deck
        Point deckCoords = deck.getCoords();
        int cardsDisplayed = (deck.cards.size() > 3) ? 3 : deck.cards.size();

        for (int i = cardsDisplayed - 1; i >= 0; i--) {
            Sprite sprite = (Sprite) deck.cards.get(i);
            double[] pos = {deckCoords.getX(), deckCoords.getY()};
            pos[1] += i * (TILE_SIZE / 10) + this.screenShakeOffset;

            sprite.draw(pos[0], pos[1], g2);
        }

        // displays at most the top three cards of the discard pile
        cardsDisplayed = (deck.discardPile.size() > 3) ? 3 : deck.discardPile.size();

        for (int i = cardsDisplayed - 1; i >= 0; i--) {
            Sprite sprite = deck.discardPile.get(i);
            double[] pos = {deckCoords.getX(), deckCoords.getY()};
            pos[0] += sprite.coords.getX() + TILE_SIZE * (pos[0] > SCREEN_WIDTH / 2 ? -1 : 1);
            pos[1] += sprite.coords.getY() + i * (TILE_SIZE / 10) + this.screenShakeOffset;

            sprite.draw(pos[0], pos[1], g2);
        }

        // displays the number of cards in the deck
        double[] pos = {deckCoords.getX(), deckCoords.getY()};
        pos[1] += TILE_SIZE * 11 / 8 + this.screenShakeOffset;

        g2.setColor(new Color(50, 50, 50));
        g2.setFont(new Font("sans-serif", Font.BOLD, TILE_SIZE / 8));
        g2.drawString(deck.cards.size() + " cards", (int) pos[0], (int) pos[1]);
    }

    /**
     * Automatically creates "repaint" method in the class
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to Graphics2D
        // Graphics 2D class extends the Graphics class to provide more sophisticated
        // control over geometry, coordinate transformations, color, and layout

        // fills graphics with a single color
        g2.setColor(new Color(220, 220, 220));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // displays decks
        this.displayDeck(playerDeck, g2);
        this.displayDeck(computerDeck, g2);

        g2.dispose(); // cleans up graphics content and releases any system resources it uses
    }

    /**
     * Handles strikes on a space bar
     * Locks the space bar on press to prevent holding
     */
    public void handleSpaceStrike() {
        if (System.nanoTime() - this.spaceKeyTime > SPACEKEYCOOLDOWN
                && this.keyHandler.spacePressed) {

            this.spaceKeyTime = System.nanoTime();
            fightBattle();

        }
    }

    public void fightBattle() {
        if (!this.cardsFlipped || this.warTurns > -1) {
            this.playerDeck.draw();
            this.computerDeck.draw();

        } else {
            Card playerCard = this.playerDeck.getTopDiscard();
            Card computerCard = this.computerDeck.getTopDiscard();
            // compares the weights of both wars
            int diff = playerCard.compareRank(computerCard);
            if (diff > 0) {
                // player's card is heavier
                this.playerDeck.winCards(this.computerDeck.discardPile);

            } else if (diff < 0) {
                // computer's card is heavier
                this.computerDeck.winCards(this.playerDeck.discardPile);

            } else {
                // war
                this.warTurns = 3;

            }
        }

        // does not decrement war turns or shake screen until war is declared
        this.cardsFlipped = !this.cardsFlipped;
        if (!(this.warTurns > -1)) {
            return;
        }

        if (this.warTurns == 0) {
            this.screenShakeOffset = TILE_SIZE / 5;

        } else {
            this.screenShakeOffset = 5;
        }

        this.warTurns--;
    }

    /**
     * Incrementaly reduces screen shake
     */
    public void dampenScreenShake() {
        if (Math.abs(this.screenShakeOffset) > 0) {
            this.screenShakeOffset *= -0.9;

            // sets minute screen shake to 0
            if (Math.abs(this.screenShakeOffset) < 0.5) {
                this.screenShakeOffset = 0;
            }

        }
    }

    public void update() {
        if (this.playerDeck.cards.isEmpty() || this.computerDeck.cards.isEmpty()) {
            return;
        }

        this.handleSpaceStrike();
        this.dampenScreenShake();
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