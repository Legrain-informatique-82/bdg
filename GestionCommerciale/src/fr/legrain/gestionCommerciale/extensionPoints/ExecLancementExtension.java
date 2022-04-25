package fr.legrain.gestionCommerciale.extensionPoints;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;

import fr.legrain.gestionCommerciale.extension.interfaces.IExecLancement;

public class ExecLancementExtension {
	
	static Logger logger = Logger.getLogger(ExecLancementExtension.class.getName());
	
	public int createContributors() {
		int nbContributor = 0;
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("GestionCommerciale.execLancement");

		if(extensionPoint != null){
			
			ImageDescriptor icon = null;
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String editorClass = confElements[jj].getAttribute("classe");//$NON-NLS-1$
		
						String contributorName = confElements[jj].getContributor().getName();
						
						if (editorClass == null)
							continue;

						Object o = confElements[jj].createExecutableExtension("classe");
						
						if(o instanceof IExecLancement)
							((IExecLancement)o).execute();
						
					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}
			
			nbContributor = extensions.length;
		}
		return nbContributor;
	}
}
