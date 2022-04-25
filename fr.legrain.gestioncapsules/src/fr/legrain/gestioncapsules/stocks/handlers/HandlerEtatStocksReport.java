package fr.legrain.gestioncapsules.stocks.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestioncapsules.stocks.controllers.PaEtatStocksReportController;
import fr.legrain.gestioncapsules.stocks.divers.ParamImpressionStocks;
import fr.legrain.gestioncapsules.stocks.ecrans.PaEtatStocks;
import fr.legrain.lib.gui.ParamAfficheSWT;

public class HandlerEtatStocksReport extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerEtatStocksReport.class.getName());
	private IWorkbenchWindow window;
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {			
			Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);		
			PaEtatStocks paEtatStocks = new PaEtatStocks(s,SWT.NULL);
			PaEtatStocksReportController paEtatStocksController = new PaEtatStocksReportController(paEtatStocks);
			ParamImpressionStocks paramImpressionStocks= new ParamImpressionStocks();
			paramImpressionStocks.setFocusSWT(paEtatStocks.getTfDATEDEB());
			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			paramAfficheSWT.setHauteur(150);
			paramAfficheSWT.setLargeur(400);
			paramAfficheSWT.setTitre("Etat des report de stocks à une date donnée.");
			LgrShellUtil.afficheSWT(paramImpressionStocks, paramAfficheSWT, paEtatStocks, 
					paEtatStocksController, s);
			if(paramImpressionStocks.getFocus()!=null)
				paramImpressionStocks.getFocusDefaut().requestFocus();
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}
		return null;
	}

	
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		try {								
//			Shell s = new Shell(window.getShell(),LgrShellUtil.styleLgr);	
//				PaEtatStocks paEtatStocks = new PaEtatStocks(s,SWT.NULL);
//				PaEtatStocksController paEtatStocksController = new PaEtatStocksController(paEtatStocks);
//				ParamAfficheSWT paramEtatStocks= new ParamAfficheSWT();
//
//				ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
//				paramAfficheSWT.setHauteur(150);
//				paramAfficheSWT.setLargeur(400);
//				paramAfficheSWT.setTitre("Etat des stocks à une date donnée.");
//				LgrShellUtil.afficheSWT(paramEtatStocks, paramAfficheSWT, paEtatStocks, paEtatStocksController, s);
//				if(paramEtatStocks.getFocus()!=null)
//					paramEtatStocks.getFocusDefaut().requestFocus();
//			
//		} catch (Exception e1) {
//			logger.error("Erreur : actionPerformed", e1);
//		}
//		return event;
//	}

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
