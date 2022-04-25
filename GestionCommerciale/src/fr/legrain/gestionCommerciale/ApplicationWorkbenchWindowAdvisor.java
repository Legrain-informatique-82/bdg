package fr.legrain.gestionCommerciale;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.intro.IIntroManager;

import fr.legrain.gestionCommerciale.extensionPoints.BrandingExtension;
import fr.legrain.gestionCommerciale.extensionPoints.ExecLancementExtension;
import fr.legrain.gestionCommerciale.extensionPoints.ExtensionPoinsClass;
import fr.legrain.gestionCommerciale.preferences.PreferenceConstants;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.services.Branding;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	static Logger logger = Logger.getLogger(ApplicationWorkbenchWindowAdvisor.class.getName());
	static final public String TITLE = "Bureau de gestion";
	
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setTitle(TITLE);
        configurer.setShowPerspectiveBar(true);
        configurer.setShowProgressIndicator(true);
        configurer.setShowStatusLine(true);
        configurer.setShowFastViewBars(true);
    }
    
    public void postWindowCreate() {
    	//fermeture du workbench sur ESC
    	getWindowConfigurer().getWindow().getShell().addTraverseListener(new TraverseListener() {
    		public void keyTraversed(TraverseEvent e) {
    			if (e.detail == SWT.TRAVERSE_ESCAPE) {
    				e.doit=false;
//    				//if(MessageDialog.openConfirm(getWindowConfigurer().getWindow().getShell(),"","Quitter ?"))
//    					getWindowConfigurer().getWorkbenchConfigurer().getWorkbench().close();
    			}
    		}
    	});
    }
    
//    @Override
//    public void openIntro() {
//    	// TODO Auto-generated method stub
//    	super.openIntro();
//    	IIntroManager introManager = getWindowConfigurer().getWorkbenchConfigurer().getWorkbench().getIntroManager();
//    	//introManager.setIntroStandby(introManager.getIntro(), true);
//    	introManager.showIntro(getWindowConfigurer().getWindow(), false);
//    }
    
    public void postWindowOpen() {
    	try {
    		if(getWindowConfigurer().getWindow().getActivePage().getEditorReferences().length>0) {
    			if(getWindowConfigurer().getWindow().getActivePage().getEditorReferences()[0].getEditor(false)!=null)
    				getWindowConfigurer().getWindow().getActivePage().getEditorReferences()[0].getEditor(false).setFocus();
    		}
    		
  			
			/* pour veresion démo zhaolin 19/07/2010 
    		 * 
    		 * pour afficher website quand lancer programme 
    		 */
    		ExtensionPoinsClass extensionPoinsClass = new ExtensionPoinsClass();
    		extensionPoinsClass.createContributors();
    	} catch (Exception e) {
    		logger.debug("",e);
    	}
    }
    
    @Override
	public boolean preWindowShellClose() {
    	if(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(true)){
    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllPerspectives(false, false);
    		try {
//  			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(true);
    			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(true);
    			PlatformUI.getWorkbench().showPerspective(Perspective.ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
//  			PlatformUI.getWorkbench().showPerspective(PlatformUI.getWorkbench().getPerspectiveRegistry().getDefaultPerspective(), PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    		} catch (WorkbenchException e) {
    			logger.error("",e);
    		}
    		return true;
    	} else {
    		return false;
    	}
	}
    
    public static void setTitre(final String titre) {
    	final Branding brand = new BrandingExtension().getBranding();
    	Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
		    	if(LgrMess.isDEVELOPPEMENT()) {				
		    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setText(titre+" - Attention !!! Version développement -");
		    	} else {
		    		String titreVersion = "";
		    		if(brand.getTypeVersion()!=null) {
		    			titreVersion+=" *** "+brand.getTypeVersion();
					}
		    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setText(titre+titreVersion);
		    	}
			}
		});
	}
    
    public static String getTitre() {
    	//TODO a ameloirer - changer l'utilisation de Workbench
    	final String[] result = new String[1];
    	Display.getCurrent().syncExec(new Runnable() {
			public void run() {
				result[0] = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getText();
			}
		});
    	return result[0];
	}

	@Override
	public void openIntro() {
		// TODO Auto-generated method stub
		super.openIntro();
		final IIntroManager mgr = PlatformUI.getWorkbench().getIntroManager();
		
		if(GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.AFFICHAGE_INTRO)) {
			//affichage de l'ecran de bienvenue => choix de l'utilisateur
			mgr.showIntro(getWindowConfigurer().getWindow(), false);
		} else {
			//affichage de l'ecran de bienvenue => suite à une mise à jour
			String v = GestionCommercialePlugin.getDefault().getBundle().getVersion().getMajor()+"."+GestionCommercialePlugin.getDefault().getBundle().getVersion().getMinor()+"."+GestionCommercialePlugin.getDefault().getBundle().getVersion().getMicro();
			//final String WELCOME_VERSION = GestionCommercialePlugin.getDefault().getBundle().getVersion().toString();
			final String WELCOME_VERSION = v;

			final IPreferenceStore store = GestionCommercialePlugin.getDefault().getPreferenceStore();
			final String lastWelcomeVersion = store.getString(PreferenceConstants.PROP_LAST_WELCOME_VERSION);

			if (null == lastWelcomeVersion || !lastWelcomeVersion.equals(WELCOME_VERSION)) {
				logger.info("Preference lastWelcomeVersion : "+lastWelcomeVersion+" ** Plugin version : "+WELCOME_VERSION);
				store.setValue(PreferenceConstants.PROP_LAST_WELCOME_VERSION, WELCOME_VERSION);
				mgr.showIntro(getWindowConfigurer().getWindow(), false);
			} 
		}
		
	}
}
