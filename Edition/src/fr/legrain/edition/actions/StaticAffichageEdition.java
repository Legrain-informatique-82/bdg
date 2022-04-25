package fr.legrain.edition.actions;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.osgi.framework.Bundle;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import fr.legrain.edition.Activator;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.edition.preferences.PreferenceConstants;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.libLgrBirt.BirtUtil;
import fr.legrain.libLgrBirt.WebViewerUtil;
import fr.legrain.silentPdfPrint.LgrSpooler;


public class StaticAffichageEdition {

	static Logger logger = Logger.getLogger(StaticAffichageEdition.class.getName());

	static private String pathFileEdition = null;
	static private HashMap appContextEdition = null;
	static private EntityManager entityManager;
	static private boolean flagEntityManager = false;
	
	/** 08/02/2010 **/
	static private boolean flagPrint = false;
	static private boolean afficheEditionAImprimer = false; 
	static private String pathFileAImprimer = null;
	static private String  pathAdobeReader = null;
	
	public static List<Object> listEditionDynamique = new LinkedList<Object>();
	
	public StaticAffichageEdition(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public StaticAffichageEdition() {

	}
	/**
	 * pour l'edition commence Fiche
	 */
	static private String parametreEditionIdDocument = null;  
	static private Integer idDocument;
	/**
	 * Edition normale sans parametre dans le fichier d'edition
	 * @param pathFile - chemin du fichier d'edition
	 * @param titleEdition - titre de l'edition - afficher dans le navigateur ou l'onglet
	 * @param formatReport - format de l'edition
	 * @param extraction
	 */
	static synchronized public void showEditionDynamiqueDefaut(String pathFile,final String titleEdition,
			String formatReport,Boolean extraction){

		prepartionBirtViewer();
		prepartionEditionEntityManager();
		makeBirtViewerEdition(pathFile,titleEdition,formatReport,extraction,false,null,false,null);
		destroyEntityManager();

	}
	static synchronized public void newImprimerThreadFicheEditon(final String fileEdition,final String Title,
			final String fromatFileReport,final Boolean extractionfinal,final LinkedList<Integer> id,
			final String param,final String params){
		
			Thread t = new Thread() {
			public void run() {
				try {
					showEditionNewFormat(fileEdition,Title,fromatFileReport,extractionfinal,id,param,params);
				}catch (Exception e) {
					logger.error("Erreur à l'impression ",e);
				} finally {
					//					ibTaTable.activeTrigger(trigger,true);
					//					ibTaTable.commitLgr();
				}
			}
		};
		t.start();
	}

	/**
	 * Edition normale avec des parametres dans le fichier d'edition
	 * @param pathFile - chemin du fichier d'edition
	 * @param titleEdition - titre de l'edition - afficher dans le navigateur ou l'onglet
	 * @param formatReport - format de l'edition
	 * @param extration
	 * @param id - Id du document à imprimer
	 * @param parametreId - nom du parametre dans l'edition representant l'ID du document a imprimer
	 * @param params - parametre de l'edition, (passage de parametre birt par l'url)
	 */
	static synchronized public void showEditionNewFormat(String pathFile,final String titleEdition,
			String formatReport,boolean extraction,LinkedList<Integer> id,
			String parametreId,String params){

		
		prepartionBirtViewer();
		prepartionEditionEntityManager();
		/*
		 * Extraction ==> url += "run?__report="; replace url += "frameset?__report=";
		 * 			  ==> url += "&__format=pdf"; delete
		 */
		File file = new File(pathFileEdition);
		String nameFileEditionSepcique = file.getName();

		if(nameFileEditionSepcique.equals(ConstEdition.NAME_FILE_EDITION_SPECIFIQUE_ONE)){
			String FileImage = Const.C_REPERTOIRE_BASE+
			Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+
			ConstEdition.FOLDER_EDITION+ConstEdition.FOLDER_IMAGE_LOGO+ConstEdition.IMAGE_LOGO_FOND;
			//System.out.println(floderImage);

			EditionAddProprityToEdition editionAddProprityToEdition = new EditionAddProprityToEdition();
			editionAddProprityToEdition.setPathFileEdition(pathFileEdition);
			editionAddProprityToEdition.initialiserDesignReportConfig(pathFileEdition);
			editionAddProprityToEdition.addImageBackGround(FileImage);
			editionAddProprityToEdition.savsAsDesignHandle();

		}
		makeBirtViewerEdition(pathFile, titleEdition, formatReport, extraction, true, params,
				false,null);
		destroyEntityManager();

	}	


	static synchronized public void showEditionDynamiqueDefautThread(final String fileEdition,final String title,
			final String fromatFileReport,final Boolean extraction){

		Thread t = new Thread() {
			public void run() {
				try {
					showEditionDynamiqueDefaut(fileEdition,title,fromatFileReport,extraction);
					
				}catch (Exception e) {
					logger.error("", e);
				}
			}
		};
		t.start();

	}

	/**
	 * Imprime plusieur fois la même edition en série.
	 * @param id - liste des enregistrements dans la bdd à imprimer
	 * @param preview - impression directe
	 * @param parametre - nom du parametre dans l'edition representant l'ID du document a imprimer
	 */
	static synchronized public void imprimerFicheTotal(LinkedList<Integer> id,boolean preview,final String parametre){
		HashMap<String,String> reportParam = new HashMap<String,String>();
		imprimerFicheTotal(id, preview, reportParam, parametre);
	}

	/**
	 * @see StaticAffichageEdition#imprimerFicheTotal(LinkedList, boolean, HashMap, String, int, boolean)
	 */
	static synchronized public void imprimerFicheTotal(LinkedList<Integer> id,boolean preview,final HashMap<String,String> reportParametres,final String parametre,int format){
		imprimerFicheTotal(id, preview, reportParametres, parametre, format, true);
	}

	/**
	 * @see StaticAffichageEdition#imprimerFicheTotal(LinkedList, boolean, HashMap, String, int, boolean)
	 */
	//public void imprimerFicheTotal(LinkedList<Integer> id,boolean preview,final HashMap<String,String> reportParametres,final String parametre){
	static synchronized public void imprimerFicheTotal(Collection collectionEntityJpa,boolean preview,final HashMap<String,String> reportParametres,final String parametre){
		imprimerFicheTotal(collectionEntityJpa, preview, reportParametres, parametre, BirtUtil.PDF_FORMAT, true);
	}

	/**
	 * Imprime plusieur fois la même edition en série.
	 * @param id - liste des enregistrements dans la bdd à imprimer
	 * @param preview - impression directe
	 * @param reportParametres - parametres de l'edition ==> pour edition JPA, on n'a pas besoin cette parametre
	 * @param parametre - nom du parametre dans l'edition representant l'ID du document a imprimer
	 * @param format - format de l'edition
	 * @param impressionFlux - vrai si on souhaite afficher le resultat de l'edition à l'écran
	 */
	//public void imprimerFicheTotal(LinkedList<Integer> id,boolean preview,final HashMap<String,String> reportParametres,final String parametre,final int format,boolean impressionFlux){
	static synchronized public void imprimerFicheTotal(final Collection collectionEntityJpa,boolean preview,final HashMap<String,String> reportParametres,
			final String parametre,final int format,boolean impressionFlux){
	
		
		///////////////////////////
		BirtUtil.setAppContextEdition(appContextEdition);
		prepartionBirtReportEngine();
		
		prepartionEditionEntityManager();
		final String[] finalIdFactureAImprimer = new String[collectionEntityJpa.size()];
		int i=0;
		
//		for (Integer integer : id) {
//			finalIdFactureAImprimer[i]=integer.toString();
//			i++;
//		}
		
//		ImprimeObjet.clearListAndMap();
//		ImprimeObjet.listEntityManager.add(new JPABdLgr().getEntityManager());
		
		final File finalReportFile = new File(pathFileEdition);
		final String nameFile = finalReportFile.getName();
//		Strhing simpleNameFile = finalReportFile.getName();
		String pathAbsoluteFinalReportFile = finalReportFile.getAbsolutePath();

		final HashMap<String,String> finalReportParam = reportParametres;

		//		String  ss =IB_APPLICATION.findDatabase().getConnection().getConnectionURL().toString();
		//		System.out.println("++++++---"+ss);
		
		LgrSpooler spooler = LgrSpooler.getInstance();
		spooler.clear();
		final LgrSpooler finalSpooler = spooler;
//		Job job = new Job("Préparation de l'impression") {
//			protected IStatus run(IProgressMonitor monitor) {
//				final int ticks = finalIdFactureAImprimer.length;
//				monitor.beginTask("Préparation de l'impression", ticks);
				try {
					OutputStream os = null;
					//finalReportParam.put("paramUrlJDBC",IB_APPLICATION.findDatabase().getConnection().getConnectionURL());
					//finalReportParam.put("paramPageTotal",String.valueOf(finalIdFactureAImprimer.length));
					/*** new ***/
					//List<Object> list = new LinkedList<Object>();
					
					Iterator it = collectionEntityJpa.iterator(); 
					while (it.hasNext()) {
						Object objectEntity = it.next();
						ImprimeObjet.l.clear();
						ImprimeObjet.m.clear();
						if(nameFile.startsWith(ConstEdition.START_V)){
							ImprimeObjet.m.put(objectEntity.getClass().getSimpleName(),listEditionDynamique);
						}else{
							ImprimeObjet.l.add(objectEntity);
							ImprimeObjet.m.put(objectEntity.getClass().getSimpleName(), ImprimeObjet.l);
						}
						os = BirtUtil.renderReportToStream(finalReportFile.getAbsolutePath(),finalReportParam,format);
						finalSpooler.add(os);
//						monitor.worked(1);
//						if (monitor.isCanceled()) {
							finalSpooler.clear();
//							return Status.CANCEL_STATUS;
//						}
					}
					os.close();
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("error OutputStream ",e);
				} 
//				finally {
//					monitor.done();
//				}
//				return Status.OK_STATUS;
//			}
//		};
//		job.setPriority(Job.SHORT);
//		//job.setUser(true);
//		job.schedule(); 
//		try {
//			job.join();
//		} catch (InterruptedException e) {
//			logger.error("Erreur à l'impression ",e);
//		}

		/** Impression (afficher l'edition )**/
		
		//if(impressionFlux) {
		if(afficheEditionAImprimer && !flagPrint) {
//			if(job.getResult()==Status.OK_STATUS)
				spooler.print(preview);
		}

		BirtUtil.destroyReportEngine();
		/** 29/01/2010 add **/
		destroyEntityManager();
		
		/** 09/02/2010 pour afficher l'edition et make pdf de l'edition , imprimer **/
		if(flagPrint) {

			String simpleNameFile = nameFile.substring(0, nameFile.indexOf(".rptdesign"));
			String pathPdf = pathFileAImprimer+"/"+simpleNameFile+PreferenceConstants.TYPE_FILE_PDF;
			makeFileEditionEnFormatPDF(pathPdf);
			
			if(afficheEditionAImprimer){
				afficheFichierResultatEdition("file://"+pathPdf,simpleNameFile);
			}
			printFileForAdobeReader(pathPdf);
		}
	}

	static public void destroyEntityManager() {
		
		if(flagEntityManager){
			entityManager.close();
			ImprimeObjet.listEntityManager.remove(entityManager);
			entityManager = null;
		}
	}

	static synchronized public void imprimerThreadAllFiche(final LinkedList<Integer> id,final boolean preview,final String parametre){
		Thread t = new Thread() {
			public void run() {
				try {
					imprimerFicheTotal(id,preview,parametre);
				}catch (Exception e) {
					logger.error("Erreur à l'impression ",e);
				} finally {
					//					ibTaTable.activeTrigger(trigger,true);
					//					ibTaTable.commitLgr();
				}
			}
		};
		t.start();
	}

	/**
	 * 
	 * @param id
	 * @param preview
	 * @param reportParametre
	 * @param parametre
	 */
	//public void imprimerThreadAllFiche(final LinkedList<Integer> id,final boolean preview,final HashMap<String,String> reportParametre,final String parametre){
	static synchronized public void imprimerThreadAllFiche(final Collection collectionEntityJpa,final boolean preview,final HashMap<String,String> reportParametre,
			final String parametre){
		
		boolean flagMessageError = false;
		if(flagPrint){
			if(pathAdobeReader.length()==0){
				flagMessageError = true;
			}
		}else{
			flagMessageError = false;
		}
		
		if(flagMessageError){
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			"Erreur", "Les préférences concernant Acrobat Reader ne sont pas toutes renseignées." +
					"\n\nVeuillez aller dans le menu Outils/Préférences " +
					"pour paramétrer Edition avant d'utiliser le publipostage.");
		}else{
//			Thread t = new Thread() {
//				public void run() {
//					try {
						//imprimerFicheTotal(id,preview,reportParametre,parametre);
						imprimerFicheTotal(collectionEntityJpa,preview,reportParametre,parametre);
//					}catch (Exception e) {
//						logger.error("Erreur à l'impression ",e);
//					} finally {
//						//					ibTaTable.activeTrigger(trigger,true);
//						//					ibTaTable.commitLgr();
//					}
//				}
//			};
//			t.start();
		}
	}	

