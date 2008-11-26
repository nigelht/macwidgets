package com.explodingpixels.widgets.plaf;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * An implementation of {@link javax.swing.plaf.ScrollBarUI} that supports dynamic skinning.
 * painting is delegated to a {@link ScrollBarSkin}.
 */
public class SkinnableScrollBarUI extends BasicScrollBarUI {

    private final ScrollBarSkin fSkin;

    private final ScrollBarOrientation fOrientation;

    private SkinnableScrollBarUI(ScrollBarSkin skin, ScrollBarOrientation orientation) {
        fSkin = skin;
        fOrientation = orientation;
    }

    /**
     * Creates a horizontal scroll bar using the given skin.
     *
     * @param skin the skin to paint the scroll bar with.
     * @return a {@code SkinnableScrollBarUI} painted with the given skin.
     */
    public static SkinnableScrollBarUI createHorizontalScrollBarUI(ScrollBarSkin skin) {
        return new SkinnableScrollBarUI(skin, ScrollBarOrientation.HORIZONTAL);
    }

    /**
     * Creates a vertical scroll bar using the given skin.
     *
     * @param skin the skin to paint the scroll bar with.
     * @return a {@code SkinnableScrollBarUI} painted with the given skin.
     */
    public static SkinnableScrollBarUI createVerticalScrollBarUI(ScrollBarSkin skin) {
        return new SkinnableScrollBarUI(skin, ScrollBarOrientation.VERTICAL);
    }

    @Override
    protected void installComponents() {
        fSkin.installComponents(scrollbar);
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        fSkin.installMouseListenersOnButtons(new CustomArrowButtonListener(-1),
                new CustomArrowButtonListener(1));
    }

    @Override
    public void layoutContainer(Container scrollbarContainer) {
        if (isDragging) {
            // do nothing.
        } else if (isAllContentVisible(scrollbar)) {
            fSkin.trackLayoutOnly(scrollbar, fOrientation);
            updateThumbBoundsFromScrollBarValue();
        } else {
            fSkin.fullLayout(scrollbar, fOrientation);
            updateThumbBoundsFromScrollBarValue();
        }
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return fSkin.getMinimumThumbSize();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return fSkin.getPreferredSize();
    }

    @Override
    protected Rectangle getThumbBounds() {
        return fSkin.getScrollThumbBounds();
    }

