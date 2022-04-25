package fr.legrain.tiers.preferences;

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
import fr.legrain.tiers.TiersPlugin;

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
			IPreferenceStore store = TiersPlugin.getDefault().getPreferenceStore();
			
			store.setDefault(PreferenceConstants.afficheEditionImprimer, true);
			
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			if (!listeGestCode.isEmpty()){
				store.setDefault(PreferenceConstants.FIXE,listeGestCode.getString(PreferenceConstants.FIXE));
//				store.setDefault(PreferenceConstants.EXO,listeGestCode.getString(PreferenceConstants.EXO));
				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.COMPTEUR);
				String value="";
				for (int i = 0; i < liste.size(); i++) {
					value+=liste.get(i);
					if(i<liste.size()-1) value+=",";
				}
				store.setDefault(PreferenceConstants.COMPTEUR,value);
				//parametrage Editions Specifiques
				store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ConstEdition.FICHE_FILE_REPORT_TIERS);
				store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,true);
				store.setDefault(PreferenceConstants.TADR_DEFAUT,"");
				store.setDefault(PreferenceConstants.TTEL_DEFAUT,"");
				store.setDefault(PreferenceConstants.TEMAIL_DEFAUT,"");
				store.setDefault(PreferenceConstants.TWEB_DEFAUT,"");
				
				store.setDefault(PreferenceConstants.TIERS_COMPTE_COMPTALE_DEFAUT,"411");
				store.setDefault(PreferenceConstants.TIERS_SAISIE_TTC_DEFAUT,false);
				store.setDefault(PreferenceConstants.TIERS_TYPE_REP_COURRIER,"RC");
				
				store.setDefault(PreferenceConstants.TIERS_REP_COURRIER_DEFAUT,Const.C_CHEMIN_REP_DOSSIER_COMPLET+"/courrier");
				
				store.setDefault(PreferenceConstants.CORRESPONDANCE_ADRESSE_COMMERCIAL_DEFAUT,true);
				store.setDefault(PreferenceConstants.CORRESPONDANCE_ADRESSE_ADMINISTRATIF_DEFAUT,true);
				store.setDefault(PreferenceConstants.CORRESPONDANCE_EMAIL_COMMERCIAL_DEFAUT,true);
				store.setDefault(PreferenceConstants.CORRESPONDANCE_EMAIL_ADMINISTRATIF_DEFAUT,true);
				store.setDefault(PreferenceConstants.CORRESPONDANCE_TELEPHONE_COMMERCIAL_DEFAUT,true);
				store.setDefault(PreferenceConstants.CORRESPONDANCE_TELEPHONE_ADMINISTRATIF_DEFAUT,true);
			}
		} catch (IOException e) {
			logger.error("", e);
		} catch (ConfigurationException e) {
			logger.error("", e);
		}
	}

	public static void initValuesProperties(){
		try {
			IPreferenceStore store = TiersPlugin.getDefault().getPreferenceStore();
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			
			if (!listeGestCode.isEmpty()){				
				store.setValue(PreferenceConstants.FIXE,listeGestCode.getString(PreferenceConstants.FIXE));
//				store.setValue(PreferenceConstants.EXO,listeGestCode.getString(PreferenceConstants.EXO));

				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.COMPTEUR);
				String value="";
				for (int i = 0; i < liste.size()-1; i++) {
					value+=liste.get(i);
					if(i<liste.size()-1) value+=",";
				}
				store.setValue(PreferenceConstants.COMPTEUR,value);
				
}
			} catch (IOException e) {
				logger.error("", e);
			} catch (ConfigurationException e) {
				logger.error("", e);
			} 
	}

}
