package com.explodingpixels.macwidgets;

public enum BottomBarSize {

    EXTRA_SMALL(18), SMALL(24), LARGE(34);

    private int fHeight;

    BottomBarSize(int height) {
        fHeight = height;
    }

    public int getHeight() {
        return fHeight;
    }
}
