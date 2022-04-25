package fr.legrain.prestashop.ws;

import javax.xml.bind.annotation.XmlAttribute;

public class IdBdgPresta {
	
	public IdBdgPresta() {
	}
	
	public IdBdgPresta(int id_bdg, int id_presta) {
		super();
		this.id_bdg = id_bdg;
		this.id_presta = id_presta;
	}

	@XmlAttribute
	public int id_bdg = 0;
	
	@XmlAttribute
	public int id_presta = 0;
}