/**
 * ClientController.java
 */
package fr.legrain.abonnement.controllers.finalisationAbonnement;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.pluginAbonnement;
import fr.legrain.abonnement.controllers.LigneLabelProvider;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.abonnement.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.abonnement.ecrans.PaFormPageAbonnement;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaTAbonnementDAO;
import fr.legrain.documents.dao.LigneDocumentSelection;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMiniEditable;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.grille.EditionGrille;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.licence.editors.SupportAbonMultiPageEditor;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * @author nicolas
 *
 */
public class LigneFinalisationAbonnementController extends AbstractControllerMiniEditable {

	static Logger logger = Logger.getLogger(LigneFinalisationAbonnementController.class.getName());	

	private Class objetIHM = null;
//	private TaLFactureDAO taLFactureDAO = null;
	private TaAbonnementDAO taAbonnementDAO = null;
	private TaAbonnement taAbonnement=null;
	private LigneDocumentSelection taLDoc = null;
	protected List<DocumentSelectionIHM> modelLDocument = null;
	protected List<LigneDocumentSelection> listeLDoc;
	protected List<LigneDocumentSelection> listeLEcheance = new LinkedList<LigneDocumentSelection>();
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipalFinalisation masterController = null;
	protected PaFormPageAbonnement vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewer;
	IObservableValue selection;
//	private LgrTableViewer tableViewerDetail;
	protected Boolean suppressionUniquement=false; 
	
	private MapperLigneDocumentSelectionIHMDocumentSelection mapper = new MapperLigneDocumentSelectionIHMDocumentSelection();
	private String libelleEcran = "";
	
	private BigDecimal totalHT = new BigDecimal(0);
	private BigDecimal totalTTC = new BigDecimal(0);
	
	public static final String C_COMMAND_DOCUMENT_REINITIALISER_ID = "fr.legrain.Document.reinitialiser";
	protected HandlerInverser handlerInverser = new HandlerInverser();

	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_ID = "fr.legrain.Publipostage.toutCocher";
	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_ID = "fr.legrain.Publipostage.toutDeCocher";
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();


	
	/* Constructeur */
	public LigneFinalisationAbonnementController(FormPageControllerPrincipalFinalisation masterContoller, PaFormPageAbonnement vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (DocumentSelectionIHM objet : modelLDocument) {
					objet.setAccepte(!objet.getAccepte());							
				}
				actRefresh();
				} catch (Exception e) {
					logger.error("", e);
				}
				return event;
			}
		}
	
	private class HandlerToutCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (DocumentSelectionIHM objet :modelLDocument) {
					objet.setAccepte(true);													
				}
				actRefresh();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerToutDeCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
//				for (IHMLRemise objet : IHMmodel()) {
				for (DocumentSelectionIHM objet : modelLDocument) {
					objet.setAccepte(false);
				}
				actRefresh();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	

	
	
	public void initController() {
		try {
			setGrille(vue.getCompositeSectionResultats().getCompoEcran().getGrille());
			taAbonnementDAO=new TaAbonnementDAO();
			initSashFormWeight();
			setTableViewerStandard(tableViewer);

			initMapComposantChamps();
//			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);




			initEtatBouton(true);
		} catch (Exception e) {
			logger.error("Erreur : PaTiersController", e);
		}
	}

	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHMNouveau(int nbResult) {
		
		// Initialisation des éléments à afficher à l'écran
		int numLigne=0;
		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
//		taAbonnementDAO = new TaAbonnementDAO(masterController.getMasterDAOEM());

		taAbonnementDAO = new TaAbonnementDAO(masterController.getMasterDAOEM());

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLDoc = rechercheLigneAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTAbonnement().get(masterController.paramControllerMini.getCodeEtat()),"%",masterController.paramControllerMini.isReglee());
		} else {
			listeLDoc = rechercheLigneAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTAbonnement().get(masterController.paramControllerMini.getCodeEtat()),
					vue.getCompositeSectionParam().getTfCodeTiers().getText(),masterController.paramControllerMini.isReglee());
		}
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<LigneDocumentSelection> listDocumentSelection = new LinkedList<LigneDocumentSelection>();
		int qte=0;
		LigneDocumentSelection object = null;
		for (int i = 0; i < listeLDoc.size() /*&& i < nbResult*/; i ++){
			Integer nb=1;
			object = listeLDoc.get(i);
			while(nb>0){
				object.setAccepte(true);
				if(object.getQte()!=null)qte=object.getQte().intValue();
				object.setDebAbonnement(LibDate.incrementDate(object.getFinAbonnement(), 1, 0, 0));
				object.setFinAbonnement(LibDate.incrementDate(object.getFinAbonnement(), 0, qte, 0));
				listDocumentSelection.add(object);
				nb--;
			}

		}

		modelLDocument = new MapperLigneDocumentSelectionIHMDocumentSelection().listeEntityToDto(listDocumentSelection);
	
//		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(modelLDocument.size()));
	}

	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHMExistant(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		int numLigne=0;
		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
//		taAbonnementDAO = new TaAbonnementDAO(masterController.getMasterDAOEM());

		taAbonnementDAO = new TaAbonnementDAO(masterController.getMasterDAOEM());

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLDoc = rechercheLigneAbonnementExistantes(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTAbonnement().get(masterController.paramControllerMini.getCodeEtat()),"%",masterController.paramControllerMini.isReglee());
		} else {
			listeLDoc = rechercheLigneAbonnementExistantes(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTAbonnement().get(masterController.paramControllerMini.getCodeEtat()),
					vue.getCompositeSectionParam().getTfCodeTiers().getText(),masterController.paramControllerMini.isReglee());
		}
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<LigneDocumentSelection> listDocumentSelection = new LinkedList<LigneDocumentSelection>();
		int qte=0;
		LigneDocumentSelection object = null;
		for (int i = 0; i < listeLDoc.size() /*&& i < nbResult*/; i ++){
			Integer nb=1;
			object = listeLDoc.get(i);
			while(nb>0){
				object.setAccepte(true);
//				if(object.getQte()!=null)qte=object.getQte().intValue();
//				object.setDebAbonnement(LibDate.incrementDate(object.getFinAbonnement(), 1, 0, 0));
//				object.setFinAbonnement(LibDate.incrementDate(object.getFinAbonnement(), 0, qte, 0));
				listDocumentSelection.add(object);
				nb--;
			}

		}

		modelLDocument = new MapperLigneDocumentSelectionIHMDocumentSelection().listeEntityToDto(listDocumentSelection);
	
