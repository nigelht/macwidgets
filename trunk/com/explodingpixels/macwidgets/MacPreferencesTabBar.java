package com.explodingpixels.macwidgets;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MacPreferencesTabBar {

    private JPanel fPanel = new JPanel();

    public void addTabButton(String name, Icon icon, ActionListener listener) {
        JButton button = new CustomJButton(name, icon);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBackground(null);

        fPanel.add(button);
    }

    public JComponent getComponent() {
        return fPanel;
    }

    private static class CustomJButton extends JButton {

        public CustomJButton(String text, Icon icon) {
            super(text, icon);
        }

        public void paint(Graphics g) {
            super.paint(g);
        }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {

        MacPreferencesTabBar tabBar = new MacPreferencesTabBar();
        tabBar.addTabButton("Computer",
                new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSComputer")),
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });

        JFrame fFrame = new JFrame();

        // set custom OS X 10.5 client properties.
        fFrame.getRootPane().putClientProperty(
                "apple.awt.brushMetalLook", Boolean.TRUE);

        fFrame.add(tabBar.getComponent(), BorderLayout.NORTH);
        fFrame.add(new JButton("foo"), BorderLayout.SOUTH);

        fFrame.pack();
        fFrame.setLocationRelativeTo(null);
        fFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fFrame.setVisible(true);

    }

}
