                                                      /**
 * ClientController.java
 */
package fr.legrain.abonnement.controllers.renouvellementAbonnement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
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
import fr.legrain.abonnement.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.abonnement.ecrans.PaFormPageAvis;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.avisecheance.editor.AvisEcheanceMultiPageEditor;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.divers.IImpressionDocumentTiers;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaEtat;
import fr.legrain.documents.dao.TaEtatDAO;
import fr.legrain.documents.dao.TaInfosAvisEcheance;
import fr.legrain.documents.dao.TaLAvisEcheance;
import fr.legrain.documents.dao.TaLEcheance;
import fr.legrain.documents.dao.TaLEcheanceDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.InfosEmail;
import fr.legrain.edition.actions.InfosFax;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.preferences.PreferenceConstants;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEnteteAvisEcheance;
import fr.legrain.gestCom.Module_Document.IHMLAvisEcheance;
import fr.legrain.gestCom.Module_Document.IHMLEcheance;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;
import fr.legrain.gestCom.global.dao.TaConstDAO;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMiniEditable;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.grille.EditionGrille;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.licence.editors.SupportAbonMultiPageEditor;
import fr.legrain.pointLgr.dao.TaComptePoint;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.pointLgr.dao.TaTypeMouvPoint;
import fr.legrain.pointLgr.dao.TaTypeMouvPointDAO;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * @author nicolas
 *
 */
public class LigneFactureController extends AbstractControllerMiniEditable {

	static Logger logger = Logger.getLogger(LigneFactureController.class.getName());	

	private static String libellePointCumul="Remise point cumul";
//	private static BigDecimal C_conversion_point=BigDecimal.valueOf(12.5);
	private Class objetIHM = null;
	private TaLEcheanceDAO taLEcheanceDAO = null;
	private TaConstDAO taConstDao = null;
	private TaLEcheance taLEcheance = null;
	private TaAvisEcheanceDAO taAvisEcheanceDAO = null;
	private TaAvisEcheance taAvisEcheance = null;
	protected List<IHMEnteteAvisEcheance> modelLDocument = null;
	protected LinkedList<TaAvisEcheance> listDocumentSelection = new LinkedList<TaAvisEcheance>();
//	Map<TaAvisEcheance,TaComptePoint> listePointBonusCumul=new LinkedHashMap<TaAvisEcheance,TaComptePoint>();
	Map<TaComptePoint,TaAvisEcheance> listePointBonusCumul=new LinkedHashMap<TaComptePoint,TaAvisEcheance>();
	protected List<TaLEcheance> listeLDoc;
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipal masterController = null;
	protected PaFormPageAvis vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewer;
	private LgrTableViewer tableViewerDetail;
//	protected Boolean ImpressionUniquement=false; 
	IObservableValue selection;
	private MapperLigneDocumentSelectionIHMDocumentSelection mapper = new MapperLigneDocumentSelectionIHMDocumentSelection();
	private String libelleEcran = "";
	
	private String commentaire="";
	private String typeAdresseFacturation="";
	private String typeAdresseLivraison="";
	
	private BigDecimal totalHT = new BigDecimal(0);
	private BigDecimal totalTTC = new BigDecimal(0);
	
	private LgrDozerMapper<TaAvisEcheance,TaInfosAvisEcheance> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaAvisEcheance, TaInfosAvisEcheance>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosAvisEcheance> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosAvisEcheance>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosAvisEcheance> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosAvisEcheance>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosAvisEcheance> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosAvisEcheance>();
	private LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();


	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();	
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();
	protected HandlerInverser handlerInverser = new HandlerInverser();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_DETAIL = "fr.legrain.gestionCommerciale.toutCocherDetail";
	protected HandlerToutCocherDetail handlerToutCocherDetail = new HandlerToutCocherDetail();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_DETAIL = "fr.legrain.gestionCommerciale.toutDeCocherDetail";
	protected HandlerToutDeCocherDetail handlerToutDeCocherDetail = new HandlerToutDeCocherDetail();

	public static final String C_COMMAND_DOCUMENT_INVERSER_COCHER_DETAIL = "fr.legrain.gestionCommerciale.InverserDetail";
	protected HandlerInverserDetail handlerInverserDetail = new HandlerInverserDetail();
	
	public static final String C_COMMAND_DOCUMENT_ANNULER_AVIS = "fr.legrain.Publipostage.annulerAvis";
	protected HandlerAnnulerAvis handlerAnnulerAvis = new HandlerAnnulerAvis();
	
	
	public static final String C_COMMAND_DOCUMENT_REINJECTER_AVIS = "fr.legrain.Publipostage.reinjecterAvis";
	protected HandlerReinjecterAvis handlerReinjecterAvis = new HandlerReinjecterAvis();
	
	
	protected Boolean suppressionUniquement=false; 
		protected Boolean ImpressionUniquement=false; 
    
	private BaseImpressionEdition impressionEdition = null;
	private IImpressionDocumentTiers impressionDocument = null; 
	/* Constructeur */
	public LigneFactureController(FormPageControllerPrincipal masterContoller, PaFormPageAvis vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					objet.setAccepte(!objet.getAccepte());
					for (IHMLAvisEcheance ihmlAvis : objet.getLignesAvis()) {
						ihmlAvis.setAccepte(objet.getAccepte());
					}
				}
				actRefresh();
				initTotaux();
				} catch (Exception e) {
					logger.error("", e);
				}
				return event;
			}
		}
	
	private class HandlerToutCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IHMEnteteAvisEcheance objet :modelLDocument) {
					objet.setAccepte(true);	
					for (IHMLAvisEcheance ihmlAvis : objet.getLignesAvis()) {
						ihmlAvis.setAccepte(objet.getAccepte());
					}
				}
				actRefresh();
				initTotaux();
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
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					objet.setAccepte(false);
					for (IHMLAvisEcheance ihmlAvis : objet.getLignesAvis()) {
						ihmlAvis.setAccepte(objet.getAccepte());
					}
				}
				actRefresh();
				initTotaux();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	private class HandlerInverserDetail extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {			
				
				for (IHMLAvisEcheance objet : ((IHMEnteteAvisEcheance)selection.getValue()).getLignesAvis()) {
					objet.setAccepte(!objet.getAccepte());							
				}
				actRefresh();
				initTotaux();
				} catch (Exception e) {
					logger.error("", e);
				}
				return event;
			}
		}
	
	private class HandlerToutCocherDetail extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (IHMLAvisEcheance objet :((IHMEnteteAvisEcheance)selection.getValue()).getLignesAvis()) {
					objet.setAccepte(true);													
				}
				actRefresh();
				initTotaux();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerToutDeCocherDetail extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
//				for (IHMLRemise objet : IHMmodel()) {
				for (IHMLAvisEcheance objet : ((IHMEnteteAvisEcheance)selection.getValue()).getLignesAvis()) {
					objet.setAccepte(false);
				}
				actRefresh();
				initTotaux();
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
			taAvisEcheanceDAO=new TaAvisEcheanceDAO();
			taConstDao=new TaConstDAO();
			initSashFormWeight();
			setTableViewerStandard(tableViewer);

			initMapComposantChamps();
