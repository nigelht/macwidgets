package com.explodingpixels.macwidgets;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class DIAppScrollBar {

    private static final String TEXT =
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Vivamus cursus, purus " +
                    "suscipit sagittis volutpat, est ipsum ullamcorper est, ac varius sem metus " +
                    "et lacus. Phasellus fringilla. Phasellus commodo orci id metus. Curabitur " +
                    "eros. Sed nulla. Sed odio lorem, lobortis nec, sollicitudin in, hendrerit " +
                    "vitae, metus. Phasellus molestie. Ut fermentum est a neque. Curabitur nec " +
                    "dolor non dolor pretium condimentum. Praesent vestibulum, leo sed hendrerit " +
                    "tristique, risus leo sagittis quam, ut pellentesque purus metus a felis. " +
                    "Vivamus egestas, ligula vel bibendum elementum, sem ante tincidunt dui, " +
                    "eget suscipit nulla urna nec lorem. Pellentesque non dolor ac odio " +
                    "ultricies ultricies. Aliquam pellentesque tortor et ante. Sed accumsan mi " +
                    "in mi. Phasellus turpis arcu, interdum congue, pulvinar ac, egestas id, " +
                    "tellus." +
                    "\n\n" +
                    "Sed faucibus lacinia nibh. Integer ut lorem eu velit lacinia ultricies. " +
                    "Phasellus vehicula tempor nibh. Duis gravida, sapien ut pellentesque " +
                    "sodales, leo purus venenatis quam, eu gravida lectus neque vitae felis. Ut " +
                    "odio. Duis consequat, ligula nec varius ultricies, ipsum diam consequat " +
                    "purus, non posuere diam ante at purus. Maecenas et libero. Donec sagittis " +
                    "nibh. Duis quis metus non purus ultrices tempus. Morbi consequat " +
                    "ullamcorper nunc. Aliquam orci lacus, sagittis sit amet, ultrices ut, " +
                    "feugiat eget, nunc. Morbi ante dui, bibendum vitae, convallis et, imperdiet " +
                    "id, pede.";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JTextArea textArea = new JTextArea(TEXT);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);

                JScrollPane scrollPane = IAppWidgetFactory.createScrollPaneWithButtonsTogether(textArea);

                JFrame frame = new JFrame();
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.add(MacWidgetFactory.createBottomBar(BottomBarSize.SMALL).getComponent(),
                        BorderLayout.SOUTH);
                frame.setSize(300, 300);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
