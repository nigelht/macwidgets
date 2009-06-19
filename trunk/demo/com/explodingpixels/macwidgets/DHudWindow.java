package com.explodingpixels.macwidgets;

import javax.swing.*;

public class DHudWindow {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame frame = new JFrame();
                frame.setSize(400, 400);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//                JTextField textField = new JTextField("foo bar");
//                textField.setOpaque(false);
                
                HudWindow hud = new HudWindow("Window", frame);
//                hud.getContentPane().add(textField);
                
                JDialog dialog = hud.getJDialog();
                
//                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                dialog.setSize(300, 350);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
    }

}
