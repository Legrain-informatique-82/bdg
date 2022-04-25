package fr.legrain.liasseFiscale.wizards;

import java.util.HashMap;

import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import fr.legrain.liasseFiscale.wizards.fichier.WizardDocFichier;

public class WizardPageCreationDossier  /*extends WizardPage*/
										extends WizardSelectionPage 
										implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageCreationDossier";
	static final private String PAGE_TITLE = "Création d'un nouveau dossier";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
    
    private final String WIZ_DOCUMENT = "wizard_document";
    private String nextWizard = null;
    

    /////////////////////////////--WizardSelectionPage--//////////////////////////////////////
    private HashMap<String,IWizardNode> wizards = new HashMap<String,IWizardNode>();

    public void addWizard(String name, IWizardNode wizard) {
    	wizards.put(name,wizard);
    }

	public WizardPageCreationDossier(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		this.addWizard(WIZ_DOCUMENT,new WizardDoc());
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(((WizardDossier)getWizard()).getModel().getNomDossier()!=null)
			((CompositePageCreationDossier)control).getTxtCodeDossier().setText(((WizardDossier)getWizard()).getModel().getNomDossier());
	}

	public void createControl(Composite parent) {
		CompositePageCreationDossier control = new CompositePageCreationDossier(parent,SWT.NULL);
		
		control.getTxtCodeDossier().addListener(SWT.Modify, listenerValidate);
				
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
	}
	
	public Control getControl() {
		return control;
	}
	
	public boolean validatePage() {
		if(((CompositePageCreationDossier)control).getTxtCodeDossier().getText().equals("")) {
			setErrorMessage(null);
			setMessage("Veuillez saisir un code dossier.");
			return false;
		}
		if(((WizardDossier)getWizard()).getModel().dossierExiste(((CompositePageCreationDossier)control).getTxtCodeDossier().getText())) {
			setErrorMessage("Ce dossier existe déjà.");
			return false;
		}
        
		saveToModel();
		if(((WizardDossier)getWizard()).getModel().isAutomatique()) {
			setSelectedNode(wizards.get(WIZ_DOCUMENT));
			initNextWizard(WIZ_DOCUMENT);
		}
        setErrorMessage(null);
        setMessage(null);
        return true;
	}

	public void finishPage() {
		if (isPageComplete()) {
			//sur la derniere page, si un autre wizard doit suivre perfromFinish
			if(((WizardDossier)getWizard()).getModel().isAutomatique()) {
				getWizard().performFinish();
			}
		}
	}

	public void saveToModel() {
		((WizardDossier)getWizard()).getModel().setNomDossier(((CompositePageCreationDossier)control).getTxtCodeDossier().getText());
	}
	
	/**
	 * Initialisation d'une partie d'une partie du modèle du prochain wizard dans le noeud
	 * @param nextWizard - ID du prochain wizard
	 */
	public void initNextWizard(String nextWizard) {
		if(nextWizard.equals(WIZ_DOCUMENT)) {
			((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().copyProperties(((WizardDossier)getWizard()).getModel());
			//((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().setNomDossier(((WizardDossier)getWizard()).getModel().getNomDossier());
			//((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().setAnneeFiscale(((WizardDossier)getWizard()).getModel().getAnneeFiscale());
	       // ((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().setCheminDocument(((WizardDocFichier)getWizard()).getModel().getCheminDocument());

		}
	}

}
