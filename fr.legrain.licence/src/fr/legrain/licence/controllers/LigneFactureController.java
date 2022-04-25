/**
 * ClientController.java
 */
package fr.legrain.licence.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
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
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.facture.editor.FactureMultiPageEditor;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.licence.pluginLicence;
import fr.legrain.licence.ecrans.PaCompositeSectionDocEcheance;
import fr.legrain.licence.ecrans.PaFormPage;
import fr.legrain.tiers.dao.TaFamilleTiers;


/**
 * @author nicolas
 *
 */
public class LigneFactureController extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(LigneFactureController.class.getName());	

	private Class objetIHM = null;
	private TaLFactureDAO taLFactureDAO = null;
	private TaLFacture taLFacture = null;
	protected List<DocumentSelectionIHM> modelLDocument = null;
//	protected List<DocumentSelectionIHM> modelDocumentExport = null;
	protected List<TaLFacture> listeLDoc;
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipal masterController = null;
	protected PaFormPage vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer tableViewer;
//	private LgrTableViewer tableViewerDetail;

	private String libelleEcran = "";
	
	private BigDecimal totalHT = new BigDecimal(0);
	private BigDecimal totalTTC = new BigDecimal(0);


	/* Constructeur */
	public LigneFactureController(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
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

		taLFactureDAO = new TaLFactureDAO(masterController.getMasterDAOEM());

		Date datefin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateFin());

		if(vue.getCompositeSectionParam().getTfCodeTiers().getText().equals("")) {
			listeLDoc = taLFactureDAO.rechercheLigneSupportAbon(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTArticle().get(masterController.paramControllerMini.getCodeEtat()),"%");
		} else {
			listeLDoc = taLFactureDAO.rechercheLigneSupportAbon(
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					datefin,
					masterController.paramControllerMini.getMapTArticle().get(masterController.paramControllerMini.getCodeEtat()),
					vue.getCompositeSectionParam().getTfCodeTiers().getText());
		}

		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<LigneDocumentSelection> listDocumentSelection = new LinkedList<LigneDocumentSelection>();
		LigneDocumentSelection documentSelection = null;

		TaLFacture object = null;
		Query queryCount = null;
		Long count=null;
		String requete="";
		for (int i = 0; i < listeLDoc.size() /*&& i < nbResult*/; i ++){
			Integer nb=1;
			object = listeLDoc.get(i);
			if(object.getQteLDocument()!=null){
				nb=object.getQteLDocument().intValue();
				requete="select count(s) from TaSupportAbon s  where s.taLDocument.idLDocument=?";
					queryCount = taLFactureDAO.getEntityManager().createQuery(requete);
					queryCount.setParameter(1, object.getIdLDocument());
					count=(Long)queryCount.getSingleResult();
					if(count!=null){
						nb=nb-count.intValue();
					}
			}
			TaFamilleTiers familleTiers=null;
			String groupe="";
			if(object.getTaDocument().getTaTiers().getTaFamilleTierses().size()>0){
				while (familleTiers == null && object.getTaDocument().getTaTiers().getTaFamilleTierses().iterator().hasNext()){
					familleTiers=object.getTaDocument().getTaTiers().getTaFamilleTierses().iterator().next();
				}
			}
			if(familleTiers!=null)groupe=familleTiers.getCodeFamille();
			String entreprise="";
			String entite="";
			String commercial="";
			if(object.getTaDocument().getTaTiers().getTaEntreprise()!=null)entreprise=object.getTaDocument().getTaTiers().getTaEntreprise().getNomEntreprise();
			if(object.getTaDocument().getTaTiers().getTaTEntite()!=null)entite=object.getTaDocument().getTaTiers().getTaTEntite().getCodeTEntite();
			if(object.getTaDocument().getTaTiers().getTaCommercial()!=null)commercial=object.getTaDocument().getTaTiers().getTaCommercial().getCodeTiers();
			while(nb>0){
				documentSelection = new LigneDocumentSelection(
						object.getTaDocument().getCodeDocument(),
						object.getLibLDocument(), 
						object.getTaDocument().getTaTiers().getCodeTiers(),
						object.getTaDocument().getTaTiers().getNomTiers(),
						object.getTaArticle().getCodeArticle(),
						object.getTaDocument().getDateDocument(),
						masterController.paramControllerMini.getCodeEtat(),
						object.getIdLDocument(),
						groupe,
						commercial,entite,entreprise,null);
				listDocumentSelection.add(documentSelection);
				nb--;
			}

		}

		modelLDocument = new MapperLigneDocumentSelectionIHMDocumentSelection().listeEntityToDto(listDocumentSelection);
	}

	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;


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
					String idEditor = FactureMultiPageEditor.ID_EDITOR;
					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}

			});
			evenementInitialise = true;
		}
		//		mapCommand = new HashMap<String, IHandler>();
		//
		//		mapCommand.put(JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_AIDE_ID, handlerModifier);
		//
		//		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		//
		//		if (mapActions == null)
		//			mapActions = new LinkedHashMap<Button, Object>();
		//
		//		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		//
		//
		//		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		//		mapActions.put(null, tabActionsAutres);
	}

	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

		//		if (mapComposantChamps == null) 
		//		mapComposantChamps = new LinkedHashMap<Control,String>();
		//
		//	if (listeComposantFocusable == null) 
		//		listeComposantFocusable = new ArrayList<Control>();
		//	listeComposantFocusable.add(vue.getGrille());
		//
		//	vue.getTfCODE_TIERS().setToolTipText("C_CODE_TIERS");
		//
		//	mapComposantChamps.put(vue.getTfCODE_TIERS(),Const.C_CODE_TIERS);
		//
		//	for (Control c : mapComposantChamps.keySet()) {
		//		listeComposantFocusable.add(c);
		//	}
		//
		//	listeComposantFocusable.add(vue.getBtnEnregistrer());
		//
		//	if(mapInitFocus == null) 
		//		mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		//	mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfCODE_TIERS());

	}

	private void initTotaux(){
		Integer nbCoche=modelLDocument.size();

		vue.getCompositeSectionTableauGauche().getTfMT_HT_CALC().setText("0");
		vue.getCompositeSectionTableauGauche().getTfNbLigne().setText(LibConversion.integerToString(nbCoche));
	}
	
	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		if(modelLDocument==null)modelLDocument= new ArrayList<DocumentSelectionIHM>(0);
		setObjetIHM(DocumentSelectionIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code","Libelle ligne","Code tiers","Nom tiers","Date","Code article","groupe"};
		// Taille des colonnes
		String [] tailleColonnes = {"75","300","100","150","100","100","100"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"code","libelleLigne","codeTiers","nomTiers","dateDocument","codeArticle","groupe"};
		this.idColonnes = idColonnesTemp;
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE,SWT.NONE};

		tableViewer = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTable());
		tableViewer.createTableCol(vue.getCompositeSectionTableauGauche().getTable(),titreColonnes,tailleColonnes,1,alignement);
		tableViewer.setListeChamp(idColonnes);
		tableViewer.tri(getObjetIHM(), idColonnesTemp, titreColonnes);
		
		LigneFactureLabelProvider.bind(tableViewer, new WritableList(modelLDocument, DocumentSelectionIHM.class),
				BeanProperties.values(idColonnes));

