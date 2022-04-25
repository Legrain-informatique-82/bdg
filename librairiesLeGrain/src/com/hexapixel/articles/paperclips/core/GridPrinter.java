package com.hexapixel.articles.paperclips.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.paperclips.BigPrint;
import net.sf.paperclips.CellBackgroundProvider;
import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.ImagePrint;
import net.sf.paperclips.LineBorder;
import net.sf.paperclips.PagePrint;
import net.sf.paperclips.PaperClips;
import net.sf.paperclips.PrintJob;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.hexapixel.articles.paperclips.data.IDataObject;
import com.hexapixel.articles.paperclips.data.IGenericTable;

/**
 * This class deals with printing a Grid to the printer, 
 * including showing a preview dialog prior to printing.
 * 
 * @author Hexapixel
 */
public class GridPrinter {

    private static final String EXPANDABLE_COLUMN      = "d:g";
    private static final String DOT                    = "...";
    private static final String EMPTY                  = "";
    

    // custom print images get disposed when we're finished or we'll have a memory leak
    private Image               _disposeImage;
    private boolean             _disposePrintImage     = false;

    private RGB                 _evenRows;
    private RGB                 _oddRows;
    private RGB                 _headerBgColor;

    private boolean             _printColHeaders;
    private boolean             _printVerticalLines;
    private boolean             _printHorizontalLines;

    private String              _footerText;

    private FontData            _headerFontData;
    private FontData            _cellFontData;

    private IPrintHandler       _printHandler;
    
    public GridPrinter(IPrintHandler handler) {
        _printHandler = handler;
        _printColHeaders = handler.isPrintColumnHeaders();
        _printVerticalLines = handler.isPrintVerticalLines();
        _printHorizontalLines = handler.isPrintHorizontalLines();
        _footerText = handler.getFooterText();
        _headerFontData = handler.getHeaderFontData();
        _cellFontData = handler.getCellFontData();

        _headerBgColor = new RGB(240, 240, 240);
        _oddRows = new RGB(230, 230, 230);
        _evenRows = new RGB(255, 255, 255);
    }

