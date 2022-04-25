package fr.legrain.prestashop.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.entities.ProductOptions;

@XmlRootElement
public class ListeID {
	@XmlAttribute
	public String description = "";
	
	public List<IdBdgPresta> ids = new ArrayList<IdBdgPresta>();
	
	public int chercheIdPresta(int idBdg) {
		Iterator<IdBdgPresta> ite = ids.iterator();
		boolean trouve = false;
		IdBdgPresta tmp = null;
		int retour = 0;
		while(!trouve && ite.hasNext()) {
			tmp = ite.next();
			if(tmp.id_bdg == idBdg) {
				retour = tmp.id_presta;
				trouve = true;
			}
		}
		return retour;	
	}
	
	public int chercheIdBdg(int idPresta) {
		Iterator<IdBdgPresta> ite = ids.iterator();
		boolean trouve = false;
		IdBdgPresta tmp = null;
		int retour = 0;
		while(!trouve && ite.hasNext()) {
			tmp = ite.next();
			if(tmp.id_presta == idPresta) {
				retour = tmp.id_bdg;
				trouve = true;
			}
		}
		return retour;	
	}

}
