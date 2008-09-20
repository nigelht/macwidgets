package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.TextProvider;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

public class DSourceListTreeUI {

    private static class MyTextProvider implements TextProvider {
        private final String fText;

        public MyTextProvider(String text) {
            fText = text;
        }

        public String getText() {
            return fText;
        }
    }

    public static void main(String[] args) {

        Icon mobileMeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSDotMac").
                        getScaledInstance(16, 16, Image.SCALE_SMOOTH));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultTreeModel model = new DefaultTreeModel(root);
        DefaultMutableTreeNode categoryOne = new DefaultMutableTreeNode("Category One");
        DefaultMutableTreeNode itemA = new DefaultMutableTreeNode("Item A");
        DefaultMutableTreeNode itemB = new DefaultMutableTreeNode(new MyTextProvider("Item B"));
        SourceListItem sourceListItem = new SourceListItem("Item C", mobileMeIcon);
        sourceListItem.setCounterValue(10);
        DefaultMutableTreeNode itemC = new DefaultMutableTreeNode(sourceListItem);

        root.add(categoryOne);
        categoryOne.add(itemA);
        categoryOne.add(itemB);
        categoryOne.add(itemC);

        JTree tree = MacWidgetFactory.makeSourceList(new JTree(model));
        final JScrollPane pane = MacWidgetFactory.makeSourceListScrollPane(new JScrollPane(tree));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.add(pane, BorderLayout.CENTER);
                frame.setSize(300, 350);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
