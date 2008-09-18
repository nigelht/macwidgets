package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.PreferencesTabBarButtonUI;
import com.explodingpixels.macwidgets.plaf.UnifiedToolbarButtonUI;
import com.explodingpixels.painter.*;
import com.explodingpixels.swingx.EPButton;
import com.explodingpixels.widgets.PopdownButton;
import com.explodingpixels.widgets.PopupMenuCustomizer;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.net.URL;

public class MacButtonFactory {

    ///////////////////////////////////////////////////////////////////////////
    // Unified toolbar button methods.
    ///////////////////////////////////////////////////////////////////////////

    public static AbstractButton makeUnifiedToolBarButton(AbstractButton button) {
        button.setUI(new UnifiedToolbarButtonUI());
        return button;
    }

    public static AbstractButton makePreferencesTabBarButton(AbstractButton button) {
        button.setUI(new PreferencesTabBarButtonUI());
        return button;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Gradient button methods.
    ///////////////////////////////////////////////////////////////////////////

    static final Color GRADIENT_BUTTON_BORDER_COLOR = new Color(190, 190, 190);

    private static URL GRADIENT_BACKGROUND_URL =
            ComponentBottomBar.class.getResource(
                    "/com/explodingpixels/macwidgets/images/component_status_bar_shiny_background_no_border.png");

    static final ImagePainter GRADIENT_BUTTON_IMAGE_PAINTER =
            new ImagePainter(GRADIENT_BACKGROUND_URL);

    private static final Painter<Component> PRESSED_AND_SELECTED_GRADIENT_PAINTER =
            new CompoundPainter<Component>(GRADIENT_BUTTON_IMAGE_PAINTER,
                    new RectanglePainter(new Color(0, 0, 0, 89)));

    private static final ButtonStatePainter<AbstractButton> GRADIENT_BUTTON_PAINTER =
            new ButtonStatePainter<AbstractButton>(
                    GRADIENT_BUTTON_IMAGE_PAINTER,
                    GRADIENT_BUTTON_IMAGE_PAINTER,
                    PRESSED_AND_SELECTED_GRADIENT_PAINTER,
                    PRESSED_AND_SELECTED_GRADIENT_PAINTER);

    public static JComponent createGradientButton(Icon icon,
                                                  ActionListener actionListener) {
        EPButton button = new EPButton(icon);
        button.addActionListener(actionListener);

        button.setBackgroundPainter(GRADIENT_BUTTON_PAINTER);
        initGradientButton(button);
        button.setPressedIcon(icon);
        return button;
    }

    public static PopdownButton createGradientPopdownButton(
            Icon icon, PopupMenuCustomizer popupMenuCustomizer) {

        PopdownButton popdownButton =
                new PopdownButton(icon, popupMenuCustomizer);
        popdownButton.setBackgroundPainter(GRADIENT_BUTTON_PAINTER);
        initGradientButton(popdownButton.getComponent());
        popdownButton.setPressedIcon(icon);

        return popdownButton;
    }

    private static void initGradientButton(JComponent button) {
        button.setBorder(BorderFactory.createLineBorder(
                GRADIENT_BUTTON_BORDER_COLOR));
        button.setPreferredSize(new Dimension(30, 22));
    }

}
