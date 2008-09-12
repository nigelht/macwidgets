package com.explodingpixels.macwidgets;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.awt.Window;

public class PreferencesTabBar {

//    private List<AbstractButton> fTabs = new ArrayList<AbstractButton>();

    private TriAreaComponent fTriAreaComponent = new TriAreaComponent();

    private ButtonGroup fButtonGroup = new ButtonGroup();

    public PreferencesTabBar() {
        Border b = BorderFactory.createEmptyBorder(0,4,0,4);
        fTriAreaComponent.getComponent().setBorder(b);
    }

    public void addTab(String title, Icon icon, ActionListener listener) {
        AbstractButton button = MacButtonFactory.makePreferencesTabBarButton(
                new JToggleButton(title, icon));
        fButtonGroup.add(button);
        button.addActionListener(listener);

        fTriAreaComponent.addComponentToLeft(button);
    }

    public void installWindowDraggerOnWindow(Window window) {
        fTriAreaComponent.installWindowDraggerOnWindow(window);
    }

    public JComponent getComponent() {
        return fTriAreaComponent.getComponent();
    }

}
