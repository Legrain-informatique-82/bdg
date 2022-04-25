package fr.legrain.gestionCommerciale;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import fr.legrain.dossier.dao.TaVersion;
import fr.legrain.dossier.dao.TaVersionDAO;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestionCommerciale.extensionPoints.ExtensionPoinsClass;
import fr.legrain.gestionCommerciale.extensionPoints.VerrouillageDemoExtension;
import fr.legrain.libMessageLGR.LgrMess;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
	
	static Logger logger = Logger.getLogger(ApplicationWorkbenchAdvisor.class.getName());
	
	private static final int DUREE_DEMO = 45;
	private static final String URL_MESSAGE_FIN_DEMO = "http://legrain.fr/bdg/version-evaluation-erp-bureau-de-gestion.html";

	private static final String PERSPECTIVE_ID = "GestionCommerciale.perspective";
	protected String C_IMAGE_INFORMATION = "/icons/information.png";

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }
    
    public void initialize(IWorkbenchConfigurer configurer) {
    	configurer.setSaveAndRestore(true);
        super.initialize(configurer);
        
        PlatformUI.getPreferenceStore().setValue(
        	    IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP, true);
        PlatformUI.getPreferenceStore().setValue(
        	    IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR, true);
        PlatformUI.getPreferenceStore().setValue(
        	    IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
        PlatformUI.getPreferenceStore().setValue(
        	    IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR, LgrMess.isDEVELOPPEMENT());
        PlatformUI.getPreferenceStore().setValue(
        	    IWorkbenchPreferenceConstants.SHOW_INTRO, false);
        
//        testCSSTheme();
        
        /*
        //IWorkbenchThemeConstants
        PlatformUI.getWorkbench().getThemeManager().setCurrentTheme("GestionCommerciale.theme.lgr");
        */
    }
    
//    public void testCSSTheme() {
//    	/*
//    	 * import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
//		 * import org.eclipse.e4.ui.css.swt.theme.IThemeManager;
//    	 */
//    	Bundle b=FrameworkUtil.getBundle(getClass());
//    	BundleContext context=b.getBundleContext();
//    	ServiceReference serviceRef=context.getServiceReference(IThemeManager.class.getName());
//    	IThemeManager themeManager=(IThemeManager)context.getService(serviceRef);
//    	final IThemeEngine engine= themeManager.getEngineForDisplay(Display.getCurrent());
////    	engine.setTheme("fr.legrain.bdg.ui.css.theme1",true);
////    	engine.setTheme("fr.legrain.bdg.ui.css.theme2",true);
////    	engine.setTheme("fr.legrain.bdg.ui.css.theme3",true);
//    	engine.setTheme("fr.legrain.bdg.ui.css.theme4",true);
//    }

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public void postStartup() {
		

		try{
			super.postStartup();
//			String message = "Initialisation de votre dossier";	
//			IStatusLineManager statusline = getWorkbenchConfigurer().getWindowConfigurer(getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow()).getActionBarConfigurer().getStatusLineManager();
//			statusline.setMessage(GestionCommercialePlugin.getImageDescriptor(C_IMAGE_INFORMATION).createImage(),message);
			UtilWorkspace uw = new UtilWorkspace();
			uw.verifyWorkplace();
			
			VerrouillageDemoExtension demoExtension = new VerrouillageDemoExtension();
			if(demoExtension.createContributors()==0) {
				//pas de plugin empechant de passer en mode démo
				verrouillageDemo();
			} // else => plugin "fr.legrain.bdg.demo" présent donc pas de vérification => version normale
			
		}finally{
			//statusline.setMessage("");
		}
	}
	
	private void verrouillageDemo() {
		
		boolean demo = true;
		TaVersionDAO taVersionDAO = new TaVersionDAO();
		TaVersion v = taVersionDAO.findInstance();
		Calendar c = Calendar.getInstance();
		c.setTime(v.getQuandCreeVersion());
		c.add(Calendar.DAY_OF_MONTH, DUREE_DEMO);
		Calendar maintenant = Calendar.getInstance();
		
		logger.debug("===========================================================");
		logger.debug("Vérification pour la version Démo");
		logger.debug("Date debut : "+v.getQuandCreeVersion());
		logger.debug("Date max : "+c.getTime());
		logger.debug("Date aujourd'hui : "+maintenant.getTime());
		
		if(maintenant.after(c)) { 
			//durée dépassée
			demo = true;
			logger.debug("La version démo a expiré");
		} else {
			demo = false;
			logger.debug("La version démo est encore valide");
		}
		logger.debug("===========================================================");
		
		if(demo) {
			getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getShell().getMenuBar().setEnabled(false);
			getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getShell().getMenuBar().setVisible(false);

			//getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getActivePage().

			MenuManager mbManager = ((ApplicationWindow)getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getActivePage().getWorkbenchWindow()).getMenuBarManager();
//			for (int i=0; i<mbManager.getItems().length; i++){
//				IContributionItem item = mbManager.getItems()[i];
//				item.setVisible(false);
//			}
			mbManager.removeAll();
			mbManager.update(true);

			CoolBarManager coolBarManager = ((ApplicationWindow)getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getActivePage().getWorkbenchWindow()).getCoolBarManager();
			//		for (int i=0; i<coolBarManager.getItems().length; i++){
			//			IContributionItem item = coolBarManager.getItems()[i];
			//			item.setVisible(false);
			//		}
			coolBarManager.removeAll();
			coolBarManager.update(true);
			coolBarManager.refresh();

			//		ToolBarManager toolBarManager = ((ApplicationWindow)getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getActivePage().getWorkbenchWindow()).getToolBarManager();
			//		for (int i=0; i<toolBarManager.getItems().length; i++){
			//			IContributionItem item = toolBarManager.getItems()[i];
			//			//if (item.getId().equals("org.eclipse.search.menu")){
			//				item.setVisible(false);
			//			//}
			//		}
			
			/*
			 * TODO
			 * On pourrait peut être tester la connexion internet avant pour afficher le message sous une forme différente,
			 * MessageDialog, Editeur particulier, ...
			 * cf : fr.legrain.statistiques.Outils#isInternetReachable() 
			 * ou fr.legrain.gestionCommerciale.extensionPoints.ExtensionPoinsClass#isInternetReachable(String site_url)
			 */
			ExtensionPoinsClass e = new ExtensionPoinsClass();
			e.openUrl(URL_MESSAGE_FIN_DEMO);
		}
	}

	@Override
	public void preStartup() {
		super.preStartup();
//		UtilWorkspace uw = new UtilWorkspace();
//        uw.verifyWorkplace();
	}

//	@Override
//	public IStatus saveState(IMemento memento) {
//		// TODO Auto-generated method stub
//		return super.saveState(memento);
//	}
//
//	@Override
//	public IStatus restoreState(IMemento memento) {
//		// TODO Auto-generated method stub
//		return super.restoreState(memento);
//	}

//	@Override
//	public void openIntro(IWorkbenchWindowConfigurer configurer) {
//		// TODO Auto-generated method stub
//		//super.openIntro(configurer);
//		IStatusLineManager statusline = configurer.getActionBarConfigurer().getStatusLineManager();
//		
//		String message = "Initialisation de votre dossier";	
//		statusline.setMessage(GestionCommercialePlugin.getImageDescriptor(C_IMAGE_INFORMATION).createImage(),message);
//		
//	}
	
	 
}
