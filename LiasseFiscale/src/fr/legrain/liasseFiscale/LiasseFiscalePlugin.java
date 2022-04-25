package fr.legrain.liasseFiscale;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fr.legrain.liasseFiscale.dossier.UtilWorkspaceLiasse;

/**
 * The main plugin class to be used in the desktop.
 */
public class LiasseFiscalePlugin extends AbstractUIPlugin {

	//The shared instance.
	private static LiasseFiscalePlugin plugin;
	
	/**
	 * The constructor.
	 */
	public LiasseFiscalePlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		UtilWorkspaceLiasse uw = new UtilWorkspaceLiasse();
        uw.verifyWorkplace();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static LiasseFiscalePlugin getDefault() {
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
		return AbstractUIPlugin.imageDescriptorFromPlugin("LiasseFiscale", path);
	}
}
