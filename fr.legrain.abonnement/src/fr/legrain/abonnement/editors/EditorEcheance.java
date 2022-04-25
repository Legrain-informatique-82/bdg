package fr.legrain.abonnement.editors;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.abonnement.controllers.SWTPaAbonnementController;
import fr.legrain.abonnement.controllers.echeance.FormPageControllerPrincipalEcheance;
import fr.legrain.abonnement.ecrans.PaAbonnementSWT;
import fr.legrain.abonnement.ecrans.PaFormPageEcheance;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;

public class EditorEcheance extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorEcheance.class.getName());
	
	public static final String ID = "fr.legrain.abonnement.Echeance";
	
	private String iconPageTB = "/icons/logo_lgr_16.png";

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	private EntityManager em = null;
	// FormPage principal
	private PaFormPageEcheance defaultFormPage = null;
	private Map<PaFormPageEcheance,FormPageControllerPrincipalEcheance> listeController = new HashMap<PaFormPageEcheance,FormPageControllerPrincipalEcheance>();

	
	private boolean enabled = true;
	private PaAbonnementSWT composite = null;


	public EditorEcheance() {
		super();
	}
	
	public EditorEcheance(EditorPart parent) {
		super(parent);
	}
	
	public EditorEcheance(EditorPart parent,EntityManager em) {
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
		composite = new PaAbonnementSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaAbonnementController(composite,em));
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
		this.enabled = enabled;
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	public PaAbonnementSWT getComposite() {
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
	
	@Override
	public String getTitle() {
		return "Gestion des abonnements";
	}
}
