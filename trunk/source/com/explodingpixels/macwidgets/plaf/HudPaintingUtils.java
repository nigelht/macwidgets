package com.explodingpixels.macwidgets.plaf;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

/**
 * A collection of utilty method for painting Heads Up Style widgets. See the following for examples
 * of HUD widgets:
 * <ul>
 * <li>{@link HudButtonUI}</li>
 * <li>{@link HudCheckBoxUI}</li>
 * <li>{@link HudComboBoxUI}</li>
 * <li>{@link HudLabelUI}</li>
 * </ul>
 */
public class HudPaintingUtils {

    public static final float FONT_SIZE = 11.0f;
    public static final Color FONT_COLOR = Color.WHITE;

    public static final Color PRESSED_MARK_COLOR = new Color(0, 0, 0, 225);

    private static final Color TOP_COLOR = new Color(170, 170, 170, 50);
    private static final Color BOTTOM_COLOR = new Color(17, 17, 17, 50);
    private static final Color TOP_PRESSED_COLOR = new Color(249, 249, 249, 153);
    private static final Color BOTTOM_PRESSED_COLOR = new Color(176, 176, 176, 153);

    public static final Color BORDER_COLOR = new Color(0xc5c8cf);
    private static final int BORDER_WIDTH = 1;

    private static final Color LIGHT_SHADOW_COLOR = new Color(0, 0, 0, 145);
    private static final Color DARK_SHADOW_COLOR = new Color(0, 0, 0, 50);

    private HudPaintingUtils() {
        // utility class - no constructor needed.
    }

    /**
     * Initializes the given {@link JComponent} as a HUD style widget. This includes setting the
     * font, foreground and opacity of the given component.
     *
     * @param component
     */
    public static void initHudComponent(JComponent component) {
        component.setFont(HudPaintingUtils.getHudFont());
        component.setForeground(HudPaintingUtils.FONT_COLOR);
        component.setOpaque(false);
    }

    /**
     * Gets the font used by HUD style widgets.
     *
     * @return the font used by HUD style widgets.
     */
    public static Font getHudFont() {
        return UIManager.getFont("Button.font").deriveFont(Font.BOLD, FONT_SIZE);
    }

    /**
     * Gets the number of pixels that a HUD style widget's shadow takes up. HUD button's have a
     * shadow directly below them, that is, there is no top, left or right component to the shadow.
     *
     * @param button the button that the shadow is drawn on.
     * @return the number of pixels that a HUD style widget's shadow takes up.
     */
    public static int getHudControlShadowSize(AbstractButton button) {
        // TODO use the button's font size to figure this out.
        return 2;
    }

    /**
     * Paints a HUD style button background onto the given {@link Graphics2D} context using the
     * given {@link Roundedness}. The background will be painted from 0,0 to width/height.
     *
     * @param graphics    the graphics context to paint onto.
     * @param button      the button being painted.
     * @param width       the width of the area to paint.
     * @param height      the height of the area to paint.
     * @param roundedness the roundedness to use when painting the background.
     */
    public static void paintHudControlBackground(Graphics2D graphics, AbstractButton button,
                                                 int width, int height, Roundedness roundedness) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // TODO replace with real drop shadow painting.

        graphics.setColor(LIGHT_SHADOW_COLOR);
        int arcDiameter = roundedness.getRoundedDiameter(height);
        graphics.drawRoundRect(0, 0, width - 1, height, arcDiameter, arcDiameter);

        graphics.setColor(DARK_SHADOW_COLOR);
        int smallerShadowArcDiameter = height - 1;
        graphics.drawRoundRect(0, 0, width - 1, height + 1, smallerShadowArcDiameter,
                smallerShadowArcDiameter);

        graphics.setPaint(HudPaintingUtils.createButtonPaint(button, BORDER_WIDTH));
        graphics.fillRoundRect(0, 1, width, height - 1, arcDiameter, arcDiameter);

        graphics.setColor(BORDER_COLOR);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcDiameter, arcDiameter);
    }

    private static Paint createButtonPaint(AbstractButton button, int lineBorderWidth) {
        boolean isPressed = button.getModel().isPressed();
        Color topColor = isPressed ? TOP_PRESSED_COLOR : TOP_COLOR;
        Color bottomColor = isPressed ? BOTTOM_PRESSED_COLOR : BOTTOM_COLOR;
        int bottomY = button.getHeight() - lineBorderWidth * 2;
        return new GradientPaint(0, lineBorderWidth, topColor, 0, bottomY, bottomColor);
    }

    /**
     * An enumeration representing the roundness styles of HUD buttons.
     */
    public enum Roundedness {

        ROUNDED_BUTTON(.95), COMBO_BUTTON(0.45), CHECK_BOX(0.4), RADIO(1.0);

        private final double fRoundedPercentage;

        private Roundedness(double roundedPercentage) {
            fRoundedPercentage = roundedPercentage;
        }

        private int getRoundedDiameter(int controlHeight) {
            // TODO make roudedness based on font size rather than component height.
            int roundedDiameter = (int) (controlHeight * fRoundedPercentage);
            int makeItEven = roundedDiameter % 2;
            return roundedDiameter - makeItEven;
        }

    }

}
