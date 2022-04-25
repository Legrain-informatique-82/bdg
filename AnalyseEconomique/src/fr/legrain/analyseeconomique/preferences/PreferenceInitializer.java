package fr.legrain.analyseeconomique.preferences;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.analyseeconomique.actions.ConstAnalyseEco;

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
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		//store.setDefault(PreferenceConstants.P_SERVEUR_ANALYSE_ECO, "http://localhost/");
		store.setDefault(PreferenceConstants.P_SERVEUR_ANALYSE_ECO, "http://www.cogerea.net/cogere/index.php");
		store.setDefault(PreferenceConstants.P_OUVERTURE_SERVEUR_AUTO, "true");
		store.setDefault(PreferenceConstants.P_ENVOI_FTP_AUTO, "true");
		
		store.setDefault(PreferenceConstants.P_CODE_COMPTABLE_SERVEUR, "");
		store.setDefault(PreferenceConstants.P_LOGIN_COMPTABLE_SERVEUR, "");
		store.setDefault(PreferenceConstants.P_PASSWORD_COMPTABLE_SERVEUR, "");
		
		store.setDefault(PreferenceConstants.P_SERVEUR_FTP, "cogerea.net");
		store.setDefault(PreferenceConstants.P_LOGIN_FTP, "ftpcogerea"); //ftpcogerea
		store.setDefault(PreferenceConstants.P_PASSWORD_FTP, "pwd0521cog"); //pwd0521cog
		
		store.setDefault(PreferenceConstants.P_AGENCE, ConstAnalyseEco.C_LISTE_AGENCE[0][1]); 
		
		if(Platform.getOS().equals(Platform.OS_WIN32))
			store.setDefault(PreferenceConstants.P_CHEMIN_EXPORT_COMPTA_LIASSE, "C:\\Program Files\\LGR\\Epicea\\Exportation"); 
		else if(Platform.getOS().equals(Platform.OS_LINUX))
			store.setDefault(PreferenceConstants.P_CHEMIN_EXPORT_COMPTA_LIASSE, File.listRoots()[0].getAbsolutePath()); 
		else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}

	}

}
