package fr.legrain.prelevement.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestionCommerciale.Perspective;
import fr.legrain.prelevement.controller.PaEditorPrelevementController;
import fr.legrain.prelevement.ecran.PaEditorPrelevement;


public class EditorPrelevement extends JPALgrEditorPart implements ILgrEditor  {
	

	public static final String ID_EDITOR = "fr.legrain.editor.prelevement.swt"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(EditorPrelevement.class.getName());
	
	private boolean enabled = true;
	private PaEditorPrelevement composite = null;
	private EntityManager em = null;
	
	
	public EditorPrelevement(EditorPart parent) {
		super(parent);
	}
	
	public EditorPrelevement(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}

	public EditorPrelevement() {
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
    //	new CompositeSwing(parent,SWT.NONE,panel);
    	composite = new PaEditorPrelevement(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaEditorPrelevementController(composite,em));
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
				if(part.getSite().getId().equals(EditorPrelevement.ID_EDITOR)){
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
