package com.explodingpixels.painter;

import org.jdesktop.swingx.painter.Painter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

public class RectanglePainter implements Painter<Component> {

    private final Color fFillColor;

    public RectanglePainter(Color fillColor) {
        fFillColor = fillColor;
    }

    public void paint(Graphics2D g, Component object, int width, int height) {
        g.setColor(fFillColor);
        g.fillRect(0, 0, width, height);
    }

}
