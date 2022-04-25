package fr.legrain.abonnement.controllers.finalisationAbonnement;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.abonnement.pluginAbonnement;
import fr.legrain.abonnement.controllers.LigneLabelProvider;
import fr.legrain.abonnement.ecrans.PaCompositeSectionParam;
import fr.legrain.abonnement.ecrans.PaFormPageAbonnement;
import fr.legrain.articles.dao.TaTAbonnement;
import fr.legrain.articles.dao.TaTAbonnementDAO;
import fr.legrain.documents.dao.TaLEcheanceDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMEnteteAvisEcheance;
import fr.legrain.gestCom.Module_Document.IHMLEcheance;
import fr.legrain.gestCom.Module_Document.IHMPeriode;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
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
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;


public class ParamControllerMiniFinalisation extends AbstractControllerMini implements RetourEcranListener {

	static Logger logger = Logger.getLogger(ParamControllerMiniFinalisation.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;

	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewerComplementaire;
	
	private IObservableValue selectionComplementaire;
	protected List<IHMPeriode> modelPeriode = null;
	
	private List<ModelObject> modele = null;

	private FormPageControllerPrincipalFinalisation masterController = null;
	private Map<String,Object> listeGestionnaireEditorSupportAbon = new LinkedHashMap<String,Object>();
//	public static final String etatEnCoursLibelle = "En cours";
//	public static final String etatEnCoursCode = "ENCOURS";

	private PaFormPageAbonnement vue = null;

	private TaInfoEntrepriseDAO taInfoEntrepriseDAO = null;
	private TaInfoEntreprise taInfoEntreprise = null;
	
	private Map<String,String> mapTAbonnement = new HashMap<String, String>();
	
	private Date datedeb;
	private Date datefin;
	private int nbJour = 0;
	private String codeEtat = null;
	private String codeTiers = null;
	private boolean reglee = true;
	
	/* Titre et contenu du message d'erreur pour date incorrectes */
	private String ttlErreurDate = "La date saisie est incorrecte";
	private String msgErreurDate = "Le tableau de bord requiert une période positive.";
	
	protected LgrModifyListener lgrModifyListener = new LgrModifyListener(); //surveille les modifications des champs relies a la bdd
	protected LgrFocusListenerCdatetime dateFocusListener = new LgrFocusListenerCdatetime();
//	protected LgrModifyListener2 lgrModifyListener2 = new LgrModifyListener2();

	/* Constructeur par défaut */
	public ParamControllerMiniFinalisation(FormPageControllerPrincipalFinalisation masterContoller, PaFormPageAbonnement vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

		taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		createContributors();
	}

	public void initialiseModelIHM() {
		initActions();
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
	}
	
	/* Permet d'initialiser la section de paramètres */
	public void appel() {
		initActions();
	}
	public void actRefresh(){
		datedeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
		codeEtat = vue.getCompositeSectionParam().getCbEtat().getText();
		codeTiers = vue.getCompositeSectionParam().getTfCodeTiers().getText();
		masterController.refreshAll();
	}
	public void actCreer(IStructuredSelection selection,final ParamAffiche param ){
		try{
			masterController.getDocEcheanceController().actEnregistrer();
		}catch (Exception e) {
			logger.error("",e);
		}
	}
	public ParamControllerMiniFinalisation getThis() {
		return this;
	}
	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshAction = new Action("Rechercher",pluginAbonnement.getImageDescriptor(PaCompositeSectionParam.iconPath)) { 
		@Override 
		public void run() { 
			datedeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
			
			//nbJour = LibConversion.stringToInteger(vue.getCompositeSectionParam().getTfVariableDate().getText(),0);
			codeEtat = vue.getCompositeSectionParam().getCbEtat().getText();
			codeTiers = vue.getCompositeSectionParam().getTfCodeTiers().getText();
			
			
//			//datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin());
//			logger.debug(datedeb+"******"+datefin);
//			if (datedeb.compareTo(datefin)>=0){
//				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlErreurDate, msgErreurDate);
//			} else {
				masterController.refreshAll();
//			}
		}
	};

//	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
//	private Action creationAction = new Action("Creer",pluginLicence.getImageDescriptor(PaCompositeSectionDocEcheance.iconValidation)) { 
//		@Override 
//		public void run() { 
//			try{
//				IStructuredSelection selection =((IStructuredSelection)masterController.getDocEcheanceController().
//						getTableViewer().getSelection());
//				DocumentSelectionIHM documentSelection= (DocumentSelectionIHM)selection.getFirstElement();
//				if(documentSelection!=null && documentSelection.getCodeArticle()!=null){
//					SWTSupportAbonLogiciel support = new SWTSupportAbonLogiciel();
//					TaTiersDAO daoTiers = new TaTiersDAO();
//					TaTiers tiers =daoTiers.findByCode(documentSelection.getCodeTiers());
////					TaLFactureDAO daoLFacture = new TaLFactureDAO();
////					TaLFacture lfacture =daoLFacture.findById(documentSelection.getIdLDocument());					
//					support.setCodeArticle(documentSelection.getCodeArticle());
//					support.setCodeTiers(documentSelection.getCodeTiers());
//					support.setDateAcquisition(documentSelection.getDateDocument());					
//					support.setIdLDocument(documentSelection.getIdLDocument());
//					if(tiers!=null){
//						support.setNom(tiers.getNomTiers());
//						support.setPrenom(tiers.getPrenomTiers());
//						if(tiers.getTaEmail()!=null)
//							support.setEmail(tiers.getTaEmail().getAdresseEmail());
//						if(tiers.getTaTelephone()!=null)
//							support.setTel(tiers.getTaTelephone().getNumeroTelephone());
//					}
//					final ParamAffiche param = new ParamAffiche();
//					param.setSelected(support);
//					param.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//					for (Object key : listeGestionnaireEditorSupportAbon.keySet()) {
//						if(key.equals(documentSelection.getCodeTArticle())){
//							final AbstractLgrMultiPageEditor editor =(AbstractLgrMultiPageEditor) listeGestionnaireEditorSupportAbon.get(key);
//							Display.getDefault().syncExec(new Runnable() {
//								@Override
//								public void run() {   
//									AbstractLgrMultiPageEditor.ouvreFiche("","", editor.getID_EDITOR(),param,false);
//								}});
//						}
//					}
//					
//					refreshAction.run();
//				}
//			}catch (Exception e) {
//				logger.error("",e);
//			}
//		}
//	};
	
	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint pointSupport = registry.getExtensionPoint("GestionCommerciale.SupportAbonnement"); //$NON-NLS-1$

		//gestion des impressions de document
		if (pointSupport != null) {
			ImageDescriptor icon = null;
			IExtension[] extensions = pointSupport.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String ClassEditorName = confElements[jj].getAttribute("classEditor");//$NON-NLS-1$
						String classSupportName= confElements[jj].getAttribute("classSupport");//$NON-NLS-1$
						String classNameSupport= confElements[jj].getAttribute("nameSupport");//$NON-NLS-1$
//						String contributorName = confElements[jj].getContributor().getName();
						
						if (ClassEditorName == null || classSupportName == null)
							continue;
						Object classEditor=confElements[jj].createExecutableExtension("classEditor");
						Object classSupport=confElements[jj].createExecutableExtension("classSupport");
						Object classTabItem= (confElements[jj].createExecutableExtension("classCTablItem"));
						if(classEditor!=null && classTabItem!=null){
							listeGestionnaireEditorSupportAbon.put(classNameSupport,classEditor);
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;
	
	public void initialiseParamIHM() {
		initActions();
	}

	@Override
	protected void initActions() {	
		if(!toolBarInitialise) {
			
			vue.getCompositeSectionParam().getSectionToolbar().add(refreshAction);
			vue.getCompositeSectionParam().getSectionToolbar().update(true);
			
			vue.getCompositeSectionParam().getBtnRefesh().setImage(pluginAbonnement.getImageDescriptor(PaCompositeSectionParam.iconRefreshPath).createImage());
			vue.getCompositeSectionParam().getBtnRefesh().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					actRefresh();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
		
			vue.getCompositeSectionParam().getBtnAideTiers().setImage(pluginAbonnement.getImageDescriptor(PaCompositeSectionParam.iconFindPath).createImage());
			vue.getCompositeSectionParam().getBtnAideTiers().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						actAide();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
			TaTAbonnementDAO taTAbonnementDAO = new TaTAbonnementDAO();
			List<TaTAbonnement> listeTAbonnement = taTAbonnementDAO.selectAll();
			

			int i = 1;
			int selection = -1;
			for (TaTAbonnement taTAbonnement : listeTAbonnement) {
				if(taTAbonnement.getTypes()!=null   
//						&&listeGestionnaireEditorSupportAbon.containsKey(taTAbonnement.getCodeTAbonnement())
						){
							mapTAbonnement.put(taTAbonnement.getCodeTAbonnement(), taTAbonnement.getCodeTAbonnement());
							vue.getCompositeSectionParam().getCbEtat().add(taTAbonnement.getCodeTAbonnement());
				}
				i++;
			}
			if(selection==-1)
				selection=0;
			vue.getCompositeSectionParam().getCbEtat().select(selection);
			
			TaLEcheanceDAO lEcheanceDao=new TaLEcheanceDAO();
			modelPeriode = lEcheanceDao.recherchePeriodeEcheance(vue.getCompositeSectionParam().getCbEtat().getText());
			

			bindComplementaire();
			
			vue.getCompositeSectionParam().getCbEtat().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					selectionEtat();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					widgetSelected(e);
				}
			});
			
			vue.getCompositeSectionComplementaire().getGrille().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					if(selectionComplementaire!=null && selectionComplementaire.getValue()!=null){
						LibDateTime.setDate(vue.getCompositeSectionParam().getCdateDeb(),((IHMPeriode)selectionComplementaire.getValue()).getPeriodeDebut());
						LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFin(),((IHMPeriode)selectionComplementaire.getValue()).getPeriodeFin());
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					widgetSelected(e);
				}
			});
			
			LibDateTime.setDate(vue.getCompositeSectionParam().getCdateDeb(),taInfoEntreprise.getDatedebInfoEntreprise());
			LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFin(),taInfoEntreprise.getDatefinInfoEntreprise());

			
