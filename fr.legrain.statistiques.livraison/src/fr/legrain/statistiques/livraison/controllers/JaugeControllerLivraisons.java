/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.livraison.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
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
public class JaugeControllerLivraisons extends JaugeController{
	static Logger logger = Logger.getLogger(JaugeControllerLivraisons.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaBonlivDAO taBonlivDAO = null;



	public JaugeControllerLivraisons(FormPageControllerPrincipal masterContoller,
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


		taBonlivDAO = new TaBonlivDAO(masterController.getMasterDAOEM());
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		// On récupère les livraisons
		List<TaBonliv> listeLivraisonsTot = taBonlivDAO.rechercheDocument(datedeb, datefin);
		List<TaBonliv> listeLivraisonsNonTrans = taBonlivDAO.findLivraisonsNonTransfos(datedeb,datefin);


		// On les comptabilise
		int nbLivraisonsNonTrans = listeLivraisonsNonTrans.size();
		double mtLivraisons = 0;
		for (int i = 0; i<nbLivraisonsNonTrans; i++){
			mtLivraisons += listeLivraisonsNonTrans.get(i).getMtHtCalc().doubleValue();
		}

		double nbLivraisonsTot = listeLivraisonsTot.size();
		// On calcule l'indice
		if (nbLivraisonsTot != 0) {
			indiceJauge = (nbLivraisonsNonTrans/nbLivraisonsTot)*100;
		} else {
			indiceJauge = 0;
		}

		// Titre du graphique
		titreJauge = "Indice des livraisons non facturés";

		// Nouvel élément Object
		JaugeIHM livraisons = new JaugeIHM(new BigDecimal(String.valueOf(nbLivraisonsNonTrans))
		,new BigDecimal(String.valueOf(mtLivraisons))
		,null);
		setSelectedObject(livraisons);

	}

}
