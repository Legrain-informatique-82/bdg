package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart3;
import org.eclipse.ui.IWorkbenchPartReference;

public class LgrPartListener implements IPartListener2 {
	
	static Logger logger = Logger.getLogger(LgrPartListener.class.getName());
	
	static public LgrPartListener partlistener = null;
	
	//WorkbenchPart dans laquelle l'utilisateur est "verrouille"
	private IWorkbenchPart lgrActivePart; 
	
	//historique des WorkbenchPart activee
	private List<IWorkbenchPart> lgrNavigation = new ArrayList<IWorkbenchPart>(); 
	
	////historique des WorkbenchPart verrouillees
	private List<IWorkbenchPart> lgrNavigationVerrou = new ArrayList<IWorkbenchPart>(); 
	
	static public LgrPartListener getLgrPartListener() {
		if(partlistener==null) partlistener = new LgrPartListener();
		return partlistener;
	}
	
	private LgrPartListener() {
	}

	public void partActivated(IWorkbenchPartReference partRef) {
		if(lgrActivePart!=null) {
			if(partRef.getPart(false)!=lgrActivePart) {
				//activation de la WorkbenchPart verrouillee a la place de la partie demandee
				lgrActivePart.getSite().getPage().activate(lgrActivePart);
			}
		}
		lgrNavigation.add(partRef.getPart(false));
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
	}

	public void partClosed(IWorkbenchPartReference partRef) {
		IWorkbenchPart precedent = null;
		//recuperation de la WorkbenchPart precedente dans l'historique
		if(lgrNavigation.indexOf(partRef.getPart(false))-1>=0) {
			precedent = lgrNavigation.get(lgrNavigation.indexOf(partRef.getPart(false))-1);
		}
		
		if(precedent!=null) { 
			//on peu faire un traitement de retour
			if(partRef.getPart(false) instanceof ILgrRetourEditeur && precedent instanceof ILgrRetourEditeur) {
				((ILgrRetourEditeur)precedent).utiliseRetour(((ILgrRetourEditeur)partRef.getPart(false)).retour());
			}
			
			if(lgrNavigationVerrou.contains(precedent)) {
				//la partie precedente etait une WorkbenchPart verrouillee, donc on l'active et on verrouille
				partRef.getPage().activate(precedent);
				setLgrActivePart(precedent);
			}
			
		}
		
		//suppression de la WorkbenchPart des historiques
		while(getLgrNavigation().remove(partRef.getPart(false)));
		while(lgrNavigationVerrou.remove(partRef.getPart(false)));
	}

	public void partDeactivated(IWorkbenchPartReference partRef) {
		if(lgrActivePart!=null) {
			if(partRef.getPart(false)==lgrActivePart) {
				//la WorkbenchPart desactive etait verrouille donc on la reactive immediatement
				lgrActivePart.getSite().getPage().activate(lgrActivePart);
			} else {
				
			}
		}
	}

	public void partHidden(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
	}

	public void partInputChanged(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
	}

	public void partOpened(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
	}

	public void partVisible(IWorkbenchPartReference partRef) {
		if(lgrActivePart!=null) {
			if(partRef.getPart(false)!=lgrActivePart) {
				//une WorkbenchPart est rendue visible mais il en existe une de verrouille, donc on active
				//la WorkbenchPart verrouille. En cas de creation d"une nouvelle WorkbenchPart non verrouille.
				//Celle ci est creee mais non affiche
				lgrActivePart.getSite().getPage().activate(lgrActivePart);
			}
		}
	}

	public IWorkbenchPart getLgrActivePart() {
		return lgrActivePart;
	}

	public void setLgrActivePart(IWorkbenchPart lgrActivePart) {
		this.lgrActivePart = lgrActivePart;
		lgrNavigationVerrou.add(lgrActivePart);
	}

	public List<IWorkbenchPart> getLgrNavigation() {
		return lgrNavigation;
	}

}
