package fr.legrain.proforma;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

import fr.legrain.gestCom.Appli.Const;




/**
 * The activator class controls the plug-in life cycle
 */
public class proformaPlugin extends AbstractUIPlugin {
	static Logger logger = Logger.getLogger(proformaPlugin.class.getName());	
	// The plug-in ID
	public static final String PLUGIN_ID = "Proforma";

	// The shared instance
	private static proformaPlugin plugin;
	
	private IPreferenceStore projectPreferenceStore; 
	private IPreferenceStore programmePreferenceStore;
	
	/**
	 * The constructor
	 */
	public proformaPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.getWorkbench().getActiveWorkbenchWindow().
		addPerspectiveListener(new PerspectiveAdapter() {

			@Override
			//TODO a finir
			public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
				// TODO Auto-generated method stub
				super.perspectiveActivated(page, perspective);
			}	
			
			public void perspectiveClosed(IWorkbenchPage page,
		            IPerspectiveDescriptor perspective) {
				logger.debug("perspectiveClosed :"+perspective.getLabel());
		    }
			
			public void perspectiveDeactivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
				logger.debug("perspectiveDeactivated :"+perspective.getLabel());
		    }
			
		});
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
	 * Returns the shared instance.
	 */
	public static proformaPlugin getDefault() {
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
        }
        return projectPreferenceStore;
    }
	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return getPreferenceStoreProject();
	}
    public IPreferenceStore getPreferenceStore_Old() {
        // Create the preference store lazily.
        if (programmePreferenceStore == null) {
        	programmePreferenceStore = new ScopedPreferenceStore(new InstanceScope(),getBundle().getSymbolicName());

        }
        return programmePreferenceStore;
    }
}


