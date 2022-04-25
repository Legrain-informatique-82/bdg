package fr.legrain.sauvegardeFTP.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.sauvegardeFTP.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	
	public PreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.LOGIN_SERVER_FTP, "");
		store.setDefault(PreferenceConstants.PASSWORD_SERVER_FTP, "");

	}

}
