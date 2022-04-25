package fr.legrain.article.export.catalogue.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.gestCom.Appli.Const;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializerProject extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		//IPreferenceStore store = Activator.getDefault().getPreferenceStoreProject();
		//IPreferenceStore store = new ScopedPreferenceStore(Const.getProjectScopeContext(),Const.getProjectName());
		//IPreferenceStore store = Const.getStore();
		//IPreferenceStore store = Const.getStore(Activator.PLUGIN_ID);
		IPreferenceStore store = Activator.getDefault().getPreferenceStoreProject();
		
		String repDefaut = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/cat_web_tmp";
//		String domaine = "http://192.168.1.35/julien/echappee_bio";
//		String domaine = "http://www.pageweb.fr/dev/echappee_bio.fr";
		String domaine = "http://192.168.1.6/prestashop";
		
//		store.setDefault(PreferenceConstantsProject.DEBUG,"true");
		store.setDefault(PreferenceConstantsProject.DEBUG,"false");
		
		store.setDefault(PreferenceConstantsProject.UTILISE_BOUTIQUE,"false");
		store.setDefault(PreferenceConstantsProject.PREMIER_TRANSFERT_EFFECTUE,"false");
		
		store.setDefault(PreferenceConstantsProject.GENERATION_COMMANDE_IMPORT_EN_TTC,"true");
		
		store.setDefault(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BOUTIQUE,"true");
		store.setDefault(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BDG,"true");
		
		store.setDefault(PreferenceConstantsProject.NOM_FICHIER_EXPORT,"catalogue_web.json");
		store.setDefault(PreferenceConstantsProject.NOM_FICHIER_IMPORT,"tiers_commandes.json");
		store.setDefault(PreferenceConstantsProject.NOM_FICHIER_VERSION,"version.json");
		store.setDefault(PreferenceConstantsProject.NOM_FICHIER_RETOUR_IMPORT,"retour_tiers_commandes.json");
		store.setDefault(PreferenceConstantsProject.REP_TRAVAIL_LOC, repDefaut);
		store.setDefault(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT, repDefaut);
		store.setDefault(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT,"");
		store.setDefault(PreferenceConstantsProject.PORT_FTP_EXPORT,"21");
		store.setDefault(PreferenceConstantsProject.LOGIN_FTP_EXPORT,"");
		store.setDefault(PreferenceConstantsProject.PASSWORD_FTP_EXPORT,"");
		store.setDefault(PreferenceConstantsProject.REP_FTP_EXPORT_DATA,"/json");
		store.setDefault(PreferenceConstantsProject.CHEMIN_FICHIER_PARAM_EXPORT,"");
		
		store.setDefault(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA,"/prestashop");
		store.setDefault(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA,"localhost");
		store.setDefault(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA,"21");
		store.setDefault(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA,"testftp");
		store.setDefault(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA,"lgr00XXXX");
		
		store.setDefault(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE,"/images/produits");
		store.setDefault(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE,"/images/categories");
		store.setDefault(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL,"/images/labels");
		
		store.setDefault(PreferenceConstantsProject.URL_BOUTIQUE,domaine);
		
		store.setDefault(PreferenceConstantsProject.MODE_AFFICHAGE_PRIX,PreferenceConstantsProject.VALEUR_MODE_AFFICHAGE_PRIX_DECLINAISON);
		store.setDefault(PreferenceConstantsProject.UNITES_PRISE_EN_COMPTE,"");
		store.setDefault(PreferenceConstantsProject.UNITE_PRIX_DEFAUT,"");
		
		store.setDefault(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH,"code");
		store.setDefault(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE,"9bf0e3c52ee4ced539564fa1bad49317ac915009");
		store.setDefault(PreferenceConstantsProject.URL_HTTP_MAJ_CATALOGUE,"/lgr/php/import/update.php");
		
		store.setDefault(PreferenceConstantsProject.WEBSERVICE_HOST,"");
		store.setDefault(PreferenceConstantsProject.WEBSERVICE_LOGIN,"");
		store.setDefault(PreferenceConstantsProject.WEBSERVICE_PASSWORD,"");
		store.setDefault(PreferenceConstantsProject.WEBSERVICE_BASE_URI,"/api");
		
		//store.setDefault(PreferenceConstantsProject.URL_HTTP_SAUVEGARDE_BOUTIQUE,"/lgr/php/outils/backup/backup.php");
		store.setDefault(PreferenceConstantsProject.URL_HTTP_SAUVEGARDE_BOUTIQUE,"/backup/backup.php");
		store.setDefault(PreferenceConstantsProject.URL_HTTP_RESTAURATION_BOUTIQUE,"");
		store.setDefault(PreferenceConstantsProject.HOSTNAME_BDD_BOUTIQUE,"localhost");
		store.setDefault(PreferenceConstantsProject.NAME_BDD_BOUTIQUE,"");
		store.setDefault(PreferenceConstantsProject.LOGIN_BDD_BOUTIQUE,"");
		store.setDefault(PreferenceConstantsProject.PASSWORD_BDD_BOUTIQUE,"");
		store.setDefault(PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE,"true");
		
		//export des données du site (commandes et tiers) vers BDG
		store.setDefault(PreferenceConstantsProject.URL_HTTP_MAJ_BDG,"/lgr/php/export/export.php");
		store.setDefault(PreferenceConstantsProject.URL_HTTP_VERSION,"/lgr/php/version/version.php");
		
		//Ajout des id BDG dans la bdd du site Internet
		store.setDefault(PreferenceConstantsProject.URL_HTTP_RETOUR_BDG,"/lgr/php/import/update_after_export.php");
		
		store.setDefault(PreferenceConstantsProject.REP_FICHIER_IMPORT_ARTICLE,repDefaut);
		store.setDefault(PreferenceConstantsProject.NOM_FICHIER_IMPORT_ARTICLE,"");
		
		store.setDefault(PreferenceConstantsProject.CREATION_AUTO_SECOND_PRIX,true);
		store.setDefault(PreferenceConstantsProject.RAPPORT_PRIX_U_COLIS,0.15); //15% de remise => U x nbUColis - 15% de rem
		
		store.setDefault(PreferenceConstantsProject.NB_UNITE_DANS_COLIS_DEFAUT,10);
		store.setDefault(PreferenceConstantsProject.ACCEPTER_EXPORTATION_PARTIELLE_VERS_BOUTIQUE,true);
		store.setDefault(PreferenceConstantsProject.EB_CATEGORIE_DEFAUT,"8000");
		store.setDefault(PreferenceConstantsProject.EB_IMPORTATION_ESPACE3000_ARTICLE_EXPORTABLE_DEFAUT,1);
		
		store.setDefault(PreferenceConstantsProject.STOCK_DEFAUT_POUR_EXPORT_BOUTIQUE,10);
		store.setDefault(PreferenceConstantsProject.EB_COMPTE_COMPTA_DEFAUT,"");

		
//		UtilWorkspace uw = new UtilWorkspace();
		
//		//sauvegarde
//		store.setDefault(PreferenceConstantsProject.P_EMPLACEMENT,Platform.getInstanceLocation().getURL().getFile());
//		//restauration
//		if(Platform.getOS().equals(Platform.OS_WIN32)){
//			store.setDefault(PreferenceConstantsProject.P_REP_DEST,"C:/LGRDOSS/BureauDeGestion/");
//		}else if(Platform.getOS().equals(Platform.OS_LINUX)){
//			store.setDefault(PreferenceConstantsProject.P_REP_DEST,Platform.getInstanceLocation().getURL().getFile());
//		}

	}

}
