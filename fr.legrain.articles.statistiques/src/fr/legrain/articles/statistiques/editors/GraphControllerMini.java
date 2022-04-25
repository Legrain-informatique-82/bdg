package fr.legrain.articles.statistiques.editors;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.ibm.icu.util.Calendar;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
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
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.DataSetChart;
import fr.legrain.libLgrBirt.chart.MultiSerieBarChart;
import fr.legrain.libLgrBirt.chart.UtilSerie;

public class GraphControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(GraphControllerMini.class.getName());	
	
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
//	private Map<Control, String> mapComposantChampsIdentite = null;
	
	private DefaultFormPage vue = null;
	
	public GraphControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	
	
	public void initialiseModelIHM(TaArticle masterEntity,TaArticleDAO masterDAO) {
		//affacer le(s) précédent graphique
		for (int i = 0; i < vue.getCompositeSectionGraph().getCompo().getChildren().length; i++) {
			vue.getCompositeSectionGraph().getCompo().getChildren()[i].dispose();
		}
		
		int precision = 0;
		if(vue.getCompositeSectionParam().getBtnJour().getSelection()) {
			precision = UtilSerie.PRECISION_GRAPH_JOUR;
		} else if (vue.getCompositeSectionParam().getBtnMois().getSelection()){
			precision = UtilSerie.PRECISION_GRAPH_MOIS;
		} else { //annee
			precision = UtilSerie.PRECISION_GRAPH_ANNEE;
		}
		
		//////////
		//ajustement du nombre d'élément dans les séries	
		
		String[] tabAnnee = UtilSerie.listeAnneeEntre2Date(
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()));
		
		String[] tabMois = UtilSerie.listeMoisEntre2Date(
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()));
		
		//////////
		
		int nbElemeSerie = 0;
		
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			nbElemeSerie = tabAnnee.length;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			nbElemeSerie = tabMois.length;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			nbElemeSerie = 0;
		}
		
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
		
		////////////////////
		
		TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());

		
		List<Object> listeObjectFacture = taFactureDAO.findChiffreAffaireByCodeArticle(masterEntity.getCodeArticle(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		
		String cle = null;
		Map<String,ResultatCA> listResulatCAFacture = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectFacture) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResulatCAFacture.put(cle,
					new ResultatCA(
					(String)((Object[])object)[0],
					(String)((Object[])object)[1],
					(String)((Object[])object)[2],
					(String)((Object[])object)[3], 
					(Double)((Object[])object)[4], 
					(Double)((Object[])object)[5], 
					(Double)((Object[])object)[6]));
		}
		


		DataSetChart dataSetChart = new DataSetChart();

		Double[] serieFacture = new Double[nbElemeSerie];

		ResultatCA resultatCAFacture = null;

		String[] tableauValeurAbscisse = null;
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			tableauValeurAbscisse = tabAnnee;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			tableauValeurAbscisse = tabMois;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			tableauValeurAbscisse = null;
		}
		
		for (int i = 0; i < tableauValeurAbscisse.length; i++) {
			resultatCAFacture = listResulatCAFacture.get(tableauValeurAbscisse[i]);
			
			if(resultatCAFacture!=null) {
				serieFacture[i] = resultatCAFacture.ht;
			} else {
				serieFacture[i] = null;//0d;
			}
			
		}

		dataSetChart.getMapValuesYSeries().put("Facture", serieFacture);

		String[] value = tableauValeurAbscisse;
		dataSetChart.setArrayValuesXSeries(value);
		
		String titreAbscisse = "";
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			titreAbscisse = "Année";
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			titreAbscisse = "Mois";
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			titreAbscisse = "Jour";
		}

		MultiSerieBarChart multiSerieBarChart = new MultiSerieBarChart(vue.getCompositeSectionGraph().getCompo(), SWT.NONE, 
				dataSetChart,"Evolution du chiffre d'affaire HT",ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL,titreAbscisse,"Montant HT");

		multiSerieBarChart.build();
		vue.getCompositeSectionGraph().getCompo().layout();
	}
	
	@Override
	public void bind(){
//		if(mapComposantChamps==null) {
//			initMapComposantChamps();
//		}
//		setObjetIHM(IdentiteIHM.class);
//		bindForm(mapComposantChamps, IdentiteIHM.class, getSelectedObject(), vue.getSectionIdentite().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeTiers");
//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelLibelleC(), "nomTiers");
//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelLibelleL(), "prenomTiers");

	}

}
