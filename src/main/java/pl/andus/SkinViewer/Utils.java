package pl.andus.SkinViewer;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;


import pl.andus.SkinViewer.logger.Logger;


public class Utils {
    private static final Logger log = new Logger();

    public static URL urlFromString(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("Hm.. Converting of " + url + " url haven't worked.");
            e.printStackTrace();
            return null;
        }
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void playExp(String path) {
        URL file = null;
        try {
            file = new URL("https://github.com/AndusDEV/SkinViewer/expsound.wav");
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }
        Applet.newAudioClip(file).play();
    }
}