//			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);
			
			impressionEdition = new BaseImpressionEdition(vue.getCompositeSectionResultats().getCompoEcran().getShell(),getEm());


			initEtatBouton(true);
		} catch (Exception e) {
			logger.error("Erreur : PaTiersController", e);
		}
	}

	
	public void initialiseModelIHM(int nbResult) {
		if(suppressionUniquement){
			initialiseModelIHMExistant(nbResult);
		}
		else {
			initialiseModelIHMNouveau(nbResult);
		}
		vue.getCompositeSectionResultats().getBtnCreer().setVisible(!suppressionUniquement);
	}
	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHMNouveau(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		//this.nbResult = nbResult;
		TaTiers tiersCourant=null;
		TaLEcheance objetOld = null;
		int numLigne=0;
		int numPiece=1;
		Date dateDoc = new Date();
		
		BigDecimal taux_conversion=taConstDao.remonteConversionPointBonus();
		taLEcheanceDAO = new TaLEcheanceDAO(masterController.getMasterDAOEM());
		listePointBonusCumul.clear();
		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

				
		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLDoc = taLEcheanceDAO.rechercheLigneRenouvellementAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTAbonnement().get(masterController.paramControllerMini.getCodeEtat()),"%",false);
		} else {
			listeLDoc = taLEcheanceDAO.rechercheLigneRenouvellementAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTAbonnement().get(masterController.paramControllerMini.getCodeEtat()),
					vue.getCompositeSectionParam().getTfCodeTiers().getText(),false);
		}
		
		// Liste qui va contenir les informations utiles pour les avis 
		listDocumentSelection = new LinkedList<TaAvisEcheance>();
		TaAvisEcheance documentSelection = null;
		TaComptePointDAO comptePointDao = new TaComptePointDAO();
		List<TaComptePoint> listePointBonus=new LinkedList<TaComptePoint>();
		Map<String,BigDecimal> listePointBonusRegroupe=new LinkedHashMap<String,BigDecimal>();

		TaLEcheance object = null;
		BigDecimal pointBonus=new BigDecimal(0);
		TaArticleDAO articleDao = new TaArticleDAO();
		TaArticle ArticlePointBonus=articleDao.remonteArticlePointBonus();
		//		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < listeLDoc.size() /*&& i < nbResult*/; i ++){
			BigDecimal pourcIndice = new BigDecimal(0);
			BigDecimal pourcPartenaire = new BigDecimal(0);
			objetOld=object;
			object = listeLDoc.get(i);					
			numLigne++;
//			if( object.getTaAbonnement()!=null){

				if(tiersCourant==null || tiersCourant!=object.getTaAbonnement().getTaTiers()){
					//rechercher les points bonus et si >0 rajouter une ligne
					if(tiersCourant!=null){
						listePointBonus=comptePointDao.findByTiersAndDate2(tiersCourant.getIdTiers(),LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb())
								,LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()),false);
//						TaArticle oldArticlePoint=null;
						TaTypeMouvPoint oldTypeMouvPoint=null;
						listePointBonusRegroupe.clear();
						for (TaComptePoint taComptePoint : listePointBonus) {
							if(oldTypeMouvPoint==null ||(taComptePoint.getTaTypeMouvPoint()!=null && 
									taComptePoint.getTaTypeMouvPoint().getTypeMouv().equals(oldTypeMouvPoint.getTypeMouv()))){
//								listePointBonusRegroupe.put(taComptePoint.getTaTypeMouvPoint().getTaArticle(),pointBonus);
								pointBonus=new BigDecimal(0);
								oldTypeMouvPoint=taComptePoint.getTaTypeMouvPoint();	
							}
							pointBonus=pointBonus.add(taComptePoint.getPoint());
							if(!listePointBonusRegroupe.containsKey(taComptePoint.getTaTypeMouvPoint().getTypeMouv()))
								listePointBonusRegroupe.put(taComptePoint.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
						}
						pointBonus=comptePointDao.calculPointIndice(documentSelection);
						BigDecimal pointBonusTemp=null;
						if(pointBonus!=null && pointBonus.signum()>0){
							TaComptePoint comptePointCumul=new TaComptePoint();
							comptePointCumul.setTaAvisEcheance(documentSelection);
							comptePointCumul.setPoint(pointBonus);
							comptePointCumul.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EI"));							
							listePointBonusCumul.put(comptePointCumul,documentSelection);
							if(!listePointBonusRegroupe.containsKey(comptePointCumul.getTaTypeMouvPoint().getTypeMouv()))
								listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
							else {
								pointBonusTemp=listePointBonusRegroupe.get(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
								if(pointBonusTemp!=null){
									pointBonus=pointBonus.add(pointBonusTemp);
								}
								listePointBonusRegroupe.remove(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
								listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(), pointBonus);
							}
							
						}
						pointBonus=comptePointDao.calculPointCogere(documentSelection);
						pointBonusTemp=null;
						if(pointBonus!=null && pointBonus.signum()>0){
							TaComptePoint comptePointCumul=new TaComptePoint();
							comptePointCumul.setTaAvisEcheance(documentSelection);
							comptePointCumul.setPoint(pointBonus);
							comptePointCumul.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("CO"));							
							listePointBonusCumul.put(comptePointCumul,documentSelection);
							if(!listePointBonusRegroupe.containsKey(comptePointCumul.getTaTypeMouvPoint().getTypeMouv()))
								listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
							else {
								pointBonusTemp=listePointBonusRegroupe.get(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
								if(pointBonusTemp!=null){
									pointBonus=pointBonus.add(pointBonusTemp);
								}
								listePointBonusRegroupe.remove(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
								listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(), pointBonus);
							}
							
						}
						for (TaLAvisEcheance lAvis : documentSelection.getLignes()) {											
						pointBonus=comptePointDao.calculPointPartenaire(lAvis.getTaLEcheance().getTaAbonnement(),lAvis,listePointBonusRegroupe);
						pointBonusTemp=null;
						if(pointBonus!=null && pointBonus.signum()>0){
							TaComptePoint comptePointCumul=new TaComptePoint();
							comptePointCumul.setTaAvisEcheance(documentSelection);
							comptePointCumul.setPoint(pointBonus);
							comptePointCumul.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EG"));							
							listePointBonusCumul.put(comptePointCumul,documentSelection);
							if(!listePointBonusRegroupe.containsKey(comptePointCumul.getTaTypeMouvPoint().getTypeMouv()))
								listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
							else {
								pointBonusTemp=listePointBonusRegroupe.get(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
								if(pointBonusTemp!=null){
									pointBonus=pointBonus.add(pointBonusTemp);
								}
								listePointBonusRegroupe.remove(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
								listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(), pointBonus);
							}
							
						}
						}
						pointBonus=BigDecimal.ZERO;
						BigDecimal pointDiv=null;
						for (String codeArticlePoint : listePointBonusRegroupe.keySet()) {
							if(codeArticlePoint!=null){
								if(listePointBonusRegroupe.get(codeArticlePoint)!=null)
									pointBonus=pointBonus.add(listePointBonusRegroupe.get(codeArticlePoint));
							}
						}
						if(pointBonus!=null && !pointBonus.equals(BigDecimal.ZERO)){
							pointDiv=pointBonus.divide(taux_conversion,MathContext.DECIMAL128).multiply(BigDecimal.valueOf(-1));
							//s'occuper de la ligne de bonus
							TaLAvisEcheance ligne = new TaLAvisEcheance(true);
							try {
								ligne.setTaLEcheance(object);
								ligne.setTaLEcheance(null);
								ligne.setTaDocument(documentSelection);
								ligne.setTaArticle(ArticlePointBonus);
								ligne.setLibLDocument(ArticlePointBonus.getLibellecArticle());
								ligne.setQteLDocument(BigDecimal.ONE);
								if(pointDiv!=null && !pointDiv.equals(BigDecimal.ZERO))pointBonus=(pointDiv.divide(BigDecimal.valueOf(1).add((ligne.getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
								)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP));
								ligne.setPrixULDocument(pointBonus);												
								ligne.setNumLigneLDocument(numLigne);
								ligne.setIdLDocument(numLigne);
								ligne.calculMontant();
								ligne.setAccepte(true);
								documentSelection.addLigne(ligne);
								numLigne++;
							} catch (Exception e) {
								logger.error("", e);
							}
						}

//						for (String ArticlePoint : listePointBonusRegroupe.keySet()) {
//							if(ArticlePoint!=null){
//								pointBonus=listePointBonusRegroupe.get(ArticlePoint);
//								BigDecimal pointDiv=null;
//								if(pointBonus!=null && !pointBonus.equals(BigDecimal.ZERO))pointDiv=pointBonus.divide(BigDecimal.valueOf(10)).multiply(BigDecimal.valueOf(-1));
//								//s'occuper de la ligne de bonus
//								TaLAvisEcheance ligne = new TaLAvisEcheance(true);
//								try {
////									ligne.setTaLEcheance(objetOld);
//									ligne.setTaLEcheance(null);
//									ligne.setTaDocument(documentSelection);
//									ligne.setTaArticle(ArticlePointBonus);
//									ligne.setLibLDocument(ArticlePointBonus.getLibellecArticle());
//									ligne.setQteLDocument(BigDecimal.ONE);
//									if(pointDiv!=null && !pointDiv.equals(BigDecimal.ZERO))pointBonus=(pointDiv.divide(BigDecimal.valueOf(1).add((ligne.getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
//											)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP));
//									ligne.setPrixULDocument(pointBonus);
//									ligne.setNumLigneLDocument(numLigne);
//									ligne.setIdLDocument(numLigne);
//									ligne.calculMontant();
//									ligne.setAccepte(true);
//									documentSelection.addLigne(ligne);
//									numLigne++;
//								} catch (Exception e) {
//									logger.error("", e);
//								}
//							}
//						}
					}
					if(documentSelection!=null){
						documentSelection.calculeTvaEtTotaux();
					}					
					
					documentSelection = new TaAvisEcheance(true);
					listDocumentSelection.add(documentSelection);
					documentSelection.setIdDocument(numPiece);
					numLigne=1;
					numPiece++;
				}					
				tiersCourant=object.getTaAbonnement().getTaTiers();				
//			}			
			documentSelection.setTaTiers(tiersCourant);
			documentSelection.setDateDocument(dateDoc);
			documentSelection.setDateEchDocument(dateDoc);
			documentSelection.setAccepte(true);

			//s'occuper des lignes
			TaLAvisEcheance ligne = new TaLAvisEcheance(true);
			try {
				ligne.setTaLEcheance(object);
				ligne.setTaDocument(documentSelection);
				ligne.setTaArticle(object.getTaArticle());
				ligne.setQteLDocument(object.getQteLDocument());
				ligne.setPrixULDocument(object.getPrixULDocument());
				if(object.getPourcIndice()!=null)pourcIndice=object.getPourcIndice();
				if(object.getPourcPartenaire()!=null)pourcPartenaire=object.getPourcPartenaire();
				ligne.setRemTxLDocument(pourcIndice.add(pourcPartenaire));
				ligne.setU1LDocument(object.getU1LDocument());
				ligne.setLibLDocument(object.getTaAbonnement().getTaSupportAbon().getCodeSupportAbon()+" : "+object.getLibLDocument());
				ligne.setNumLigneLDocument(numLigne);
				ligne.setIdLDocument(numLigne);
				ligne.calculMontant();
				ligne.setAccepte(true);
				documentSelection.addLigne(ligne);
				numLigne++;
			} catch (Exception e) {
				logger.error("", e);
			}	
			documentSelection.calculeTvaEtTotaux();

		}
		pointBonus=new BigDecimal(0);
		//rechercher les points bonus et si >0 rajouter une ligne
		if(tiersCourant!=null){
			listePointBonus=comptePointDao.findByTiersAndDate2(tiersCourant.getIdTiers(),LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb())
					,LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()),false);
