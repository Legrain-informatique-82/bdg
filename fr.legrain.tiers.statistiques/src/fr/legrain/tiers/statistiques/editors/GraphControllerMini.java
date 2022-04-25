package fr.legrain.tiers.statistiques.editors;

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

import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.DataSetChart;
import fr.legrain.libLgrBirt.chart.MultiSerieBarChart;
import fr.legrain.libLgrBirt.chart.UtilSerie;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class GraphControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(GraphControllerMini.class.getName());	
	
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
//	private Map<Control, String> mapComposantChampsIdentite = null;
	
	private DefaultFormPage vue = null;
	
	public GraphControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	public void initialiseModelIHM(TaTiers masterEntity,TaTiersDAO masterDAO) {
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
		
//		String[] tabJour = UtilSerie.listeJoursEntre2Date(
//				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
//				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()));

		//////////
		
		int nbElemeSerie = 0;
		
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			nbElemeSerie = tabAnnee.length;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			nbElemeSerie = tabMois.length;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			//nbElemeSerie = tabJour.length;
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
		TaDevisDAO taDevisDAO = new TaDevisDAO(masterDAO.getEntityManager());
		
		TaApporteurDAO taApporteurDAO = new TaApporteurDAO(masterDAO.getEntityManager());
		TaProformaDAO taProformaDAO = new TaProformaDAO(masterDAO.getEntityManager());
		TaAvoirDAO taAvoirDAO = new TaAvoirDAO(masterDAO.getEntityManager());
		TaBoncdeDAO taBoncdeDAO = new TaBoncdeDAO(masterDAO.getEntityManager());
		TaBonlivDAO taBonlivDAO = new TaBonlivDAO(masterDAO.getEntityManager());
		TaAcompteDAO taAcompteDAO = new TaAcompteDAO(masterDAO.getEntityManager());
		
		List<Object> listeObjectFacture = taFactureDAO.findChiffreAffaireByCodeTiers(masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		List<Object> listeObjectDevis = taDevisDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		List<Object> listeObjectApporteur = taApporteurDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		List<Object> listeObjectProforma = taProformaDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		List<Object> listeObjectAvoir = taAvoirDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
			
		List<Object> listeObjectBoncde = taBoncdeDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		List<Object> listeObjectBonliv = taBonlivDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),
				precision);
		
		List<Object> listeObjectAcompte = taAcompteDAO.findChiffreAffaireByCodeTiers(
				masterEntity.getCodeTiers(),
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
		
		Map<String,ResultatCA> listResultatCADevis = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectDevis) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCADevis.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(String)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5], 
							(Double)((Object[])object)[6]));
		}
		
		Map<String,ResultatCA> listResultatCAApporteur = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectApporteur) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCAApporteur.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(String)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5], 
							(Double)((Object[])object)[6]));
		}
		
		Map<String,ResultatCA> listResultatCAProforma = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectProforma) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCAProforma.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(String)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5], 
							(Double)((Object[])object)[6]));
		}
		
		Map<String,ResultatCA> listResultatCAAvoir = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectAvoir) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCAAvoir.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(String)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5], 
							(Double)((Object[])object)[6]));
		}
		
		Map<String,ResultatCA> listResultatCABoncde = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectBoncde) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCABoncde.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(String)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5], 
							(Double)((Object[])object)[6]));
		}
		
		Map<String,ResultatCA> listResultatCABonliv = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectBonliv) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCABonliv.put(cle,
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1],
							(String)((Object[])object)[2],
							(String)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5], 
							(Double)((Object[])object)[6]));
		}
		
		Map<String,ResultatCA> listResultatCAAcompte = new LinkedHashMap<String,ResultatCA>();
		for (Object object : listeObjectAcompte) {
			cle = UtilSerie.genereCleDate((String)((Object[])object)[0], (String)((Object[])object)[1], (String)((Object[])object)[2], precision);
			listResultatCAAcompte.put(cle,
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

		Double[] serieDevis = new Double[nbElemeSerie];
		Double[] serieFacture = new Double[nbElemeSerie];
		Double[] serieApporteur = new Double[nbElemeSerie];
		Double[] serieProforma = new Double[nbElemeSerie];
		Double[] serieAvoir = new Double[nbElemeSerie];
		Double[] serieBoncde = new Double[nbElemeSerie];
		Double[] serieBonliv = new Double[nbElemeSerie];
		Double[] serieAcompte = new Double[nbElemeSerie];
		
		ResultatCA resultatCADevis = null;
		ResultatCA resultatCAFacture = null;
		ResultatCA resultatCAApporteur = null;
		ResultatCA resultatCAProforma = null;
		ResultatCA resultatCAAvoir = null;
		ResultatCA resultatCABoncde = null;
		ResultatCA resultatCABonliv = null;
		ResultatCA resultatCAAcompte = null;
		
		String[] tableauValeurAbscisse = null;
		if(precision==UtilSerie.PRECISION_GRAPH_ANNEE) {
			tableauValeurAbscisse = tabAnnee;
		} else if (precision==UtilSerie.PRECISION_GRAPH_MOIS){
			tableauValeurAbscisse = tabMois;
		} else if (precision==UtilSerie.PRECISION_GRAPH_JOUR){
			tableauValeurAbscisse = null;
		}
		
		for (int i = 0; i < tableauValeurAbscisse.length; i++) {
			resultatCADevis = listResultatCADevis.get(tableauValeurAbscisse[i]);
			resultatCAFacture = listResulatCAFacture.get(tableauValeurAbscisse[i]);
			resultatCAApporteur = listResultatCAApporteur.get(tableauValeurAbscisse[i]);
			resultatCAProforma = listResultatCAProforma.get(tableauValeurAbscisse[i]);
			resultatCAAvoir = listResultatCAAvoir.get(tableauValeurAbscisse[i]);
			resultatCABoncde = listResultatCABoncde.get(tableauValeurAbscisse[i]);
			resultatCABonliv = listResultatCABonliv.get(tableauValeurAbscisse[i]);
			resultatCAAcompte = listResultatCAAcompte.get(tableauValeurAbscisse[i]);
			
			if(resultatCADevis!=null) {
				serieDevis[i] = resultatCADevis.ht;
			} else {
				serieDevis[i] = null;//0d;
			}
			
			if(resultatCAFacture!=null) {
				serieFacture[i] = resultatCAFacture.ht;
			} else {
				serieFacture[i] = null;//0d;
			}
			
			if(resultatCAApporteur!=null) {
				serieApporteur[i] = resultatCAApporteur.ht;
			} else {
				serieApporteur[i] = null;//0d;
			}
			
			if(resultatCAProforma!=null) {
				serieProforma[i] = resultatCAProforma.ht;
			} else {
				serieProforma[i] = null;//0d;
			}
			
			if(resultatCAAvoir!=null) {
				serieAvoir[i] = resultatCAAvoir.ht;
			} else {
				serieAvoir[i] = null;//0d;
			}
			
			if(resultatCABoncde!=null) {
				serieBoncde[i] = resultatCABoncde.ht;
			} else {
				serieBoncde[i] = null;//0d;
			}
			
			if(resultatCABonliv!=null) {
				serieBonliv[i] = resultatCABonliv.ht;
			} else {
				serieBonliv[i] = null;//0d;
			}
			
			if(resultatCAAcompte!=null) {
				serieAcompte[i] = resultatCAAcompte.ht;
			} else {
				serieAcompte[i] = null;//0d;
			}
		}
		
		dataSetChart.getMapValuesYSeries().put("Devis", serieDevis);
		dataSetChart.getMapValuesYSeries().put("Facture", serieFacture);
		dataSetChart.getMapValuesYSeries().put("Apporteur", serieApporteur);
		dataSetChart.getMapValuesYSeries().put("Proforma", serieProforma);
		dataSetChart.getMapValuesYSeries().put("Avoir", serieAvoir);
		dataSetChart.getMapValuesYSeries().put("Bon de commande", serieBoncde);
		dataSetChart.getMapValuesYSeries().put("Bon de livraison", serieBonliv);
		dataSetChart.getMapValuesYSeries().put("Acompte", serieAcompte);

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
//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelNom(), "nomTiers");
//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelPrenom(), "prenomTiers");

	}

}
