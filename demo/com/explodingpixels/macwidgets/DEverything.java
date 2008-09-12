package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.PopupMenuCustomizerUsingStrings;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

public class DEverything {

    public DEverything(JFrame frame) {
        TriAreaComponent toolBar = createUnifiedToolBar();
        toolBar.installWindowDraggerOnWindow(frame);

        JSplitPane splitPane = createSourceListAndMainArea();

        frame.add(toolBar.getComponent(), BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
    }

    private TriAreaComponent createUnifiedToolBar() {
        Icon blueGlobeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                        "NSImage://NSDotMac").getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        Icon greyGlobeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                        "NSImage://NSNetwork").getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        Icon preferences =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                        "NSImage://NSPreferencesGeneral").getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        Icon gear =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                        "NSImage://NSAdvanced").getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        AbstractButton greyGlobeButton =
                MacButtonFactory.makeUnifiedToolBarButton(
                        new JButton("Network", greyGlobeIcon));
        greyGlobeButton.setEnabled(false);

        AbstractButton blueButton = new JButton("MobileMe", blueGlobeIcon);
        blueButton.setEnabled(true);

        TriAreaComponent toolBar = MacWidgetFactory.createUnifiedToolBar();
        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(
                blueButton));
        toolBar.addComponentToLeft(greyGlobeButton);
        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Preferences", preferences)));
        toolBar.addComponentToRight(MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Advanced", gear)));

        return toolBar;
    }

    private JSplitPane createSourceListAndMainArea() {
        Icon blueGlobeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSDotMac").getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        Icon greyGlobeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSNetwork").getScaledInstance(16, 16, Image.SCALE_SMOOTH));

        SourceListCategory categoryOne = new SourceListCategory("Category 1");
        SourceListCategory categoryTwo = new SourceListCategory("Category 2");
        SourceListCategory categoryThree = new SourceListCategory("Category 3");

        SourceListItem itemA = new SourceListItem("SourceListItem A", blueGlobeIcon);
        SourceListItem itemB = new SourceListItem("SourceListItem B", greyGlobeIcon);

        SourceListItem itemC = new SourceListItem("SourceListItem D", greyGlobeIcon);
        SourceListItem itemD = new SourceListItem("SourceListItem E", greyGlobeIcon);
        SourceListItem itemE = new SourceListItem("Really really really really really long SourceListItem", greyGlobeIcon);

        SourceListItem itemG = new SourceListItem("SourceListItem G", greyGlobeIcon);
        SourceListItem itemH = new SourceListItem("SourceListItem H", greyGlobeIcon);
        SourceListItem itemI = new SourceListItem("SourceListItem I", greyGlobeIcon);
        SourceListItem itemJ = new SourceListItem("SourceListItem J", greyGlobeIcon);
        SourceListItem itemK = new SourceListItem("SourceListItem K", greyGlobeIcon);
        SourceListItem itemL = new SourceListItem("SourceListItem L", greyGlobeIcon);

        itemA.setCounterValue(1);
        itemC.setCounterValue(385);
        itemE.setCounterValue(3);
        itemI.setCounterValue(17);

        final SourceListModel model = new SourceListModel();
        model.addCategory(categoryOne);
        model.addItemToCategory(itemA, categoryOne);
        model.addItemToCategory(itemB, categoryOne);

        model.addCategory(categoryTwo);
        model.addItemToCategory(itemC, categoryTwo);
        model.addItemToCategory(itemD, categoryTwo);
        model.addItemToCategory(itemE, categoryTwo);

        model.addCategory(categoryThree);
        model.addItemToCategory(itemG, categoryThree);
        model.addItemToCategory(itemH, categoryThree);
        model.addItemToCategory(itemI, categoryThree);
        model.addItemToCategory(itemJ, categoryThree);
        model.addItemToCategory(itemK, categoryThree);
        model.addItemToCategory(itemL, categoryThree);

        SourceList sourceList = new SourceList(model);
//        sourceList.setFocusable(false);

        SourceListControlBar controlBar = new SourceListControlBar();
        controlBar.createAndAddButton(MacIcons.PLUS, null);
        controlBar.createAndAddButton(MacIcons.MINUS, null);
        controlBar.createAndAddPopdownButton(MacIcons.GEAR,
                new PopupMenuCustomizerUsingStrings(null, "Item One", "Item Two", "Item Three"));

        sourceList.installSourceListControlBar(controlBar);

        JTextArea textArea = new JTextArea();

        JSplitPane splitPane = MacWidgetFactory.createSplitPaneForSourceList(sourceList, textArea);

        // TODO make SourceList a better size by default
        splitPane.setDividerLocation(200);

        controlBar.installDraggableWidgetOnSplitPane(splitPane);

        return splitPane;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                MacUtils.makeWindowLeopardStyle(frame.getRootPane());
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                DEverything everything = new DEverything(frame);
                frame.setSize(650, 450);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }

}
