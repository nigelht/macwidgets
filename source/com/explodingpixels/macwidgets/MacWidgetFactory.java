package com.explodingpixels.macwidgets;

import com.explodingpixels.border.FocusStateMatteBorder;
import com.explodingpixels.macwidgets.plaf.EmphasizedLabelUI;
import com.explodingpixels.painter.GradientPainter;
import com.explodingpixels.painter.TriStateFocusPainter;
import com.jgoodies.forms.factories.Borders;
import org.jdesktop.swingx.painter.Painter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Component;
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

    /**
     * Creates a Mac style Unified Tool Bar. For a full description of what a Unified Tool Bar is,
     * see the <a href="http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-BABIFCFJ">Toolbars</a>
     * section of Apple's Human Interface Guidelines.
     *
     * @return a {@link TriAreaComponent} configured as a Unified Tool Bar.
     */
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

    private static void installUnifiedToolBarBorder(final JComponent component) {

        FocusStateMatteBorder border = new FocusStateMatteBorder(0, 0, 1, 0,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR,
                component);

        component.setBorder(BorderFactory.createCompoundBorder(
                border, component.getBorder()));
    }

    public static ComponentBottomBar createComponentStatusBar() {
        return new ComponentBottomBar();
    }

    /**
     * Creates a Mac style Bottom Bar. For a full descrption of what a Bottom Bar is, see the
     * <a href="http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-SW6">Bottom Bars</a>
     * section of Apple's Human Interface Guidelines.
     *
     * @param size the size of the Bottom Bar.
     * @return a {@link TriAreaComponent} configured as a Bottom Bar.
     */
    public static TriAreaComponent createBottomBar(BottomBarSize size) {

        Painter<Component> focusedPainter =
                new GradientPainter(
                        MacColorUtils.OS_X_BOTTOM_BAR_ACTIVE_TOP_COLOR,
                        MacColorUtils.OS_X_BOTTOM_BAR_ACTIVE_BOTTOM_COLOR);
        Painter<Component> unfocusedPainter =
                new GradientPainter(
                        MacColorUtils.OS_X_BOTTOM_BAR_INACTIVE_TOP_COLOR,
                        MacColorUtils.OS_X_BOTTOM_BAR_INACTIVE_BOTTOM_COLOR);

        Painter<Component> painter = new TriStateFocusPainter(focusedPainter, focusedPainter,
                unfocusedPainter);

        final TriAreaComponent bottomBar = new TriAreaComponent(5);
        bottomBar.forceAreasToHaveTheSameWidth();
        bottomBar.setBackgroundPainter(painter);
        // calculate the height of the bottom bar. this includes adding two pixels to incorporate
        // the height of the line border.
        int height = size.getHeight() + 2;
        bottomBar.getComponent().setPreferredSize(new Dimension(-1, height));

        FocusStateMatteBorder outterBorder = new FocusStateMatteBorder(1, 0, 0, 0,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR,
                bottomBar.getComponent());
        Border innerBorder = BorderFactory.createMatteBorder(1, 0, 0, 0,
                MacColorUtils.OS_X_BOTTOM_BAR_BORDER_HIGHLIGHT_COLOR);
        Border lineBorders = BorderFactory.createCompoundBorder(outterBorder, innerBorder);

        int padding = size == BottomBarSize.SMALL ? 5 : 10;
        padding = 5;
        bottomBar.getComponent().setBorder(
                BorderFactory.createCompoundBorder(lineBorders,
                        BorderFactory.createEmptyBorder(0, padding, 0, padding)));

        return bottomBar;
    }

    public static LabeledComponentGroup createLabledComponentGroup(
            String labelString, JComponent compoentGroup) {
        return new LabeledComponentGroup(labelString, compoentGroup);
    }

    public static LabeledComponentGroup createLabledComponentGroup(
            String labelString, JComponent... components) {
        JPanel panel = new JPanel(new FlowLayout(0, 0, FlowLayout.CENTER));
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

    public static JLabel createEmphasizedLabel(String text) {
        return makeEmphasizedLabel(new JLabel(text));
    }

    public static JLabel makeEmphasizedLabel(JLabel label) {
        label.setUI(new EmphasizedLabelUI());
        return label;
    }

    public static JLabel makeEmphasizedLabel(JLabel label, Color focusedColor, Color unfocusedColor,
                                             Color emphasisColor) {
        label.setUI(new EmphasizedLabelUI(focusedColor, unfocusedColor, emphasisColor));
        return label;
    }

    public static JSplitPane createSplitPaneForSourceList(SourceList sourceList, JComponent component) {
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, sourceList.getComponent(), component);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(1);
        ((BasicSplitPaneUI) splitPane.getUI()).getDivider().setBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0xa5a5a5)));
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        return splitPane;
    }

    // TODO do something with this method.
    private JComponent createContainerForComponents(JComponent... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setOpaque(false);
        for (JComponent component : components) {
            panel.add(component);
        }
        return panel;
    }

}
