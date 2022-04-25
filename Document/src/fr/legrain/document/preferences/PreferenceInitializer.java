package fr.legrain.document.preferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.libMessageLGR.LgrMess;


public class PreferenceInitializer extends AbstractPreferenceInitializer {
	static Logger logger = Logger.getLogger(PreferenceInitializer.class.getName());
	
	public PreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = DocumentPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.afficheEditionImprimer, true);
		if(LgrMess.isDEVELOPPEMENT()){ 
			store.setDefault(PreferenceConstants.P_ONGLETS_DOC,"true");
		}else{
			store.setDefault(PreferenceConstants.P_ONGLETS_DOC,"false");
		}
		if(LgrMess.isDEVELOPPEMENT()){ 
			store.setDefault(PreferenceConstants.AFF_ADRESSE,"true");
		}else{
			store.setDefault(PreferenceConstants.AFF_ADRESSE,"false");
		}
		if(LgrMess.isDEVELOPPEMENT()){ 
			store.setDefault(PreferenceConstants.AFF_ADRESSE_LIV,"true");
		}else{
			store.setDefault(PreferenceConstants.AFF_ADRESSE_LIV,"false");
		}
		if(LgrMess.isDEVELOPPEMENT()){ 
			store.setDefault(PreferenceConstants.AFF_CPAIEMENT,"true");
		}else{
			store.setDefault(PreferenceConstants.AFF_CPAIEMENT,"false");
		}
		if(LgrMess.isDEVELOPPEMENT()){ 
			store.setDefault(PreferenceConstants.AFF_COMMENTAIRE,"true");
		}else{
			store.setDefault(PreferenceConstants.AFF_COMMENTAIRE,"false");
		}	
		if(LgrMess.isDEVELOPPEMENT()){ 
			store.setDefault(PreferenceConstants.AFF_IDENTITE_TIERS,"true");
		}else{
			store.setDefault(PreferenceConstants.AFF_IDENTITE_TIERS,"false");
		}	
		store.setDefault(PreferenceConstants.TYPE_ADRESSE_FACTURATION,"FACT");
		store.setDefault(PreferenceConstants.TYPE_ADRESSE_DEVIS,"FACT");
		store.setDefault(PreferenceConstants.TYPE_ADRESSE_BONLIV,"LIV");
//		store.setDefault(PreferenceConstants.TYPE_AFFICHAGE_AIDE, "true");
		store.setDefault(PreferenceConstants.TYPE_PAIEMENT_DEFAUT,"");
		store.setDefault(PreferenceConstants.MESSAGE_TIERS_DIFFERENT, "true");
		
		


		store.setDefault(PreferenceConstants.COMMENTAIRE_TRAITE,Const.finDeLigne+Const.finDeLigne);
		
		
		store.setDefault(PreferenceConstants.AFF_EDITION, "true");
//		store.setDefault(PreferenceConstants.TYPE_AFFICHAGE_AIDE, "false");
		

	
	}
	
	
	public static void initDefautProperties(){
		try {
			IPreferenceStore store = DocumentPlugin.getDefault().getPreferenceStore();
			
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			if (!listeGestCode.isEmpty()){
				if(listeGestCode.getString(PreferenceConstants.TA_REG_FIXE_1)!=null &&
						listeGestCode.getString(PreferenceConstants.TA_REG_FIXE_1)!="")
					store.setDefault(PreferenceConstants.TA_REG_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_REG_FIXE_1));
				else store.setDefault(PreferenceConstants.TA_REG_FIXE_1, "RG");
				if(listeGestCode.getString(PreferenceConstants.TA_REG_EXO)!=null &&
						listeGestCode.getString(PreferenceConstants.TA_REG_EXO)!="")
					store.setDefault(PreferenceConstants.TA_REG_EXO,listeGestCode.getString(PreferenceConstants.TA_REG_EXO));
				else store.setDefault(PreferenceConstants.TA_REG_EXO, "courant");
				String value="";
				if(listeGestCode.getString(PreferenceConstants.TA_REG_COMPTEUR)!=null){
					List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_REG_COMPTEUR);
					for (int i = 0; i < liste.size(); i++) {
						value+=liste.get(i);
						if(i<liste.size()-1) value+=",";
					}				
				}
				if(value!="")store.setDefault(PreferenceConstants.TA_REG_COMPTEUR,value);
				else store.setDefault(PreferenceConstants.TA_REG_COMPTEUR, "TA_REGLEMENT,CODE_DOCUMENT,5");
			}
			
		} catch (IOException e) {
			logger.error("", e);
		} catch (ConfigurationException e) {
			logger.error("", e);
		}
	}
	
	public static void initValuesProperties(){
		try {
			IPreferenceStore store = DocumentPlugin.getDefault().getPreferenceStore();
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();

			if (!listeGestCode.isEmpty()){
				if(listeGestCode.getString(PreferenceConstants.TA_REG_FIXE_1)!=null)
					store.setValue(PreferenceConstants.TA_REG_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_REG_FIXE_1));
				else
					store.setValue(PreferenceConstants.TA_REG_FIXE_1,"RG");
				if(listeGestCode.getString(PreferenceConstants.TA_REG_EXO)!=null)
					store.setValue(PreferenceConstants.TA_REG_EXO,listeGestCode.getString(PreferenceConstants.TA_REG_EXO));
				else
					store.setValue(PreferenceConstants.TA_REG_EXO,"courant");

				if(listeGestCode.getString(PreferenceConstants.TA_REG_COMPTEUR)!=null){
					List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_REG_COMPTEUR);
					String value="";
					for (int i = 0; i < liste.size()-1; i++) {
						value+=liste.get(i);
						if(i<liste.size()-1) value+=",";
					}
					store.setValue(PreferenceConstants.TA_REG_COMPTEUR,value);
				}else
					store.setValue(PreferenceConstants.TA_REG_COMPTEUR,"TA_REGLEMENT,CODE_DOCUMENT,5");
			}
		} catch (IOException e) {
			logger.error("", e);
		} catch (ConfigurationException e) {
			logger.error("", e);
		} 
	}

	public static void remplieProperties(){
		/********************/
		PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
		//File fProp = new File(Const.C_FICHIER_GESTCODE) ;

		// Charge le contenu de ton fichier properties dans un objet Properties
		FileInputStream stream;
		try {
			stream = new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(stream) ;
			stream.close();
			String taNouvelleValeur=null;
			IPreferenceStore store = DocumentPlugin.getDefault().getPreferenceStore();

			// Change la valeur de la clé taCle dans l'objet Properties
			taNouvelleValeur=DocumentPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_REG_FIXE_1) ;
			listeGestCode.setProperty(PreferenceConstants.TA_REG_FIXE_1,taNouvelleValeur) ;

			taNouvelleValeur=DocumentPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_REG_EXO) ;
			listeGestCode.setProperty(PreferenceConstants.TA_REG_EXO,taNouvelleValeur) ;

			taNouvelleValeur=DocumentPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_REG_COMPTEUR) ;
			listeGestCode.setProperty(PreferenceConstants.TA_REG_COMPTEUR,taNouvelleValeur) ;

			// Charge le contenu de ton objet Properties dans ton fichier properties
			FileOutputStream oStream = new FileOutputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.save(oStream,null) ;
			oStream.close();

		} catch (ConfigurationException e) {
			logger.error("",e);
		
		} catch (IOException e) {
			logger.error("",e);
		}

	}
	
}
