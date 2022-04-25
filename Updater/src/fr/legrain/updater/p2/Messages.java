package fr.legrain.updater.p2;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "fr.legrain.updater.p2.messages"; //$NON-NLS-1$
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	public static String InstallNewSoftwareHandler_LoadRepositoryJobLabel;
	public static String UpdateHandler_NoSitesMessage;
	public static String UpdateHandler_NoSitesTitle;
}
