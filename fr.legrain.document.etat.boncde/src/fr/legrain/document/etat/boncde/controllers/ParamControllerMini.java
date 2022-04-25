package fr.legrain.document.etat.boncde.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
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

import fr.legrain.document.etat.boncde.Activator;
import fr.legrain.document.etat.boncde.preferences.PreferenceConstants;
import fr.legrain.document.etat.devis.ecrans.PaCompositeSectionParam;
import fr.legrain.document.etat.devis.ecrans.PaFormPage;
import fr.legrain.documents.dao.TaEtat;
import fr.legrain.documents.dao.TaEtatDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
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
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
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

	private FormPageControllerBoncde masterController = null;

	private PaFormPage vue = null;

	private TaInfoEntrepriseDAO taInfoEntrepriseDAO = null;
	private TaInfoEntreprise taInfoEntreprise = null;
	
	private Map<String,String> mapEtatCodeLibelle = new HashMap<String, String>();
	
	private Date datedeb;
	private Date datefin;
	private int nbJour = 0;
	private String codeEtat = null;
	private String codeTiers = null;
	
	/* Titre et contenu du message d'erreur pour date incorrectes */
	private String ttlErreurDate = "La date saisie est incorrecte";
	private String msgErreurDate = "Le tableau de bord requiert une période positive.";
	
	/* Constructeur par défaut */
	public ParamControllerMini(FormPageControllerBoncde masterContoller, PaFormPage vue, EntityManager em) {
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
		nbJour = LibConversion.stringToInteger(vue.getCompositeSectionParam().getTfVariableDate().getText(),0);
		codeEtat = vue.getCompositeSectionParam().getCbEtat().getText();
		codeTiers = vue.getCompositeSectionParam().getTfCodeTiers().getText();
	}
	
	/* Permet d'initialiser la section de paramètres */
	public void appel() {
		initActions();
	}
	
	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshAction = new Action("Rechercher",Activator.getImageDescriptor(PaCompositeSectionParam.iconPath)) { 
		@Override 
		public void run() { 
			datedeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
			
			nbJour = LibConversion.stringToInteger(vue.getCompositeSectionParam().getTfVariableDate().getText(),0);
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
			
			vue.getCompositeSectionParam().getCbEtat().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					initChampDate();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
			vue.getCompositeSectionParam().getBtnAideTiers().setImage(Activator.getImageDescriptor(PaCompositeSectionParam.iconFindPath).createImage());
			vue.getCompositeSectionParam().getBtnAideTiers().addSelectionListener(new SelectionListener() {
				
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
			});
			
			TaEtatDAO taEtatDAO = new TaEtatDAO();
			List<TaEtat> listeEtat = taEtatDAO.selectAll();
			
			mapEtatCodeLibelle.put(fr.legrain.document.etat.devis.controllers.ParamControllerMini.etatEnCoursLibelle, null);
			vue.getCompositeSectionParam().getCbEtat().add(fr.legrain.document.etat.devis.controllers.ParamControllerMini.etatEnCoursLibelle);
			
			String etatDefaut = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ETAT_DEFAUT);

			int i = 1;
			int selection = -1;
			for (TaEtat taEtat : listeEtat) {
				mapEtatCodeLibelle.put(taEtat.getLibEtat(), taEtat.getCodeEtat());
				vue.getCompositeSectionParam().getCbEtat().add(taEtat.getLibEtat());
				if(selection==-1 && taEtat.getCodeEtat().equals(etatDefaut)) {
					selection = i;
				}
				i++;
			}
			if(selection==-1)
				selection=0;
			vue.getCompositeSectionParam().getCbEtat().select(selection);
			
			LibDateTime.setDate(vue.getCompositeSectionParam().getCdateDeb(),new Date());
			LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFin(),new Date());
			String decalageJour = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_NB_JOUR);
			vue.getCompositeSectionParam().getTfVariableDate().setText(decalageJour);
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
	
	public void initChampDate() {
		String v = vue.getCompositeSectionParam().getCbEtat().getText();
		if(v.equals(fr.legrain.document.etat.devis.controllers.ParamControllerMini.etatEnCoursLibelle)) {
			vue.getCompositeSectionParam().getCdateDeb().setEnabled(false);
			vue.getCompositeSectionParam().getCdateFin().setEnabled(true);
		} else {
			vue.getCompositeSectionParam().getCdateDeb().setEnabled(true);
			vue.getCompositeSectionParam().getCdateFin().setEnabled(true);
		}
		
		Date datefin = LibDate.incrementDate(new Date(),
				LibConversion.stringToInteger(vue.getCompositeSectionParam().getTfVariableDate().getText(),0)
				, 0, 0);
		
		LibDateTime.setDate(vue.getCompositeSectionParam().getCdateFin(),datefin);
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

		vue.getCompositeSectionParam().getTfCodeTiers().setToolTipText("Code tiers");

		mapComposantChamps.put(vue.getCompositeSectionParam().getTfCodeTiers(),Const.C_CODE_TIERS);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getCompositeSectionParam().getBtnRefesh());

//	if(mapInitFocus == null) 
//		mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
//	mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfCODE_TIERS());

	}
	
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
		}
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

	public Map<String, String> getMapEtatCodeLibelle() {
		return mapEtatCodeLibelle;
	}

}

