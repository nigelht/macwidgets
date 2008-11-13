/* Copyright 2008 The MathWorks, Inc. */

package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class DSourceListClickListener {

    private static String getPluralModifer(int clickCount) {
        return clickCount == 1 ? "" : "s";
    }

    public static void main(String[] args) {

        final SourceListClickListener clickListener = new SourceListClickListener() {
            public void sourceListItemClicked(SourceListItem item, Button button,
                                              int clickCount) {
                System.out.println(item.getText() + " clicked " + clickCount
                        + " time" + getPluralModifer(clickCount) + ".");
            }

            public void sourceListCategoryClicked(SourceListCategory category,
                                                  Button button, int clickCount) {
                System.out.println(category.getText() + " clicked " + clickCount
                        + " time" + getPluralModifer(clickCount) + ".");
            }
        };

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                SourceList sourceList = DSourceListMail.createSourceList();
                sourceList.addSourceListClickListener(clickListener);

                JFrame frame = new JFrame();
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.add(sourceList.getComponent(), BorderLayout.CENTER);
                frame.setSize(225, 250);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

            }
        });

    }
}
