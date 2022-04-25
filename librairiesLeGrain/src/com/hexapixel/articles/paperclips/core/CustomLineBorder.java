package com.hexapixel.articles.paperclips.core;

import net.sf.paperclips.AbstractBorderPainter;
import net.sf.paperclips.Border;
import net.sf.paperclips.BorderPainter;
import net.sf.paperclips.LineBorder;
import net.sf.paperclips.internal.ResourcePool;
import net.sf.paperclips.internal.Util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;

/**
 * Custom border drawing, code copied from paperclips LineBorder class and modified
 * 
 * @author Hexapixel
 */
public class CustomLineBorder implements Border {

    RGB     rgb;
    int     lineWidth   = 1;    // in points
    int     gapSize     = 5;    // in points
    boolean _horizontal = false;

    /**
     * Constructs a LineBorder with a black border and 5-pt insets. (72 pts = 1")
     */
    public CustomLineBorder(boolean horizontal) {
        this(new RGB(0, 0, 0), horizontal); // black
    }

    /**
     * Constructs a LineBorder with 5-pt insets. (72 pts = 1")
     * 
     * @param rgb
     *            the color to use for the border.
     */
    public CustomLineBorder(RGB rgb, boolean horizontal) {
        setRGB(rgb);
        _horizontal = horizontal;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + gapSize;
        result = prime * result + lineWidth;
        result = prime * result + ((rgb == null) ? 0 : rgb.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LineBorder other = (LineBorder) obj;
        if (gapSize != other.getGapSize()) return false;
        if (lineWidth != other.getLineWidth()) return false;
        if (rgb == null) {
            if (other.getRGB() != null) return false;
        } else if (!rgb.equals(other.getRGB())) return false;
        return true;
    }

    /**
     * Sets the border color to the argument.
     * 
     * @param rgb
     *            the new border color.
     */
    public void setRGB(RGB rgb) {
        this.rgb = new RGB(rgb.red, rgb.green, rgb.blue);
    }

    /**
     * Returns the border color.
     * 
     * @return the border color.
     */
    public RGB getRGB() {
        return new RGB(rgb.red, rgb.green, rgb.blue);
    }

    /**
     * Sets the line width to the argument.
     * 
     * @param points
     *            the line width, in points.
     */
    public void setLineWidth(int points) {
        if (points < 1) points = 1;

        this.lineWidth = points;
    }

    /**
     * Returns the line width of the border, expressed in points.
     * 
     * @return the line width of the border, expressed in points.
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the size of the gap between the line border and the target print.
     * 
     * @param points
     *            the gap size, expressed in points.
     */
    public void setGapSize(int points) {
        if (points < 1) points = 1;

        this.gapSize = points;
    }

    /**
     * Returns the size of the gap between the line border and the target print, expressed in points.
     * 
     * @return the gap size between the line border and the target print.
     */
    public int getGapSize() {
        return Math.max(lineWidth, gapSize);
    }

    public BorderPainter createPainter(Device device, GC gc) {
        return new LineBorderPainter(this, device, gc, _horizontal);
    }
}

class LineBorderPainter extends AbstractBorderPainter {
    private final Device device;
    private final RGB    rgb;
    private final Point  lineWidth;
    private final Point  borderWidth;
    private final boolean _horizontalOnly;

    LineBorderPainter(CustomLineBorder border, Device device, GC gc, boolean horizontalOnly) {
        Util.notNull(border, device, gc);
        this.rgb = border.rgb;
        this.device = device;
        this._horizontalOnly = horizontalOnly;

        int lineWidthPoints = border.getLineWidth();
        int borderWidthPoints = border.getGapSize();

        Point dpi = device.getDPI();
        lineWidth = new Point(Math.round(lineWidthPoints * dpi.x / 72f), Math.round(lineWidthPoints * dpi.y / 72f));
        borderWidth = new Point(Math.round(borderWidthPoints * dpi.x / 72f), Math.round(borderWidthPoints * dpi.y / 72f));
    }

    public int getLeft() {
        return borderWidth.x;
    }

    public int getRight() {
        return borderWidth.x;
    }

    public int getTop(boolean open) {
        return open ? 0 : borderWidth.y;
    }

    public int getBottom(boolean open) {
        return open ? 0 : borderWidth.y;
    }

    public void paint(GC gc, int x, int y, int width, int height, boolean topOpen, boolean bottomOpen) {
        Color oldColor = gc.getBackground();

        try {
            gc.setBackground(ResourcePool.forDevice(device).getColor(rgb));

            // Left & right (vertical)
            if (!_horizontalOnly) {
                gc.fillRectangle(x, y, lineWidth.x, height);
                gc.fillRectangle(x + width - lineWidth.x, y, lineWidth.x, height);
            }

            if (_horizontalOnly) {
                // Top & bottom (horizontal)
                if (!topOpen) gc.fillRectangle(x, y, width, lineWidth.y);
                if (!bottomOpen) gc.fillRectangle(x, y + height - lineWidth.y, width, lineWidth.y);
            }
        } finally {
            gc.setBackground(oldColor);
        }
    }

    public Point getOverlap() {
        return new Point(lineWidth.x, lineWidth.y);
    }

    public void dispose() {
    } // Shared resources -- nothing to dispose

}
