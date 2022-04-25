package fr.legrain.document.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;

import fr.legrain.document.divers.DeclencheChangementEtatEvent;
import fr.legrain.document.divers.IDeclencheChangementEtatListener;
import fr.legrain.document.divers.ParamAfficheListeExporte;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.divers.ViewerSupportLocal;
import fr.legrain.document.ecran.PaSelectionLigneDocumentCochable;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDeclencheCommandeControllerListener;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;


public class PaSelectionLigneDocumentCochableControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener{
	protected static Logger logger = Logger.getLogger(PaSelectionLigneDocumentCochableControlleur.class.getName());	

	protected PaSelectionLigneDocumentCochable  vue = null;
	protected IObservableValue selectedLigneDocument;
	
	protected IDocumentTiers document = null;


	private Object ecranAppelant = null;
	protected Realm realm;
	protected DataBindingContext dbc;
	protected MapChangeListener changeListener = new MapChangeListener();
	protected Class classModel = IHMEnteteDocument.class;
	protected String nomClass = this.getClass().getSimpleName();
	protected String finDeLigne = "\r\n";
	protected String separateur = ";";
	protected Boolean ImpressionUniquement=false; 

	
//	private IDocumentTiers  masterEntity = null;
	protected List<IDocumentTiers> masterListEntity = null;
	protected IDocumentTiersDAO masterDAO = null;
	protected List<IHMEnteteDocument> modelDocument = null;
	
