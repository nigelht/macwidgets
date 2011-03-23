package com.explodingpixels.macwidgets;

/**
 * An interface for listening for expansion events.
 * It handles expansion events for both {@link SourceListItem} and {@link SourceListCategory}.
 */
public interface SourceListExpansionListener {

    /**
     * Called when a {@link SourceListItem} is expanded in a {@link SourceList}.
     * @param item the item that was expanded.
     */
    void sourceListItemExpanded(SourceListItem item);

    /**
     * Called when a {@link SourceListItem} is collapsed in a {@link SourceList}.
     * @param item the item that was collapsed.
     */
    void sourceListItemCollapsed(SourceListItem item);

    /**
     * Called when a {@link SourceListCategory} is expanded in a {@link SourceList}.
     * @param category the category that was expanded.
     */
    void sourceListCategoryExpanded(SourceListCategory category);

    /**
     * Called when a {@link SourceListCategory} is collapsed in a {@link SourceList}.
     * @param category the category that was collapsed.
     */
    void sourceListCategoryCollapsed(SourceListCategory category);
}