    private void setThumbBounds(Rectangle thumbBounds) {
        setThumbBounds(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        fSkin.setScrollThumbBounds(new Rectangle(x, y, width, height));
    }

    @Override
    protected Rectangle getTrackBounds() {
        return fSkin.getTrackBounds();
    }

    @Override
    protected void paintIncreaseHighlight(Graphics g) {
        // do nothing - not supported.
    }

    @Override
    protected void paintDecreaseHighlight(Graphics g) {
        // do nothing - not supported.
    }

    private void updateThumbBoundsFromScrollBarValue() {
        float min = scrollbar.getMinimum();
        float extent = scrollbar.getVisibleAmount();
        float range = scrollbar.getMaximum() - min;
        float value = scrollbar.getValue();

        int trackSize = fOrientation.getLength(fSkin.getTrackBounds().getSize());
        int thumbLength = (int) (trackSize * (extent / range));

        int minimumThumbLength = fOrientation.getLength(getMinimumThumbSize());
        thumbLength = Math.max(thumbLength, minimumThumbLength);

        float thumbRange = trackSize - thumbLength;
        int thumbPosition = (int) (0.5f + (thumbRange * ((value - min) / (range - extent))));

        fSkin.setScrollThumbBounds(
                fOrientation.createBounds(scrollbar, thumbPosition, thumbLength));
    }

    private void updateThumbBoundsAndScrollBarValueFromMouseEvent(MouseEvent event, int offset) {
        int mouseLocation = adjustMousePosition(event.getPoint(), offset);
        updateThumbBoundsFromMouseEvent(mouseLocation);
        updateScrollBarValueFromMouseEvent(mouseLocation);
    }

    private void updateThumbBoundsFromMouseEvent(int mouseLocation) {
        Dimension thumbSize = getThumbBounds().getSize();
        Dimension trackSize = getTrackBounds().getSize();

        // set the visible thumb bounds. this smoothly tracks where the user has the mouse.
        // when they release the mouse, the actual scroll thumb position will be updated to
        // reflect the exact scroll view window.
        int thumbMaxPossiblePosition =
                fOrientation.getLength(trackSize) - fOrientation.getLength(thumbSize);
        int thumbPosition = Math.min(thumbMaxPossiblePosition, Math.max(0, mouseLocation));
        // update the scroll thumb's position.
        setThumbBounds(fOrientation.updateBoundsPosition(getThumbBounds(), thumbPosition));
    }

    private void updateScrollBarValueFromMouseEvent(int mouseLocation) {
        BoundedRangeModel model = scrollbar.getModel();
        Rectangle thumbBounds = getThumbBounds();
        Rectangle trackBounds = getTrackBounds();

        // calculate what the value of the scrollbar should be.
        int minimumPossibleThumbPosition = fOrientation.getPosition(trackBounds.getLocation());
        int maximumPossibleThumbPosition = getMaximumPossibleThumbPosition(trackBounds, thumbBounds);
        int actualThumbPosition = Math.min(maximumPossibleThumbPosition,
                Math.max(minimumPossibleThumbPosition, mouseLocation));

        // calculate the new value for the scroll bar (the top of the scroll thumb) based
        // on the dragged location.
        float valueMax = model.getMaximum() - model.getExtent();
        float valueRange = valueMax - model.getMinimum();
        float thumbValue = actualThumbPosition - minimumPossibleThumbPosition;
        float thumbRange = maximumPossibleThumbPosition - minimumPossibleThumbPosition;
        int value = (int) Math.ceil((thumbValue / thumbRange) * valueRange);

        scrollbar.setValue(value + model.getMinimum());
    }

    private int getMaximumPossibleThumbPosition(Rectangle trackBounds, Rectangle thumbBounds) {
        int trackStartPosition = fOrientation.getPosition(trackBounds.getLocation());
        int trackLength = fOrientation.getLength(trackBounds.getSize());
        int thumbLength = fOrientation.getLength(thumbBounds.getSize());
        return trackStartPosition + trackLength - thumbLength;
    }

    private int adjustMousePosition(Point mousePoint, int offset) {
        return fOrientation.getPosition(mousePoint) - offset;
    }

    private boolean isPointBeforeScrollThumb(Point point) {
        int mousePosition = fOrientation.getPosition(point);
        int thumbPosition = fOrientation.getPosition(getThumbBounds().getLocation());
        return mousePosition < thumbPosition;
    }

    private boolean isPointAfterScrollThumb(Point point) {
        int mousePosition = fOrientation.getPosition(point);
        int thumbPosition = fOrientation.getPosition(getThumbBounds().getLocation());
        return mousePosition > thumbPosition;
    }

    private int getDirectionToMoveThumb(Point mousePoint) {
        return isPointBeforeScrollThumb(mousePoint) ? -1 : 1;
    }

    @Override
    protected TrackListener createTrackListener() {
        return new SkinnableTrackListener();
    }

    @Override
    protected PropertyChangeListener createPropertyChangeListener() {
        return new SkinnablePropertyChangeHandler();
    }

    private static boolean isAllContentVisible(JScrollBar scrollBar) {
        float extent = scrollBar.getVisibleAmount();
        float range = scrollBar.getMaximum() - scrollBar.getMinimum();
        return extent / range == 1.0;
    }

    // SkinnablePropertyChangeHandler implementation. /////////////////////////////////////////////

    private class SkinnablePropertyChangeHandler extends PropertyChangeHandler {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            super.propertyChange(e);
            // repaint the scroll bar if the parent windows focus state has changed.
            if (e.getPropertyName().equals(WindowUtils.FRAME_ACTIVE_PROPERTY)) {
                scrollbar.repaint();
            }
        }
    }

