package fr.legrain.librairiesLeGrain;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Util;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class LibrairiesLeGrainPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static LibrairiesLeGrainPlugin plugin;
	public static final String CHECK = "CHECK";
	public static final String UNCHECK = "UNCHECK";

//	protected String CH_IMAGE_CHECK = "/icons/green-check.png";
//	protected String CH_IMAGE_NO_CHECK ="/icons/red-check.png";
	protected String CH_IMAGE_CHECK = "/icons/checkbox-checked.jpg";
	protected String CH_IMAGE_NO_CHECK ="/icons/checkbox-unchecked.jpg";

	public static ImageRegistry ir = new ImageRegistry();
	/**
	 * The constructor.
	 */
	public LibrairiesLeGrainPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	    ir.put(CHECK, LibrairiesLeGrainPlugin.getImageDescriptor(CH_IMAGE_CHECK));
	    ir.put(UNCHECK, LibrairiesLeGrainPlugin.getImageDescriptor(CH_IMAGE_NO_CHECK));
	}
	
	public static void initCheckImage(Control control) {
//		if (ir.getDescriptor(CHECK) == null) {
//			ir.put(CHECK,makeShot(control, true));
//			ir.put(UNCHECK,makeShot(control, false));
//		}
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		ir.dispose();
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static LibrairiesLeGrainPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("librairiesLeGrain", path);
	}
	
	/**
	 * http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.snippets/Eclipse%20JFace%20Snippets/org/eclipse/jface/snippets/viewers/Snippet061FakedNativeCellEditor.java?view=markup
	 * @param control
	 * @param type
	 * @return
	 */
	//private Image makeShot(Control control, boolean type) {
	private static Image makeShot(Control control, boolean type) {
		// Hopefully no platform uses exactly this color because we'll make
		// it transparent in the image.
		Color greenScreen = new Color(control.getDisplay(), 222, 223, 224);

		Shell shell = new Shell(control.getShell(), SWT.NO_TRIM);
		
		// otherwise we have a default gray color
		shell.setBackground(greenScreen);

		if( Util.isMac() ) {
			Button button2 = new Button(shell, SWT.CHECK);
			Point bsize = button2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			
			// otherwise an image is stretched by width
			bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
			bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
			button2.setSize(bsize);
			button2.setLocation(100, 100);				
		}
		
		Button button = new Button(shell, SWT.CHECK);
		button.setBackground(greenScreen);
		button.setSelection(type);

		// otherwise an image is located in a corner
		button.setLocation(1, 1);
		Point bsize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		
		// otherwise an image is stretched by width
		bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
		bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
		button.setSize(bsize);
		
		shell.setSize(bsize);

		shell.open();
		//affichage du shell en dehors de la zone d'affichage pour ne pas risquer de le voir si la machine est trop lente
		shell.setLocation(control.toDisplay(-50, -50));
//		shell.setVisible(false);
		
		GC gc = new GC(shell);
		Image image = new Image(control.getDisplay(), bsize.x, bsize.y);
		gc.copyArea(image, 0, 0);
		gc.dispose();
		shell.close();

		ImageData imageData = image.getImageData();
		imageData.transparentPixel = imageData.palette.getPixel(greenScreen
				.getRGB());

		Image img = new Image(control.getDisplay(), imageData);
		image.dispose();
		
		return img;
	}
}
