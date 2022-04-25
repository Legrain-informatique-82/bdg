/**
 * DocumentsController.java
 */
package fr.legrain.document.etat.prelevement.controllers;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.document.etat.devis.controllers.TotauxControllerMini;
import fr.legrain.document.etat.devis.ecrans.PaFormPage;
import fr.legrain.lib.data.LibConversion;

/**
 * Classe controller de la section documents
 * @author nicolas
 *
 */
public class TotauxControllerPrincipal extends TotauxControllerMini{
	static Logger logger = Logger.getLogger(TotauxControllerPrincipal.class.getName());	

	private FormPageControllerPrelevement m = null;

	public TotauxControllerPrincipal(FormPageControllerPrelevement masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		this.m = masterContoller;
	}


	public void initialiseModelIHM() {

		DocIHM doc = null;
		if(m!=null && m.getDocEcheanceControllerPrelevement()!=null) {
			doc = new DocIHM(
				LibConversion.bigDecimalToString(m.getDocEcheanceControllerPrelevement().getTotalHT())+" €",
				LibConversion.bigDecimalToString(m.getDocEcheanceControllerPrelevement().getTotalTTC())+" €"
				);
		} else {
			doc = new DocIHM("","");
		}
		setSelectedObject(doc);
	}

}
