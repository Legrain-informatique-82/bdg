package fr.legrain.liasseFiscale.wizards;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import com.lowagie.tools.Executable;

import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.actions.InfosCompta;
import fr.legrain.liasseFiscale.actions.InfosComptaTxtEpicea;
import fr.legrain.liasseFiscale.actions.Repart;
import fr.legrain.liasseFiscale.actions.VerrouDocument;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.preferences.PreferenceConstants;

public abstract class WizardDocumentFiscalModel {
	
	static Logger logger = Logger.getLogger(WizardDocumentFiscalModel.class.getName());
	protected EventListenerList listenerList = new EventListenerList();
	private String C_MAPPING_FILE = "/mapping/mapping-modele-liasse.xml";
	protected Mapping mapping = null;
	
	/** chemin absolue du repertoire des dossiers des clients*/
	protected String cheminDossiers = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
	/** chemin absolue du repertoire du document courant */
	protected String cheminDocument = null;
	/** nom du dossier du client */
	protected String nomDossier = null;
	/** vrai ssi le document on creer un nouveau document, faux si reprise d'un document existant */
	protected boolean nouveauDocument = true;
	/** chemin absolue du fichier de compta (l'original, pas la copie locale) */
	protected String cheminFichierCompta = null;
	protected EnumRegimeFiscal regime = null;
	protected EnumTypeDoc typeDocument = null;
	protected int anneeDocumentPDF; //année du PDF
	protected String anneeFiscale; // identifiant annee fiscale
	protected boolean automatique = false; //vrai ssi l'enchainement des ecrans est automatique
	
	private boolean finish = false; //vrai ssi la methode "performFinish" du wizard a ete appele
	
	protected Repart repartition = new Repart();
	protected InfosCompta infosCompta = null;
	
	/**
	 * Création et initialisation des informations de mapping si celles ci n'existe pas
	 * @throws IOException
	 * @throws MappingException
	 */
	protected void initMapping() throws IOException, MappingException {
		if ( mapping == null ) {
			URL mappingURL = FileLocator.find(LiasseFiscalePlugin.getDefault().getBundle(),new Path(C_MAPPING_FILE),null);
			mapping = new Mapping();
			mapping.loadMapping(mappingURL);
		}
	}
	
	/**
	 * Copie la valeurs de propriétés de <code>m</code> dans celles de l'instance courante
	 * @param m
	 */
	public void copyProperties(WizardDocumentFiscalModel m){
		//setCheminDocument(m.getCheminDocument());
		setCheminFichierCompta(m.getCheminFichierCompta());
		setNomDossier(m.getNomDossier());
		setNouveauDocument(m.getNouveauDocument());
		setAnneeDocumentPDF(m.getAnneeDocumentPDF());
		setAnneeFiscale(m.getAnneeFiscale());
		setAutomatique(m.isAutomatique());
		
		setRegime(m.getRegime());
		setTypeDocument(m.getTypeDocument());
		
		setInfosCompta(m.getInfosCompta());
		setRepartition(m.getRepartition());
	}
	
	/**
	 * @param cheminRepertoireBase emplacement où chercher les dossiers clients.
	 * @return - liste des dossiers client
	 */
	public List<String> listeDossier(String cheminRepertoireBase) {
		//liste dossier
		ArrayList<String> res = new ArrayList<String>();
		
		File rep = new File(cheminRepertoireBase);
		File[] listeDossier = rep.listFiles();
		for (int i = 0; i < listeDossier.length; i++) {
			if(listeDossier[i].isDirectory()) {
				if(!listeDossier[i].getName().equals(ConstLiasse.C_REPERTOIRE_BD)) {
					res.add(listeDossier[i].getName());
				}
			}
		}
		return res;
	}
	
	/**
	 * @return - liste des dossiers client (recherche à l'emplacement par defaut)
	 */
	public List<String> listeDossier() {
		return listeDossier(cheminDossiers);
	}
	
