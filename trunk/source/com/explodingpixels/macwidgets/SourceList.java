package com.explodingpixels.macwidgets;

import com.explodingpixels.painter.TriStateFocusPainter;
import com.explodingpixels.widgets.TreeUtils;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.RectanglePainter;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of an OS X Source List. For a full descrption of what a Source List is, see the
 * <a href="http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-CHDDIGDE">Source Lists</a>
 * section of Apple's Human Interface Guidelines.
 *
 * This component provides the two basic sytles of Source List: focusble and non-focusable.
 * As the name implies, focusable Source Lists and recieve keyboard focus, and thus can be navigated
 * using the arrow keys. When focused, the Source List looks like this:
 *<br>
 * <img src="../../../resources/iTunesSourceList.png">
 * <br>
 * The second type of Source List, non-focusable, cannot receive keyboard focus, and thus cannot be
 * navigated via the arrow keys. A non-focusable Source List looks like this:
 * <br>
 * <img src="../../../resources/MailSourceList.png">
 * <br>
 * Here's how to create a simple {@code SourceList} with one item:
 * <pre>
 * SourceListModel model = new SourceListModel();
 * SourceListCategory category = new SourceListCategory("Category");
 * model.addCategory(category);
 * model.addItemToCategory(new SourceListItem("Item"), category);
 * SourceList sourceList = new SourceList(model);
 * </pre>
 */
public class SourceList {

    private final SourceListModel fModel;

    private final SourceListModelListener fModelListener =
            createSourceListModelListener();

    private final List<SourceListSelectionListener> fSourceListSelectionListeners =
            new ArrayList<SourceListSelectionListener>();

    private DefaultMutableTreeNode fRoot = new DefaultMutableTreeNode("root");

    private DefaultTreeModel fTreeModel = new DefaultTreeModel(fRoot);

    private CustomJTree fTree = new CustomJTree(fTreeModel);

    private JScrollPane fScrollPane = new JScrollPane(fTree,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private final JPanel fComponent = new JPanel(new BorderLayout());

    private TreeSelectionListener fTreeSelectionListener =
            createTreeSelectionListener();

    private CustomTreeUI fTreeUI = new CustomTreeUI();

    private SourceListCategoryTreeCellRenderer fCategoryRenderer;

    private SourceListItemCellRenderer fItemRenderer;

    private SourceListControlBar fSourceListControlBar;

    private static Icon COLLAPSED_ICON = new ImageIcon(
            SourceList.class.getResource(
                    "/com/explodingpixels/macwidgets/images/group_list_right_arrow.png"));

    private static Icon EXPANDED_ICON = new ImageIcon(
            SourceList.class.getResource(
                    "/com/explodingpixels/macwidgets/images/group_list_down_arrow.png"));

    /**
     * Creates a {@code SourceList} with an empty {@link SourceListModel}.
     */
    public SourceList() {
        this(new SourceListModel());
    }

    /**
     * Creates a {@code SourceList} with the given {@link SourceListModel}.
     * @param model the {@code SourceListModel} to use.
     */
    public SourceList(SourceListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Groups cannot be null.");
        }

        fModel = model;
        fModel.addSourceListModelListener(fModelListener);

        fCategoryRenderer = new SourceListCategoryTreeCellRenderer();
        fItemRenderer = new SourceListItemCellRenderer();

        initUi();

        // add each category and its sub-items to backing JTree.
        for (SourceListCategory group : model.getCategories()) {
            doAddCategory(group);
        }
    }

