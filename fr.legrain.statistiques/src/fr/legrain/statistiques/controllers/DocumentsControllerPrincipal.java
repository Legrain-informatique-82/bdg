/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class DocumentsControllerPrincipal extends DocumentsControllerMini{
	static Logger logger = Logger.getLogger(DocumentsControllerPrincipal.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaFacture taFacture = null;
	private TaDevisDAO taDevisDAO = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBonlivDAO taBonlivDAO = null;

	
	public DocumentsControllerPrincipal(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {
		
		Date datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		Date datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
			
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		taDevisDAO = new TaDevisDAO(masterController.getMasterDAOEM());
		taBoncdeDAO = new TaBoncdeDAO(masterController.getMasterDAOEM());
		taBonlivDAO = new TaBonlivDAO(masterController.getMasterDAOEM());
		
		// On récupère les docs transformés
		List<TaDevis> listeDevis = taDevisDAO.findDevisNonTransfos(datedeb,datefin);
		List<TaDevis> listeDevisTot = taDevisDAO.rechercheDocument(datedeb, datefin);
		
		List<TaBoncde> listeBoncde = taBoncdeDAO.findCommandesNonTransfos(datedeb,datefin);
		List<TaBoncde> listeBoncdeTot = taBoncdeDAO.rechercheDocument(datedeb, datefin);
		
		List<TaBonliv> listeBonliv = taRDocumentDAO.findBLNonTransfos(datedeb,datefin);
		List<TaBonliv> listeBonlivTot = taBonlivDAO.rechercheDocument(datedeb, datefin);
		
		
		// On les comptabilise
		/* -- Devis -- */
		int nbDevis = listeDevis.size();
		int totDevis = listeDevisTot.size();		
		String afficDevis = genAffic(totDevis-nbDevis,totDevis);
		
		/* -- Commandes -- */
		int nbCommandes = listeBoncde.size();
		int totCommandes = listeBoncdeTot.size();
		String afficCommandes = genAffic(totCommandes-nbCommandes,totCommandes);		
		/* -- Livraisons -- */
		int nbBL = listeBonliv.size();
		int totBL = listeBonlivTot.size();
		String afficBL = genAffic(totBL-nbBL,totBL);		
		// Nouvel élément Object

		DocIHM doc = new DocIHM(afficDevis,afficCommandes,afficBL);
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
