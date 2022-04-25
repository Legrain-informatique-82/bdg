package fr.legrain.document.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;

import fr.legrain.document.divers.ModelRReglement;
//import fr.legrain.document.divers.ParamAfficheReglementMultiple;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiersComplet;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrDatabindingUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheReglementMultiple;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton_grilleReduite;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;


public class PaVisuReglementControlleur extends JPABaseControllerSWTStandard implements RetourEcranListener {
	static Logger logger = Logger.getLogger(PaVisuReglementControlleur.class
			.getName());
	
	private DefaultFrameGrilleSansBouton_grilleReduite vue;
	private TaReglementDAO daoReglement = null;
	private ModelRReglement modelReglement;

	private IDocumentTiersComplet masterEntity = null;
	private IDocumentTiersDAO masterDAO = null;
	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
	private Realm realm;
	private DataBindingContext dbc;
	private LgrTableViewer tableViewer;
	private IObservableValue selectedReglement;
	
	
	
	public PaVisuReglementControlleur(DefaultFrameGrilleSansBouton_grilleReduite ecran,EntityManager em) {
		if (em != null) {
			setEm(em);
		}
		try {
			setVue(ecran);
			daoReglement = new TaReglementDAO(getEm());

			this.vue.getShell().addShellListener(this);
			this.vue.getShell().addTraverseListener(new Traverse());

			initController();

		} catch (Exception e) {
			logger.debug("", e);
		}
	}
	private void initController() {
		try {
			setDaoStandard(daoReglement);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

//			initVue();

			initMapComposantChamps();
			initMapComposantDecoratedField();

			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();

			bindReglements();

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bindReglements() {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			modelReglement = new ModelRReglement();

			vue.getLaTitreGrille().setText(
					"Liste des règlements du document en cours");

			tableViewer = new LgrTableViewer(vue.getGrille());

			tableViewer.createTableCol(IHMReglement.class,
					 vue.getGrille(),
					"ReglementsDocument", Const.C_FICHIER_LISTE_CHAMP_GRILLE,
					0);
			String[] listeChampAffectation = tableViewer			
					.setListeChampGrille("ReglementsDocument",
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedReglement = ViewersObservables
					.observeSingleSelection(tableViewer);
			if(masterEntity==null)
				LgrViewerSupport.bind(tableViewer, new WritableList(
						modelReglement.razListeReglements(),
						IHMReglement.class), BeanProperties
						.values(listeChampAffectation));
			else
				LgrViewerSupport.bind(tableViewer, new WritableList(
					modelReglement.remplirListeReglementsFactureTous((TaFacture)masterEntity),
					IHMReglement.class), BeanProperties
					.values(listeChampAffectation));
			
			tableViewer.tri(IHMReglement.class, tableViewer.getListeChamp(), tableViewer.getTitresColonnes());
			
			selectedReglement.addChangeListener(new IChangeListener() {
				public void handleChange(ChangeEvent event) {
					//changementDeSelection();
				}
			});
			dbc = new DataBindingContext(realm);

			bindingFormMaitreDetail(mapComposantChamps,
					dbc, realm, selectedReglement, IHMReglement.class);
			


			vue.getGrille().addMouseListener(
			new MouseAdapter() {
				public void mouseDoubleClick(MouseEvent e) {
					ParamAfficheReglementMultiple paramAfficheReglementMultiple = 
						new ParamAfficheReglementMultiple();
					
					paramAfficheReglementMultiple.setIdFacture(masterEntity.getIdDocument());
					//paramAfficheReglementMultiple.setDateDeb(((IHMReglement)selectedReglement.getValue()).getDateDocument());
					//paramAfficheReglementMultiple.setDateFin(((IHMReglement)selectedReglement.getValue()).getDateDocument());
					paramAfficheReglementMultiple.setIdTiers(masterEntity.getTaTiers().getIdTiers());
					paramAfficheReglementMultiple.setCodeReglement(((IHMReglement)selectedReglement.getValue()).getCodeDocument());
					
					ChangementDePageEvent changementDePageEvent = new ChangementDePageEvent(this);
					changementDePageEvent.setSens(ChangementDePageEvent.AUTRE);
					changementDePageEvent.setPosition(1);
					changementDePageEvent.setParamPage(paramAfficheReglementMultiple);
					
					fireChangementDePage(changementDePageEvent);

				}

			});

			
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bind() {
		try {
			modelReglement = new ModelRReglement();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			vue.getLaTitreGrille().setText("Liste des règlements liées au document");
			String[] listeChampAffectation = setListeChampGrille("ReglementsDocument",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			
			selectedReglement = lgrDatabindingUtil.bindTable(vue.getGrille(),modelReglement.remplirListeReglementsFactureTous((TaFacture)masterEntity),IHMReglement.class,
					listeChampAffectation,
					setListeTailleChampGrille("ReglementsDocument", Const.C_FICHIER_LISTE_CHAMP_GRILLE),
					setListeTitreChampGrille("ReglementsDocument", Const.C_FICHIER_LISTE_CHAMP_GRILLE)
			);
			
			
//			vue.getGrille().addMouseListener(
//				new MouseAdapter() {
//					public void mouseDoubleClick(MouseEvent e) {
//						String idEditor =TypeDoc.getInstance().getEditorDoc()
//						.get(((IHMEnteteFacture) selectedReglement
//										.getValue())
//										.getTypeDocument());
//						String valeurIdentifiant = ((IHMEnteteFacture) selectedReglement
//								.getValue()).getCodeDocument();
//						ouvreDocument(valeurIdentifiant, idEditor);
//					}
//
//				});
		} 
		catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actEnregistrer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actImprimer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actModifier() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actRefresh() throws Exception {
		actRefresh(masterEntity);
	}

	public void actRefresh(IDocumentTiersComplet taReglement) throws Exception {
		setMasterEntity(taReglement);
		if(tableViewer==null)bindReglements();
		WritableList writableListReglement = new WritableList(modelReglement.
		remplirListeReglementsFactureTous((TaFacture)masterEntity), IHMReglement.class);
		tableViewer.setInput(writableListReglement);
		tableViewer.selectionGrille(0);
	}
	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initActions() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());
	}

	@Override
	protected void initMapComposantDecoratedField() {
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
	public DefaultFrameGrilleSansBouton_grilleReduite getVue() {
		// TODO Auto-generated method stub
		return vue;
	}
	public void setVue(DefaultFrameGrilleSansBouton_grilleReduite vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		
	}
	public IDocumentTiersComplet getMasterEntity() {
		return masterEntity;
	}
	public void setMasterEntity(IDocumentTiersComplet masterEntity) {
		this.masterEntity = masterEntity;
	}
	public IDocumentTiersDAO getMasterDAO() {
		return masterDAO;
	}
	public void setMasterDAO(IDocumentTiersDAO masterDAO) {
		this.masterDAO = masterDAO;
	}
	public TaReglementDAO getDaoReglement() {
		return daoReglement;
	}
	public void setDaoReglement(TaReglementDAO daoReglement) {
		this.daoReglement = daoReglement;
	}

	public ModelRReglement getModelReglement() {
		return modelReglement;
	}
}
