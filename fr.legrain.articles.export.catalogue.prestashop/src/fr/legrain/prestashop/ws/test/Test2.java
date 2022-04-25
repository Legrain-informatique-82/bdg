package fr.legrain.prestashop.ws.test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.legrain.prestashop.ws.IdBdgPresta;
import fr.legrain.prestashop.ws.ListeID;

public class Test2 {

	/**
	 * @param args
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws JAXBException {
		String basePath = "/tmp/";
		//id_tva.txt	=> Correspondance entre les code TVA du site et celle du BDG {"4":2,"5":1}
		//mapIdTVA = new HashMap<String,String>();
		ListeID mapIdTVA = new ListeID();
		File idTVA = new File(basePath+"id_tva.txt");
		if(idTVA.exists()) {
//			InputStream is = new FileInputStream(idTVA);
//			byte[] content = readAndClose(is);
//			String c = new String(content);
			
			Unmarshaller unmarshaller = JAXBContext.newInstance(ListeID.class).createUnmarshaller();
			mapIdTVA = (ListeID) unmarshaller.unmarshal(idTVA);
			System.out.println(mapIdTVA);
		} else {
			mapIdTVA.description = "ID TVA";
			mapIdTVA.ids.add(new IdBdgPresta(4, 2));
			mapIdTVA.ids.add(new IdBdgPresta(5, 1));
			Marshaller marshaller = JAXBContext.newInstance(ListeID.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(mapIdTVA, idTVA);
			
		}

	}

}
