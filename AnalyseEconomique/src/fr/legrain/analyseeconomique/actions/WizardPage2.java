package fr.legrain.analyseeconomique.actions;

import org.apache.log4j.Logger;
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

public class WizardPage2 extends WizardPage implements ILgrWizardPage {
	
	static Logger logger = Logger.getLogger(WizardPage2.class.getName());
	
	static final public String PAGE_NAME = "WizardPage2";
	static final private String PAGE_TITLE = "Page 2";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };

	public WizardPage2(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}

	public void createControl(Composite parent) {
		CompositePage2 control = new CompositePage2(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePage2 controlfinal = control;
		
		control.getTxtFichierCompta().addListener(SWT.Modify, listenerValidate);
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
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		if(((CompositePage2)control).getTxtFichierCompta().getText().equals("")) {
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
		if(((WizardAnalyseEconomique)getWizard()).getModel().getCheminFichierCompta()!=null) {
			((CompositePage2)control).getTxtFichierCompta()
			.setText(((WizardAnalyseEconomique)getWizard()).getModel().getCheminFichierCompta());
		}
		if(!((WizardAnalyseEconomique)getWizard()).getModel().getNouveauDocument()) {
			((CompositePage2)control).getTxtFichierCompta().setEnabled(false);
		}
		super.setVisible(visible);
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			if(((WizardAnalyseEconomique)getWizard()).getModel().getNouveauDocument()) {
				((WizardAnalyseEconomique)getWizard()).getModel().copieFichierCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				((WizardAnalyseEconomique)getWizard()).getModel().lectureInfosCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				((WizardAnalyseEconomique)getWizard()).getModel().sauveInfosComptaXML(ConstLiasse.C_FICHIER_XML_COMPTA_INITIAL);
				
//				((WizardAnalyseEconomique)getWizard()).repartitionAvecProgression();
				((WizardAnalyseEconomique)getWizard()).getModel().sauveRepartXML(ConstLiasse.C_FICHIER_XML_REPART_INITIAL);
//				((WizardAnalyseEconomique)getWizard()).getModel().testRepartition();
//				((WizardAnalyseEconomique)getWizard()).updatePageSaisie();
			}
					
//			////////////////////////////////
//			//TODO saisie des compléments //
//			////////////////////////////////
//			txtEpi.getListeSaisieComplement().add(new InfoComplement("RN_ZRL_1","Le nom de l'entreprise",null));
//			
//			/////////////////////////////////////////////
//			//TODO Enregistrement dans la bdd (ou pas) //
//			/////////////////////////////////////////////
		}
	}

	public void saveToModel() {
		((WizardAnalyseEconomique)getWizard()).getModel().setCheminFichierCompta(((CompositePage2)control).getTxtFichierCompta().getText());
	}

}
