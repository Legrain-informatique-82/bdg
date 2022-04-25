package fr.legrain.document.RechercheDocument.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.RechercheDocument.ecrans.PaCompositeSectionParam;
import fr.legrain.document.RechercheDocument.ecrans.PaFormPage;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;


public class ParamControllerMini extends AbstractControllerMini implements RetourEcranListener {

	static Logger logger = Logger.getLogger(ParamControllerMini.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;

	private List<ModelObject> modele = null;

	private FormPageControllerPrincipal masterController = null;
	
	public static final String etatEnCoursLibelle = "En cours";
	public static final String etatEnCoursCode = "ENCOURS";
	private Control focusCourantSWT=null;
	private PaFormPage vue = null;

	private TaInfoEntrepriseDAO taInfoEntrepriseDAO = null;
	private TaInfoEntreprise taInfoEntreprise = null;
	
//	private Map<String,String> mapTypeDocument = new HashMap<String, String>();
	
	private Date datedeb;
	private Date datefin;
	private String typeDocEnCours = null;
	private String codeTiers = null;
	private Boolean nonRegle = true;
	private String nomTiers = null;
	private String codeDocument = null;
	
	TypeDoc typeDocument = TypeDoc.getInstance();
	
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
		if(!toolBarInitialise) {
			initController();
		}

		datedeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
		typeDocEnCours = vue.getCompositeSectionParam().getCbTypeDoc().getText();
		codeTiers = vue.getCompositeSectionParam().getTfCodeTiers().getText();
		nomTiers = vue.getCompositeSectionParam().getTfNomTiers().getText();
		codeDocument = vue.getCompositeSectionParam().getTfCodeDocument().getText();		
//		nonRegle = vue.getCompositeSectionParam().getBtnNonRegle().getSelection();
	}
	
	/* Permet d'initialiser la section de paramètres */
	public void appel() {
		initActions();
	}
	
	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshAction = new Action("Rechercher",DocumentPlugin.getImageDescriptor(PaCompositeSectionParam.iconPath)) { 
		@Override 
		public void run() { 
			datedeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
			
			typeDocEnCours = vue.getCompositeSectionParam().getCbTypeDoc().getText();
			codeTiers = vue.getCompositeSectionParam().getTfCodeTiers().getText();
			nomTiers = vue.getCompositeSectionParam().getTfNomTiers().getText();
			codeDocument = vue.getCompositeSectionParam().getTfCodeDocument().getText();
//			nonRegle = vue.getCompositeSectionParam().getBtnNonRegle().getSelection();
			masterController.refreshAll();
		}
	};

	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;
	
	public void initialiseParamIHM() {
		initActions();
	}
	
