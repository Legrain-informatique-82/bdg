package fr.legrain.gestionCommerciale.extensionPoints;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;

import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.services.Branding;
import fr.legrain.services.IServiceBranding;

public class BrandingExtension implements IServiceBranding{
	
	static Logger logger = Logger.getLogger(BrandingExtension.class.getName());
	
	private Branding branding = null;
	
	public BrandingExtension() {
		//createContributors();
		getBranding();
	}
	
	public Branding getBranding() {
		if(branding==null) {
			branding = Branding.getInstance();
			createContributors();
		}
		return branding;
	}
	
	public int createContributors() {
		int nbContributor = 0;
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("GestionCommerciale.branding");

		if(extensionPoint != null){
			
			ImageDescriptor icon = null;
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						//String editorClass = confElements[jj].getAttribute("classe");//$NON-NLS-1$
						branding.setTypeVersion(confElements[jj].getAttribute("type_version"));//$NON-NLS-1$
						branding.setDescriptionVersion(confElements[jj].getAttribute("description_version"));//$NON-NLS-1$
		
						String contributorName = confElements[jj].getContributor().getName();
						
//						if (editorClass == null)
//							continue;
//
//						Object o = confElements[jj].createExecutableExtension("classe");
//						
//						if(o instanceof IExecLancement)
//							((IExecLancement)o).execute();
						
					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}
			
			nbContributor = extensions.length;
		}
		
		Platform.getBundle(GestionCommercialePlugin.PLUGIN_ID).getBundleContext().registerService(IServiceBranding.class.getName(), this, null);
		
		return nbContributor;
	}

	
}
