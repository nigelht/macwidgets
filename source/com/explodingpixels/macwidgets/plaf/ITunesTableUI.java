package com.explodingpixels.macwidgets.plaf;

import com.explodingpixels.macwidgets.MacFontUtils;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ViewportLayout;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.plaf.basic.BasicViewportUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ITunesTableUI extends BasicTableUI {

    private static final Color EVEN_ROW_COLOR = new Color(241, 245, 250);
    private static final Color ITUNES_TABLE_GRID_COLOR = new Color(0xd9d9d9);

    private PropertyChangeListener fAncestorPropertyChangeListener =
            createAncestorPropertyChangeListener();

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        // TODO save defaults.

        table.setOpaque(false);
        table.setFont(MacFontUtils.ITUNES_FONT);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(false);
        table.setShowGrid(false);
//        table.setGridColor(ITUNES_TABLE_GRID_COLOR);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.addPropertyChangeListener("ancestor", fAncestorPropertyChangeListener);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    private PropertyChangeListener createAncestorPropertyChangeListener() {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // indicate that the parent of the JTable has changed.
                parentDidChange();
            }
        };
    }

    private void parentDidChange() {
        // if the parent of the table is an instance of JViewport, and that JViewport's parent is
        // a JScrollpane, then install the custom BugFixedViewportLayout.
        if (table.getParent() instanceof JViewport
                && table.getParent().getParent() instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
//            scrollPane.setUI(new CustomScrollPaneUI());
            scrollPane.getViewport().setLayout(new BugFixedViewportLayout());
            scrollPane.getViewport().setUI(new CustomViewportUI());
            scrollPane.getViewport().setOpaque(false);
        }
    }

    //    @Override
    public void paint(Graphics g, JComponent c) {
//        // get the row index at the top of the clip bounds (the first row to paint).
//        int rowAtPoint = table.rowAtPoint(g.getClipBounds().getLocation());
//        // get the y coordinate of the first row to paint. if there are no rows in the table, start
//        // painting at the top of the supplied clipping bounds.
//        int topY = rowAtPoint < 0 ? g.getClipBounds().y : table.getCellRect(rowAtPoint,0,true).y;
//
//        // create a counter variable to hold the current row. if there are no rows in the table,
//        // start the counter at 0.
//        int currentRow = rowAtPoint < 0 ? 0 : rowAtPoint;
//        while (topY < g.getClipBounds().y + g.getClipBounds().height) {
//            int bottomY = topY + table.getRowHeight();
//            g.setColor(getRowColor(currentRow));
//            g.fillRect(g.getClipBounds().x, topY, g.getClipBounds().width, bottomY);
//            topY = bottomY;
//            currentRow ++;
//        }
//
//        super.paint(g, c);
    }

    // Utility methods. ///////////////////////////////////////////////////////////////////////////

    private Color getRowColor(int row) {
        return row % 2 == 0 ? EVEN_ROW_COLOR : table.getBackground();
    }

    // BugFixedViewportLayout implementation. /////////////////////////////////////////////////////

    /**
     * <p>
     * A modified ViewportLayout to fix the JFC bug where components that implement Scrollable do
     * not resize correctly, if their size is less than the viewport size.
     * </p>
     * <p>
     * This is a JDK1.2.2 bug (id 4310721). This used to work in Swing 1.0.3 and the fix is putting
     * the old logic back.
     * </p>
     * Copied from: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4310721
     */
    private class BugFixedViewportLayout extends ViewportLayout {
        public void layoutContainer(Container parent) {
            // note that the original code (at the link supplied in the comment above) contained the
            // following call to super.layoutContainer(parent). this call caused the table to
            // continuously paint itself when the table did not fill the view. thus, i've commented
            // it out for now, as doing so seems to have no ill effects.
            // super.layoutContainer(parent);

            JViewport vp = (JViewport) parent;
            Component view = vp.getView();

            if (view == null) {
                return;
            }

            Point viewPosition = vp.getViewPosition();
            Dimension viewPrefSize = view.getPreferredSize();
            Dimension vpSize = vp.getSize();
            Dimension viewSize = new Dimension(viewPrefSize);

            if ((viewPosition.x == 0) && (vpSize.width > viewPrefSize.width)) {
                viewSize.width = vpSize.width;
            }

            if ((viewPosition.y == 0) && (vpSize.height > viewPrefSize.height)) {
                viewSize.height = vpSize.height;
            }

            if (!viewSize.equals(viewPrefSize)) {
                vp.setViewSize(viewSize);
            }
        }
    }

    private class CustomViewportUI extends BasicViewportUI {

        @Override
        public void paint(Graphics g, JComponent c) {
            // get the row index at the top of the clip bounds (the first row to paint).
            int rowAtPoint = table.rowAtPoint(g.getClipBounds().getLocation());
            // get the y coordinate of the first row to paint. if there are no rows in the table, start
            // painting at the top of the supplied clipping bounds.
            int topY = rowAtPoint < 0 ? g.getClipBounds().y : table.getCellRect(rowAtPoint, 0, true).y;

            // create a counter variable to hold the current row. if there are no rows in the table,
            // start the counter at 0.
            int currentRow = rowAtPoint < 0 ? 0 : rowAtPoint;
            while (topY < g.getClipBounds().y + g.getClipBounds().height) {
                int bottomY = topY + table.getRowHeight();
                g.setColor(getRowColor(currentRow));
                g.fillRect(g.getClipBounds().x, topY, g.getClipBounds().width, bottomY);
                topY = bottomY;
                currentRow++;
            }
        }
    }

}
