package com.explodingpixels.widgets.plaf;

import com.explodingpixels.painter.Painter;
import com.explodingpixels.widgets.TabCloseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EPTabbedPaneUI extends BasicTabbedPaneUI {

    public static final String TAB_CLOSE_LISTENER_KEY = "TabbedPane.closeListener";
    public static final String CLOSE_BUTTON_LOCATION_KEY = "TabbedPane.closeButtonLocation";
    public static final Object CLOSE_BUTTON_LOCATION_VALUE_LEFT = EPTabPainter.CloseButtonLocation.LEFT;
    public static final Object CLOSE_BUTTON_LOCATION_VALUE_RIGHT = EPTabPainter.CloseButtonLocation.RIGHT;

    private EPTabPainter fTabPainter = new EPTabPainter();
    private Painter<Component> fContentBorderTopEdgeBackgroundPainter = createContentBorderTopEdgeBackgroundPainter();
    private boolean fPaintFullContentBorder = true;
    private int fCurrentDefaultTabWidth = DEFAULT_TAB_WIDTH;
    private int fMouseOverCloseButtonTabIndex = NO_TAB;
    private int fMousePressedCloseButtonTabIndex = NO_TAB;
    private TabCloseListener fTabCloseListener;
    private Timer fTabCloseTimer = new Timer(10, null);
    private Map<Component, Integer> fTabWidths = new HashMap<Component, Integer>();

    private static final Insets FULL_CONTENT_BORDER_INSETS = new Insets(6, 0, 0, 0);
    private static final Insets HAIRLINE_BORDER_INSETS = new Insets(2, 0, 0, 0);

    private static final int DEFAULT_TAB_WIDTH = 100;
    private static final int NO_TAB = -1;
    private static final int SMALLEST_TAB_WIDTH_BEFORE_CLOSING = 25;
    private static final int TAB_ANIMATION_DELTA = 7;

    @Override
    protected void installDefaults() {
        super.installDefaults();

        Font oldFont = tabPane.getFont();
        tabPane.setFont(oldFont.deriveFont(oldFont.getSize() - 2.0f));
        tabPane.setBorder(BorderFactory.createEmptyBorder());

        tabInsets = new Insets(2, 10, 2, 10);
        selectedTabPadInsets = new Insets(2, 0, 2, 0);

        doExtractTabCloseProperty();
        doExtractCloseButtonLocationProperty();
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        tabPane.addMouseMotionListener(createCloseButtonMouseMotionListener());
        tabPane.addMouseListener(createCloseButtonMouseListener());
        tabPane.addContainerListener(createContainerListener());
        tabPane.addPropertyChangeListener(TAB_CLOSE_LISTENER_KEY, createTabCloseListenerPropertyChangeListener());
        tabPane.addPropertyChangeListener(CLOSE_BUTTON_LOCATION_KEY, createCloseButtonLocationPropertyChangeListener());
    }

    private MouseMotionListener createCloseButtonMouseMotionListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                doMouseMoved(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                doMousePressed(e.getPoint());
                doMouseMoved(e.getPoint());
            }
        };
    }

    private void doMouseMoved(Point point) {
        int tabIndex = tabForCoordinate(tabPane, point.x, point.y);
        int oldMouseOverCloseButtonTabIndex = fMouseOverCloseButtonTabIndex;
        if (isTabIndexValid(tabIndex)) {
            Rectangle tabBounds = getTabBounds(tabPane, tabIndex);
            fMouseOverCloseButtonTabIndex = fTabPainter.isPointOverCloseButton(tabBounds, point) ? tabIndex : NO_TAB;
            repaintTab(fMouseOverCloseButtonTabIndex);
        }
        repaintTab(oldMouseOverCloseButtonTabIndex);
    }

    private MouseListener createCloseButtonMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                int oldMouseOverCloseButtonTabIndex = fMouseOverCloseButtonTabIndex;
                fMouseOverCloseButtonTabIndex = NO_TAB;
                repaintTab(oldMouseOverCloseButtonTabIndex);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                doMousePressed(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                closeTabUsingAnimationIfValid(fMousePressedCloseButtonTabIndex);
                int oldMousePressedOverCloseButtonTabIndex = fMouseOverCloseButtonTabIndex;
                fMousePressedCloseButtonTabIndex = NO_TAB;
                repaintTab(oldMousePressedOverCloseButtonTabIndex);
            }
        };
    }

    private void doMousePressed(Point point) {
        int tabIndex = tabForCoordinate(tabPane, point.x, point.y);
        int oldMousePressedCloseButtonIndex = fMousePressedCloseButtonTabIndex;
        if (isTabIndexValid(tabIndex)) {
            Rectangle tabBounds = getTabBounds(tabPane, tabIndex);
            fMousePressedCloseButtonTabIndex = fTabPainter.isPointOverCloseButton(tabBounds, point) ? tabIndex : NO_TAB;
            repaintTab(fMousePressedCloseButtonTabIndex);
        }
        repaintTab(oldMousePressedCloseButtonIndex);
    }

    private ContainerListener createContainerListener() {
        return new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
                Component componentAdded = e.getChild();
                fTabWidths.put(componentAdded, SMALLEST_TAB_WIDTH_BEFORE_CLOSING);
                animateTabBeingAdded(componentAdded);
            }

            public void componentRemoved(ContainerEvent e) {
                fTabWidths.remove(e.getChild());
            }
        };
    }

    private PropertyChangeListener createTabCloseListenerPropertyChangeListener() {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                doExtractTabCloseProperty();
            }
        };
    }

    private void doExtractTabCloseProperty() {
        Object closeListenerValue = tabPane.getClientProperty(TAB_CLOSE_LISTENER_KEY);
        if (closeListenerValue instanceof TabCloseListener) {
            fTabCloseListener = (TabCloseListener) closeListenerValue;
        }
    }

    private PropertyChangeListener createCloseButtonLocationPropertyChangeListener() {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                doExtractCloseButtonLocationProperty();
            }
        };
    }

    private void doExtractCloseButtonLocationProperty() {
        Object closeButtonLocationValue = tabPane.getClientProperty(CLOSE_BUTTON_LOCATION_KEY);
        if (closeButtonLocationValue instanceof EPTabPainter.CloseButtonLocation) {
            setCloseButtonLocation((EPTabPainter.CloseButtonLocation) closeButtonLocationValue);
        }
    }

    @Override
    protected LayoutManager createLayoutManager() {
        return new CustomLayoutManager();
    }

    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return fPaintFullContentBorder ? FULL_CONTENT_BORDER_INSETS : HAIRLINE_BORDER_INSETS;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
        paintContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex());
    }

    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
        Rectangle tabRect = rects[tabIndex];
        boolean isSelected = tabIndex == tabPane.getSelectedIndex();

        String title = tabPane.getTitleAt(tabIndex);
        Icon icon = getIconForTab(tabIndex);

        Graphics2D graphics = (Graphics2D) g;
        boolean isMouseOverCloseButton = fMouseOverCloseButtonTabIndex == tabIndex;
        boolean isMousePressedOverCloseButton = fMousePressedCloseButtonTabIndex == tabIndex;
        fTabPainter.paintTab(graphics, tabPane, tabRect, title, icon, isSelected, isMouseOverCloseButton,
                isMousePressedOverCloseButton);
    }

    @Override
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int width, int height) {
        Graphics2D graphics = (Graphics2D) g;

        graphics.translate(x, y);
        int borderHeight = getContentBorderInsets(tabPane.getTabPlacement()).top;
        fContentBorderTopEdgeBackgroundPainter.paint(graphics, tabPane, width, borderHeight);
        graphics.translate(-x, -y);

        if (tabPane.getSelectedIndex() >= 0) {
            graphics.setColor(Color.WHITE);
            Rectangle boundsOfSelectedTab = getTabBounds(tabPane, tabPane.getSelectedIndex());
            graphics.drawLine(boundsOfSelectedTab.x, y, boundsOfSelectedTab.x + boundsOfSelectedTab.width, y);
        }
    }

    private Painter<Component> createContentBorderTopEdgeBackgroundPainter() {
        return new Painter<Component>() {
            public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {
                Paint paint = new GradientPaint(0, 0, Color.WHITE, 0, height - 1, new Color(0xf8f8f8));
                graphics.setPaint(paint);
                graphics.fillRect(0, 0, width, height - 1);
                graphics.setColor(EPTabPainter.SELECTED_BORDER_COLOR);
                graphics.drawLine(0, 0, width, 0);
                graphics.setColor(new Color(0x999999));
                // TODO figure out why we need to subtract off another extra pixel here -- doesn't make sense.
                graphics.drawLine(0, height - 2, width, height - 2);
            }
        };
    }

    @Override
    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex,
                                              int x, int y, int w, int h) {
        // do nothing.
    }

    @Override
    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex,
                                               int x, int y, int w, int h) {
        // do nothing.
    }

    @Override
    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex,
                                                int x, int y, int w, int h) {
        // do nothing.
    }

    @Override
    protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    @Override
    protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        Component tabComponent = tabPane.getComponent(tabIndex);
        return fTabWidths.get(tabComponent);
    }

    // Public API methods. ////////////////////////////////////////////////////////////////////////////////////////////

    public void setPaintsFullContentBorder(boolean paintsFullContentBorder) {
        fPaintFullContentBorder = paintsFullContentBorder;
        tabPane.repaint();
    }

    public void setCloseButtonLocation(EPTabPainter.CloseButtonLocation closeButtonLocation) {
        fTabPainter.setCloseButtonLocation(closeButtonLocation);
    }

    // Helper methods. ////////////////////////////////////////////////////////////////////////////

    private void repaintTab(int tabIndex) {
        if (isTabIndexValid(tabIndex)) {
            Rectangle tabBounds = getTabBounds(tabPane, tabIndex);
            tabPane.repaint(tabBounds);
        }
    }

    private boolean isTabIndexValid(int tabIndex) {
        return tabIndex >= 0 && tabIndex < tabPane.getTabCount();
    }

    private void animateTabBeingAdded(Component tabComponent) {
        fTabCloseTimer.addActionListener(createTabAddedAnimation(tabComponent));
        fTabCloseTimer.start();
    }

    private void closeTabUsingAnimationIfValid(int tabIndex) {
        if (isTabIndexValid(tabIndex)) {
            Component tabComponentToClose = tabPane.getComponent(tabIndex);
            fTabCloseTimer.addActionListener(createTabRemovedAnimation(tabComponentToClose));
            fTabCloseTimer.start();
        }
    }

    private void closeTab(int tabIndex) {
        assert isTabIndexValid(tabIndex) : "The tab index should be valid.";

        if (fTabCloseListener != null && fTabCloseListener.tabAboutToBeClosed(tabIndex)) {
            String title = tabPane.getTitleAt(tabIndex);
            Component component = tabPane.getComponentAt(tabIndex);
            tabPane.removeTabAt(tabIndex);
            fTabCloseListener.tabClosed(title, component);
        }
    }

    // Tab animation helper methods. //////////////////////////////////////////////////////////////

    private ActionListener createTabAddedAnimation(final Component tabComponentAdded) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int currentTabWidth = fTabWidths.get(tabComponentAdded);
                int newTabWidth = Math.min(currentTabWidth + TAB_ANIMATION_DELTA, fCurrentDefaultTabWidth);
                fTabWidths.put(tabComponentAdded, newTabWidth);
                if (newTabWidth == fCurrentDefaultTabWidth) {
                    animationFinished(this);
                }
                tabPane.doLayout();
                tabPane.repaint();
            }
        };
    }

    private ActionListener createTabRemovedAnimation(final Component tabComponentToClose) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int currentTabWidth = fTabWidths.get(tabComponentToClose);
                int newTabWidth = Math.max(currentTabWidth - TAB_ANIMATION_DELTA, SMALLEST_TAB_WIDTH_BEFORE_CLOSING);
                fTabWidths.put(tabComponentToClose, newTabWidth);
                if (newTabWidth == SMALLEST_TAB_WIDTH_BEFORE_CLOSING) {
                    animationFinished(this);
                    int tabIndex = tabPane.indexOfComponent(tabComponentToClose);
                    closeTab(tabIndex);
                }
                tabPane.doLayout();
                tabPane.repaint();
            }
        };
    }

    private void animationFinished(ActionListener actionListenerToRemove) {
        fTabCloseTimer.removeActionListener(actionListenerToRemove);
        if (fTabCloseTimer.getActionListeners().length == 0) {
            fTabCloseTimer.stop();
        }
    }

    // CustomLayoutManager implementation. ////////////////////////////////////////////////////////

    private class CustomLayoutManager extends TabbedPaneLayout {

        @Override
        protected void calculateTabRects(int tabPlacement, int tabCount) {
            System.out.println("here");
            super.calculateTabRects(tabPlacement, tabCount);
        }

        @Override
        public void calculateLayoutInfo() {
            super.calculateLayoutInfo();

            Insets tabAreaInsets = getTabAreaInsets(tabPane.getTabPlacement());
            int tabAreaWidth = tabPane.getWidth() - tabAreaInsets.left - tabAreaInsets.right;
            int numTabs = tabPane.getTabCount();
            int totalTabWidth = 0;
            List<Component> componentsUsingDefaultTabWidth = new ArrayList<Component>();

            for (Component component : fTabWidths.keySet()) {
                int width = fTabWidths.get(component);
                totalTabWidth += width;
                if (width == fCurrentDefaultTabWidth) {
                    componentsUsingDefaultTabWidth.add(component);
                }
            }

            int extraSpace = tabAreaWidth - totalTabWidth;

//            System.out.println("extraSpace " + extraSpace);
            if ((extraSpace > 0 && fCurrentDefaultTabWidth < DEFAULT_TAB_WIDTH || extraSpace < 0) && componentsUsingDefaultTabWidth.size() > 0) {
                int extraSpaceForTab = extraSpace / componentsUsingDefaultTabWidth.size();

                fCurrentDefaultTabWidth += extraSpaceForTab - 2;
                System.out.println("fCurrentDefaultTabWidth " + fCurrentDefaultTabWidth);
                for (Component component : componentsUsingDefaultTabWidth) {
                    fTabWidths.put(component, fCurrentDefaultTabWidth);
//                    fTabWidths.put(component,  25);
                }
            }
        }
    }

}
