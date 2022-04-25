package fr.legrain.openmail.mail;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fr.legrain.openmail.mail.preferences.PreferenceInitializerProject;
import fr.legrain.services.IServicePreferenceDossier;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	static Logger logger = Logger.getLogger(Activator.class.getName());

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.legrain.openmail.mail"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private IPreferenceStore projectPreferenceStore;
	private IScopeContext s = null;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		try {
			ServiceReference<?> serviceReference = context.getServiceReference(IServicePreferenceDossier.class.getName());
			if(serviceReference!=null) {
				IServicePreferenceDossier service = (IServicePreferenceDossier) context.getService(serviceReference); 
				s = service.getProjectScopeContext();
				new PreferenceInitializerProject().initializeDefaultPreferences();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public IPreferenceStore getPreferenceStoreProject() {
		
		if(projectPreferenceStore==null) {
			if(s!=null) {
				//projectPreferenceStore = new ScopedPreferenceStore(Const.getProjectScopeContext(),PLUGIN_ID);
				projectPreferenceStore = new ScopedPreferenceStore(s,PLUGIN_ID);
				logger.info("Preference au niveau du projet");
			} else {
				logger.info("Pas de preference au niveau du projet => stockage au niveau programme");
				projectPreferenceStore = plugin.getPreferenceStore();
			}
		}
		return projectPreferenceStore;
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
	public static Activator getDefault() {
		return plugin;
	}

}
