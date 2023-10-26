import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {
    public int x, y;
    public Dimension size;

    protected String direction;
    protected int frame;
    public int spriteLayer;
    public boolean canCollide;

    protected ArrayList<BufferedImage> images;
    protected ArrayList<Sprite> sprites;

    public Sprite(int[] coords, Dimension size, ArrayList<Sprite> sprites) {
        this.x = coords[0];
        this.y = coords[1];

        this.size = size;

        this.direction = "right";
        this.frame = 0;
        this.spriteLayer = 0;
        this.canCollide = false;

        this.images = new ArrayList<BufferedImage>();
        this.sprites = sprites;
        this.sprites.add(this);
    }

    public ArrayList<Sprite> getSprites() {
        return this.sprites;
    }

    public void draw(int x, int y, Graphics2D g2) {
        int frameDelayMultiper = 6;
        if (this.frame >= this.images.size() * frameDelayMultiper) {
            this.frame = 0;
        }

        BufferedImage image = this.images.get(this.frame / frameDelayMultiper);
        if (this.direction.equals("left")) {
            g2.drawImage(
                    image,
                    (int) (x + this.size.getWidth()),
                    y,
                    (int) (-this.size.getWidth()),
                    (int) (this.size.getHeight()),
                    null);

        } else {
            g2.drawImage(
                    image,
                    x,
                    y,
                    (int) (this.size.getWidth()),
                    (int) (this.size.getHeight()),
                    null);
        }

        this.frame++;
    }
}