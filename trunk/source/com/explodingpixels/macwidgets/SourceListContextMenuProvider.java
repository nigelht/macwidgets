package com.explodingpixels.macwidgets;

import javax.swing.JPopupMenu;

/**
 * <p>
 * An interface to hook into the context-menu showing process. When installed on a
 * {@link SourceList}, this interface will be notified just prior to a context menu being shown.
 * </p>
 * <p>
 * Here's a sample implementation and installation of this interface:
 * </p>
 * <pre>
 * SourceListContextMenuProvider menuProvider = new SourceListContextMenuProvider() {
 *           public void customizeContextMenu(JPopupMenu popupMenu) {
 *               // install your custom menu items for context-menu's on the SourceList.
 *               popupMenu.add(new JMenuItem("Generic Menu for SourceList"));
 *           }
 *           public void customizeContextMenu(JPopupMenu popupMenu, SourceListItem item) {
 *               // install your custom menu items for context-menu's on a SourceListItem.
 *               popupMenu.add(new JMenuItem("Menu for " + item.getText()));
 *           }
 *           public void customizeContextMenu(JPopupMenu popupMenu, SourceListCategory category) {
 *               // install your custom menu items for context-menu's on a SourceListCategory.
 *               popupMenu.add(new JMenuItem("Menu for " + category.getText()));
 *           }
 *       };
 * mySourceList..setSourceListContextMenuProvider(menuProvider);
 * <pre>
 */
public interface SourceListContextMenuProvider {

    /**
     * Called when the user requests that a context-menu be shown on the {@link SourceList} itself.
     * This will only be called if the {@code SourceList} does not fill the entire view (doesn't
     * have scroll bars) and the user clicks below any item or category. If no menu items are added
     * to the menu, then the menu will not be shown.
     *
     * @param popupMenu the {@link JPopupMenu} to add menu items to. The menu will be empty by
     *                  default, thus there is no need to clear it.
     */
    void customizeContextMenu(JPopupMenu popupMenu);

    /**
     * Called when the user requests that a context-menu be shown on a {@link SourceListItem}.
     * If no menu items are added to the menu, then the menu will not be shown.
     *
     * @param popupMenu the {@link JPopupMenu} to add menu items to. The menu will be empty by
     *                  default, thus there is no need to clear it.
     * @param item      the {@code SourceListItem} that the context-menu was requested for.
     */
    void customizeContextMenu(JPopupMenu popupMenu, SourceListItem item);

    /**
     * Called when the user requests that a context-menu be shown on a {@link SourceListCategory}.
     * If no menu items are added to the menu, then the menu will not be shown.
     *
     * @param popupMenu the {@link JPopupMenu} to add menu items to. The menu will be empty by
     *                  default, thus there is no need to clear it.
     * @param category  the {@code SourceListCategory} that the context-menu was requested for.
     */
    void customizeContextMenu(JPopupMenu popupMenu, SourceListCategory category);

}
