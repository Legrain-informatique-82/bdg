package fr.legrain.tiers.divers;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.SwtCompositeReport_new;
import fr.legrain.edition.actions.SwtReportWithExpandbar;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.ecran.ConstEditionTiers;
import fr.legrain.tiers.preferences.PreferenceConstants;


public class Impression {
	Shell shellParent = null;
	ConstEdition constEdition = null;
	
	private TaTiers taTiers = null;
	private Collection collection;
	private String nomChampIdTable;
	private Object object = null;
	private Integer id;
	private EntityManager entityManager;
	
	public Impression(Shell s){
		setShellParent(s);	
	}
	public Impression(){	
	
	}
	
	public Impression(ConstEdition constEdition,Object object,Collection collection, String nomChampIdTable,int id){	
		this( constEdition, object, collection,  nomChampIdTable,id,null);
	}
	public Impression(ConstEdition constEdition,Object object,Collection collection, String nomChampIdTable,int id,
			EntityManager entityManager){	
		this.constEdition = constEdition;
		this.object = object;
		this.collection = collection;
		this.nomChampIdTable = nomChampIdTable;
		this.id = Integer.valueOf(id);
		this.entityManager = entityManager;
	}
	
	static Logger logger = Logger.getLogger(Impression.class.getName());

	public void imprimer(boolean preview,String pathEditionDynamique,String fileEditionDefaut,String namePlugin,String nomEntity) throws Exception{
		imprimer(preview, pathEditionDynamique, fileEditionDefaut, namePlugin, nomEntity,false);
	}
	//public void imprimer(String[] idFactureAImprimer,String oneIdAImprimer,boolean preview,String pathEditionDefaut) throws Exception{
	public void imprimer(boolean preview,String pathEditionDynamique,String fileEditionDefaut,String namePlugin,String nomEntity,
			boolean flag) throws Exception{
		
		IPreferenceStore preferenceStore = TiersPlugin.getDefault().getPreferenceStore();
		
		String nomOnglet = ConstEdition.EDITION + namePlugin;
		HashMap<String,String> reportParam = new HashMap<String,String>();
		
		String reportFileLocationDefaut = null;
		boolean affiche = preferenceStore.getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
		
		/*** 08/02/2010 **/
		boolean imprimerDirect =  preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
		boolean afficheEditionAImprimer =  preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);
		IPreferenceStore preferenceStoreEdition = Activator.getDefault().getPreferenceStore();
		String pathFileAImprimer = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
				   PATH_SAVE_PDF);
		String pathAdobeReader = preferenceStoreEdition.getString(fr.legrain.edition.preferences.PreferenceConstants.
			     PATH_ACROBAT_READER);
		
		/********************/
		boolean buttonEditionDefaut = false;
//		boolean flagMessageEditionPreference = false;
//		boolean flagImprimeTypeEntity = false; 
		
		LinkedList<Integer> idEntity = new LinkedList<Integer>();
		idEntity.add(id);
		constEdition.setIdOne(idEntity);
		constEdition.setObjectEntity(object);
		constEdition.setCollection(collection);
		constEdition.setNomChampIdTable(nomChampIdTable);
		constEdition.setFlagButtonOneFiche(flag);
		constEdition.setNameEntity(nomEntity);
		
		/** 31/12/2009 **/
		constEdition.setPARAM_ID_TABLE(ConstEditionTiers.PARAM_REPORT_ID_TIER);
		constEdition.paramId = ConstEditionTiers.PARAM_REPORT_ID_TIER;
		ImprimeObjet.clearListAndMap();
