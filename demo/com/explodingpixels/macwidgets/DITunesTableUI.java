package com.explodingpixels.macwidgets;

import com.explodingpixels.data.Rating;
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

                Object[][] data = new String[][]{
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


                data = new Object[][]{
                        {Rating.FIVE_STARS, 4.0, "Cat"},
                        {Rating.TWO_STARS, 3.0, "Dog"},
                        {Rating.THREE_STARS, 8.0, "Parrot"},
                        {Rating.ONE_STAR, 5.0, "Goat"},
                        {Rating.FOUR_STARS, 2.0, "Tiger"},
                };

//                Object[][] data = new Object[][]{
//                        {1.0},
//                        {2.0},
//                };

//                JTable table = new JTable(new DefaultTableModel(data, new String[]{"Name", "Artist", "Album"})) {
                JTable table = new JTable(new DefaultTableModel(data, new String[]{"Rating", "Value", "Animal"})) {
                    @Override
                    public Class<?> getColumnClass(int column) {
                        if (column == 0) {
                            return Rating.class;
                        } else if (column == 1) {
                            return Double.class;
                        } else {
                            return Object.class;
                        }
                    }
                };
//                JTable table = new JTable(new DefaultTableModel(data, new String[]{"Name", "Artist", "Album"}));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setUI(new ITunesTableUI());
//                table.getColumnModel().getColumn(0).setPreferredWidth(150);
//                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getColumnModel().getColumn(0).setPreferredWidth(75);
                table.getColumnModel().getColumn(1).setPreferredWidth(50);

                TableUtils.makeSortable(table, createDummySortDelegate());

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                JFrame frame = new JFrame();
                frame.add(scrollPane, BorderLayout.CENTER);
//                frame.setSize(400, 265);
                frame.setSize(275, 125);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
