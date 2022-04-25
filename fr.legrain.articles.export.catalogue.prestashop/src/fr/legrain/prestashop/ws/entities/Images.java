package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/*
 * The images
 */
@XmlRootElement(name="prestashop")
public class Images {
/*
 * Pas de schema
 */
	
	@XmlPath("image/id/text()")
	private int id = 1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
