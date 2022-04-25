package fr.legrain.gestCom.librairiesEcran.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;

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
		IPreferenceStore store = LibrairiesEcranPlugin.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.AFFICHAGE_MESSAGE_EXTENSION_OPTIONNEL,true);
	}

}
