package fr.legrain.analyseeconomique.direct;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.liasseFiscale.wizards.HeadlessLiasse;

public class ActionHeadlessAE implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
//		HeadlessLiasse h = new HeadlessLiasse("/home/nicolas/Desktop/ExportLiasse_915602008.Txt",true);
//		HeadlessAnalyseEco l = new HeadlessAnalyseEco();
//		l.traitementAnalyseEco(h.traitementLiasse());
		
		try {
			WizardAnalyseEconomiqueDirect w = new WizardAnalyseEconomiqueDirect();
			new WizardDialogAnalyseEconomiqueDirect(window.getShell(),w).open();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
