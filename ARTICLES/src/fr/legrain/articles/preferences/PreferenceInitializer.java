package fr.legrain.articles.preferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Appli.Const;

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
		IPreferenceStore store = ArticlesPlugin.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.REGENERATION_URL_REWRITING_POUR_CATALOGUE_WEB_A_PARTIR_ECRAN_PRINCIPAL,"true");
		store.setDefault(PreferenceConstants.REGENERATION_DESCRIPTION_POUR_CATALOGUE_WEB_A_PARTIR_ECRAN_PRINCIPAL,"true");
		
		store.setDefault(PreferenceConstants.STOCKAGE_IMAGES,PreferenceConstants.VALEUR_STOCKAGE_IMAGES_ORIGINE);
		
		store.setDefault(PreferenceConstants.RETAILLE_IMAGE_TROP_GROSSE_EXPORT,"true");
		store.setDefault(PreferenceConstants.LONGUEUR_MAX_IMAGE,650); //pixel
		store.setDefault(PreferenceConstants.HAUTEUR_MAX_IMAGE,650);  //pixel
		store.setDefault(PreferenceConstants.POIDS_MAX_IMAGE,100);      //Ko
		//store.setDefault(PreferenceConstants.PREFIXE_IMAGE_RETAILLE,"th_");
		store.setDefault(PreferenceConstants.PREFIXE_IMAGE_RETAILLE,"web_");
		
		
		initDefautProperties();
		//initValuesProperties();
	}

	public static void initDefautProperties(){
		try {
			IPreferenceStore store = ArticlesPlugin.getDefault().getPreferenceStore();
			/** 08/02/2010 **/
			store.setDefault(PreferenceConstants.afficheEditionImprimer,true);
			/****************/
			
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
				store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ConstEdition.FICHE_FILE_REPORT_ARTICLES);
				store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,true);

			}
		} catch (IOException e) {
			logger.error("", e);
		} catch (ConfigurationException e) {
			logger.error("", e);
		}
	}

	public static void initValuesProperties(){
		try {
			IPreferenceStore store = ArticlesPlugin.getDefault().getPreferenceStore();
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
