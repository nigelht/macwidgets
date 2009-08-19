package com.explodingpixels.widgets;

import com.explodingpixels.util.PlatformUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

public class WindowUtils {

    // TODO get rid of this, as it doesn't work across platforms.
    private static final String FRAME_ACTIVE_PROPERTY = "Frame.active";

    /**
     * Try's to make the given {@link Window} non-opqaue (transparent) across platforms and JREs. This method is not
     * guaranteed to succeed, and will fail silently if the given {@code Window} cannot be made non-opaque.
     * <p/>
     * This method is useful, for example, when creating a HUD style window that is semi-transparent, and thus doesn't
     * want the window background to be drawn.
     *
     * @param window the {@code Window} to make non-opaque.
     */
    public static void makeWindowNonOpaque(Window window) {
        // on the mac, simply setting the window's background color to be fully transparent makes the window non-opaque.
        window.setBackground(new Color(0, 0, 0, 0));
        // on non-mac platforms, try to use the facilities of Java 6 update 10.
        if (!PlatformUtils.isMac()) {
            quietlyTryToMakeWindowNonOqaque(window);
        }
    }

    /**
     * Trys to invoke {@code com.sun.awt.AWTUtilities.setWindowOpaque(window,false)} on the given {@link Window}. This
     * will only work when running with JRE 6 update 10 or higher. This method will silently fail if the method cannot
     * be invoked.
     *
     * @param window the {@code Window} to try and make non-opaque.
     */
    private static void quietlyTryToMakeWindowNonOqaque(Window window) {
        try {
            Class clazz = Class.forName("com.sun.awt.AWTUtilities");
            Method method = clazz.getMethod("setWindowOpaque", java.awt.Window.class, Boolean.TYPE);
            method.invoke(clazz, window, false);
        } catch (Exception e) {
            // silently ignore this exception.
        }
    }

    public static WindowFocusListener createAndInstallRepaintWindowFocusListener(Window window) {

        // create a WindowFocusListener that repaints the window on focus
        // changes.
        WindowFocusListener windowFocusListener = new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
                e.getWindow().repaint();
            }

            public void windowLostFocus(WindowEvent e) {
                e.getWindow().repaint();
            }
        };

        window.addWindowFocusListener(windowFocusListener);

        return windowFocusListener;
    }

    /**
     * {@code true} if the given {@link Component}'s parent {@link Window} is currently active (focused).
     *
     * @param component the {@code Component} to check the parent {@code Window}'s focus for.
     * @return {@code true} if the given {@code Component}'s parent {@code Window} is currently active.
     */
    public static boolean isParentWindowFocused(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        return window != null && window.isFocused();
    }

    // TODO fix this method - doesn't work across platforms.
    public static void installWindowFocusListener(
            WindowFocusListener focusListener, JComponent component) {
        // TODO add null argument checks.
        component.addPropertyChangeListener(FRAME_ACTIVE_PROPERTY,
                createFrameFocusPropertyChangeListener(focusListener, component));
    }

    /**
     * Installs a listener on the given {@link JComponent}'s parent {@link Window} that repaints
     * the given component when the parent window's focused state changes. If the given component
     * does not have a parent at the time this method is called, then an ancestor listener will be
     * installed that installs a window listener when the components parent changes.
     *
     * @param component the {@code JComponent} to add the repaint focus listener to.
     * @return a {@link Disposer} than uninstalls listeners installed by this method.
     */
    public static Disposer installJComponentRepainterOnWindowFocusChanged(JComponent component) {
        // TODO check to see if the component already has an ancestor.
        WindowListener windowListener = createWindowListener(component);
        AncestorListener ancestorListener = createAncestorListener(component, windowListener);
        component.addAncestorListener(ancestorListener);
        return new Disposer(component, ancestorListener, windowListener);
    }

    private static AncestorListener createAncestorListener(
            final JComponent component, final WindowListener windowListener) {
        return new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) {
                Window window = SwingUtilities.getWindowAncestor(component);
                if (window != null) {
                    window.removeWindowListener(windowListener);
                    window.addWindowListener(windowListener);
                }
            }

            public void ancestorRemoved(AncestorEvent event) {
                // no implementation.
            }

            public void ancestorMoved(AncestorEvent event) {
                // no implementation.
            }
        };
    }

    private static WindowListener createWindowListener(final JComponent component) {
        return new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                component.repaint();
            }

            public void windowDeactivated(WindowEvent e) {
                component.repaint();
            }
        };
    }

    private static PropertyChangeListener createFrameFocusPropertyChangeListener(
            final WindowFocusListener focusListener, final JComponent component) {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                Window window = SwingUtilities.getWindowAncestor(component);
                // use the client property that initiated this this
                // property change event, as the actual window's
                // isFocused method may not return the correct value
                // because the window is in transition.
                boolean hasFocus = (Boolean) component.getClientProperty(FRAME_ACTIVE_PROPERTY);
                if (hasFocus) {
                    focusListener.windowGainedFocus(
                            new WindowEvent(window, WindowEvent.WINDOW_GAINED_FOCUS));
                } else {
                    focusListener.windowLostFocus(
                            new WindowEvent(window, WindowEvent.WINDOW_LOST_FOCUS));
                }
            }
        };
    }

    /**
     * A helper class that uninstalls listeners installed by
     * {@link WindowUtils#installJComponentRepainterOnWindowFocusChanged(javax.swing.JComponent)}. The
     * {@link com.explodingpixels.widgets.WindowUtils.Disposer#dispose()} method should be called when object that
     * installed the listener is no longer needed.
     */
    public static class Disposer {

        private final JComponent fComponent;
        private final AncestorListener fAncestorListener;
        private final WindowListener fWindowListener;

        private Disposer(JComponent component, AncestorListener ancestorListener, WindowListener windowListener) {
            fComponent = component;
            fAncestorListener = ancestorListener;
            fWindowListener = windowListener;
        }

        /**
         * Should be called when the {@link JComponent} that the
         * {@link WindowUtils#installJComponentRepainterOnWindowFocusChanged(javax.swing.JComponent)} method was called
         * on is no longer needed.
         */
        public void dispose() {
            // first, remove the AncestorListener that was installed in installJComponentRepainterOnWindowFocusChanged.
            fComponent.removeAncestorListener(fAncestorListener);
            // next, remove the WindowListener that was installed on the parent Window.
            Window window = SwingUtilities.getWindowAncestor(fComponent);
            if (window != null) {
                window.removeWindowListener(fWindowListener);
            }
        }
    }
}
