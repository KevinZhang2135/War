import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Sprite {
    protected Point coords;
    public Dimension size;

    protected int frame;
    public int spriteLayer;

    protected ArrayList<BufferedImage> images;

    public Sprite(Point coords, Dimension size) {
        this.coords = coords;

        this.size = size;
        this.frame = 0;
        this.spriteLayer = 0;

        this.images = new ArrayList<BufferedImage>();
    }

    public void setCoords(int x, int y) {
        this.coords.move(x, y);
    }

    public void setCoords(double x, double y) {
        this.coords.move((int) x, (int) y);
    }

    public void draw(double x, double y, Graphics2D g2) {
        BufferedImage image = this.images.get(this.frame);
        g2.drawImage(
                image,
                (int) x,
                (int) y,
                (int) (this.size.getWidth()),
                (int) (this.size.getHeight()),
                null);
    }
}