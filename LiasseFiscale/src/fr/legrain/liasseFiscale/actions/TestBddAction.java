package fr.legrain.liasseFiscale.actions;

import java.math.BigDecimal;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.liasseFiscale.db.dao.Initial;
import fr.legrain.liasseFiscale.db.ibTables.IbTaInitial;
import fr.legrain.lib.data.ExceptLgr;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class TestBddAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	public TestBddAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		//MessageDialog.openInformation(
		//	window.getShell(),
		//	"LiasseFiscale Plug-in",
		//	"Liasse Fiscale");
		
		//new Test().test();
		
		changeBd();
		
	}
	
	public void changeBd() {
		Initial i = new Initial();
		i.setLibelleCpt("lib");
		i.setNumCpt("1000");
		i.setMtDebit(new BigDecimal(15));
		i.setMtCredit(new BigDecimal(30));
		
		IbTaInitial ib = new IbTaInitial();
		try {
			ib.insertion(i);
			ib.enregistrement(i);
		} catch (ExceptLgr e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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