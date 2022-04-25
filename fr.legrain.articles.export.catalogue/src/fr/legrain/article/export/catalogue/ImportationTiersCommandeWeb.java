package fr.legrain.article.export.catalogue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaCategorieArticleDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaInfosBoncde;
import fr.legrain.documents.dao.TaInfosBoncdeDAO;
import fr.legrain.documents.dao.TaLBoncde;
import fr.legrain.documents.dao.TaTLigneDAO;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEmailDAO;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTelephoneDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ImportationTiersCommandeWeb implements IImportationTiersCommandeWeb {

	static Logger logger = Logger.getLogger(ImportationTiersCommandeWeb.class.getName());
	private static FileAppender myAppender = new FileAppender();
	
	static {
		logger.addAppender(myAppender);
	}

	public ImportationTiersCommandeWeb() {}

	public void effaceFichierTexte(String chemin) {
		File f = new File(chemin);
		f.delete();
	}

	public void addLog() {
		try {
			myAppender.setFile(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)+"/import_tiers_commandes.log");
			myAppender.setLayout(new PatternLayout());
			myAppender.setAppend(false);
			myAppender.addFilter(new Filter() {

				@Override
				public int decide(LoggingEvent e) {
					if(e.getLoggerName().startsWith("fr.legrain.article.export") && (e.getLevel() == Level.INFO))
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

	/**
	 * Lecture du fichier JSON préalablement récupérer sur le FTP et création/mise à jour des tiers et des commandes
	 * du BDG en fonction de son contenu.
	 */
	public ResultatImportation importWeb() {
		addLog();
		String fichier = null;
		if(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)!=null)
			fichier = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)
			+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_IMPORT);

//		TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO();
		ResultatImportation res = new ResultatImportation();

		try {
			//String encoding = "ISO-8859-1";
			String encoding = "UTF-8";
			FileInputStream fis = new FileInputStream(fichier);
			InputStreamReader isr = new InputStreamReader(fis, encoding);

			JSONParser parser = new JSONParser();
			ContainerFactory containerFactory = new ContainerFactory(){
				public List creatArrayContainer() {
					return new LinkedList();
				}

				public Map createObjectContainer() {
					return new LinkedHashMap();
				}

			};


			try{
				JSONObject json = (JSONObject)parser.parse(isr);

				Map<String,String> listeNouveauTiersBDG = new HashMap<String,String>();
				TaTiers tiersCourant = null;
				TaTiersDAO taTiersDAO = new TaTiersDAO();
				TaAdresseDAO taAdresseDAO = new TaAdresseDAO(taTiersDAO.getEntityManager());
				TaTelephoneDAO taTelephoneDAO = new TaTelephoneDAO(taTiersDAO.getEntityManager());
				TaEmailDAO taEmailDAO = new TaEmailDAO(taTiersDAO.getEntityManager());
				TaTTiersDAO taTTiersDAO = new TaTTiersDAO(taTiersDAO.getEntityManager());

				String origineImport = "site_web";

				/*
				 * Importation des tiers
				 */
				JSONArray listeTiers = (JSONArray)json.get("tiers");
				Iterator iterTiers = listeTiers.iterator();
				String idSiteWeb = null;

				logger.info("=========================================================================================");
				logger.info("Importation des tiers : "+listeTiers.size());
				logger.info("=========================================================================================");

				while(iterTiers.hasNext()) {
					JSONObject tiers = (JSONObject)iterTiers.next();

					idSiteWeb = tiers.get("id").toString();
					boolean nouveauTiers = false;

					//if(tiers.get("bdg-id")!=null) {
					//	tiersCourant = taTiersDAO.findById(LibConversion.stringToInteger(tiers.get("bdg-id").toString()));
					if(taTiersDAO.rechercheParImport(origineImport, idSiteWeb).size()>0) {
						tiersCourant = taTiersDAO.rechercheParImport(origineImport, idSiteWeb).get(0);
						nouveauTiers = false;

					} else {
						nouveauTiers = true;
						tiersCourant = new TaTiers();
					}

					if(nouveauTiers) {
						//générer un nouveau code
						// et récupérer l'id après enregistrement
						tiersCourant.setCodeTiers(taTiersDAO.genereCode());
						//validateUIField(Const.C_CODE_TIERS,tiersCourant.getCodeTiers());//permet de verrouiller le code genere
						tiersCourant.setCodeCompta(tiersCourant.getCodeTiers());
						tiersCourant.setTaTTiers(taTTiersDAO.findByCode("C"));
						tiersCourant.setCompte("411");
						tiersCourant.setActifTiers(1);
						tiersCourant.setTtcTiers(0);

						tiersCourant.setIdImport(idSiteWeb);
						tiersCourant.setOrigineImport(origineImport);
					}

					tiersCourant.setNomTiers(tiers.get("name").toString());
					tiersCourant.setPrenomTiers(tiers.get("firstname").toString());
					tiersCourant.setCodeCompta(tiersCourant.getCodeTiers());
					tiersCourant.setTtcTiers(LibConversion.stringToInteger(tiers.get("ttc").toString()));
					
					logger.info(tiersCourant.getNomTiers()+" "+tiersCourant.getPrenomTiers());
					logger.info("Nouveau : "+(nouveauTiers?"oui":"non (maj)"));
					logger.info("==============");

					if(tiers.get("email")!=null) {
						TaEmail email = null;
						if(nouveauTiers) {
							email = new TaEmail();
							email.setIdImport(idSiteWeb);
							email.setOrigineImport(origineImport);
						} else {
							email = taEmailDAO.rechercheParImport(origineImport, idSiteWeb).get(0);
						}
						email.setAdresseEmail(tiers.get("email").toString());

						if(nouveauTiers) {
							tiersCourant.addEmail(email);
							email.setTaTiers(tiersCourant);
						}
					}


					if(tiers.get("phone-number")!=null) {
						TaTelephone tel = null;
						if(nouveauTiers) {
							tel = new TaTelephone();
							tel.setIdImport(idSiteWeb);
							tel.setOrigineImport(origineImport);
						} else {
							tel = taTelephoneDAO.rechercheParImport(origineImport, idSiteWeb).get(0);
						}
						tel.setNumeroTelephone(tiers.get("phone-number").toString());

						if(nouveauTiers) {
							tiersCourant.addTelephone(tel);
							tel.setTaTiers(tiersCourant);
						}
					}

					tiers.get("birthday-date-fr");

					JSONArray listeAdresseFacturation = (JSONArray)tiers.get("adress-fact");
					if(listeAdresseFacturation.size()>0) {
						//TaTAdr typeAdrFact = taTAdrDAO.findByCode("FACT");
						Iterator iterAdresse = listeAdresseFacturation.iterator();
						TaAdresse adr2 = null;
						boolean nouvelleAdresse = false;
						while(iterAdresse.hasNext()){
							JSONObject adrFact = (JSONObject)iterAdresse.next();
							adrFact.get("name-adress");

							if(!nouveauTiers && taAdresseDAO.rechercheParImport(origineImport, adrFact.get("id-adress").toString()).size()>0) {
								adr2 = taAdresseDAO.rechercheParImport(origineImport, adrFact.get("id-adress").toString()).get(0);
								nouvelleAdresse = false;
							} else {
								nouvelleAdresse = true;
								adr2 = new TaAdresse();
							}

							if(nouvelleAdresse) {
								adr2.setOrigineImport(origineImport);
								if(adrFact.get("id-adress")!=null)
									adr2.setIdImport(adrFact.get("id-adress").toString());
							}

							if(adrFact.get("address")!=null)
								adr2.setAdresse1Adresse(adrFact.get("address").toString());
							if(adrFact.get("city")!=null)
								adr2.setVilleAdresse(adrFact.get("city").toString());
							if(adrFact.get("zip-code")!=null)
								adr2.setCodepostalAdresse(adrFact.get("zip-code").toString());

							adr2.setPaysAdresse("FRANCE");

							//adr2.setTaTAdr(typeAdrFact);
							if(nouvelleAdresse) {
								tiersCourant.addAdresse(adr2);
								adr2.setTaTiers(tiersCourant);
							}
						}
					}

					EntityTransaction transaction = taTiersDAO.getEntityManager().getTransaction();
					taTiersDAO.begin(transaction);
					if((taTiersDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
						tiersCourant=taTiersDAO.enregistrerMerge(tiersCourant);
					}
					else tiersCourant=taTiersDAO.enregistrerMerge(tiersCourant);
					taTiersDAO.commit(transaction);

					if(tiers.get("bdg-id")!=null) {
						listeNouveauTiersBDG.put(LibConversion.integerToString(tiersCourant.getIdTiers()),idSiteWeb);
					}

					transaction = null;
				} //fin traitements des tiers

				/*
				 * Importation des commandes
				 */
				Map<String,String> listeNouvelleCdeBDG = new HashMap<String,String>();
				List<TaBoncde> listeNouvelleCdeBDGRetour = new LinkedList<TaBoncde>();
				List<TaTiers> listeTiersBDGRetour = new LinkedList<TaTiers>();
				TaBoncde cdeCourante = null;
				TaLBoncde ligneCourante = null;
				TaInfosBoncde infosCdeCourante= null;
				TaBoncdeDAO taBoncdeDAO = new TaBoncdeDAO(taTiersDAO.getEntityManager());
				TaInfosBoncdeDAO taInfosBoncdeDAO = new TaInfosBoncdeDAO(taTiersDAO.getEntityManager());
				TaArticleDAO taArticleDAO = new TaArticleDAO(taTiersDAO.getEntityManager());
				TaTLigneDAO taTLigneDAO = new TaTLigneDAO(taTiersDAO.getEntityManager());


				JSONArray listeCommandes = (JSONArray)json.get("commandes");
				Iterator iterCmd = listeCommandes.iterator();
				String idTiersCommande = null;
				
				logger.info("=========================================================================================");
				logger.info("Importation des commandes : "+listeCommandes.size());
				logger.info("=========================================================================================");
				
				//génération des commandes en "mode TTC ou HT"
				boolean modeTTC = Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.GENERATION_COMMANDE_IMPORT_EN_TTC);

				while(iterCmd.hasNext()) {
					JSONObject commande = (JSONObject)iterCmd.next();

					idSiteWeb = commande.get("id-command").toString();

					cdeCourante = new TaBoncde();
					cdeCourante.setCodeDocument(taBoncdeDAO.genereCode());
					//validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteBoncde) selectedBoncde).getCodeDocument());//permet de verrouiller le code genere
					//cdeCourante.setCommentaire(boncdePlugin.getDefault().getPreferenceStore().getString(PreferenceConstantsProject.COMMENTAIRE));

					//verifier les dates par rapport aux dates exo ?

					infosCdeCourante = new TaInfosBoncde();

					idTiersCommande = commande.get("id_user").toString();
					if(taTiersDAO.rechercheParImport(origineImport, idTiersCommande).size()>0) {
						cdeCourante.setTaTiers(taTiersDAO.rechercheParImport(origineImport, idTiersCommande).get(0));
					}

					cdeCourante.setLibelleDocument(commande.get("command-libel").toString());

					cdeCourante.setDateDocument(LibDate.stringToDate(commande.get("date-of-command-fr").toString()));
					
					if(modeTTC) {
						//cdeCourante.setTtc(1);
						cdeCourante.setTtc(cdeCourante.getTaTiers().getTtcTiers());
						modeTTC= LibConversion.intToBoolean(cdeCourante.getTtc());
					}

					infosCdeCourante.setAdresse1(commande.get("billing-address").toString());
					infosCdeCourante.setVille(commande.get("billing-city").toString());
					infosCdeCourante.setCodepostal(commande.get("billing-zip_code").toString());
					infosCdeCourante.setAdresse1Liv(commande.get("delivery-address").toString());
					infosCdeCourante.setVilleLiv(commande.get("delivery-city").toString());
					infosCdeCourante.setCodepostalLiv(commande.get("delivery-zip_code").toString());

					infosCdeCourante.setCompte(cdeCourante.getTaTiers().getCompte());
					infosCdeCourante.setCodeCompta(cdeCourante.getTaTiers().getCodeCompta());
					
					//commande.get("total-ht").toString();
					//commande.get("total-tva").toString();
					//commande.get("user-name").toString();
					//commande.get("user-firstname").toString();

					JSONArray listeLignesCmd = (JSONArray)commande.get("lines");
					if(listeLignesCmd.size()>0) {
						Iterator iterLigneCmd = listeLignesCmd.iterator();
						TaArticle articleCourant = null;
						//Ajout des lignes de commandes
						while(iterLigneCmd.hasNext()){
							JSONObject ligne = (JSONObject)iterLigneCmd.next();

							ligneCourante = new TaLBoncde(true);

							ligneCourante.setTaTLigne(taTLigneDAO.findByCode(Const.C_CODE_T_LIGNE_H));
							ligneCourante.setTaDocument(cdeCourante);
							if(ligne.get("id-product")!=null) {
								
								if(!(ligne.get("id-product") instanceof Boolean)) {

									if(ligne.get("id-product")!=null) {
										articleCourant = taArticleDAO.findById(LibConversion.stringToInteger(ligne.get("id-product").toString()));
										ligneCourante.setTaArticle(articleCourant);
									}
									
									if(ligne.get("unit-price_ht")!=null) {
										if(modeTTC) {
											BigDecimal prixUnitaireTTC = 
												LibConversion.stringToBigDecimal(ligne.get("unit-price_ht").toString()).multiply(
														new BigDecimal(1).add(
																articleCourant.getTaTva().getTauxTva()
																.divide(new BigDecimal(100))
														),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP);
									
									
											ligneCourante.setPrixULDocument(prixUnitaireTTC);
										} else {
											ligneCourante.setPrixULDocument(LibConversion.stringToBigDecimal(ligne.get("unit-price_ht").toString()));
										}
									}

									if(ligne.get("quantity")!=null) {
										ligneCourante.setQteLDocument(LibConversion.stringToBigDecimal(ligne.get("quantity").toString()));
									}

									cdeCourante.addLigne(ligneCourante);

//									if(ligne.get("total-ht")!=null)
//										ligneCourante.setMtHtLDocument(ligne.get("total-ht").toString());
//									if(ligne.get("total-tva")!=null)
//										ligneCourante.setVilleAdresse(ligne.get("total-tva").toString());
//									if(ligne.get("unit-product")!=null)
//										ligneCourante.setCodepostalAdresse(ligne.get("unit-product").toString());
//									if(ligne.get("product-name")!=null)
//										ligneCourante.setCodepostalAdresse(ligne.get("product-name").toString());
//									if(ligne.get("id-TVA")!=null)
//										ligneCourante.setCodepostalAdresse(ligne.get("id-TVA").toString());
								}
							} else {
								logger.info("Article de la boutique web (ID = "+ligne.get("id-product")+") introuvable dans le BDG");
							}

						}
					}
					//Ajout des lignes de commentaires
					String codeArticleFDP = "FDP"; // faire une constante ou une préférence (non modifiable)
					TaArticleDAO daoArticle = new TaArticleDAO();
					TaTvaDAO daoTVA = new TaTvaDAO();
					TaArticle articleFraisDePort = null;
					articleFraisDePort = daoArticle.findByCode(codeArticleFDP);
					ligneCourante = new TaLBoncde(true);
					ligneCourante.setTaDocument(cdeCourante);
					if(articleFraisDePort!=null) {
						ligneCourante.setTaTLigne(taTLigneDAO.findByCode(Const.C_CODE_T_LIGNE_H));
						
						TaTva tva = daoTVA.findByTauxOrCreate(commande.get("carrier_tax_rate").toString());
	
						articleFraisDePort.setTaTva(tva);
						ligneCourante.setTaArticle(articleFraisDePort);
						//ligneCourante.setTauxTvaLDocument(LibConversion.stringToBigDecimal(commande.get("carrier_tax_rate").toString()));
						if(modeTTC) {
							ligneCourante.setPrixULDocument(LibConversion.stringToBigDecimal(commande.get("total_shipping").toString()));
						} else {
							BigDecimal prixUnitaireHT = 
							LibConversion.stringToBigDecimal(commande.get("total_shipping").toString()).divide(
								new BigDecimal(1).add(
										LibConversion.stringToBigDecimal(commande.get("carrier_tax_rate").toString())
										.divide(new BigDecimal(100))
										),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP);
						
//							ligneCourante.setLibLDocument("Total frais de port "+"ttc=>"+commande.get("total_shipping")+" tx=>"+commande.get("carrier_tax_rate"));
						
							ligneCourante.setPrixULDocument(prixUnitaireHT);
						}
						ligneCourante.setQteLDocument(new BigDecimal(1));
					} else {
						//pas d'article frais de port, affichage sous forme de commentaire
						ligneCourante.setTaTLigne(taTLigneDAO.findByCode(Const.C_CODE_T_LIGNE_C));
						ligneCourante.setLibLDocument("Total frais de port TTC : "+commande.get("total_shipping").toString());
					}
					cdeCourante.addLigne(ligneCourante);
					
					logger.info("Commande : "+idSiteWeb+", "+cdeCourante.getTaTiers().getNomTiers()+" "+cdeCourante.getTaTiers().getPrenomTiers()
							+", "+listeLignesCmd.size()+" lignes");
					logger.info("=====================");

					cdeCourante.setTaInfosDocument(infosCdeCourante);
					infosCdeCourante.setTaDocument(cdeCourante);
					cdeCourante.calculDateEcheance(null, null);

					EntityTransaction transaction = taBoncdeDAO.getEntityManager().getTransaction();
					taBoncdeDAO.begin(transaction);
					if((taBoncdeDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
						cdeCourante=taBoncdeDAO.enregistrerMerge(cdeCourante);
					}
					else cdeCourante=taBoncdeDAO.enregistrerMerge(cdeCourante);
					taBoncdeDAO.commit(transaction);

					transaction = null;

					listeNouvelleCdeBDG.put(LibConversion.integerToString(cdeCourante.getIdDocument()),idSiteWeb);
					listeNouveauTiersBDG.put(LibConversion.integerToString(cdeCourante.getTaTiers().getIdTiers()),cdeCourante.getTaTiers().getIdImport());
					
					listeNouvelleCdeBDGRetour.add(cdeCourante);
					listeTiersBDGRetour.add(cdeCourante.getTaTiers());

				}// fin traitements commandes

				retourImportation(listeNouveauTiersBDG,listeNouvelleCdeBDG);
				
				res.setListeCommande(listeNouvelleCdeBDGRetour);
				res.setListeTiers(listeTiersBDGRetour);
				
				return res;

			}
			catch(ParseException pe){
				logger.error("", pe);
			}

		} catch(Exception e) {
			logger.error("", e);
		}
		
		return res;
	}

	/**
	 * Création du fichier JSON contenant les correspondance d'ID entre le BDG et la boutique.<br>
	 * Ce fichier pourra ensuite être envoyé sur le serveur.
	 * @param listeNouveauTiersBDG - les tiers nouvellement créés dans le bdg
	 * @param listeNouvelleCdeBDG - les commandes nouvellement créées dans le bdg
	 */
	public void retourImportation(Map<String,String> listeNouveauTiersBDG, Map<String,String> listeNouvelleCdeBDG) {
		String fichierRetourImportation = null;

		String pathRepExportWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
		File repExportWebTmp = new File(pathRepExportWebTmp);
		if(repExportWebTmp.exists()) {
			repExportWebTmp.delete();
		}
		repExportWebTmp.mkdirs();

		fichierRetourImportation = pathRepExportWebTmp
		+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_RETOUR_IMPORT);

		try {
			FileWriter fw = null;
			BufferedWriter bw = null;

			fw = new FileWriter(fichierRetourImportation);
			File exportFile = new File(fichierRetourImportation);
			bw = new BufferedWriter(fw);

			/////////////////////////////////////////////////////////////////
			Map<String,Object> objetGlobal = new LinkedHashMap<String,Object>();

			Map<String,Object> obj;
			Map<String,Object> objInterne;
			String jsonText = null;

			JSONArray arrayRetourTiers = new JSONArray();
			for (String idBdg : listeNouveauTiersBDG.keySet()) {
				obj = new LinkedHashMap<String,Object>();
				obj.put("id",listeNouveauTiersBDG.get(idBdg));
				obj.put("id-bdg",idBdg);
				arrayRetourTiers.add(obj);

			}
			objetGlobal.put("retour-tiers", arrayRetourTiers);

			JSONArray arrayRetourCmd = new JSONArray();
			for (String idBdg : listeNouvelleCdeBDG.keySet()) {
				obj = new LinkedHashMap<String,Object>();
				obj.put("id",listeNouvelleCdeBDG.get(idBdg));
				obj.put("id-bdg",idBdg);
				arrayRetourCmd.add(obj);
			}
			objetGlobal.put("retour-commande", arrayRetourCmd);

			StringWriter out = new StringWriter();
			JSONValue.writeJSONString(objetGlobal, out);
			jsonText = out.toString();
			fw.write(jsonText);

			/////////////////////////////////////////////////////////////////
			fw.close();

		} catch(Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * Appel par HTTP de la page sur le seveur qui va déclencher l'exportation des commandes et des tiers du site.<br>
	 * Suite à cet appel, il doit être possible de récupérer par FTP le fichier JSON contenant ces données.
	 */
	public void declencheExportSite() {
		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH);
		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE);
		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_MAJ_BDG);

		UtilHTTP.post(urlString, authTokenName, authTokenValue);
	}

	/**
	 * Appel par HTTP de la page sur le seveur qui va déclencher la prise en compte par le site des nouvelles commandes enregistrées par le bdg.<br>
	 * Avant d'appeler cette méthode il faut s'assurer que toutes les informations (fichiers JSON), ont été envoyer sur le serveur.
	 */
	public void declencheMAJSite() {
		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH);
		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE);
		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_RETOUR_BDG);

		UtilHTTP.post(urlString, authTokenName, authTokenValue);
	}
	
	/**
	 * Récupération par FTP du fichier JSON contenant les commandes et les tiers. 
	 */
	public void recuperationFTPTiersCommandeWeb() {
		FtpUtil ftpUtil = new FtpUtil(); 
		if(ftpUtil.connectServer(
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT),
				LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_EXPORT)),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_EXPORT),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_EXPORT))) {

			String pathRepWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
			String fichierImport = pathRepWebTmp
			+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_IMPORT);

			//création du répertoire de travail s'il n'existe pas
			new File(pathRepWebTmp).mkdirs();

			logger.debug("Import data ...");
			ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_DATA));
			try {
				ftpUtil.downloadFile(
						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_IMPORT),
						fichierImport
				);
			} catch (IOException e) {
				logger.error("",e);
			}

			logger.debug("FERMETURE CNX FTP");
			ftpUtil.closeServer();

		} else {
			logger.error("Problème de connection");
		}
	}
	
	/**
	 * Envoi par FTP du fichier JSON contenant les correspondance entre les nouveaux ID bdg et ceux du site.
	 */
	public void transfertMAJSite() {
		FtpUtil ftpUtil = new FtpUtil(); 
		if(ftpUtil.connectServer(
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT),
				LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_EXPORT)),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_EXPORT),
				Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_EXPORT))) {

			String pathRepWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
			String fichierRetourDonnees = pathRepWebTmp
			+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_RETOUR_IMPORT);

			ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE);

			logger.debug("Export retour data ...");
			ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_DATA));
			try {
				ftpUtil.uploadFile(fichierRetourDonnees);
			} catch (IOException e) {
				logger.error("",e);;
			}

			logger.debug("FERMETURE CNX FTP");
			ftpUtil.closeServer();

		} else {
			logger.error("Problème de connection");
		}
	}

	@Override
	public void transfert(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
	}
}
