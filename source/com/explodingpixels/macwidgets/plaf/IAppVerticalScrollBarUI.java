package com.explodingpixels.macwidgets.plaf;

import com.explodingpixels.painter.ImagePainter;
import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.ImageBasedJComponent;
import com.explodingpixels.widgets.plaf.*;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

/**
 * Creates an iApp style vertical scroll bar.
 * <br>
 * <img src="../../../../../graphics/iAppVerticalScrollBar.png">
 */
public class IAppVerticalScrollBarUI extends SkinnableScrollBarUI {

    public IAppVerticalScrollBarUI() {
        super(createSkin(), ScrollBarOrientation.VERTICAL);

    }

    private static ScrollBarSkin createSkin() {
        Image topCapImage = IAppScrollBarArtworkUtils.getScrollBarTopCap().getImage();
        JComponent topCap = new ImageBasedJComponent(topCapImage);

        Dimension minimumThumbSize = IAppScrollBarArtworkUtils.getVerticalScrollBarMinimumSize();
        AbstractButton decrementButton = IAppScrollBarArtworkUtils.createVerticalTogetherDecrementButton();
        AbstractButton incrementButton = IAppScrollBarArtworkUtils.createVerticalTogetherIncrementButton();
        Painter<Component> trackPainter = new ImagePainter(IAppScrollBarArtworkUtils.getVerticalTrack().getImage());
        ScrollThumbImagePainter scrollerThumb = IAppScrollBarArtworkUtils.createVerticalScrollerThumb();
        int topCapRecess = IAppScrollBarArtworkUtils.getScrollBarTopCapRecess();
        int decrementButtonRecess = IAppScrollBarArtworkUtils.getDecrementButtonRecess();
        Dimension preferredSize = new Dimension(decrementButton.getPreferredSize().width, 100);

        return new ButtonsTogetherScrollBarSkin(
                topCap, decrementButton, incrementButton, trackPainter, scrollerThumb,
                topCapRecess, decrementButtonRecess, minimumThumbSize, preferredSize);
    }
}
