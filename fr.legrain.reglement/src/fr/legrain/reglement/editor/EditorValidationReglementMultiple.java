//package fr.legrain.reglement.editor;
//
//import javax.persistence.EntityManager;
//
//import org.apache.log4j.Logger;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.ui.IEditorInput;
//import org.eclipse.ui.IEditorSite;
//import org.eclipse.ui.PartInitException;
//import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.part.EditorPart;
//
//import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
//import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
//import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
//import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
//import fr.legrain.reglement.ecran.PaValidationReglementMultiple;
//import fr.legrain.reglement.ecran.SWTPaValidationReglementMultipleController;
//
//public class EditorValidationReglementMultiple extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
//	
//	static Logger logger = Logger.getLogger(EditorValidationReglementMultiple.class.getName());
//	
//	public static final String ID = "fr.legrain.reglement.editor.EditorValidationReglementMultiple";
//	
//	private boolean enabled = true;
//	private PaValidationReglementMultiple composite = null;
//	//private SWTPaArticlesController controller = null;
//	private EntityManager em = null;
//
//	public EditorValidationReglementMultiple() {
//		super();
//	}
//	
//	public EditorValidationReglementMultiple(EditorPart parent) {
//		super(parent);
//	}
//	
//	public EditorValidationReglementMultiple(EditorPart parent,EntityManager em) {
//		super(parent);
//		this.em=em;
//	}
//	
//	@Override
//	protected void initEditor() {
//		setPanel(composite);
//	}
//
////	@Override
////	public void doSave(IProgressMonitor monitor) {
////		// TODO Auto-generated method stub
////	}
////
////	@Override
////	public void doSaveAs() {
////		// TODO Auto-generated method stub
////	}
//
//	@Override
//	public void init(IEditorSite site, IEditorInput input)
//			throws PartInitException {
//		setSite(site);
//	    setInput(input);
//	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());
//	    
//	    //getSite().getWorkbenchWindow().getPartService().addPartListener(TestPartListener.getTestPartListener());
//	    //getSite().getPage().addPartListener(TestPartListenerVide.getTestPartListener());
//	    //TestPartListener.getTestPartListener().setLgrActivePart(this);
//	}
//
////	@Override
////	public boolean isDirty() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean isSaveAsAllowed() {
////		// TODO Auto-generated method stub
////		return false;
////	}
//
//	@Override
//	public void createPartControl(Composite parent) {
//		composite = new PaValidationReglementMultiple(parent,SWT.NONE);
//		if(getController()==null) {
//			setController(new SWTPaValidationReglementMultipleController(composite,em));
//		} else {
//			getController().setEm(em);
//		}
//	}
//
////	@Override
////	public void setFocus() {
////		// TODO Auto-generated method stub
////	}
//
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return enabled;
//	}
//
//	public void setEnabled(boolean enabled) {
//		composite.setEnabled(enabled);
//		//c.setVisible(enabled);
//		this.enabled = enabled;
//	}
//
//	public boolean validate() {
//		try {
//			getController().validateUI();
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
//
//	public PaValidationReglementMultiple getComposite() {
//		return composite;
//	}
//
//	public boolean canLeaveThePage() {
//		//return true;
//		return validate();
//	}
//
//	public Object retour() {
//		// TODO Auto-generated method stub
//		//return c.getText1().getText();
//		return null;
//	}
//
//	public void utiliseRetour(Object r) {
//		// TODO Auto-generated method stub
//		//c.getText2().setText(r.toString());
//	}
//	
//
//}
