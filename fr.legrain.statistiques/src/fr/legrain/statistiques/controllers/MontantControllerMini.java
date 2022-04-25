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

public abstract class MontantControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;

	private List<ModelObject> modele = null;
	private FormPageControllerPrincipal masterController;
	protected PaFormPage vue = null;

	/* Constructeur */
	public MontantControllerMini(FormPageControllerPrincipal masterController, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterController;
	}

	public abstract void initialiseModelIHM();


	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(MontantIHM.class);
		
		mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
		mapComposantUpdateValueStrategy.put(vue.getCompositeSectionMontant().getText(),new LgrUpdateValueStrategyAffichageMilliersAvecEuros());
		
		bindForm(mapComposantUpdateValueStrategy,mapComposantChamps, MontantIHM.class, getSelectedObject(), vue.getSctnMontant().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
		mapComposantChamps.put(vue.getCompositeSectionMontant().getText(), "montant");

	}
	
	/**
	 * Classe permettant l'affichage du chiffre d'affaires
	 */
	public class MontantIHM extends ModelObject {
		BigDecimal montant = null;

		public MontantIHM() {}

		public MontantIHM(BigDecimal montant) {
			super();
			this.montant = montant;
		}

		public BigDecimal getMontant() {
			return montant;
		}
		public void setChiffreAffaire(BigDecimal montant) {
			firePropertyChange("montant", this.montant, this.montant = montant);
		}

	}

	public FormPageControllerPrincipal getMasterController() {
		return masterController;
	}

}
