package fr.legrain.statistiques.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.Activator;
import fr.legrain.statistiques.ecrans.PaCompositeSectionParam;
import fr.legrain.statistiques.ecrans.PaFormPage;
import fr.legrain.statistiques.Outils;


public class ParamControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(ParamControllerMini.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;

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
	public ParamControllerMini(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
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
	
	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshAction = new Action("Recalculer",Activator.getImageDescriptor(PaCompositeSectionParam.iconPath)) { 
		@Override 
		public void run() { 
			datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
			datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
			logger.debug(datedeb+"******"+datefin);
			if (datedeb.compareTo(datefin)>=0){
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlErreurDate, msgErreurDate);
			} else {
				masterController.refreshAll();
			}
		}
	};

	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;

	@Override
	protected void initActions() {	
		if(!toolBarInitialise) {
			vue.getCompositeSectionParam().getSectionToolbar().add(refreshAction);
			vue.getCompositeSectionParam().getSectionToolbar().update(true);
			
			vue.getCompositeSectionParam().getBtnRefesh().setImage(Activator.getImageDescriptor(PaCompositeSectionParam.iconRefreshPath).createImage());
			vue.getCompositeSectionParam().getBtnRefesh().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					refreshAction.run();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
			
			toolBarInitialise = true;
		}

		//initialisation des dates
		if (taInfoEntreprise.getDatedebInfoEntreprise() != null){
			Outils.setDateTime(vue.getCompositeSectionParam().getCdateDeb(),taInfoEntreprise.getDatedebInfoEntreprise());
			Outils.setDateTime(vue.getCompositeSectionParam().getCdatefin(),taInfoEntreprise.getDatefinInfoEntreprise());
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

