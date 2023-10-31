import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {
    protected int x, y;
    public Dimension size;

    protected int frame;
    public int spriteLayer;

    protected ArrayList<BufferedImage> images;

    public Sprite(int[] coords, Dimension size) {
        this.x = coords[0];
        this.y = coords[1];

        this.size = size;
        this.frame = 0;
        this.spriteLayer = 0;

        this.images = new ArrayList<BufferedImage>();
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw(int x, int y, Graphics2D g2) {
        BufferedImage image = this.images.get(0);
        g2.drawImage(
                image,
                x,
                y,
                (int) (this.size.getWidth()),
                (int) (this.size.getHeight()),
                null);
    }
}