package fr.legrain.exportation;

import org.eclipse.ui.plugin.*;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

import fr.legrain.exportation.preferences.PreferenceInitializerProject;
import fr.legrain.gestCom.Appli.Const;

/**
 * The main plugin class to be used in the desktop.
 */
public class ExportationPlugin extends AbstractUIPlugin {

	//The shared instance.
	public static final String PLUGIN_ID = "Exportation";
	private static ExportationPlugin plugin;
	private IPreferenceStore projectPreferenceStore; 
	/**
	 * The constructor.
	 */
	public ExportationPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        new PreferenceInitializerProject().initializeDefaultPreferences(); 
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
	public static ExportationPlugin getDefault() {
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
		return AbstractUIPlugin.imageDescriptorFromPlugin("Exportation", path);
	}

    public IPreferenceStore getPreferenceStoreProject() {
        if(projectPreferenceStore==null) {
            projectPreferenceStore = new ScopedPreferenceStore(Const.getProjectScopeContext(),PLUGIN_ID);
            //((ScopedPreferenceStore)projectPreferenceStore).setSearchContexts(new IScopeContext[]{Const.getProjectScopeContext(),new InstanceScope()});
        }
        return projectPreferenceStore;
    } 
}
