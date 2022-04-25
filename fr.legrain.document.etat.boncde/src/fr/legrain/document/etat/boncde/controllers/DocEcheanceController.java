/**
 * ClientController.java
 */
package fr.legrain.document.etat.boncde.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.boncde.editor.BoncdeMultiPageEditor;
import fr.legrain.document.etat.boncde.Activator;
import fr.legrain.document.etat.devis.controllers.DocEcheanceLabelProvider;
import fr.legrain.document.etat.devis.controllers.FancyToolTipSupportTiers;
import fr.legrain.document.etat.devis.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.document.etat.devis.ecrans.PaFormPage;
import fr.legrain.document.etat.devis.edition.EditionTable;
import fr.legrain.document.etat.devis.edition.InfosPresentation;
import fr.legrain.document.etat.devis.edition.Resultat;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaEtat;
import fr.legrain.documents.dao.TaEtatDAO;
import fr.legrain.documents.dao.TaLBoncde;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
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

	static Logger logger = Logger.getLogger(DocEcheanceController.class.getName());	

	private Class objetIHM = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBoncde taBoncde = null;
	protected List<DocumentSelectionIHM> modelDocument = null;
	protected List<DocumentSelectionIHM> modelDocumentExport = null;
	protected List<TaBoncde> listeDoc;
	private List<ModelObject> modele = null;
	protected  FormPageControllerBoncde masterController = null;
	protected PaFormPage vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewer;
	private LgrTableViewer tableViewerDetail;
	
	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;

	private String libelleEcran = "";
	
	private BigDecimal totalHT = new BigDecimal(0);
	private BigDecimal totalTTC = new BigDecimal(0);


	/* Constructeur */
	public DocEcheanceController(FormPageControllerBoncde masterContoller, PaFormPage vue, EntityManager em) {
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

		taBoncdeDAO = new TaBoncdeDAO(masterController.getMasterDAOEM());

		Date datefin = LibDate.incrementDate(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()),
				LibConversion.stringToInteger(vue.getCompositeSectionParam().getTfVariableDate().getText(),0)
				, 0, 0);

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeDoc = taBoncdeDAO.rechercheDocumentEtat(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMiniBoncde.getMapEtatCodeLibelle().get(masterController.paramControllerMiniBoncde.getCodeEtat()));
		} else {
			listeDoc = taBoncdeDAO.rechercheDocumentEtat(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMiniBoncde.getMapEtatCodeLibelle().get(masterController.paramControllerMiniBoncde.getCodeEtat()),
					vue.getCompositeSectionParam().getTfCodeTiers().getText());
		}

		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<DocumentSelection> listDocumentSelection = new LinkedList<DocumentSelection>();
		DocumentSelection documentSelection = null;
		totalHT = new BigDecimal(0);
		totalTTC = new BigDecimal(0);
		TaBoncde object = null;
		for (int i = 0; i < listeDoc.size() /*&& i < nbResult*/; i ++){

			object = listeDoc.get(i);
			documentSelection = new DocumentSelection(
					object.getCodeDocument(),
					object.getLibelleDocument(), 
					object.getTaTiers()!=null?object.getTaTiers().getCodeTiers():"",
					object.getTaTiers()!=null?object.getTaTiers().getNomTiers():"",
					object.getDateDocument(),
					object.getDateEchDocument(),
					object.getMtHtCalc(),
					object.getMtTtcCalc(),
					object.getTaEtat()!=null?object.getTaEtat().getLibEtat():fr.legrain.document.etat.devis.controllers.ParamControllerMini.etatEnCoursLibelle);

			for (TaLBoncde l : object.getLignes()) {
				if(documentSelection.getLignes()==null)
					documentSelection.setLignes(new ArrayList<DocEcheanceController.LigneSelection>(0));

				documentSelection.getLignes().add(new LigneSelection(l.getTaArticle()!=null?l.getTaArticle().getCodeArticle():"", l.getLibLDocument(),l.getQteLDocument(),l.getU1LDocument(),l.getPrixULDocument(),l.getMtHtLDocument(),l.getMtTtcLDocument()));
			}
			
			totalHT = totalHT.add(object.getMtHtCalc());
			totalTTC = totalTTC.add(object.getMtTtcCalc());

			listDocumentSelection.add(documentSelection);
		}

		if(vue.getCompositeSectionParam().getCbEtat().getText().equals(fr.legrain.document.etat.devis.controllers.ParamControllerMini.etatEnCoursLibelle)) {
			libelleEcran = "Liste des bons de commandes en cours arrivant à échéance avant le "
					+ LibDate.dateToString(datefin) + " ("+listDocumentSelection.size()+" documents)";
		} else {
			libelleEcran =
					"Liste des bons de commandes arrivant à échéance entre le "
							+ LibDate.dateToString(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()))
							+ " et le "
							+ LibDate.dateToString(datefin)
							+" (Etat : "
							+ vue.getCompositeSectionParam().getCbEtat().getText()
							+")"
							+ " ("+listDocumentSelection.size()+" documents)";	
		}
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
					String valeurIdentifiant = vue.getCompositeSectionTableauGauche().getTable().getSelection()[0].getText(
							getTableViewer().findPositionNomChamp("code")
							);
					String idEditor = BoncdeMultiPageEditor.ID_EDITOR;
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

		// Titre des colonnes
		String [] titreColonnes = {"Code","Libelle","Code tiers","Nom tiers","Date","Echeance","Montant HT","Montant TTC","Etat"};
		// Taille des colonnes
		String [] tailleColonnes = {"75","300","100","100","100","100","100","100","100"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"code","libelle","codeTiers","nomTiers","dateDocument","dateEcheance","montant","montantTTC","etat"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionTableauGauche().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);

		DocEcheanceLabelProvider.bind(tableViewer, new WritableList(modelDocument, DocumentSelectionIHM.class),
				BeanProperties.values(idColonnes));

		TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[8]);
		etatColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((DocumentSelectionIHM) cell.getElement()).getEtat());
			}
		});

		EditingSupport exampleEditingSupport = new ExampleEditingSupport(etatColumn.getViewer());
		etatColumn.setEditingSupport(exampleEditingSupport);

		FancyToolTipSupportTiers.enableFor(tableViewer,ToolTip.NO_RECREATE);
		//ColumnViewerToolTipSupport.enableFor(tableViewer,ToolTip.NO_RECREATE);
		CellLabelProvider labelProvider = new CellLabelProvider() {

			public String getToolTipText(Object element) {
				return ((DocumentSelectionIHM)element).getCodeTiers();
			}

			public Point getToolTipShift(Object object) {
				return new Point(-10, -10);
			}

			public int getToolTipDisplayDelayTime(Object object) {
				return 1000;
			}

			public int getToolTipTimeDisplayed(Object object) {
				return 10000;
			}

			public void update(ViewerCell cell) {
				cell.setText(cell.getElement().toString());
			}
		};
		TableViewerColumn tiersColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[2]);
		tiersColumn.setLabelProvider(labelProvider);

		bindDetail();

		initActions();
	}

	public void bindDetail() {
		String [] titreColonnes = {"Code article","Libelle","Qté","Unité","Tarif","Montant HT","Montant TTC"};
		String [] tailleColonnes =  {"100","300","100","100","100","100","100"};
		String[] idColonnesTemp = {"code","libelle","qte","unite","tarif","montantHT","montantTTC"};
		int[] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};

		// Création de l'élément graphique du tableau et affichage à l'écran
		if(!toolBarInitialise) {
			tableViewerDetail = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTableDetail());
			tableViewerDetail.createTableCol(vue.getCompositeSectionTableauGauche().getTableDetail(),titreColonnes,tailleColonnes,1,alignement);
			tableViewerDetail.setListeChamp(idColonnesTemp);
		}

		tableViewer.selectionGrille(0);
		IObservableValue selection = ViewersObservables.observeSingleSelection(tableViewer);

		IObservableList tmp = BeansObservables.observeDetailList(selection,"lignes", DocumentSelectionIHM.class);
		ViewerSupport.bind(tableViewerDetail, tmp, BeanProperties.values(LigneSelectionIHM.class, idColonnesTemp));
	}

	public void changeEtat(String v) {
		DocumentSelectionIHM doc = ((DocumentSelectionIHM)((IStructuredSelection) getTableViewer().getSelection()).getFirstElement());
		//String codeNouvelEtat = masterController.paramControllerMini.getMapEtatCodeLibelle().get(doc.etat);
		String codeNouvelEtat = masterController.paramControllerMiniBoncde.getMapEtatCodeLibelle().get(v);

		taBoncdeDAO = new TaBoncdeDAO(masterController.getMasterDAOEM());
		TaEtatDAO taEtatDAO = new TaEtatDAO(masterController.getMasterDAOEM());

		TaBoncde taBoncde =  taBoncdeDAO.findByCode(doc.code);
		TaEtat taEtat = null;

		if(codeNouvelEtat!=null)
			taEtat = taEtatDAO.findByCode(codeNouvelEtat);

		EntityTransaction transaction = taBoncdeDAO.getEntityManager().getTransaction();
		try {			
			taBoncde.setTaEtat(taEtat);
			taBoncdeDAO.begin(transaction);
			
			taBoncde.setLegrain(false); //Pour éviter setTaArticle sur les lignes suite au merge et donc la réinitialisation des informations de la ligne y compris pour les quantités

			//if ((taDevisDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			taBoncdeDAO.enregistrerMerge(taBoncde);
			//} 
			taBoncdeDAO.commit(transaction);
			transaction = null;

		} catch (RollbackException e) {	
			logger.error("",e);
			//if(e.getCause() instanceof OptimisticLockException)
			//	MessageDialog.openError(vue.getShell(), "", e.getMessage()+"\n"+e.getCause().getMessage());
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		}

	}

	//public final class ExampleEditingSupport extends EditingSupport {
	public final class ExampleEditingSupport extends ObservableValueEditingSupport {

		private ComboBoxViewerCellEditor cellEditor = null;

		private ExampleEditingSupport(ColumnViewer viewer) {
			super(viewer, new DataBindingContext());
			//super(viewer);
			cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
			cellEditor.setLabelProvider(new LabelProvider());
			cellEditor.setContenProvider(new ArrayContentProvider());
			cellEditor.setInput(new WritableList(masterController.paramControllerMiniBoncde.getMapEtatCodeLibelle().keySet(),String.class));

			cellEditor.addListener(new ICellEditorListener() {

				@Override
				public void applyEditorValue() {
					String v = cellEditor.getValue().toString();
					changeEtat(v);
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

	public class DocumentSelection {
		private String code = null;
		private String libelle = null;
		private String codeTiers = null;
		private String nomTiers = null;
		private Date dateDocument = null;
		private Date dateEcheance = null;
		private BigDecimal montant = null;
		private BigDecimal montantTTC = null;
		private String etat = null;
		private List<LigneSelection> lignes = null;

		public DocumentSelection(String code, String libelle, String codeTiers,
				String nomTiers, Date dateDocument, Date dateEcheance,
				BigDecimal montant, BigDecimal montantTTC, String etat) {
			super();
			this.code = code;
			this.libelle = libelle;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.dateDocument = dateDocument;
			this.dateEcheance = dateEcheance;
			this.montant = montant;
			this.montantTTC = montantTTC;
			this.etat = etat;
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
		public Date getDateEcheance() {
			return dateEcheance;
		}
		public void setDateEcheance(Date dateEcheance) {
			this.dateEcheance = dateEcheance;
		}
		public BigDecimal getMontant() {
			return montant;
		}
		public void setMontant(BigDecimal montant) {
			this.montant = montant;
		}

		public BigDecimal getMontantTTC() {
			return montantTTC;
		}
		public void setMontantTTC(BigDecimal montantTTC) {
			this.montantTTC = montantTTC;
		}

		public String getEtat() {
			return etat;
		}

		public void setEtat(String etat) {
			this.etat = etat;
		}

		public List<LigneSelection> getLignes() {
			return lignes;
		}

		public void setLignes(List<LigneSelection> lignes) {
			this.lignes = lignes;
		}

	}

	/* ------------------- Affichage Section Clients ------------------- */


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas
	 *
	 */
	public class DocumentSelectionIHM extends ModelObject {
		private String code = null;
		private String libelle = null;
		private String codeTiers = null;
		private String nomTiers = null;
		private Date dateDocument = null;
		private Date dateEcheance = null;
		private BigDecimal montant = null;
		private BigDecimal montantTTC = null;
		private String etat = null;
		private List<LigneSelectionIHM> lignes = null;

		public DocumentSelectionIHM(String code, String libelle,
				String codeTiers, String nomTiers, Date dateDocument,
				Date dateEcheance, BigDecimal montant, BigDecimal montantTTC, String etat) {
			super();
			this.code = code;
			this.libelle = libelle;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.dateDocument = dateDocument;
			this.dateEcheance = dateEcheance;
			this.montant = montant;
			this.montantTTC = montantTTC;
			this.etat = etat;
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


		public String getCodeTiers() {
			return codeTiers;
		}

		public void setCodeTiers(String codeTiers) {
			firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
		}

		public BigDecimal getMontant() {
			return montant;
		}

		public void setMontant(BigDecimal montant) {
			firePropertyChange("montant", this.montant, this.montant = montant);
		}

		public BigDecimal getMontantTTC() {
			return montantTTC;
		}

		public void setMontantTTC(BigDecimal montantTTC) {
			firePropertyChange("montantTTC", this.montantTTC, this.montantTTC = montantTTC);
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

		public Date getDateEcheance() {
			return dateEcheance;
		}

		public void setDateEcheance(Date dateEcheance) {
			this.dateEcheance = dateEcheance;
		}

		public String getEtat() {
			return etat;
		}

		public void setEtat(String etat) {
			firePropertyChange("etat", this.etat, this.etat = etat);
		}

		public List<LigneSelectionIHM> getLignes() {
			return lignes;
		}

		public void setLignes(List<LigneSelectionIHM> lignes) {
			this.lignes = lignes;
		}
	}

	public class LigneSelection {
		private String code = null;
		private String libelle = null;
		private BigDecimal qte = null;
		private String unite = null;
		private BigDecimal tarif = null;
		private BigDecimal montantHT = null;
		private BigDecimal montantTTC = null;

		public LigneSelection(String code, String libelle, BigDecimal qte,
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

			DocumentSelectionIHM documentSelectionIHM = new DocumentSelectionIHM(
					e.getCode(),e.getLibelle(),e.getCodeTiers(),e.getNomTiers(),e.getDateDocument(),e.getDateEcheance(),e.getMontant(),e.getMontantTTC(),
					e.getEtat()
					);
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
	private Action editonAction = new Action("Edition",Activator.getImageDescriptor(PaCompositeSectionDocEcheance.iconpath)) { 
		@Override 
		public void run() {
			print();
		}
	};

	public void print() {
		//		List<DocumentSelectionIHM> modelDocument
		LinkedList<Resultat> listeRes = new LinkedList<Resultat>();
		for (int i = 0; i < modelDocument.size(); i++) {
			String[] temp = {
					(modelDocument.get(i).getCode() != null ? modelDocument
							.get(i).getCode()
							: ""),
							(modelDocument.get(i).getLibelle() != null ? modelDocument
									.get(i).getLibelle()
									: ""),
									(modelDocument.get(i).getCodeTiers() != null ? modelDocument
											.get(i).getCodeTiers()
											: ""),
											(modelDocument.get(i).getNomTiers() != null ? modelDocument
													.get(i).getNomTiers()
													: ""),
													(modelDocument.get(i).getDateDocument() != null ? LibDate.dateToString(modelDocument
															.get(i).getDateDocument())
															: ""),
															(modelDocument.get(i).getDateEcheance() != null ? LibDate.dateToString(modelDocument
																	.get(i).getDateEcheance())
																	: ""),
																	(modelDocument.get(i).getMontant() != null ? modelDocument
																			.get(i).getMontant().toString()
																			: ""),
																			(modelDocument.get(i).getMontantTTC() != null ? modelDocument
																					.get(i).getMontantTTC().toString()
																					: ""),
																					(modelDocument.get(i).getEtat() != null ? modelDocument
																							.get(i).getEtat()
																							: "")
			};
			listeRes.add(new Resultat(temp));
		}

		InfosPresentation[] infosDocuments = {
				(new InfosPresentation("Code", "8", String.class,
						"string", false)),
						(new InfosPresentation("Libellé", "20", String.class,
								"string", false)),
								(new InfosPresentation("Code tiers", "8", String.class,
										"string", false)),
										(new InfosPresentation("Nom tiers", "10", String.class,
												"string", false)),
												(new InfosPresentation("Date", "8", Date.class,
														"date", false)),
														(new InfosPresentation("Echéance", "8", Date.class,
																"date", false)),
																(new InfosPresentation("Montant HT", "10", BigDecimal.class,
																		"decimal", true)),
																		(new InfosPresentation("Montant TTC", "10", BigDecimal.class,
																				"decimal", true)),
																				(new InfosPresentation("Etat", "8", String.class,
																						"string", false)) };

		EditionTable ed = new EditionTable(infosDocuments, taBoncdeDAO.getEntityManager(), tableViewer, 
				DocumentSelectionIHM.class.getSimpleName(), listeRes,
				libelleEcran,null,"Edition échéance");

		ed.imprJPA();

	}

	public BigDecimal getTotalHT() {
		return totalHT;
	}

	public BigDecimal getTotalTTC() {
		return totalTTC;
	}

}
