/**
 * ClientController.java
 */
package fr.legrain.reglement.reglementEnListe;

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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.facture.editor.FactureMultiPageEditor;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
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
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.reglement.Activator;
import fr.legrain.reglement.divers.BigDecimalEditingSupport;
import fr.legrain.reglement.divers.ComboBoxEditingSupport;
import fr.legrain.reglement.divers.DateTimeEditingSupport;
import fr.legrain.reglement.divers.LigneLabelProvider;
import fr.legrain.reglement.divers.StringEditingSupport;
import fr.legrain.reglement.ecran.PaCompositeSectionDocReglement;
import fr.legrain.reglement.ecran.PaFormPageReglement;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * @author nicolas
 *
 */
public class LigneReglementController extends AbstractControllerMiniEditable {

	static Logger logger = Logger.getLogger(LigneReglementController.class.getName());	

	private Class objetIHM = null;
	private TaRReglementDAO taRReglementDAO = null;
	private TaRReglement taRReglement = null;
	private TaFactureDAO taFactureDao=null;
	protected List<IHMReglement> modelLDocument = new ArrayList<IHMReglement>(0);
	protected List<TaFacture> listeLDoc;
	protected List<TaRReglement> listeRReglement = new LinkedList<TaRReglement>();
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipalReglement masterController = null;
	protected PaFormPageReglement vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewer;
	IObservableValue selection;
//	private LgrTableViewer tableViewerDetail;
	protected Boolean suppressionUniquement=false; 
	
	private MapperLigneDocumentSelectionIHMDocumentSelection mapper = new MapperLigneDocumentSelectionIHMDocumentSelection();
	private LgrDozerMapper<TaTPaiement, SWTTPaiement>mapperModelToSWTTpaiement = new LgrDozerMapper<TaTPaiement, SWTTPaiement>();
	
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
	public LigneReglementController(FormPageControllerPrincipalReglement masterContoller, PaFormPageReglement vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IHMReglement objet : modelLDocument) {
					Boolean accepte=!objet.getAccepte();
					objet.setAccepte(accepte);
					checkAccept(objet.getId(),accepte);												
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
				for (IHMReglement objet :modelLDocument) {
					objet.setAccepte(true);
					checkAccept(objet.getId(),true);
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
				for (IHMReglement objet : modelLDocument) {
					objet.setAccepte(false);
					checkAccept(objet.getId(),false);
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
			taRReglementDAO=new TaRReglementDAO(masterController.getMasterDAOEM());
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
		//this.nbResult = nbResult;
		int numLigne=0;
		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
		taFactureDao = new TaFactureDAO();

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLDoc = taFactureDao.rechercheDocumentNonTotalementRegle(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,"%","%");
		} else {
			listeLDoc = taFactureDao.rechercheDocumentNonTotalementRegle(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,vue.getCompositeSectionParam().getTfCodeTiers().getText(),"%");
		}
		
		BigDecimal total = new BigDecimal(0);
		// Liste qui va contenir les informations utiles pour les renouvellements
		LinkedList<TaRReglement> listDocumentSelection = new LinkedList<TaRReglement>();
		TaRReglement documentSelection = null;
		TaFacture object = null;
		TaTPaiementDAO daoTPaiement = new TaTPaiementDAO();
//		String typePaiementDefaut =DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
		TaTPaiement tPaiement =  daoTPaiement.findByCodeDefaut(masterController.paramControllerMini.getCodeEtat());
		
		for (int i = 0; i < listeLDoc.size(); i ++){

			object = listeLDoc.get(i);
			documentSelection = new TaRReglement();

			numLigne++;
			documentSelection.setId(numLigne);
			documentSelection.setAccepte(true);
			documentSelection.setAffectation(object.getResteAReglerComplet());
			documentSelection.setTaFacture(object);
			documentSelection.setTaReglement(new TaReglement());
			documentSelection.getTaReglement().setDateDocument(object.getDateDocument());
			documentSelection.getTaReglement().setDateLivDocument(object.getDateDocument());
			if(object.getTaTiers().getTaTPaiement()!=null){
				documentSelection.getTaReglement().setTaTPaiement(object.getTaTiers().getTaTPaiement());
			}else 	documentSelection.getTaReglement().setTaTPaiement(tPaiement);
			documentSelection.getTaReglement().setDateDocument(object.getDateDocument());
			documentSelection.getTaReglement().setAccepte(true);
			documentSelection.getTaReglement().setLibelleDocument(documentSelection.getTaReglement().getTaTPaiement().getLibTPaiement());
			documentSelection.getTaReglement().setNetTtcCalc(documentSelection.getAffectation());
			documentSelection.getTaReglement().setTaTiers(object.getTaTiers());
			listDocumentSelection.add(documentSelection);
			total=total.add(documentSelection.getAffectation());
		}
		for (TaRReglement taLEcheance : listDocumentSelection) {
			listeRReglement.add(taLEcheance);
		}
		modelLDocument = mapper.listeEntityToDto(listDocumentSelection);
		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(modelLDocument.size()));
		
	}

	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHMExistant(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		//this.nbResult = nbResult;
		LinkedList<TaRReglement> listDocumentSelection = new LinkedList<TaRReglement>();
//		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
//		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
//		taRReglementDAO = new TaLEcheanceDAO(masterController.getMasterDAOEM());
//
//		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());
//
//		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
//			listeLEcheance = taRReglementDAO.rechercheLigneRenouvellementAbonnement(
//					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
//					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),"%");
//		} else {
//			listeLEcheance = taRReglementDAO.rechercheLigneRenouvellementAbonnement(
//					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
//					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),vue.getCompositeSectionParam().getTfCodeTiers().getText());
//		}
//		for (TaLEcheance taLEcheance : listeLEcheance) {
//			listDocumentSelection.add(taLEcheance);
//		}

		modelLDocument = mapper.listeEntityToDto(listDocumentSelection);
		initTotaux();
	}
	
	
	private void initTotaux(){
		BigDecimal total = new BigDecimal(0);
		Integer nbCoche=0;
		for (IHMReglement obj : modelLDocument) {
			if(obj.getAccepte()){
				total=total.add(obj.getAffectation());
				nbCoche++;
			}
		}

		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
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
							getTableViewer().findPositionNomChamp("codeFacture")
							);
					String idEditor = FactureMultiPageEditor.ID_EDITOR;
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
		if(modelLDocument==null)modelLDocument=new ArrayList<IHMReglement>(0);
		setObjetIHM(IHMReglement.class);
		
