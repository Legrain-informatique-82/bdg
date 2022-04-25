package fr.legrain.statistiques.controllers.a_supprimer;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;



public class MontantControllerAcompte extends MontantControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerAcompte.class.getName());	

	private Class objetIHM = null;
	private TaAcompteDAO taAcompteDAO = null;
	private TaAcompte taAcompte = null;
	private List<ModelObject> modele = null;
	
	
	
	public MontantControllerAcompte(FormPageControllerPrincipal masterController, PaFormPage vue,
			EntityManager em) {
		super(masterController, vue, em);
	}



	public void initialiseModelIHM() {

		taAcompteDAO = new TaAcompteDAO(super.getMasterController().getMasterDAOEM());
		// On récupère la somme des montant ht des devis sur la période
		List<Object> sommeAcomptes = taAcompteDAO.findCASurPeriode(Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));

		// On traduit la donnée en double
		BigDecimal ht = new BigDecimal(0);;
		if (sommeAcomptes.get(0) != null){
			ht = (BigDecimal)sommeAcomptes.get(0);
		} 



		// Arrondi du CA
		ht = LibCalcul.arrondi(ht);
		// Nouvel élément Object
		MontantIHM lemontantHT = new MontantIHM(new BigDecimal(String.valueOf(ht)));
		// Mise à jour de l'objet
		setSelectedObject(lemontantHT);
	}

}