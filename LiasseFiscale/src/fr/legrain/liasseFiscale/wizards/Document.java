package fr.legrain.liasseFiscale.wizards;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

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

public class Document {
	static Logger logger = Logger.getLogger(Document.class.getName());
	
	
	private String C_MAPPING_FILE = "/mapping/mapping-infodoc.xml";
	private Mapping mapping = null;
	
	private EnumTypeDoc typeDocument = null;
	
	public Document() {}
	
	/**
	 * Création et initialisation des informations de mapping si celles ci n'existe pas
	 * @throws IOException
	 * @throws MappingException
	 */
	protected void initMapping() throws IOException, MappingException {
		if ( mapping == null ) {
			//URL mappingURL = LiasseFiscalePlugin.getDefault().find(new Path(C_MAPPING_FILE));
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

	public EnumTypeDoc getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(EnumTypeDoc typeDocument) {
		this.typeDocument = typeDocument;
	}

}
