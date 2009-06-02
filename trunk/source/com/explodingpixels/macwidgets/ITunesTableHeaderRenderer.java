package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.EmphasizedLabelUI;
import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A table header renderer for an iTunes style table. Note that this class specifically extends
 * {@link JLabel} in order to be compatible with Glazed Lists. Glazed Lists looks for a label in the
 * header renderer in order to install the sort icon, if necessary.
 */
public class ITunesTableHeaderRenderer extends JLabel
        implements TableCellRenderer {

    private JTable fTable;

    private int fSelectedColumn = -1;

    private int fPressedColumn = -1;

    private boolean fIsColumnPressed = false;

    private boolean fIsColumnSelected = false;

    private static Color HIGHLIGHT_BORDER_COLOR = new Color(255, 255, 255, 77);

    private static Color BORDER_COLOR = new Color(0, 0, 0, 51);

    private static Color UNFOCUSED_FONT_COLOR = new Color(0x8f8f8f);

    private static Border BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, MacColorUtils.LEOPARD_BORDER_COLOR),
            BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR),
                    BorderFactory.createEmptyBorder(1, 5, 0, 5)));

    public ITunesTableHeaderRenderer(JTable table) {
        fTable = table;
        init();
    }

    private void init() {
        fTable.getTableHeader().addMouseListener(new HeaderClickHandler());
        MacWidgetFactory.makeEmphasizedLabel(this,
                EmphasizedLabelUI.DEFAULT_FOCUSED_FONT_COLOR, UNFOCUSED_FONT_COLOR,
                EmphasizedLabelUI.DEFAULT_EMPHASIS_COLOR);
        setBorder(BORDER);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // if the given value is an Icon, then use that. otherwise, use the string version of the
        // given value.
        if (value instanceof Icon) {
            setIcon((Icon) value);
            setText("");
            setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            setIcon(null);
            setText(value.toString());
            setFont(fTable.getTableHeader().getFont());
            setHorizontalAlignment(SwingConstants.LEFT);
        }

        // only change the selected and pressed flags if the given column index is within the
        // bounds of the column model. this renderer is robust to painting a column header not
        // within the column models bounds, which allows it to paint the header for to the right of
        // the right-most column if necessary.
        if (0 <= column && column < fTable.getColumnCount()) {
            int modelColumn = fTable.convertColumnIndexToModel(column);
            fIsColumnSelected = isColumnSelected(modelColumn);
            fIsColumnPressed = isColumnPressed(modelColumn);
        } else {
            fIsColumnSelected = false;
            fIsColumnPressed = false;
        }

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g.create();
        Painter<Component> painter = getBackgroundPainter();
        painter.paint(graphics2d, this, getWidth(), getHeight());

        super.paintComponent(g);

//        paintLeftHighlight(graphics2d);
//        paintRightHighlight(graphics2d);

        graphics2d.dispose();
    }

    protected void paintLeftHighlight(Graphics2D graphics2d) {
        graphics2d.setColor(HIGHLIGHT_BORDER_COLOR);
        graphics2d.drawLine(0, 0, 0, getHeight() - getInsets().bottom);
    }

    protected void paintRightHighlight(Graphics2D graphics2d) {
        graphics2d.setColor(BORDER_COLOR);
        graphics2d.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - getInsets().bottom);
//        graphics2d.setColor(HIGHLIGHT_BORDER_COLOR);
//        graphics2d.drawLine(getWidth(), 0, getWidth(), getHeight() - getInsets().bottom);
//        graphics2d.drawLine(0, 0, 0, getHeight() - getInsets().bottom);
    }

    public Painter<Component> getBackgroundPainter() {
        Painter<Component> retVal;
        boolean windowHasFocus = WindowUtils.isParentWindowFocused(fTable);
        // TODO cleanup this logic.
        if (!fTable.isEnabled()) {
            retVal = MacPainterFactory.createIAppUnpressedUnselectedHeaderPainter();
        } else if (windowHasFocus && fIsColumnPressed && fIsColumnSelected) {
            retVal = MacPainterFactory.createIAppPressedSelectedHeaderPainter();
        } else if (windowHasFocus && fIsColumnPressed) {
            retVal = MacPainterFactory.createIAppPressedUnselectedHeaderPainter();
        } else if (windowHasFocus && fIsColumnSelected) {
            retVal = MacPainterFactory.createIAppUnpressedSelectedHeaderPainter();
        } else {
            retVal = MacPainterFactory.createIAppUnpressedUnselectedHeaderPainter();
        }
        return retVal;
    }

    private boolean isColumnSelected(int column) {
        return column == fSelectedColumn;
    }

    private boolean isColumnPressed(int column) {
        return column == fPressedColumn;
    }

    private class HeaderClickHandler extends MouseAdapter {

        private boolean mouseEventIsPerformingPopupTrigger = false;

        public void mouseClicked(MouseEvent mouseEvent) {
            // if the MouseEvent is popping up a context menu, do not sort
            if (mouseEventIsPerformingPopupTrigger) return;

            // if the cursor indicates we're resizing columns, do not sort
            if (fTable.getTableHeader().getCursor() == Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
                return;
            }

            final TableColumnModel columnModel = fTable.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(mouseEvent.getX());
            fSelectedColumn = fTable.convertColumnIndexToModel(viewColumn);

            fTable.getTableHeader().repaint();
        }

        public void mousePressed(MouseEvent mouseEvent) {
            this.mouseEventIsPerformingPopupTrigger = mouseEvent.isPopupTrigger();

            if (fTable.getTableHeader().getCursor() != Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
                final TableColumnModel columnModel = fTable.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(mouseEvent.getX());
                fPressedColumn = fTable.convertColumnIndexToModel(viewColumn);

                fTable.getTableHeader().repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            fPressedColumn = -1;
            fTable.getTableHeader().repaint();
        }
    }


}
