/**
 * ClientController.java
 */
package fr.legrain.document.RechercheDocument.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.RechercheDocument.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.document.RechercheDocument.ecrans.PaFormPage;
import fr.legrain.document.RechercheDocument.editions.EditionTable;
import fr.legrain.document.RechercheDocument.editions.InfosPresentation;
import fr.legrain.document.RechercheDocument.editions.Resultat;
import fr.legrain.document.divers.IImpressionDocumentTiers;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;

/**
 * @author nicolas
 *
 */
public class DocEcheanceController extends AbstractControllerMini {

	public List<LigneSelectionIHM> getModelLDocument() {
		return modelLDocument;
	}

	static Logger logger = Logger.getLogger(DocEcheanceController.class.getName());	

	private Class objetIHM = null;
	private IDocumentTiersDAO dao = null;//new TaFactureDAO();
	private IImpressionDocumentTiers impressionDocument = null; 

	private IDocumentTiers document = null;
	protected List<DocumentSelectionIHM> modelDocument = null;
	protected List<DocumentSelectionIHM> modelDocumentExport = null;
	protected List<Object[]> listeDoc;
//	protected List<IDocumentTiers> listeDoc;
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipal masterController = null;
	protected PaFormPage vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;//
	protected String [] idColonnesLigne;
	private Realm realm;
	private LgrTableViewer tableViewer;
	private LgrTableViewer tableViewerDetail;
	protected List<LigneSelectionIHM> modelLDocument = null;
	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;

	IObservableValue selectionDoc = null;
	IObservableValue selectionLigne = null;
			
	private String libelleEcran = "";

	private BigDecimal totalHT = new BigDecimal(0);
	private BigDecimal totalTTC = new BigDecimal(0);
	TypeDoc typeDocument = TypeDoc.getInstance();	


