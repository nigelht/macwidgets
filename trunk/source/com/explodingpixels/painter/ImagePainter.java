package com.explodingpixels.painter;

import javax.imageio.ImageIO;
import java.awt.Component;
import java.io.IOException;
import java.net.URL;

public class ImagePainter extends org.jdesktop.swingx.painter.ImagePainter<Component> {

    public ImagePainter(URL url, boolean fillHorizontal, boolean fillVertical) {

        try {
            setImage(ImageIO.read(url));
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem reading image file.");
        }

        setFillHorizontal(fillHorizontal);
        setFillVertical(fillVertical);

    }



}
