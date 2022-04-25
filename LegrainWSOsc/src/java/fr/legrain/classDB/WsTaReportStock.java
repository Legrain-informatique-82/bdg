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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_report_stock",
      uniqueConstraints={@UniqueConstraint(columnNames={"date_deb_report_stock","date_fin_report_stock"})})
public class WsTaReportStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_report_stock")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_report_stock_osc")
    private Integer idReportStockOsc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article",insertable=false,updatable=false)
    private WsTaArticle wsTaArticle;

    @Column(name="date_deb_report_stock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebReportStock;

    @Column(name="date_fin_report_stock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinReportStock;

    @Column(name="qte_report_stock",precision=15,scale=2)
    private BigDecimal qteReportStock;

    @Column(name="unite_report_stock",length=20,nullable=false)
    private String uniteReportStock;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    public WsTaReportStock() {
    }

    
    public WsTaArticle getWsTaArticle() {
        return wsTaArticle;
    }

    public void setWsTaArticle(WsTaArticle wsTaArticle) {
        this.wsTaArticle = wsTaArticle;
    }

    public Date getDateDebReportStock() {
        return dateDebReportStock;
    }

    public void setDateDebReportStock(Date dateDebReportStock) {
        this.dateDebReportStock = dateDebReportStock;
    }

    public Date getDateFinReportStock() {
        return dateFinReportStock;
    }

    public void setDateFinReportStock(Date dateFinReportStock) {
        this.dateFinReportStock = dateFinReportStock;
    }

    public Integer getIdReportStockOsc() {
        return idReportStockOsc;
    }

    public void setIdReportStockOsc(Integer idReportStockOsc) {
        this.idReportStockOsc = idReportStockOsc;
    }

    public BigDecimal getQteReportStock() {
        return qteReportStock;
    }

    public void setQteReportStock(BigDecimal qteReportStock) {
        this.qteReportStock = qteReportStock;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public String getUniteReportStock() {
        return uniteReportStock;
    }

    public void setUniteReportStock(String uniteReportStock) {
        this.uniteReportStock = uniteReportStock;
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
        if (!(object instanceof WsTaReportStock)) {
            return false;
        }
        WsTaReportStock other = (WsTaReportStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaReportStock[id=" + id + "]";
    }

}
