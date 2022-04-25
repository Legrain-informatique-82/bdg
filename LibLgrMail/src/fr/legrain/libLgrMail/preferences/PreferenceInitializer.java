
package fr.legrain.libLgrMail.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.libLgrMail.LibLgrMailPlugin;
import fr.legrain.libMessageLGR.LgrMess;

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
		IPreferenceStore store = LibLgrMailPlugin.getDefault().getPreferenceStore();
		if(LgrMess.isDEVELOPPEMENT()){
			store.setDefault(PreferenceConstants.P_SERVEUR_SMTP,"smtp.orange.fr");
			store.setDefault(PreferenceConstants.P_USER,"sa.le-grain-informatique@orange.fr");
			store.setDefault(PreferenceConstants.P_PASSWORD,"");
			store.setDefault(PreferenceConstants.P_SSL,"true");
			store.setDefault(PreferenceConstants.P_ADRESSE_MAIL_EXP,"isabelleaye@legrain.fr");
			store.setDefault(PreferenceConstants.P_ADRESSE_MAIL_DEST,"isabelleaye@legrain.fr");
		}else
		{
			store.setDefault(PreferenceConstants.P_SERVEUR_SMTP,"");
			store.setDefault(PreferenceConstants.P_USER,"");
			store.setDefault(PreferenceConstants.P_PASSWORD,"");
			store.setDefault(PreferenceConstants.P_SSL,"true");
			store.setDefault(PreferenceConstants.P_ADRESSE_MAIL_EXP,"");
			store.setDefault(PreferenceConstants.P_ADRESSE_MAIL_DEST,"support@legrain.fr");
		}
	}

}