	/* Constructeur */
	public DocEcheanceController(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}


	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		//this.nbResult = nbResult;
		int typeDoc = vue.getCompositeSectionParam().getCbTypeDoc().getSelectionIndex();
		if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
			dao = new TaFactureDAO(masterController.getMasterDAOEM());
		} else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
			dao = new TaDevisDAO(masterController.getMasterDAOEM());
		} else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
			dao = new TaApporteurDAO(masterController.getMasterDAOEM());
		} else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
			dao = new TaAvoirDAO(masterController.getMasterDAOEM());
		} else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {
			dao = new TaProformaDAO(masterController.getMasterDAOEM());
		} else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
			dao = new TaBoncdeDAO(masterController.getMasterDAOEM());
		} else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_ACOMPTE)) {
			dao = new TaAcompteDAO(masterController.getMasterDAOEM());
		}else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
			dao = new TaBonlivDAO(masterController.getMasterDAOEM());
		}else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
			dao = new TaAvisEcheanceDAO(masterController.getMasterDAOEM());
		}else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PRELEVEMENT)) {
			dao = new TaPrelevementDAO(masterController.getMasterDAOEM());
		}else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_REGLEMENT)) {
			dao = new TaReglementDAO(masterController.getMasterDAOEM());
		}else if(vue.getCompositeSectionParam().getCbTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
			dao = new TaAvisEcheanceDAO(masterController.getMasterDAOEM());
		}

		if(!vue.getControllerPage().getParamControllerMini().getCodeDocument().equals("")){
			listeDoc = dao.rechercheDocumentLight(
					vue.getControllerPage().getParamControllerMini().getCodeDocument(),"%");
		}else
		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeDoc = dao.rechercheDocumentLight(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()),"%");
		} else {
			listeDoc = dao.rechercheDocumentLight(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()),
					vue.getCompositeSectionParam().getTfCodeTiers().getText());
		}

		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<DocumentSelection> listDocumentSelection = new LinkedList<DocumentSelection>();
		DocumentSelection documentSelection = null;
		totalHT = new BigDecimal(0);
		totalTTC = new BigDecimal(0);
		BigDecimal ht =null;
		BigDecimal ttc = null;
		Object[] object = null;
		for (int i = 0; i < listeDoc.size() /*&& i < nbResult*/; i ++){
//			f.idDocument, f.codeDocument, f.dateDocument, f.libelleDocument, tiers.codeTiers, infos.nomTiers,f.dateEchDocument,f.export,f.netHtCalc,f.netTtcCalc
			object = listeDoc.get(i);
//			object.setAccepte(false);
//			if(vue.getCompositeSectionParam().getBtnNonRegle().getSelection()==false || object.getRegleCompletDocument()==null 
//					||object.getRegleCompletDocument().compareTo(BigDecimal.ZERO)==0)
//				object.setAccepte(true);
//			if(object.getAccepte()){
			ht=object[8]!=null?(BigDecimal)object[8]:BigDecimal.ZERO;
			ttc=object[9]!=null?(BigDecimal)object[9]:BigDecimal.ZERO;
				documentSelection = new DocumentSelection(
						(Integer)object[0],
						(String)object[1],
						(Date)object[2],
						object[4]!=null?(String)object[4]:"",
								object[5]!=null?(String)object[5]:"",
										object[3]!=null?(String)object[3]:"",
												ht,
														ttc,
										BigDecimal.ZERO,
												BigDecimal.ZERO,
												object[7]!=null?LibConversion.intToBoolean((Integer)object[7]):false);

//				for (ILigneDocumentTiers l : object.getLignesGeneral()) {
//					if(documentSelection.getLignes()==null)
						documentSelection.setLignes(new ArrayList<DocEcheanceController.LigneSelection>(0));
//
//					documentSelection.getLignes().add(new LigneSelection(l.getTaArticle()!=null?l.getTaArticle().getCodeArticle():"", l.getLibLDocument(),l.getQteLDocument(),l.getU1LDocument(),l.getPrixULDocument(),l.getMtHtLDocument(),l.getMtTtcLDocument()));
//				}

				totalHT = totalHT.add(ht);
				totalTTC = totalTTC.add(ttc);

				listDocumentSelection.add(documentSelection);
//			}
		}

		libelleEcran =
				"Liste des documents compris entre le "
						+ LibDate.dateToString(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()))
						+ " et le "
						+ LibDate.dateToString(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()))
						+" (de type : "
						+ vue.getCompositeSectionParam().getCbTypeDoc().getText()
						+")"
						+ " ("+listDocumentSelection.size()+" documents)";	

		//String totaux = " *** Total HT : "+totalHT.toString()+ "€ *** Total TTC : "+totalTTC.toString()+"€ ";
		vue.getSctnTableauGauche().setText(libelleEcran/*+totaux*/);

		modelDocument = new MapperDocumentSelectionIHMDocumentSelection().listeEntityToDto(listDocumentSelection);
	}

	protected void initActions() {
		if(!toolBarInitialise) {
			vue.getCompositeSectionTableauGauche().getSectionToolbar().add(editonAction);
			vue.getCompositeSectionTableauGauche().getSectionToolbar().update(true);

			toolBarInitialise = true;
		}
	
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionTableauGauche().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					//int typeDoc = vue.getCompositeSectionParam().getCbTypeDoc().getSelectionIndex();
					String idEditor = null;
					idEditor= typeDocument.getEditorDoc().get(vue.getControllerPage().getParamControllerMini().getTypeDocEnCours());
					
					String valeurIdentifiant = vue.getCompositeSectionTableauGauche().getTable().getSelection()[0].getText(
							getTableViewer().findPositionNomChamp("code")
							);
					
					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}

			});
			evenementInitialise = true;
		}
	}

	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(DocumentSelectionIHM.class);
		if(modelDocument==null)modelDocument=new ArrayList<DocEcheanceController.DocumentSelectionIHM>();
		// Titre des colonnes
		String [] titreColonnes = {"Code","Date","Code tiers","Nom tiers","Libelle","Net HT","Net TTC","Exporté"};
		// Taille des colonnes
		String [] tailleColonnes = {"75","80","100","100","300","100","100","100"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"code","dateDocument","codeTiers","nomTiers","libelle","netHtCalc","netTtcCalc","export"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionTableauGauche().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		selectionDoc = ViewersObservables.observeSingleSelection(tableViewer);
		LigneLabelProvider.bind(tableViewer, new WritableList(modelDocument, DocumentSelectionIHM.class),
				BeanProperties.values(idColonnes));


		bindDetail();
		tableViewer.selectionGrille(0);
		selectionDoc.addChangeListener(new IChangeListener() {

			public void handleChange(ChangeEvent event) {
				changementDeSelection();
			}

		});
		
		changementDeSelection();
		initActions();
	}

	public void bindDetail() {
		String [] titreColonnes = {"Code article","Libelle","Qté","Unité","Tarif","Montant HT","Montant TTC"};
		String [] tailleColonnes =  {"100","300","100","100","100","100","100"};
		String [] idColonnesTemp = {"code","libelle","qte","unite","tarif","montantHT","montantTTC"};
		int[] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};
		idColonnesLigne=idColonnesTemp;
		modelLDocument = new ArrayList<DocEcheanceController.LigneSelectionIHM>();
		
		// Création de l'élément graphique du tableau et affichage à l'écran
		//if(!toolBarInitialise) {
			tableViewerDetail = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTableDetail());
			tableViewerDetail.createTableCol(vue.getCompositeSectionTableauGauche().getTableDetail(),titreColonnes,tailleColonnes,1,alignement);
			tableViewerDetail.setListeChamp(idColonnesLigne);
			selectionLigne = ViewersObservables.observeSingleSelection(tableViewerDetail);
			LigneLabelProvider.bind(tableViewerDetail, new WritableList(modelLDocument, LigneSelectionIHM.class),
					BeanProperties.values(idColonnesLigne));
		//}

		
		

