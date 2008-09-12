package com.explodingpixels.macwidgets.demos;

import com.explodingpixels.widgets.WindowUtils;
import com.explodingpixels.widgets.PopupMenuCustomizer;
import com.explodingpixels.macwidgets.SourceList;
import com.explodingpixels.macwidgets.SourceListControlBar;
import com.explodingpixels.macwidgets.MacIcons;
import com.explodingpixels.macwidgets.MacWidgetFactory;

import javax.swing.*;
import java.awt.BorderLayout;

public class DSourceListControlBar {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SourceList sourceList = DSourceListMail.createSourceList();
                SourceListControlBar controlBar = new SourceListControlBar();
                sourceList.installSourceListControlBar(controlBar);

                controlBar.createAndAddButton(MacIcons.PLUS, null);
                controlBar.createAndAddButton(MacIcons.MINUS, null);
                controlBar.createAndAddPopdownButton(MacIcons.GEAR,
                        new PopupMenuCustomizer() {
                            public void customizePopup(JPopupMenu popup) {
                                popup.removeAll();
                                popup.add(new JMenuItem("Item One"));
                                popup.add(new JMenuItem("Item Two"));
                                popup.add(new JMenuItem("Item Three"));
                            }
                        });

                JSplitPane splitPane =
                        MacWidgetFactory.createSplitPaneForSourceList(sourceList, new JTextArea());
                splitPane.setDividerLocation(200);

                controlBar.installDraggableWidgetOnSplitPane(splitPane);

                JFrame frame = new JFrame();
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
//                frame.add(splitPane, BorderLayout.CENTER);
                frame.add(sourceList.getComponent(), BorderLayout.CENTER);
//                frame.setSize(500,275);
                frame.setSize(225,275);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

            }
        });
    }


}
