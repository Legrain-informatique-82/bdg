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
@Table(name="ws_ta_l_facture")
public class WsTaLFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_l_facture")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_l_facture_osc",nullable=false)
    private Integer idLFactureOsc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_facture",nullable=false)
    private WsTaFacture wsTaFacture;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_ligne",nullable=false)
    private WsTaTLigne wsTaTLigne;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article")
    private WsTaArticle wsTaArticle;

     @Column(name="num_ligne_l_facture",nullable=false)
    private Integer numLigneLFacture;

    @Column(name="lib_l_facture",length=255)
    private String libLFacture;

    @Column(name="qte_l_facture",precision=15,scale=2)
    private BigDecimal qteLFacture;

    @Column(name="u1_l_facture",length=20)
    private String u1LFacture;

    @Column(name="u2_l_facture",length=20)
    private String u2LFacture;

    @Column(name="prix_u_l_facture",precision=15,scale=2)
    private BigDecimal prixULFacture;

    @Column(name="taux_tva_l_facture",precision=15,scale=4)
    private BigDecimal tauxTvaLFacture;

    @Column(name="compte_l_facture",length=8)
    private String compteLFacture;

    @Column(name="code_tva_l_facture",length=20)
    private String codeTvaLFacture;

    @Column(name="code_t_tva_l_facture",length=1)
    private String codeTTvaLFacture;

    @Column(name="mt_ht_l_facture",precision=15,scale=2)
    private BigDecimal mtHtLFacture;

    @Column(name="mt_ttc_l_facture",precision=15,scale=2)
    private BigDecimal mtTtcLFacture;

    @Column(name="rem_tx_l_facture",precision=15,scale=2)
    private BigDecimal remTxLFacture;

    @Column(name="rem_ht_l_facture",precision=15,scale=2)
    private BigDecimal remHtLFacture;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

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
        if (!(object instanceof WsTaLFacture)) {
            return false;
        }
        WsTaLFacture other = (WsTaLFacture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaLFacture[id=" + id + "]";
    }

}
