package generationdocumentlgr;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PluginGenerationDocumentLGR extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "GenerationDocumentLGR";

	// The shared instance
	private static PluginGenerationDocumentLGR plugin;
	
	/**
	 * The constructor
	 */
	public PluginGenerationDocumentLGR() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
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
	public static PluginGenerationDocumentLGR getDefault() {
		return plugin;
	}

}
