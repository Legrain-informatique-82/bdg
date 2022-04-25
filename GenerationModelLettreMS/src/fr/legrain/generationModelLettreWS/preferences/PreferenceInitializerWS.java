package fr.legrain.generationModelLettreWS.preferences;

import java.io.File;

import fr.legrain.gestCom.Appli.Const;
import generationmodellettrems.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceInitializerWS extends AbstractPreferenceInitializer {

	
	public PreferenceInitializerWS() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.PATH_WORD_OFFICE, "");
		store.setDefault(PreferenceConstants.PATH_SAVE_PUBLIPOSTAGE_WO, 
				new File(Const.C_REPERTOIRE_BASE+Const.C_NOM_PROJET_TMP).getPath());
//		store.setDefault(PreferenceConstants.OPTION_AFFICHAGE, false);
	}

}
