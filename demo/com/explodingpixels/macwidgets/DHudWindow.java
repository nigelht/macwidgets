package com.explodingpixels.macwidgets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DHudWindow {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame frame = new JFrame();
                frame.setSize(400, 400);
                frame.setVisible(true);

                JDialog dialog = new HudWindow("Window", frame).getJDialog();
//                frame.getRootPane().add(new JLabel("hello"));
//                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                dialog.setSize(300, 350);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
    }

}
