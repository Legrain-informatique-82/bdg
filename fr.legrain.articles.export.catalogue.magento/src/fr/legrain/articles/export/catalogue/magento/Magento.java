package fr.legrain.articles.export.catalogue.magento;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityTransaction;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import Magento.CatalogCategoryEntity;
import Magento.CatalogCategoryEntityCreate;
import Magento.CatalogCategoryTree;
import Magento.CatalogProductAttributeMediaCreateEntity;
import Magento.CatalogProductCreateEntity;
import Magento.CatalogProductImageFileEntity;
import Magento.CatalogProductTierPriceEntity;
import Magento.CustomerCustomerEntity;
import Magento.CustomerCustomerEntityToCreate;
import Magento.Mage_Api_Model_Server_V2_HandlerPortTypeProxy;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaCategorieArticle;
import fr.legrain.articles.dao.TaImageArticle;
import fr.legrain.articles.dao.TaLabelArticle;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.tiers.dao.TaTiers;

public class Magento {

	static Logger logger = Logger.getLogger(Magento.class.getName());

	private String sessionId;
	private Mage_Api_Model_Server_V2_HandlerPortTypeProxy proxy;
	
	private static final String defaultStoreViewCode = "default";
	
	private static final String ORIGINE_IMPORT = "magento";
	
	private TaArticleDAO taArticleDAO;
	private TaSynchroBoutiqueDAO taSynchroBoutiqueDAO;
	private TaSynchroBoutique taSynchroBoutique;
	
	private CatalogCategoryTree arbreCategories;

	public void initSession() {
		try {
			//String url = "http://127.0.0.1/magento/index.php/api/v2_soap?wsdl";
			//String url = "http://127.0.0.1/magento/index.php/api/";
			String url = "http://127.0.0.1/magento/index.php/api/v2_soap";

			proxy = new Mage_Api_Model_Server_V2_HandlerPortTypeProxy(url);

			sessionId = proxy.login("nicolas", "lgr00XXXX");

			System.out.println("Session: " + sessionId);
		} catch (RemoteException e) {
			logger.error("",e);
		}
	}
	
	private void initDAO() {
		taArticleDAO = new TaArticleDAO();
		taSynchroBoutiqueDAO = new TaSynchroBoutiqueDAO();
		
		taSynchroBoutique = taSynchroBoutiqueDAO.findInstance();
	}
	
	private void initArbreCategories(boolean update) throws RemoteException {
		if(arbreCategories==null || update)
			arbreCategories = proxy.catalogCategoryTree(sessionId, null, defaultStoreViewCode);
	}
	
	public CatalogCategoryEntity chercheCategorie(CatalogCategoryEntity[] rootChildren, String nom) {
		boolean trouve = false;
		CatalogCategoryEntity resultat = null;
		int i = 0;
		while(!trouve && i<rootChildren.length) {
			if(rootChildren[i].getName().equals(nom)) {
				trouve = true;
			} else {
				chercheCategorie(rootChildren[i].getChildren(),nom);
				i++;
			}
		}
		if(trouve) {
			resultat = rootChildren[i];
		}
		return resultat;
	}
	
	/**
	 * 
	 * @param nom - nom de la categorie recherchée
	 * @return - la catégorie ou null si elle n'existe pas
	 */
	public CatalogCategoryEntity chercheCategorie(String nom) {
		return chercheCategorie(arbreCategories.getChildren(), nom);
	}
	
