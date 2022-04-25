/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.controllers.a_supprimer;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.swt.SWT;

import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.libLgrBirt.chart.DataSetChart;
import fr.legrain.libLgrBirt.chart.MultiSerieBarChart;
import fr.legrain.libLgrBirt.chart.UtilSerie;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.GraphControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class GraphControllerApporteur extends GraphControllerMini{
	static Logger logger = Logger.getLogger(GraphControllerApporteur.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaApporteur taApporteur = null;
	private TaApporteurDAO taApporteurDAO = null;
	private TaDevisDAO taDevisDAO = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBonlivDAO taBonlivDAO = null;
	

	
	public GraphControllerApporteur(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {
		datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());

		// On efface le(s) précédent(s) graphique(s)
		for (int i = 0; i < vue.getCompositeSectionEvolution().getCompo().getChildren().length; i++) {
			vue.getCompositeSectionEvolution().getCompo().getChildren()[i].dispose();
		}

		// On initialise la précision
		int precision = 0;
		if(vue.getCompositeSectionParam().getBtnJour().getSelection()) {
			precision = UtilSerie.PRECISION_GRAPH_JOUR;
		} else if (vue.getCompositeSectionParam().getBtnMois().getSelection()){
			precision = UtilSerie.PRECISION_GRAPH_MOIS;
		} else { //annee
			precision = UtilSerie.PRECISION_GRAPH_ANNEE;
		}

		// On ajuste le nombre d'élément dans les séries	
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

		// Classe Chiffre d'affaires
		class ResultatCA {
			// date de la apporteur
			String jour = null;
			String mois = null;
			String annee = null;
			// infos quantitatives
			Double ht = 0d;
			Double tva = 0d;
			Double ttc = 0d;

			/**
			 * Constructeur de Resultat Chiffre d'affaires
			 * @param jour le jour d'édition de la apporteur
			 * @param mois le mois " "
			 * @param annee l'année " "
			 * @param ht montant ht de la apporteur
			 * @param tva tva de la apporteur
			 * @param ttc montant ttc de la apporteur
			 */
			public ResultatCA(String jour, String mois, String annee, Double ht,
					Double tva, Double ttc) {
				super();
				// date apporteur
				this.jour = jour;
				this.mois = mois;
				this.annee = annee;
				// infos quantitatives
				this.ht = ht;
				this.tva = tva;
				this.ttc = ttc;
			}

		}


		// On récupère les éléments dans la base de données
		taApporteurDAO = new TaApporteurDAO(masterController.getMasterDAOEM());


		List<Object> listeObjectApporteur = taApporteurDAO.findChiffreAffaireTotal(
				datedeb,datefin,
				precision);


		String cle = null;
		Map<String,ResultatCA> listResultatCAApporteur = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectApporteur) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCAApporteur.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(Double)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5]));
		}



		DataSetChart dataSetChart = new DataSetChart();

		Double[] serieApporteur = new Double[nbElemeSerie];

		ResultatCA resultatCAApporteur = null;

		String[] tableauValeurAbscisse = null;
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			tableauValeurAbscisse = tabAnnee;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			tableauValeurAbscisse = tabMois;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			tableauValeurAbscisse = null;
		}

		for (int i = 0; i < tableauValeurAbscisse.length; i++) {
			resultatCAApporteur = listResultatCAApporteur.get(tableauValeurAbscisse[i]);

			if(resultatCAApporteur!=null) {
				serieApporteur[i] = resultatCAApporteur.ht;
			} else {
				serieApporteur[i] = null;//0d;
			}

		}

		dataSetChart.getMapValuesYSeries().put("CA", serieApporteur);

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

		MultiSerieBarChart multiSerieBarChart = new MultiSerieBarChart(vue.getCompositeSectionEvolution().getCompo(), SWT.NONE, 
				dataSetChart,"Evolution des factures apporteur HT",ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL,titreAbscisse,"Montant HT");

		
		
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
		multiSerieBarChart.getChart().getLegend().getText().getFont().setSize(12);
		multiSerieBarChart.getXAxisPrimary().getTitle().getCaption().getFont().setSize(12);
		multiSerieBarChart.getYAxisPrimary().getTitle().getCaption().getFont().setSize(12);
		multiSerieBarChart.getXAxisPrimary().getLabel().getCaption().getFont().setSize(10);
		multiSerieBarChart.getYAxisPrimary().getLabel().getCaption().getFont().setSize(10);


		vue.getCompositeSectionEvolution().getCompo().layout();
	}


}
