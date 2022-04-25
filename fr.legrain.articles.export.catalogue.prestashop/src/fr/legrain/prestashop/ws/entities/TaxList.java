package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class TaxList {

	@XmlElementWrapper(name="taxes")
	@XmlElement(name="tax")
	private List<TaxListValue> taxes = null;
	
	public TaxList() {
		
	}

	public List<TaxListValue> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<TaxListValue> taxes) {
		this.taxes = taxes;
	}
}