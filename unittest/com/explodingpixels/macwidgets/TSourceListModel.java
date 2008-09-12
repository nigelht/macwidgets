package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListModel;
import com.explodingpixels.macwidgets.SourceListItem;
import org.junit.Test;
import org.junit.Assert;

public class TSourceListModel {

    @Test
    public void testCategoryAddedAndRemoved() {
        SourceListModel model = new SourceListModel();
        TrackingSourceListModelListener listener = new TrackingSourceListModelListener();
        model.addSourceListModelListener(listener);

        // create and add the category.
        SourceListCategory category = new SourceListCategory("Category");
        model.addCategory(category);

        // assert that we were notified of the event with the correct data.
        Assert.assertTrue("categoryAdded should have been called.",
                listener.wasCategoryAddedCalled());
        Assert.assertEquals("The wrong category was indicated as the category added.",
                category, listener.getCategoryAddedOrRemoved());

        listener.reset();

        // remove the category.
        model.removeCategory(category);

        // assert that we were notified of the event with the correct data.
        Assert.assertTrue("categoryRemoved should have been called.",
                listener.wasCategoryRemovedCalled());
        Assert.assertEquals("The wrong category was indicated as the category removed.",
                category, listener.getCategoryAddedOrRemoved());
    }

    @Test
    public void testItemAddedAndRemovedFromCategory() {
        SourceListModel model = new SourceListModel();

        // create and add the category .
        SourceListCategory category = new SourceListCategory("Category");
        model.addCategory(category);

        // create and add the SourceListModelListener.
        TrackingSourceListModelListener listener = new TrackingSourceListModelListener();
        model.addSourceListModelListener(listener);

        // create and add the item.
        SourceListItem item = new SourceListItem("Item");
        model.addItemToCategory(item, category);

        // assert that we were notified of the event with the correct data.
        Assert.assertTrue("itemAddedToCategory should have been called.",
                listener.wasItemAddedToCategoryCalled());
        Assert.assertEquals("The wrong item was indicated as the item added.",
                item, listener.getItemAddedOrRemoved());
        Assert.assertEquals("The wrong category was indicated as the category added to.",
                category, listener.getCategoryAddedToOrRemovedFrom());

        listener.reset();

        // remove the item.
        model.removeItemFromCategory(item, category);

        // assert that we were notified of the event with the correct data.
        Assert.assertTrue("itemRemovedFromCategory should have been called.",
                listener.wasItemRemovedFromCategoryCalled());
        Assert.assertEquals("The wrong item was indicated as the item removed.",
                item, listener.getItemAddedOrRemoved());
        Assert.assertEquals("The wrong category was indicated as the category removed from.",
                category, listener.getCategoryAddedToOrRemovedFrom());
    }

    @Test
    public void testItemAddedAndRemovedFromItem() {
        SourceListModel model = new SourceListModel();

        // create and add a category and item.
        SourceListCategory category = new SourceListCategory("Category");
        SourceListItem item = new SourceListItem("Item");
        model.addCategory(category);
        model.addItemToCategory(item, category);

        // create and add the SourceListModelListener.
        TrackingSourceListModelListener listener = new TrackingSourceListModelListener();
        model.addSourceListModelListener(listener);

        // create and add a second item.
        SourceListItem itemTwo = new SourceListItem("Item");
        model.addItemToItem(itemTwo, item);

        // assert that we were notified of the event with the correct data.
        Assert.assertTrue("itemAddedToItem should have been called.",
                listener.wasItemAddedToItemCalled());
        Assert.assertEquals("The wrong item was indicated as the item added.",
                itemTwo, listener.getItemAddedOrRemoved());
        Assert.assertEquals("The wrong item was indicated as the item added to.",
                item, listener.getItemAddedToOrRemovedFrom());

        listener.reset();

        // remove the item.
        model.removeItemFromItem(itemTwo, item);

        // assert that we were notified of the event with the correct data.
        Assert.assertTrue("itemRemovedFromItem should have been called.",
                listener.wasItemRemovedToItemCalled());
        Assert.assertEquals("The wrong item was indicated as the item removed.",
                itemTwo, listener.getItemAddedOrRemoved());
        Assert.assertEquals("The wrong item was indicated as the item removed from.",
                item, listener.getItemAddedToOrRemovedFrom());

    }

}
