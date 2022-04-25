/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
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
@Table(name="ws_ta_t_tva")
public class WsTaTTva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_tva",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_tva",length=1,nullable=false)
    private String codeTTva;

    @Column(name="lib_t_tva",length=255)
    private String libTTva;

    @OneToMany(mappedBy="wsTaTTva",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaArticle> wsTaArticles = new HashSet<WsTaArticle>();

    public WsTaTTva() {
        
    }
    public WsTaTTva(String codeTTva) {
        this.codeTTva = codeTTva;
    }
    public WsTaTTva(String codeTTva, String libTTva) {
        this.codeTTva = codeTTva;
        this.libTTva = libTTva;
    }

    public String getCodeTTva() {
        return codeTTva;
    }

    public void setCodeTTva(String codeTTva) {
        this.codeTTva = codeTTva;
    }

    public String getLibTTva() {
        return libTTva;
    }

    public void setLibTTva(String libTTva) {
        this.libTTva = libTTva;
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
        if (!(object instanceof WsTaTTva)) {
            return false;
        }
        WsTaTTva other = (WsTaTTva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.ws_ta_t_tva[id=" + id + "]";
    }

}
