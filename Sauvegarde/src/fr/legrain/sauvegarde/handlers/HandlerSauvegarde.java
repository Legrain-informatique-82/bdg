package fr.legrain.sauvegarde.handlers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.compress.UtilZip;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.sauvegarde.SauvegardePlugin;
import fr.legrain.sauvegarde.preferences.PreferenceConstants;


public class HandlerSauvegarde extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerSauvegarde.class.getName());


	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
//		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
		fonctionSauvegarde.setShell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		fonctionSauvegarde.sauvegarde();		
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
}
