package fr.legrain.liasseFiscale.wizards.old;

import java.io.File;
import java.util.Calendar;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardPageNouveauDossier extends WizardPage implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageNouveauDossier";
	static final private String PAGE_TITLE = "Nouvelle liasse dans nouveau dossier";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };

	public WizardPageNouveauDossier(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}

	public void createControl(Composite parent) {
		CompositePageNouveauDossier control = new CompositePageNouveauDossier(parent,SWT.NULL);

		control.getSpinAnnee().setSelection(Calendar.getInstance().get(Calendar.YEAR));
		
		control.getTxtNomDossier().addListener(SWT.Modify, listenerValidate);
		control.getSpinAnnee().addListener(SWT.Modify, listenerValidate);
		control.getTxtFichierCompta().addListener(SWT.Modify, listenerValidate);
		
		final Shell shell = parent.getShell();
		final CompositePageNouveauDossier controlfinal = control;
		control.getBtnParcourirFichierCompta().addListener(SWT.Selection,new Listener() {
	        public void handleEvent(Event e) {
	        	FileDialog d = new FileDialog(shell);
	        	controlfinal.getTxtFichierCompta().setText(d.open());
	        }
	    });
		
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
	}
	
	public boolean validatePage() {
		
		//Nom de dossier non vide
		if(((CompositePageNouveauDossier)control).getTxtNomDossier().getText().equals("")) {
			setErrorMessage(null);
			setMessage("Veuillez saisir un nom pour le dossier");
			return false;
		}
		
		//Nom de dossier inexistant
		String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
		File rep = new File(chemin);
		File[] listeDossier = rep.listFiles();
		for (int i = 0; i < listeDossier.length; i++) {
			if(listeDossier[i].isDirectory()) {
				if(listeDossier[i].getName().equals(
						((CompositePageNouveauDossier)control).getTxtNomDossier().getText())
						) {
					setErrorMessage("Le dossier \""+listeDossier[i].getName()+"\" existe déjà.");
					return false;
				}
			}
		}
		
		//Nom/Annee de liasse inexistant
		
		//Fichier de compta valide
		if(((CompositePageNouveauDossier)control).getTxtFichierCompta().getText().equals("")) {
			setErrorMessage(null);
			setMessage("Veuillez indiquer l'emplacement du fichier provenant du logiciel de comptabilité.");
			return false;
		}
		if(!((CompositePageNouveauDossier)control).getTxtFichierCompta().getText().equals("")) {
			if(!verifFichierCompta(((CompositePageNouveauDossier)control).getTxtFichierCompta().getText())) {
				setErrorMessage("Fichier de la compta invalide.");
				return false;
			}
		}

        setErrorMessage(null);
        setMessage(null);
        return true;
	}
	
	public Control getControl() {
		return control;
	}
	
	@Override
	public IWizardPage getPreviousPage() {
		return getWizard().getPage(WizardPageDebut.PAGE_NAME);
	}

	@Override
	public IWizardPage getNextPage() {
		return getWizard().getPage(WizardPageCinq.PAGE_NAME);
	}
	
	private boolean verifFichierCompta(String fichierComtpa) {
		return true;
	}
	
	public void saveToModel() {
		((WizardLiasse)getWizard()).getModel().setAnneeDocumentPDF(((CompositePageNouveauDossier)control).getSpinAnnee().getSelection());
		((WizardLiasse)getWizard()).getModel().setCheminFichierCompta(((CompositePageNouveauDossier)control).getTxtFichierCompta().getText());
		((WizardLiasse)getWizard()).getModel().setNomDossier(((CompositePageNouveauDossier)control).getTxtNomDossier().getText());
		((WizardLiasse)getWizard()).getModel().setNouveauDocument(true);
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			((WizardLiasse)getWizard()).etape1();
		}
	}
}
