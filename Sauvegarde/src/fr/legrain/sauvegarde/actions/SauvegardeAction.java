package fr.legrain.sauvegarde.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.compress.UtilZip;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
import fr.legrain.sauvegarde.SauvegardePlugin;
import fr.legrain.sauvegarde.preferences.PreferenceConstants;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 * @deprecated utiliser HandlerSauvegarde
 */
public class SauvegardeAction implements IWorkbenchWindowActionDelegate {

	static Logger logger = Logger.getLogger(SauvegardeAction.class.getName());

	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SauvegardeAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {			
		//		sauvegarde();		
		logger.error("utiliser HandlerSauvegarde.java et les commande plutot que l'action");
	}


	public String sauvegarde(){
		String retour=null;
//		try{
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//			.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//
//			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
//			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//			UtilZip s = new UtilZip();
//
//			//		String fichierSauvegarde = "bureaugestion";
//			String fichierSauvegarde = null;
//
//			UtilWorkspace uw = new UtilWorkspace();
//			//		String aSauvegarder = "C:/LGRDOSS/BureauDeGestion/dossier";
//			//		String aSauvegarder = Platform.getInstanceLocation().getURL().getFile()+"/dossier";
//			//String aSauvegarder = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER) ;
//			String aSauvegarder = uw.openProjectLocationPath();
//
//			if(taInfoEntreprise!=null && taInfoEntreprise.getNomInfoEntreprise()!=null) {
//				fichierSauvegarde = "bdg_";
//				if(taInfoEntreprise.getNomInfoEntreprise().length()>10)
//					fichierSauvegarde += taInfoEntreprise.getNomInfoEntreprise().substring(0,10)+"_";
//				else
//					fichierSauvegarde += taInfoEntreprise.getNomInfoEntreprise();
//				fichierSauvegarde = LibChaine.clearString(fichierSauvegarde,LibChaine.C_UNAUTHORIZED_CHAR_FILE_IN_NAME,'_')+"_";
//
//			}
//
//			if(fichierSauvegarde==null)
//				fichierSauvegarde = "bureaugestion";
//
//			DateFormat df = new SimpleDateFormat ("ddMMyyyy_HHmmss");
//			Date d = new Date();
//			fichierSauvegarde+=LibDate.getDateLGR(d)+"_"+LibDate.getHeure(d)+".zip";
//
//			//FileDialog dd = new FileDialog(Workbench.getInstance().getActiveWorkbenchWindow().getShell(),SWT.SAVE);
//			DirectoryDialog dd = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.SAVE);
//			dd.setText("Choix d'un emplacement pour la sauvegarde : "+fichierSauvegarde);
//			dd.setFilterPath(SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT));
//			//dd.setFilterExtensions(new String[] {"zip"});
//			//dd.setFilterNames(new String[] {"dossier bureau de gestion"});
//			String emplacementSauvegarde = dd.open();
//			//System.out.println(choix);
//
//
//			if(emplacementSauvegarde!=null) {
//				if(!emplacementSauvegarde.equalsIgnoreCase(SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT))){
//					SauvegardePlugin.getDefault().getPreferenceStore().setDefault(PreferenceConstants.P_EMPLACEMENT,emplacementSauvegarde);
//					//SauvegardePlugin.getDefault().getPreferenceStore().setValue(PreferenceConstants.P_EMPLACEMENT,emplacementSauvegarde);
//					//SauvegardePlugin.getDefault().getPreferenceStore().setToDefault(PreferenceConstants.P_EMPLACEMENT);					
//				}
//				s.zip(aSauvegarder,emplacementSauvegarde+"/"+fichierSauvegarde);
//				//			retour="";
//				retour=emplacementSauvegarde+"/"+fichierSauvegarde;
//				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Sauvegarde","Sauvegarde terminée.");
//			}
//
//		} catch(Exception e) {
//			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Sauvegarde","Erreur durant la sauvegarde.");
//			logger.error("",e);
//		}
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//		.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		return retour; 
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}