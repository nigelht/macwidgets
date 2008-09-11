package com.explodingpixels.macwidgets;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;

import javax.swing.JLabel;
import java.awt.Component;

class SourceListItemCellRenderer {

    private PanelBuilder fBuilder;

    private JLabel fSelectedLabel =
            MacWidgetFactory.makeEmphasizedLabel(new JLabel(),
                    MacColorUtils.MAC_SOURCE_LIST_SELECTED_ITEM_FONT_COLOR,
                    MacColorUtils.MAC_SOURCE_LIST_SELECTED_ITEM_FONT_COLOR,
                    MacColorUtils.MAC_SOURCE_LIST_SELECTED_ITEM_FONT_SHADOW_COLOR);

    private JLabel fUnselectedLabel =
            MacWidgetFactory.makeEmphasizedLabel(new JLabel(),
                    MacColorUtils.MAC_SOURCE_LIST_ITEM_FONT_COLOR,
                    MacColorUtils.MAC_SOURCE_LIST_ITEM_FONT_COLOR,
                    MacColorUtils.MAC_SOURCE_LIST_ITEM_FONT_SHADOW_COLOR);

    private SourceListCountBadgeRenderer fCountRenderer =
            new SourceListCountBadgeRenderer();

    SourceListItemCellRenderer() {

        fSelectedLabel.setFont(MacFontUtils.SOURCE_LIST_ITEM_SELECTED_FONT);
        fUnselectedLabel.setFont(MacFontUtils.SOURCE_LIST_ITEM_FONT);

         // definte the FormLayout columns and rows.
        FormLayout layout = new FormLayout("fill:0px:grow 5px p 5px","3px fill:p:grow 3px");
        // create the builders with our panels as the component to be filled.
        fBuilder = new PanelBuilder(layout);
        fBuilder.getPanel().setOpaque(false);

    }

    public Component getCellRendererComponent(
            SourceListItem item, boolean selected) {

        JLabel label = selected ? fSelectedLabel : fUnselectedLabel;

        label.setText(item.getText());
        label.setIcon(item.getIcon());

        fBuilder.getPanel().removeAll();
        CellConstraints cc = new CellConstraints();
        fBuilder.add(label, cc.xywh(1,1,1,3));
        fBuilder.add(fCountRenderer.getComponent(), cc.xy(3,2, "center, fill"));

        if (item.hasCounterValue()) {
            fCountRenderer.getComponent().setVisible(true);
            fCountRenderer.setState(item.getCounterValue(),selected);
        } else {
            fCountRenderer.getComponent().setVisible(false);
        }

        return fBuilder.getPanel();
    }

}
