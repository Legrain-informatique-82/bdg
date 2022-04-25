package fr.legrain.document.controller;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Control;

import fr.legrain.document.divers.ModelDocument;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IDocumentTiersComplet;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrDatabindingUtil;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;


public class PaVisuDocumentControlleur extends JPABaseControllerSWTStandard implements RetourEcranListener {
	static Logger logger = Logger.getLogger(PaVisuDocumentControlleur.class
			.getName());
	
	private DefaultFrameGrilleSansBouton vue;
	private TaReglementDAO daoReglement = null;
	private ModelDocument modelDocument;
	private IDocumentTiers masterEntity = null;
	private TaReglementDAO masterDAO = null;
	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
	private Realm realm;
	private DataBindingContext dbc;
	private LgrTableViewer tableViewer;
	private IObservableValue selectedDocument;
	
	public PaVisuDocumentControlleur(DefaultFrameGrilleSansBouton ecran,EntityManager em) {
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

			bind();

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind() {
		try {
			modelDocument = new ModelDocument(daoReglement, IHMEnteteDocument.class);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			vue.getLaTitreGrille().setText("Liste des factures liées au réglement");
//			if(masterEntity.getTypeDocument()==TaReglement.TYPE_DOC){
			selectedDocument = lgrDatabindingUtil.bindTable(vue.getGrille(),modelDocument.remplirListe((masterEntity),getEm()),IHMEnteteFacture.class,
					new String[] {"Code document","Libellé","Date","Date échéance","Code tiers","Net TTC","Déjà réglé","Reste à régler"},
					new String[] {"100","250","100","150","150","150","150","150"},
					new String[] {"codeDocument","libelleDocument","dateDocument","dateEchDocument","codeTiers","netTtcCalc","regleCompletDocument","resteAReglerComplet"}
																											   
			,1);
//			}			else{
//				selectedDocument = lgrDatabindingUtil.bindTable(vue.getGrille(),modelDocument.remplirListe(((TaAvoir)masterEntity),getEm()),IHMEnteteFacture.class,
//						new String[] {"Code document","Libellé","Date","Date échéance","Code tiers","Net TTC","Déjà réglé","Reste à régler"},
//						new String[] {"100","250","100","150","150","150","150","150"},
//						new String[] {"codeDocument","libelleDocument","dateDocument","dateEchDocument","codeTiers","netTtcCalc","regleCompletDocument","resteAReglerComplet"}
//																												   
//				,1);	
//			}
			
			vue.getGrille().addMouseListener(
				new MouseAdapter() {
					public void mouseDoubleClick(MouseEvent e) {
						String idEditor =TypeDoc.getInstance().getEditorDoc()
						.get(((IHMEnteteFacture) selectedDocument.getValue()).getTypeDocument());
						String valeurIdentifiant = ((IHMEnteteFacture) selectedDocument
								.getValue()).getCodeDocument();
						ouvreDocument(valeurIdentifiant, idEditor);
					}

				});
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

	public void actRefresh(IDocumentTiers taDocument) throws Exception {
		setMasterEntity((IDocumentTiers)taDocument);
		if (lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getGrille())==null)
			bind();
		WritableList writableListReglement = new WritableList(modelDocument.
				remplirListe(masterEntity,getEm()), IHMAideFacture.class);
		lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getGrille()).setInput(writableListReglement);
		
	}
//	public void actRefresh(IDocumentTiersComplet taDocument) throws Exception {
//		setMasterEntity((IDocumentTiersComplet)taDocument);
//		if (lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getGrille())==null)
//			bind();
//		WritableList writableListReglement = new WritableList(modelDocument.
//				remplirListe(masterEntity,getEm()), IHMAideFacture.class);
//		lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getGrille()).setInput(writableListReglement);
//		
//	}
	
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
	public DefaultFrameGrilleSansBouton getVue() {
		// TODO Auto-generated method stub
		return vue;
	}
	public void setVue(DefaultFrameGrilleSansBouton vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		
	}
	public IDocumentTiers getMasterEntity() {
		return masterEntity;
	}
	public void setMasterEntity(IDocumentTiers masterEntity) {
		this.masterEntity = masterEntity;
	}
	public TaReglementDAO getMasterDAO() {
		return masterDAO;
	}
	public void setMasterDAO(TaReglementDAO masterDAO) {
		this.masterDAO = masterDAO;
	}
	public TaReglementDAO getDaoReglement() {
		return daoReglement;
	}
	public void setDaoReglement(TaReglementDAO daoReglement) {
		this.daoReglement = daoReglement;
	}

}
