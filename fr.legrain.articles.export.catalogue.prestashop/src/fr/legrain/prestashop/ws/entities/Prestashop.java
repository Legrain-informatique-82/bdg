package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="prestashop")
public class Prestashop {
//	private Addresses addresses;
	@XmlList
	@XmlElement(name="product")
	private List<Products> product;
	
//	public Addresses getAddresses() {
//		return addresses;
//	}
//	public void setAddresses(Addresses addresses) {
//		this.addresses = addresses;
//	}
	public List<Products> getProduct() {
		return product;
	}
	public void setProduct(List<Products> product) {
		this.product = product;
	}
}
