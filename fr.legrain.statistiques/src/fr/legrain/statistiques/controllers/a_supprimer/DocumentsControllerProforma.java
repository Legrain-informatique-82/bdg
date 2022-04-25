/**
R * DocumentsController.java
 */
package fr.legrain.statistiques.controllers.a_supprimer;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
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
public class DocumentsControllerProforma extends DocumentsControllerMini{
	static Logger logger = Logger.getLogger(DocumentsControllerProforma.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaProformaDAO taProformaDAO = null;
	private TaFacture taFacture = null;

	
	public DocumentsControllerProforma(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {
		
		Date datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
		Date datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
			
		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		taProformaDAO = new TaProformaDAO(masterController.getMasterDAOEM());
		
		// On récupère les docs  transformés
		List<Object> listeProformaTransFac = taRDocumentDAO.findDocTransfosEn("TaProforma","taProforma","taFacture", datedeb, datefin);
		List<Object> listeProformaTransBL = taRDocumentDAO.findDocTransfosEn("TaProforma","taProforma","taBonliv", datedeb, datefin);
		List<Object> listeProformaTransBC = taRDocumentDAO.findDocTransfosEn("TaProforma","taProforma","taBoncde", datedeb, datefin);
		List<Object> listeProformaTransProf = taRDocumentDAO.findDocTransfosEn("TaProforma","taProforma","taProforma", datedeb, datefin);
		List<Object> listeProformaTransPlusieursFois = taRDocumentDAO.findDocTransPlusieursFois("TaProforma","taProforma",datedeb, datefin);
		List<TaProforma> listeProformaNonTrans = taProformaDAO.findProformaNonTransfos(datedeb,datefin);
		List<TaProforma> listeProformaTot = taProformaDAO.rechercheDocument(datedeb, datefin);
		
		
		
		// On les comptabilise
		/* -- Proforma -- */
		int nbProformaTransPlusieursFois = listeProformaTransPlusieursFois.size();
		int nbNonTrans = listeProformaNonTrans.size();
		int nbtransFac = listeProformaTransFac.size();
		int totProforma = listeProformaTot.size();		
		
		String afficNonTrans = genAffic(nbNonTrans,totProforma);
		String afficFac = genAffic(nbtransFac,totProforma);
		String proformaTransPF;
		// Vérification des proforma transformés en plusieurs documents
		if (nbProformaTransPlusieursFois == 0){
			proformaTransPF = null;
		} else {
			proformaTransPF = "( "+nbProformaTransPlusieursFois+" proforma(s) transformé(s) plusieurs fois )";
		}
		
		
		/* -- Commandes -- */
		int nbtransCommandes = listeProformaTransBC.size();
		String afficCommandes = genAffic(nbtransCommandes,totProforma);	
		
		/* -- Livraisons -- */
		int nbtransBL = listeProformaTransBL.size();
		String afficBL = genAffic(nbtransBL,totProforma);	
		

		// Nouvel élément Object
		DocIHM doc = new DocIHM(afficFac,afficCommandes,afficBL,afficNonTrans,proformaTransPF);
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
