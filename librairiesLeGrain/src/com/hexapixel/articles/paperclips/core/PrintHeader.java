package com.hexapixel.articles.paperclips.core;

import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.ImagePrint;
import net.sf.paperclips.PageDecoration;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.Print;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class PrintHeader implements PageDecoration {

    private String        _title;
    private IPrintHandler _handler;

    public PrintHeader(IPrintHandler handler, String title) {
        super();
        _title = title;
        _handler = handler;
    }

    @Override
    public Print createPrint(PageNumber arg0) {
        DefaultGridLook dgl = new DefaultGridLook(0, 3);
        GridPrint gp = new GridPrint("p, C:D:GROW", dgl);
        FontData fd = Display.getDefault().getSystemFont().getFontData()[0];
        fd.height = 14;
        fd.setStyle(SWT.BOLD);

        if (_handler.getActivePrintLogo() != null) {
            gp.add(new ImagePrint(_handler.getActivePrintLogo().getImageData(), Display.getDefault().getDPI()));
            gp.add(new TextPrint(""));
            gp.add(SWT.CENTER, new TextPrint(_title, fd, SWT.CENTER), GridPrint.REMAINDER);
        }

        return gp;
    }

}