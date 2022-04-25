package fr.legrain.prestashop.ws;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.core.runtime.IProgressMonitor;

import fr.legrain.abonnement.dao.TaAbonnement;
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
import fr.legrain.boutique.dao.TaCorrespondanceIDBoutique;
import fr.legrain.boutique.dao.TaCorrespondanceIDBoutiqueDAO;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaInfosBoncde;
import fr.legrain.documents.dao.TaInfosBoncdeDAO;
import fr.legrain.documents.dao.TaLBoncde;
import fr.legrain.documents.dao.TaTLigneDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.prestashop.ws.entities.Addresses;
import fr.legrain.prestashop.ws.entities.AddressesList;
import fr.legrain.prestashop.ws.entities.AddressesListValue;
import fr.legrain.prestashop.ws.entities.Categories;
import fr.legrain.prestashop.ws.entities.Configurations;
import fr.legrain.prestashop.ws.entities.ConfigurationsList;
import fr.legrain.prestashop.ws.entities.ConfigurationsListValue;
import fr.legrain.prestashop.ws.entities.Customers;
import fr.legrain.prestashop.ws.entities.CustomersList;
import fr.legrain.prestashop.ws.entities.CustomersListValue;
import fr.legrain.prestashop.ws.entities.Groups;
import fr.legrain.prestashop.ws.entities.Images;
import fr.legrain.prestashop.ws.entities.Language;
import fr.legrain.prestashop.ws.entities.LanguagesList;
import fr.legrain.prestashop.ws.entities.OrderList;
import fr.legrain.prestashop.ws.entities.OrderListValue;
import fr.legrain.prestashop.ws.entities.OrderRow;
import fr.legrain.prestashop.ws.entities.Orders;
import fr.legrain.prestashop.ws.entities.PrestaConst;
import fr.legrain.prestashop.ws.entities.ProductAssociationCategory;
import fr.legrain.prestashop.ws.entities.ProductAssociationFeature;
import fr.legrain.prestashop.ws.entities.ProductAssociationImage;
import fr.legrain.prestashop.ws.entities.ProductFeature;
import fr.legrain.prestashop.ws.entities.ProductFeatureValues;
import fr.legrain.prestashop.ws.entities.ProductFeatures;
import fr.legrain.prestashop.ws.entities.ProductOptionValues;
import fr.legrain.prestashop.ws.entities.ProductOptionValuesList;
import fr.legrain.prestashop.ws.entities.ProductOptions;
import fr.legrain.prestashop.ws.entities.ProductOptionsList;
import fr.legrain.prestashop.ws.entities.Products;
import fr.legrain.prestashop.ws.entities.ProductsCategoryAssociationValue;
import fr.legrain.prestashop.ws.entities.ProductsListValue;
import fr.legrain.prestashop.ws.entities.SpecificPriceList;
import fr.legrain.prestashop.ws.entities.SpecificPrices;
import fr.legrain.prestashop.ws.entities.SpecificPricesListValue;
import fr.legrain.prestashop.ws.entities.StockAvailable;
import fr.legrain.prestashop.ws.entities.TaxList;
import fr.legrain.prestashop.ws.entities.TaxListValue;
import fr.legrain.prestashop.ws.entities.Taxes;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEmailDAO;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTelephoneDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.preferences.PreferenceConstants;

/*
  	login : julien@legrain.fr
	pass  : pwdlegrain

	install 1.5.4 => mettre en commentaire la ligne 50 de classes/ConfigurationTest.php (concernant les sessions)
	base : dev2_pageweb
	user : lgr2
	pass : legrain

	curl 
	-X POST 
	-u 'NAEVYYHRN00WH0SS0G87RDE550OL9V92:' 
	-d xml="<?xml version="1.0" encoding="UTF-8"?><prestashop><out_of_stock>0</out_of_stock><price>1.0</price><quantity>0</quantity><meta_description/><meta_keywords/><meta_title/><link_rewrite><language id="1">produit_test</language></link_rewrite><name><language id="1">Produit test</language></name><available_now/><available_later/><description/><description_short/><associations><categories node_type="category"/><images node_type="image"/><combinations node_type="combinations"/><product_option_values node_type="product_option_values"/><product_features node_type="product_feature"/></associations></prestashop>" 
	http://dev2.pageweb.fr/api

	config/config.inc.php
	//@ini_set('display_errors', 'on');
    //define('_PS_DEBUG_SQL_', true);
 */
public class WSPrestashop {

	//	private String baseURI = "http://dev2.pageweb.fr/api";
	//	private String cle = "commande";
	//	private String password = "";
	//	private String hostName = "dev2.pageweb.fr";
	
	private float reductionColis = 0f;

	private boolean utiliseSecondPrix = true;
	private boolean utiliseDeclinaison = false;
	private UtilPreferenceImage utilPreferenceImage = new UtilPreferenceImage();
	
	private TaCorrespondanceIDBoutiqueDAO taCorrespondanceIDBoutiqueDAO = new TaCorrespondanceIDBoutiqueDAO();
	
	//private int idCategorieMereDefaut = 1; //1 => root
	private int idCategorieMereDefaut = 2; //2 => Accueil
	
	private int quantiteDefautArticle;

	//id_tva.txt	=> Correspondance entre les code TVA du site et celle du BDG {"4":2,"5":1}
	//private Map<String,String> mapIdTVA = new HashMap<String,String>();
	private List<TaCorrespondanceIDBoutique> listeIdTVA = null;
	//private ListeID listeIdTVA = new ListeID();

	//id_categorie.txt	=> Correspondance entre les catégories du site et celle du BDG
	private List<TaCorrespondanceIDBoutique> listeIdCategorie = null;
	//private ListeID listeIdCategorie = new ListeID();

	//id_article.txt	=> Correspondance entre les artilces du site et ceux du BDG
	private List<TaCorrespondanceIDBoutique> listeIdArticle = null;
	//private ListeID listeIdArticle = new ListeID();

	//id_commandes.txt	=> Correspondance entre les commandes du site et celle du BDG
	private List<TaCorrespondanceIDBoutique> listeIdCommande = null;
	//private ListeID listeIdCommande = new ListeID();

	//id_image_art.txt	=> Correspondance entre les images du site et celle du BDG
	private List<TaCorrespondanceIDBoutique> listeIdImageArticle = null;
	//private ListeID listeIdImageArticle = new ListeID();

	//id_tiers.txt		=> Correspondance entre les tiers du site et ceux du BDG
	private List<TaCorrespondanceIDBoutique> listeIdTiers = null;
	//private ListeID listeIdTiers = new ListeID();

	//id_label.txt		=> Correspondance entre les labels du site et ceux du BDG
	private List<TaCorrespondanceIDBoutique> listeIdLabel = null;
	//private ListeID listeIdLabel = new ListeID();

	private int featureLabelId = 0;

	private Marshaller marshallerListeID = null;

//	private File idTVA = null;
//	private File idCategorie = null;
//	private File idArticle = null;
//	private File idCommande = null;
//	private File idImageArticle = null;
//	private File idTiers = null;
//	private File idLabel = null;
	
	private WSPrestaUtil<Products> utilProduct = new WSPrestaUtil<Products>(Products.class);
	private WSPrestaUtil<SpecificPrices> utilSpecificPrices = new WSPrestaUtil<SpecificPrices>(SpecificPrices.class);
	private WSPrestaUtil<Categories> utilCategories = new WSPrestaUtil<Categories>(Categories.class);
	private WSPrestaUtil<StockAvailable> utilStockAvailables = new WSPrestaUtil<StockAvailable>(StockAvailable.class);

	private List<TaCategorieArticle> listeCategorieArticleBDG = null;
	private List<TaArticle> listeArticleBDG = null;
	private List<TaLabelArticle> listeLabelBDG = null;
	private List<TaTva> listeTvaBDG = null;
	
	private CustomersList listeCustomers = null;
	private OrderList listeOrders = null;
	
	private Boolean modeTTC = null;
	
	static Logger logger = Logger.getLogger(WSPrestashop.class.getName());
	private static FileAppender myAppender = new FileAppender();
	
	static {
		logger.addAppender(myAppender);
		addLog();
	}
	
	public WSPrestashop(Boolean modeTTC) {
		this.modeTTC = modeTTC;
	}

	static public void addLog() {
		try {
			String basePath = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/";
			
			String formatDate = "yyyyMMdd_HHmmss";
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			String date = sdf.format(new Date());
			
			myAppender.setFile(basePath+"/import_tiers_commandes_"+date+".log");
			//myAppender.setFile(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_LOC_FICHIER_EXPORT)+"/import_tiers_commandes.log");
			myAppender.setLayout(new PatternLayout());
			myAppender.setAppend(false);
			myAppender.addFilter(new Filter() {

				@Override
				public int decide(LoggingEvent e) {
					if(e.getLoggerName().startsWith("fr.legrain.article.export") && (e.getLevel() == Level.INFO)) {
						return Filter.ACCEPT;
					} else if(e.getLoggerName().startsWith("fr.legrain.prestashop") && (e.getLevel() == Level.INFO))
						return Filter.ACCEPT;
					else
						return Filter.DENY;
				}
			});
			myAppender.activateOptions();
			//logger.debug("test");
		} catch (Exception e) {
			logger.error("", e);
		}

	}
	
	/**
	 * Récupération d'une valeur de configuration de la boutique prestashop à partir de son nom (ex: PS_VERSION_DB)
	 * @param key
	 * @return
	 * @throws JAXBException
	 */
	public String findPrestashopConfigValue(String key) throws JAXBException {
		WSPrestaUtil<ConfigurationsList> utilList = new WSPrestaUtil<ConfigurationsList>(ConfigurationsList.class);
		WSPrestaUtil<Configurations> util = new WSPrestaUtil<Configurations>(Configurations.class);
		String strURLList = util.getBaseURI()+"/configurations?filter[name]="+key;
		String strURL = util.getBaseURI()+"/configurations";
		
		ConfigurationsList l = utilList.getQuery(strURLList);
		Configurations c = null;
		
		
		if(!l.getConfigurations().isEmpty()) {
			c = util.get(strURL, l.getConfigurations().get(0).getId());
		}
		
		return c!=null?c.getValue():"";
	}
	
	/**
	 * Récupération du numéro de version du Prestashop installé
	 * @return
	 * @throws JAXBException
	 */
	public String findPrestashopVersion() throws JAXBException {
		return findPrestashopConfigValue("PS_VERSION_DB");
	}
	
