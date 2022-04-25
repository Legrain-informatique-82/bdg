//package fr.legrain.facture.handlers;
//import java.io.File;
//
//import org.apache.log4j.Logger;
//import org.eclipse.core.commands.ExecutionEvent;
//import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.core.commands.IHandlerListener;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.ui.IWorkbenchWindow;
//import org.eclipse.ui.PlatformUI;
//
//import fr.legrain.facture.controller.SWTPaImpressionFactureController;
//import fr.legrain.facture.divers.ParamImpressionFacture;
//import fr.legrain.facture.ecran.PaImpressionFactureSWT;
//import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
//import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
//import fr.legrain.lib.gui.ParamAfficheSWT;
//
//
//public class HandlerImpressionFacture extends LgrAbstractHandler {
//
//	static Logger logger = Logger.getLogger(HandlerImpressionFacture.class.getName());
//
//	private IWorkbenchWindow window;
//
//	public void addHandlerListener(IHandlerListener handlerListener) {
//		// TODO Auto-generated method stub
//	}
//
//	public void dispose() {
//		// TODO Auto-generated method stub
//	}
//	
////	public void imprimerSelection(int idDoc,final String codeDoc,boolean preview){
////		try {
////			Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
////			String reportFileLocation =FacturePlugin.getDefault().getPreferenceStore().getDefaultString(PreferenceConstants.P_PATH_EDITION_DEFAUT);
////			if (reportFileLocation == null)
////				reportFileLocation = "/report/defaut/Fiche_facture.rptdesign";
////			//PreferenceConstants.AFFICHER_SELECTION_EDITION
////			if(FacturePlugin.getDefault().getPreferenceStore().getDefaultBoolean(PreferenceConstants.AFFICHER_SELECTION_EDITION)){
////			ConstEdition constEdition = new ConstEdition();
////			File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
////					"/report/defaut/", "");
////			
////			final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Tiers>>
////			
////			ConstEdition.CONTENT_COMMENTS =ConstEditionFacture.COMMENTAIRE_EDITION_DEFAUT;
////			
////			String FloderEdition="";
////			if(ConstEdition.pathRepertoireEditionsSpecifiquesClient()!=null)	
////			  FloderEdition = ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+namePlugin;
////			//////*********************
////			
////
////			File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);			
////			String nomOnglet = ConstEdition.EDITION+namePlugin;
////			Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
////					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
////					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
////			dialogShell.setText(ConstEdition.TITLE_SHELL);
////			dialogShell.setLayout(new FillLayout());
////			//dialogShell
////			
////			SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
////			
////			LinkedList<Integer> idSWTTiers = new LinkedList<Integer>();
////			LinkedList<Integer> oneIDSWTTiers = new LinkedList<Integer>();
////			idSWTTiers.add(idDoc);
////			idSWTTiers.add(idDoc);
////			oneIDSWTTiers.add(idDoc);
////			oneIDSWTTiers.add(idDoc);
////
////			constEdition.addValues(idSWTTiers,oneIDSWTTiers);
////			constEdition.setPARAM_ID_TABLE(ConstEditionFacture.PARAM_ID_FACTURE);
////			constEdition.paramId = ConstEditionFacture.PARAM_ID_FACTURE;
////			
////			constEdition.openDialogChoixEdition_new(dialogReport, FloderFileEditions, 
////					reportFileLocation, namePlugin,nomOnglet,dialogShell,reportFile);
////		
////			}else{
////				
////			}
////
////		} catch (Exception ex) {
////			logger.error(ex);
////		}
////		
////	}
//
////	public void imprimer(String[] idFactureAImprimer,boolean preview) throws Exception{		
////		//Chemin de l'edition
////		PlatformUI.getWorkbench().getActiveWorkbenchWindow();
////		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
////		URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry("/report/defaut/Fiche_facture.rptdesign"));
////		URI uriReportFile = new URI("file",urlReportFile.getAuthority(),urlReportFile.getPath(),urlReportFile.getQuery(),null);
////		File reportFile = new File(uriReportFile);
////		
////		//Preparation de l'edition
////		BirtUtil.startReportEngine();
////		
////		SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1",null);
////		
////		HashMap<String,String> reportParam = new HashMap<String,String>();
////		reportParam.put("paramUrlJDBC",IB_APPLICATION.findDatabase().getConnection().getConnectionURL());
////		reportParam.put("capital",infoEntreprise.getCAPITAL_INFO_ENTREPRISE());
////		reportParam.put("ape",infoEntreprise.getAPE_INFO_ENTREPRISE());
////		reportParam.put("siret",infoEntreprise.getSIRET_INFO_ENTREPRISE());
////		reportParam.put("rcs",infoEntreprise.getRCS_INFO_ENTREPRISE());
////		reportParam.put("nomEntreprise",infoEntreprise.getNOM_INFO_ENTREPRISE());
////		
////		LgrSpooler spooler = LgrSpooler.getInstance();
////	
////		final String[] finalIdFactureAImprimer = idFactureAImprimer;
////		final File finalReportFile = reportFile;
////		final HashMap finalReportParam = reportParam;
////		final LgrSpooler finalSpooler = spooler;
////		Job job = new Job("Préparation de l'impression") {
////			protected IStatus run(IProgressMonitor monitor) {
////				final int ticks = finalIdFactureAImprimer.length;
////				monitor.beginTask("Préparation de l'impression", ticks);
////				try {
////					OutputStream os = null;
////					for (int i = 0; i < finalIdFactureAImprimer.length; i++) {
////						finalReportParam.put("paramID_DOC",finalIdFactureAImprimer[i]);
////						os = BirtUtil.renderReportToStream(finalReportFile.getAbsolutePath(),finalReportParam,BirtUtil.PDF_FORMAT);
////						finalSpooler.add(os);
////						monitor.worked(1);
////						if (monitor.isCanceled()) {
////							finalSpooler.clear();
////							return Status.CANCEL_STATUS;
////						}
////					}
////				} finally {
////					monitor.done();
////				}
////				return Status.OK_STATUS;
////			}
////		};
////		job.setPriority(Job.SHORT);
////		//job.setUser(true);
////		job.schedule(); 
////		job.join();
////		
////		//Impression
////		if(job.getResult()==Status.OK_STATUS)
////			spooler.print(preview);
////		
////		BirtUtil.destroyReportEngine();		
////	}
//
//
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		try {			
//			Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);		
//			PaImpressionFactureSWT paImpressionFacture = new PaImpressionFactureSWT(s,SWT.NULL);
//			SWTPaImpressionFactureController paImpressionFactureController = new SWTPaImpressionFactureController(paImpressionFacture);
//			ParamImpressionFacture paramImpressionFacture= new ParamImpressionFacture();
//			paramImpressionFacture.setFocusSWT(paImpressionFacture.getTfNumDeb());
//			paramImpressionFacture.setFocusSWT(paImpressionFacture.getTfNumDeb());
//			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
//			paramAfficheSWT.setHauteur(180);
//			paramAfficheSWT.setLargeur(400);
//			paramAfficheSWT.setTitre("Choix des factures à imprimer.");
//			LgrShellUtil.afficheSWT(paramImpressionFacture, paramAfficheSWT, paImpressionFacture, paImpressionFactureController, s);
////			LgrShellUtil.affiche(paramExportationFacture,paramAfficheSWT,paExportationFacture,paExportationFactureController,window.getShell());
//			if(paramImpressionFacture.getFocus()!=null)
//				paramImpressionFacture.getFocusDefaut().requestFocus();
//		} catch (Exception e) {
//			logger.error("Erreur : run", e);
//		}
//		return null;
//	}
//
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean isHandled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public void removeHandlerListener(IHandlerListener handlerListener) {
//		// TODO Auto-generated method stub
//	}
//
//
//
//	//Deletes all files and subdirectories under dir.
//	// Returns true if all deletions were successful.
//	// If a deletion fails, the method stops attempting to delete and returns false.
//	public static boolean deleteDir(File dir) {
//		if (dir.isDirectory()) {
//			String[] children = dir.list();
//			for (int i=0; i<children.length; i++) {
//				boolean success = deleteDir(new File(dir, children[i]));
//				if (!success) {
//					return false;
//				}
//			}
//		}
//		return dir.delete();
//	}
//
//}
