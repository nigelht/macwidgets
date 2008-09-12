package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.EmphasizedLabelUI;

import java.awt.Color;

public class MacColorUtils {

    public static Color EMPTY_COLOR = new Color(0,0,0,0);

    ///////////////////////////////////////////////////////////////////////////
    // Leopard colors.
    ///////////////////////////////////////////////////////////////////////////

    public static Color LEOPARD_BORDER_COLOR = new Color(0x555555);

    public static Color SOURCE_LIST_FOCUSED_BACKGROUND_COLOR =
            new Color(0xd6dde5);

    public static Color SOURCE_LIST_UNFOCUSED_BACKGROUND_COLOR =
            new Color(0xe8e8e8);

    ///////////////////////////////////////////////////////////////////////////
    // Leopard list colors
    ///////////////////////////////////////////////////////////////////////////

    public static final Color MAC_FOCUSED_SOURCE_LIST_BACKGROUND_COLOR =
            new Color(0xd6dde5);

    public static final Color MAC_SOURCE_LIST_BACKGROUND_COLOR =
            new Color(0xe8e8e8);

    public static final Color MAC_SOURCE_LIST_CATEGORY_FONT_COLOR =
            new Color(0x606e80);

    public static final Color MAC_SOURCE_LIST_CATEGORY_FONT_SHADOW_COLOR =
            EmphasizedLabelUI.DEFAULT_EMPHASIS_COLOR;

    public static final Color MAC_SOURCE_LIST_ITEM_FONT_COLOR =
            Color.BLACK;

    public static final Color MAC_SOURCE_LIST_ITEM_FONT_SHADOW_COLOR =
            new Color(0,0,0,0);

    public static final Color MAC_SOURCE_LIST_SELECTED_ITEM_FONT_COLOR =
            Color.WHITE;

    public static final Color MAC_SOURCE_LIST_SELECTED_ITEM_FONT_SHADOW_COLOR =
            new Color(0,0,0,110);

    ///////////////////////////////////////////////////////////////////////////
    // OS X Group List font colors.
    ///////////////////////////////////////////////////////////////////////////

    public static final Color GROUP_LIST_FONT_COLOR =
            EmphasizedLabelUI.DEFAULT_FOCUSED_FONT_COLOR;

    public static final Color GROUP_LIST_FONT_SHADOW_COLOR =
            EmphasizedLabelUI.DEFAULT_EMPHASIS_COLOR;

    public static final Color GROUP_LIST_SELECTED_ITEM_FONT_COLOR =
            Color.WHITE;

    public static final Color GROUP_LIST_SELECTED_ITEM_FONT_SHADOW_COLOR =
            new Color(0,0,0,110);

    ///////////////////////////////////////////////////////////////////////////
    // OS X unified toolbar colors.
    ///////////////////////////////////////////////////////////////////////////

    public static final Color OS_X_UNIFIED_TOOLBAR_FOCUSED_BOTTOM_COLOR =
            new Color(64, 64, 64);

    public static final Color OS_X_UNIFIED_TOOLBAR_UNFOCUSED_BORDER_COLOR =
            new Color(135, 135, 135);

    ///////////////////////////////////////////////////////////////////////////
    // OS X bottom bar colors
    ///////////////////////////////////////////////////////////////////////////

    public static final Color OS_X_BOTTOM_BAR_ACTIVE_TOP_COLOR =
            new Color(0xbbbbbb);

    public static final Color OS_X_BOTTOM_BAR_ACTIVE_BOTTOM_COLOR =
            new Color(0x969696);

    public static final Color OS_X_BOTTOM_BAR_INACTIVE_TOP_COLOR =
            new Color(0xe3e3e3);

    public static final Color OS_X_BOTTOM_BAR_INACTIVE_BOTTOM_COLOR =
            new Color(0xcfcfcf);

    public static final Color OS_X_BOTTOM_BAR_BORDER_HIGHLIGHT_COLOR =
            new Color(255,255,255,110);

}
