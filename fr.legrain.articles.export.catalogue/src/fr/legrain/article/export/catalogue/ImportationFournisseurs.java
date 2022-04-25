package fr.legrain.article.export.catalogue;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;

import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaCatalogueWeb;
import fr.legrain.articles.dao.TaCategorieArticle;
import fr.legrain.articles.dao.TaCategorieArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaPrixDAO;
import fr.legrain.articles.dao.TaRapportUnite;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTTvaDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.prestashop.ws.WSPrestaUtil;
import fr.legrain.prestashop.ws.entities.Addresses;
import fr.legrain.prestashop.ws.entities.Categories;
import fr.legrain.prestashop.ws.entities.Customers;
import fr.legrain.prestashop.ws.entities.Groups;
import fr.legrain.prestashop.ws.entities.PrestaConst;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEmailDAO;
import fr.legrain.tiers.dao.TaEntrepriseDAO;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTelephoneDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ImportationFournisseurs {

	static Logger logger = Logger.getLogger(ImportationFournisseurs.class.getName());
	private static FileAppender myAppender = new FileAppender();

	static {
		logger.addAppender(myAppender);
		addLog();
	}

	static public void addLog() {
		try {
			String basePath = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/";
			myAppender.setFile(basePath+"/import_articles.log");
			//myAppender.setFile(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)+"/import_tiers_commandes.log");
			myAppender.setLayout(new PatternLayout());
			myAppender.setAppend(false);
			myAppender.addFilter(new Filter() {

				@Override
				public int decide(LoggingEvent e) {
					if(e.getLoggerName().startsWith("fr.legrain.article.export")/* && (e.getLevel() == Level.INFO)*/) {
						return Filter.ACCEPT;
					} else if(e.getLoggerName().startsWith("fr.legrain.prestashop")/* && (e.getLevel() == Level.INFO)*/)
						return Filter.ACCEPT;
					else
						return Filter.DENY;
				}
			});
			myAppender.activateOptions();
			logger.debug("test");
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	//c.fourn;raison sociale;n responsable;adresse 1;adresse 2;c.postal;ville;telephone;telex/fax;telephone 2;email;n client
	private static final int POS_IMPORT_CODE_FOURNISSEUR 		= 0;
	private static final int POS_IMPORT_RAISON_SOCIALE_NOM 	= 1;
	private static final int POS_IMPORT_RESPONSABLE_CONTACT 	= 2;
	private static final int POS_IMPORT_ADRESSE_1 			= 3;
	private static final int POS_IMPORT_ADRESSE_2 			= 4;
	private static final int POS_IMPORT_CODE_POSTAL 			= 5;
	private static final int POS_IMPORT_VILLE 				= 6;
	private static final int POS_IMPORT_TELEPHONE 			= 7;
	private static final int POS_IMPORT_FAX					= 8;
	private static final int POS_IMPORT_TELEPHONE_2 			= 9;
	private static final int POS_IMPORT_EMAIL 				= 10;
	private static final int POS_IMPORT_NUM_CLI 				= 11;

	private String finDeLigne = "\r\n";
	public static final int RE_EXPORT = 1;
	public static final String SEPARATEUR = ";";
	private String messageRetour="";
	private Boolean retour=true;
	private String locationFichier="";

	private static final int COMMIT_AUTO = -1;
	private static final int COMMIT_FIN	 = 0;
	//COMMIT_AUTO auto, COMMIT_FIN commit à la fin, n (avec n>0) commit tout les n enregistrement
	//private int autoCommit = COMMIT_AUTO;
	private int autoCommit = COMMIT_FIN;

	private int nbLigneFichierArticle = 0;

	public ImportationFournisseurs() {}

	public void effaceFichierTexte(String chemin) {
		File f = new File(chemin);
		f.delete();
	}

	/**
	 * http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public int count(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		byte[] c = new byte[1024];
		int count = 0;
		int readChars = 0;
		while ((readChars = is.read(c)) != -1) {
			for (int i = 0; i < readChars; ++i) {
				if (c[i] == '\n')
					++count;
			}
		}
		return count;
	}

	public void imporFournisseurProgress(final String fichierArticleAImporter) {
		Job job = new Job("Importation des fournisseurs") {
			//		IRunnableWithProgress i = new IRunnableWithProgress() {
			//			
			//			@Override
			//			public void run(IProgressMonitor monitor) throws InvocationTargetException,
			//					InterruptedException {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					nbLigneFichierArticle = count(fichierArticleAImporter);
					monitor.beginTask("Importation des fournisseurs", nbLigneFichierArticle);
				} catch (IOException e) {
					logger.error("",e);
				}
				importFournisseur(fichierArticleAImporter,monitor);
				//				if (isModal(this)) {
				//					// The progress dialog is still open so just open the message
				//					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Importation des articles", "Importation terminée.");
				//				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setPriority(Job.SHORT);
		job.schedule(); // start as soon as poss

		//		try {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		pmd.setBlockOnOpen(true);
		//PlatformUI.getWorkbench().getProgressService().run(false, false, i);
		PlatformUI.getWorkbench().getProgressService().showInDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),job);
		//		} catch (InvocationTargetException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

	public boolean isModal(Job job) {
		Boolean isModal = (Boolean)job.getProperty(
				IProgressConstants.PROPERTY_IN_DIALOG);
		if(isModal == null) return false;
		return isModal.booleanValue();
	}

	public void importFournisseur(String fichierArticleAImporter) {
		importFournisseur(fichierArticleAImporter,null);
	}


	public void importFournisseur(String fichierArticleAImporter, IProgressMonitor monitor) {		
		try {
			///////////////////////////////////////////////////////////////////////////////////////
			int exportationAutoNouveauArticle = 1;
			int marqueNouveauAutoNouveauArticle = 1;

			String codeTypeTiersDefaut = "F";
			TaTTiers typeTiersDefaut = null;
			///////////////////////////////////////////////////////////////////////////////////////

			BufferedReader br = null;

			TaTiers tiersCourant = null;
			TaTiersDAO taTiersDAO = new TaTiersDAO();
			TaTTiersDAO taTTiersDAO = new TaTTiersDAO(taTiersDAO.getEntityManager());

			TaAdresseDAO taAdresseDAO = new TaAdresseDAO(taTiersDAO.getEntityManager());
			TaTelephoneDAO taTelephoneDAO = new TaTelephoneDAO(taTiersDAO.getEntityManager());
			TaEmailDAO taEmailDAO = new TaEmailDAO(taTiersDAO.getEntityManager());
			TaEntrepriseDAO taEntrepriseDAO = new TaEntrepriseDAO(taTiersDAO.getEntityManager());

			typeTiersDefaut = taTTiersDAO.findByCode(codeTypeTiersDefaut);

			String encoding = "ISO-8859-1";
			//				String encoding = "UTF-8";
			FileInputStream fis = new FileInputStream(fichierArticleAImporter);
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			br = new BufferedReader(isr);

			//br = new BufferedReader(new FileReader(fichierTxt));

			String ligne = br.readLine();
			String[] retour = null;
			//			String origine = "changement d'origine"; //init avec une chaine qui ne doit pas se trouver dans le fichier
			//			Compte c = null;
			//			int[] positionZoneNum = null;
			//			InfosComptaTxtEpicea infosTxt = new InfosComptaTxtEpicea();

			String idOrigine = null;
			String origineImport = "logiciel_EB";

			EntityTransaction transaction = taTiersDAO.getEntityManager().getTransaction();
			if(autoCommit==COMMIT_FIN)
				taTiersDAO.begin(transaction);

			int nbLigne = 1;
			while (ligne!=null){
				monitor.setTaskName("Importation du fournisseur "+nbLigne+"/"+nbLigneFichierArticle);
				retour = ligne.split(SEPARATEUR,13); //13 car 11 colones

				//TODO sauter la 1ère ligne ou bien l'analyser pour initialiser des variable indiquant la position des champs
				//TODO gérer la ligne vide en fin de fichier
				if(nbLigne!=1 && retour.length>1) {

					if(autoCommit==COMMIT_AUTO || (autoCommit>0 && autoCommit%nbLigne==0))
						taTiersDAO.begin(transaction);

					//en fonction de constante représentant le nom des champs
					idOrigine = retour[POS_IMPORT_CODE_FOURNISSEUR];
					boolean nouveauTiers = false;

					if(taTiersDAO.rechercheParImport(origineImport, idOrigine).size()>0) {
						tiersCourant = taTiersDAO.rechercheParImport(origineImport, idOrigine).get(0);
						nouveauTiers = false;

					} else {
						nouveauTiers = true;
						tiersCourant = new TaTiers();
					}

					if(nouveauTiers) {
						//générer un nouveau code
						// et récupérer l'id après enregistrement
						//tiersCourant.setCodeTiers(taTiersDAO.genereCode());
						tiersCourant.setCodeTiers(idOrigine);
						//validateUIField(Const.C_CODE_TIERS,tiersCourant.getCodeTiers());//permet de verrouiller le code genere
						tiersCourant.setCodeCompta(tiersCourant.getCodeTiers());
						tiersCourant.setTaTTiers(typeTiersDefaut);
						tiersCourant.setCompte("401");
						tiersCourant.setActifTiers(1);
						tiersCourant.setTtcTiers(0);

						tiersCourant.setIdImport(idOrigine);
						tiersCourant.setOrigineImport(origineImport);
					}

					tiersCourant.setNomTiers(retour[POS_IMPORT_RAISON_SOCIALE_NOM]);
					tiersCourant.setCodeCompta(tiersCourant.getCodeTiers());


					logger.info(tiersCourant.getNomTiers()+" "+tiersCourant.getPrenomTiers());
					logger.info("Nouveau : "+(nouveauTiers?"oui":"non (maj)"));
					logger.info("==============");

					if(retour[POS_IMPORT_EMAIL]!=null && !retour[POS_IMPORT_EMAIL].equals("")) {
						TaEmail email = null;
						if(nouveauTiers) {
							email = new TaEmail();
							email.setIdImport(idOrigine);
							email.setOrigineImport(origineImport);
						} else {
							email = taEmailDAO.rechercheParImport(origineImport, idOrigine).get(0);
						}
						email.setAdresseEmail(retour[POS_IMPORT_EMAIL]);

						if(nouveauTiers) {
							tiersCourant.addEmail(email);
							email.setTaTiers(tiersCourant);
						}
					}
					
					if(retour[POS_IMPORT_TELEPHONE]!=null && !retour[POS_IMPORT_TELEPHONE].equals("")) {
						TaTelephone tel = null;
						if(nouveauTiers) {
							tel = new TaTelephone();
							tel.setIdImport(idOrigine);
							tel.setOrigineImport(origineImport);
						} else {
							tel = taTelephoneDAO.rechercheParImport(origineImport, idOrigine).get(0);
						}
						if(retour[POS_IMPORT_TELEPHONE].length()>20) {
							logger.info(idOrigine+" : Champ trop long : "+retour[POS_IMPORT_TELEPHONE]);
							tel.setNumeroTelephone("err_import");
						} else {
							tel.setNumeroTelephone(retour[POS_IMPORT_TELEPHONE]);
						}

						if(nouveauTiers) {
							tiersCourant.addTelephone(tel);
							tel.setTaTiers(tiersCourant);
						}
					}


					//					if(tiers.get("phone-number")!=null) {
					//						TaTelephone tel = null;
					//						if(nouveauTiers) {
					//							tel = new TaTelephone();
					//							tel.setIdImport(idSiteWeb);
					//							tel.setOrigineImport(origineImport);
					//						} else {
					//							tel = taTelephoneDAO.rechercheParImport(origineImport, idSiteWeb).get(0);
					//						}
					//						tel.setNumeroTelephone(tiers.get("phone-number").toString());
					//
					//						if(nouveauTiers) {
					//							tiersCourant.addTelephone(tel);
					//							tel.setTaTiers(tiersCourant);
					//						}
					//					}

					if(retour[POS_IMPORT_ADRESSE_1]!=null && !retour[POS_IMPORT_ADRESSE_1].equals("")) {
						TaAdresse adresse = null;
						if(nouveauTiers) {
							adresse = new TaAdresse();
							adresse.setIdImport(idOrigine);
							adresse.setOrigineImport(origineImport);
							
							if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.tiers.preferences.PreferenceConstants.CORRESPONDANCE_ADRESSE_ADMINISTRATIF_DEFAUT)) {
								adresse.setCommAdministratifAdresse(1);
							} else {
								adresse.setCommAdministratifAdresse(0);
							}

							if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.tiers.preferences.PreferenceConstants.CORRESPONDANCE_ADRESSE_COMMERCIAL_DEFAUT)) {
								adresse.setCommCommercialAdresse(1);
							} else {
								adresse.setCommCommercialAdresse(0);
							}
						} else {
							adresse = taAdresseDAO.rechercheParImport(origineImport, idOrigine).get(0);
						}
						adresse.setAdresse1Adresse(retour[POS_IMPORT_ADRESSE_1]);
						adresse.setAdresse2Adresse(retour[POS_IMPORT_ADRESSE_2]);
						adresse.setCodepostalAdresse(retour[POS_IMPORT_CODE_POSTAL]);
						adresse.setVilleAdresse(retour[POS_IMPORT_VILLE]);
						adresse.setPaysAdresse("FRANCE");

						if(nouveauTiers) {
							tiersCourant.addAdresse(adresse);
							adresse.setTaTiers(tiersCourant);
						}
					}

				
					taTiersDAO.mergeFlush(tiersCourant);
					
					if(autoCommit==COMMIT_AUTO || (autoCommit>0 && autoCommit%nbLigne==0))
						taTiersDAO.commit(transaction);

					//transaction = null;

				}// fin if

				///////////////////////////////////////////////////////
				if(monitor!=null)
					monitor.worked(1);

				ligne = br.readLine();
				nbLigne++;
			} //fin while

			//if(autoCommit==COMMIT_FIN)
			taTiersDAO.commit(transaction); //toujours un commit à la fin : OK dans le cas de ligne non valider et dans le cas d'un seul commit à la fin
			br.close();
			transaction = null;
			logger.info("Nombre de ligne dans le fichier d'import : "+nbLigne);

		} catch(Exception e) {
			logger.error("", e);
		}
	}


	private boolean expediable(String familleOuCategorie) {
		boolean retour = false;
		if(familleOuCategorie.equals("FRLG")) {
			retour = false;
		} else if(familleOuCategorie.equals("LAIT")) {
			retour = false;
		} else if(familleOuCategorie.equals("EPIC")) {
			retour = true;
		} else {
			retour = true;
		}
		return retour;
	}

	/**
	 * Correspondance entre le code importé et le code dans le bureau de gestion
	 * @param donneesImport - code importé
	 * @return - code dans le bureau de gestion
	 */
	private String correspondanceTVA(String donneesImport) {
		/*
		V1	Ventes 5.5
		V2	Ventes 19.6
		V3	Ventes 7
		 */
		String retour = null;
		if(donneesImport.equals("5,50")) {
			retour = "V1";
		} else if(donneesImport.equals("19,60")) {
			retour = "V2";
		} else if(donneesImport.equals("7,00")) {
			retour = "V3";
		}
		return retour;
	}

	private String correspondanceCategories(String donneesImport) {
		String retour = null;
		/*
		 * TODO Remplacer par des maps ?
		 */
		if(donneesImport.equals("0")) {
			retour = "FRLG";
		} else if(donneesImport.equals("29")) {
			retour = "EPIC";
		} else if(donneesImport.equals("1728")) {
			retour = "LAIT";
		} else if(donneesImport.equals("1729")) {
			retour = "PATEC";
		} else if(donneesImport.equals("1730")) {
			retour = "SAUCE";
		} else if(donneesImport.equals("1751")) {
			retour = "EPIC";
		} else if(donneesImport.equals("1757")) {
			retour = "EPIC";
		} else if(donneesImport.equals("1758")) {
			retour = "EPIC";
		} else if(donneesImport.equals("1799")) {
			retour = "EPIC";
		} else if(donneesImport.equals("1831")) {
			retour = "EPIC";
		} else if(donneesImport.equals("1799")) {
			retour = "EPIC";
		} else {
			retour = "EPIC";
		}
		return retour;
	}

	public TaCategorieArticle chercheCategorieRecursif(String code, TaCategorieArticle categorieDefaut) {
		TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO();
		TaCategorieArticle cat = null;
		try {
			cat = taCategorieArticleDAO.findByCode(code);
			return cat;
		} catch(Exception e) {
			if(code.length() > 2) {
				String codeCategorieMere = code.substring(0, code.length()-1);
				chercheCategorieRecursif(codeCategorieMere,categorieDefaut);
			} else {
				return categorieDefaut;
			}
		}
		return cat;
	}

	/**
	 * Format du fichier texte : 
	 * <li> separateur = ;</li>
	 * <li> ligne contentant les titres, puis ligne avec les valeurs, entre 2 series de titre
	 * les lignes de valeurs commencent par la même valeur, ex: <br>
	 * <b>titre1;titre2;titre3</b><br>
	 * valeur1;valeur2;valeur3<br>
	 * valeur1;valeur4;valeur5<br>
	 * <b>titre4;titre5;titre6</b><br>
	 * valeur15;valeur2;valeur3<br>
	 * valeur15;valeur5;valeur6<br>
	 * valeur15;valeur7;valeur8<br>
	 * </li>
	 * <li>ligne classique : origine;compte;debit;credit</li>
	 */
	public void readTxt(String fichierTxt) {
		//		BufferedReader br = null;
		//		try {
		//			
		//			String encoding = "ISO-8859-1";
		//			FileInputStream fis = new FileInputStream(fichierTxt);
		//			InputStreamReader isr = new InputStreamReader(fis, encoding);
		//			br = new BufferedReader(isr);
		//	        
		//			//br = new BufferedReader(new FileReader(fichierTxt));
		//			
		//			String ligne = br.readLine();
		//    		String[] retour = null;
		//    		String origine = "changement d'origine"; //init avec une chaine qui ne doit pas se trouver dans le fichier
		//    		Compte c = null;
		//    		int[] positionZoneNum = null;
		//    		InfosComptaTxtEpicea infosTxt = new InfosComptaTxtEpicea();
		//    		while (ligne!=null){
		//    			
		//    			//si le dernier caractère est SEPARATEUR, la dernière valeur est nulle
		//    			if(ligne.endsWith(SEPARATEUR))
		//    				ligne+="0";
		//    			
		//    			retour = ligne.split(SEPARATEUR);
		//    			
		////    			for (int i = 0; i < retour.length; i++) {
		////					System.out.println(i+" - "+retour[i]);
		////				}
		//
		//    			if(origine.equalsIgnoreCase(retour[0])) {
		//    				
		//////    				Remplacement des chaines vide dans les zones numériques (zones devant contenir le montant d'un compte)
		////					positionZoneNum = new int[]{2,3,4,5,6,7};
		////					for (int i = 0; i < positionZoneNum.length; i++) {
		////						if(LibChaine.empty(retour[positionZoneNum[i]]))
		////							retour[positionZoneNum[i]] = "0";
		////					}    				
		//    				
		//    				if(origine.equals(origineBilan)) {
		//    					donneesAnalyseEco.getListeInfosCompta().add(new InfosCompta(infosTxt.readLigneTxtEpicea(retour)));
		//    				} else if(origine.equals(origineAnalytique)) {
		//    					donneesAnalyseEco.getListeInfosAnalytique().add(new InfosAnalytique(
		//    							retour[0],retour[1],retour[2],retour[3],retour[4],retour[5],
		//    							retour[6],retour[7],retour[8],retour[9],retour[10],retour[11],retour[12],retour[13]
		//    							));
		//    				} else if(origine.equals(origineStocks)) {
		//    					donneesAnalyseEco.getListeInfosStocks().add(new InfosStocks(
		//    							retour[0],retour[1],retour[2],retour[3],retour[4],retour[5],
		//    							retour[6]));
		//    				} else if(origine.equals(origineAcquisitions)) {
		//    					positionZoneNum = new int[]{2,3};
		//    					for (int i = 0; i < positionZoneNum.length; i++) {
		//    						if(LibChaine.empty(retour[positionZoneNum[i]]))
		//    							retour[positionZoneNum[i]] = "0";
		//    					}
		//    					donneesAnalyseEco.getListeAcquisition().add(new Acquisition(new CompteSimple(
		//    							retour[0],retour[1],retour[1],
		//    							Double.parseDouble(retour[2]),
		//    							Double.parseDouble(retour[3]))));
		//    				} else if(origine.equals(origineGrdLivreQte)) {
		//    					donneesAnalyseEco.getListeQte().add(new InfosGrdLivreQte(
		//    							retour[0],retour[1],retour[2]));
		//    				} else if(origine.equals(origineInfosDossier)) {
		//    					donneesAnalyseEco.getListeInfosDossier().add(new InfoComplement(
		//    							retour[1],retour[2],null));
		//    				} else {
		//    					logger.error("format de ligne incorrect pour l'analyse economique: "+ligne);
		//					}
		//    				
		//    			} else {
		//    				origine = retour[0];
		//    			}
		//    			ligne = br.readLine();
		//    		}
		//			br.close();
		//			
		//		} catch (Exception e) {
		//			logger.error("",e);
		//		} 
	}

	public String getMessageRetour() {
		return messageRetour;
	}

	public void setMessageRetour(String messageRetour) {
		this.messageRetour = messageRetour;
	}

	public String getLocationFichier() {
		return locationFichier;
	}

	public void setLocationFichier(String locationFichier) {
		this.locationFichier = locationFichier;
	}

	public Boolean getRetour() {
		return retour;
	}

	public void setRetour(Boolean retour) {
		this.retour = retour;
	}
}
