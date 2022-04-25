package fr.legrain.liasseFiscale.wizards;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.liasseFiscale.actions.InfoComplement;
import fr.legrain.liasseFiscale.db.ConstLiasse;

public class HeadlessLiasse {
	
	static Logger logger = Logger.getLogger(HeadlessLiasse.class.getName());
	
	protected EventListenerList listenerList = new EventListenerList();
	
	private String cheminFichierCompta = null;
	private boolean ecraser = false;

	public HeadlessLiasse(String cheminFichierCompta, boolean ecraser){
		this.cheminFichierCompta = cheminFichierCompta;
		this.ecraser = ecraser;
	}
	
	public void addLgrWorkListener(LgrWorkListener l) {
		listenerList.add(LgrWorkListener.class, l);
	}
	
	public void removeLgrWorkListener(LgrWorkListener l) {
		listenerList.remove(LgrWorkListener.class, l);
	}
	
	public WizardLiasseModel infosCompta() {
		WizardLiasseModel dataLiasse = new WizardLiasseModel();
//		WizardDoc wdoc = new WizardDoc();
//		WizardDossier wdoss = new WizardDossier();

		//1ere lecture du fichier de compta
		dataLiasse.setCheminFichierCompta(cheminFichierCompta);
		dataLiasse.lectureInfosCompta(cheminFichierCompta);

		Map<String,String> infosDossier = new HashMap<String,String>();
		for (InfoComplement info : dataLiasse.getInfosCompta().getListeInfosDossier()) {
			System.out.println(info.getCle()+" --- "+info.getValeur1());
			infosDossier.put(info.getCle(), info.getValeur1());
		}

		String dossier = infosDossier.get("Dossier");
		String exo = infosDossier.get("Nom_Exercice");

		dataLiasse.setNomDossier(dossier);
		dataLiasse.setAnneeFiscale(exo);
		
		dataLiasse.setTypeDocument(EnumTypeDoc.liasse);
		dataLiasse.setAnneeDocumentPDF(2002);
		dataLiasse.setRegime(EnumRegimeFiscal.agricole);
		
		return dataLiasse;
	}
	
	public boolean infosComptaValide(WizardLiasseModel wlf) {
		if (wlf.getNomDossier()==null
				|| wlf.getAnneeFiscale()==null) {
			return false;
		}
		return true;
	}
	
	public WizardLiasseModel traitementLiasse() {
		return traitementLiasse(null,null);
	}

