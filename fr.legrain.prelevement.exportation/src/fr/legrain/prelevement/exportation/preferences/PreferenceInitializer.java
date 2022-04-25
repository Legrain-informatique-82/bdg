package fr.legrain.prelevement.exportation.preferences;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.prelevement.exportation.pluginPrelevement;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
	static Logger logger = Logger.getLogger(PreferenceInitializer.class.getName());
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		initDefautProperties();
		//initValuesProperties();
	}

	public static void initDefautProperties(){
		try {
			IPreferenceStore store = pluginPrelevement.getDefault().getPreferenceStore();

			File reportFile = new File(Platform.getInstanceLocation().getURL().getFile());
			store.setDefault(PreferenceConstants.P_PATH_EXPORTATION_BANQUE,reportFile.getAbsolutePath()+"\\Sepa_Prelevement\\" );

			
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static void initValuesProperties(){
			IPreferenceStore store = pluginPrelevement.getDefault().getPreferenceStore();
	}
	
	
}
