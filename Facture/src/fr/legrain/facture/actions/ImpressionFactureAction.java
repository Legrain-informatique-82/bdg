package fr.legrain.facture.actions;


import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ImpressionFactureAction implements IWorkbenchWindowActionDelegate {
	static Logger logger = Logger.getLogger(ImpressionFactureAction.class.getName());
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public ImpressionFactureAction() {
	}

//	/**
//	 * The action has been activated. The argument of the
//	 * method represents the 'real' action sitting
//	 * in the workbench UI.
//	 * @see IWorkbenchWindowActionDelegate#run
//	 */
//	public void run(IAction action) {
//		try {			
//			PaImpressionFacture paImpressionFacture = new PaImpressionFacture();
//			PaImpressionFactureController paImpressionFactureController = new PaImpressionFactureController(paImpressionFacture);
//			ParamImpressionFacture paramImpressionFacture= new ParamImpressionFacture();
//			paramImpressionFacture.setFocusDefaut(paImpressionFacture.getTfNumDeb());
//			paramImpressionFacture.setFocus(paImpressionFacture.getTfNumDeb());
//			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
//			paramAfficheSWT.setHauteur(150);
//			paramAfficheSWT.setLargeur(400);
//			paramAfficheSWT.setTitre("Choix des factures à imprimer.");
//			LgrShellUtil.affiche(paramImpressionFacture,paramAfficheSWT,paImpressionFacture,paImpressionFactureController,window.getShell());
//			if(paramImpressionFacture.getFocus()!=null)
//				paramImpressionFacture.getFocusDefaut().requestFocus();
//		} catch (Exception e) {
//			logger.error("Erreur : run", e);
//		}
//	}
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		logger.error("utiliser HandlerImpressionFacture.java et les commande plutot que l'action");
//		try {			
//		Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);		
//			PaImpressionFactureSWT paImpressionFacture = new PaImpressionFactureSWT(s,SWT.NULL);
//			SWTPaImpressionFactureController paImpressionFactureController = new SWTPaImpressionFactureController(paImpressionFacture);
//			ParamImpressionFacture paramImpressionFacture= new ParamImpressionFacture();
//			paramImpressionFacture.setFocusSWT(paImpressionFacture.getTfNumDeb());
//			paramImpressionFacture.setFocusSWT(paImpressionFacture.getTfNumDeb());
//			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
//			paramAfficheSWT.setHauteur(150);
//			paramAfficheSWT.setLargeur(400);
//			paramAfficheSWT.setTitre("Choix des factures à imprimer.");
//			LgrShellUtil.afficheSWT(paramImpressionFacture, paramAfficheSWT, paImpressionFacture, paImpressionFactureController, s);
////			LgrShellUtil.affiche(paramExportationFacture,paramAfficheSWT,paExportationFacture,paExportationFactureController,window.getShell());
//			if(paramImpressionFacture.getFocus()!=null)
//				paramImpressionFacture.getFocusDefaut().requestFocus();
//		} catch (Exception e) {
//			logger.error("Erreur : run", e);
//		}
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