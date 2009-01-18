package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.ITunesTableUI;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class DITunesTableUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                String[][] data = new String[][]{
                        {"Batman Begins", "2005"}, {"The Dark Knight", "2008"},
                        {"Austin Powers", "1997"}, {"The Last Samurai", "2003"},
                        {"Back to the Future", "1985"}
                };

                // TODO add MacWidgetFactory method.
                JTable table = new JTable(data, new String[]{"Movie Title", "Year Released"}) {
//                    @Override
//                    public void doLayout() {
//                        super.doLayout();
//                    }
                };
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setUI(new ITunesTableUI());
                table.setShowGrid(false);

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
