package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.IAppScrollBarArtworkUtils;
import com.explodingpixels.painter.ImagePainter;
import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.plaf.ButtonsTogetherScrollBarSkin;
import com.explodingpixels.widgets.plaf.ScrollThumbImagePainter;
import com.explodingpixels.widgets.plaf.SkinnableScrollBarUI;

import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import java.awt.*;

/**
 * A factory for iApp style widgets.
 */
public class IAppWidgetFactory {

    private static JComponent SCROLL_PANE_CORNER =
            new ImageBasedJComponent(new ImageIcon(IAppWidgetFactory.class.getResource(
                    "/com/explodingpixels/macwidgets/images/iapp_scrollpane_corner.png")).getImage());

    private IAppWidgetFactory() {
        // utility class - no constructor needed.
    }

    /**
     * Creates an iApp style {@link JScrollPane}, with vertical and horizontal scrollbars shown as
     * needed.
     *
     * @param view the view to wrap inside the {@code JScrollPane}.
     * @return an iApp style {@code JScrollPane}.
     * @see #makeIAppScrollPane
     */
    public static JScrollPane createScrollPaneWithButtonsTogether(Component view) {
        return createScrollPaneWithButtonsTogether(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Creates an iApp style {@link JScrollPane} using the given scroll bar policies.
     *
     * @param view                      the view to wrap inside the {@code JScrollPane}.
     * @param verticalScrollBarPolicy   the vertical scroll bar policy.
     * @param horizontalScrollBarPolicy the horizontal scroll bar policy.
     * @return an iApp style {@code JScrollPane} using the given scroll bar policies.
     * @see #makeIAppScrollPane
     */
    public static JScrollPane createScrollPaneWithButtonsTogether(
            Component view, int verticalScrollBarPolicy, int horizontalScrollBarPolicy) {
        JScrollPane retVal = new JScrollPane(view, verticalScrollBarPolicy, horizontalScrollBarPolicy);
        makeIAppScrollPane(retVal);
        return retVal;
    }

    /**
     * Makes the given {@link JScrollPane} an iApp style scroll pane that looks like this:
     * <br>
     * <img src="../../../../graphics/iAppScrollbars.png">
     *
     * @param scrollPane the {@code JScrollPane} to make an iApp style scroll pane.
     * @return an iApp style scroll pane.
     */
    public static JScrollPane makeIAppScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        installButtonsTogetherUIDelegates(scrollPane);
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, SCROLL_PANE_CORNER);
        // TODO listen for scrollBar.setUI calls in order to reinstall UI delegates.
        return scrollPane;
    }

    // ScrollBarUI creation methods ///////////////////////////////////////////////////////////////

    private static void installButtonsTogetherUIDelegates(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(createVerticalScrollBarButtonsTogetherUI());
        scrollPane.getHorizontalScrollBar().setUI(createHorizontalScrollBarButtonsTogetherUI());
    }

    public static ScrollBarUI createVerticalScrollBarButtonsTogetherUI() {
        JComponent topCap = new ImageBasedJComponent(IAppScrollBarArtworkUtils.getScrollBarTopCap().getImage());

        Dimension minimumThumbSize = IAppScrollBarArtworkUtils.getVerticalScrollBarMinimumSize();
        AbstractButton decrementButton = IAppScrollBarArtworkUtils.createVerticalTogetherDecrementButton();
        AbstractButton incrementButton = IAppScrollBarArtworkUtils.createVerticalTogetherIncrementButton();
        Painter<Component> trackPainter = new ImagePainter(IAppScrollBarArtworkUtils.getVerticalTrack().getImage());
        ScrollThumbImagePainter scrollerThumb = IAppScrollBarArtworkUtils.createVerticalScrollerThumb();
        int topCapRecess = IAppScrollBarArtworkUtils.getScrollBarTopCapRecess();
        int decrementButtonRecess = IAppScrollBarArtworkUtils.getDecrementButtonRecess();
        Dimension preferredSize = new Dimension(decrementButton.getPreferredSize().width, 100);

        ButtonsTogetherScrollBarSkin skin = new ButtonsTogetherScrollBarSkin(
                topCap, decrementButton, incrementButton, trackPainter, scrollerThumb,
                topCapRecess, decrementButtonRecess, minimumThumbSize, preferredSize);

        return SkinnableScrollBarUI.createVerticalScrollBarUI(skin);
    }

    public static ScrollBarUI createHorizontalScrollBarButtonsTogetherUI() {
        JComponent topCap = new ImageBasedJComponent(IAppScrollBarArtworkUtils.getScrollBarLeftCap().getImage());

        Dimension minimumThumbSize = IAppScrollBarArtworkUtils.getHorizontalScrollBarMinimumSize();
        AbstractButton decrementButton = IAppScrollBarArtworkUtils.createHorizontalTogetherDecrementButton();
        AbstractButton incrementButton = IAppScrollBarArtworkUtils.createHorizontalTogetherIncrementButton();
        Painter<Component> trackPainter = new ImagePainter(IAppScrollBarArtworkUtils.getHorizontalTrack().getImage());
        ScrollThumbImagePainter scrollerThumb = IAppScrollBarArtworkUtils.createHorizontalScrollerThumb();
        int topCapRecess = IAppScrollBarArtworkUtils.getScrollBarTopCapRecess();
        int decrementButtonRecess = IAppScrollBarArtworkUtils.getDecrementButtonRecess();
        Dimension preferredSize = new Dimension(100, decrementButton.getPreferredSize().height);

        ButtonsTogetherScrollBarSkin skin = new ButtonsTogetherScrollBarSkin(
                topCap, decrementButton, incrementButton, trackPainter, scrollerThumb,
                topCapRecess, decrementButtonRecess, minimumThumbSize, preferredSize);

        return SkinnableScrollBarUI.createHorizontalScrollBarUI(skin);
    }

    // Custom JComponent that uses a painter. /////////////////////////////////////////////////////

    // TODO replace with EPPanel.

    private static class ImageBasedJComponent extends JComponent {

        private final ImagePainter fPainter;

        private ImageBasedJComponent(Image image) {
            fPainter = new ImagePainter(image);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            fPainter.paint(graphics, this, getWidth(), getHeight());
            graphics.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(fPainter.getImage().getWidth(null),
                    fPainter.getImage().getHeight(null));
        }
    }
}
