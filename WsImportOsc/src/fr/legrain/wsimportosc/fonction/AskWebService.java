package fr.legrain.wsimportosc.fonction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.IB_TABLES.SWT_IB_TA_ARTICLE;
import fr.legrain.gestCom.Module_Tiers.IB_Tables.SWT_IB_TA_TIERS;
import fr.legrain.wsimportosc.client.ClassWebService;
import fr.legrain.wsimportosc.client.EnvoyerInfosDB;
import fr.legrain.wsimportosc.client.EnvoyerInfosDBService;
import fr.legrain.wsimportosc.client.WsCustomers;
import fr.legrain.wsimportosc.client.WsOrders;
import fr.legrain.wsimportosc.client.WsOrdersProducts;
import fr.legrain.wsimportosc.client.WsProducts;
import fr.legrain.wsimportosc.client.ClassWebService.MapWsOrders.Entry;
import fr.legrain.wsimportosc.contants.ConstantWebClient;

public class AskWebService {

	EnvoyerInfosDBService envoyerInfosDBService = new EnvoyerInfosDBService();
	EnvoyerInfosDB envoyerInfosDB = envoyerInfosDBService.getEnvoyerInfosDBPort();
	private String messageError = null;
	private float SommeFactureHt = (float) 0.0;
	
	private float SommeFactureTtc = (float) 0.0;
	
	private ClassWebService sizeWsOrders;
	private String[] codeFactureOsc = null;
	private List<String> listCodeFactureOsc = new ArrayList<String>();
	private Map<String, Integer> mapCodeEtIdFactureOsc = new HashMap<String, Integer>();
	
	private Connection connectionFirebird = IB_APPLICATION.findDatabase().getJdbcConnection();

	
	public String obtenirInfoMap(){
		
		String infos = null;
		ClassWebService sizeWsOrders = envoyerInfosDB.envoyerInfos("genboucom","1234");
		int size = sizeWsOrders.getMapWsOrders().getEntry().size();
		Entry entry = (Entry) sizeWsOrders.getMapWsOrders().getEntry();
		for(int i =0;i<size;i++){
			entry.getValue().getCustomersId();
		}
//		for(int i=0;i<sizeWsOrders.getListWsOrders().size();i++){
//			sizeWsOrders.getListWsOrders().get(i).get
//		}
		return infos;
	}
	public int obtenirInfoWsorders(){
		ClassWebService sizeWsOrders = envoyerInfosDB.envoyerInfos("genboucom","1234");
		
		//return sizeWsOrders.getListWsOrders().size();
		//return sizeWsOrders.getMapWsOrders().getEntry().size();
		return sizeWsOrders.getListWsProducts().size();
	}
	
	public void getInfosOscommerce(String login,String password){
	
		sizeWsOrders = envoyerInfosDB.envoyerInfos(login,password);
		if(!sizeWsOrders.isFlag()){
			messageError = ConstantWebClient.MESSAGE_ERROR_LOGIN_OR_PASSWORD;
		}else if(sizeWsOrders.getListWsOrders().size() == 0){
			messageError = ConstantWebClient.MESSAGE_NO_INFOS_IMPORTER;
		}else{
			String messageTiers = "";
			String messageArticles = "";
			List<WsCustomers> listWsCustomers =  sizeWsOrders.getListWsCustomers();
			if(listWsCustomers.size() == 0) messageTiers = ConstantWebClient.MESSAGE_INFOS_NO_TIERS_IMPORTER;
			List<WsProducts> listWsProducts =  sizeWsOrders.getListWsProducts();
			if(listWsProducts.size() == 0) messageArticles = ConstantWebClient.MESSAGE_INFOS_NO_ARTICLES_IMPORTER;
			List<WsOrders> listWsOrders =  sizeWsOrders.getListWsOrders();
			codeFactureOsc = new String[listWsOrders.size()];
			List<WsOrdersProducts> listWsOrdersProducts =  sizeWsOrders.getListWsOrdersProducts(); 
			
			if(insertInfosTaTiersOsc(listWsCustomers) && insertInfosTaArticleOsc(listWsProducts) 
					&& insertInfosTaFactureOsc(listWsOrders,sizeWsOrders) && insertInfosTaLfactureOsc(listWsOrdersProducts)){
				messageError = ConstantWebClient.MESSAGE_SUCCESS_INFOS_IMPORTER + messageTiers + messageArticles;
			}
			
		}
	}
	
