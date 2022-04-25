package fr.legrain.document.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.document.controller.PaReglementController;
import fr.legrain.document.ecran.PaAffectationReglementSurFacture;
import fr.legrain.gestCom.librairiesEcran.editor.IContexteVisualisationId;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.PaSelectionVisualisation;

public class EditorReglement extends JPALgrEditorPart implements 
ILgrEditor, ILgrRetourEditeur, IContexteReglementId  {
	
	static Logger logger = Logger.getLogger(EditorReglement.class.getName());

	public static final String ID = "fr.legrain.document.editor.Reglement";
	
	private boolean enabled = true;
	private PaAffectationReglementSurFacture composite = null;
	private EntityManager em = null;
	
	public EditorReglement() {
		super();
	}
	
	public EditorReglement(EditorPart parent) {
		super(parent);
	}
	
	public EditorReglement(EditorPart parent,EntityManager em) {
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
	   
	    IContextService ics = (IContextService)getSite().getService(IContextService.class);
	    IContextActivation iac = ics.activateContext(C_CONTEXT_ID);

	}


	@Override
	public void createPartControl(Composite parent) {
		composite = new PaAffectationReglementSurFacture(parent,SWT.NONE);
		if(getController()==null) {
//			setControllerSelection(new BaseControllerSelection(composite,"V2_BONLIV","PaEditorBLController"));
			setController(new PaReglementController(composite,em));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		composite.setEnabled(enabled);
		this.enabled = enabled;
	}

	public boolean validate() {
		return true;
	}

	public PaAffectationReglementSurFacture getComposite() {
		return composite;
	}
	public boolean canLeaveThePage() {
		return true;
	}

	public Object retour() {
		return null;
	}

	public void utiliseRetour(Object r) {

	}
	

}
