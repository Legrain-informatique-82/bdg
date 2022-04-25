package fr.legrain.liasseFiscale.actions;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.preferences.PreferenceConstants;
import fr.legrain.lib.compress.UtilZip;

public class RestaurationLiasseAction implements IWorkbenchWindowActionDelegate {
	
	static Logger logger = Logger.getLogger(RestaurationLiasseAction.class.getName());
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
			
//			String repDestination = "C:/LGRDOSS/BureauDeGestion/";
//			String dossierAEffacer = "C:/LGRDOSS/BureauDeGestion/dossier";
			
//			String repDestination = Platform.getInstanceLocation().getURL().getFile();
//			String dossierAEffacer = Platform.getInstanceLocation().getURL().getFile()+"/dossier";
			String repDestination = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REP_DEST_RESTAURATION) ;
			String dossierAEffacer = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DOSSIER_A_EFFACER_RESTAURATION) ;

			File dest = new File(repDestination);
			
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.OPEN);
			dd.setText("Choix de la sauvegarde à restaurer");
			dd.setFilterPath(LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT_SAUVEGARDE));
			dd.setFilterExtensions(new String[] {"*.zip"}); // !! chaine des extension dépend de l'OS
			dd.setFilterNames(new String[] {"dossier bureau de gestion"});
			String emplacementSauvegarde = dd.open();
			
			if(emplacementSauvegarde!=null) {
				if(MessageDialog.openConfirm(window.getShell(),"Restauration","Êtes-vous certains de vouloir restaurer :\n"+emplacementSauvegarde)) {
					//Sauvegarde avant restauration
					logger.debug("Liasse : sauvegarde avant restauration désactivée");
//					String svg = "C:/svgAvantRestauration.zip";
//					File svgAvRest = new File(svg); 
//					if(!svgAvRest.equals(new File(emplacementSauvegarde))) {
//						//on restaure svgAvRest donc pas de sauvegarde avant restauration
//						svgAvRest.delete();
//						s.zip(dossierAEffacer,svg);
//					}
					//Restauration
					String value1=emplacementSauvegarde.replaceAll("/", "").replaceAll("\\\\", "");
					String value2=dossierAEffacer.replaceAll("/", "").replaceAll("\\\\", "");
					if(value1.toLowerCase().contains(value2.toLowerCase())){
						MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"ATTENTION", "La sauvegarde est contenu dans le dossier à supprimer. " +
										"Vous devez sauvegarder dans un autre dossier avant de restaurer");
						throw new Exception();
					}
					deleteDir(new File(dossierAEffacer));
					s.unzip(repDestination,emplacementSauvegarde);
					MessageDialog.openInformation(window.getShell(),"Restauration","Restauration terminée.");
				}
			}
		} catch(Exception e) {
			MessageDialog.openInformation(window.getShell(),"Restauration","Erreur durant la restauration.");
			logger.error("",e);
		}
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

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