	public int insertInfosTaTelephoneOsc(String numeroTelephone){
		PreparedStatement preparedStatement = null;
		int idTelephone = 0;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_TELEPHONE_OSC);
			idTelephone = getIdTable(ConstantWebClient.SELECT_TA_TELEPHONE_OSC, ConstantWebClient.ID_TELEPHONE_OSC);
			preparedStatement.setInt(1, idTelephone);
			preparedStatement.setString(2, numeroTelephone);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return idTelephone;
	}
	
	public void insertInfosTaRTel(int idTelePhone,int idTiers){
		PreparedStatement preparedStatement = null;
		int idRTel = 0;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_R_TEL_OSC);
			idRTel = getIdTable(ConstantWebClient.SELECT_TA_R_TEL_OSC, ConstantWebClient.ID_R_TEL_OSC);
			preparedStatement.setInt(1, idRTel);
			preparedStatement.setInt(2, idTiers);
			preparedStatement.setInt(3, idTelePhone);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void insertInfosTaRTelTTelOsc(int idTelephone,int idTTel){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_R_TEL_T_TEL_OSC);
			int idRTelTTel = getIdTable(ConstantWebClient.SELECT_TA_R_TEL_T_TEL_OSC, ConstantWebClient.ID_R_TEL_T_TEL);
			preparedStatement.setInt(1, idRTelTTel);
			preparedStatement.setInt(2, idTelephone);
			preparedStatement.setInt(3, idTTel);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void insertInfosTaInfosFactureOsc(int idTiers, ClassWebService classWebService,int idFacture,String address1Fact,String address2Fact,
											 String address3Fact,String codePostalFact,String villeFact,String paysFact,String address1Liv,
											 String address2Liv,String address3Liv,String codePostalLiv,String villeLiv,String paysLiv){
		
		List<WsCustomers> listWsCustomers = classWebService.getListWsCustomers();
		WsCustomers wsCustomers = null;
		for (WsCustomers objetWsCustomers : listWsCustomers) {
			if(idTiers == objetWsCustomers.getCustomersId()){
				wsCustomers = objetWsCustomers;
				break;
			}
		}
		boolean flag = false;
		
		String codeTiers = getCodeTiersOsc(wsCustomers.getCustomersId(),wsCustomers.getCustomersFirstname());
		String codeCompta = getCodeCompta(codeTiers);
		String codeTCivilite  = wsCustomers.getCustomersGender();
		String compte = ConstantWebClient.COMPTE_TA_TIERS_OSC;
		String nomTiers = wsCustomers.getCustomersFirstname();
		String pronomTiers = wsCustomers.getCustomersLastname();
		String codeTEntite = ConstantWebClient.CODE_T_TIERS_OSC;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_INFOS_FACTURE_OSC);
			int idInfoFacture = getIdTable(ConstantWebClient.SELECT_TA_INFOS_FACTURE_OSC, ConstantWebClient.ID_INFOS_FACTURE_OSC);
			preparedStatement.setInt(1, idInfoFacture);
			preparedStatement.setInt(2, idFacture);
			preparedStatement.setString(3, address1Fact);
			preparedStatement.setString(4, address2Fact);
			preparedStatement.setString(5, address3Fact);
			preparedStatement.setString(6, codePostalFact);
			preparedStatement.setString(7, villeFact);
			preparedStatement.setString(8, paysFact);
			preparedStatement.setString(9, address1Liv);
			preparedStatement.setString(10, address2Liv);
			preparedStatement.setString(11, address3Liv);
			preparedStatement.setString(12, codePostalLiv);
			preparedStatement.setString(13, villeLiv);
			preparedStatement.setString(14, paysLiv);
			preparedStatement.setString(15,codeCompta);
			preparedStatement.setString(16,ConstantWebClient.COMPTE_TA_TIERS_OSC);
			preparedStatement.setString(17,nomTiers);
			preparedStatement.setString(18,pronomTiers);
			preparedStatement.setString(19,codeTCivilite);
			preparedStatement.setString(20,codeTEntite);
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//return flag;
		
	}
	
	public boolean insertInfosTaFactureOsc(List<WsOrders> listWsOrders,ClassWebService classWebService){
		boolean flag = false;
		int count = 0;
		for (WsOrders wsOrders : listWsOrders) {
			
			PreparedStatement preparedStatement = null; 
			int idFacture = wsOrders.getOrdersId();
			String codeFacture = ConstantWebClient.FACTURE+idFacture;
			codeFactureOsc[count] = codeFacture;
			listCodeFactureOsc.add(codeFacture);
			mapCodeEtIdFactureOsc.put(codeFacture, idFacture);
			Date dateFacture = convertXMLGregorianCalendarToDate(wsOrders.getDatePurchased());
			Date dateLivFacture = convertXMLGregorianCalendarToDate(wsOrders.getDatePurchased());
			String libelleFacture = ConstantWebClient.LIBELLE_FACTURE+idFacture;
			int idTiers = wsOrders.getCustomersId();
			
			String address1Bureau = wsOrders.getCustomersCompany();
			String address2Bureau = wsOrders.getCustomersStreetAddress();
			String address3Bureau = wsOrders.getCustomersSuburb();
			String codePostalBureau = wsOrders.getCustomersPostcode();
			String villeBureau = wsOrders.getCustomersCity();
			if(villeBureau==null || villeBureau.trim().length()== 0)villeBureau = ConstantWebClient.INCONNU;
			String paysBureau = wsOrders.getCustomersCountry();
			int idAddressBureau = insertInforsTaAddressOsc(address1Bureau, address2Bureau, address3Bureau, codePostalBureau, villeBureau, paysBureau);
			
			insertInfosTaRAdrOsc(idTiers,idAddressBureau);
			insertInfosTaRAdrTAdrOsc(idAddressBureau, ConstantWebClient.ID_T_ADR_B);
			
			String address1Liv = wsOrders.getDeliveryCompany();
			String address2Liv = wsOrders.getDeliveryStreetAddress();
			String address3Liv = wsOrders.getDeliverySuburb();
			String codePostalLiv = wsOrders.getDeliveryPostcode();
			String villeLiv = wsOrders.getDeliveryCity();
			if(villeLiv==null || villeLiv.trim().length()==0)villeLiv = ConstantWebClient.INCONNU;
			String paysLiv = wsOrders.getDeliveryCountry();
			int idAddressLiv = insertInforsTaAddressOsc(address1Liv, address2Liv, address3Liv, codePostalLiv, villeLiv, paysLiv);
			insertInfosTaRAdrOsc(idTiers,idAddressLiv);
			insertInfosTaRAdrTAdrOsc(idAddressLiv, ConstantWebClient.ID_T_ADR_LIV);
			
			String address1Facture = wsOrders.getBillingCompany();
			String address2Facture = wsOrders.getBillingStreetAddress();
			String address3Facture = wsOrders.getBillingSuburb();
			String codePostalFacture = wsOrders.getBillingPostcode();
			String villeFacture = wsOrders.getBillingCity();
			if(villeFacture==null || villeFacture.trim().length()==0)villeFacture = ConstantWebClient.INCONNU;
			String paysFacture = wsOrders.getBillingCountry();
			int idAddressFacture = insertInforsTaAddressOsc(address1Facture, address2Facture, address3Facture, codePostalFacture, villeFacture, paysFacture);
			
			insertInfosTaRAdrOsc(idTiers,idAddressFacture);
			insertInfosTaRAdrTAdrOsc(idAddressFacture, ConstantWebClient.ID_T_ADR_FACT);
			
			int idEmail = insertInfosTaEmailOsc(wsOrders.getCustomersEmailAddress());
			insertInfosTaREmailOsc(idTiers, idEmail);
			
			try {
				preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_FACTURE_OSC);
				preparedStatement.setInt(1, idFacture);
				preparedStatement.setString(2, codeFacture);
				preparedStatement.setDate(3,new java.sql.Date(dateFacture.getTime()));
				preparedStatement.setDate(4,new java.sql.Date(dateLivFacture.getTime()));
				preparedStatement.setString(5, libelleFacture);
				preparedStatement.setInt(6, idAddressBureau);
				preparedStatement.setInt(7, idAddressLiv);
				preparedStatement.setInt(8, idTiers);
				preparedStatement.executeUpdate();
				flag = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			insertInfosTaInfosFactureOsc(idTiers, classWebService, idFacture, address1Facture, address2Facture, address3Facture, 
										 codePostalFacture, villeFacture, paysFacture, address1Liv, address2Liv, address3Liv, 
										 codePostalLiv, villeLiv, paysLiv);
			count++;
		}
		return flag;
	}
	
	public boolean insertInfosTaLfactureOsc(List<WsOrdersProducts> listWsOrdersProducts){
		boolean flag = false;
		int numLineFacture = 0;
		List<Integer> verfierNumLine = new ArrayList<Integer>();
		
		for (WsOrdersProducts wsOrdersProducts : listWsOrdersProducts) {
			/*** pour trouver num_ligne_facture ***/
			if(!verfierNumLine.contains(wsOrdersProducts.getOrdersId())){
				verfierNumLine.add(wsOrdersProducts.getOrdersId());
				numLineFacture = 0;
			}else{
				numLineFacture +=1;
			}
			
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_L_FACTURE_OSC);
				preparedStatement.setInt(1, wsOrdersProducts.getOrdersProductsId());
				preparedStatement.setInt(2, wsOrdersProducts.getOrdersId());
				preparedStatement.setInt(3, ConstantWebClient.ID_T_LIGNE_H);
				preparedStatement.setInt(4, wsOrdersProducts.getProductsId());
				preparedStatement.setInt(5, numLineFacture);
				preparedStatement.setString(6, wsOrdersProducts.getProductsName());
				preparedStatement.setBigDecimal(7, new BigDecimal(wsOrdersProducts.getProductsQuantity()));
				preparedStatement.setBigDecimal(8, BigDecimal.valueOf(wsOrdersProducts.getFinalPrice()));
				preparedStatement.setBigDecimal(9, BigDecimal.valueOf(wsOrdersProducts.getProductsTax()));
				String codeTvaLFacture = getCodeTva(wsOrdersProducts.getProductsTax());
				preparedStatement.setString(10,codeTvaLFacture);
				preparedStatement.setString(11,ConstantWebClient.CODE_T_TVA_E);
				float mtHtLfacture = getMtHtLfacture(wsOrdersProducts.getFinalPrice(),wsOrdersProducts.getProductsQuantity());
				float mtTtcLfacture = getMtTtcLfacture(wsOrdersProducts.getFinalPrice(),wsOrdersProducts.getProductsQuantity(),
													  wsOrdersProducts.getProductsTax());
				
				preparedStatement.setBigDecimal(12, new BigDecimal(mtHtLfacture));
				preparedStatement.setBigDecimal(13, new BigDecimal(mtTtcLfacture));
				preparedStatement.executeUpdate();
				flag = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public void getSommeFactureHtEtSommeFactureTtc(List<WsOrdersProducts> listWsOrdersProducts, List<Integer> idFactureSelection){
		for (WsOrdersProducts wsOrdersProducts : listWsOrdersProducts) {
			int idFacture = wsOrdersProducts.getOrdersId();
			if(idFactureSelection.contains(idFacture)){
				float mtHtLfacture = getMtHtLfacture(wsOrdersProducts.getFinalPrice(),wsOrdersProducts.getProductsQuantity());
				float mtTtcLfacture = getMtTtcLfacture(wsOrdersProducts.getFinalPrice(),wsOrdersProducts.getProductsQuantity(),
													  wsOrdersProducts.getProductsTax());
				SommeFactureHt += mtHtLfacture;
				SommeFactureTtc += mtTtcLfacture;
			}
		}
	}
	
	public String getCodeTva(float tax){
		String codeTva = null;
		if(tax == ConstantWebClient.VALUE_TAUX_TVA_A3){
			codeTva = ConstantWebClient.CODE_TVA_A3;
		}
		if(tax == ConstantWebClient.VALUE_TAUX_TVA_A2){
			codeTva = ConstantWebClient.CODE_TVA_A2;
		}
		return codeTva;
	}
	/***pour calculer montant hors tax ligne facture***/
	public float getMtHtLfacture(float ProductFinalPrice,int ProductsQuantity){
		float mtHtLfacture = (float)0.0;
		mtHtLfacture = ProductFinalPrice * ProductsQuantity;
		return mtHtLfacture;
	}
	/***pour calculer montant ttc ligne facture***/
	public float getMtTtcLfacture(float ProductFinalPrice,int ProductsQuantity,float tax){
		float mtTtcLfacture = (float)0.0;
		float taxPourcentage = (float)(tax/100);
		mtTtcLfacture = (1+taxPourcentage)*ProductFinalPrice * ProductsQuantity;
		return mtTtcLfacture;
	}
	public void insertInfosTaRAdrTAdrOsc(int idAddress,int idTAdr){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_R_ADR_T_ADR_OSC);
			int idTaRAdrTadr = getIdTable(ConstantWebClient.SELECT_TA_R_ADR_T_ADR_OSC, ConstantWebClient.ID_R_ADR_T_ADR_OSC);
			preparedStatement.setInt(1, idTaRAdrTadr);
			preparedStatement.setInt(2, idTAdr);
			preparedStatement.setInt(3, idAddress);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void insertInfosTaRAdrOsc(int idTiers,int idAddress){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_R_ADR_OSC);
			int idTaRAdrOSC = getIdTable(ConstantWebClient.SELECT_TA_R_ADR_OSC,ConstantWebClient.ID_R_ADR_OSC);
			preparedStatement.setInt(1, idTaRAdrOSC);
			preparedStatement.setInt(2, idTiers);
			preparedStatement.setInt(3, idAddress);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean insertInfosTaREmailOsc(int idTiers,int idEmail) {
		boolean flag = false;
		int idREmail = 0;
		
		PreparedStatement preparedStatement = null;
		idREmail = getIdTable(ConstantWebClient.SELECT_TA_R_EMAIL_OSC, ConstantWebClient.ID_R_EMAIL_OSC);
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_R_EMAIL_OSC);
			preparedStatement.setInt(1, idREmail);
			preparedStatement.setInt(2, idTiers);
			preparedStatement.setInt(3, idEmail);
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public int insertInfosTaEmailOsc(String Email) {
		boolean flag = false;
		int idEmail = 0;
		if(Email==null || Email.trim().length()==0){
			Email = ConstantWebClient.INCONNU;
		}
		PreparedStatement preparedStatement = null;
		idEmail = getIdTable(ConstantWebClient.SELECT_TA_EMAIL_OSC, ConstantWebClient.ID_EMAIL_OSC);
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_EMAIL_OSC);
			preparedStatement.setInt(1, idEmail);
			preparedStatement.setString(2, Email);
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idEmail;
	}
	public int getIdTable(String querySql,String idNomChamp){
		int idTable = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connectionFirebird.createStatement();
			resultSet = statement.executeQuery(querySql);
			while (resultSet.next()) {
				idTable = resultSet.getInt(idNomChamp);
			}
			idTable = idTable+1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return idTable;
	}
	public int insertInforsTaAddressOsc(String address1,String address2,String address3,String codePostal,String ville,String pays){
		boolean flag = false;
		int idAddress = 0;
		PreparedStatement preparedStatement = null;
		try {
			
			idAddress = getIdTable(ConstantWebClient.SELECT_TA_ADRESSE_OSC,ConstantWebClient.ID_ADDRESS_OSC);
			
			preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_ADRESSE_OSC);
			preparedStatement.setInt(1, idAddress);
			preparedStatement.setString(2, address1);
			preparedStatement.setString(3, address2);
			preparedStatement.setString(4, address3);
			preparedStatement.setString(5, codePostal);
			preparedStatement.setString(6, ville);
			preparedStatement.setString(7, pays);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		return idAddress;
	}
	
	public Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar xmlGgregorianCalendar){
		Date date = null;
		GregorianCalendar gregorianCalendar= xmlGgregorianCalendar.toGregorianCalendar();
		date = gregorianCalendar.getTime();
		return date;
	}
	public boolean insertInfosTaArticleOsc(List<WsProducts> listWsProducts){
		boolean flag = false;
		if(listWsProducts.size() == 0){
			flag =true;
		}
		else{
			for (WsProducts wsProducts : listWsProducts) {
				PreparedStatement preparedStatement = null;
				int idArticle = wsProducts.getProductsId();
				String codeArticle = wsProducts.getProductsCode();
				String libellecArticle = wsProducts.getProductsName();
				float stockMinArticle = ConstantWebClient.STOCK_MIN_ARTICLE;
				BigDecimal.valueOf(stockMinArticle);
				try {
					preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_ARTICLE_OSC);
					preparedStatement.setInt(1, idArticle);
					preparedStatement.setString(2, codeArticle);
					preparedStatement.setString(3, libellecArticle);
					preparedStatement.setBigDecimal(4, BigDecimal.valueOf(stockMinArticle));
					preparedStatement.execute();
					flag = true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return flag;
		
	}
	
	public boolean insertInfosTaTiersOsc(List<WsCustomers> listWsCustomers) {
		boolean flag = false;
		if(listWsCustomers.size() == 0){
			flag = true;
		}
		else{
			for (WsCustomers wsCustomers : listWsCustomers) {
				//count+=1;
				PreparedStatement preparedStatement = null;
				int idTiers = wsCustomers.getCustomersId();
				String codeTiers = getCodeTiersOsc(wsCustomers.getCustomersId(),wsCustomers.getCustomersFirstname());
				String codeCompta = getCodeCompta(codeTiers);
				int idTCivilite  = findIdTCivilte(wsCustomers.getCustomersGender());
				String compte = ConstantWebClient.COMPTE_TA_TIERS_OSC;
				String nomTiers = wsCustomers.getCustomersFirstname();
				String pronomTiers = wsCustomers.getCustomersLastname();
				int idTTiers = ConstantWebClient.ID_T_TIERS_TA_TIERS_OSC;
				
				String numeroTelBureau = wsCustomers.getCustomersTelephone();
				if(numeroTelBureau !=null && numeroTelBureau.trim().length()!=0){
					int idTelephone = insertInfosTaTelephoneOsc(numeroTelBureau);
					insertInfosTaRTelTTelOsc(idTelephone, ConstantWebClient.ID_T_TEL_B);
					insertInfosTaRTel(idTelephone, idTiers);
				}
				String numeroTelFax = wsCustomers.getCustomersFax();
				if(numeroTelFax !=null && numeroTelFax.trim().length()!=0){
					int idTelephone = insertInfosTaTelephoneOsc(numeroTelFax);
					insertInfosTaRTelTTelOsc(idTelephone, ConstantWebClient.ID_T_TEL_FAX);
					insertInfosTaRTel(idTelephone, idTiers);
				}
				try {
					preparedStatement = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_TIERS_OSC);
					preparedStatement.setInt(1, idTiers);
					preparedStatement.setString(2, codeTiers);
					preparedStatement.setString(3, codeCompta);
					preparedStatement.setString(4, compte);
					preparedStatement.setString(5, nomTiers);
					preparedStatement.setString(6, pronomTiers);
					preparedStatement.setInt(7, idTCivilite);
					preparedStatement.setInt(8, idTTiers);
					preparedStatement.executeUpdate();
					flag = true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return flag;
	}

	/**trouver ID_T_CIVILITE sinon ajouter **/
	public int findIdTCivilte(String customersGender){
		int idTCivilite = 0;
		int count = 0;
		PreparedStatement preparedStatementSelect = null;
		ResultSet resultSetSelect = null;
		
		PreparedStatement preparedStatementInsert = null;
		
		try {
			preparedStatementSelect = connectionFirebird.prepareStatement(ConstantWebClient.SELECT_TA_T_CIVILITE);
			resultSetSelect = preparedStatementSelect.executeQuery();
			while (resultSetSelect.next()) {
				if(customersGender.equalsIgnoreCase(resultSetSelect.getString(ConstantWebClient.CODE_T_CIVILITE))){
					idTCivilite = resultSetSelect.getInt(ConstantWebClient.ID_T_CIVILITE);
				}
				count = resultSetSelect.getInt(ConstantWebClient.ID_T_CIVILITE);
			}
			if(idTCivilite == 0){
				idTCivilite = count+1;
				
				preparedStatementInsert = connectionFirebird.prepareStatement(ConstantWebClient.INSERT_TA_T_CIVILITE);
				preparedStatementInsert.setInt(1,idTCivilite);
				preparedStatementInsert.setString(2, customersGender);
				preparedStatementInsert.executeUpdate();
				preparedStatementInsert.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatementSelect.close();
				//preparedStatementInsert.close();
				resultSetSelect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return idTCivilite;
		
	}
	public String getCodeTiersOsc(int customersId,String customersFirstname){
	
		String codeTiers = null;
		codeTiers = String.valueOf(customersId) + getPartCustomersFirstname(customersFirstname);
		return codeTiers;
		
	}
	
	public String getCodeCompta(String codeTiers){
		String codeCompta = null;
		if(codeTiers.length()>7){
			codeCompta = codeTiers.substring(0,7);
			System.out.println(codeCompta);
		}else{
			codeCompta = codeTiers;
		}
		return codeCompta;
	}
	
	public String getPartCustomersFirstname(String customersFirstname){
		String partcustomersFirstname = null;
		String chaine = customersFirstname.replaceAll("\\s", "_");
		//System.out.println(chaine);
		if(chaine.length()>=5){
			partcustomersFirstname = chaine.substring(0,5);
		}else{
			partcustomersFirstname = chaine.substring(0);
		}
		return partcustomersFirstname.toUpperCase();
	}
	
	public String getMessageError() {
		return messageError;
	}
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	public Connection getConnectionFirebird() {
		return connectionFirebird;
	}
	public void setConnectionFirebird(Connection connectionFirebird) {
		this.connectionFirebird = connectionFirebird;
	}
	public void test(){
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connectionFirebird.createStatement();
			resultSet = statement.executeQuery(ConstantWebClient.SELECT_TA_T_CIVILITE_TEST);
			while (resultSet.next()) {
				System.out.println("********** "+resultSet.getString("CODE_T_CIVILITE")+" **********");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void importTaArticleOScVersBDG(){
		Statement statement = null;
		PreparedStatement preparedStatementOSC= null;
		PreparedStatement preparedStatementBDG= null;
		
		SWT_IB_TA_ARTICLE swt_ib_ta_article = new SWT_IB_TA_ARTICLE();
		String codeArticle = null;
		int idArticleOsc = 0;
		try {
			
			statement = connectionFirebird.createStatement();
			ResultSet resultSet = statement.executeQuery(ConstantWebClient.SELECT_TA_ARTICLE_OSC);
			while (resultSet.next()) {	
				try {
					idArticleOsc = resultSet.getInt("ID_ARTICLE");
					codeArticle = swt_ib_ta_article.genereCode();
					SWTArticle swtArticle = new SWTArticle();
					swtArticle.setCodeArticle(codeArticle);
					swtArticle.setLibellecArticle(resultSet.getString("LIBELLEC_ARTICLE"));
					swtArticle.setStockMinArticle(BigDecimal.valueOf(ConstantWebClient.STOCK_MIN_ARTICLE));
					swt_ib_ta_article.insertion();
					swt_ib_ta_article.enregistrement(swtArticle);
					
					preparedStatementBDG = connectionFirebird.prepareStatement(ConstantWebClient.SELECT_TA_ARTICLE);
					preparedStatementOSC = connectionFirebird.prepareStatement(ConstantWebClient.UPDATE_TA_ARTICLE_OSC);
					preparedStatementBDG.setString(1, codeArticle);
					ResultSet resultSetBDG = preparedStatementBDG.executeQuery();
					if(resultSet.next()){
						int idArticleBDG = resultSet.getInt("ID_ARTICLE");
						preparedStatementOSC.setInt(1, idArticleBDG);
						preparedStatementOSC.setString(2, codeArticle);
						preparedStatementOSC.setInt(3, idArticleOsc);
						preparedStatementOSC.executeUpdate();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatementOSC.close();
				preparedStatementBDG.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String[] getCodeFactureOsc() {
		return codeFactureOsc;
	}
	public void setCodeFactureOsc(String[] codeFactureOsc) {
		this.codeFactureOsc = codeFactureOsc;
	}
	public List<String> getListCodeFactureOsc() {
		return listCodeFactureOsc;
	}
	public void setListCodeFactureOsc(List<String> listCodeFactureOsc) {
		this.listCodeFactureOsc = listCodeFactureOsc;
	}
	public float getSommeFactureHt() {
		return SommeFactureHt;
	}
	public void setSommeFactureHt(float sommeFactureHt) {
		SommeFactureHt = sommeFactureHt;
	}
	public float getSommeFactureTtc() {
		return SommeFactureTtc;
	}
	public void setSommeFactureTtc(float sommeFactureTtc) {
		SommeFactureTtc = sommeFactureTtc;
	}
	public Map<String, Integer> getMapCodeEtIdFactureOsc() {
		return mapCodeEtIdFactureOsc;
	}
	public void setMapCodeEtIdFactureOsc(Map<String, Integer> mapCodeEtIdFactureOsc) {
		this.mapCodeEtIdFactureOsc = mapCodeEtIdFactureOsc;
	}
	public ClassWebService getSizeWsOrders() {
		return sizeWsOrders;
	}
	public void setSizeWsOrders(ClassWebService sizeWsOrders) {
		this.sizeWsOrders = sizeWsOrders;
	}
    
	
}
