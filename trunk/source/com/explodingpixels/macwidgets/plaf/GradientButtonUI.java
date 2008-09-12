package com.explodingpixels.macwidgets.plaf;

import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.ButtonModel;
import java.awt.*;

public class GradientButtonUI extends BasicButtonUI {

    private static final String LIGHT_GRADIENT_BUTTON = "gradient.light";

    private static final Color LIGHT_TEXTURED_FONT_COLOR = Color.BLACK;
    private static final Color LIGHT_TEXTURED_BORDER_COLOR = new Color(0x727272);
    private static final Color LIGHT_TEXTURED_TOP_COLOR = new Color(0xfffffe);
    private static final Color LIGHT_TEXTURED_BOTTOM_COLOR = new Color(0xafadad);
    private static final Color LIGHT_TEXTURED_PRESSED_TOP_COLOR = new Color(0x848284);
    private static final Color LIGHT_TEXTURED_PRESSED_BOTTOM_COLOR = new Color(0xa6a6a7);

    private static final Color DARK_TEXTURED_FONT_COLOR = Color.WHITE;
    private static final Color DARK_TEXTURED_BORDER_COLOR = new Color(0xb5b8bf);
    private static final Color DARK_TEXTURED_TOP_COLOR = new Color(0x2e2e2f);
    private static final Color DARK_TEXTURED_BOTTOM_COLOR = new Color(0x080808);
    private static final Color DARK_TEXTURED_PRESSED_TOP_COLOR = new Color(0x989a9e);
    private static final Color DARK_TEXTURED_PRESSED_BOTTOM_COLOR = new Color(0xbf6164);

    private final ButtonType fButtonType;

    public GradientButtonUI(ButtonType buttonType) {
        fButtonType = buttonType;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        // TODO save original values.

        b.setHorizontalTextPosition(AbstractButton.CENTER);
        b.setMargin(new Insets(3,16,3,16));
        b.setForeground(fButtonType.getFontColor());
        b.setOpaque(false);
    }

    @Override
    protected void uninstallDefaults(AbstractButton b) {
        super.uninstallDefaults(b);
        // TODO implement.
    }

    @Override
    public void paint(Graphics g, JComponent c) {

        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setPaint(model.isPressed()
                ? fButtonType.getPressedPaint(c,1) : fButtonType.getPaint(c,1));
//        graphics.fillRoundRect(0,1,c.getWidth(),c.getHeight()-2,6,6);
        int arcDiameter = c.getHeight()-2;
        graphics.fillRoundRect(0,1,c.getWidth(),c.getHeight()-2,arcDiameter,arcDiameter);
        graphics.setColor(fButtonType.getBorderColor());
//        graphics.drawRoundRect(0,0,c.getWidth()-1,c.getHeight()-1,6,6);
        graphics.drawRoundRect(0,0,c.getWidth()-1,c.getHeight()-1,arcDiameter,arcDiameter);

        super.paint(g, c);
    }


    public enum ButtonType {

        LIGHT(Color.BLACK, new Color(0x727272),
                new Color(0xfffffe),
                new Color(0xafadad), new Color(0x848284), new Color(0xa6a6a7)),
        DARK(Color.WHITE, new Color(0xb5b8bf), new Color(0x2e2e2f),
                new Color(0x080808), new Color(0x989a9e), new Color(0x5f6164));

        private Color fFontColor;
        private Color fBorderColor;
        private Color fTopColor;
        private Color fBottomColor;
        private Color fTopPressedColor;
        private Color fBottomPressedColor;

        private ButtonType(Color fontColor,
                             Color borderColor, Color topColor,
                             Color bottomColor, Color topPressedColor,
                             Color bottomPressedColor) {
            fFontColor = fontColor;
            fBorderColor = borderColor;
            fTopColor = topColor;
            fBottomColor = bottomColor;
            fTopPressedColor = topPressedColor;
            fBottomPressedColor = bottomPressedColor;
        }

        public Color getFontColor() {
            return fFontColor;
        }

        public Color getBorderColor() {
            return fBorderColor;
        }

        public Paint getPaint(JComponent component, int borderWidth_pixels) {
            return new GradientPaint(0,borderWidth_pixels,fTopColor,
                    0,component.getHeight()-(borderWidth_pixels*2), fBottomColor);
        }

        public Paint getPressedPaint(JComponent component, int borderWidth_pixels) {
            return new GradientPaint(0,borderWidth_pixels,fTopPressedColor,
                    0,component.getHeight()-(borderWidth_pixels*2), fBottomPressedColor);
        }

    }

}