    private void initUi() {
        fComponent.add(fScrollPane, BorderLayout.CENTER);
        fScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // setup the default background painter.
        RectanglePainter<Component> focusedPainter = new RectanglePainter<Component>(
                MacColorUtils.SOURCE_LIST_FOCUSED_BACKGROUND_COLOR,
                MacColorUtils.SOURCE_LIST_FOCUSED_BACKGROUND_COLOR);
        RectanglePainter<Component> unfocusedPainter = new RectanglePainter<Component>(
                MacColorUtils.SOURCE_LIST_UNFOCUSED_BACKGROUND_COLOR,
                MacColorUtils.SOURCE_LIST_UNFOCUSED_BACKGROUND_COLOR);
        setBackgroundPainter(new TriStateFocusPainter(
                focusedPainter, focusedPainter, unfocusedPainter));

//        fTree.setOpaque(false);
        fTree.setSelectionModel(new CustomTreeSelectionModel());
        fTree.setLargeModel(true);
        fTree.setUI(fTreeUI);

        fTree.setRowHeight(20);
        fTree.setRootVisible(false);
        fTree.setShowsRootHandles(true);
//        fTree.expandPath(new TreePath(fRoot.getPath()));

        fTree.addTreeSelectionListener(fTreeSelectionListener);

        // set the disclosure icons and the corresponding indents.
        TreeUtils.setCollapsedIcon(fTree, COLLAPSED_ICON);
        TreeUtils.setExpandedIcon(fTree, EXPANDED_ICON);
        int indent = COLLAPSED_ICON.getIconWidth()/2 + 4;
        TreeUtils.setLeftChildIndent(fTree, indent);
        TreeUtils.setRightChildIndent(fTree, indent);

        fTree.setCellRenderer(new GroupListRenderer());
    }

    /**
     * Installs the given {@link SourceListControlBar} at the base of this {@code SourceList}.
     * @param sourceListControlBar the {@link SourceListControlBar} to add.
     * @throws IllegalStateException if a {@code SourceListControlBar} has already been installed
     *         on this {@code SourceList}.
     */
    public void installSourceListControlBar(SourceListControlBar sourceListControlBar) {
        if (fSourceListControlBar != null) {
            throw new IllegalStateException("A SourceListControlBar has already been installed on" +
                    " this SourceList.");
        }
        fSourceListControlBar = sourceListControlBar;
        fComponent.add(fSourceListControlBar.getComponent(), BorderLayout.SOUTH);
    }

    /**
     * Uninstalls any listeners that this {@code SourceList} installed on creation, thereby allowing
     * it to be garbage collected.
     */
    public void dispose() {
        fModel.removeSourceListModelListener(fModelListener);
    }

    /**
     * Gets the selected {@link SourceListItem}.
     * @return the selected {@code SourceListItem}.
     */
    public SourceListItem getSelectedItem() {
        SourceListItem selectedItem = null;
        if (fTree.getSelectionPath() != null
                && fTree.getSelectionPath().getLastPathComponent() != null) {
            DefaultMutableTreeNode selectedNode =
                    (DefaultMutableTreeNode) fTree.getSelectionPath().getLastPathComponent();
            assert selectedNode.getUserObject() instanceof SourceListItem
                            : "Only SourceListItems can be selected.";
            selectedItem = (SourceListItem) selectedNode.getUserObject();
        }
        return selectedItem;
    }

    /**
     * Sets whether this {@code SourceList} can have focus. When focusable and this
     * {@code SourceList} has focus, the keyboard can be used for navigation.
     * @param focusable true if this {@code SourceList} should be focusable.
     */
    public void setFocusable(boolean focusable) {
        fTree.setFocusable(focusable);
    }

    private void setBackgroundPainter(Painter<Component> painter) {
        fTree.setBackgroundPainter(painter);
    }

    private static DefaultMutableTreeNode getNodeForObject(DefaultMutableTreeNode parentNode,
                                                           Object userObject) {
        if (parentNode.getUserObject().equals(userObject)) {
            return parentNode;
        } else if (parentNode.children().hasMoreElements()) {
            for (int i=0; i<parentNode.getChildCount(); i++) {
                DefaultMutableTreeNode childNode =
                        (DefaultMutableTreeNode) parentNode.getChildAt(i);
                DefaultMutableTreeNode retVal =
                        getNodeForObject(childNode, userObject);
                if (retVal != null) {
                    return retVal;
                }
            }
        } else {
            return null;
        }

        return null;
    }

