/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_stock"
/*,uniqueConstraints=@UniqueConstraint(columnNames={"date_stock"})*/)
public class WsTaStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_stocks")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_stock_osc",nullable=false)
    private Integer idStockOsc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article_stock",nullable=false)
    private WsTaArticle wsTaArticle;

    @Column(name="mouv_stock",length=1,nullable=false)
    private String mouvStock;

    @Column(name="date_stock")
    @Temporal(TemporalType.DATE)
    private Date dateStock;

    @Column(name="libelle_stock",length=255)
    private String libelleStock;

    @Column(name="qte1_stock",precision=15,scale=2)
    private BigDecimal qte1Stock;

    @Column(name="un1_stock",length=20,nullable=false)
    private String un1Stock;

    @Column(name="qte2_stock",precision=15,scale=2)
    private BigDecimal qte2Stock;

    @Column(name="un2_stock",length=20,nullable=false)
    private String un2Stock;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;


    public WsTaStock() {
    }

    public Integer getIdStockOsc() {
        return idStockOsc;
    }

    public void setIdStockOsc(Integer idStockOsc) {
        this.idStockOsc = idStockOsc;
    }

    public String getMouvStock() {
        return mouvStock;
    }

    public void setMouvStock(String mouvStock) {
        this.mouvStock = mouvStock;
    }

    public BigDecimal getQte1Stock() {
        return qte1Stock;
    }

    public void setQte1Stock(BigDecimal qte1Stock) {
        this.qte1Stock = qte1Stock;
    }

    public BigDecimal getQte2Stock() {
        return qte2Stock;
    }

    public void setQte2Stock(BigDecimal qte2Stock) {
        this.qte2Stock = qte2Stock;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public String getUn1Stock() {
        return un1Stock;
    }

    public void setUn1Stock(String un1Stock) {
        this.un1Stock = un1Stock;
    }

    public String getUn2Stock() {
        return un2Stock;
    }

    public void setUn2Stock(String un2Stock) {
        this.un2Stock = un2Stock;
    }

    public WsTaArticle getWsTaArticle() {
        return wsTaArticle;
    }

    public void setWsTaArticle(WsTaArticle wsTaArticle) {
        this.wsTaArticle = wsTaArticle;
    }

    public Date getDateStock() {
        return dateStock;
    }

    public void setDateStock(Date dateStock) {
        this.dateStock = dateStock;
    }

    
    public String getLibelleStock() {
        return libelleStock;
    }

    public void setLibelleStock(String libelleStock) {
        this.libelleStock = libelleStock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaStock)) {
            return false;
        }
        WsTaStock other = (WsTaStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaStock[id=" + id + "]";
    }

}
