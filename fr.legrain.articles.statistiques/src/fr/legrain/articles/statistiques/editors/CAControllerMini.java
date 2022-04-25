package fr.legrain.articles.statistiques.editors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.UtilSerie;

public class CAControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(CAControllerMini.class.getName());	
	
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
	private DefaultFormPage vue = null;
	
	public CAControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	public void initialiseModelIHM(TaArticle masterEntity,TaArticleDAO masterDAO) {
		
		class ResultatCA {
			String jour = null;
			String mois = null;
			String annee = null;
			String codeTiers = null;
			Double ht = 0d;
			Double tva = 0d;
			Double ttc = 0d;

			public ResultatCA(String jour, String mois, String annee, String codeTiers, Double ht,
					Double tva, Double ttc) {
				super();
				this.jour = jour;
				this.mois = mois;
				this.annee = annee;
				this.codeTiers = codeTiers;
				this.ht = ht;
				this.tva = tva;
				this.ttc = ttc;
			}

		}
		
		TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());
//		List<TaFacture> listeFacture = taFactureDAO.findByCodeTiersAndDate(
//				masterEntity.getCodeArticle(),
//				vue.getCompositeSectionParam().getCdateDeb().getSelection(),
//				vue.getCompositeSectionParam().getCdatefin().getSelection()
//				);
		
		List<Object> listeFacture = taFactureDAO.findChiffreAffaireByCodeArticle(
				masterEntity.getCodeArticle(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				UtilSerie.PRECISION_GRAPH_ANNEE);

		LinkedList<ResultatCA> listResulatCAFacture = new LinkedList<ResultatCA>();
		for (Object object : listeFacture) {
			listResulatCAFacture.add(
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1], 
					(String)((Object[])object)[2],
					(String)((Object[])object)[3], 
					(Double)((Object[])object)[4], 
					(Double)((Object[])object)[5], 
					(Double)((Object[])object)[6])
					);
		}

		double caTotal = 0;

		for (ResultatCA resultatCA : listResulatCAFacture) {
			caTotal += resultatCA.ht;
		}
		caTotal = LibCalcul.arrondi(caTotal);
		DefaultFormPageController.CAIHM ca = masterController.new CAIHM(new BigDecimal(String.valueOf(caTotal)));
		
		setSelectedObject(ca);
	}
	
	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(DefaultFormPageController.CAIHM.class);
		bindForm(mapComposantChamps, DefaultFormPageController.CAIHM.class, getSelectedObject(), vue.getSectionCA().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
		mapComposantChamps.put(vue.getCompositeSectionCA().getText(), "chiffreAffaire");

	}

}