	private void tagSynchro() throws Exception {
		EntityTransaction transaction = taSynchroBoutiqueDAO.getEntityManager().getTransaction();
		taSynchroBoutiqueDAO.begin(transaction);
		taSynchroBoutique.setDerniereSynchro(new Date());
		if((taSynchroBoutiqueDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
			taSynchroBoutique = taSynchroBoutiqueDAO.enregistrerMerge(taSynchroBoutique);
		}
		else taSynchroBoutique = taSynchroBoutiqueDAO.enregistrerMerge(taSynchroBoutique);
		taSynchroBoutiqueDAO.commit(transaction);
	}

	public void exportArticle() {
		try {
			initSession();
			initArbreCategories(true);
			initDAO();
			//export categorie - fonction OK mais comment géré catégorie avec le même nom
			//export label - rien de prévu, gestion par les templates/css ou un plugin, si plugin => accessible web service ?
			//export tva - non accessible web service => import/export CSV
			//export article - problème de maj des images
			createProduct(taArticleDAO.findByCode("ART_00001"));
			tagSynchro();
			System.out.println("Session: " + sessionId);
		} catch (RemoteException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	
	public void createCategorie(TaCategorieArticle cat) throws RemoteException {
		int idCategorieMere = 4;
		CatalogCategoryEntityCreate catalogCategoryEntityCreate = new CatalogCategoryEntityCreate();
		catalogCategoryEntityCreate.setName(cat.getCodeCategorieArticle());
		catalogCategoryEntityCreate.setUrl_key(cat.getUrlRewritingCategorieArticle());
		catalogCategoryEntityCreate.setInclude_in_menu(1);
		catalogCategoryEntityCreate.setIs_active(1);
		catalogCategoryEntityCreate.setAvailable_sort_by(new String[]{"price"});
		catalogCategoryEntityCreate.setDefault_sort_by("price");
		proxy.catalogCategoryCreate(sessionId, idCategorieMere, catalogCategoryEntityCreate, defaultStoreViewCode);
	}
	
	public void createProduct(TaArticle art) throws Exception {
		String defaultProductType = "simple";
		String defaultProductSet = "4";
		String typeMimeImageJPG = "image/jpeg";
		String typeImageMagento_Image = "image";
		String typeImageMagento_SmallImage = "small_image";
		String defaultProductStatus = "1";
		String sku = art.getCodeArticle();
		
		CatalogProductCreateEntity catalogProductCreateEntity = null;
		
		//infos générales
		catalogProductCreateEntity = new CatalogProductCreateEntity();
		catalogProductCreateEntity.setName(art.getLibellecArticle());
		catalogProductCreateEntity.setShort_description(art.getLibellelArticle());
		//catalogProductCreateEntity.setWeight("10");
		catalogProductCreateEntity.setStatus(defaultProductStatus);
		
		//prix
		if(art.getTaPrix()!=null) { //prix par defaut
			catalogProductCreateEntity.setPrice(art.getTaPrix().getPrixPrix().toString()); 
		}
		if(art.getTaPrixes()!=null) { //recherche du 2eme prix ===> prix autre conditionnement ==> colis
			CatalogProductTierPriceEntity tierPrice;
			boolean trouve = false;
			Iterator<TaPrix> ite = art.getTaPrixes().iterator();
			TaPrix prix = null;
			while(ite.hasNext() && !trouve) {
				prix = ite.next();
				if(art.getTaPrix().getIdPrix()!=prix.getIdPrix()) {
					//le 1er prix different du prix par defaut
					tierPrice = new CatalogProductTierPriceEntity();
					tierPrice.setQty(art.getTaRapportUnite().getRapport().intValue());
					tierPrice.setPrice(prix.getPrixPrix().doubleValue());
					catalogProductCreateEntity.setTier_price(new CatalogProductTierPriceEntity[]{tierPrice});
					trouve = true;
				}
			}
		}
		
		//infos web : html, url, ...
		if(art.getTaCatalogueWeb()!=null) {
			catalogProductCreateEntity.setDescription(art.getTaCatalogueWeb().getDescriptionLongueCatWeb());
//			obj.put("url-rewriting",taArticle.getTaCatalogueWeb().getUrlRewritingCatalogueWeb());
//			obj.put("catalogue",taArticle.getTaCatalogueWeb().getExportationCatalogueWeb());
//			obj.put("nouveaute",taArticle.getTaCatalogueWeb().getNouveauteCatalogueWeb());
//			obj.put("promo",taArticle.getTaCatalogueWeb().getPromotionCatalogueWeb());
//			obj.put("promo-colis",taArticle.getTaCatalogueWeb().getPromotionU2CatalogueWeb());
//			obj.put("desc-html",taArticle.getTaCatalogueWeb().getDescriptionLongueCatWeb());
//			obj.put("special",taArticle.getTaCatalogueWeb().getSpecialCatalogueWeb());
//			obj.put("expediable",taArticle.getTaCatalogueWeb().getExpediableCatalogueWeb());
		}
		
		//categories
		if(art.getTaCategorieArticles()!=null) {
			for (TaCategorieArticle taCategorieArticle : art.getTaCategorieArticles()) {
				
				CatalogCategoryEntity c = chercheCategorie(taCategorieArticle.getLibelleCategorieArticle());
				if(c!=null) { //la categorie existe, on ajoute le produit
					proxy.catalogCategoryAssignProduct(sessionId, c.getCategory_id(), sku, "1", null);
				}
//				objInterne = new LinkedHashMap<String,Object>();
//				objInterne.put("id",taCategorieArticle.getIdCategorieArticle());
//				objInterne.put("code",taCategorieArticle.getCodeCategorieArticle());
//				objInterne.put("libelle",taCategorieArticle.getLibelleCategorieArticle());
//				objInterne.put("url-rewriting",taCategorieArticle.getUrlRewritingCategorieArticle());
//				objInterne.put("description",taCategorieArticle.getDesciptionCategorieArticle());
//				objInterne.put("image",taCategorieArticle.getNomImageCategorieArticle());
//				objInterne.put("id-categorie-mere",taCategorieArticle.getCategorieMereArticle()!=null?taCategorieArticle.getCategorieMereArticle().getIdCategorieArticle():null);
			}
		}
		
		//labels
		if(art.getTaLabelArticles()!=null) {
			for (TaLabelArticle labelArticle : art.getTaLabelArticles()) {
//				objInterne = new LinkedHashMap<String,Object>();
//				objInterne.put("id",labelArticle.getIdLabelArticle());
//				objInterne.put("code",labelArticle.getCodeLabelArticle());
//				objInterne.put("libelle",labelArticle.getLibelleLabelArticle());
//				objInterne.put("description",labelArticle.getDesciptionLabelArticle());
//				objInterne.put("image",labelArticle.getNomImageLabelArticle());
			}
		}
		
		//taxes, TVA
		//catalogProductCreateEntity.setTax_class_id("");
		
		if(taSynchroBoutique.getDerniereSynchro()==null //1ere synchro
				|| art.getQuandCreeArticle().after(taSynchroBoutique.getDerniereSynchro())) {//l'article n'existe pas sur le site
			
			int newId = proxy.catalogProductCreate(sessionId, defaultProductType, defaultProductSet, sku, catalogProductCreateEntity, defaultStoreViewCode);

			//image
			if(art.getTaImageArticles()!=null) {
				for (TaImageArticle img : art.getTaImageArticles()) {
					CatalogProductAttributeMediaCreateEntity image = new CatalogProductAttributeMediaCreateEntity();
					CatalogProductImageFileEntity imageFile = new CatalogProductImageFileEntity();

					imageFile.setContent(Base64.encodeBase64String(readFile(img.getCheminImageArticle())).toString());
					imageFile.setName(img.getNomImageArticle());
					imageFile.setMime(typeMimeImageJPG);

					image.setFile(imageFile);
					image.setLabel(img.getNomImageArticle());
					image.setTypes(new String[] {typeImageMagento_Image,typeImageMagento_SmallImage});
					proxy.catalogProductAttributeMediaCreate(sessionId, sku, image, defaultStoreViewCode, null);
				}
			}
		} else { //article deja sur le site
			
			if(art.getQuandModifArticle().after(taSynchroBoutique.getDerniereSynchro())) {
				 //==> faire une maj si article modifié depuis la dernière synchro
				
				//for (int i = 0; i < cpe.length; i++) {
				//				CatalogProductCreateEntity cpce = new CatalogProductCreateEntity();
				//				cpce.setStatus("1");
				//				proxy.catalogProductUpdate(sessionId, cpe[i].getSku(), cpce,defaultStoreViewCode, null);
				//			}
				proxy.catalogProductUpdate(sessionId, sku, catalogProductCreateEntity, defaultStoreViewCode, null);
			}
		}
		
		//recherche
//		Filters f = new Filters();
//		//			AssociativeEntity ae = new AssociativeEntity("entity_id", newId+"");
//		//			f.setFilter(new AssociativeEntity[]{ae});
//		CatalogProductEntity[] cpe = proxy.catalogProductList(sessionId,f,defaultStoreViewCode);
//		System.out.println("Nb product "+cpe.length);
//		if(cpe.length>0) {
//			System.out.println("Nouveau produit "+cpe[0].getName());
//		}

		//ajout du produit dans une categorie
//		proxy.catalogCategoryAssignProduct(sessionId, 4, sku, "1", null);
	}
	
	public void createCustomer(TaTiers tiers) throws RemoteException {
		CustomerCustomerEntity[] customerCustomerEntity = proxy.customerCustomerList(sessionId, null);
		//customerCustomerEntity[0].setFirstname("name 1 "+new Date().getTime());

		/*
		 * ********************************************************************************************************
		 */
		CustomerCustomerEntityToCreate c = new CustomerCustomerEntityToCreate();
		c.setFirstname(tiers.getPrenomTiers());
		//c.
		proxy.customerCustomerUpdate(sessionId, customerCustomerEntity[0].getCustomer_id(), c);
	}

	private byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int bytes = (int) file.length();
		byte[] buffer = new byte[bytes];
		int readBytes = bis.read(buffer);
		bis.close();
		return buffer;
	}
}