//		constEdition.prepartionEditionBirt(this.entityManager);
		
		if(!flag){
			AffichageEdition affichageEdition = new AffichageEdition(this.entityManager);
			affichageEdition.setAppContextEdition(new EditionAppContext().getExtensionMap());
			constEdition.afficheEditionDynamiqueDirect(affichageEdition, pathEditionDynamique, nomOnglet);
		}else{
			try {
				/**
				 * affiche is false ==> ne affiche pas les choix des edition.pour obtenir l'edition
				 * 						prendre la chemin de l'edition dans le preference  
				 */
				if(!affiche){
					/** 31/12/2009 add ( new ) **/
					constEdition.editionDirect(namePlugin, nomOnglet, reportParam,false,imprimerDirect,
							afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity,
							Const.C_NOM_VU_ARTICLE,false,fileEditionDefaut);
				}else{
//					String reportFileLocation =TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
					
					reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,TiersPlugin.PLUGIN_ID);
					if(reportFileLocationDefaut!=null){
						File fileReportFileLocationDefaut = new File(reportFileLocationDefaut);
						if(fileReportFileLocationDefaut.exists()){
							buttonEditionDefaut = true;
						}
					}
					//if (reportFileLocationDefaut == null){
					else{
						reportFileLocationDefaut = fileEditionDefaut;
					}
									
					File fileReportDynamique = new File(pathEditionDynamique);			
					
					ConstEdition.CONTENT_COMMENTS =ConstEdition.COMMENTAIRE_EDITION_DEFAUT;

					/**
					 * trouver path de l'edition pour un client spécific 
					 * EX : /home/lee/testJPA26052009/1715/EditionsClient/Tiers
					 */
					String FloderEditionSpecifiquesClient="";
					if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)	
						FloderEditionSpecifiquesClient = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+
						ConstEdition.SEPARATOR+namePlugin+ConstEdition.SEPARATOR+nomEntity;
					//File fileEditionSpecifiquesClient = constEdition.makeFolderEditions(FloderEditionSpecifiquesClient);
					File fileEditionSpecifiquesClient = new File(FloderEditionSpecifiquesClient);
					/**
					 * trouver path de l'edition pour plugin EditionSpecifique 
					 * EX : /home/lee/testJPA26052009/EditionsSpecifiques/Editions/Tiers/TaTiers
					 */
					String FloderEditionSpecifiques="";
					if(ConstEdition.pathRepertoireEditionsSpecifiques()!=null)
						FloderEditionSpecifiques = ConstEdition.pathRepertoireEditionsSpecifiques()+ConstEdition.SEPARATOR+
						namePlugin+ConstEdition.SEPARATOR+nomEntity;    
					File fileEditionSpecifiques = new File(FloderEditionSpecifiques);

				
					
					Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
							SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
					dialogShell.setText(ConstEdition.TITLE_SHELL);
					dialogShell.setLayout(new FillLayout());
					/**
					 * sans Expandbar
					 */
//					SwtCompositeReport_new ecranDialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//					
//					ConstEdition.addCommentToEditionDefaut(ecranDialogReport.getRadioReportDefaut(), 
//							buttonEditionDefaut, ConstEdition.COMMENT_EDITION_DEFAUT,nameOnglet);
					
					//ecranDialogReport.getRadioReportDefaut().setText(string);
					
					/**
					 * With Expandbar
					 */
					SwtReportWithExpandbar ecranDialogReport = new SwtReportWithExpandbar(dialogShell,SWT.NULL);
//					LinkedList<Integer> idSWTTiers = new LinkedList<Integer>();
//					LinkedList<Integer> oneIDSWTTiers = new LinkedList<Integer>();
					
//					for (int i = 0; i < idFactureAImprimer.length; i++) {
//						idSWTTiers.add(LibConversion.stringToInteger(idFactureAImprimer[0]));
//					}
//						oneIDSWTTiers.add(LibConversion.stringToInteger(oneIdAImprimer));

//					constEdition.addValues(idSWTTiers,oneIDSWTTiers);
//					constEdition.setPARAM_ID_TABLE(ConstEdition.PARAM_ID_DOC);
//					constEdition.paramId = ConstEdition.PARAM_ID_DOC;
					
//					constEdition.addValues(idSWTTiers,oneIDSWTTiers);
					
//					constEdition.setPARAM_ID_TABLE(ConstEditionTiers.PARAM_REPORT_ID_TIER);
//					constEdition.paramId = ConstEditionTiers.PARAM_ID_TIERS;
					
//					constEdition.openDialogChoixEdition_Defaut(dialogReport, FloderFileEditions,
//							reportFileLocation, namePlugin,nomOnglet,dialogShell,reportFile,affiche,reportFileLocation,reportParam);
					/**
					 * sans Expandbar
					 */
//					constEdition.openDialogChoixEdition_Defaut(ecranDialogReport, fileEditionSpecifiquesClient, 
//							reportFileLocationDefaut, namePlugin,nomOnglet,dialogShell,fileReportDynamique,affiche,
//							reportParam,fileEditionSpecifiques,buttonEditionDefaut);
				
//					}else{
//						imprimerModeleDefaut(idFactureAImprimer,preview);
//					}
					/** ajouter 28/12/2009 **/
					constEdition.setCommentEditionDynamique(ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT);
					constEdition.fillMapNameExpandbar(false);
//					constEdition.addExpandItem(ecranDialogReport.getExpandBarEdition());
					
					constEdition.openDialogChoixEditionDefaut(ecranDialogReport,fileEditionSpecifiquesClient, 
					reportFileLocationDefaut, namePlugin,nomOnglet,dialogShell,fileReportDynamique,affiche,
					reportParam,fileEditionSpecifiques,buttonEditionDefaut,imprimerDirect,
					afficheEditionAImprimer,pathFileAImprimer,pathAdobeReader,nomEntity);
					
				}

			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		
	}

	
