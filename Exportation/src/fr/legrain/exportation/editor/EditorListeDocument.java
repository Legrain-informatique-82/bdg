package fr.legrain.exportation.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.document.controller.PaCritereListeDocumentCochableController;
import fr.legrain.document.controller.PaSelectionLigneDocumentCochableControlleur;
import fr.legrain.document.ecran.PaCritereListeDocumentCochable;
import fr.legrain.document.ecran.PaSelectionLigneDocumentCochable;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;


public class EditorListeDocument extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorListeDocument.class.getName());
	
	public static final String ID = "fr.legrain.exportation.editor.EditorListeDocument";
	
	private boolean enabled = true;
	private PaSelectionLigneDocumentCochable composite = null;
	private EntityManager em = null;

	public EditorListeDocument() {
		super();
	}
	
	public EditorListeDocument(EditorPart parent) {
		super(parent);
	}
	
	public EditorListeDocument(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}
	
	@Override
	protected void initEditor() {
		setPanel(composite);
	}
	
	@Override
	protected String getID() {
		return ID;
	}



	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
	    setInput(input);
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());

	}



	@Override
	public void createPartControl(Composite parent) {
		composite = new PaSelectionLigneDocumentCochable(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaSelectionLigneDocumentCochableControlleur(composite,em));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
	}

//	@Override
//	public void setFocus() {
//		// TODO Auto-generated method stub
//	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		composite.setEnabled(enabled);
		//c.setVisible(enabled);
		this.enabled = enabled;
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	public PaSelectionLigneDocumentCochable getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object retour() {
		// TODO Auto-generated method stub
		//return c.getText1().getText();
		return null;
	}

	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
		//c.getText2().setText(r.toString());
	}
	

}
