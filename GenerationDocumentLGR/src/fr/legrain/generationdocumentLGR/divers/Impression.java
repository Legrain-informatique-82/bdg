package fr.legrain.generationdocumentLGR.divers;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.SwtCompositeReport_new;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.preferences.PreferenceConstants;
import fr.legrain.lib.data.LibConversion;
import generationdocumentlgr.PluginGenerationDocumentLGR;

public class Impression {
	Shell shellParent = null;
	
	public Impression(Shell s){
		setShellParent(s);	
	}

	static Logger logger = Logger.getLogger(Impression.class.getName());

	public void imprimer(String[] idFactureAImprimer,boolean preview) throws Exception{
		boolean affiche =true;
		try {
			Bundle bundleCourant = PluginGenerationDocumentLGR.getDefault().getBundle();
			String reportFileLocation =PluginGenerationDocumentLGR.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
			if (reportFileLocation == null)
				reportFileLocation = ConstEdition.FICHE_GENERATIONDOCUMENT_LGR;
			//PreferenceConstants.AFFICHER_SELECTION_EDITION
			//if(affiche){
			ConstEdition constEdition = new ConstEdition();
			File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
					"/report/", "");
			
			final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Tiers>>
			
			ConstEdition.CONTENT_COMMENTS =ConstEdition.COMMENTAIRE_EDITION_DEFAUT;
			
			String FloderEdition="";
			if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)	
			  FloderEdition = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+namePlugin;
			//////*********************
			

			File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);			
			String nomOnglet = ConstEdition.EDITION+namePlugin;
			Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
			dialogShell.setText(ConstEdition.TITLE_SHELL);
			dialogShell.setLayout(new FillLayout());
			//dialogShell
			
			SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
			
			LinkedList<Integer> idSWTTiers = new LinkedList<Integer>();
			LinkedList<Integer> oneIDSWTTiers = new LinkedList<Integer>();
			
			for (int i = 0; i < idFactureAImprimer.length; i++) {
				idSWTTiers.add(LibConversion.stringToInteger(idFactureAImprimer[i]));
				oneIDSWTTiers.add(LibConversion.stringToInteger(idFactureAImprimer[i]));
			}

			constEdition.addValues(idSWTTiers,oneIDSWTTiers);
			constEdition.setPARAM_ID_TABLE(ConstEdition.PARAM_ID_DOC);
			constEdition.paramId = ConstEdition.PARAM_ID_DOC;
			
			HashMap<String,String> reportParam = new HashMap<String,String>();
			
			//#EDITION JPA A FAIRE
//			constEdition.openDialogChoixEdition_Defaut(dialogReport, FloderFileEditions, 
//					reportFileLocation, namePlugin,nomOnglet,dialogShell,reportFile,affiche,reportFileLocation,reportParam);
		
//			}else{
//				imprimerModeleDefaut(idFactureAImprimer,preview);
//			}

		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	
	public void imprimerSelection(int idDoc,final String codeDoc,boolean preview){
		boolean affiche =FacturePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION);
		try {
			Bundle bundleCourant = PluginGenerationDocumentLGR.getDefault().getBundle();
			String reportFileLocation =FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
			if (reportFileLocation == null)
				reportFileLocation = ConstEdition.FICHE_FILE_REPORT_FACTURE;
			//PreferenceConstants.AFFICHER_SELECTION_EDITION
			//if(affiche){
			ConstEdition constEdition = new ConstEdition();
			File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
					"/report/defaut/", "");
			
			final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Tiers>>
			
			ConstEdition.CONTENT_COMMENTS =ConstEdition.COMMENTAIRE_EDITION_DEFAUT;
			
			String FloderEdition="";
			if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)	
			  FloderEdition = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+namePlugin;
			//////*********************
			

			File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);			
			String nomOnglet = ConstEdition.EDITION+namePlugin;
			Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
			dialogShell.setText(ConstEdition.TITLE_SHELL);
			dialogShell.setLayout(new FillLayout());
			//dialogShell
			
			SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
			
			LinkedList<Integer> idSWTTiers = new LinkedList<Integer>();
			LinkedList<Integer> oneIDSWTTiers = new LinkedList<Integer>();
			idSWTTiers.add(idDoc);
			oneIDSWTTiers.add(idDoc);

			constEdition.addValues(idSWTTiers,oneIDSWTTiers);
			constEdition.setPARAM_ID_TABLE(ConstEdition.PARAM_ID_DOC);
			constEdition.paramId = ConstEdition.PARAM_ID_DOC;
			HashMap<String,String> reportParam = new HashMap<String,String>();
			
			//#EDITION JPA A FAIRE
//			constEdition.openDialogChoixEdition_Defaut(dialogReport, FloderFileEditions, 
//					reportFileLocation, namePlugin,nomOnglet,dialogShell,reportFile,affiche,reportFileLocation,reportParam);
		
//			}else{
//				imprimerModeleDefaut(idDoc,codeDoc,preview);
//			}

		} catch (Exception ex) {
			logger.error(ex);
		}
		
	}


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
