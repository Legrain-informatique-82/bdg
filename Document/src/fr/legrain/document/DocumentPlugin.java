package fr.legrain.document;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fr.legrain.document.preferences.PreferenceConstants;
import fr.legrain.document.preferences.PreferenceInitializer;


public class DocumentPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "Document";
	public static final String C_COMMAND_CREATION_DOC_ID = "fr.legrain.document.creationDoc";

	// The shared instance
	private static DocumentPlugin plugin;
	
	/**
	 * The constructor
	 */
	public DocumentPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		if(getDefault().getPreferenceStore().getString(PreferenceConstants.TA_REG_FIXE_1)==null
				||getDefault().getPreferenceStore().getString(PreferenceConstants.TA_REG_FIXE_1)==""){
			PreferenceInitializer.initDefautProperties();
			PreferenceInitializer.remplieProperties();
		}		
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DocumentPlugin getDefault() {
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