	static synchronized public void makeBirtViewerEdition(String pathFile,final String titleEdition,
			String formatReport,boolean extraction,boolean flagIdEntity,
			String params,boolean flagPDF,String idBrowserEditor) {
		
		String pathURL = "";
		if(flagPDF){
			pathURL = pathFile;
		}else{
			String url = WebViewerUtil.debutURL();
//			System.setProperty("RUN_UNDER_ECLIPSE", "true");

			if(extraction){
				url += "frameset?__report=";
			}else{
				url += "run?__report=";
			}
			url += pathFile;

			if(flagIdEntity){
				url +="&"+parametreEditionIdDocument+"="+String.valueOf(idDocument);
			}
			if(params != null){
				url +=params;
			}

			logger.debug("Report file asLocalURL File : "+ pathFileEdition);
			url += "&__document=doc"+new Date().getTime();

			if(!extraction&&!formatReport.equalsIgnoreCase(ConstEdition.FORMAT_HTML)){
				url += "&__format="+formatReport;
			}
			pathURL = url;
		}
		final String finalURL = pathURL;
		final String finalIdBrowserEditor = idBrowserEditor==null?idBrowserEditor:"myId";		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Thread() {
			public void run() {
				try {
					LgrPartListener.getLgrPartListener().setLgrActivePart(null);
					PlatformUI.getWorkbench().getBrowserSupport()
					.createBrowser(
							IWorkbenchBrowserSupport.AS_EDITOR,
							finalIdBrowserEditor,
							titleEdition
							//+ codeFacture,
							,""
					).openURL(
							new URL(finalURL));
					// oldFacture = new OldFacture();//réinitialisation de cet objet
				} catch (PartInitException e) {
					logger.error("", e);
				} catch (MalformedURLException e) {
					logger.error("", e);
				}
			}
		});

	}

	/**
	 * Affichage d'un fichier déjà existant (PDF, HTML, ...) sous la forme d'une edition. (Onglet edition)
	 * @param cheminFichier
	 */
	static public void afficheFichierResultatEdition(String cheminFichier, final String titleEdition) {
//		final String finalURL = cheminFichier;
//		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//			public void run() {
//				try {
//					LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//					PlatformUI.getWorkbench().getBrowserSupport()
//					.createBrowser(
//							IWorkbenchBrowserSupport.AS_EDITOR,
//							"myId",
//							titleEdition
//							//+ codeFacture,
//							,""
//					).openURL(
//							new URL(finalURL));
//					// oldFacture = new OldFacture();//réinitialisation de
//					// cet objet
//				} catch (PartInitException e) {
//					logger.error("", e);
//				} catch (MalformedURLException e) {
//					logger.error("", e);
//				}
//			}
//		});
		makeBirtViewerEdition(cheminFichier, titleEdition, null, false, false, null, true, null);
	}

	/**
	 * pour generer file del'edition à imprimer 
	 * @param pathFile ==> path file edition
	 */
	static synchronized public void makeFileEditionEnFormatPDF(String pathPdfFile){
		
		try {
			
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileOutputStream fos = new FileOutputStream(pathPdfFile);


				PdfCopy writer = new PdfCopy(document, baos);
				document.open();

				for (Object fichier : LgrSpooler.getInstance()) {
					if(fichier instanceof String) {
						PdfReader reader = new PdfReader((String)fichier);
						PdfImportedPage page = null; 
						for (int i = 1; i <= reader.getNumberOfPages(); i++) {
							//writer.getImportedPage(reader,i);
							page = writer.getImportedPage(reader, i);
							writer.addPage(page);
						}
					} else if(fichier instanceof OutputStream) {
						PdfReader reader = new PdfReader(new ByteArrayInputStream(((ByteArrayOutputStream)fichier).toByteArray()));
						PdfImportedPage page = null; 
						for (int i = 1; i <= reader.getNumberOfPages(); i++) {
							//writer.getImportedPage(reader,i);
							page = writer.getImportedPage(reader, i);
							writer.addPage(page);
						}
					}
				}
//				baos.writeTo(fos);
//				fos.flush();
				document.close();
				
				baos.writeTo(fos);
				fos.close();
				baos.close();
//				LgrSpooler.getInstance().clear();
				writer.close();
			
			

			LgrSpooler.getInstance().clear();

		} catch(Exception e) {
			logger.error("",e);
		}
	}
