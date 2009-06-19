package com.explodingpixels.widgets;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TableUtils {

    private static final CellRendererPane CELL_RENDER_PANE = new CellRendererPane();

    private static final String SELECTED_COLUMN_KEY = "EPJTableHeader.selectedColumn";
    private static final String SORT_DIRECTION_KEY = "EPJTableHeader.sortDirection";

    private TableUtils() {
        // no constructor - utility class.
    }

    /**
     * Add's striping to the background of the given {@link JTable}. The actual striping is
     * installed on the containing {@link JScrollPane}'s {@link JViewport}, so if this table is not
     * added to a {@code JScrollPane}, then no stripes will be painted. This method can be called
     * before the given table is added to a scroll pane, though, as a {@link PropertyChangeListener}
     * will be installed to handle "ancestor" changes.
     *
     * @param table      the table to paint row stripes for.
     * @param stipeColor the color of the stripes to paint.
     */
    public static void makeStriped(JTable table, Color stipeColor) {
        table.addPropertyChangeListener("ancestor",
                createAncestorPropertyChangeListener(table, stipeColor));
        // install a listener to cause the whole table to repaint when a column is resized. we do
        // this because the extended grid lines may need to be repainted. this could be cleaned up,
        // but for now, it works fine.
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).addPropertyChangeListener(
                    createAncestorPropertyChangeListener(table, stipeColor)
            );
        }
    }

    private static PropertyChangeListener createAncestorPropertyChangeListener(
            final JTable table, final Color stipeColor) {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                // indicate that the parent of the JTable has changed.
                parentDidChange(table, stipeColor);
            }
        };
    }

    private static void parentDidChange(JTable table, Color stipeColor) {
        // if the parent of the table is an instance of JViewport, and that JViewport's parent is
        // a JScrollpane, then install the custom BugFixedViewportLayout.
        if (table.getParent() instanceof JViewport
                && table.getParent().getParent() instanceof JScrollPane) {

            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
            scrollPane.setViewportBorder(
                    new StripedViewportBorder(scrollPane.getViewport(), table, stipeColor));
            scrollPane.getViewport().setOpaque(false);

            scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, createCornerComponent(table));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    // Support for making a component look like a given tables default table header ///////////////

    /**
     * Creates a component that paints the header background, which can be used, for example, in a
     * {@link JScrollPane} corner.
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

    public static int getSelectedColumn(JTableHeader tableHeader) {
        Object selectedColumnValue = tableHeader.getClientProperty(SELECTED_COLUMN_KEY);
        return selectedColumnValue != null && selectedColumnValue instanceof Integer
                ? ((Integer) selectedColumnValue) : -1;
    }

    public static boolean isColumnSelected(JTableHeader tableHeader, int columnModelIndex) {
        int selectedColumn = getSelectedColumn(tableHeader);
        return selectedColumn >= 0 && selectedColumn == columnModelIndex;
    }

    public static SortDirection getSortDirection(JTableHeader tableHeader, int columnModelIndex) {
        Object sortDirection = tableHeader.getClientProperty(SORT_DIRECTION_KEY);
        boolean isColumnSelected = isColumnSelected(tableHeader, columnModelIndex);
        return sortDirection != null && sortDirection instanceof String && isColumnSelected
                ? SortDirection.find((String) sortDirection) : SortDirection.NONE;
    }

    public static void toggleSortDirection(JTableHeader tableHeader, int columnModelIndex) {
        Object oldSortDirection = tableHeader.getClientProperty(SORT_DIRECTION_KEY);
        SortDirection newSortDirection;
        if (oldSortDirection == null) {
            newSortDirection = SortDirection.ASCENDING;
        } else {
            newSortDirection =
                    SortDirection.find((String) oldSortDirection) == SortDirection.ASCENDING
                            ? SortDirection.DESCENDING : SortDirection.ASCENDING;
        }
        setSortDirection(tableHeader, columnModelIndex, newSortDirection);
    }

    private static void setSortDirection(JTableHeader tableHeader, int columnModelIndex,
                                         SortDirection sortDirection) {
        tableHeader.putClientProperty(SELECTED_COLUMN_KEY, columnModelIndex);
        tableHeader.putClientProperty(SORT_DIRECTION_KEY, sortDirection.getValue());
    }

    public enum SortDirection {
        NONE(""), ASCENDING("ascending"), DESCENDING("descending");

        private final String fValue;

        SortDirection(String value) {
            fValue = value;
        }

        private String getValue() {
            return fValue;
        }

        private static SortDirection find(String value) {
            for (SortDirection sortDirection : values()) {
                if (sortDirection.getValue().equals(value)) {
                    return sortDirection;
                }
            }
            throw new IllegalArgumentException("No sort direction found for " + value);
        }
    }
}
