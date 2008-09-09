package com.explodingpixels.macwidgets;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.explodingpixels.widgets.WindowDragger;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Window;
import java.awt.Color;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.Painter;

/**
 * A component that has three areas in which it can add widgets.
 */
public class TriAreaComponent {

    private PanelBuilder fLeftPanelBuilder = new PanelBuilder(
            new FormLayout("", "fill:p:grow"), new JPanel());

    private PanelBuilder fCenterPanelBuilder = new PanelBuilder(
            new FormLayout("", "fill:p:grow"), new JPanel());

    private PanelBuilder fRightPanelBuilder = new PanelBuilder(
            new FormLayout("", "fill:p:grow"), new JPanel());

    private final JXPanel fPanel;

    private int fSpacer_pixels;

    TriAreaComponent() {
        this(0);
    }

    TriAreaComponent(int spacer_pixels) {
        this(new JXPanel());
        fSpacer_pixels = spacer_pixels;
    }

    TriAreaComponent(JXPanel panel) {

        fPanel = panel;

        // definte the FormLayout columns and rows.
        FormLayout layout = new FormLayout(
                "left:p:grow center:p:grow right:p:grow",
                "fill:p:grow");
        // TODO decide whether to offer the option to force left, center and
        // TODO right groups to be the same size
//        layout.setColumnGroups(new int[][]{{1,2,3}});
        // create the cell constraints to use in the layout.
        CellConstraints cc = new CellConstraints();
        // create the builder with our panel as the component to be filled.
        PanelBuilder builder = new PanelBuilder(layout, fPanel);

        builder.add(fLeftPanelBuilder.getPanel(), cc.xy(1,1));
        builder.add(fCenterPanelBuilder.getPanel(), cc.xy(2,1));
        builder.add(fRightPanelBuilder.getPanel(), cc.xy(3,1));

        fLeftPanelBuilder.getPanel().setOpaque(false);
        fCenterPanelBuilder.getPanel().setOpaque(false);
        fRightPanelBuilder.getPanel().setOpaque(false);

//        fLeftPanelBuilder.getPanel().setBorder(BorderFactory.createLineBorder(Color.RED));
//        fCenterPanelBuilder.getPanel().setBorder(BorderFactory.createLineBorder(Color.BLUE));
//        fRightPanelBuilder.getPanel().setBorder(BorderFactory.createLineBorder(Color.GREEN));
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
        fLeftPanelBuilder.add(MacWidgetFactory.createSpacer(spacer_pixels,0));
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
            fCenterPanelBuilder.add(MacWidgetFactory.createSpacer(spacer_pixels,0));
            fCenterPanelBuilder.nextColumn();
        }

        fCenterPanelBuilder.appendColumn("p");
        fCenterPanelBuilder.add(toolToAdd);
        fCenterPanelBuilder.nextColumn();
    }


    public void addComponentToRight(JComponent toolToAdd) {
        fRightPanelBuilder.appendColumn("p");
        fRightPanelBuilder.add(toolToAdd);
        fRightPanelBuilder.nextColumn();
        // TODO add spacer
    }

    void setBackgroundPainter(Painter backgroundPainter) {
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
