package com.explodingpixels.macwidgets;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DHudWindow {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new HudWindow("Window").getJFrame();
//                frame.getRootPane().add(new JLabel("hello"));
//                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.setSize(300, 350);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
