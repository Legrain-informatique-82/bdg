package fr.legrain.gestionCommerciale.extensionPoints;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class VerrouillageDemoExtension {
	
	static Logger logger = Logger.getLogger(VerrouillageDemoExtension.class.getName());
	
	public int createContributors() {
		int nbContributor = 0;
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("GestionCommerciale.verificationDemo");
//		String type = "";
		if(extensionPoint != null){
			IExtension[] extensions = extensionPoint.getExtensions();
//			if(extensions.length>0) {
//				for (int i = 0; i < extensions.length; i++) {
//					IConfigurationElement configurationElement[] = extensions[i].getConfigurationElements();
//					for (int j = 0; j < configurationElement.length; j++) {
//						type = configurationElement[j].getAttribute("type");
//					}
//				}
//				openUrl(type);
//			}
			nbContributor = extensions.length;
		}
		return nbContributor;
	}
}
