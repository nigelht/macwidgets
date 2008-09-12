package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class TITunesTable {

    public static void main(String[] args) {

        String[][] data =
                new String[][]{{"a", "b", "c", "d", "e"},{"a", "b", "c", "d", "e"}};
        String[] columnNames = new String[]{"One", "Two", "Three", "Four", "Five"};
        TableModel model = new DefaultTableModel(data, columnNames);

        JTable iTunesTable = MacWidgetFactory.createITunesTable(model);
        JScrollPane scrollPane = MacWidgetFactory.wrapITunesTableInJScrollPane(iTunesTable);

        JFrame frame = new JFrame();
        WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
