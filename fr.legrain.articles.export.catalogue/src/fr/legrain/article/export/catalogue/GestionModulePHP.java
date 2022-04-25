package fr.legrain.article.export.catalogue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.export.catalogue.handlers.UtilProgress;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.lib.data.LibConversion;

/**
 * Gestion du module PHP
 * 
 * "install_prestashop"
 * "install_prestashop"/lgr
 * "install_prestashop"/lgr/data
 * "install_prestashop"/lgr/data/dyn
 * "install_prestashop"/lgr/data/fixe
 * "install_prestashop"/lgr/log
 * "install_prestashop"/lgr/php
 * "install_prestashop"/lgr/php/class
 * "install_prestashop"/lgr/php/configuration
 * "install_prestashop"/lgr/php/export
 * "install_prestashop"/lgr/php/import
 * "install_prestashop"/lgr/php/lib
 * "install_prestashop"/lgr/php/log4php
 * "install_prestashop"/lgr/php/version
 */
public class GestionModulePHP {
	
	static Logger logger = Logger.getLogger(GestionModulePHP.class.getName());
	
	public static final String PRESTA_1_4 = "1.4";
	public static final String PRESTA_1_5 = "1.5";
	public static final String MODULE_PHP_2_0_6 = "2.0.6";
	public static final String REP_MODULE_PHP_2_0_6 = "/php/2_0_6";
	public static final String REP_MODULE_PHP_COMMUN = "/php/commun";
	
	public static final String REP_BASE_MODULE = "lgr";
	public static final String REP_BASE_MODULE_PHP = "lgr/php";
	public static final String REP_BASE_MODULE_PHP_VERSION = "lgr/php/version";
	public static final String REP_BASE_MODULE_LOG = "lgr/log";
	public static final String REP_BASE_MODULE_DATA = "lgr/data";
	
	/**
	 * Map : cle => version_de_prestashop , valeurs => [version_module_php, emplacement_module]
	 */
	public static Map<String,String[]> correspondanceVersion = null;
	
	public static ImportVersion version = new ImportVersion();
	
	/**
	 * Envoi du bon module sur le serveur en fonction de la version de prestashop,
	 * force la MAJ
	 * @param monitor - IProgressMonitor
	 */
	public static void miseAJourModulePHP(final IProgressMonitor monitor) {
		miseAJourModulePHP(monitor, true);
	}
	
