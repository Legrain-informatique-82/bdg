/**
 * GraphController.java
 */
package fr.legrain.statistiques.devis.controllers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.swt.SWT;

import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.libLgrBirt.chart.DataSetChart;
import fr.legrain.libLgrBirt.chart.StackedMultiSerieBarChart;
import fr.legrain.libLgrBirt.chart.UtilSerie;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.GraphControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section graph des devis
 * @author nicolas²
 *
 */
public class GraphControllerDevis extends GraphControllerMini{
	static Logger logger = Logger.getLogger(GraphControllerDevis.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaFacture taFacture = null;
	private TaDevisDAO taDevisDAO = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBonlivDAO taBonlivDAO = null;



	public GraphControllerDevis(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {

		/* -- Extraction de la date -- */
		datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());

		/* -- Libération des précédents graphiques -- */
		for (int i = 0; i < vue.getCompositeSectionEvolution().getCompo().getChildren().length; i++) {
			vue.getCompositeSectionEvolution().getCompo().getChildren()[i].dispose();
		}

		/* -- Initialisation de la précision -- */
		int precision = 0;
		if(vue.getCompositeSectionParam().getBtnJour().getSelection()) {
			precision = UtilSerie.PRECISION_GRAPH_JOUR;
		} else if (vue.getCompositeSectionParam().getBtnMois().getSelection()){
			precision = UtilSerie.PRECISION_GRAPH_MOIS;
		} else { //annee
			precision = UtilSerie.PRECISION_GRAPH_ANNEE;
		}

		/* -- Ajustement des éléments des séries -- */
		String[] tabAnnee = UtilSerie.listeAnneeEntre2Date(datedeb,datefin);

		String[] tabMois = UtilSerie.listeMoisEntre2Date(datedeb,datefin);


		int nbElemeSerie = 0;

		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			nbElemeSerie = tabAnnee.length;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			nbElemeSerie = tabMois.length;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			nbElemeSerie = 0;
		}


		// On récupère les éléments dans la base de données
		taDevisDAO = new TaDevisDAO(masterController.getMasterDAOEM());
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());


		List<Object> listeObjectDevisTot = taRDocumentDAO.findChiffreAffaireTotalByTypeDoc("TaDevis",
				datedeb,datefin,
				precision);
		
		List<Object> listeObjectDevisTrans = taDevisDAO.findChiffreAffaireTotalTransfo(
				datedeb, datefin, 
				precision);

		

