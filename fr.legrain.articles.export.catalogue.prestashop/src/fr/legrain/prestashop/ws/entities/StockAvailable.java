package fr.legrain.prestashop.ws.entities;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class StockAvailable {

	@XmlPath("stock_available/id/text()")
	private Integer id = null;	

	@XmlPath("stock_available/id_product/text()")
	private Integer idProduct;
	
	@XmlPath("stock_available/id_product_attribute/text()")
	private Integer idProductAttribute;

	@XmlPath("stock_available/id_shop/text()")
	private Integer idShop;

	@XmlPath("stock_available/id_shop_group/text()")
	private Integer idShopGroup;
	
	@XmlPath("stock_available/quantity/text()")
	private Integer quantity;

	@XmlJavaTypeAdapter( BooleanAdapter.class )
	@XmlPath("stock_available/depends_on_stock/text()")
	private Boolean dependsOnStock;
	
	@XmlPath("stock_available/out_of_stock/text()")
	private Integer outOfStock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public Integer getIdShop() {
		return idShop;
	}

	public void setIdShop(Integer idShop) {
		this.idShop = idShop;
	}

	public Integer getIdShopGroup() {
		return idShopGroup;
	}

	public void setIdShopGroup(Integer idShopGroup) {
		this.idShopGroup = idShopGroup;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getDependsOnStock() {
		return dependsOnStock;
	}

	public void setDependsOnStock(Boolean dependsOnStock) {
		this.dependsOnStock = dependsOnStock;
	}

	public Integer getOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(Integer outOfStock) {
		this.outOfStock = outOfStock;
	}

	public Integer getIdProductAttribute() {
		return idProductAttribute;
	}

	public void setIdProductAttribute(Integer idProductAttribute) {
		this.idProductAttribute = idProductAttribute;
	}
	
}
