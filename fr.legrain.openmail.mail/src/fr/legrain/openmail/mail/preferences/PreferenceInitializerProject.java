package fr.legrain.openmail.mail.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.openmail.mail.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializerProject extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStoreProject();

		store.setDefault(PreferenceConstantsProject.LOGIN,"");
		store.setDefault(PreferenceConstantsProject.PASSWORD,"");
		store.setDefault(PreferenceConstantsProject.USE_OPENMAIL,true);
		store.setDefault(PreferenceConstantsProject.EXPEDITEUR_EMAIL,"");
		store.setDefault(PreferenceConstantsProject.REPONDRE_A_EMAIL,"");
		store.setDefault(PreferenceConstantsProject.EXPEDITEUR_SMS,"");
		
		store.setDefault(PreferenceConstantsProject.TYPE_TELEPHONE_SMS,"P");

	}

}
