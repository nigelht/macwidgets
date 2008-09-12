package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.EmphasizedLabelUI;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

class SourceListCategoryTreeCellRenderer {

    private JLabel fLabel;

    SourceListCategoryTreeCellRenderer() {
        this(MacColorUtils.MAC_SOURCE_LIST_CATEGORY_FONT_COLOR,
                MacColorUtils.MAC_SOURCE_LIST_CATEGORY_FONT_COLOR,
                EmphasizedLabelUI.DEFAULT_EMPHASIS_COLOR);
    }

    SourceListCategoryTreeCellRenderer(Color focusedTextColor,
                                       Color unfocusedTextColor,
                                       Color textShadowColor) {
        fLabel = MacWidgetFactory.makeEmphasizedLabel(new JLabel(),
                focusedTextColor, unfocusedTextColor, textShadowColor);
    }

    public Component getCellRendererComponent(SourceListCategory category) {

        fLabel.setText(category.getText().toUpperCase());
        fLabel.setFont(MacFontUtils.SOURCE_LIST_CATEGORY_FONT);

        return fLabel;
    }
}
