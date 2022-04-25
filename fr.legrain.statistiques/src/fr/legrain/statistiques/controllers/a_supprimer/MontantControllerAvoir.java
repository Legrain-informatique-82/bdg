package fr.legrain.statistiques.controllers.a_supprimer;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;



public class MontantControllerAvoir extends MontantControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerAvoir.class.getName());	

	private Class objetIHM = null;
	private TaAvoirDAO taAvoirDAO = null;
	private TaAvoir taAvoir = null;
	private List<ModelObject> modele = null;
	
	
	
	public MontantControllerAvoir(FormPageControllerPrincipal masterController, PaFormPage vue,
			EntityManager em) {
		super(masterController, vue, em);
	}



	public void initialiseModelIHM() {

		taAvoirDAO = new TaAvoirDAO(super.getMasterController().getMasterDAOEM());
		// On récupère la somme des montant ht des devis sur la période
		List<Object> sommeAvoirs = taAvoirDAO.findCASurPeriode(Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));

		// On traduit la donnée en double
		BigDecimal ht = new BigDecimal(0);;
		if (sommeAvoirs.get(0) != null){
			ht = (BigDecimal)sommeAvoirs.get(0);
		} 



		// Arrondi du CA
		ht = LibCalcul.arrondi(ht);
		// Nouvel élément Object
		MontantIHM lemontantHT = new MontantIHM(new BigDecimal(String.valueOf(ht)));
		// Mise à jour de l'objet
		setSelectedObject(lemontantHT);
	}

}