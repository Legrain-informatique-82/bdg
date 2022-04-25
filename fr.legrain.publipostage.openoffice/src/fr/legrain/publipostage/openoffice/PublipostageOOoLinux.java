package fr.legrain.publipostage.openoffice;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;

import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.uno.UnoRuntime;

import fr.legrain.publipostage.divers.ParamPublipostage;

public class PublipostageOOoLinux extends AbstractPublipostageOOo {

	static Logger logger = Logger.getLogger(PublipostageOOoLinux.class.getName());
	
	public PublipostageOOoLinux(List<ParamPublipostage> listeParamPublipostages) {
		super(listeParamPublipostages);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void demarrerServeur() {
		try {
			String cmd = cheminOpenOffice+PARAMS_START_SERVER_OPENOFFICE_PARAM1.
						 replace("?", portOpenOffice);
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);
			Thread.sleep(10000);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			logger.error("demarrerServeur() has problem for linux ! ", e);
		}

	}

	@Override
	public String convertCheminFileVersUri(String cheminFichier) {
	
			String pathFile = new File(cheminFichier).getPath();
			URI uri = null;
			try {
				uri = new URI("file","",pathFile,null,null);
			} catch (Exception e) {
				logger.error("convertCheminFileVersUri(ParamPublipostage param) has problem ! ", e);
			}
			return uri.toString();
			
		}

	@Override
	public String convertRetournLigne(String texteModele) {
		// TODO Auto-generated method stub
		if(texteModele.endsWith("\n"))
			texteModele = texteModele.substring(0, texteModele.lastIndexOf("\n")); 
		if(texteModele.startsWith("\n"))
			texteModele = texteModele.replaceFirst("\n","");
		
		return texteModele;
	}

	@Override
	public void OuvreDocument(String portOpenOffice, String fichier) {
		try {
			xCC = Bootstrap.createInitialComponentContext(null);
			xUUR = UnoUrlResolver.create(xCC);
			Object initialObject = xUUR.resolve(PARAM_CONNECTION_OPEN_OFFICE.replace("?",portOpenOffice));
			xMCF = (com.sun.star.lang.XMultiComponentFactory)UnoRuntime.queryInterface(com.sun.star.lang.XMultiComponentFactory.class,initialObject);
			com.sun.star.beans.XPropertySet xProperySet = (com.sun.star.beans.XPropertySet) UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, xMCF);
			Object oDefaultContext = xProperySet.getPropertyValue("DefaultContext");
			xCC = (com.sun.star.uno.XComponentContext)UnoRuntime.queryInterface(com.sun.star.uno.XComponentContext.class, oDefaultContext);
			// now create the desktop service
			// NOTE: use the office component context here!
			Object desktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xCC);
			xCL = (com.sun.star.frame.XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class,desktop);
			
			loadProps = new com.sun.star.beans.PropertyValue[1];
			loadProps[0] = new com.sun.star.beans.PropertyValue();

			loadProps[0].Value = new Boolean(true); 
			
			xC = xCL.loadComponentFromURL(convertCheminFileVersUri(fichier), "_blank", 0, loadProps);

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("OuvreDocument()", e);
		}
	}
}
