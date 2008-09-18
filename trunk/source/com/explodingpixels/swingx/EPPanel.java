package com.explodingpixels.swingx;

import com.explodingpixels.painter.Painter;

import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EPPanel extends JPanel {

    private Painter<Component> fBackgroundPainter;

    public EPPanel() {
        super();
        init();
    }

    private void init() {
        setOpaque(false);
    }

    public void setBackgroundPainter(Painter<Component> painter) {
        fBackgroundPainter = painter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (fBackgroundPainter != null) {
            Graphics2D graphics2D = (Graphics2D) g.create();
            fBackgroundPainter.paint(graphics2D, this, getWidth(), getHeight());
            graphics2D.dispose();
        }

        super.paintComponent(g);
    }


}
