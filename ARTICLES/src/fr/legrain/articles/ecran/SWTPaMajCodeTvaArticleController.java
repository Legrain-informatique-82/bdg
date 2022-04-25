package fr.legrain.articles.ecran;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorTva;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTTva;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrSimpleTextContentProposal;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;


public class SWTPaMajCodeTvaArticleController extends JPABaseControllerSWTStandard implements
RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaMajCodeTvaArticleController.class.getName());		
	private PaMajCodeTvaArticle vue = null; // vue	
	private TaArticleDAO dao = null;//new TaFactureDAO();
	private TaTvaDAO daoTva = null;

	private Object ecranAppelant = null;
	private ContentProposalAdapter codeTVaContentProposalDebut = null;
	private ContentProposalAdapter codeFactureContentProposalFin = null;
	
	private LgrModifyListener tfModifyListener = new LgrModifyListener();
	
	
	
	public SWTPaMajCodeTvaArticleController(PaMajCodeTvaArticle vue) {
		this(vue,null);
	}

	public SWTPaMajCodeTvaArticleController(PaMajCodeTvaArticle vue,EntityManager em) {
		try{
			initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}
		dao = new TaArticleDAO(getEm());
		daoTva= new TaTvaDAO(dao.getEntityManager());
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);

			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Exporter [F11]");

			vue.getTfNumDeb().addModifyListener(tfModifyListener);
			vue.getTfNumFin().setEnabled(true);

			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		}finally{
			initCursor(SWT.CURSOR_ARROW);
		}
	}


	private class LgrModifyListener implements ModifyListener, SelectionListener{

		public void modifyText(ModifyEvent e) {
			modif(e);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			modif(e);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			modif(e);
		}
	}

	private void modif(TypedEvent e) {
		try {
			if(e.getSource().equals(vue.getTfNumDeb())) {
				if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
					vue.getTfNumFin().setEnabled(true);
				} else {
					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
				}
			}

			
		} catch (Exception e1) {
			logger.error(e1);
		}		
	}
	private void initController() {
		try {
			setDaoStandard(((AbstractLgrDAO) dao));
			

			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);


			initEtatBouton();
			
		} catch (Exception e) {
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getPaBtn1().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getPaBtn1().getBtnImprimer().setText("Mettre à jour [F3]");
		vue.layout(true);
	}


	public Composite getVue() {return vue;}

	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){

			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			param.setFocus(((AbstractLgrDAO) dao).getModeObjet().getFocusCourant());
		}
		return null;
	}

	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {
	}

	/**
	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {
		super.initEtatBouton();
		boolean trouve =false;

		trouve = daoStandard.selectCount()>0;

		switch (((AbstractLgrDAO) dao).getModeObjet().getMode()) {
		case C_MO_INSERTION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		case C_MO_EDITION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		case C_MO_CONSULTATION:
			actionInserer.setEnabled(!trouve);
			actionModifier.setEnabled(trouve);
			actionEnregistrer.setEnabled(false);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(true);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		default:
			break;
		}

		initFocusSWT(((AbstractLgrDAO) dao),mapInitFocus);
	}	


	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList();

		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap();
		vue.getTfNumDeb().setToolTipText("NumDeb");
		vue.getTfNumFin().setToolTipText("NumFin");
		
		listeComposantFocusable.add(vue.getTfNumDeb());
		listeComposantFocusable.add(vue.getTfNumFin());

		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfNumDeb());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfNumDeb());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfNumDeb());
		
		String activationContentProposal = "Ctrl+Space";
		//String activationContentProposal = C_COMMAND_GLOBAL_AIDE_ID;
		String [][]listeTva =initContentProposalCodeTva();
		codeTVaContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDeb(),activationContentProposal,listeTva,null);
		LgrSimpleTextContentProposal.defaultOptions(codeTVaContentProposalDebut);
		codeFactureContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFin(),activationContentProposal,listeTva,null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalFin);
	}

	public String[][] initContentProposalCodeTva(){
		String[][] valeurs = null;
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			try {
				List<TaTva> l =  daoTva.selectAll();

				valeurs = new String[l.size()][2];
				int i = 0;
				String description = "";
				for (TaTva tva : l) {
					valeurs[i][0] = tva.getCodeTva();

					description = "";
					description += tva.getLibelleTva();
					valeurs[i][1] = description;

					i++;
				}

			} catch (Exception e) {
				return null;
			}
		return valeurs;			
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
//		initCommands();
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaMajCodeTvaArticleController getThis(){
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		return true;
	}


	public void retourEcran(RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
//		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception{}

	@Override
	protected void actModifier() throws Exception{}

	@Override
	protected void actSupprimer() throws Exception{}

	@Override
	protected void actFermer() throws Exception {
		if (onClose()) {
			closeWorkbenchPart();
		}
	}

	@Override
	protected void actAnnuler() throws Exception{
		if (focusDansEcran())actionFermer.run();
	}

	@Override
	protected void actImprimer() throws Exception{
		actEnregistrer();
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}


	@Override
	protected void actAide(String message) throws Exception {
//	VerrouInterface.setVerrouille(true);
	try {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//		paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
		paramAfficheAideRecherche.setMessage(message);
		// Creation de l'ecran d'aide principal
		Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
		PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
		SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
		/** ************************************************************ */
		LgrPartListener.getLgrPartListener().setLgrActivePart(null);
		IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
		LgrPartListener.getLgrPartListener().setLgrActivePart(e);
		LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
		paAideController = new SWTPaAideControllerSWT(((EditorAide) e).getComposite());
		((LgrEditorPart) e).setController(paAideController);
		((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
		/** ************************************************************ */
		JPABaseControllerSWTStandard controllerEcranCreation = null;
		ParamAffiche parametreEcranCreation = null;
		IEditorPart editorCreation = null;
		String editorCreationId = null;
		IEditorInput editorInputCreation = null;
		Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
		Class nomClass=null;
		
			if (getFocusCourantSWT().equals(vue.getTfNumDeb())||
					getFocusCourantSWT().equals(vue.getTfNumFin())) {
				PaTVASWT paTvaSWT = new PaTVASWT(s2,SWT.NULL);
				SWTPaTvaController swtPaTvaController = new SWTPaTvaController(paTvaSWT);

				editorCreationId = EditorTva.ID;
				editorInputCreation = new EditorInputTva();

				ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
				paramAfficheAideRecherche.setJPQLQuery(new TaTvaDAO(getEm()).getJPQLQuery());
				paramAfficheTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
				paramAfficheTva.setEcranAppelant(paAideController);
				controllerEcranCreation = swtPaTvaController;
				parametreEcranCreation = paramAfficheTva;

				paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
				
				paramAfficheAideRecherche.setTypeEntite(TaTva.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TVA);
				if(getFocusCourantSWT().equals(vue.getTfNumDeb()))
				paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDeb().getText());
				else paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFin().getText());
				paramAfficheAideRecherche.setControllerAppelant(SWTPaMajCodeTvaArticleController.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTva,TaTvaDAO,TaTva>(SWTTva.class,dao.getEntityManager()));
				paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());

				paramAfficheAideRecherche.setChampsIdentifiant(swtPaTvaController.getDao().getChampIdTable());
			}

		if (paramAfficheAideRecherche.getJPQLQuery() != null) {

			PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s, SWT.NULL);
			SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

			// Parametrage de la recherche
			paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
			paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
			paramAfficheAideRecherche.setEditorCreation(editorCreation);
			paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
			paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
			paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
			paramAfficheAideRecherche.setShellCreation(s2);
			paAideRechercheController1.configPanel(paramAfficheAideRecherche);

			// Ajout d'une recherche
			paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());

			// Parametrage de l'ecran d'aide principal
			ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
			ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

			// enregistrement pour le retour de l'ecran d'aide
			paAideController.addRetourEcranListener(getThis());
			Control focus = vue.getShell().getDisplay().getFocusControl();
			// affichage de l'ecran d'aide principal (+ ses recherches)

			paAideController.configPanel(paramAfficheAide);

		}

	} finally {
//		VerrouInterface.setVerrouille(false);
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
}
	@Override
	protected void actEnregistrer() throws Exception{
		
		TaTva tvaSource = null;
		TaTva tvaDest = null;
		List<TaArticle> listeArticle=null;
		try {

			if(vue.getTfNumDeb().getText()!=null){
				tvaSource=daoTva.findByCode(vue.getTfNumDeb().getText());
			}
			if(vue.getTfNumFin().getText()!=null){
				tvaDest=daoTva.findByCode(vue.getTfNumFin().getText());
			}	
			if(tvaSource!=null && tvaDest!=null){
				if(MessageDialog.openConfirm(vue.getShell(), "Modification des codes tva sur tous les articles", "Attention, si vous continuez, tous les articles" +
						" ayant pour code tva "+tvaSource.getCodeTva()+" vont être affectés du code tva "+tvaDest.getCodeTva()+". Etes-vous sûr de vouloir continuer ?")){
					dao.getEntityManager().getTransaction().begin();
					listeArticle=dao.findByCodeTva(tvaSource.getCodeTva());
					for (TaArticle taArticle : listeArticle) {
						taArticle.setTaTva(tvaDest);
						dao.mergeFlush(taArticle);
					}
					dao.getEntityManager().getTransaction().commit();
					MessageDialog.openInformation(vue.getShell(), "Modification des codes tva sur tous les articles", "Terminée avec succès");
				}

			}else{
				MessageDialog.openConfirm(vue.getShell(), "Modification des codes tva sur tous les articles", "Attention, vous n'avez pas selectionné 2 codes de tva valides !!!");
			}
		} catch (Exception e) {
			MessageDialog.openInformation(vue.getShell(), "Modification des codes tva sur tous les articles", "Abandon");
			dao.getEntityManager().getTransaction().rollback();
			logger.error("", e);
		}

	}

	public AbstractLgrDAO getDao() {
		return ((AbstractLgrDAO) dao);
	}

	@Override
	public void initEtatComposant() {}


	@Override
	protected void actRefresh() throws Exception {
	}

	@Override
	protected void initMapComposantDecoratedField() {
	}

	@Override
	protected void sortieChamps() {
		try {
			if(getFocusCourantSWT().equals(vue.getTfNumDeb())){
				if(!vue.getTfNumDeb().getText().equals(""))
					daoTva.findByCode(vue.getTfNumDeb().getText());
			}
			if(getFocusCourantSWT().equals(vue.getTfNumFin())){
				if(!vue.getTfNumFin().getText().equals(""))
					daoTva.findByCode(vue.getTfNumFin().getText());
			}
		} catch (Exception e) {
			MessageDialog.openInformation(vue.getShell(), "Erreur de Saisie", "ce code tva n'existe pas");
			//getFocusCourantSWT().forceFocus();
			logger.error("", e);
		}

	}
	


}
