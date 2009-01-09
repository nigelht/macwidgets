package com.explodingpixels.macwidgets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.BorderLayout;
import java.awt.Color;

public class DLabeledComponentGroup {

    public static LabeledComponentGroup createAnatomy() {
        JLabel itemA = new JLabel("A");
        itemA.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JLabel itemB = new JLabel("B");
        itemB.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLUE, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JLabel itemC = new JLabel("C");
        itemC.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));


        return new LabeledComponentGroup("Group Label", itemA, itemB, itemC);
    }

    public static LabeledComponentGroup createViewExample() {
        JToggleButton leftButton = new JToggleButton(new ImageIcon(
                DLabeledComponentGroup.class.getResource("/com/explodingpixels/macwidgets/icons/sourceViewNormal.png")));
        leftButton.setSelectedIcon(new ImageIcon(
                DLabeledComponentGroup.class.getResource("/com/explodingpixels/macwidgets/icons/sourceViewNormalSelected.png")));
        leftButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        leftButton.putClientProperty("JButton.segmentPosition", "first");
        leftButton.setFocusable(false);

        JToggleButton rightButton = new JToggleButton(new ImageIcon(
                DLabeledComponentGroup.class.getResource("/com/explodingpixels/macwidgets/icons/ColumnViewTemplate.png")));
        rightButton.putClientProperty("JButton.buttonType", "segmentedTextured");
        rightButton.putClientProperty("JButton.segmentPosition", "last");
        rightButton.setFocusable(false);

        return new LabeledComponentGroup("View", leftButton, rightButton);
    }

    public static LabeledComponentGroup createSearchExample() {
        JTextField textField = new JTextField(10);
        textField.putClientProperty("JTextField.variant", "search");
        textField.setFocusable(false);
        return new LabeledComponentGroup("Search", textField);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JPanel examplePanel = new JPanel();
        examplePanel.add(createViewExample().getComponent());
        examplePanel.add(createSearchExample().getComponent());

        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        frame.add(createAnatomy().getComponent(), BorderLayout.NORTH);
        frame.add(examplePanel, BorderLayout.SOUTH);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
