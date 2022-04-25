package fr.legrain.liasseFiscale.wizards.old;

import java.io.File;
import java.util.Calendar;
import java.util.List;

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

public class WizardPageAjoutLiasse extends WizardPage implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageAjoutLiasse";
	static final private String PAGE_TITLE = "Nouvelle liasse dans dossier existant";
	private Control control;
	private Listener listenerValidate = new Listener() {
		public void handleEvent(Event e) {
			setPageComplete(validatePage());
		}
	};
	
	public WizardPageAjoutLiasse(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}
	
	public void createControl(Composite parent) {
		CompositePageAjoutLiasse control = new CompositePageAjoutLiasse(parent,SWT.NULL);
		
		control.getSpinAnnee().setSelection(Calendar.getInstance().get(Calendar.YEAR));
		
		control.getSpinAnnee().addListener(SWT.Modify , listenerValidate);
		control.getSpinAnnee().addListener(SWT.Selection, listenerValidate);
		control.getTxtFichierCompta().addListener(SWT.Modify, listenerValidate);
		control.getListeDossier().addListener(SWT.Selection, listenerValidate);
		
		final Shell shell = parent.getShell();
		final CompositePageAjoutLiasse controlfinal = control;
		
		control.getBtnParcourirFichierCompta().addListener(SWT.Selection,new Listener() {
			public void handleEvent(Event e) {
				FileDialog d = new FileDialog(shell);
				controlfinal.getTxtFichierCompta().setText(d.open());
			}
		});
		
		//liste dossier
		List<String> l  = ((WizardLiasse)getWizard()).getModel().listeDossier();
		for (String s : l) {
			controlfinal.getListeDossier().add(s);
		}
		
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
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
	
	public boolean validatePage() {
		
		//Selection d'un dossier
		if(!(((CompositePageAjoutLiasse)control).getListeDossier().getSelection().length>0)) {
			setErrorMessage(null);
			setMessage("Veuillez sélectionner un dossier.");
			return false;
		}
		
		//Nom/Annee de liasse inexistant dans le dossier
		if(((CompositePageAjoutLiasse)control).getListeDossier().getSelection().length>0) {
			String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
			String annee = String.valueOf(((CompositePageAjoutLiasse)control).getSpinAnnee().getSelection());
			String nomDossier = ((CompositePageAjoutLiasse)control).getListeDossier().getSelection()[0];
			File rep = new File(chemin+"/"+nomDossier);
			File[] listeDossier = rep.listFiles();
			for (int i = 0; i < listeDossier.length; i++) {
				if(listeDossier[i].isDirectory()) {
					if(listeDossier[i].getName().equals(annee)) {
						setErrorMessage("La liasse pour l'année \""+listeDossier[i].getName()+"\" existe déjà.");
						return false;
					}
				}
			}
		}
		
		//Fichier de compta valide
		if(((CompositePageAjoutLiasse)control).getTxtFichierCompta().getText().equals("")) {
			setErrorMessage(null);
			setMessage("Veuillez indiquer l'emplacement du fichier provenant du logiciel de comptabilité.");
			return false;
		}
		if(!((CompositePageAjoutLiasse)control).getTxtFichierCompta().getText().equals("")) {
			if(!verifFichierCompta(((CompositePageAjoutLiasse)control).getTxtFichierCompta().getText())) {
				setErrorMessage("Fichier de la compta invalide.");
				return false;
			}
		}
		
		setErrorMessage(null);
		setMessage(null);
		return true;
	}
	
	private boolean verifFichierCompta(String fichierComtpa) {
		return true;
	}
	
	public void saveToModel() {
		((WizardLiasse)getWizard()).getModel().setAnneeDocumentPDF(((CompositePageAjoutLiasse)control).getSpinAnnee().getSelection());
		((WizardLiasse)getWizard()).getModel().setCheminFichierCompta(((CompositePageAjoutLiasse)control).getTxtFichierCompta().getText());
		((WizardLiasse)getWizard()).getModel().setNomDossier(((CompositePageAjoutLiasse)control).getListeDossier().getSelection()[0]);
		((WizardLiasse)getWizard()).getModel().setNouveauDocument(true);
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			((WizardLiasse)getWizard()).etape1();
		}
	}
	
}
