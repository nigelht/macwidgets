package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.WindowUtils;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Renders a rounded rectangle (i.e. a badge) with a given number in the center of the rectangle.
 */
public class SourceListCountBadgeRenderer {

    private CustomJLabel fLabel = new CustomJLabel();

    private boolean fSelected = false;

    private static Font BADGE_FONT = new Font("Helvetica", Font.BOLD, 11);

//    private static Color BADGE_SELECTED_BACKGROUND_COLOR = Color.WHITE;
//
//    private static Color BADGE_UNSELECTED_BACKGROUND_COLOR = new Color(0x8899bc);
//
//    private static Color BADGE_UNSELECTED_UNFOCUSED_BACKGROUND_COLOR = new Color(0x9a9a9a);
//
//    private static Color BADGE_TEXT_COLOR = BADGE_SELECTED_BACKGROUND_COLOR;

    private final Color fSelectedColor;
    private final Color fActiveUnselectedColor;
    private final Color fInactiveUnselectedColor;
    private final Color fTextColor;

    /**
     * Creates a badge renderer.
     */
    public SourceListCountBadgeRenderer(Color selectedColor, Color activeUnselectedColor,
                                        Color inactiveUnselectedColor, Color textColor) {
        fLabel.setFont(BADGE_FONT);
        fLabel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

        fSelectedColor = selectedColor;
        fActiveUnselectedColor = activeUnselectedColor;
        fInactiveUnselectedColor = inactiveUnselectedColor;
        fTextColor = textColor;
    }

    /**
     * Sets the state to use when drawing the badge.
     *
     * @param count    the count value to draw in the center of the badge.
     * @param selected {@code} true if the badge should be rendered in a selected state.
     */
    public void setState(int count, boolean selected) {
        fLabel.setText(String.valueOf(count));
        fSelected = selected;
    }

    /**
     * Gets the user interface component to representing this {@code SourceListCountBadgeRenderer}.
     * The returned {@link JComponent} should be added to a container that will be displayed.
     *
     * @return the user interface component representing this {@code SourceListCountBadgeRenderer}.
     */
    public JComponent getComponent() {
        return fLabel;
    }

    // Custom JLabel implementation. //////////////////////////////////////////////////////////////

    private class CustomJLabel extends JLabel {

        private Color getSelectedBadgeColor() {
            return fSelectedColor;
        }

        private Color getUnselectedBadgeColor(boolean parentWindowHasFocus) {
            return parentWindowHasFocus ? fActiveUnselectedColor : fInactiveUnselectedColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            // create a buffered image to draw the component into. this lets us
            // draw "out" an area, making it transparent.
            BufferedImage image = new BufferedImage(getWidth(), getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            // create the graphics and set its initial state.
            Graphics2D g2d = image.createGraphics();
            g2d.setFont(getFont());
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(fSelected
                    ? getSelectedBadgeColor()
                    : getUnselectedBadgeColor(WindowUtils.isParentWindowFocused(this)));

            // draw the badge.
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());

            // get the bounds of the badge text in order to calculate the center.
            Rectangle2D bounds =
                    g2d.getFontMetrics().getStringBounds(getText(), g2d);
            // set the color to use for the text - note this color is always
            // the same, though it won't always show because of the composite
            // set below.
            g2d.setColor(fTextColor);
            // if the badge is selected, punch out the text so that the
            //    underlying color shows through as the font color.
            // else use use a standard alpha composite to simply draw on top of
            //    whatever is currently there.
            g2d.setComposite(fSelected
                    ? AlphaComposite.DstOut : AlphaComposite.SrcOver);
            // calculate the bottom left point to draw the text at.
            int x = getWidth() / 2 - g2d.getFontMetrics().stringWidth(getText()) / 2;
            int y = getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2;
            // draw the badge text.
            g2d.drawString(getText(), x, y);

            // draw the image into this component.
            g.drawImage(image, 0, 0, null);

            // dispose of the buffered image graphics.
            g2d.dispose();
        }
    }

}
