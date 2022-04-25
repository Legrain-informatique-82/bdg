package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class SpecificPriceList {

	@XmlElementWrapper(name="specific_prices")
	@XmlElement(name="specific_price")
	private List<SpecificPricesListValue> specificPrices = null;
	
	public SpecificPriceList() {
		
	}

	public List<SpecificPricesListValue> getSpecificPrices() {
		return specificPrices;
	}

	public void setSpecificPrices(List<SpecificPricesListValue> specificPrices) {
		this.specificPrices = specificPrices;
	}
}