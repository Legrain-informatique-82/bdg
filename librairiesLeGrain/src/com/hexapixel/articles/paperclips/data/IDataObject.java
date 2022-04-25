package com.hexapixel.articles.paperclips.data;

import org.eclipse.swt.graphics.Image;

public interface IDataObject {

    /**
     * Should return the column header text for a given column index.
     * 
     * @param colIndex Column index
     * @return Column header text
     */
    public String getColumnText(int colIndex);
    
    /**
     * Should return the column image for a given column index.
     * 
     * @param colIndex Column index
     * @return Image or null
     */
    public Image getColumnImage(int colIndex);   
}
