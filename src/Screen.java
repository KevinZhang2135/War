import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Screen extends JPanel implements Runnable {
    // constants
    private final int SCREEN_WIDTH = 1200;
    private final int SCREEN_HEIGHT = 600;
    private final int TILE_SIZE = 200;
    private final int FPS = 60;

    // game assets, system resources, and input
    private Thread gameThread;

    private KeyHandler keyHandler;
    private boolean spaceStrike;
    private boolean cardsFlipped;
    private int warTurns;

    private ImageHandler imageHandler;

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

        // initializes decks
        int deckY = SCREEN_HEIGHT / 2 - TILE_SIZE / 2;
        this.playerDeck = new Deck(
                new int[] { SCREEN_WIDTH / 4 - TILE_SIZE * 3 / 8, deckY },
                TILE_SIZE,
                this.imageHandler);

        this.computerDeck = this.playerDeck.copy();
        this.computerDeck.setCoords(SCREEN_WIDTH * 3 / 4 - TILE_SIZE * 3 / 8, deckY);

        // initializes decks and splits it between player and computer decks
        this.playerDeck.fill();
        this.playerDeck.shuffle();
        System.out.println(this.playerDeck.cards.size());
        this.playerDeck.splitDeck(this.computerDeck);

    }

    /**
     * Handles strikes on a space bar
     * Locks the space bar on press to prevent holding
     */
    public void handleSpaceStrike() {
        // locks space bar after press
        if (this.keyHandler.spacePressed && !this.spaceStrike) {
            this.spaceStrike = true;
            fightBattle();
        }

        // unlocks space bar after release
        if (!this.keyHandler.spacePressed && this.spaceStrike) {
            this.spaceStrike = false;
        }
    }

    public void fightBattle() {
        if (!this.cardsFlipped || this.warTurns > 0) {
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

            System.out.println(playerDeck.cards.size() + " " + computerDeck.cards.size());
        }

        this.cardsFlipped = !this.cardsFlipped;
        if (this.warTurns > 0) {
            this.warTurns--;
        }
        

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
        int cardsDisplayed = (deck.cards.size() > 3) ? 3 : deck.cards.size();
        for (int i = cardsDisplayed - 1; i >= 0; i--) {
            Sprite sprite = (Sprite) deck.cards.get(i).data;
            int[] pos = deck.getCoords();
            pos[1] += i * (TILE_SIZE / 10);

            sprite.draw(pos[0], pos[1], g2);
        }

        // displays at most the top three cards of the discard pile
        cardsDisplayed = (deck.discardPile.size() > 3) ? 3 : deck.discardPile.size();
        for (int i = cardsDisplayed - 1; i >= 0; i--) {
            Sprite sprite = deck.discardPile.get(i).data;
            int[] pos = deck.getCoords();
            pos[0] += sprite.x + TILE_SIZE * (pos[0] > SCREEN_WIDTH / 2 ? -1 : 1);
            pos[1] += sprite.y + i * (TILE_SIZE / 10);

            sprite.draw(pos[0], pos[1], g2);
        }
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
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // displays decks
        this.displayDeck(playerDeck, g2);
        this.displayDeck(computerDeck, g2);

        g2.dispose(); // cleans up graphics content and releases any system resources it uses
    }

    public void update() {
        if (this.playerDeck.cards.isEmpty() || this.computerDeck.cards.isEmpty()) {
            return;
        }

        this.handleSpaceStrike();
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