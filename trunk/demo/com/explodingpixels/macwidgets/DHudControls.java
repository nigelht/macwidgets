package com.explodingpixels.macwidgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class DHudControls {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JLabel label = HudWidgetFactory.createHudLabel("Label");

                JButton button = HudWidgetFactory.createHudButton("Button");

                JCheckBox checkBox = HudWidgetFactory.createHudCheckBox("Check Box");
                checkBox.setSelected(true);

                final JComboBox comboBox = HudWidgetFactory.createHudComboBox(
                        new DefaultComboBoxModel(new String[]{"Item One", "Item Two", "Item Three"}));
//                        new DefaultComboBoxModel());

                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ((DefaultComboBoxModel) comboBox.getModel()).addElement("Item One");
                    }
                });
                comboBox.setEnabled(false);


                JTextField textField = HudWidgetFactory.createHudTextField("Text field");
                textField.setColumns(8);

                JTextField passwordField = HudWidgetFactory.createHudPasswordField("Password field");
                passwordField.setColumns(8);

                JRadioButton radioButton = HudWidgetFactory.createHudRadioButton("Radio Button");

                JSlider sliderWithoutTicks = HudWidgetFactory.createHudSlider();

                JSlider sliderWithTicks = HudWidgetFactory.createHudSlider();
                sliderWithTicks.setPaintTicks(true);

                HudWindow hudWindow = new HudWindow("");
                JDialog dialog = hudWindow.getJDialog();

                hudWindow.getContentPane().setLayout(
                        new FormLayout("10dlu,left:p",
                                "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p"));
                CellConstraints cc = new CellConstraints();
                hudWindow.getContentPane().add(label, cc.xy(2, 2));
                hudWindow.getContentPane().add(button, cc.xy(2, 4));
                hudWindow.getContentPane().add(checkBox, cc.xy(2, 6));
                hudWindow.getContentPane().add(comboBox, cc.xy(2, 8));
                hudWindow.getContentPane().add(textField, cc.xy(2, 10));
                hudWindow.getContentPane().add(passwordField, cc.xy(2, 12));
                hudWindow.getContentPane().add(radioButton, cc.xy(2, 14));
                hudWindow.getContentPane().add(sliderWithoutTicks, cc.xy(2, 16));
                hudWindow.getContentPane().add(sliderWithTicks, cc.xy(2, 18));

                dialog.setSize(250, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });


    }

}