//			TaArticle oldArticlePoint=null;
			TaTypeMouvPoint oldTypeMouvPoint=null;
			TaComptePoint taComptePointTemp=null;
			listePointBonusRegroupe.clear();
			for (TaComptePoint taComptePoint : listePointBonus) {
				taComptePointTemp=taComptePoint;
				if(oldTypeMouvPoint==null ||(taComptePoint.getTaTypeMouvPoint()!=null  && 
						!taComptePoint.getTaTypeMouvPoint().getTypeMouv().equals(oldTypeMouvPoint.getTypeMouv()))){
					listePointBonusRegroupe.put(taComptePoint.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
					pointBonus=new BigDecimal(0);
					oldTypeMouvPoint=taComptePoint.getTaTypeMouvPoint();					
				}
				pointBonus=pointBonus.add(taComptePoint.getPoint());
			}
			if(taComptePointTemp!=null)//!listePointBonusRegroupe.containsValue(pointBonus)&&
				listePointBonusRegroupe.put(taComptePointTemp.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
			taComptePointTemp=null;
			pointBonus=comptePointDao.calculPointIndice(documentSelection);
			BigDecimal pointBonusTemp=null;
			if(pointBonus!=null && pointBonus.signum()>0){
				TaComptePoint comptePointCumul=new TaComptePoint();
				comptePointCumul.setTaAvisEcheance(documentSelection);
				comptePointCumul.setPoint(pointBonus);
				comptePointCumul.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EI"));
				listePointBonusCumul.put(comptePointCumul,documentSelection);
				if(!listePointBonusRegroupe.containsKey(comptePointCumul.getTaTypeMouvPoint().getTypeMouv()))
					listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
				else {
					pointBonusTemp=listePointBonusRegroupe.get(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
					if(pointBonusTemp!=null){
						pointBonus=pointBonus.add(pointBonusTemp);
					}
					listePointBonusRegroupe.remove(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
					listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(), pointBonus);
				}
				
			}
			if(!listePointBonusRegroupe.containsValue(pointBonus)&&taComptePointTemp!=null)
				listePointBonusRegroupe.put(taComptePointTemp.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
			taComptePointTemp=null;
			pointBonus=comptePointDao.calculPointCogere(documentSelection);
			pointBonusTemp=null;
			if(pointBonus!=null && pointBonus.signum()>0){
				TaComptePoint comptePointCumul=new TaComptePoint();
				comptePointCumul.setTaAvisEcheance(documentSelection);
				comptePointCumul.setPoint(pointBonus);
				comptePointCumul.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("CO"));							
				listePointBonusCumul.put(comptePointCumul,documentSelection);
				if(!listePointBonusRegroupe.containsKey(comptePointCumul.getTaTypeMouvPoint().getTypeMouv()))
					listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
				else {
					pointBonusTemp=listePointBonusRegroupe.get(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
					if(pointBonusTemp!=null){
						pointBonus=pointBonus.add(pointBonusTemp);
					}
					listePointBonusRegroupe.remove(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
					listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(), pointBonus);
				}
				
			}
			if(!listePointBonusRegroupe.containsValue(pointBonus)&&taComptePointTemp!=null)
				listePointBonusRegroupe.put(taComptePointTemp.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
			
			for (TaLAvisEcheance ligne : documentSelection.getLignes()) {
				taComptePointTemp=null;
				pointBonus=null;
				if(ligne.getTaLEcheance().getTaAbonnement()!=null)
					pointBonus=comptePointDao.calculPointPartenaire(ligne.getTaLEcheance().getTaAbonnement(),ligne,listePointBonusRegroupe);
				pointBonusTemp=null;
				if(pointBonus!=null && pointBonus.signum()>0){
					TaComptePoint comptePointCumul=new TaComptePoint();
					comptePointCumul.setTaAvisEcheance(documentSelection);
					comptePointCumul.setPoint(pointBonus);
					comptePointCumul.setTaTypeMouvPoint(new TaTypeMouvPointDAO().findByCode("EG"));							
					listePointBonusCumul.put(comptePointCumul,documentSelection);
					if(!listePointBonusRegroupe.containsKey(comptePointCumul.getTaTypeMouvPoint().getTypeMouv()))
						listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(),pointBonus);
					else {
						pointBonusTemp=listePointBonusRegroupe.get(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
						if(pointBonusTemp!=null){
							pointBonus=pointBonus.add(pointBonusTemp);
						}
						listePointBonusRegroupe.remove(comptePointCumul.getTaTypeMouvPoint().getTypeMouv());
						listePointBonusRegroupe.put(comptePointCumul.getTaTypeMouvPoint().getTypeMouv(), pointBonus);
					}
					
				}			
			}
			
			pointBonus=BigDecimal.ZERO;
			BigDecimal pointDiv=null;
			for (String codeArticlePoint : listePointBonusRegroupe.keySet()) {
				if(codeArticlePoint!=null){
					if(listePointBonusRegroupe.get(codeArticlePoint)!=null)
						pointBonus=pointBonus.add(listePointBonusRegroupe.get(codeArticlePoint));
				}
			}
			if(pointBonus!=null && !pointBonus.equals(BigDecimal.ZERO)){
				pointDiv=pointBonus.divide(taux_conversion,MathContext.DECIMAL128).multiply(BigDecimal.valueOf(-1));
				//s'occuper de la ligne de bonus
				TaLAvisEcheance ligne = new TaLAvisEcheance(true);
				try {
					ligne.setTaLEcheance(object);
					ligne.setTaLEcheance(null);
					ligne.setTaDocument(documentSelection);
					ligne.setTaArticle(ArticlePointBonus);
					ligne.setLibLDocument(ArticlePointBonus.getLibellecArticle());
					ligne.setQteLDocument(BigDecimal.ONE);
					if(pointDiv!=null && !pointDiv.equals(BigDecimal.ZERO))pointBonus=(pointDiv.divide(BigDecimal.valueOf(1).add((ligne.getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
					)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP));
					ligne.setPrixULDocument(pointBonus);												
					ligne.setNumLigneLDocument(numLigne);
					ligne.setIdLDocument(numLigne);
					ligne.calculMontant();
					ligne.setAccepte(true);
					documentSelection.addLigne(ligne);
					numLigne++;
				} catch (Exception e) {
					logger.error("", e);
				}
			}
//			for (String codeArticlePoint : listePointBonusRegroupe.keySet()) {
//				if(codeArticlePoint!=null){
//					pointBonus=listePointBonusRegroupe.get(codeArticlePoint);
//					BigDecimal pointDiv=null;
//					if(pointBonus!=null && !pointBonus.equals(BigDecimal.ZERO))pointDiv=pointBonus.divide(BigDecimal.valueOf(10)).multiply(BigDecimal.valueOf(-1));
//					//s'occuper de la ligne de bonus
//					TaLAvisEcheance ligne = new TaLAvisEcheance(true);
//					try {
//						ligne.setTaLEcheance(object);
//						ligne.setTaLEcheance(null);
//						ligne.setTaDocument(documentSelection);
//						ligne.setTaArticle(ArticlePointBonus);
//						ligne.setLibLDocument(ArticlePointBonus.getLibellecArticle());
//						ligne.setQteLDocument(BigDecimal.ONE);
//						if(pointDiv!=null && !pointDiv.equals(BigDecimal.ZERO))pointBonus=(pointDiv.divide(BigDecimal.valueOf(1).add((ligne.getTauxTvaLDocument().divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
//						)),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP));
//						ligne.setPrixULDocument(pointBonus);												
//						ligne.setNumLigneLDocument(numLigne);
//						ligne.setIdLDocument(numLigne);
//						ligne.calculMontant();
//						ligne.setAccepte(true);
//						documentSelection.addLigne(ligne);
//						numLigne++;
//					} catch (Exception e) {
//						logger.error("", e);
//					}
//				}
//			}
		}
		if(documentSelection!=null){
			documentSelection.calculeTvaEtTotaux();		
		}

		//		for (TaAvisEcheance obj : listDocumentSelection) {
		//			total=total.add(obj.getNetHtCalc());
		//		}

		modelLDocument = new MapperLigneDocumentSelectionIHMDocumentSelection().listeEntityToDto(listDocumentSelection);
		//		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
		//		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(modelLDocument.size()));
		initTotaux();
	}
	
	public void initialiseModelIHMExistant(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		//this.nbResult = nbResult;
		listDocumentSelection = new LinkedList<TaAvisEcheance>();
		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText("0");
		vue.getCompositeSectionResultats().getTfNbLigne().setText("0");
		taAvisEcheanceDAO = new TaAvisEcheanceDAO(masterController.getMasterDAOEM());

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());
		List<TaAvisEcheance> listTemp;
		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listTemp = taAvisEcheanceDAO.rechercheDocumentTAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),"%",LibConversion.stringToBigDecimal(vue.getCompositeSectionParam().
							getTfMontantDepart().getText()),vue.getCompositeSectionParam().getBtnNonTransforme().getSelection());
		} else {
			listTemp = taAvisEcheanceDAO.rechercheDocumentTAbonnement(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,vue.getCompositeSectionParam().getCbEtat().getText(),vue.getCompositeSectionParam().getTfCodeTiers().getText(),
					LibConversion.stringToBigDecimal(vue.getCompositeSectionParam().getTfMontantDepart().getText()),
					vue.getCompositeSectionParam().getBtnNonTransforme().getSelection());
		}

		if(listTemp!=null){
		for (TaAvisEcheance taAvisEcheance : listTemp) {
			listDocumentSelection.add(taAvisEcheance);
		}
		}
		modelLDocument = mapper.listeEntityToDto(listDocumentSelection);
		initTotaux();
		
	}
	
	private void initTotaux(){
		BigDecimal total = new BigDecimal(0);
		Integer nbCoche=0;
		for (TaAvisEcheance obj : listDocumentSelection) {
			//obj.calculeTvaEtTotaux();
			total=total.add(obj.getNetHtCalc());
			if(obj.getAccepte())nbCoche++;
		}

		vue.getCompositeSectionResultats().getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(total));
		vue.getCompositeSectionResultats().getTfNbLigne().setText(LibConversion.integerToString(nbCoche));
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
					String valeurIdentifiant=null;
					String idEditor = null;
					if(!suppressionUniquement){
						valeurIdentifiant = vue.getCompositeSectionResultats().getTable().getSelection()[0].getText(
								getTableViewer().findPositionNomChamp("codeTiers")
								);
						idEditor = SupportAbonMultiPageEditor.ID_EDITOR;		
					}else{
						valeurIdentifiant = vue.getCompositeSectionResultats().getTable().getSelection()[0].getText(
								getTableViewer().findPositionNomChamp("codeDocument")
								);
						idEditor = AvisEcheanceMultiPageEditor.ID_EDITOR;	
					}

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
				mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerAnnulerAvis);
			}
			
			mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
			mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
			
			if(!suppressionUniquement)
				mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
			else mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerReinjecterAvis);
			
			mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
			
			mapCommand.put(C_COMMAND_DOCUMENT_TOUT_COCHER_DETAIL, handlerToutCocherDetail);//tout cocher Detail
			mapCommand.put(C_COMMAND_DOCUMENT_TOUT_DECOCHER_DETAIL, handlerToutDeCocherDetail);//tout décocher Detail
			mapCommand.put(C_COMMAND_DOCUMENT_INVERSER_COCHER_DETAIL, handlerInverserDetail);// Inverser Detail

			
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
			
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnEnregister(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnInserer(), C_COMMAND_DOCUMENT_INVERSER_COCHER_DETAIL);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnModifier(), C_COMMAND_DOCUMENT_TOUT_COCHER_DETAIL);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnSupprimer(), C_COMMAND_DOCUMENT_TOUT_DECOCHER_DETAIL);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
			mapActions.put(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
			

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
				vue.getCompositeSectionResultats().getCompoEcran().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
				vue.getCompositeSectionResultats().getCompoEcran().getBtnSupprimer().setText("Supprimer");
				vue.getCompositeSectionResultats().getCompoEcran().getBtnEnregister().setText("Annuler avis");
				vue.getCompositeSectionResultats().getCompoEcran().getBtnFermer().setText("Ré-injecter avis");
			}

			vue.getCompositeSectionResultats().getCompoEcran().getBtnModifier().setText("tout cocher");
			vue.getCompositeSectionResultats().getCompoEcran().getBtnInserer().setText("Inverser les cochés");
		
		
			
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXPORT));
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnModifier().setText("tout cocher");
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnSupprimer().setText("tout Décocher");
		vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnInserer().setText("Inverser les cochés");		
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
			
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnEnregister());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnInserer());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnModifier());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnSupprimer());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnFermer());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnAnnuler());
			listeComposantFocusable.add(vue.getCompositeSectionResultats().getCompoEcranDetail().getBtnImprimer());
		initImageBouton();
	}

	protected void initEtatBouton(boolean initFocus) {
		boolean trouve =modelLDocument.size()>0;
		enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,!ImpressionUniquement && trouve);//Inverser les cochés
		enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,!ImpressionUniquement && trouve);//Tout cocher
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,suppressionUniquement && trouve);//changer d'état
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,suppressionUniquement && trouve);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,!ImpressionUniquement && trouve);//tout décocher
		enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
		
		enableActionAndHandler(C_COMMAND_DOCUMENT_TOUT_COCHER_DETAIL,!ImpressionUniquement && trouve);
		enableActionAndHandler(C_COMMAND_DOCUMENT_TOUT_DECOCHER_DETAIL,!ImpressionUniquement && trouve);
		enableActionAndHandler(C_COMMAND_DOCUMENT_INVERSER_COCHER_DETAIL,!ImpressionUniquement && trouve);
	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		if(modelLDocument==null)modelLDocument= new ArrayList<IHMEnteteAvisEcheance>(0);
		setObjetIHM(IHMEnteteAvisEcheance.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code tiers","Nom tiers","code document","Date","Echéance","ht","Tva","TTC","Etat"};
		// Taille des colonnes
		String [] tailleColonnes = {"100","150","100","100","100","100","100","100","100"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"codeTiers","nomTiers","codeDocument","dateDocument","dateEchDocument","netHtCalc","netTvaCalc","netTtcCalc","codeEtat"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionResultats().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionResultats().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		LigneLabelProvider.bind(tableViewer, new WritableList(modelLDocument, IHMEnteteAvisEcheance.class),
				BeanProperties.values(idColonnes));

//		TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[0]);
//		etatColumn.setLabelProvider(new CellLabelProvider() {
//			@Override
//			public void update(ViewerCell cell) {
//				//cell.setText(((DocumentSelectionIHM) cell.getElement()).getEtat());
//			}
//		});

//		EditingSupport exampleEditingSupport = new ExampleEditingSupport(etatColumn.getViewer());
//		etatColumn.setEditingSupport(exampleEditingSupport);
//
//		//FancyToolTipSupport.enableFor(tableViewer,ToolTip.NO_RECREATE);
//		//ColumnViewerToolTipSupport.enableFor(tableViewer,ToolTip.NO_RECREATE);
//		CellLabelProvider labelProvider = new CellLabelProvider() {
//
//			public String getToolTipText(Object element) {
//				return ((IHMEnteteAvisEcheance)element).getCodeTiers();
//			}
//
//			public Point getToolTipShift(Object object) {
//				return new Point(-10, -10);
//			}
//
//			public int getToolTipDisplayDelayTime(Object object) {
//				return 1000;
//			}
//
//			public int getToolTipTimeDisplayed(Object object) {
//				return 10000;
//			}
//
//			public void update(ViewerCell cell) {
//				cell.setText(cell.getElement().toString());
//			}
//		};
		//TableViewerColumn tiersColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[1]);
		//tiersColumn.setLabelProvider(labelProvider);
		tableViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMEnteteAvisEcheance)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMEnteteAvisEcheance)element).getAccepte())
					return true;
				return false;
			}
		});
		
		bindDetail();
		
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkAccept(((IHMEnteteAvisEcheance)event.getElement()).getIdDocument(),event.getChecked());
				
			}
		});	

		tableViewerDetail.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkAcceptDetail(((IHMLAvisEcheance)event.getElement()).getIdLDocument(),event.getChecked());	
			}
		});
		

		initController();
		//initActions();

		initActions();
		initTotaux();
	}
	
	public void checkAccept(Integer idDocument,boolean check){
		try {
			Object objet=recherche(idDocument);
			StructuredSelection selectionloc =new StructuredSelection(objet);
			tableViewer.setSelection(selectionloc,true);
			if(selection.getValue()!=null){
				((IHMEnteteAvisEcheance)selection.getValue()).setAccepte(check);
				Object entity=rechercheEntity(idDocument);
				((TaAvisEcheance)entity).setAccepte(check);
				for (TaLAvisEcheance lAvis : ((TaAvisEcheance)entity).getLignes()) {
					checkAcceptDetail(lAvis.getIdLDocument(),check);
//					lAvis.setAccepte(check);					
				}
				initTotaux();
			}
		} catch (Exception e) {
			logger.error("", e);
		}		
	}

	public void checkAcceptDetail(Integer idLDocument,boolean check){
		try {
			Object objet=rechercheDetail(((IHMEnteteAvisEcheance)selection.getValue()), idLDocument);
			StructuredSelection selectionloc =new StructuredSelection(objet);
			tableViewerDetail.setSelection(selectionloc,true);
			if(objet!=null){
				actModifier();
				((IHMLAvisEcheance)objet).setAccepte(check);
				tableViewerDetail.setChecked(objet,check);
				Object entity=rechercheEntity(((IHMEnteteAvisEcheance)selection.getValue()).getIdDocument());
				Object entityDetail=rechercheEntityDetail(((TaAvisEcheance)entity), idLDocument);
				((TaLAvisEcheance)entityDetail).setAccepte(check);

				initTotaux();
			}
		} catch (Exception e) {
			logger.error("", e);
		}		
	}
	public void bindDetail() {
		String [] titreColonnes = {"Code article","Libelle","Qté","Unité","Tarif","Montant HT","Montant TTC"};
		String [] tailleColonnes =  {"100","300","100","100","100","100","100"};
		String[] idColonnesTemp = {"codeArticle","libLDocument","qteLDocument","u1LDocument","prixULDocument","mtHtLDocument","mtTtcLDocument"};
		int[] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};

		// Création de l'élément graphique du tableau et affichage à l'écran
		if(!toolBarInitialise) {
			tableViewerDetail = new LgrTableViewer(vue.getCompositeSectionResultats().getCompoEcranDetail().getGrille());
			tableViewerDetail.createTableCol(vue.getCompositeSectionResultats().getCompoEcranDetail().getGrille(),titreColonnes,tailleColonnes,1,alignement);
			tableViewerDetail.setListeChamp(idColonnesTemp);
		}

		tableViewer.selectionGrille(0);
		selection = ViewersObservables.observeSingleSelection(tableViewer);

		IObservableList tmp = BeansObservables.observeDetailList(selection,"lignesAvis", IHMEnteteAvisEcheance.class);
		ViewerSupport.bind(tableViewerDetail, tmp, BeanProperties.values(IHMLAvisEcheance.class, idColonnesTemp));
		
		
		vue.getCompositeSectionResultats().getCompoEcranDetail().getPaBtn().setVisible(false);
		
		tableViewerDetail.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMLAvisEcheance)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMLAvisEcheance)element).getAccepte())
					return true;
				return false;
			}
		});
	}

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
			return BeansObservables.observeValue(element, "codeTiers"); 
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

	
	public class MapperLigneDocumentSelectionIHMDocumentSelection implements IlgrMapper<IHMEnteteAvisEcheance, TaAvisEcheance> {

		public List<IHMEnteteAvisEcheance> listeEntityToDto(LinkedList<TaAvisEcheance> l) {
			List<IHMEnteteAvisEcheance> res = new ArrayList<IHMEnteteAvisEcheance>(0);
			for (TaAvisEcheance document : l) {
				res.add(entityToDto(document));
			}
			return res;
		}

		public IHMEnteteAvisEcheance entityToDto(TaAvisEcheance e) {
			LgrDozerMapper<TaAvisEcheance,IHMEnteteAvisEcheance> mapper = new LgrDozerMapper<TaAvisEcheance,IHMEnteteAvisEcheance>();
			LgrDozerMapper<TaLAvisEcheance,IHMLAvisEcheance> mapperLignes = new LgrDozerMapper<TaLAvisEcheance,IHMLAvisEcheance>();
			IHMEnteteAvisEcheance documentSelectionIHM = new IHMEnteteAvisEcheance();
			mapper.map(e,documentSelectionIHM);
			if(e.getTaEtat()!=null)
				documentSelectionIHM.setCodeEtat(e.getTaEtat().getCodeEtat());
			documentSelectionIHM.setLignesAvis(new LinkedList<IHMLAvisEcheance>());
			for (TaLAvisEcheance ligne : e.getLignes()) {
				IHMLAvisEcheance ihmLigne=new IHMLAvisEcheance();
				mapperLignes.map(ligne,ihmLigne);
				documentSelectionIHM.getLignesAvis().add(ihmLigne);
			}
			
			return documentSelectionIHM;
		}

		@Override
		public TaAvisEcheance dtoToEntity(IHMEnteteAvisEcheance e) {
			LgrDozerMapper<IHMEnteteAvisEcheance,TaAvisEcheance> mapper = new LgrDozerMapper<IHMEnteteAvisEcheance,TaAvisEcheance>();
			LgrDozerMapper<IHMLAvisEcheance,TaLAvisEcheance> mapperLignes = new LgrDozerMapper<IHMLAvisEcheance,TaLAvisEcheance>();
			TaAvisEcheance l = new TaAvisEcheance();
			TaArticleDAO daoArticle = new TaArticleDAO();
			TaLEcheanceDAO doaEcheance = new TaLEcheanceDAO();
			mapper.map(e, l);
			l.getLignes().clear();
			for (IHMLAvisEcheance ligne : e.getLignesAvis()) {
				try {
					if(ligne.getAccepte()){
						TaLAvisEcheance lAvis = new TaLAvisEcheance(false);
						lAvis.setTaDocument(l);
						mapperLignes.map(ligne, lAvis);
						lAvis.setLegrain(true);
						lAvis.setTaArticle(daoArticle.findByCode(ligne.getCodeArticle()));
						mapperLignes.map(ligne, lAvis);
						if(ligne.getIdLEcheance()!=null)lAvis.setTaLEcheance(doaEcheance.findById(ligne.getIdLEcheance()));
						l.addLigne(lAvis);
					}
				} catch (Exception e1) {
					logger.error("", e1);
				}

			}
			l.calculeTvaEtTotaux();
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


	private class HandlerAnnulerAvis extends LgrAbstractHandler {
		
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actAnnulerAvis();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	private class HandlerReinjecterAvis extends LgrAbstractHandler {
		
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actReinjecterAvis();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = taAvisEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				setCommentaire(FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMMENTAIRE));
				setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
				setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));

				
				TaArticleDAO daoArticle = new TaArticleDAO();
				TaTiersDAO daoTiers = new TaTiersDAO();
				TaComptePointDAO daoComptePoint = new TaComptePointDAO(getEm());
				List <TaComptePoint> listeComptePointCumul=null;
				//enregistrer les lignes d'échéance
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						taAvisEcheanceDAO.begin(transaction);
						taAvisEcheance = new TaAvisEcheance();
						taAvisEcheance.setIdDocument(objet.getIdDocument());
						listeComptePointCumul=remonteComptePointSurListe(taAvisEcheance);
						taAvisEcheance=mapper.dtoToEntity(objet);
						taAvisEcheance.setCodeDocument(taAvisEcheanceDAO.genereCode());
						taAvisEcheance.setCommentaire(commentaire);
						taAvisEcheance.setLibelleDocument("Avis d'échéance");
						taAvisEcheance.setDateEchDocument(objet.getDateDocument());
						taAvisEcheance.setDateDocument(objet.getDateEchDocument());
						taAvisEcheance.setDateLivDocument(objet.getDateLivDocument());
						//taAvisEcheance.setRegleDocument(BigDecimal.valueOf(0));
						taAvisEcheance.setTaTiers(daoTiers.findByCode(objet.getCodeTiers()));
						
						taAvisEcheance.setLegrain(true);
						taAvisEcheance.setTaTiers(daoTiers.findByCode(objet.getCodeTiers()));
						if(taAvisEcheance.getVersionObj()==null)taAvisEcheance.setVersionObj(0);

						TaInfosAvisEcheance infos = new TaInfosAvisEcheance();;
						infos.setTaDocument(taAvisEcheance);
						taAvisEcheance.setTaInfosDocument(infos);
						changementDeTiers(taAvisEcheance);
						//taAvisEcheance.getTaInfosDocument().setCompte("6");
						//taAvisEcheance.getTaInfosDocument().setCodeCompta("6");
						taAvisEcheanceDAO.inserer(taAvisEcheance);
						taAvisEcheance.calculeTvaEtTotaux();

						taAvisEcheance=taAvisEcheanceDAO.enregistrerMerge(taAvisEcheance);
						for (TaComptePoint taComptePoint : listeComptePointCumul) {
							if(taComptePoint!=null && taComptePoint.getPoint().signum()>0){
								daoComptePoint.enregistrePointBonusSurDocument(taComptePoint, taAvisEcheance);
							}
						}

						taAvisEcheanceDAO.commit(transaction);
					}
				}
				vue.getControllerPage().raz(true);
				initEtatBouton(true);

				transaction = null;

			}
			catch (Exception e) {
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),"Erreur d'enregistrement",e.getMessage());
				logger.error("",e);
				throw e;
			}	
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
		
	protected void actReinjecterAvis() throws Exception {
		EntityTransaction transaction = taAvisEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Suppression", "Etes-vous sûr de vouloir changer l'état des avis sélectionnés ?.")){
				IDocumentTiers document;
				TaEtatDAO etatDao= new TaEtatDAO(taAvisEcheanceDAO.getEntityManager());
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						document=null;
						taAvisEcheance =taAvisEcheanceDAO.findById(objet.getIdDocument());
						taAvisEcheanceDAO.begin(transaction);
						taAvisEcheance.setLegrain(false);
						taAvisEcheance.setTaEtat(null);
						
						for (TaLAvisEcheance ligne : taAvisEcheance.getLignes()) {
							TaLEcheance echeance=ligne.getTaLEcheance();
							if(ligne.getTaLEcheance()!=null){
								//echeance=taLEcheanceDAO.findById(ligne.getTaLEcheance().getIdLEcheance());
								echeance.setTaEtat(null);
								//echeance=taLEcheanceDAO.enregistrerMerge(echeance);
							}
						}
						
						taAvisEcheanceDAO.enregistrerMerge(taAvisEcheance);
						taAvisEcheanceDAO.commit(transaction);
					}
					
				}

				
				vue.getControllerPage().raz(true);
				initEtatBouton(true);

				transaction = null;
			}
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
	
	protected void actAnnulerAvis() throws Exception {
		EntityTransaction transaction = taAvisEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Suppression", "Etes-vous sûr de vouloir changer l'état des avis sélectionnés ?.")){
				IDocumentTiers document;
				TaEtatDAO etatDao= new TaEtatDAO(taAvisEcheanceDAO.getEntityManager());
				TaEtat etat= etatDao.findByCode("ANNULE");
				//supprimer les avis d'échéance si ils ne sont pas déjà transformé en facture
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						document=null;
						taAvisEcheance =taAvisEcheanceDAO.findById(objet.getIdDocument());
						taAvisEcheanceDAO.begin(transaction);
						taAvisEcheance.setLegrain(false);
						taAvisEcheance.setTaEtat(etat);
						
						for (TaLAvisEcheance ligne : taAvisEcheance.getLignes()) {
							TaLEcheance echeance=ligne.getTaLEcheance();
							if(ligne.getTaLEcheance()!=null){
								//echeance=taLEcheanceDAO.findById(ligne.getTaLEcheance().getIdLEcheance());
								echeance.setTaEtat(etat);
								//echeance=taLEcheanceDAO.enregistrerMerge(echeance);
							}
						}
						taAvisEcheanceDAO.enregistrerMerge(taAvisEcheance);

						taAvisEcheanceDAO.commit(transaction);
					}
					
				}

				
				vue.getControllerPage().raz(true);
				initEtatBouton(true);

				transaction = null;
				}
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
	
	
	public List<TaComptePoint>  remonteComptePointSurListe(TaAvisEcheance taAvisEcheance){
		List<TaComptePoint> liste=new LinkedList<TaComptePoint>();
//		for (TaAvisEcheance avis : listePointBonusCumul.keySet()) {
//			if(avis.getIdDocument()==taAvisEcheance.getIdDocument())
//				liste.add(listePointBonusCumul.get(avis));				
//		}
		TaAvisEcheance AvisEcheance=null;
		for (TaComptePoint comptePoint : listePointBonusCumul.keySet()) {
			AvisEcheance=listePointBonusCumul.get(comptePoint);
			if(AvisEcheance.getIdDocument()==taAvisEcheance.getIdDocument())
				liste.add(comptePoint);				
		}
		return liste;
	}
	
	
	public void changementDeTiers(TaAvisEcheance taDoc) {
		boolean leTiersADesAdresseLiv = false;
		boolean leTiersADesAdresseFact = false;
		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
		if(taDoc.getTaTiers()!=null){
			if(taDoc.getTaTiers().getTaTTvaDoc()!=null && taDoc.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc()!=null)
			taDoc.getTaInfosDocument().setCodeTTvaDoc(taDoc.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
			else taDoc.getTaInfosDocument().setCodeTTvaDoc("F");
//			taFacture.setTaInfosFactures(new TaInfosFacture());
//			taFacture.getTaInfosFactures().setTaFacture(taFacture);
//			taFacture.setTaInfosFactures(taFacture.getTaInfosFactures());
			
			mapperUIToModelDocumentVersInfosDoc.map(taDoc,taDoc.getTaInfosDocument());
			taDoc.getTaInfosDocument().setNomTiers(taDoc.getTaTiers().getNomTiers());
			
			if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseLiv = taDoc.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le codeTiers a des adresse de ce type
			}
			if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseFact = taDoc.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le codeTiers a des adresse de ce type
			}	
			
			
			Iterator<TaAdresse> ite = null;
			TaAdresse taAdresseLiv =null;
			IHMAdresseInfosLivraison ihmAdresseInfosLivraison = null;
			if(taDoc.getTaTiers().getTaAdresses()!=null)ite =  taDoc.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseLiv) { 
				//ajout des adresse de livraison au modele
				while (ihmAdresseInfosLivraison==null && ite.hasNext()){	
					taAdresseLiv =ite.next();
					if(taAdresseLiv.getTaTAdr()!=null && taAdresseLiv.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
						ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
						mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
						mapperUIToModelAdresseLivVersInfosDoc.map(ihmAdresseInfosLivraison, taDoc.getTaInfosDocument());
					}
				}
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
				mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
				mapperUIToModelAdresseLivVersInfosDoc.map(ihmAdresseInfosLivraison, taDoc.getTaInfosDocument());
				}

			TaAdresse taAdresseFact =null;
			IHMAdresseInfosFacturation ihmAdresseInfosFacturation =null;
			if(taDoc.getTaTiers().getTaAdresses()!=null)ite =  taDoc.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseFact) { 
				while (ihmAdresseInfosFacturation==null && ite.hasNext()){
					taAdresseFact =ite.next();
					if(taAdresseLiv.getTaTAdr()!=null && taAdresseFact.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
						ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
						mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
						mapperUIToModelAdresseFactVersInfosDoc.map(ihmAdresseInfosFacturation, taDoc.getTaInfosDocument());
					}
				}				
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
				mapperModelToUIAdresseInfosDocument.map(taAdresseLiv, ihmAdresseInfosFacturation);
				mapperUIToModelAdresseFactVersInfosDoc.map(ihmAdresseInfosFacturation, taDoc.getTaInfosDocument());
			}
			
			
			if(taDoc.getTaTiers().getTaCPaiement()==null)
				mapperUIToModelCPaiementVersInfosDoc.map(new IHMInfosCPaiement(), taDoc.getTaInfosDocument());
			else{
				IHMInfosCPaiement ihmInfosCPaiement =new IHMInfosCPaiement();
				mapperModelToUICPaiementInfosDocument.map(taDoc.getTaTiers().getTaCPaiement(), ihmInfosCPaiement);
				mapperUIToModelCPaiementVersInfosDoc.map(ihmInfosCPaiement, taDoc.getTaInfosDocument());
			}
			mapperUIToModelDocumentVersInfosDoc.map(taDoc, taDoc.getTaInfosDocument());	
		}
	}
		


	@Override
	protected void actModifier() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = taAvisEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Suppression", "Etes-vous sûr de vouloir supprimer les avis sélectionnés ?.")){
				taAvisEcheanceDAO.begin(transaction);
				IDocumentTiers document;
				//supprimer les avis d'échéance si ils ne sont pas déjà transformé en facture
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						document=null;
						taAvisEcheance =taAvisEcheanceDAO.findById(objet.getIdDocument());
						for (TaRDocument rdocument : taAvisEcheance.getTaRDocuments()) {
							if(rdocument.getTaFacture()!=null)document=rdocument.getTaAvisEcheance();
							else if(rdocument.getTaPrelevement()!=null)document=rdocument.getTaPrelevement();
						}
						if(document==null)taAvisEcheanceDAO.supprimer(taAvisEcheance);
						else {
							MessageDialog.openWarning(PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(), "Suppression impossible", "l'avis d'échéance "+taAvisEcheance.getCodeDocument()+" est relié au document "+document.getCodeDocument()+", " +
											" vous devez supprimer ce document avant de pouvoir supprimer cet avis.");
						}
						//supprimer les points bonus reliés
						if(document==null){
							TaComptePointDAO daoComptePoint = new TaComptePointDAO(taAvisEcheanceDAO.getEntityManager());
							daoComptePoint.removeTousLesPointsBonus(taAvisEcheance,modelLDocument.size()<=1);					
						}
					}
					
				}
			
				taAvisEcheanceDAO.commit(transaction);
				vue.getControllerPage().raz(true);
				initEtatBouton(true);

				transaction = null;
				}
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
		//création facture + abonnement pour finaliser
		EntityTransaction transaction = taAvisEcheanceDAO.getEntityManager().getTransaction();
		try {
			try {
				taAvisEcheanceDAO.begin(transaction);
				
				for (IHMEnteteAvisEcheance objet : modelLDocument) {
					if(objet.getAccepte()){
						taAvisEcheance =taAvisEcheanceDAO.findById(objet.getIdDocument());
						
					}
				}
				taAvisEcheanceDAO.commit(transaction);
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
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub
		exportCSV(modelLDocument);
	}

//	@Override
//	protected void actImprimer()throws Exception {
//		//dao.rechercheSommeAvisEcheance(taDocument);
//		TaAvisEcheance taDocument=null;
//		String simpleNameClass = TaAvisEcheance.class.getSimpleName();
//
//		Collection<TaAvisEcheance> collectionTaAvisEcheance = new LinkedList<TaAvisEcheance>();
//		for (TaAvisEcheance obj : listDocumentSelection) {
//		collectionTaAvisEcheance.add(obj);
//			taDocument =obj;
//	}
//		
//		ConstEdition constEdition = new ConstEdition(getEm()); 
//		constEdition.setFlagEditionMultiple(true);
//		
//		Bundle bundleCourant = PlugInAvisEcheance.getDefault().getBundle();
//		String namePlugin = bundleCourant.getSymbolicName();
//		
//		/** 01/03/2010 modifier les editions (zhaolin) **/
//		impressionEdition.setObject(taDocument);
//		impressionEdition.setConstEdition(constEdition);
//		impressionEdition.setCollection(collectionTaAvisEcheance);
//		impressionEdition.setIdEntity(taDocument.getIdDocument());
//		
//
//		
//		IPreferenceStore preferenceStore = PlugInAvisEcheance.getDefault().getPreferenceStore();
//		impressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,namePlugin,
//												ConstEdition.FICHE_FILE_REPORT_AVIS_ECHEANCE,
//												true,false,true,false,false,false,"");
//		
//		
//	}

	@Override
	protected void actImprimer() throws Exception {
		//Récupération des paramètres dans l'ihm
				//String[] idFactureAImprimer = null;
				List<IDocumentTiers> listDocumentAImprimer = null;
					
				final boolean preview = true;
				final boolean printDirect = false;
				TaAvisEcheance avis;
				listDocumentAImprimer=new LinkedList<IDocumentTiers>();
				for (IHMEnteteAvisEcheance obj : modelLDocument) {
					if(obj.getAccepte()){
					avis=taAvisEcheanceDAO.findById(obj.getIdDocument());
					listDocumentAImprimer.add(avis);
					}
				}

				
				if(listDocumentAImprimer==null ||listDocumentAImprimer.size()==0){
					MessageDialog.openWarning(vue.getCompositeSectionResultats().getCompoEcran().getShell(),
							"ATTENTION", "Aucun document valide n'a été sélectionné !!!");
					throw new Exception("pas de factures à imprimer");
				} 
				final List<IDocumentTiers> finalListDocumentAImprimer = listDocumentAImprimer;
			
				final boolean finalPreview = preview;
				String fichierEdition = null;
				String nomOnglet = null;
				String nomEntity = null;
				String typeTraite = null;
				final LinkedList<TaRReglement> listeTraite=new LinkedList<TaRReglement>();
				listeTraite.clear();
				/** 01/03/2010 modifier les editions (zhaolin) **/
				IPreferenceStore preferenceStore = null;
				String namePlugin = null;
				/************************************************/
//					impressionDocument = new Impression(vue.getShell());
					impressionDocument=(IImpressionDocumentTiers) new fr.legrain.avisecheance.divers.Impression();
					fichierEdition = ConstEdition.FICHE_FILE_REPORT_AVIS_ECHEANCE;
					nomOnglet = "Avis d'échéance";
					nomEntity = TaAvisEcheance.class.getSimpleName();
					
					if(listDocumentAImprimer!=null && listDocumentAImprimer.size()==1){
						IDocumentTiers taDocument = listDocumentAImprimer.get(0);
						//information pour l'envoie de document par email
						impressionEdition.setInfosEmail(null);
						String email = null;
						if(taDocument.getTaTiers().getTaEmail()!=null && taDocument.getTaTiers().getTaEmail().getAdresseEmail()!=null) {
							email = taDocument.getTaTiers().getTaEmail().getAdresseEmail();
						}
						impressionEdition.setInfosEmail(
								new InfosEmail(
										new String[]{email},
										null,
										taDocument.getCodeDocument()+".pdf")
								);
						
						//information pour l'envoie de document par fax
						impressionEdition.setInfosFax(null);
						String fax = null;
						if(!taDocument.getTaTiers().findNumeroFax().isEmpty()) {
							fax = taDocument.getTaTiers().findNumeroFax().get(0);
						}
						impressionEdition.setInfosFax(
								new InfosFax(
										new String[]{fax},
										taDocument.getCodeDocument()+".pdf")
								);
						
					}
					
				preferenceStore=impressionDocument.getPreferenceStore();
				namePlugin = impressionDocument.getPluginName();
				
				final String finalFichierEdition = fichierEdition;
				final String finalNomOnglet = nomOnglet;
				final String finalNomEntity = nomEntity;
				final String finalTypeTraite = typeTraite;
				
				/** 01/03/2010 modifier les editions (zhaolin) **/
				final String finalNamePlugin = namePlugin;
				final IPreferenceStore finalPreferenceStore = preferenceStore;
				final ConstEdition constEdition = new ConstEdition(null);

				vue.getCompositeSectionResultats().getCompoEcran().getDisplay().asyncExec(new Thread() {
					public void run() {
						try {	
							impressionEdition.setConstEdition(constEdition);
							impressionEdition.setCollection(finalListDocumentAImprimer);
							impressionEdition.setTypeTraite(finalTypeTraite);
							impressionEdition.impressionEdition(finalPreferenceStore, finalNomEntity,/*true,*/ 
							        null, finalNamePlugin, finalFichierEdition, true, 
							        false, true, true , true,false,"");
							
							

						} catch (Exception e) {
							logger.error("Erreur à l'impression ",e);
						} finally {
						}
					}
				});

			}



	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void actRefresh() throws Exception {
		try{	
			Integer idActuel=0;
			if(taAvisEcheance!=null)
				idActuel =taAvisEcheance.getIdDocument();				
			WritableList writableListFacture = new WritableList(modelLDocument, IHMEnteteAvisEcheance.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(idActuel!=null && writableListFacture.size()>0) {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(idActuel)),true);
			}
			initTotaux();
//			ChangementDeSelection();
			initEtatBouton(true);
			vue.getCompositeSectionResultats().getCompoEcran().getGrille().forceFocus();
		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
		}
	}
	public Object recherche( Integer value) {
		boolean trouve = false;
		int i = 0;
		List<IHMEnteteAvisEcheance> model=modelLDocument;
		while(!trouve && i<model.size()){
			try {
				if(PropertyUtils.getProperty(model.get(i), Const.C_ID_DOCUMENT)==value) {
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
	
	public Object rechercheDetail(IHMEnteteAvisEcheance obj,  Integer value) {
		boolean trouve = false;
		int i = 0;
		List<IHMLAvisEcheance> model=obj.getLignesAvis();
		while(!trouve && i<model.size()){
			try {
				if(PropertyUtils.getProperty(model.get(i), Const.C_ID_L_DOCUMENT).equals(value)) {
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
	
	public TaAvisEcheance rechercheEntity( Integer value) {
		boolean trouve = false;
		int i = 0;
		TaAvisEcheance objet;
		List<TaAvisEcheance> model=listDocumentSelection;
		while(!trouve && i<model.size()){
			try {
				objet=model.get(i);
				if(objet!=null && objet.getIdDocument()==(value)){
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
	
	public TaLAvisEcheance rechercheEntityDetail(TaAvisEcheance obj, Integer value) {
		boolean trouve = false;
		int i = 0;
		List<TaLAvisEcheance> model=obj.getLignes();
		TaLAvisEcheance objet;
		while(!trouve && i<model.size()){
			try {
				objet=model.get(i);
				if(objet!=null && value!=null && objet.getIdLDocument()==(value)){
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

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getTypeAdresseFacturation() {
		return typeAdresseFacturation;
	}

	public void setTypeAdresseFacturation(String typeAdresseFacturation) {
		this.typeAdresseFacturation = typeAdresseFacturation;
	}

	public String getTypeAdresseLivraison() {
		return typeAdresseLivraison;
	}

	public void setTypeAdresseLivraison(String typeAdresseLivraison) {
		this.typeAdresseLivraison = typeAdresseLivraison;
	}

	public Boolean getSuppressionUniquement() {
		return suppressionUniquement;
	}

	public void setSuppressionUniquement(Boolean suppressionUniquement) {
		this.suppressionUniquement = suppressionUniquement;
	}


	public void exportCSV(List<IHMEnteteAvisEcheance> l) {
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
				

				filecontent += "codeTiers;";
				filecontent += "nomTiers;";
				filecontent += "codeDocument;";
				filecontent += "dateDocument;";
				filecontent += "dateEchDocument;";
				filecontent += "netHtCalc;";
				filecontent += "netTvaCalc;";
				filecontent += "netTtcCalc";

				
			
				for (IHMEnteteAvisEcheance m : l) {
					filecontent += "\n";
					filecontent += m.getCodeTiers()!= null ? m.getCodeTiers()+";" : ";";
					filecontent += (m.getNomTiers()!=null)? m.getNomTiers()+";" : ";";
					filecontent += (m.getCodeDocument()!=null ) 	? m.getCodeDocument()+";" : ";";
					filecontent += (m.getDateDocument()!=null)? LibDate.dateToString(m.getDateDocument())+";" : ";";
					filecontent += (m.getDateEchDocument()!=null) ? LibDate.dateToString(m.getDateEchDocument())+";" : ";";
					filecontent += (m.getNetHtCalc()!= null ) ? m.getNetHtCalc()+";" : ";";
					filecontent += (m.getNetTvaCalc()!= null ) ? m.getNetTvaCalc()+";" : ";";
					filecontent += (m.getNetTtcCalc()!= null ) ? m.getNetTtcCalc()+";" : ";";


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
