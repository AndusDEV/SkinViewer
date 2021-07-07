package pl.andus.SkinViewer;

import pl.andus.SkinViewer.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Themes {
    static wForm wf;
    public static BufferedImage imgFor;
    public static BufferedImage imgGrass;
    public static Logger log = new Logger();

    static {
        try {
            imgFor = ImageIO.read(new File(System.getProperty("user.home") + File.separator + "MC SkinViewer"
                    + File.separator + "Themes" + File.separator + "Forest.png"));
            imgGrass = ImageIO.read(new File(System.getProperty("user.home") + File.separator + "MC SkinViewer"
                    + File.separator + "Themes" + File.separator + "Forest.png"));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static ImageIcon imgIforest = new ImageIcon(imgFor);
    public static ImageIcon imgIgrass = new ImageIcon(imgGrass);
    
    public Themes() {

    }

    public static void BlueTheme() {
        wf.mainPanel().setBackground(new Color(100, 120, 136));
        wf.userPanel().setBackground(new Color(0, 119, 255));
        wf.themeImgMP.setVisible(false);
        wf.themeImgUP.setVisible(false);
        ChangedTheme("Blue");
    }

    public static void ForestTheme() {
        wf.themeImgMP.setIcon(imgIforest);
        wf.themeImgUP.setIcon(imgIgrass);
        wf.themeImgMP.setVisible(true);
        wf.themeImgUP.setVisible(true);
        ChangedTheme("Forest");
    }

    public static void ChangedTheme(String themeName) {
        log.info("Changed Theme to: " + themeName);
    }

}
