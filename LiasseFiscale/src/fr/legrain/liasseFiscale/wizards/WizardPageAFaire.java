package fr.legrain.liasseFiscale.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class WizardPageAFaire extends WizardPage implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageAFaire";
	static final private String PAGE_TITLE = "A Faire";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
    private IControllerInfos controllerInfos = null;

	public WizardPageAFaire(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}
	
	private WizardPageAFaire getThis() {
		return this;
	}

	public void createControl(Composite parent) {
		CompositePageAFaire control = new CompositePageAFaire(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePageAFaire controlfinal = control;
		
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		ValidationResult validation;

//		if(((CompositePageCreationDoc)control).getTxtCodeDossier().getText().equals("")) {
//			setErrorMessage(null);
//			setMessage("Veuillez saisir un code dossier.");
//			return false;
//		}
        
		saveToModel();
        setErrorMessage(null);
        setMessage(null);
        return true;
	}

	public void finishPage() {
		if (isPageComplete()) {
			validatePage();
		}
	}

	public void saveToModel() {
		
	}

}
