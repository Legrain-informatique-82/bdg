package fr.legrain.tiers.views;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.ModelTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.ILgrListView;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
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

public class HandlerListeTiersViewAide extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerListeTiersViewAide.class.getName());
	
	private ModelTiers modelTiers = null;
	private PaListeTiersView vue = null;
	private ListeTiersViewController controller = null;

	public Object execute(ExecutionEvent event) throws ExecutionException{
		try {
			System.out.println("Handler aide");
			
			IViewReference[] vr = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
			ISelection selection = null;
			for (int i = 0; i < vr.length; i++) {
				if(vr[i].getId().endsWith(ListeTiersView.ID)) {
					modelTiers = ((ListeTiersView)vr[i].getView(false)).getController().getModelTiers();
					vue = ((ListeTiersView)vr[i].getView(false)).getVue();
					controller = ((ListeTiersView)vr[i].getView(false)).getController();
					actAide();
				}
				break;
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return event;
	}
	
	protected boolean aideDisponible() {
		boolean result = false;
		result = true;
		return result;
	}

//	@Override
	protected void actAide() throws Exception{
		actAide(null);
	}

//	@Override
	protected void actAide(String message) throws Exception{
		if(aideDisponible()){
			try{
		//		setActiveAide(true);
				VerrouInterface.setVerrouille(true);
				vue.getLgrViewer().getViewer().getTable().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				paramAfficheAideRecherche.setMessage(message);
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


				PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
				SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
				paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

				editorCreationId = TiersMultiPageEditor.ID_EDITOR;
				editorInputCreation = new EditorInputTiers();

				ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
				paramAfficheAideRecherche.setJPQLQuery(modelTiers.getJPQLQuerySansOrder());
				paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
				paramAfficheTiers.setEcranAppelant(paAideController);
				controllerEcranCreation = swtPaTiersController;
				parametreEcranCreation = paramAfficheTiers;

				paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
				//paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TIERS().getText());
				paramAfficheAideRecherche.setDebutRecherche("");
				//	paramAfficheAideRecherche.setControllerAppelant(this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers, TaTiersDAO, TaTiers>(SWTTiers.class,new TaTiersDAO().getEntityManager()));
				//paramAfficheAideRecherche.setModel(modelTiers);
				paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);


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
					paAideController.addRetourEcranListener(controller);
					Control focus = vue.getLgrViewer().getViewer().getTable().getShell().getDisplay().getFocusControl();
					//affichage de l'ecran d'aide principal (+ ses recherches)

				//	dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/	
				//	dbc.getValidationStatusMap().addMapChangeListener(changeListener);

				}

			}finally{
				VerrouInterface.setVerrouille(false);
				vue.getLgrViewer().getViewer().getTable().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}
} 
