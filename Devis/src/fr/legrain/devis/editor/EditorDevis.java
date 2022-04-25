package fr.legrain.devis.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.devis.controller.PaEditorDevisController;
import fr.legrain.devis.ecran.PaEditorDevis;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestionCommerciale.Perspective;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class EditorDevis extends JPALgrEditorPart implements ILgrEditor  {
	

	public static final String ID_EDITOR = "fr.legrain.editor.devis.swt"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(EditorDevis.class.getName());
	
	private boolean enabled = true;
	private PaEditorDevis composite = null;
	private EntityManager em = null;
	
	
	public EditorDevis(EditorPart parent) {
		super(parent);
	}

	public EditorDevis() {
		super();
	}
	
	public EditorDevis(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}
	
	@Override
	protected void initEditor() {
		setPanel(composite);
	}
	
	@Override
	protected String getID() {
		return ID_EDITOR;
	}
	
	@Override
	public void createPartControl(Composite parent) {
    //	new CompositeSwing(parent,SWT.NONE,panel);
    	composite = new PaEditorDevis(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaEditorDevisController(composite,em));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
	}
    class FenetrePart implements IPartListener{
		public void partActivated(IWorkbenchPart part) {
			if(getController()!=null)
				getController().getFocusCourantSWT().setFocus();
		}

		public void partBroughtToTop(IWorkbenchPart part) {}

		public void partClosed(IWorkbenchPart part) {
			try {
				//je n'active la perspective bureau que si je ferme l'éditeur devis, et non pas si je ferme
				//l'éditeur de l'impression
				if(part.getSite().getId().equals(EditorDevis.ID_EDITOR)){
					PlatformUI.getWorkbench().showPerspective(Perspective.ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				}
			} catch (Exception e) {
				logger.error("",e);
			}
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
		}
    }

    
    private void changeEditorName() {
	}

	public boolean canLeaveThePage() {
		return validate();
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	public boolean validate() {
		try {
			getController().validateUI();
		} catch (Exception e) {
			return false;
		}
		if(!getController().changementPageValide())return false;
		return true;
	}






    

}
