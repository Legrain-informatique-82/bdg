package fr.legrain.exportation.preferences;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.exportation.ExportationPlugin;
import fr.legrain.libMessageLGR.LgrMess;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ExportationPlugin.getDefault().getPreferenceStore();
		File reportFile = new File(Platform.getInstanceLocation().getURL().getFile());
		store.setDefault(PreferenceConstants.REPERTOIRE_EXPORTATION,reportFile.getAbsolutePath() );
		store.setDefault(PreferenceConstants.AFFICHAGE_CTRL_ESPACE,true);
		
//		store.setDefault(PreferenceConstants.TYPE_REGLEMENT,true);
		store.setDefault(PreferenceConstants.ACOMPTES,true);
		store.setDefault(PreferenceConstants.REGLEMENT_SIMPLE,true);
		store.setDefault(PreferenceConstants.REMISE,true);
		store.setDefault(PreferenceConstants.DOCUMENTS_LIES,false);
		store.setDefault(PreferenceConstants.REGLEMENTS_LIES,true);
		store.setDefault(PreferenceConstants.POINTAGES,true);
	}
}
