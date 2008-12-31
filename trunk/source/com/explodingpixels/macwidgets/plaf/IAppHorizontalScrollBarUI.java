package com.explodingpixels.macwidgets.plaf;

import com.explodingpixels.painter.ImagePainter;
import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.ImageBasedJComponent;
import com.explodingpixels.widgets.plaf.*;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Creates an iApp style horizontal scroll bar.
 * <br>
 * <img src="../../../../../graphics/iAppHorizontalScrollBar.png">
 */
public class IAppHorizontalScrollBarUI extends SkinnableScrollBarUI {

    public IAppHorizontalScrollBarUI() {
        super(createSkin(), ScrollBarOrientation.HORIZONTAL);
    }

    private static ScrollBarSkin createSkin() {
        JComponent topCap = new ImageBasedJComponent(IAppScrollBarArtworkUtils.getScrollBarLeftCap().getImage());

        Dimension minimumThumbSize = IAppScrollBarArtworkUtils.getHorizontalScrollBarMinimumSize();
        AbstractButton decrementButton = IAppScrollBarArtworkUtils.createHorizontalTogetherDecrementButton();
        AbstractButton incrementButton = IAppScrollBarArtworkUtils.createHorizontalTogetherIncrementButton();
        Painter<Component> trackPainter = new ImagePainter(IAppScrollBarArtworkUtils.getHorizontalTrack().getImage());
        ScrollThumbImagePainter scrollerThumb = IAppScrollBarArtworkUtils.createHorizontalScrollerThumb();
        int topCapRecess = IAppScrollBarArtworkUtils.getScrollBarTopCapRecess();
        int decrementButtonRecess = IAppScrollBarArtworkUtils.getDecrementButtonRecess();
        Dimension preferredSize = new Dimension(100, decrementButton.getPreferredSize().height);

        return new ButtonsTogetherScrollBarSkin(
                topCap, decrementButton, incrementButton, trackPainter, scrollerThumb,
                topCapRecess, decrementButtonRecess, minimumThumbSize, preferredSize);
    }
}
