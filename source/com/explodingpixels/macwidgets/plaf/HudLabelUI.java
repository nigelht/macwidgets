package com.explodingpixels.macwidgets.plaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

/**
 * Creates a Heads Up Display (HUD) style label, similar to that seen in various iApps (e.g. iPhoto).
 * <br>
 * <img src="../../../../../graphics/HUDLabelUI.png">
 */
public class HudLabelUI extends BasicLabelUI {

    @Override
    protected void installDefaults(JLabel c) {
        super.installDefaults(c);
        HudPaintingUtils.initHudComponent(c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        HudPaintingUtils.updateGraphicsToPaintDisabledControlIfNecessary((Graphics2D) g, c);
        super.paint(g, c);
    }

    @Override
    protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        super.paintEnabledText(l, g, s, textX, textY);
    }
}
