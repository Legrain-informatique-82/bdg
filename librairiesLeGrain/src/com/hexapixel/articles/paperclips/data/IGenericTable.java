package com.hexapixel.articles.paperclips.data;

import java.util.List;

import org.eclipse.swt.graphics.Point;

public interface IGenericTable {

    /**
     * Should return a list of all visible column indexes (meaning: don't include hidden columns
     * unless you want them printed too).
     * 
     * @return List of visible column indexes
     */
    public List<Integer> getVisibleColumnOrder();
    
    /**
     * Should return a list of all selected cells. X represents the horizontal axis and Y the vertical axis of the grid.
     * 
     * @return Array of selected points
     */
    public Point [] getCellSelection();
    
    /**
     * How many cells are selected in the grid.
     * 
     * @return Cell selection count
     */
    public int getCellSelectionCount();
    
    /**
     * How many columns there are in the grid.
     * 
     * @return Column count
     */
    public int getColumnCount();
    
    /**
     * List of column indexes in the order they are in - in the grid.
     * 
     * @return Order of column indexes
     */
    public List<Integer> getColumnOrder();
    
    /**
     * Should return the width of the given column index.
     *  
     * @param colIndex Column index
     * @return Width of column
     */
    public int getBodyColumnWidth(int colIndex);
    
    /**
     * Should return the full column title text of a given column index.
     * 
     * @param colIndex Column index
     * @return Column title text
     */
    public String getColumnTitle(int colIndex);
    
    /**
     * Should return the row at the given row index.
     * 
     * @param row Row index
     * @return Row data
     */
    public IDataObject getItem(int row);
    
    /**
     * Should return all items in the grid (don't include hidden ones unless you wan them printed).
     * 
     * @return List of all items
     */
    public List<IDataObject> getAllItems();
}
