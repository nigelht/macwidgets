package com.explodingpixels.macwidgets.plaf;

import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

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

}
