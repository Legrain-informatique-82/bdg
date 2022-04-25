package fr.legrain.stocks.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.stocks.controllers.PaEtatStocksController;
import fr.legrain.stocks.ecran.PaEtatStocks;

public class EditorEtatStocks extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorEtatStocks.class.getName());
	
	public static final String ID = "fr.legrain.stocks.editor.EditorEtatStocks";
	
	private boolean enabled = true;
	private PaEtatStocks composite = null;
	private EntityManager em = null;

	public EditorEtatStocks() {
		super();
	}
	
	public EditorEtatStocks(EditorPart parent) {
		super(parent);
	}
	
	public EditorEtatStocks(EditorPart parent,EntityManager em) {
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
		setSite(site);
	    setInput(input);
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());
	    
	    //getSite().getWorkbenchWindow().getPartService().addPartListener(TestPartListener.getTestPartListener());
	    //getSite().getPage().addPartListener(TestPartListenerVide.getTestPartListener());
	    //TestPartListener.getTestPartListener().setLgrActivePart(this);
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
		composite = new PaEtatStocks(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaEtatStocksController(composite,em));
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

	public PaEtatStocks getComposite() {
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
