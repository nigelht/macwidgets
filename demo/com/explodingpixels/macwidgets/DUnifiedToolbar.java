package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.BorderLayout;

public class DUnifiedToolbar {

    public static void main(String[] args) {

        JToggleButton leftButton = new JToggleButton(new ImageIcon(
                DUnifiedToolbar.class.getResource("/com/explodingpixels/macwidgets/icons/sourceViewNormal.png")));
        leftButton.setSelectedIcon(new ImageIcon(
                DUnifiedToolbar.class.getResource("/com/explodingpixels/macwidgets/icons/sourceViewNormalSelected.png")));
        leftButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        leftButton.putClientProperty("JButton.segmentPosition", "first");
        leftButton.setFocusable(false);

        JToggleButton rightButton = new JToggleButton(new ImageIcon(
                DUnifiedToolbar.class.getResource("/com/explodingpixels/macwidgets/icons/ColumnViewTemplate.png")));
        rightButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        rightButton.putClientProperty("JButton.segmentPosition", "last");
        rightButton.setFocusable(false);

        ButtonGroup group = new ButtonGroup();
        group.add(leftButton);
        group.add(rightButton);

        LabeledComponentGroup viewButtons = new LabeledComponentGroup("View", leftButton, rightButton);

        Icon blueGlobeIcon = new ImageIcon(DUnifiedToolbar.class.getResource(
                "/com/explodingpixels/macwidgets/icons/DotMac.png"));
        Icon greyGlobeIcon = new ImageIcon(DUnifiedToolbar.class.getResource(
                "/com/explodingpixels/macwidgets/icons/Network.png"));
        Icon gear = new ImageIcon(DUnifiedToolbar.class.getResource(
                "/com/explodingpixels/macwidgets/icons/Advanced.png"));

        AbstractButton greyGlobeButton =
                MacButtonFactory.makeUnifiedToolBarButton(
                        new JButton("Network", greyGlobeIcon));
        greyGlobeButton.setEnabled(false);

        UnifiedToolBar toolBar = new UnifiedToolBar();

        toolBar.addComponentToLeft(viewButtons.getComponent());
        toolBar.addComponentToCenter(MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("MobileMe", blueGlobeIcon)));
        toolBar.addComponentToCenter(greyGlobeButton);
//        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(
//                new JButton("Network", greyGlobeIcon)));
        toolBar.addComponentToCenter(MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Advanced", gear)));

        JTextField textField = new JTextField(10);
        textField.putClientProperty("JTextField.variant", "search");
        toolBar.addComponentToRight(new LabeledComponentGroup("Search", textField).getComponent());

        JTextArea textArea = new JTextArea();

        JFrame frame = new JFrame();
        toolBar.installWindowDraggerOnWindow(frame);
        MacUtils.makeWindowLeopardStyle(frame.getRootPane());
        WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
        frame.add(toolBar.getComponent(), BorderLayout.NORTH);
        frame.add(textArea, BorderLayout.CENTER);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
