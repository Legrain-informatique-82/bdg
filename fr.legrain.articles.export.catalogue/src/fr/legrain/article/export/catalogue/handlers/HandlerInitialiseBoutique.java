package fr.legrain.article.export.catalogue.handlers;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.GestionModulePHP;
import fr.legrain.article.export.catalogue.ImportVersion;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;

public class HandlerInitialiseBoutique extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerInitialiseBoutique.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			
			BoutiqueInitializer bi = new BoutiqueInitializer();
			bi.initialize();
				
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}
