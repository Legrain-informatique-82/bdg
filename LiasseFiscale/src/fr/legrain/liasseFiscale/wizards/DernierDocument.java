package fr.legrain.liasseFiscale.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Information sur le dernier document traite.
 * Utile pour faire le lien avec d'autre partie du programme
 */
public class DernierDocument {
	
	static Logger logger = Logger.getLogger(DernierDocument.class.getName());
	private String fichierDernierDoc = "infos.properties";
	private String cleDernierDoc = "dernier";
	
	public void enregistreDernierDoc(WizardDocumentFiscalModel model) {
		Properties properties = new Properties();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(model.cheminDossiers+"/"+fichierDernierDoc);
			properties.put(cleDernierDoc, model.getNomDossier()+"/"+model.getAnneeFiscale());
//			properties.put(cleDernierDoc, model.getNomDossier()+"/"+model.getAnneeDocumentPDF());
			properties.store(fos, "dernier document traite");
		} catch (FileNotFoundException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	public String recupereDernierDoc(WizardDocumentFiscalModel model) {
		Properties properties = new Properties();
		FileInputStream fis;
		if(new File(model.cheminDossiers+"/"+fichierDernierDoc).exists()) {
			try {
				fis = new FileInputStream(model.cheminDossiers+"/"+fichierDernierDoc);
				properties.load(fis);
				return (String)properties.get(cleDernierDoc);
			} catch (FileNotFoundException e) {
				logger.error("",e);
			} catch (IOException e) {
				logger.error("",e);
			}
		}
		return null;
	}

}
