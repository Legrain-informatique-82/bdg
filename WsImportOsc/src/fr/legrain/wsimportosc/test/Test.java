package fr.legrain.wsimportosc.test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//import fr.legrain.articles.dao.TaArticleOsc;
//import fr.legrain.articles.dao.TaArticleOscDAO;

import fr.legrain.wsimportosc.client.EnvoyerInfosDB;
import fr.legrain.wsimportosc.client.EnvoyerInfosDBService;
import fr.legrain.wsimportosc.client.WsOrders;
import fr.legrain.wsimportosc.client.WsOrdersProducts;

public class Test {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
	
	
	
		// TODO Auto-generated method stub
//		String partcustomersFirstname = "eee"; 
//		String chaine = partcustomersFirstname.replaceAll("\\s", "_");
//		System.out.println(chaine);
		
		
//		TaArticleOscDAO taArticleOscDAO = new TaArticleOscDAO();
//		TaArticleOsc taArticleOsc = new TaArticleOsc();
//		taArticleOsc.setIdArticle(99);
//		taArticleOsc.setCodeArticle("99");
//		taArticleOsc.setLibellecArticle("99");
//		taArticleOsc.setStockMinArticle(BigDecimal.valueOf(1.00));
//		taArticleOscDAO.persist(taArticleOsc);
		float a = (float) 19.00;
		float b = (float) 19.0000;
//		if(a == b){
//			System.out.println("zzzz");
//		}
		List<String> s = new ArrayList<String>();
		s.add("e");
		boolean f = false;
		int aa = 0;
		for (String string : s) {
			//f = true;
			aa +=1;
			System.out.println(aa);
		}
		//if(f)System.out.println("dd");
		//System.out.println(aa);
		List<Integer> ss = new ArrayList<Integer>();
		ss.add(63);ss.add(71);ss.add(71);ss.add(72);ss.add(72);ss.add(72);
		List<Integer> ssss = new ArrayList<Integer>();
		float prix = (float)12.3746;  	
		float tax = (float)19.6000;
		float taxPr = (float)tax/100;
		System.out.println(taxPr);
		int quantity = 12;
		System.out.println((1+taxPr)*prix*12);
		int numl = 0;
		int orderId = 0;
		int orderIdV = 0;
		
//		String test1 = "12345678";
//		System.out.println("test1 is "+test1.length());
//		System.out.println(test1.substring(0, 7));
		
//		for (Integer integer : ss) {
//			if(!ssss.contains(integer)){
//				ssss.add(integer);
//				numl = 0;
//			}
//			else{
//				numl = numl +1;
//			}
//			System.out.println("-orderId--"+integer+"-numl--"+numl+"---");
//			
//			int aaa = 0;
//			if(orderIdV == 0){
//				orderIdV = integer;
//			}
//			orderId = integer;
//			if(orderId == orderIdV){
//				numl+=1;
//			}
//			if(orderId != orderIdV){
//				numl = 0;
//				orderIdV = orderId;
//			}
//			System.out.println("-orderId--"+orderId+"-numl--"+numl+"---");
//		}
		Locale myLocale = new Locale("fr","FR"); 
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		System.out.println(numberFormat.format(10.05));
	}
}
