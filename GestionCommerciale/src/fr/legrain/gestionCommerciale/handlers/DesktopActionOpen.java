package fr.legrain.gestionCommerciale.handlers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class DesktopActionOpen {
	
	static Logger logger = Logger.getLogger(DesktopActionOpen.class.getName());

	static public void open(String url) {
		final String finalURL = url;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				File file = new File(finalURL);
				//TODO ajouter un test sur le type liens
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.OPEN)) {
						try {
							if(file.exists())
								desktop.open(file);
							else
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur", 
										"Le chemin est invalide ou inaccessible pour l'instant.\n"+finalURL);
						} catch (IOException e) {
							logger.error("",e);
						}
					}
				}
			}	
		});
	}
}