//		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(modelLDocument.size()));
	}
	
	
	private void initTotaux(){
		BigDecimal total = new BigDecimal(0);
		Integer nbCoche=0;
		for (DocumentSelectionIHM obj : modelLDocument) {
			if(obj.getAccepte()){
				nbCoche++;
			}
		}
		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(nbCoche));
	}
	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM(int nbResult) {
		if(suppressionUniquement)initialiseModelIHMExistant(nbResult);
		else initialiseModelIHMNouveau(nbResult);
	}
	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;


	protected void initActions() {
		if(!toolBarInitialise) {
			vue.getCompositeSectionResultats().getSectionToolbar().add(editonAction);
			vue.getCompositeSectionResultats().getSectionToolbar().update(true);

			toolBarInitialise = true;
		}
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionResultats().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionResultats().getTable().getSelection()[0].getText(
							getTableViewer().findPositionNomChamp("codeTiers")
							);
					String idEditor = SupportAbonMultiPageEditor.ID_EDITOR;
					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}//FormEditorAbonnement.ID

			});
			mapCommand = new HashMap<String, IHandler>();


			mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
			mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerToutCocher);//tout cocher
			
			mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInverser);// Inverser
			if(!suppressionUniquement){
				mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerToutDeCocher);//tout décocher
				mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
			}else{
				mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);//tout décocher
				mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerToutDeCocher);
			}
			
			mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
			mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
			mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
			mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

			mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
			mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

			initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

			if (mapActions == null)
				mapActions = new LinkedHashMap<Button, Object>();


			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcran().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

			Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
			mapActions.put(null, tabActionsAutres);
			
			branchementBouton();
			evenementInitialise = true;
		}
	}
	protected void initImageBouton() {
		vue.getCompositeSectionResultats().getCompoEcran().getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getCompositeSectionResultats().getCompoEcran().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getCompositeSectionResultats().getCompoEcran().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getCompositeSectionResultats().getCompoEcran().getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getCompositeSectionResultats().getCompoEcran().getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		if(!suppressionUniquement){
			vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setText("tout Décocher");
		}else{
			vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setText("Supprimer");
			vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister().setText("tout Décocher");
		}

		vue.getCompositeSectionResultats().getCompoEcran().getBtnModifier().setText("tout cocher");
		//		vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setText("tout Décocher");
		vue.getCompositeSectionResultats().getCompoEcran().getBtnInserer().setText("Inverser les cochés");
	}
	@Override
	protected void initMapComposantChamps() {

				if (mapComposantChamps == null) 
				mapComposantChamps = new LinkedHashMap<Control,String>();
		
				
			if (listeComposantFocusable == null) 
				listeComposantFocusable = new ArrayList<Control>();

			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnInserer());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnModifier());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnFermer());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnAnnuler());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcran().getBtnImprimer());
		initImageBouton();
	}

	protected void initEtatBouton(boolean initFocus) {
		boolean trouve =modelLDocument.size()>0;
		enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID, trouve);//Inverser les cochés
		enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);//Tout cocher
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,  trouve);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, trouve);
		enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);	
		
	}

	@Override
	public void bind(){
//		if(mapComposantChamps==null) {
//			initMapComposantChamps();
//		}
		if(modelLDocument==null)modelLDocument= new ArrayList<DocumentSelectionIHM>(0);
		setObjetIHM(DocumentSelectionIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Type Abonnement","document","libellé Ligne","date Document","code Tiers","nom Tiers","code Article","unité","qté"
				,"debut abonnement","fin abonnement","support"};
		// Taille des colonnes
		String [] tailleColonnes = {"100","80","300","80","80","100","150","50","80","80","80","100"};
		// Id relatives dans la classe associée
		
		String[] idColonnesTemp = {"codeTAbonnement","code","libelleLigne","dateDocument","codeTiers","nomTiers","codeArticle","unite","qte"
				,"debAbonnement","finAbonnement","codeSupport"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionResultats().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionResultats().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		tableViewer.selectionGrille(0);
		selection = ViewersObservables.observeSingleSelection(tableViewer);
		
		LigneLabelProvider.bind(tableViewer, new WritableList(modelLDocument, DocumentSelectionIHM.class),
				BeanProperties.values(idColonnes));

		//TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[1]);
//		tableViewer.getTable().getColumns()[12].setResizable(false);
		tableViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((DocumentSelectionIHM)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((DocumentSelectionIHM)element).getAccepte())
					return true;
				return false;
			}
		});
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkAccept(((DocumentSelectionIHM)event.getElement()).getIdLDocument(),event.getChecked());
				
			}
		});
		initController();
		initTotaux();
	}
	public void checkAccept(Integer idDocument,boolean check){
		try {
			Object objet=recherche(idDocument);
			StructuredSelection selectionloc =new StructuredSelection(objet);
			tableViewer.setSelection(selectionloc,true);
			if(selection.getValue()!=null){
				((DocumentSelectionIHM)selection.getValue()).setAccepte(check);
				Object entity=rechercheEntity(idDocument);
				((LigneDocumentSelection)entity).setAccepte(check);
				initTotaux();
			}
		} catch (Exception e) {
			logger.error("", e);
		}		
	}
	
	public Object recherche( Integer value) {
	boolean trouve = false;
	int i = 0;
	List<DocumentSelectionIHM> model=modelLDocument;
	while(!trouve && i<model.size()){
		try {
			if(PropertyUtils.getProperty(model.get(i), Const.C_ID_L_DOCUMENT)==value) {
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
	
	public LigneDocumentSelection rechercheEntity( Integer value) {
		boolean trouve = false;
		int i = 0;
		LigneDocumentSelection objet;
		List<LigneDocumentSelection> model=listeLEcheance;
		while(!trouve && i<model.size()){
			try {
				objet=model.get(i);
				if(objet!=null && objet.getIdLDocument()==(value)){
					trouve = true;
				} else {
					i++;
				}
			} catch (Exception e) {
				logger.error("",e);
			}
		}

		if(trouve)
			return model.get(i);
		else 
			return null;

	}
	
	
//	public void bindDetail() {
//		String [] titreColonnes = {"Code article","Libelle","Qté","Unité","Tarif","Montant HT","Montant TTC"};
//		String [] tailleColonnes =  {"100","300","100","100","100","100","100"};
//		String[] idColonnesTemp = {"code","libelle","qte","unite","tarif","montantHT","montantTTC"};
//		int[] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};
//
//		// Création de l'élément graphique du tableau et affichage à l'écran
//		if(!toolBarInitialise) {
//			tableViewerDetail = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTableDetail());
//			tableViewerDetail.createTableCol(vue.getCompositeSectionTableauGauche().getTableDetail(),titreColonnes,tailleColonnes,1,alignement);
//			tableViewerDetail.setListeChamp(idColonnesTemp);
//		}
//
//		tableViewer.selectionGrille(0);
//		IObservableValue selection = ViewersObservables.observeSingleSelection(tableViewer);
//
//		IObservableList tmp = BeansObservables.observeDetailList(selection,"lignes", DocumentSelectionIHM.class);
//		ViewerSupport.bind(tableViewerDetail, tmp, BeanProperties.values(DocumentSelectionIHM.class, idColonnesTemp));
//	}

//	public void changeEtat(String v) {
//		DocumentSelectionIHM doc = ((DocumentSelectionIHM)((IStructuredSelection) getTableViewer().getSelection()).getFirstElement());
//		//String codeNouvelEtat = masterController.paramControllerMini.getMapEtatCodeLibelle().get(doc.etat);
//		String codeNouvelEtat = masterController.paramControllerMini.getMapEtatCodeLibelle().get(v);
//
//		taLFactureDAO = new TaLFactureDAO(masterController.getMasterDAOEM());
//		TaEtatDAO taEtatDAO = new TaEtatDAO(masterController.getMasterDAOEM());
//
//		TaDevis taDevis =  taLFactureDAO.findByCode(doc.code);
//		TaEtat taEtat = null;
//
//		if(codeNouvelEtat!=null)
//			taEtat = taEtatDAO.findByCode(codeNouvelEtat);
//
//		EntityTransaction transaction = taLFactureDAO.getEntityManager().getTransaction();
//		try {			
//			taDevis.setTaEtat(taEtat);
//			taLFactureDAO.begin(transaction);
//
//			//if ((taLFactureDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
//			taLFactureDAO.enregistrerMerge(taDevis);
//			//} 
//			taLFactureDAO.commit(transaction);
//			transaction = null;
//
//		} catch (RollbackException e) {	
//			logger.error("",e);
//			//if(e.getCause() instanceof OptimisticLockException)
//			//	MessageDialog.openError(vue.getShell(), "", e.getMessage()+"\n"+e.getCause().getMessage());
//		} catch (Exception e) {
//			logger.error("",e);
//		} finally {
//			if(transaction!=null && transaction.isActive()) {
//				transaction.rollback();
//			}
//		}
//
//	}

	//public final class ExampleEditingSupport extends EditingSupport {
	public final class ExampleEditingSupport extends ObservableValueEditingSupport {

		private ComboBoxViewerCellEditor cellEditor = null;

		private ExampleEditingSupport(ColumnViewer viewer) {
			super(viewer, new DataBindingContext());
			//super(viewer);
			cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
			cellEditor.setLabelProvider(new LabelProvider());
			cellEditor.setContenProvider(new ArrayContentProvider());
			cellEditor.setInput(new WritableList(masterController.paramControllerMini.getMapTAbonnement().keySet(),String.class));

			cellEditor.addListener(new ICellEditorListener() {

				@Override
				public void applyEditorValue() {
					String v = cellEditor.getValue().toString();
					//changeEtat(v);
				}

				@Override
				public void cancelEditor() {
				}

				@Override
				public void editorValueChanged(boolean oldValidState, boolean newValidState) {					
				}

			});
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		//	    @Override
		//	    protected Object getValue(Object element) {
		//	        if (element instanceof DocumentSelectionIHM) {
		//	        	DocumentSelectionIHM data = (DocumentSelectionIHM)element;
		//	            return data.getEtat();
		//	        }
		//	        return null;
		//	    }
		//	     
		//	    @Override
		//	    protected void setValue(Object element, Object value) {
		//	        if (element instanceof DocumentSelectionIHM && value instanceof String) {
		//	        	DocumentSelectionIHM data = (DocumentSelectionIHM) element;
		//	            String newValue = (String) value;
		//	            /* only set new value if it differs from old one */
		//	            if (!data.getEtat().equals(newValue)) {
		//	                data.setEtat(newValue);
		//	                changeEtat();
		//	            }
		//	        }
		//	    }

		@Override
		protected IObservableValue doCreateCellEditorObservable(
				CellEditor cellEditor) {
			return ViewersObservables.observeSingleSelection(this.cellEditor.getViewer());
			//return null;
		}

		@Override
		protected IObservableValue doCreateElementObservable(Object element,
				ViewerCell cell) {
			return BeansObservables.observeValue(element, "etat"); 
			//return null;
		}

	}

//	public ToolTipDetail addToolTipDetailTiers(Control t, TaTiers tiers) {
//		/*
//		ToolTipDetail myTooltipLabel = addToolTipDetailCompte(t,r.getDetail());
//		myTooltipLabel.setHeaderText("Détail - "+nomChamp);
//		myTooltipLabel.setShift(new Point(-5, -5));
//		myTooltipLabel.setHideOnMouseDown(false);
//		//myTooltipLabel.setPopupDelay(4000);
//		myTooltipLabel.activate();
//		 */
//		ToolTipDetail myTooltipLabel = new ToolTipDetail(t) {
//
//			protected Composite createContentArea(Composite parent) {
//				Composite comp = super.createContentArea(parent);
//				comp.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
//				//FillLayout layout = new FillLayout();
//				//layout.marginWidth=5;
//				//comp.setLayout(layout);
//				GridLayout gl = new GridLayout(1,false);
//				GridData layout = new GridData(SWT.FILL,SWT.FILL,true,false);
//				layout.heightHint=200;
//				comp.setLayout(gl);
//				comp.setLayoutData(layout);
//
//
//				Table table = new Table(comp, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
//				table.setHeaderVisible(true);
//				table.setLinesVisible(true);
//				table.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
//
//				TableColumn colonne1 = new TableColumn(table, SWT.LEFT);
//				colonne1.setText("Compte");
//				TableColumn colonne2 = new TableColumn(table, SWT.RIGHT);
//				colonne2.setText("Débit");
//				TableColumn colonne3 = new TableColumn(table, SWT.RIGHT);
//				colonne3.setText("Crédit");
//
//				/*
//				for (Compte cpt : detail) {
//					TableItem ligne = new TableItem(table,SWT.NONE);
//					if(cpt.getMtDebit()!=null) 
//						ligne.setText(new String[]{cpt.getNumero(),cpt.getMtDebit().toString(),""});
//					else if(cpt.getMtCredit()!=null) 
//						ligne.setText(new String[]{cpt.getNumero(),"",cpt.getMtCredit().toString()});
//					else
//						ligne.setText(new String[]{cpt.getNumero(),"",""});
//				}
//				 */
//
//				int marge = 10;
//				colonne1.pack();
//				colonne2.pack();
//				colonne3.pack();
//				colonne1.setWidth(colonne1.getWidth()+marge);
//				colonne2.setWidth(colonne2.getWidth()+marge);
//				colonne3.setWidth(colonne3.getWidth()+marge);
//				//table.pack();
//
//				return comp;
//			}
//		};
//		return myTooltipLabel;
//	}

//	private static class FancyToolTipSupport extends ColumnViewerToolTipSupport {
//
//		private TaTiersDAO dao = null;
//
//		protected FancyToolTipSupport(ColumnViewer viewer, int style,
//				boolean manualActivation) {
//			super(viewer, style, manualActivation);
//		}
//
//
//		protected Composite createToolTipContentArea(Event event,
//				Composite parent) {
//
//			if(dao==null)
//				dao = new TaTiersDAO();
//
//			final TaTiers tiers = dao.findByCode(getText(event));
//			Composite comp = new Composite(parent,SWT.NONE);
//
//			if(tiers!=null) {
//				comp.setSize(500, 500);
//				GridLayout l = new GridLayout(1,false);
//				l.horizontalSpacing=0;
//				l.marginWidth=0;
//				l.marginHeight=0;
//				l.verticalSpacing=0;
//
//				comp.setLayout(l);
//				Label label = null;
//				String texte = null;
//				GridData gd = null;
//
//				Button b = new Button(comp,SWT.PUSH);
//				//b.setText("Ouvrir fiche tiers");
//				b.setToolTipText("Ouvrir fiche tiers");
//				b.addSelectionListener(new SelectionListener() {
//
//					@Override
//					public void widgetSelected(SelectionEvent e) {
//						try{
//							if(tiers.getIdTiers()!=0){
//								AbstractLgrMultiPageEditor.ouvreFiche(String.valueOf(tiers.getIdTiers()),null, SupportAbonMultiPageEditor.ID_EDITOR,null,false);
//							}
//						}catch (Exception ex) {
//							logger.error("",ex);
//						}			
//					}
//
//					@Override
//					public void widgetDefaultSelected(SelectionEvent e) {
//						widgetSelected(e);	
//					}
//				});
//				b.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS));
//				gd = new GridData();
//				gd.horizontalAlignment = SWT.END;
//				b.setLayoutData(gd);
//
//				texte = "";
//				if(tiers.getTaTEntite()!=null && tiers.getTaTEntite().getCodeTEntite()!=null) {
//					texte+=tiers.getTaTEntite().getCodeTEntite()+" ";
//				}
//				if(tiers.getTaEntreprise()!=null && tiers.getTaEntreprise().getNomEntreprise()!=null) {
//					texte+=tiers.getTaEntreprise().getNomEntreprise();
//				}
//				label = new Label(comp,SWT.NONE); label.setText(texte);
//
//
//				texte = "";
//				if(tiers.getTaTCivilite()!=null && tiers.getTaTCivilite().getCodeTCivilite()!=null) {
//					texte+=tiers.getTaTCivilite().getCodeTCivilite()+" ";
//
//				}
//				if(tiers.getNomTiers()!=null) {
//					texte+=tiers.getNomTiers()+" ";
//				}
//				if(tiers.getPrenomTiers()!=null) {
//					texte+=tiers.getPrenomTiers();
//
//				}
//				label = new Label(comp,SWT.NONE); label.setText(texte);
//
//				if(tiers.getTaTelephone()!=null && tiers.getTaTelephone().getNumeroTelephone()!=null) {
//					label = new Label(comp,SWT.NONE); label.setText(tiers.getTaTelephone().getNumeroTelephone());
//				}
//				if(tiers.getTaEmail()!=null && tiers.getTaEmail().getAdresseEmail()!=null) {
//					label = new Label(comp,SWT.NONE); label.setText(tiers.getTaEmail().getAdresseEmail());
//				}
//				if(tiers.getTaWeb()!=null && tiers.getTaWeb().getAdresseWeb()!=null) {
//					label = new Label(comp,SWT.NONE); label.setText(tiers.getTaWeb().getAdresseWeb());
//				}
//
//				if(tiers.getTaCompl()!=null && tiers.getTaCompl().getTvaIComCompl()!=null) {
//					label = new Label(comp,SWT.NONE); label.setText(tiers.getTaCompl().getTvaIComCompl());
//				}
//
//				if(tiers.getTaCompl()!=null && tiers.getTaCompl().getSiretCompl()!=null) {
//					label = new Label(comp,SWT.NONE); label.setText(tiers.getTaCompl().getSiretCompl());
//				}
//
//				//Browser browser = new Browser(comp,SWT.BORDER);
//				//browser.setText(tiers.getCodeTiers()+"<br>"+
//				//			tiers.getNomTiers()+"<br>"+
//				//			tiers.getPrenomTiers()+"<br>"
//				//		);
//				//browser.setLayoutData(new GridData(200,150));
//			}
//
//			return comp;
//		}
//
//		public boolean isHideOnMouseDown() {
//			return false;
//		}
//
//		public static final void enableFor(ColumnViewer viewer, int style) {
//			new FancyToolTipSupport(viewer,style,false);
//		}
//	}

//	public class LigneDocumentSelection {
//		private String code = null;
//		private String libelleLigne = null;
//		private String codeTiers = null;
//		private String nomTiers = null;
//		private String codeArticle = null;
//		private Date dateDocument = null;
//		private String codeTSupport = null;
//		private Integer idLDocument = 0;
//		//private List<LigneSelection> lignes = null;
//
//		public LigneDocumentSelection(String code, String libelleLigne, String codeTiers,
//				String nomTiers, Date dateDocument,String codeArticle,
//				String codeTSupport,Integer idLDocument) {
//			super();
//			this.code = code;
//			this.libelleLigne = libelleLigne;
//			this.codeTiers = codeTiers;
//			this.nomTiers = nomTiers;
//			this.dateDocument = dateDocument;
//			this.codeArticle = codeArticle;
//			this.codeTSupport = codeTSupport;
//			this.idLDocument = idLDocument;
//		}
//
//		public String getCode() {
//			return code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getLibelleLigne() {
//			return libelleLigne;
//		}
//		public void setLibelleLigne(String libelleLigne) {
//			this.libelleLigne = libelleLigne;
//		}
//		public String getCodeTiers() {
//			return codeTiers;
//		}
//		public void setCodeTiers(String codeTiers) {
//			this.codeTiers = codeTiers;
//		}
//		public String getNomTiers() {
//			return nomTiers;
//		}
//		public void setNomTiers(String nomTiers) {
//			this.nomTiers = nomTiers;
//		}
//		public Date getDateDocument() {
//			return dateDocument;
//		}
//		public void setDateDocument(Date dateDocument) {
//			this.dateDocument = dateDocument;
//		}
//		public String getCodeArticle() {
//			return codeArticle;
//		}
//
//		public void setCodeArticle(String codeArticle) {
//			this.codeArticle = codeArticle;
//		}
//
//		public String getCodeTSupport() {
//			return codeTSupport;
//		}
//
//		public void setCodeTSupport(String codeTSupport) {
//			this.codeTSupport = codeTSupport;
//		}
//
//		public Integer getIdLDocument() {
//			return idLDocument;
//		}
//
//		public void setIdLDocument(Integer idLDocument) {
//			this.idLDocument = idLDocument;
//		}
//
////		public List<LigneSelection> getLignes() {
////			return lignes;
////		}
////
////		public void setLignes(List<LigneSelection> lignes) {
////			this.lignes = lignes;
////		}
//
//
//
//	}

	/* ------------------- Affichage Section Clients ------------------- */


//	/**
//	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
//	 * @author nicolas²
//	 *
//	 */
//	public class DocumentSelectionIHM extends ModelObject {
//		private String code = null;
//		private String libelleLigne = null;
//		private String codeTiers = null;
//		private String nomTiers = null;
//		private Date dateDocument = null;
//		private String codeArticle = null;
//		private String codeTSupport = null;
//		private Integer idLDocument = null;
////		private List<LigneSelectionIHM> lignes = null;
//
//		public DocumentSelectionIHM(String code, String libelleLigne,
//				String codeTiers, String nomTiers, Date dateDocument,
//				String codeArticle, String codeTSupport, Integer idLDocument) {
//			super();
//			this.code = code;
//			this.libelleLigne = libelleLigne;
//			this.codeTiers = codeTiers;
//			this.nomTiers = nomTiers;
//			this.dateDocument = dateDocument;
//			this.codeArticle = codeArticle;
//			this.codeTSupport = codeTSupport;
//			this.idLDocument = idLDocument;
//		}
//
//		public String getCode() {
//			return code;
//		}
//
//		public void setCode(String code) {
//			firePropertyChange("code", this.code, this.code = code);
//		}
//
//		public String getLibelleLigne() {
//			return libelleLigne;
//		}
//
//		public void setLibelleLigne(String libelleLigne) {
//			firePropertyChange("libelleLigne", this.libelleLigne, this.libelleLigne = libelleLigne);
//		}
//
//
//		public String getCodeTiers() {
//			return codeTiers;
//		}
//
//		public void setCodeTiers(String codeTiers) {
//			firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
//		}
//
//		public String getNomTiers() {
//			return nomTiers;
//		}
//
//		public void setNomTiers(String nomTiers) {
//			firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = nomTiers);
//		}
//
//		public Date getDateDocument() {
//			return dateDocument;
//		}
//
//		public void setDateDocument(Date dateDocument) {
//			firePropertyChange("dateDocument", this.dateDocument, this.dateDocument = dateDocument);
//		}
//
//
//		public String getCodeArticle() {
//			return codeArticle;
//		}
//
//		public void setCodeArticle(String codeArticle) {
//			firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = codeArticle);
//		}
//
//		public String getCodeTSupport() {
//			return codeTSupport;
//		}
//
//		public void setCodeTSupport(String codeTSupport) {
//			firePropertyChange("codeTSupport", this.codeTSupport, this.codeTSupport = codeTSupport);
//		}
//
//		public Integer getIdLDocument() {
//			return idLDocument;
//		}
//
//		public void setIdLDocument(Integer idLDocument) {
//			firePropertyChange("idLDocument", this.idLDocument, this.idLDocument = idLDocument);
//		}
//
//	}

	public class MapperIHMDocumentSelectionVersTaAbonnement implements IlgrMapper<DocumentSelectionIHM, TaAbonnement> {
	
		public DocumentSelectionIHM entityToDto(TaAbonnement e) {
			// TODO Auto-generated method stub
			return null;
		}

		public TaAbonnement dtoToEntity(DocumentSelectionIHM e) {
			TaArticleDAO daoArticle = new TaArticleDAO();
			TaLFactureDAO daoLFacture = new TaLFactureDAO();
			TaSupportAbonDAO daoSupport = new TaSupportAbonDAO();
			TaTAbonnementDAO daoTAbonnement = new TaTAbonnementDAO();
			TaTiersDAO daoTiers = new TaTiersDAO();

			TaAbonnement abon = new TaAbonnement();
			abon.setDateDebut(e.getDebAbonnement());
			abon.setDateFin(e.getFinAbonnement());
//			abon.setIdAbonnement(e.getIdAbonnement());
			if(e.getCodeArticle()!=null){
				abon.setTaArticle(daoArticle.findByCode(e.getCodeArticle()));
			}
			if(e.getIdLDocument()!=null){
				abon.setTaLDocument(daoLFacture.findById(e.getIdLDocument()));
			}		
			if(e.getCodeSupport()!=null){
				abon.setTaSupportAbon(daoSupport.findByCode(e.getCodeSupport()));
			}		
			if(e.getCodeTAbonnement()!=null){
				abon.setTaTAbonnement(daoTAbonnement.findByCode(e.getCodeTAbonnement()));
			}	
			if(e.getCodeTiers()!=null){
				abon.setTaTiers(daoTiers.findByCode(e.getCodeTiers()));
			}		
			return abon;
		}
		
	}
	
	public class MapperLigneDocumentSelectionIHMDocumentSelection implements IlgrMapper<DocumentSelectionIHM, LigneDocumentSelection> {

		public List<DocumentSelectionIHM> listeEntityToDto(LinkedList<LigneDocumentSelection> l) {
			List<DocumentSelectionIHM> res = new ArrayList<DocumentSelectionIHM>(0);
			for (LigneDocumentSelection document : l) {
				res.add(entityToDto(document));
			}
			return res;
		}

		public DocumentSelectionIHM entityToDto(LigneDocumentSelection e) {
			DocumentSelectionIHM documentSelectionIHM = new DocumentSelectionIHM(
					e.getCode(),e.getLibelleLigne(),e.getCodeTiers(),e.getNomTiers(),e.getDateDocument(),
					e.getCodeArticle(),e.getCodeTAbonnement(),e.getIdLDocument(),e.getUnite(),e.getQte(),
					e.getDebAbonnement(),e.getFinAbonnement(),e.getIdAbonnement(),e.getCodeSupport(),e.getAccepte(),e.getEtat());

			return documentSelectionIHM;
		}

		@Override
		public LigneDocumentSelection dtoToEntity(DocumentSelectionIHM e) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public LgrTableViewer getTableViewer() {
		return tableViewer;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action editonAction = new Action("Edition",pluginAbonnement.getImageDescriptor(PaCompositeSectionDocEcheance.iconpath)) { 
		@Override 
		public void run() {
			EditionGrille edition = new EditionGrille(vue.getForm().getShell());
			edition.print(vue.getCompositeSectionResultats().getTable());
		}
	};

	public void print() {

	}

	public BigDecimal getTotalHT() {
		return totalHT;
	}

	public BigDecimal getTotalTTC() {
		return totalTTC;
	}


	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = taAbonnementDAO.getEntityManager().getTransaction();
		try {
			try {
				TaComptePointDAO daoComptePoint=new TaComptePointDAO(taAbonnementDAO.getEntityManager());
				TaSupportAbon support = null;
				taAbonnementDAO.begin(transaction);
				TaArticleDAO daoArticle = new TaArticleDAO();
				MapperIHMDocumentSelectionVersTaAbonnement mapperihmTaAbonnement = new MapperIHMDocumentSelectionVersTaAbonnement();
				//enregistrer les lignes d'échéance
				for (DocumentSelectionIHM objet : modelLDocument) {
					if(objet.getAccepte()){
						taAbonnement = new TaAbonnement();
						taAbonnement=mapperihmTaAbonnement.dtoToEntity(objet);
						support=taAbonnement.getTaSupportAbon();
						if(taAbonnement.getVersionObj()==null)taAbonnement.setVersionObj(0);
						if(taAbonnement.getEtat()==null)taAbonnement.setEtat(0);
						taAbonnement=taAbonnementDAO.enregistrerMerge(taAbonnement);
						daoComptePoint.calculPointTotalAnnee(taAbonnementDAO.getEntityManager(), taAbonnement,true);
					}

				}
				taAbonnementDAO.commit(transaction);
				//calculer les points bonus suite à renouvellement
				
				vue.getControllerPage().raz(true);
				initEtatBouton(true);

				transaction = null;

			}
			catch (Exception e) {
				logger.error("",e);
				throw e;
			}	
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
		
		


			


	@Override
	protected void actModifier() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = taAbonnementDAO.getEntityManager().getTransaction();
		try {
			try {
				taAbonnementDAO.begin(transaction);

				//enregistrer les lignes d'échéance
				for (DocumentSelectionIHM objet : modelLDocument) {
					if(objet.getAccepte()){
						taAbonnement =taAbonnementDAO.findById(objet.getIdAbonnement());
						if(taAbonnement.getEtat()==1){
							MessageDialog.openWarning(PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(), "Suppression impossible", "vous ne pouvez pas supprimer cet abonnement.");
						}else
						 taAbonnementDAO.supprimer(taAbonnement);						
					}
				}
				
				taAbonnementDAO.commit(transaction);

				vue.getControllerPage().raz(true);
				initEtatBouton(true);

				transaction = null;

			}
			catch (Exception e) {
				logger.error("",e);
				throw e;
			}	
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}


	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actImprimer() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actRefresh() throws Exception {
		try{	
			Integer idActuel=0;
			if(taLDoc!=null)
				idActuel =taLDoc.getIdLDocument();				
			WritableList writableListFacture = new WritableList(modelLDocument, DocumentSelectionIHM.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(idActuel!=null && writableListFacture.size()>0) {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_ID_L_DOCUMENT
						, idActuel)),true);
			}
			initTotaux();
//			changementDeSelection();
			initEtatBouton(true);
			vue.getCompositeSectionResultats().getCompoEcran().getGrille().forceFocus();
		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
		}
	}
	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		List<DocumentSelectionIHM> model=modelLDocument;
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


	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Composite getVue() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getSuppressionUniquement() {
		return suppressionUniquement;
	}

	public void setSuppressionUniquement(Boolean suppressionUniquement) {
		this.suppressionUniquement = suppressionUniquement;
	}


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas²
	 *
	 */
	public class DocumentSelectionIHM extends ModelObject {
		private String code = null;
		private String libelleLigne = null;
		private String codeTiers = null;
		private String nomTiers = null;
		private Date dateDocument = null;
		private String codeArticle = null;
		private String codeTAbonnement = null;
		private Integer idLDocument = null;
		private String unite=null;
		private BigDecimal qte=null;
		private Date debAbonnement=null;
		private Date finAbonnement=null;
		private Integer idAbonnement=null;
		private String codeSupport=null;
		private Boolean accepte=true;
		private String codeEtat=null;


		public DocumentSelectionIHM(String code, String libelleLigne,
				String codeTiers, String nomTiers, Date dateDocument,
				String codeArticle, String codeTAbonnement,
				Integer idLDocument, String unite, BigDecimal qte,
				Date debAbonnement, Date finAbonnement, Integer idAbonnement,
				String codeSupport, Boolean accepte, String etat) {
			super();
			this.code = code;
			this.libelleLigne = libelleLigne;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.dateDocument = dateDocument;
			this.codeArticle = codeArticle;
			this.codeTAbonnement = codeTAbonnement;
			this.idLDocument = idLDocument;
			this.unite = unite;
			this.qte = qte;
			this.debAbonnement = debAbonnement;
			this.finAbonnement = finAbonnement;
			this.idAbonnement = idAbonnement;
			this.codeSupport = codeSupport;
			this.accepte = accepte;
			this.codeEtat = etat;
		}


		public String getCode() {
			return code;
		}


		public void setCode(String code) {
			firePropertyChange("code", this.code, this.code = code);
		}

		public String getLibelleLigne() {
			return libelleLigne;
		}

		public void setLibelleLigne(String libelleLigne) {
			firePropertyChange("libelleLigne", this.libelleLigne, this.libelleLigne = libelleLigne);
		}


		public String getCodeTiers() {
			return codeTiers;
		}

		public void setCodeTiers(String codeTiers) {
			firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
		}

		public String getNomTiers() {
			return nomTiers;
		}

		public void setNomTiers(String nomTiers) {
			firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = nomTiers);
		}

		public Date getDateDocument() {
			return dateDocument;
		}

		public void setDateDocument(Date dateDocument) {
			firePropertyChange("dateDocument", this.dateDocument, this.dateDocument = dateDocument);
		}


		public String getCodeArticle() {
			return codeArticle;
		}

		public void setCodeArticle(String codeArticle) {
			firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = codeArticle);
		}

		public String getCodeTAbonnement() {
			return codeTAbonnement;
		}

		public void setCodeTAbonnement(String codeTSupport) {
			firePropertyChange("codeTSupport", this.codeTAbonnement, this.codeTAbonnement = codeTSupport);
		}

		public Integer getIdLDocument() {
			return idLDocument;
		}

		public void setIdLDocument(Integer idLDocument) {
			firePropertyChange("idLDocument", this.idLDocument, this.idLDocument = idLDocument);
		}


		public String getUnite() {
			return unite;
		}


		public void setUnite(String unite) {
			firePropertyChange("unite", this.unite, this.unite = unite);
		}


		public BigDecimal getQte() {
			return qte;
		}


		public void setQte(BigDecimal qte) {
			firePropertyChange("qte", this.qte, this.qte = qte);
		}


		public Date getDebAbonnement() {
			return debAbonnement;
		}


		public void setDebAbonnement(Date debAbonnement) {
			firePropertyChange("debAbonnement", this.debAbonnement, this.debAbonnement = debAbonnement);
		}


		public Date getFinAbonnement() {
			return finAbonnement;
		}


		public void setFinAbonnement(Date finAbonnement) {
			firePropertyChange("finAbonnement", this.finAbonnement, this.finAbonnement = finAbonnement);
		}


		public Boolean getAccepte() {
			return accepte;
		}


		public void setAccepte(Boolean accepte) {
			firePropertyChange("accepte", this.accepte, this.accepte = accepte);
		}


		public Integer getIdAbonnement() {
			return idAbonnement;
		}


		public void setIdAbonnement(Integer idAbonnement) {
			firePropertyChange("idAbonnement", this.idAbonnement, this.idAbonnement = idAbonnement);
		}


		public String getCodeSupport() {
			return codeSupport;
		}


		public void setCodeSupport(String codeSupport) {
			firePropertyChange("codeSupport", this.codeSupport, this.codeSupport = codeSupport);
		}


		public String getCodeEtat() {
			return codeEtat;
		}


		public void setCodeEtat(String codeEtat) {
			firePropertyChange("codeEtat", this.codeEtat, this.codeEtat = codeEtat);
		}
	
	}
	
//	public class LigneDocumentSelection {
//		private String code = null;
//		private String libelleLigne = null;
//		private String codeTiers = null;
//		private String nomTiers = null;
//		private String codeArticle = null;
//		private Date dateDocument = null;
//		private String codeTAbonnement = null;
//		private Integer idLDocument = 0;
//		private String unite=null;
//		private BigDecimal qte=null;
//		private Date debAbonnement=null;
//		private Date finAbonnement=null;
//		private Integer idAbonnement=null;
//		private Boolean accepte=true;
//
//
//
//
//		public LigneDocumentSelection(String code, String libelleLigne,
//				String codeTiers, String nomTiers, String codeArticle,
//				Date dateDocument, String codeTAbonnement, Integer idLDocument,
//				String unite, BigDecimal qte, Date debAbonnement,
//				Date finAbonnement, Integer idAbonnement) {
//			super();
//			this.code = code;
//			this.libelleLigne = libelleLigne;
//			this.codeTiers = codeTiers;
//			this.nomTiers = nomTiers;
//			this.codeArticle = codeArticle;
//			this.dateDocument = dateDocument;
//			this.codeTAbonnement = codeTAbonnement;
//			this.idLDocument = idLDocument;
//			this.unite = unite;
//			this.qte = qte;
//			this.debAbonnement = debAbonnement;
//			this.finAbonnement = finAbonnement;
//			this.idAbonnement = idAbonnement;
//		}
//
//
//		public String getCode() {
//			return code;
//		}
//		
//
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getLibelleLigne() {
//			return libelleLigne;
//		}
//		public void setLibelleLigne(String libelleLigne) {
//			this.libelleLigne = libelleLigne;
//		}
//		public String getCodeTiers() {
//			return codeTiers;
//		}
//		public void setCodeTiers(String codeTiers) {
//			this.codeTiers = codeTiers;
//		}
//		public String getNomTiers() {
//			return nomTiers;
//		}
//		public void setNomTiers(String nomTiers) {
//			this.nomTiers = nomTiers;
//		}
//		public Date getDateDocument() {
//			return dateDocument;
//		}
//		public void setDateDocument(Date dateDocument) {
//			this.dateDocument = dateDocument;
//		}
//		public String getCodeArticle() {
//			return codeArticle;
//		}
//
//		public void setCodeArticle(String codeArticle) {
//			this.codeArticle = codeArticle;
//		}
//
//		public String getCodeTAbonnement() {
//			return codeTAbonnement;
//		}
//
//		public void setCodeTAbonnement(String codeTSupport) {
//			this.codeTAbonnement = codeTSupport;
//		}
//
//		public Integer getIdLDocument() {
//			return idLDocument;
//		}
//
//		public void setIdLDocument(Integer idLDocument) {
//			this.idLDocument = idLDocument;
//		}
//
//
//		public String getUnite() {
//			return unite;
//		}
//
//
//		public void setUnite(String unite) {
//			this.unite = unite;
//		}
//
//
//		public BigDecimal getQte() {
//			return qte;
//		}
//
//
//		public void setQte(BigDecimal qte) {
//			this.qte = qte;
//		}
//
//
//		public Date getDebAbonnement() {
//			return debAbonnement;
//		}
//
//
//		public void setDebAbonnement(Date debAbonnement) {
//			this.debAbonnement = debAbonnement;
//		}
//
//
//		public Date getFinAbonnement() {
//			return finAbonnement;
//		}
//
//
//		public void setFinAbonnement(Date finAbonnement) {
//			this.finAbonnement = finAbonnement;
//		}
//
//
//		public Boolean getAccepte() {
//			return accepte;
//		}
//
//
//		public void setAccepte(Boolean accepte) {
//			this.accepte = accepte;
//		}
//
//
//		public Integer getIdAbonnement() {
//			return idAbonnement;
//		}
//
//
//		public void setIdAbonnement(Integer idAbonnement) {
//			this.idAbonnement = idAbonnement;
//		}		
//
//	}
	public List<LigneDocumentSelection> rechercheLigneAbonnement(Date dateDeb, Date datefin, String codeAbonnement, String codeTiers , boolean reglee) {
		vue.getForm().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		List<LigneDocumentSelection> result = new LinkedList<LigneDocumentSelection>();
		List<LigneDocumentSelection> temp = null;
		temp=rechercheLigneAbonnementLieAAvisEcheance(dateDeb, datefin, codeAbonnement, codeTiers, reglee);
		boolean accept=true;
		for (LigneDocumentSelection ligneDocumentSelection : temp) {
			accept=true;
			for (LigneDocumentSelection ligne : result) {
				if(accept)
				accept=!ligne.getCodeSupport().equals(ligneDocumentSelection.getCodeSupport()) &&
						!ligne.getIdLDocument().equals(ligneDocumentSelection.getIdLDocument());
					
			}
			if(accept)result.add(ligneDocumentSelection);
		}
		temp=rechercheLigneAbonnementLieAPrelevement(dateDeb, datefin, codeAbonnement, codeTiers, reglee);
		accept=true;
		for (LigneDocumentSelection ligneDocumentSelection : temp) {
			accept=true;
			for (LigneDocumentSelection ligne : result) {
				if(accept)
				accept=!ligne.getCodeSupport().equals(ligneDocumentSelection.getCodeSupport()) &&
						!ligne.getIdLDocument().equals(ligneDocumentSelection.getIdLDocument());
					
			}
			if(accept)result.add(ligneDocumentSelection);
		}
		vue.getForm().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		return result;
	}

		
	public List<LigneDocumentSelection> rechercheLigneAbonnementLieAAvisEcheance(Date dateDeb, Date datefin, String codeAbonnement, String codeTiers , boolean reglee) {
		List<LigneDocumentSelection> result = new LinkedList<LigneDocumentSelection>();
		List<LigneDocumentSelection> temp = null;
		Query query = null;
		String requete="";
		if(codeAbonnement==null || codeAbonnement.toLowerCase().equals("tous")) {
			codeAbonnement="%";
		}
		requete="select NEW fr.legrain.documents.dao.LigneDocumentSelection(f.codeDocument,lf.libLDocument,t.codeTiers,t.nomTiers,a.codeArticle," +
				" f.dateDocument,tab.codeTAbonnement,lf.idLDocument," +
				" lf.u1LDocument,lf.qteLDocument,ab.dateDebut,ab.dateFin,ab.idAbonnement,sa.codeSupportAbon )" +
				" from TaLFacture lf join lf.taArticle a join lf.taDocument f join f.taTiers t " +
				" join f.taRDocuments rd join rd.taAvisEcheance av join rd.taFacture f2  join av.lignes lav join lav.taLEcheance  le join le.taAbonnement ab" +
				" join ab.taSupportAbon sa join ab.taTAbonnement tab" +
				" where f.dateDocument between  cast('"+LibDate.dateToStringSql(dateDeb)+"'as date) and cast('"+LibDate.dateToStringSql(datefin)+"' as date)"+
				" and le.taArticle = lf.taArticle" +
				" and t.codeTiers like '"+codeTiers+"' and f.idDocument=f2.idDocument " +
//				" and a.codeArticle = ab.taArticle.codeArticle " +
				" and exists(select rt from TaRTSupport rt join rt.taArticleAbonnement aa join rt.taTAbonnement tab2 where tab2.idTAbonnement=tab.idTAbonnement " +
				" and aa.codeArticle=a.codeArticle) " +
				" and not exists(select s from TaAbonnement s join s.taLDocument ld where ld.idLDocument=lf.idLDocument) " +
				" and tab.codeTAbonnement like '"+codeAbonnement+"' " +
				" and sa.inactif<>1 "				
				;
		if(reglee)requete+=	" and f.netAPayer<=(select sum(rr.affectation)from TaRReglement rr where rr.taFacture=f)" ;
		requete+=" order by f.codeDocument,a.codeArticle";

			query = taAbonnementDAO.getEntityManager().createQuery(requete);
//			query.setParameter(1, dateDeb,TemporalType.DATE);
//			query.setParameter(2, datefin,TemporalType.DATE);
//			query.setParameter(3, codeTiers);
//			query.setParameter(4, codeAbonnement);
//			
//			
//			query.setParameter(5, dateDeb,TemporalType.DATE);
//			query.setParameter(6, datefin,TemporalType.DATE);
//			query.setParameter(7, codeTiers);
//			query.setParameter(8, codeAbonnement);			

		boolean accept=true;
		temp = query.getResultList();
		for (LigneDocumentSelection ligneDocumentSelection : temp) {
			accept=true;
			for (LigneDocumentSelection ligne : result) {
				if(accept)
				accept=!ligne.getCodeSupport().equals(ligneDocumentSelection.getCodeSupport()) &&
						!ligne.getIdLDocument().equals(ligneDocumentSelection.getIdLDocument());
					
			}
			if(accept)result.add(ligneDocumentSelection);
		}
		return result;
	}
	
	public List<LigneDocumentSelection> rechercheLigneAbonnementLieAPrelevement(Date dateDeb, Date datefin, String codeAbonnement, String codeTiers , boolean reglee) {
		List<LigneDocumentSelection> result = new LinkedList<LigneDocumentSelection>();
		List<LigneDocumentSelection> temp = null;
		Query query = null;
		String requete="";
		if(codeAbonnement==null || codeAbonnement.toLowerCase().equals("tous")) {
			codeAbonnement="%";
		}
		
		requete="select NEW fr.legrain.documents.dao.LigneDocumentSelection(f.codeDocument,lf.libLDocument,t.codeTiers,t.nomTiers,a.codeArticle," +
		" f.dateDocument,tab.codeTAbonnement,lf.idLDocument," +
		" lf.u1LDocument,lf.qteLDocument,ab.dateDebut,ab.dateFin,ab.idAbonnement,sa.codeSupportAbon )" +
		" from TaLFacture lf join lf.taArticle a join lf.taDocument f join f.taTiers t " +
		" join f.taRDocuments rd join rd.taPrelevement pl join pl.taRDocuments rd2 join rd2.taAvisEcheance av join rd.taFacture f2 join av.lignes lav " +
		" join lav.taLEcheance  le join le.taAbonnement ab" +
		" join ab.taSupportAbon sa join ab.taTAbonnement tab" +
		" where f.dateDocument between  cast('"+LibDate.dateToStringSql(dateDeb)+"'as date) and cast('"+LibDate.dateToStringSql(datefin)+"' as date)"+
		" and le.taArticle = lf.taArticle" +
		" and t.codeTiers like '"+codeTiers+"' and f.idDocument=f2.idDocument " +
//		" and a.codeArticle = ab.taArticle.codeArticle " +
		" and exists(select rt from TaRTSupport rt join rt.taArticleAbonnement aa join rt.taTAbonnement tab2 where tab2.idTAbonnement=tab.idTAbonnement " +
		" and aa.codeArticle=a.codeArticle) " +
		" and not exists(select s from TaAbonnement s join s.taLDocument ld where ld.idLDocument=lf.idLDocument) " +
		" and tab.codeTAbonnement like '"+codeAbonnement+"' " +
		" and sa.inactif<>1 "				
		;
if(reglee)requete+=	" and f.netAPayer<=(select sum(rr.affectation)from TaRReglement rr where rr.taFacture=f)" ;			
		
				requete+=" order by f.codeDocument,a.codeArticle";

			query = taAbonnementDAO.getEntityManager().createQuery(requete);
//			query.setParameter(1, dateDeb,TemporalType.DATE);
//			query.setParameter(2, datefin,TemporalType.DATE);
//			query.setParameter(3, codeTiers);
//			query.setParameter(4, codeAbonnement);
//			
//			
//			query.setParameter(5, dateDeb,TemporalType.DATE);
//			query.setParameter(6, datefin,TemporalType.DATE);
//			query.setParameter(7, codeTiers);
//			query.setParameter(8, codeAbonnement);			

		boolean accept=true;
		temp = query.getResultList();
		for (LigneDocumentSelection ligneDocumentSelection : temp) {
//			for (LigneDocumentSelection l : result) {
//				if(l.getCodeSupport().equals(ligneDocumentSelection.getCodeSupport())
//						&& l.getCodeTiers().e)
//			}
			accept=true;
			for (LigneDocumentSelection ligne : result) {
				if(accept)
				accept=!ligne.getCodeSupport().equals(ligneDocumentSelection.getCodeSupport()) &&
						!ligne.getIdLDocument().equals(ligneDocumentSelection.getIdLDocument());
					
			}
			if(accept)result.add(ligneDocumentSelection);
//			if(!result.contains(ligneDocumentSelection))result.add(ligneDocumentSelection);
		}
		return result;
	}
	
	public List<LigneDocumentSelection> rechercheLigneAbonnementExistantes(Date dateDeb, Date datefin, String codeAbonnement, String codeTiers , boolean reglee) {
		vue.getForm().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		List<LigneDocumentSelection> result = new LinkedList<LigneDocumentSelection>();
		List<LigneDocumentSelection> temp = null;
		Query query = null;
		
//		String requete="select NEW fr.legrain.documents.dao.LigneDocumentSelection(f.codeDocument,lf.libLDocument,t.codeTiers,t.nomTiers,a.codeArticle," +
//				" f.dateDocument,tab.codeTAbonnement,lf.idLDocument," +
//				" lf.u1LDocument,lf.qteLDocument,ab.dateDebut,ab.dateFin,ab.idAbonnement,sa.codeSupportAbon )" +
//				" from TaLFacture lf join lf.taArticle a join lf.taDocument f join f.taTiers t " +
//				" join f.taRDocuments rd join rd.taAvisEcheance av join rd.taFacture f2  join av.lignes lav join lav.taLEcheance le join le.taAbonnement ab" +
//				" join ab.taSupportAbon sa join ab.taTAbonnement tab" +
//				" where f.dateDocument between ? and ?" +
//				" and t.codeTiers like ? and f.idDocument=f2.idDocument and lf.taArticle = ab.taArticle " +
//				" and not exists(select s from TaAbonnement s join s.taLDocument ld where ld.idLDocument=lf.idLDocument) " +
//				" and tab.codeTAbonnement like ?" ;
		
		String requete="select NEW fr.legrain.documents.dao.LigneDocumentSelection(f.codeDocument,lf.libLDocument,t.codeTiers,t.nomTiers,a1.codeArticle," +
				" f.dateDocument,tab.codeTAbonnement,lf.idLDocument," +
				" lf.u1LDocument,lf.qteLDocument,a.dateDebut,a.dateFin,a.idAbonnement,sa.codeSupportAbon )" +
				" from TaAbonnement a join a.taLDocument lf join lf.taArticle a1 join lf.taDocument f join f.taTiers t " +				
				" join a.taSupportAbon sa join a.taTAbonnement tab" +
				" where f.dateDocument between ? and ?" +
				" and t.codeTiers like ?  " +
				" and tab.codeTAbonnement like ?" ;
		
		if(reglee)requete+=	" and f.netAPayer<=(select sum(rr.affectation)from TaRReglement rr where rr.taFacture=f)" ;
		
				requete+=" order by f.codeDocument,a1.codeArticle";

		if(codeAbonnement==null || codeAbonnement.toLowerCase().equals("tous")) {
			query = taAbonnementDAO.getEntityManager().createQuery(requete);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeTiers);
			query.setParameter(4, "%");
		}else if(codeAbonnement!=null ) {
			query =  taAbonnementDAO.getEntityManager().createQuery(requete);
			query.setParameter(1, dateDeb);
			query.setParameter(2, datefin);
			query.setParameter(3, codeTiers);
			query.setParameter(4, codeAbonnement);
		}
		
		temp = query.getResultList();
		for (LigneDocumentSelection ligneDocumentSelection : temp) {
			if(!result.contains(ligneDocumentSelection))result.add(ligneDocumentSelection);
		}
		vue.getForm().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		return result;
	}
}
