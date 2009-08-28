package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.TabCloseListener;
import com.explodingpixels.widgets.plaf.EPTabbedPaneUI;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class DEPTabbedPaneUI {

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

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

//                IAppWidgetFactory.setIAppScrollBarButtonsSeparate(true);
//                JScrollPane scrollPane = IAppWidgetFactory.createScrollPane(textArea);

                JTabbedPane tabbedPane = new JTabbedPane();
//                tabbedPane.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 0));
                tabbedPane.setUI(new EPTabbedPaneUI());
                tabbedPane.addTab("Tab One", textArea);
                tabbedPane.addTab("Tab Two Really long tab title", new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        g.setColor(Color.RED);
                        GeneralPath path = new GeneralPath();
                        path.moveTo(0, 0);
                        path.quadTo(0f, 100f, 100f, 100f);
                        path.closePath();
                        ((Graphics2D) g).draw(path);
                    }
                });
                tabbedPane.setOpaque(false);

                TabCloseListener closeListener = new TabCloseListener() {
                    public boolean tabAboutToBeClosed(int tabIndex) {
                        return true;
                    }

                    public void tabClosed(String title, Component component) {
                    }
                };
                tabbedPane.putClientProperty(EPTabbedPaneUI.TAB_CLOSE_LISTENER_KEY, closeListener);
//                tabbedPane.putClientProperty(EPTabbedPaneUI.CLOSE_BUTTON_LOCATION_KEY, EPTabbedPaneUI.CLOSE_BUTTON_LOCATION_VALUE_RIGHT);

//                JButton remove = new JButton("Remove Scrollpane");
//                BottomBar bottomBar = new BottomBar(BottomBarSize.SMALL);
//                bottomBar.addComponentToLeft(remove);

//                TabbedComponent tabbedComponent = new TabbedComponent();
//                tabbedComponent.addTab("Tab One", textArea);
//                tabbedComponent.addTab("Tab Two", new JTextArea());

                UnifiedToolBar toolbar = new UnifiedToolBar();

                final JFrame frame = new JFrame();
                MacUtils.makeWindowLeopardStyle(frame.getRootPane());
//                frame.add(toolbar.getComponent(), BorderLayout.NORTH);
                frame.add(tabbedPane, BorderLayout.CENTER);
//                frame.add(tabbedComponent.getComponent(), BorderLayout.CENTER);
//                frame.add(bottomBar.getComponent(), BorderLayout.SOUTH);
                frame.setSize(300, 300);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
