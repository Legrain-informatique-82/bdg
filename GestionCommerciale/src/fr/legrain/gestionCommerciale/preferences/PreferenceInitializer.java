package fr.legrain.gestionCommerciale.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestionCommerciale.GestionCommercialePlugin;



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
		
		IPreferenceStore store = GestionCommercialePlugin.getDefault().getPreferenceStore();
//		store.setDefault(PreferenceConstants.TYPE_AFFICHAGE_AIDE,true);
		
		store.setDefault(PreferenceConstants.AFFICHAGE_INTRO,false);
		store.setDefault(PreferenceConstants.AFFICHAGE_CTRL_ESPACE,true);
		store.setDefault(PreferenceConstants.PROP_LAST_WELCOME_VERSION,"");
	}

}
