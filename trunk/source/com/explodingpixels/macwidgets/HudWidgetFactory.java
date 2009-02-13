package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.*;

import javax.swing.*;

/**
 * A factory for creating HUD style widgets. These widgets should be added to a
 * {@link com.explodingpixels.macwidgets.HudWindow}.
 */
public class HudWidgetFactory {

    private HudWidgetFactory() {
        // utility class - no constructor needed.
    }

    /**
     * Creates a Heads Up Display (HUD) style label, similar to that seen in various iApps (e.g.
     * iPhoto).
     * <br/><br/>
     * <img src="../../../../graphics/HUDLabelUI.png">
     * @param labelText the text of the label.
     * @return the HUD style label.
     * @see HudLabelUI
     */
    public static JLabel createHudLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setUI(new HudLabelUI());
        return label;
    }

    /**
     * Creates a Heads Up Display (HUD) style button, similar to that seen in various iApps (e.g.
     * iPhoto).
     * <br/><br/>
     * <img src="../../../../graphics/HUDButtonUI.png">
     * @param buttonText the text of the button.
     * @return the HUD style button.
     * @see HudButtonUI
     */
    public static JButton createHudButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setUI(new HudButtonUI());
        return button;
    }

    /**
     * Creates a Heads Up Display (HUD) style check box, similar to that seen in various iApps (e.g.
     * iPhoto).
     * <br/><br/>
     * <img src="../../../../graphics/HUDCheckBoxUI.png">
     * @param checkBoxText the text of the check box.
     * @return the HUD style check box.
     * @see HudCheckBoxUI
     */
    public static JCheckBox createHudCheckBox(String checkBoxText) {
        JCheckBox checkBox = new JCheckBox(checkBoxText);
        checkBox.setUI(new HudCheckBoxUI());
        return checkBox;
    }

    /**
     * Creates a Heads Up Display (HUD) style combo box, similar to that seen in various iApps (e.g.
     * iPhoto).
     * <br/><br/>
     * <img src="../../../../graphics/HUDComboBoxUI.png">
     * @param model the model containing the combo box items.
     * @return the HUD style combo box.
     * @see HudComboBoxUI
     */
    public static JComboBox createHudCheckBox(ComboBoxModel model) {
        JComboBox comboBox = new JComboBox(model);
        comboBox.setUI(new HudComboBoxUI());
        return comboBox;
    }

    /**
     * Creates a Heads Up Display (HUD) style text field, similar to that seen in various iApps (e.g.
     * iPhoto).
     * <br/><br/>
     * <img src="../../../../graphics/HUDTextFieldUI.png">
     * @param text the initial text in the text field.
     * @return the HUD style text field.
     * @see HudTextFieldUI
     */
    public static JTextField createHudTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setUI(new HudTextFieldUI());
        return textField;
    }

}
