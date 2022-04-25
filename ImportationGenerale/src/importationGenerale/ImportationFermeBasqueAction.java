package importationGenerale;


import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


public class ImportationFermeBasqueAction implements IWorkbenchWindowActionDelegate {
	static Logger logger = Logger.getLogger(ImportationFermeBasqueAction.class.getName());

	public void dispose() {
		// TODO Raccord de méthode auto-généré

	}

	public void init(IWorkbenchWindow window) {
		// TODO Raccord de méthode auto-généré

	}

	public void run(IAction action) {
		try {
			// TODO Chemin relatif vers la bdd
			CreationTableImportation.creeTableSurFichier("TA_CEGERE",null);

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Erreur : run", e);
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Raccord de méthode auto-généré

	}

}
