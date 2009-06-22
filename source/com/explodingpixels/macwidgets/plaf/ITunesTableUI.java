package com.explodingpixels.macwidgets.plaf;

import com.explodingpixels.data.Rating;
import com.explodingpixels.macwidgets.ITunesRatingTableCellRenderer;
import com.explodingpixels.macwidgets.ITunesTableHeaderRenderer;
import com.explodingpixels.macwidgets.MacFontUtils;
import com.explodingpixels.widgets.TableHeaderUtils;
import com.explodingpixels.widgets.TableUtils;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * A UI delegate that renders an iTunes style table.
 * <br/>
 * <img src="../../../../../graphics/iTunesTable.png">
 */
public class ITunesTableUI extends BasicTableUI {

    private static final Color EVEN_ROW_COLOR = new Color(241, 245, 250);
    private static final Color TABLE_GRID_COLOR = new Color(0xd9d9d9);
    private static final Color SELECTION_ACTIVE_SELECTION_FOREGROUND_COLOR = Color.WHITE;
    private static final Color SELECTION_ACTIVE_SELECTION_BACKGROUND_COLOR = new Color(0x3d80df);
    private static final Color SELECTION_INACTIVE_SELECTION_FOREGROUND_COLOR = Color.BLACK;
    private static final Color SELECTION_INACTIVE_SELECTION_BACKGROUND_COLOR = new Color(0xc0c0c0);
    private static final Color SELECTION_ACTIVE_BOTTOM_BORDER_COLOR = new Color(125, 170, 234);
    private static final Color SELECTION_INACTIVE_BOTTOM_BORDER_COLOR = new Color(224, 224, 224);

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        // TODO save defaults.

        table.setOpaque(false);
        table.setFont(MacFontUtils.ITUNES_FONT);
        table.setGridColor(TABLE_GRID_COLOR);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getTableHeader().setDefaultRenderer(new ITunesTableHeaderRenderer(table));

        table.setShowHorizontalLines(false);
        TableHeaderUtils.makeHeaderFillEmptySpace(table);
        TableUtils.makeStriped(table, EVEN_ROW_COLOR);

        table.setDefaultRenderer(Rating.class, new ITunesRatingTableCellRenderer());
        table.setDefaultRenderer(Object.class, createTransparentTableCellRenderer());

        table.setDefaultEditor(Object.class, createDefaultTableCellEditor());

        makeTableActive();
        WindowUtils.installWindowFocusListener(createWindowFocusListener(), table);
    }

    @Override
    protected void installListeners() {
        super.installListeners();
    }

    private void makeTableActive() {
        table.setSelectionForeground(SELECTION_ACTIVE_SELECTION_FOREGROUND_COLOR);
        table.setSelectionBackground(SELECTION_ACTIVE_SELECTION_BACKGROUND_COLOR);
    }

    private void makeTableInactive() {
        table.setSelectionForeground(SELECTION_INACTIVE_SELECTION_FOREGROUND_COLOR);
        table.setSelectionBackground(SELECTION_INACTIVE_SELECTION_BACKGROUND_COLOR);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    private TableCellRenderer createTransparentTableCellRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (component instanceof JComponent) {
                    JComponent jcomponent = (JComponent) component;
                    jcomponent.setOpaque(isSelected);
                    jcomponent.setBorder(table.isRowSelected(row)
                            ? getSelectedRowBorder() : getRowBorder());
                }
                return component;
            }
        };
    }

    private TableCellEditor createDefaultTableCellEditor() {
        JTextField textField = new JTextField();
        textField.setFont(MacFontUtils.ITUNES_FONT);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return new DefaultCellEditor(textField);
    }

    private WindowFocusListener createWindowFocusListener() {
        return new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
                makeTableActive();
            }

            public void windowLostFocus(WindowEvent e) {
                makeTableInactive();
            }
        };
    }

    private Border getRowBorder() {
        return BorderFactory.createEmptyBorder(0, 5, 0, 5);
    }

    private Border getSelectedRowBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, getSelectedRowBottomHighlight()),
                BorderFactory.createEmptyBorder(1, 5, 0, 5));
    }

    private Color getSelectedRowBottomHighlight() {
        return WindowUtils.isParentWindowFocused(table)
                ? SELECTION_ACTIVE_BOTTOM_BORDER_COLOR : SELECTION_INACTIVE_BOTTOM_BORDER_COLOR;
    }

}
