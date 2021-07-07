package pl.andus.SkinViewer;

import pl.andus.SkinViewer.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static void createFolders() throws IOException {
        Logger log = new Logger();
        
        String settsPath = System.getProperty("user.home") + File.separator + "MC SkinViewer";
        File settsDir = new File(settsPath);
        File settsFile = new File(settsPath + File.separator + "Settings.txt");

        if (settsDir.exists()) {
            log.info(settsDir + " already exists.");
        } else if (settsDir.mkdirs()) {
            log.info(settsDir + " was created.");
        } else {
            log.error(settsDir + " was not created!");
        }

        if (settsFile.isFile() || settsFile.createNewFile()) {
            if (settsFile.exists()) {
                log.info(settsFile + " already exists.");
            } else if (settsFile.createNewFile()) {
                log.info(settsFile + " was created.");
            } else {
                log.error(settsFile + " was not created!");
            }
        } else {
            log.error("For some reason Settings.txt is not a file!");
        }

        String themesPath = settsPath + File.separator + "Themes";
        File themesDir = new File(themesPath);

        if (themesDir.exists()) {
            log.info(themesDir + " already exists.");
        } else if (themesDir.mkdirs()) {
            log.info(themesDir + " was created.");
        } else {
            log.error(themesDir + "was not created!");
        }

        DownloadTheme(new URL("https://raw.githubusercontent.com/AndusDEV/SkinViewer/main/themes/forest.png"), themesPath + File.separator + "Forest.png");
    }

    public static void DownloadTheme(URL url, String name) throws IOException {
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(name));

        for ( int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
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
