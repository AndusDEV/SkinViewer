package pl.andus.SkinViewer;

import javax.swing.*;

public class Window extends JFrame{

    public wForm winForm = new wForm();
    public static JFrame frame;

    public Window() {
        super(Constants.title);
        this.add(winForm);
        this.setPreferredSize(Constants.standardSize);
        this.setResizable(Constants.resize);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        winForm.setPreferredSize(Constants.standardSize);
        this.pack();

        this.setVisible(true);
    }

    public static void main(String[] args) {
        frame = new Window();
    }
}
