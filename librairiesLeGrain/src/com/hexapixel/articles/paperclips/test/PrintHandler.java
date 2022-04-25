package com.hexapixel.articles.paperclips.test;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.hexapixel.articles.paperclips.core.IPrintHandler;
import com.hexapixel.articles.paperclips.core.ImageCache;

public class PrintHandler implements IPrintHandler {

    @Override
    public Image getActivePrintLogo() {
        return getDefaultPrintLogo();
    }

    @Override
    public FontData getCellFontData() {
        return Display.getDefault().getSystemFont().getFontData()[0];
    }

    @Override
    public Image getDefaultPrintLogo() {
        return ImageCache.getImage("chart48.png");
    }

    @Override
    public String getFooterText() {
        return "Footer Text";
    }

    @Override
    public FontData getHeaderFontData() {
        return Display.getDefault().getSystemFont().getFontData()[0];
    }

    @Override
    public Image getImage(String fileName) {
        return ImageCache.getImage(fileName);
    }

    @Override
    public String getPrintJobName() {
        return "Print Job Name";
    }

    @Override
    public Image getPrintPreviewShellIcon() {
        return ImageCache.getImage("chart16.png");
    }

    @Override
    public boolean isPrintColumnHeaders() {
        return true;
    }

    @Override
    public boolean isPrintHorizontalLines() {
        return true;
    }

    @Override
    public boolean isPrintRowHeaders() {
        return false;
    }

    @Override
    public boolean isPrintVerticalLines() {
        return true;
    }

}