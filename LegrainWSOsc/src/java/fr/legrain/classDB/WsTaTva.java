/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_tva")
public class WsTaTva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_tva",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_tva",length=20,nullable=false)
    private String codeTva;

    @Column(name="libelle_tva",length=255)
    private String libelleTva;

    @Column(name="taux_tva",precision=15,scale=2)
    private BigDecimal tauxTva;

    @OneToMany(mappedBy="wsTaTva",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaArticle> wsTaArticles = new HashSet<WsTaArticle>();

    public WsTaTva() {
    }

    public WsTaTva(String codeTva) {
        this.codeTva = codeTva;
    }
    
    public WsTaTva(String codeTva, String libelleTva, BigDecimal tauxTva) {
        this.codeTva = codeTva;
        this.libelleTva = libelleTva;
        this.tauxTva = tauxTva;
    }


    public String getCodeTva() {
        return codeTva;
    }

    public void setCodeTva(String codeTva) {
        this.codeTva = codeTva;
    }

    public String getLibelleTva() {
        return libelleTva;
    }

    public void setLibelleTva(String libelleTva) {
        this.libelleTva = libelleTva;
    }

    public BigDecimal getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(BigDecimal tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Set<WsTaArticle> getWsTaArticles() {
        return wsTaArticles;
    }

    public void setWsTaArticles(Set<WsTaArticle> wsTaArticles) {
        this.wsTaArticles = wsTaArticles;
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
        if (!(object instanceof WsTaTva)) {
            return false;
        }
        WsTaTva other = (WsTaTva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTva[id=" + id + "]";
    }

}
