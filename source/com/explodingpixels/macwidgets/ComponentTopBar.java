package com.explodingpixels.macwidgets;

import java.awt.Dimension;

import com.explodingpixels.painter.GradientWithBorderPainter;

public class ComponentTopBar extends TriAreaComponent {
        
    private WidgetColorScheme colorScheme = new WidgetStandardColorScheme();
    private BottomBarSize size = BottomBarSize.SMALL;
    
    public ComponentTopBar() {
        initialize();
    }
    
    public ComponentTopBar(WidgetColorScheme scheme) {
    	this.colorScheme = scheme;
    	initialize();
    }
    
    private void initialize() {
    	int height = size.getHeight() + 2;
    	this.getComponent().setPreferredSize(new Dimension(-1, height));
        setBackgroundPainter(new GradientWithBorderPainter(
                                                           colorScheme.getTopGradientBorderColor(), colorScheme.getBottomGradientBorderColor(),
                                                           colorScheme.getTopGradientColor(), colorScheme.getBottomGradientColor()));
    }
}
