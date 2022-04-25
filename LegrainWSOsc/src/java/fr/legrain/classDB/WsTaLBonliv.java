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
@Table(name="ws_ta_l_bonliv")
public class WsTaLBonliv implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_l_bonliv")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_l_bonliv_osc",nullable=false)
    private Integer idLBonlivOsc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_bonliv",nullable=false)
    private WsTaBonliv wsTaBonliv;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_ligne",nullable=false)
    private WsTaTLigne wsTaTLigne;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article")
    private WsTaArticle wsTaArticle;

    @Column(name="num_ligne_l_bonliv")
    private Integer numLigneLBonliv;

    @Column(name="lib_l_bonliv",length=255)
    private String libLBonliv;

    @Column(name="qte_l_bonliv",precision=15,scale=2)
    private BigDecimal qteLBonliv;

    @Column(name="u1_l_bonliv",length=20)
    private String u1LBonliv;

    @Column(name="u2_l_bonliv",length=20)
    private String u2LBonliv;

    @Column(name="prix_u_l_bonliv",precision=15,scale=2)
    private BigDecimal prixULBonliv;

    @Column(name="taux_tva_l_bonliv",precision=15,scale=4)
    private BigDecimal tauxTvaLBonliv;

    @Column(name="compte_l_bonliv",length=8)
    private String compteLBonliv;

    @Column(name="code_tva_l_bonliv",length=20)
    private String codeTvaLBonliv;

    @Column(name="code_t_tva_l_bonliv",length=1)
    private String codeTTvaLBonliv;

    @Column(name="mt_ht_l_bonliv",precision=15,scale=2)
    private BigDecimal mtHtLBonliv;

    @Column(name="mt_ttc_l_bonliv",precision=15,scale=2)
    private BigDecimal mtTtcLBonliv;

    @Column(name="rem_tx_l_bonliv",precision=15,scale=2)
    private BigDecimal remTxLBonliv;

    @Column(name="rem_ht_l_bonliv",precision=15,scale=2)
    private BigDecimal remHtLBonliv;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    
    public WsTaLBonliv() {
    }

    public WsTaLBonliv(Integer idLBonlivOsc, WsTaBonliv wsTaBonliv, WsTaTLigne wsTaTLigne, WsTaArticle wsTaArticle, Integer numLigneLBonliv, String libLBonliv, BigDecimal qteLBonliv, String u1LBonliv, String u2LBonliv, BigDecimal prixULBonliv, BigDecimal tauxTvaLBonliv, String compteLBonliv, String codeTvaLBonliv, String codeTTvaLBonliv, BigDecimal mtHtLBonliv, BigDecimal mtTtcLBonliv, BigDecimal remTxLBonliv, BigDecimal remHtLBonliv, Date quandAdd) {
        this.idLBonlivOsc = idLBonlivOsc;
        this.wsTaBonliv = wsTaBonliv;
        this.wsTaTLigne = wsTaTLigne;
        this.wsTaArticle = wsTaArticle;
        this.numLigneLBonliv = numLigneLBonliv;
        this.libLBonliv = libLBonliv;
        this.qteLBonliv = qteLBonliv;
        this.u1LBonliv = u1LBonliv;
        this.u2LBonliv = u2LBonliv;
        this.prixULBonliv = prixULBonliv;
        this.tauxTvaLBonliv = tauxTvaLBonliv;
        this.compteLBonliv = compteLBonliv;
        this.codeTvaLBonliv = codeTvaLBonliv;
        this.codeTTvaLBonliv = codeTTvaLBonliv;
        this.mtHtLBonliv = mtHtLBonliv;
        this.mtTtcLBonliv = mtTtcLBonliv;
        this.remTxLBonliv = remTxLBonliv;
        this.remHtLBonliv = remHtLBonliv;
        this.quandAdd = quandAdd;
    }

    public String getCodeTTvaLBonliv() {
        return codeTTvaLBonliv;
    }

    public void setCodeTTvaLBonliv(String codeTTvaLBonliv) {
        this.codeTTvaLBonliv = codeTTvaLBonliv;
    }

    public String getCodeTvaLBonliv() {
        return codeTvaLBonliv;
    }

    public void setCodeTvaLBonliv(String codeTvaLBonliv) {
        this.codeTvaLBonliv = codeTvaLBonliv;
    }

    public String getCompteLBonliv() {
        return compteLBonliv;
    }

    public void setCompteLBonliv(String compteLBonliv) {
        this.compteLBonliv = compteLBonliv;
    }

    public Integer getIdLBonlivOsc() {
        return idLBonlivOsc;
    }

    public void setIdLBonlivOsc(Integer idLBonlivOsc) {
        this.idLBonlivOsc = idLBonlivOsc;
    }

    public String getLibLBonliv() {
        return libLBonliv;
    }

    public void setLibLBonliv(String libLBonliv) {
        this.libLBonliv = libLBonliv;
    }

    public BigDecimal getMtHtLBonliv() {
        return mtHtLBonliv;
    }

    public void setMtHtLBonliv(BigDecimal mtHtLBonliv) {
        this.mtHtLBonliv = mtHtLBonliv;
    }

    public BigDecimal getMtTtcLBonliv() {
        return mtTtcLBonliv;
    }

    public void setMtTtcLBonliv(BigDecimal mtTtcLBonliv) {
        this.mtTtcLBonliv = mtTtcLBonliv;
    }

    public Integer getNumLigneLBonliv() {
        return numLigneLBonliv;
    }

    public void setNumLigneLBonliv(Integer numLigneLBonliv) {
        this.numLigneLBonliv = numLigneLBonliv;
    }

    public BigDecimal getPrixULBonliv() {
        return prixULBonliv;
    }

    public void setPrixULBonliv(BigDecimal prixULBonliv) {
        this.prixULBonliv = prixULBonliv;
    }

    public BigDecimal getQteLBonliv() {
        return qteLBonliv;
    }

    public void setQteLBonliv(BigDecimal qteLBonliv) {
        this.qteLBonliv = qteLBonliv;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public BigDecimal getRemHtLBonliv() {
        return remHtLBonliv;
    }

    public void setRemHtLBonliv(BigDecimal remHtLBonliv) {
        this.remHtLBonliv = remHtLBonliv;
    }

    public BigDecimal getRemTxLBonliv() {
        return remTxLBonliv;
    }

    public void setRemTxLBonliv(BigDecimal remTxLBonliv) {
        this.remTxLBonliv = remTxLBonliv;
    }

    public BigDecimal getTauxTvaLBonliv() {
        return tauxTvaLBonliv;
    }

    public void setTauxTvaLBonliv(BigDecimal tauxTvaLBonliv) {
        this.tauxTvaLBonliv = tauxTvaLBonliv;
    }

    public String getU1LBonliv() {
        return u1LBonliv;
    }

    public void setU1LBonliv(String u1LBonliv) {
        this.u1LBonliv = u1LBonliv;
    }

    public String getU2LBonliv() {
        return u2LBonliv;
    }

    public void setU2LBonliv(String u2LBonliv) {
        this.u2LBonliv = u2LBonliv;
    }

    public WsTaArticle getWsTaArticle() {
        return wsTaArticle;
    }

    public void setWsTaArticle(WsTaArticle wsTaArticle) {
        this.wsTaArticle = wsTaArticle;
    }

    public WsTaBonliv getWsTaBonliv() {
        return wsTaBonliv;
    }

    public void setWsTaBonliv(WsTaBonliv wsTaBonliv) {
        this.wsTaBonliv = wsTaBonliv;
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
        if (!(object instanceof WsTaLBonliv)) {
            return false;
        }
        WsTaLBonliv other = (WsTaLBonliv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaLBoncde[id=" + id + "]";
    }

}
