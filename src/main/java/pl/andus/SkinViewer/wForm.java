package pl.andus.SkinViewer;

import me.kbrewster.exceptions.APIException;
import me.kbrewster.mojangapi.MojangAPI;
import me.kbrewster.mojangapi.profile.Profile;
import pl.andus.SkinViewer.logger.Logger;
import pl.andus.SkinViewer.skin.Skin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static me.kbrewster.mojangapi.MojangAPI.getUUID;

public class wForm extends JPanel {

    public Image secImage;
    public Image image;
    public Image ogImage;
    public JLabel skinLabel = new JLabel();
    public JLabel secSkinLabel = new JLabel();

    JLabel ogLabel = new JLabel();
    JLabel ogSkinLabel = new JLabel();
    Profile userProfile;
    JTextField usernameTf = new JTextField();
    public Image mrAndussImg;

    JCheckBox secSkinCb = new JCheckBox("Show/Hide Second Layer");

    //for future use (displaying/hiding only some of second layer)
    JCheckBox hatCb = new JCheckBox("Hat");
    JCheckBox jacketCb = new JCheckBox("Jacket");
    JCheckBox armLcb = new JCheckBox("Left Sleeve");
    JCheckBox armRcb = new JCheckBox("Right Sleeve");
    JCheckBox legLcb = new JCheckBox("Left Pants");
    JCheckBox legRcb = new JCheckBox("Right Pants");

    public JLayeredPane layeredPane = new JLayeredPane();

    private Logger log = new Logger();

    public wForm() {
        this.setLayout(null);
        JPanel pa = mainPanel();
        JPanel upa = userPanel();

        pa.setBounds(0,0, 790, 480);
        upa.setBounds(0,480, 790, 100);

        this.add(pa);
        this.add(upa);

        this.setVisible(true);
    }

    public JPanel mainPanel() {
        JPanel p = new JPanel();
        p.setLayout(null);

        p.setPreferredSize(new Dimension(600, 600));
        p.setBackground(new Color(100, 120, 136));

        layeredPane.setBounds(0, 0, 790, 580);

        ogLabel.setBounds(650, 325, 170, 30);
        ogLabel.setOpaque(false);

        ogSkinLabel.setBounds(680, 365, 115, 115);
        ogSkinLabel.setOpaque(false);

        //layers
        layeredPane.add(secSkinLabel);
        layeredPane.add(skinLabel);

        p.add(layeredPane);
        p.add(secSkinCb);
        p.add(ogLabel);
        p.add(ogSkinLabel);
        p.setVisible(true);

        return p;
    }

