import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.HashMap;

public class ImageHandler {
    private HashMap<String, BufferedImage> images;

    public ImageHandler(String filepath) {
        this.images = new HashMap<String, BufferedImage>();

        try (Stream<Path> paths = Files.walk(Paths.get(filepath))) { // stream within try to prevent resource leak
            paths.filter(Files::isRegularFile) // same as "file -> file.isRegularFile()"
                    .forEach(path -> images.put(new File(path.toString()).getName(), this.fetchImage(path.toString())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage fetchImage(String filepath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public BufferedImage getImage(String filename) {
        return this.images.get(filename);
    }
}