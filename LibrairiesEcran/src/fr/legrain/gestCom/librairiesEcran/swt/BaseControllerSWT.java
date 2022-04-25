package fr.legrain.gestCom.librairiesEcran.swt;


import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.lib.data.*;
import fr.legrain.lib.gui.BaseController;
import fr.legrain.lib.gui.DestroyEvent;
import fr.legrain.lib.gui.DestroyListener;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public abstract class BaseControllerSWT extends BaseController{
	
	/** Référence sur le composant SWT englobant la JPanel Swing de ce controller. */
	protected Shell parent;
	static Logger logger = Logger.getLogger(BaseControllerSWT.class.getName());
	
	protected EventListenerList listenerList = new EventListenerList();
	
	public BaseControllerSWT() {
		super();
	}
	
	/**
	 * Méthode appelée à la fermeture du Shell
	 * @return true ssi on autorise la fermeture du Shell
	 */
	abstract public boolean onClose() throws ExceptLgr;
	
	/**
	 * Appel de <code>parent.close()</code> à partir du thread SWT (appel asynchrone).<br>
	 * Permet le déclenchement de la fermeture du Shell <code>parent</code> à partir du thread de traitements
	 * des evenements Swing/AWT
	 */
	public void swingClose() {
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				parent.close();
				if(getFocusCourantApplication()!=null)
					getFocusCourantApplication().requestFocus();
			}
		});
		if(getFocusCourantApplication()!=null)
			getFocusCourantApplication().requestFocus();
	}
	
	/**
	 * Retourne le parent du shell courant
	 * @return
	 */
	public Shell getParent() {
		return parent;
	}
	
	public void setParent(Shell parent) {
		this.parent = parent;
		
		//retour ecran aide apres creation nouvelle valeur
		getParent().getDisplay().asyncExec(new Runnable(){
			public void run() {
				getParent().addShellListener(new ShellAdapter(){
					@Override
					public void shellActivated(ShellEvent e) {
						super.shellActivated(e);
						if (getFocusCourant()!=null){
							getFocusCourant().requestFocus();
						}
					}
					
				});
			}
		});
		
	}
	
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


	

}
