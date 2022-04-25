package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class OrderList {

	@XmlElementWrapper(name="orders")
	@XmlElement(name="order")
	private List<OrderListValue> orders = null;
	
	public OrderList() {
		
	}

	public List<OrderListValue> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderListValue> orders) {
		this.orders = orders;
	}
}