//	public void imprimerSelection(int idDoc,final String codeDoc,boolean preview){
//		boolean affiche =TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
//		try {
//			Bundle bundleCourant = TiersPlugin.getDefault().getBundle();
//			String reportFileLocation =TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
//			reportFileLocation = ConstEdition.pathFichierEditionsSpecifiques(reportFileLocation,TiersPlugin.PLUGIN_ID);
//			if (reportFileLocation == null)
//				reportFileLocation = ConstEdition.FICHE_FILE_REPORT_TIERS;
//			//PreferenceConstants.AFFICHER_SELECTION_EDITION
//			//if(affiche){
//			ConstEdition constEdition = new ConstEdition();
//			File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
//					"/report/defaut/", "");
//			
//			final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Tiers>>
//			
//			ConstEdition.CONTENT_COMMENTS =ConstEdition.COMMENTAIRE_EDITION_DEFAUT;
//			
//			String FloderEdition="";
//			if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)	
//			  FloderEdition = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+namePlugin;
//			//////*********************
//			
//
//			File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);			
//			String nomOnglet = ConstEdition.EDITION+namePlugin;
//			Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
//					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
//			dialogShell.setText(ConstEdition.TITLE_SHELL);
//			dialogShell.setLayout(new FillLayout());
//			//dialogShell
//			
//			SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//			
//			LinkedList<Integer> idSWTTiers = new LinkedList<Integer>();
//			LinkedList<Integer> oneIDSWTTiers = new LinkedList<Integer>();
//			idSWTTiers.add(idDoc);
//			oneIDSWTTiers.add(idDoc);
//
//			constEdition.addValues(idSWTTiers,oneIDSWTTiers);
//			constEdition.setPARAM_ID_TABLE(ConstEdition.PARAM_ID_DOC);
//			constEdition.paramId = ConstEdition.PARAM_ID_DOC;
//			HashMap<String,String> reportParam = new HashMap<String,String>();
//		
//			constEdition.openDialogChoixEdition_Defaut(dialogReport, FloderFileEditions, 
//					reportFileLocation, namePlugin,nomOnglet,dialogShell,reportFile,affiche,reportFileLocation,reportParam);
//		
////			}else{
////				imprimerModeleDefaut(idDoc,codeDoc,preview);
////			}
//
//		} catch (Exception ex) {
//			logger.error(ex);
//		}
//		
//	}


	public Shell getShellParent() {
		return shellParent;
	}


	public void setShellParent(Shell shellParent) {
		this.shellParent = shellParent;
	}

	
