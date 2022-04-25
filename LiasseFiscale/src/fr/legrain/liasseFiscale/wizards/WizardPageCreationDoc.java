package fr.legrain.liasseFiscale.wizards;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import fr.legrain.liasseFiscale.db.ConstLiasse;

public class WizardPageCreationDoc /*extends WizardPage*/
									extends WizardSelectionPage
									implements ILgrWizardPage {
	
	static Logger logger = Logger.getLogger(WizardPageCreationDoc.class.getName());
	static final public String PAGE_NAME = "WizardPageCreationDoc";
	static final private String PAGE_TITLE = "Création d'un nouveau document";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
    private IControllerInfos controllerInfos = null;
    
    private final String WIZ_LIASSE_FISCALE = "wizard_liasse_fiscale";
    private final String WIZ_TVA = "wizard_tva";
    private String nextWizard = null;
    

	public WizardPageCreationDoc(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		addWizard(WIZ_LIASSE_FISCALE,new WizardLiasseFiscale());
		addWizard(WIZ_TVA,new WizardTVA());
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////--WizardSelectionPage--//////////////////////////////////////
	private HashMap<String,IWizardNode> wizards = new HashMap<String,IWizardNode>();

	public void addWizard(String name, IWizardNode wizard) {
		wizards.put(name,wizard);
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	
	private WizardPageCreationDoc getThis() {
		return this;
	}

	public void createControl(Composite parent) {
		CompositePageCreationDoc control = new CompositePageCreationDoc(parent,SWT.NULL);
		
		final CompositePageCreationDoc controlfinal = control;
		
		control.getListeTypeDoc().addListener(SWT.Modify, new Listener() {
	        public void handleEvent(Event e) {
	        	
	          	for (int i = 0; i < controlfinal.getGroup1().getChildren().length; i++) {
	        		controlfinal.getGroup1().getChildren()[i].dispose();
				}
	          	controlfinal.getGroup1().setLayout(new FillLayout());
	          	
	          	if(controlfinal.getListeTypeDoc().getText().equals("Liasse fiscale")) {
	          		CompositeInfosLiasse comp = new CompositeInfosLiasse(controlfinal.getGroup1(),SWT.NULL);
	          		if(((WizardDoc)getWizard()).getModel().getAnneeFiscale()!=null)
	          			comp.getTfAnneeFiscale().setText(((WizardDoc)getWizard()).getModel().getAnneeFiscale());
	          		controllerInfos = new ControllerInfosLiasse(comp,getThis());
	          		logger.debug("creation liasse : annee par defaut = 2002");
	          		comp.getSpinAnnee().setSelection(2002);
	          		if(((WizardDoc)getWizard()).getModel().getAnneeFiscale()!=null)
	          			comp.getTfAnneeFiscale().setText(((WizardDoc)getWizard()).getModel().getAnneeFiscale());
	          	} else if(controlfinal.getListeTypeDoc().getText().equals("Déclaration TVA")) {
	          		CompositeInfosTVA comp = new CompositeInfosTVA(controlfinal.getGroup1(),SWT.NULL);
	          		controllerInfos = new ControllerInfosTVA(comp,getThis());
	          	}
	          	
	        	controlfinal.getGroup1().layout();
	        	setPageComplete(validatePage());
	        }
	    });
		//control.getListeTypeDoc().addListener(SWT.Modify, listenerValidate);
		
		control.getListeTypeDoc().add("Liasse fiscale");
		//control.getListeTypeDoc().add("Déclaration TVA");
		
		this.control = control;
		setControl(control);
		
	//	setPageComplete(validatePage());
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		try {
			setErrorMessage(null);
			setMessage(null);
			ValidationResult validation = null;
			if(((CompositePageCreationDoc)control).getGroup1().getChildren().length>0 && controllerInfos!=null) {
				if(((CompositePageCreationDoc)control).getGroup1().getChildren()[0] instanceof CompositeInfosLiasse) {
					((CompositeInfosLiasse)((CompositePageCreationDoc)control).getGroup1().getChildren()[0]).getSpinAnnee().getSelection();
					validation = controllerInfos.validateComposite();

				} else if(((CompositePageCreationDoc)control).getGroup1().getChildren()[0] instanceof CompositeInfosTVA) {
					validation = controllerInfos.validateComposite();
				}
				if(!validation.isEmpty()) {
					
					if(((WizardDoc)getWizard()).getModel().isAutomatique()) {
						//setErrorMessage(validation.getError());
						if(validation.getCodeRetour().equals(ControllerInfosLiasse.DOCUMENT_EXISTE)) {
							setMessage(validation.getInfo()+"\n Si vous continuez les données seront écrasées.",DialogPage.WARNING);
						} else if(validation.getCodeRetour().equals(ControllerInfosLiasse.DOCUMENT_VERROUILLE)) {
							setMessage(validation.getInfo()+"\n Ce document est vérrouillé. Impossible de poursuivre.",DialogPage.ERROR);
							return false;
						}
						//return true;
					} else {
						setErrorMessage(validation.getError());
						setMessage(validation.getInfo());
						return false;
					}
				}
			}
			choixWizard();
			
			saveToModel();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void finishPage() {
		if (isPageComplete()) {
			validatePage();
			
			//sur la derniere page, si un autre wizard doit suivre perfromFinish
			if(((WizardDoc)getWizard()).getModel().isAutomatique()) {
				getWizard().performFinish();
			}
		}
	}

	public void saveToModel() {
		if(((CompositePageCreationDoc)control).getGroup1().getChildren().length>0) {
			if(((CompositePageCreationDoc)control).getGroup1().getChildren()[0] instanceof CompositeInfosLiasse) {
				((WizardDoc)getWizard()).getModel().setTypeDocument(EnumTypeDoc.liasse);
				((WizardDoc)getWizard()).getModel().setAnneeDocumentPDF(
				((CompositeInfosLiasse)((CompositePageCreationDoc)control).getGroup1().getChildren()[0]).getSpinAnnee().getSelection());
				
				((WizardDoc)getWizard()).getModel().setAnneeFiscale(
						((CompositeInfosLiasse)((CompositePageCreationDoc)control).getGroup1().getChildren()[0]).getTfAnneeFiscale().getText());
				
			} else if(((CompositePageCreationDoc)control).getGroup1().getChildren()[0] instanceof CompositeInfosTVA) {
				((WizardDoc)getWizard()).getModel().setTypeDocument(EnumTypeDoc.TVA);
				((WizardDoc)getWizard()).getModel().setAnneeDocumentPDF(
				((CompositeInfosTVA)((CompositePageCreationDoc)control).getGroup1().getChildren()[0]).getSpinAnnee().getSelection());
				
			}
		}
	}
	
	/**
	 * Selection du wizard suivant en fonction de l'element selectionne.
	 * @param nomDossierDoc
	 * @return - L'ID du wizard suivant
	 */
	public String choixWizard() {
		//TODO identification du type de document et choix du wizard en conséquence.
		nextWizard = null;
		if(((WizardDoc)getWizard()).getModel().isAutomatique()) {
			if(((CompositePageCreationDoc)control).getGroup1().getChildren()[0] instanceof CompositeInfosLiasse) {
				nextWizard = WIZ_LIASSE_FISCALE;
				((WizardDoc)getWizard()).getModel().setCheminDocument(((WizardDoc)getWizard()).getModel().getCheminDossiers()+"/"+((WizardDoc)getWizard()).getModel().getNomDossier()+"/"+ConstLiasse.C_REP_DOC_TYPE_LIASSE+"/"+((WizardDoc)getWizard()).getModel().getAnneeFiscale());
			} else if(((CompositePageCreationDoc)control).getGroup1().getChildren()[0] instanceof CompositeInfosTVA) {
				nextWizard = WIZ_TVA;
			}
		}
		
		setSelectedNode(wizards.get(nextWizard));
		if(nextWizard!=null)
			initNextWizard(nextWizard);
		
		return nextWizard;
	}
	
	/**
	 * Initialisation d'une partie d'une partie du modèle du prochain wizard dans le noeud
	 * @param nextWizard - ID du prochain wizard
	 */
	public void initNextWizard(String nextWizard) {
		if(nextWizard.equals(WIZ_LIASSE_FISCALE)) {
			((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().copyProperties(((WizardDoc)getWizard()).getModel());
			((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().setCheminDocument(((WizardDoc)getWizard()).getModel().getCheminDocument());
			//((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().setNomDossier(((WizardDocumentFiscal)getWizard()).getModel().getNomDossier());
	        //((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().setCheminDocument(((WizardDocumentFiscal)getWizard()).getModel().getCheminDocument());
	 
		} else if(nextWizard.equals(WIZ_TVA)) {
			;
		}
	}

}