	public static final String C_COMMAND_DOCUMENT_REINITIALISER_ID = "fr.legrain.Document.reinitialiser";
	protected HandlerInverser handlerInverser = new HandlerInverser();

	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_ID = "fr.legrain.Publipostage.toutCocher";
	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_ID = "fr.legrain.Publipostage.toutDeCocher";
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();
	
	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IDocumentTiers objet : masterListEntity) {
					objet.setExport(LibConversion.booleanToInt(!LibConversion.intToBoolean(objet.getExport())));							
				}
				actRefresh();
				actModifier();
				} catch (Exception e) {
					logger.error("", e);
				}
				return event;
			}
		}
	
	private class HandlerToutCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IDocumentTiers objet : masterListEntity) {
					objet.setExport(1);							
				}
				actRefresh();
				actModifier();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerToutDeCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IDocumentTiers objet : masterListEntity) {
					objet.setExport(0);							
				}
				actRefresh();
				actModifier();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	public PaSelectionLigneDocumentCochableControlleur(PaSelectionLigneDocumentCochable vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		setVue(vue);
		masterDAO=new TaFactureDAO(getEm());
		setDaoStandard(((AbstractLgrDAO)masterDAO));
		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();

	}

	public PaSelectionLigneDocumentCochableControlleur(PaSelectionLigneDocumentCochable vue) {
		this(vue,null);
	}

	
	private void initController()	{
		try {	
			
			setDaoStandard((AbstractLgrDAO)masterDAO);
			initVue();			
			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] {
					popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getParent().setMenu(popupMenuFormulaire);
			initEtatBouton(true);
			
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}

	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		initVerifyListener(mapInfosVerifSaisie, (AbstractLgrDAO)masterDAO);
	}
	private void initVue() {
	}
	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		return false;
	}
	@Override
	protected void actAide(String message) throws Exception {
	}



	@Override
	protected void actAnnuler() throws Exception {
		DeclencheCommandeControllerEvent e = null; 
			e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_ANNULER_ID);
		fireDeclencheCommandeController(e);
			e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_IMPRIMER_ID);
		fireDeclencheCommandeController(e);	
		
	}

	@Override
	protected void actEnregistrer() throws Exception {
		try {
			ctrlTousLesChampsAvantEnregistrementSWT();
			
//			if(MessageDialog.openQuestion(vue.getShell(), "Enregistrer les modifications", "Etes-vous sur de vouloir enregistrer ces modifications ?")){
			document.setExport(LibConversion.booleanToInt(((IHMEnteteDocument)selectedLigneDocument.getValue()).getExport()));
			((AbstractLgrDAO)masterDAO).getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_ENREGISTRER_ID);
			fireDeclencheCommandeController(e);
			initEtatBouton(false);
//			}			
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	protected void actFermer() throws Exception {

	}

	@Override
	protected void actImprimer() throws Exception {
		
	}

	

	
	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actModifier() throws Exception {
		((AbstractLgrDAO)masterDAO).getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
		daoStandard.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
		initEtatBouton(false);
		DeclencheChangementEtatEvent e = new DeclencheChangementEtatEvent(
				this,ModeObjet.EnumModeObjet.C_MO_EDITION);
		fireDeclencheChangementEtat(e);
	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de ligne pour l'IHM, a partir de la liste des lignes contenue dans l'entite facture.
	 * @return
	 */
	public List<IHMEnteteDocument> IHMmodel() {
		LinkedList<IDocumentTiers> ldao = new LinkedList<IDocumentTiers>();
		LinkedList<IHMEnteteDocument> lswt = new LinkedList<IHMEnteteDocument>();

		
		if(masterListEntity!=null ){
				//&& !masterListEntity.isEmpty()) {

			ldao.addAll(masterListEntity);
			for (IDocumentTiers document : ldao) {
				IHMEnteteDocument ihm = new IHMEnteteDocument();
				ihm.setExport(LibConversion.intToBoolean(document.getExport()));
				ihm.setCodeDocument(document.getCodeDocument());
				ihm.setDateDocument(document.getDateDocument());
				ihm.setLibelleDocument(document.getLibelleDocument());
				if(document.getTaTiers()!=null)//si remise alors =null
					ihm.setCodeTiers(document.getTaTiers().getCodeTiers());
				lswt.add(ihm);
			}
		}
		return lswt;
	}
	
	@Override
	protected void actRefresh() throws Exception {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{	
			String codeActuel="";
			if(document!=null)
				codeActuel =document.getCodeDocument();				
			WritableList writableListFacture = new WritableList(IHMmodel(), IHMEnteteDocument.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(codeActuel!="") {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT
						, codeActuel)),true);
			}
			changementDeSelection();
			initEtatBouton(true);

		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	public void bind(PaSelectionLigneDocumentCochable paSelectionLigneRelance){
		try {
			vue.getPaFomulaire().setVisible(false);
			
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));//
			String[] titreColonnes =new String[] {"Document exporté","Tiers","Date","libellé","Export"};
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"130","100","80","300","0"},0);
			String[] listeChamp = new String[] {"codeDocument","codeTiers","dateDocument","libelleDocument","export"};//
			getTableViewerStandard().setListeChamp(listeChamp);
			getTableViewerStandard().tri(classModel, listeChamp,titreColonnes);
			modelDocument=IHMmodel();

			ViewerSupportLocal.bind(getTableViewerStandard(), 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);
			getTableViewerStandard().addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					try {
						Object objet=recherche(Const.C_CODE_DOCUMENT
								, ((IHMEnteteDocument)event.getElement()).getCodeDocument());
						getImpressionUniquement();
						StructuredSelection selection =new StructuredSelection(objet);
						getTableViewerStandard().setSelection(selection,true);
						if(selectedLigneDocument.getValue()!=null){
							actModifier();
							((IHMEnteteDocument)selectedLigneDocument.getValue()).setExport(event.getChecked());
							validateUIField(Const.C_EXPORT,event.getChecked());
							document.setExport(LibConversion.booleanToInt(((IHMEnteteDocument)selectedLigneDocument.getValue()).getExport()));
						}

					} catch (Exception e) {
						logger.error("", e);
					}		
				}
			});	
		
		getTableViewerStandard().setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMEnteteDocument)element).getExport())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMEnteteDocument)element).getExport())
					return true;
				return false;
			}
		});

		vue.getGrille().addMouseListener(
				new MouseAdapter() {

					public void mouseDoubleClick(MouseEvent e) {
						String idEditor = TypeDoc.getInstance().getEditorDoc()
								.get(document.getTypeDocument());
						String valeurIdentifiant = ((IHMEnteteDocument) selectedLigneDocument
								.getValue()).getCodeDocument();
						ouvreDocument(valeurIdentifiant, idEditor);
					}

				});
		dbc = new DataBindingContext(realm);

		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		setDbcStandard(dbc);
		selectedLigneDocument = ViewersObservables.observeSingleSelection(getTableViewerStandard());
		bindingFormMaitreDetail(dbc, realm, selectedLigneDocument,classModel);
		selectedLigneDocument.addChangeListener(new IChangeListener() {

			public void handleChange(ChangeEvent event) {
				changementDeSelection();
			}

		});


		changementDeSelection();
		initEtatBouton(true);

	} catch(Exception e) {
		logger.error("",e);
		vue.getLaMessage().setText(e.getMessage());
	}
}

