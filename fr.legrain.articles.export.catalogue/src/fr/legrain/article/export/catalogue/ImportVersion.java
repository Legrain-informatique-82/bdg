package fr.legrain.article.export.catalogue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.lib.data.LibConversion;

public class ImportVersion {
	
	static Logger logger = Logger.getLogger(ImportVersion.class.getName());
	
	public static String versionPrestashop = null;
	public static String versionModulePHP = null;
	
	/**
	 * Appel par HTTP de la page sur le seveur qui va déclencher la création d'un fichier contenant les numéros de version
	 * actuellement présent sur le serveur.<br>
	 */
	public void declencheRecupVersionSite() {
		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH);
		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE);
		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_VERSION);

		UtilHTTP.post(urlString, authTokenName, authTokenValue);
	}
	
	/**
	 * Récupération par FTP du fichier contenant les numéros de version.
	 */
	public void recupFTP() {
		try {
			FtpUtil ftpUtil = new FtpUtil(); // init FTP
			if(ftpUtil.connectServer(
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT),
					LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_EXPORT)),
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_EXPORT),
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_EXPORT))) {

				String pathRepWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
				String fichierImport = pathRepWebTmp
				+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_VERSION);

				//création du répertoire de travail s'il n'existe pas
				new File(pathRepWebTmp).mkdirs();

				logger.debug("Import version ...");
				ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_DATA));

				ftpUtil.downloadFile(
						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_VERSION),
						fichierImport
				);


				logger.debug("FERMETURE CNX FTP");
				ftpUtil.closeServer();


			} else {
				logger.error("Problème de connection");
			}
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Test la présence du répertoire "lgr" dans le répertoire d'installation de prestashop
	 * @return
	 */
	public boolean testPresenceModule() {
		boolean res = false;
		FtpUtil ftpUtil = new FtpUtil(); 
		if(ftpUtil.connectServer(
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA),
				LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA)),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA))) {

			logger.debug("Upload du scrit récupération du numéro de version ...");

			if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/"+GestionModulePHP.REP_BASE_MODULE)){
				res = true;
			}

			logger.debug("FERMETURE CNX FTP");
			ftpUtil.closeServer();

		} else {
			logger.error("Problème de connection");
		}
		return res;
	}
	
	/**
	 * Initialise la strucutre des répertoires du module PHP avec les fichiers par défaut
	 * et le script PHP nécessaire pour récupérer la version de Prestashop actuellement installé.
	 */
	public void envoiScriptVersion() {
		try {
			FtpUtil ftpUtil = new FtpUtil(); // init FTP
			if(ftpUtil.connectServer(
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_INSTALL_PRESTA),
					LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_INSTALL_PRESTA)),
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_INSTALL_PRESTA),
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_INSTALL_PRESTA))) {

				if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA))){
					logger.debug("Upload du script récupération du numéro de version ...");
					ftpUtil.createDirectory(GestionModulePHP.REP_BASE_MODULE);
				}
				
				ftpUtil.changeToRootDirectory();
				if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA))){
					URL urlCodePHP = FileLocator.find(fr.legrain.articles.export.catalogue.prestashop.Activator.getDefault().getBundle(),new Path(GestionModulePHP.REP_MODULE_PHP_COMMUN),null);
					File f = FileUtils.toFile(FileLocator.toFileURL(urlCodePHP));
					logger.debug("Upload du scrit récupération du numéro de version ...");
					ftpUtil.uploadDirectory(f.getAbsolutePath(),GestionModulePHP.REP_BASE_MODULE);
				}
				
				ftpUtil.changeToRootDirectory();
				if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/"+GestionModulePHP.REP_BASE_MODULE_LOG)) {
					ftpUtil.updatePermissionDirectory("bdg_presta.log", "0777");
				}
				
				ftpUtil.changeToRootDirectory();
				if(ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_INSTALL_PRESTA)+"/"+GestionModulePHP.REP_BASE_MODULE_PHP_VERSION)) {
					ftpUtil.updatePermissionDirectory("json", "0777");
				}

				logger.debug("FERMETURE CNX FTP");
				ftpUtil.closeServer();


			} else {
				logger.error("Problème de connection");
			}
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Lecture du fichier contenant les numéros de version et initialisation de variables.
	 */
	public boolean importVersion() {
		boolean res = true;
		String fichier = null;
		if(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)!=null)
			fichier = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)
			+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_VERSION);

		try {
			//String encoding = "ISO-8859-1";
			String encoding = "UTF-8";
			
			if(new File(fichier).exists()) {
				FileInputStream fis;
				fis = new FileInputStream(fichier);

				InputStreamReader isr = new InputStreamReader(fis, encoding);

				JSONParser parser = new JSONParser();
				ContainerFactory containerFactory = new ContainerFactory(){
					public List creatArrayContainer() {
						return new LinkedList();
					}

					public Map createObjectContainer() {
						return new LinkedHashMap();
					}
				};

				JSONObject json = (JSONObject)parser.parse(isr);

				/*
				 * Importation des numéros de version
				 */
				versionPrestashop = json.get("version_presta").toString();
				versionModulePHP = json.get("version_module_php").toString();

				logger.info("=========================================================================================");
				logger.info("Version Prestashop : "+versionPrestashop);
				logger.info("Version version_module_php : "+versionModulePHP);
				logger.info("=========================================================================================");
			} else {
				logger.error("Erreur : Le fichier contenant le numéro de version de prestashop n'exsite pas.");
				res = false;
			}
		} catch (FileNotFoundException e) {
			logger.error("",e);
			return false;
		} catch (IOException e) {
			logger.error("",e);
			return false;
		} catch (ParseException e) {
			logger.error("",e);
			return false;
		}
		return res;
	}

	
}
