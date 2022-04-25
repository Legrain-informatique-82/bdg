package fr.legrain.article.export.catalogue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.PlatformUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaCategorieArticle;
import fr.legrain.articles.dao.TaCategorieArticleDAO;
import fr.legrain.articles.dao.TaImageArticle;
import fr.legrain.articles.dao.TaLabelArticle;
import fr.legrain.articles.dao.TaLabelArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.preferences.UtilPreferenceImage;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.UtilImage;
import fr.legrain.lib.data.LibConversion;

public class ExportationArticle implements IExportationArticle {

	static Logger logger = Logger.getLogger(ExportationArticle.class.getName());

	public ExportationArticle() {}

	public void effaceFichierTexte(String chemin) {
		File f = new File(chemin);
		f.delete();
	}
	
	/**
	 * Création du fichier JSON contenant toutes les informations relatives aux articles, catégories et labels.<br>
	 * Le fichier est placé dans le répertoire temporaire.<br>
	 * Les images des articles, catégories et labels sont également copiées dans le répertoire temporaire en attente de leur transfert
	 * par FTP sur le serveur.
	 */
	public ResultatExportation exportationCatalogueWeb() {
		String fichierExport = null;
		UtilPreferenceImage utilPreferenceImage = new UtilPreferenceImage();
		ResultatExportation resultat = new ResultatExportation();
		
		String pathRepExportWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
		File repExportWebTmp = new File(pathRepExportWebTmp);
		if(repExportWebTmp.exists()) {
			repExportWebTmp.delete();
		}
		repExportWebTmp.mkdirs();

		fichierExport = pathRepExportWebTmp
							+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_EXPORT);
		
		TaSynchroBoutiqueDAO taSynchroBoutiqueDAO = new TaSynchroBoutiqueDAO();
		TaSynchroBoutique taSynchroBoutique = taSynchroBoutiqueDAO.findInstance();
		
		TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO();
		List<TaCategorieArticle> listeCategorie = null;
		
		TaLabelArticleDAO taLabelArticleDAO = new TaLabelArticleDAO();
		List<TaLabelArticle> listeLabel = null;
		
		TaArticleDAO taArticleDAO = new TaArticleDAO();
		List<TaArticle> listeArticle = null;
		
		TaTvaDAO taTvaDAO = new TaTvaDAO();
		List<TaTva> listeTva = taTvaDAO.selectAll();
		
		if(taSynchroBoutique==null || taSynchroBoutique.getDerniereSynchro()==null) {
			listeArticle = taArticleDAO.selectAll();
			listeCategorie = taCategorieArticleDAO.selectAll();
			listeLabel = taLabelArticleDAO.selectAll();
		} else {
			logger.info("Sélection de tous les articles créés ou modifiés depuis le : "+taSynchroBoutique.getDerniereSynchro());
			listeArticle = taArticleDAO.findByNewOrUpdatedAfter(taSynchroBoutique.getDerniereSynchro());
			logger.info("Nombre d'articles à créer/mettre à jour : "+listeArticle.size());
			
			logger.info("Sélection de toutes les catégories d'article créés ou modifiés depuis le : "+taSynchroBoutique.getDerniereSynchro());
			listeCategorie = taCategorieArticleDAO.findByNewOrUpdatedAfter(taSynchroBoutique.getDerniereSynchro());
			logger.info("Nombre de catégrories à créer/mettre à jour : "+listeCategorie.size());
			
			logger.info("Sélection de tous les labels d'articles créés ou modifiés depuis le : "+taSynchroBoutique.getDerniereSynchro());
			listeLabel = taLabelArticleDAO.findByNewOrUpdatedAfter(taSynchroBoutique.getDerniereSynchro());
			logger.info("Nombre de label à créer/mettre à jour : "+listeLabel.size());
		}
		
		File repImageCategorie = new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE));
		File repImageLabel = new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL));
		File repImageArticles = new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE));
		