private void changementDeSelection() {
	try {
		if(selectedLigneDocument==null ||selectedLigneDocument.getValue()==null)
			getTableViewerStandard().selectionGrille(0);
		if(selectedLigneDocument!=null && selectedLigneDocument.getValue()!=null){
			//if(((AbstractLgrDAO)masterDAO).getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				for (IDocumentTiers ligne : masterListEntity) {
					if(ligne.getCodeDocument().equals(((IHMEnteteDocument) selectedLigneDocument.getValue()).getCodeDocument()))
						document =ligne;	
				}
//			}
		}
		initEtatBouton(true);
	} catch (Exception e) {
		logger.error("",e );
	}
}

	@Override
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

	
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerToutCocher);//tout cocher
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerToutDeCocher);//tout décocher
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInverser);// Inverser
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);//envoyer dans l'autre grille
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();


		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnEnregister(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
		initEtatBouton(true);
	}

	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();

		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnInserer());//Inverser		
		listeComposantFocusable.add(vue.getBtnModifier());//tout cocher
		listeComposantFocusable.add(vue.getBtnEnregister());//Envoyer dans l'autre grille
		listeComposantFocusable.add(vue.getBtnSupprimer());//tout Décocher
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		vue.getBtnImprimer().setVisible(false);
		vue.getBtnFermer().setVisible(false);
		
		vue.getBtnModifier().setText("tout cocher");
		vue.getBtnSupprimer().setText("tout Décocher");
		vue.getBtnInserer().setText("Inverser les cochés");		
		

		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getGrille());

		activeModifytListener();

	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param!=null){
			Map<String,String[]> map = ((AbstractLgrDAO)masterDAO).getParamWhereSQL();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else param.setFocusDefautSWT(vue.getGrille());



			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(param instanceof ParamAfficheListeExporte){
				setImpressionUniquement(((ParamAfficheListeExporte)param).getEnregistreDirect());
			}
		}	

		bind(vue);
		initSashFormWeight();
			getTableViewerStandard().selectionGrille(0);
//			getTableViewerStandard().tri(classModel,nomClass,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			initEtatBouton(true);
			return null;
	}

	@Override
	public PaSelectionLigneDocumentCochable getVue() {
		// TODO Auto-generated method stub
		return vue;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub

	}

	public void setVue(PaSelectionLigneDocumentCochable vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.layout(true);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = true;
		switch (((AbstractLgrDAO)masterDAO).getModeObjet().getMode()) {
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (vue.getGrille()!=null)vue.getGrille().setEnabled(true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if (initFocus)
			initFocusSWT(((AbstractLgrDAO)masterDAO), mapInitFocus);	
		
	}



	
	public IStatus validateUI() throws Exception {
		if ((((AbstractLgrDAO)masterDAO).getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (((AbstractLgrDAO)masterDAO).getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "FACTURE";
		try {
			IStatus s = null;
			if (nomChamp.equals(Const.C_CODE_DOCUMENT) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_CODE_TIERS) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_EXPORT) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}

			if (s.getSeverity() != IStatus.ERROR) {
			}
			//			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		List<IHMEnteteDocument> model=IHMmodel();
		while(!trouve && i<model.size()){
			try {
				if(PropertyUtils.getProperty(model.get(i), propertyName).equals(value)) {
					trouve = true;
				} else {
					i++;
				}
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}

		if(trouve)
			return model.get(i);
		else 
			return null;

	}

	protected void initSashFormWeight() {
		int premierComposite = 30;
		int secondComposite = 70;
//		if(!getEnregistreDirect()){
//			premierComposite = 0;
//			secondComposite = 100;
//		}
		vue.getCompositeForm().setWeights(new int[]{premierComposite,secondComposite});
	}
	
	public Boolean getImpressionUniquement() {
		return ImpressionUniquement;
	}

	public void setImpressionUniquement(Boolean enregistreDirect) {
		this.ImpressionUniquement = enregistreDirect;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());		
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}			
		} else if (evt.getRetour() != null){

		}
		super.retourEcran(evt);
	}

	public List<IDocumentTiers> getMasterListEntity() {
		return masterListEntity;
	}

	public void setMasterListEntity(List<IDocumentTiers> masterListEntity) {
		this.masterListEntity = masterListEntity;
	}

	public IDocumentTiersDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(IDocumentTiersDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	public IDocumentTiers getDocument() {
		return document;
	}

	public void setDocument(IDocumentTiers document) {
		this.document = document;
	}

	public void addDeclencheChangementEtatListener(IDeclencheChangementEtatListener l) {
		listenerList.add(IDeclencheChangementEtatListener.class, l);
	}
	
	public void removeDeclencheChangementEtatListener(IDeclencheChangementEtatListener l) {
		listenerList.remove(IDeclencheChangementEtatListener.class, l);
	}
	
	protected void fireDeclencheChangementEtat(DeclencheChangementEtatEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IDeclencheChangementEtatListener.class) {
				if (e == null)
					e = new DeclencheChangementEtatEvent(this);
				( (IDeclencheChangementEtatListener) listeners[i + 1]).declencheChangementEtat(e);
			}
		}
	}
}
