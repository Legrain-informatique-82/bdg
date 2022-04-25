package fr.legrain.prestashop.ws.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;

import org.eclipse.persistence.oxm.annotations.XmlPath;

public class OrderRowAssociation {
	
	@XmlList
	@XmlElementWrapper(name="order_rows")
	@XmlElement(name="order_row")
	private List<OrderRow> orderRow = new ArrayList<OrderRow>();
	
	@XmlPath("order_rows/@virtual_entity")
	private String virtual_entity = "true";
	
	@XmlPath("order_rows/@node_type")
	private String node_type = "order_row";

	public List<OrderRow> getOrderRow() {
		return orderRow;
	}

	public void setOrderRow(List<OrderRow> orderRow) {
		this.orderRow = orderRow;
	}
	
}
