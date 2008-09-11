package com.explodingpixels.widgets;

import javax.swing.JTable;
import java.awt.Rectangle;

public class TableUtils {

    private TableUtils() {
        // no constructor - utility class.
    }

    /**
     * Repaints the selection. This allows the row selection to have a
     * background color that changes based on the focus state of the component.
     * @param table the {@code JTable} to repaint the selection of.
     */
    public static void repaintSelection(JTable table) {
        int[] selectedRows = table.getSelectedRows();

        // if there is at least one column and one selected row, then repaint
        // the selected area.
        if (table.getColumnCount() > 0
                && selectedRows != null && selectedRows.length > 0) {
            // grab the bounds of the first and last selected cells in column
            // one (index zero).
            Rectangle firstSelectedCell = table.getCellRect(selectedRows[0],0,true);
            Rectangle lastSelectedCell =
                    table.getCellRect(selectedRows[selectedRows.length-1],0,true);

            // create the rectangle to repaint by unioning the first and last
            // selected cells in column one and then extending that rectangle
            // to extend from the left edge of the table all the way to the
            // right edge.
            Rectangle repaintRectangle = firstSelectedCell.union(lastSelectedCell);
            repaintRectangle.x = 0;
            repaintRectangle.width = table.getWidth();

            // repaint the selection area.
            table.repaint(repaintRectangle);
        }

    }
}
