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
public class PreferenceInitializerProject extends AbstractPreferenceInitializer {
	static Logger logger = Logger.getLogger(PreferenceInitializerProject.class.getName());
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
			IPreferenceStore storeProgramme = FacturePlugin.getDefault().getPreferenceStore_Old();
			IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStoreProject();
            /***/			
			Boolean imprimerAuto=false;
			if(store.contains(PreferenceConstants.IMPRIMER_AUTO)==false){;
				if(storeProgramme.contains(PreferenceConstants.IMPRIMER_AUTO)){
					store.setValue(PreferenceConstants.IMPRIMER_AUTO,storeProgramme.getBoolean(PreferenceConstants.IMPRIMER_AUTO));
				}
			}
			store.setDefault(PreferenceConstants.IMPRIMER_AUTO,imprimerAuto);
			

			/***/
			Integer coupureLigneEditionDef=54;
			if(store.contains(PreferenceConstants.COUPURE_LIGNE_EDITION)==false){;
				if(storeProgramme.contains(PreferenceConstants.COUPURE_LIGNE_EDITION)){
					store.setValue(PreferenceConstants.COUPURE_LIGNE_EDITION,storeProgramme.getInt(PreferenceConstants.COUPURE_LIGNE_EDITION));
				}
			}
			store.setDefault(PreferenceConstants.COUPURE_LIGNE_EDITION,coupureLigneEditionDef);
			
			
            /***/			
			Integer pageBreakMaxiDef=36;
			if(store.contains(PreferenceConstants.PAGE_BREAK_MAXI)==false){;
				if(storeProgramme.contains(PreferenceConstants.PAGE_BREAK_MAXI)){
					store.setValue(PreferenceConstants.PAGE_BREAK_MAXI,storeProgramme.getInt(PreferenceConstants.PAGE_BREAK_MAXI));
				}
			}
			store.setDefault(PreferenceConstants.PAGE_BREAK_MAXI,pageBreakMaxiDef);
			
			
            /***/			
			Integer pageBreakTotauxDef=23;
			if(store.contains(PreferenceConstants.PAGE_BREAK_TOTAUX)==false){;
				if(storeProgramme.contains(PreferenceConstants.PAGE_BREAK_TOTAUX)){
					store.setValue(PreferenceConstants.PAGE_BREAK_TOTAUX,storeProgramme.getInt(PreferenceConstants.PAGE_BREAK_TOTAUX));
				}
			}
			store.setDefault(PreferenceConstants.PAGE_BREAK_TOTAUX,pageBreakTotauxDef);
			
			
            /***/			
			Boolean afficheEditionImprimerDef=true;
			if(store.contains(PreferenceConstants.afficheEditionImprimer)==false){;
				if(storeProgramme.contains(PreferenceConstants.afficheEditionImprimer)){
					store.setValue(PreferenceConstants.afficheEditionImprimer,storeProgramme.getBoolean(PreferenceConstants.afficheEditionImprimer));
				}
			}
			store.setDefault(PreferenceConstants.afficheEditionImprimer,afficheEditionImprimerDef);
			
			
			/***/
			Boolean afficherSelectionEditionDef=true;
			if(store.contains(PreferenceConstants.AFFICHER_SELECTION_EDITION)==false){;
				if(storeProgramme.contains(PreferenceConstants.AFFICHER_SELECTION_EDITION)){
					store.setValue(PreferenceConstants.AFFICHER_SELECTION_EDITION,storeProgramme.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION));
				}
			}
			store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,afficherSelectionEditionDef);
			
			
			
            /***/			
			Boolean afficherSelectionEditionEditionEnListeDef=true;
			if(store.contains(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE)==false){;
				if(storeProgramme.contains(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE)){
					store.setValue(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE,storeProgramme.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE));
				}
			}
			store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE,afficherSelectionEditionEditionEnListeDef);
			

            /***/			
			String typeAdresseCorrespondanceDef="";
			if(store.contains(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE)==false){;
			if(storeProgramme.contains(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE)){
				store.setValue(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE,storeProgramme.getString(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE));
			}
			}
			store.setDefault(PreferenceConstants.TYPE_ADRESSE_CORRESPONDANCE,typeAdresseCorrespondanceDef);
			
			
			
            /***/	
			Boolean affAffectationAcompteDef=false;
			if(store.contains(PreferenceConstants.AFF_AFFECTATION_ACOMPTE)==false){;
				if(storeProgramme.contains(PreferenceConstants.AFF_AFFECTATION_ACOMPTE)){
					store.setValue(PreferenceConstants.AFF_AFFECTATION_ACOMPTE,storeProgramme.getBoolean(PreferenceConstants.AFF_AFFECTATION_ACOMPTE));
				}
			}			
			store.setDefault(PreferenceConstants.AFF_AFFECTATION_ACOMPTE,affAffectationAcompteDef);
			

			
            /***/	
			String commentaireDef=Const.finDeLigne+Const.finDeLigne;
			if(store.contains(PreferenceConstants.COMMENTAIRE)==false){;
			if(storeProgramme.contains(PreferenceConstants.COMMENTAIRE)){
				store.setValue(PreferenceConstants.COMMENTAIRE,storeProgramme.getString(PreferenceConstants.COMMENTAIRE));
			}
			}
			store.setDefault(PreferenceConstants.COMMENTAIRE,commentaireDef);
			
			
            /***/			
			String ficheFileReportDef=ConstEdition.FICHE_FILE_REPORT_FACTURE;
			if(store.contains(PreferenceConstants.P_PATH_EDITION_DEFAUT)==false){;
			if(storeProgramme.contains(PreferenceConstants.P_PATH_EDITION_DEFAUT)){
				store.setValue(PreferenceConstants.P_PATH_EDITION_DEFAUT,storeProgramme.getString(PreferenceConstants.P_PATH_EDITION_DEFAUT));
			}
			}
			store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ficheFileReportDef);		
            
            /***/
			Boolean imprimerCourriersAutoDef=false;
			if(store.contains(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT)==false){;
				if(storeProgramme.contains(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT)){
					store.setValue(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT,storeProgramme.getBoolean(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT));
				}
			}						
			store.setDefault(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT,imprimerCourriersAutoDef);
			
			
			
			/****************/		
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			if (!listeGestCode.isEmpty()){
				store.setDefault(PreferenceConstants.TA_FACTURE_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_FACTURE_FIXE_1));
				store.setDefault(PreferenceConstants.TA_FACTURE_EXO,listeGestCode.getString(PreferenceConstants.TA_FACTURE_EXO));
				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_FACTURE_COMPTEUR);
				String value="";
				for (int i = 0; i < liste.size(); i++) {
					value+=liste.get(i);
					if(i<liste.size()-1) value+=",";
				}
				store.setDefault(PreferenceConstants.TA_FACTURE_COMPTEUR,value);
				
			}


			//store.setDefault(PreferenceConstants.AFFECTATION_STRICTE,true);

//			store.setDefault(PreferenceConstants.EDITION_CHAMP_1,"15");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_2,"44");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_3,"8");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_4,"5");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_5,"9");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_6,"5");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_7,"10");
//			store.setDefault(PreferenceConstants.EDITION_CHAMP_8,"3");
			
		} catch (IOException e) {
			logger.error("", e);
		} catch (ConfigurationException e) {
			logger.error("", e);
		}
	}
	
	public static void initValuesProperties(){
		try {
			IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStoreProject();
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			
			if (!listeGestCode.isEmpty()){				
				store.setValue(PreferenceConstants.TA_FACTURE_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_FACTURE_FIXE_1));
				store.setValue(PreferenceConstants.TA_FACTURE_EXO,listeGestCode.getString(PreferenceConstants.TA_FACTURE_EXO));

				List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_FACTURE_COMPTEUR);
				String value="";
				for (int i = 0; i < liste.size()-1; i++) {
					value+=liste.get(i);
					if(i<liste.size()-1) value+=",";
				}
				store.setValue(PreferenceConstants.TA_FACTURE_COMPTEUR,value);

				
}
			} catch (IOException e) {
				logger.error("", e);
			} catch (ConfigurationException e) {
				logger.error("", e);
			} 
	}
}
