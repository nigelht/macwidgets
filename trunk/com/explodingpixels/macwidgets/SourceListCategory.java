package com.explodingpixels.macwidgets;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A category in a {@link SourceList}. {@code SourceListCategory}s are top level containers for
 * {@link SourceListItem}s. {@code SourceListCategory}s are text only, and rendered in full caps
 * (regardless of supplied text capitalization).
 */
public class SourceListCategory {

    private List<SourceListItem> fItems = new ArrayList<SourceListItem>();

    private String fText;

    /**
     * Creates a {@code SourceListCategory} with the given text. The capitalization of the text will
     * be ignored, as categories are rendered in full caps.
     * @param text the category text. Cannot be null.
     */
    public SourceListCategory(String text) {
        checkText(text);
        fText = text;
    }

    private void checkText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }
    }

    /**
     * Gets the category text. The returned will be the text that was set on the category - that is,
     * this method does not return an all caps version of the text.
     * @return the category text.
     */
    public String getText() {
        return fText;
    }

    /**
     * Sets the text to use for this {@code SourceListCategory}. The capitalization of the text will
     * be ignored, as categories are rendered in full caps.
     * @param text the category text.
     */
    public void setText(String text) {
        checkText(text);
        fText = text;
    }

    /**
     * Gets the number of child {@link SourceListItem}s that are part of this category.
     * @return the number of {@code SourceListItem}s that are part of this category.
     */
    public int getItemCount() {
        return fItems.size();
    }

    /**
     * Returns {@code true} if the given {@link SourceListItem} is contained by this category, to
     * include being a sub-element of another {@code SourceListItem} contained by this category.
     * @param item the {@code SourceListItem} to determine whether or not is contained by this
     *        category.
     * @return {@code true} if the given {@code SourceListItem} is contained within this category
     *         or within on of this categories {@code SourceListItem}s.
     */
    public boolean containsItem(SourceListItem item) {
        boolean contains = false;
        for (SourceListItem childItem : fItems) {
            contains = childItem.equals(item) || childItem.containsItem(item);
            if (contains) {
                break;
            }
        }
        return contains;
    }

    SourceListItem getItem(int index) {
        return fItems.get(index);
    }

    List<SourceListItem> getItems() {
        return Collections.unmodifiableList(fItems);
    }

    void addItem(SourceListItem item) {
        fItems.add(item);
    }

    void addItem(int index, SourceListItem item) {
        fItems.add(index, item);
    }

    void removeItem(SourceListItem item) {
        // TODO should throw error if item not in list?
        fItems.remove(item);
    }

    SourceListItem removeItem(int index) {
        // TODO should throw error if item not in list?
        return fItems.remove(index);
    }

}
