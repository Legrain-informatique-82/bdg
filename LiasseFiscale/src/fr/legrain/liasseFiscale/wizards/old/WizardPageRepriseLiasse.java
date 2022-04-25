package fr.legrain.liasseFiscale.wizards.old;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardPageRepriseLiasse extends WizardPage implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageRepriseLiasse";
	static final private String PAGE_TITLE = "Reprise d'une liasse";
	private Control control;
	private Listener listenerValidate = new Listener() {
		public void handleEvent(Event e) {
			setPageComplete(validatePage());
		}
	};

	public WizardPageRepriseLiasse(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}

	public void createControl(Composite parent) {
		CompositePageRepriseLiasse control = new CompositePageRepriseLiasse(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePageRepriseLiasse controlfinal = control;
		
		control.getListeLiasse().addListener(SWT.Selection, listenerValidate);
		control.getListeDossier().addListener(SWT.Selection, listenerValidate);
		
		//liste dossier
		List<String> l  = ((WizardLiasse)getWizard()).getModel().listeDossier();
		for (String s : l) {
			controlfinal.getListeDossier().add(s);
		}
		
		//liste liasse dans dossier
		control.getListeDossier().addListener(SWT.Selection,new Listener() {
	        public void handleEvent(Event e) {
	    		List<String> l  = ((WizardLiasse)getWizard()).getModel().listeDocument(controlfinal.getListeDossier().getSelection()[0]);
	    		controlfinal.getListeLiasse().removeAll();
	    		for (String s : l) {
	    			controlfinal.getListeLiasse().add(s);
	    		}
	        }
	    });
		
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
		if(!(((CompositePageRepriseLiasse)control).getListeDossier().getSelection().length>0)) {
			setErrorMessage(null);
			setMessage("Veuillez sélectionner un dossier.");
			return false;
		}
		
		//Selection d'une liasse
		if(!(((CompositePageRepriseLiasse)control).getListeLiasse().getSelection().length>0)) {
			setErrorMessage(null);
			setMessage("Veuillez sélectionner une liasse.");
			return false;
		}
		
		setErrorMessage(null);
		setMessage(null);
		return true;
	}
	
	public void saveToModel() {
		((WizardLiasse)getWizard()).getModel().setAnneeDocumentPDF(Integer.valueOf(((CompositePageRepriseLiasse)control).getListeLiasse().getSelection()[0]));
		((WizardLiasse)getWizard()).getModel().setNomDossier(((CompositePageRepriseLiasse)control).getListeDossier().getSelection()[0]);
		((WizardLiasse)getWizard()).getModel().setNouveauDocument(false);
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			((WizardLiasse)getWizard()).etape1();
		}
	}

}