//		boolean retailleImg = fr.legrain.articles.ArticlesPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.articles.preferences.PreferenceConstants.RETAILLE_IMAGE_TROP_GROSSE_EXPORT);
//		int longueurMaxImage = fr.legrain.articles.ArticlesPlugin.getDefault().getPreferenceStore().getInt(fr.legrain.articles.preferences.PreferenceConstants.LONGUEUR_MAX_IMAGE);
//		int hauteurMaxImage = fr.legrain.articles.ArticlesPlugin.getDefault().getPreferenceStore().getInt(fr.legrain.articles.preferences.PreferenceConstants.HAUTEUR_MAX_IMAGE);
//		int poidsMaxImageKo = fr.legrain.articles.ArticlesPlugin.getDefault().getPreferenceStore().getInt(fr.legrain.articles.preferences.PreferenceConstants.POIDS_MAX_IMAGE);
//		UtilImage utilImage = new UtilImage();
		
		repImageCategorie.mkdirs();
		repImageLabel.mkdirs();
		repImageArticles.mkdirs();
		
		String encoding = "UTF-8";
		//String encoding = "ISO-8859-1";
		
		try {
			BufferedWriter bw = null;
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichierExport),encoding));
			
			/////////////////////////////////////////////////////////////////
			Map<String,Object> objetGlobal = new LinkedHashMap<String,Object>();
			
			Map<String,Object> obj;
			Map<String,Object> objInterne;
			String jsonText = null;
			
			JSONArray arrayCategorie = new JSONArray();
			for (TaCategorieArticle taCategorieArticle : listeCategorie) {
				obj = new LinkedHashMap<String,Object>();
				obj.put("id",taCategorieArticle.getIdCategorieArticle());
				obj.put("code",taCategorieArticle.getCodeCategorieArticle());
				obj.put("libelle",taCategorieArticle.getLibelleCategorieArticle());
				obj.put("url-rewriting",taCategorieArticle.getUrlRewritingCategorieArticle());
				obj.put("description",taCategorieArticle.getDesciptionCategorieArticle());
				obj.put("image",taCategorieArticle.getNomImageCategorieArticle());
				obj.put("id-categorie-mere",taCategorieArticle.getCategorieMereArticle()!=null?taCategorieArticle.getCategorieMereArticle().getIdCategorieArticle():null);
				arrayCategorie.add(obj);
				if(taCategorieArticle.getCheminImageCategorieArticle()!=null && !taCategorieArticle.getCheminImageCategorieArticle().equals("")) {
					FileUtils.copyFile(
							new File(taCategorieArticle.getCheminImageCategorieArticle()), 
							new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE)+"/"+taCategorieArticle.getNomImageCategorieArticle()));
				}

			}
			objetGlobal.put("categories", arrayCategorie);
			
			JSONArray arrayLabel = new JSONArray();
			for (TaLabelArticle taLabelArticle : listeLabel) {
				obj = new LinkedHashMap<String,Object>();
				obj.put("id",taLabelArticle.getIdLabelArticle());
				obj.put("code",taLabelArticle.getCodeLabelArticle());
				obj.put("libelle",taLabelArticle.getLibelleLabelArticle());
				obj.put("description",taLabelArticle.getDesciptionLabelArticle());
				obj.put("image",taLabelArticle.getNomImageLabelArticle());
				arrayLabel.add(obj);
				if(taLabelArticle.getCheminImageLabelArticle()!=null  && !taLabelArticle.getCheminImageLabelArticle().equals("")) {
					FileUtils.copyFile(
							new File(taLabelArticle.getCheminImageLabelArticle()), 
							new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL)+"/"+taLabelArticle.getNomImageLabelArticle()));
				}
			}
			objetGlobal.put("labels", arrayLabel);
			
			JSONArray arrayTva = new JSONArray();
			for (TaTva taTva : listeTva) {
				obj = new LinkedHashMap<String,Object>();
				obj.put("id",taTva.getIdTva());
				obj.put("code",taTva.getCodeTva());
				obj.put("libelle",taTva.getLibelleTva());
				obj.put("num-cpt",taTva.getNumcptTva());
				obj.put("taux",taTva.getTauxTva());
				arrayTva.add(obj);
			}
			objetGlobal.put("tva", arrayTva);
			
			JSONArray arrayArticle = new JSONArray();
			for (TaArticle taArticle : listeArticle) {
				obj = new LinkedHashMap<String,Object>();
				obj.put("id",taArticle.getIdArticle());
				obj.put("code",taArticle.getCodeArticle());
				obj.put("libelle-court",taArticle.getLibellecArticle());
				obj.put("libelle-long",taArticle.getLibellelArticle());
				obj.put("famille",taArticle.getTaFamille()!=null?taArticle.getTaFamille().getIdFamille():null);
				obj.put("tva",taArticle.getTaTva()!=null?taArticle.getTaTva().getIdTva():null);
				obj.put("hauteur",taArticle.getHauteur());
				obj.put("longueur",taArticle.getLongueur());
				obj.put("largeur",taArticle.getLargeur());
				obj.put("poids",taArticle.getPoids());
				if(taArticle.getTaPrix()!=null) { //prix par defaut ===> prix unitaire
					obj.put("prix-ht",taArticle.getTaPrix().getPrixPrix());
					obj.put("prix-ttc",taArticle.getTaPrix().getPrixttcPrix());
//					if(taArticle.getTaPrix().getTaUnite()!=null) {
//						obj.put("hauteur-colis",taArticle.getTaPrix().getTaUnite().getHauteur());
//						obj.put("longueur-colis",taArticle.getTaPrix().getTaUnite().getLongueur());
//						obj.put("largeur-colis",taArticle.getTaPrix().getTaUnite().getLargeur());
//						obj.put("poids-colis",taArticle.getTaPrix().getTaUnite().getPoids());
//					}
				}
				if(taArticle.getTaPrixes()!=null) { //recherche du 2eme prix ===> prix autre conditionnement ==> colis
					boolean trouve = false;
						Iterator<TaPrix> ite = taArticle.getTaPrixes().iterator();
						TaPrix prix = null;
						while(ite.hasNext() && !trouve) {
							prix = ite.next();
							if(taArticle.getTaPrix().getIdPrix()!=prix.getIdPrix()) {
								//le 1er prix different du prix par defaut
								obj.put("prix-ht-colis",prix.getPrixPrix());
								obj.put("prix-ttc-colis",prix.getPrixttcPrix());
//								obj.put("hauteur-colis",taArticle.getHauteur());
//								obj.put("longueur-colis",taArticle.getLongueur());
//								obj.put("largeur-colis",taArticle.getLargeur());
//								obj.put("poids-colis",taArticle.getPoids());
								
								if(prix.getTaUnite()!=null) {
									obj.put("hauteur-colis",prix.getTaUnite().getHauteur());
									obj.put("longueur-colis",prix.getTaUnite().getLongueur());
									obj.put("largeur-colis",prix.getTaUnite().getLargeur());
									obj.put("poids-colis",prix.getTaUnite().getPoids());
								}
								
								trouve = true;
							}
					}
				}
//				if(taArticle.getTaConditionnementArticle()!=null) { //recherche du 2eme prix ===> prix autre conditionnement ==> colis
//					//if(taArticle.getTaConditionnementArticle.getTaPrix().getIdPrix()!=prix.getIdPrix()) {
////						obj.put("prix-ht-colis",prix.getPrixPrix());
////						obj.put("prix-ttc-colis",prix.getPrixttcPrix());
//						obj.put("hauteur-colis",taArticle.getTaConditionnementArticle().getHauteur());
//						obj.put("longueur-colis",taArticle.getTaConditionnementArticle().getLongueur());
//						obj.put("largeur-colis",taArticle.getTaConditionnementArticle().getLargeur());
//						obj.put("poids-colis",taArticle.getTaConditionnementArticle().getPoids());
//					//}
//				}
				if(taArticle.getTaRapportUnite()!=null) {
					obj.put("qte-colis",taArticle.getTaRapportUnite().getRapport());
				}
				//obj.put("image",taArticle.getTaImageArticle()!=null?taArticle.getTaImageArticle().getNomImageArticle():null);
				if(taArticle.getTaImageArticles()!=null) {
					JSONArray arrayImagesArticle = new JSONArray();
					TaImageArticle imageRetaille = null;
					String cheminImageAEnovyer = null;
					for (TaImageArticle image : taArticle.getTaImageArticles()) {
						if(image.getImageOrigine()==null) { //ce n'est pas une image retaillé
							objInterne = new LinkedHashMap<String,Object>();
							objInterne.put("defaut",image.getDefautImageArticle()); //on garde la propriété "image par défaut" de l'image originale
							if(!image.getTaImagesGenere().isEmpty()) {
								imageRetaille = image.getTaImagesGenere().iterator().next();
								objInterne.put("image",imageRetaille.getNomImageArticle());
								cheminImageAEnovyer = utilPreferenceImage.cheminCompletImageArticle(imageRetaille,true);
							} else {
								//normalement l'image est deja assez petite et n'avais pas besoin d'êtreretaillé, faire une vérif ?
								objInterne.put("image",image.getNomImageArticle());
								cheminImageAEnovyer = utilPreferenceImage.cheminCompletImageArticle(image);
							}
							FileUtils.copyFile(
									new File(cheminImageAEnovyer), 
									new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE)+"/"+objInterne.get("image")));
							arrayImagesArticle.add(objInterne);
						}
						
					}
					obj.put("images-article",arrayImagesArticle);
				}
				if(taArticle.getTaCatalogueWeb()!=null) {
					obj.put("url-rewriting",taArticle.getTaCatalogueWeb().getUrlRewritingCatalogueWeb());
					obj.put("catalogue",taArticle.getTaCatalogueWeb().getExportationCatalogueWeb());
					obj.put("nouveaute",taArticle.getTaCatalogueWeb().getNouveauteCatalogueWeb());
					obj.put("promo",taArticle.getTaCatalogueWeb().getPromotionCatalogueWeb());
					obj.put("promo-colis",taArticle.getTaCatalogueWeb().getPromotionU2CatalogueWeb());
					obj.put("desc-html",taArticle.getTaCatalogueWeb().getDescriptionLongueCatWeb());
					obj.put("special",taArticle.getTaCatalogueWeb().getSpecialCatalogueWeb());
					obj.put("expediable",taArticle.getTaCatalogueWeb().getExpediableCatalogueWeb());
				}
				if(taArticle.getTaLabelArticles()!=null) {
					JSONArray arrayLabelArticle = new JSONArray();
					for (TaLabelArticle labelArticle : taArticle.getTaLabelArticles()) {
						objInterne = new LinkedHashMap<String,Object>();
						objInterne.put("id",labelArticle.getIdLabelArticle());
						objInterne.put("code",labelArticle.getCodeLabelArticle());
						objInterne.put("libelle",labelArticle.getLibelleLabelArticle());
						objInterne.put("description",labelArticle.getDesciptionLabelArticle());
						objInterne.put("image",labelArticle.getNomImageLabelArticle());
						arrayLabelArticle.add(objInterne);
					}
					obj.put("labels-article",arrayLabelArticle);
				}
				if(taArticle.getTaCategorieArticles()!=null) {
					JSONArray arrayCategorieArticle = new JSONArray();
					for (TaCategorieArticle taCategorieArticle : taArticle.getTaCategorieArticles()) {
						objInterne = new LinkedHashMap<String,Object>();
						objInterne.put("id",taCategorieArticle.getIdCategorieArticle());
						objInterne.put("code",taCategorieArticle.getCodeCategorieArticle());
						objInterne.put("libelle",taCategorieArticle.getLibelleCategorieArticle());
						objInterne.put("url-rewriting",taCategorieArticle.getUrlRewritingCategorieArticle());
						objInterne.put("description",taCategorieArticle.getDesciptionCategorieArticle());
						objInterne.put("image",taCategorieArticle.getNomImageCategorieArticle());
						objInterne.put("id-categorie-mere",taCategorieArticle.getCategorieMereArticle()!=null?taCategorieArticle.getCategorieMereArticle().getIdCategorieArticle():null);
						arrayCategorieArticle.add(objInterne);
					}
					obj.put("categories-article",arrayCategorieArticle);
				}
				arrayArticle.add(obj);
				
