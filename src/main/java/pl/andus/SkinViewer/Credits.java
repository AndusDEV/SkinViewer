package pl.andus.SkinViewer;

import pl.andus.SkinViewer.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Credits {
    private static BufferedImage skinMrAnduss;

    public Credits(String mrUrl) {
        Logger log = new Logger();
        BufferedImage image = null;

        try {
            URL url = new URL(mrUrl);
            log.info("Connecting to: " + mrUrl);
            url = url.openConnection().getURL();
            image = ImageIO.read(url);
        } catch (Exception e) {
            log.error("Error reading skin: " + e);
        }
        assert image != null;
        BufferedImage head = image.getSubimage(8, 8, 8, 8);
        skinMrAnduss = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        skinMrAnduss.getGraphics().drawImage(head, 0, 0, 32, 32, null);
    }

    public static BufferedImage getMrSkin(String mrUrl) {
        return skinMrAnduss;
    }
}
