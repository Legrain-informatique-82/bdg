/**
R * DocumentsController.java
 */
package fr.legrain.statistiques.livraison.controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.DocumentsControllerMini;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.DocumentsControllerMini.DocIHM;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class DocumentsControllerLivraisons extends DocumentsControllerMini{
	static Logger logger = Logger.getLogger(DocumentsControllerLivraisons.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaBonlivDAO taBonlivDAO = null;
	private TaFacture taFacture = null;

	
	public DocumentsControllerLivraisons(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {
		
		Date datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		Date datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
		
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		taBonlivDAO = new TaBonlivDAO(masterController.getMasterDAOEM());
		
		// On récupère les docs  transformés
		List<Object> listeLivraisonsTransFac = taRDocumentDAO.findDocTransfosEn("TaBonliv","taBonliv","taFacture", datedeb, datefin);
		List<TaBonliv> listeLivraisonsTot = taBonlivDAO.rechercheDocument(datedeb, datefin);
		
		
		
		// On les comptabilise
		/* -- Livraisons -- */
		int nbtransFac = listeLivraisonsTransFac.size();
		int totLivraisons = listeLivraisonsTot.size();		
		
		String afficFac = genAffic(nbtransFac,totLivraisons);

		// Nouvel élément Object
		DocIHM doc = new DocIHM(afficFac);
		setSelectedObject(doc);
	}
	
	/**
	 * Méthode permettant la génération de la string passée au DOCIHM
	 * @param nombre nombre de documents concernés
	 * @param total nombre total de documents
	 */
	public String genAffic(int lenombre, int letotal ) {
		double nombre = lenombre;
		double total = letotal;
		double pourcent = total!=0?(nombre/total)*100:100;
		
		pourcent = LibCalcul.arrondi(pourcent);
		
		return Double.toString(pourcent)+"% ( "+Integer.toString(lenombre)+" / "+Integer.toString(letotal)+" )";
		
	}

}
