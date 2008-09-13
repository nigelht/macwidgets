package com.explodingpixels.macwidgets;

/**
 * An enumeration encapsulating the possible sizes of a Bottom Bar. The sizes called out by Apple in
 * Human Interface Guideline <a href="http://developer.apple.com/documentation/UserExperience/Conceptual/AppleHIGuidelines/XHIGWindows/chapter_18_section_4.html#//apple_ref/doc/uid/20000961-SW6">here</a>
 * are a 22 pixel and 32 pixel area. These correspond to the {@link #SMALL} and {@link #LARGE}
 * sizes. The {@link #EXTRA_SMALL} size isn't officially in the HIG, but can be seen in use in
 * Safari's status bar for example.
 */
public enum BottomBarSize {

    EXTRA_SMALL(14), SMALL(22), LARGE(32);

    private int fHeight;

    BottomBarSize(int height) {
        fHeight = height;
    }

    public int getHeight() {
        return fHeight;
    }
}