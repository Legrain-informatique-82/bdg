package fr.legrain.liasseFiscale.wizards;

import org.apache.log4j.Logger;

import fr.legrain.liasseFiscale.actions.Repart;

public class WizardDocumentModel extends WizardDocumentFiscalModel {
	
	static Logger logger = Logger.getLogger(WizardDocumentModel.class.getName());
	
	/**
	 * Répartition 
	 * @return
	 */
	public Repart repartitionDocument() {
		return repartition;
	}

	@Override
	public WizardDocumentModel lectureXML(String xmlFile) {
		logger.error("Non implémenter");
		return null;
	}
	
}
