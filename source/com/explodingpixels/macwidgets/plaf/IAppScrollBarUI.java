package com.explodingpixels.macwidgets.plaf;

import com.explodingpixels.painter.ImagePainter;
import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.ImageBasedJComponent;
import com.explodingpixels.widgets.plaf.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 * Creates an iApp style scroll bar, either horizontal or vertical based on
 * {@link javax.swing.JScrollBar#getOrientation()}.
 * <br>
 * <img src="../../../../../graphics/iAppHorizontalScrollBar.png">
 * <img src="../../../../../graphics/iAppVerticalScrollBar.png">
 */
public class IAppScrollBarUI extends SkinnableScrollBarUI {

    public IAppScrollBarUI() {
        super(createScrollBarSkinProvider());
    }

    public static ComponentUI createUI(JComponent c)    {
        return new IAppScrollBarUI();
    }

    private static ScrollBarSkinProvider createScrollBarSkinProvider() {
        return new ScrollBarSkinProvider() {
            public ScrollBarSkin provideSkin(ScrollBarOrientation orientation) {
                return orientation == ScrollBarOrientation.HORIZONTAL
                        ? createHorizontalSkin() : createVerticalSkin();
            }
        };
    }

    private static ScrollBarSkin createHorizontalSkin() {
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

    private static ScrollBarSkin createVerticalSkin() {
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
