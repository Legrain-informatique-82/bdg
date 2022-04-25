package fr.legrain.articles.statistiques.editors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import com.ibm.icu.util.Calendar;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.DataSetChart;
import fr.legrain.libLgrBirt.chart.MultiSerieBarChart;
import fr.legrain.libLgrBirt.chart.UtilSerie;

public class GraphQuantiteControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(GraphQuantiteControllerMini.class.getName());	
	
	private Class objetIHM = null;
	
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
	private DefaultFormPage vue = null;
	
	public GraphQuantiteControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	
	
	public void initialiseModelIHM(TaArticle masterEntity,TaArticleDAO masterDAO) {
		//affacer le(s) précédent graphique
		for (int i = 0; i < vue.getCompositeSectionGraphQuantite().getCompo().getChildren().length; i++) {
			vue.getCompositeSectionGraphQuantite().getCompo().getChildren()[i].dispose();
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
		
		class ResultatGraphQuantite {
			String jour = null;
			String mois = null;
			String annee = null;;
			String codeArticle = null;
			String unite = null;
			Double qte = 0d;
			
			public ResultatGraphQuantite(String jour, String mois, String annee, String codeArticle, String unite,
					Double qte) {
				super();
				this.jour = jour;
				this.mois = mois;
				this.annee = annee;
				this.codeArticle = codeArticle;
				this.unite = unite;
				this.qte = qte;
			}
			
		}
		
		////////////////////
		
		TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());
		
		List<Object> listeObjectFacture = taFactureDAO.findQuantiteByCodeArticle(masterEntity.getCodeArticle(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				1,
				precision);
		
		Map<String,Map<String,ResultatGraphQuantite>> listResultatGraphQuantite = new LinkedHashMap<String,Map<String,ResultatGraphQuantite>>();
		LinkedList<String> listeUniteDisponible = new LinkedList<String>();
		
		String cle = null;
		ResultatGraphQuantite resultatGraphQuantite = null;
		for (Object object : listeObjectFacture) {
			if(((Object[])object)[5]!=null 
//					&&((Object[])object)[4]!=null /*&& !((String)((Object[])object)[4]).equals("")*/
					 ) { //il y a vraiment une unite 
				cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
				resultatGraphQuantite = new ResultatGraphQuantite(
						(String)((Object[])object)[0],
						(String)((Object[])object)[1],
						(String)((Object[])object)[2], //annee
						(String)((Object[])object)[3], //codeArticle
						(String)((Object[])object)[4], //codeUnite
						((BigDecimal)((Object[])object)[5]).doubleValue() //quantite
				);

				if(!listResultatGraphQuantite.containsKey(cle)) {
					//nouvelle annee
					listResultatGraphQuantite.put(cle, new LinkedHashMap<String,ResultatGraphQuantite>());
				} 
				listResultatGraphQuantite.get(cle).put(resultatGraphQuantite.unite, resultatGraphQuantite);

				if(!listeUniteDisponible.contains(resultatGraphQuantite.unite)) {
					//nouvelle unite encore non traité, ajout d'une série
					listeUniteDisponible.add(resultatGraphQuantite.unite);
				}
			}
		}

		DataSetChart dataSetChart = new DataSetChart();
		
		Map<String,Double[]> mapSeries = new LinkedHashMap<String,Double[]>();
		
		//creation des series
		String[] tableauValeurAbscisse = null;
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			tableauValeurAbscisse = tabAnnee;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			tableauValeurAbscisse = tabMois;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			tableauValeurAbscisse = null;
		}
		
		for (int j = 0; j < listeUniteDisponible.size(); j++) {
			mapSeries.put(listeUniteDisponible.get(j), new Double[tableauValeurAbscisse.length]);
		}

		Map<String,ResultatGraphQuantite> listResultatGraphQuantiteAnnee = null;
		
		for (String nomSerie : mapSeries.keySet()) {
			for (int i = 0; i < tableauValeurAbscisse.length; i++) {
				listResultatGraphQuantiteAnnee = listResultatGraphQuantite.get(tableauValeurAbscisse[i]);
				if(listResultatGraphQuantiteAnnee!=null) {
					if(listResultatGraphQuantiteAnnee.get(nomSerie)!=null){
						mapSeries.get(nomSerie)[i] = listResultatGraphQuantiteAnnee.get(nomSerie).qte;
					} else {
						mapSeries.get(nomSerie)[i] = null;//0d;
					}
				} else {
					mapSeries.get(nomSerie)[i] = null;//0d;
				}
			}
		}
		
		//ajoout des series au graphique
		for (String nomSerie : mapSeries.keySet()) {
			dataSetChart.getMapValuesYSeries().put(nomSerie, mapSeries.get(nomSerie));
		}

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

		MultiSerieBarChart multiSerieBarChart = new MultiSerieBarChart(vue.getCompositeSectionGraphQuantite().getCompo(), SWT.NONE, 
				dataSetChart,"Quantités 1 facturées",ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL,titreAbscisse,"Quantité");

		multiSerieBarChart.build();
		vue.getCompositeSectionGraphQuantite().getCompo().layout();
	}
	
	@Override
	public void bind(){

	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
	}

}
