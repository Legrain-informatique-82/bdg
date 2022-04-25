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
import java.text.SimpleDateFormat;
import java.util.Date;

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
import fr.legrain.prestashop.ws.entities.Categories;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ImportationArticle {

	static Logger logger = Logger.getLogger(ImportationArticle.class.getName());
	private static FileAppender myAppender = new FileAppender();
	
	static {
		logger.addAppender(myAppender);
		addLog();
	}

	static public void addLog() {
		try {
			String basePath = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/";
			
			String formatDate = "yyyyMMdd_HHmmss";
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			String date = sdf.format(new Date());
			
			myAppender.setFile(basePath+"/import_articles_"+date+".log");
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
		} catch (Exception e) {
			logger.error("", e);
		}

	}
	
	//C.ARTICLE;DESIGNATION;C.FA;C.FOUR;C.RA;C.MA;TV;P.VENTE;QTE KG/L;PRX KG/L
	private static final int POS_IMPORT_CODE_ARTICLE 	= 0;
	private static final int POS_IMPORT_LIBELLE 		= 1;
	private static final int POS_IMPORT_CODE_FAMILLE 	= 2;
	private static final int POS_IMPORT_CODE_FRS 		= 3;
	private static final int POS_IMPORT_CODE_RA 		= 4;
	private static final int POS_IMPORT_CODE_MA 		= 5;
	private static final int POS_IMPORT_TVA 			= 7;
	private static final int POS_IMPORT_PRIX_VENTE 		= 6;
	private static final int POS_IMPORT_QTE_POIDS 		= 8;
	private static final int POS_IMPORT_QTE_PRIX 		= 9;

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

	public ImportationArticle() {}

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

	public void imporArticleProgress(final String fichierArticleAImporter) {
		Job job = new Job("Importation des articles") {
//		IRunnableWithProgress i = new IRunnableWithProgress() {
//			
//			@Override
//			public void run(IProgressMonitor monitor) throws InvocationTargetException,
//					InterruptedException {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					nbLigneFichierArticle = count(fichierArticleAImporter);
					monitor.beginTask("Importation des articles", nbLigneFichierArticle);
				} catch (IOException e) {
					logger.error("",e);
				}
				imporArticle(fichierArticleAImporter,monitor);
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
			//ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			//pmd.setBlockOnOpen(true);
		
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
	
	public void imporArticle(String fichierArticleAImporter) {
		imporArticle(fichierArticleAImporter,null);
	}

	public void imporArticle(String fichierArticleAImporter, IProgressMonitor monitor) {		
		try {
			///////////////////////////////////////////////////////////////////////////////////////
			int exportationAutoNouveauArticle = Activator.getDefault().getPreferenceStoreProject().getInt(PreferenceConstantsProject.EB_IMPORTATION_ESPACE3000_ARTICLE_EXPORTABLE_DEFAUT);
			int marqueNouveauAutoNouveauArticle = 1;
			boolean urlRewintingAutoFromLibelleCourt = true;
			boolean descriptionHTMLAutoFromLibelleLong = true;
			String compteParDefautArticle = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.EB_COMPTE_COMPTA_DEFAUT);;
			//correspondance Famille <=> Catégorie ====> fonction
			//correspondance TVA <=> TVA BDG ====> fonction
			//Famille ou catégorie expédiable ====> fonction
			String typeTVADefaut = "E";
			String unite1Defaut = "UNITE";
			String unite2Defaut = "COLIS";
			String codeCategorieDefaut = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.EB_CATEGORIE_DEFAUT);
			TaCategorieArticle categorieDefaut = null;
			float txRemiseU2 = Activator.getDefault().getPreferenceStoreProject().getFloat(PreferenceConstantsProject.RAPPORT_PRIX_U_COLIS);
			int nbDecimaleDefautRapportUnite = 2;
			BigDecimal rapportUniteDefaut = new BigDecimal(Activator.getDefault().getPreferenceStoreProject().getFloat(PreferenceConstantsProject.NB_UNITE_DANS_COLIS_DEFAUT));
			///////////////////////////////////////////////////////////////////////////////////////

			
			
			BufferedReader br = null;

			TaArticle taArticleCourant = null;
			TaArticleDAO taArticleDAO = new TaArticleDAO();
			TaPrixDAO taPrixDAO = new TaPrixDAO(taArticleDAO.getEntityManager());
			TaUniteDAO taUniteDAO = new TaUniteDAO(taArticleDAO.getEntityManager());
			TaTvaDAO taTvaDAO = new TaTvaDAO(taArticleDAO.getEntityManager());
			TaTTvaDAO taTTvaDAO = new TaTTvaDAO(taArticleDAO.getEntityManager());
			TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO(taArticleDAO.getEntityManager());
			TaTiersDAO taTiersDAO = new TaTiersDAO(taArticleDAO.getEntityManager());
			
			categorieDefaut = taCategorieArticleDAO.findByCode(codeCategorieDefaut);

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
			
			EntityTransaction transaction = taArticleDAO.getEntityManager().getTransaction();
			if(autoCommit==COMMIT_FIN)
				taArticleDAO.begin(transaction);
			
			int nbLigne = 1;
			logger.info("Importation des articles");
			while (ligne!=null){
				monitor.setTaskName("Importation de l'article "+nbLigne+"/"+nbLigneFichierArticle);
				retour = ligne.split(SEPARATEUR);
				
				//TODO sauter la 1ère ligne ou bien l'analyser pour initialiser des variable indiquant la position des champs
				//TODO gérer la ligne vide en fin de fichier
				if(nbLigne!=1 && retour.length>1) {
					
					if(autoCommit==COMMIT_AUTO || (autoCommit>0 && autoCommit%nbLigne==0))
						taArticleDAO.begin(transaction);
					
					//en fonction de constante représentant le nom des champs
					idOrigine = retour[POS_IMPORT_CODE_ARTICLE];
					boolean nouvelArticle = false;

					if(taArticleDAO.rechercheParImport(origineImport, idOrigine).size()>0) {
						logger.info("Mise à jour de l'article "+idOrigine);
						taArticleCourant = taArticleDAO.rechercheParImport(origineImport, idOrigine).get(0);
						nouvelArticle = false;
					} else {
						nouvelArticle = true;
						taArticleCourant = new TaArticle();
						logger.info("Création de l'article "+idOrigine);
					}

					if(nouvelArticle) {
						//générer un nouveau code
						// et récupérer l'id après enregistrement
						taArticleCourant.setCodeArticle(idOrigine);
						taArticleCourant.setActif(1);
						//					taArticleCourant.setCodeArticle(taArticleDAO.genereCode());
						//					validateUIField(Const.C_CODE_ARTICLE,taArticleCourant.getCodeArticle());//permet de verrouiller le code genere
						taArticleCourant.setStockMinArticle(new BigDecimal(-1));

						taArticleCourant.setIdImport(idOrigine);
						taArticleCourant.setOrigineImport(origineImport);
						if(taArticleCourant.getTaCatalogueWeb()==null) {
							taArticleCourant.setTaCatalogueWeb(new TaCatalogueWeb());
						}
						
						taArticleCourant.getTaCatalogueWeb().setExportationCatalogueWeb(exportationAutoNouveauArticle);
						taArticleCourant.getTaCatalogueWeb().setNouveauteCatalogueWeb(marqueNouveauAutoNouveauArticle);
						if(compteParDefautArticle!=null && !compteParDefautArticle.equals(""))
							taArticleCourant.setNumcptArticle(compteParDefautArticle);
					}

					taArticleCourant.setLibellecArticle(retour[POS_IMPORT_LIBELLE]);
					if(urlRewintingAutoFromLibelleCourt) {
						//URL rewriting par defaut ?
						taArticleCourant.getTaCatalogueWeb().setUrlRewritingCatalogueWeb(LibChaine.toUrlRewriting(taArticleCourant.getLibellecArticle()));
					}
					taArticleCourant.setLibellelArticle(retour[POS_IMPORT_LIBELLE]);
					if(descriptionHTMLAutoFromLibelleLong) {
						//Desciption HTML par defaut ?
						taArticleCourant.getTaCatalogueWeb().setDescriptionLongueCatWeb(taArticleCourant.getLibellecArticle());
					}

					if(retour[POS_IMPORT_TVA]!=null) {
						//gérer le code TVA   ==> 1 = 5.5  et 2 = 19.6 (à vérifier)
						TaTva tva = null;
						TaTTva ttva = null;
						String codeBdg = correspondanceTVA(retour[POS_IMPORT_TVA]);
						if(codeBdg!=null) {
							tva = taTvaDAO.findByCode(codeBdg);
							taArticleCourant.setTaTva(tva);
						} else {
							logger.info("Pas de TVA pour : ["+retour[POS_IMPORT_TVA]+"]"+taArticleCourant.getCodeArticle()+" "+taArticleCourant.getLibellecArticle());
						}
						
						if(typeTVADefaut!=null) {
							//affecter un Type TVA par defaut ?
							ttva = taTTvaDAO.findByCode(typeTVADefaut);
							if(ttva!=null) taArticleCourant.setTaTTva(ttva);
						}
					}

					if(retour[POS_IMPORT_PRIX_VENTE]!=null) {
						//gérer les prix et leur mise à jour
						TaPrix prix = null;
						TaUnite unite1 = null;
						if(nouvelArticle) {
							prix = new TaPrix();
							prix.setIdImport(idOrigine);
							prix.setOrigineImport(origineImport);
						} else {
							prix = taPrixDAO.rechercheParImport(origineImport, idOrigine).get(0);
						}
						
						prix.setPrixPrix(LibConversion.stringFRToBigDecimal(retour[POS_IMPORT_PRIX_VENTE]));
						
						if(unite1Defaut!=null) {
							//choisir une unité pour ce prix ?
							unite1 = taUniteDAO.findByCode(unite1Defaut);
							if(unite1!=null) prix.setTaUnite(unite1);
						}

						if(nouvelArticle) {
							taArticleCourant.addPrix(prix);
							prix.setTaArticle(taArticleCourant);
							taArticleCourant.setTaPrix(prix);
						}
						taArticleCourant.afterPost();
					}

					if(retour[POS_IMPORT_CODE_FAMILLE]!=null) {
						TaCategorieArticle cat = null;
						//String codeBdg = correspondanceCategories(retour[POS_IMPORT_CODE_FAMILLE]);
						//if(codeBdg!=null) {
							try {
								//cat = taCategorieArticleDAO.findByCode(codeBdg);
								//cat = taCategorieArticleDAO.findByCode(retour[POS_IMPORT_CODE_FAMILLE]);
								
								cat = chercheCategorieRecursif(retour[POS_IMPORT_CODE_FAMILLE],categorieDefaut,1);
								logger.info(idOrigine+" : categorie origine : "+retour[POS_IMPORT_CODE_FAMILLE]+" attribué "+cat.getCodeCategorieArticle());
								
								taArticleCourant.addCategorie(cat);
								//vérifier que la catégorie n'apparait pas plusieurs fois
								
								taArticleCourant.getTaCatalogueWeb().setExpediableCatalogueWeb(expediable(cat.getCodeCategorieArticle())?1:0);
							} catch(Exception e) {
								logger.info("Pas de catégorie pour : ["+retour[POS_IMPORT_CODE_FAMILLE]+"]"+taArticleCourant.getCodeArticle()+" "+taArticleCourant.getLibellecArticle());
							}
						//}
					}

					if(retour[POS_IMPORT_CODE_FRS]!=null) {
						//gérer les fournisseurs des articles
						try {
							TaTiers frs = taTiersDAO.findByCode(retour[POS_IMPORT_CODE_FRS]);
							taArticleCourant.addFournisseur(frs);
						} catch(Exception e) {
							logger.info(taArticleCourant.getCodeArticle()+" : Pas de fournisseur trouvé, code FRS rechercher : "+retour[POS_IMPORT_CODE_FRS]);
						}
					}

					if(unite2Defaut!=null) {
						//gérer le ratio pour le prix  au colis
						
						if(!(taArticleCourant.getTaRapportUnites().size()>1)) {
							//un seul rapport d'unite possible pour EchappeeBio
							TaUnite unite2 = null;
							TaRapportUnite rapport = null;
							unite2 = taUniteDAO.findByCode(unite2Defaut);
							if(unite2!=null) {
								rapport = new TaRapportUnite();
								rapport.setTaUnite1(taArticleCourant.getTaPrix().getTaUnite());
								rapport.setTaUnite2(unite2);
								rapport.setSens(0);
								rapport.setNbDecimal(nbDecimaleDefautRapportUnite);
								rapport.setRapport(rapportUniteDefaut);
								
								taArticleCourant.addRapportUnite(rapport);
								rapport.setTaArticle(taArticleCourant);
								taArticleCourant.setTaRapportUnite(rapport);
								
								//Création du prix pour l'unité 2
								//ex: 15% de remise => U x nbUColis - 15% de rem
								TaPrix prix2 = new TaPrix();
								prix2.setTaUnite(unite2);
								
								BigDecimal prixU2 = taArticleCourant.getTaPrix().getPrixPrix().multiply(rapport.getRapport(),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP);
								prix2.setPrixPrix(prixU2.subtract(prixU2.multiply(new BigDecimal(txRemiseU2),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP)));
								
								taArticleCourant.addPrix(prix2);
								prix2.setTaArticle(taArticleCourant);
							}
						}
					}
					
					//(gérer les unités)
					//gérer les poids

					///////////////////////////////////////////////////////
					
//					if((taArticleDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
//						/*taArticleCourant=*/taArticleDAO.enregistrerMerge(taArticleCourant);
//					}
//					else /*taArticleCourant=*/taArticleDAO.enregistrerMerge(taArticleCourant);
					
					taArticleDAO.mergeFlush(taArticleCourant);
					//taArticleDAO.enregistrerMerge(taArticleCourant);
					
					if(autoCommit==COMMIT_AUTO || (autoCommit>0 && autoCommit%nbLigne==0))
						taArticleDAO.commit(transaction);

					
					///////////////////////////////////////////////////////
				}//fin if
				
				if(monitor!=null)
					monitor.worked(1);
				
				ligne = br.readLine();
				nbLigne++;
			} //fin while
			
			//if(autoCommit==COMMIT_FIN)
				taArticleDAO.commit(transaction); //toujours un commit à la fin : OK dans le cas de ligne non valider et dans le cas d'un seul commit à la fin
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
	
	public TaCategorieArticle chercheCategorieRecursif(String code, TaCategorieArticle categorieDefaut, int niveau) {
		TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO();
		TaCategorieArticle cat = null;
		try {
			cat = taCategorieArticleDAO.findByCode(code);
			System.out.println("***** ok "+code);
			return cat;
		} catch(Exception e) {
			System.out.println("***** non trouve "+code);
			if(code.length() > 2) {
				String codeCategorieMere = code.substring(0, code.length()-niveau+1);
				for(int i=1; i<niveau;i++) {
					codeCategorieMere+="0";
				}
				System.out.println("***** recherche de "+codeCategorieMere);
				return chercheCategorieRecursif(codeCategorieMere,categorieDefaut, ++niveau);
			} else {
				return categorieDefaut;
			}
		}
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
