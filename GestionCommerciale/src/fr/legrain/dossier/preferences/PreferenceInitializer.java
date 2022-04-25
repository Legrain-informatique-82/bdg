package fr.legrain.dossier.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.gestionCommerciale.UtilWorkspace;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		initDefaut();
	}

	
	public static void initDefaut(){
		IPreferenceStore store = GestionCommercialePlugin.getDefault().getPreferenceStore();
		UtilWorkspace uw = new UtilWorkspace();
		store.setDefault(PreferenceConstants.P_EMPLACEMENT,Platform.getInstanceLocation().getURL().getFile().replaceFirst("/",""));
	}
}