//			refreshAction.run();
			toolBarInitialise = true;
		}

		//initialisation des dates
		//if (taInfoEntreprise.getDatedebInfoEntreprise() != null){
			
			//LibDateTime.setDate(vue.getCompositeSectionParam().getCdatefin(),taInfoEntreprise.getDatefinInfoEntreprise());
		//}
			
			datedeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
			//nbJour = LibConversion.stringToInteger(vue.getCompositeSectionParam().getTfVariableDate().getText(),0);
			codeEtat = vue.getCompositeSectionParam().getCbEtat().getText();
			codeTiers = vue.getCompositeSectionParam().getTfCodeTiers().getText();
			reglee = vue.getCompositeSectionParam().getCbReglee().getSelection();
	}
	
	public void selectionEtat(){
		TaLEcheanceDAO lEcheanceDao=new TaLEcheanceDAO();
		modelPeriode = lEcheanceDao.recherchePeriodeEcheance(vue.getCompositeSectionParam().getCbEtat().getText());
		

		bindComplementaire();
	}

	public void bindComplementaire(){



		// Titre des colonnes
		String [] titreColonnes = {"Période debut","Période fin"};
		// Taille des colonnes
		String [] tailleColonnes = {"200","200"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"periodeDebut","periodeFin"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE};

		tableViewerComplementaire = new LgrTableViewer(vue.getCompositeSectionComplementaire().getGrille());
		tableViewerComplementaire.createTableCol(vue.getCompositeSectionComplementaire().getGrille(),titreColonnes,tailleColonnes,1,alignement);
		tableViewerComplementaire.setListeChamp(idColonnes);
		tableViewerComplementaire.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		tableViewerComplementaire.selectionGrille(0);
		selectionComplementaire = ViewersObservables.observeSingleSelection(tableViewerComplementaire);
		LigneLabelProvider.bind(tableViewerComplementaire, new WritableList(modelPeriode, IHMPeriode.class),
				BeanProperties.values(idColonnes));	
		
		//tableViewerComplementaire.getTable().getColumns()[titreColonnes.length-1].setResizable(false);

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
				mapComposantChamps = new HashMap<Control, String>();
				
		vue.getCompositeSectionParam().getCdateDeb().addFocusListener(dateFocusListener);
		//vue.getCompositeSectionParam().getCdateFin().addTraverseListener(new DateTraverse());
		vue.getCompositeSectionParam().getCdateFin().addFocusListener(dateFocusListener);
//		vue.getCompositeSectionParam().getCdateFinCalcul().addFocusListener(dateFocusListener);
		vue.getCompositeSectionParam().getCbEtat().addModifyListener(lgrModifyListener);
	}
	
	private class LgrFocusListenerCdatetime implements FocusListener {


		public void focusGained(FocusEvent e) {
			((DateTime) e.getSource()).addSelectionListener(lgrModifyListener);
		}

		public void focusLost(FocusEvent e) {
			((DateTime) e.getSource())
			.removeSelectionListener(lgrModifyListener);
			
		}

	}

	
	public class LgrModifyListener implements ModifyListener, SelectionListener {

		public void modifyText(ModifyEvent e) {
			masterController.raz(true);
//			if(((DateTime) e.getSource())==vue.getCompositeSectionParam().getCdateFin()){
//				LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFinCalcul(), LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()));
//			}
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			masterController.raz(true);
		}

		public void widgetSelected(SelectionEvent e) {

			masterController.raz(true);
//			if(((DateTime) e.getSource())==vue.getCompositeSectionParam().getCdateFin()){
//				LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFinCalcul(), LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()));
//			}
		}

	}
	
