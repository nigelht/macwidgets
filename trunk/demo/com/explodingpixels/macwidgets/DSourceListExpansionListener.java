package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class DSourceListExpansionListener {
    private static Object[] willExpandOptions = {"Cancel Expansion", "Expand"};
    private static String willExpandText = "A branch node is about to be expanded.\n"
                                  + "Click \"Cancel Expansion\" to prevent it.";
    private static String willExpandTitle = "Tree Will Expand";

    public static void main(String[] args) {

        final SourceListExpansionListener expansionListener = new SourceListExpansionListener() {
            public void sourceListItemExpanded(SourceListItem item) {
                System.out.println("SourceListItem " +item.getText() + " was expanded.");
            }

            public void sourceListItemCollapsed(SourceListItem item) {
                System.out.println("SourceListItem " +item.getText() + " was collapsed.");
            }

            public void sourceListCategoryExpanded(SourceListCategory category) {
                System.out.println("SourceListCategory " +category.getText() + " was expanded.");
            }

            public void sourceListCategoryCollapsed(SourceListCategory category) {
                System.out.println("SourceListCategory " +category.getText() + " was collapsed.");
            }

            public boolean shouldExpandSourceListItem(SourceListItem item) {
                int n = JOptionPane.showOptionDialog(
                        null, willExpandText, willExpandTitle,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        willExpandOptions,
                        willExpandOptions[1]);

                if (n == JOptionPane.YES_OPTION) {
                    return false;
                }

                System.out.println("SourceListItem " +item.getText() + " will be expanded.");
                return true;
            }

            public boolean shouldCollapseSourceListItem(SourceListItem item) {
                System.out.println("SourceListItem " +item.getText() + " will be collapsed.");
                return true;
            }

            public boolean shouldExpandSourceListCategory(SourceListCategory category) {
                System.out.println("SourceListCategory " +category.getText() + " will be expanded.");
                return true;
            }

            public boolean shouldToCollapseSourceListCategory(SourceListCategory category) {
                System.out.println("SourceListCategory " +category.getText() + " will be collapsed.");
                return true;
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
