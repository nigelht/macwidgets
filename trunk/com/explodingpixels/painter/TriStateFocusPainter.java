package com.explodingpixels.painter;

import org.jdesktop.swingx.painter.Painter;

import java.awt.Component;
import java.awt.Graphics2D;

import com.explodingpixels.widgets.WindowUtils;

public class TriStateFocusPainter implements Painter<Component> {

    private Painter<Component> fComponentFocusedPainter;

    private Painter<Component> fWindowFocusedPainter;

    private Painter<Component> fWindowUnfocusedPainter;

    public TriStateFocusPainter(Painter<Component> componentFocusedPainter,
                                Painter<Component> windowFocusedPainter,
                                Painter<Component> windowUnfocusedPainter) {

        if (componentFocusedPainter == null) {
            throw new IllegalArgumentException("Component focused Painter cannot be null.");
        }

        if (windowFocusedPainter == null) {
            throw new IllegalArgumentException("Window focused Painter cannot be null.");
        }

        if (windowUnfocusedPainter == null) {
            throw new IllegalArgumentException("Window unfocused Painter cannot be null.");
        }

        fComponentFocusedPainter = componentFocusedPainter;
        fWindowFocusedPainter = windowFocusedPainter;
        fWindowUnfocusedPainter = windowUnfocusedPainter;

    }

    public void paint(Graphics2D g, Component component, int width, int height) {
        Painter<Component> painterToUse;
//        =
//                WindowUtils.isParentWindowFocused(component)
//                ? fComponentFocusedPainter : fWindowUnfocusedPainter;

        if (component.hasFocus()) {
            painterToUse = fComponentFocusedPainter;
        } else if (WindowUtils.isParentWindowFocused(component)) {
            painterToUse = fWindowFocusedPainter;
        } else {
            painterToUse = fWindowUnfocusedPainter;
        }

        painterToUse.paint(g, component, width, height);
    }
}
