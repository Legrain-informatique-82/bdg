package fr.legrain.avoir.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;


import fr.legrain.avoir.controller.PaEditorAvoirController;
import fr.legrain.avoir.ecran.PaEditorAvoir;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestionCommerciale.Perspective;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;


public class EditorAvoir extends JPALgrEditorPart implements ILgrEditor  {
	

	public static final String ID_EDITOR = "fr.legrain.editor.avoir.swt"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(EditorAvoir.class.getName());
	
	private boolean enabled = true;
	private PaEditorAvoir composite = null;
	private EntityManager em = null;

	
	public EditorAvoir(EditorPart parent) {
		super(parent);
	}
	
	public EditorAvoir(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}

	public EditorAvoir() {
		super();
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
    	composite = new PaEditorAvoir(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaEditorAvoirController(composite,em));
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
				//je n'active la perspective bureau que si je ferme l'éditeur facture, et non pas si je ferme
				//l'éditeur de l'impression
				if(part.getSite().getId().equals(EditorAvoir.ID_EDITOR)){
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
		//return true;
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
