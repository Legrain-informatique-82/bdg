package fr.legrain.updater;

import org.eclipse.ui.plugin.*;
//import org.eclipse.equinox.internal.provisional.p2.ui.policy.Policy;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

//import fr.legrain.updater.p2.CloudPolicy;

/**
 * The main plugin class to be used in the desktop.
 */
public class UpdaterPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static UpdaterPlugin plugin;

	public static BundleContext bundleContext;
	
	ServiceRegistration policyRegistration;

	
	/**
	 * The constructor.
	 */
	public UpdaterPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		bundleContext = context;
		plugin = this;
		/// XXX register the p2 UI policy
		registerP2Policy(context);

	}
	
	private void registerP2Policy(BundleContext context) {
//		policyRegistration = context.registerService(Policy.class.getName(), new CloudPolicy(), null);
	}


	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		// XXX unregister the UI policy
		policyRegistration.unregister();
		policyRegistration = null;
		super.stop(context);

	}

	/**
	 * Returns the shared instance.
	 */
	public static UpdaterPlugin getDefault() {
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
		return AbstractUIPlugin.imageDescriptorFromPlugin("Updater", path);
	}
}
