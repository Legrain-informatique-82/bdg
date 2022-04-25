package fr.legrain.liasseFiscale.actions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.wizards.Document;

public class VerrouDocument {
static Logger logger = Logger.getLogger(VerrouDocument.class.getName());
	
	
	private String C_MAPPING_FILE = "/mapping/mapping-verroudoc.xml";
	private Mapping mapping = null;
	
	private Date date = new Date();
	
	public VerrouDocument() {}
	
	/**
	 * Création et initialisation des informations de mapping si celles ci n'existe pas
	 * @throws IOException
	 * @throws MappingException
	 */
	protected void initMapping() throws IOException, MappingException {
		if ( mapping == null ) {
			URL mappingURL = FileLocator.find(LiasseFiscalePlugin.getDefault().getBundle(), new Path(C_MAPPING_FILE), null);
			mapping = new Mapping();
			mapping.loadMapping(mappingURL);
		}
	}
	
	/**
	 * 
	 * @param xmlFile - fichier destination
	 */
	public void sortieXML(String xmlFile) {
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
	 * @return Document
	 */
	public Document lectureXML(String xmlFile) {
		//binding de this avec Castor XML
		try {
			initMapping();
			FileReader reader = new FileReader(xmlFile);
			//Create a new Unmarshaller
			Unmarshaller unmarshaller = new Unmarshaller(Document.class);
			unmarshaller.setMapping(mapping);
			//Unmarshal the object
			Document doc = (Document)unmarshaller.unmarshal(reader);
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
