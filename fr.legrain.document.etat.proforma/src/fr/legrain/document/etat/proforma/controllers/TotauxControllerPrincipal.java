/**
 * DocumentsController.java
 */
package fr.legrain.document.etat.proforma.controllers;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.document.etat.devis.ecrans.PaFormPage;
import fr.legrain.lib.data.LibConversion;

/**
 * Classe controller de la section documents
 * @author nicolas
 *
 */
public class TotauxControllerPrincipal extends TotauxControllerMini{
	static Logger logger = Logger.getLogger(TotauxControllerPrincipal.class.getName());	

	private FormPageControllerProforma m = null;

	public TotauxControllerPrincipal(FormPageControllerProforma masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		this.m = masterContoller;
	}


	public void initialiseModelIHM() {

		DocIHM doc = null;
		if(m!=null && m.getDocEcheanceControllerProforma()!=null) {
			doc = new DocIHM(
				LibConversion.bigDecimalToString(m.getDocEcheanceControllerProforma().getTotalHT())+" €",
				LibConversion.bigDecimalToString(m.getDocEcheanceControllerProforma().getTotalTTC())+" €"
				);
		} else {
			doc = new DocIHM("","");
		}
		setSelectedObject(doc);
	}

}
