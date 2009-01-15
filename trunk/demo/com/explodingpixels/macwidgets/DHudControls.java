package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.HudCheckBoxUI;
import com.explodingpixels.macwidgets.plaf.HudComboBoxUI;
import com.explodingpixels.macwidgets.plaf.HudLabelUI;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class DHudControls {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JLabel label = new JLabel("Label");
                label.setUI(new HudLabelUI());

                AbstractButton button = HudWidgetFactory.createHudButton("Button");

                JCheckBox checkBox = new JCheckBox("Check Box");
                checkBox.setUI(new HudCheckBoxUI());

                JComboBox comboBox = new JComboBox(new String[]{"Item One", "Item Two", "Item Three"});
                comboBox.setUI(new HudComboBoxUI());
//                comboBox.setUI(new BasicComboBoxUI());

                HudWindow hudWindow = new HudWindow("");
                JDialog dialog = hudWindow.getJDialog();

                hudWindow.getContentPane().setLayout(
                        new FormLayout("20dlu,left:p", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu"));
                CellConstraints cc = new CellConstraints();
                hudWindow.getContentPane().add(label, cc.xy(2, 2));
                hudWindow.getContentPane().add(button, cc.xy(2, 4));
                hudWindow.getContentPane().add(checkBox, cc.xy(2, 6));
                hudWindow.getContentPane().add(comboBox, cc.xy(2, 8));

                dialog.setSize(500, 400);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });


    }

}
