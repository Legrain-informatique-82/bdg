/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_prix")
public class WsTaPrix implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_prix")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article")
    private WsTaArticle wsTaArticle;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_unite")
    private WsTaUnite wsTaUnite;

    @Column(name="prix_prix",precision=15,scale=2)
    private BigDecimal prixPrix;

    @Column(name="prixttc_prix",precision=15,scale=2)
    private BigDecimal prixttcPrix;

    @OneToOne(mappedBy="wsTaPrix",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private WsTaRefPrix wsTaRefPrix;

    public WsTaPrix() {
    }

    public WsTaPrix(WsTaArticle wsTaArticle, WsTaUnite wsTaUnite, BigDecimal prixPrix, BigDecimal prixttcPrix) {
        this.wsTaArticle = wsTaArticle;
        this.wsTaUnite = wsTaUnite;
        this.prixPrix = prixPrix;
        this.prixttcPrix = prixttcPrix;
    }

    public WsTaRefPrix getWsTaRefPrix() {
        return wsTaRefPrix;
    }

    public void setWsTaRefPrix(WsTaRefPrix wsTaRefPrix) {
        this.wsTaRefPrix = wsTaRefPrix;
    }

    public BigDecimal getPrixPrix() {
        return prixPrix;
    }

    public void setPrixPrix(BigDecimal prixPrix) {
        this.prixPrix = prixPrix;
    }
    
    public BigDecimal getPrixttcPrix() {
        return prixttcPrix;
    }

    public void setPrixttcPrix(BigDecimal prixttcPrix) {
        this.prixttcPrix = prixttcPrix;
    }

    public WsTaArticle getWsTaArticle() {
        return wsTaArticle;
    }

    public void setWsTaArticle(WsTaArticle wsTaArticle) {
        this.wsTaArticle = wsTaArticle;
    }

    public WsTaUnite getWsTaUnite() {
        return wsTaUnite;
    }

    public void setWsTaUnite(WsTaUnite wsTaUnite) {
        this.wsTaUnite = wsTaUnite;
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
        if (!(object instanceof WsTaPrix)) {
            return false;
        }
        WsTaPrix other = (WsTaPrix) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaPrix[id=" + id + "]";
    }

}