	/**
	 * Liste sur la sortie standard toutes les valeurs de configurations du prestashop accesible à partir du webservice
	 * @throws JAXBException
	 */
	public void printPrestaConfig() throws JAXBException {
		WSPrestaUtil<ConfigurationsList> util = new WSPrestaUtil<ConfigurationsList>(ConfigurationsList.class);
		WSPrestaUtil<Configurations> utilConfig = new WSPrestaUtil<Configurations>(Configurations.class);
		String strURL = util.getBaseURI()+"/configurations";
		ConfigurationsList l = util.getQuery(strURL);
		
		List<String> list = new ArrayList<String>();
		
		Configurations c = null;
		for(ConfigurationsListValue v : l.getConfigurations()) {
			c = utilConfig.get(strURL, v.getId());
			String s = "Nom : "+c.getName()+" **** "+c.getValue();
			list.add(s);
		}
		
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	public void preparationImportation() throws Exception {
		preparationImportation(null,null);
	}
	
	public void preparationImportation(Date deb, Date fin) throws Exception {
		
		initMappingId();
		
		TaSynchroBoutiqueDAO taSynchroBoutiqueDAO = new TaSynchroBoutiqueDAO();
		TaSynchroBoutique taSynchroBoutique = taSynchroBoutiqueDAO.findInstance();
		
		Date dateDerniereImportation = null;
		if(deb==null)
			dateDerniereImportation = taSynchroBoutique.getDerniereSynchro();
		else
			dateDerniereImportation = deb;
		
		Date dateMaintenant = null;
		if(fin==null)
			dateMaintenant = new Date();
		else
			dateMaintenant = fin;
		
		WSPrestaUtil<CustomersList> utilCustomersList= new WSPrestaUtil<CustomersList>(CustomersList.class);
		WSPrestaUtil<OrderList> utilOrderList= new WSPrestaUtil<OrderList>(OrderList.class);
		
		String strURLCustomersListAdd = utilCustomersList.getBaseURI()+"/customers";
		String strURLCustomersListUpdate = utilCustomersList.getBaseURI()+"/customers";
		String strURLOrderListAdd = utilOrderList.getBaseURI()+"/orders";
		String strURLOrderListUpdate = utilOrderList.getBaseURI()+"/orders";
		
		if(dateDerniereImportation!=null) {
//			strURLCustomersList += "?filter[date_add]="+filterDateRange(dateDerniereImportation,dateMaintenant,false)+
//					//URLEncoder.encode("|","UTF-8")+"filter[date_upd]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
//					"filter[date_upd]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
//			strURLOrderList += "?filter[date_add]="+filterDateRange(dateDerniereImportation,dateMaintenant,false)+
//					//URLEncoder.encode("|","UTF-8")+"filter[date_upd]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
//					"filter[date_upd]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
			
			//Préparation des filtres
			
			strURLCustomersListAdd += "?filter[date_add]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
			strURLOrderListAdd += "?filter[date_add]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
			
			strURLCustomersListUpdate += "?filter[date_upd]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
			strURLOrderListUpdate += "?filter[date_upd]="+filterDateRange(dateDerniereImportation,dateMaintenant,true);
		} 
		
		//Rempli les listes soit avec toutes les données si première synchro, sinon toutes les données ajoutées depuis la dernière synchro
		listeCustomers = utilCustomersList.getQuery(strURLCustomersListAdd);
		listeOrders = utilOrderList.getQuery(strURLOrderListAdd);
		
		if(dateDerniereImportation!=null) {
			//toutes les données modifiées depuis la dernière synchro
			listeCustomers.getCustomers().addAll(utilCustomersList.getQuery(strURLCustomersListUpdate).getCustomers());
			//listeOrders.getOrders().addAll(utilOrderList.getQuery(strURLOrderListUpdate).getOrders());
		}
	}
	
	/**
	 * Création d'une partie d'URL, pour un filtre comportant un intervalle de dates<br>
	 * ex : api/customers?filter[date_add]=[2011-03-3,2011-3-4]&date=1
	 * @param dateDeb
	 * @param dateFin
	 * @param complet - si vrai, ajoute &date=1, obligatoire pour les filtres prestashop sur les dates, mais ne doit être présent q'une seule fois
	 * si le/les filtre/s de l'url porte sur plusieur date
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String filterDateRange(Date dateDeb, Date dateFin,boolean complet) throws UnsupportedEncodingException {
		String formatDatePresta = "yyyy-MM-dd HH:mm:ss";
		String res = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatDatePresta);
		String dateDebString = sdf.format(dateDeb);
		String dateFinString = sdf.format(dateFin);
		//res = "["+dateDebString+","+dateFinString+"]";
		res = URLEncoder.encode("["+dateDebString+","+dateFinString+"]","UTF-8");
		if(complet)
			res += "&date=1";
		return res;	
	}
	
	/**
	 * Importation (creation/mise à jour) des tiers et des commandes
	 * @param monitor
	 * @throws Exception
	 */
	public void importCommand(IProgressMonitor monitor) throws Exception {
		//addLog();
		//printPrestaConfig();
		System.err.println(findPrestashopVersion());
		
		WSPrestaUtil<OrderList> utilList = new WSPrestaUtil<OrderList>(OrderList.class);
		WSPrestaUtil<Orders> util= new WSPrestaUtil<Orders>(Orders.class);
		String strURL = utilList.getBaseURI()+"/orders";
		
		WSPrestaUtil<Addresses> utilAdresses = new WSPrestaUtil<Addresses>(Addresses.class);
		String strURLAdresses = utilList.getBaseURI()+"/addresses";
		
		WSPrestaUtil<Customers> utilCustomers= new WSPrestaUtil<Customers>(Customers.class);
		String strURLCustomers = utilList.getBaseURI()+"/customers";

//		OrderList p = utilList.getQuery(strURL);
//		
//		Orders o = null;
//		Addresses address = null;
//		Customers customer = null;
//		for (OrderListValue order : p.getOrders()) {
//			o = util.get(strURL, order.getId());
//			System.out.println(o.getId());
//			
//			address = utilAdresses.get(strURLAdresses, o.getIdAddressDelivry());
//			
//			customer = utilCustomers.get(strURLCustomers, o.getIdCustomer());
//		}
		
		logger.info("=========================================================================================");
		logger.info("Importation des tiers : "+listeCustomers.getCustomers().size());
		logger.info("=========================================================================================");
		Customers cutomers = null;
		for (CustomersListValue o : listeCustomers.getCustomers()) {
			cutomers = utilCustomers.get(strURLCustomers, o.getId());
			if(monitor!=null) {
				monitor.setTaskName("Création du tiers : "+cutomers.getId());
			}
			importTiers(cutomers);

			if(monitor!=null) {
				monitor.worked(1);
			}
		}
		
		logger.info("=========================================================================================");
		logger.info("Importation des commandes : "+listeOrders.getOrders().size());
		logger.info("=========================================================================================");
		Orders orders = null;
		for (OrderListValue o : listeOrders.getOrders()) {
			orders = util.get(strURL, o.getId());
			if(monitor!=null) {
				monitor.setTaskName("Création de la commande : "+orders.getId());
			}
			importCommande(orders);

			if(monitor!=null) {
				monitor.worked(1);
			}
		}
		
	}
	
	public List<Addresses> findAddressesForCustomer(int idCustomer) throws JAXBException {
		WSPrestaUtil<AddressesList> utilAdresses= new WSPrestaUtil<AddressesList>(AddressesList.class);
		String strURLAdresses = utilAdresses.getBaseURI()+"/addresses/?filter[id_customer]=["+idCustomer+"]";
		
		WSPrestaUtil<Addresses> util= new WSPrestaUtil<Addresses>(Addresses.class);
		String strURL = util.getBaseURI()+"/addresses";
		
		List<Addresses> retour = new ArrayList<Addresses>();
		
		AddressesList l = utilAdresses.getQuery(strURLAdresses);
		
		Addresses a = null;
		for (AddressesListValue adr : l.getAddresses()) {
			a = util.get(strURL, adr.getId());
			retour.add(a);
		}
		
		return retour;
	}
	
	public void importTiers(int id) throws Exception {
		WSPrestaUtil<Customers> util = new WSPrestaUtil<Customers>(Customers.class);
		String strURL = util.getBaseURI()+"/customers";
		
		Customers o = util.get(strURL, id);
		
		importTiers(o) ;
	}
	
	/**
	 * Imporation (création/mise à jour) d'un tiers
	 * @param customer - client/tiers Prestashop
	 * @throws Exception
	 */
	public void importTiers(Customers customer) throws Exception {
		Map<String,String> listeNouveauTiersBDG = new HashMap<String,String>();
		TaTiers tiersCourant = null;
		TaTiersDAO taTiersDAO = new TaTiersDAO();
		TaAdresseDAO taAdresseDAO = new TaAdresseDAO(taTiersDAO.getEntityManager());
		TaTelephoneDAO taTelephoneDAO = new TaTelephoneDAO(taTiersDAO.getEntityManager());
		TaEmailDAO taEmailDAO = new TaEmailDAO(taTiersDAO.getEntityManager());
		TaTTiersDAO taTTiersDAO = new TaTTiersDAO(taTiersDAO.getEntityManager());
		
		WSPrestaUtil<Groups> utilGroups = new WSPrestaUtil<Groups>(Groups.class);
		String strURLGroups = utilGroups.getBaseURI()+"/groups";

		String origineImport = "site_web";

		/*
		 * Importation des tiers
		 */
//		JSONArray listeTiers = (JSONArray)json.get("tiers");
//		Iterator iterTiers = listeTiers.iterator();
		String idSiteWeb = null;

		//while(iterTiers.hasNext()) {
			//JSONObject tiers = (JSONObject)iterTiers.next();
			Customers tiers = customer;

			idSiteWeb = tiers.getId().toString();
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

			tiersCourant.setNomTiers(tiers.getLastname());
			tiersCourant.setPrenomTiers(tiers.getFirstname());
			tiersCourant.setCodeCompta(tiersCourant.getCodeTiers());
			
			Groups grp = utilGroups.get(strURLGroups, tiers.getIdDefault_group());
			if(grp.getPriceDisplayMethod()==PrestaConst.PS_TAX_EXC) {
				//HT
				tiersCourant.setTtcTiers(0);
			} else {
				//TTC
				tiersCourant.setTtcTiers(1);
			}
			//tiersCourant.setTtcTiers(LibConversion.stringToInteger(tiers.get("ttc").toString()));
			
			logger.info(tiersCourant.getNomTiers()+" "+tiersCourant.getPrenomTiers());
			logger.info("Nouveau : "+(nouveauTiers?"oui":"non (maj)"));
			logger.info("==============");

			if(tiers.getEmail()!=null) {
				TaEmail email = null;
				if(nouveauTiers) {
					email = new TaEmail();
					email.setIdImport(idSiteWeb);
					email.setOrigineImport(origineImport);
				} else {
					email = taEmailDAO.rechercheParImport(origineImport, idSiteWeb).get(0);
				}
				email.setAdresseEmail(tiers.getEmail());

				if(nouveauTiers) {
					tiersCourant.addEmail(email);
					email.setTaTiers(tiersCourant);
				}
			}


//			if(tiers.get("phone-number")!=null) {
//				TaTelephone tel = null;
//				if(nouveauTiers) {
//					tel = new TaTelephone();
//					tel.setIdImport(idSiteWeb);
//					tel.setOrigineImport(origineImport);
//				} else {
//					tel = taTelephoneDAO.rechercheParImport(origineImport, idSiteWeb).get(0);
//				}
//				tel.setNumeroTelephone(tiers.get("phone-number").toString());
//
//				if(nouveauTiers) {
//					tiersCourant.addTelephone(tel);
//					tel.setTaTiers(tiersCourant);
//				}
//			}

			tiers.getBirthday();

			//JSONArray listeAdresseFacturation = (JSONArray)tiers.get("adress-fact");
			List<Addresses> listeAdresseFacturation = findAddressesForCustomer(tiers.getId());
			if(listeAdresseFacturation.size()>0) {
				//TaTAdr typeAdrFact = taTAdrDAO.findByCode("FACT");
				Iterator<Addresses> iterAdresse = listeAdresseFacturation.iterator();
				TaAdresse adr2 = null;
				boolean nouvelleAdresse = false;
				while(iterAdresse.hasNext()){
					//JSONObject adrFact = (JSONObject)iterAdresse.next();
					Addresses adrFact = iterAdresse.next();
					adrFact.getAlias();

					if(!nouveauTiers && taAdresseDAO.rechercheParImport(origineImport, adrFact.getId().toString()).size()>0) {
						adr2 = taAdresseDAO.rechercheParImport(origineImport, adrFact.getId().toString()).get(0);
						nouvelleAdresse = false;
					} else {
						nouvelleAdresse = true;
						adr2 = new TaAdresse();
						if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CORRESPONDANCE_ADRESSE_ADMINISTRATIF_DEFAUT)) {
							adr2.setCommAdministratifAdresse(1);
						} else {
							adr2.setCommAdministratifAdresse(0);
						}
						
						if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CORRESPONDANCE_ADRESSE_COMMERCIAL_DEFAUT)) {
							adr2.setCommCommercialAdresse(1);
						} else {
							adr2.setCommCommercialAdresse(0);
						}
					}

