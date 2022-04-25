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
@Table(name="ws_ta_web")
public class WsTaWeb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_web")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="adresse_web",length=255)
    private String adresseWeb;

    @OneToMany(mappedBy="wsTaWeb",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaRWeb> wsTaRWeb = new HashSet<WsTaRWeb>();

    public WsTaWeb() {
    }

    public Set<WsTaRWeb> getWsTaRWeb() {
        return wsTaRWeb;
    }

    public void setWsTaRWeb(Set<WsTaRWeb> wsTaRWeb) {
        this.wsTaRWeb = wsTaRWeb;
    }

    public String getAdresseWeb() {
        return adresseWeb;
    }

    public void setAdresseWeb(String adresseWeb) {
        this.adresseWeb = adresseWeb;
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
        if (!(object instanceof WsTaWeb)) {
            return false;
        }
        WsTaWeb other = (WsTaWeb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaWeb[id=" + id + "]";
    }

}