//	public void imprimerModeleDefaut(int idDoc,final String codeDoc,boolean preview){
//		try {
//			String url = "http://" + WebappAccessor.getHost() + ":"
//					+ WebappAccessor.getPort() + "/birt/";
//
//			System.setProperty("RUN_UNDER_ECLIPSE", "true");
//
//			url += "run?__report=";
//
//			Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
//			String reportFileLocation =FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
//			if (reportFileLocation == null){
//				reportFileLocation = ConstEdition.FICHE_FILE_REPORT_FACTURE;
//			URL urlReportFile = Platform.asLocalURL(bundleCourant
//					.getEntry(reportFileLocation));
//			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
//					urlReportFile.getPath(), urlReportFile.getQuery(), null);
//			File reportFile = new File(uriReportFile);
//			url += reportFile.getAbsolutePath();
//			}else
//				url += reportFileLocation;
//
//			url += "&paramID_DOC=" + idDoc;
//			url += "&paramUrlJDBC="
//					+ IB_APPLICATION.findDatabase().getConnection()
//							.getConnectionURL();
//
//			SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE
//					.infosEntreprise("1", null);
//			url += "&capital=" + infoEntreprise.getCAPITAL_INFO_ENTREPRISE();
//			url += "&ape=" + infoEntreprise.getAPE_INFO_ENTREPRISE();
//			url += "&siret=" + infoEntreprise.getSIRET_INFO_ENTREPRISE();
//			url += "&rcs=" + infoEntreprise.getRCS_INFO_ENTREPRISE();
//			url += "&nomEntreprise=" + infoEntreprise.getNOM_INFO_ENTREPRISE();
//			url += "&__document=doc"+new Date().getTime();
//
//			url += "&__format=pdf";
//
//			logger.debug("URL edition: " + url);
//			final String finalURL = url;
//			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					try {
//						PlatformUI.getWorkbench().getBrowserSupport()
//								.createBrowser(
//										IWorkbenchBrowserSupport.AS_EDITOR,
//										"myId",
//										"Prévisualisation de la facture "
//												+ codeDoc,
//										"Prévisualisation de la facture "
//												+ codeDoc).openURL(
//										new URL(finalURL));
//						// oldFacture = new OldFacture();//réinitialisation de
//						// cet objet
//					} catch (PartInitException e) {
//						logger.error("", e);
//					} catch (MalformedURLException e) {
//						logger.error("", e);
//					}
//				}
//			});
//////			
//////			SWTPaBrowserController.openURL(finalURL, 
//////					"Prévisualisation de la facture "+ codeFacture, 
//////					"Prévisualisation de la facture "+ codeFacture);
//
//		} catch (Exception ex) {
//			logger.error(ex);
//		}
//		
//	}

	
//	protected void imprimerModeleDefaut(String[] idFactureAImprimer,boolean preview) throws Exception{		
//	//Chemin de l'edition
//		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
//		File reportFile = null;
//		String reportFileLocation =FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
//		if (reportFileLocation == null){
//			reportFileLocation = ConstEdition.FICHE_FILE_REPORT_FACTURE;
//		URL urlReportFile = Platform.asLocalURL(bundleCourant
//				.getEntry(reportFileLocation));
//		URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
//				urlReportFile.getPath(), urlReportFile.getQuery(), null);
//		reportFile = new File(uriReportFile);
//		}else
//			reportFile = new File(reportFileLocation);
//	
//	//Preparation de l'edition
//	BirtUtil.startReportEngine();
//	
//	SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1",null);
//	
//	HashMap<String,String> reportParam = new HashMap<String,String>();
//	reportParam.put("paramUrlJDBC",IB_APPLICATION.findDatabase().getConnection().getConnectionURL());
//	reportParam.put("capital",infoEntreprise.getCAPITAL_INFO_ENTREPRISE());
//	reportParam.put("ape",infoEntreprise.getAPE_INFO_ENTREPRISE());
//	reportParam.put("siret",infoEntreprise.getSIRET_INFO_ENTREPRISE());
//	reportParam.put("rcs",infoEntreprise.getRCS_INFO_ENTREPRISE());
//	reportParam.put("nomEntreprise",infoEntreprise.getNOM_INFO_ENTREPRISE());
//
//	
//	
//	LgrSpooler spooler = LgrSpooler.getInstance();
//
//	final String[] finalIdFactureAImprimer = idFactureAImprimer;
//	final File finalReportFile = reportFile;
//	final HashMap finalReportParam = reportParam;
//	final LgrSpooler finalSpooler = spooler;
//	Job job = new Job("Préparation de l'impression") {
//		protected IStatus run(IProgressMonitor monitor) {
//			final int ticks = finalIdFactureAImprimer.length;
//			monitor.beginTask("Préparation de l'impression", ticks);
//			try {
//				OutputStream os = null;
//				for (int i = 0; i < finalIdFactureAImprimer.length; i++) {
//					finalReportParam.put("paramID_DOC",finalIdFactureAImprimer[i]);
//					os = BirtUtil.renderReportToStream(finalReportFile.getAbsolutePath(),finalReportParam,BirtUtil.PDF_FORMAT);
//					finalSpooler.add(os);
//					monitor.worked(1);
//					if (monitor.isCanceled()) {
//						finalSpooler.clear();
//						return Status.CANCEL_STATUS;
//					}
//				}
//			} finally {
//				monitor.done();
//			}
//			return Status.OK_STATUS;
//		}
//	};
//	job.setPriority(Job.SHORT);
//	//job.setUser(true);
//	job.schedule(); 
//	job.join();
//	
//	//Impression
//	if(job.getResult()==Status.OK_STATUS)
//		spooler.print(preview);
//	
//	BirtUtil.destroyReportEngine();
//}

}
