package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.HudButtonUI;

import javax.swing.AbstractButton;
import javax.swing.JButton;

public class HudWidgetFactory {

    private HudWidgetFactory() {
        // utility class - no constructor needed.
    }

    public static AbstractButton createHudButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setUI(new HudButtonUI());
        return button;
    }

}
