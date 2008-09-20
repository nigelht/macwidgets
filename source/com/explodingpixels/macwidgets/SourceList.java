package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.TreeUtils;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of an OS X Source List. For a full descrption of what a Source List is, see the
 * <a href="http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-CHDDIGDE">Source Lists</a>
 * section of Apple's Human Interface Guidelines.
 * <p/>
 * This component provides the two basic sytles of Source List: focusble and non-focusable.
 * As the name implies, focusable Source Lists and recieve keyboard focus, and thus can be navigated
 * using the arrow keys. Non-focusable, cannot receive keyboard focus, and thus cannot be
 * navigated via the arrow keys. The two styles of {@code SourceList} are pictured below:
 * <br>
 * <table>
 * <tr><td align="center"><img src="../../../../graphics/iTunesSourceList.png"></td>
 * <td align="center"><img src="../../../../graphics/MailSourceList.png"></td></tr>
 * <tr><td align="center"><font size="2" face="arial"><b>Focusable SourceList<b></font></td>
 * <td align="center"><font size="2" face="arial"><b>Non-focusable SourceList<b></font></td></tr>
 * </table>
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

    private JTree fTree = MacWidgetFactory.createSourceList(fTreeModel);

    private JScrollPane fScrollPane = MacWidgetFactory.createSourceListScrollPane(fTree);

    private final JPanel fComponent = new JPanel(new BorderLayout());

    private TreeSelectionListener fTreeSelectionListener = createTreeSelectionListener();

    private SourceListControlBar fSourceListControlBar;

    /**
     * Creates a {@code SourceList} with an empty {@link SourceListModel}.
     */
    public SourceList() {
        this(new SourceListModel());
    }

    /**
     * Creates a {@code SourceList} with the given {@link SourceListModel}.
     *
     * @param model the {@code SourceListModel} to use.
     */
    public SourceList(SourceListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Groups cannot be null.");
        }

        fModel = model;
        fModel.addSourceListModelListener(fModelListener);

        initUi();

        // add each category and its sub-items to backing JTree.
        for (SourceListCategory group : model.getCategories()) {
            doAddCategory(group);
        }
    }

    private void initUi() {
        fComponent.add(fScrollPane, BorderLayout.CENTER);
        fTree.addTreeSelectionListener(fTreeSelectionListener);
    }

    /**
     * Installs the given {@link SourceListControlBar} at the base of this {@code SourceList}.
     *
     * @param sourceListControlBar the {@link SourceListControlBar} to add.
     * @throws IllegalStateException if a {@code SourceListControlBar} has already been installed
     *                               on this {@code SourceList}.
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
     *
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
     * Selects the given {@link SourceListItem} in the list.
     *
     * @param item the item to select.
     * @throws IllegalArgumentException if the given item is not in the list.
     */
    public void setSelectedItem(SourceListItem item) {
        getModel().checkItemIsInModel(item);
        DefaultMutableTreeNode treeNode = getNodeForObject(fRoot, item);
        fTree.setSelectionPath(new TreePath(treeNode.getPath()));
    }

    /**
     * Sets whether this {@code SourceList} can have focus. When focusable and this
     * {@code SourceList} has focus, the keyboard can be used for navigation.
     *
     * @param focusable true if this {@code SourceList} should be focusable.
     */
    public void setFocusable(boolean focusable) {
        fTree.setFocusable(focusable);
    }

    private static DefaultMutableTreeNode getNodeForObject(DefaultMutableTreeNode parentNode,
                                                           Object userObject) {
        if (parentNode.getUserObject().equals(userObject)) {
            return parentNode;
        } else if (parentNode.children().hasMoreElements()) {
            for (int i = 0; i < parentNode.getChildCount(); i++) {
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
     *
     * @return the user interface component representing this {@code SourceList}.
     */
    public JComponent getComponent() {
        return fComponent;
    }

    /**
     * Gets the {@link SourceListModel} backing this {@code SourceList}.
     *
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
     *
     * @param listener the {@code SourceListSelectionListener} to add.
     */
    public void addSourceListSelectionListener(SourceListSelectionListener listener) {
        fSourceListSelectionListeners.add(listener);
    }

    /**
     * Removes the {@link SourceListSelectionListener} from the list of listeners.
     *
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

}
