package fr.legrain.prestashop.ws;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;


public class WSPrestashopProgress implements IRunnableWithProgress {
	
	static Logger logger = Logger.getLogger(WSPrestashopProgress.class.getName());
	
	private static final String typeSvgAvantMaj = "avant_update";
	private static final String typeSvgApresMaj = "apres_update";
	
	public static int TYPE_EXPORT = 1;
	public static int TYPE_IMPORT = 2;
	
	private int type = 0;
	private Boolean sauvegardeAuto = false;
	
	private String authTokenName = null; 
	private String authTokenValue = null; 
	private String urlString = null;
	
	private float reductionColis = 0f; 
	private int stockDefautExportBoutique = 0;
	
	private WSPrestashop ws = null;
	
	/**
	 * 
	 * @param type - import ou export
	 * @param modeTTC - mode de génération des commandes à l'importation
	 * @param sauvegarde - si true, sauvegarde automatique de la boutique (mysqldump + tar)
	 */
	public WSPrestashopProgress(int type, Boolean modeTTC, Boolean sauvegarde) {
		this.type = type;
		this.sauvegardeAuto = sauvegarde;
		ws = new WSPrestashop(modeTTC);
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			
			TaSynchroBoutiqueDAO dao = new TaSynchroBoutiqueDAO();
			TaSynchroBoutique taSynchroBoutique = dao.findInstance();
			
			if(type == TYPE_EXPORT) {
				
//				if(sauvegardeAuto) {
//					monitor.beginTask("Sauvegarde de la boutique Prestashop ...",IProgressMonitor.UNKNOWN);
//					sauvegardeBoutique(typeSvgAvantMaj);
//				}

				ws.preparationExportation();

				int total = ws.getListeCategorieArticleBDG().size()*2; //x2 car 1 boucle pour creation/maj et une pour hierarchie
				total += ws.getListeArticleBDG().size();
				String taskLibelle = "Exportation de vos données vers la boutique ...";
				//monitor.beginTask("Exportation de vos données vers la boutique ...", 100);	
				monitor.beginTask(taskLibelle, total);
				
				if(sauvegardeAuto) {
					monitor.setTaskName("Sauvegarde de la boutique Prestashop avant exportation ...");
					sauvegardeBoutique(typeSvgAvantMaj);
				}
				
				
				ws.setReductionColis(reductionColis);
				ws.setQuantiteDefautArticle(stockDefautExportBoutique);
				monitor.setTaskName("Exportation ...");
				ws.exportProdcut(monitor);
				
				taSynchroBoutique.setDerniereExport(new Date());
				EntityTransaction tx = dao.getEntityManager().getTransaction();
				tx.begin();
				dao.enregistrerMerge(taSynchroBoutique);
				dao.commit(tx);
				
				if(sauvegardeAuto) {
					//monitor.beginTask("Sauvegarde de la boutique Prestashop ...",IProgressMonitor.UNKNOWN);
					monitor.subTask("Sauvegarde de la boutique Prestashop après exportation...");
					sauvegardeBoutique(typeSvgApresMaj);
				}
				
			} else if(type == TYPE_IMPORT) {
				ws.preparationImportation();
				
				int total = ws.getListeCustomers().getCustomers().size();
				total += ws.getListeOrders().getOrders().size();
				//monitor.beginTask("Exportation de vos données vers la boutique ...", 100);	
				monitor.beginTask("Importation de vos données dans le Bureau de gestion ...", total);
				
				ws.importCommand(monitor);
				
				taSynchroBoutique.setDerniereSynchro(new Date());
				EntityTransaction tx = dao.getEntityManager().getTransaction();
				tx.begin();
				dao.enregistrerMerge(taSynchroBoutique);
				dao.commit(tx);
			}
			
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (JAXBException e) {
			logger.error("", e);
		}
//		if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE)) {
//			
//			runSubTaskSauvegardeAvantMaj(new SubProgressMonitor(monitor,10));
//			runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,40));
//			runSubTaskUpdateSite(new SubProgressMonitor(monitor,40));
//			runSubTaskSauvegardeApresMaj(new SubProgressMonitor(monitor,10));
//			
//		} else {
		catch (Exception e) {
			logger.error("", e);
			
			final Exception ex = e;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur",
							"Erreur lors de la synchronisation avec la boutique : " +
							"\n" +
							"\n" +
							ex.getMessage());
				}
			}); 
			
//			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur",
//					"Erreur lors de la synchronisation avec la boutique : " +
//					"\n" +
//					"\n" +
//					e.getMessage());
		}

//			runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,50));
			//runSubTaskUpdateSite(new SubProgressMonitor(monitor,50));
			
//		}

		//		monitor.beginTask("Mise à jour du site",IProgressMonitor.UNKNOWN);
		//		monitor.subTask("Mise à jour du site .");
		//		new ExportationArticle().declencheMAJSite();
		monitor.done();
	}
	
//	private void runSubTaskEnvoyerFTP(IProgressMonitor monitor) {
//		//File f = new File(path);
//		//monitor.beginTask("",(int)f.length());
//		int taille = 0;
//		String pathRepExportWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
//		taille += UtilProgress.getFileSize(new File(pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE)));
//		taille += UtilProgress.getFileSize(new File(pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL)));
//		taille += UtilProgress.getFileSize(new File(pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE)));
//		
//		System.err.println("Taille rep img = "+taille);
//		monitor.beginTask("",taille);
//		monitor.subTask("Transfert des données sur le serveur.");
//		//boolean flag = transfert(monitor);
//		exp.transfert(monitor);
//		monitor.done();
//		
//		//f.delete();
//		//return flag;
//	}
	
	public void sauvegardeBoutique() {

		UtilHTTP.post(urlString, authTokenName, authTokenValue);
	}
	
	public void sauvegardeBoutique(String typeSauvegarde) throws Exception {
		String nomParamPostPHPSauvegarde = "prefixe";
				
		Map<String,String> param = new HashMap<String, String>();
		param.put(authTokenName, authTokenValue);
		param.put(nomParamPostPHPSauvegarde, typeSauvegarde);

		logger.info("Sauvegarde de la boutique == Type["+typeSauvegarde+"]");
		String retour = UtilHTTP.post(urlString, param);
		
		//Chaine définie dans le fichier php effectuant la sauvegarde
		String retourOk = "Votre base est en cours de sauvegarde.......C'est fini. Vous pouvez recuperer la base par FTP";
		if(!retour.equals(retourOk)) {
			if(!MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur",
					"Une erreur s'est produite lors de la sauvegarde automatique de la boutique." +
					"\n" +
					"Souhaitez vous continuer quand même ?")
					) {
				throw new Exception("Erreur lors de la sauvegarde de la boutique");
			}
				
		}
			
	}

	public void setSauvegardeAuto(Boolean sauvegardeAuto) {
		this.sauvegardeAuto = sauvegardeAuto;
	}

	public void setAuthTokenName(String authTokenName) {
		this.authTokenName = authTokenName;
	}

	public void setAuthTokenValue(String authTokenValue) {
		this.authTokenValue = authTokenValue;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public void setReductionColis(float reductionColis) {
		this.reductionColis = reductionColis;
	}

	public void setStockDefautExportBoutique(int stockDefautExportBoutique) {
		this.stockDefautExportBoutique = stockDefautExportBoutique;
	}

}
