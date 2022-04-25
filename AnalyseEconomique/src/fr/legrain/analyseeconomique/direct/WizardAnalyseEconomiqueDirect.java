package fr.legrain.analyseeconomique.direct;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.analyseeconomique.actions.ConstAnalyseEco;
import fr.legrain.analyseeconomique.actions.HeadlessAnalyseEco;
import fr.legrain.analyseeconomique.actions.ModelAnalyseEconomique;
import fr.legrain.analyseeconomique.preferences.PreferenceConstants;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.liasseFiscale.wizards.HeadlessLiasse;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;
import fr.legrain.liasseFiscale.wizards.WizardLiasseModel;

public class WizardAnalyseEconomiqueDirect extends Wizard /*implements IWizardNode*/ {
	
	static Logger logger = Logger.getLogger(WizardAnalyseEconomiqueDirect.class.getName());
	
	private ModelAnalyseEconomique model = new ModelAnalyseEconomique(); 
	
	private WizardPageAnalyseEcoDirect wizardPageAnalyseEcoDirect = new WizardPageAnalyseEcoDirect(WizardPageAnalyseEcoDirect.PAGE_NAME);
	private WizardPage2 wizardPage2 = new WizardPage2(WizardPage2.PAGE_NAME);
	private boolean ouvertureAutoSite = Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_ENVOI_FTP_AUTO);
	private boolean transfertAutoSite =	Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_OUVERTURE_SERVEUR_AUTO);
	private String agence = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_AGENCE);
	
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
	
	public WizardAnalyseEconomiqueDirect() {
		super();
		setWindowTitle("Assistant - Analyse Economique");

		addPage(wizardPageAnalyseEcoDirect);
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
			boolean error = false;
///////////SANS PROGRESSION
			HeadlessLiasse hl = new HeadlessLiasse(model.getCheminFichierCompta(),true);
			HeadlessAnalyseEco l = new HeadlessAnalyseEco();
			
			getContainer().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
			
			WizardLiasseModel dataLiasse = hl.traitementLiasse(
					model.getNomDossier(),
					model.getAnneeFiscale()
			);
			l.traitementAnalyseEco(dataLiasse,agence);

///////////AVEC PROGRESSION
//			//****
//			setNeedsProgressMonitor(true);
//			DoWithProgress doWithProgress = new DoWithProgress("test");
////			hl.addLgrWorkListener(doWithProgress);
//			//execution dans un autre thread car rien ne concerne l'ui
//			getContainer().run(true, true, doWithProgress);
////			hl.removeLgrWorkListener(doWithProgress);
//			//****
			
			if(isTransfertAutoSite()) {
				String repertoireComptable = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CODE_COMPTABLE_SERVEUR);
				
				if(repertoireComptable==null 
						|| repertoireComptable.equals("")
						){
					MessageDialog.openError(getShell(), "Analyse économique", "Connexion FTP automatique impossible.\nVeuillez saisir vos identifiants de connexion dans les préférences.");
					error = true;
				} else {
					///////////FTP AVEC PROGRESSION
					//****
					setNeedsProgressMonitor(true);
					DoFTPProgress doFTPProgress = new DoFTPProgress(
							null,
							dataLiasse.getCheminDocument()+"/"+ConstAnalyseEco.C_FICHIER_XML_AE,
							dataLiasse
					);
////					hl.addLgrWorkListener(doWithProgress);
//					//execution dans un autre thread car rien ne concerne l'ui
					getContainer().run(true, true, doFTPProgress);
////					hl.removeLgrWorkListener(doWithProgress);
//					//****
				}
			}
			
			if(isOuvertureAutoSite()) {
				IWebBrowser browser;
				String loginComptable = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CODE_COMPTABLE_SERVEUR);
				String pwdComptable = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD_COMPTABLE_SERVEUR);
				if((loginComptable==null 
						|| loginComptable.equals("")
						|| pwdComptable==null 
						|| pwdComptable.equals(""))
						&& !error){
					MessageDialog.openInformation(getShell(), "Analyse économique", "Connexion automatique impossible.\nVeuillez saisir vos identifiants de connexion dans les préférences.");
				} 
				//String url = "http://213.186.38.48:8080/birt/report/test.jsp";
				//String url = "http://localhost/php/AnalyseEco/test_upload.php";
				String url = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_ANALYSE_ECO);
				url+="?"+ConstAnalyseEco.C_VAR_CNX_SERVEUR_LOGIN+"="+loginComptable+"&"+ConstAnalyseEco.C_VAR_CNX_SERVEUR_PWD+"="+pwdComptable;
				//URL urlAnalyseEco = new URL(url);
				URL urlAnalyseEco = new URL(url);
				browser = PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser();
				//browser = PlatformUI.getWorkbench().getBrowserSupport().createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, "Analyse Economique","Analyse Economique","Analyse Economique");
				browser.openURL(urlAnalyseEco);	
			}
		} catch(Exception e) {
			logger.error("",e);
		} finally {
			getContainer().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}	
	
	public ModelAnalyseEconomique getModel() {
		return model;
	}
	
	private class DoWithProgress implements IRunnableWithProgress, LgrWorkListener {
		private IProgressMonitor monitor;
		private String libelle = "Répartition";
		
		public DoWithProgress(String libelle) {
			super();
			if(libelle != null)
				this.libelle = libelle;
		}
		
		public void run(IProgressMonitor monitor) {
			this.monitor = monitor;
			//model.repartitionDocument();
			

			HeadlessLiasse hl = new HeadlessLiasse(model.getCheminFichierCompta(),true);
			HeadlessAnalyseEco l = new HeadlessAnalyseEco();
			
			//****
			hl.addLgrWorkListener(this);
			//****
			
			l.traitementAnalyseEco(hl.traitementLiasse(
					model.getNomDossier(),
					model.getAnneeFiscale()
					),agence);
			
			hl.removeLgrWorkListener(this);
		}
		
		public void work(LgrWorkEvent evt) {
			monitor.worked(1);
		}
		
		public void beginWork(LgrWorkEvent evt) {
//			if(evt.getSubTaskName()==null)
				//monitor.beginTask(libelle+" : ", evt.getTotalAmount());
				monitor.beginTask(null, evt.getTotalAmount());
//			else
//				monitor.beginTask(libelle+" - "+evt.getSubTaskName()+" : ", evt.getTotalAmount());
		}
		
		public void endWork(LgrWorkEvent evt) {
			monitor.done();
		}

		public void beginSubtask(LgrWorkEvent evt) {
			if(evt.getSubTaskName()!=null)
				monitor.subTask(evt.getSubTaskName());
		}
	}
	
	private class DoFTPProgress implements IRunnableWithProgress, LgrWorkListener {
		private IProgressMonitor monitor;
		private String libelle = "Transfert sur le serveur : ";
		private String fichierAEnvoyer = null;
		private WizardLiasseModel dataLiasse = null;;
		
		public DoFTPProgress(String libelle, String fichierAEnvoyer, WizardLiasseModel dataLiasse) {
			super();
			if(libelle != null)
				this.libelle = libelle;
			this.fichierAEnvoyer = fichierAEnvoyer;
			this.dataLiasse = dataLiasse;
		}
		
		public void run(final IProgressMonitor monitor) {
			this.monitor = monitor;
			//model.repartitionDocument();
			
			FtpUtil ftpUtil = new FtpUtil();
			
			String urlServeurFTP = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_FTP);
			String repertoireComptable = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CODE_COMPTABLE_SERVEUR);
			//String loginComptable =  Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_LOGIN_COMPTABLE_SERVEUR);
			//String passwordComptable = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD_COMPTABLE_SERVEUR);
			String loginFTP = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_LOGIN_FTP);
			String passwordFTP = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD_FTP);