					if(nouvelleAdresse) {
						adr2.setOrigineImport(origineImport);
						if(adrFact.getId()!=null)
							adr2.setIdImport(adrFact.getId().toString());
					}

					if(adrFact.getAddress1()!=null)
						adr2.setAdresse1Adresse(adrFact.getAddress1().toString());
					if(adrFact.getCity()!=null)
						adr2.setVilleAdresse(adrFact.getCity().toString());
					if(adrFact.getPostcode()!=null)
						adr2.setCodepostalAdresse(adrFact.getPostcode().toString());

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

//			if(tiers.get("bdg-id")!=null) {
//				listeNouveauTiersBDG.put(LibConversion.integerToString(tiersCourant.getIdTiers()),idSiteWeb);
//			}

			transaction = null;
		//} //fin traitements des tiers

	}
	
	/**
	 * Importation d'une commande prestashop dans le Bureau de Gestion
	 * @param id - id de la commande Prestashop
	 * @throws Exception
	 */
	public void importCommande(int id) throws Exception {
		WSPrestaUtil<Orders> util = new WSPrestaUtil<Orders>(Orders.class);
		String strURL = util.getBaseURI()+"/orders";
		
		Orders o = util.get(strURL, id);
		
		importCommande(o) ;
	}
	
	/**
	 * Importation d'une commande prestashop dans le Bureau de Gestion
	 * @param orders - commande Prestashop
	 * @throws Exception
	 */
	public void importCommande(Orders orders) throws Exception {
		
		WSPrestaUtil<Addresses> utilAdresses= new WSPrestaUtil<Addresses>(Addresses.class);
		String strURLAdresses = utilAdresses.getBaseURI()+"/addresses";
		
		WSPrestaUtil<Customers> utilCustomers= new WSPrestaUtil<Customers>(Customers.class);
		String strURLCustomers = utilCustomers.getBaseURI()+"/customers";
		
		/*
		 * Importation des commandes
		 */
		
		Map<String,String> listeNouveauTiersBDG = new HashMap<String,String>();
		//TaTiers tiersCourant = null;
		TaTiersDAO taTiersDAO = new TaTiersDAO();
		//TaAdresseDAO taAdresseDAO = new TaAdresseDAO(taTiersDAO.getEntityManager());
		//TaTelephoneDAO taTelephoneDAO = new TaTelephoneDAO(taTiersDAO.getEntityManager());
		//TaEmailDAO taEmailDAO = new TaEmailDAO(taTiersDAO.getEntityManager());
		//TaTTiersDAO taTTiersDAO = new TaTTiersDAO(taTiersDAO.getEntityManager());

		String origineImport = "site_web";
		
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


		//JSONArray listeCommandes = (JSONArray)json.get("commandes");
		//Iterator iterCmd = listeCommandes.iterator();
		String idTiersCommande = null;
		String idSiteWeb = null;
		
		//génération des commandes en "mode TTC ou HT"
		boolean modeTTC = this.modeTTC;

		//while(iterCmd.hasNext()) {
			//JSONObject commande = (JSONObject)iterCmd.next();
			Orders commande = orders;

			
			//commande.getCurrentState()
			
			idSiteWeb = commande.getId().toString();

			cdeCourante = new TaBoncde();
			cdeCourante.setCodeDocument(taBoncdeDAO.genereCode());
			cdeCourante.setOrigineImport(origineImport);
			cdeCourante.setIdImport(idSiteWeb);
			//validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteBoncde) selectedBoncde).getCodeDocument());//permet de verrouiller le code genere
			//cdeCourante.setCommentaire(boncdePlugin.getDefault().getPreferenceStore().getString(PreferenceConstantsProject.COMMENTAIRE));

			//verifier les dates par rapport aux dates exo ?

			infosCdeCourante = new TaInfosBoncde();

			idTiersCommande = commande.getIdCustomer().toString();
			importTiers(commande.getIdCustomer());
			if(taTiersDAO.rechercheParImport(origineImport, idTiersCommande).size()>0) {
				cdeCourante.setTaTiers(taTiersDAO.rechercheParImport(origineImport, idTiersCommande).get(0));
//			} else {
//				
//				cdeCourante.setTaTiers(taTiersDAO.rechercheParImport(origineImport, idTiersCommande).get(0));
			}

			cdeCourante.setLibelleDocument("Commande boutique web "+commande.getReference()+" : "+commande.getId());

			cdeCourante.setDateDocument(commande.getDateAdd());
			
			if(modeTTC) {
				//cdeCourante.setTtc(1);
				cdeCourante.setTtc(cdeCourante.getTaTiers().getTtcTiers());
				modeTTC= LibConversion.intToBoolean(cdeCourante.getTtc());
			}

			Addresses adr = utilAdresses.get(strURLAdresses, commande.getIdAddressInvoice());
			infosCdeCourante.setAdresse1(adr.getAddress1());
			infosCdeCourante.setVille(adr.getCity());
			infosCdeCourante.setCodepostal(adr.getPostcode());
			
			Addresses adrLiv = utilAdresses.get(strURLAdresses, commande.getIdAddressDelivry());
			infosCdeCourante.setAdresse1Liv(adrLiv.getAddress1());
			infosCdeCourante.setVilleLiv(adrLiv.getCity());
			infosCdeCourante.setCodepostalLiv(adrLiv.getPostcode());

			infosCdeCourante.setCompte(cdeCourante.getTaTiers().getCompte());
			infosCdeCourante.setCodeCompta(cdeCourante.getTaTiers().getCodeCompta());
			
			//commande.get("total-ht").toString();
			//commande.get("total-tva").toString();
			//commande.get("user-name").toString();
			//commande.get("user-firstname").toString();

			//JSONArray listeLignesCmd = (JSONArray)commande.get("lines");
			List<OrderRow> listeLignesCmd = commande.getAssociations().getOrderRow();
			if(listeLignesCmd.size()>0) {
				Iterator iterLigneCmd = listeLignesCmd.iterator();
				TaArticle articleCourant = null;
				//Ajout des lignes de commandes
				while(iterLigneCmd.hasNext()){
					//JSONObject ligne = (JSONObject)iterLigneCmd.next();
					OrderRow ligne = (OrderRow) iterLigneCmd.next();

					ligneCourante = new TaLBoncde(true);

					ligneCourante.setTaTLigne(taTLigneDAO.findByCode(Const.C_CODE_T_LIGNE_H));
					ligneCourante.setTaDocument(cdeCourante);
					if(ligne.getProductId()!=null) {
						
						//if(!(ligne.getProductId() instanceof Boolean)) {

						
							if(ligne.getProductId()!=null) {
								articleCourant = taArticleDAO.findById(TaCorrespondanceIDBoutique.chercheIdBdg(listeIdArticle,ligne.getProductId()));
								if(articleCourant==null) {
									String message = "Pas de correspondance pour l'article "+ligne.getProductId()+" (ID Prestashop) dans le bureau de gestion";
									logger.error(message);
									throw new Exception(message);
								}
								//articleCourant = taArticleDAO.findById(origineImport,ligne.getProductId().toString()).get(0);
								ligneCourante.setTaArticle(articleCourant);
								
							}
							
							if(ligne.getProductPrice()!=null) {
								if(modeTTC) {
									BigDecimal prixUnitaireTTC = 
										LibConversion.stringToBigDecimal(ligne.getProductPrice().toString()).multiply(
												new BigDecimal(1).add(
														articleCourant.getTaTva().getTauxTva()
														.divide(new BigDecimal(100))
												),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP);
							
							
									ligneCourante.setPrixULDocument(prixUnitaireTTC);
								} else {
									ligneCourante.setPrixULDocument(LibConversion.stringToBigDecimal(ligne.getProductPrice().toString()));
								}
							}

							if(ligne.getProductQuantity()!=null) {
								ligneCourante.setQteLDocument(LibConversion.stringToBigDecimal(ligne.getProductQuantity().toString()));
							}

							cdeCourante.addLigne(ligneCourante);

//							if(ligne.get("total-ht")!=null)
//								ligneCourante.setMtHtLDocument(ligne.get("total-ht").toString());
//							if(ligne.get("total-tva")!=null)
//								ligneCourante.setVilleAdresse(ligne.get("total-tva").toString());
//							if(ligne.get("unit-product")!=null)
//								ligneCourante.setCodepostalAdresse(ligne.get("unit-product").toString());
//							if(ligne.get("product-name")!=null)
//								ligneCourante.setCodepostalAdresse(ligne.get("product-name").toString());
//							if(ligne.get("id-TVA")!=null)
//								ligneCourante.setCodepostalAdresse(ligne.get("id-TVA").toString());
						}
//					} else {
//						logger.info("Article de la boutique web (ID = "+ligne.getProductId()+") introuvable dans le BDG");
//					}

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
				
				TaTva tva = daoTVA.findByTauxOrCreate(commande.getCarrierTaxRate().toString());

				articleFraisDePort.setTaTva(tva);
				ligneCourante.setTaArticle(articleFraisDePort);
				//ligneCourante.setTauxTvaLDocument(LibConversion.stringToBigDecimal(commande.get("carrier_tax_rate").toString()));
				if(modeTTC) {
					ligneCourante.setPrixULDocument(LibConversion.stringToBigDecimal(commande.getTotalShipping().toString()));
				} else {
					BigDecimal prixUnitaireHT = 
					LibConversion.stringToBigDecimal(commande.getTotalShipping().toString()).divide(
						new BigDecimal(1).add(
								LibConversion.stringToBigDecimal(commande.getCarrierTaxRate().toString())
								.divide(new BigDecimal(100))
								),MathContext.DECIMAL128).setScale(2,BigDecimal.ROUND_HALF_UP);
				
//					ligneCourante.setLibLDocument("Total frais de port "+"ttc=>"+commande.get("total_shipping")+" tx=>"+commande.get("carrier_tax_rate"));
				
					ligneCourante.setPrixULDocument(prixUnitaireHT);
				}
				ligneCourante.setQteLDocument(new BigDecimal(1));
			} else {
				//pas d'article frais de port, affichage sous forme de commentaire
				ligneCourante.setTaTLigne(taTLigneDAO.findByCode(Const.C_CODE_T_LIGNE_C));
				ligneCourante.setLibLDocument("Total frais de port TTC : "+commande.getTotalShipping().toString());
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

		//}// fin traitements commandes

	}

	/**
	 * Récupération des différentes listes afin de pouvoir initialisé correctement les barres de progression
	 */
	public void preparationExportation() {		
		//listeCategorieArticleBDG = new TaCategorieArticleDAO().selectAll();
		//listeArticleBDG = new TaArticleDAO().selectAll();
		
		UtilPreferenceImage utilPreferenceImage = new UtilPreferenceImage();
		//ResultatExportation resultat = new ResultatExportation();
		
//		String pathRepExportWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
//		File repExportWebTmp = new File(pathRepExportWebTmp);
//		if(repExportWebTmp.exists()) {
//			repExportWebTmp.delete();
//		}
//		repExportWebTmp.mkdirs();
//
//		fichierExport = pathRepExportWebTmp
//							+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.NOM_FICHIER_EXPORT);
		
		TaSynchroBoutiqueDAO taSynchroBoutiqueDAO = new TaSynchroBoutiqueDAO();
		TaSynchroBoutique taSynchroBoutique = taSynchroBoutiqueDAO.findInstance();
		
		TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO();
		//List<TaCategorieArticle> listeCategorie = null;
		
		TaLabelArticleDAO taLabelArticleDAO = new TaLabelArticleDAO();
		//List<TaLabelArticle> listeLabel = null;
		
		TaArticleDAO taArticleDAO = new TaArticleDAO();
		//List<TaArticle> listeArticleBDG = null;
		
		TaTvaDAO taTvaDAO = new TaTvaDAO();
		//List<TaTva> listeTva = taTvaDAO.selectAll();
		
		if(taSynchroBoutique==null || taSynchroBoutique.getDerniereExport()==null) {
			listeArticleBDG = taArticleDAO.selectAll();
			listeCategorieArticleBDG = taCategorieArticleDAO.selectAll();
			listeLabelBDG = taLabelArticleDAO.selectAll();
		} else {
			logger.info("=========================================================================================");
			logger.info("Sélection de tous les articles créés ou modifiés depuis le : "+taSynchroBoutique.getDerniereExport());
			listeArticleBDG = taArticleDAO.findByNewOrUpdatedAfter(taSynchroBoutique.getDerniereExport());
			logger.info("Nombre d'articles à créer/mettre à jour : "+listeArticleBDG.size());
			logger.info("=========================================================================================");
			
			logger.info("=========================================================================================");
			logger.info("Sélection de toutes les catégories d'article créés ou modifiés depuis le : "+taSynchroBoutique.getDerniereExport());
			listeCategorieArticleBDG = taCategorieArticleDAO.findByNewOrUpdatedAfter(taSynchroBoutique.getDerniereExport());
			logger.info("Nombre de catégrories à créer/mettre à jour : "+listeCategorieArticleBDG.size());
			logger.info("=========================================================================================");
			
			logger.info("=========================================================================================");
			logger.info("Sélection de tous les labels d'articles créés ou modifiés depuis le : "+taSynchroBoutique.getDerniereExport());
			listeLabelBDG = taLabelArticleDAO.findByNewOrUpdatedAfter(taSynchroBoutique.getDerniereExport());
			logger.info("Nombre de label à créer/mettre à jour : "+listeLabelBDG.size());
			logger.info("=========================================================================================");
		}
		
//		File repImageCategorie = new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE));
//		File repImageLabel = new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL));
//		File repImageArticles = new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE));
//		
//		repImageCategorie.mkdirs();
//		repImageLabel.mkdirs();
//		repImageArticles.mkdirs();
//		
//		String encoding = "UTF-8";
	}

	/**
	 * Exportation des labels, des catégories et des articles vers la boutique
	 * @param monitor
	 * @throws Exception
	 */
	public void exportProdcut(IProgressMonitor monitor) throws Exception {

		logger.info("Initialisation des correspondace ID BDG / ID Prestashop");
		initMappingId();

		getLanguageId();

		logger.info("Initialisation des labels");
		featureLabelId = initialiseLabelFeature();
		/*******************************************
		 * 			Declinaison
		 *******************************************/
		//$attributeUniteId = NULL;
		//$attributeColisId = NULL;
		if(utiliseSecondPrix) {
			if(utiliseDeclinaison) {
				logger.info("Initialisation des déclinaisons");
				int attributeGroupId = initDeclinaison();

				//Le groupe d'attributs vient d'être créé, ajout des attributs ou récupération des Id
				//				int attributeUniteId = addAttributeUnite(attributeGroupId);
				//				int attributeColisId = addAttributeColis(attributeGroupId);
				addAttributeUnite(attributeGroupId);
				addAttributeColis(attributeGroupId);

			}
		}

		logger.info("Initialisation des catégories");
		initCategories(monitor);

		initLabels();

		/*************************************************
		 * 				TVA
		 ************************************************/
		//	 	//Le mapping entre l'id de TVA BDG et l'id de la règle de taxe BDG est faite en dur dans le fichier
		//	 	//JSON correspondant, il faut pour l'instant le maintenir à la main.
		//	 	lgr_log_info("Importation des taux de TVA");
		//	 	foreach( $$json->tva as $a ) {
		////	 	Tax::createWithSpecificId($pdo, $a->id, $a->libelle, $a->taux / 100);
		////	  		$tax = new TaxCore();
		////	  		$tax->name = $a->libelle;
		////	  		$tax->rate = $a->taux / 100;
		////	  		$tax->save();
		//	 	}
		/************************************************
		 * 			Produit
		 ***********************************************/
		logger.info("Exportation des articles");
		synchroProducts(monitor);

	}

	/**
	 * Initialisation des listes de correspondance entre les ID Bureau de gestion et les ID de la boutique.
	 * @throws Exception
	 */
	public void initMappingId() throws Exception {
		//String basePath = "/tmp/";
		String basePath = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/";
		marshallerListeID = JAXBContext.newInstance(ListeID.class).createMarshaller();
		marshallerListeID.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshallerListeID.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Unmarshaller unmarshaller = JAXBContext.newInstance(ListeID.class).createUnmarshaller();
		
		EntityTransaction transaction = taCorrespondanceIDBoutiqueDAO.getEntityManager().getTransaction();
		taCorrespondanceIDBoutiqueDAO.begin(transaction);

		//id_tva.txt	=> Correspondance entre les code TVA du site et celle du BDG {"4":2,"5":1}
//		listeIdTVA = new ListeID();
//		idTVA = new File(basePath+"id_tva.txt");
//		if(idTVA.exists()) {			
//			listeIdTVA = (ListeID) unmarshaller.unmarshal(idTVA);
//			System.out.println(listeIdTVA);
//		} else {
//			listeIdTVA.description = "ID TVA";
//			listeIdTVA.ids.add(new IdBdgPresta(4, 2));
//			listeIdTVA.ids.add(new IdBdgPresta(5, 1));
//			marshallerListeID.marshal(listeIdTVA, idTVA);
//		}
		listeIdTVA = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA);
		//idTVA = new File(basePath+"id_tva.txt");
		if(listeIdTVA!=null && !listeIdTVA.isEmpty()) {			
			//listeIdTVA = (ListeID) unmarshaller.unmarshal(idTVA);
			System.out.println(listeIdTVA);
		} else {
			if(listeIdTVA==null) listeIdTVA = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdTVA.description = "ID TVA";
			/*
			 PRESTASHOP DEFAUT :
			 	1 	TVA FR 19.6% 	19.600 % 	
				2 	TVA FR 7% 		7.000 % 	
				3 	TVA FR 5.5% 	5.500 % 	
				4 	TVA FR 2.1% 
			 BDG DEFAUT
			 	1	A1	Immobilisations	19,60
				2	A2	Achats 5.5		5,50
				3	A3	Achats 7		7,00
				4	A4	Achats 19.6		19,60
				5	V1	Ventes 5.5		5,50
				6	V2	Ventes 19.6		19,60
				7	V3	Ventes 7		7,00
			 */
			TaCorrespondanceIDBoutique a = new TaCorrespondanceIDBoutique(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA,7,2); //7
			TaCorrespondanceIDBoutique b = new TaCorrespondanceIDBoutique(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA,6,1); //19,6
			TaCorrespondanceIDBoutique c = new TaCorrespondanceIDBoutique(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA,5,3); //5.5
			//listeIdTVA.ids.add(new IdBdgPresta(4, 2));
			//listeIdTVA.ids.add(new IdBdgPresta(5, 1));
			//marshallerListeID.marshal(listeIdTVA, idTVA);
			taCorrespondanceIDBoutiqueDAO.enregistrerPersist(a);
			taCorrespondanceIDBoutiqueDAO.enregistrerPersist(b);
			taCorrespondanceIDBoutiqueDAO.enregistrerPersist(c);
			listeIdTVA.add(a);
			listeIdTVA.add(b);
			listeIdTVA.add(c);
			
		}

		//id_categorie.txt	=> Correspondance entre les catégories du site et celle du BDG
//		listeIdCategorie = new ListeID();
//		idCategorie = new File(basePath+"id_categorie.txt");
//		if(idCategorie.exists()) {			
//			listeIdCategorie = (ListeID) unmarshaller.unmarshal(idCategorie);
//		} else {
//			listeIdCategorie.description = "ID Categorie";
//			marshallerListeID.marshal(listeIdCategorie, idCategorie);
//		}
		listeIdCategorie = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_CATEGORIE);
		if(listeIdCategorie!=null && !listeIdCategorie.isEmpty()) {		
			//listeIdCategorie = (ListeID) unmarshaller.unmarshal(idCategorie);
		} else {
			if(listeIdCategorie==null) listeIdCategorie = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdCategorie.description = "ID Categorie";
			//marshallerListeID.marshal(listeIdCategorie, idCategorie);
		}

		//id_article.txt	=> Correspondance entre les artilces du site et ceux du BDG
//		listeIdArticle = new ListeID();
//		idArticle = new File(basePath+"id_article.txt");
//		if(idArticle.exists()) {			
//			listeIdArticle = (ListeID) unmarshaller.unmarshal(idArticle);
//		} else {
//			listeIdArticle.description = "ID Article";
//			marshallerListeID.marshal(listeIdArticle, idArticle);
//		}
		//listeIdArticle = new ListeID();
		//idArticle = new File(basePath+"id_article.txt");
		listeIdArticle = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_ARTICLE);
		//if(idArticle.exists()) {
		if(listeIdArticle!=null && !listeIdArticle.isEmpty()) {
			//listeIdArticle = (ListeID) unmarshaller.unmarshal(idArticle);
		} else {
			if(listeIdArticle==null) listeIdArticle = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdArticle.description = "ID Article";
			//marshallerListeID.marshal(listeIdArticle, idArticle);
		}

		//id_commandes.txt	=> Correspondance entre les commandes du site et celle du BDG
		//listeIdCommande = new ListeID();
		//idCommande = new File(basePath+"id_commandes.txt");
		listeIdCommande = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_COMMANDE);
		//if(idCommande.exists()) {
		if(listeIdCommande!=null && !listeIdCommande.isEmpty()) {
			//listeIdCommande = (ListeID) unmarshaller.unmarshal(idCommande);
		} else {
			if(listeIdCommande==null) listeIdCommande = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdCommande.description = "ID Categorie";
			//marshallerListeID.marshal(listeIdCommande, idCommande);
		}

		//id_image_art.txt	=> Correspondance entre les images du site et celle du BDG
		//listeIdImageArticle = new ListeID();
		//idImageArticle = new File(basePath+"id_image_art.txt");
		listeIdImageArticle = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_IMG_ARTICLE);
		//if(idImageArticle.exists()) {
		if(listeIdImageArticle!=null && !listeIdImageArticle.isEmpty()) {
			//listeIdImageArticle = (ListeID) unmarshaller.unmarshal(idImageArticle);
		} else {
			if(listeIdImageArticle==null) listeIdImageArticle = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdImageArticle.description = "ID image article";
			//marshallerListeID.marshal(listeIdImageArticle, idImageArticle);
		}

		//id_tiers.txt		=> Correspondance entre les tiers du site et ceux du BDG
		//listeIdTiers = new ListeID();
		//idTiers = new File(basePath+"id_tiers.txt");
		listeIdTiers = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_TIERS);
		//if(idTiers.exists()) {
		if(listeIdTiers!=null && !listeIdTiers.isEmpty()) {
			//listeIdTiers = (ListeID) unmarshaller.unmarshal(idTiers);
		} else {
			if(listeIdTiers==null) listeIdTiers = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdTiers.description = "ID Tiers";
			//marshallerListeID.marshal(listeIdTiers, idTiers);
		}

		//id_label.txt	=> Correspondance entre les tiers du site et ceux du BDG
		//listeIdLabel = new ListeID();
		//idLabel = new File(basePath+"id_label.txt");
		listeIdLabel = taCorrespondanceIDBoutiqueDAO.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_LABEL);
		//if(idLabel.exists()) {
		if(listeIdLabel!=null && !listeIdLabel.isEmpty()) {
			//listeIdLabel = (ListeID) unmarshaller.unmarshal(idLabel);
		} else {
			if(listeIdLabel==null) listeIdLabel = new ArrayList<TaCorrespondanceIDBoutique>();
			//listeIdLabel.description = "ID Label";
			//marshallerListeID.marshal(listeIdLabel, idLabel);
		}
		
		taCorrespondanceIDBoutiqueDAO.commit(transaction);
	}

	public void getLanguageId() throws JAXBException {
		//		WSPrestaUtil<LanguagesList> util = new WSPrestaUtil<LanguagesList>(LanguagesList.class);
		//		String strURL = util.getBaseURI()+"/languages?filter[iso_code]=fr";
		//		
		//		LanguagesList p = util.getQuery(strURL);

	}

	/**
	 * Initialisation des déclinaisons dans la boutique prestashop
	 * @return
	 * @throws JAXBException
	 */
	public int initDeclinaison() throws JAXBException {
		//Teste l'existance du groupe d'attributs pour un langage particulier
		WSPrestaUtil<ProductOptionsList> utilList = new WSPrestaUtil<ProductOptionsList>(ProductOptionsList.class);
		WSPrestaUtil<ProductOptions> util= new WSPrestaUtil<ProductOptions>(ProductOptions.class);
		String strURL = utilList.getBaseURI()+"/product_options?filter[name]=Conditionnement";

		ProductOptionsList p = utilList.getQuery(strURL);

		//		$attrib = AttributeGroup::getAttributesGroups(getLanguageId());
		boolean trouve = false;
		//		foreach ($attrib AS $a) {
		//			if($a['name']=='Conditionnement') {
		//				trouve = true;
		//			}
		//		}
		if(!p.getProductOptions().isEmpty()) {
			trouve = true;
		}

		int retour = 0;
		if(!trouve) {
			strURL = util.getBaseURI()+"/product_options";
			//Le groupe d'attributs n'exite pas, création d'un groupe d'attribut
			ProductOptions attributeGroup = new ProductOptions();

			List<Language> listeName = new ArrayList<Language>();
			listeName.add(new Language(1,"Conditionnement"));
			attributeGroup.setName(listeName);

			List<Language> listePublicName = new ArrayList<Language>();
			listePublicName.add(new Language(1,"Conditionnement"));
			attributeGroup.setPublicName(listePublicName);

			attributeGroup.setIsColorGroup(false);
			ProductOptions newProductOptions = util.create(strURL, attributeGroup);
			retour = newProductOptions.getId();
			//			$attributeGroup = new AttributeGroup();
			//			$attributeGroup->name = createMultiLangField("Conditionnement");
			//			$attributeGroup->is_color_group = 0;
			//			$attributeGroup->public_name = createMultiLangField("Conditionnement");
			//			$attributeGroup->save();
			//			$retour = $attributeGroup->id;
		}
		return retour;
	}

	public int initialiseLabelFeature() throws Exception {
		
		WSPrestaUtil<ProductFeatures> utilList = new WSPrestaUtil<ProductFeatures>(ProductFeatures.class);
		WSPrestaUtil<ProductFeature> util = new WSPrestaUtil<ProductFeature>(ProductFeature.class);
		String strURL = util.getBaseURI()+"/product_features?filter[name]=Label";
		//strURL = util.getBaseURI()+"/product_features";

		//Teste l'existance de la feature pour un langage particulier
		//			$attrib = FeatureCore::getFeatures(getLanguageId());
		boolean trouve = false;
		int retour = 0;
		//			foreach ($attrib AS $a) {
		//				if($a['name']=='Label') {
		//					trouve = true;
		//					$retour = $a['id_feature'];
		//				}
		//			}

		ProductFeatures p = utilList.getQuery(strURL);

		if(!p.getProductFeatures().isEmpty()) {
			trouve = true;
			retour = p.getProductFeatures().get(0).getId();
		}

		if(!trouve) {
			strURL = util.getBaseURI()+"/product_features";
			//La feature Label n'exite pas, création de la feature
			ProductFeature feature = new ProductFeature();

			List<Language> listeName = new ArrayList<Language>();
			listeName.add(new Language(1,"Label"));
			feature.setName(listeName);

			ProductFeature newFeature = util.create(strURL, feature);
			//				$feature = new FeatureCore();
			//				$feature->name = createMultiLangField('Label');
			//				$feature->save();
			retour = newFeature.getId();
		}
		return retour;
	}

	/**
	 * Initialisation de la "déclinaison" UNITE
	 * @param attributeGroupId
	 * @throws JAXBException
	 */
	public void addAttributeUnite(int attributeGroupId) throws JAXBException {
		WSPrestaUtil<ProductOptionValuesList> utilList = new WSPrestaUtil<ProductOptionValuesList>(ProductOptionValuesList.class);
		WSPrestaUtil<ProductOptionValues> util= new WSPrestaUtil<ProductOptionValues>(ProductOptionValues.class);
		String strURL = utilList.getBaseURI()+"/product_option_values?filter[name]=Unite";

		ProductOptionValuesList p = utilList.getQuery(strURL);

		//		$att = Attribute::getAttributes(getLanguageId());
		boolean trouve = false;
		//		$id = 0;
		//		foreach ($att AS $a) {
		//			if($a['name']=='Unite') {
		//				$trouve = true;
		//				$id = $a['id_attribute'];
		//			}
		//		}
		if(!p.getProductOptionValues().isEmpty()) {
			trouve = true;
		}
		//		$retour = $id;
		if(!trouve && attributeGroupId!=0) {
			ProductOptionValues o = new ProductOptionValues();
			o.setIdAttributeGroup(attributeGroupId);

			List<Language> listeName = new ArrayList<Language>();
			listeName.add(new Language(1,"Unite"));
			o.setName(listeName);
			util.create(strURL, o);
			//			$attributeUnite->save();
			//			$retour = $attributeUnite->id;
		}
		//		if(!$trouve && $attributeGroupId) {
		//			$attributeUnite = new Attribute();
		//			$attributeUnite->id_attribute_group = $attributeGroupId;
		//			$attributeUnite->name = createMultiLangField("Unite");
		//			$attributeUnite->save();
		//			$retour = $attributeUnite->id;
		//		}
		//		return $retour;

	}

	/**
	 * Initialisation de la "déclinaison" COLIS
	 * @param attributeGroupId
	 * @throws JAXBException
	 */
	public void addAttributeColis(int attributeGroupId) throws JAXBException {
		WSPrestaUtil<ProductOptionValuesList> utilList = new WSPrestaUtil<ProductOptionValuesList>(ProductOptionValuesList.class);
		WSPrestaUtil<ProductOptionValues> util= new WSPrestaUtil<ProductOptionValues>(ProductOptionValues.class);
		String strURL = utilList.getBaseURI()+"/product_option_values?filter[name]=Colis";

		ProductOptionValuesList p = utilList.getQuery(strURL);

		//		$att = Attribute::getAttributes(getLanguageId());
		boolean trouve = false;
		//		$id = 0;
		//		foreach ($att AS $a) {
		//			if($a['name']=='Colis') {
		//				$trouve = true;
		//				$id = $a['id_attribute'];
		//			}
		//		}
		//		$retour = $id;
		if(!p.getProductOptionValues().isEmpty()) {
			trouve = true;
		}
		if(!trouve && attributeGroupId!=0) {
			ProductOptionValues o = new ProductOptionValues();
			o.setIdAttributeGroup(attributeGroupId);

			List<Language> listeName = new ArrayList<Language>();
			listeName.add(new Language(1,"Colis"));
			o.setName(listeName);
			util.create(strURL, o);
			//			$attributeColis->save();
			//			$retour = $attributeColis->id;
		}
		//		if(!$trouve && $attributeGroupId) {
		//			$attributeColis = new Attribute();
		//			$attributeColis->id_attribute_group = $attributeGroupId;
		//			$attributeColis->name = createMultiLangField("Colis");
		//			$attributeColis->save();
		//			$retour = $attributeColis->id;
		//		}
		//		return $retour;

	}

	/**
	 * Initialisation des labels
	 * @throws Exception
	 */
	public void initLabels() throws Exception {
		
		EntityTransaction transaction = taCorrespondanceIDBoutiqueDAO.getEntityManager().getTransaction();
		taCorrespondanceIDBoutiqueDAO.begin(transaction);
		
		/**********************************************
		 *  				labels
		 *********************************************/
		//List<TaLabelArticle> l = new TaLabelArticleDAO().selectAll();
		System.out.println("Importation des labels");
		//		$featureLabelId = initialiseLabelFeature();
		//		$map_label_bdg_presta = array();
		//		$map_label_bdg_presta = chargeId($fichierIdLabel);

		int id = 0;
		WSPrestaUtil<ProductFeatureValues> util = new WSPrestaUtil<ProductFeatureValues>(ProductFeatureValues.class);
		String strURL = util.getBaseURI()+"/product_feature_values";
		for (TaLabelArticle taLabelArticle : listeLabelBDG) {
			id = TaCorrespondanceIDBoutique.chercheIdPresta(listeIdLabel,taLabelArticle.getIdLabelArticle());
			ProductFeatureValues val = null;
			if(id!=0) {
				//la valeur existe deja, on recupére son id presta pour la modifier
				val = util.get(strURL, id);
			} else {
				//nouvelle valeur
				val = new ProductFeatureValues();
			}
			val.setIdFeature(featureLabelId);
			List<Language> listeValue = new ArrayList<Language>();
			listeValue.add(new Language(1,taLabelArticle.getLibelleLabelArticle()));
			val.setValue(listeValue);

			if(id!=0) {
				util.update(strURL, val);//modif
			} else {
				val = util.create(strURL, val);//creation
				TaCorrespondanceIDBoutique a = new TaCorrespondanceIDBoutique(TaCorrespondanceIDBoutiqueDAO.TYPE_LABEL,taLabelArticle.getIdLabelArticle(), val.getId());
				taCorrespondanceIDBoutiqueDAO.enregistrerPersist(a);
				listeIdLabel.add(a);
				//listeIdLabel.add(new IdBdgPresta(taLabelArticle.getIdLabelArticle(), val.getId()));
				//marshallerListeID.marshal(listeIdLabel, idLabel);
			}
		}
		//	 	foreach($$json->labels as $a) {
		//	 		if($map_label_bdg_presta[$a->id]) { 
		//				//la valeur existe deja, on recupére son id presta pour la modifier
		//				$featureValue = new FeatureValueCore($map_label_bdg_presta[$a->id]);
		//			} else {
		//				//nouvelle valeur
		//				$featureValue = new FeatureValueCore();
		//			}
		//			/***********************************************/
		//			$featureValue->id_feature= $featureLabelId;
		//			$featureValue->value = createMultiLangField($a->libelle);
		//			$featureValue->save();
		//			/***********************************************/
		//			$map_label_bdg_presta[$a->id] = $featureValue->id;
		//			
		//			/*
		//			obj.put("id",taLabelArticle.getIdLabelArticle());
		//			obj.put("code",taLabelArticle.getCodeLabelArticle());
		//			obj.put("libelle",taLabelArticle.getLibelleLabelArticle());
		//			obj.put("description",taLabelArticle.getDesciptionLabelArticle());
		//			obj.put("image",taLabelArticle.getNomImageLabelArticle());
		//			*/
		//	 	}
		//	 	enregistreId($fichierIdLabel, $map_label_bdg_presta);
		
		taCorrespondanceIDBoutiqueDAO.commit(transaction);
	}

	/**
	 * Initialisation des catégories
	 * @param monitor
	 * @throws Exception
	 */
	public void initCategories(IProgressMonitor monitor) throws Exception {
		
		if(monitor!=null) {
			monitor.setTaskName("Initialisation des catégories.");
		}
		
		EntityTransaction transaction = taCorrespondanceIDBoutiqueDAO.getEntityManager().getTransaction();
		taCorrespondanceIDBoutiqueDAO.begin(transaction);
		
		/*******************************************
		 * 			Categories
		 *******************************************/
		System.out.println("Importation des catégories");
		//listeCategorieArticleBDG = new TaCategorieArticleDAO().selectAll();
		
		//		$map_categorie_bdg_presta = array();
		//		//var_dump($map_categorie_bdg_presta);
		//		$map_categorie_bdg_presta = chargeId($fichierIdCategorie);
		//var_dump($map_categorie_bdg_presta);

		int id = 0;
		WSPrestaUtil<Categories> util = new WSPrestaUtil<Categories>(Categories.class);
		String strURL = util.getBaseURI()+"/categories";

		int i = 1;
		List<Language> listeDescription = new ArrayList<Language>();
		List<Language> listeLinkRewrite = new ArrayList<Language>();
		List<Language> listeName = new ArrayList<Language>();
		for (TaCategorieArticle taCategorieArticle : listeCategorieArticleBDG) {
			if(monitor!=null) {
				monitor.setTaskName("Création/mise à jour de la catégorie : "+i+"/"+listeCategorieArticleBDG.size());
				i++;
			}
			id = TaCorrespondanceIDBoutique.chercheIdPresta(listeIdCategorie,taCategorieArticle.getIdCategorieArticle());
			Categories val = null;
			if(id!=0) {
				//la valeur existe deja, on recupére son id presta pour la modifier
				val = util.get(strURL, id);
				logger.info("Mise à jour de la catégorie : "+taCategorieArticle.getCodeCategorieArticle()+" ID BDG "+taCategorieArticle.getIdCategorieArticle()+" ID Presta "+id) ;
			} else {
				//nouvelle valeur
				val = new Categories();
				//val.setIdParent(util.get(strURL, idCategorieMereDefaut));
				logger.info("Création de la catégorie : "+taCategorieArticle.getCodeCategorieArticle()+" ID BDG "+taCategorieArticle.getIdCategorieArticle()) ;
			}
			val.setIdParent(util.get(strURL, idCategorieMereDefaut));
			//val.setIdParent(1);
			val.setActive(true);
			//val.setIsRootCategory(true);
			listeDescription = new ArrayList<Language>();
			listeDescription.add(new Language(1,taCategorieArticle.getDesciptionCategorieArticle()));
			val.setDescription(listeDescription);

			listeLinkRewrite = new ArrayList<Language>();
			listeLinkRewrite.add(new Language(1,taCategorieArticle.getUrlRewritingCategorieArticle()));
			val.setLinkRewrite(listeLinkRewrite);

			listeName = new ArrayList<Language>();
			listeName.add(new Language(1,taCategorieArticle.getLibelleCategorieArticle()));
			val.setName(listeName);

			if(id!=0) {
				util.update(strURL, val);//modif
			} else {
				val = util.create(strURL, val);//creation
				TaCorrespondanceIDBoutique a = new TaCorrespondanceIDBoutique(TaCorrespondanceIDBoutiqueDAO.TYPE_CATEGORIE,taCategorieArticle.getIdCategorieArticle(), val.getId());
				taCorrespondanceIDBoutiqueDAO.enregistrerPersist(a);
				listeIdCategorie.add(a);
				//listeIdCategorie.add(new IdBdgPresta(taCategorieArticle.getIdCategorieArticle(), val.getId()));
				//marshallerListeID.marshal(listeIdCategorie, idCategorie);
			}

			if(taCategorieArticle.getCheminImageCategorieArticle()!=null && !taCategorieArticle.getCheminImageCategorieArticle().equals("")) {
				//TaImageArticle imageRetaille = null;
				String cheminImageAEnovyer = taCategorieArticle.getCheminImageCategorieArticle();


				//===== Ajout image article: POST ======
				//String img = "/home/nicolas/echappee_bio/echappee bio/images_test_echapeebio/img_article/6h.jpg";
				String img = cheminImageAEnovyer;
				File file = new File(img);
				if(!file.exists()) {
					System.err.println(img);
				}
				String urlImage = util.getBaseURI()+"/images/categories/"+val.getId();
				//UtilHTTP.put(urlImage, "image", input2 );
				Map<String, String> param = new HashMap<String, String>();
				param.put("Content-Type", "image/jpeg");
				param.put("Content-Length", "" + file.length());

				//BufferedInputStream b;
				InputStream f =  new BufferedInputStream(new FileInputStream(file));
				byte[] r = readAndClose(f);

				param.put("image", new String(r));
				//UtilHTTP.post(urlImage, param );
				UtilHTTP.postImage(urlImage, param, file);
				//					FileUtils.copyFile(
				//							new File(cheminImageAEnovyer), 
				//							new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE)+"/"+objInterne.get("image")));
				//					arrayImagesArticle.add(objInterne);

			}
			if(monitor!=null) {
				monitor.worked(1);
			}
		}

		i=1;
		if(monitor!=null) {
			monitor.setTaskName("Mise à jour de la hiérachie des catégories : "+i+"/"+listeCategorieArticleBDG.size());
		}
		Categories tmp = null;
		for (TaCategorieArticle taCategorieArticle : listeCategorieArticleBDG) {
			if(monitor!=null) {
				monitor.setTaskName("Mise à jour de la catégorie : "+i+"/"+listeCategorieArticleBDG.size());
				i++;
			}
			tmp = util.get(strURL, TaCorrespondanceIDBoutique.chercheIdPresta(listeIdCategorie,taCategorieArticle.getIdCategorieArticle()));
			if(taCategorieArticle.getCategorieMereArticle()!=null) {
				//tmp.setIdParent(listeIdCategorie.chercheIdPresta(taCategorieArticle.getCategorieMereArticle().getIdCategorieArticle()));
				tmp.setIdParent(util.get(strURL, TaCorrespondanceIDBoutique.chercheIdPresta(listeIdCategorie,taCategorieArticle.getCategorieMereArticle().getIdCategorieArticle())));
				
			} else {
				//tmp.setIdParent(null);
				tmp.setIsRootCategory(true);
			}
			util.update(strURL, tmp);
			if(monitor!=null) {
				monitor.worked(1);
			}
		}
		
		taCorrespondanceIDBoutiqueDAO.commit(transaction);

	}

	/**
	 * Synchronisation (ajout/modification) de la liste des articles récupérée dans <code>{@link #preparationExportation()}</code>
	 * @param monitor
	 * @throws Exception
	 */
	public void synchroProducts(IProgressMonitor monitor) throws Exception {
		/*
		 * boucle sur les produits ajoutés ou modifié depuis la derniere synchro
		 * et ajout ou modification sur le serveur
		 */
		//listeArticleBDG = new TaArticleDAO().selectAll();
		int i = 1;
		for (TaArticle taArticle : listeArticleBDG) {
			if(monitor!=null) {
				monitor.setTaskName("Création/mise à jour de l'article "+i+"/"+listeArticleBDG.size()+" : "+taArticle.getCodeArticle());
				i++;
			}
			createProduct(taArticle);

			if(monitor!=null) {
				monitor.worked(1);
			}
		}
	}

	/**
	 * Récupération d'un produit prestashop à partir de son ID
	 * @param idProduct - id du produit dans prestashop
	 * @return
	 * @throws JAXBException
	 */
	public Products findProduct(int idProduct) throws JAXBException {
		WSPrestaUtil<Products> util = new WSPrestaUtil<Products>(Products.class);
		String strURL = util.getBaseURI()+"/products/";

		Products p = util.get(strURL, idProduct);

		return p;
	}
	
	/**
	 * Récupération d'une commande prestashop à partir de son ID
	 * @param idOrder - id de la commande dans prestashop
	 * @return
	 * @throws JAXBException
	 */
	public Orders findOrder(int idOrder) throws JAXBException {
		WSPrestaUtil<Orders> util = new WSPrestaUtil<Orders>(Orders.class);
		String strURL = util.getBaseURI()+"/orders/";

		Orders p = util.get(strURL, idOrder);

		return p;
	}
	
	/**
	 * Récupération d'un client prestashop à partir de son ID
	 * @param idCustomer - id de la commande dans prestashop
	 * @return
	 * @throws JAXBException
	 */
	public Customers findCustomer(int idCustomer) throws JAXBException {
		WSPrestaUtil<Customers> util = new WSPrestaUtil<Customers>(Customers.class);
		String strURL = util.getBaseURI()+"/customers/";

		Customers p = util.get(strURL, idCustomer);

		return p;
	}

	/**
	 * Création/modification d'un article BDG dans la boutique
	 * cf : gestBdd.php
	 * @param article - article Bureau de Gestion
	 * @throws Exception 
	 */
	public void createProduct(TaArticle article) throws Exception {

		EntityTransaction transaction = taCorrespondanceIDBoutiqueDAO.getEntityManager().getTransaction();
		
		try {
			taCorrespondanceIDBoutiqueDAO.begin(transaction);

			String strURL = utilProduct.getBaseURI()+"/products/";
			String strURLSpecificPrices = utilProduct.getBaseURI()+"/specific_prices/";
			String strURLCategories = utilProduct.getBaseURI()+"/categories/";
			String strURLStockAvailable = utilProduct.getBaseURI()+"/stock_availables/";

			if(article.getTaCatalogueWeb()!=null && LibConversion.intToBoolean(article.getTaCatalogueWeb().getExportationCatalogueWeb())) {

				Products newProduct = null;
				int id = 0;
				id = TaCorrespondanceIDBoutique.chercheIdPresta(listeIdArticle,article.getIdArticle());
				//id = 0; //pour les test on créer tout le temps pour l'instant
				if(id!=0) {
					//l'article existe deja, on recupére son id presta pour la modifier
					newProduct = utilProduct.get(strURL, TaCorrespondanceIDBoutique.chercheIdPresta(listeIdArticle,article.getIdArticle()));
					logger.info("Mise à jour de l'article : "+article.getCodeArticle()+" ID BDG "+article.getIdArticle()+" ID Presta "+id) ;
				} else {
					//nouvel article
					logger.info("Création de l'article : "+article.getCodeArticle()+" ID BDG "+article.getIdArticle());
					newProduct = new Products();
				}	

				if(article.getTaTva()!=null) {
					if(TaCorrespondanceIDBoutique.chercheIdPresta(listeIdTVA,article.getTaTva().getIdTva())!=0) {
						//il y a une correspondance entre l'id du code TVA BDG, et l'id d'une regle de taxe presta dans le fichier de mapping
						newProduct.setIdTaxRulesGroup(TaCorrespondanceIDBoutique.chercheIdPresta(listeIdTVA,article.getTaTva().getIdTva()));
					} else {
						//pas de correspondance => pas de taxe dans presta
						newProduct.setIdTaxRulesGroup(0);
					}
				}


				//newProduct.setId(0);
				List<Language> listeName = new ArrayList<Language>();
				listeName.add(new Language(1,article.getLibellecArticle()));
				newProduct.setName(listeName);

				if(article.getTaCatalogueWeb()!=null) {
					List<Language> listeLinkRewrite = new ArrayList<Language>();
					listeLinkRewrite.add(new Language(1,article.getTaCatalogueWeb().getUrlRewritingCatalogueWeb()));
					newProduct.setLinkRewrite(listeLinkRewrite);

					List<Language> listeDescription = new ArrayList<Language>();
					listeDescription.add(new Language(1,article.getTaCatalogueWeb().getDescriptionLongueCatWeb()));
					newProduct.setDescription(listeDescription);


					//			obj.put("catalogue",article.getTaCatalogueWeb().getExportationCatalogueWeb());
					//			obj.put("nouveaute",article.getTaCatalogueWeb().getNouveauteCatalogueWeb());
					//			obj.put("promo",article.getTaCatalogueWeb().getPromotionCatalogueWeb());
					//			obj.put("promo-colis",article.getTaCatalogueWeb().getPromotionU2CatalogueWeb());
					//			obj.put("special",article.getTaCatalogueWeb().getSpecialCatalogueWeb());
					//			obj.put("expediable",article.getTaCatalogueWeb().getExpediableCatalogueWeb());
				}

				//newProduct.setQuantity(0);
				newProduct.setPrice(1f);
				newProduct.setOutOfStock(1);
				//newProduct.setIdCategoryDefault(1);
				newProduct.setIdCategoryDefault(2);

				newProduct.setActive(true);
				newProduct.setAvailableForOrder(true);
				newProduct.setShowPrice(true);

				/***********************************************************************************************/

				newProduct.setReference(article.getCodeArticle());

				List<Language> listeDescriptionShort = new ArrayList<Language>();
				listeDescriptionShort.add(new Language(1,article.getLibellelArticle()));
				newProduct.setDescriptionShort(listeDescriptionShort);

				//		obj.put("famille",article.getTaFamille()!=null?article.getTaFamille().getIdFamille():null);
				//		obj.put("tva",article.getTaTva()!=null?article.getTaTva().getIdTva():null);

				newProduct.setHeight(LibConversion.BigDecimalToFloat(article.getHauteur()));
				newProduct.setWidth(LibConversion.BigDecimalToFloat(article.getLongueur()));
				newProduct.setWeight(LibConversion.BigDecimalToFloat(article.getPoids()));


				if(article.getTaPrix()!=null) { //prix par defaut ===> prix unitaire
					//				newProduct.setUnitPrice(LibConversion.BigDecimalToFloat(article.getTaPrix().getPrixPrix()));
					//				newProduct.setPrice(LibConversion.BigDecimalToFloat(article.getTaPrix().getPrixPrix()));

					/*
					 * http://www.prestashop.com/forums/topic/163561-modification-product-webservices/
					 * le champ "price" semble être HT en écriture et TTC en lecture, ce qui fait qu'en insérant un prix HT,
					 * prestashop lit et affiche un prix TTC,
					 * Donc pour l'instant, en attendant mieux il faut enlever la TVA au prix HT et prestashop la rajoutera
					 * On envoie : HT/(1+taux TVA/100)
					 */
					newProduct.setPrice(LibConversion.BigDecimalToFloat(
							article.getTaPrix().getPrixPrix().divide(
									new BigDecimal(1).add(
											article.getTaTva().getTauxTva()
											.divide(new BigDecimal(100),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
											,MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
											,MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP)
							)
							);

				} else {
					newProduct.setUnitPrice(0f);
					newProduct.setPrice(0f);
				}

				//Products newProducReturn = util.create(strURL, newProduct);
				Products newProducReturn = null;
				if(id!=0) {
					utilProduct.update(strURL, newProduct);//modif
					newProducReturn = utilProduct.get(strURL, id);
				} else {
					newProducReturn = utilProduct.create(strURL, newProduct);//creation
					TaCorrespondanceIDBoutique a = new TaCorrespondanceIDBoutique(TaCorrespondanceIDBoutiqueDAO.TYPE_ARTICLE,article.getIdArticle(), newProducReturn.getId());
					taCorrespondanceIDBoutiqueDAO.enregistrerPersist(a);
					listeIdArticle.add(a);
					//marshallerListeID.marshal(listeIdArticle, idArticle);
				}

				//StockAvailable stock = utilStockAvailables.get(strURLStockAvailable, newProducReturn.getId());
				StockAvailable stock = utilStockAvailables.get(strURLStockAvailable, newProducReturn.getAssociations().getStocks().get(0).getId()); //premiere valeur si pas de déclinaison, sinon chercher le product_attribute correspondant
				stock.setQuantity(quantiteDefautArticle);
				stock.setDependsOnStock(false);
				stock.setOutOfStock(article.getStockMinArticle().intValue());
				//stock.setIdProductAttribute(0); //pas de déclinaison
				utilStockAvailables.update(strURLStockAvailable, stock);//modif

				if(utiliseSecondPrix) {
					if(article.getTaPrixes()!=null) { //recherche du 2eme prix ===> prix autre conditionnement ==> colis
						boolean trouve = false;
						Iterator<TaPrix> ite = article.getTaPrixes().iterator();
						TaPrix prix = null;
						while(ite.hasNext() && !trouve) {
							prix = ite.next();
							if(article.getTaPrix().getIdPrix()!=prix.getIdPrix()) {
								//Utilisation de prix spécifiques ou de déclinaison pour le 2ème prix

								if(!utiliseDeclinaison) {
									//--PRIX SPECIFIQUE
									deleteSpecificPrices(newProducReturn.getId());

									SpecificPrices sp = new SpecificPrices();

									sp.setIdGroup(0);
									sp.setIdCustomer(0);
									sp.setIdCountry(0);
									sp.setIdCurrency(0);
									sp.setIdShop(0);
									sp.setIdCart(0);

									sp.setIdProduct(newProducReturn.getId());
									if(article.getTaRapportUnite()!=null) {
										sp.setFromQuantity(article.getTaRapportUnite().getRapport().intValue());
									}



									if(reductionColis!=0f) {
										sp.setReductionType("percentage");
										sp.setReduction(reductionColis);
										sp.setPrice(-1f);
									} else {
										sp.setReductionType("amount");
										sp.setPrice(prix.getPrixPrix().floatValue());
									}

									sp.setFrom(null); //'0000-00-00'
									sp.setTo(null); //'0000-00-00'

									utilSpecificPrices.create(strURLSpecificPrices, sp);

									//le 1er prix different du prix par defaut
									//							obj.put("prix-ht-colis",prix.getPrixPrix());
									//							obj.put("prix-ttc-colis",prix.getPrixttcPrix());

									//							if(prix.getTaUnite()!=null) {
									//								obj.put("hauteur-colis",prix.getTaUnite().getHauteur());
									//								obj.put("longueur-colis",prix.getTaUnite().getLongueur());
									//								obj.put("largeur-colis",prix.getTaUnite().getLargeur());
									//								obj.put("poids-colis",prix.getTaUnite().getPoids());
									//							}

									trouve = true;
								} else {
									//--DECLINAISON POUR LE SECOND PRIX
								}
							}
						}
					}

				}
				if(article.getTaImageArticles()!=null) {
					TaImageArticle imageRetaille = null;
					String cheminImageAEnovyer = null;

					//suppression de toutes les imges de l'article avant envoi
					deleteImages(newProducReturn);

					for (TaImageArticle image : article.getTaImageArticles()) {
						if(image.getImageOrigine()==null) { //ce n'est pas une image retaillé

							if(!image.getTaImagesGenere().isEmpty()) {
								imageRetaille = image.getTaImagesGenere().iterator().next();
								cheminImageAEnovyer = utilPreferenceImage.cheminCompletImageArticle(imageRetaille,true);
							} else {
								//normalement l'image est deja assez petite et n'avais pas besoin d'être retaillé, faire une vérif ?
								cheminImageAEnovyer = utilPreferenceImage.cheminCompletImageArticle(image);
							}
							//===== Ajout image article: POST ======
							String img = cheminImageAEnovyer;
							Images i = utilProduct.createImageProduct(utilProduct.getBaseURI()+"/images/products/"+newProducReturn.getId(),img);

							if(image.getDefautImageArticle()) { //on garde la propriété "image par défaut" de l'image originale
								newProducReturn.setIdDefaultImage(i.getId());
								//util.update(strURL, newProducReturn); //mis à jour à la fin de la boucle por cet article
							}
						}

					}
				}

				if(article.getTaLabelArticles()!=null && article.getTaLabelArticles().size()>0) {
					WSPrestaUtil<ProductFeatureValues> util = new WSPrestaUtil<ProductFeatureValues>(ProductFeatureValues.class);
					String strURLLabel = util.getBaseURI()+"/product_feature_values";

					//suppression des anciennes associations pour la caractéristique "Label"
					if(newProducReturn!=null && newProducReturn.getAssociations()!=null && newProducReturn.getAssociations().getFeatures()!=null) {
						for(ProductAssociationFeature pasf : newProducReturn.getAssociations().getFeatures() ) {
							if(pasf.getId()==featureLabelId) { //on est sur une caractéristique "Label"
								util.delete(strURLLabel+"/", pasf.getIdFeatureValue());
							}
						}
					}

					//Création d'une valeur de caractéristique label, valeur "dynamique"
					ProductFeatureValues pfv = new ProductFeatureValues();
					pfv.setCustom(true);

					String tousLesLabels = "";
					int i = 0;
					for (TaLabelArticle taLabelArticle : article.getTaLabelArticles()) {
						if(i!=0) tousLesLabels += "|";
						tousLesLabels += taLabelArticle.getCodeLabelArticle();
						i++;
					}

					List<Language> listeLabel = new ArrayList<Language>();
					listeLabel.add(new Language(1,tousLesLabels));
					pfv.setValue(listeLabel);

					//ProductFeatureValues val = null;
					//val = util.get(strURLLabel, featureLabelId);

					pfv.setIdFeature(featureLabelId);
					pfv = util.create(strURLLabel, pfv);

					//association avec le produit
					newProducReturn.getAssociations().getFeatures().clear();
					newProducReturn.getAssociations().getFeatures().add(new ProductAssociationFeature(featureLabelId,pfv.getId()));

				}

				if(article.getTaCategorieArticles()!=null) {
					//ajout des articles dans les catégories
					Categories c = null;
					//				for (TaCategorieArticle taCategorieArticle : article.getTaCategorieArticles()) {
					//					c= utilCategories.get(strURLCategories, TaCorrespondanceIDBoutique.chercheIdPresta(listeIdCategorie,taCategorieArticle.getIdCategorieArticle()));
					//					c.getAssociationProducts().clear();
					//					//c.getAssociationProducts().add(new ProductsListValue(newProducReturn.getId()));
					//					c.getAssociationProducts().add(new ProductsCategoryAssociationValue(newProducReturn.getId()));
					//					utilCategories.update(strURLCategories, c);
					//				}
					//ajout des catégories aux articles
					newProducReturn.getAssociations().getCategories().clear();
					for (TaCategorieArticle taCategorieArticle : article.getTaCategorieArticles()) {
						c = utilCategories.get(strURLCategories, TaCorrespondanceIDBoutique.chercheIdPresta(listeIdCategorie,taCategorieArticle.getIdCategorieArticle()));
						newProducReturn.getAssociations().getCategories().add(new ProductAssociationCategory(c.getId()));
						//c.getAssociationProducts().add(new ProductsCategoryAssociationValue(newProducReturn.getId()));
						//utilCategories.update(strURLCategories, c);
					}
				}

				utilProduct.update(strURL, newProducReturn);
			} else {
				//l'article ne doit plus être dans le catalogue

				int id = 0;
				id = TaCorrespondanceIDBoutique.chercheIdPresta(listeIdArticle,article.getIdArticle());
				if(id!=0) {
					//l'article existe deja, on recupére son id presta pour la modifier
					Products newProduct = utilProduct.get(strURL, id);
					newProduct.setActive(false);
					utilProduct.update(strURL, newProduct);
				}
			}

			taCorrespondanceIDBoutiqueDAO.commit(transaction);
			//} catch (Exception e) {
		} finally {
			if(transaction!=null && transaction.isActive()) {
				//pour être sur de bien enregistrer les correspondance entre les articles Prestashop et Bdg même si la procédure ne va pas jusqu'au bout
				//évite la création du doublon sur la boutique prestashop en cas plantage pendant le transfert.
				taCorrespondanceIDBoutiqueDAO.commit(transaction);
			}
		}
	}
	
	/**
	 * Supprime tous les "prix spécifiques" pour un produit à partir de son ID
	 * @param idProduct
	 * @throws JAXBException
	 */
	public void deleteSpecificPrices(int idProduct) throws JAXBException {
		WSPrestaUtil<SpecificPriceList> utilList = new WSPrestaUtil<SpecificPriceList>(SpecificPriceList.class);
		WSPrestaUtil<SpecificPrices> util = new WSPrestaUtil<SpecificPrices>(SpecificPrices.class);
		String strURLList = utilList.getBaseURI()+"/specific_prices?filter[id_product]="+idProduct;
		String strURL = utilList.getBaseURI()+"/specific_prices/";
		
		SpecificPriceList l = utilList.getQuery(strURLList);
		if(l!=null) {
			for (SpecificPricesListValue v : l.getSpecificPrices()) {
				util.delete(strURL, v.getId() );
			}
		}
		
	}
	
	//public void deleteImages(int idProduct) throws JAXBException {
	public void deleteImages(Products p) throws JAXBException {
		WSPrestaUtil<SpecificPriceList> utilList = new WSPrestaUtil<SpecificPriceList>(SpecificPriceList.class);
		WSPrestaUtil<SpecificPrices> util = new WSPrestaUtil<SpecificPrices>(SpecificPrices.class);
	//	String strURLList = utilList.getBaseURI()+"/images/products/"+idProduct;
		String strURL = utilList.getBaseURI()+"/images/products/"+p.getId()+"/";
		
		
		if(p.getAssociations()!=null && p.getAssociations().getImages()!=null) {
			for (ProductAssociationImage pai : p.getAssociations().getImages()) {
				
				util.delete(strURL, pai.getId() );
			}
		}
		
	}

	public void addLabelToProduct() {
	}

	public void addCategoryToProduct() {
	}

	public void createLabel() {
	}

	public void createCaracteristiqueForLabel() {
	}

	public void createTaCategorieArticle(TaCategorieArticle taCategorieArticle) throws JAXBException {

	}

	public Categories findCategory(int idCategory) throws JAXBException {

		WSPrestaUtil<Categories> util = new WSPrestaUtil<Categories>(Categories.class);
		String strURL = util.getBaseURI()+"/categories/";

		Categories cat = util.get(strURL, idCategory);

		return cat;
	}
	
	public Taxes findTax(int idTax) throws JAXBException {

		WSPrestaUtil<Taxes> util = new WSPrestaUtil<Taxes>(Taxes.class);
		String strURL = util.getBaseURI()+"/taxes/";

		Taxes tax = util.get(strURL, idTax);

		return tax;
	}

	public SpecificPrices createSpecificPrice(int idCategory) throws JAXBException {

		WSPrestaUtil<SpecificPrices> util = new WSPrestaUtil<SpecificPrices>(SpecificPrices.class);
		String strURL = util.getBaseURI()+"/specific_prices/";

		SpecificPrices cat = util.get(strURL, idCategory);

		return cat;
	}

	public void createTaLabelArticle(TaLabelArticle article) throws JAXBException {
		//		JSONArray arrayLabel = new JSONArray();
		//		for (TaLabelArticle taLabelArticle : listeLabel) {
		//			obj = new LinkedHashMap<String,Object>();
		//			obj.put("id",taLabelArticle.getIdLabelArticle());
		//			obj.put("code",taLabelArticle.getCodeLabelArticle());
		//			obj.put("libelle",taLabelArticle.getLibelleLabelArticle());
		//			obj.put("description",taLabelArticle.getDesciptionLabelArticle());
		//			obj.put("image",taLabelArticle.getNomImageLabelArticle());
		//			arrayLabel.add(obj);
		//			if(taLabelArticle.getCheminImageLabelArticle()!=null  && !taLabelArticle.getCheminImageLabelArticle().equals("")) {
		//				FileUtils.copyFile(
		//						new File(taLabelArticle.getCheminImageLabelArticle()), 
		//						new File(pathRepExportWebTmp+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL)+"/"+taLabelArticle.getNomImageLabelArticle()));
		//			}
		//		}
		//		objetGlobal.put("labels", arrayLabel);
	}

	public void createTaTva(TaTva article) throws JAXBException {
		//		JSONArray arrayTva = new JSONArray();
		//		for (TaTva taTva : listeTva) {
		//			obj = new LinkedHashMap<String,Object>();
		//			obj.put("id",taTva.getIdTva());
		//			obj.put("code",taTva.getCodeTva());
		//			obj.put("libelle",taTva.getLibelleTva());
		//			obj.put("num-cpt",taTva.getNumcptTva());
		//			obj.put("taux",taTva.getTauxTva());
		//			arrayTva.add(obj);
		//		}
		//		objetGlobal.put("tva", arrayTva);
	}

	/**
	   Read an input stream, and return it as a byte array.  
	   Sometimes the source of bytes is an input stream instead of a file. 
	   This implementation closes aInput after it's read.
	 */
	public byte[] readAndClose(InputStream aInput){
		//carries the data from input to output :    
		byte[] bucket = new byte[32*1024]; 
		ByteArrayOutputStream result = null; 
		try  {
			try {
				//Use buffering? No. Buffering avoids costly access to disk or network;
				//buffering to an in-memory stream makes no sense.
				result = new ByteArrayOutputStream(bucket.length);
				int bytesRead = 0;
				while(bytesRead != -1){
					//aInput.read() returns -1, 0, or more :
					bytesRead = aInput.read(bucket);
					if(bytesRead > 0){
						result.write(bucket, 0, bytesRead);
					}
				}
			}
			finally {
				aInput.close();
				//result.close(); this is a no-operation for ByteArrayOutputStream
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return result.toByteArray();
	}

	public List<TaCategorieArticle> getListeCategorieArticleBDG() {
		return listeCategorieArticleBDG;
	}

	public List<TaArticle> getListeArticleBDG() {
		return listeArticleBDG;
	}

	public CustomersList getListeCustomers() {
		return listeCustomers;
	}

	public OrderList getListeOrders() {
		return listeOrders;
	}

	public void setReductionColis(float reductionColis) {
		this.reductionColis = reductionColis;
	}

	public void setQuantiteDefautArticle(int quantiteDefautArticle) {
		this.quantiteDefautArticle = quantiteDefautArticle;
	}
	
	public List<Taxes> findAllTaxes() throws JAXBException {
		List<Taxes> l = new ArrayList<Taxes>();
		WSPrestaUtil<TaxList> utilTaxList= new WSPrestaUtil<TaxList>(TaxList.class);
		WSPrestaUtil<Taxes> utilTaxes= new WSPrestaUtil<Taxes>(Taxes.class);

		String strURLTaxList = utilTaxList.getBaseURI()+"/taxes";

		TaxList listeTaxes = utilTaxList.getQuery(strURLTaxList);

		Taxes tax = null;
		for (TaxListValue o : listeTaxes.getTaxes()) {
			tax = utilTaxes.get(strURLTaxList, o.getId());
			l.add(tax);
		}
		return l;
	}

}

