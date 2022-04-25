//package fr.legrain.updater.p2;
//
//import org.eclipse.equinox.internal.provisional.p2.ui.IProvHelpContextIds;
//import org.eclipse.equinox.internal.provisional.p2.ui.QueryableMetadataRepositoryManager;
//import org.eclipse.equinox.internal.provisional.p2.ui.dialogs.InstallWizard;
//import org.eclipse.equinox.internal.provisional.p2.ui.dialogs.ProvisioningWizardDialog;
//import org.eclipse.equinox.internal.provisional.p2.ui.policy.Policy;
//import org.eclipse.jface.wizard.WizardDialog;
//import org.eclipse.ui.PlatformUI;
//
///**
// * InstallNewSoftwareHandler invokes the install wizard
// * 
// * @since 3.5
// */
//public class InstallNewSoftwareHandler extends PreloadingRepositoryHandler {
//
//	/**
//	 * The constructor.
//	 */
//	public InstallNewSoftwareHandler() {
//		super();
//	}
//
//	public void doExecute(String profileId, QueryableMetadataRepositoryManager manager) {
//		InstallWizard wizard = new InstallWizard(Policy.getDefault(), profileId, null, null, manager);
//		WizardDialog dialog = new ProvisioningWizardDialog(getShell(), wizard);
//		dialog.create();
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IProvHelpContextIds.INSTALL_WIZARD);
//
//		dialog.open();
//	}
//
//	protected boolean waitForPreload() {
//		// If there is no way for the user to manipulate repositories,
//		// then we may as well wait for existing repos to load so that
//		// content is available.  If the user can manipulate the
//		// repositories, then we don't wait, because we don't know which
//		// ones they want to work with.
//		return Policy.getDefault().getRepositoryManipulator() == null;
//	}
//}
