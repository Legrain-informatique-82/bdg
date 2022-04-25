package com.hexapixel.articles.paperclips.core;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

public interface IPrintHandler {

    /**
     * Should return the image to be used for a given image file name.
     * 
     * @param fileName File name to return image for
     * @return Image or null
     */
    public Image getImage(String fileName);

    /**
     * The name of the Print Job as used in the Print Queue.
     * 
     * @return Print Job name
     */
    public String getPrintJobName();

    /**
     * Shell icon to be used in Print Preview dialog.
     * 
     * @return Shell icon image
     */
    public Image getPrintPreviewShellIcon();

    /**
     * FontData used in the header cells of the printed grid.
     * 
     * @return Header FontData
     */
    public FontData getHeaderFontData();

    /**
     * FontData used in the cells of the printed grid.
     * 
     * @return Cell FontData
     */
    public FontData getCellFontData();

    /**
     * Whether column headers are printed or not.
     * 
     * @return true if printed
     */
    public boolean isPrintColumnHeaders();

    /**
     * Whether row headers are printed or not.
     * 
     * @return true if printed
     */
    public boolean isPrintRowHeaders();

    /**
     * Whether we print vertical divider lines in the grid or not.
     * 
     * @return true if printed
     */
    public boolean isPrintVerticalLines();

    /**
     * Whether we print horizontal divider lines in the grid or not.
     * 
     * @return true if printed
     */
    public boolean isPrintHorizontalLines();

    /**
     * Returns the logo used in the printed document.
     * 
     * @return logo to print
     */
    public Image getDefaultPrintLogo();

    /**
     * Same as {@link #getDefaultPrintLogo()} except if it is set, it simply overrides the default logo without actually
     * replacing it.
     * 
     * @return logo to use for this print
     */
    public Image getActivePrintLogo();

    /**
     * Text to show in the footer.
     * 
     * @return Footer text
     */
    public String getFooterText();
}
