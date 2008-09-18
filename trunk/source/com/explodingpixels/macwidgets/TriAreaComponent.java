package com.explodingpixels.macwidgets;

import com.explodingpixels.painter.Painter;
import com.explodingpixels.swingx.EPPanel;
import com.explodingpixels.widgets.WindowDragger;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Window;

/**
 * A component that has three areas in which it widgets can be added. This is the component behind
 * the Unfied Tool Bar and the Bottom Bar. Here's what a {@code TriAreaComponent} Unified Tool Bar
 * looks like with widgets added to each of the three areas:
 * <br>
 * <img src="../../../resources/UnifiedToolBar.png">
 * <br>
 * You cannot directly create a {@code TriAreaComponent} and should instead use the factory
 * methods provided in {@link MacWidgetFactory}.
 *
 * @see MacWidgetFactory#createBottomBar(BottomBarSize)
 * @see MacWidgetFactory#createUnifiedToolBar()
 */
public class TriAreaComponent {

    private PanelBuilder fLeftPanelBuilder = new PanelBuilder(
            new FormLayout("", "fill:p:grow"), new JPanel());

    private PanelBuilder fCenterPanelBuilder = new PanelBuilder(
            new FormLayout("", "fill:p:grow"), new JPanel());

    private PanelBuilder fRightPanelBuilder = new PanelBuilder(
            new FormLayout("", "fill:p:grow"), new JPanel());

    private final EPPanel fPanel = new EPPanel();

    private int fSpacer_pixels;

    TriAreaComponent() {
        this(0);
    }

    TriAreaComponent(int spacer_pixels) {
        fSpacer_pixels = spacer_pixels;

        // definte the FormLayout columns and rows.
        FormLayout layout = new FormLayout(
                "left:p:grow, center:p:grow, right:p:grow",
                "fill:p:grow");
        // TODO decide whether to offer the option to force left, center and
        // TODO right groups to be the same size
//        layout.setColumnGroups(new int[][]{{1,2,3}});
        // create the cell constraints to use in the layout.
        CellConstraints cc = new CellConstraints();
        // create the builder with our panel as the component to be filled.
        PanelBuilder builder = new PanelBuilder(layout, fPanel);

        builder.add(fLeftPanelBuilder.getPanel(), cc.xy(1, 1));
        builder.add(fCenterPanelBuilder.getPanel(), cc.xy(2, 1));
        builder.add(fRightPanelBuilder.getPanel(), cc.xy(3, 1));

        fLeftPanelBuilder.getPanel().setOpaque(false);
        fCenterPanelBuilder.getPanel().setOpaque(false);
        fRightPanelBuilder.getPanel().setOpaque(false);

//        fLeftPanelBuilder.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED));
//        fCenterPanelBuilder.getPanel().setBorder(BorderFactory.createLineBorder(Color.BLUE));
//        fRightPanelBuilder.getPanel().setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    void forceAreasToHaveTheSameWidth() {
        ((FormLayout) fPanel.getLayout()).setColumnGroups(new int[][]{{1, 2, 3}});
    }

    public JComponent getComponent() {
        return fPanel;
    }

    public void installWindowDraggerOnWindow(Window window) {
        new WindowDragger(window, getComponent());
    }

    public void addComponentToLeft(JComponent toolToAdd) {
        addComponentToLeft(toolToAdd, fSpacer_pixels);
    }

    public void addComponentToLeft(JComponent toolToAdd, int spacer_pixels) {
        fLeftPanelBuilder.appendColumn("p");
        fLeftPanelBuilder.add(toolToAdd);
        fLeftPanelBuilder.nextColumn();
        fLeftPanelBuilder.appendColumn("p");
        fLeftPanelBuilder.add(MacWidgetFactory.createSpacer(spacer_pixels, 0));
        fLeftPanelBuilder.nextColumn();
    }

    public void addComponentToCenter(JComponent toolToAdd) {
//        fCenterPanelBuilder.appendColumn("p");
//        fCenterPanelBuilder.add(toolToAdd);
//        fCenterPanelBuilder.nextColumn();
        // TODO add spacer
        addComponentToCenter(toolToAdd, fSpacer_pixels);
    }

    public void addComponentToCenter(JComponent toolToAdd, int spacer_pixels) {
        if (getCenterComponentCount() > 0) {
            fCenterPanelBuilder.appendColumn("p");
            fCenterPanelBuilder.add(MacWidgetFactory.createSpacer(spacer_pixels, 0));
            fCenterPanelBuilder.nextColumn();
        }

        fCenterPanelBuilder.appendColumn("p");
        fCenterPanelBuilder.add(toolToAdd);
        fCenterPanelBuilder.nextColumn();
    }


    public void addComponentToRight(JComponent toolToAdd) {
        addComponentToRight(toolToAdd, fSpacer_pixels);
    }

    public void addComponentToRight(JComponent toolToAdd, int spacer_pixels) {
        fRightPanelBuilder.appendColumn("p");
        fRightPanelBuilder.add(toolToAdd);
        fRightPanelBuilder.nextColumn();

        if (getCenterComponentCount() > 0) {
            fRightPanelBuilder.appendColumn("p");
            fRightPanelBuilder.add(MacWidgetFactory.createSpacer(spacer_pixels, 0));
            fRightPanelBuilder.nextColumn();
        }
    }

    void setBackgroundPainter(Painter<Component> backgroundPainter) {
        fPanel.setBackgroundPainter(backgroundPainter);
    }

    protected final int getLeftComponentCount() {
        return fLeftPanelBuilder.getPanel().getComponentCount();
    }

    protected final int getCenterComponentCount() {
        return fCenterPanelBuilder.getPanel().getComponentCount();
    }

    protected final int getRightComponentCount() {
        return fRightPanelBuilder.getPanel().getComponentCount();
    }
}
