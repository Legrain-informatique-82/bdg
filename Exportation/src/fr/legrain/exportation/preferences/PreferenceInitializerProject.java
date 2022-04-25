package fr.legrain.exportation.preferences;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.exportation.ExportationPlugin;

public class PreferenceInitializerProject extends AbstractPreferenceInitializer {

	public PreferenceInitializerProject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ExportationPlugin.getDefault().getPreferenceStoreProject();
		File reportFile = new File(Platform.getInstanceLocation().getURL().getFile());
		store.setDefault(PreferenceConstants.REPERTOIRE_EXPORTATION,reportFile.getAbsolutePath() );
		store.setDefault(PreferenceConstants.AFFICHAGE_CTRL_ESPACE,true);
		
//		store.setDefault(PreferenceConstants.TYPE_REGLEMENT,false);
		store.setDefault(PreferenceConstants.ACOMPTES,false);
		store.setDefault(PreferenceConstants.REGLEMENT_SIMPLE,false);
		store.setDefault(PreferenceConstants.REMISE,false);
		store.setDefault(PreferenceConstants.DOCUMENTS_LIES,false);
		store.setDefault(PreferenceConstants.REGLEMENTS_LIES,false);
		store.setDefault(PreferenceConstants.POINTAGES,false);
		store.setDefault(PreferenceConstants.CENTRALISATION,false);
	}
	
	public static void initValuesProperties(){
		
		
	}
}
