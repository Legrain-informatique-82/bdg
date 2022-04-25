package fr.legrain.liasseFiscale;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import fr.legrain.liasseFiscale.wizards.UtilImage;

/**
 * Demonstrate usage of custom toolstips for controls
 * 
 * @author Tom Schindl
 * 
 */
public class Test {
	protected class MyToolTip extends ToolTip {
		
		private Shell parentShell;
		
		private String headerText = "ToolTip-Header";
		
		public static final String HEADER_BG_COLOR = Policy.JFACE + ".TOOLTIP_HEAD_BG_COLOR";
		
		public static final String HEADER_FG_COLOR = Policy.JFACE + ".TOOLTIP_HEAD_FG_COLOR";
		
		public static final String HEADER_FONT = Policy.JFACE + ".TOOLTIP_HEAD_FONT";
		
		public static final String HEADER_CLOSE_ICON = Policy.JFACE + ".TOOLTIP_CLOSE_ICON";
		public static final String HEADER_HELP_ICON = Policy.JFACE + ".TOOLTIP_HELP_ICON";
		
		public MyToolTip(Control control) {
			super(control);
			this.parentShell = control.getShell();
		}

		protected Composite createToolTipContentArea(Event event,
				Composite parent) {
			JFaceResources.getColorRegistry().put(MyToolTip.HEADER_BG_COLOR, new RGB(255,255,255));
			JFaceResources.getFontRegistry().put(MyToolTip.HEADER_FONT, JFaceResources.getFontRegistry().getBold(JFaceResources.getDefaultFont().getFontData()[0].getName()).getFontData());

			
			JFaceResources.getImageRegistry().put(MyToolTip.HEADER_CLOSE_ICON,ImageDescriptor.createFromFile(Test.class, "logo_lgr_16.png"));
			JFaceResources.getImageRegistry().put(MyToolTip.HEADER_HELP_ICON,ImageDescriptor.createFromFile(Test.class, "sample.gif"));

			Composite comp = new Composite(parent,SWT.NONE);
			
			GridLayout gl = new GridLayout(1,false);
			gl.marginBottom=0;
			gl.marginTop=0;
			gl.marginHeight=0;
			gl.marginWidth=0;
			gl.marginLeft=0;
			gl.marginRight=0;
			gl.verticalSpacing=1;
			comp.setLayout(gl);
			
			Composite topArea = new Composite(comp,SWT.NONE);
			GridData data = new GridData(SWT.FILL,SWT.FILL,true,false);
			data.widthHint=200;
			topArea.setLayoutData(data);
			topArea.setBackground(JFaceResources.getColorRegistry().get(HEADER_BG_COLOR));
			
			gl = new GridLayout(2,false);
			gl.marginBottom=2;
			gl.marginTop=2;
			gl.marginHeight=0;
			gl.marginWidth=0;
			gl.marginLeft=5;
			gl.marginRight=2;
			
			topArea.setLayout(gl);
			
			Label l = new Label(topArea,SWT.NONE);
			l.setText(headerText);
			l.setBackground(JFaceResources.getColorRegistry().get(HEADER_BG_COLOR));
			l.setFont(JFaceResources.getFontRegistry().get(HEADER_FONT));
			l.setForeground(JFaceResources.getColorRegistry().get(HEADER_FG_COLOR));
			l.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			Composite iconComp = new Composite(topArea,SWT.NONE);
			iconComp.setLayoutData(new GridData());
			iconComp.setLayout(new GridLayout(2,false));
			iconComp.setBackground(JFaceResources.getColorRegistry().get(HEADER_BG_COLOR));
			
			gl = new GridLayout(2,false);
			gl.marginBottom=0;
			gl.marginTop=0;
			gl.marginHeight=0;
			gl.marginWidth=0;
			gl.marginLeft=0;
			gl.marginRight=0;
			iconComp.setLayout(gl);
			
//			Label helpIcon = new Label(iconComp,SWT.NONE);
//			helpIcon.setBackground(JFaceResources.getColorRegistry().get(HEADER_BG_COLOR));
//			helpIcon.setImage(JFaceResources.getImage(HEADER_HELP_ICON));
//			helpIcon.addMouseListener(new MouseAdapter() {
//
//				public void mouseDown(MouseEvent e) {
//					hide();
//					openHelp();
//				}
//			});
//			
//			
//			Label closeIcon = new Label(iconComp,SWT.NONE);
//			closeIcon.setBackground(JFaceResources.getColorRegistry().get(HEADER_BG_COLOR));
//			closeIcon.setImage(JFaceResources.getImage(HEADER_CLOSE_ICON));
//			closeIcon.addMouseListener(new MouseAdapter() {
//
//				public void mouseDown(MouseEvent e) {
//					parentShell.setFocus();
//					hide();
//				}
//			});
			
			createContentArea(comp).setLayoutData(new GridData(GridData.FILL_BOTH));
			
			return comp;
		}
		
		protected Composite createContentArea(Composite parent) {
			return new Composite(parent,SWT.NONE);
		}
		
		protected void openHelp() {
			parentShell.setFocus();
		}
	}

