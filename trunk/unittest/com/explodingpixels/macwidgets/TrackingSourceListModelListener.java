package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.SourceListModelListener;
import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListItem;

public class TrackingSourceListModelListener implements SourceListModelListener {

    private boolean fWasCategoryAddedCalled;
    private boolean fWasCategoryRemovedCalled;
    private boolean fWasItemAddedToCategoryCalled;
    private boolean fWasItemRemovedFromCategoryCalled;
    private boolean fWasItemAddedToItemCalled;
    private boolean fWasItemRemovedToItemCalled;

    private SourceListCategory fCategoryAddedOrRemoved;
    private SourceListCategory fCategoryAddedToOrRemovedFrom;
    private SourceListItem fItemAddedOrRemoved;
    private SourceListItem fItemAddedToOrRemovedFrom;

    public void categoryAdded(SourceListCategory category) {
        fWasCategoryAddedCalled = true;
        fCategoryAddedOrRemoved = category;
    }

    public void categoryRemoved(SourceListCategory category) {
        fWasCategoryRemovedCalled = true;
        fCategoryAddedOrRemoved = category;
    }

    public void itemAddedToCategory(SourceListItem item, SourceListCategory category) {
        fWasItemAddedToCategoryCalled = true;
        fItemAddedOrRemoved = item;
        fCategoryAddedToOrRemovedFrom = category;
    }

    public void itemRemovedFromCategory(SourceListItem item, SourceListCategory category) {
        fWasItemRemovedFromCategoryCalled = true;
        fItemAddedOrRemoved = item;
        fCategoryAddedToOrRemovedFrom = category;
    }

    public void itemAddedToItem(SourceListItem item, SourceListItem parentItem) {
        fWasItemAddedToItemCalled = true;
        fItemAddedOrRemoved = item;
        fItemAddedToOrRemovedFrom = parentItem;
    }

    public void itemRemovedFromItem(SourceListItem item, SourceListItem parentItem) {
        fWasItemRemovedToItemCalled = true;
        fItemAddedOrRemoved = item;
        fItemAddedToOrRemovedFrom = parentItem;
    }

    public boolean wasCategoryAddedCalled() {
        return fWasCategoryAddedCalled;
    }

    public boolean wasCategoryRemovedCalled() {
        return fWasCategoryRemovedCalled;
    }

    public boolean wasItemAddedToCategoryCalled() {
        return fWasItemAddedToCategoryCalled;
    }

    public boolean wasItemRemovedFromCategoryCalled() {
        return fWasItemRemovedFromCategoryCalled;
    }

    public boolean wasItemAddedToItemCalled() {
        return fWasItemAddedToItemCalled;
    }

    public boolean wasItemRemovedToItemCalled() {
        return fWasItemRemovedToItemCalled;
    }

    public SourceListCategory getCategoryAddedOrRemoved() {
        return fCategoryAddedOrRemoved;
    }

    public SourceListCategory getCategoryAddedToOrRemovedFrom() {
        return fCategoryAddedToOrRemovedFrom;
    }

    public SourceListItem getItemAddedOrRemoved() {
        return fItemAddedOrRemoved;
    }

    public SourceListItem getItemAddedToOrRemovedFrom() {
        return fItemAddedToOrRemovedFrom;
    }

    public void reset() {
        fWasCategoryAddedCalled = false;
        fWasCategoryRemovedCalled = false;
        fWasItemAddedToCategoryCalled = false;
        fWasItemRemovedFromCategoryCalled = false;
        fWasItemAddedToItemCalled = false;
        fWasItemRemovedToItemCalled = false;

        fCategoryAddedOrRemoved = null;
        fCategoryAddedToOrRemovedFrom = null;
        fItemAddedOrRemoved = null;
        fItemAddedToOrRemovedFrom = null;
    }

}
