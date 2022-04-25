package fr.legrain.tiers;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fr.legrain.tiers.preferences.PreferenceConstants;
import fr.legrain.tiers.preferences.TiersPreferencePage;

public class TiersPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static TiersPlugin plugin;
	public static final String PLUGIN_ID = "Tiers";
	/**
	 * The constructor.
	 */
	public TiersPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		//boolean test = TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_BOOLEAN);
		//Preferences preferences = new ConfigurationScope().getNode(Application.PLUGIN_ID);
	    //boolean auto_login = preferences.getBoolean(PreferenceConstants.P_BOOLEAN, true);
		//System.out.println("test : "+test);
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
	public static TiersPlugin getDefault() {
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
