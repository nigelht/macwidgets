package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

public class DPreferencesTabBar {


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Icon preferences =
                        new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                                "NSImage://NSPreferencesGeneral").getScaledInstance(32, 32, Image.SCALE_SMOOTH));

                Icon userAccounts =
                        new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                                "NSImage://NSUserAccounts").getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                Icon mobileMe =
                        new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                                "NSImage://NSDotMac").getScaledInstance(32, 32, Image.SCALE_SMOOTH));

                PreferencesTabBar tabBar = MacWidgetFactory.createUnifiedPreferencesTabBar();
                tabBar.addTab("General", preferences, null);
                tabBar.addTab("Accounts", userAccounts, null);
                tabBar.addTab("MobileMe", mobileMe, null);
//                tabBar.addTab("General", preferences, null);

                JFrame frame = new JFrame("A Java Preferences Window");
                tabBar.installWindowDraggerOnWindow(frame);
                MacUtils.makeWindowLeopardStyle(frame.getRootPane());
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);

                frame.add(tabBar.getComponent(), BorderLayout.NORTH);
                frame.add(new JTextArea(), BorderLayout.CENTER);
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }

}