//	public class LgrModifyListener2 implements ModifyListener {
//
//		public void modifyText(ModifyEvent e) {
//			
//		}
//
//	}
	//@Override
	//protected void actAide(String message) throws Exception{
	protected void actAide() throws Exception {
		
		setFocusAvantAideSWT(vue.getCompositeSectionParam().getTfCodeTiers());
	
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
				
				
					//if(getFocusCourantSWT().equals(vue.getGrille())){
						
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
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers, TaTiersDAO, TaTiers>(SWTTiers.class, new TaTiersDAO(getEm()).getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
						
					//}
					
					
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
//					if(getFocusAvantAideSWT().equals(vue.getCompositeSectionParam().getTfCodeTiers())){
//						TaTEntite entity = null;
//						TaTEntiteDAO dao = new TaTEntiteDAO(getEm());
//						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						dao = null;
//						taTiers.setTaTEntite(entity);
//					}
					
//					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					//if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					//vue.getLaMessage().setText(e.getMessage());
				}


			}
		}else 
			actRefresh();

		//super.retourEcran(evt);
	}

	public Date getDatedeb() {
		return datedeb;
	}

	public int getNbJour() {
		return nbJour;
	}

	public String getCodeEtat() {
		return codeEtat;
	}

	public Date getDatefin() {
		return datefin;
	}

	public Map<String, String> getMapTAbonnement() {
		return mapTAbonnement;
	}

	public boolean isReglee() {
		return reglee;
	}

	public void setReglee(boolean reglee) {
		this.reglee = reglee;
	}

}

