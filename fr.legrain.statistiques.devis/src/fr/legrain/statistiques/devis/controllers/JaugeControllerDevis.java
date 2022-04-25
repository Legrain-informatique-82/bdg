/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.devis.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.JaugeController;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class JaugeControllerDevis extends JaugeController{
	static Logger logger = Logger.getLogger(JaugeControllerDevis.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaDevisDAO taDevisDAO = null;



	public JaugeControllerDevis(FormPageControllerPrincipal masterContoller,
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



		taDevisDAO = new TaDevisDAO(masterController.getMasterDAOEM());
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		// On récupère les devis
		List<TaDevis> listeDevisTot = taDevisDAO.rechercheDocument(datedeb, datefin);
		List<TaDevis> listeDevisNonTrans = taDevisDAO.findDevisNonTransfos(datedeb,datefin);


		// On les comptabilise
		int nbDevisNonTrans = listeDevisNonTrans.size();
		double mtDevis = 0;
		for (int i = 0; i<nbDevisNonTrans; i++){
			mtDevis += listeDevisNonTrans.get(i).getMtHtCalc().doubleValue();
		}

		double nbDevisTot = listeDevisTot.size();
		// On calcule l'indice
		if (nbDevisTot != 0) {
			indiceJauge = (nbDevisNonTrans/nbDevisTot)*100;
		} else {
			indiceJauge = 0;
		}

		// Titre du graphique
		titreJauge = "Indice des devis non transformés";

		// Nouvel élément Object
		JaugeIHM devis = new JaugeIHM(new BigDecimal(String.valueOf(nbDevisNonTrans))
		,new BigDecimal(String.valueOf(mtDevis))
		,null);
		setSelectedObject(devis);

	}

}
