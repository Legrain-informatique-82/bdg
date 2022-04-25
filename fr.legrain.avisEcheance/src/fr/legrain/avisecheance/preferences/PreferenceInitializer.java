package fr.legrain.avisecheance.preferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.avisecheance.PlugInAvisEcheance;
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
		initDefautProperties();
		//initValuesProperties();
	}

	public static void initDefautProperties(){
		try {
			IPreferenceStore store = PlugInAvisEcheance.getDefault().getPreferenceStore();
			store.setDefault(PreferenceConstants.COUPURE_LIGNE_EDITION,54);
			store.setDefault(PreferenceConstants.PAGE_BREAK_MAXI,36);
			store.setDefault(PreferenceConstants.PAGE_BREAK_TOTAUX,27);
			store.setDefault(PreferenceConstants.afficheEditionImprimer, true);
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			store.setDefault(PreferenceConstants.IMPRIMER_AUTO, false);
			if (!listeGestCode.isEmpty()){
				store.setDefault(PreferenceConstants.TA_DOC_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_DOC_FIXE_1));
				store.setDefault(PreferenceConstants.TA_DOC_EXO,listeGestCode.getString(PreferenceConstants.TA_DOC_EXO));
				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_DOC_COMPTEUR);
				String value="";
				for (int i = 0; i < liste.size(); i++) {
					value+=liste.get(i);
					if(i<liste.size()-1) value+=",";
				}
				store.setDefault(PreferenceConstants.TA_DOC_COMPTEUR,value);
				store.setDefault(PreferenceConstants.COMMENTAIRE,Const.finDeLigne+Const.finDeLigne);
				
				//parametrage Editions Specifiques
				store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ConstEdition.FICHE_FILE_REPORT_AVIS_ECHEANCE);
				store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,true);
				store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE,true);


			}
		} catch (IOException e) {
			logger.error("", e);
		} catch (ConfigurationException e) {
			logger.error("", e);
		}
	}
	
	public static void initValuesProperties(){
		try {
			IPreferenceStore store = PlugInAvisEcheance.getDefault().getPreferenceStore();
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			
			if (!listeGestCode.isEmpty()){				
				store.setValue(PreferenceConstants.TA_DOC_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_DOC_FIXE_1));
				store.setValue(PreferenceConstants.TA_DOC_EXO,listeGestCode.getString(PreferenceConstants.TA_DOC_EXO));

				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_DOC_COMPTEUR);
				String value="";
				for (int i = 0; i < liste.size()-1; i++) {
					value+=liste.get(i);
					if(i<liste.size()-1) value+=",";
				}
				store.setValue(PreferenceConstants.TA_DOC_COMPTEUR,value);
				
}
			} catch (IOException e) {
				logger.error("", e);
			} catch (ConfigurationException e) {
				logger.error("", e);
			} 
	}
}
