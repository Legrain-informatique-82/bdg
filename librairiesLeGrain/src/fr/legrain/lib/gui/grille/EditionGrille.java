package fr.legrain.lib.gui.grille;

import net.sf.paperclips.BigPrint;
import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridColumn;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.ImagePrint;
import net.sf.paperclips.LineBorder;
import net.sf.paperclips.PageDecoration;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.PageNumberPrint;
import net.sf.paperclips.PagePrint;
import net.sf.paperclips.PaperClips;
import net.sf.paperclips.Print;
import net.sf.paperclips.PrintJob;
import net.sf.paperclips.ScalePrint;
import net.sf.paperclips.TextPrint;
import net.sf.paperclips.ui.PrintPreview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class EditionGrille {
	
	private Shell shell;
	
	


public EditionGrille(Shell shell) {
		setShell(shell);
	}


public Shell getShell() {
	return shell;
}

public void setShell(Shell shell) {
	this.shell = shell;
}


public void print(Table table) {
		
		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay(), SWT.SHELL_TRIM);
		shell.setText("PagePrintExample.java");
		shell.setLayout(new GridLayout());
		shell.setSize(600, 800);


		
		final PrintJob job = new PrintJob("PagePrintExample.java",
				createPrint(table));
		job.setOrientation(PaperClips.ORIENTATION_LANDSCAPE);
		
		Composite buttonPanel = new Composite(shell, SWT.NONE);
		buttonPanel
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		buttonPanel.setLayout(new RowLayout(SWT.HORIZONTAL));

		final PrintPreview preview = new PrintPreview(shell, SWT.BORDER);

		Button prev = new Button(buttonPanel, SWT.PUSH);
		prev.setText("<< Prev");
		prev.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				preview.setPageIndex(Math.max(preview.getPageIndex() - 1, 0));
			}
		});

		Button next = new Button(buttonPanel, SWT.PUSH);
		next.setText("Next >>");
		next.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				preview.setPageIndex(Math.min(preview.getPageIndex() + 1,
						preview.getPageCount() - 1));
			}
		});

		Button print = new Button(buttonPanel, SWT.PUSH);
		print.setText("Print");
		print.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				
				PrintDialog dialog = new PrintDialog( getShell(), SWT.NONE );
		        PrinterData printerData = dialog.open();
		        if ( printerData != null ) {
		        	PaperClips.print(job, printerData);
		        }				
				
			}
		});

		preview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		preview.setFitHorizontal(true);
		preview.setFitVertical(true);
		preview.setPrintJob(job);

		shell.open();

	}
	
	public  Print createPrint(Table table) {
		PageDecoration header = createHeader();
		Print body = createBody(table);
		PageDecoration footer = createFooter();

		PagePrint page = new PagePrint(header, body, footer);
		page.setHeaderGap(72 / 4);
		page.setFooterGap(72 / 8);

		return page;
	}
	
	public Print createPrinthh() {
		// Using "preferred" size columns, to force the document to be wider
		// than
		// the page. In most
		// cases it is recommended to use "d" for "default" columns, which can
		// shrink when needed.
		DefaultGridLook look = new DefaultGridLook();
		look.setCellBorder(new LineBorder());
		GridPrint grid = new GridPrint(look);

		final int ROWS = 60;
		final int COLS = 10;

		for (int i = 0; i < COLS; i++)
			grid.addColumn("p");
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				grid.add(new TextPrint("Row " + r + " Col " + c));

		// Give entire grid a light green background.
		return new BigPrint(grid);
	}

	public  PageDecoration createHeader() {
		PageDecoration header = new PageDecoration() {
			public Print createPrint(PageNumber pageNumber) {
				// Only show a header on the first page
				if (pageNumber.getPageNumber() == 0) {
//					ImageData imageData = new ImageData(PagePrintExample.class
//							.getResourceAsStream("logo.png"));
//					ImagePrint image = new ImagePrint(imageData);
//					image.setDPI(300, 300);
//					return image;
				}

				return null;
			}
		};
		return header;
	}

	public  PageDecoration createFooter() {
		PageDecoration footer = new PageDecoration() {
			public Print createPrint(PageNumber pageNumber) {
				GridPrint grid = new GridPrint("d:g, r:d");
				grid.add(new TextPrint(
						"Bureau de gestion"));
				grid.add(new PageNumberPrint(pageNumber, SWT.RIGHT));
				return grid;
			}
		};
		return footer;
	}
	
	public  Print createBody(Table table) {
		return createPrintTable(table);
	}

	
	public Print createPrintTable( Table table ) {
	    // Create GridPrint with all columns at default size.

	    DefaultGridLook look = new DefaultGridLook();
	    look.setCellBorder( new LineBorder() );
	    RGB background = table.getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ).getRGB();
	    look.setHeaderBackground( background );
	    look.setFooterBackground( background );
	    

	    GridPrint grid = new GridPrint( look );

	    // Add header and footer to match table column names.
	    TableColumn[] columns = table.getColumns();
	    for ( int i = 0; i < columns.length; i++ ) {
	      TableColumn col = columns[i];

	      // Add the column to the grid with alignment applied, default width, no
	      // weight
	      grid.addColumn( new GridColumn( col.getAlignment(), SWT.DEFAULT, 0 ) );
//	      grid.addColumn("p");

	      Print cell = createCell( col.getImage(), col.getText(), SWT.CENTER );
	      grid.addHeader( cell );
	      grid.addFooter( cell );
	    }

	    // Add content rows
	    TableItem[] items = table.getItems();
	    for ( int i = 0; i < items.length; i++ ) {
	      TableItem item = items[i];
	      for ( int j = 0; j < columns.length; j++ )
	        grid.add( createCell( item.getImage( j ), item.getText( j ), columns[j].getAlignment() ) );
	    }
	    
	    return new ScalePrint(grid);
//	    return new BigPrint(grid);
	  }
	
	  public Print createCell( Image image, String text, int align ) {
		    if ( image == null )
		      return new TextPrint( text, align );

		    GridPrint grid = new GridPrint( "p, d" );
		    grid.add( new ImagePrint( image.getImageData(), image.getDevice().getDPI() ) );
		    grid.add( new TextPrint( text, align ) );
		    return grid;
		  }
	  
}
