package com.hexapixel.articles.paperclips.test;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableItem;

import com.hexapixel.articles.paperclips.data.IDataObject;

/**
 * Wrapper class to wrap a TableItem in the IDataObject interface
 * 
 * @author Hexapixel
 *
 */
public class TableItemData implements IDataObject {

    private TableItem _ti;

    public TableItemData(TableItem ti) {
        _ti = ti;
    }

    @Override
    public Image getColumnImage(int colIndex) {
        return _ti.getImage(colIndex);
    }

    @Override
    public String getColumnText(int colIndex) {
        return _ti.getText(colIndex);
    }

}
