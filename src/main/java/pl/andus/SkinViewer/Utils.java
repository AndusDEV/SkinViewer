package pl.andus.SkinViewer;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playExp() {
        URL file = null;
        try {
            file = new URL("https://github.com/AndusDEV/SkinViewer/blob/main/expsound.wav?raw=true");
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }
        Applet.newAudioClip(file).play();
    }

    //Not mine
    public static BufferedImage flipImage(BufferedImage src) {
        BufferedImage bufferedImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bufferedImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bufferedImage = op.filter(src, null);
        return bufferedImage;
    }
}
