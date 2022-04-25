package com.hexapixel.articles.paperclips.core;

import net.sf.paperclips.GridPrint;
import net.sf.paperclips.PageDecoration;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.PageNumberPrint;
import net.sf.paperclips.Print;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class PrintFooter implements PageDecoration {

    private String _footerText;

    public PrintFooter(String footerText) {
        super();
        _footerText = footerText;
    }

    public Print createPrint(PageNumber pageNumber) {
        FontData fd = Display.getDefault().getSystemFont().getFontData()[0];
        GridPrint grid = new GridPrint("l:d, L:D:GROW, r:d");
        grid.add(new TextPrint(_footerText, fd));
        grid.add(new TextPrint("", fd));
        grid.add(new PageNumberPrint(pageNumber, fd, SWT.RIGHT));
        return grid;
    }
}
