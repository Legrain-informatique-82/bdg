package fr.legrain.article.export.catalogue.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.gestCom.Appli.Const;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
//		//IPreferenceStore store = Activator.getDefault().getPreferenceStoreProject();
//		//IPreferenceStore store = new ScopedPreferenceStore(Const.getProjectScopeContext(),Const.getProjectName());
//		//IPreferenceStore store = Const.getStore();
//		//IPreferenceStore store = Const.getStore(Activator.PLUGIN_ID);
//		IPreferenceStore store = Activator.getDefault().getProjectPreferenceStore();
//		
//		String repDefaut = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/cat_web_tmp";
////		String domaine = "http://192.168.1.35/julien/echappee_bio";
////		String domaine = "http://www.pageweb.fr/dev/echappee_bio.fr";
//		String domaine = "http://192.168.1.6/prestashop";
//		
//		store.setDefault(PreferenceConstants.MESSAGE_CONFIRMATION_UPDATE,"true");
//		
//		store.setDefault(PreferenceConstants.NOM_FICHIER_EXPORT,"catalogue_web.json");
//		store.setDefault(PreferenceConstants.NOM_FICHIER_IMPORT,"tiers_commandes.json");
//		store.setDefault(PreferenceConstants.NOM_FICHIER_VERSION,"version.json");
//		store.setDefault(PreferenceConstants.NOM_FICHIER_RETOUR_IMPORT,"retour_tiers_commandes.json");
//		store.setDefault(PreferenceConstants.REP_TRAVAIL_LOC, repDefaut);
//		store.setDefault(PreferenceConstants.REP_LOC_FICHIER_EXPORT, repDefaut);
//		store.setDefault(PreferenceConstants.HOSTNAME_FTP_EXPORT,"");
//		store.setDefault(PreferenceConstants.PORT_FTP_EXPORT,"21");
//		store.setDefault(PreferenceConstants.LOGIN_FTP_EXPORT,"");
//		store.setDefault(PreferenceConstants.PASSWORD_FTP_EXPORT,"");
//		store.setDefault(PreferenceConstants.REP_FTP_EXPORT_DATA,"/json");
//		store.setDefault(PreferenceConstants.CHEMIN_FICHIER_PARAM_EXPORT,"");
//		
//		store.setDefault(PreferenceConstants.REP_FTP_INSTALL_PRESTA,"/prestashop");
//		store.setDefault(PreferenceConstants.HOSTNAME_FTP_INSTALL_PRESTA,"localhost");
//		store.setDefault(PreferenceConstants.PORT_FTP_INSTALL_PRESTA,"21");
//		store.setDefault(PreferenceConstants.LOGIN_FTP_INSTALL_PRESTA,"testftp");
//		store.setDefault(PreferenceConstants.PASSWORD_FTP_INSTALL_PRESTA,"lgr00XXXX");
//		
//		store.setDefault(PreferenceConstants.REP_FTP_EXPORT_IMG_ARTICLE,"/images/produits");
//		
//		store.setDefault(PreferenceConstants.URL_BOUTIQUE,domaine);
//		
//		store.setDefault(PreferenceConstants.MODE_AFFICHAGE_PRIX,PreferenceConstants.VALEUR_MODE_AFFICHAGE_PRIX_DECLINAISON);
//		store.setDefault(PreferenceConstants.UNITES_PRISE_EN_COMPTE,"");
//		store.setDefault(PreferenceConstants.UNITE_PRIX_DEFAUT,"");
//		
//		store.setDefault(PreferenceConstants.URL_HTTP_TOKEN_AUTH,"code");
//		store.setDefault(PreferenceConstants.URL_HTTP_TOKEN_AUTH_VALUE,"9bf0e3c52ee4ced539564fa1bad49317ac915009");
//		store.setDefault(PreferenceConstants.URL_HTTP_MAJ_CATALOGUE,domaine+"/lgr/php/import/update.php");
//		
//		//export des données du site (commandes et tiers) vers BDG
//		store.setDefault(PreferenceConstants.URL_HTTP_MAJ_BDG,domaine+"/lgr/php/export/export.php");
//		store.setDefault(PreferenceConstants.URL_HTTP_VERSION,domaine+"/lgr/php/version/version.php");
//		
//		//Ajout des id BDG dans la bdd du site Internet
//		store.setDefault(PreferenceConstants.URL_HTTP_RETOUR_BDG,domaine+"/lgr/php/import/update_after_export.php");
//		
//		store.setDefault(PreferenceConstants.REP_FICHIER_IMPORT_ARTICLE,repDefaut);
//		store.setDefault(PreferenceConstants.NOM_FICHIER_IMPORT_ARTICLE,"");
//		
//		store.setDefault(PreferenceConstants.RAPPORT_PRIX_U_COLIS,0.15); //15% de remise => U x nbUColis - 15% de rem

	}

}
