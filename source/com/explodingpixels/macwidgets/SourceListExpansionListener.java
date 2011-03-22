package com.explodingpixels.macwidgets;

/**
 *
 */
public interface SourceListExpansionListener {

    void sourceListItemExpanded(SourceListItem item);

    void sourceListItemCollapsed(SourceListItem item);

    void sourceListCategoryExpanded(SourceListCategory category);

    void sourceListCategoryCollapsed(SourceListCategory category);
}
