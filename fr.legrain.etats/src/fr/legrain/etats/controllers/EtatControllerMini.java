package fr.legrain.etats.controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.etats.Activator;
import fr.legrain.etats.ecrans.PaCompositeGroupeEtat1;
import fr.legrain.etats.ecrans.PaFormPage;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.ModelObject;


public class EtatControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(EtatControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;

	private List<ModelObject> modele = null;

	private FormPageControllerPrincipal masterController = null;

	private PaFormPage vue = null;

	private TaInfoEntrepriseDAO taInfoEntrepriseDAO = null;
	private TaInfoEntreprise taInfoEntreprise = null;
	
	private Date datedeb;
	private Date datefin;
	
	/* Titre et contenu du message d'erreur pour date incorrectes */
	private String ttlErreurDate = "La date saisie est incorrecte";
	private String msgErreurDate = "Le tableau de bord requiert une période positive.";
	

	/* Constructeur par défaut */
	public EtatControllerMini(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

		taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
	}

	public void initialiseModelIHM() {
		initActions();
	}
	
	/* Permet d'initialiser la section de paramètres */
	public void appel() {
		initActions();
	}
	
	private static int maxZoomIn = 150;
	private static int maxZoomOut = 50;
	private static int stepZoom = 10;
	private Action zoomOutAction = new Action("Plus petit",Activator.getImageDescriptor("/icons/zoom_out.png")) { 
		@Override 
		public void run() { 
			Control[] controls = vue.getCompositeListe().getCompo().getChildren();
			RowData gd = null;
			for (int i = 0; i < controls.length; i++) {
				gd = (RowData)controls[i].getLayoutData();
				if(gd.width-stepZoom>maxZoomOut) {
					gd.width = gd.width-stepZoom;
					gd.height = gd.height-stepZoom;
					controls[i].setLayoutData(gd);
				}
			}
			vue.getCompositeListe().getCompo().layout();
		}
	};
	
	private Action zoomInAction = new Action("Plus grand",Activator.getImageDescriptor("/icons/zoom_in.png")) { 
		@Override 
		public void run() { 
			Control[] controls = vue.getCompositeListe().getCompo().getChildren();
			RowData gd = null;
			for (int i = 0; i < controls.length; i++) {
				gd = (RowData)controls[i].getLayoutData();
				if(gd.width+stepZoom<maxZoomIn) {
					gd.width = gd.width+stepZoom;
					gd.height = gd.height+stepZoom;
					controls[i].setLayoutData(gd);
				}
			}
			vue.getCompositeListe().getCompo().layout();
		}
	};

	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;

	@Override
	protected void initActions() {	
		if(!toolBarInitialise) {
			vue.getCompositeListe().getSectionToolbar().add(zoomInAction);
			vue.getCompositeListe().getSectionToolbar().add(zoomOutAction);
			vue.getCompositeListe().getSectionToolbar().update(true);
			
//			vue.getCompositeListe().getBtnRefesh().setImage(Activator.getImageDescriptor(PaCompositeGroupeEtat1.iconRefreshPath).createImage());
//			vue.getCompositeListe().getBtnRefesh().addSelectionListener(new SelectionListener() {
//				
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					refreshAction.run();
//				}
//				
//				@Override
//				public void widgetDefaultSelected(SelectionEvent e) {
//					widgetSelected(e);
//				}
//			});
			
			
			toolBarInitialise = true;
		}

		//initialisation des dates
		if (taInfoEntreprise.getDatedebInfoEntreprise() != null){
//			Outils.setDateTime(vue.getCompositeSectionParam().getCdateDeb(),taInfoEntreprise.getDatedebInfoEntreprise());
//			Outils.setDateTime(vue.getCompositeSectionParam().getCdatefin(),taInfoEntreprise.getDatefinInfoEntreprise());
		}

	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		//		setObjetIHM(IdentiteIHM.class);
		//		bindForm(mapComposantChamps, IdentiteIHM.class, getSelectedObject(), vue.getSectionIdentite().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		//		mapComposantChamps = new HashMap<Control, String>();
		//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeTiers");
		//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelNom(), "nomTiers");
		//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelPrenom(), "prenomTiers");

	}

	public Date getDatedeb() {
		return datedeb;
	}

	public Date getDatefin() {
		return datefin;
	}

}