	public Test(final Shell parent) {
		JFaceResources.getColorRegistry().put(MyToolTip.HEADER_BG_COLOR, new RGB(255,255,255));
		JFaceResources.getFontRegistry().put(MyToolTip.HEADER_FONT, JFaceResources.getFontRegistry().getBold(JFaceResources.getDefaultFont().getFontData()[0].getName()).getFontData());

		
		JFaceResources.getImageRegistry().put(MyToolTip.HEADER_CLOSE_ICON,ImageDescriptor.createFromFile(Test.class, "logo_lgr_16.png"));
		JFaceResources.getImageRegistry().put(MyToolTip.HEADER_HELP_ICON,ImageDescriptor.createFromFile(Test.class, "sample.gif"));
		
		final String testImage = "/donnees/Projet/Java/Eclipse/GestionCommerciale/LiasseFiscale/documents/liasse/2002/agricole/images/agri-12.png";
		
		UtilImage img = new UtilImage();
		ScrolledComposite control = null;
		try {
			control = img.imageBackgroundSC(parent,parent.getShell().getDisplay(),new FileInputStream(testImage),400,400);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FillLayout layout = new FillLayout();
		parent.setLayout(layout);
		parent.layout();
//		final Canvas canvas = new Canvas (parent
//				,
//				SWT.NO_BACKGROUND
//				| 
//				SWT.NO_REDRAW_RESIZE
//				| SWT.H_SCROLL | SWT.V_SCROLL 
//				);
		//canvas.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		//Text text = new Text(canvas,SWT.BORDER);
		Text text = new Text((Composite)control.getContent(),SWT.CENTER ); 
		text.setBounds(0,0,50,30);
		text.setText("Hello World");
		text.setEditable(false);
		
//		canvas.addListener (SWT.Paint, new Listener () {
//			public void handleEvent (Event e) {
//				GC gc = e.gc;
//				Image image = null;
//				image = new Image (parent.getDisplay(),testImage );
//				gc.drawImage (image, 0,0);
//				Rectangle rect = image.getBounds ();
//				Rectangle client = canvas.getClientArea();
//				int marginWidth = client.width - rect.width;
//				if (marginWidth > 0) {
//					gc.fillRectangle (rect.width, 0, marginWidth, client.height);
//				}
//				int marginHeight = client.height - rect.height;
//				if (marginHeight > 0) {
//					gc.fillRectangle (0, rect.height, client.width, marginHeight);
//				}
//				
//			}
//		});
////		FillLayout layout2 = new FillLayout();
////		
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 4;
// 		canvas.setLayout(layout2);
//		canvas.layout();

		MyToolTip myTooltipLabel = new MyToolTip(text) {

			protected Composite createContentArea(Composite parent) {
				Composite comp = super.createContentArea(parent);
				comp.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
				FillLayout layout = new FillLayout();
				layout.marginWidth=5;
				comp.setLayout(layout);
				
				Table table = new Table(comp, SWT.NONE);
			TableColumn colonne1 = new TableColumn(table, SWT.CENTER,0);
				colonne1.setText("N°");
				TableColumn colonne2 = new TableColumn(table, SWT.CENTER,1);
				colonne2.setText("Intitulé");
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				//for (int i=0;i<monVecteur.size();i++) {

					// Création d'une nouvelle ligne
					TableItem ligne = new TableItem(table,SWT.NONE);

					// Remplissage de la ligne nouvellement créé
				//	String[] valeurs = (String[])monVecteur.get(i);
					String[] valeurs = new String[]{"a","b"};
					ligne.setText(valeurs);
				//	}
					colonne1.pack();
					colonne2.pack();
		
				Link l = new Link(comp,SWT.NONE);
				l.setText("This a custom tooltip you can: \n- pop up any control you want\n- define delays\n - ... \nGo and get Eclipse M4 from <a>http://www.eclipse.org</a>");
				l.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
				l.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						openURL();
					}
				});
				return comp;
			}
			
			protected void openURL() {
				MessageBox box = new MessageBox(parent,SWT.ICON_INFORMATION);
				box.setText("Eclipse.org");
				box.setMessage("Here is where we'd open the URL.");
				box.open();
			}

			protected void openHelp() {
				MessageBox box = new MessageBox(parent,SWT.ICON_INFORMATION);
				box.setText("Info");
				box.setMessage("Here is where we'd show some information.");
				box.open();
			}
			
		};
		myTooltipLabel.setShift(new Point(-5, -5));
		myTooltipLabel.setHideOnMouseDown(false);
		myTooltipLabel.activate();

//		text = new Text(canvas,SWT.BORDER);
//		text.setText("Hello World");
//		DefaultToolTip toolTip = new DefaultToolTip(text);
//		toolTip.setText("Hello World\nHello World");
//		toolTip.setBackgroundColor(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
//		
//		Button b = new Button(canvas,SWT.PUSH);
//		b.setText("Popup on press");
//		
//		final DefaultToolTip toolTipDelayed = new DefaultToolTip(b,ToolTip.RECREATE,true);
//		toolTipDelayed.setText("Hello World\nHello World");
//		toolTipDelayed.setBackgroundColor(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
//		toolTipDelayed.setHideDelay(2000);
//		
//		b.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				toolTipDelayed.show(new Point(0,0));
//			}
//		});
		
		
	}

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());
		new Test(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}

