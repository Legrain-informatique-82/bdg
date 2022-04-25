/**
 * ClientController.java
 */
package fr.legrain.abonnement.controllers.echeance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.abonnement.pluginAbonnement;
import fr.legrain.abonnement.controllers.LigneLabelProvider;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.abonnement.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.abonnement.ecrans.PaFormPageEcheance;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaRTSupport;
import fr.legrain.articles.dao.TaRTSupportDAO;
import fr.legrain.dms.dao.TaEtatMouvementDms;
import fr.legrain.documents.dao.TaLEcheance;
import fr.legrain.documents.dao.TaLEcheanceDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IHMLEcheance;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMiniEditable;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.grille.EditionGrille;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.licence.editors.SupportAbonMultiPageEditor;
import fr.legrain.pointLgr.dao.TaPourcGroupeDAO;

/**
 * @author nicolas
 *
 */
public class LigneEcheanceController extends AbstractControllerMiniEditable {

	static Logger logger = Logger.getLogger(LigneEcheanceController.class.getName());	

	private Class objetIHM = null;
	private TaLEcheanceDAO taLEcheanceDAO = null;
	private TaAbonnementDAO taAbonnementDAO = null;
	private TaLEcheance taLEcheance = null;
	protected List<IHMLEcheance> modelLDocument = null;
	protected List<TaAbonnement> listeLDoc;
	protected List<TaLEcheance> listeLEcheance = new LinkedList<TaLEcheance>();
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipalEcheance masterController = null;
	protected PaFormPageEcheance vue = null;
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
	public LigneEcheanceController(FormPageControllerPrincipalEcheance masterContoller, PaFormPageEcheance vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IHMLEcheance objet : modelLDocument) {
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
				for (IHMLEcheance objet :modelLDocument) {
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
				for (IHMLEcheance objet : modelLDocument) {
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
			taLEcheanceDAO=new TaLEcheanceDAO();
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
		taAbonnementDAO = new TaAbonnementDAO(masterController.getMasterDAOEM());

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLDoc = taAbonnementDAO.findAbonnementBetweenDateDebDateFin(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,"%");
		} else {
			listeLDoc = taAbonnementDAO.findAbonnementBetweenDateDebDateFin(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,vue.getCompositeSectionParam().getTfCodeTiers().getText());
		}
		
		BigDecimal total = new BigDecimal(0);
		// Liste qui va contenir les informations utiles pour les renouvellements
		LinkedList<TaLEcheance> listDocumentSelection = new LinkedList<TaLEcheance>();
		TaLEcheance documentSelection = null;
		TaAbonnement object = null;
		for (int i = 0; i < listeLDoc.size(); i ++){

			object = listeLDoc.get(i);
//			TaFamilleTiers groupe =null;
//			TaPourcGroupe pourcGroupe= null;
			TaPrix prix = null;
			Integer duree=0;
			documentSelection = new TaLEcheance();
			TaPourcGroupeDAO daoPourcGroupe = new TaPourcGroupeDAO();
			TaRTSupportDAO doaRTSupport = new TaRTSupportDAO();
			TaRTSupport taRTSupport = null;
			if(object.getTaSupportAbon()!=null && object.getTaSupportAbon().getTaTSupport()!=null && object.getTaSupportAbon().getTaArticle()!=null){
				if(doaRTSupport.existByidArticleIdTSupport(object.getTaSupportAbon().getTaArticle().getIdArticle(),object.getTaSupportAbon().getTaTSupport().getIdTSupport()))
					taRTSupport=doaRTSupport.findByidArticleIdTSupport(object.getTaSupportAbon().getTaArticle().getIdArticle(),object.getTaSupportAbon().getTaTSupport().getIdTSupport());
			}
			if(taRTSupport!=null){
				if(taRTSupport.getTaArticleAbonnement()!=null){
					documentSelection.setTaArticle(taRTSupport.getTaArticleAbonnement());
					prix=taRTSupport.getTaArticleAbonnement().getTaPrix();
					if(prix!=null){
						documentSelection.setPrixULDocument(prix.getPrixPrix());
						if(prix.getTaUnite()!=null)
							documentSelection.setU1LDocument(prix.getTaUnite().getCodeUnite());
					}
					documentSelection.setLibLDocument(documentSelection.getTaArticle().getLibellecArticle());
					if(LibDateTime.isDateNull(vue.getCompositeSectionParam().getCdateFinCalcul())){
						duree=object.getTaTAbonnement().getDuree();
					}else{
						duree=LibDate.nbMois(object.getDateFin(),LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFinCalcul()));
					}
					documentSelection.setDateFinCalcul(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFinCalcul()));
					documentSelection.setDebutPeriode(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()));
					documentSelection.setFinPeriode(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()));
					documentSelection.setDebAbonnement(LibDate.incrementDate(object.getDateFin(), 1, 0, 0) );
					documentSelection.setFinAbonnement(LibDate.incrementDate(object.getDateFin(), 0,duree, 0) );
					documentSelection.setDateFinCalcul(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFinCalcul()));
//					if(object.getTaSupportAbon()!=null && object.getTaSupportAbon() instanceof TaLicenceSara){
//						groupe=((TaLicenceSara)object.getTaSupportAbon()).getGroupe();				
//					}
//					if(object.getTaSupportAbon()!=null && object.getTaSupportAbon() instanceof TaLicenceBdg){
//						groupe=((TaLicenceBdg)object.getTaSupportAbon()).getGroupe();	
//					}			
//					if(object.getTaSupportAbon()!=null && object.getTaSupportAbon() instanceof TaLicenceEpicea){
//						groupe=((TaLicenceEpicea)object.getTaSupportAbon()).getGroupe();	
//					}			
//					if(groupe!=null)pourcGroupe=daoPourcGroupe.findByCodeAbonnementGoupe(object.getTaTAbonnement().getCodeTAbonnement(), groupe.getCodeFamille());
//					if(pourcGroupe!=null)documentSelection.setPourcPartenaire(pourcGroupe.getPourcentage());
					if(duree!=null)documentSelection.setQteLDocument(BigDecimal.valueOf(duree));
					if(documentSelection.getPourcPartenaire()!=null)documentSelection.setRemTxLDocument(documentSelection.getPourcPartenaire());
					documentSelection.calculMontant();
					documentSelection.setTaAbonnement(object);
					numLigne++;
					documentSelection.setIdLEcheance(numLigne);
					if(documentSelection.getQteLDocument()!=null && documentSelection.getQteLDocument().intValue()!=0)listDocumentSelection.add(documentSelection);
					total=total.add(documentSelection.getMtTtcLApresRemiseGlobaleDocument());
				}