	private SelectionListener selectionAide = new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				actAide();
			} catch (Exception e1) {
				logger.error("Erreur", e1);
			}
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	};
	
	private FocusAdapter focus = new FocusAdapter() {

		@Override
		public void focusGained(FocusEvent e) {
			setFocusCourantSWT((Control)e.getSource());
		}
		
	};
	
	@Override
	protected void initActions() {	
		if(!toolBarInitialise) {
			vue.getCompositeSectionParam().getSectionToolbar().add(refreshAction);
			vue.getCompositeSectionParam().getSectionToolbar().update(true);
			
			vue.getCompositeSectionParam().getBtnRefesh().setImage(DocumentPlugin.getImageDescriptor(PaCompositeSectionParam.iconRefreshPath).createImage());
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
			
			vue.getCompositeSectionParam().getCbTypeDoc().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					initChampDate();
					vue.getCompositeSectionParam().getTfCodeDocument().setText("");
					vue.getControllerPage().raz(false);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
			
			vue.getCompositeSectionParam().getBtnAideTiers().setImage(DocumentPlugin.getImageDescriptor(PaCompositeSectionParam.iconFindPath).createImage());
			vue.getCompositeSectionParam().getBtnAideTiers().addSelectionListener(selectionAide);
			vue.getCompositeSectionParam().getBtnAideNomTiers().setImage(DocumentPlugin.getImageDescriptor(PaCompositeSectionParam.iconFindPath).createImage());
			vue.getCompositeSectionParam().getBtnAideNomTiers().addSelectionListener(selectionAide);
			vue.getCompositeSectionParam().getBtnAideDoc().setImage(DocumentPlugin.getImageDescriptor(PaCompositeSectionParam.iconFindPath).createImage());
			vue.getCompositeSectionParam().getBtnAideDoc().addSelectionListener(selectionAide);			

			
			List<String> temp = new LinkedList<String>();
			for (String libelle : typeDocument.getTypeDocCompletPresent().values()) {
				if(libelle!=TaRemise.TYPE_DOC && libelle!=TaReglement.TYPE_DOC)	
					temp.add(libelle);				
			}
			
			String[] liste= new String[temp.size()];
			int i = 0;
			for (String libelle : temp) {
				if(libelle!=TaRemise.TYPE_DOC && libelle!=TaReglement.TYPE_DOC)	liste[i]=libelle;				
				i++;
			}
//			mapTypeDocument=typeDocument.getTypeDocCompletPresent();
			vue.getCompositeSectionParam().getCbTypeDoc().setItems(liste);
			initListe();
			

			
			LibDateTime.setDate(vue.getCompositeSectionParam().getCdateDeb(),new Date());
			LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFin(),new Date());
//			vue.getCompositeSectionParam().getBtnNonRegle().setSelection(true);
			initChampDate();
			
			
			
			mapCommand = new HashMap<String, org.eclipse.core.commands.IHandler>();

			mapCommand.put(JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_AIDE_ID, new LgrAbstractHandler() {

				public Object execute(ExecutionEvent event) throws ExecutionException{
					try {
						actAide();
					} catch (Exception e1) {
						logger.error("Erreur : actionPerformed", e1);
					}
					return event;
				}}
			);

			
			initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

			if (mapActions == null)
				mapActions = new LinkedHashMap<Button, Object>();

			mapActions.put(vue.getCompositeSectionParam().getBtnAideTiers(), JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_AIDE_ID);

			Object[] tabActionsAutres = new Object[] {/* C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID */};
			mapActions.put(null, tabActionsAutres);
			
//			refreshAction.run();
			toolBarInitialise = true;
		}

	}
	
	public void initListe(){
		Integer rang = 0;
		if(getTypeDocEnCours()!=null) rang =vue.getCompositeSectionParam().getCbTypeDoc().indexOf(getTypeDocEnCours());
		if(rang==-1)rang=0;
		vue.getCompositeSectionParam().getCbTypeDoc().select(rang);
		vue.getCompositeSectionParam().getCbTypeDoc().setVisibleItemCount(vue.getCompositeSectionParam().getCbTypeDoc().getItemCount());
	}
	public void initChampDate() {
		LibDateTime.setDate(vue.getCompositeSectionParam().getCdateDeb(),taInfoEntreprise.getDatedebInfoEntreprise());
		LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFin(),taInfoEntreprise.getDatefinInfoEntreprise());

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
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getCompositeSectionParam().getTfCodeTiers());
		listeComposantFocusable.add(vue.getCompositeSectionParam().getTfNomTiers());
		listeComposantFocusable.add(vue.getCompositeSectionParam().getTfCodeDocument());
		
		vue.getCompositeSectionParam().getTfCodeTiers().setToolTipText("Code tiers");

		mapComposantChamps.put(vue.getCompositeSectionParam().getTfCodeTiers(),Const.C_CODE_TIERS);
		mapComposantChamps.put(vue.getCompositeSectionParam().getTfNomTiers(),Const.C_NOM_TIERS);
		mapComposantChamps.put(vue.getCompositeSectionParam().getTfCodeDocument(),Const.C_CODE_DOCUMENT);
		

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}
//		listeComposantFocusable.add(vue.getCompositeSectionParam().getBtnNonRegle());
		listeComposantFocusable.add(vue.getCompositeSectionParam().getBtnRefesh());

		vue.getCompositeSectionParam().getTfCodeDocument().addFocusListener(focus);
		vue.getCompositeSectionParam().getTfCodeTiers().addFocusListener(focus);
		vue.getCompositeSectionParam().getTfNomTiers().addFocusListener(focus);
		
		vue.getCompositeSectionParam().getBtnAideNomTiers().addFocusListener(focus);
		vue.getCompositeSectionParam().getBtnAideTiers().addFocusListener(focus);
		vue.getCompositeSectionParam().getBtnAideDoc().addFocusListener(focus);
