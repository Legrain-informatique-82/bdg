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
@Table(name="ws_ta_l_devis")
public class WsTaLDevis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_l_devis")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_l_devis_osc",nullable=false)
    private Integer idLDevisOsc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_devis",nullable=false)
    private WsTaDevis wsTaDevis;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_ligne",nullable=false)
    private WsTaTLigne wsTaTLigne;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article")
    private WsTaArticle wsTaArticle;

    @Column(name="num_ligne_l_devis",nullable=false)
    private Integer numLigneLDevis;

    @Column(name="lib_l_devis",length=255)
    private String libLDevis;

    @Column(name="qte_l_devis",precision=15,scale=2)
    private BigDecimal qteLDevis;

    @Column(name="u1_l_devis",length=20)
    private String u1LDevis;

    @Column(name="u2_l_devis",length=20)
    private String u2LDevis;

    @Column(name="prix_u_l_devis",precision=15,scale=2)
    private BigDecimal prixULDevis;

    @Column(name="taux_tva_l_devis",precision=15,scale=4)
    private BigDecimal tauxTvaLDevis;

    @Column(name="compte_l_devis",length=8)
    private String compteLDevis;

    @Column(name="code_tva_l_devis",length=20)
    private String codeTvaLDevis;

    @Column(name="code_t_tva_l_devis",length=1)
    private String codeTTvaLDevis;

    @Column(name="mt_ht_l_devis",precision=15,scale=2)
    private BigDecimal mtHtLDevis;

    @Column(name="mt_ttc_l_devis",precision=15,scale=2)
    private BigDecimal mtTtcLDevis;

    @Column(name="rem_tx_l_devis",precision=15,scale=2)
    private BigDecimal remTxLDevis;

    @Column(name="rem_ht_l_devis",precision=15,scale=2)
    private BigDecimal remHtLDevis;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    public WsTaLDevis() {
    }

    public String getCodeTTvaLDevis() {
        return codeTTvaLDevis;
    }

    public void setCodeTTvaLDevis(String codeTTvaLDevis) {
        this.codeTTvaLDevis = codeTTvaLDevis;
    }

    public String getCodeTvaLDevis() {
        return codeTvaLDevis;
    }

    public void setCodeTvaLDevis(String codeTvaLDevis) {
        this.codeTvaLDevis = codeTvaLDevis;
    }

    public String getCompteLDevis() {
        return compteLDevis;
    }

    public void setCompteLDevis(String compteLDevis) {
        this.compteLDevis = compteLDevis;
    }

    public Integer getIdLDevisOsc() {
        return idLDevisOsc;
    }

    public void setIdLDevisOsc(Integer idLDevisOsc) {
        this.idLDevisOsc = idLDevisOsc;
    }

    public String getLibLDevis() {
        return libLDevis;
    }

    public void setLibLDevis(String libLDevis) {
        this.libLDevis = libLDevis;
    }

    public BigDecimal getMtHtLDevis() {
        return mtHtLDevis;
    }

    public void setMtHtLDevis(BigDecimal mtHtLDevis) {
        this.mtHtLDevis = mtHtLDevis;
    }

    public BigDecimal getMtTtcLDevis() {
        return mtTtcLDevis;
    }

    public void setMtTtcLDevis(BigDecimal mtTtcLDevis) {
        this.mtTtcLDevis = mtTtcLDevis;
    }

    public Integer getNumLigneLDevis() {
        return numLigneLDevis;
    }

    public void setNumLigneLDevis(Integer numLigneLDevis) {
        this.numLigneLDevis = numLigneLDevis;
    }

    public BigDecimal getPrixULDevis() {
        return prixULDevis;
    }

    public void setPrixULDevis(BigDecimal prixULDevis) {
        this.prixULDevis = prixULDevis;
    }

    public BigDecimal getQteLDevis() {
        return qteLDevis;
    }

    public void setQteLDevis(BigDecimal qteLDevis) {
        this.qteLDevis = qteLDevis;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public BigDecimal getRemHtLDevis() {
        return remHtLDevis;
    }

    public void setRemHtLDevis(BigDecimal remHtLDevis) {
        this.remHtLDevis = remHtLDevis;
    }

    public BigDecimal getRemTxLDevis() {
        return remTxLDevis;
    }

    public void setRemTxLDevis(BigDecimal remTxLDevis) {
        this.remTxLDevis = remTxLDevis;
    }

    public BigDecimal getTauxTvaLDevis() {
        return tauxTvaLDevis;
    }

    public void setTauxTvaLDevis(BigDecimal tauxTvaLDevis) {
        this.tauxTvaLDevis = tauxTvaLDevis;
    }

    public String getU1LDevis() {
        return u1LDevis;
    }

    public void setU1LDevis(String u1LDevis) {
        this.u1LDevis = u1LDevis;
    }

    public String getU2LDevis() {
        return u2LDevis;
    }

    public void setU2LDevis(String u2LDevis) {
        this.u2LDevis = u2LDevis;
    }

    public WsTaArticle getWsTaArticle() {
        return wsTaArticle;
    }

    public void setWsTaArticle(WsTaArticle wsTaArticle) {
        this.wsTaArticle = wsTaArticle;
    }

    public WsTaDevis getWsTaDevis() {
        return wsTaDevis;
    }

    public void setWsTaDevis(WsTaDevis wsTaDevis) {
        this.wsTaDevis = wsTaDevis;
    }

    public WsTaTLigne getWsTaTLigne() {
        return wsTaTLigne;
    }

    public void setWsTaTLigne(WsTaTLigne wsTaTLigne) {
        this.wsTaTLigne = wsTaTLigne;
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
        if (!(object instanceof WsTaLDevis)) {
            return false;
        }
        WsTaLDevis other = (WsTaLDevis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaLDevis[id=" + id + "]";
    }

}
