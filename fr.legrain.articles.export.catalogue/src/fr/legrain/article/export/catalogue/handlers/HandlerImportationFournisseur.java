package fr.legrain.article.export.catalogue.handlers;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.ExportationArticle;
import fr.legrain.article.export.catalogue.ImportationArticle;
import fr.legrain.article.export.catalogue.ImportationFournisseurs;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.UtilImage;
import fr.legrain.lib.data.LibConversion;

public class HandlerImportationFournisseur extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerImportationFournisseur.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {

			
			ImportationFournisseurs imp = new ImportationFournisseurs();
			String repDefaut = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FICHIER_IMPORT_ARTICLE);
			String nomDefaut = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_IMPORT_ARTICLE);
			
			FileDialog d = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			d.setFilterExtensions(new String[]{"*.TXT","*.txt","*.*"});
			d.setFilterNames(new String[]{"TEXTE","Texte","Tous les fichiers"});
			d.setFilterPath(repDefaut);
			String chemin = d.open();
			
			if(chemin!=null) {
				logger.debug("DEBUT LECTURE IMPORT");
				//imp.imporArticle(chemin);
				//MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Importation des articles", "Importation terminée.");
				imp.imporFournisseurProgress(chemin);
				logger.debug("FIN LECTURE IMPORT");


				
			}
		} catch (Exception e) {
			logger.error("",e);
		}
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
