package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class DBottomBar {

    public static void main(String[] args) {

        JButton leftButton = new JButton(new ImageIcon(
                DBottomBar.class.getResource("/com/explodingpixels/macwidgets/icons/AddItem16.png")));
        leftButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        leftButton.putClientProperty("JButton.segmentPosition", "first");
        leftButton.setFocusable(false);

        JButton rightButton = new JButton(new ImageIcon(
                DBottomBar.class.getResource("/com/explodingpixels/macwidgets/icons/RemoveItem16.png")));
        rightButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        rightButton.putClientProperty("JButton.segmentPosition", "last");
        rightButton.setFocusable(false);

        ButtonGroup group = new ButtonGroup();
        group.add(leftButton);
        group.add(rightButton);

        JButton lockButton = new JButton(new ImageIcon(
                DBottomBar.class.getResource("/com/explodingpixels/macwidgets/icons/lock.png")));
        lockButton.putClientProperty("JButton.buttonType", "textured");

        JTextArea textArea = new JTextArea();

        BottomBar bottomBar = new BottomBar(BottomBarSize.LARGE);
        bottomBar.addComponentToLeft(leftButton, 0);
        bottomBar.addComponentToLeft(rightButton);
        bottomBar.addComponentToCenter(MacWidgetFactory.createEmphasizedLabel("362 Items"));
        bottomBar.addComponentToRight(lockButton);

        JFrame frame = new JFrame();
        bottomBar.installWindowDraggerOnWindow(frame);
        MacUtils.makeWindowLeopardStyle(frame.getRootPane());
        WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
        frame.add(bottomBar.getComponent(), BorderLayout.SOUTH);
        frame.add(textArea, BorderLayout.CENTER);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }

}
