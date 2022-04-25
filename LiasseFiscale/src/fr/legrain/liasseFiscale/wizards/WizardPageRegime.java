package fr.legrain.liasseFiscale.wizards;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class WizardPageRegime extends WizardPage implements ILgrWizardPage {
	
	static Logger logger = Logger.getLogger(WizardDocumentFiscal.class.getName());
	
	static final public String PAGE_NAME = "WizardPageRegime";
	static final private String PAGE_TITLE = "Régime";
	private Control control;
	private boolean enableControl = true;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };

	public WizardPageRegime(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}

	public void createControl(Composite parent) {
		CompositePageRegime control = new CompositePageRegime(parent,SWT.NULL);
		
		control.getBtnAgricole().setSelection(true);
		
		control.getBtnAgricole().addListener(SWT.Selection, listenerValidate);
		control.getBtnBIC().addListener(SWT.Selection, listenerValidate);
				
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
	}
	
	public Control getControl() {
		return control;
	}
	
	private void setEnableControl(boolean enable) {
		((CompositePageRegime)control).getBtnAgricole().setEnabled(enable);
		((CompositePageRegime)control).getBtnBIC().setEnabled(enable);
		this.enableControl = enable;
	}

	@Override
	public void setVisible(boolean visible) {
		if(((WizardLiasseFiscale)getWizard()).getModel().getRegime()!=null) {
			selectionRegime(((WizardLiasseFiscale)getWizard()).getModel().getRegime());
			setEnableControl(false);
		}
		super.setVisible(visible);
	}
	
	/**
	 * Selection du bouton radio correspondant au regime fiscal passé en parametre
	 * @param regime
	 */
	private void selectionRegime(EnumRegimeFiscal regime) {
		if(regime.compareTo(EnumRegimeFiscal.agricole)==0) {
			((CompositePageRegime)control).getBtnAgricole().setSelection(true);
			((CompositePageRegime)control).getBtnBIC().setSelection(false);
		} else if(regime.compareTo(EnumRegimeFiscal.BIC)==0) {
			((CompositePageRegime)control).getBtnBIC().setSelection(true);
			((CompositePageRegime)control).getBtnAgricole().setSelection(false);
		}
	}

	public boolean validatePage() {
		if( !( ((CompositePageRegime)control).getBtnAgricole().getSelection() 
				|| ((CompositePageRegime)control).getBtnBIC().getSelection() ) ) {
			setErrorMessage(null);
			setMessage("Veuillez sélectionner un régime");
			return false;
		}
        
		//saveToModel();
        setErrorMessage(null);
        setMessage(null);
        return true;
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			if(enableControl && ((WizardLiasseFiscale)getWizard()).getModel().getNouveauDocument()) {
				((WizardLiasseFiscale)getWizard()).ajoutPageSaisieAvecProgression("Génération des pages");
			}
		}
	}

	public void saveToModel() {
		if(((CompositePageRegime)control).getBtnAgricole().getSelection()) {
			((WizardLiasseFiscale)getWizard()).getModel().setRegime(EnumRegimeFiscal.agricole);
		} else if (((CompositePageRegime)control).getBtnBIC().getSelection()) {
			((WizardLiasseFiscale)getWizard()).getModel().setRegime(EnumRegimeFiscal.BIC);
		}
	}

}
