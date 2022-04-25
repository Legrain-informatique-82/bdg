package fr.legrain.gestionCommerciale.handlers;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.branding.IProductConstants;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.splash.BasicSplashHandler;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.gestionCommerciale.extensionPoints.BrandingExtension;
import fr.legrain.services.Branding;
import fr.legrain.services.IServiceBranding;



public class SplashHandler extends BasicSplashHandler {
	public void init(Shell splash) {
		super.init(splash);
		String progressRectString = null;
		String messageRectString = null;
		String foregroundColorString = null;
		IProduct product = Platform.getProduct();
		if (product != null) {
			progressRectString = product
					.getProperty(IProductConstants.STARTUP_PROGRESS_RECT);
			messageRectString = product
					.getProperty(IProductConstants.STARTUP_MESSAGE_RECT);
			foregroundColorString = product
					.getProperty(IProductConstants.STARTUP_FOREGROUND_COLOR);
		}
		Rectangle progressRect = StringConverter.asRectangle(
				progressRectString, new Rectangle(10, 10, 300, 15));
		setProgressRect(progressRect);

		Rectangle messageRect = StringConverter.asRectangle(messageRectString,
				new Rectangle(10, 35, 300, 15));
		setMessageRect(messageRect);

		int foregroundColorInteger;
		try {
			foregroundColorInteger = Integer
					.parseInt(foregroundColorString, 16);
		} catch (Exception ex) {
			foregroundColorInteger = 0xD2D7FF; // off white
		}

		setForeground(new RGB((foregroundColorInteger & 0xFF0000) >> 16,
				(foregroundColorInteger & 0xFF00) >> 8,
				foregroundColorInteger & 0xFF));
		
		// the following code will be removed for release time
		
//		if (PrefUtil.getInternalPreferenceStore().getBoolean("SHOW_BUILDID_ON_STARTUP")) { //$NON-NLS-1$
		boolean afficheInfosVersion = true;
		if (afficheInfosVersion) { //$NON-NLS-1$
			
			new BrandingExtension().getBranding(); //création et enregistrement du service
		//final String buildId = System.getProperty("eclipse.buildId", "Unknown Build"); //$NON-NLS-1$ //$NON-NLS-2$
			BundleContext context = Platform.getBundle(GestionCommercialePlugin.PLUGIN_ID).getBundleContext();
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
			
			final String typeVersionFinal = typeVersion;
			final String descriptionVersionFinal = descriptionVersion;
			
			if(typeVersionFinal!=null) {
				// find the specified location.  Not currently API
				// hardcoded to be sensible with our current splash Graphic
				String buildIdLocString = product.getProperty("buildIdLocation"); //$NON-NLS-1$
				//final Point buildIdPoint = StringConverter.asPoint(buildIdLocString,new Point(322, 190));
				final Point buildIdPoint = StringConverter.asPoint(buildIdLocString,new Point(100, 190));
				getContent().addPaintListener(new PaintListener() {

					public void paintControl(PaintEvent e) {
						e.gc.setForeground(new Color(e.gc.getDevice(), 255, 0, 0));
						e.gc.drawText(typeVersionFinal, buildIdPoint.x, buildIdPoint.y,true);
						e.gc.drawText(descriptionVersionFinal, buildIdPoint.x, buildIdPoint.y+20,true);
					}
				});
			} else {
				getContent();
			}
			
			//Exemple de chargement d'un image supplémentaire
//			//here you could check some condition on which decoration to show
//			String BETA_PNG = "splash.bmp";
//			int BORDER = 10;
//			Image image = null;
//			ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(GestionCommercialePlugin.PLUGIN_ID, BETA_PNG);
//			if (descriptor != null)
//				image = descriptor.createImage();
//			if (image !=null) {
//				final int xposition = splash.getSize().x - image.getImageData().width - BORDER;
//				final int yposition = BORDER;
//				final Image finalImage = image;
//				getContent().addPaintListener (new PaintListener () {
//					public void paintControl (PaintEvent e) {
//						e.gc.drawImage (finalImage, xposition, yposition);
//					}
//				});
//			}
			
		}
		else {
			getContent(); // ensure creation of the progress
		}
	}


}
