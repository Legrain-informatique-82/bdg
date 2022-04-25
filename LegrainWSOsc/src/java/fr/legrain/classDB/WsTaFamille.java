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
@Table(name="ws_ta_famille")
public class WsTaFamille implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_famille",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_famille",nullable=false)
    private String codeFamille;

    @Column(name="libc_famille",nullable=false)
    private String libcFamille;

    @Column(name="libl_famille")
    private String liblFamille;

    @OneToMany(mappedBy="wsTaFamille",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaArticle> wsTaArticles = new HashSet<WsTaArticle>();

    public WsTaFamille() {
    }


    public WsTaFamille(String codeFamille,String libcFamile){
        this.codeFamille = codeFamille;
        this.libcFamille = libcFamile;
    }
    public WsTaFamille(String codeFamille,String libcFamille,String liblFamille){
        this.codeFamille = codeFamille;
        this.libcFamille = libcFamille;
        this.liblFamille = liblFamille;
    }

    public String getCodeFamille() {
        return codeFamille;
    }

    public void setCodeFamille(String codeFamille) {
        this.codeFamille = codeFamille;
    }

    public String getLibcFamille() {
        return libcFamille;
    }

    public void setLibcFamille(String libcFamille) {
        this.libcFamille = libcFamille;
    }

    public String getLiblFamille() {
        return liblFamille;
    }

    public void setLiblFamille(String liblFamille) {
        this.liblFamille = liblFamille;
    }

    public Set<WsTaArticle> getWsTaArticle() {
        return wsTaArticles;
    }

    public void setWsTaArticle(Set<WsTaArticle> wsTaArticles) {
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
        if (!(object instanceof WsTaFamille)) {
            return false;
        }
        WsTaFamille other = (WsTaFamille) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaFamille[id=" + id + "]";
    }

}
