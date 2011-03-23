package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class DSourceListExpansionListener {

    public static void main(String[] args) {

        final SourceListExpansionListener expansionListener = new SourceListExpansionListener() {
            public void sourceListItemExpanded(SourceListItem item) {
                System.out.println(item.getText() + " was expanded.");
            }

            public void sourceListItemCollapsed(SourceListItem item) {
                System.out.println(item.getText() + " was collapsed.");
            }

            public void sourceListCategoryExpanded(SourceListCategory category) {
                System.out.println(category.getText() + " was expanded.");
            }

            public void sourceListCategoryCollapsed(SourceListCategory category) {
                System.out.println(category.getText() + " was collapsed.");
            }
        };

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                SourceList sourceList = DSourceListMail.createSourceList();
                sourceList.addSourceListExpansionListener(expansionListener);

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
