package fr.legrain.liasseFiscale.wizards.fichier;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;
import fr.legrain.liasseFiscale.wizards.WizardDocumentFiscal;
import fr.legrain.liasseFiscale.wizards.WizardDocumentModel;
import fr.legrain.liasseFiscale.wizards.WizardPageCreationDoc;
import fr.legrain.liasseFiscale.wizards.WizardPageCreationDossier;
import fr.legrain.liasseFiscale.wizards.WizardPageDocumentFiscal;

public class WizardDocFichier extends Wizard {
	
	static Logger logger = Logger.getLogger(WizardDocFichier.class.getName());
	
	private WizardDocumentModel model = new WizardDocumentModel(); 
	private WizardDocumentFiscal parent;
	
	private WizardPageImportFichier wizardPageImportFichier = new WizardPageImportFichier(WizardPageImportFichier.PAGE_NAME);
	private WizardPageImportFichier wizardPageImportFichier2 = new WizardPageImportFichier(WizardPageImportFichier.PAGE_NAME);
	
	public WizardDocFichier() {
		super();
		setWindowTitle("Assistant - Création d'un nouveau document à partir d'un fichier");
		
		//addPage(wizardPageCreationDocFichier);
		//importation epicea
		addPage(wizardPageImportFichier);
		addPage(wizardPageImportFichier2); //a modifie : 2 page sinon pas de next
		//si nouveau dossier => creation dossier
		//type de document ?
		//si nouveau document => creation doc
		//sinon on ecrase le document, sauf si verrou
		
		model.setAutomatique(true);

	}
	
	@Override
	public boolean canFinish() {
		if(getContainer().getCurrentPage().getName().equals(WizardPageImportFichier.PAGE_NAME)) {
			return false;
		}
		return true;
		//return super.canFinish();
	}

	protected void backPressed(ILgrWizardPage page) {
	}
	
	protected void nextPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	protected void finishPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	@Override
	public boolean performFinish() {
		String repertoireBase = null;
//		Document doc = new Document();
//		if(model.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
//			repertoireBase = ConstLiasse.C_REP_DOC_TYPE_LIASSE;
//			//File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeDocument());
//			File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeFiscale());
//			dossier.mkdirs();
//			//ajout du fichier permettant d'identifier le répertoire comme un rep contenant un document fiscal
//			doc.setTypeDocument(EnumTypeDoc.liasse);
//			doc.sortieXML(dossier.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_ID_DOC);
//			
//			WizardLiasseModel liasseModel = new WizardLiasseModel();
//			liasseModel.copyProperties(model);
//			liasseModel.sortieXML(dossier.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
//
//		} else if(model.getTypeDocument().compareTo(EnumTypeDoc.TVA)==0) {
//			repertoireBase = ConstLiasse.C_REP_DOC_TYPE_TVA;
//			File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeDocumentPDF());
//			dossier.mkdirs();
//			doc.setTypeDocument(EnumTypeDoc.TVA);
//			doc.sortieXML(dossier.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_ID_DOC);
//		}
					
		return true;
	}
	
	public WizardDocumentModel getModel() {
		return model;
	}

	public WizardDocumentFiscal getParent() {
		return parent;
	}

	public void setParent(WizardDocumentFiscal parent) {
		this.parent = parent;
//		//model = parent.getModel();
//		model.copyProperties(parent.getModel());
//		setWindowTitle("Dossier : "+parent.getModel().getNomDossier());
	}

}