    /**
     * Gets the user interface component representing this {@code SourceList}. The returned
     * {@link JComponent} should be added to a container that will be displayed.
     * @return the user interface component representing this {@code SourceList}.
     */
    public JComponent getComponent() {
        return fComponent;
    }

    /**
     * Gets the {@link SourceListModel} backing this {@code SourceList}.
     * @return the {@code SourceListModel} backing this {@code SourceList}.
     */
    public SourceListModel getModel() {
        return fModel;
    }

    private void doAddCategory(SourceListCategory category) {
        DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category);
        fTreeModel.insertNodeInto(categoryNode, fRoot, fRoot.getChildCount());
        for (SourceListItem item : category.getItems()) {
            doAddItemToCategory(item, category);
        }

        TreeUtils.expandPathOnEdt(fTree, new TreePath(categoryNode.getPath()));
    }

    private void doRemoveCategory(SourceListCategory category) {
        DefaultMutableTreeNode categoryNode = getNodeForObject(fRoot, category);
        checkNodeNotNull(categoryNode);
        fTreeModel.removeNodeFromParent(categoryNode);
    }

    private void doAddItemToCategory(SourceListItem itemToAdd, SourceListCategory category) {
        DefaultMutableTreeNode categoryNode = getNodeForObject(fRoot, category);
        checkNodeNotNull(categoryNode);
        doAddItemToNode(itemToAdd, categoryNode);
    }

    private void doRemoveItemFromCategory(SourceListItem itemToRemove, SourceListCategory category) {
        DefaultMutableTreeNode categoryNode = getNodeForObject(fRoot, category);
        checkNodeNotNull(categoryNode);
        DefaultMutableTreeNode itemNode = getNodeForObject(categoryNode, itemToRemove);
        checkNodeNotNull(itemNode);
        fTreeModel.removeNodeFromParent(itemNode);
    }

    private void doAddItemToItem(SourceListItem itemToAdd, SourceListItem parentItem) {
        DefaultMutableTreeNode parentItemNode = getNodeForObject(fRoot, parentItem);
        checkNodeNotNull(parentItemNode);
        doAddItemToNode(itemToAdd, parentItemNode);
    }

    private void doRemoveItemFromItem(SourceListItem itemToRemove, SourceListItem parentItem) {
        DefaultMutableTreeNode parentNode = getNodeForObject(fRoot, parentItem);
        checkNodeNotNull(parentNode);
        DefaultMutableTreeNode itemNode = getNodeForObject(parentNode, itemToRemove);
        checkNodeNotNull(itemNode);
        fTreeModel.removeNodeFromParent(itemNode);
    }

    private void doAddItemToNode(SourceListItem itemToAdd, DefaultMutableTreeNode parentNode) {
        DefaultMutableTreeNode itemNode = new DefaultMutableTreeNode(itemToAdd);
        fTreeModel.insertNodeInto(itemNode, parentNode, parentNode.getChildCount());

        for (SourceListItem childItem : itemToAdd.getChildItems()) {
            doAddItemToItem(childItem, itemToAdd);
        }
    }

    private TreeSelectionListener createTreeSelectionListener() {
        return new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                fireSourceListItemSelected(getSelectedItem());
            }
        };
    }

    private SourceListModelListener createSourceListModelListener() {
        return new SourceListModelListener() {
            public void categoryAdded(SourceListCategory category) {
                doAddCategory(category);
            }
            public void categoryRemoved(SourceListCategory category) {
                doRemoveCategory(category);
            }
            public void itemAddedToCategory(SourceListItem item, SourceListCategory category
            ) {
                doAddItemToCategory(item, category);
            }
            public void itemRemovedFromCategory(SourceListItem item, SourceListCategory category
            ) {
                doRemoveItemFromCategory(item, category);
            }
            public void itemAddedToItem(SourceListItem item, SourceListItem parentItem
            ) {
                doAddItemToItem(item, parentItem);
            }
            public void itemRemovedFromItem(SourceListItem item, SourceListItem parentItem
            ) {
                doRemoveItemFromItem(item, parentItem);
            }
        };
    }

    // SourceListSelectionListener support. ///////////////////////////////////////////////////////

    private void fireSourceListItemSelected(SourceListItem item) {
        for (SourceListSelectionListener listener : fSourceListSelectionListeners) {
            listener.sourceListItemSelected(item);
        }
    }

    /**
     * Adds a {@link SourceListSelectionListener} to the list of listeners.
     * @param listener the {@code SourceListSelectionListener} to add.
     */
    public void addSourceListSelectionListener(SourceListSelectionListener listener) {
        fSourceListSelectionListeners.add(listener);
    }

    /**
     * Removes the {@link SourceListSelectionListener} from the list of listeners.
     * @param listener the {@code SourceListSelectionListener} to remove.
     */
    public void removeSourceListSelectionListener(SourceListSelectionListener listener) {
        fSourceListSelectionListeners.remove(listener);
    }

    // Utility methods. ///////////////////////////////////////////////////////////////////////////

    private static void checkNodeNotNull(MutableTreeNode node) {
        if (node == null) {
            throw new IllegalArgumentException("The given SourceListCategory " +
                    "does not exist in this SourceList.");
        }
    }

    private boolean isSourceListItem(int index) {
        if (index < 0 || fTree.getRowCount() <= index) {
            throw new IllegalArgumentException("Row index " + index + " is out of bounds.");
        }

        TreePath path = fTree.getPathForRow(index);
        assert path != null : "TreePath should not be null.";
        assert path.getLastPathComponent() instanceof DefaultMutableTreeNode
                : "";

        return ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject()
                instanceof SourceListItem;
    }

    // Custom JTree. //////////////////////////////////////////////////////////////////////////////

    private static class CustomJTree extends JTree {

        private Painter<Component> fBackgroundPainter;

        public CustomJTree(TreeModel newModel) {
            super(newModel);
            setBackground(new Color(0,0,0,0));
            addFocusListener(createFocusListener());
        }

        private void setBackgroundPainter(Painter<Component> painter) {
            fBackgroundPainter = painter;
        }

        /**
         * Creates a {@link java.awt.event.FocusListener} that repaints the selection on focus
         * gained and focus lost events.
         * @return a {@code FocusListener} that repaints the selecion on focus state
         *         changes.
         */
        private FocusListener createFocusListener() {
            return new FocusListener() {
                public void focusGained(FocusEvent e) {
                    TreeUtils.repaintSelection(CustomJTree.this);
                }
                public void focusLost(FocusEvent e) {
                    TreeUtils.repaintSelection(CustomJTree.this);
                }
            };
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g.create();
            fBackgroundPainter.paint(graphics2D,this,getWidth(),getHeight());
            graphics2D.dispose();

            // paint the background for the selected entry, if there is one.
            int selectedRow = getSelectionModel().getLeadSelectionRow();
            if (selectedRow > 0 && isVisible(getPathForRow(selectedRow))) {

                Rectangle bounds = getRowBounds(selectedRow);

                TriStateFocusPainter painter = new TriStateFocusPainter(
                        MacPainterFactory.createSourceListSelectionPainter_componentFocused(),
                        MacPainterFactory.createSourceListSelectionPainter_windowFocused(),
                        MacPainterFactory.createSourceListSelectionPainter_windowUnfocused());


                graphics2D = (Graphics2D) g.create();
                graphics2D.translate(0, bounds.y);
                painter.paint(graphics2D, this, getWidth(), bounds.height);
                graphics2D.dispose();
            }

            super.paintComponent(g);
        }

    }

    // Custom TreeCellRenderer. ///////////////////////////////////////////////////////////////////

    private class GroupListRenderer implements TreeCellRenderer {

        public Component getTreeCellRendererComponent(
                JTree tree, Object value, boolean selected, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {

            Object node = ((DefaultMutableTreeNode)value).getUserObject();
            Component c = new JLabel();

            if (node instanceof SourceListCategory) {
                c = fCategoryRenderer.getCellRendererComponent(
                        (SourceListCategory) node);
            } else if (node instanceof SourceListItem) {
                c = fItemRenderer.getCellRendererComponent(
                        (SourceListItem) node, selected);
            }

            return c;
        }

    }

    // Custom TreeSelectionModel. /////////////////////////////////////////////////////////////////

    private class CustomTreeSelectionModel extends DefaultTreeSelectionModel {
        public CustomTreeSelectionModel() {
            setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        }

        private boolean canSelect(TreePath path) {
            Object node = path == null ? null : path.getLastPathComponent();
            return node != null
                    && node instanceof DefaultMutableTreeNode
                    && ((DefaultMutableTreeNode)node).getUserObject() instanceof SourceListItem;
        }

        @Override
        public void setSelectionPath(TreePath path) {
            if (canSelect(path)) {
                super.setSelectionPath(path);
            }
        }

        @Override
        public void setSelectionPaths(TreePath[] paths) {
            if (canSelect(paths[0])) {
                super.setSelectionPaths(paths);
            }
        }
    }

    // Custom BaicTreeUI. /////////////////////////////////////////////////////////////////////////

    private class CustomTreeUI extends BasicTreeUI {

        private final String SELECT_NEXT = "selectNext";
        private final String SELECT_PREVIOUS = "selectPrevious";

        @Override
        protected void installKeyboardActions() {
            super.installKeyboardActions();
            tree.getInputMap().put(KeyStroke.getKeyStroke("pressed DOWN"), SELECT_NEXT);
            tree.getInputMap().put(KeyStroke.getKeyStroke("pressed UP"), SELECT_PREVIOUS);
            tree.getActionMap().put(SELECT_NEXT, createNextAction());
            tree.getActionMap().put(SELECT_PREVIOUS, createPreviousAction());
        }

        @Override
        protected AbstractLayoutCache.NodeDimensions createNodeDimensions() {
            return new NodeDimensionsHandler() {
                @Override
                public Rectangle getNodeDimensions(
                        Object value, int row, int depth, boolean expanded,
                        Rectangle size) {

                    Rectangle dimensions = super.getNodeDimensions(value, row,
                            depth, expanded, size);
                    dimensions.width =
                            fScrollPane.getViewport().getWidth() - getRowX(row, depth);

                    return dimensions;
                }
            };
        }

        @Override
        protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
            // do nothing - don't paint horizontal lines.
        }

        @Override
        protected void paintVerticalPartOfLeg(Graphics g, Rectangle clipBounds, Insets insets,
                                              TreePath path) {
            // do nothing - don't paint vertical lines.
        }

        private Action createNextAction() {
            return new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tree.getLeadSelectionRow();
                    int rowToSelect = selectedRow + 1;
                    while(rowToSelect >= 0 && rowToSelect < fTree.getRowCount()) {
                        if (isSourceListItem(rowToSelect)) {
                            tree.setSelectionRow(rowToSelect);
                            break;
                        } else {
                            rowToSelect++;
                        }
                    }
                }
            };
        }

        private Action createPreviousAction() {
            return new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tree.getLeadSelectionRow();
                    int rowToSelect = selectedRow - 1;
                    while(rowToSelect >= 0 && rowToSelect < fTree.getRowCount()) {
                        if (isSourceListItem(rowToSelect)) {
                            tree.setSelectionRow(rowToSelect);
                            break;
                        } else {
                            rowToSelect--;
                        }
                    }
                }
            };
        }
    }

}
