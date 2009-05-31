package com.explodingpixels.widgets;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.border.AbstractBorder;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Creates a border for a {@link JViewport} that draws a striped background corresponding to the row
 * positions of the given {@link JTable}.
 */
class StripedViewportBorder extends AbstractBorder {

    private final JViewport fViewport;
    private final JTable fTable;
    private final Color fStripeColor;

    StripedViewportBorder(JViewport viewport, JTable table, Color stripeColor) {
        fViewport = viewport;
        fTable = table;
        fStripeColor = stripeColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        paintStripedBackground(g);
        paintVerticalGridLines(g, y, height);
    }

    private void paintStripedBackground(Graphics g) {
        // get the row index at the top of the clip bounds (the first row
        // to paint).
        Rectangle clip = g.getClipBounds();
        Point viewPosition = fViewport.getViewPosition();
        // TODO figure out how to honor the beginning of clip region.
        // Point viewPostionWithClip = new Point(viewPosition.x + clip.x, viewPosition.y + clip.y);
        int rowAtPoint = fTable.rowAtPoint(viewPosition);
        // get the y coordinate of the first row to paint. if there are no
        // rows in the table, start painting at the top of the supplied
        // clipping bounds.
        int topY = rowAtPoint < 0
                ? clip.y
                : fTable.getCellRect(rowAtPoint, 0, true).y - viewPosition.y;

        // create a counter variable to hold the current row. if there are no
        // rows in the table, start the counter at 0.
        int currentRow = rowAtPoint < 0 ? 0 : rowAtPoint;
        while (topY < clip.y + clip.height) {
            int bottomY = topY + fTable.getRowHeight();
            g.setColor(getRowColor(currentRow));
            g.fillRect(clip.x, topY, clip.width, bottomY);
            topY = bottomY;
            currentRow++;
        }
    }

    private Color getRowColor(int row) {
        return row % 2 == 0 ? fStripeColor : fTable.getBackground();
    }

    private void paintVerticalGridLines(Graphics g, int y, int height) {
        // paint the column grid dividers for the non-existent rows.
        int x = 0 - fViewport.getViewPosition().x;
        for (int i = 0; i < fTable.getColumnCount(); i++) {
            TableColumn column = fTable.getColumnModel().getColumn(i);
            // increase the x position by the width of the current column.
            x += column.getWidth();
            g.setColor(fTable.getGridColor());
            // draw the grid line (not sure what the -1 is for, but BasicTableUI
            // also does it.source
            g.drawLine(x - 1, y, x - 1, y + height);
        }
    }
}
