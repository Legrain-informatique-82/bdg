package fr.legrain.document.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import fr.legrain.document.RechercheDocument.editor.FormEditorInputRechercheDocument;
import fr.legrain.document.RechercheDocument.editor.FormEditorRechercheDocument;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaListeCreationDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestCom.librairiesEcran.workbench.lgrFormEditor;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.SortieChampsEvent;
import fr.legrain.lib.gui.SortieChampsListener;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaDocListeCreationController extends JPABaseControllerSWTStandard 
  implements RetourEcranListener,SortieChampsListener {

	static Logger logger = Logger.getLogger(PaDocListeCreationController.class.getName());	
	private PaListeCreationDoc vue = null; // vue
	
	public static final String C_COMMAND_CREER_ID = "fr.legrain.gestionCommerciale.document.creer";
	private HandlerCreer handlerCreer = new HandlerCreer();
	
	public static final String C_COMMAND_LISTER_ID = "fr.legrain.gestionCommerciale.document.lister";
	private HandlerLister handlerLister = new HandlerLister();
	
	TypeDoc typeDocument = TypeDoc.getInstance();
	
	public PaDocListeCreationController(PaListeCreationDoc vue) {
		this(vue,null);
	}
	
	public PaDocListeCreationController(PaListeCreationDoc vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		this.vue = vue;
	
		initMapComposantChamps();
		initMapComposantDecoratedField();
		listeComponentFocusableSWT(listeComposantFocusable);
		initFocusOrder();
		initActions();
		initDeplacementSaisie(listeComposantFocusable);

		branchementBouton();
		

	}
	
	@Override
	protected void initEtatBouton() {

	}

	protected class HandlerLister extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				lister();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected class HandlerCreer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				creer();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	@Override
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_CREER_ID, handlerCreer);
		mapCommand.put(C_COMMAND_LISTER_ID, handlerLister);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getBtnCreerFacture(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerAcompte(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerApporteur(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerAvisEcheance(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerAvoir(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerBonCde(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerBonLiv(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerDevis(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerPrelevement(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerProforma(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerReglement(), C_COMMAND_CREER_ID);
		mapActions.put(vue.getBtnCreerRemise(), C_COMMAND_CREER_ID);
		
		mapActions.put(vue.getBtnListerAcompte(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerApporteur(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerAvisEcheance(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerAvoir(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerBonCde(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerBonLiv(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerDevis(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerFacture(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerPrelevement(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerProforma(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerReglement(), C_COMMAND_LISTER_ID);
		mapActions.put(vue.getBtnListerRemise(), C_COMMAND_LISTER_ID);
		
		vue.getBtnCreerAcompte().setToolTipText(TaAcompte.TYPE_DOC);
		vue.getBtnCreerApporteur().setToolTipText(TaApporteur.TYPE_DOC);
		vue.getBtnCreerAvisEcheance().setToolTipText(TaAvisEcheance.TYPE_DOC);
		vue.getBtnCreerAvoir().setToolTipText(TaAvoir.TYPE_DOC);
		vue.getBtnCreerBonCde().setToolTipText(TaBoncde.TYPE_DOC);
		vue.getBtnCreerBonLiv().setToolTipText(TaBonliv.TYPE_DOC);
		vue.getBtnCreerDevis().setToolTipText(TaDevis.TYPE_DOC);
		vue.getBtnCreerFacture().setToolTipText(TaFacture.TYPE_DOC);
		vue.getBtnCreerPrelevement().setToolTipText(TaPrelevement.TYPE_DOC);
		vue.getBtnCreerProforma().setToolTipText(TaProforma.TYPE_DOC);
		vue.getBtnCreerReglement().setToolTipText(TaReglement.TYPE_DOC);
		vue.getBtnCreerRemise().setToolTipText(TaRemise.TYPE_DOC);
	
		vue.getBtnListerApporteur().setToolTipText(TaApporteur.TYPE_DOC);
		vue.getBtnListerAcompte().setToolTipText(TaAcompte.TYPE_DOC);
		vue.getBtnListerAvisEcheance().setToolTipText(TaAvisEcheance.TYPE_DOC);
		vue.getBtnListerAvoir().setToolTipText(TaAvoir.TYPE_DOC);
		vue.getBtnListerBonCde().setToolTipText(TaBoncde.TYPE_DOC);
		vue.getBtnListerBonLiv().setToolTipText(TaBonliv.TYPE_DOC);
		vue.getBtnListerDevis().setToolTipText(TaDevis.TYPE_DOC);
		vue.getBtnListerFacture().setToolTipText(TaFacture.TYPE_DOC);
		vue.getBtnListerPrelevement().setToolTipText(TaPrelevement.TYPE_DOC);
		vue.getBtnListerProforma().setToolTipText(TaProforma.TYPE_DOC);
		vue.getBtnListerReglement().setToolTipText(TaReglement.TYPE_DOC);
		vue.getBtnListerRemise().setToolTipText(TaRemise.TYPE_DOC);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}	
	
	private void creer() {
		try {
//			int typeDoc = vue.getfComboTypeDoc().getSelectionIndex();
			String typeDoc="";
			if(getFocusCourantSWT() instanceof Button)
				typeDoc=((Button)getFocusCourantSWT()).getToolTipText();
			String idEditor = null;
			idEditor= typeDocument.getEditorDoc().get(typeDoc);

			IEditorInput editorInput = new IEditorInput() {

				public boolean exists() {
					// TODO Auto-generated method stub
					return false;
				}

				public ImageDescriptor getImageDescriptor() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getName() {
					// TODO Auto-generated method stub
					return "";
				}

				public IPersistableElement getPersistable() {
					// TODO Auto-generated method stub
					return null;
				}

				public String getToolTipText() {
					// TODO Auto-generated method stub
					return "";
				}

				public Object getAdapter(Class adapter) {
					// TODO Auto-generated method stub
					return null;
				}

			};

			if(idEditor!=null) {
				IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
				if(editor==null) {
					IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(editorInput, idEditor);
					LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

					ParamAffiche paramAffiche = new ParamAffiche();				
					paramAffiche.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
				}

			}
			
		} catch (PartInitException e1) {
			logger.error("",e1);
		}
	}
	
	private void lister() {
		try {
//			int typeDoc = vue.getfComboTypeDoc().getSelectionIndex();
			String typeDoc="";
			if(getFocusCourantSWT() instanceof Button)
				typeDoc=((Button)getFocusCourantSWT()).getToolTipText();
			String idEditor = null;
			idEditor= typeDocument.getEditorDoc().get(typeDoc);

			IEditorInput editorInput = null;
			

				idEditor=FormEditorRechercheDocument.ID;
				editorInput=new FormEditorInputRechercheDocument(typeDoc);

				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				ParamAffiche paramAffiche = new ParamAffiche();
				paramAffiche.setCodeDocument(typeDoc);
				try {
					IEditorPart editor = lgrFormEditor.verifEditeurOuvert(idEditor);
					if(editor==null){
						IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
								editorInput, idEditor
					);						
					} else {
						((FormEditorInputRechercheDocument)editor.getEditorInput()).setTypeDoc(typeDoc);
						page.activate(editor);
					}
				} catch (Exception e) {
					logger.error("Erreur pendant l'ouverture de l'éditeur ",e);
				}
			
		} catch (Exception e1) {
			logger.error("",e1);
		}
	}
	
	protected void initImageBouton() {
		if(vue instanceof PaListeCreationDoc) {
			
			((PaListeCreationDoc)vue).getLabelAcompte().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelApporteur().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelAvisEcheance().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelAvoir().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelBonCde().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelBonLiv().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelDevis().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelFacture().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelPrelevement().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelProforma().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelReglement().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			((PaListeCreationDoc)vue).getLabelRemise().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_DEVIS));
			
			((PaListeCreationDoc)vue).getBtnCreerAcompte().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerApporteur().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerAvisEcheance().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerAvoir().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerBonCde().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerBonLiv().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerDevis().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerFacture().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerPrelevement().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerProforma().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerReglement().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaListeCreationDoc)vue).getBtnCreerRemise().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			
			((PaListeCreationDoc)vue).getBtnListerAcompte().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerApporteur().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerAvisEcheance().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerAvoir().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerBonCde().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerBonLiv().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerDevis().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerFacture().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerPrelevement().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerProforma().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerReglement().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			((PaListeCreationDoc)vue).getBtnListerRemise().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));

			
			vue.layout(true);
		}
	}

	
	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		if (mapComposantChamps == null) {
			mapComposantChamps = new HashMap<Control,String>(); 

			mapComposantChamps.put(vue.getBtnCreerFacture(), null);
			mapComposantChamps.put(vue.getBtnListerFacture(), null);
			mapComposantChamps.put(vue.getBtnCreerDevis(), null);
			mapComposantChamps.put(vue.getBtnListerDevis(), null);
			mapComposantChamps.put(vue.getBtnCreerBonLiv(), null);
			mapComposantChamps.put(vue.getBtnListerBonLiv(), null);
			mapComposantChamps.put(vue.getBtnCreerBonCde(), null);
			mapComposantChamps.put(vue.getBtnListerBonCde(), null);
			mapComposantChamps.put(vue.getBtnCreerAvoir(), null);
			mapComposantChamps.put(vue.getBtnListerAvoir(), null);
			mapComposantChamps.put(vue.getBtnCreerAcompte(), null);
			mapComposantChamps.put(vue.getBtnListerAcompte(), null);
			mapComposantChamps.put(vue.getBtnCreerProforma(), null);
			mapComposantChamps.put(vue.getBtnListerProforma(), null);
			mapComposantChamps.put(vue.getBtnCreerApporteur(), null);
			mapComposantChamps.put(vue.getBtnListerApporteur(), null);
			mapComposantChamps.put(vue.getBtnCreerPrelevement(), null);
			mapComposantChamps.put(vue.getBtnListerPrelevement(), null);
			mapComposantChamps.put(vue.getBtnCreerReglement(), null);
			mapComposantChamps.put(vue.getBtnListerReglement(), null);
			mapComposantChamps.put(vue.getBtnCreerRemise(), null);
			mapComposantChamps.put(vue.getBtnListerRemise(), null);
			mapComposantChamps.put(vue.getBtnCreerAvisEcheance(), null);
			mapComposantChamps.put(vue.getBtnListerAvisEcheance(), null);

			for (Control c : mapComposantChamps.keySet()) {
				listeComposantFocusable.add(c);
			}
		}
		
		if(mapInitFocus == null) 
			mapInitFocus = new HashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getBtnCreerFacture());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getBtnCreerFacture());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getBtnCreerFacture());
	}
	
	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
	//	param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		vue.getBtnCreerFacture().setFocus();
		return null;
	}

	@Override
	public Composite getVue() {
		return vue;
	}

	public void retourEcran(RetourEcranEvent evt) {
		
	}

	@Override
	protected void actInserer() throws Exception {
	}

	@Override
	protected void actEnregistrer() throws Exception {}

	@Override
	protected void actModifier() throws Exception {
	}

	@Override
	protected void actSupprimer() throws Exception {}

	@Override
	protected void actFermer() throws Exception {
		closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		actFermer();
	}

	@Override
	protected void actImprimer() throws Exception {}

	@Override
	protected void actAide() throws Exception {}

	@Override
	protected void initComposantsVue() throws ExceptLgr {
	}

	@Override
	public void initEtatComposant() {
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		return false;
	}

	@Override
	protected void actRefresh() throws Exception {
	}

	@Override
	protected void initMapComposantDecoratedField() {
	}

	@Override
	protected void sortieChamps() {
	}

	@Override
	protected void actAide(String message) throws Exception {
	}



	public void sortieChamps(SortieChampsEvent evt) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
