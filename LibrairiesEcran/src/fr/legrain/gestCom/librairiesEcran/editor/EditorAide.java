package fr.legrain.gestCom.librairiesEcran.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.aide.PaAideSWT;

public class EditorAide extends LgrEditorPart implements ILgrEditor, ILgrRetourEditeur, IContexteAideId {
	
	static Logger logger = Logger.getLogger(EditorAide.class.getName());
	
	public static final String ID = "fr.legrain.gestCom.librairiesEcran.editor.EditorAide";
	
	private boolean enabled = true;
	private PaAideSWT composite = null;
	//private SWTPaArticlesController controller = null;
	private IContextActivation iac = null;
	private IContextService ics = null;
	private EntityManager em = null;

	public EditorAide() {
		super();
	}
	
	public EditorAide(EditorPart parent) {
		super(parent);
	}
	
	public EditorAide(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}
	
	@Override
	protected void initEditor() {
	}

//	@Override
//	public void doSave(IProgressMonitor monitor) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void doSaveAs() {
//		// TODO Auto-generated method stub
//	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());
	    
	    //getSite().getWorkbenchWindow().getPartService().addPartListener(TestPartListener.getTestPartListener());
	    //getSite().getPage().addPartListener(TestPartListenerVide.getTestPartListener());
	    //TestPartListener.getTestPartListener().setLgrActivePart(this);

	    ics = (IContextService)getSite().getService(IContextService.class);
	   // ics = (IContextService)site.getService(IContextService.class);
	   // ics = (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);

	    iac = ics.activateContext(C_CONTEXT_ID_AIDE);

	  //  getSite().getPage().addPartListener(new FenetrePart());
	   
	    
	    //Test message dans StatusLine
	    getEditorSite().getActionBars().getStatusLineManager().setMessage(LibrairiesEcranPlugin.getImageDescriptor("/icons/help.png").createImage(),"Aide");
	}

//	@Override
//	public boolean isDirty() {
//		// TODO Auto-generated method stub
//		return false;
//	}

//	@Override
//	public boolean isSaveAsAllowed() {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public void createPartControl(Composite parent) {
		composite = new PaAideSWT(parent,SWT.NONE);
	}

	@Override
	public void setFocus() {
//		// TODO Auto-generated method stub
			
		//System.err.println(this.hashCode()+" *********"+ ics.getActiveContextIds() +"**************");
		
		super.setFocus();
		if(getController()!=null && getController().getFocusCourantSWT()!=null) {
			if(!getController().getFocusCourantSWT().isDisposed()) {
				getController().getFocusCourantSWT().setFocus();
			}
		}
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		composite.setEnabled(enabled);
		//c.setVisible(enabled);
		this.enabled = enabled;
		if(enabled==false)		
			if(ics!=null && iac!=null) {
			ics.deactivateContext(iac);
			}
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	public PaAideSWT getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object retour() {

		return null;
	}

	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
		//c.getText2().setText(r.toString());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
//		if(ics!=null && iac!=null) {
//			ics.deactivateContext(iac);
//		}
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		super.partClosed(part);
		if(ics!=null && iac!=null) {
		ics.deactivateContext(iac);
	}

	}

}
