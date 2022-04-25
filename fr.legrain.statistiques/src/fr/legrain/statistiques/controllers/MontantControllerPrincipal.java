package fr.legrain.statistiques.controllers;

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
import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.UtilSerie;
import fr.legrain.statistiques.LgrUpdateValueStrategyAffichageMilliersAvecEuros;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.ecrans.PaFormPage;

public class MontantControllerPrincipal extends MontantControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerPrincipal.class.getName());	

	private Class objetIHM = null;
	private TaFactureDAO taFactureDAO = null;
	private TaFacture taFacture = null;
	private List<ModelObject> modele = null;
	private PaFormPage vue = null;
	private FormPageControllerPrincipal masterController = null;
	
	
	
	public MontantControllerPrincipal(FormPageControllerPrincipal masterController, PaFormPage vue,
			EntityManager em) {
		super(masterController, vue, em);
		this.masterController = masterController;
		this.vue = vue;
	}



	public void initialiseModelIHM() {

		// Classe Chiffre d'affaires
		class ResultatCA {
			// date de la facture
			String jour = null;
			String mois = null;
			String annee = null;
			// infos quantitatives
			Double ht = 0d;
			Double tva = 0d;
			Double ttc = 0d;

			/**
			 * Constructeur de Resultat Chiffre d'affaires
			 * @param jour le jour d'édition de la facture
			 * @param mois le mois " "
			 * @param annee l'année " "
			 * @param ht montant ht de la facture
			 * @param tva tva de la facture
			 * @param ttc montant ttc de la facture
			 */
			public ResultatCA(String jour, String mois, String annee, Double ht,
					Double tva, Double ttc) {
				super();
				// date facture
				this.jour = jour;
				this.mois = mois;
				this.annee = annee;
				// infos quantitatives
				this.ht = ht;
				this.tva = tva;
				this.ttc = ttc;
			}

		}

		taFactureDAO = new TaFactureDAO(super.getMasterController().getMasterDAOEM());
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		List<Object> listeFacture = taFactureDAO.findChiffreAffaireTotal(Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()),
				UtilSerie.PRECISION_GRAPH_ANNEE);

		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<ResultatCA> listResulatCAFacture = new LinkedList<ResultatCA>();
		for (Object object : listeFacture) {
			listResulatCAFacture.add(
					new ResultatCA(
							(String)((Object[])object)[0],
							(String)((Object[])object)[1], 
							(String)((Object[])object)[2],
							(Double)((Object[])object)[3], 
							(Double)((Object[])object)[4], 
							(Double)((Object[])object)[5])
			);
		}

		// Chiffre d'affaires total
		double caTotal = 0;

		// Calcul du chiffre d'affaires
		for (ResultatCA resultatCA : listResulatCAFacture) {
			caTotal += resultatCA.ht;
		}

		// Arrondi du CA
		caTotal = LibCalcul.arrondi(caTotal);
		// Nouvel élément Object
		MontantIHM ca = new MontantIHM(new BigDecimal(String.valueOf(caTotal)));
		// Mise à jour de l'objet
		setSelectedObject(ca);
	}

}