	/**
	 * @param codeDossier
	 * @return - liste des typeDocument de document deja present dans ce dossier (dossier à l'emplacement par defaut)
	 */
	public List<String> listeDocument(String codeDossier) {
		//liste liasse dans dossier
		ArrayList<String> res = new ArrayList<String>();
		
		File rep = new File(cheminDossiers+"/"+codeDossier);
		File[] listeDossier = rep.listFiles();
		for (int i = 0; i < listeDossier.length; i++) {
			if(listeDossier[i].isDirectory()) {
				res.add(listeDossier[i].getName());
			}
		}
		return res;
	}
	
	static public boolean estVerrouille(String cheminDocument) {
		//String cheminDossiers = null;
		//cheminDossiers = cheminRepSelection(item);
		
		//Recherche du fichier identifiant un répertoire "document fiscal" valide
		File f = new File(cheminDocument);
		File[] listeFichier = f.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(pathname.getName().equals(ConstLiasse.C_FICHIER_VERROU_DOC))
					return true;
				else
					return false;
			}
		});
		
		if(listeFichier!=null) {
			return (listeFichier.length>0);
		} else
			return false;
	}
	
	public boolean estVerrouille() {
		return estVerrouille(cheminDocument);
	}
	
	/**
	 * Verrouille le document dont le chemin est passé en paramètre
	 * @param cheminDocument
	 */
	static public void verrouilleDocument(String cheminDocument) {
		VerrouDocument verrou = null;
		verrou = new VerrouDocument();
		
		verrou.sortieXML(cheminDocument+"/"+ConstLiasse.C_FICHIER_VERROU_DOC);
		
	}
	
	/**
	 * @param nomDossier
	 * @return - vrai ssi le dossier <code>nomDossier</code> existe deja
	 */
	public boolean dossierExiste(String nomDossier) {
		return listeDossier().contains(nomDossier);
	}
	
	/**
	 * copie du fichier de la compta
	 * @param nomFichierCompta - nom du fichier de compta avec son chemin
	 */
	public void copieFichierCompta(String nomFichierCompta) {
		if(logger.isDebugEnabled())
			logger.debug("debut copie fichier compta");
		
		try {
			FileInputStream fichier = new FileInputStream(getCheminFichierCompta());
			FileOutputStream copie = new FileOutputStream(getCheminDocument()+"/"+nomFichierCompta);
			IOUtils.copy(fichier,copie);
			fichier.close();
			copie.close();
		} catch (FileNotFoundException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
		
		if(logger.isDebugEnabled())
			logger.debug("fin copie fichier compta");
	}
	
	/**
	 * Lecture du fichier de comptabilite
	 * @param nomFichierCompta
	 */
	public void lectureInfosCompta(String nomFichierCompta){
		if(logger.isDebugEnabled())
			logger.debug("debut lecture compta");
		String fichier = null;
		if(getCheminDocument()!=null) {
			fichier = getCheminDocument()+"/"+nomFichierCompta;
		} else {
			fichier = nomFichierCompta;
		}
		infosCompta = new InfosComptaTxtEpicea(fichier);
		infosCompta.readTxt();
		
		if(logger.isDebugEnabled())
			logger.debug("fin lecture compta");
	}
	
	/**
	 * Enregistrement des informations comptables et des informations supplementaires saisies
	 * @param nomFichierXML
	 */
	public void sauveInfosComptaXML(String nomFichierXML) {
		if(logger.isDebugEnabled())
			logger.debug("debut compta => XML");
		
		infosCompta.sortieXML(getCheminDocument()+"/"+nomFichierXML);
		
		if(logger.isDebugEnabled())
			logger.debug("fin compta => XML");
	}
	
	/**
	 * Répartition 
	 * @return
	 */
	public abstract Repart repartitionDocument();
	
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
	 * Lecture à partir d'un fichier XML
	 * @param xmlFile
	 * @return
	 */
	public abstract WizardDocumentFiscalModel lectureXML(String xmlFile);
	
	/**
	 * Enregistrement de la répartition au format XML
	 * @param nomFichierXML
	 */
	public void sauveRepartXML(String nomFichierXML) {
		if(logger.isDebugEnabled())
			logger.debug("debut repart => XML");
		
		repartition.sortieXML(getCheminDocument()+"/"+nomFichierXML);
		
		if(logger.isDebugEnabled())
			logger.debug("fin repart => XML");
	}
	
	/**
	 * Enregistrement de la répartition au format FDF
	 * @param nomFichierFDF
	 * @param fichierPDF - fichier PDF a associe au fichier FDF
	 */
	public void sauveFDF(String nomFichierFDF, String fichierPDF) {
		if(logger.isDebugEnabled())
			logger.debug("debut repart => FDF");
		
		String fichierFdf = getCheminDocument()+"/"+nomFichierFDF;
		repartition.saveFDF(fichierFdf,fichierPDF);
		
		if(logger.isDebugEnabled())
			logger.debug("fin repart => FDF");
	}
	
	/**
	 * Enregistrement de la répartition au format FDF
	 * @param nomFichierFDF
	 * @param fichierPDF - fichier PDF a associe au fichier FDF
	 * @param nomFichierPDF - nom du fichier PDF
	 */
	public void sauveFDF(String nomFichierFDF, String nomFichierPDF, URL fichierPDF) {
		try {
			if(logger.isDebugEnabled())
				logger.debug("debut repart => FDF");
			
			String fichierFdf = getCheminDocument()+"/"+nomFichierFDF;
			FileOutputStream copie = new FileOutputStream(getCheminDocument()+"/"+nomFichierPDF);
			InputStream source = fichierPDF.openStream();
			IOUtils.copy(source,copie);
			source.close();
			copie.close();
			repartition.saveFDF(fichierFdf,nomFichierPDF);
			
			if(logger.isDebugEnabled())
				logger.debug("fin repart => FDF");
		} catch (FileNotFoundException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Ouverture d'un fichier FDF avec le pdf correspondant dans le lecteur PDF (Adobe Reader), dont le chemin est indiqué dans les preferences<br>
	 * @param nomFichierFDF
	 */
	public void openFDFPreference(String nomFichierFDF) {
		String adobeReaderPath = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CHEMIN_ADOBE_READER);
		String fichierFdf = getCheminDocument()+"/"+nomFichierFDF;
		
		if(logger.isDebugEnabled())
			logger.debug("ouverture du FDF dans acrobat reader");
		
		try {
			Runtime.getRuntime().exec(
					new String[] {adobeReaderPath,
							new File(fichierFdf).getName()
					}, null,
					new File(fichierFdf).getParentFile()
			);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Ouverture d'un fichier FDF avec le pdf correspondant dans Adobe Reader sous Windows<br>
	 * <b>Attention :</b> Spéficique à l'OS.
	 * @param nomFichierFDF
	 */
	public void openFDFWindows(String nomFichierFDF) {
		//TODO a changer : Spécifique à l'OS
		String adobeReaderPath = "C:/Program Files/Adobe/Acrobat 7.0/Reader/AcroRd32.exe";
		String fichierFdf = getCheminDocument()+"/"+nomFichierFDF;
		
		if(logger.isDebugEnabled())
			logger.debug("ouverture du FDF dans acrobat reader");
		
		try {
			Runtime.getRuntime().exec(
					new String[] {adobeReaderPath,
							new File(fichierFdf).getName()
					}, null,
					new File(fichierFdf).getParentFile()
			);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Ouverture d'un fichier FDF avec le pdf correspondant dans Adobe Reader sous Linux<br>
	 * <b>Attention :</b> Spéficique à l'OS.
	 * @param nomFichierFDF
	 */
	public void openFDFLinux(String nomFichierFDF) {
		//TODO a changer : Spécifique à l'OS
		String adobeReaderPath = "acroread";
		String fichierFdf = getCheminDocument()+"/"+nomFichierFDF;
		
		if(logger.isDebugEnabled())
			logger.debug("ouverture du FDF dans acrobat reader");
		
		try {
			Runtime.getRuntime().exec(
					new String[] {adobeReaderPath,
							new File(fichierFdf).getName()
					}, null,
					new File(fichierFdf).getParentFile()
			);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Ouverture d'un fichier FDF avec le pdf correspondant dans Adobe Reader sous Windows<br>
	 * <b>Attention :</b> Spéficique à l'OS.
	 * @param nomFichierFDF
	 */
	public void openFDFIText(String nomFichierFDF) {		
		if(logger.isDebugEnabled())
			logger.debug("ouverture du FDF dans acrobat reader");
		
			String fichierFdf = getCheminDocument()+"/"+nomFichierFDF;
		try {
			Executable.openDocument(fichierFdf);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	public void addLgrWorkListener(LgrWorkListener l) {
		listenerList.add(LgrWorkListener.class, l);
	}
	
	public void removeLgrWorkListener(LgrWorkListener l) {
		listenerList.remove(LgrWorkListener.class, l);
	}
	
	protected void fireWork(LgrWorkEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == LgrWorkListener.class) {
				if (evt == null)
					evt = new LgrWorkEvent(this);
				( (LgrWorkListener) listeners[i + 1]).work(evt);
			}
		}
	}
	
	protected void fireBeginWork(LgrWorkEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == LgrWorkListener.class) {
				if (evt == null)
					evt = new LgrWorkEvent(this);
				( (LgrWorkListener) listeners[i + 1]).beginWork(evt);
			}
		}
	}
	
	protected void fireEndWork(LgrWorkEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == LgrWorkListener.class) {
				if (evt == null)
					evt = new LgrWorkEvent(this);
				( (LgrWorkListener) listeners[i + 1]).endWork(evt);
			}
		}
	}
	
	protected void fireSubTask(LgrWorkEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == LgrWorkListener.class) {
				if (evt == null)
					evt = new LgrWorkEvent(this);
				( (LgrWorkListener) listeners[i + 1]).beginSubtask(evt);
			}
		}
	}
	
	public String getCheminFichierCompta() {
		return cheminFichierCompta;
	}
	
	public void setCheminFichierCompta(String cheminFichierCompta) {
		this.cheminFichierCompta = cheminFichierCompta;
	}
	
	public String getNomDossier() {
		return nomDossier;
	}
	
	public void setNomDossier(String nomDossier) {
		this.nomDossier = nomDossier;
	}
	
	public boolean isNouveauDocument() {
		return nouveauDocument;
	}
	
	public void setNouveauDocument(boolean nouveauDocument) {
		this.nouveauDocument = nouveauDocument;
	}
	
	public boolean getNouveauDocument() {
		return nouveauDocument;
	}

	public String getCheminDossiers() {
		return cheminDossiers;
	}

	public EnumTypeDoc getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(EnumTypeDoc type) {
		this.typeDocument = type;
	}

	public String getCheminDocument() {
		return cheminDocument;
	}

	public void setCheminDocument(String cheminDocument) {
		this.cheminDocument = cheminDocument;
	}

	public EnumRegimeFiscal getRegime() {
		return regime;
	}

	public void setRegime(EnumRegimeFiscal regime) {
		this.regime = regime;
	}

	public Repart getRepartition() {
		return repartition;
	}

	public void setRepartition(Repart repartition) {
		this.repartition = repartition;
	}

	public InfosCompta getInfosCompta() {
		return infosCompta;
	}

	public void setInfosCompta(InfosCompta infosCompta) {
		this.infosCompta = infosCompta;
	}

	public int getAnneeDocumentPDF() {
		return anneeDocumentPDF;
	}

	public void setAnneeDocumentPDF(int anneeDocument) {
		this.anneeDocumentPDF = anneeDocument;
	}

	public String getAnneeFiscale() {
		return anneeFiscale;
	}

	public void setAnneeFiscale(String anneeFiscale) {
		this.anneeFiscale = anneeFiscale;
	}

	public boolean isAutomatique() {
		return automatique;
	}

	public void setAutomatique(boolean automatique) {
		this.automatique = automatique;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
}
