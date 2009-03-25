package com.explodingpixels.macwidgets;

import com.explodingpixels.border.FocusStateMatteBorder;
import com.explodingpixels.painter.FocusStatePainter;
import com.explodingpixels.painter.GradientPainter;
import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.WindowDragger;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;


/**
 * A Mac style Bottom Bar. For a full descrption of what a Bottom Bar is, see the
 * <a href="http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-SW6">Bottom Bars</a>
 * section of Apple's Human Interface Guidelines. Here's an example of what this method cretes:
 * <br>
 * <img src="../../../../graphics/BottomBar.png">
 * <br>
 * Here's a simple example that creates a Bottom Bar:
 * <pre>
 * BottomBar bottomBar = BottomBar(BottomBarSize.LARGE);
 * bottomBar.addComponentToCenter(MacWidgetFactory.createEmphasizedLabel("My Label"));
 * </pre>
 */
public class BottomBar {

    private final TriAreaComponent fBottomBar = new TriAreaComponent(5);

    /**
     * Creates a {@code BottomBar} of the given size.
     *
     * @param size the height of the {@code BottomBar}.
     */
    public BottomBar(BottomBarSize size) {

        createAndInstallBackgroundPainter();
        createAndInstallBorder();

        fBottomBar.forceAreasToHaveTheSameWidth();

        // TODO use the actual border insets instead of the hard-coded value 2.
        // calculate the height of the bottom bar. this includes adding two pixels to incorporate
        // the height of the line border.
        int height = size.getHeight() + 2;
        fBottomBar.getComponent().setPreferredSize(new Dimension(-1, height));
    }

    /**
     * Adds the given component to the left side of this {@code BottomBar}.
     *
     * @param toolToAdd the tool to add to this {@code BottomBar}.
     */
    public void addComponentToLeft(JComponent toolToAdd) {
        fBottomBar.addComponentToLeft(toolToAdd);
    }

    /**
     * Adds the given component to the left side of this {@code BottomBar} followed by the
     * given an empty space of the given pixel width.
     *
     * @param toolToAdd     the tool to add to this {@code BottomBar}.
     * @param spacer_pixels the amount of space to post-pend the added component with.
     */
    public void addComponentToLeft(JComponent toolToAdd, int spacer_pixels) {
        fBottomBar.addComponentToLeft(toolToAdd, spacer_pixels);
    }

    /**
     * Adds the given component to the side of this {@code BottomBar}.
     *
     * @param toolToAdd the tool to add to this {@code BottomBar}.
     */
    public void addComponentToCenter(JComponent toolToAdd) {
        fBottomBar.addComponentToCenter(toolToAdd);
    }

    /**
     * Adds the given component to the center of this {@code BottomBar}. If this is not the
     * first component to be added to the center, then the given component will be preceeded by a
     * space of the given width.
     *
     * @param toolToAdd     the tool to add to this {@code BottomBar}.
     * @param spacer_pixels the amount of space to pre-pend the added component with *if* the given
     *                      component is *not* the first component to be added to the center.
     */
    public void addComponentToCenter(JComponent toolToAdd, int spacer_pixels) {
        fBottomBar.addComponentToCenter(toolToAdd, spacer_pixels);
    }

    /**
     * Adds the given component to the right side of this {@code BottomBar}.
     *
     * @param toolToAdd the tool to add to this {@code BottomBar}.
     */
    public void addComponentToRight(JComponent toolToAdd) {
        fBottomBar.addComponentToRight(toolToAdd);
    }

    /**
     * Adds the given component to the right side of this {@code BottomBar}. If this is not
     * the first component to be added to the right, then the given component will be followed by a
     * space of the given width.
     *
     * @param toolToAdd     the tool to add to this {@code BottomBar}.
     * @param spacer_pixels the amount of space to post-pend the added component with *if* the given
     *                      component is *not* the first component to be added to the center.
     */
    public void addComponentToRight(JComponent toolToAdd, int spacer_pixels) {
        fBottomBar.addComponentToRight(toolToAdd, spacer_pixels);
    }

    /**
     * Installs a drag listener on this {@code BottomBar} such that if it is dragged, it will
     * move the given {@link java.awt.Window}.
     *
     * @param window the {@code Window} to move when the this {@code BottomBar} is dragged.
     */
    public void installWindowDraggerOnWindow(Window window) {
        new WindowDragger(window, getComponent());
    }

    /**
     * Gets the user interface component representing this {@code BottomBar}. The returned
     * {@link JComponent} should be added to a container that will be displayed.
     *
     * @return the user interface component representing this {@code BottomBar}.
     */
    public JComponent getComponent() {
        return fBottomBar.getComponent();
    }

    // Private methods. ///////////////////////////////////////////////////////////////////////////

    private void createAndInstallBackgroundPainter() {
        Painter<Component> focusedPainter =
                new GradientPainter(
                        MacColorUtils.OS_X_BOTTOM_BAR_ACTIVE_TOP_COLOR,
                        MacColorUtils.OS_X_BOTTOM_BAR_ACTIVE_BOTTOM_COLOR);
        Painter<Component> unfocusedPainter =
                new GradientPainter(
                        MacColorUtils.OS_X_BOTTOM_BAR_INACTIVE_TOP_COLOR,
                        MacColorUtils.OS_X_BOTTOM_BAR_INACTIVE_BOTTOM_COLOR);
        Painter<Component> painter = new FocusStatePainter(focusedPainter, focusedPainter,
                unfocusedPainter);
        fBottomBar.setBackgroundPainter(painter);
    }

    private void createAndInstallBorder() {
        FocusStateMatteBorder outterBorder = new FocusStateMatteBorder(1, 0, 0, 0,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR,
                MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR,
                fBottomBar.getComponent());
        Border innerBorder = BorderFactory.createMatteBorder(1, 0, 0, 0,
                MacColorUtils.OS_X_BOTTOM_BAR_BORDER_HIGHLIGHT_COLOR);
        Border lineBorders = BorderFactory.createCompoundBorder(outterBorder, innerBorder);

        // TODO determine if there is a good standard for this. there doesn't seem to be any
        // TODO consistent value used by Apple.
        // left and right edge padding.
        int padding = 5;
        fBottomBar.getComponent().setBorder(
                BorderFactory.createCompoundBorder(lineBorders,
                        BorderFactory.createEmptyBorder(0, padding, 0, padding)));
    }

}
