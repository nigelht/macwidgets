package com.explodingpixels.macwidgets;

import org.junit.Test;
import org.junit.Assert;

public class TSourceListItem {

    @Test
    public void testContains() {
        SourceListItem itemOne = new SourceListItem("Item One");
        SourceListItem itemTwo = new SourceListItem("Item Two");
        itemOne.addItem(itemTwo);
        Assert.assertTrue("The item should contain the given item.",
                itemOne.containsItem(itemTwo));
    }

    @Test
    public void testNotContains() {
        SourceListItem itemOne = new SourceListItem("Item One");
        SourceListItem itemTwo = new SourceListItem("Item Two");
        Assert.assertFalse("The item should not contain the given item.",
                itemOne.containsItem(itemTwo));    }

    @Test
    public void testContainsNested() {
        SourceListItem itemOne = new SourceListItem("Item One");
        SourceListItem itemTwo = new SourceListItem("Item Two");
        SourceListItem itemThree = new SourceListItem("Item Three");

        itemOne.addItem(itemTwo);
        itemTwo.addItem(itemThree);

        Assert.assertTrue("The item should contain the given item.",
                itemOne.containsItem(itemThree));
    }

}
