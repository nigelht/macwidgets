package com.explodingpixels.painter;

import com.explodingpixels.widgets.WindowUtils;

import java.awt.Component;
import java.awt.Graphics2D;

public class FocusStatePainter implements Painter<Component> {

    private Painter<Component> fComponentFocusedPainter;

    private Painter<Component> fWindowFocusedPainter;

    private Painter<Component> fWindowUnfocusedPainter;

    public FocusStatePainter(Painter<Component> componentFocusedPainter,
                             Painter<Component> componentUnfocusedPainter) {
        this(componentFocusedPainter, componentUnfocusedPainter, componentUnfocusedPainter);
    }

    public FocusStatePainter(Painter<Component> componentFocusedPainter,
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
