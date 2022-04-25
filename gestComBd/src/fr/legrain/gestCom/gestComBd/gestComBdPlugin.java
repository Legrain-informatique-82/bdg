package fr.legrain.gestCom.gestComBd;

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

import fr.legrain.gestCom.Appli.Const;

/**
 * The main plugin class to be used in the desktop.
 */
public class gestComBdPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "gestComBd";

	//The shared instance.
	private static gestComBdPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public gestComBdPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		//context.registerService("abc", Const.getProjectScopeContext(), null);
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
	public static gestComBdPlugin getDefault() {
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
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
