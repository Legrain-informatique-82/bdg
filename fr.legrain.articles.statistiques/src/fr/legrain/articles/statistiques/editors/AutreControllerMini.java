package fr.legrain.articles.statistiques.editors;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.statistiques.editors.DefaultFormPageController.TiersIHM;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;


public class AutreControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(AutreControllerMini.class.getName());	

	private Class objetIHM = null;

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;

	private List<ModelObject> modele = null;

	private DefaultFormPageController masterController = null;

	private DefaultFormPage vue = null;

	private List<TiersIHM> modelDocument = null;
	
	private boolean evenementInitialise = false;

	public AutreControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}

	public void initialiseModelIHM(TaArticle masterEntity,TaArticleDAO masterDAO) {
		try {
			TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());


			List<TaTiers> listeTiers = taFactureDAO.findTiersByCodeArticleAndDate(
					masterEntity.getCodeArticle(),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
					LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
			);

			modelDocument = masterController.new MapperTiersIHMTaTiers().listeEntityToDto(listeTiers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void initActions() {
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionAutre().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionAutre().getTable().getSelection()[0].getText(
							getMapLgrTableViewer().get(vue.getCompositeSectionAutre().getTable()).findPositionNomChamp("codeTiers")
					);

//					String typeDocument = vue.getCompositeSectionAutre().getTable().getSelection()[0].getText(
//							getMapLgrTableViewer().get(vue.getCompositeSectionAutre().getTable()).findPositionNomChamp("typeDocument")
//					);

					String idEditor = "fr.legrain.editor.tiers.multi";
//					if (typeDocument.equals(MapperDocumentIHMTaFacture.TYPE_DOCUMENT)) {
//						//					idEditor = FactureMultiPageEditor.ID_EDITOR;
//						idEditor = "fr.legrain.editor.facture.swt.multi";
//					} else if (typeDocument.equals(MapperDocumentIHMTaApporteur.TYPE_DOCUMENT)) {
//						idEditor = "fr.legrain.editor.apporteur.swt.multi";
//					}

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
		setObjetIHM(TiersIHM.class);

		bindTable(vue.getCompositeSectionAutre().getTable(),modelDocument,TiersIHM.class,
				new String[] {"Code","Nom","Prénom","Entreprise"},
				new String[] {"100","250","100","100"},
				new String[] {"codeTiers","nom","prenom","entreprise"}
		);
		
		initActions();
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

	}

}
