package com.explodingpixels.macwidgets;

import com.explodingpixels.border.FocusStateMatteBorder;
import com.explodingpixels.macwidgets.plaf.EmphasizedLabelUI;
import com.jgoodies.forms.factories.Borders;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class MacWidgetFactory {

    public static JTable createITunesTable(TableModel tableModel) {
        return new ITunesTable(tableModel);
    }

    public static JScrollPane wrapITunesTableInJScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // TODO install custom ScrollBarUIs here.

        return scrollPane;
    }

    public static TriAreaComponent createUnifiedToolBar() {
        final TriAreaComponent unifiedToolBar = new TriAreaComponent(10);
        unifiedToolBar.getComponent().setBorder(Borders.createEmptyBorder("3dlu, 4dlu, 3dlu, 4dlu"));
        installUnifiedToolBarBorder(unifiedToolBar.getComponent());
        return unifiedToolBar;
    }

    public static PreferencesTabBar createUnifiedPreferencesTabBar() {
        final PreferencesTabBar tabBar = new PreferencesTabBar();
        installUnifiedToolBarBorder(tabBar.getComponent());
        return tabBar;
    }

    public static void installUnifiedToolBarBorder(final JComponent component) {

        FocusStateMatteBorder border = new FocusStateMatteBorder(0,0,1,0,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR,
                component);

        component.setBorder(BorderFactory.createCompoundBorder(
                border, component.getBorder()));
    }

    public static ComponentBottomBar createComponentStatusBar() {
        return new ComponentBottomBar();
    }

    public static BottomBar createBottomBar() {
        return new BottomBar();
    }

    public static LabeledComponentGroup createLabledComponentGroup(
            String labelString, JComponent compoentGroup) {
        return new LabeledComponentGroup(labelString, compoentGroup);
    }

    public static LabeledComponentGroup createLabledComponentGroup(
            String labelString, JComponent ... components) {
        JPanel panel = new JPanel(new FlowLayout(0,0,FlowLayout.CENTER));
        for (JComponent component : components) {
            panel.add(component);
        }
        return createLabledComponentGroup(labelString, panel);
    }

    public static JComponent createSpacer(int width, int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    public static JLabel makeEmphasizedLabel(JLabel label) {
        label.setUI(new EmphasizedLabelUI());
        return label;
    }

    public static JLabel makeEmphasizedLabel(JLabel label,
                                             Color focusedColor,
                                             Color unfocusedColor,
                                             Color emphasisColor) {
        label.setUI(new EmphasizedLabelUI(focusedColor, unfocusedColor, emphasisColor));
        return label;
    }

    public static JSplitPane createSplitPaneForSourceList(SourceList sourceList, JComponent component) {
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, sourceList.getComponent(), component);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(1);
        ((BasicSplitPaneUI)splitPane.getUI()).getDivider().setBorder(
                BorderFactory.createMatteBorder(0,1,0,0, new Color(0xa5a5a5)));
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        return splitPane;
    }

    // TODO do something with this method.
    private JComponent createContainerForComponents(JComponent ... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panel.setOpaque(false);
        for (JComponent component : components) {
            panel.add(component);
        }
        return panel;
    }

}
