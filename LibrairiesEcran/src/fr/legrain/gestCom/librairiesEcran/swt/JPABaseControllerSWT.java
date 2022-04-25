package fr.legrain.gestCom.librairiesEcran.swt;


import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IAnnuleToutListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.gui.JPABaseController;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.SWTBaseController;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public abstract class JPABaseControllerSWT extends JPABaseController{
	
	static Logger logger = Logger.getLogger(JPABaseControllerSWT.class.getName());
	
	protected EventListenerList listenerList = new EventListenerList();
	
	//protected List<IContextActivation> listeContext = new ArrayList<IContextActivation>();
	//protected String idContext = "GestionCommerciale.contextGlobal";
	public JPABaseControllerSWT() {
		super();
	}
	
	/**
	 * Méthode appeléeà la fermeture du Shell
	 * @return true ssi on autorise la fermeture du Shell
	 */
	abstract public boolean onClose() throws ExceptLgr;
	
	public void addRetourEcranListener(RetourEcranListener l) {
		listenerList.add(RetourEcranListener.class, l);
	}
	
	protected void fireRetourEcran(RetourEcranEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == RetourEcranListener.class) {
				if (e == null)
					e = new RetourEcranEvent(this,null);
				( (RetourEcranListener) listeners[i + 1]).retourEcran(e);
			}
		}
	}

//	public List<IContextActivation> getListeContext() {
//		return listeContext;
//	}
//
//	public void annulerListeContext(){
//		for (IContextActivation c : getListeContext()) {					
//			//((IContextService) PlatformUI.getWorkbench().getService(IContextService.class)).deactivateContext(c);
//			logger.debug("annulerListeContext = "+idContext+" obj: "+c.hashCode());
//		}
//		//getListeContext().clear();
//	}
//
//	public void activationContext(){
//		if(!idContext.equals("")){
//			//IContextActivation icontextActivation =((IContextService) PlatformUI.getWorkbench().getService(IContextService.class)).activateContext(idContext); 
//			//getListeContext().add(icontextActivation);
//			//logger.debug("activationContext = "+idContext+" obj= "+icontextActivation.hashCode()+" controller = "+this.hashCode());
//			logger.debug("activationContext = ");
//		}
//	}
//
//	public String getIdContext() {
//		return idContext;
//	}
//
//	public void setIdContext(String idContext) {
//		this.idContext = idContext;
//	}
	
	public void addChangementDePageListener(IChangementDePageListener l) {
		listenerList.add(IChangementDePageListener.class, l);
	}
	
	public void removeChangementDePageListener(IChangementDePageListener l) {
		listenerList.remove(IChangementDePageListener.class, l);
	}
	
	protected void fireChangementDePage(ChangementDePageEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChangementDePageListener.class) {
				if (e == null)
					e = new ChangementDePageEvent(this);
				( (IChangementDePageListener) listeners[i + 1]).changementDePage(e);
			}
		}
	}
	
	public void addChangementDeSelectionListener(IChangementDeSelectionListener l) {
		listenerList.add(IChangementDeSelectionListener.class, l);
	}
	
	public void removeChangementDeSelectionListener(IChangementDeSelectionListener l) {
		listenerList.remove(IChangementDeSelectionListener.class, l);
	}
	
	protected void fireChangementDeSelection(ChangementDeSelectionEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChangementDeSelectionListener.class) {
				if (e == null)
					e = new ChangementDeSelectionEvent(this);
				( (IChangementDeSelectionListener) listeners[i + 1]).changementDeSelection(e);
			}
		}
	}
	
	public void addDeclencheCommandeControllerListener(IDeclencheCommandeControllerListener l) {
		listenerList.add(IDeclencheCommandeControllerListener.class, l);
	}
	
	public void removeDeclencheCommandeControllerListener(IDeclencheCommandeControllerListener l) {
		listenerList.remove(IDeclencheCommandeControllerListener.class, l);
	}
	
	protected void fireDeclencheCommandeController(DeclencheCommandeControllerEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IDeclencheCommandeControllerListener.class) {
				if (e == null)
					e = new DeclencheCommandeControllerEvent(this);
				( (IDeclencheCommandeControllerListener) listeners[i + 1]).declencheCommandeController(e);
			}
		}
	}
	
	public void addAnnuleToutListener(IAnnuleToutListener l) {
		listenerList.add(IAnnuleToutListener.class, l);
	}
	
	public void removeAnnuleToutListener(IAnnuleToutListener l) {
		listenerList.remove(IAnnuleToutListener.class, l);
	}
	
	protected void fireAnnuleTout(AnnuleToutEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IAnnuleToutListener.class) {
				if (e == null)
					e = new AnnuleToutEvent(this);
				( (IAnnuleToutListener) listeners[i + 1]).annuleTout(e);
			}
		}
	}
	
	protected void fireEnregistreTout(AnnuleToutEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IAnnuleToutListener.class) {
				if (e == null)
					e = new AnnuleToutEvent(this);
				( (IAnnuleToutListener) listeners[i + 1]).enregistreTout(e);
			}
		}
	}

}
