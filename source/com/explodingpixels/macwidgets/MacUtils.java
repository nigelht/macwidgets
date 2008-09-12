package com.explodingpixels.macwidgets;

import javax.swing.JRootPane;

public class MacUtils {

    public static void makeWindowLeopardStyle(JRootPane rootPane) {

        // TODO figure out correct way to determine if the JRootPane has been
        // TODO realized.
        if (rootPane.isValid()) {
            throw new IllegalArgumentException("This method only works if the" +
                    "given JRootPane has not yet been realized.");
        }

        rootPane.putClientProperty("apple.awt.brushMetalLook", Boolean.TRUE);

    }

}
