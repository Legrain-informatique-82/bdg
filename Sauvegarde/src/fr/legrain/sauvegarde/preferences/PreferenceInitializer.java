package fr.legrain.sauvegarde.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.sauvegarde.SauvegardePlugin;

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
		IPreferenceStore store = SauvegardePlugin.getDefault().getPreferenceStore();
		UtilWorkspace uw = new UtilWorkspace();
		//sauvegarde
//		store.setDefault(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER,uw.openProjectLocationPath());
		store.setDefault(PreferenceConstants.P_EMPLACEMENT,Platform.getInstanceLocation().getURL().getFile());
		//restauration
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			store.setDefault(PreferenceConstants.P_REP_DEST,"C:/LGRDOSS/BureauDeGestion/");
		}else if(Platform.getOS().equals(Platform.OS_LINUX) 
				||Platform.getOS().equals(Platform.OS_MACOSX)){
			store.setDefault(PreferenceConstants.P_REP_DEST,Platform.getInstanceLocation().getURL().getFile());
		}
		
//		store.setDefault(PreferenceConstants.P_DOSSIER_A_EFFACER,"C:/LGRDOSS/BureauDeGestion/dossier");
		store.setDefault(PreferenceConstants.EMPLACEMENT_SAUVEGARDE_FERMETURE,"C:/LGRDOSS/BureauDeGestion/sauvegardesFermeture/");
		store.setDefault(PreferenceConstants.sauvegardeFermeture,"false");
	}

}
