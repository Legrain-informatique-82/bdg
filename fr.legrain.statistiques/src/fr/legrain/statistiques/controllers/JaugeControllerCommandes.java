/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class JaugeControllerCommandes extends JaugeController{
	static Logger logger = Logger.getLogger(JaugeControllerCommandes.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaBoncdeDAO taBoncdeDAO = null;



	public JaugeControllerCommandes(FormPageControllerPrincipal masterContoller,
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


		taBoncdeDAO = new TaBoncdeDAO(masterController.getMasterDAOEM());
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		// On récupère les commandes
		List<TaBoncde> listeCommandesTot = taBoncdeDAO.rechercheDocument(datedeb, datefin);
		List<TaBoncde> listeCommandesNonTrans = taBoncdeDAO.findCommandesNonTransfos(datedeb,datefin);


		// On les comptabilise
		int nbCommandesNonTrans = listeCommandesNonTrans.size();
		double mtCommandes = 0;
		for (int i = 0; i<nbCommandesNonTrans; i++){
			mtCommandes += listeCommandesNonTrans.get(i).getMtHtCalc().doubleValue();
		}

		double nbCommandesTot = listeCommandesTot.size();
		// On calcule l'indice
		if (nbCommandesTot != 0) {
			indiceJauge = (nbCommandesNonTrans/nbCommandesTot)*100;
		} else {
			indiceJauge = 0;
		}

		// Titre du graphique
		titreJauge = "Indice des commandes non transformés";

		// Nouvel élément Object
		JaugeIHM commandes = new JaugeIHM(new BigDecimal(String.valueOf(nbCommandesNonTrans))
		,new BigDecimal(String.valueOf(mtCommandes))
		,null);
		setSelectedObject(commandes);

	}

}
