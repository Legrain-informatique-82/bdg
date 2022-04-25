package fr.legrain.tiers.statistiques.editors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.DocumentIHM;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaAcompte;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaApporteur;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaAvoir;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaBoncde;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaBonliv;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaDevis;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaFacture;
import fr.legrain.tiers.statistiques.editors.DefaultFormPageController.MapperDocumentIHMTaProforma;

public class AutreControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(AutreControllerMini.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private List<ModelObject> modele = null;

	private DefaultFormPageController masterController = null;

	private DefaultFormPage vue = null;

	private List<DocumentIHM> modelDocument = null;

	private boolean evenementInitialise = false;

	public AutreControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}

	public void initialiseModelIHM(TaTiers masterEntity,TaTiersDAO masterDAO) {
		//		setSelectedObject(new MapperIdentite().entityToDto(masterEntity));

		try {
			TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());
			TaDevisDAO taDevisDAO = new TaDevisDAO(masterDAO.getEntityManager());

			TaApporteurDAO taApporteurDAO = new TaApporteurDAO(masterDAO.getEntityManager());
			TaProformaDAO taProformaDAO = new TaProformaDAO(masterDAO.getEntityManager());
			TaAvoirDAO taAvoirDAO = new TaAvoirDAO(masterDAO.getEntityManager());
			TaBoncdeDAO taBoncdeDAO = new TaBoncdeDAO(masterDAO.getEntityManager());
			TaBonlivDAO taBonlivDAO = new TaBonlivDAO(masterDAO.getEntityManager());
			TaAcompteDAO taAcompteDAO = new TaAcompteDAO(masterDAO.getEntityManager());

			List<TaFacture> listeFacture = taFactureDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			List<TaDevis> listeDevis = taDevisDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			List<TaApporteur> listeApporteur = taApporteurDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			List<TaProforma> listeProforma = taProformaDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			List<TaAvoir> listeAvoir = taAvoirDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			List<TaBoncde> listeBoncde = taBoncdeDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			List<TaBonliv> listeBonliv = taBonlivDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);
			
			List<TaAcompte> listeAcompte = taAcompteDAO.findByCodeTiersAndDate(
					masterEntity.getCodeTiers(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			modelDocument = masterController.new MapperDocumentIHMTaFacture().listeEntityToDto(listeFacture);
			modelDocument.addAll(masterController.new MapperDocumentIHMTaDevis().listeEntityToDto(listeDevis));

			modelDocument.addAll(masterController.new MapperDocumentIHMTaApporteur().listeEntityToDto(listeApporteur));
			modelDocument.addAll(masterController.new MapperDocumentIHMTaProforma().listeEntityToDto(listeProforma));
			modelDocument.addAll(masterController.new MapperDocumentIHMTaAvoir().listeEntityToDto(listeAvoir));
			modelDocument.addAll(masterController.new MapperDocumentIHMTaBoncde().listeEntityToDto(listeBoncde));
			modelDocument.addAll(masterController.new MapperDocumentIHMTaBonliv().listeEntityToDto(listeBonliv));
			modelDocument.addAll(masterController.new MapperDocumentIHMTaAcompte().listeEntityToDto(listeAcompte));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void initActions() {
		if(!evenementInitialise) {
			//		pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionAutre().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionAutre().getTable().getSelection()[0].getText(
							getMapLgrTableViewer().get(vue.getCompositeSectionAutre().getTable()).findPositionNomChamp("codeDocument")
					);

					String typeDocument = vue.getCompositeSectionAutre().getTable().getSelection()[0].getText(
							getMapLgrTableViewer().get(vue.getCompositeSectionAutre().getTable()).findPositionNomChamp("typeDocument")
					);

					String idEditor = null;
					if (typeDocument.equals(MapperDocumentIHMTaFacture.TYPE_DOCUMENT)) {
						//					idEditor = FactureMultiPageEditor.ID_EDITOR;
						idEditor = "fr.legrain.editor.facture.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaDevis.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.devis.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaAvoir.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.avoir.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaBoncde.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.boncde.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaBonliv.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.Bl.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaProforma.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.proforma.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaApporteur.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.apporteur.swt.multi";
					} else if (typeDocument.equals(MapperDocumentIHMTaAcompte.TYPE_DOCUMENT)) {
						idEditor = "fr.legrain.editor.acompte.swt.multi";
					}

					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}

			});

			evenementInitialise = true;
		} 
	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(DocumentIHM.class);

		bindTable(vue.getCompositeSectionAutre().getTable(),modelDocument,DocumentIHM.class,
				new String[] {"Code","Libellé","Date","Montant HT","Type"},
				new String[] {"100","250","100","100","0"},
				new String[] {"codeDocument","libelleDocument","dateDocument","montantDocument","typeDocument"}
		);
		initActions();
		//		try {
		//		bindTree(vue.getCompositeSectionAutre().getTree(),modelFacture,DocumentIHM.class,
		//				new String[] {"Code","Libellé","Date","Montant","Type"},
		//				new String[] {"100","250","100","100","0"},
		//				new String[] {"codeDocument","libelleDocument","dateDocument","montantDocument","typeDocument"}
		//		);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}


		//		vue.getCompositeSectionAutre().getCompo().layout();
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeTiers");
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelNom(), "nomTiers");
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelPrenom(), "prenomTiers");

	}

}
