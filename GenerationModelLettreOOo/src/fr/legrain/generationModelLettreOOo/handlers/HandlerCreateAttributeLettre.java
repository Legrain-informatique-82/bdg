package fr.legrain.generationModelLettreOOo.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationModelLettreOOo.attribute.CompositeCreateAttributeLettre;
import fr.legrain.generationModelLettreOOo.attribute.ControllerCreateModelLettre;
import fr.legrain.generationModelLettreOOo.divers.ConstModelLettre;
import fr.legrain.generationModelLettreOOo.divers.FonctionOpenOffice;
import fr.legrain.generationModelLettreOOo.preferences.PreferenceConstants;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import generationmodellettreooo.Activator;

public class HandlerCreateAttributeLettre extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerCreateAttributeLettre.class.getName());
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		String pathOffice = preferenceStore.getString(PreferenceConstants.PATH_OPEN_OFFICE);
		String portOffice = preferenceStore.getString(PreferenceConstants.PORT_SERVER_OPEN_OFFICE);
		// TODO Auto-generated method stub
		
		if(!pathOffice.equals("")& !portOffice.equals("")) {
			Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
			dialogShell.setSize(800, 600);
			dialogShell.setText(ConstModelLettre.TITLE_SHELL);
			dialogShell.setLayout(new FillLayout());
			
			FonctionOpenOffice fonctionOpenOffice = new FonctionOpenOffice();
			ControllerCreateModelLettre controllerCreateModelLettre = new ControllerCreateModelLettre(dialogShell
																		,pathOffice,portOffice,fonctionOpenOffice);
			controllerCreateModelLettre.init();
			dialogShell.open();
		}else{
			logger.info("Preferences OpenOffice incompletes");
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Erreur", "Les préférences concernant OpenOffice ne sont pas toutes renseignées.\n\n" +
					"Veuillez aller dans le menu Outils/Préférences pour paramétrer OpenOffice avant " +
					"d'utiliser le publipostage.");
		}

		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isHandled() {
		return true;
	}
	
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
