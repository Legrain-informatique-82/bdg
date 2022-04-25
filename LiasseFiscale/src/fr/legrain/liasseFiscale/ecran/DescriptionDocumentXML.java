package fr.legrain.liasseFiscale.ecran;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.Compte;
import fr.legrain.liasseFiscale.actions.InfoComplement;
import fr.legrain.liasseFiscale.actions.InfosCompta;
import fr.legrain.liasseFiscale.actions.Repart;
import fr.legrain.liasseFiscale.actions.Repartition;

public class DescriptionDocumentXML extends DescriptionDocument{
	
	static Logger logger = Logger.getLogger(DescriptionDocumentXML.class.getName());
	
	private Mapping mapping;
	static final private String C_MAPPING_FILE = "/mapping/mapping-genecran.xml";
	
	
	private void initMapping() throws IOException, MappingException {
		if ( mapping == null ) {
			URL mappingURL = LiasseFiscalePlugin.getDefault().find(new Path(C_MAPPING_FILE));
			mapping = new Mapping();
			mapping.loadMapping(mappingURL);
		}
	}
	
	/**
	 * 
	 * @param xmlFile - fichier destination
	 */
	public void saveXML(String xmlFile) {
		//binding de this avec Castor XML
		try {
			initMapping();
			//Create a File to marshal to
			FileWriter writer = new FileWriter(xmlFile);
			//Marshal the person object
			Marshaller m = new Marshaller(writer); 
			m.setMapping(mapping); 
			m.marshal(this);
			writer.close();
		} catch (IOException e) {
			logger.error("",e);
		} catch (MappingException e) {
			logger.error("",e);
		} catch (MarshalException e) {
			logger.error("",e);
		} catch (ValidationException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * 
	 * @param xmlFile - fichier source
	 * @return InfosCompta
	 */
	public DescriptionDocumentXML loadXML(String xmlFile) {
		//binding de this avec Castor XML
		try {
			initMapping();
			FileReader reader = new FileReader(xmlFile);
			//Create a new Unmarshaller
			Unmarshaller unmarshaller = new Unmarshaller(DescriptionDocumentXML.class);
			unmarshaller.setMapping(mapping);
			//Unmarshal the object
			DescriptionDocumentXML doc = (DescriptionDocumentXML)unmarshaller.unmarshal(reader);
			reader.close();
			return doc;
		} catch (IOException e) {
			logger.error("",e);
		} catch (MappingException e) {
			logger.error("",e);
		} catch (MarshalException e) {
			logger.error("",e);
		} catch (ValidationException e) {
			logger.error("",e);
		}
		return null;
	}
}
