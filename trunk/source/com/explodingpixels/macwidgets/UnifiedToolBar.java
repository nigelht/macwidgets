package com.explodingpixels.macwidgets;

import com.jgoodies.forms.factories.Borders;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.jdesktop.swingx.JXPanel;

public class UnifiedToolBar extends TriAreaComponent {

    private boolean fShowLabels;

    // TODO generify TriAreaComponent by adding the capability to insert spacers
    // TODO between comopnents.

    private static final Border PAD_BORDER = Borders.createEmptyBorder("3dlu, 5dlu, 3dlu, 5dlu");

    private static final Border FOCUSED_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,
                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR), PAD_BORDER);

    private static final Border UNFOCUSED_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,
                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR), PAD_BORDER);

    UnifiedToolBar() {
//        super(new CustomPanel());
//        getComponent().setBorder(Borders.createEmptyBorder("3dlu, 5dlu, 3dlu, 5dlu"));
        getComponent().setBorder(FOCUSED_BORDER);
        System.out.println("here");
    }

    private WindowListener createWindowFocusListener() {
        return new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                getComponent().setBorder(FOCUSED_BORDER);
            }
            @Override
            public void windowLostFocus(WindowEvent e) {
                getComponent().setBorder(UNFOCUSED_BORDER);
            }
        };
    }

//    ///////////////////////////////////////////////////////////////////////////
//    // Custom panel to handle switching border based on window foucs.
//    ///////////////////////////////////////////////////////////////////////////
//
//    private static class CustomPanel extends JXPanel {
//
//        public CustomPanel() {
//            setOpaque(false);
//        }
//
//        @Override
//        public Border getBorder() {
//            Window window = SwingUtilities.getWindowAncestor(this);
//            return window != null && window.isFocused()
//                    ? BorderFactory.createMatteBorder(0,0,1,0,
//                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR)
//                    : BorderFactory.createMatteBorder(0,0,1,0,
//                    MacColorUtils.OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR);
//        }
//    }

}
