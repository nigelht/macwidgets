package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class DSourceListContextMenuProvider {

    public static void main(String[] args) {

        final SourceListContextMenuProvider menuProvider = new SourceListContextMenuProvider() {

            public void customizeContextMenu(JPopupMenu popupMenu) {
                popupMenu.add(new JMenuItem("Generic Menu for SourceList"));
            }

            public void customizeContextMenu(JPopupMenu popupMenu, SourceListItem item) {
                popupMenu.add(new JMenuItem("Menu for " + item.getText()));
            }

            public void customizeContextMenu(JPopupMenu popupMenu, SourceListCategory category) {
                popupMenu.add(new JMenuItem("Menu for " + category.getText()));
            }
        };

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                SourceList sourceList = DSourceListMail.createSourceList();
                sourceList.setSourceListContextMenuProvider(menuProvider);

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
