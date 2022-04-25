package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class CustomersList {

	@XmlElementWrapper(name="customers")
	@XmlElement(name="customer")
	private List<CustomersListValue> customers = null;
	
	public CustomersList() {
		
	}

	public List<CustomersListValue> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomersListValue> customers) {
		this.customers = customers;
	}
}