package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.editor.EditorBrowser;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputBrowser;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;



public class SWTPaBrowserController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaBrowserController.class.getName()); 
	private PaBrowserSWT vue = null;

	private Object ecranAppelant = null;
	
	public SWTPaBrowserController(PaBrowserSWT vue) {
		this(vue,null);
	}


	public SWTPaBrowserController(PaBrowserSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		setVue(vue);
		
		vue.getShell().addShellListener(this);

		initController();
	}

	private void initController() {
		try {
			
			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
//			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
//			vue.getTfCODE_ARTICLE().setMenu(popupMenuFormulaire);
//			vue.getPaGrille().setMenu(popupMenuGrille);
//			vue.getGrille().setMenu(popupMenuGrille);

			initEtatBouton();
//			if(listeContext.size()>0){
//			if(!listeContext.get(0).isActive(((IEvaluationContext) PlatformUI.getWorkbench().getService(IEvaluationContext.class))))
//				((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
//					.activateContext(idContext);
//			}
		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaBrowserSWT paArticleSWT) {
		
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if(param instanceof ParamAfficheBrowser) {
				if(((ParamAfficheBrowser)param).getPostData()!=null){
					vue.getBrowser().setUrl(((ParamAfficheBrowser)param).getUrl(), 
							((ParamAfficheBrowser)param).getPostData(),
							((ParamAfficheBrowser)param).getHttpHeader()
							);
				} else if(((ParamAfficheBrowser)param).getUrl()!=null){
					vue.getBrowser().setUrl(((ParamAfficheBrowser)param).getUrl());
				} else {
					//vue.getBrowser().setUrl("http://www.google.fr");
				}
				
				if(((ParamAfficheBrowser)param).getTitre()!=null){
					vue.getBrowser().setUrl(((ParamAfficheBrowser)param).getUrl());
				} else {
					//vue.getBrowser().setUrl("http://www.google.fr");
				}
			}
		}
		vue.getBrowser().setFocus();
		return null;
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
//		listeComposantFocusable.add(vue.getGrille());

//		vue.getTfCODE_ARTICLE().setToolTipText(Const.C_CODE_ARTICLE);
//		vue.getTfLIBELLEC_ARTICLE().setToolTipText(Const.C_LIBELLEC_ARTICLE);
//		vue.getTfLIBELLEL_ARTICLE().setToolTipText(Const.C_LIBELLEL_ARTICLE);
//		vue.getTfCODE_FAMILLE().setToolTipText(Const.C_CODE_FAMILLE);
//		vue.getTfNUMCPT_ARTICLE().setToolTipText(Const.C_NUMCPT_ARTICLE);
//		vue.getTfCODE_TVA().setToolTipText(Const.C_CODE_TVA);
//		vue.getTfCODE_T_TVA().setToolTipText(Const.C_CODE_T_TVA);
//		vue.getTfDIVERS_ARTICLE().setToolTipText(Const.C_DIVERS_ARTICLE);
//		vue.getTfCOMMENTAIRE_ARTICLE().setToolTipText(Const.C_COMMENTAIRE_ARTICLE);
//		vue.getTfSTOCK_MIN_ARTICLE().setToolTipText(Const.C_STOCK_MIN_ARTICLE);


//		mapComposantChamps.put(vue.getTfCODE_ARTICLE(), Const.C_CODE_ARTICLE);
//		mapComposantChamps.put(vue.getTfLIBELLEC_ARTICLE(), Const.C_LIBELLEC_ARTICLE);
//		mapComposantChamps.put(vue.getTfLIBELLEL_ARTICLE(), Const.C_LIBELLEL_ARTICLE);
//		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_CODE_FAMILLE);
//		mapComposantChamps.put(vue.getTfNUMCPT_ARTICLE(), Const.C_NUMCPT_ARTICLE);
//		mapComposantChamps.put(vue.getTfCODE_TVA(), Const.C_CODE_TVA);
//		mapComposantChamps.put(vue.getTfCODE_T_TVA(), Const.C_CODE_T_TVA);
//		mapComposantChamps.put(vue.getTfDIVERS_ARTICLE(), Const.C_DIVERS_ARTICLE);
//		mapComposantChamps.put(vue.getTfCOMMENTAIRE_ARTICLE(), Const.C_COMMENTAIRE_ARTICLE);
//		mapComposantChamps.put(vue.getTfSTOCK_MIN_ARTICLE(), Const.C_STOCK_MIN_ARTICLE);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBrowser());
//		listeComposantFocusable.add(vue.getBtnInserer());
//		listeComposantFocusable.add(vue.getBtnModifier());
//		listeComposantFocusable.add(vue.getBtnSupprimer());
//		listeComposantFocusable.add(vue.getBtnFermer());
//		listeComposantFocusable.add(vue.getBtnAnnuler());
		// listeComposantFocusable.add(vue.getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
//				.getTfCODE_ARTICLE());
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
//				.getTfCODE_ARTICLE());
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
//				.getGrille());
//
//		activeModifytListener();
//		
//		vue.getTfSTOCK_MIN_ARTICLE().addVerifyListener(queDesChiffres);
//		vue.getTfNUMCPT_ARTICLE().addVerifyListener(queDesChiffres);
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
//		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
//		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
//		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
//		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
//		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
//		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		// mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
//		//mapActions.put(vue.getBtnSelection(), C_COMMAND_GLOBAL_SELECTION_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}

	protected void initEtatBouton() {
		//super.initEtatBouton();
		//initEtatBoutonCommand();
		
	}	

	public SWTPaBrowserController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		
	}

	@Override
	protected void actModifier() throws Exception {
		
	}

	@Override
	protected void actSupprimer() throws Exception {
		
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		//if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {

	}

	@Override
	protected void actImprimer() throws Exception {
		actFermer();
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		return result;
	}
	
	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {

	}
	
	protected void initImageBouton() {
		
	}

	@Override
	protected void actEnregistrer() throws Exception {
		
	}

	public void setVue(PaBrowserSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		//mapComposantDecoratedField.put(vue.getBrowser(), vue.getFieldCODE_ARTICLE());
	}

	@Override
	protected void actRefresh() throws Exception {
		vue.getBrowser().refresh();
	}

	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		
	}
	
	public static void openURL(String url, String title, String tooltip) throws PartInitException {
		openURL(url, null, null, title, tooltip);
	}

	public static void openURL(String url, String postData, String[] httpHeader, String title, String tooltip) throws PartInitException {
		if(url!=null) {
			if(title==null) title="";
			if(tooltip==null) tooltip="";
			//LgrPartListener.getLgrPartListener().setLgrActivePart(null);
			EditorInputBrowser editorInputBrowser = new EditorInputBrowser();
			editorInputBrowser.setName(title);
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(editorInputBrowser, EditorBrowser.ID);
			//LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			//LgrPartListener.getLgrPartListener().setLgrActivePart(e);
			
			ParamAfficheBrowser paramAfficheBrowser = new ParamAfficheBrowser();
			paramAfficheBrowser.setUrl(url);
			paramAfficheBrowser.setHttpHeader(httpHeader);
			paramAfficheBrowser.setPostData(postData);
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheBrowser);
		}
	}


}
