package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.ITunesTableUI;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class DITunesTableUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                String[][] data = new String[][]{
                        {"Batman Begins", "2005"}, {"The Dark Knight", "2008"},
                        {"Austin Powers", "1997"}, {"The Last Samurai", "2003"},
                        {"Back to the Future", "1985"},
                        {"Batman Begins", "2005"}, {"The Dark Knight", "2008"},
                        {"Austin Powers", "1997"}, {"The Last Samurai", "2003"},
                        {"Back to the Future", "1985"},
                        {"Batman Begins", "2005"}, {"The Dark Knight", "2008"},
                        {"Austin Powers", "1997"}, {"The Last Samurai", "2003"},
                        {"Back to the Future", "1985"}
                };

                JTable table = new JTable(new DefaultTableModel(data, new String[]{"Movie Title", "Year Released"}));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setUI(new ITunesTableUI());
//                table.setEnabled(false);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                JFrame frame = new JFrame();
                frame.add(scrollPane, BorderLayout.CENTER);
//                frame.getRootPane().add(new JLabel("hello"));
//                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.setSize(300, 225);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
