/**
R * DocumentsController.java
 */
package fr.legrain.statistiques.controllers.a_supprimer;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.DocumentsControllerMini;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class DocumentsControllerCommandes extends DocumentsControllerMini{
	static Logger logger = Logger.getLogger(DocumentsControllerCommandes.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaFacture taFacture = null;

	
	public DocumentsControllerCommandes(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {
		
		Date datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		Date datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
		
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		taBoncdeDAO = new TaBoncdeDAO(masterController.getMasterDAOEM());
		
		// On récupère les docs  transformés
		List<Object> listeCommandesTransFac = taRDocumentDAO.findDocTransfosEn("TaBoncde","taBoncde","taFacture", datedeb, datefin);
		List<Object> listeCommandesTransBL = taRDocumentDAO.findDocTransfosEn("TaBoncde","taBoncde","taBonliv", datedeb, datefin);
		List<Object> listeCommandesTransPlusieursFois = taRDocumentDAO.findDocTransPlusieursFois("TaBoncde","taBoncde",datedeb, datefin);
		List<TaBoncde> listeCommandesNonTrans = taBoncdeDAO.findCommandesNonTransfos(datedeb,datefin);
		List<TaBoncde> listeCommandesTot = taBoncdeDAO.rechercheDocument(datedeb, datefin);
		
		
		
		// On les comptabilise
		/* -- Commandes -- */
		int nbCommandesTransPlusieursFois = listeCommandesTransPlusieursFois.size();
		int nbNonTrans = listeCommandesNonTrans.size();
		int nbtransFac = listeCommandesTransFac.size();
		int totCommandes = listeCommandesTot.size();		
		
		String afficNonTrans = genAffic(nbNonTrans,totCommandes);
		String afficFac = genAffic(nbtransFac,totCommandes);
		String commandesTransPF;
		// Vérification des commandes transformés en plusieurs documents
		if (nbCommandesTransPlusieursFois == 0){
			commandesTransPF = null;
		} else {
			commandesTransPF = "( "+nbCommandesTransPlusieursFois+" bon(s) de commande transformé(s) plusieurs fois )";
		}
		
		
		/* -- Livraisons -- */
		int nbtransBL = listeCommandesTransBL.size();
		String afficBL = genAffic(nbtransBL,totCommandes);	
		
		
		// Nouvel élément Object
		DocIHM doc = new DocIHM(afficFac,afficBL,afficNonTrans,commandesTransPF);
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
