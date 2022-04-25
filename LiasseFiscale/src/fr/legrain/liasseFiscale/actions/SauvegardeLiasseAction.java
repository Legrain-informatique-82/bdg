package fr.legrain.liasseFiscale.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.preferences.PreferenceConstants;
import fr.legrain.lib.compress.UtilZip;
import fr.legrain.lib.data.LibChaine;


public class SauvegardeLiasseAction implements IWorkbenchWindowActionDelegate {

	static Logger logger = Logger.getLogger(SauvegardeLiasseAction.class.getName());
	private IWorkbenchWindow window;
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		try {
		UtilZip s = new UtilZip();
		
//		String fichierSauvegarde = "bureaugestion";
		String fichierSauvegarde = null;

//		String aSauvegarder = "C:/LGRDOSS/BureauDeGestion/dossier";
//		String aSauvegarder = Platform.getInstanceLocation().getURL().getFile()+"/dossier";
		String aSauvegarder = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER) ;
		
		if(fichierSauvegarde==null)
			fichierSauvegarde = "liasse";
		
		DateFormat df = new SimpleDateFormat ("ddMMyyyyHHmmss");
		Date d = new Date();
		fichierSauvegarde+=df.format(d)+".zip";
		
		//FileDialog dd = new FileDialog(Workbench.getInstance().getActiveWorkbenchWindow().getShell(),SWT.SAVE);
		DirectoryDialog dd = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.SAVE);
		dd.setText("Choix d'un emplacement pour la sauvegarde : "+fichierSauvegarde);
		dd.setFilterPath(LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT_SAUVEGARDE));
		//dd.setFilterExtensions(new String[] {"zip"});
		//dd.setFilterNames(new String[] {"dossier bureau de gestion"});
		String emplacementSauvegarde = dd.open();
		//System.out.println(choix);
		
		
		if(emplacementSauvegarde!=null) {
			if(!emplacementSauvegarde.equalsIgnoreCase(LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT_SAUVEGARDE))){
				LiasseFiscalePlugin.getDefault().getPreferenceStore().setDefault(PreferenceConstants.P_EMPLACEMENT_SAUVEGARDE,emplacementSauvegarde);
				//SauvegardePlugin.getDefault().getPreferenceStore().setValue(PreferenceConstants.P_EMPLACEMENT,emplacementSauvegarde);
				//SauvegardePlugin.getDefault().getPreferenceStore().setToDefault(PreferenceConstants.P_EMPLACEMENT);					
			}
			s.zip(aSauvegarder,emplacementSauvegarde+"/"+fichierSauvegarde);
			MessageDialog.openInformation(window.getShell(),"Sauvegarde","Sauvegarde terminée.");
		}
		//s.unzip("C:/test/","c:/test.zip");
	} catch(Exception e) {
		MessageDialog.openInformation(window.getShell(),"Sauvegarde","Erreur durant la sauvegarde.");
		logger.error("",e);
	}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
