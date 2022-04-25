package gestComBd.actions;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.global.VERSION;
import fr.legrain.services.Branding;
import fr.legrain.services.IServiceBranding;
import fr.legrain.services.IServicePreferenceDossier;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class VersionAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public VersionAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		String texte = "";
		
		BundleContext context = Platform.getBundle(gestComBdPlugin.PLUGIN_ID).getBundleContext();
		IServiceBranding service = null;
		ServiceReference<?> serviceReference = context.getServiceReference(IServiceBranding.class.getName());
		if(serviceReference!=null) {
			service = (IServiceBranding) context.getService(serviceReference); 
		}
		
		String typeVersion = null;
		if(service!=null && service.getBranding()!=null && service.getBranding().getTypeVersion()!=null) {
			typeVersion = service.getBranding().getTypeVersion();
		}
		
		String descriptionVersion = null;
		if(service!=null && service.getBranding()!=null && service.getBranding().getDescriptionVersion()!=null) {
			descriptionVersion = service.getBranding().getDescriptionVersion();
		}
		
		if(typeVersion!=null && !typeVersion.equals(""))
			texte = "Version "+typeVersion;
		if(descriptionVersion!=null && !descriptionVersion.equals(""))
			texte += "\n"+descriptionVersion;
		
		if(typeVersion!=null && !typeVersion.equals(""))
			texte += "\n======================================\n";
		
		VERSION v = VERSION.getVERSION("1",null);
		texte += "\nVersion actuelle de la base de données : "+v.getNUM_VERSION()
				+"\nVersion précédente de la base de données : "+v.getOLD_VERSION()
				+"\nDate de la Version : "+v.getDATE_VERSION();
				
		MessageDialog.openInformation(
			window.getShell(),
			"Version",
			texte);
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