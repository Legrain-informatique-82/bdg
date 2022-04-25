package fr.legrain.prestashop.ws.entities;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import fr.legrain.prestashop.ws.UtilHTTP;


public class StateAdapter extends XmlAdapter<Integer, States> {
	
	private static String base = "http://dev2.pageweb.fr/api/states/";

    @Override
    public Integer marshal(States target) throws Exception {
        return target.getId();
    }

    @Override
    public States unmarshal(Integer id) throws Exception {
    	//States target = new States();
        //target.setId(id);
    	
    	String result = UtilHTTP.get(base+id);
		Unmarshaller unmarshaller = JAXBContext.newInstance(States.class).createUnmarshaller();
		//unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringReader reader = new StringReader(result);
		States target = (States) unmarshaller.unmarshal(reader);
		
        return target;
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
