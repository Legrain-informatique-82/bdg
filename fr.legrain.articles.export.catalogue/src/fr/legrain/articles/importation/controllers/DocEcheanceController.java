package fr.legrain.articles.importation.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.articles.importation.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.articles.importation.ecrans.PaFormPage;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.prestashop.ws.WSPrestashop;
import fr.legrain.prestashop.ws.WSPrestashopConfig;
import fr.legrain.prestashop.ws.entities.Orders;

public class DocEcheanceController extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(DocEcheanceController.class.getName());	

	private Class objetIHM = null;
	//private TaDevisDAO taDevisDAO = null;
	private WSPrestashop ws = null;
	private TaDevis taDevis = null;
	protected List<DocumentSelectionIHM> modelDocument = null;
	protected List<DocumentSelectionIHM> modelDocumentExport = null;
	protected List<TaDevis> listeDoc;
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipal masterController = null;
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

	static private String ETAT_DEJA_IMPORTEE = "Déjà importée";
	static private String ETAT_A_IMPORTER = "A importer";


	/* Constructeur */
	public DocEcheanceController(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

		String cle = fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_LOGIN);
		String password = fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_PASSWORD);
		String hostName = fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_HOST);
		String baseURI = "http://"+hostName+fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_BASE_URI);

		WSPrestashopConfig.init(
				baseURI, 
				cle, 
				password, 
				hostName
				);

		ws = new WSPrestashop(fr.legrain.article.export.catalogue.Activator.getDefault().getPreferenceStoreProject().getBoolean(fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject.GENERATION_COMMANDE_IMPORT_EN_TTC));
	}

	public String findEtatCommande(int id) {
		String result = "";
		String origineImport = "site_web";

		TaBoncdeDAO dao = new TaBoncdeDAO();
		List<TaBoncde> l = dao.rechercheParImport(origineImport, LibConversion.integerToString(id));
		if(l!=null && !l.isEmpty()) {
			result = ETAT_DEJA_IMPORTEE;
		} else {
			result = ETAT_A_IMPORTER;
		}

		return result;
	}


	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM(int nbResult) {
		
		final Date[] d = new Date[2];
		final boolean[] b = new boolean[1];

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				d[0] = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
				d[1] = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());
				b[0] = vue.getCompositeSectionParam().getCdateDeb().isEnabled();
			}
		});

		try {
			Date deb = d[0];
			Date fin = d[1];			
			boolean debEnable = b[0];
			
			if(debEnable) {
				ws.preparationImportation(
						deb,
						fin
						);
			} else {
				ws.preparationImportation(
						null,
						fin
						);
			}
		} catch (Exception e) {
			logger.error("",e);
		}

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				// Liste qui va contenir les informations
				LinkedList<DocumentSelection> listDocumentSelection = new LinkedList<DocumentSelection>();
				DocumentSelection documentSelection = null;
				totalHT = new BigDecimal(0);
				totalTTC = new BigDecimal(0);
				//TaDevis object = null;
				Orders object = null;
				for (int i = 0; i < ws.getListeOrders().getOrders().size(); i ++){

					try {
						object = ws.findOrder(ws.getListeOrders().getOrders().get(i).getId());
					} catch (JAXBException e) {
						logger.error("",e);
					}
					documentSelection = new DocumentSelection(
							String.valueOf(object.getId()),
							object.getReference(), 
							String.valueOf(object.getIdCustomer()),
							object.getDateAdd(),
							LibConversion.stringToBigDecimal(LibConversion.floatToString(object.getTotalPaid())),
							findEtatCommande(object.getId())
							);

					listDocumentSelection.add(documentSelection);
				}

				libelleEcran =
						"Liste des commandes passées entre le "
								+ LibDate.dateToString(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()))
								+ " et le "
								+ LibDate.dateToString(LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin()))

								+ " ("+listDocumentSelection.size()+" documents)";	
				vue.getSctnTableauGauche().setText(libelleEcran);

				modelDocument = new MapperDocumentSelectionIHMDocumentSelection().listeEntityToDto(listDocumentSelection);

			}
		});

	}

	protected void initActions() {
		if(!toolBarInitialise) {
			vue.getCompositeSectionTableauGauche().getSectionToolbar().add(importationAction);
			vue.getCompositeSectionTableauGauche().getSectionToolbar().update(true);

			toolBarInitialise = true;
		}
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionTableauGauche().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					//					String valeurIdentifiant = vue.getCompositeSectionTableauGauche().getTable().getSelection()[0].getText(
					//							getTableViewer().findPositionNomChamp("code")
					//							);
					//					String idEditor = DevisMultiPageEditor.ID_EDITOR;
					//					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
					System.out.println("double click");
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
		String [] titreColonnes = {"ID Commande (Boutique)","Reference","ID Client (Boutique)","Date","Montant","Etat"};
		// Taille des colonnes
		String [] tailleColonnes = {"200","100","150","100","100","200"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"id","reference","idCustomer","dateAdd","total","etat"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionTableauGauche().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);

		DocEcheanceLabelProvider.bind(tableViewer, new WritableList(modelDocument, DocumentSelectionIHM.class),
				BeanProperties.values(idColonnes));

		//		TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[8]);
		//		etatColumn.setLabelProvider(new CellLabelProvider() {
		//			@Override
		//			public void update(ViewerCell cell) {
		//				cell.setText(((DocumentSelectionIHM) cell.getElement()).getEtat());
		//			}
		//		});

		//		EditingSupport exampleEditingSupport = new ExampleEditingSupport(etatColumn.getViewer());
		//		etatColumn.setEditingSupport(exampleEditingSupport);

		FancyToolTipSupportTiers.enableFor(tableViewer,ToolTip.NO_RECREATE);
		//ColumnViewerToolTipSupport.enableFor(tableViewer,ToolTip.NO_RECREATE);
		CellLabelProvider labelProvider = new CellLabelProvider() {

			public String getToolTipText(Object element) {
				return "";//((DocumentSelectionIHM)element).getCodeTiers();
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

		//Object[] tabCheck = tableViewer.getElementAt().;
		for (int i = 0; i < tableViewer.getTable().getItemCount(); i++) {
			DocumentSelectionIHM object = (DocumentSelectionIHM) tableViewer.getElementAt(i);
			if(object.getEtat().equals(ETAT_A_IMPORTER)) {
				tableViewer.setChecked(object, true);
			} else {
				tableViewer.setGrayed(object, true);
			}


		}

		//		bindDetail();

		initActions();

		vue.getForm().setBusy(false);
	}

	public class DocumentSelection {
		private String id = null;
		private String reference = null;
		private String idCustomer = null;
		private Date dateAdd = null;
		private BigDecimal total = null;
		private String etat = null;

		public DocumentSelection(String id, String reference, String idCustomer,
				Date dateAdd, BigDecimal total, String etat) {
			super();
			this.id = id;
			this.reference = reference;
			this.idCustomer = idCustomer;
			this.dateAdd = dateAdd;
			this.total = total;
			this.etat = etat;
		}

		public String getId() {
			return id;
		}
		public void setId(String code) {
			this.id = code;
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String libelle) {
			this.reference = libelle;
		}
		public String getIdCustomer() {
			return idCustomer;
		}
		public void setIdCustomer(String codeTiers) {
			this.idCustomer = codeTiers;
		}
		public Date getDateAdd() {
			return dateAdd;
		}
		public void setDateAdd(Date nomTiers) {
			this.dateAdd = nomTiers;
		}
		public BigDecimal getTotal() {
			return total;
		}
		public void setTotal(BigDecimal dateDocument) {
			this.total = dateDocument;
		}

		public String getEtat() {
			return etat;
		}

		public void setEtat(String etat) {
			this.etat = etat;
		}

	}

	/* ------------------- Affichage Section Clients ------------------- */


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas
	 *
	 */
	public class DocumentSelectionIHM extends ModelObject {

		private String id = null;
		private String reference = null;
		private String idCustomer = null;
		private Date dateAdd = null;
		private BigDecimal total = null;
		private String etat = null;


		public DocumentSelectionIHM(String id, String reference, String idCustomer,
				Date dateAdd, BigDecimal total, String etat) {
			super();
			this.id = id;
			this.reference = reference;
			this.idCustomer = idCustomer;
			this.dateAdd = dateAdd;
			this.total = total;
			this.etat = etat;
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			firePropertyChange("id", this.id, this.id = id);
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			firePropertyChange("reference", this.reference, this.reference = reference);
		}
		public String getIdCustomer() {
			return idCustomer;
		}
		public void setIdCustomer(String idCustomer) {
			firePropertyChange("idCustomer", this.idCustomer, this.idCustomer = idCustomer);
		}
		public Date getDateAdd() {
			return dateAdd;
		}
		public void setDateAdd(Date dateAdd) {
			firePropertyChange("dateAdd", this.dateAdd, this.dateAdd = dateAdd);
		}
		public BigDecimal getTotal() {
			return total;
		}
		public void setTotal(BigDecimal total) {
			firePropertyChange("total", this.total, this.total = total);
		}

		public String getEtat() {
			return etat;
		}

		public void setEtat(String etat) {
			firePropertyChange("etat", this.etat, this.etat = etat);
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
					e.getId(),e.getReference(),e.getIdCustomer(),e.getDateAdd(),e.getTotal(), e.getEtat()
					);
			//			if(documentSelectionIHM.getLignes()==null)
			//				documentSelectionIHM.setLignes(new ArrayList<DocEcheanceController.LigneSelectionIHM>(0));
			//
			//			if(e.getLignes()!=null) {
			//				for (LigneSelection l : e.getLignes()) {
			//					documentSelectionIHM.getLignes().add(new LigneSelectionIHM(l.getCode(), l.getLibelle(),l.getQte(),l.getUnite(),l.getTarif(),l.getMontantHT(),l.getMontantTTC()));
			//				}
			//			}

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
	private Action importationAction = new Action("Importer",Activator.getImageDescriptor(PaCompositeSectionDocEcheance.iconpath)) { 
		@Override 
		public void run() {
			Thread t = new Thread() {

				final Object[] tabCheck = tableViewer.getCheckedElements();

				public void run() {
					Job job = new Job("Préparation de l'importation") {
						protected IStatus run(IProgressMonitor monitor) {
							//Object[] tabCheck = tableViewer.getCheckedElements();
							final int ticks = tabCheck.length;
							monitor.beginTask("Importation", ticks);
							try {
								for (int i = 0; i < tabCheck.length; i++) {
									DocumentSelectionIHM object = (DocumentSelectionIHM)tabCheck[i];

									ws.importCommande(LibConversion.stringToInteger(object.getId()));

									monitor.worked(1);
									if (monitor.isCanceled()) {
										return Status.CANCEL_STATUS;
									}
								}
							} catch (Exception e) {
								logger.error("",e);
							} finally {
								monitor.done();
							}
							return Status.OK_STATUS;
						}
					};
					job.setPriority(Job.SHORT);
					//job.setUser(true);
					job.schedule(); 
					try {
						job.join();
					} catch (InterruptedException e) {
						logger.error("Erreur à l'impression ",e);
					}

				}
			};
			t.start();


			//print();
			try {
				//				Object[] tabCheck = tableViewer.getCheckedElements();
				//				for (int i = 0; i < tabCheck.length; i++) {
				//					DocumentSelectionIHM object = (DocumentSelectionIHM)tabCheck[i];
				//
				//
				//					ws.importCommande(LibConversion.stringToInteger(object.getId()));
				//
				//				}

				TaSynchroBoutiqueDAO dao = new TaSynchroBoutiqueDAO();
				TaSynchroBoutique taSynchroBoutique = dao.findInstance();

				taSynchroBoutique.setDerniereSynchro(new Date());
				EntityTransaction tx = dao.getEntityManager().getTransaction();
				tx.begin();
				dao.enregistrerMerge(taSynchroBoutique);
				dao.commit(tx);
			} catch (Exception e) {
				logger.error("",e);
			}
		}
	};

	public void print() {
		//		//		List<DocumentSelectionIHM> modelDocument
		//		LinkedList<Resultat> listeRes = new LinkedList<Resultat>();
		//		for (int i = 0; i < modelDocument.size(); i++) {
		//			String[] temp = {
		//					(modelDocument.get(i).getCode() != null ? modelDocument
		//							.get(i).getCode()
		//							: ""),
		//							(modelDocument.get(i).getLibelle() != null ? modelDocument
		//									.get(i).getLibelle()
		//									: ""),
		//									(modelDocument.get(i).getCodeTiers() != null ? modelDocument
		//											.get(i).getCodeTiers()
		//											: ""),
		//											(modelDocument.get(i).getNomTiers() != null ? modelDocument
		//													.get(i).getNomTiers()
		//													: ""),
		//													(modelDocument.get(i).getDateDocument() != null ? LibDate.dateToString(modelDocument
		//															.get(i).getDateDocument())
		//															: ""),
		//															(modelDocument.get(i).getDateEcheance() != null ? LibDate.dateToString(modelDocument
		//																	.get(i).getDateEcheance())
		//																	: ""),
		//																	(modelDocument.get(i).getMontant() != null ? modelDocument
		//																			.get(i).getMontant().toString()
		//																			: ""),
		//																			(modelDocument.get(i).getMontantTTC() != null ? modelDocument
		//																					.get(i).getMontantTTC().toString()
		//																					: ""),
		//																					(modelDocument.get(i).getEtat() != null ? modelDocument
		//																							.get(i).getEtat()
		//																							: "")
		//			};
		//			listeRes.add(new Resultat(temp));
		//		}
		//
		//		InfosPresentation[] infosDocuments = {
		//				(new InfosPresentation("Code", "8", String.class,
		//						"string", false)),
		//						(new InfosPresentation("Libellé", "20", String.class,
		//								"string", false)),
		//								(new InfosPresentation("Code tiers", "8", String.class,
		//										"string", false)),
		//										(new InfosPresentation("Nom tiers", "10", String.class,
		//												"string", false)),
		//												(new InfosPresentation("Date", "8", Date.class,
		//														"date", false)),
		//														(new InfosPresentation("Echéance", "8", Date.class,
		//																"date", false)),
		//																(new InfosPresentation("Montant HT", "10", BigDecimal.class,
		//																		"decimal", true)),
		//																		(new InfosPresentation("Montant TTC", "10", BigDecimal.class,
		//																				"decimal", true)),
		//																				(new InfosPresentation("Etat", "8", String.class,
		//																						"string", false)) };
		//
		//		EditionTable ed = new EditionTable(infosDocuments, taDevisDAO.getEntityManager(), tableViewer, 
		//				DocumentSelectionIHM.class.getSimpleName(), listeRes,
		//				libelleEcran,null,"Edition échéance");
		//
		//		ed.imprJPA();

	}

	public BigDecimal getTotalHT() {
		return totalHT;
	}

	public BigDecimal getTotalTTC() {
		return totalTTC;
	}

}