//	/**
//	 * Génére une edition et stocke le fichier généré à l'emplacement indiqué.
//	 * @param cheminFichierGenere - chemin de l'edition - le fichier généré
//	 * @param pathFile - chemin du fichier d'edition (.rptdesign)
//	 * @param reportParametres - parametres de l'edition
//	 * @param parametre - parametre de l'edition (passage de parametre dans l'url birt)
//	 * @param formatReport - format de l'edition
//	 * @param id - Id du document à imprimer
//	 * @param parametreId - nom du parametre représentant l'Id de l'edition
//	 * @param params
//	 */
//	public void genereFichierResultatEdition(String cheminFichierGenere,String pathFile,HashMap<String,String> reportParametres,
//			//String parametre, int formatReport,LinkedList<Integer> id,String parametreId,String params){
//			String parametre, int formatReport,Collection collectionEntityJpa,String parametreId,String params){
//		try {
//			
//			setPathFileEdition(pathFile);
//			BirtUtil.setAppContextEdition(appContextEdition);
//
//			int formatEdition = formatReport;
//			if(collectionEntityJpa.size()>1) {
//				//pour les listes, seul le format PDF est géré (probleme de concatenation de fichier)
//				formatEdition = BirtUtil.PDF_FORMAT;
//			}
//			imprimerFicheTotal(collectionEntityJpa,false,reportParametres,parametre,formatReport,false);
//
//			Document document = new Document();
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			FileOutputStream fos = new FileOutputStream("/home/lee/lgrdoss/BureauDeGestion/tmp/aaa.pdf");
//
//			if(formatEdition == BirtUtil.PDF_FORMAT) {
//				PdfCopy writer = new PdfCopy(document, baos);
//				document.open();
//
//				for (Object fichier : LgrSpooler.getInstance()) {
//					if(fichier instanceof String) {
//						PdfReader reader = new PdfReader((String)fichier);
//						PdfImportedPage page = null; 
//						for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//							//writer.getImportedPage(reader,i);
//							page = writer.getImportedPage(reader, i);
//							writer.addPage(page);
//						}
//					} else if(fichier instanceof OutputStream) {
//						PdfReader reader = new PdfReader(new ByteArrayInputStream(((ByteArrayOutputStream)fichier).toByteArray()));
//						PdfImportedPage page = null; 
//						for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//							//writer.getImportedPage(reader,i);
//							page = writer.getImportedPage(reader, i);
//							writer.addPage(page);
//						}
//					}
//				}
////				baos.writeTo(fos);
////				fos.flush();
//				document.close();
//				
//				baos.writeTo(fos);
//				fos.close();
//				baos.close();
////				LgrSpooler.getInstance().clear();
//				writer.close();
//			} 
//			
//			LgrSpooler.getInstance().clear();
//
//		} catch(Exception e) {
//			logger.error("",e);
//		}
//	}
	
	
	/** 08/02/2010 **/
	static public boolean printFileForAdobeReader(String pathFilePdf){
		
		AnalyseFileReport analyseFileReport = new AnalyseFileReport(); 
		analyseFileReport.initializeBuildDesignReportConfig(pathFileEdition);
		String orientationReport = analyseFileReport.findOrientationReport();
		if(!orientationReport.equals("landscape")){
			orientationReport="";
		}
		analyseFileReport.closeDesignReportConfig();
		
		String cmdAdobeReader = null;
		try{

			if(Platform.getOS().equals(Platform.OS_WIN32)){
				//C:/Program Files/Adobe/Reader 9.0/Reader/AcroRd32.exe /N /T C:/lgrdoss/BureauDeGestion/tmp/FicheFacture.pdf
				cmdAdobeReader = pathAdobeReader+ConstEdition.SEPARATOR+"AcroRd32.exe /N /T "+pathFilePdf;
				Process p1 = Runtime.getRuntime().exec(cmdAdobeReader);
			}else if(Platform.getOS().equals(Platform.OS_LINUX)){
				String pathFileShell = getPathShellFile();
			    Process p1 = Runtime.getRuntime().exec(""+pathFileShell+" "+pathFilePdf+" "+pathAdobeReader+"/acroread "+
			    		orientationReport+"");
			} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
		}catch(Exception e){   
			logger.error("", e);   
			return false;   
		}
		return true;
	}
	/** 29/01/2010 **/
	
	static public void prepartionEditionEntityManager() {
		if(entityManager == null ){
			entityManager =  new JPABdLgr().getEntityManager();
			flagEntityManager = true;
		}
		ImprimeObjet.listEntityManager.add(entityManager);
	}
	
	static public String getPathShellFile(){
		
		String path = null;
		Bundle bundleEditions= Platform.getBundle(Activator.getDefault().getBundle().getSymbolicName());
		URL urlReportFile;

			
		urlReportFile = FileLocator.find(bundleEditions,new Path(""),null);
		if(urlReportFile!=null){
			try {
				urlReportFile = FileLocator.toFileURL(urlReportFile);
				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
						urlReportFile.getPath(), urlReportFile.getQuery(), null);
				File reportFileEdition = new File(uriReportFile);
				path = reportFileEdition.getAbsoluteFile()+"/"+"printEdition.sh";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error("", e);
			}
		}
		
		return path;
	}
	public static void prepartionBirtViewer(){
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		//new InstanceScope().getNode(ViewerPlugin.PLUGIN_ID).put(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
	}
	
	public static void prepartionBirtReportEngine(){
		BirtUtil.startReportEngine();
	}
	
	public HashMap getAppContextEdition() {
		return appContextEdition;
	}
	
	public void setAppContextEdition(HashMap appContextEdition) {
		this.appContextEdition = appContextEdition;
	}

	public Integer getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(Integer idDocument) {
		this.idDocument = idDocument;
	}
	public String getParametreEditionIdDocument() {
		return parametreEditionIdDocument;
	}
	public void setParametreEditionIdDocument(String parametreEditionIdDocument) {
		this.parametreEditionIdDocument = parametreEditionIdDocument;
	}

	public boolean isFlagPrint() {
		return flagPrint;
	}

	public void setFlagPrint(boolean flagPrint) {
		this.flagPrint = flagPrint;
	}

	public boolean isAfficheEditionAImprimer() {
		return afficheEditionAImprimer;
	}

	public void setAfficheEditionAImprimer(boolean afficheEditionAImprimer) {
		this.afficheEditionAImprimer = afficheEditionAImprimer;
	}

	public String getPathFileAImprimer() {
		return pathFileAImprimer;
	}

	public void setPathFileAImprimer(String pathFileAImprimer) {
		this.pathFileAImprimer = pathFileAImprimer;
	}

	public String getPathAdobeReader() {
		return pathAdobeReader;
	}

	public void setPathAdobeReader(String pathAdobeReader) {
		this.pathAdobeReader = pathAdobeReader;
	}

	public String getPathFileEdition() {
		return pathFileEdition;
	}

	public void setPathFileEdition(String pathFileEdition) {
		this.pathFileEdition = pathFileEdition;
	}
}
