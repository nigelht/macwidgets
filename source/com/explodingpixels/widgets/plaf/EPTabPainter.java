/* Copyright 2009 The MathWorks, Inc. */

package com.explodingpixels.widgets.plaf;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class EPTabPainter {

    private CloseButtonLocation fCloseButtonLocation = CloseButtonLocation.LEFT;
    private CloseButtonIcon fCloseButtonIcon = new DefaultCloseButtonIcon();

    private static final int CONTENT_DISTANCE_FROM_EDGE = 5;
    private static final int CLOSE_BUTTON_DISTANCE_FROM_EDGE = 4;
    private static final int CLOSE_BUTTON_DISTANCE_FROM_CONTENT = 3;

    // create single variable to reuse when layout the tab. this reuse of rectangle is also seen in BasicLabelUI and
    // was used there for performance gains -- we're following that example, though it's unclear whether or not there
    // are still performance gains to be had by doing this.
    private static final Rectangle ADJUSTED_TAB_BOUNDS = new Rectangle();
    private static final Rectangle TEXT_BOUNDS = new Rectangle();
    private static final Rectangle ICON_BOUNDS = new Rectangle();

    // the colors used to draw the tab backround and border.
    static final Color SELECTED_BORDER_COLOR = new Color(0x666666);
    private static final Color UNSELECTED_BORDER_COLOR = new Color(0x888888);
    private static final Color SELECTED_BACKGROUND_COLOR = Color.WHITE;
    private static final Color UNSELECTED_BACKGROUND_COLOR = new Color(0xcccccc);

    private static final int CORNER_ARC_DIAMETER = 6;

    public void setCloseButtonLocation(CloseButtonLocation closeButtonLocation) {
        fCloseButtonLocation = closeButtonLocation;
    }

    public void paintTab(Graphics2D graphics, JTabbedPane tabPane, Rectangle tabBounds, String tabText,
                         Icon tabIcon, boolean isSelected, boolean isMouseOverCloseButton, boolean isMousePressedOverCloseButton) {

        paintTabBackgroundAndBorder(graphics, tabBounds, isSelected);
        paintCloseButton(graphics, tabBounds, isSelected, isMouseOverCloseButton, isMousePressedOverCloseButton);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int closeButtonWidth = fCloseButtonIcon.getWidth();

        int textWidth = fontMetrics.stringWidth(tabText);

        int widthRequiredForCloseButton = fCloseButtonLocation.calculateWidthRequiredForCloseButton(closeButtonWidth);
        boolean tooWide = textWidth > tabBounds.width - widthRequiredForCloseButton;

        Rectangle adjustedTabRect = new Rectangle();
        adjustedTabRect.x = tooWide
                ? fCloseButtonLocation.calculateContentX(tabBounds, closeButtonWidth) : tabBounds.x;
        adjustedTabRect.y = tabBounds.y;
        adjustedTabRect.width = tooWide ? tabBounds.width - widthRequiredForCloseButton - CONTENT_DISTANCE_FROM_EDGE : tabBounds.width;
        adjustedTabRect.height = tabBounds.height;

        ICON_BOUNDS.x = 0;
        ICON_BOUNDS.y = 0;

        String clippedText = SwingUtilities.layoutCompoundLabel(tabPane, fontMetrics, tabText, tabIcon,
                SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.TRAILING,
                adjustedTabRect, ICON_BOUNDS, TEXT_BOUNDS, 4);

        graphics.setColor(tabPane.getForeground());
        int textY = TEXT_BOUNDS.y + fontMetrics.getAscent();
        BasicGraphicsUtils.drawString(graphics, clippedText, -1, TEXT_BOUNDS.x, textY);

    }

    private void paintTabBackgroundAndBorder(Graphics2D graphics, Rectangle tabBounds, boolean isSelected) {
        int extendedHeight = tabBounds.height + CORNER_ARC_DIAMETER / 2;
        // paint the background.
        graphics.setColor(isSelected ? SELECTED_BACKGROUND_COLOR : UNSELECTED_BACKGROUND_COLOR);
        graphics.fillRoundRect(tabBounds.x, tabBounds.y, tabBounds.width - 1, extendedHeight,
                CORNER_ARC_DIAMETER, CORNER_ARC_DIAMETER);

        // paint the border.
        graphics.setColor(isSelected ? SELECTED_BORDER_COLOR : UNSELECTED_BORDER_COLOR);
        graphics.drawRoundRect(tabBounds.x, tabBounds.y, tabBounds.width - 1, extendedHeight,
                CORNER_ARC_DIAMETER, CORNER_ARC_DIAMETER);
    }

    private void paintCloseButton(Graphics2D graphics, Rectangle tabBounds, boolean isSelected,
                                  boolean isMouseOverCloseButton, boolean isMousePressedOverCloseButton) {
        int x = fCloseButtonLocation.calculateCloseButtonX(tabBounds, fCloseButtonIcon.getWidth());
        int y = fCloseButtonLocation.calculateCloseButtonY(tabBounds, fCloseButtonIcon.getHeight());
        ImageIcon closeImageIcon =
                fCloseButtonIcon.getImageIcon(isSelected, isMouseOverCloseButton, isMousePressedOverCloseButton);
        graphics.drawImage(closeImageIcon.getImage(), x, y, null);
    }

    public boolean isPointOverCloseButton(Rectangle tabBounds, Point point) {
        int closeButtonWidth = fCloseButtonIcon.getWidth();
        int closeButtonHeight = fCloseButtonIcon.getHeight();
        int closeButtonX = fCloseButtonLocation.calculateCloseButtonX(tabBounds, closeButtonWidth);
        int closeButtonY = fCloseButtonLocation.calculateCloseButtonY(tabBounds, closeButtonHeight);
        boolean overHorizontally = closeButtonX <= point.x && point.x <= closeButtonX + closeButtonWidth;
        boolean overVertically = closeButtonY <= point.y && point.y <= closeButtonY + closeButtonHeight;
        return overHorizontally && overVertically;
    }

    // CloseButton enumeration implementation. ////////////////////////////////////////////////////////////////////////

    public enum CloseButtonLocation {

        LEFT {
            int calculateCloseButtonX(Rectangle tabBounds, int closeButtonWidth) {
                return tabBounds.x + CLOSE_BUTTON_DISTANCE_FROM_EDGE;
            }
            int calculateContentX(Rectangle tabBounds, int closeButtonWidth) {
                return tabBounds.x + calculateWidthRequiredForCloseButton(closeButtonWidth);
            }
        },
        RIGHT {
            int calculateCloseButtonX(Rectangle tabBounds, int closeButtonWidth) {
                return tabBounds.x + tabBounds.width - CLOSE_BUTTON_DISTANCE_FROM_CONTENT - closeButtonWidth;
            }
            int calculateContentX(Rectangle tabBounds, int closeButtonWidth) {
                return tabBounds.x + CONTENT_DISTANCE_FROM_EDGE;
            }
        };

        abstract int calculateCloseButtonX(Rectangle tabBounds, int closeButtonWidth);

        abstract int calculateContentX(Rectangle tabBounds, int closeButtonWidth);

        private int calculateCloseButtonY(Rectangle tabBounds, int closeButtonHeight) {
            return tabBounds.y + tabBounds.height / 2 - closeButtonHeight / 2;
        }

        int calculateWidthRequiredForCloseButton(int closeButtonWidth) {
            return closeButtonWidth + CLOSE_BUTTON_DISTANCE_FROM_EDGE + CLOSE_BUTTON_DISTANCE_FROM_CONTENT;
        }

        private int calculateContentWidthAvailable(Rectangle tabBounds, Icon closeButtonIcon) {
            return tabBounds.width - closeButtonIcon.getIconWidth() - CLOSE_BUTTON_DISTANCE_FROM_EDGE
                    - CLOSE_BUTTON_DISTANCE_FROM_CONTENT - CONTENT_DISTANCE_FROM_EDGE;
        }

    }

    // CloseButton interface and implementations. /////////////////////////////////////////////////////////////////////

    private interface CloseButtonIcon {
        int getWidth();

        int getHeight();

        ImageIcon getImageIcon(boolean isSelected, boolean isOver, boolean isPressed);
    }

    private static class DefaultCloseButtonIcon implements CloseButtonIcon {

        private ImageIcon fSelected = createImageIcon("close.png");
        private ImageIcon fUnselected = createImageIcon("close_unselected.png");
        private ImageIcon fOver = createImageIcon("close_over.png");
        private ImageIcon fPressed = createImageIcon("close_over.png");
        // TODO uncomment out below line.
//        private ImageIcon fPressed = createImageIcon("close_pressed.png");

        public int getWidth() {
            return fSelected.getIconWidth();
        }

        public int getHeight() {
            return fSelected.getIconHeight();
        }

        public ImageIcon getImageIcon(boolean isSelected, boolean isOver, boolean isPressed) {
            ImageIcon closeImageIcon;

            if (isOver && isPressed) {
                closeImageIcon = fPressed;
            } else if (isOver) {
                closeImageIcon = fOver;
            } else if (isSelected) {
                closeImageIcon = fSelected;
            } else {
                closeImageIcon = fUnselected;
            }

            return closeImageIcon;
        }

        private static ImageIcon createImageIcon(String fileName) {
            return new ImageIcon(EPTabbedPaneUI.class.getResource("/com/explodingpixels/widgets/images/" + fileName));
        }
    }
}
