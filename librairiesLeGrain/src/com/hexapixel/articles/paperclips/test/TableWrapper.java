package com.hexapixel.articles.paperclips.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.hexapixel.articles.paperclips.data.IDataObject;
import com.hexapixel.articles.paperclips.data.IGenericTable;

public class TableWrapper implements IGenericTable {

    private Table             _table;
    private List<IDataObject> _allData;

    public TableWrapper(Table table) {
        _table = table;
        _allData = new ArrayList<IDataObject>();
    }

    @Override
    public List<IDataObject> getAllItems() {
        if (_allData.isEmpty()) {
            for (TableItem ti : _table.getItems()) {
                _allData.add(new TableItemData(ti));
            }
        }
        return _allData;
    }

    @Override
    public int getBodyColumnWidth(int colIndex) {
        return _table.getColumn(colIndex).getWidth();
    }

    @Override
    public Point[] getCellSelection() {
        List<Point> ret = new ArrayList<Point>();
        int[] sel = _table.getSelectionIndices();
        for (int i = 0; i < sel.length; i++) {
            int row = sel[i];
            for (int col = 0; col < getVisibleColumnOrder().size(); col++) {
                Point p = new Point(0, row);
                p.x = col;
                ret.add(p);
            }
        }

        Point[] toReturn = new Point[ret.size()];
        for (int i = 0; i < ret.size(); i++) {
            toReturn[i] = ret.get(i);
        }

        return toReturn;
    }

    @Override
    public int getCellSelectionCount() {
        return _table.getSelectionCount();
    }

    @Override
    public int getColumnCount() {
        return _table.getColumnCount();
    }

    @Override
    public List<Integer> getColumnOrder() {
        List<Integer> ret = new ArrayList<Integer>();
        for (Integer col : _table.getColumnOrder()) {
            ret.add(col);
        }
        return ret;
    }

    @Override
    public String getColumnTitle(int colIndex) {
        return _table.getColumn(colIndex).getText();
    }

    @Override
    public IDataObject getItem(int row) {
        return _allData.get(row);
    }

    @Override
    public List<Integer> getVisibleColumnOrder() {
        return getColumnOrder();
    }

}
