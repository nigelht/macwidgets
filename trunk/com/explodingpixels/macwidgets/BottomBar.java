package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.TriAreaComponent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.*;

import org.jdesktop.swingx.JXPanel;

public class BottomBar extends TriAreaComponent {

    BottomBar() {
        super(new BottomBarGradientPanel());
        setSize(Size.LARGE);
    }

    public void setSize(Size size) {
        getComponent().setPreferredSize(new Dimension(-1, size.getHeight()));
    }

    ///////////////////////////////////////////////////////////////////////////
    // Custom panel to paint background gradient.
    ///////////////////////////////////////////////////////////////////////////

    private static class BottomBarGradientPanel extends JXPanel {

        public BottomBarGradientPanel() {
            super();
            setBorder(BorderFactory.createMatteBorder(1,0,0,0,
                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();

            Window window = SwingUtilities.getWindowAncestor(this);
            boolean hasFoucs = window != null && window.isFocused();
            
            Color topColor = hasFoucs
                    ? MacColorUtils.OS_X_BOTTOM_BAR_ACTIVE_TOP_COLOR
                    : MacColorUtils.OS_X_BOTTOM_BAR_INACTIVE_TOP_COLOR;
            Color bottomColor = hasFoucs
                    ? MacColorUtils.OS_X_BOTTOM_BAR_ACTIVE_BOTTOM_COLOR
                    : MacColorUtils.OS_X_BOTTOM_BAR_INACTIVE_BOTTOM_COLOR;

            Paint paint = new GradientPaint(0, 0, topColor,
                    0, getHeight(), bottomColor);

            graphics.setPaint(paint);
            graphics.fillRect(0,0,getWidth(),getHeight());

            graphics.dispose();
        }

        @Override
        public Border getBorder() {
            Window window = SwingUtilities.getWindowAncestor(this);
            Border outterBorder = window != null && window.isFocused()
                    ? BorderFactory.createMatteBorder(1,0,0,0,
                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR)
                    : BorderFactory.createMatteBorder(1,0,0,0,
                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR);
            Border innerBorder = BorderFactory.createMatteBorder(1,0,0,0,
                    MacColorUtils.OS_X_BOTTOM_BAR_BORDER_HIGHLIGHT_COLOR);
            return BorderFactory.createCompoundBorder(outterBorder, innerBorder);
        }

    }

    public enum Size {

        SMALL(22), LARGE(32);

        private int fHeight;

        Size(int height) {
            fHeight = height;
        }

        public int getHeight() {
            return fHeight;
        }
    }

}