    // SkinnableTrackListener implementation. ////////////////////////////////////////////////////

    private class SkinnableTrackListener extends TrackListener {

        private Point iMousePoint = new Point();

        @Override
        public void mousePressed(MouseEvent event) {
            if (shouldHandleMousePressed(event)) {
                doMousePressed(event);
            }
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            if (shouldHandleMouseDragged(event)) {
                doMouseDragged(event);
            }
        }

        private void startScrollTimerIfNecessary() {
            if (isPointBeforeScrollThumb(iMousePoint) || isPointAfterScrollThumb(iMousePoint)) {
                scrollTimer.start();
            }
        }

        private void captureCurrentMousePosition(MouseEvent event) {
            assert event.getSource() == scrollbar
                    : "The listener should be registered with the scrollbar for mouse events.";
            currentMouseX = event.getX();
            currentMouseY = event.getY();
            iMousePoint.x = currentMouseX;
            iMousePoint.y = currentMouseY;
        }

        private boolean isIgnorableMiddleMousePress(MouseEvent event) {
            return SwingUtilities.isMiddleMouseButton(event) && !getSupportsAbsolutePositioning();
        }

        private boolean shouldHandleMousePressed(MouseEvent event) {
            return !isIgnorableMiddleMousePress(event) && !SwingUtilities.isRightMouseButton(event)
                    && scrollbar.isEnabled();
        }

        private boolean shouldHandleMouseDragged(MouseEvent event) {
            return !isIgnorableMiddleMousePress(event) && !SwingUtilities.isRightMouseButton(event)
                    && scrollbar.isEnabled() && !getThumbBounds().isEmpty();
        }

        private void doMousePressed(MouseEvent event) {
            scrollbar.setValueIsAdjusting(true);
            captureCurrentMousePosition(event);

            if (getThumbBounds().contains(iMousePoint)) {
                doMousePressedOnThumb();
            } else
            if (getSupportsAbsolutePositioning() && SwingUtilities.isMiddleMouseButton(event)) {
                doMiddleMouseButtonPressedOnTrack(event);
            } else if (getTrackBounds().contains(iMousePoint)) {
                doMousePressedOnTrack();
            }
        }

        private void doMousePressedOnThumb() {
            offset = fOrientation.getPosition(iMousePoint) - fOrientation.getPosition(getThumbBounds().getLocation());
            isDragging = true;
        }

        private void doMiddleMouseButtonPressedOnTrack(MouseEvent event) {
            offset = fOrientation.getLength(getThumbBounds().getSize()) / 2;
            isDragging = true;
            updateThumbBoundsAndScrollBarValueFromMouseEvent(event, offset);
        }

        private void doMousePressedOnTrack() {
            isDragging = false;
            int direction = getDirectionToMoveThumb(iMousePoint);
            scrollByBlock(direction);
            scrollTimer.stop();
            scrollListener.setDirection(direction);
            scrollListener.setScrollByBlock(true);
            startScrollTimerIfNecessary();
        }

        private void doMouseDragged(MouseEvent event) {
            if (isDragging) {
                updateThumbBoundsAndScrollBarValueFromMouseEvent(event, offset);
            } else {
                captureCurrentMousePosition(event);
                startScrollTimerIfNecessary();
            }
        }
    }

    // IncrementButtonListener implementation. ////////////////////////////////////////////////////

    protected class CustomArrowButtonListener extends ArrowButtonListener {

        private final int iScrollDirection;

        private CustomArrowButtonListener(int scrollDirection) {
            iScrollDirection = scrollDirection;
        }

        public void mousePressed(MouseEvent e) {
            if (scrollbar.isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
                scrollByUnit(iScrollDirection);
                scrollTimer.stop();
                scrollListener.setDirection(iScrollDirection);
                scrollListener.setScrollByBlock(false);
                scrollTimer.start();
            }
        }

        public void mouseReleased(MouseEvent e) {
            scrollTimer.stop();
            scrollbar.setValueIsAdjusting(false);
        }
    }

}
