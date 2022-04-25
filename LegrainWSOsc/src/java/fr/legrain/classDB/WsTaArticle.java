/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_article")
public class WsTaArticle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_article",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="is_article_osc",nullable=false)
    private Integer idArticleOsc;

    @Column(name="code_article",length=20,nullable=false)
    private String codeArticle;

    @Column(name="libellec_article",length=100,nullable=false)
    private String libellecArticle;

    @Column(name="libellel_article",length=255,nullable=false)
    private String libellelArticle;

    @Column(name="numcpt_article",length=8)
    private String numcptArticle;

    @Column(name="divers_article",length=255)
    private String diverArticle;

    @Column(name="stock_min_article",precision=15,scale=2)
    private BigDecimal stockMinArticle;

    @Column(name="quand_add")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date quandAdd;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_famille")
    private WsTaFamille wsTaFamille;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tva")
    private WsTaTva wsTaTva;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_tva")
    private WsTaTTva wsTaTTva;

    @OneToMany(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaLBonliv> wsTaLBonlivs = new HashSet<WsTaLBonliv>();

    @OneToMany(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaLDevis> wsTaLDevis = new HashSet<WsTaLDevis>();

    @OneToMany(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaLFacture> wsTaLFacture = new HashSet<WsTaLFacture>();

    @OneToMany(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaPrix> wsTaPrixs = new HashSet<WsTaPrix>();

    @OneToOne(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private WsTaRefPrix wsTaRefPrix;

    @OneToMany(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaReportStock> wsTaReportStock = new HashSet<WsTaReportStock>();

    @OneToMany(mappedBy="wsTaArticle",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaStock> wsTaStocks = new HashSet<WsTaStock>();

   
    public WsTaArticle() {
    }

    public WsTaArticle(Integer idArticleOsc,String codeArticle,String libellecArticle){
        this.idArticleOsc = idArticleOsc;
        this.codeArticle = codeArticle;
        this.libellecArticle = libellecArticle;
        
    }
    public WsTaArticle(Integer idArticleOsc, String codeArticle, String libellecArticle, String libellelArticle, 
                       String numcptArticle, String diverArticle, BigDecimal stockMinArticle, Date quandAdd,WsTaFamille wsTaFamille,
                       WsTaTva wsTaTva,WsTaTTva wsTaTTva) {
        this.idArticleOsc = idArticleOsc;
        this.codeArticle = codeArticle;
        this.libellecArticle = libellecArticle;
        this.libellelArticle = libellelArticle;
        this.numcptArticle = numcptArticle;
        this.diverArticle = diverArticle;
        this.stockMinArticle = stockMinArticle;
        this.quandAdd = quandAdd;
        this.wsTaFamille = wsTaFamille;
        this.wsTaTva = wsTaTva;
        this.wsTaTTva = wsTaTTva;
    }

    public Set<WsTaStock> getWsTaStocks() {
        return wsTaStocks;
    }

    public void setWsTaStocks(Set<WsTaStock> wsTaStocks) {
        this.wsTaStocks = wsTaStocks;
    }

    public Set<WsTaReportStock> getWsTaReportStock() {
        return wsTaReportStock;
    }

    public void setWsTaReportStock(Set<WsTaReportStock> wsTaReportStock) {
        this.wsTaReportStock = wsTaReportStock;
    }    

    public WsTaRefPrix getWsTaRefPrix() {
        return wsTaRefPrix;
    }

    public void setWsTaRefPrix(WsTaRefPrix wsTaRefPrix) {
        this.wsTaRefPrix = wsTaRefPrix;
    }

    
    public Set<WsTaPrix> getWsTaPrixs() {
        return wsTaPrixs;
    }

    public void setWsTaPrixs(Set<WsTaPrix> wsTaPrixs) {
        this.wsTaPrixs = wsTaPrixs;
    }

    public Set<WsTaLDevis> getWsTaLDevis() {
        return wsTaLDevis;
    }

    public void setWsTaLDevis(Set<WsTaLDevis> wsTaLDevis) {
        this.wsTaLDevis = wsTaLDevis;
    }

    public Set<WsTaLFacture> getWsTaLFacture() {
        return wsTaLFacture;
    }

    public void setWsTaLFacture(Set<WsTaLFacture> wsTaLFacture) {
        this.wsTaLFacture = wsTaLFacture;
    }

    
    public Set<WsTaLBonliv> getWsTaLBonlivs() {
        return wsTaLBonlivs;
    }

    public void setWsTaLBonlivs(Set<WsTaLBonliv> wsTaLBonlivs) {
        this.wsTaLBonlivs = wsTaLBonlivs;
    }

    public WsTaTTva getWsTaTTva() {
        return wsTaTTva;
    }

    public void setWsTaTTva(WsTaTTva wsTaTTva) {
        this.wsTaTTva = wsTaTTva;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getDiverArticle() {
        return diverArticle;
    }

    public void setDiverArticle(String diverArticle) {
        this.diverArticle = diverArticle;
    }

    public Integer getIdArticleOsc() {
        return idArticleOsc;
    }

    public void setIdArticleOsc(Integer idArticleOsc) {
        this.idArticleOsc = idArticleOsc;
    }

    public String getLibellecArticle() {
        return libellecArticle;
    }

    public void setLibellecArticle(String libellecArticle) {
        this.libellecArticle = libellecArticle;
    }

    public String getLibellelArticle() {
        return libellelArticle;
    }

    public void setLibellelArticle(String libellelArticle) {
        this.libellelArticle = libellelArticle;
    }

    public String getNumcptArticle() {
        return numcptArticle;
    }

    public WsTaTva getWsTaTva() {
        return wsTaTva;
    }

    public void setWsTaTva(WsTaTva wsTaTva) {
        this.wsTaTva = wsTaTva;
    }

    public void setNumcptArticle(String numcptArticle) {
        this.numcptArticle = numcptArticle;
    }

    public BigDecimal getStockMinArticle() {
        return stockMinArticle;
    }

    public void setStockMinArticle(BigDecimal stockMinArticle) {
        this.stockMinArticle = stockMinArticle;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    
    public WsTaFamille getWsTaFamille() {
        return wsTaFamille;
    }

    public void setWsTaFamille(WsTaFamille wsTaFamille) {
        this.wsTaFamille = wsTaFamille;
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
        if (!(object instanceof WsTaArticle)) {
            return false;
        }
        WsTaArticle other = (WsTaArticle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaArticle[id=" + id + "]";
    }

}