//			if(repertoireComptable==null 
//					|| repertoireComptable.equals("") ){
//				MessageDialog.openError(getShell(), "Analyse économique", "Connexion FTP automatique impossible.\nVeuillez saisir vos identifiants de connexion dans les préférences.");
//			} else {

				//ftpUtil.connectServer("lgrser.lgr", 21, "nicolas", "lgr00XXXX","");
				ftpUtil.connectServer(urlServeurFTP, 21, loginFTP, passwordFTP,"");
				ftpUtil.setFileType(FTP.BINARY_FILE_TYPE);
				ftpUtil.setBufferSize(256);

				ftpUtil.setCopyStreamListener(new CopyStreamListener(){

					public void bytesTransferred(CopyStreamEvent arg0) {
						// TODO Auto-generated method stub

					}

					public void bytesTransferred(long arg0, int arg1, long arg2) {
						// TODO Auto-generated method stub
						monitor.worked(arg1);
					}

				});

				try {
					//String fichierAEnvoyer = "/home/nicolas/Desktop/0193405032008-145854.zlg";
					logger.info("Envoi par FTP du fichier : "+fichierAEnvoyer);
					libelle+=urlServeurFTP;

					File f = new File(fichierAEnvoyer);
					monitor.beginTask(libelle, (int) f.length());
					ftpUtil.createDirectory(ConstAnalyseEco.C_CHEMIN_FTP_AE+"/"+repertoireComptable+"/"+dataLiasse.getNomDossier()+"/"+dataLiasse.getAnneeFiscale(),"777");
					ftpUtil.uploadFile(fichierAEnvoyer);
					ftpUtil.updatePermissionFile(f.getName(), "666");

					monitor.done();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("",e);
//					MessageDialog.openError(getShell(), "Analyse économique", "Erreur de connexion au serveur.");
				}

				ftpUtil.getReplyCodeServer();
				ftpUtil.closeServer();
//			}
		}
		
		public void work(LgrWorkEvent evt) {
			monitor.worked(1);
		}
		
		public void beginWork(LgrWorkEvent evt) {
//			if(evt.getSubTaskName()==null)
				//monitor.beginTask(libelle+" : ", evt.getTotalAmount());
				monitor.beginTask(null, evt.getTotalAmount());
//			else
//				monitor.beginTask(libelle+" - "+evt.getSubTaskName()+" : ", evt.getTotalAmount());
		}
		
		public void endWork(LgrWorkEvent evt) {
			monitor.done();
		}

		public void beginSubtask(LgrWorkEvent evt) {
			if(evt.getSubTaskName()!=null)
				monitor.subTask(evt.getSubTaskName());
		}
	}

	public boolean isOuvertureAutoSite() {
		return ouvertureAutoSite;
	}

	public void setOuvertureAutoSite(boolean ouvertureAutoSite) {
		this.ouvertureAutoSite = ouvertureAutoSite;
	}

	public boolean isTransfertAutoSite() {
		return transfertAutoSite;
	}

	public void setTransfertAutoSite(boolean transfertAutoSite) {
		this.transfertAutoSite = transfertAutoSite;
	}

	public String getAgence() {
		return agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}

}
