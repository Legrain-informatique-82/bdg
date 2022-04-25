package fr.legrain.archivageepicea.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.archivageepicea.Activator;

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
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			store.setDefault(PreferenceConstants.P_PATH_ARCHIVAGE_DOSSIER, "C:\\Archivage_epicea");	
		}
		else if(Platform.getOS().equals(Platform.OS_LINUX)){
			store.setDefault(PreferenceConstants.P_PATH_ARCHIVAGE_DOSSIER, "/home");
		} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
		else{
			
		}
	}

}
