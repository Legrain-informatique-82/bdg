package fr.legrain.prestashop.ws.entities;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import fr.legrain.prestashop.ws.UtilHTTP;
import fr.legrain.prestashop.ws.WSPrestashopConfig;


public class CategoryAdapter extends XmlAdapter<Integer, Categories> {
	
	//private static String base = "http://dev2.pageweb.fr/api/categories/";

    @Override
    public Integer marshal(Categories target) throws Exception {
        return target.getId();
    }

    @Override
    public Categories unmarshal(Integer id) throws Exception {
    	//States target = new States();
        //target.setId(id);
    	
    	if(id!=0) {
    		//String result = UtilHTTP.get(base+id);
    		String result = UtilHTTP.get(WSPrestashopConfig.getConfig().getBaseURI()+"/categories/"+id);
    		Unmarshaller unmarshaller = JAXBContext.newInstance(Categories.class).createUnmarshaller();
    		//unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    		StringReader reader = new StringReader(result);
    		Categories target = (Categories) unmarshaller.unmarshal(reader);

    		return target;
    	} else {
    		// pas de catégorie (surement pas de catégorie mère),
    		//ATTENTION aux appels récursif avec les catégories mère
    		return null;
    	}
    }

}

//public class StateAdapter extends XmlAdapter<String, States> {
//
//    @Override
//    public String marshal(States target) throws Exception {
//        return String.valueOf(target.getId());
//    }
//
//    @Override
//    public States unmarshal(String id) throws Exception {
//    	States target = new States();
//        //target.setId(id);
//        return target;
//    }
//
//}
