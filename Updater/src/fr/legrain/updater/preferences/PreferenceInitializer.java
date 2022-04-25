package fr.legrain.updater.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.updater.UpdaterPlugin;

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
		IPreferenceStore store = UpdaterPlugin.getDefault().getPreferenceStore();
		if(LgrMess.isDEVELOPPEMENT()){
			store.setDefault(PreferenceConstants.P_SERVEUR_POP,"mail.bureau-gestion.fr");
			store.setDefault(PreferenceConstants.P_USER,"usr1");
			store.setDefault(PreferenceConstants.P_PASS,"pwd01tt");		
		}else{
			store.setDefault(PreferenceConstants.P_SERVEUR_POP,"mail.bureau-gestion.fr");
			store.setDefault(PreferenceConstants.P_USER,"");
			store.setDefault(PreferenceConstants.P_PASS,"***");
		}
	}

}
