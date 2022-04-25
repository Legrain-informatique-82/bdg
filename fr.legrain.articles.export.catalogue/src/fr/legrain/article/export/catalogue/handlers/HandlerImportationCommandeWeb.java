package fr.legrain.article.export.catalogue.handlers;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.IImportationTiersCommandeWeb;
import fr.legrain.article.export.catalogue.ImportationTiersCommandeWeb;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.prestashop.ws.WSPrestashopConfig;
import fr.legrain.prestashop.ws.WSPrestashopProgress;

public class HandlerImportationCommandeWeb extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerImportationCommandeWeb.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			
			boolean useWebService = false;

			IImportationTiersCommandeWeb imp = null;
//			FtpUtil ftpUtil = null;
			
			logger.error("Appel site web");
			if(!useWebService) {
				imp = new ImportationTiersCommandeWeb();
				//imp = ImportationTiersCommandeWebFactory.createImportationTiersCommandeWeb();
				//imp.declencheExportSite();
				
				boolean afficheMessageConfirmation = Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BDG);
				boolean maj = true;
				
				MessageDialogWithToggle dialog = null;
				if(afficheMessageConfirmation) {
					dialog = MessageDialogWithToggle.openYesNoQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Attention", "Etes vous sur de vouloir importer les commandes et les tiers de la boutique ?",
						"Ne plus afficher ce message.", false, 
						Activator.getDefault().getPreferenceStoreProject(), 
						PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BDG);
					//TODO chercher la constante pour le retour des messageDialog OK
					if(dialog.getReturnCode() != 2) {
						maj = false;
					}
					Activator.getDefault().getPreferenceStoreProject().setValue(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BDG,!dialog.getToggleState());
				}
				
				if(maj) {
					
					String cle = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_LOGIN);
					String password = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_PASSWORD);
					String hostName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_HOST);
					String baseURI = "http://"+hostName+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_BASE_URI);
					
					WSPrestashopConfig.init(
							baseURI, 
							cle, 
							password, 
							hostName
							);
					
					//ImportationTiersCommandeWebProgress e = new ImportationTiersCommandeWebProgress(imp);
					WSPrestashopProgress wsp = new WSPrestashopProgress(
							WSPrestashopProgress.TYPE_IMPORT, 
							fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getBoolean(fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject.GENERATION_COMMANDE_IMPORT_EN_TTC),
							false);
					PlatformUI.getWorkbench().getProgressService().run(true, false, wsp);
					//PlatformUI.getWorkbench().getProgressService().run(true, false, e);
					
					String messageFin = "Importation terminée.";
//					if(e.getResultatImportation()!=null 
//							&& e.getResultatImportation().getListeCommande()!=null
//							&& e.getResultatImportation().getListeCommande().size()>=1) {
//						messageFin += "\n\n"+e.getResultatImportation().getListeCommande().size()+" commandes importées.";
//						String codeCmdDebut = e.getResultatImportation().getListeCommande().get(0).getCodeDocument();
//						String codeCmdFin = e.getResultatImportation().getListeCommande().get(e.getResultatImportation().getListeCommande().size()-1).getCodeDocument();
//						messageFin += "\nDe la commande "+codeCmdDebut+" à la commande "+codeCmdFin;
//					}
//					if(e.getResultatImportation()!=null && e.getResultatImportation().getListeTiers()!=null) {
//						messageFin += "\n\n"+e.getResultatImportation().getListeTiers().size()+" tiers créés ou mis à jour.";
//					}
					
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Importation du Catalogue Web", messageFin);
				}
			}
//			logger.error("Site web appelé");
//			
//			if(!useWebService) {
////				ftpUtil = new FtpUtil(); // init FTP
////				if(ftpUtil.connectServer(
////						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.HOSTNAME_FTP_EXPORT),
////						LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.PORT_FTP_EXPORT)),
////						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.LOGIN_FTP_EXPORT),
////						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.PASSWORD_FTP_EXPORT))) {
////
////					String pathRepWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REP_TRAVAIL_LOC);
////					String fichierImport = pathRepWebTmp
////					+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.NOM_FICHIER_IMPORT);
////
////					//création du répertoire de travail s'il n'existe pas
////					new File(pathRepWebTmp).mkdirs();
////
////					logger.debug("Import data ...");
////					ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REP_FTP_EXPORT_DATA));
////					ftpUtil.downloadFile(
////							Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.NOM_FICHIER_IMPORT),
////							fichierImport
////					);
////
////					logger.debug("FERMETURE CNX FTP");
////					ftpUtil.closeServer();
////
////
////				} else {
////					logger.error("Problème de connection");
////				}
//			}
//			
//			logger.debug("DEBUT LECTURE IMPORT");
//			if(!useWebService) {
//				//imp.importWeb();
//			}
//			logger.debug("FIN LECTURE IMPORT");
//			
//			if(!useWebService) {
////				if(ftpUtil.connectServer(
////						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.HOSTNAME_FTP_EXPORT),
////						LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.PORT_FTP_EXPORT)),
////						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.LOGIN_FTP_EXPORT),
////						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.PASSWORD_FTP_EXPORT))) {
////
////					String pathRepWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REP_TRAVAIL_LOC);
////					String fichierRetourDonnees = pathRepWebTmp
////					+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.NOM_FICHIER_RETOUR_IMPORT);
////
////					ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE);
////
////					logger.debug("Export retour data ...");
////					ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REP_FTP_EXPORT_DATA));
////					ftpUtil.uploadFile(fichierRetourDonnees);
////
////					logger.debug("FERMETURE CNX FTP");
////					ftpUtil.closeServer();
////
////
////				} else {
////					logger.error("Problème de connection");
////				}
//			}
//			
//			logger.error("Appel site web : retour");
//			//imp.declencheMAJSite();
//			logger.error("Site web appelé");
//
//
//			
//			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Importation du Catalogue Web", "Importation terminée.");
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}
