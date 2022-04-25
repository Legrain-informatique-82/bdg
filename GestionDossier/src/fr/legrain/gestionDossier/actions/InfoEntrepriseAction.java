package fr.legrain.gestionDossier.actions;


import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestionDossier.ecran.ParamInfoEntreprise;
import fr.legrain.gestionDossier.ecran.SWTPaInfoEntrepriseSimpleController;
import fr.legrain.gestionDossier.wizards.PaInfoEntrepriseSimpleSWT;
import fr.legrain.lib.gui.ParamAfficheSWT;



/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class InfoEntrepriseAction implements IWorkbenchWindowActionDelegate {
	static Logger logger = Logger.getLogger(InfoEntrepriseAction.class.getName());
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public InfoEntrepriseAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {
//			PaInfoEntreprise paInfoEntreprise = new PaInfoEntreprise();
//			PaInfoEntrepriseController paTvaController = new PaInfoEntrepriseController(paInfoEntreprise);
//			ParamInfoEntreprise paramAfficheInfoEntreprise= new ParamInfoEntreprise();
//			paramAfficheInfoEntreprise.setFocusDefaut(paInfoEntreprise.getJdbGrille());
//			paramAfficheInfoEntreprise.setFocus(paInfoEntreprise.getJdbGrille());
//			LgrShellUtil.affiche(paramAfficheInfoEntreprise,null,paInfoEntreprise,paTvaController,window.getShell());
//			paramAfficheInfoEntreprise.getFocusDefaut().requestFocus();
			
//			PaInfoEntrepriseSimple paInfoEntreprise = new PaInfoEntrepriseSimple();
//			PaInfoEntrepriseSimpleController paTvaController = new PaInfoEntrepriseSimpleController(paInfoEntreprise);
//			ParamInfoEntreprise paramAfficheInfoEntreprise= new ParamInfoEntreprise();
//			paramAfficheInfoEntreprise.setFocusDefaut(paInfoEntreprise.getTfNOM());
//			paramAfficheInfoEntreprise.setFocus(paInfoEntreprise.getTfNOM());
//			LgrShellUtil.affiche(paramAfficheInfoEntreprise,null,paInfoEntreprise,paTvaController,window.getShell());
//			if(paramAfficheInfoEntreprise.getFocus()!=null)
//				paramAfficheInfoEntreprise.getFocusDefaut().requestFocus();
			
			
			Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);
			PaInfoEntrepriseSimpleSWT paInfoEntrepriseSimpleSWT = new PaInfoEntrepriseSimpleSWT(s,SWT.NULL);
			SWTPaInfoEntrepriseSimpleController swtPaInfoEntrepriseSimpleController = new SWTPaInfoEntrepriseSimpleController(paInfoEntrepriseSimpleSWT);
			ParamInfoEntreprise paramInfoEntreprise = new ParamInfoEntreprise();
			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			LgrShellUtil.afficheSWT(paramInfoEntreprise, null, paInfoEntrepriseSimpleSWT, swtPaInfoEntrepriseSimpleController, s);
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}
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