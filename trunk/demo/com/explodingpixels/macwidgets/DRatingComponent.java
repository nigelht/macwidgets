package com.explodingpixels.macwidgets;

import com.explodingpixels.data.Rating;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class DRatingComponent {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                RatingComponent ratingComponent = new RatingComponent(Rating.FOUR_STARS);

                JFrame frame = new JFrame();
                frame.add(ratingComponent.getComponent(), BorderLayout.CENTER);
                frame.setSize(150, 100);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
