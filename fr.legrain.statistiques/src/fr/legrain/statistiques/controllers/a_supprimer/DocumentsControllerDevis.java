/**
R * DocumentsController.java
 */
package fr.legrain.statistiques.controllers.a_supprimer;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
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
public class DocumentsControllerDevis extends DocumentsControllerMini{
	static Logger logger = Logger.getLogger(DocumentsControllerDevis.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaDevisDAO taDevisDAO = null;
	private TaFacture taFacture = null;

	
	public DocumentsControllerDevis(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {
		
		Date datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		Date datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
			
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		taDevisDAO = new TaDevisDAO(masterController.getMasterDAOEM());
		
		// On récupère les docs  transformés
		List<Object> listeDevisTransFac = taRDocumentDAO.findDocTransfosEn("TaDevis","taDevis","taFacture", datedeb, datefin);
		List<Object> listeDevisTransBL = taRDocumentDAO.findDocTransfosEn("TaDevis","taDevis","taBonliv", datedeb, datefin);
		List<Object> listeDevisTransBC = taRDocumentDAO.findDocTransfosEn("TaDevis","taDevis","taBoncde", datedeb, datefin);
		List<Object> listeDevisTransProf = taRDocumentDAO.findDocTransfosEn("TaDevis","taDevis","taProforma", datedeb, datefin);
		List<Object> listeDevisTransPlusieursFois = taRDocumentDAO.findDocTransPlusieursFois("TaDevis","taDevis",datedeb, datefin);
		List<TaDevis> listeDevisNonTrans = taDevisDAO.findDevisNonTransfos(datedeb,datefin);
		List<TaDevis> listeDevisTot = taDevisDAO.rechercheDocument(datedeb, datefin);
		
		
		
		// On les comptabilise
		/* -- Devis -- */
		int nbDevisTransPlusieursFois = listeDevisTransPlusieursFois.size();
		int nbNonTrans = listeDevisNonTrans.size();
		int nbtransFac = listeDevisTransFac.size();
		int totDevis = listeDevisTot.size();		
		
		String afficNonTrans = genAffic(nbNonTrans,totDevis);
		String afficFac = genAffic(nbtransFac,totDevis);
		String devisTransPF;
		// Vérification des devis transformés en plusieurs documents
		if (nbDevisTransPlusieursFois == 0){
			devisTransPF = null;
		} else {
			devisTransPF = "( "+nbDevisTransPlusieursFois+" devis transformé(s) plusieurs fois )";
		}
		
		
		/* -- Commandes -- */
		int nbtransCommandes = listeDevisTransBC.size();
		String afficCommandes = genAffic(nbtransCommandes,totDevis);	
		
		/* -- Livraisons -- */
		int nbtransBL = listeDevisTransBL.size();
		String afficBL = genAffic(nbtransBL,totDevis);	
		
		/* -- Proformas -- */
		int nbtransProf = listeDevisTransProf.size();
		String afficProf = genAffic(nbtransProf,totDevis);
		
		// Nouvel élément Object
		DocIHM doc = new DocIHM(afficFac,afficCommandes,afficBL,afficProf,afficNonTrans,devisTransPF);
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
