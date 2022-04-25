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
@Table(name="ws_ta_t_paiement")
public class WsTaTPaiement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_paiement",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_paiement",length=50,nullable=false)
    private String codeTPaiement;

    @Column(name="lib_t_paiement",length=255)
    private String LibTPaiement;

    @OneToMany(mappedBy="wsTaTPaiement",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaBonliv> WsTaBonlivs = new HashSet<WsTaBonliv>();

    @OneToMany(mappedBy="wsTaTPaiement",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaDevis> wsTaDevis = new HashSet<WsTaDevis>();

    @OneToMany(mappedBy="wsTaTPaiement",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaFacture> wsTaFactures = new HashSet<WsTaFacture>();

    public Set<WsTaFacture> getWsTaFactures() {
        return wsTaFactures;
    }

    public void setWsTaFactures(Set<WsTaFacture> wsTaFactures) {
        this.wsTaFactures = wsTaFactures;
    }

    
    public WsTaTPaiement() {
    }

     public WsTaTPaiement(String codeTPaiement) {
         this.codeTPaiement = codeTPaiement;
    }

    public Set<WsTaDevis> getWsTaDevis() {
        return wsTaDevis;
    }

    public void setWsTaDevis(Set<WsTaDevis> wsTaDevis) {
        this.wsTaDevis = wsTaDevis;
    }

    public Set<WsTaBonliv> getWsTaBonlivs() {
        return WsTaBonlivs;
    }

    public void setWsTaBonlivs(Set<WsTaBonliv> WsTaBonlivs) {
        this.WsTaBonlivs = WsTaBonlivs;
    }

     
    public String getLibTPaiement() {
        return LibTPaiement;
    }

    public void setLibTPaiement(String LibTPaiement) {
        this.LibTPaiement = LibTPaiement;
    }

    public String getCodeTPaiement() {
        return codeTPaiement;
    }

    public void setCodeTPaiement(String codeTPaiement) {
        this.codeTPaiement = codeTPaiement;
    }

    public WsTaTPaiement(String codeTPaiement, String LibTPaiement) {
        this.codeTPaiement = codeTPaiement;
        this.LibTPaiement = LibTPaiement;
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
        if (!(object instanceof WsTaTPaiement)) {
            return false;
        }
        WsTaTPaiement other = (WsTaTPaiement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTPaiement[id=" + id + "]";
    }

}
