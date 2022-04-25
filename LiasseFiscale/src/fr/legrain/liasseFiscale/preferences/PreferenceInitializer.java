package fr.legrain.liasseFiscale.preferences;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;

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
		IPreferenceStore store = LiasseFiscalePlugin.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.P_COULEUR_CALCULS, "229,229,229");
		store.setDefault(PreferenceConstants.P_COULEUR_A_SAISIR, "242,230,207");
		store.setDefault(PreferenceConstants.P_COULEUR_SAISIE, "242,230,133"); 
		
		if(Platform.getOS().equals(Platform.OS_WIN32))
			store.setDefault(PreferenceConstants.P_CHEMIN_COMPTA, "C:\\Program Files\\LGR\\Epicea\\ExportationLiasse"); 
		else if(Platform.getOS().equals(Platform.OS_LINUX))
			store.setDefault(PreferenceConstants.P_CHEMIN_COMPTA, File.listRoots()[0].getAbsolutePath()); 
		
		
		if(Platform.getOS().equals(Platform.OS_WIN32))
			store.setDefault(PreferenceConstants.P_CHEMIN_ADOBE_READER, "C:\\Program Files\\Adobe\\Reader 8.0\\Reader\\AcroRd32.exe"); 
		else if(Platform.getOS().equals(Platform.OS_LINUX))
			store.setDefault(PreferenceConstants.P_CHEMIN_ADOBE_READER, "/usr/bin/acroread"); 
		
		store.setDefault(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER,Platform.getInstanceLocation().getURL().getFile()+"liasse");
		store.setDefault(PreferenceConstants.P_EMPLACEMENT_SAUVEGARDE,Platform.getInstanceLocation().getURL().getFile());
		
		store.setDefault(PreferenceConstants.P_DOSSIER_A_EFFACER_RESTAURATION,Platform.getInstanceLocation().getURL().getFile()+"liasse");
		store.setDefault(PreferenceConstants.P_REP_DEST_RESTAURATION,Platform.getInstanceLocation().getURL().getFile());
		
		store.setDefault(PreferenceConstants.P_ARRONDI,true);
	}

}
