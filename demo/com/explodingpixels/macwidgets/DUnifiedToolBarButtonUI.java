package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

public class DUnifiedToolBarButtonUI {

    public static void main(String[] args) {

        Icon preferences = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                "NSImage://NSPreferencesGeneral").getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        TriAreaComponent unifiedToolBar = MacWidgetFactory.createUnifiedToolBar();
        AbstractButton macWidgetsButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Preferences", preferences));
//        macWidgetsButton.setEnabled(false);
        unifiedToolBar.addComponentToLeft(MacWidgetFactory.createSpacer(0, 0));
        unifiedToolBar.addComponentToLeft(macWidgetsButton);

        JToolBar regularToolBar = new JToolBar();
        JToggleButton javaPreferencesButton = new JToggleButton("Preferences", preferences);
//        javaPreferencesButton.setEnabled(false);
        javaPreferencesButton.setHorizontalTextPosition(AbstractButton.CENTER);
        javaPreferencesButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        javaPreferencesButton.setIconTextGap(0);
        javaPreferencesButton.setMargin(new Insets(0, 0, 0, 0));
        regularToolBar.add(javaPreferencesButton);

        JFrame frame = new JFrame();
        unifiedToolBar.installWindowDraggerOnWindow(frame);
        MacUtils.makeWindowLeopardStyle(frame.getRootPane());
        WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
        frame.add(unifiedToolBar.getComponent(), BorderLayout.NORTH);
        frame.add(regularToolBar, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
