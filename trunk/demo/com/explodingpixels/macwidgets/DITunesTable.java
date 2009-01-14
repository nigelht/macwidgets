package com.explodingpixels.macwidgets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DITunesTable {

    public static void main(String[] args) {

        String[] columnNames = new String[]{"foo", "bar"}; 
        String[][] data = new String[][]{{"A", "B"}};
        
        ITunesTable table = new ITunesTable(new DefaultTableModel(data, columnNames));
        table.getTableHeader().setFont(table.getFont().deriveFont(18.0f));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JFrame frame = new JFrame();

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
    