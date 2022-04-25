package fr.legrain.majcomptelegrainfr.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.majcomptelegrainfr.Activator;

public class PreferenceInitializerProject extends AbstractPreferenceInitializer {

	public PreferenceInitializerProject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		initDefautProperties();
	}
	public static void initDefautProperties() {
		// TODO Auto-generated method stub
		IPreferenceStore store = Activator.getDefault().getPreferenceStoreProject();
		store.setDefault(PreferenceConstantsProject.LOGIN_SERVER_FTP, "scripts");
		store.setDefault(PreferenceConstantsProject.PASSWORD_SERVER_FTP, "eThob9si");
		store.setDefault(PreferenceConstantsProject.PATH_SERVER_FTP, "legrain.fr");
		store.setDefault(PreferenceConstantsProject.PORT_SERVER_FTP, "21");
		store.setDefault(PreferenceConstantsProject.PATH_FICHIER, "majSupports.csv");
		store.setDefault(PreferenceConstantsProject.URL, "http://moncompte.legrain.fr/scripts/maj_supports.php?verif=IM4ooXa1Aey7xoojzeY6Quie");
	}
}