    public PrintResultCode printTable(IGenericTable tableImpl, boolean printSelectionOnly, boolean showPreview, String docTitle, final Shell parentShell) {
        try {
            int columnCount = tableImpl.getColumnCount();

            boolean useFullColumnWidths = false;

            StringBuffer buf = new StringBuffer();

            List<Integer> cols = null;
            List<Integer> widths = new ArrayList<Integer>();
            Map<Integer, Integer> widthMap = new HashMap<Integer, Integer>();

            // figure out size of grid (how many columns it has)
            if (printSelectionOnly) {
                if (tableImpl.getCellSelectionCount() == 0) { return PrintResultCode.ERROR_NO_DATA_TO_PRINT; }

                Point[] points = tableImpl.getCellSelection();

                cols = new ArrayList<Integer>();
                for (Point p : points) {
                    if (!cols.contains(p.x)) {
                        cols.add(p.x);
                    }
                }

                columnCount = cols.size();
            } else {
                cols = tableImpl.getVisibleColumnOrder();
                columnCount = cols.size();
            }

            // order columns that have selection in same order as table order
            // we do this as we can have a selection like this:
            //
            // - 0 1 2 3 4
            // 0 x x x   x
            // 1 x   x x 
            // 2 x x x   x
            //
            // and if we didn't know 3 came before 4 even though it doesn't have a selection
            // until row #2 we'd have an order of 0 1 2 4 3 instead of the correct 0 1 2 3 4

            // get the full order regardless of column visibility, we need the real order
            List<Integer> visibleOrder = tableImpl.getColumnOrder();
            List<Integer> correctOrderedCols = new ArrayList<Integer>();
            for (Integer correct : visibleOrder) {
                if (cols.contains(correct)) {
                    correctOrderedCols.add(correct);
                }
            }
            cols = correctOrderedCols;

            // create grid
            for (int i = 0; i < cols.size(); i++) {
                int column = cols.get(i);
                widths.add(tableImpl.getBodyColumnWidth(column));
                widthMap.put(column, tableImpl.getBodyColumnWidth(column));
                if (useFullColumnWidths) {
                    buf.append(EXPANDABLE_COLUMN);
                } else {
                    int colWidthPix = tableImpl.getBodyColumnWidth(column);
                    float pts = convertToPoints(colWidthPix);
                    buf.append("L:");
                    buf.append((int) pts);
                    buf.append(":N");
                }

                if (i != columnCount - 1) {
                    buf.append(", ");
                }
            }

            // create the printable grid
            GridPrint print = null;

            GC concatGc = new GC(parentShell);
            Font headerFont = new Font(Display.getDefault(), _headerFontData);
            Font cellFont = new Font(Display.getDefault(), _cellFontData);

            DefaultGridLook look = new DefaultGridLook(0, 0);
            if (_printHorizontalLines && _printVerticalLines) {
                look.setCellBorder(new LineBorder());
            } else if (_printHorizontalLines) {
                look.setCellBorder(new CustomLineBorder(true));
            } else if (_printVerticalLines) {
                look.setCellBorder(new CustomLineBorder(false));
            }

            print = new GridPrint(buf.toString(), look);

            look.setHeaderBackgroundProvider(new CellBackgroundProvider() {
                @Override
                public RGB getCellBackground(int row, int column, int colspan) {
                    return _headerBgColor;
                }
            });

            // Alternate between light yellow and light blue every 5 rows
            look.setBodyBackgroundProvider(new CellBackgroundProvider() {
                @Override
                public RGB getCellBackground(int row, int col, int colspan) {
                    return row % 2 == 0 ? _evenRows : _oddRows;
                }
            });

            // fill the grid
            if (printSelectionOnly) {
                Point[] points = tableImpl.getCellSelection();

                if (_printColHeaders) {
                    for (int i = 0; i < cols.size(); i++) {
                        String name = concat(tableImpl.getColumnTitle(cols.get(i)), widths.get(i), concatGc, headerFont);
                        print.addHeader(new TextPrint(name, _headerFontData));
                    }
                }

                // make a map out of what is selected on each row
                Map<Integer, List<Integer>> selMap = new HashMap<Integer, List<Integer>>();

                for (int i = 0; i < points.length; i++) {
                    Point p = points[i];

                    boolean newEntry = !selMap.containsKey(p.y);
                    List<Integer> toUse = newEntry ? new ArrayList<Integer>() : selMap.get(p.y);

                    if (!toUse.contains(p.x)) {
                        toUse.add(p.x);
                    }

                    selMap.put(p.y, toUse);
                }

                List<Integer> printedRows = new ArrayList<Integer>();

                // fill in the cells
                for (int i = 0; i < points.length; i++) {
                    Point p = points[i];

                    if (printedRows.contains(p.y)) {
                        continue;
                    }

                    for (int col : cols) {
                        // this cell is not selected, but we need to print an empty cell
                        // or alignment gets completely crazy later
                        List<Integer> selColsForRow = selMap.get(p.y);
                        if (!selColsForRow.contains(col)) {
                            print.add(tp(" "));
                            continue;
                        }

                        IDataObject gi = tableImpl.getItem(p.y);
                        String value = concat(gi.getColumnText(col), widthMap.get(col), concatGc, cellFont);
                        Image image = gi.getColumnImage(col);
                        if (image != null) {
                            print.add(new ImagePrint(image.getImageData(), Display.getDefault().getDPI()));
                        }
                        else {
                            print.add(tp(value, _cellFontData));
                        }
                    }

                    printedRows.add(p.y);
                }
            } else {
                List<IDataObject> all = tableImpl.getAllItems();

                IDataObject[] sel = new IDataObject[all.size()];
                sel = new IDataObject[all.size()];
                for (int i = 0; i < all.size(); i++)
                    sel[i] = all.get(i);

                if (_printColHeaders) {
                    for (int i = 0; i < cols.size(); i++) {
                        String name = concat(tableImpl.getColumnTitle(cols.get(i)), widths.get(i), concatGc, headerFont);
                        print.addHeader(new TextPrint(name, _headerFontData));
                    }
                }

                for (IDataObject gi : sel) {
                    for (int i = 0; i < cols.size(); i++) {
                        String colText = gi.getColumnText(cols.get(i));
                        if (colText == null) {
                            colText = "";
                        }
                        String text = concat(colText, widths.get(i), concatGc, cellFont);
                        print.add(tp(text, _cellFontData));
                    }
                }
            }

            // dispose resourcesemil    
            concatGc.dispose();
            headerFont.dispose();
            cellFont.dispose();

            // we need to space things out, it's too tight usually, so we create a wrapper to
            // force the printout over multiple pages when it doesn't fit
            BigPrint big = new BigPrint(print);

            // add header, footer, spacing
            PagePrint pp = new PagePrint(new PrintHeader(_printHandler, docTitle), big, new PrintFooter(getFooterText()));
            pp.setHeaderGap(9);
            pp.setFooterGap(18);

            if (showPreview) {
                // show preview dialog, user prints from there
                PrintPreviewDialog ppd = new PrintPreviewDialog(pp, parentShell, _printHandler);
                ppd.open();
            } else {
                PrintDialog dialog = new PrintDialog(parentShell, SWT.NONE);
                PrinterData printerData = dialog.open();

                if (printerData != null) {
                    PaperClips.print(new PrintJob("Printing", pp).setMargins(36), printerData);
                }
            }

            if (_disposePrintImage) {
                _disposeImage.dispose();
            }

            return PrintResultCode.SUCCESS;
        } catch (Exception err) {
            err.printStackTrace();
        }

        return PrintResultCode.ERROR_PRINTING;
    }

    // checks whether text needs concatenation or not
    private String concat(String str, int pixWidth, GC gc, Font f) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        gc.setFont(f);
        Point size = gc.stringExtent(str);
        // text fits, dispose & return
        if (size.x <= pixWidth) {
            return str;
        } else {
            // text doesn't fit, concatenate with ... and return
            String ret = getAvailableTextToDisplay(gc, new Rectangle(0, 0, pixWidth, 20), str);
            return ret;
        }
    }

    private String getAvailableTextToDisplay(final GC gc, final Rectangle rectangle, final String text) {
        int width = gc.textExtent(text).x;
        boolean displayDot = width > rectangle.width;

        String ret = null;

        try {
            if (displayDot) {
                BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
                StringBuffer output = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    width = gc.textExtent(line).x;
                    if (width > rectangle.width) {
                        int textLen = line.length();
                        for (int i = textLen - 1; i >= 0; i--) {
                            String temp = line.substring(0, i) + DOT;
                            width = gc.textExtent(temp).x;
                            if (width < rectangle.width) {
                                line = temp;
                                break;
                            } else if (i == 0) {
                                line = EMPTY;
                            }
                        }
                    }
                    output.append(line);
                    output.append('\n');
                }
                ret = output.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private int convertToPoints(int pixels) {
        return 72 * pixels / Display.getDefault().getDPI().x;
    }

    private TextPrint tp(String str) {
        return new TextPrint(str == null ? "" : (str.equals("") ? "-" : str));
    }

    private TextPrint tp(String str, FontData fd) {
        return new TextPrint(str == null ? "" : (str.equals("") ? "-" : str), fd);
    }

    public String getFooterText() {
        return _footerText;
    }
}
