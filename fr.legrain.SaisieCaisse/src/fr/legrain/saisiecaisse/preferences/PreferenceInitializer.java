package fr.legrain.saisiecaisse.preferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.saisiecaisse.saisieCaissePlugin;
import fr.legrain.saisiecaisse.ecran.PaOperation;

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
			IPreferenceStore store = saisieCaissePlugin.getDefault().getPreferenceStore();
			
				store.setDefault(PreferenceConstants.COMPTE_ACHAT,"6022");
				store.setDefault(PreferenceConstants.FIXE_OP_ACHAT,"A");
				store.setDefault(PreferenceConstants.TIERS,"");
				store.setDefault(PreferenceConstants.FOCUS_INSERTION,"2");
				//parametrage Editions Specifiques
				store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ConstEdition.FICHE_FILE_ETAT_SAISIECAISSE_JOUR);
				store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,true);

		} catch (Exception e) {
			logger.error("", e);
		}
	}


}
