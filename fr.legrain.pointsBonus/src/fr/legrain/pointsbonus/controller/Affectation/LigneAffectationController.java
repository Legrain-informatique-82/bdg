/**
 * ClientController.java
 */
package fr.legrain.pointsbonus.controller.Affectation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaRTSupport;
import fr.legrain.articles.dao.TaRTSupportDAO;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IHMReglement;
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
import fr.legrain.pointLgr.dao.SWTArticlePoint;
import fr.legrain.pointLgr.dao.TaArticlePoint;
import fr.legrain.pointLgr.dao.TaArticlePointDAO;
import fr.legrain.pointLgr.dao.TaTypeMouvPoint;
import fr.legrain.pointLgr.dao.TaTypeMouvPointDAO;
import fr.legrain.pointsbonus.PluginPointBonus;
import fr.legrain.pointsbonus.controller.LigneLabelProvider;
import fr.legrain.pointsbonus.divers.BigDecimalEditingSupport;
import fr.legrain.pointsbonus.divers.ComboBoxEditingSupport;
import fr.legrain.pointsbonus.divers.DateTimeEditingSupport;
import fr.legrain.pointsbonus.divers.StringEditingSupport;


/**
 * @author nicolas
 *
 */
public class LigneAffectationController extends AbstractControllerMiniEditable {

	static Logger logger = Logger.getLogger(LigneAffectationController.class.getName());	

	private Class objetIHM = null;
	private TaArticleDAO taArticleDAO = null;
	private TaArticlePointDAO taArticlePointDAO = null;
	protected List<SWTArticlePoint> modelLDocument = null;
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipalAffectation masterController = null;
	protected PaFormPageAffectation vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewer;
	IObservableValue selection;
//	private LgrTableViewer tableViewerDetail;
	protected Boolean suppressionUniquement=false; 
	private List<TaArticle> listeLDoc=null;
	
//	private MapperLigneDocumentSelectionIHMDocumentSelection mapper = new MapperLigneDocumentSelectionIHMDocumentSelection();
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
	public LigneAffectationController(FormPageControllerPrincipalAffectation masterContoller, PaFormPageAffectation vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (SWTArticlePoint objet : modelLDocument) {
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
				for (SWTArticlePoint objet :modelLDocument) {
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
				for (SWTArticlePoint objet : modelLDocument) {
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
//			taArticleDAO=new TaArticleDAO(getEm());
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
		taArticleDAO = new TaArticleDAO(masterController.getMasterDAOEM());
		taArticlePointDAO=new TaArticlePointDAO(masterController.getMasterDAOEM());
		
		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeArticle().getText().equals("")) {
			listeLDoc = taArticleDAO.findByFamilleAndSuffixe(vue.getCompositeSectionParam().getCbFamille().getText(),
					null,vue.getCompositeSectionParam().getTfAncienCode().getText());
		} else {
			listeLDoc = taArticleDAO.findByFamilleAndSuffixe(vue.getCompositeSectionParam().getCbFamille().getText(),
					vue.getCompositeSectionParam().getTfCodeArticle().getText(),vue.getCompositeSectionParam().getTfAncienCode().getText());
		}
		
		modelLDocument.clear();
		
		TaTypeMouvPointDAO typeMouvDao=new TaTypeMouvPointDAO(getEm());
		TaTypeMouvPoint mouv=typeMouvDao.findByCode("EF");
		
		String annee=LibDate.getAnnee(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()));		
		String anneePlusUn=LibDate.getAnnee(LibDate.incrementDate(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()), 0, 0, 1));
		
		for (TaArticle obj : listeLDoc) {
			SWTArticlePoint swtArticlePoint=new SWTArticlePoint();
			TaArticlePoint articlePointInitial=null;
			
			try {
				articlePointInitial= taArticlePointDAO.findByArticle(obj.getIdArticle());
			}catch (Exception e) {
				
			}
			BigDecimal prix=BigDecimal.ZERO; 
			if(obj.getTaPrix()!=null)prix=obj.getTaPrix().getPrixPrix();
			prix=prix.multiply(BigDecimal.valueOf(12));
			String libelle=obj.getLibellecArticle();
			swtArticlePoint.setCodeArticle(obj.getCodeArticle().replaceAll(vue.getCompositeSectionParam().getTfAncienCode().getText(), 
					vue.getCompositeSectionParam().getTfNouveauCode().getText()));
			swtArticlePoint.setIdArticle(obj.getIdArticle());
			libelle=libelle.replace("/"+annee.substring(2, 4),"/"+anneePlusUn);
			libelle=libelle.replace(annee,anneePlusUn);
			swtArticlePoint.setLibelleArticle(libelle);			
			swtArticlePoint.setLibelleMouv(mouv.getLibelleMouv());
			swtArticlePoint.setIdTypeMouv(mouv.getIdTypeMouv());
			swtArticlePoint.setPrixReference(prix);
			swtArticlePoint.setTypeMouv(mouv.getTypeMouv());
			if(articlePointInitial!=null)swtArticlePoint.setPoints(articlePointInitial.getPoints());
			swtArticlePoint.setDebutPeriode(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()));
			swtArticlePoint.setFinPeriode(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()));
			swtArticlePoint.setAccepte(true);
			swtArticlePoint.setIndice(1);
			