	/*
		-copie du fichier de liasse
		-creation de la liasse
		-parametrage du traitement du document fiscal
		-lecture du fichier
		-repartition/calcul
		-(saisie)
		-(re-calcul)
	 */
	public WizardLiasseModel traitementLiasse(String nomDossier, String anneeDossier) {
		try {
			WizardLiasseModel dataLiasse = infosCompta();
			
			//cet le modele qui declenche les evenement, il faut donc "re"bracher les listener sur le model
			//et les enleves apres les traitement
			for (int i = 0; i < listenerList.getListeners(LgrWorkListener.class).length; i++) {
				dataLiasse.addLgrWorkListener(listenerList.getListeners(LgrWorkListener.class)[i]);
			}

//			WizardDoc wdoc = new WizardDoc();
//			WizardDossier wdoss = new WizardDossier();

//			wlf.setTypeDocument(EnumTypeDoc.liasse);
//			wlf.setAnneeDocumentPDF(2002);
//			wlf.setRegime(EnumRegimeFiscal.agricole);

			//1ere lecture du fichier de compta
			dataLiasse.fireBeginWork(new LgrWorkEvent(this,10000));
			dataLiasse.fireSubTask(new LgrWorkEvent(this,100,"Lecture du fichier de comptabilité"));
			
			dataLiasse.setCheminFichierCompta(cheminFichierCompta);
			dataLiasse.lectureInfosCompta(cheminFichierCompta);
			
			
			dataLiasse.fireWork(null);
			
			
			
			//remplacer les valeur par defaut s'il y a des parametres
			if(nomDossier!=null)
				dataLiasse.setNomDossier(nomDossier);
			if(anneeDossier!=null)
				dataLiasse.setAnneeFiscale(anneeDossier);

			//copie du fichier de liasse
			//creation de la liasse

			if(dataLiasse.dossierExiste(dataLiasse.getNomDossier())) {

				System.out.println("Le dossier existe deja");
				//quel type de document
				//test document
				//si doc existe deja : ecrasé ?
				dataLiasse.fireSubTask(new LgrWorkEvent(this,1,"Creation du dossier de liasse fiscale"));
				creationLiasse(dataLiasse);

			} else {
				dataLiasse.fireSubTask(new LgrWorkEvent(this,1,"Creation du dossier dossier"));
				creationDossier(dataLiasse);
				dataLiasse.fireSubTask(new LgrWorkEvent(this,1,"Creation du dossier de liasse fiscale"));
				creationLiasse(dataLiasse);

			}
			//parametrage du traitement du document fiscal  -----  WizardLiasseFiscale
			//**************************WizardPageRegime.saveToModel
//			if(((CompositePageRegime)control).getBtnAgricole().getSelection()) {

//			} else if (((CompositePageRegime)control).getBtnBIC().getSelection()) {
//			wlf.getModel().setRegime(EnumRegimeFiscal.BIC);
//			}
			//lecture du fichier
			//******************************WizardPageImportCompta.saveToModel()
			dataLiasse.setCheminFichierCompta(cheminFichierCompta);
			//******************************WizardPageImportCompta.finishPage
			if(dataLiasse.getNouveauDocument() || ecraser) {
				dataLiasse.copieFichierCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				dataLiasse.lectureInfosCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				dataLiasse.sauveInfosComptaXML(ConstLiasse.C_FICHIER_XML_COMPTA_INITIAL);

//				#			
//				wlf.repartitionAvecProgression();
				dataLiasse.fireSubTask(new LgrWorkEvent(this,1,"Generation de la liasse fiscale"));
				dataLiasse.repartitionDocument();

				dataLiasse.getRepartition().arrondiFinalLiasse();

				dataLiasse.sauveRepartXML(ConstLiasse.C_FICHIER_XML_REPART_INITIAL);
//				wlf.getModel().testRepartition();
//				#			
//				wlf.updatePageSaisie();
			}
			//repartition/calcul
			//(saisie)
			//(re-calcul)
			//*********************WizardLiasseFiscale.fin()
			dataLiasse.sauveInfosComptaXML(ConstLiasse.C_FICHIER_XML_COMPTA_FINAL);
//			#
//			repartitionAvecProgression("Répartition finale");

			dataLiasse.sauveRepartXML(ConstLiasse.C_FICHIER_XML_REPART_FINAL);
//			String nomFdf = "repart.fdf";
////			if(wlf.getModel().getRegime().compareTo(EnumRegimeFiscal.agricole)==0) {
//			wlf.getModel().sauveFDF(nomFdf,ConstLiasse.C_PDF_LIASSE_AGRI,urlPDF);

//			} else {
//			wlf.getModel().sauveFDF(nomFdf,ConstLiasse.C_PDF_LIASSE_BIC,urlPDF);
//			}

//			System.err.println("************** "+model.getRegime().toString()+" --- "+model.getTypeDocument());
			dataLiasse.sortieXML(dataLiasse.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
//			System.err.println("************** "+model.getRegime().toString()+" --- "+model.getTypeDocument());

//			if(System.getProperty("os.name").equals("Windows"))
//			model.openFDFWindows(nomFdf);
//			else if(System.getProperty("os.name").equals("Linux"))
//			model.openFDFLinux(nomFdf);

//			#
//			wlf.getModel().openFDFPreference(nomFdf);

			//model.openFDFIText(nomFdf);

//			new DernierDocument().enregistreDernierDoc(wlf);
			
			dataLiasse.fireEndWork(null);
			
			for (int i = 0; i < listenerList.getListeners(LgrWorkListener.class).length; i++) {
				dataLiasse.removeLgrWorkListener(listenerList.getListeners(LgrWorkListener.class)[i]);
			}
			
			return dataLiasse;
		} catch(Exception e) {
			logger.error("",e);
		}
		return null;
	}

	public void creationDossier(WizardLiasseModel wlf) {
		// surcharge de ((WizardDocFichier)getWizard()).getNextPage(page)
		//creation du dossier et du document
		//faire suivre une variable pour enchainer les 2 creations

		//************WizardPageCreationDossier.saveToModel();
		wlf.setNomDossier(wlf.getNomDossier());

		//*************WizardDossier.performFinish()
		File f = new File(wlf.getCheminDossiers()+"/"+wlf.getNomDossier());
		f.mkdirs();
	}

	public void creationLiasse(WizardLiasseModel wlf){
		//**********************************ControllerInfosLiasse.validateComposite
		String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
//		String annee = "2002";
//		String anneeFiscale = wlf.getModel().getAnneeFiscale();
		String nomDossier = wlf.getNomDossier();
		String cheminDoc = chemin+"/"+nomDossier+"/"+ConstLiasse.C_REP_DOC_TYPE_LIASSE;
		File rep = new File(cheminDoc);
		if(rep.exists()) { //else : 1ere liasse dans ce dossier
			File[] listeDossier = rep.listFiles();
			for (int i = 0; i < listeDossier.length; i++) {
				if(listeDossier[i].isDirectory()) {
					if(listeDossier[i].getName().equals(wlf.getAnneeFiscale())) {
						//if(listeDossier[i].getName().equals(annee)) {
						String message = "La liasse pour l'année fiscale \""+listeDossier[i].getName()+"\" existe déjà dans le dossier "+nomDossier+".";

						if(WizardDocumentFiscalModel.estVerrouille(cheminDoc+"/"+wlf.getAnneeFiscale())) {
							//"\n Si vous continuez les données seront écrasées.",DialogPage.WARNING
						} else {
							//"\n Ce document est vérrouillé. Impossible de poursuivre.",DialogPage.ERROR);
						}
						//return true;
					}
				}
			}
		}

		//**********************************WizardPageCreationDoc.saveToModel()


//		wlf.getModel().setAnneeFiscale(exo);

		//**************************WizardDoc.performFinish()
//		if(!wlf.isFinish()) {
		String repertoireBase = null;
		Document doc = new Document();

		repertoireBase = ConstLiasse.C_REP_DOC_TYPE_LIASSE;
		//File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeDocument());
		wlf.setCheminDocument(wlf.getCheminDossiers()+"/"+wlf.getNomDossier()+"/"+repertoireBase+"/"+wlf.getAnneeFiscale());
		File d = new File(wlf.getCheminDocument());
		d.mkdirs();
		//ajout du fichier permettant d'identifier le répertoire comme un rep contenant un document fiscal
		doc.setTypeDocument(EnumTypeDoc.liasse);
		doc.sortieXML(d.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_ID_DOC);

		WizardLiasseModel liasseModel = new WizardLiasseModel();
		liasseModel.copyProperties(wlf);
		liasseModel.setCheminDocument(wlf.getCheminDocument());
		liasseModel.sortieXML(d.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);

//		wlf.setFinish(true);
//		}
	}

}