//		TableViewerColumn etatColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[1]);
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
//				return ((DocumentSelectionIHM)element).getCodeTiers();
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
//		TableViewerColumn tiersColumn = new TableViewerColumn(tableViewer, tableViewer.getTable().getColumns()[1]);
//		tiersColumn.setLabelProvider(labelProvider);

		//bindDetail();

		initActions();
		initTotaux();
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
			cellEditor.setInput(new WritableList(masterController.paramControllerMini.getMapTArticle().keySet(),String.class));

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

	public class LigneDocumentSelection {
		private String code = null;
		private String libelleLigne = null;
		private String codeTiers = null;
		private String nomTiers = null;
		private String codeArticle = null;
		private Date dateDocument = null;
		private String codeTSupport = null;
		private Integer idLDocument = 0;
		private String groupe=null;
		private String commercial=null;
		private String entite=null;
		private String entreprise=null;
		private Integer idTSupport = null;
		//private List<LigneSelection> lignes = null;


		public LigneDocumentSelection(String code, String libelleLigne,
				String codeTiers, String nomTiers, String codeArticle,
				Date dateDocument, String codeTSupport, Integer idLDocument,
				String groupe, String commercial, String entite,
				String entreprise,Integer idTSupport) {
			super();
			this.code = code;
			this.libelleLigne = libelleLigne;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.codeArticle = codeArticle;
			this.dateDocument = dateDocument;
			this.codeTSupport = codeTSupport;
			this.idLDocument = idLDocument;
			this.groupe = groupe;
			this.commercial = commercial;
			this.entite = entite;
			this.entreprise = entreprise;
			this.idTSupport = idTSupport;
		}
		
		
		public String getCode() {
			return code;
		}
		

		public void setCode(String code) {
			this.code = code;
		}
		public String getLibelleLigne() {
			return libelleLigne;
		}
		public void setLibelleLigne(String libelleLigne) {
			this.libelleLigne = libelleLigne;
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
		public String getCodeArticle() {
			return codeArticle;
		}

		public void setCodeArticle(String codeArticle) {
			this.codeArticle = codeArticle;
		}

		public String getCodeTSupport() {
			return codeTSupport;
		}

		public void setCodeTSupport(String codeTSupport) {
			this.codeTSupport = codeTSupport;
		}

		public Integer getIdLDocument() {
			return idLDocument;
		}

		public void setIdLDocument(Integer idLDocument) {
			this.idLDocument = idLDocument;
		}

		public String getGroupe() {
			return groupe;
		}

		public void setGroupe(String groupe) {
			this.groupe = groupe;
		}
		public String getCommercial() {
			return commercial;
		}
		public void setCommercial(String commercial) {
			this.commercial = commercial;
		}
		public String getEntite() {
			return entite;
		}
		public void setEntite(String entite) {
			this.entite = entite;
		}
		public String getEntreprise() {
			return entreprise;
		}
		public void setEntreprise(String entreprise) {
			this.entreprise = entreprise;
		}


		public Integer getIdTSupport() {
			return idTSupport;
		}


		public void setIdTSupport(Integer idTSupport) {
			this.idTSupport = idTSupport;
		}

//		public List<LigneSelection> getLignes() {
//			return lignes;
//		}
//
//		public void setLignes(List<LigneSelection> lignes) {
//			this.lignes = lignes;
//		}



	}

	/* ------------------- Affichage Section Clients ------------------- */


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
		private String codeTSupport = null;
		private Integer idLDocument = null;
		private String groupe=null;
		private String commercial=null;
		private String entite=null;
		private String entreprise=null;
		private Integer idTSupport = null;
//		private List<LigneSelectionIHM> lignes = null;


		
		public DocumentSelectionIHM(String code, String libelleLigne, String codeTiers,
				String nomTiers, Date dateDocument, String codeArticle,
				String codeTSupport, Integer idLDocument, String groupe,
				String commercial, String entite, String entreprise,Integer idTSupport) {
			super();
			this.code = code;
			this.libelleLigne = libelleLigne;
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.dateDocument = dateDocument;
			this.codeArticle = codeArticle;
			this.codeTSupport = codeTSupport;
			this.idLDocument = idLDocument;
			this.groupe = groupe;
			this.commercial = commercial;
			this.entite = entite;
			this.entreprise = entreprise;
			this.idTSupport = idTSupport;
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

		public String getCodeTSupport() {
			return codeTSupport;
		}

		public void setCodeTSupport(String codeTSupport) {
			firePropertyChange("codeTSupport", this.codeTSupport, this.codeTSupport = codeTSupport);
		}

		public Integer getIdLDocument() {
			return idLDocument;
		}

		public void setIdLDocument(Integer idLDocument) {
			firePropertyChange("idLDocument", this.idLDocument, this.idLDocument = idLDocument);
		}


		public String getGroupe() {
			return groupe;
		}


		public void setGroupe(String groupe) {
			firePropertyChange("groupe", this.groupe, this.groupe = groupe);
		}


		public String getCommercial() {
			return commercial;
		}


		public void setCommercial(String commercial) {
			firePropertyChange("commercial", this.commercial, this.commercial = commercial);
		}


		public String getEntite() {
			return entite;
		}


		public void setEntite(String entite) {
			firePropertyChange("entite", this.entite, this.entite = entite);
		}


		public String getEntreprise() {
			return entreprise;
		}


		public void setEntreprise(String entreprise) {
			firePropertyChange("entreprise", this.entreprise, this.entreprise = entreprise);
		}


		public Integer getIdTSupport() {
			return idTSupport;
		}


		public void setIdTSupport(Integer idTSupport) {
			firePropertyChange("idTSupport", this.idTSupport, this.idTSupport = idTSupport);
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
					e.getCodeArticle(),e.getCodeTSupport(),e.getIdLDocument(),e.getGroupe(),e.getCommercial(),e.getEntite(),e.getEntreprise(),e.getIdTSupport());

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
	private Action editonAction = new Action("Edition",pluginLicence.getImageDescriptor(PaCompositeSectionDocEcheance.iconpath)) { 
		@Override 
		public void run() {
//			EditionGrille edition = new EditionGrille(vue.getForm().getShell());
//			edition.print(vue.getCompositeSectionTableauGauche().getTable());
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

}
