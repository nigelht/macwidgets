package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.PopupMenuCustomizerUsingStrings;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DEverything {

    private UnifiedToolBar fUnifiedToolBar;

    private SourceList fSourceList;

    public DEverything(JFrame frame) {
        fUnifiedToolBar = createUnifiedToolBar();
        fUnifiedToolBar.installWindowDraggerOnWindow(frame);

        JSplitPane splitPane = createSourceListAndMainArea();

        frame.add(fUnifiedToolBar.getComponent(), BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
    }

    private UnifiedToolBar createUnifiedToolBar() {
        Icon blueGlobeIcon = new ImageIcon(DEverything.class.getResource(
                "/com/explodingpixels/macwidgets/icons/DotMac.png"));
        Icon greyGlobeIcon = new ImageIcon(DEverything.class.getResource(
                "/com/explodingpixels/macwidgets/icons/Network.png"));
        Icon preferences = new ImageIcon(DEverything.class.getResource(
                "/com/explodingpixels/macwidgets/icons/PreferencesGeneral.png"));
        Icon gear = new ImageIcon(DEverything.class.getResource(
                "/com/explodingpixels/macwidgets/icons/Advanced.png"));

        AbstractButton greyGlobeButton =
                MacButtonFactory.makeUnifiedToolBarButton(
                        new JButton("Network", greyGlobeIcon));
        greyGlobeButton.setEnabled(false);

        AbstractButton blueButton = new JButton("MobileMe", blueGlobeIcon);
        blueButton.setEnabled(true);

        UnifiedToolBar toolBar = new UnifiedToolBar();
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

        Image blueGlobeImage = new ImageIcon(DEverything.class.getResource(
                "/com/explodingpixels/macwidgets/icons/DotMac.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        Image greyGlobeImage = new ImageIcon(DEverything.class.getResource(
                "/com/explodingpixels/macwidgets/icons/Network.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

        Icon blueGlobeIcon = new ImageIcon(blueGlobeImage);
        Icon greyGlobeIcon = new ImageIcon(greyGlobeImage);

        SourceListCategory categoryOne = new SourceListCategory("Category 1");
        final SourceListCategory categoryTwo = new SourceListCategory("Category 2");
        SourceListCategory categoryThree = new SourceListCategory("Category 3");

        SourceListItem itemA = new SourceListItem("SourceListItem A", blueGlobeIcon);
        SourceListItem itemB = new SourceListItem("SourceListItem B", greyGlobeIcon);

        SourceListItem itemC = new SourceListItem("SourceListItem D", greyGlobeIcon);
        SourceListItem itemD = new SourceListItem("SourceListItem E", greyGlobeIcon);
        SourceListItem itemE = new SourceListItem("Really really really really really long SourceListItem", greyGlobeIcon);

        final SourceListItem itemG = new SourceListItem("SourceListItem G", greyGlobeIcon);
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

        fSourceList = new SourceList(model);
//        sourceList.setFocusable(false);

        SourceListControlBar controlBar = new SourceListControlBar();
        controlBar.createAndAddButton(MacIcons.PLUS, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.addItemToItem(new SourceListItem(("Hello")), itemG);
            }
        });
        controlBar.createAndAddButton(MacIcons.MINUS, null);
        controlBar.createAndAddPopdownButton(MacIcons.GEAR,
                new PopupMenuCustomizerUsingStrings(null, "Item One", "Item Two", "Item Three"));

        fSourceList.installSourceListControlBar(controlBar);

        JTextArea textArea = new JTextArea();

        JSplitPane splitPane = MacWidgetFactory.createSplitPaneForSourceList(fSourceList, textArea);

        // TODO make SourceList a better size by default
        splitPane.setDividerLocation(200);

        controlBar.installDraggableWidgetOnSplitPane(splitPane);

        return splitPane;
    }

    public UnifiedToolBar getUnifiedToolBar() {
        return fUnifiedToolBar;
    }

    public SourceList getSourceList() {
        return fSourceList;
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
