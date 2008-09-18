package com.explodingpixels.macwidgets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class TMacPainterFactory {

    private JScrollPane fScrollPane;

    public TMacPainterFactory() {

//        JXLabel leopardFocusedSelectedPainter = new JXLabel("");
//        leopardFocusedSelectedPainter.setBackgroundPainter(
//                MacPainterFactory.createSourceListSelectionPainter_componentFocused());
//
//        JXLabel iAppUnfocusedSelectedPainter = new JXLabel("");
//        iAppUnfocusedSelectedPainter.setBackgroundPainter(
//                MacPainterFactory.createSourceListSelectionPainter_windowFocused());
//
//        JXLabel leopardUnfocusedSelectedPainter = new JXLabel("");
//        leopardUnfocusedSelectedPainter.setBackgroundPainter(
//                MacPainterFactory.createSourceListSelectionPainter_windowUnfocused());
//
//        JXLabel iAppHeaderPainter = new JXLabel("");
//        iAppHeaderPainter.setBackgroundPainter(
//                MacPainterFactory.createIAppUnpressedUnselectedHeaderPainter());
//
//        JXLabel iAppPressedHeader = new JXLabel("");
//        iAppPressedHeader.setBackgroundPainter(
//                MacPainterFactory.createIAppPressedUnselectedHeaderPainter());
//
//        // define the FormLayout columns and rows.
//        FormLayout layout = new FormLayout(
//                "3dlu, 3dlu, 100dlu:grow, 3dlu ",
//                "3dlu, p, 3dlu, fill:12dlu, 3dlu, fill:12dlu, 3dlu, fill:12dlu, 3dlu, " +
//                        "p, 3dlu, fill:12dlu, 3dlu, fill:12dlu, 3dlu");
//        // create the cell constraints to use in the layout.
//        CellConstraints cc = new CellConstraints();
//        // create the builder with our panel as the component to be filled.
//        PanelBuilder builder = new PanelBuilder(layout);
//
//        builder.addLabel("Selection Renderers", cc.xywh(2, 2, 2, 1));
//        builder.add(leopardFocusedSelectedPainter, cc.xy(3, 4));
//        builder.add(iAppUnfocusedSelectedPainter, cc.xy(3, 6));
//        builder.add(leopardUnfocusedSelectedPainter, cc.xy(3, 8));
//        builder.addLabel("Header Renderers", cc.xywh(2, 10, 2, 1));
//        builder.add(iAppHeaderPainter, cc.xy(3, 12));
//        builder.add(iAppPressedHeader, cc.xy(3, 14));
//
//        fScrollPane = new JScrollPane(builder.getPanel());
//        fScrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    public JComponent getComponent() {
        return fScrollPane;
    }

    public static void main(String[] args) {
        TMacPainterFactory factory = new TMacPainterFactory();

        JFrame fFrame = new JFrame();
        fFrame.add(factory.getComponent(), BorderLayout.CENTER);
        fFrame.pack();
        fFrame.setLocationRelativeTo(null);
        fFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fFrame.setVisible(true);
    }

}