//			}
//			documentSelection.setDateFinCalcul(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFinCalcul()));
//			documentSelection.setDebutPeriode(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()));
//			documentSelection.setFinPeriode(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()));
//			documentSelection.setDebAbonnement(LibDate.incrementDate(object.getDateFin(), 1, 0, 0) );
//			documentSelection.setFinAbonnement(LibDate.incrementDate(object.getDateFin(), 0,duree, 0) );
//			documentSelection.setDateFinCalcul(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFinCalcul()));
////			if(object.getTaSupportAbon()!=null && object.getTaSupportAbon() instanceof TaLicenceSara){
////				groupe=((TaLicenceSara)object.getTaSupportAbon()).getGroupe();				
////			}
////			if(object.getTaSupportAbon()!=null && object.getTaSupportAbon() instanceof TaLicenceBdg){
////				groupe=((TaLicenceBdg)object.getTaSupportAbon()).getGroupe();	
////			}			
////			if(object.getTaSupportAbon()!=null && object.getTaSupportAbon() instanceof TaLicenceEpicea){
////				groupe=((TaLicenceEpicea)object.getTaSupportAbon()).getGroupe();	
////			}			
////			if(groupe!=null)pourcGroupe=daoPourcGroupe.findByCodeAbonnementGoupe(object.getTaTAbonnement().getCodeTAbonnement(), groupe.getCodeFamille());
////			if(pourcGroupe!=null)documentSelection.setPourcPartenaire(pourcGroupe.getPourcentage());
//			if(duree!=null)documentSelection.setQteLDocument(BigDecimal.valueOf(duree));
//			if(documentSelection.getPourcPartenaire()!=null)documentSelection.setRemTxLDocument(documentSelection.getPourcPartenaire());
//			documentSelection.calculMontant();
//			documentSelection.setTaAbonnement(object);
//			numLigne++;
//			documentSelection.setIdLEcheance(numLigne);
//			if(documentSelection.getQteLDocument()!=null && documentSelection.getQteLDocument().intValue()!=0)listDocumentSelection.add(documentSelection);
//			total=total.add(documentSelection.getMtTtcLApresRemiseGlobaleDocument());
			}
		}
		for (TaLEcheance taLEcheance : listDocumentSelection) {			
				listeLEcheance.add(taLEcheance);
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
		LinkedList<TaLEcheance> listDocumentSelection = new LinkedList<TaLEcheance>();
		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
		taLEcheanceDAO = new TaLEcheanceDAO(masterController.getMasterDAOEM());

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLEcheance = taLEcheanceDAO.rechercheLigneRenouvellementAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),"%",vue.getCompositeSectionParam().getBtnTransforme().getSelection());
		} else {
			listeLEcheance = taLEcheanceDAO.rechercheLigneRenouvellementAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),vue.getCompositeSectionParam().getTfCodeTiers().getText(),vue.getCompositeSectionParam().getBtnTransforme().getSelection());
		}
		for (TaLEcheance taLEcheance : listeLEcheance) {
			listDocumentSelection.add(taLEcheance);
		}

		modelLDocument = mapper.listeEntityToDto(listDocumentSelection);
		initTotaux();
	}
	
	
	private void initTotaux(){
		BigDecimal total = new BigDecimal(0);
		Integer nbCoche=0;
		for (IHMLEcheance obj : modelLDocument) {
			if(obj.getAccepte()){
				total=total.add(obj.getMtTtcLApresRemiseGlobaleDocument());
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
		vue.getCompositeSectionResultats().getCompoEcran().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXPORT));
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
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
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
		if(modelLDocument==null)modelLDocument= new ArrayList<IHMLEcheance>(0);
		setObjetIHM(IHMLEcheance.class);

		// Titre des colonnes
		String [] titreColonnes = {"Type abonnement","code support","code article","Code tiers","Nom tiers","Début période","fin période","fin calcul","début abonnement","fin abonnement",
				"Qté","Prix U.","HT","idAbonnement"};
		// Taille des colonnes
		String [] tailleColonnes = {"100","100","100","100","100","100","100","100","100","100","100","100","100","0"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"codeTAbonnement","codeSupportAbon","codeArticle","codeTiers","nomTiers","debutPeriode","finPeriode","dateFinCalcul","debAbonnement","finAbonnement",
				"qteLDocument","prixULDocument","mtHtLDocument","idAbonnement"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionResultats().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionResultats().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		tableViewer.selectionGrille(0);
		selection = ViewersObservables.observeSingleSelection(tableViewer);
		LigneLabelProvider.bind(tableViewer, new WritableList(modelLDocument, IHMLEcheance.class),
				BeanProperties.values(idColonnes));

		//TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[1]);
		tableViewer.getTable().getColumns()[titreColonnes.length-1].setResizable(false);
		tableViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMLEcheance)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMLEcheance)element).getAccepte())
					return true;
				return false;
			}
		});
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkAccept(((IHMLEcheance)event.getElement()).getIdLEcheance(),event.getChecked());
				
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
				((IHMLEcheance)selection.getValue()).setAccepte(check);
				Object entity=rechercheEntity(idDocument);
				((TaLEcheance)entity).setAccepte(check);
				initTotaux();
			}
		} catch (Exception e) {
			logger.error("", e);
		}		
	}
	
	public Object recherche( Integer value) {
	boolean trouve = false;
	int i = 0;
	List<IHMLEcheance> model=modelLDocument;
	while(!trouve && i<model.size()){
		try {
			if(PropertyUtils.getProperty(model.get(i), Const.C_ID_L_ECHEANCE)==value) {
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
	
	public TaLEcheance rechercheEntity( Integer value) {
		boolean trouve = false;
		int i = 0;
		TaLEcheance objet;
		List<TaLEcheance> model=listeLEcheance;
		while(!trouve && i<model.size()){
			try {
				objet=model.get(i);
				if(objet!=null && objet.getIdLEcheance()==(value)){
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

	
	public class MapperLigneDocumentSelectionIHMDocumentSelection implements IlgrMapper<IHMLEcheance, TaLEcheance> {

		public List<IHMLEcheance> listeEntityToDto(LinkedList<TaLEcheance> l) {
			List<IHMLEcheance> res = new ArrayList<IHMLEcheance>(0);
			for (TaLEcheance document : l) {
				res.add(entityToDto(document));
			}
			return res;
		}

		public IHMLEcheance entityToDto(TaLEcheance e) {
			LgrDozerMapper<TaLEcheance,IHMLEcheance> mapper = new LgrDozerMapper<TaLEcheance,IHMLEcheance>();
			IHMLEcheance documentSelectionIHM = new IHMLEcheance();
			mapper.map(e,documentSelectionIHM);
			documentSelectionIHM.setCodeSupportAbon(e.getTaAbonnement().getTaSupportAbon().getCodeSupportAbon());
			return documentSelectionIHM;
		}

		@Override
		public TaLEcheance dtoToEntity(IHMLEcheance e) {
			LgrDozerMapper<IHMLEcheance,TaLEcheance> mapper = new LgrDozerMapper<IHMLEcheance,TaLEcheance>();
			TaLEcheance l = new TaLEcheance();
			mapper.map(e, l);
			return l;
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

//	public void print(Table table) {
//		
//		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay(), SWT.SHELL_TRIM);
//		shell.setText("PagePrintExample.java");
//		shell.setLayout(new GridLayout());
//		shell.setSize(600, 800);
//
//
//		
//		final PrintJob job = new PrintJob("PagePrintExample.java",
//				createPrint(table));
//		job.setOrientation(PaperClips.ORIENTATION_LANDSCAPE);
//		
//		Composite buttonPanel = new Composite(shell, SWT.NONE);
//		buttonPanel
//				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		buttonPanel.setLayout(new RowLayout(SWT.HORIZONTAL));
//
//		final PrintPreview preview = new PrintPreview(shell, SWT.BORDER);
//
//		Button prev = new Button(buttonPanel, SWT.PUSH);
//		prev.setText("<< Prev");
//		prev.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				preview.setPageIndex(Math.max(preview.getPageIndex() - 1, 0));
//			}
//		});
//
//		Button next = new Button(buttonPanel, SWT.PUSH);
//		next.setText("Next >>");
//		next.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				preview.setPageIndex(Math.min(preview.getPageIndex() + 1,
//						preview.getPageCount() - 1));
//			}
//		});
//
//		Button print = new Button(buttonPanel, SWT.PUSH);
//		print.setText("Print");
//		print.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				
//				PrintDialog dialog = new PrintDialog( vue.getForm().getShell(), SWT.NONE );
//		        PrinterData printerData = dialog.open();
//		        if ( printerData != null ) {
//		        	PaperClips.print(job, printerData);
//		        }				
//				
//			}
//		});
//
//		preview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		preview.setFitHorizontal(true);
//		preview.setFitVertical(true);
//		preview.setPrintJob(job);
//
//		shell.open();
//
//	}
//	
//	public  Print createPrint(Table table) {
//		PageDecoration header = createHeader();
//		Print body = createBody(table);
//		PageDecoration footer = createFooter();
//
//		PagePrint page = new PagePrint(header, body, footer);
//		page.setHeaderGap(72 / 4);
//		page.setFooterGap(72 / 8);
//
//		return page;
//	}
//	
//	public Print createPrinthh() {
//		// Using "preferred" size columns, to force the document to be wider
//		// than
//		// the page. In most
//		// cases it is recommended to use "d" for "default" columns, which can
//		// shrink when needed.
//		DefaultGridLook look = new DefaultGridLook();
//		look.setCellBorder(new LineBorder());
//		GridPrint grid = new GridPrint(look);
//
//		final int ROWS = 60;
//		final int COLS = 10;
//
//		for (int i = 0; i < COLS; i++)
//			grid.addColumn("p");
//		for (int r = 0; r < ROWS; r++)
//			for (int c = 0; c < COLS; c++)
//				grid.add(new TextPrint("Row " + r + " Col " + c));
//
//		// Give entire grid a light green background.
//		return new BigPrint(grid);
//	}
//
//	public  PageDecoration createHeader() {
//		PageDecoration header = new PageDecoration() {
//			public Print createPrint(PageNumber pageNumber) {
//				// Only show a header on the first page
//				if (pageNumber.getPageNumber() == 0) {
////					ImageData imageData = new ImageData(PagePrintExample.class
////							.getResourceAsStream("logo.png"));
////					ImagePrint image = new ImagePrint(imageData);
////					image.setDPI(300, 300);
////					return image;
//				}
//
//				return null;
//			}
//		};
//		return header;
//	}
//
//	public  PageDecoration createFooter() {
//		PageDecoration footer = new PageDecoration() {
//			public Print createPrint(PageNumber pageNumber) {
//				GridPrint grid = new GridPrint("d:g, r:d");
//				grid.add(new TextPrint(
//						"Bureau de gestion"));
//				grid.add(new PageNumberPrint(pageNumber, SWT.RIGHT));
//				return grid;
//			}
//		};
//		return footer;
//	}
//	
//	public  Print createBody(Table table) {
//		return createPrintTable(table);
//	}
//
//	
//	public Print createPrintTable( Table table ) {
//	    // Create GridPrint with all columns at default size.
//
//	    DefaultGridLook look = new DefaultGridLook();
//	    look.setCellBorder( new LineBorder() );
//	    RGB background = table.getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ).getRGB();
//	    look.setHeaderBackground( background );
//	    look.setFooterBackground( background );
//	    
//
//	    GridPrint grid = new GridPrint( look );
//
//	    // Add header and footer to match table column names.
//	    TableColumn[] columns = table.getColumns();
//	    for ( int i = 0; i < columns.length; i++ ) {
//	      TableColumn col = columns[i];
//
//	      // Add the column to the grid with alignment applied, default width, no
//	      // weight
//	      grid.addColumn( new GridColumn( col.getAlignment(), SWT.DEFAULT, 0 ) );
////	      grid.addColumn("p");
//
//	      Print cell = createCell( col.getImage(), col.getText(), SWT.CENTER );
//	      grid.addHeader( cell );
//	      grid.addFooter( cell );
//	    }
//
//	    // Add content rows
//	    TableItem[] items = table.getItems();
//	    for ( int i = 0; i < items.length; i++ ) {
//	      TableItem item = items[i];
//	      for ( int j = 0; j < columns.length; j++ )
//	        grid.add( createCell( item.getImage( j ), item.getText( j ), columns[j].getAlignment() ) );
//	    }
//	    
//	    return new ScalePrint(grid);
////	    return new BigPrint(grid);
//	  }
//	
//	  public Print createCell( Image image, String text, int align ) {
//		    if ( image == null )
//		      return new TextPrint( text, align );
//
//		    GridPrint grid = new GridPrint( "p, d" );
//		    grid.add( new ImagePrint( image.getImageData(), image.getDevice().getDPI() ) );
//		    grid.add( new TextPrint( text, align ) );
//		    return grid;
//		  }
//	  
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
		EntityTransaction transaction = taLEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				taLEcheanceDAO.begin(transaction);
				TaArticleDAO daoArticle = new TaArticleDAO();
				
				//enregistrer les lignes d'échéance
				for (IHMLEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						taLEcheance = new TaLEcheance();
						taLEcheance=mapper.dtoToEntity(objet);
						taLEcheance.setTaAbonnement(taAbonnementDAO.findById(objet.getIdAbonnement()));
						taLEcheance.setTaArticle(daoArticle.findByCode(objet.getCodeArticle()));
						
						if(taLEcheance.getVersionObj()==null)taLEcheance.setVersionObj(0);
						
						if((taLEcheanceDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
							taLEcheance=taLEcheanceDAO.enregistrerMerge(taLEcheance);
						}
						else taLEcheance=taLEcheanceDAO.enregistrerMerge(taLEcheance);
					}
				}
				taLEcheanceDAO.commit(transaction);
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
		EntityTransaction transaction = taLEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				taLEcheanceDAO.begin(transaction);
				TaArticleDAO daoArticle = new TaArticleDAO();
				
				//enregistrer les lignes d'échéance
				for (IHMLEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						taLEcheance =taLEcheanceDAO.findById(objet.getIdLEcheance());
//						taLEcheance.setTaAbonnement(taAbonnementDAO.findById(objet.getIdAbonnement()));
//						taLEcheance.setTaArticle(daoArticle.findByCode(objet.getCodeArticle()));
//						
						
						taLEcheanceDAO.supprimer(taLEcheance);
					}
				}
				taLEcheanceDAO.commit(transaction);
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
		exportCSV(modelLDocument);
	}


	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actRefresh() throws Exception {
		try{	
			Integer idActuel=0;
			if(taLEcheance!=null)
				idActuel =taLEcheance.getIdLEcheance();				
			WritableList writableListFacture = new WritableList(modelLDocument, IHMLEcheance.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(idActuel!=null && writableListFacture.size()>0) {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_ID_L_ECHEANCE
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
		List<IHMLEcheance> model=modelLDocument;
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

	
	
	public void exportCSV(List<IHMLEcheance> l) {
		try {
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
			// Configuration de la fenêtre
			dd.setText("Exporter en fichier csv");
			dd.setFileName("*.csv");
			dd.setFilterExtensions(new String[] { "*.csv" });
			dd.setOverwrite(true);

			// Récupération des informations
			String repDestination = "";
//			String repDestination = GestionDossierPlugin.getDefault().getPreferenceStore().getString("repertoire_w");
			if (repDestination.equals("")) {
				repDestination = Platform.getInstanceLocation().getURL().getFile();
			}
			dd.setFilterPath(LibChaine.pathCorrect(repDestination));
			String choix = dd.open();
			System.err.println(choix);

			// Si la fenetre de dialogue n'a pas été fermée volontairement
			String filecontent = "";
			if (choix != null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				

				filecontent += "typeAbonnement;";
				filecontent += "codeSupport;";
				filecontent += "code article;";
				filecontent += "codeTiers;";
				filecontent += "nomTiers;";
				filecontent += "début période;";
				filecontent += "fin période;";
				filecontent += "fin calcul;";
				filecontent += "début abonnement;";
				filecontent += "fin abonnement;";
				filecontent += "Qté;";
				filecontent += "Prix U.;";
				filecontent += "HT;";
				
			
				for (IHMLEcheance m : l) {
					filecontent += "\n";
					filecontent += m.getCodeTAbonnement()!= null ? m.getCodeTAbonnement()+";" : ";";
					filecontent += (m.getCodeSupportAbon()!=null)? m.getCodeSupportAbon()+";" : ";";
					filecontent += (m.getCodeArticle()!=null ) 	? m.getCodeArticle()+";" : ";";
					filecontent += (m.getCodeTiers()!=null)? m.getCodeTiers()+";" : ";";
					filecontent += (m.getNomTiers()!=null) ? m.getNomTiers()+";" : ";";
					filecontent += (m.getDebutPeriode()!= null ) ? LibDate.dateToString(m.getDebutPeriode())+";" : ";";
					filecontent += (m.getFinPeriode()!= null ) ? LibDate.dateToString(m.getFinPeriode())+";" : ";";
					filecontent += (m.getDateFinCalcul()!= null ) ? LibDate.dateToString(m.getDateFinCalcul())+";" : ";";
					filecontent += (m.getDebAbonnement()!= null ) ? LibDate.dateToString(m.getDebAbonnement())+";" : ";";
					filecontent += (m.getFinAbonnement()!= null ) ? LibDate.dateToString(m.getFinAbonnement())+";" : ";";
					filecontent += m.getQteLDocument()!= null ? m.getQteLDocument()+";" : ";";
					filecontent += m.getPrixULDocument()!= null ? m.getPrixULDocument()+";" : ";";
					filecontent += m.getMtHtLApresRemiseGlobaleDocument()!= null ? m.getMtHtLApresRemiseGlobaleDocument()+";" : ";";

				}
			

				PrintStream out;
				out = new PrintStream(new FileOutputStream(choix), true, "UTF-8");
				out.print(filecontent);
				out.flush();
				out.close();
				out = null;
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Exportation",
						"Exportation terminée.");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("",e1);
		} catch (FileNotFoundException e1) {
			logger.error("",e1);
		} finally {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}


	}
}
