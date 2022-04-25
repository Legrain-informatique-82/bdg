/**
 * DocumentsController.java
 */
package fr.legrain.document.RechercheDocument.controllers;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.document.RechercheDocument.ecrans.PaFormPage;
import fr.legrain.lib.data.LibConversion;

/**
 * Classe controller de la section documents
 * @author nicolas
 *
 */
public class TotauxControllerPrincipal extends TotauxControllerMini{
	static Logger logger = Logger.getLogger(TotauxControllerPrincipal.class.getName());	

	private FormPageControllerPrincipal m = null;

	public TotauxControllerPrincipal(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		this.m = masterContoller;
	}


	public void initialiseModelIHM() {

		DocIHM doc = null;
		if(m!=null && m.getDocEcheanceController()!=null) {
			doc = new DocIHM(
				LibConversion.bigDecimalToString(m.getDocEcheanceController().getTotalHT())+" €",
				LibConversion.bigDecimalToString(m.getDocEcheanceController().getTotalTTC())+" €"
				);
		} else {
			doc = new DocIHM("","");
		}
		setSelectedObject(doc);
	}

}
