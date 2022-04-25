package fr.legrain.sauvegarde.actions;

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
 * @deprecated utiliser SauvegardeEtEnvoieParEmailAction
 */
public class SauvegardeEtEnvoieParEmailAction implements IWorkbenchWindowActionDelegate {
	
	static Logger logger = Logger.getLogger(SauvegardeEtEnvoieParEmailAction.class.getName());
	
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SauvegardeEtEnvoieParEmailAction() {
	}
	
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
//		try {
//			String message="...";
//			INFO_ENTREPRISE infoEts = INFO_ENTREPRISE.getINFO_ENTREPRISE("1",null);
//			if(infoEts!=null && infoEts.getNOM_INFO_ENTREPRISE()!=null) {
//				message="Envoie de la sauvegarde de : ";
//				message+="\n";
//				message+=infoEts.getNOM_INFO_ENTREPRISE();
//			}
//
//
//			SauvegardeAction sauv = new SauvegardeAction();			
//			EnvoieSauvegarde envoie = new EnvoieSauvegarde();
//
//			envoie.sendSauvegardeFiles(sauv.sauvegarde(),message);
//
//			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Sauvegarde et envoie par E-mail","Sauvegarde et envoie terminés.");
//		} catch(Exception e) {
//			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Sauvegarde et envoie par E-mail","Erreur durant la sauvegarde et l'envoie par E-mail.");
//			logger.error("",e);
//		}
		logger.error("utiliser SauvegardeEtEnvoieParEmailAction.java et les commande plutot que l'action");
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