package fr.legrain.sauvegarde.actions;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * @deprecated utiliser HandlerRestauration
 */
public class RestaurationAction implements IWorkbenchWindowActionDelegate {

	static Logger logger = Logger.getLogger(RestaurationAction.class.getName());
	
	private IWorkbenchWindow window;
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void run(IAction action) {
//		//original();
//		nouveau();
		logger.error("utiliser HandlerRestauration.java et les commande plutot que l'action");
	}
	
	private void original() {
	}
	
	private void nouveau() {
		
	}
	
    //Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
