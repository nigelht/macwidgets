package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.ITunesTableUI;
import com.explodingpixels.widgets.TableUtils;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class DITunesTableUI {

    private static TableUtils.SortDelegate createDummySortDelegate() {
        return new TableUtils.SortDelegate() {
            public void sort(int columnModelIndex, TableUtils.SortDirection sortDirection) {
                // no implementation.
            }
        };
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                String[][] data = new String[][]{
                        {"All These Things I Hate (Revolve Around Me)", "Bullet For My Valentine", "The Poison"},
                        {"Cries In Vain", "Bullet For My Valentine", "The Poison"},
                        {"The End", "Bullet For My Valentine", "The Poison"},
                        {"Her Voice Resides", "Bullet For My Valentine", "The Poison"},
                        {"Hit The Floor", "Bullet For My Valentine", "The Poison"},
                        {"Intro", "Bullet For My Valentine Apocalyptica", "The Poison"},
                        {"The Poison", "Bullet For My Valentine", "The Poison"},
                        {"Room 409", "Bullet For My Valentine", "The Poison"},
                        {"Spit You Out", "Bullet For My Valentine", "The Poison"},
                        {"Suffocating Under Words Of Sorrow (What Can I Do)", "Bullet For My Valentine", "The Poison"},
                        {"Tears Don't Fall", "Bullet For My Valentine", "The Poison"},
                        {"4 Words (To Choke Upon)", "Bullet For My Valentine", "The Poison"},
                        {"10 Years Today", "Bullet For My Valentine", "The Poison"}
                };


                JTable table = new JTable(new DefaultTableModel(data, new String[]{"Name", "Artist", "Album"}));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setUI(new ITunesTableUI());
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(100);

                TableUtils.makeSortable(table, createDummySortDelegate());

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                JFrame frame = new JFrame();
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.setSize(400, 265);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
