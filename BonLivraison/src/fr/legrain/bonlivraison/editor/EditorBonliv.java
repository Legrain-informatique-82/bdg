package fr.legrain.bonlivraison.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.bonlivraison.controller.PaEditorBLController;
import fr.legrain.bonlivraison.ecran.PaEditorBL;
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
public class EditorBonliv extends JPALgrEditorPart implements ILgrEditor  {
	

	public static final String ID_EDITOR = "fr.legrain.editor.bonliv.swt"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(EditorBonliv.class.getName());
	
	private boolean enabled = true;
	private PaEditorBL composite = null;
	private EntityManager em = null;
	
	
	public EditorBonliv(EditorPart parent) {
		super(parent);
	}
	
	public EditorBonliv(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}

	public EditorBonliv() {
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
    	composite = new PaEditorBL(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaEditorBLController(composite,em));
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
				if(part.getSite().getId().equals(EditorBonliv.ID_EDITOR)){
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
