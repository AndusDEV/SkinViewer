package pl.andus.SkinViewer;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import pl.andus.SkinViewer.logger.Logger;

import javax.swing.*;

public class Window extends JFrame{

    public wForm winForm = new wForm();
    public static JFrame frame;
    public static final Logger logger = new Logger();

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
        initDRPC();
        createNewPresence();

        frame = new Window();
    }

    public static void initDRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
        }).build();
        DiscordRPC.discordInitialize(Constants.discord_rpc_id, handlers, true);
    }

    public static void createNewPresence() {
        DiscordRichPresence rich = new DiscordRichPresence.Builder("Not displaying any skin right now.").setDetails("v" + Constants.version + " By AndusDEV.").setBigImage("icon", "SkinViewer v" + Constants.version).setSmallImage("mranduss", "By AndusDEV/MrAnduss").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
}