	/**
	 * Envoi du bon module sur le serveur en fonction de la version de prestashop
	 * @param monitor - IProgressMonitor
	 * @param force - boolean - si vrai force la mise à jour sans demander à l'utilisateur, s'il n'y a pas besoin de MAJ,
	 *  renvoi quand même la même version sur le serveur. Sinon (faux) pose la question à l'utilisateur si une nouvelle version est disponible.
	 */
	public static void miseAJourModulePHP(final IProgressMonitor monitor, boolean force) {
		//monitor.beginTask("Vérification du numéro de version ...",monitor.UNKNOWN);
		monitor.subTask("Vérification du numéro de version ...");
		initVersionServeur(new SubProgressMonitor(monitor,10));
		initCorrespondanceVersion();
		String[] versionModule = null;
		String v = null;
		
		if(ImportVersion.versionPrestashop.startsWith(PRESTA_1_4)){
			versionModule = correspondanceVersion.get(PRESTA_1_4);
			v = PRESTA_1_4;
		}else if(ImportVersion.versionPrestashop.startsWith(PRESTA_1_5)){
			versionModule = correspondanceVersion.get(PRESTA_1_5);
			v = PRESTA_1_5;
		} else {
			logger.error("Version de prestashop ("+ImportVersion.versionPrestashop+") non gérée par le Bureau de Gestion");
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Mise à jour", "Version de prestashop ("+ImportVersion.versionPrestashop+") non gérée par le Bureau de Gestion.");
			//lancer une exception
		}
		
		boolean maj = true;
		if(versionModule[0].equals(version.versionModulePHP)) {
			logger.info("Version OK");
			boolean debug = Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.DEBUG);
			//if(!force) {
			if(debug) {
				final boolean res[] = new boolean[1];
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						res[0] = MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Mise à jour", "La version du module PHP qui est installée sur le serveur est correcte.\n" +
						"Souhaitez vous quand même renvoyer la même version ?");
					}});
				maj = res[0];
			} else {
				maj = false;
			}
		} else {
			logger.info("La version du module PHP intallé ("+version.versionModulePHP+") n'est pas compatible avec cette" +
					" version de Prestashop ("+version.versionPrestashop+").");
			//faire une mise à jour du module PHP par FTP
			if(version.versionModulePHP.equals("0")) {
				force = true;
				logger.info("Première installation du module PHP");
			}
			if(!force) {
				maj = MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Mise à jour", "Une mise à jour pour le module PHP est disponible.\n" +
								"Effectuer la mise à jour ?");
			}
		}
		
		if(force || maj) {
			if(force) {
				logger.info("Mise à jour forcée du module vers la version : "+versionModule[0]);
			} else {
				logger.info("Mise à jour du module vers la version : "+versionModule[0]);
			}
			//monitor.beginTask("",456000);
			updateModulePresta(v,monitor);
			updateParamBoutique();
		} else {
			logger.info("Mise à jour du module vers la version "+versionModule[0]+" annulé par l'utilisateur");
		}

	}
	
	/**
	 * Initialise la map <code>correspondanceVersion</code> contenant les correspondances entre
	 * les version de version de prestashop et le module PHP correspondant
	 */
	static public void initCorrespondanceVersion() {
		correspondanceVersion = new HashMap<String,String[]>();
		correspondanceVersion.put(PRESTA_1_4, new String[] {MODULE_PHP_2_0_6,REP_MODULE_PHP_2_0_6});
		correspondanceVersion.put(PRESTA_1_5, new String[] {MODULE_PHP_2_0_6,REP_MODULE_PHP_2_0_6});
	}
	
	/**
	 * Initialisation des champs de l'objet <code>version</code> en fonction de la version de prestashop
	 * présente sur le serveur.
	 */
	static public void initVersionServeur(IProgressMonitor monitor) {
		if(monitor!=null)
			monitor.beginTask("initVersionServeur", IProgressMonitor.UNKNOWN);
		if(!version.testPresenceModule()) {
			version.envoiScriptVersion();
		} else {
			initDroits();
		}
		updateParamBoutique();
		version.declencheRecupVersionSite();
		version.recupFTP();
		version.importVersion();
		if(monitor!=null)
			monitor.done();
	}
	
	static public void initVersionServeur() {
		initVersionServeur(null);
	}
	
	/**
	 * Retourne la taille d'un des modules PHP en fonction du numéro de version
	 * @param version - numéro de version
	 * @return taille en octect du module
	 */
	public static long tailleModule(String version) {
		long taille = 0;
		String[] versionModule = correspondanceVersion.get(version);
		URL urlCodePHP = FileLocator.find(fr.legrain.articles.export.catalogue.prestashop.Activator.getDefault().getBundle(),new Path(versionModule[1]+"/php"),null);
		try {
			File f = FileUtils.toFile(FileLocator.toFileURL(urlCodePHP));
			taille = UtilProgress.getFileSize(f);
		} catch (IOException e) {
			logger.error("",e);
		}
		
		return taille;
	}
	
	/**
	 * Envoi du module PHP correspondant à la version passée en paramètre sur le serveur.<br>
	 * Dans un premier temps, le module PHP présent sur le serveur est supprimé, UNIQUEMENT le répertoire "php" contenant les traitement,
	 * et pas les données de correspondance.
	 * @param version
	 */
	private static void updateModulePresta(String version, final IProgressMonitor monitor) {
		FtpUtil ftpUtil = new FtpUtil();
		
		ftpUtil.setCopyStreamListener(new CopyStreamListener(){
			public void bytesTransferred(CopyStreamEvent arg0) {}
			public void bytesTransferred(long arg0, int arg1, long arg2) {
				if(monitor!=null)
					monitor.worked(arg1);
			}
		});
		
		boolean effacerDonneesGenerees = false;
		boolean effacerCorrespondanceFixe = false; //code TVA
		if(ftpUtil.connectServer(
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA),
				LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA)),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA))) {
		

			ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE);

			try {
				String[] versionModule = correspondanceVersion.get(version);

				logger.debug("Suppression de l'ancien module ...");
				ftpUtil.removeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php",true);

				if(effacerDonneesGenerees) {
					//					logger.debug("Suppression des données générées ...");
					//					ftpUtil.removeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/data/dyn");
					//					ftpUtil.removeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/data/dyn");
					//					URL urlCodePHP = FileLocator.find(fr.legrain.articles.export.catalogue.prestashop.Activator.getDefault().getBundle(),new Path(pathRepExportWebTmp+versionModule[1]+"/data/fixe"),null);
					//					File f = FileUtils.toFile(FileLocator.toFileURL(urlCodePHP));
					//					ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA));
					//					ftpUtil.uploadDirectory(f.getAbsolutePath(),"lgr/data/dyn");
				}

				if(effacerCorrespondanceFixe) {
					//					logger.debug("Suppression des correspondances fixes (codeTVA,...) ...");
					//					ftpUtil.removeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/data/fixe");
					//					URL urlCodePHP = FileLocator.find(fr.legrain.articles.export.catalogue.prestashop.Activator.getDefault().getBundle(),new Path(pathRepExportWebTmp+versionModule[1]+"/data/fixe"),null);
					//					File f = FileUtils.toFile(FileLocator.toFileURL(urlCodePHP));
					//					ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA));
					//					ftpUtil.uploadDirectory(f.getAbsolutePath(),"lgr/data/fixe");
				}

				logger.debug("Upload du nouveau module ...");
				URL urlCodePHP = FileLocator.find(fr.legrain.articles.export.catalogue.prestashop.Activator.getDefault().getBundle(),new Path(versionModule[1]+"/php"),null);
				File f = FileUtils.toFile(FileLocator.toFileURL(urlCodePHP));
				ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA));
				ftpUtil.uploadDirectory(f.getAbsolutePath(),REP_BASE_MODULE_PHP);

				initDroits();

				logger.debug("FERMETURE CNX FTP");
				ftpUtil.closeServer();
			} catch (IOException e) {
				logger.error("",e);
			}

		} else {
			logger.error("Problème de connection");
		}
	}
	
	/**
	 * Mise à jour des droits des répertoires dans le module PHP sur le serveur.<br>
	 * Mise à jour des droits par FTP (Utilise la commande "SITE" et "CHMOD" du serveur FTP).
	 */
	static public void initDroits() {
		FtpUtil ftpUtil = new FtpUtil();
		if(ftpUtil.connectServer(
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA),
				LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA)),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA))) {

			ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE);

			logger.debug("Mise à jour des droits utilisateurs ...");
			//String droits = "0666";
			String droits = "0777";
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php/export")) {
				ftpUtil.updatePermissionDirectory("json", droits);
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php/import")) {
				ftpUtil.updatePermissionDirectory("json", droits);
				ftpUtil.updatePermissionDirectory("images", droits);
				
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php/import/images")) {
				ftpUtil.updatePermissionDirectory("categories", droits);
				ftpUtil.updatePermissionDirectory("produits", droits);
				ftpUtil.updatePermissionDirectory("labels", droits);
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php/version")) {
				ftpUtil.updatePermissionDirectory("json", droits);
				ftpUtil.updatePermissionDirectory("backup", droits);
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php/outils/backup")) {
				ftpUtil.updatePermissionDirectory("bdd", droits);
				ftpUtil.updatePermissionDirectory("archives", droits);
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr")) {
				ftpUtil.updatePermissionDirectory("log", droits);
				ftpUtil.updatePermissionFile("log/bdg_presta.log", droits);
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/data")) {
				ftpUtil.updatePermissionDirectory("dyn", droits);
			}
			ftpUtil.changeToRootDirectory();
			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/lgr/php/import")) {
				//ftpUtil.updatePermissionDirectory("treatment.sh", "u+x,g+x,o+x");
				ftpUtil.updatePermissionDirectory("treatment.sh", "0777");
			}

			logger.debug("FERMETURE CNX FTP");
			ftpUtil.closeServer();
		} else {
			logger.error("Problème de connection");
		}
	}
	
	/**
	 * Création d'un fichier de paramétrage pour le module PHP à partir des préférences du bureau de gestion
	 * et envoi sur le serveur par FTP.
	 */
	static public void updateParamBoutique() {
		//génération du fichier ini à partir des préférences bdg
		Properties p = new Properties();
		String emplacementFichierIniSurServeur = "lgr/php/configuration";
		String pathTempFile = Const.C_CHEMIN_REP_TMP_COMPLET+"/param.ini";

		p.put("FTP_HOST", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT));
		p.put("FTP_USER", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_EXPORT));
		p.put("FTP_PASSWORD", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_EXPORT));
		p.put("SECURITY_KEY", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE));
		
		p.put("WEBSITE_ADRESS", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE));
		
		p.put("MODE_AFFICHAGE_PRIX", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.MODE_AFFICHAGE_PRIX));
		
		p.put("HOSTNAME_BDD_BOUTIQUE", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_BDD_BOUTIQUE));
		p.put("NAME_BDD_BOUTIQUE", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NAME_BDD_BOUTIQUE));
		p.put("LOGIN_BDD_BOUTIQUE", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_BDD_BOUTIQUE));
		p.put("PASSWORD_BDD_BOUTIQUE", Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_BDD_BOUTIQUE));
		
		try {
			p.store(new FileOutputStream(pathTempFile), null);
		} catch (FileNotFoundException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
		
		//envoi du fichier ini par FTP dans configuration/param.ini
		FtpUtil ftpUtil = new FtpUtil();
		if(ftpUtil.connectServer(
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA),
				LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA)),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA))) {
			try {
				ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA));
				ftpUtil.uploadFile(pathTempFile,emplacementFichierIniSurServeur);
			} catch (IOException e) {
				logger.error("",e);
			}
		} else {
			logger.error("Problème de connection");
		}
		logger.debug("FERMETURE CNX FTP");
		ftpUtil.closeServer();
	}
	
	public void sauvegardeBoutique() {
		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH);
		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE);
		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_SAUVEGARDE_BOUTIQUE);

		UtilHTTP.post(urlString, authTokenName, authTokenValue);
	}
	
	public void sauvegardeBoutique(String typeSauvegarde) {
		String nomParamPostPHPSauvegarde = "prefixe";
		
		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH);
		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE);
		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_SAUVEGARDE_BOUTIQUE);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put(authTokenName, authTokenValue);
		param.put(nomParamPostPHPSauvegarde, typeSauvegarde);

		logger.info("Sauvegarde de la boutique == Type["+typeSauvegarde+"]");
		UtilHTTP.post(urlString, param);
	}
}