		String cle = null;
		Map<String,ResultatDevis> listResultatCADevisTot = new LinkedHashMap<String,ResultatDevis>();
		for (Object object : listeObjectDevisTot) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCADevisTot.put(cle,
					new ResultatDevis(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							LibConversion.BigDecimalToDouble((BigDecimal)((Object[])object)[3]), 
							LibConversion.BigDecimalToDouble((BigDecimal)((Object[])object)[4]), 
							LibConversion.BigDecimalToDouble((BigDecimal)((Object[])object)[5])));
		}
		
		
		Map<String,ResultatDevis> listResultatCADevisTransfo = new LinkedHashMap<String,ResultatDevis>();
		for (Object object : listeObjectDevisTrans) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCADevisTransfo.put(cle,
					new ResultatDevis(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							LibConversion.BigDecimalToDouble((BigDecimal)((Object[])object)[3]), 
							LibConversion.BigDecimalToDouble((BigDecimal)((Object[])object)[4]), 
							LibConversion.BigDecimalToDouble((BigDecimal)((Object[])object)[5])));
		}
		
		



		DataSetChart dataSetChart = new DataSetChart();
		DataSetChart overDataSetChart = new DataSetChart();

		Double[] serieDevisTot = new Double[nbElemeSerie];
		Double[] serieDevisTrans = new Double[nbElemeSerie];

		ResultatDevis resultatCADevisTot = null;
		ResultatDevis resultatCADevisTrans = null;

		String[] tableauValeurAbscisse = null;

		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			tableauValeurAbscisse = tabAnnee;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			tableauValeurAbscisse = tabMois;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			tableauValeurAbscisse = null;
		}

		for (int i = 0; i < tableauValeurAbscisse.length; i++) {
			resultatCADevisTot = listResultatCADevisTot.get(tableauValeurAbscisse[i]);
			resultatCADevisTrans = listResultatCADevisTransfo.get(tableauValeurAbscisse[i]);
			
			if(resultatCADevisTot!=null && resultatCADevisTrans!=null) {
				serieDevisTot[i] = resultatCADevisTot.ht - resultatCADevisTrans.ht ;
			} else if(resultatCADevisTot!=null && resultatCADevisTrans==null) {
				serieDevisTot[i] = resultatCADevisTot.ht ;
			} else {
				serieDevisTot[i] = null;//0d;
			}
			
			if(resultatCADevisTrans!=null) {
				serieDevisTrans[i] = resultatCADevisTrans.ht;
			} else {
				serieDevisTrans[i] = null;//0d;
			}

		}

		dataSetChart.getMapValuesYSeries().put("Devis transformés", serieDevisTrans);
		overDataSetChart.getMapValuesYSeries().put("Devis non transformés", serieDevisTot);
		
		String[] value = tableauValeurAbscisse;

		dataSetChart.setArrayValuesXSeries(value);
		overDataSetChart.setArrayValuesXSeries(value);

		String titreAbscisse = "";
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			titreAbscisse = "Année";
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			titreAbscisse = "Mois";
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			titreAbscisse = "Jour";
		}

		StackedMultiSerieBarChart multiSerieBarChart = new StackedMultiSerieBarChart(vue.getCompositeSectionEvolution().getCompo(), SWT.NONE, 
				dataSetChart,overDataSetChart,"Evolution des Devis",ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL,titreAbscisse,"Montant HT");



		// Mise en forme des dates sous forme de calendriers grégoriens
		GregorianCalendar calendardebmois = new GregorianCalendar(); 
		GregorianCalendar calendardebannee = new GregorianCalendar(); 
		calendardebannee.setTime( datedeb );
		calendardebannee.add (Calendar.YEAR, 12); // on ajoute douze ans 
		calendardebmois.setTime( datedeb );
		calendardebmois.add (Calendar.MONTH, 12); // on ajoute douze mois 

		GregorianCalendar calendarfin = new GregorianCalendar(); 
		calendarfin.setTime( datefin );

		// Si le graphique comporte trop de barres (+12 a peu près), on n'affiche pas les labels
		if (precision==UtilSerie.PRECISION_GRAPH_MOIS && calendardebmois.compareTo(calendarfin)>0 
				|| precision==UtilSerie.PRECISION_GRAPH_ANNEE && calendardebannee.compareTo(calendarfin)>0) {
			multiSerieBarChart.setLabelAxisVisibility(true);
			multiSerieBarChart.setLabelAxisSize(10);
			multiSerieBarChart.setLabelAxisRotation(25);
		} else {
			multiSerieBarChart.setLabelAxisVisibility(false);
		}

		// Construction du graphique
		multiSerieBarChart.build();

		// Arrangement des légendes pour plus de clarté
		multiSerieBarChart.getChart().getLegend().getText().getFont().setSize(10);
		multiSerieBarChart.getXAxisPrimary().getTitle().getCaption().getFont().setSize(12);
		multiSerieBarChart.getYAxisPrimary().getTitle().getCaption().getFont().setSize(12);
		multiSerieBarChart.getXAxisPrimary().getLabel().getCaption().getFont().setSize(10);
		multiSerieBarChart.getYAxisPrimary().getLabel().getCaption().getFont().setSize(10);


		vue.getCompositeSectionEvolution().getCompo().layout();
	}
	
	// Classe Devis
	class ResultatDevis {
		// date de la facture
		String jour = null;
		String mois = null;
		String annee = null;
		// infos quantitatives
		Double ht = 0d;
		Double tva = 0d;
		Double ttc = 0d;

		/**
		 * Constructeur de Resultat de Devis
		 * @param jour le jour d'édition du devis
		 * @param mois le mois " "
		 * @param annee l'année " "
		 * @param ht montant ht du devis
		 * @param tva tva du devis
		 * @param ttc montant ttc du devis
		 */
		public ResultatDevis(String jour, String mois, String annee, Double ht,
				Double tva, Double ttc) {
			super();
			// date devis
			this.jour = jour;
			this.mois = mois;
			this.annee = annee;
			// infos quantitatives
			this.ht = ht;
			this.tva = tva;
			this.ttc = ttc;
		}

	}


}
