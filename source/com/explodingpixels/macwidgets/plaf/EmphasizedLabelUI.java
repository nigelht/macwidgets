package com.explodingpixels.macwidgets.plaf;

import com.explodingpixels.macwidgets.MacColorUtils;
import com.explodingpixels.widgets.WindowUtils;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.Color;
import java.awt.Graphics;

public class EmphasizedLabelUI extends BasicLabelUI {

    private Color fShadowColor;
    private Color fFocusedTextColor;
    private Color fUnfocusedTextColor;

    public static final Color DEFAULT_EMPHASIS_COLOR =
            MacColorUtils.MAC_SOURCE_LIST_CATEGORY_FONT_SHADOW_COLOR;
    public static final Color DEFAULT_FOCUSED_FONT_COLOR = new Color(0x000000);
    public static final Color DEFAULT_UNFOCUSED_FONT_COLOR = new Color(0x3f3f3f);
    public static final Color DEFAULT_DISABLED_FONT_COLOR = new Color(0x3f3f3f);

    public EmphasizedLabelUI() {
        this(DEFAULT_FOCUSED_FONT_COLOR, DEFAULT_UNFOCUSED_FONT_COLOR,
                DEFAULT_EMPHASIS_COLOR);
    }

    public EmphasizedLabelUI(Color focusedTextColor,
                             Color unfocusedTextColor, Color emphasisColor) {
        fFocusedTextColor = focusedTextColor;
        fUnfocusedTextColor = unfocusedTextColor;
        fShadowColor = emphasisColor;

    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        // TODO add uninstallation.
    }

    @Override
    protected void paintEnabledText(JLabel label, Graphics g, String s,
                                    int textX, int textY) {
        g.setColor(fShadowColor);
        g.setFont(label.getFont());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, -1, textX, textY + 1);
        g.setColor(WindowUtils.isParentWindowFocused(label)
                ? fFocusedTextColor : fUnfocusedTextColor);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, -1, textX, textY);
    }

    @Override
    protected void paintDisabledText(JLabel label, Graphics g, String s, int textX, int textY) {
        // TODO do use a diabled color here.
        g.setColor(fShadowColor);
        g.setFont(label.getFont());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, -1, textX, textY + 1);
        g.setColor(DEFAULT_DISABLED_FONT_COLOR);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, -1, textX, textY);
    }
}
