package fr.legrain.article.export.catalogue.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.ExportationArticle;
import fr.legrain.article.export.catalogue.GestionModulePHP;
import fr.legrain.article.export.catalogue.IExportationArticle;
import fr.legrain.article.export.catalogue.ResultatExportation;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.prestashop.ws.WSPrestashop;
import fr.legrain.prestashop.ws.WSPrestashopConfig;
import fr.legrain.prestashop.ws.WSPrestashopProgress;
import fr.legrain.prestashop.ws.entities.Prestashop;

public class HandlerExportationCatalogueWeb extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerExportationCatalogueWeb.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			boolean useWebService = false;
//			GestionModulePHP gestionModulePHP = new GestionModulePHP();
//			String typeSvgAvantMaj = "avant_update";
//			String typeSvgApresMaj = "apres_update";
			ResultatExportation res = null;
			
			//WSPrestashop ws = new WSPrestashop();

//			logger.debug("DEBUT GENERATION EXPORT");
//			IExportationArticle exp = null;
//			if(!useWebService) {
//				exp = new ExportationArticle();
//				//exp = ExportationArticleFactory.createExportationArticle();
//			} else {
//				// exp = new ExportationArticleWebserviceMagento();
//				// exp = new ExportationArticleWebservicePrestashop();
//			}
//			//exp.exportationCatalogueWeb();
//			logger.debug("FIN GENERATION EXPORT");

			if(!useWebService) {
				try {
					boolean afficheMessageConfirmation = Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BOUTIQUE);
					boolean maj = true;
					
					MessageDialogWithToggle dialog = null;
					if(afficheMessageConfirmation) {
						dialog = MessageDialogWithToggle.openYesNoQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Attention", "Etes vous sur de vouloir effectuer une mise à jour des articles sur votre boutique ?",
							"Ne plus afficher ce message.", false, 
							Activator.getDefault().getPreferenceStoreProject(), 
							PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BOUTIQUE);
						//TODO chercher la constante pour le retour des messageDialog OK
						if(dialog.getReturnCode() != 2) {
							maj = false;
						}
						Activator.getDefault().getPreferenceStoreProject().setValue(PreferenceConstantsProject.MESSAGE_CONFIRMATION_UPDATE_BOUTIQUE,!dialog.getToggleState());
					}
					
					if(maj) {
						//logger.debug("DEBUT GENERATION EXPORT");
						//res = exp.exportationCatalogueWeb();
						//logger.debug("FIN GENERATION EXPORT");
						
//						String baseURI = "http://dev2.pageweb.fr/api";
//						String cle = "NAEVYYHRN00WH0SS0G87RDE550OL9V92";
//						String password = "";
//						String hostName = "dev2.pageweb.fr";
						
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
						
						WSPrestashopProgress wsp = new WSPrestashopProgress(
								WSPrestashopProgress.TYPE_EXPORT,
								null,
								fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getBoolean(fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE));
						
						//Paramétrage de l'URL du script de sauvegarde
						wsp.setAuthTokenName(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH));
						wsp.setAuthTokenValue(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE));
						wsp.setUrlString(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_SAUVEGARDE_BOUTIQUE));
						
						wsp.setReductionColis(Activator.getDefault().getPreferenceStoreProject().getFloat(PreferenceConstantsProject.RAPPORT_PRIX_U_COLIS));
						wsp.setStockDefautExportBoutique(Activator.getDefault().getPreferenceStoreProject().getInt(PreferenceConstantsProject.STOCK_DEFAUT_POUR_EXPORT_BOUTIQUE));

						//ExportationArticleProgress e = new ExportationArticleProgress(exp);
						//GestionModulePHPProgress e2 = new GestionModulePHPProgress();
						
//						if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE)) {
//							gestionModulePHP.sauvegardeBoutique(typeSvgAvantMaj);
//						}
						
						PlatformUI.getWorkbench().getProgressService().run(false, false, wsp);
						//PlatformUI.getWorkbench().getProgressService().run(false, false, e2);
						//PlatformUI.getWorkbench().getProgressService().run(true, false, e);
						
//						if(!Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.PREMIER_TRANSFERT_EFFECTUE)) {
//							logger.info("Verrouillage de certaines préférences du module de gestion de la boutique après le premier envoi de données.");
//							Activator.getDefault().getPreferenceStoreProject().setDefault(PreferenceConstantsProject.PREMIER_TRANSFERT_EFFECTUE,true);
//							Activator.getDefault().getPreferenceStoreProject().setValue(PreferenceConstantsProject.PREMIER_TRANSFERT_EFFECTUE,true);
//							
//							((IPersistentPreferenceStore)Activator.getDefault().getPreferenceStoreProject()).save();
							
//							  Preferences prefs =
//								    Platform.getPreferencesService().getRootNode().node(Plugin.PLUGIN_PREFEERENCES_SCOPE).node(Activator.PLUGIN_ID);
							    //InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID); // does all the above behind the scenes
							//Activator.getDefault().getPreferenceStoreProject().
							
//						}
						String messageFin = "Exportation terminée.";
						if(res!=null && res.getListeArticleEnvoyes()!=null) {
							messageFin += "\n"+res.getListeArticleEnvoyes().size()+" articles envoyés vers la boutique.";
						}
						
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Exportation du Catalogue Web", messageFin);
						
//						if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE)) {
//							gestionModulePHP.sauvegardeBoutique(typeSvgApresMaj);
//						}
					}
				} catch (InvocationTargetException e) {
					logger.error("", e);
				} catch (InterruptedException e) {
					logger.error("", e);
				}

//				logger.error("Appel site web");
//				if(exp instanceof ExportationArticle)
//					((ExportationArticle)exp).declencheMAJSite();
//				logger.error("Site web appelé");
			}

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
