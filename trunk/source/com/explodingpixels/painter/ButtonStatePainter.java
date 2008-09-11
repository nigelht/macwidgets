package com.explodingpixels.painter;

import org.jdesktop.swingx.painter.Painter;

import javax.swing.AbstractButton;
import java.awt.Graphics2D;
import java.awt.Component;

public class ButtonStatePainter<B extends AbstractButton>
        implements Painter<B> {

    private final Painter<Component> fDefaultPainter;
    private final Painter<Component> fRolloverPainter;
    private final Painter<Component> fPressedPainter;
    private final Painter<Component> fSelectedPainter;

    public ButtonStatePainter(Painter<Component> defaultPainter) {
        this(defaultPainter, defaultPainter, defaultPainter, defaultPainter);
    }

    public ButtonStatePainter(Painter<Component> defaultPainter,
                              Painter<Component> rolloverPainter,
                              Painter<Component> pressedPainter,
                              Painter<Component> selectedPainter) {
        fDefaultPainter = defaultPainter;
        fRolloverPainter = rolloverPainter;
        fPressedPainter = pressedPainter;
        fSelectedPainter = selectedPainter;
    }

    public void paint(Graphics2D g, B button, int width, int height) {

        if (button.getModel().isRollover()) {
            fRolloverPainter.paint(g,button,width,height);
        } else if (button.getModel().isPressed()) {
            fPressedPainter.paint(g,button,width,height);
        } else if (button.getModel().isSelected()) {
            fSelectedPainter.paint(g,button,width,height);
        } else {
            fDefaultPainter.paint(g,button,width,height);
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // Dummy Painter implementation.
    ///////////////////////////////////////////////////////////////////////////

    public static class DummyPainter implements Painter<Component> {
        public void paint(Graphics2D g, Component component, int width, int height) {
            // do nothing.
        }
    }
}
