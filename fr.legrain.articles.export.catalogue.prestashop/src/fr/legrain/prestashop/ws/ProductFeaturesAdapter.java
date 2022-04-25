package fr.legrain.prestashop.ws;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import fr.legrain.prestashop.ws.entities.ProductFeature;
import fr.legrain.prestashop.ws.entities.ProductFeatures;


public class ProductFeaturesAdapter extends XmlAdapter<String, ProductFeatures> {
	
	String empty = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><prestashop xmlns:xlink=\"http://www.w3.org/1999/xlink\"><product_features></product_features></prestashop>";

	@Override
	public ProductFeatures unmarshal(String v) throws Exception {
		ProductFeatures list = new ProductFeatures();
		
		if(v.equals(empty)) {
			return list;
		}
		
//		WSPrestaUtil<ProductFeatures> util = new WSPrestaUtil<ProductFeatures>(ProductFeatures.class);
//		String strURL = util.getBaseURI()+"/product_features?filter[name]=Label";
//		
//			
//		ProductFeaturesList p = utilList.getQuery(strURL);
		return null;
	}

	@Override
	public String marshal(ProductFeatures v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
   
}
