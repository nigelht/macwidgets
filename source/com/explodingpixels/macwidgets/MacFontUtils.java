package com.explodingpixels.macwidgets;

import javax.swing.UIManager;
import java.awt.Font;

public class MacFontUtils {

    public static final Font ITUNES_FONT =
            UIManager.getFont("Table.font").deriveFont(11.0f);

    public static final Font ITUNES_TABLE_HEADER_FONT =
            UIManager.getFont("Table.font").deriveFont(Font.BOLD, 11.0f);

    public static final Font TOOLBAR_LABEL_FONT =
            UIManager.getFont("Label.font").deriveFont(11.0f);

    public static final Font SOURCE_LIST_CATEGORY_FONT =
            UIManager.getFont("Label.font").deriveFont(Font.BOLD, 11.0f);

    public static final Font SOURCE_LIST_ITEM_FONT =
            UIManager.getFont("Label.font").deriveFont(11.0f);

    public static final Font SOURCE_LIST_ITEM_SELECTED_FONT =
            SOURCE_LIST_ITEM_FONT.deriveFont(Font.BOLD);
}
