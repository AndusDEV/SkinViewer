package pl.andus.SkinViewer.skin;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

import pl.andus.SkinViewer.Utils;
import pl.andus.SkinViewer.logger.Logger;

public class Skin {

    public BufferedImage skinImage;
    private BufferedImage head;
    private BufferedImage body;
    private BufferedImage armLeft;
    private BufferedImage armRight;
    private BufferedImage legLeft;
    private BufferedImage legRight;
    private BufferedImage ogSkinImage;
    private BufferedImage full;
    private Logger log;

    public Skin(String usr){
        log = new Logger();
        BufferedImage image = null;
        try {
            URL url = new URL(usr);
            log.info("Connecting to: " + url);
            url = url.openConnection().getURL();
            image = ImageIO.read(url);
        } catch (Exception e) {
            log.error("Error reading skin: " + e.toString());
        }

        head = image.getSubimage(8, 8, 8, 8);
        body = image.getSubimage(20, 20, 8, 12);
        armLeft = image.getSubimage(44, 20, 8, 12);
        armRight = Utils.flipImage(armLeft);
        legLeft = image.getSubimage(4, 20, 4, 12);
        legRight = Utils.flipImage(legLeft);
        skinImage = new BufferedImage(16, 32, BufferedImage.TYPE_INT_ARGB);
        skinImage.getGraphics().drawImage(head, 4, 0, 8, 8, null);
        skinImage.getGraphics().drawImage(body, 4, 8, 8, 12, null);
        skinImage.getGraphics().drawImage(armLeft, 0, 8, 4, 12, null);
        skinImage.getGraphics().drawImage(armRight, 12, 8, 4, 12, null);
        skinImage.getGraphics().drawImage(legLeft, 4, 20, 4, 12, null);
        skinImage.getGraphics().drawImage(legRight, 8, 20, 4, 12, null);
    }

    public BufferedImage getSkin() {
        return skinImage;
    }

    public BufferedImage getOgSkin(String usr) {

        log = new Logger();
        BufferedImage ogimage = null;
        try {
            URL url = new URL(usr);
            log.info("Connecting to: " + url);
            url = url.openConnection().getURL();
            ogimage = ImageIO.read(url);
        } catch (Exception e) {
            log.error("Error reading skin: " + e.toString());
        }

        if(ogimage.getHeight(null) == 64) {
            full = ogimage.getSubimage(0, 0, 64, 64);
            ogSkinImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            ogSkinImage.getGraphics().drawImage(full, 0, 0, 64, 64, null);
        } else if(ogimage.getHeight(null) == 32) {
            full = ogimage.getSubimage(0, 0, 64, 32);
            ogSkinImage = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);
            ogSkinImage.getGraphics().drawImage(full, 0, 0, 64, 32, null);
        } else {

        }

        return ogSkinImage;
    }
}
