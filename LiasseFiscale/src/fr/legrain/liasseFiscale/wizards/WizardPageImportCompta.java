package fr.legrain.liasseFiscale.wizards;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.preferences.PreferenceConstants;

public class WizardPageImportCompta extends WizardPage implements ILgrWizardPage {
	
	static Logger logger = Logger.getLogger(WizardDocumentFiscal.class.getName());
	
	static final public String PAGE_NAME = "WizardPageImportCompta";
	static final private String PAGE_TITLE = "Importation des données.";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };

	public WizardPageImportCompta(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}

	public void createControl(Composite parent) {
		CompositePageImportCompta control = new CompositePageImportCompta(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePageImportCompta controlfinal = control;
		
		control.getTxtFichierCompta().addListener(SWT.Modify, listenerValidate);
		control.getBtnParcourirFichierCompta().addListener(SWT.Selection,new Listener() {
			public void handleEvent(Event e) {
				FileDialog d = new FileDialog(shell);
				d.setFilterPath(LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CHEMIN_COMPTA));
				controlfinal.getTxtFichierCompta().setText(d.open());
			}
		});
		
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		if(((CompositePageImportCompta)control).getTxtFichierCompta().getText().equals("")) {
			setErrorMessage(null);
			setMessage("Veuillez indiquer l'emplacement du fichier provenant du logiciel de comptabilité.");
			return false;
		}
        
		saveToModel();
        setErrorMessage(null);
        setMessage(null);
        return true;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(((WizardLiasseFiscale)getWizard()).getModel().getCheminFichierCompta()!=null) {
			((CompositePageImportCompta)control).getTxtFichierCompta()
			.setText(((WizardLiasseFiscale)getWizard()).getModel().getCheminFichierCompta());
		}
		if(!((WizardLiasseFiscale)getWizard()).getModel().getNouveauDocument()) {
			((CompositePageImportCompta)control).getTxtFichierCompta().setEnabled(false);
		}
		if(((WizardLiasseFiscale)getWizard()).getModel().getNouveauDocument()) {
			((CompositePageImportCompta)control).getCbEcraser().setVisible(false);
		}
		if(((WizardLiasseFiscale)getWizard()).getModel().estVerrouille()) {
			((CompositePageImportCompta)control).getCbEcraser().setVisible(false);
			((CompositePageImportCompta)control).getCbEcraser().setSelection(false);
			((CompositePageImportCompta)control).getTxtFichierCompta().setEnabled(false);
			((CompositePageImportCompta)control).getBtnParcourirFichierCompta().setEnabled(false);
		}
		if(((WizardLiasseFiscale)getWizard()).getModel().isAutomatique()) {
			((CompositePageImportCompta)control).getTxtFichierCompta().setEnabled(false);
			((CompositePageImportCompta)control).getBtnParcourirFichierCompta().setEnabled(false);
		}
		super.setVisible(visible);
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			if(((WizardLiasseFiscale)getWizard()).getModel().getNouveauDocument()
					|| ((CompositePageImportCompta)control).getCbEcraser().getSelection()
					) {
				((WizardLiasseFiscale)getWizard()).getModel().copieFichierCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				((WizardLiasseFiscale)getWizard()).getModel().lectureInfosCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				((WizardLiasseFiscale)getWizard()).getModel().sauveInfosComptaXML(ConstLiasse.C_FICHIER_XML_COMPTA_INITIAL);
				
				((WizardLiasseFiscale)getWizard()).repartitionAvecProgression();
				((WizardLiasseFiscale)getWizard()).getModel().sauveRepartXML(ConstLiasse.C_FICHIER_XML_REPART_INITIAL);
//				((WizardLiasseFiscale)getWizard()).getModel().testRepartition();
				((WizardLiasseFiscale)getWizard()).updatePageSaisie();
			}

		}
	}

	public void saveToModel() {
		((WizardLiasseFiscale)getWizard()).getModel().setCheminFichierCompta(((CompositePageImportCompta)control).getTxtFichierCompta().getText());
	}

}
