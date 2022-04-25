/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.proforma.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.JaugeController;
import fr.legrain.statistiques.controllers.JaugeController.JaugeIHM;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class JaugeControllerProforma extends JaugeController{
	static Logger logger = Logger.getLogger(JaugeControllerProforma.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaProformaDAO taProformaDAO = null;



	public JaugeControllerProforma(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {


		//On efface le(s) précédent(s) graphique(s)
		if (vue.getCompositeSectionJauge().getCompoInterieur().getChildren().length > 0) {
			vue.getCompositeSectionJauge().getCompoInterieur().getChildren()[0].dispose();
		}
		datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());



		taProformaDAO = new TaProformaDAO(masterController.getMasterDAOEM());
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		// On récupère les proforma
		List<TaProforma> listeProformaTot = taProformaDAO.rechercheDocument(datedeb, datefin);
		List<TaProforma> listeProformaNonTrans = taProformaDAO.findProformaNonTransfos(datedeb,datefin);


		// On les comptabilise
		int nbProformaNonTrans = listeProformaNonTrans.size();
		double mtProforma = 0;
		for (int i = 0; i<nbProformaNonTrans; i++){
			mtProforma += listeProformaNonTrans.get(i).getMtHtCalc().doubleValue();
		}

		double nbProformaTot = listeProformaTot.size();
		// On calcule l'indice
		if (nbProformaTot != 0) {
			indiceJauge = (nbProformaNonTrans/nbProformaTot)*100;
		} else {
			indiceJauge = 0;
		}

		// Titre du graphique
		titreJauge = "Indice des proformas non transformés";

		// Nouvel élément Object
		JaugeIHM proforma = new JaugeIHM(new BigDecimal(String.valueOf(nbProformaNonTrans))
		,new BigDecimal(String.valueOf(mtProforma))
		,null);
		setSelectedObject(proforma);

	}

}
