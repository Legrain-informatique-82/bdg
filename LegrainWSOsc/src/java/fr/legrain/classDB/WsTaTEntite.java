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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_t_entite")
public class WsTaTEntite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_entite")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_entite",length=20,nullable=false)
    private String codeTEntite;

    @Column(name="libl_t_entite",length=100,nullable=false)
    private String liblTEntite;

    @OneToMany(mappedBy="wsTaTEntite",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaEntreprise> wsTaEntreprise = new HashSet<WsTaEntreprise>();

    @OneToOne(mappedBy="wsTaTEntite",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private WsTaTiers wsTaTiers;

    public WsTaTEntite() {
    }

    public WsTaTEntite(String codeTEntite, String liblTEntite) {
        this.codeTEntite = codeTEntite;
        this.liblTEntite = liblTEntite;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

    
    public String getCodeTEntite() {
        return codeTEntite;
    }

    public void setCodeTEntite(String codeTEntite) {
        this.codeTEntite = codeTEntite;
    }

    public String getLiblTEntite() {
        return liblTEntite;
    }

    public void setLiblTEntite(String liblTEntite) {
        this.liblTEntite = liblTEntite;
    }

    public Set<WsTaEntreprise> getWsTaEntreprise() {
        return wsTaEntreprise;
    }

    public void setWsTaEntreprise(Set<WsTaEntreprise> wsTaEntreprise) {
        this.wsTaEntreprise = wsTaEntreprise;
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
        if (!(object instanceof WsTaTEntite)) {
            return false;
        }
        WsTaTEntite other = (WsTaTEntite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTEntite[id=" + id + "]";
    }

}
