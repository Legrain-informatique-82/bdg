package fr.legrain.liasseFiscale.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.liasseFiscale.ecran.CompositeGenerator;
import fr.legrain.liasseFiscale.ecran.DescriptionDocumentXML;
import fr.legrain.liasseFiscale.wizards.TestPDF;

public class TestAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		TestPDF test = new TestPDF();
		test.test();
		test.testRectangle();
		
//		Shell shell = new Shell(window.getShell());
//		test.testGenEcran(shell,SWT.NONE);
//		shell.setSize(800,600);
//		shell.setLayout(new FillLayout());
//		shell.layout();
//		shell.setVisible(true);
		
//		testXML(); //generation ecran
		
		aa();
		
		//test();
	}
	
	public void aa() {
//		Shell shell = new Shell(window.getShell());
//		shell.setSize(800,600);
//		//Composite c = new Composite(shell,SWT.NULL);
//		Canvas ca = new Canvas(shell,SWT.NULL);
//		shell.setLayout(new FillLayout());
//		shell.layout();
//		shell.setVisible(true);
		
		Display display = Display.getCurrent();
		Shell shell = new Shell (display);
		shell.setLayout(new FillLayout());
		Image originalImage = null;
		FileDialog dialog = new FileDialog (shell, SWT.OPEN);
		dialog.setText ("Open an image file or cancel");
		String string = dialog.open ();
		if (string != null) {
			originalImage = new Image (display, string);
			
			originalImage = new Image(display,
					originalImage.getImageData().scaledTo(
							(int)(originalImage.getBounds().width*1),
							(int)(originalImage.getBounds().height*1)));
			
		}
		if (originalImage == null) {
			int width = 150, height = 200;
			originalImage = new Image (display, width, height);
			GC gc = new GC (originalImage);
			gc.fillRectangle (0, 0, width, height);
			gc.drawLine (0, 0, width, height);
			gc.drawLine (0, height, width, 0);
			gc.drawText ("Default Image", 10, 10);
			gc.dispose ();
		}
		final Image image = originalImage;
		final Point origin = new Point (0, 0);
		final Canvas canvas = new Canvas (shell, SWT.NO_BACKGROUND |
				SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
		final Text t = new Text(canvas,SWT.NULL);
		final ScrollBar hBar = canvas.getHorizontalBar ();
		hBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int hSelection = hBar.getSelection ();
				int destX = -hSelection - origin.x;
				Rectangle rect = image.getBounds ();
				canvas.scroll (destX, 0, 0, 0, rect.width, rect.height, true);
				origin.x = -hSelection;
			}
		});
		final ScrollBar vBar = canvas.getVerticalBar ();
		vBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int vSelection = vBar.getSelection ();
				int destY = -vSelection - origin.y;
				Rectangle rect = image.getBounds ();
				canvas.scroll (0, destY, 0, 0, rect.width, rect.height, true);
				origin.y = -vSelection;
			}
		});
		canvas.addListener (SWT.Resize,  new Listener () {
			public void handleEvent (Event e) {
				Rectangle rect = image.getBounds ();
				Rectangle client = canvas.getClientArea ();
				hBar.setMaximum (rect.width);
				vBar.setMaximum (rect.height);
				hBar.setThumb (Math.min (rect.width, client.width));
				vBar.setThumb (Math.min (rect.height, client.height));
				int hPage = rect.width - client.width;
				int vPage = rect.height - client.height;
				int hSelection = hBar.getSelection ();
				int vSelection = vBar.getSelection ();
				if (hSelection >= hPage) {
					if (hPage <= 0) hSelection = 0;
					origin.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					origin.y = -vSelection;
				}
				canvas.redraw ();
			}
		});
		canvas.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event e) {
				GC gc = e.gc;
				gc.drawImage (image, origin.x, origin.y);
				Rectangle rect = image.getBounds ();
				Rectangle client = canvas.getClientArea ();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					gc.fillRectangle (rect.width, 0, marginWidth, client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					gc.fillRectangle (0, rect.height, client.width, marginHeight);
				}
			}
		});
		
		Label l = new Label(canvas,SWT.NONE);
		//RN_ZCL_11
//		getUpperRightX 492.0
//		getUpperRightY 529.0
//		getHeight 18.5
//		getWidth 90.20001

		l.setText("label");
		//GridData lData = new GridData(GridData.FILL,GridData.FILL,true,false);
		//l.setLayoutData(lData);
		
		//Text t = new Text(canvas,SWT.NULL);
		t.setBounds(492+1,529,90,18-2);
		t.setText("dgfdgf");
		//GridData tData = new GridData(GridData.FILL,GridData.FILL,true,false);
		//t.setLayoutData(tData);

		//canvas.setLayout(new GridLayout(2,true));
		canvas.layout();
		
		shell.setSize (200, 150);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		originalImage.dispose();
		//display.dispose ();
	}
	
	public void test() {
		//C:\Liasse Fiscale\pdf2images
		Display display = Display.getCurrent();
		final Image image = new Image (display, "C:/Liasse Fiscale/pdf2images/a-0.bmp");
		//Color color = display.getSystemColor (SWT.COLOR_RED);
		//GC gc = new GC (image);
		//gc.setBackground (color);
		//gc.fillRectangle (image.getBounds ());
		//gc.dispose ();

		Shell shell = new Shell (display);
		shell.setLayout (new FillLayout ());
		Group group = new Group (shell, SWT.NONE);
		group.setLayout (new FillLayout ());
		group.setText ("a square");
		Canvas canvas = new Canvas (group, SWT.V_SCROLL);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (image, 0, 0);
			}
		});

		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		image.dispose ();
		//display.dispose ();
	}
	
	public void testXML() {
//		DescriptionDocumentXML doc = new DescriptionDocumentXML();
//		
//		Page p1 = new Page();
//		Page p2 = new Page();
//		Champ c1 = new Champ();
//		Champ c2 = new Champ();
//		Champ c3 = new Champ();
//		Champ c4 = new Champ();
//		
//		doc.setTitre("le titre");
//		
//		p1.setNumero(1);
//		p2.setNumero(2);
//		
//		c1.setLabel("lab1");
//		c2.setLabel("lab2");
//		c3.setLabel("lab3");
//		c4.setLabel("lab4");
//		
//		p1.getChamps().add(c1);
//		p1.getChamps().add(c2);
//		p2.getChamps().add(c3);
//		p2.getChamps().add(c4);
//		
//		doc.getPages().add(p1);
//		doc.getPages().add(p2);
//		
//		doc.saveXML("c:/doc.txt");
//		
//		DescriptionDocumentXML doc2 = new DescriptionDocumentXML();
//		doc2 = doc2.loadXML("c:/doc.txt");
//		System.out.println(doc2.getTitre());
//		System.out.println(doc2.getPages().size());
		
		
		DescriptionDocumentXML doc2 = new DescriptionDocumentXML();
		doc2 = doc2.loadXML("c:/doc.txt");
		CompositeGenerator gen = new CompositeGenerator();
		gen.setDescEcran(doc2);
		
		Shell shell = new Shell(window.getShell());
		
		/*Composite[] pages = */gen.genPage(shell,0);
		
		//shell.setData(pages[0]);
		shell.setSize(800,600);
		shell.setLayout(new FillLayout());
		shell.layout();
		shell.setVisible(true);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}

}
