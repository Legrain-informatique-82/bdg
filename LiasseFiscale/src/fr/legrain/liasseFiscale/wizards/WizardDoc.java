package fr.legrain.liasseFiscale.wizards;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Point;

import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.fichier.WizardDialogDocFichier;
import fr.legrain.liasseFiscale.wizards.fichier.WizardPageImportFichier;

public class WizardDoc extends Wizard implements IWizardNode{
	
	static Logger logger = Logger.getLogger(WizardDoc.class.getName());
	
	private WizardDocumentModel model = new WizardDocumentModel(); 
	private WizardDocumentFiscal parent;
	
	private WizardPageCreationDoc wizardPageCreationDoc = new WizardPageCreationDoc(WizardPageCreationDoc.PAGE_NAME);
	
	public WizardDoc() {
		super();
		setWindowTitle("Assistant - Création d'un nouveau document");
	
		addPage(wizardPageCreationDoc);

	}
	
	public boolean canFinish() {
		if(getContainer() instanceof WizardDialogDocFichier //le wizard est un noeud de WizardDocFichier
				&& getContainer().getCurrentPage().getName().equals(WizardPageCreationDoc.PAGE_NAME)) {
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
		if(!model.isFinish()) {
			String repertoireBase = null;
			Document doc = new Document();
			
			if(model.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
				repertoireBase = ConstLiasse.C_REP_DOC_TYPE_LIASSE;
				//File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeDocument());
				File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeFiscale());
				dossier.mkdirs();
				//ajout du fichier permettant d'identifier le répertoire comme un rep contenant un document fiscal
				doc.setTypeDocument(EnumTypeDoc.liasse);
				doc.sortieXML(dossier.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_ID_DOC);

				WizardLiasseModel liasseModel = new WizardLiasseModel();
				liasseModel.copyProperties(model);
				liasseModel.setCheminDocument(model.getCheminDocument());
				liasseModel.sortieXML(dossier.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);

			} else if(model.getTypeDocument().compareTo(EnumTypeDoc.TVA)==0) {
				repertoireBase = ConstLiasse.C_REP_DOC_TYPE_TVA;
				File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier()+"/"+repertoireBase+"/"+model.getAnneeDocumentPDF());
				dossier.mkdirs();
				doc.setTypeDocument(EnumTypeDoc.TVA);
				doc.sortieXML(dossier.getAbsolutePath()+"/"+ConstLiasse.C_FICHIER_ID_DOC);
			}
			model.setFinish(true);
		}	
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
		//model = parent.getModel();
		model.copyProperties(parent.getModel());
		setWindowTitle("Dossier : "+parent.getModel().getNomDossier());
	}
	


	public Point getExtent() {
		//TODO Auto-generated method stub
		return new Point(-1,-1);
	}

	public IWizard getWizard() {
		//TODO Auto-generated method stub
		return this;
	}

	public boolean isContentCreated() {
		//TODO Auto-generated method stub
		return false;
	}

}
