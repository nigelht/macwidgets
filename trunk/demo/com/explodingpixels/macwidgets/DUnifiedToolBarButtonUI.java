package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Insets;

public class DUnifiedToolBarButtonUI {

    public static void main(String[] args) {

        Icon preferences = new ImageIcon(DUnifiedToolBarButtonUI.class.getResource(
                "/com/explodingpixels/macwidgets/icons/PreferencesGeneral.png"));

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
