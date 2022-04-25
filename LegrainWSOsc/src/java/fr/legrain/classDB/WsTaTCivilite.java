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
@Table(name="ws_ta_t_civilite")
public class WsTaTCivilite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_civilite")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_civilite",length=50,nullable=false)
    private String codeTCivilite;

    @OneToMany(mappedBy="wsTaTCivilite",cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaTiers> WsTaTier = new HashSet<WsTaTiers>();

    public WsTaTCivilite() {
    }

    public Set<WsTaTiers> getWsTaTier() {
        return WsTaTier;
    }

    public void setWsTaTier(Set<WsTaTiers> WsTaTier) {
        this.WsTaTier = WsTaTier;
    }
   
    public String getCodeTCivilite() {
        return codeTCivilite;
    }

    public void setCodeTCivilite(String codeTCivilite) {
        this.codeTCivilite = codeTCivilite;
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
        if (!(object instanceof WsTaTCivilite)) {
            return false;
        }
        WsTaTCivilite other = (WsTaTCivilite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaTCivilite[id=" + id + "]";
    }

}
