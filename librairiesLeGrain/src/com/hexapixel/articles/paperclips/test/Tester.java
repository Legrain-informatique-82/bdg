package com.hexapixel.articles.paperclips.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.hexapixel.articles.paperclips.core.GridPrinter;
import com.hexapixel.articles.paperclips.core.ImageCache;

public class Tester {

    public static void main(String[] args) {
        new Tester();
    }

    public Tester() {
        // copied from Eclipse Snippet #38
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setImage(ImageCache.getImage("chart16.png"));
        shell.setLayout(new GridLayout());
        shell.setText("Hexapixel PaperClips Article");
        final Table table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
        table.setLayoutData(data);
        String[] titles = { " ", "C", "!", "Description", "Resource", "In Folder", "Location" };
        for (int i = 0; i < titles.length; i++) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
        }
        int count = 128;
        for (int i = 0; i < count; i++) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, "x");
            item.setText(1, "y");
            item.setText(2, "!");
            item.setText(3, "this stuff behaves the way I expect");
            item.setText(4, "almost everywhere");
            item.setText(5, "some.folder");
            item.setText(6, "line " + i + " in nowhere");
        }
        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }
        // -- end snippet code

        // add button for printing
        Button printButton = new Button(shell, SWT.PUSH);
        printButton.setImage(ImageCache.getImage("print.gif"));
        printButton.setText("Print Grid");
        printButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                GridPrinter gp = new GridPrinter(new PrintHandler());
                gp.printTable(new TableWrapper(table), false, true, "Print Test", shell);
            }
        });

        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }

}
