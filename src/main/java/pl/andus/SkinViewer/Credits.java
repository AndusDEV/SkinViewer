package pl.andus.SkinViewer;

import pl.andus.SkinViewer.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Credits {
    private static BufferedImage skinMrAnduss;
    private BufferedImage head;
    private Logger log;

    public Credits(String mrUrl) {
        log = new Logger();
        BufferedImage image = null;

        try {
            URL url = new URL(mrUrl);
            log.info("Connecting to: " + mrUrl);
            url = url.openConnection().getURL();
            image = ImageIO.read(url);
        } catch (Exception e) {
            log.error("Error reading skin: " + e.toString());
        }
        head = image.getSubimage(8, 8, 8, 8);
        skinMrAnduss = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        skinMrAnduss.getGraphics().drawImage(head, 0, 0, 32, 32, null);
    }

    public static BufferedImage getMrSkin() {
        return skinMrAnduss;
    }
}
