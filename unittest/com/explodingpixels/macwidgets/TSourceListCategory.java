package com.explodingpixels.macwidgets;

import org.junit.Test;
import org.junit.Assert;

public class TSourceListCategory {

    @Test
    public void testContains() {
        SourceListCategory category = new SourceListCategory("Category");
        SourceListItem item = new SourceListItem("Item");
        category.addItem(item);
        Assert.assertTrue("The category should contain the given item.",
                category.containsItem(item));
    }

    @Test
    public void testNotContains() {
        SourceListCategory category = new SourceListCategory("Category");
        SourceListItem item = new SourceListItem("Item");
        Assert.assertFalse("The category should not contain the given item.",
                category.containsItem(item));
    }

    @Test
    public void testContainsNested() {
        SourceListCategory category = new SourceListCategory("Category");
        SourceListItem itemOne = new SourceListItem("Item One");
        SourceListItem itemTwo = new SourceListItem("Item Two");

        category.addItem(itemOne);
        itemOne.addItem(itemTwo);

        Assert.assertTrue("The category should contain the given item.",
                category.containsItem(itemTwo));
    }

}
