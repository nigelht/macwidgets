package com.explodingpixels.macwidgets;

import java.awt.Color;
import java.awt.Component;

import com.explodingpixels.painter.GradientWithBorderPainter;
import com.explodingpixels.painter.Painter;

public class SourceListSeaGlassColorScheme extends
		SourceListStandardColorScheme {

	private static final Painter<Component> ACTIVE_FOCUSED_SELECTION_PAINTER = createSourceListActiveFocusedSelectionPainter();

	@Override
	public Painter<Component> getActiveFocusedSelectedItemPainter() {
		return ACTIVE_FOCUSED_SELECTION_PAINTER;
	}
		
    private static Painter<Component> createSourceListActiveFocusedSelectionPainter() {
        Color topLineColor = new Color(58, 93, 137);
        Color topColor = new Color(106, 144, 182);
        Color bottomColor = new Color(77, 111, 148);
        return new GradientWithBorderPainter(topLineColor, bottomColor, topColor, bottomColor);
    }
}
