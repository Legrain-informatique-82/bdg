package fr.legrain.remisecheque.preferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.remisecheque.pluginRemise;

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
			IPreferenceStore store = pluginRemise.getDefault().getPreferenceStore();
			PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
			
			FileInputStream file =new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(file);
			file.close();
			if (!listeGestCode.isEmpty()){
				if(listeGestCode.getString(PreferenceConstants.TA_DOC_FIXE_1)!=null &&
						listeGestCode.getString(PreferenceConstants.TA_DOC_FIXE_1)!="")
					store.setDefault(PreferenceConstants.TA_DOC_FIXE_1,listeGestCode.getString(PreferenceConstants.TA_DOC_FIXE_1));
				else store.setDefault(PreferenceConstants.TA_DOC_FIXE_1, "RM");
				if(listeGestCode.getString(PreferenceConstants.TA_DOC_EXO)!=null &&
						listeGestCode.getString(PreferenceConstants.TA_DOC_EXO)!="")
					store.setDefault(PreferenceConstants.TA_DOC_EXO,listeGestCode.getString(PreferenceConstants.TA_DOC_EXO));
				else store.setDefault(PreferenceConstants.TA_DOC_EXO, "courant");
				String value="";
				if(listeGestCode.getString(PreferenceConstants.TA_DOC_COMPTEUR)!=null){
					List<String> liste = (List<String>)listeGestCode.getList(PreferenceConstants.TA_DOC_COMPTEUR);
					for (int i = 0; i < liste.size(); i++) {
						value+=liste.get(i);
						if(i<liste.size()-1) value+=",";
					}				
				}
				if(value!="")store.setDefault(PreferenceConstants.TA_DOC_COMPTEUR,value);
				else store.setDefault(PreferenceConstants.TA_DOC_COMPTEUR, "TA_REMISE,CODE_DOCUMENT,5");
			}
			
			//parametrage Editions Specifiques
			store.setDefault(PreferenceConstants.AFFICHE_EDITION_IMPRIMER,false);
			store.setDefault(PreferenceConstants.EDITION_IMPRIMER_DIRECT,true);
			store.setDefault(PreferenceConstants.P_PATH_EDITION_DEFAUT,ConstEdition.FICHE_FILE_REPORT_REMISE);
			store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION,true);
			store.setDefault(PreferenceConstants.AFFICHER_SELECTION_EDITION_EDITION_EN_LISTE,true);
			
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static void initValuesProperties(){
			IPreferenceStore store = pluginRemise.getDefault().getPreferenceStore();
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
			IPreferenceStore store = pluginRemise.getDefault().getPreferenceStore();

			// Change la valeur de la clé taCle dans l'objet Properties
			taNouvelleValeur=pluginRemise.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_DOC_FIXE_1) ;
			listeGestCode.setProperty(PreferenceConstants.TA_DOC_FIXE_1,taNouvelleValeur) ;

			taNouvelleValeur=pluginRemise.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_DOC_EXO) ;
			listeGestCode.setProperty(PreferenceConstants.TA_DOC_EXO,taNouvelleValeur) ;

			taNouvelleValeur=pluginRemise.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_DOC_COMPTEUR) ;
			listeGestCode.setProperty(PreferenceConstants.TA_DOC_COMPTEUR,taNouvelleValeur) ;

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
