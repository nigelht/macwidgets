package com.explodingpixels.widgets.plaf;

import com.explodingpixels.painter.Painter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class EPTabbedPaneUI extends BasicTabbedPaneUI {

    private static final Color SELECTED_BORDER_COLOR = new Color(0x666666);
    private static final Color UNSELECTED_BORDER_COLOR = new Color(0x888888);
    private static final Color SELECTED_BACKGROUND_COLOR = Color.WHITE;
    private static final Color UNSELECTED_BACKGROUND_COLOR = new Color(0xcccccc);

    private static final int CORNER_ARC_DIAMETER = 6;

    private Painter<Component> fContentBorderTopEdgeBackgroundPainter = createContentBorderTopEdgeBackgroundPainter();
    private boolean fPaintFullContentBorder = true;

    private static final Insets FULL_CONTENT_BORDER_INSETS = new Insets(6, 0, 0, 0);
    private static final Insets HAIRLINE_BORDER_INSETS = new Insets(1, 0, 0, 0);

    private static final Insets DEFALT_TAB_INSETS = new Insets(2, 10, 2, 10);

    @Override
    protected void installDefaults() {
        super.installDefaults();

        Font oldFont = tabPane.getFont();
        tabPane.setFont(oldFont.deriveFont(oldFont.getSize() - 2.0f));
        tabPane.setBorder(BorderFactory.createEmptyBorder());

        tabInsets = DEFALT_TAB_INSETS;
        selectedTabPadInsets = new Insets(0, 1, 0, 1);
    }

    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return fPaintFullContentBorder ? FULL_CONTENT_BORDER_INSETS : HAIRLINE_BORDER_INSETS;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
        paintContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex());

    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height,
                                      boolean isSelected) {
//        super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);

        Graphics2D graphics = (Graphics2D) g;

        graphics.setColor(isSelected ? SELECTED_BACKGROUND_COLOR : UNSELECTED_BACKGROUND_COLOR);
        graphics.fillRoundRect(x, y, width - 1, height, CORNER_ARC_DIAMETER, CORNER_ARC_DIAMETER);

    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height, boolean isSelected) {
//        super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(isSelected ? SELECTED_BORDER_COLOR : UNSELECTED_BORDER_COLOR);
        graphics.drawRoundRect(x, y, width - 1, height + CORNER_ARC_DIAMETER / 2,
                CORNER_ARC_DIAMETER, CORNER_ARC_DIAMETER);
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
//        super.paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);
    }

    @Override
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int width, int height) {
//        super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        Graphics2D graphics = (Graphics2D) g;

        graphics.translate(x, y);
        int borderHeight = getContentBorderInsets(tabPane.getTabPlacement()).top;
        fContentBorderTopEdgeBackgroundPainter.paint(graphics, tabPane, width, borderHeight);
        graphics.translate(-x, -y);

//        graphics.setRenderingHint(
//                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setColor(Color.WHITE);
        Rectangle boundsOfSelectedTab = getTabBounds(tabPane, tabPane.getSelectedIndex());
        graphics.drawLine(boundsOfSelectedTab.x + 1, y, boundsOfSelectedTab.x + boundsOfSelectedTab.width - 2, y);
    }

    private Painter<Component> createContentBorderTopEdgeBackgroundPainter() {
        return new Painter<Component>() {
            public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {

                Paint paint = new GradientPaint(0, 0, Color.WHITE, 0, height - 1, new Color(0xf8f8f8));
                graphics.setPaint(paint);
                graphics.fillRect(0, 0, width, height - 1);
                graphics.setColor(SELECTED_BORDER_COLOR);
                graphics.drawLine(0, 0, width, 0);
                graphics.setColor(new Color(0x999999));
                // TODO figure out why we need to subtract off another extra pixel here -- doesn't make sense.
                graphics.drawLine(0, height - 2, width, height - 2);
            }
        };
    }

    private void paintContentBorderTopEdgeBackground(Graphics g, int tabPlacement, int x, int y, int width, int height) {

    }

    @Override
    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
//        super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    }

    @Override
    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
//        super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    }

    @Override
    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
//        super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    }

    @Override
    protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    @Override
    protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    public void setPaintsFullContentBorder(boolean paintsFullContentBorder) {
        fPaintFullContentBorder = paintsFullContentBorder;
        tabPane.repaint();
    }
}
