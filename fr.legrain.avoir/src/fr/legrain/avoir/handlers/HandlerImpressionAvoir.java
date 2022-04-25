package fr.legrain.avoir.handlers;
import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.avoir.divers.ParamImpressionAvoir;
import fr.legrain.avoir.ecran.PaImpressionAvoir;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.lib.gui.ParamAfficheSWT;


public class HandlerImpressionAvoir extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerImpressionAvoir.class.getName());

	private IWorkbenchWindow window;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		try {			
//			Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);		
//			PaImpressionAvoir paImpressionFacture = new PaImpressionAvoir(s,SWT.NULL);
//			SWTPaImpressionFactureController paImpressionFactureController = new SWTPaImpressionFactureController(paImpressionFacture);
//			ParamImpressionAvoir paramImpressionFacture= new ParamImpressionAvoir();
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
		return null;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
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
