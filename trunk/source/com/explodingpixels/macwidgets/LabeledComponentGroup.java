package com.explodingpixels.macwidgets;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabeledComponentGroup {

    private JComponent fComponent;

    LabeledComponentGroup(String labelString, JComponent componentGroup) {

         // definte the FormLayout columns and rows.
        FormLayout layout = new FormLayout("p", "fill:p:grow p");
        // create the cell constraints to use in the layout.
        CellConstraints cc = new CellConstraints();
        // create the builder with our panel as the component to be filled.
        PanelBuilder builder = new PanelBuilder(layout, new JPanel());

        builder.add(componentGroup, cc.xy(1,1,"center, center"));
        builder.add(createLabel(labelString), cc.xy(1,2,"center, top"));

        fComponent = builder.getPanel();
    }

    public JComponent getComponent() {
        return fComponent;
    }

    private JLabel createLabel(String labelString) {
        JLabel label = MacWidgetFactory.makeEmphasizedLabel(new JLabel(labelString));
        label.setFont(MacFontUtils.TOOLBAR_LABEL_FONT);
        return label;
    }
}