//				if(taArticle.getTaImageArticles()!=null) {
//					ImageData imageData = null;
//					String fichierGenere = null;
//					for (TaImageArticle image : taArticle.getTaImageArticles()) {
//						if(image!=null  && !image.getCheminImageArticle().equals("")) {
////							FileUtils.copyFile(
////								new File(utilPreferenceImage.cheminCompletImageArticle(image)), 
////								new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE)+"/"+image.getNomImageArticle()));
//							
////							if(retailleImg) {
////								//Chercher dans le "cache" s'il n'y a pas déjà une image retaillé pour cette image
////								
////								//Création d'une image plus petite pour la boutique						
////								imageData = new ImageData(image.getCheminImageArticle());
////								if(new File(image.getCheminImageArticle()).length() > poidsMaxImageKo*1024
////										|| imageData.height > hauteurMaxImage
////										|| imageData.width > longueurMaxImage) {
////									//fichier trop gros en poids ou dimensions de l'image trop grandes
////									
////									//fichierGenere = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/th_"+new File(image.getCheminImageArticle()).getName(); 
////									fichierGenere = Const.C_CHEMIN_REP_DOSSIER_COMPLET+"/"+Const.FOLDER_IMAGES+"/"+Const.FOLDER_IMAGES_ARTICLES+"/th_"+new File(image.getCheminImageArticle()).getName();
////									if(imageData.height > hauteurMaxImage){
////										utilImage.scaleImage(image.getCheminImageArticle(), 
////												fichierGenere,
////												PlatformUI.getWorkbench().getDisplay(), hauteurMaxImage, 0);
////									} else if(imageData.width > longueurMaxImage){
////										utilImage.scaleImage(image.getCheminImageArticle(), 
////												fichierGenere,
////												PlatformUI.getWorkbench().getDisplay(), 0, longueurMaxImage);
////									}
////								}
//////								util.scaleImage(image.getCheminImageArticle(), 
//////										Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/th_"+new File(image.getCheminImageArticle()).getName(),
//////										PlatformUI.getWorkbench().getDisplay(), 0.5);
////							}
//						}
//					}
//				}
			}
			objetGlobal.put("articles", arrayArticle);
			
			
			StringWriter out = new StringWriter();
			JSONValue.writeJSONString(objetGlobal, out);
			jsonText = out.toString();
			bw.write(jsonText);

			/////////////////////////////////////////////////////////////////

			
			bw.close();
			
			resultat.setListeArticleEnvoyes(listeArticle);
			
		} catch(Exception e) {
			logger.error("", e);
		}
		return resultat;
	}
	
	/**
	 * Appel par HTTP de la page sur le seveur qui va déclencher la mise à jour de la boutique.<br>
	 * Avant d'appeler cette méthode il faut s'assurer que toutes les informations (fichiers JSON et images), ont été envoyer sur le serveur.
	 */
	public void declencheMAJSite() {
		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH);
		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_TOKEN_AUTH_VALUE);
		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_BOUTIQUE)+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL_HTTP_MAJ_CATALOGUE);

		UtilHTTP.post(urlString, authTokenName, authTokenValue);
	}
	
	/**
	 * Transfert du fichier JSON contentant les informations sur les articles et les images associés sur le serveur.<br>
	 * Transfert par FTP.
	 */
	public void transfert(final IProgressMonitor monitor) {
		try {
			FtpUtil ftpUtil = new FtpUtil();

			ftpUtil.setCopyStreamListener(new CopyStreamListener(){
				public void bytesTransferred(CopyStreamEvent arg0) {}
				public void bytesTransferred(long arg0, int arg1, long arg2) {
					if(monitor!=null)
						monitor.worked(arg1);
				}
			});

			if(ftpUtil.connectServer(
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.HOSTNAME_FTP_EXPORT),
					LibConversion.stringToInteger(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_FTP_EXPORT)),
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_FTP_EXPORT),
					Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_FTP_EXPORT))) {

				String pathRepExportWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
				String fichierExport = pathRepExportWebTmp
				+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_EXPORT);

				ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE);

				logger.debug("Export data ...");
				ftpUtil.createDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_DATA));
				ftpUtil.changeDirectory(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_DATA));
				ftpUtil.uploadFile(fichierExport);

				logger.debug("Export img categorie ...");
				ftpUtil.changeToRootDirectory();
				ftpUtil.uploadDirectory(
						pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE),
						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE));

				logger.debug("Export img label ...");
				ftpUtil.changeToRootDirectory();
				ftpUtil.uploadDirectory(
						pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL),
						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL));

				logger.debug("Export img article ...");
				ftpUtil.changeToRootDirectory();
				ftpUtil.uploadDirectory(
						pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE),
						Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE));


				logger.debug("FERMETURE CNX FTP");
				ftpUtil.closeServer();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void exportWS() {
		// TODO Auto-generated method stub
		
	}

}
