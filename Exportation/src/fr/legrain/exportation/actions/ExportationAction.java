package fr.legrain.exportation.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.exportation.controllers.PaExportationFactureControllerReference;
import fr.legrain.exportation.controllers.PaExportationFactureControllerTous;
import fr.legrain.exportation.divers.ParamExportationFacture;
import fr.legrain.exportation.ecrans.PaExportationFactureTous;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.lib.gui.ParamAfficheSWT;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ExportationAction implements IWorkbenchWindowActionDelegate {
	
	static Logger logger = Logger.getLogger(ExportationAction.class.getName());
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public ExportationAction() {
	}
	
	
//	public void run(IAction action) {
////		ExportationEpicea exp = new ExportationEpicea();
////		//exp.export();
////		exp.exportProcedureStockee();
//		
//		try {			
//			PaExportationFacture paExportationFacture = new PaExportationFacture();
//			PaExportationFactureController paExportationFactureController = new PaExportationFactureController(paExportationFacture);
//			ParamExportationFacture paramExportationFacture= new ParamExportationFacture();
//			paramExportationFacture.setFocusDefaut(paExportationFacture.getTfNumDeb());
//			paramExportationFacture.setFocus(paExportationFacture.getTfNumDeb());
//			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
//			paramAfficheSWT.setHauteur(150);
//			paramAfficheSWT.setLargeur(400);
//			paramAfficheSWT.setTitre("Choix des factures à exporter.");
//			LgrShellUtil.affiche(paramExportationFacture,paramAfficheSWT,paExportationFacture,paExportationFactureController,window.getShell());
//			if(paramExportationFacture.getFocus()!=null)
//				paramExportationFacture.getFocusDefaut().requestFocus();
//		} catch (Exception e) {
//			logger.error("Erreur : run", e);
//		}
//		
////		MessageDialog.openInformation(
////		window.getShell(),"Exportation","Exportation terminée.");
//	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {			
		Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);		
		PaExportationFactureTous paExportationFacture = new PaExportationFactureTous(s,SWT.NULL);
			PaExportationFactureControllerTous paExportationFactureController = new PaExportationFactureControllerTous(paExportationFacture);
			ParamExportationFacture paramExportationFacture= new ParamExportationFacture();
			paramExportationFacture.setFocusSWT(paExportationFacture.getBtnExporter());
			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			paramAfficheSWT.setHauteur(230);
			paramAfficheSWT.setLargeur(400);
			paramAfficheSWT.setTitre("Exportation des documents");
			LgrShellUtil.afficheSWT(paramExportationFacture, paramAfficheSWT, paExportationFacture, paExportationFactureController, s);
//			LgrShellUtil.affiche(paramExportationFacture,paramAfficheSWT,paExportationFacture,paExportationFactureController,window.getShell());
			if(paramExportationFacture.getFocus()!=null)
				paramExportationFacture.getFocusDefaut().requestFocus();
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}
		
//		MessageDialog.openInformation(
//		window.getShell(),"Exportation","Exportation terminée.");
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}