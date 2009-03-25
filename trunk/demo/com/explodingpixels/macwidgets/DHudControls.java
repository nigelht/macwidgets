package com.explodingpixels.macwidgets;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class DHudControls {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JLabel label = HudWidgetFactory.createHudLabel("Label");

                JButton button = HudWidgetFactory.createHudButton("Button");

                JCheckBox checkBox = HudWidgetFactory.createHudCheckBox("Check Box");
                checkBox.setSelected(true);

                JComboBox comboBox = HudWidgetFactory.createHudComboBox(
                        new DefaultComboBoxModel(new String[]{"Item One", "Item Two", "Item Three"}));

                JTextField textField = HudWidgetFactory.createHudTextField("Text field");
                textField.setColumns(8);

                HudWindow hudWindow = new HudWindow("");
                JDialog dialog = hudWindow.getJDialog();

                hudWindow.getContentPane().setLayout(
                        new FormLayout("10dlu,left:p", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p"));
                CellConstraints cc = new CellConstraints();
                hudWindow.getContentPane().add(label, cc.xy(2, 2));
                hudWindow.getContentPane().add(button, cc.xy(2, 4));
                hudWindow.getContentPane().add(checkBox, cc.xy(2, 6));
                hudWindow.getContentPane().add(comboBox, cc.xy(2, 8));
                hudWindow.getContentPane().add(textField, cc.xy(2, 10));

                dialog.setSize(500, 400);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });


    }

}
