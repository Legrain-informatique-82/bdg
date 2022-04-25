package fr.legrain.licencesara;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


public class pluginLicenceSara extends AbstractUIPlugin {

	// The plug-in ID
		public static final String PLUGIN_ID = "LicenceSara";

		// The shared instance
		private static pluginLicenceSara plugin;
		
		public pluginLicenceSara() {
			plugin = this;
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
			super.stop(context);
			plugin = null;
		}

		/**
		 * Returns the shared instance
		 *
		 * @return the shared instance
		 */
		public static pluginLicenceSara getDefault() {
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




