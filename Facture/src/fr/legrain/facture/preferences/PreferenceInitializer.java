package fr.legrain.facture.preferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.gestCom.Appli.Const;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends PreferenceInitializerProject {
	static Logger logger = Logger.getLogger(PreferenceInitializer.class.getName());
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
//	 */
//	public void initializeDefaultPreferences() {
//		initDefautProperties();
//		
//		//initValuesProperties();
//	}
//
//	public static void initDefautProperties(){
//		try {
//			IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStore();
//			store.setDefault(PreferenceConstants.COUPURE_LIGNE_EDITION,54);
//			store.setDefault(PreferenceConstants.PAGE_BREAK_MAXI,36);
//			store.setDefault(PreferenceConstants.PAGE_BREAK_TOTAUX,23);
//			
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_1,"15");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_2,"44");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_3,"8");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_4,"5");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_5,"9");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_6,"5");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_7,"10");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_8,"3");
//			
//			/** 08/02/2010 **/
//			store.setDefault(PreferenceConstants.afficheEditionImprimer,true);
//			/****************/		
//			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
//			
//			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
//			listeGestCode.load(file);
//			file.close();
//			if (!listeGestCode.isEmpty()){
//				store.setDefault(PreferenceConstants.TA_FACTURE_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_FACTURE_FIXE_1));
//				store.setDefault(PreferenceConstants.TA_FACTURE_EXO,listeGestCode.getString(PreferenceConstants.TA_FACTURE_EXO));
//				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_FACTURE_COMPTEUR);
//				String value="";
//				for (int i = 0; i < liste.size(); i++) {
//					value+=liste.get(i);
//					if(i<liste.size()-1) value+=",";
//				}
//				store.setDefault(PreferenceConstants.TA_FACTURE_COMPTEUR,value);
//				
//			}
//
//			//parametrage Editions Specifiques
//			store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ConstEdition.FICHE_FILE_REPORT_FACTURE);
//			store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,true);
//			store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE,true);
//
//			store.setDefault(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE,"");
//			store.setDefault(PreferenceConstants.AFF_AFFECTATION_ACOMPTE,false);
//			store.setDefault(PreferenceConstants.COMMENTAIRE,Const.finDeLigne+Const.finDeLigne);
//			store.setDefault(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT,false);
//			//store.setDefault(PreferenceConstants.AFFECTATION_STRICTE,true);
//
//		} catch (IOException e) {
//			logger.error("", e);
//		} catch (ConfigurationException e) {
//			logger.error("", e);
//		}
//	}
//	
//	public static void initValuesProperties(){
//		try {
//			IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStore();
//			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
//			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
//			listeGestCode.load(file);
//			file.close();
//			
//			if (!listeGestCode.isEmpty()){				
//				store.setValue(PreferenceConstants.TA_FACTURE_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_FACTURE_FIXE_1));
//				store.setValue(PreferenceConstants.TA_FACTURE_EXO,listeGestCode.getString(PreferenceConstants.TA_FACTURE_EXO));
//
//				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_FACTURE_COMPTEUR);
//				String value="";
//				for (int i = 0; i < liste.size()-1; i++) {
//					value+=liste.get(i);
//					if(i<liste.size()-1) value+=",";
//				}
//				store.setValue(PreferenceConstants.TA_FACTURE_COMPTEUR,value);
//
//				
//}
//			} catch (IOException e) {
//				logger.error("", e);
//			} catch (ConfigurationException e) {
//				logger.error("", e);
//			} 
//	}
}
