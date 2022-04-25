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
@Table(name="ws_ta_t_ligne")
public class WsTaTLigne implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_ligne")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_ligne",length=50,nullable=false)
    private String codeTLigne;

    @Column(name="lib_t_ligne",length=255)
    private String libTLigne;

    @OneToMany(mappedBy="wsTaTLigne",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaLBonliv> wsTaLBonlivs = new HashSet<WsTaLBonliv>();

    @OneToMany(mappedBy="wsTaTLigne",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaLDevis> wsTaLDevis = new HashSet<WsTaLDevis>();

    @OneToMany(mappedBy="wsTaTLigne",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaLFacture> wsTaLFactures = new HashSet<WsTaLFacture>();


    public WsTaTLigne() {
    }

    public WsTaTLigne(String codeTLigne) {
        this.codeTLigne = codeTLigne;
    }

    public WsTaTLigne(String codeTLigne, String libTLigne) {
        this.codeTLigne = codeTLigne;
        this.libTLigne = libTLigne;
    }

    public Set<WsTaLBonliv> getWsTaLBonlivs() {
        return wsTaLBonlivs;
    }

    public void setWsTaLBonlivs(Set<WsTaLBonliv> wsTaLBonlivs) {
        this.wsTaLBonlivs = wsTaLBonlivs;
    }

    public Set<WsTaLDevis> getWsTaLDevis() {
        return wsTaLDevis;
    }

    public void setWsTaLDevis(Set<WsTaLDevis> wsTaLDevis) {
        this.wsTaLDevis = wsTaLDevis;
    }

    public Set<WsTaLFacture> getWsTaLFactures() {
        return wsTaLFactures;
    }

    public void setWsTaLFactures(Set<WsTaLFacture> wsTaLFactures) {
        this.wsTaLFactures = wsTaLFactures;
    }

    
    public String getCodeTLigne() {
        return codeTLigne;
    }

    public void setCodeTLigne(String codeTLigne) {
        this.codeTLigne = codeTLigne;
    }

    public String getLibTLigne() {
        return libTLigne;
    }

    public void setLibTLigne(String libTLigne) {
        this.libTLigne = libTLigne;
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
        if (!(object instanceof WsTaTLigne)) {
            return false;
        }
        WsTaTLigne other = (WsTaTLigne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTLigne[id=" + id + "]";
    }

}
