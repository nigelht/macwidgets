package com.explodingpixels.widgets;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TableHeaderUtils {

    private static final CellRendererPane CELL_RENDER_PANE = new CellRendererPane();

    private static final String PRESSED_COLUMN_KEY = "EPJTableHeader.pressedColumn";
    private static final String SELECTED_COLUMN_KEY = "EPJTableHeader.selectedColumn";
    private static final String SORT_DIRECTION_KEY = "EPJTableHeader.sortDirection";

    private TableHeaderUtils() {
        // no constructor - utility class.
    }

    // Support for making a component look like a given tables default table header ///////////////

    /**
     * Creates a component that paints the header background, which can be used, for example, in a
     * {@link javax.swing.JScrollPane} corner.
     */
    public static JComponent createCornerComponent(final JTable table) {
        return new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                paintHeader(g, table, 0, getWidth());
            }
        };
    }

    // Support for making a table header fill the empty space. ////////////////////////////////////

    public static void makeHeaderFillEmptySpace(JTable table) {
        table.getTableHeader().setBorder(createTableHeaderEmptyColumnPainter(table));
    }

    /**
     * Paints the given JTable's table default header background at given
     * x for the given width.
     */
    private static void paintHeader(Graphics g, JTable table, int x, int width) {
        TableCellRenderer renderer = table.getTableHeader().getDefaultRenderer();
        Component component = renderer.getTableCellRendererComponent(
                table, "", false, false, -1, table.getColumnCount());

        component.setBounds(0, 0, width, table.getTableHeader().getHeight());

        ((JComponent) component).setOpaque(false);
        CELL_RENDER_PANE.paintComponent(g, component, null, x, 0,
                width, table.getTableHeader().getHeight(), true);
    }

    private static Border createTableHeaderEmptyColumnPainter(final JTable table) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                // if this JTableHeader is parented in a JViewport, then paint the table header
                // background to the right of the last column if neccessary.
                JViewport viewport = (JViewport) table.getParent();
                if (viewport != null && table.getWidth() < viewport.getWidth()) {
                    int startX = table.getWidth();
                    int emptyColumnWidth = viewport.getWidth() - table.getWidth();
                    paintHeader(g, table, startX, emptyColumnWidth);
                }
            }
        };
    }

    // Support for making column headers selected and sorted. /////////////////////////////////////

    public static int getPressedColumn(JTableHeader tableHeader) {
        Object pressedColumnValue = tableHeader.getClientProperty(PRESSED_COLUMN_KEY);
        return pressedColumnValue != null && pressedColumnValue instanceof Integer
                ? ((Integer) pressedColumnValue) : -1;
    }

    public static boolean isColumnPressed(JTableHeader tableHeader, int columnModelIndex) {
        int pressedColumn = getPressedColumn(tableHeader);
        return pressedColumn >= 0 && pressedColumn == columnModelIndex;
    }

    public static void setPressedColumn(JTableHeader tableHeader, int columnModelIndex) {
        tableHeader.putClientProperty(PRESSED_COLUMN_KEY, columnModelIndex);
    }

    public static int getSelectedColumn(JTableHeader tableHeader) {
        Object selectedColumnValue = tableHeader.getClientProperty(SELECTED_COLUMN_KEY);
        return selectedColumnValue != null && selectedColumnValue instanceof Integer
                ? ((Integer) selectedColumnValue) : -1;
    }

    public static boolean isColumnSelected(JTableHeader tableHeader, int columnModelIndex) {
        int selectedColumn = getSelectedColumn(tableHeader);
        return selectedColumn >= 0 && selectedColumn == columnModelIndex;
    }

    public static TableUtils.SortDirection getSortDirection(JTableHeader tableHeader, int columnModelIndex) {
        Object sortDirection = tableHeader.getClientProperty(SORT_DIRECTION_KEY);
        boolean isColumnSelected = isColumnSelected(tableHeader, columnModelIndex);
        return sortDirection != null && sortDirection instanceof String && isColumnSelected
                ? TableUtils.SortDirection.find((String) sortDirection) : TableUtils.SortDirection.NONE;
    }

    public static TableUtils.SortDirection toggleSortDirection(JTableHeader tableHeader, int columnModelIndex) {
        Object oldSortDirection = tableHeader.getClientProperty(SORT_DIRECTION_KEY);
        TableUtils.SortDirection newSortDirection;
        if (oldSortDirection == null) {
            newSortDirection = TableUtils.SortDirection.ASCENDING;
        } else {
            newSortDirection =
                    TableUtils.SortDirection.find((String) oldSortDirection) == TableUtils.SortDirection.ASCENDING
                            ? TableUtils.SortDirection.DESCENDING : TableUtils.SortDirection.ASCENDING;
        }
        setSortDirection(tableHeader, columnModelIndex, newSortDirection);

        return newSortDirection;
    }

    private static void setSortDirection(JTableHeader tableHeader, int columnModelIndex,
                                         TableUtils.SortDirection sortDirection) {
        tableHeader.putClientProperty(SELECTED_COLUMN_KEY, columnModelIndex);
        tableHeader.putClientProperty(SORT_DIRECTION_KEY, sortDirection.getValue());
    }

    // Support for listening to header clicks. ////////////////////////////////////////////////////

    public interface ColumnHeaderClickListener {
        /**
         * Called when a column header in an associated {@link JTable} is pressed.
         *
         * @param columnModelIndex the model index of the column header that was pressed.
         */
        void columnHeaderPressed(int columnModelIndex);

        /**
         * Called when a column header in an associated {@link JTable} is clicked.
         *
         * @param columnModelIndex the model index of the column header that was clicked.
         */
        void columnHeaderClicked(int columnModelIndex);

        /**
         * Called when the column header that was pressed has been released.
         */
        void columnHeaderReleased();
    }

    public static MouseListener createColumnHeaderSortHandler(
            final JTable table, final TableUtils.SortDelegate sortDelegate) {
        return new MouseAdapter() {
            private boolean fMouseEventIsPerformingPopupTrigger = false;

            public void mouseClicked(MouseEvent mouseEvent) {
                if (shouldProcessMouseClicked()) {
                    final TableColumnModel columnModel = table.getColumnModel();
                    int columnViewIndex = columnModel.getColumnIndexAtX(mouseEvent.getX());
                    int columnModelIndex = table.convertColumnIndexToModel(columnViewIndex);
                    TableUtils.SortDirection sortDirection =
                            TableHeaderUtils.toggleSortDirection(table.getTableHeader(), columnModelIndex);
                    sortDelegate.sort(columnModelIndex, sortDirection);

                    table.getTableHeader().repaint();
                }
            }

            private boolean shouldProcessMouseClicked() {
                return !fMouseEventIsPerformingPopupTrigger
                        && table.getTableHeader().getCursor() != Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
            }

            public void mousePressed(MouseEvent mouseEvent) {
                fMouseEventIsPerformingPopupTrigger = mouseEvent.isPopupTrigger();

                if (table.getTableHeader().getCursor() != Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
                    final TableColumnModel columnModel = table.getColumnModel();
                    int viewColumnIndex = columnModel.getColumnIndexAtX(mouseEvent.getX());
                    int columnModelIndex = table.convertColumnIndexToModel(viewColumnIndex);
//                    listener.columnHeaderPressed(columnModelIndex);
                    TableHeaderUtils.setPressedColumn(table.getTableHeader(), columnModelIndex);

                    table.getTableHeader().repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                listener.columnHeaderReleased();
                TableHeaderUtils.setPressedColumn(table.getTableHeader(), -1);
                table.getTableHeader().repaint();
            }
        };
    }

}
