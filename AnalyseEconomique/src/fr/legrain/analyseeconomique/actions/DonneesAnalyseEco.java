package fr.legrain.analyseeconomique.actions;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.liasseFiscale.actions.InfoComplement;

public class DonneesAnalyseEco {
	/*
	 * <bilan-balance></bilan-balance>
	 * <pages-liasse></pages-liasse>
	 * <analytique></analytique>
	 * <stocks></stocks>
	 */
	static Logger logger = Logger.getLogger(DonneesAnalyseEco.class.getName());
	
	private String C_MAPPING_FILE = "/mapping/mapping-analyse-eco.xml";
	private Mapping mapping = null;
	
	private List<InfosCompta> listeInfosCompta = new ArrayList<InfosCompta>();
	private List<InfosLiasse> listeInfosLiasse = new ArrayList<InfosLiasse>();
	private List<InfosAnalytique> listeInfosAnalytique = new ArrayList<InfosAnalytique>();
	private List<InfosStocks> listeInfosStocks = new ArrayList<InfosStocks>();
	private List<Acquisition> listeAcquisition = new ArrayList<Acquisition>();
	private List<Divers> listeDivers = new ArrayList<Divers>();
	private List<InfosGrdLivreQte> listeQte = new ArrayList<InfosGrdLivreQte>();
	private List<InfoComplement> listeInfosDossier = new ArrayList<InfoComplement>();
	private String agence = null;

	/**
	 * Création et initialisation des informations de mapping si celles ci n'existe pas
	 * @throws IOException
	 * @throws MappingException
	 */
	private void initMapping() throws IOException, MappingException {
		try {
		if ( mapping == null ) {
			URL mappingURL = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),new Path(C_MAPPING_FILE),null);
			mapping = new Mapping();
			mapping.loadMapping(mappingURL);
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param xmlFile - fichier destination
	 */
	public void sortieXML(String xmlFile) {
		//String encoding = "ISO-8859-1";
		String encoding = "UTF-8";
		//binding de this avec Castor XML
		try {
			initMapping();
			//Create a File to marshal to
			//FileWriter writer = new FileWriter(xmlFile);
			FileOutputStream fos = new FileOutputStream(xmlFile);
	        OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
			//Marshal the person object
			Marshaller m = new Marshaller(osw); 
			m.setEncoding(encoding);
			m.setMapping(mapping); 
			m.marshal(this);
			osw.close();
			//writer.close();
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
	 * @param xmlFile - fichier source
	 * @return DonneesAnalyseEco
	 */
	public DonneesAnalyseEco lectureXML(String xmlFile) {
		//binding de this avec Castor XML
		try {
			initMapping();
			FileReader reader = new FileReader(xmlFile);
			//Create a new Unmarshaller
			Unmarshaller unmarshaller = new Unmarshaller(DonneesAnalyseEco.class);
			unmarshaller.setMapping(mapping);
			//Unmarshal the object
			DonneesAnalyseEco donneesAnalyseEco = (DonneesAnalyseEco)unmarshaller.unmarshal(reader);
			reader.close();
			return donneesAnalyseEco;
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

	public List<InfosAnalytique> getListeInfosAnalytique() {
		return listeInfosAnalytique;
	}

	public void setListeInfosAnalytique(List<InfosAnalytique> listeInfosAnalytique) {
		this.listeInfosAnalytique = listeInfosAnalytique;
	}

	public List<InfosCompta> getListeInfosCompta() {
		return listeInfosCompta;
	}

	public void setListeInfosCompta(List<InfosCompta> listeInfosCompta) {
		this.listeInfosCompta = listeInfosCompta;
	}

	public List<InfosLiasse> getListeInfosLiasse() {
		return listeInfosLiasse;
	}

	public void setListeInfosLiasse(List<InfosLiasse> listeInfosLiasse) {
		this.listeInfosLiasse = listeInfosLiasse;
	}

	public List<InfosStocks> getListeInfosStocks() {
		return listeInfosStocks;
	}

	public void setListeInfosStocks(List<InfosStocks> listeInfosStocks) {
		this.listeInfosStocks = listeInfosStocks;
	}

	public List<Acquisition> getListeAcquisition() {
		return listeAcquisition;
	}

	public void setListeAcquisition(List<Acquisition> listeAcquisition) {
		this.listeAcquisition = listeAcquisition;
	}

	public List<Divers> getListeDivers() {
		return listeDivers;
	}

	public void setListeDivers(List<Divers> listeDivers) {
		this.listeDivers = listeDivers;
	}

	public List<InfosGrdLivreQte> getListeQte() {
		return listeQte;
	}

	public void setListeQte(List<InfosGrdLivreQte> listeQte) {
		this.listeQte = listeQte;
	}

	public List<InfoComplement> getListeInfosDossier() {
		return listeInfosDossier;
	}

	public void setListeInfosDossier(List<InfoComplement> listeInfosDossier) {
		this.listeInfosDossier = listeInfosDossier;
	}

	public String getAgence() {
		return agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}
}
