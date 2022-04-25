package fr.legrain.gestionDossier.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestionDossier.GestionDossierPlugin;

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
		IPreferenceStore store = GestionDossierPlugin.getDefault().getPreferenceStore();
		//Création et ouverture des dossiers
		String valeur = Platform.getInstanceLocation().getURL().getFile();
		if (valeur.startsWith("/"))valeur = valeur.replaceFirst("/", "");
		store.setDefault(PreferenceConstants.P_REPERTOIRE_W,valeur);
	}

}
