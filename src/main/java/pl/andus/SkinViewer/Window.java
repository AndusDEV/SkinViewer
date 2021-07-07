package pl.andus.SkinViewer;

import com.sun.jna.Structure;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import pl.andus.SkinViewer.logger.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class Window extends JFrame{

    public wForm winForm = new wForm();
    public static JFrame frame;
    public static final Logger logger = new Logger();

    public Window() throws IOException {
        super(Constants.title);
        this.add(winForm);
        this.setPreferredSize(Constants.standardSize);
        this.setResizable(Constants.resize);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        winForm.setPreferredSize(Constants.standardSize);
        this.pack();

        this.setVisible(true);

        WindowListener wl = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                CloseOperation();
            }
        };
        this.addWindowListener(wl);
    }

    public static void main(String[] args) throws IOException {
        Utils.createFolders();
        initDRPC();
        createNewPresence();

        frame = new Window();
    }

    public static void initDRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) ->
                logger.info("Welcome " + user.username + "#" + user.discriminator + "!"))
                .build();
        DiscordRPC.discordInitialize(Constants.discord_rpc_id, handlers, true);
    }

    public static void createNewPresence() {
        DiscordRichPresence rich = new DiscordRichPresence.Builder("Not displaying any skin right now.").setDetails("v" + Constants.version + " By AndusDEV.").setBigImage("icon", "SkinViewer v" + Constants.version).setSmallImage("mranduss", "By AndusDEV/MrAnduss").build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public static void CloseOperation() {
        logger.info("Closing Discord RPC hook");
        DiscordRPC.discordShutdown();
        logger.info("Closing App");
        System.exit(0);
    }
}
