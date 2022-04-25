package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class AddressesList {

	@XmlElementWrapper(name="addresses")
	@XmlElement(name="address")
	private List<AddressesListValue> addresses = null;
	
	public AddressesList() {
		
	}

	public List<AddressesListValue> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressesListValue> addresses) {
		this.addresses = addresses;
	}
}