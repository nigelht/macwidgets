package com.explodingpixels.painter;

import java.awt.Graphics2D;

public class CompoundPainter<T> implements Painter<T> {

    private Painter<T>[] fPainters;

    public CompoundPainter(Painter<T>... painters) {
        fPainters = painters;
    }

    public void paint(Graphics2D graphics, T objectToPaint, int width, int height) {
        for (Painter<T> painter : fPainters) {
            Graphics2D graphicsCopy = (Graphics2D) graphics.create();
            painter.paint(graphicsCopy, objectToPaint, width, height);
            graphicsCopy.dispose();
        }
    }
}
