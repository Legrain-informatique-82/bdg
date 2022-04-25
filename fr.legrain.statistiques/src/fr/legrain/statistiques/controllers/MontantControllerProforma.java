package fr.legrain.statistiques.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.ecrans.PaFormPage;



public class MontantControllerProforma extends MontantControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerProforma.class.getName());	

	private Class objetIHM = null;
	private TaProformaDAO taProformaDAO = null;
	private TaProforma taProforma = null;
	private List<ModelObject> modele = null;
	
	
	
	public MontantControllerProforma(FormPageControllerPrincipal masterController, PaFormPage vue,
			EntityManager em) {
		super(masterController, vue, em);
	}



	public void initialiseModelIHM() {

		taProformaDAO = new TaProformaDAO(super.getMasterController().getMasterDAOEM());
		// On récupère la somme des montant ht des proforma sur la période
		List<Object> sommeProforma = taProformaDAO.findCAProformaSurPeriode(Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));

		// On traduit la donnée en double
		BigDecimal ht = new BigDecimal(0);;
		if (sommeProforma.get(0) != null){
			ht = (BigDecimal)sommeProforma.get(0);
		} 
		

		


		// Arrondi du CA
		ht = LibCalcul.arrondi(ht);
		// Nouvel élément Object
		MontantIHM lemontantHT = new MontantIHM(new BigDecimal(String.valueOf(ht)));
		// Mise à jour de l'objet
		setSelectedObject(lemontantHT);
	}

}