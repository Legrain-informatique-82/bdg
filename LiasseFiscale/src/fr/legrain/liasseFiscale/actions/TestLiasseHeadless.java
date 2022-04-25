package fr.legrain.liasseFiscale.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.liasseFiscale.wizards.HeadlessLiasse;

public class TestLiasseHeadless implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
//		HeadlessLiasse h = new HeadlessLiasse("/home/nicolas/Desktop/ExportLiasse_017852006.Txt",true);
		HeadlessLiasse h = new HeadlessLiasse("/home/nicolas/Desktop/ExportLiasse_915602008.Txt",true);
		h.traitementLiasse();

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