			TaRTSupportDAO rTSupportDao=new TaRTSupportDAO(masterController.getMasterDAOEM());
			List<TaRTSupport> listeRTSupport=rTSupportDao.findByidArticleAbonnement(obj.getIdArticle());
			String valeurCode="";
			for (TaRTSupport taRTSupport : listeRTSupport) {
				if(taRTSupport!=null && taRTSupport.getTaArticle()!=null){
					if(!valeurCode.isEmpty())valeurCode+=" / ";
					valeurCode+=taRTSupport.getTaArticle().getCodeArticle();
				}
			}
			swtArticlePoint.setCodeArticleSupport(valeurCode);

			if(!taArticleDAO.exist(swtArticlePoint.getCodeArticle()))
				modelLDocument.add(swtArticlePoint);
		}

//		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(modelLDocument.size()));
	}

//	/**
//	 * Initialise l'affichage du classement
//	 * @param nbResult le nombre de résultats affichés dans le tableau
//	 */
//	public void initialiseModelIHMExistant(int nbResult) {
//		// Initialisation des éléments à afficher à l'écran
//		//this.nbResult = nbResult;
//		LinkedList<TaLEcheance> listDocumentSelection = new LinkedList<TaLEcheance>();
//		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
//		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
//		taArticleDAO = new TaLEcheanceDAO(masterController.getMasterDAOEM());
//
//		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());
//
//		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
//			listeLEcheance = taArticleDAO.rechercheLigneRenouvellementAbonnement(
//					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
//					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),"%");
//		} else {
//			listeLEcheance = taArticleDAO.rechercheLigneRenouvellementAbonnement(
//					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
//					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),vue.getCompositeSectionParam().getTfCodeTiers().getText());
//		}
//		for (TaLEcheance taLEcheance : listeLEcheance) {
//			listDocumentSelection.add(taLEcheance);
//		}
//
//		modelLDocument = mapper.listeEntityToDto(listDocumentSelection);
//		initTotaux();
//	}
	
	
	private void initTotaux(){
		BigDecimal total = new BigDecimal(0);
		Integer nbCoche=0;
		for (SWTArticlePoint obj : modelLDocument) {
			if(obj.getAccepte()){
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
//		if(suppressionUniquement)initialiseModelIHMExistant(nbResult);
//		else 
			initialiseModelIHMNouveau(nbResult);
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
							getTableViewer().findPositionNomChamp("idArticle")
							);
					TaArticle artInitial= taArticleDAO.findById(LibConversion.stringToInteger(valeurIdentifiant));
					if(artInitial!=null)valeurIdentifiant=artInitial.getCodeArticle();
					String idEditor = ArticleMultiPageEditor.ID_EDITOR;
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
		if(modelLDocument==null)modelLDocument= new ArrayList<SWTArticlePoint>(0);
		setObjetIHM(SWTArticlePoint.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code article","libellé article","code article support","prix référence","Points","Type Point","Début période","fin période","idArticle"};
		// Taille des colonnes
		String [] tailleColonnes = {"200","500","300","100","100","100","100","100","0"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"codeArticle","libelleArticle","codeArticleSupport","prixReference","points","typeMouv","debutPeriode","finPeriode","idArticle"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionResultats().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionResultats().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		tableViewer.selectionGrille(0);
		selection = ViewersObservables.observeSingleSelection(tableViewer);
		LigneLabelProvider.bind(tableViewer, new WritableList(modelLDocument, SWTArticlePoint.class),
				BeanProperties.values(idColonnes));

		
//		TableViewerColumn col0 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[0]);
//		TableViewerColumn col1 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[1]);
//		TableViewerColumn col2 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[2]);
//		TableViewerColumn col3 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[3]);
//		TableViewerColumn col4 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[4]);
//		TableViewerColumn col5 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[5]);
//		TableViewerColumn col6 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[6]);
//		TableViewerColumn col7 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[7]);
//		TableViewerColumn col8 = new TableViewerColumn(tableViewer,tableViewer.getTable().getColumns()[8]);
//
//		
//		col0.setLabelProvider(new MyColumnLabelProvider(0));//codeArticle
//		col1.setLabelProvider(new MyColumnLabelProvider(1));//libelleArticle
//		col2.setLabelProvider(new MyColumnLabelProvider(2));//codeArticleSupport
//		col3.setLabelProvider(new MyColumnLabelProvider(3));//prixReference
//		col4.setLabelProvider(new MyColumnLabelProvider(4));//points
//		col5.setLabelProvider(new MyColumnLabelProvider(5));//typeMouv
//		col6.setLabelProvider(new MyColumnLabelProvider(6));//debutPeriode
//		col7.setLabelProvider(new MyColumnLabelProvider(7));//finPeriode
//		col8.setLabelProvider(new MyColumnLabelProvider(8));//idArticle
//		
//
//		EditingSupport col0EditingSupport = new StringEditingSupport(tableViewer,0);
//		col0.setEditingSupport(col0EditingSupport);
//		
//		EditingSupport col1EditingSupport = new StringEditingSupport(tableViewer,1);
//		col1.setEditingSupport(col1EditingSupport);
//
//		EditingSupport col3EditingSupport = new BigDecimalEditingSupport(tableViewer,3);
//		col3.setEditingSupport(col3EditingSupport);
//		
//		EditingSupport col4EditingSupport = new BigDecimalEditingSupport(tableViewer,4);
//		col4.setEditingSupport(col4EditingSupport);
//		
//		EditingSupport col5EditingSupport = new StringEditingSupport(tableViewer,5);
//		col5.setEditingSupport(col5EditingSupport);
//		

//		EditingSupport col1EditingSupport = new ComboBoxEditingSupport(tableViewer,7);
//		col7.setEditingSupport(col1EditingSupport);


		//TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[1]);
		tableViewer.getTable().getColumns()[titreColonnes.length-1].setResizable(false);
		tableViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((SWTArticlePoint)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((SWTArticlePoint)element).getAccepte())
					return true;
				return false;
			}
		});
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkAccept(((SWTArticlePoint)event.getElement()).getIdArticlePoint(),event.getChecked());
				
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
					&& ((SWTArticlePoint) element).getCodeArticle()!=null ) {
					return ((SWTArticlePoint) element).getCodeArticle().toString();
				}
				break;
			case 1:
				if (element!=null 
					&& ((SWTArticlePoint) element).getLibelleArticle()!=null ) {
					return ((SWTArticlePoint) element).getLibelleArticle().toString();
				}
				break;
			case 2:
				if (element!=null 
					&& ((SWTArticlePoint) element).getCodeArticleSupport()!=null ) {
					return ((SWTArticlePoint) element).getCodeArticleSupport().toString();
				}
				break;
			case 3:
				if (element!=null 
					&& ((SWTArticlePoint) element).getPrixReference()!=null ) {
					return ((SWTArticlePoint) element).getPrixReference().toString();
				}
				break;
			case 4:
				if (element!=null 
					&& ((SWTArticlePoint) element).getPoints()!=null ) {
					return ((SWTArticlePoint) element).getPoints().toString();
				}
				break;
			case 5:
				if (element!=null 
					&& ((SWTArticlePoint) element).getTypeMouv()!=null ) {
					return ((SWTArticlePoint) element).getTypeMouv().toString();
				}
				break;
			case 6:
				if (element!=null 
					&& ((SWTArticlePoint) element).getDebutPeriode()!=null ) {
					return LibDate.dateToString(((SWTArticlePoint) element).getDebutPeriode());
				}
				break;
			case 7:
				if (element!=null 
						&& ((SWTArticlePoint) element).getFinPeriode()!=null ) {
						return LibDate.dateToString(((SWTArticlePoint) element).getFinPeriode());
				}
				break;
			case 8:
				if (element!=null 
					&& ((SWTArticlePoint) element).getIdArticle()!=null ) {
					return ((SWTArticlePoint) element).getIdArticle().toString();
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
				((SWTArticlePoint)selection.getValue()).setAccepte(check);
//				Object entity=rechercheEntity(idDocument);
//				((SWTArticlePoint)entity).setAccepte(check);
				initTotaux();
			}
		} catch (Exception e) {
			logger.error("", e);
		}		
	}
	
	public Object recherche( Integer value) {
	boolean trouve = false;
	int i = 0;
	List<SWTArticlePoint> model=modelLDocument;
	while(!trouve && i<model.size()){
		try {
			if(PropertyUtils.getProperty(model.get(i), Const.C_ID_ARTICLE_POINT)==value) {
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
	
//	public TaLEcheance rechercheEntity( Integer value) {
//		boolean trouve = false;
//		int i = 0;
//		TaLEcheance objet;
//		List<TaLEcheance> model=listeLEcheance;
//		while(!trouve && i<model.size()){
//			try {
//				objet=model.get(i);
//				if(objet!=null && objet.getIdLEcheance()==(value)){
//					trouve = true;
//				} else {
//					i++;
//				}
//			} catch (Exception e) {
//				logger.error("",e);
//			}
//		}
//
//		if(trouve)
//			return model.get(i);
//		else 
//			return null;
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


	


	public LgrTableViewer getTableViewer() {
		return tableViewer;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action editonAction = new Action("Edition",PluginPointBonus.getImageDescriptor(PaCompositeSectionDocAffectation.iconpath)) { 
		@Override 
		public void run() {
			EditionGrille edition = new EditionGrille(vue.getForm().getShell());
			edition.print(vue.getCompositeSectionResultats().getTable());
		}
	};


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
		EntityTransaction transaction = taArticleDAO.getEntityManager().getTransaction();
		try {
			try {
				taArticleDAO.begin(transaction);
				//taArticlePointDAO.begin(transaction);
				TaArticleDAO daoArticle = new TaArticleDAO();
				
				//enregistrer les lignes d'article et point à renouveler
				for (SWTArticlePoint objet : modelLDocument) {
					if(objet.getAccepte()){
						TaArticle articleDupl=taArticleDAO.findById(objet.getIdArticle());
						TaArticle taArticle = new TaArticle();
						taArticle=(TaArticle)articleDupl.clone();
						
						taArticle.setCodeArticle(objet.getCodeArticle().toUpperCase());
						taArticle.setLibellecArticle(objet.getLibelleArticle().toUpperCase());
						taArticle.setActif(1);
						taArticle=taArticleDAO.enregistrerMerge(taArticle);
						
						
						TaArticlePoint articlePoint=new TaArticlePoint();
						articlePoint.setTaArticle(taArticle);
						articlePoint.setDebutPeriode(objet.getDebutPeriode());
						articlePoint.setFinPeriode(objet.getFinPeriode());
						articlePoint.setIndice(objet.getIndice());
						articlePoint.setPoints(objet.getPoints());
						articlePoint.setPrixReference(objet.getPrixReference());
						
						TaTypeMouvPointDAO typeMouvDao=new TaTypeMouvPointDAO(getEm());
						TaTypeMouvPoint mouv=typeMouvDao.findById(objet.getIdTypeMouv());
						articlePoint.setTaTypeMouvPoint(mouv);
						articlePoint.setVersionObj(0);
						articlePoint=taArticlePointDAO.enregistrerMerge(articlePoint);
						
						TaRTSupportDAO rTSupportDao=new TaRTSupportDAO(masterController.getMasterDAOEM());
						List<TaRTSupport> listeRTSupport=rTSupportDao.findByidArticleAbonnement(objet.getIdArticle());
						for (TaRTSupport taRTSupport : listeRTSupport) {
							if(taRTSupport!=null){
								taRTSupport.setTaArticleAbonnement(taArticle);
								rTSupportDao.enregistrerMerge(taRTSupport);
							}
						}

					}
				}
				taArticleDAO.commit(transaction);
				//taArticlePointDAO.commit(transaction);
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
//			if(taLEcheance!=null)
//				idActuel =taLEcheance.getIdLEcheance();				
			WritableList writableListFacture = new WritableList(modelLDocument, SWTArticlePoint.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(idActuel!=null && writableListFacture.size()>0) {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_ID_ARTICLE
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
		List<SWTArticlePoint> model=modelLDocument;
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