    public JPanel userPanel() {
        JPanel up = new JPanel();
        up.setLayout(null);

        up.setPreferredSize(new Dimension(200, 600));
        up.setBackground(new Color(0, 119, 255));

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(0,10,82, 10);
        usernameLabel.setOpaque(false);

        JLabel showedNowLabel = new JLabel();
        showedNowLabel.setBounds(270, 15, 270, 15);
        showedNowLabel.setOpaque(false);

        JLabel verLabel = new JLabel("Version: " + Constants.version);
        verLabel.setBounds(690, 70, 140, 25);

        usernameTf.setBounds(80, 5, 100, 20);

        JButton btnResult = new JButton("Show skin");
        btnResult.setBounds(20, 30, 140, 25);

        JButton btnChanges = new JButton("Changelog");
        btnChanges.setBounds(635, 15, 140, 25);

        JButton btnCredits = new JButton("Credits");
        btnCredits.setBounds(635, 45, 140, 25);

        JButton btnNmcProf = new JButton("NameMC Profile");
        btnNmcProf.setBounds(310, 35, 160, 25);
        btnNmcProf.setVisible(false);

        JButton btnTexture = new JButton("Skin URL");
        btnTexture.setBounds(310, 65, 160, 25);
        btnTexture.setVisible(false);

        up.add(usernameLabel);
        up.add(showedNowLabel);
        up.add(verLabel);
        up.add(usernameTf);
        up.add(btnResult);
        up.add(btnChanges);
        up.add(btnCredits);
        up.add(btnNmcProf);
        up.add(btnTexture);
        up.setVisible(true);

        btnResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    createNewSkinLabel();

                    //skinLabel changes
                    skinLabel.setBounds(310,70,160,320);
                    skinLabel.setIcon(new ImageIcon(image));

                    //show sec skin checkbox
                    secSkinCb.setBackground(new Color(100, 120, 136));
                    secSkinCb.setBounds(285, 30, 200, 13);
                    if (ogImage.getHeight(null) == 57) {
                        secSkinCb.setEnabled(false);
                        log.info("Second Layer not avalable for this skin(64x32)");
                    } else {
                        secSkinCb.setEnabled(true);
                    }


                    //ogSkinLabel changes
                    ogLabel.setText("Original Skin File:");
                    ogSkinLabel.setIcon(new ImageIcon(ogImage));
                    if(ogSkinLabel.getHeight() == 64) {
                        ogSkinLabel.setBounds(680, 365, 115, 115);
                    } else if (ogSkinLabel.getHeight() == 32) {
                        ogSkinLabel.setBounds(680, 365, 115, 57);
                    }

                    //showedNowLabel changes
                    showedNowLabel.setText("<html>Now displaying skin of: <font style=\"color:#9dff00\">" + userProfile.getName() + "</font></html>");

                    btnNmcProf.setVisible(true);

                    btnNmcProf.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            Utils.openWebpage("https://namemc.com/profile/" + userProfile.getId());
                        }
                    });

                    btnTexture.setVisible(true);

                    btnTexture.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            Utils.openWebpage(userProfile.getTextures().getTextures().getSkin().getUrl());
                        }
                    });
                } catch (APIException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame changesFrame = new JFrame("MC Skin Viewer - Changelog");

                JEditorPane editorPane = new JEditorPane();
                editorPane.setEditable(false);

                try {
                    editorPane.setPage(new URL("https://raw.githubusercontent.com/AndusDEV/SkinViewer/main/changelog.md"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changesFrame.add(new JScrollPane(editorPane));

                changesFrame.setPreferredSize(Constants.changesSize);
                changesFrame.setResizable(Constants.resize);
                changesFrame.setLocationRelativeTo(null);
                changesFrame.pack();
                changesFrame.setVisible(true);
            }
        });

        btnCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame creditsFrame = new JFrame("MC Skin Viewer - Credits");

                try {
                    creditsMrAnduss();
                } catch (APIException | IOException e) {
                    e.printStackTrace();
                }

                JPanel cp = creditsPanel();

                cp.setBounds(0,0, 790, 480);

                creditsFrame.add(cp);

                creditsFrame.setPreferredSize(Constants.changesSize);
                creditsFrame.setResizable(Constants.resize);
                creditsFrame.setLocationRelativeTo(null);
                creditsFrame.pack();
                creditsFrame.setVisible(true);
            }
        });

        secSkinCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(secSkinCb.isSelected()) {
                    secSkinLabel.setVisible(true);
                    secSkinLabel.setBounds(310,70,160,320);
                    secSkinLabel.setIcon(new ImageIcon(secImage));
                    log.info("Second layer visible");
                } else {
                    secSkinLabel.setVisible(false);
                    log.info("Second layer hidden");
                }
            }
        });

        return up;
    }

    public void createNewSkinLabel() throws APIException, IOException {
        userProfile = MojangAPI.getProfile(getUUID(usernameTf.getText()));
        String userUrl = userProfile.getTextures().getTextures().getSkin().getUrl();
        Skin skin = new Skin(userUrl);
        image = skin.getSkin().getScaledInstance(160, 320, 0);
        ogImage = skin.getOgSkin(userProfile.getTextures().getTextures().getSkin().getUrl());
        if(ogImage.getHeight(null) == 32) {
            ogImage = skin.getOgSkin(userProfile.getTextures().getTextures().getSkin().getUrl()).getScaledInstance(115, 57, 0);
        } else {
            ogImage = skin.getOgSkin(userProfile.getTextures().getTextures().getSkin().getUrl()).getScaledInstance(115, 115, 0);
        }

        if (ogImage.getHeight(null) == 57) {

        } else {
            secImage = skin.getsecSkin().getScaledInstance(160, 320, 0);
        }
    }

    public void creditsMrAnduss() throws APIException, IOException {
        userProfile = MojangAPI.getProfile(getUUID("MrAnduss"));
        String mrUrl = userProfile.getTextures().getTextures().getSkin().getUrl();
        Credits credits = new Credits(mrUrl);
        mrAndussImg = credits.getMrSkin().getScaledInstance(32, 32, 0);
    }

    public JPanel creditsPanel() {
        JPanel creditsPanel = new JPanel();

        JPanel mrAndussPanel = new JPanel();
        mrAndussPanel.setPreferredSize(new Dimension(190, 270));
        mrAndussPanel.setBackground(new Color(0, 119, 255));
        mrAndussPanel.setLocation(0,0);

        JPanel mojAPIpanel = new JPanel();
        mojAPIpanel.setPreferredSize((new Dimension(190, 270)));
        mojAPIpanel.setBackground(new Color(255, 0, 0));
        mojAPIpanel.setLocation(0,0);

        JPanel nameMCpanel = new JPanel();
        nameMCpanel.setPreferredSize((new Dimension(190, 270)));
        nameMCpanel.setBackground(new Color(153, 153, 153));
        nameMCpanel.setLocation(0,0);

        JPanel associationPanel = new JPanel();
        associationPanel.setPreferredSize((new Dimension(580, 95)));
        associationPanel.setBackground(new Color(206, 0, 255));
        associationPanel.setLocation(0,275);

        //MrAnduss Panel
        JLabel mrAnduss = new JLabel("AndusDEV");
        mrAnduss.setBounds(0, 0, 55, 35);
        mrAnduss.setIcon(new ImageIcon(mrAndussImg));
        mrAnduss.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnGithubAdev = new JButton("Github");
        btnGithubAdev.setBounds(0, 0, 10, 15);
        btnGithubAdev.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnGithubAdev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Utils.openWebpage("https://github.com/AndusDEV/");
            }
        });

        JButton btnGithubRepo = new JButton("Github Repo");
        btnGithubAdev.setBounds(0, 0, 10, 15);
        btnGithubAdev.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnGithubRepo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Utils.openWebpage("https://github.com/AndusDEV/SkinViewer");
            }
        });

        //MojangAPI Panel
        JLabel mojangApi = new JLabel("Mojang-API");
        mojangApi.setBounds(0, 0, 55, 35);
        mojangApi.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnGithubMojAPI = new JButton("Github");
        btnGithubMojAPI.setBounds(20, 0, 10, 15);
        btnGithubMojAPI.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnGithubMojAPI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Utils.openWebpage("https://github.com/KevinPriv/MojangAPI");
            }
        });

        //NameMC Panel
        JLabel nameMC = new JLabel("NameMC");
        nameMC.setBounds(0,0,55, 35);
        nameMC.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnNmcWebsite = new JButton("Website");
        btnNmcWebsite.setBounds( 20, 0, 10, 15);
        btnNmcWebsite.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnNmcWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Utils.openWebpage("https://namemc.com");
            }
        });

        //Associations Panel
        JLabel associationInfo = new JLabel("I'm NOT associated with Mojang and/or NameMC", JLabel.CENTER);
        associationInfo.setFont(new Font("Courier", Font.BOLD, 20));
        associationInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        mrAndussPanel.add(mrAnduss);
        mrAndussPanel.add(btnGithubAdev);
        mrAndussPanel.add(btnGithubRepo);

        mojAPIpanel.add(mojangApi);
        mojAPIpanel.add(btnGithubMojAPI);

        nameMCpanel.add(nameMC);
        nameMCpanel.add(btnNmcWebsite);

        associationPanel.add(associationInfo);

        mrAndussPanel.setLayout(new BoxLayout(mrAndussPanel, BoxLayout.PAGE_AXIS));
        mojAPIpanel.setLayout(new BoxLayout(mojAPIpanel, BoxLayout.PAGE_AXIS));
        nameMCpanel.setLayout(new BoxLayout(nameMCpanel, BoxLayout.PAGE_AXIS));
        associationPanel.setLayout(new BoxLayout(associationPanel, BoxLayout.PAGE_AXIS));

        creditsPanel.add(mrAndussPanel);
        creditsPanel.add(mojAPIpanel);
        creditsPanel.add(nameMCpanel);
        creditsPanel.add(associationPanel);

        return creditsPanel;
    }
}
