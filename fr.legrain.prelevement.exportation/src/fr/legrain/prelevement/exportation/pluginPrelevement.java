package fr.legrain.prelevement.exportation;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

import fr.legrain.gestCom.Appli.Const;

/**
 * The activator class controls the plug-in life cycle
 */
public class pluginPrelevement extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.legrain.prelevement.exportation";

	// The shared instance
	private static pluginPrelevement plugin;
	private IPreferenceStore projectPreferenceStore; 
	/**
	 * The constructor
	 */
	public pluginPrelevement() {
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
	public static pluginPrelevement getDefault() {
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
	
    public IPreferenceStore getPreferenceStoreProject() {
        if(projectPreferenceStore==null) {
            projectPreferenceStore = new ScopedPreferenceStore(Const.getProjectScopeContext(),PLUGIN_ID);
            //((ScopedPreferenceStore)projectPreferenceStore).setSearchContexts(new IScopeContext[]{Const.getProjectScopeContext(),new InstanceScope()});
        }
        return projectPreferenceStore;
    }	
}
