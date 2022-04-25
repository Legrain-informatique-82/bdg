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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaCreationDoc;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.SortieChampsEvent;
import fr.legrain.lib.gui.SortieChampsListener;

public class PaCreationDocController extends JPABaseControllerSWTStandard 
  implements RetourEcranListener,SortieChampsListener {

	static Logger logger = Logger.getLogger(PaCreationDocController.class.getName());	
	private PaCreationDoc vue = null; // vue
	
	public static final String C_COMMAND_CREER_ID = "fr.legrain.gestionCommerciale.document.creer";
	private HandlerCreer handlerCreer = new HandlerCreer();
	
	TypeDoc typeDocument = TypeDoc.getInstance();
	
	public PaCreationDocController(PaCreationDoc vue) {
		this(vue,null);
	}
	
	public PaCreationDocController(PaCreationDoc vue,EntityManager em) {
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
		
		//ComboSousListe n'est pas utilise pour l'instant
		vue.getComboSousListe().setVisible(false);
		vue.getLaSousListe().setVisible(false);

		try {

			String[] liste= new String[typeDocument.getTypeDocCompletPresent().size()];
			int i = 0;
			for (String libelle : typeDocument.getTypeDocCompletPresent().values()) {
				liste[i]=libelle;
				i++;
			}
			vue.getComboTypeDoc().setItems(liste);
			vue.getComboTypeDoc().select(0);
			vue.getComboTypeDoc().setEditable(false);
			vue.getComboTypeDoc().setVisibleItemCount(vue.getComboTypeDoc().getItemCount());
			
			initEtatBouton();
			vue.getComboTypeDoc().addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}

				public void widgetSelected(SelectionEvent e) {
					initEtatBouton();
				}
				
			});
			
		} catch(Exception e) {
			logger.error("",e);
		}

	}
	
	@Override
	protected void initEtatBouton() {
		if(vue.getComboTypeDoc().getSelectionIndex()==-1) {
			vue.getBtnCreer().setEnabled(false);
		} else {
			vue.getBtnCreer().setEnabled(true);
		}
	}



	protected class HandlerCreer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				valider();
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
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getBtnCreer(), C_COMMAND_CREER_ID);

		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}	
	
	private void valider() {
		try {
			int typeDoc = vue.getComboTypeDoc().getSelectionIndex();
			
			String idEditor = null;
			idEditor= typeDocument.getEditorDoc().get(vue.getComboTypeDoc().getItem(typeDoc));

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
	
	protected void initImageBouton() {
		if(vue instanceof PaCreationDoc) {
			((PaCreationDoc)vue).getBtnCreer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			vue.layout(true);
		}
	}

	
	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		if (mapComposantChamps == null) {
			mapComposantChamps = new HashMap<Control,String>(); 

			mapComposantChamps.put(vue.getComboTypeDoc(), null);
//			mapComposantChamps.put(vue.getComboSousListe(), null);
			mapComposantChamps.put(vue.getBtnCreer(), null);

//			listeComposantFocusable.add(vue.getGrille());
			for (Control c : mapComposantChamps.keySet()) {
				listeComposantFocusable.add(c);
			}
		}
		
		if(mapInitFocus == null) 
			mapInitFocus = new HashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getComboTypeDoc());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getComboTypeDoc());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getComboTypeDoc());
	}
	
	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
	//	param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		vue.getBtnCreer().setFocus();
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
