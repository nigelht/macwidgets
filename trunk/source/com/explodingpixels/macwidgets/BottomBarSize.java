package com.explodingpixels.macwidgets;

public enum BottomBarSize {

    SMALL(24), LARGE(34);

    private int fHeight;

    BottomBarSize(int height) {
        fHeight = height;
    }

    public int getHeight() {
        return fHeight;
    }
}
