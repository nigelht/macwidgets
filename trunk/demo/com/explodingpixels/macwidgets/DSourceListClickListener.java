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

//                JTree sourceList = new JTree(new String[]{
//                        "Node One", "Node Two", "Node Three", "Node Four",
//                        "Node Five"});
//                sourceList.setUI(new BasicTreeUI() {
//                    protected MouseListener createMouseListener() {
//                        return new MouseListener() {
//
//                            public void mouseClicked(MouseEvent e) {
//                                System.out.println("Node clicked.");
//                            }
//
//                            public void mousePressed(MouseEvent e) {
//                                System.out.println("Node pressed.");
//                            }
//
//                            public void mouseReleased(MouseEvent e) {
//                            }
//
//                            public void mouseEntered(MouseEvent e) {
//                            }
//
//                            public void mouseExited(MouseEvent e) {
//                            }
//                        };
//                    }
//                });
//
//                sourceList.addMouseListener(new MouseAdapter() {
//                    public void mouseClicked(MouseEvent e) {
//                        System.out.println("Node clicked.");
//                    }
//
//                    public void mousePressed(MouseEvent e) {
//                        System.out.println("Node pressed.");
//                    }
//                });

//                JScrollPane scrollPane = new JScrollPane(sourceList);

                JFrame frame = new JFrame();
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.add(sourceList.getComponent(), BorderLayout.CENTER);
//                frame.add(scrollPane, BorderLayout.CENTER);
                frame.setSize(225, 250);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

            }
        });

    }
}