		// Titre des colonnes
		String [] titreColonnes = {"Code tiers","Nom tiers","facture","date règlement","Date encaissement","montant règlement","montant affecté",
				"type paiement","libellé paiement","id"};
		// Taille des colonnes
		String [] tailleColonnes = {"100","100","100","100","100","100","100","100","100","0"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"codeTiers","nomTiers","codeFacture","dateDocument","dateLivDocument","netTtcCalc","affectation","codeTPaiement","libelleDocument","id"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.NONE,SWT.NONE,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionResultats().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionResultats().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		//tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		tableViewer.selectionGrille(0);
		selection = ViewersObservables.observeSingleSelection(tableViewer);
		
		LigneLabelProvider.bind(tableViewer, new WritableList(modelLDocument, IHMReglement.class),
				BeanProperties.values(idColonnes));
			
		
		TableViewerColumn col0 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[0]);
		TableViewerColumn col1 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[1]);
		TableViewerColumn col2 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[2]);
		TableViewerColumn col3 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[3]);
		TableViewerColumn col4 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[4]);
		TableViewerColumn col5 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[5]);
		TableViewerColumn col6 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[6]);
		TableViewerColumn col7 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[7]);
		TableViewerColumn col8 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[8]);
		TableViewerColumn col9 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[9]);
		
		col0.setLabelProvider(new MyColumnLabelProvider(0));//codeTiers
		col1.setLabelProvider(new MyColumnLabelProvider(1));//nomTiers
		col2.setLabelProvider(new MyColumnLabelProvider(2));//codeFacture
		col3.setLabelProvider(new MyColumnLabelProvider(3));//dateDocument
		col4.setLabelProvider(new MyColumnLabelProvider(4));//dateLivDocument
		col5.setLabelProvider(new MyColumnLabelProvider(5));//netTtcCalc
		col6.setLabelProvider(new MyColumnLabelProvider(6));//affectation
		col7.setLabelProvider(new MyColumnLabelProvider(7));//codeTPaiement
		col8.setLabelProvider(new MyColumnLabelProvider(8));//libelleDocument
		col9.setLabelProvider(new MyColumnLabelProvider(9));//id
		

		
		EditingSupport col3EditingSupport = new DateTimeEditingSupport(tableViewer,3);
		col3.setEditingSupport(col3EditingSupport);
		
		EditingSupport col4EditingSupport = new DateTimeEditingSupport(tableViewer,4);
		col4.setEditingSupport(col4EditingSupport);
		
		EditingSupport col5EditingSupport = new BigDecimalEditingSupport(tableViewer,5);
		col5.setEditingSupport(col5EditingSupport);
		
		EditingSupport col6EditingSupport = new BigDecimalEditingSupport(tableViewer,6);
		col6.setEditingSupport(col6EditingSupport);		
		
		EditingSupport col1EditingSupport = new ComboBoxEditingSupport(tableViewer,7);
		col7.setEditingSupport(col1EditingSupport);
		
		EditingSupport col2EditingSupport = new StringEditingSupport(tableViewer,8);
		col8.setEditingSupport(col2EditingSupport);


		tableViewer.getTable().getColumns()[9].setResizable(false);
		tableViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMReglement)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMReglement)element).getAccepte())
					return true;
				return false;
			}
		});
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkAccept(((IHMReglement)event.getElement()).getId(),event.getChecked());
				
			}
		});
		initController();
		initTotaux();
	}
	
	
	private class MyColumnLabelProvider extends ColumnLabelProvider {
		private int column = 0;
		private Font f = null;
		
		public MyColumnLabelProvider(int column) {
			this.column = column;

			f = Display.getCurrent().getSystemFont();
			f = new Font(Display.getCurrent(),
					f.getFontData()[0].getName()
					,f.getFontData()[0].getHeight()
					,SWT.BOLD);
		}
		
		@Override
		public String getText(Object element) {
						
			switch (column) {
			case 0:
				if (element!=null 
					&& ((IHMReglement) element).getCodeTiers()!=null ) {
					return ((IHMReglement) element).getCodeTiers().toString();
				}
				break;
			case 1:
				if (element!=null 
					&& ((IHMReglement) element).getNomTiers()!=null ) {
					return ((IHMReglement) element).getNomTiers().toString();
				}
				break;
			case 2:
				if (element!=null 
					&& ((IHMReglement) element).getCodeFacture()!=null ) {
					return ((IHMReglement) element).getCodeFacture().toString();
				}
				break;
			case 3:
				if (element!=null 
					&& ((IHMReglement) element).getDateDocument()!=null ) {
					return LibDate.dateToString(((IHMReglement) element).getDateDocument());
				}
				break;
			case 4:
				if (element!=null 
					&& ((IHMReglement) element).getDateLivDocument()!=null ) {
					return LibDate.dateToString(((IHMReglement) element).getDateLivDocument());
				}
				break;
			case 5:
				if (element!=null 
					&& ((IHMReglement) element).getNetTtcCalc()!=null ) {
					return ((IHMReglement) element).getNetTtcCalc().toString();
				}
				break;
			case 6:
				if (element!=null 
					&& ((IHMReglement) element).getAffectation()!=null ) {
					return ((IHMReglement) element).getAffectation().toString();
				}
				break;
			case 7:
				if (element!=null 
					&& ((IHMReglement) element).getCodeTPaiement()!=null ) {
					return ((IHMReglement) element).getCodeTPaiement().toString();
				}
				break;
			case 8:
				if (element!=null 
					&& ((IHMReglement) element).getLibelleDocument()!=null ) {
					return ((IHMReglement) element).getLibelleDocument().toString();
				}
				break;
			case 9:
				if (element!=null 
					&& ((IHMReglement) element).getId()!=null ) {
					return ((IHMReglement) element).getId().toString();
				}
				break;
			default:
				break;
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}
		
		@Override
		public Font getFont(Object element) {
			return super.getFont(element);
		}
	}
	
	public void checkAccept(Integer idDocument,boolean check){
		try {
			Object objet=recherche(idDocument);
			StructuredSelection selectionloc =new StructuredSelection(objet);
			tableViewer.setSelection(selectionloc,true);
			if(selection.getValue()!=null){
				((IHMReglement)selection.getValue()).setAccepte(check);
				Object entity=rechercheEntity(idDocument);
				((TaRReglement)entity).setAccepte(check);
				initTotaux();
			}
		} catch (Exception e) {
			logger.error("", e);
		}		
	}
	
	public Object recherche( Integer value) {
	boolean trouve = false;
	int i = 0;
	List<IHMReglement> model=modelLDocument;
	while(!trouve && i<model.size()){
		try {
			if(PropertyUtils.getProperty(model.get(i), Const.C_ID)==value) {
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
	
	public TaRReglement rechercheEntity( Integer value) {
		boolean trouve = false;
		int i = 0;
		TaRReglement objet;
		List<TaRReglement> model=listeRReglement;
		while(!trouve && i<model.size()){
			try {
				objet=model.get(i);
				if(objet!=null && objet.getId()==(value)){
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
	
	
	



				




	
	public class MapperLigneDocumentSelectionIHMDocumentSelection implements IlgrMapper<IHMReglement, TaRReglement> {

		public List<IHMReglement> listeEntityToDto(LinkedList<TaRReglement> l) {
			List<IHMReglement> res = new ArrayList<IHMReglement>(0);
			for (TaRReglement document : l) {
				res.add(entityToDto(document));
			}
			return res;
		}

		public IHMReglement entityToDto(TaRReglement e) {
			LgrDozerMapper<TaRReglement,IHMReglement> mapper = new LgrDozerMapper<TaRReglement,IHMReglement>();
			IHMReglement documentSelectionIHM = new IHMReglement();
			mapper.map(e,documentSelectionIHM);
			//documentSelectionIHM.setCodeFacture(e.getTaFacture().getCodeDocument());
			return documentSelectionIHM;
		}

		@Override
		public TaRReglement dtoToEntity(IHMReglement e) {
			LgrDozerMapper<IHMReglement,TaRReglement> mapper = new LgrDozerMapper<IHMReglement,TaRReglement>();
			TaRReglement l = new TaRReglement();
			mapper.map(e, l);
			return l;
		}
	}

	public LgrTableViewer getTableViewer() {
		return tableViewer;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action editonAction = new Action("Edition",Activator.getImageDescriptor(PaCompositeSectionDocReglement.iconpath)) { 
		@Override 
		public void run() {
			print();
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
		EntityTransaction transaction = taRReglementDAO.getEntityManager().getTransaction();
		try {
			try {
				
				TaReglementDAO daoReglement = new TaReglementDAO(taRReglementDAO.getEntityManager());
				TaTiers tiers =null;
				TaTiersDAO daoTiers = new TaTiersDAO(taRReglementDAO.getEntityManager());
				TaFactureDAO daoFacture = new TaFactureDAO(taRReglementDAO.getEntityManager());
				TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(taRReglementDAO.getEntityManager());
				//enregistrer les lignes de règlement
				for (IHMReglement objet : modelLDocument) {
					if(objet.getAccepte()){
						taRReglementDAO.begin(transaction);
						taRReglement = new TaRReglement();
						TaReglement taReglement = new TaReglement();
						taReglement.setCodeDocument(daoReglement.genereCode());
						taReglement=daoReglement.enregistrerMerge(taReglement);
						tiers=daoTiers.findByCode(objet.getCodeTiers());
						if(tiers!=null)	taReglement.setTaTiers(tiers);
						//taRReglement=mapper.dtoToEntity(objet);

						taRReglement.setTaReglement(taReglement);
						TaTPaiement taTPaiement = daoTPaiement.findByCode(objet.getCodeTPaiement());
						taReglement.setTaCompteBanque(new TaCompteBanqueDAO(taRReglementDAO.getEntityManager()).findByTiersEntreprise(taTPaiement));
						taRReglement.setAffectation(objet.getAffectation());
						taReglement.setNetTtcCalc(objet.getNetTtcCalc());
						taReglement.setDateDocument(objet.getDateDocument());
						taReglement.setDateLivDocument(objet.getDateLivDocument());
						taRReglement.getTaReglement().setEtat(IHMEtat.integre);
						taReglement.setTaTPaiement(daoTPaiement.findByCode(objet.getCodeTPaiement()));
						taReglement.setLibelleDocument(objet.getLibelleDocument());
						taRReglement.setTaFacture(daoFacture.findByCode(objet.getCodeFacture()));
						
						if(taRReglement.getVersionObj()==null)taRReglement.setVersionObj(0);
						
						taRReglement=taRReglementDAO.enregistrerMerge(taRReglement);
						taRReglementDAO.commit(transaction);
					}
				}
				taRReglementDAO.commit(transaction);
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
		EntityTransaction transaction = taRReglementDAO.getEntityManager().getTransaction();
		try {
			try {
				taRReglementDAO.begin(transaction);
				
				//enregistrer les lignes d'échéance
				for (IHMReglement objet : modelLDocument) {
					if(objet.getAccepte()){
						taRReglement =taRReglementDAO.findById(objet.getId());
//						taLEcheance.setTaAbonnement(taAbonnementDAO.findById(objet.getIdAbonnement()));
//						taLEcheance.setTaArticle(daoArticle.findByCode(objet.getCodeArticle()));
//						
						
						taRReglementDAO.supprimer(taRReglement);
					}
				}
				taRReglementDAO.commit(transaction);
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
			if(taRReglement!=null)
				idActuel =taRReglement.getId();				
			WritableList writableListFacture = new WritableList(modelLDocument, IHMReglement.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(idActuel!=null && writableListFacture.size()>0) {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_ID
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
		List<IHMReglement> model=modelLDocument;
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

}
