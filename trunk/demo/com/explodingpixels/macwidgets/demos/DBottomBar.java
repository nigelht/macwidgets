package com.explodingpixels.macwidgets.demos;

import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class DBottomBar {

    public static void main(String[] args) {

        JButton leftButton = new JButton(new ImageIcon(
                DBottomBar.class.getResource("/com/explodingpixels/macwidgets/demos/icons/AddItem16.png")));
        leftButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        leftButton.putClientProperty("JButton.segmentPosition", "first");
        leftButton.setFocusable(false);

        JButton rightButton = new JButton(new ImageIcon(
                DBottomBar.class.getResource("/com/explodingpixels/macwidgets/demos/icons/RemoveItem16.png")));
        rightButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        rightButton.putClientProperty("JButton.segmentPosition", "last");
        rightButton.setFocusable(false);

        ButtonGroup group = new ButtonGroup();
        group.add(leftButton);
        group.add(rightButton);

        JButton lockButton = new JButton(new ImageIcon(
                DBottomBar.class.getResource("/com/explodingpixels/macwidgets/demos/icons/lock.png")));
        lockButton.putClientProperty("JButton.buttonType", "textured");

        JTextArea textArea = new JTextArea();

        TriAreaComponent bottomBar = MacWidgetFactory.createBottomBar(BottomBarSize.LARGE);
        bottomBar.addComponentToLeft(leftButton,0);
        bottomBar.addComponentToLeft(rightButton);
        bottomBar.addComponentToCenter(MacWidgetFactory.createEmphasizedLabel("362 Items"));
        bottomBar.addComponentToRight(lockButton);

        JFrame frame = new JFrame();
        bottomBar.installWindowDraggerOnWindow(frame);
        MacUtils.makeWindowLeopardStyle(frame.getRootPane());
        WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
        frame.add(bottomBar.getComponent(), BorderLayout.SOUTH);
        frame.add(textArea, BorderLayout.CENTER);
        frame.setSize(500,350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }

}