//		IObservableList tmp = BeansObservables.observeDetailList(selection,"lignes", DocumentSelectionIHM.class);
//		ViewerSupport.bind(tableViewerDetail, tmp, BeanProperties.values(LigneSelectionIHM.class, idColonnesTemp));
		
	}

	//	

	protected void changementDeSelection() {
		if(selectionDoc!=null && selectionDoc.getValue()!=null){
			IDocumentTiers doc =(IDocumentTiers)dao.findById(((DocumentSelectionIHM)selectionDoc.getValue()).id);
			modelLDocument=new ArrayList<DocEcheanceController.LigneSelectionIHM>(0);

			if(doc.getLignesGeneral()!=null) {
				for (ILigneDocumentTiers l : doc.getLignesGeneral()) {
					modelLDocument.add(new LigneSelectionIHM(l.getTaArticle()!=null?l.getTaArticle().getCodeArticle():"", l.getLibLDocument(),l.getQteLDocument(),l.getU1LDocument(),l.getPrixULDocument(),l.getMtHtLDocument(),l.getMtTtcLDocument()));
				}
			}
		}
		LigneLabelProvider.bind(tableViewerDetail, new WritableList(modelLDocument, LigneSelectionIHM.class),
				BeanProperties.values(idColonnesLigne));	
		tableViewerDetail.selectionGrille(0);
	}


	public class DocumentSelection {
		private Integer id = null;
		private String code = null;
		private Date dateDocument = null;
		private String codeTiers = null;
		private String nomTiers = null;
		private String libelle = null;
		private BigDecimal netHtCalc = null;
		private BigDecimal netTtcCalc = null;
		private BigDecimal regleCompletDocument = null;
		private BigDecimal resteAReglerComplet = null;
		private Boolean export=null;


		public DocumentSelection(Integer id,String code, Date dateDocument,
				String codeTiers, String nomTiers, String libelle,
				BigDecimal netHtCalc, BigDecimal netTtcCalc,
				BigDecimal regleCompletDocument,
				BigDecimal resteAReglerComplet, Boolean export) {
			super();
			this.id = id;
			this.code = code;
			this.dateDocument = dateDocument;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.libelle = libelle;
			this.netHtCalc = netHtCalc;
			this.netTtcCalc = netTtcCalc;
			this.regleCompletDocument = regleCompletDocument;
			this.resteAReglerComplet = resteAReglerComplet;
			this.export = export;
		}

		//		private String etat = null;
		private List<LigneSelection> lignes = null;



		public List<LigneSelection> getLignes() {
			return lignes;
		}

		public void setLignes(List<LigneSelection> lignes) {
			this.lignes = lignes;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}

		public String getCodeTiers() {
			return codeTiers;
		}

		public void setCodeTiers(String codeTiers) {
			this.codeTiers = codeTiers;
		}

		public String getNomTiers() {
			return nomTiers;
		}

		public void setNomTiers(String nomTiers) {
			this.nomTiers = nomTiers;
		}

		public Date getDateDocument() {
			return dateDocument;
		}

		public void setDateDocument(Date dateDocument) {
			this.dateDocument = dateDocument;
		}

		public BigDecimal getNetHtCalc() {
			return netHtCalc;
		}

		public void setNetHtCalc(BigDecimal netHtCalc) {
			this.netHtCalc = netHtCalc;
		}

		public BigDecimal getNetTtcCalc() {
			return netTtcCalc;
		}

		public void setNetTtcCalc(BigDecimal netTtcCalc) {
			this.netTtcCalc = netTtcCalc;
		}

		public BigDecimal getRegleCompletDocument() {
			return regleCompletDocument;
		}

		public void setRegleCompletDocument(BigDecimal regleCompletDocument) {
			this.regleCompletDocument = regleCompletDocument;
		}

		public BigDecimal getResteAReglerComplet() {
			return resteAReglerComplet;
		}

		public void setResteAReglerComplet(BigDecimal resteAReglerComplet) {
			this.resteAReglerComplet = resteAReglerComplet;
		}

		public Boolean getExport() {
			return export;
		}

		public void setExport(Boolean export) {
			this.export = export;
		}

	}

	/* ------------------- Affichage Section Clients ------------------- */


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas
	 *
	 */
	public class DocumentSelectionIHM extends ModelObject {
		private Integer id = null;
		private String code = null;
		private Date dateDocument = null;
		private String codeTiers = null;
		private String nomTiers = null;
		private String libelle = null;
		private BigDecimal netHtCalc = null;
		private BigDecimal netTtcCalc = null;
		private BigDecimal regleCompletDocument = null;
		private BigDecimal resteAReglerComplet = null;
		private Boolean export=null;

		private List<LigneSelectionIHM> lignes = null;



		public DocumentSelectionIHM(Integer id,String code, Date dateDocument,
				String codeTiers, String nomTiers, String libelle,
				BigDecimal netHtCalc, BigDecimal netTtcCalc,
				BigDecimal regleCompletDocument,
				BigDecimal resteAReglerComplet, Boolean export) {
			super();
			this.id = id; 
			this.code = code;
			this.dateDocument = dateDocument;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.libelle = libelle;
			this.netHtCalc = netHtCalc;
			this.netTtcCalc = netTtcCalc;
			this.regleCompletDocument = regleCompletDocument;
			this.resteAReglerComplet = resteAReglerComplet;
			this.export = export;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			firePropertyChange("id", this.id, this.id = id);
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			firePropertyChange("code", this.code, this.code = code);
		}



		public List<LigneSelectionIHM> getLignes() {
			return lignes;
		}

		public void setLignes(List<LigneSelectionIHM> lignes) {
			this.lignes = lignes;
		}

		public Date getDateDocument() {
			return dateDocument;
		}

		public void setDateDocument(Date dateDocument) {
			firePropertyChange("dateDocument", this.dateDocument, this.dateDocument = dateDocument);
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

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			firePropertyChange("libelle", this.libelle, this.libelle = libelle);
		}

		public BigDecimal getNetHtCalc() {
			return netHtCalc;
		}

		public void setNetHtCalc(BigDecimal netHtCalc) {
			firePropertyChange("netHtCalc", this.netHtCalc, this.netHtCalc = netHtCalc);
		}

		public BigDecimal getNetTtcCalc() {
			return netTtcCalc;
		}

		public void setNetTtcCalc(BigDecimal netTtcCalc) {
			firePropertyChange("netTtcCalc", this.netTtcCalc, this.netTtcCalc = netTtcCalc);
		}

		public BigDecimal getRegleCompletDocument() {
			return regleCompletDocument;
		}

		public void setRegleCompletDocument(BigDecimal regleCompletDocument) {
			firePropertyChange("regleCompletDocument", this.regleCompletDocument, this.regleCompletDocument = regleCompletDocument);
		}

		public BigDecimal getResteAReglerComplet() {
			return resteAReglerComplet;
		}

		public void setResteAReglerComplet(BigDecimal resteAReglerComplet) {
			firePropertyChange("resteAReglerComplet", this.resteAReglerComplet, this.resteAReglerComplet = resteAReglerComplet);
		}

		public Boolean getExport() {
			return export;
		}

		public void setExport(Boolean export) {
			firePropertyChange("export", this.export, this.export = export);
		}


	}

	public class LigneSelection {

		private Integer id = null;
		private String code = null;
		private String libelle = null;
		private BigDecimal qte = null;
		private String unite = null;
		private BigDecimal tarif = null;
		private BigDecimal montantHT = null;
		private BigDecimal montantTTC = null;

		public LigneSelection(Integer id,String code, String libelle, BigDecimal qte,
				String unite, BigDecimal tarif, BigDecimal montantHT,
				BigDecimal montantTTC) {
			super();
			this.id = id;
			this.code = code;
			this.libelle = libelle;
			this.qte = qte;
			this.unite = unite;
			this.tarif = tarif;
			this.montantHT = montantHT;
			this.montantTTC = montantTTC;
		}
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getLibelle() {
			return libelle;
		}
		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}
		public BigDecimal getQte() {
			return qte;
		}
		public void setQte(BigDecimal qte) {
			this.qte = qte;
		}
		public String getUnite() {
			return unite;
		}
		public void setUnite(String unite) {
			this.unite = unite;
		}
		public BigDecimal getTarif() {
			return tarif;
		}
		public void setTarif(BigDecimal tarif) {
			this.tarif = tarif;
		}
		public BigDecimal getMontantHT() {
			return montantHT;
		}
		public void setMontantHT(BigDecimal montantHT) {
			this.montantHT = montantHT;
		}
		public BigDecimal getMontantTTC() {
			return montantTTC;
		}
		public void setMontantTTC(BigDecimal montantTTC) {
			this.montantTTC = montantTTC;
		}

	}

	public class LigneSelectionIHM extends ModelObject {
		private String code = null;
		private String libelle = null;
		private BigDecimal qte = null;
		private String unite = null;
		private BigDecimal tarif = null;
		private BigDecimal montantHT = null;
		private BigDecimal montantTTC = null;

		public LigneSelectionIHM(String code, String libelle, BigDecimal qte,
				String unite, BigDecimal tarif, BigDecimal montantHT,
				BigDecimal montantTTC) {
			super();
			this.code = code;
			this.libelle = libelle;
			this.qte = qte;
			this.unite = unite;
			this.tarif = tarif;
			this.montantHT = montantHT;
			this.montantTTC = montantTTC;
		}

		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			firePropertyChange("code", this.code, this.code = code);
		}
		public String getLibelle() {
			return libelle;
		}
		public void setLibelle(String libelle) {
			firePropertyChange("libelle", this.libelle, this.libelle = libelle);
		}

		public BigDecimal getQte() {
			return qte;
		}

		public void setQte(BigDecimal qte) {
			firePropertyChange("qte", this.qte, this.qte = qte);
		}

		public String getUnite() {
			return unite;
		}

		public void setUnite(String unite) {
			firePropertyChange("unite", this.unite, this.unite = unite);
		}

		public BigDecimal getTarif() {
			return tarif;
		}

		public void setTarif(BigDecimal tarif) {
			firePropertyChange("tarif", this.tarif, this.tarif = tarif);
		}

		public BigDecimal getMontantHT() {
			return montantHT;
		}

		public void setMontantHT(BigDecimal montantHT) {
			firePropertyChange("montantHT", this.montantHT, this.montantHT = montantHT);
		}

		public BigDecimal getMontantTTC() {
			return montantTTC;
		}

		public void setMontantTTC(BigDecimal montantTTC) {
			firePropertyChange("montantTTC", this.montantTTC, this.montantTTC = montantTTC);
		}
	}

	public class MapperDocumentSelectionIHMDocumentSelection implements IlgrMapper<DocumentSelectionIHM, DocumentSelection> {

		public List<DocumentSelectionIHM> listeEntityToDto(LinkedList<DocumentSelection> l) {
			List<DocumentSelectionIHM> res = new ArrayList<DocumentSelectionIHM>(0);
			for (DocumentSelection document : l) {
				res.add(entityToDto(document));
			}
			return res;
		}

		public DocumentSelectionIHM entityToDto(DocumentSelection e) {

			DocumentSelectionIHM documentSelectionIHM = new DocumentSelectionIHM(e.id,
					e.code,
					e.dateDocument,
					e.codeTiers,
					e.nomTiers ,
					e.libelle ,
					e.netHtCalc ,
					e.netTtcCalc,
					e.regleCompletDocument,
					e.resteAReglerComplet,
					e.export);
			if(documentSelectionIHM.getLignes()==null)
				documentSelectionIHM.setLignes(new ArrayList<DocEcheanceController.LigneSelectionIHM>(0));

			if(e.getLignes()!=null) {
				for (LigneSelection l : e.getLignes()) {
					documentSelectionIHM.getLignes().add(new LigneSelectionIHM(l.getCode(), l.getLibelle(),l.getQte(),l.getUnite(),l.getTarif(),l.getMontantHT(),l.getMontantTTC()));
				}
			}

			return documentSelectionIHM;
		}

		@Override
		public DocumentSelection dtoToEntity(DocumentSelectionIHM e) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public LgrTableViewer getTableViewer() {
		return tableViewer;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action editonAction = new Action("Edition",DocumentPlugin.getImageDescriptor(PaCompositeSectionDocEcheance.iconpath)) { 
		@Override 
		public void run() {
			print();
		}
	};

	public void print() {
		//		List<DocumentSelectionIHM> modelDocument
		LinkedList<Resultat> listeRes = new LinkedList<Resultat>();
		for (int i = 0; i < modelDocument.size(); i++) {
			String[] temp ={ (modelDocument.get(i).getCode() != null ? modelDocument.get(i).getCode(): ""),
					(modelDocument.get(i).getDateDocument() != null ? LibDate.dateToString(modelDocument.get(i).getDateDocument()): ""),							
					(modelDocument.get(i).getCodeTiers() != null ? modelDocument.get(i).getCodeTiers(): ""),
					(modelDocument.get(i).getNomTiers() != null ? modelDocument.get(i).getNomTiers(): ""),
					(modelDocument.get(i).getLibelle() != null ? modelDocument.get(i).getLibelle(): ""),
					(modelDocument.get(i).getNetHtCalc() != null ?LibConversion.bigDecimalToString(modelDocument.get(i).getNetHtCalc()):"0"),
					(modelDocument.get(i).getNetTtcCalc() != null ?LibConversion.bigDecimalToString(modelDocument.get(i).getNetTtcCalc()):"0"),
					//(modelDocument.get(i).getRegleCompletDocument() != null ?LibConversion.bigDecimalToString(modelDocument.get(i).getRegleCompletDocument()):"0"),
					//(modelDocument.get(i).getResteAReglerComplet() != null ?LibConversion.bigDecimalToString(modelDocument.get(i).getResteAReglerComplet()):"0"),
					(modelDocument.get(i).getExport() != null ? LibConversion.booleanToStringFrancais(modelDocument.get(i).getExport()): "faux")	};
			listeRes.add(new Resultat(temp));
		}

		InfosPresentation[] infosDocuments = {
				(new InfosPresentation("Code", "8", String.class,
						"string", false)),
						(new InfosPresentation("Date", "8", Date.class,
								"date", false)),						
								(new InfosPresentation("Code tiers", "8", String.class,
										"string", false)),
										(new InfosPresentation("Nom tiers", "10", String.class,
												"string", false)),
												(new InfosPresentation("Libellé", "20", String.class,
														"string", false)),
														(new InfosPresentation("Net HT", "10", BigDecimal.class,
																"decimal", true)),
																(new InfosPresentation("Net TTC", "10", BigDecimal.class,
																		"decimal", true)),
//																		(new InfosPresentation("Réglé", "10", BigDecimal.class,
//																				"decimal", true)),
//																				(new InfosPresentation("Reste à régler", "10", BigDecimal.class,
//																						"decimal", true)),																						
																						(new InfosPresentation("Export", "8", Integer.class,
																								"string", false)) };

		EditionTable ed = new EditionTable(infosDocuments, masterController.getMasterDAOEM(), tableViewer, 
				DocumentSelectionIHM.class.getSimpleName(), listeRes,
				libelleEcran,null,"Edition liste des documents recherchés");

		ed.imprJPA();

	}

	public BigDecimal getTotalHT() {
		return totalHT;
	}

	public BigDecimal getTotalTTC() {
		return totalTTC;
	}

}
