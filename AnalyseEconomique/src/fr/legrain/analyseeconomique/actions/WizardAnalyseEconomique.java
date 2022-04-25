package fr.legrain.analyseeconomique.actions;

import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.analyseeconomique.preferences.PreferenceConstants;
import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.Compte;
import fr.legrain.liasseFiscale.actions.InfosCompta;
import fr.legrain.liasseFiscale.actions.Repart;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardAnalyseEconomique extends Wizard /*implements IWizardNode*/ {
	
	static Logger logger = Logger.getLogger(WizardAnalyseEconomique.class.getName());
	
	private ModelAnalyseEconomique model = new ModelAnalyseEconomique(); 
	
	private WizardPageChoixLiasse wizardPageChoixLiasse = new WizardPageChoixLiasse(WizardPageChoixLiasse.PAGE_NAME);
	private WizardPage2 wizardPage2 = new WizardPage2(WizardPage2.PAGE_NAME);
	
//	private String repDoc = "/documents";
//	private String repTypeDoc = "/liasse";
//	private String repAnneeDoc = null;
//	private String repRegimeDoc = null;
//	
//	private String streamRepertoireDoc = "";
//	private String streamRepertoirePDF = "/pdf";
//	private String streamRepertoireImagesPDF = "/images";
//	
//	private URL urlImage = null;
//	private URL urlPDF = null;
//	private String nomFichierPDF = null;
	
	public WizardAnalyseEconomique() {
		super();
		setWindowTitle("Assistant - Analyse Economique");

		addPage(wizardPageChoixLiasse);
//		addPage(wizardPage2);
	}
	
	private void initWizard() {
		
//		File modelFile = new File(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
//		if(modelFile.exists()) {
//	      	//récupération des parametres du document
//        	WizardLiasseModel m = (WizardLiasseModel)model.lectureXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
//        	model.copyProperties(m);
//		} else {
//			MessageDialog.openError(
//				this.getShell(),"Erreur","Le fichier "+ConstLiasse.C_FICHIER_XML_MODEL_DOC+" est introuvable.");
//			getShell().close();
//		}
//		
//		File repart = new File(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_REPART_INITIAL);
//        if(repart.exists()) { //reprise d'une liasse
//        	
//        	Repart r = new Repart();
//        	InfosCompta inf = new InfosCompta();
//        	//récupération des données provenant de la compta
//        	getModel().setRepartition(r.lectureXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_REPART_INITIAL));
//        	//récupération des données saisies à la main
//        	if(new File(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_COMPTA_FINAL).exists()) {
//        		getModel().setInfosCompta(inf.lectureXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_COMPTA_FINAL));
//        	} else {
//        		logger.debug(ConstLiasse.C_FICHIER_XML_COMPTA_FINAL+" introuvable, pas de récupération des données saisies à la main");
//        	}
//        	
//        	//ajoutPageSaisieAvecProgression("Récupération des données ...");
//
//        	getModel().setNouveauDocument(false);
//        	
//        }
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
		fin();
		return true;
	}
	
	public void fin() {	
		try {
			model.traitementAnalyseEco();
			
			if(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_OUVERTURE_SERVEUR_AUTO)) {
				IWebBrowser browser;
				//String url = "http://213.186.38.48:8080/birt/report/test.jsp";
				//String url = "http://localhost/php/AnalyseEco/test_upload.php";
				String url = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_ANALYSE_ECO);
				//URL urlAnalyseEco = new URL(url);
				URL urlAnalyseEco = new URL(url);
				browser = PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser();
				//browser = PlatformUI.getWorkbench().getBrowserSupport().createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, "Analyse Economique","Analyse Economique","Analyse Economique");
				browser.openURL(urlAnalyseEco);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public ModelAnalyseEconomique getModel() {
		return model;
	}

}
