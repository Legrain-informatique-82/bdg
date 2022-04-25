package fr.legrain.articles.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.SWTPaArticlesController;
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
public class SWTArticlesAction implements IWorkbenchWindowActionDelegate {
	static Logger logger = Logger.getLogger(SWTArticlesAction.class.getName());
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SWTArticlesAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {
			Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);
			PaArticleSWT paArticleSWT = new PaArticleSWT(s,SWT.NULL);
			SWTPaArticlesController swtPaArticleController = new SWTPaArticlesController(paArticleSWT);
			
			ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			 
//			LgrShellUtil.afficheSWT(paramAfficheArticles, null, paArticleSWT, swtPaArticleController, s);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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