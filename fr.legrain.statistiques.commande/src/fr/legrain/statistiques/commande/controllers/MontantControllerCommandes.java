package fr.legrain.statistiques.commande.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerMini;
import fr.legrain.statistiques.controllers.MontantControllerMini.MontantIHM;
import fr.legrain.statistiques.ecrans.PaFormPage;



public class MontantControllerCommandes extends MontantControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerCommandes.class.getName());	

	private Class objetIHM = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBoncde taBoncde = null;
	private List<ModelObject> modele = null;



	public MontantControllerCommandes(FormPageControllerPrincipal masterController, PaFormPage vue,
			EntityManager em) {
		super(masterController, vue, em);
	}



	public void initialiseModelIHM() {

		taBoncdeDAO = new TaBoncdeDAO(super.getMasterController().getMasterDAOEM());
		// On récupère la somme des montant ht des devis sur la période
		List<Object> sommeCommandes = taBoncdeDAO.findCABCSurPeriode(Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));

		// On traduit la donnée en double
		BigDecimal ht = new BigDecimal(0);;
		if (sommeCommandes.get(0) != null){
			ht = (BigDecimal)sommeCommandes.get(0);
		} 



		// Arrondi du CA
		ht = LibCalcul.arrondi(ht);

		// Nouvel élément Object
		MontantIHM lemontantHT = new MontantIHM(new BigDecimal(String.valueOf(ht)));
		// Mise à jour de l'objet
		setSelectedObject(lemontantHT);
	}

}