//		vue.getCompositeSectionParam().getBtnNonRegle().addFocusListener(focus);
		vue.getCompositeSectionParam().getBtnRefesh().addFocusListener(focus);
		
	}
	
	//@Override
	//protected void actAide(String message) throws Exception{
	protected void actAide() throws Exception {
		
		setFocusAvantAideSWT(getFocusCourantSWT());
	
		//if(aideDisponible()){
		if(true){

			try{
				//setActiveAide(true);
				VerrouInterface.setVerrouille(true);
				vue.getPartControl().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();

				//paramAfficheAideRecherche.setMessage(message);
				//Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s,SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/***************************************************************/
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
				((LgrEditorPart)e).setController(paAideController);
				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
				/***************************************************************/
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);


//				switch ((SWTPaTiersController.this.dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
				
				if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getBtnAideDoc()) ||
						getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getTfCodeDocument())){
					if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getBtnAideDoc()))
							setFocusAvantAideSWT(vue.getCompositeSectionParam().getTfCodeDocument());
					int typeDoc = vue.getCompositeSectionParam().getCbTypeDoc().getSelectionIndex();
					AbstractLgrDAO dao = null;
					Class nomClass=null;
					paramAfficheAideRecherche.setAfficheDetail(false);
					paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
						dao =new TaFactureDAO(getEm());
						nomClass=TaFacture.class;
						paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
						dao =new TaApporteurDAO(getEm());
						nomClass=TaApporteur.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorApporteurController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaApporteurDAO,TaApporteur>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
						dao =new TaAvoirDAO(getEm());
						nomClass=TaAvoir.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorAvoirController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvoirDAO,TaAvoir>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
						dao =new TaBoncdeDAO(getEm());
						nomClass=TaBoncde.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorBoncdeController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaBoncdeDAO,TaBoncde>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
						dao =new TaBonlivDAO(getEm());
						nomClass=TaBonliv.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorBLController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaBonlivDAO,TaBonliv>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
						dao =new TaDevisDAO(getEm());
						nomClass=TaDevis.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaDevisDAO,TaDevis>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {
						dao =new TaProformaDAO(getEm());
						nomClass=TaProforma.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaProformaDAO,TaProforma>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_ACOMPTE)) {
						dao =new TaAcompteDAO(getEm());
						nomClass=TaAcompte.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorAcompteController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAcompteDAO,TaAcompte>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PRELEVEMENT)) {
						dao =new TaPrelevementDAO(getEm());
						nomClass=TaPrelevement.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaPrelevementDAO,TaPrelevement>(dao,IHMAideFacture.class));
					}
					if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
						dao =new TaAvisEcheanceDAO(getEm());
						nomClass=TaAvisEcheance.class;
						paramAfficheAideRecherche.setCleListeTitre("PaEditorAvisEcheanceController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvisEcheanceDAO,TaAvisEcheance>(dao,IHMAideFacture.class));
					}				
					paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//					controllerEcranCreation = this;
					paramAfficheAideRecherche.setTypeEntite(nomClass);
					paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
					paramAfficheAideRecherche.setDebutRecherche(vue.getCompositeSectionParam().getTfCodeDocument().getText());
					
//					paramAfficheAideRecherche.setControllerAppelant(SWTPaImpressionDocumentController.this);
					paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
					paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
				}
					if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getBtnAideTiers()) ||
							getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getTfCodeTiers())){
						
						if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getBtnAideTiers()))
							setFocusAvantAideSWT(vue.getCompositeSectionParam().getTfCodeTiers());
						
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getJPQLQuery());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;
						
						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getCompositeSectionParam().getTfCodeTiers().getText());
			//			paramAfficheAideRecherche.setControllerAppelant(this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers, TaTiersDAO, TaTiers>(SWTTiers.class, 
								new TaTiersDAO(getEm()).getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
						
					}
					if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getBtnAideNomTiers()) ||
							getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getTfNomTiers())){
						
						if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getBtnAideNomTiers()))
							setFocusAvantAideSWT(vue.getCompositeSectionParam().getTfNomTiers());
						
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getJPQLQuery());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;
						
						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_NOM_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getCompositeSectionParam().getTfNomTiers().getText());
			//			paramAfficheAideRecherche.setControllerAppelant(this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers, TaTiersDAO, TaTiers>(SWTTiers.class, 
								new TaTiersDAO(getEm()).getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
						
					}
					
					
//					break;
//				case C_MO_EDITION:
//				case C_MO_INSERTION:										
//	
//					break;
//				default:
//					break;
//				}

				if (paramAfficheAideRecherche.getJPQLQuery()!=null){


					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);	
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					//Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT)paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
					//paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					//Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1, paramAfficheAideRecherche.getTitreRecherche());

					//Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					//enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(this);
					Control focus = vue.getPartControl().getShell().getDisplay().getFocusControl();
					//affichage de l'ecran d'aide principal (+ ses recherches)

			//		dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/	
			//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);

				}

			}finally{
				VerrouInterface.setVerrouille(false);
				vue.getPartControl().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}
	
	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());
//					if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getTfNomTiers())||
//							getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getTfCodeTiers())){
//						//remplir la zone code tiers
//						TaTiers entity=null;
//						TaTiersDAO dao = new TaTiersDAO(getEm());
//						entity=dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						dao = null;
//						vue.getCompositeSectionParam().getTfCodeTiers().setText(entity.getCodeTiers());
//					}

					
//					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					//if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					//vue.getLaMessage().setText(e.getMessage());
				}


			}else{

			}
		}
		//super.retourEcran(evt);
	}

	public Date getDatedeb() {
		return datedeb;
	}

	public String getTypeDocEnCours() {
		return typeDocEnCours;
	}

	public Date getDatefin() {
		return datefin;
	}

	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}

	public Control getFocusCourantSWT() {
		return focusCourantSWT;
	}

	public void setFocusCourantSWT(Control focusCourantSWT) {
		this.focusCourantSWT = focusCourantSWT;
	}

	public void setTypeDocEnCours(String typeDocEnCours) {
		this.typeDocEnCours = typeDocEnCours;
	}

//	public Map<String, String> getMapTypeDocument() {
//		return mapTypeDocument;
//	}

}

