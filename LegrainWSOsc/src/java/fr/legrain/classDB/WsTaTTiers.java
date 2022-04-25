/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_t_tiers")
public class WsTaTTiers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_tiers")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_tiers",length=50,nullable=false)
    private String codeTTiers;

    @Column(name="libelle_t_tiers",length=100,nullable=false)
    private String libelleTLigne;

    @Column(name="compte_t_tiers",length=8)
    private String compteTTiers;

    @OneToOne(mappedBy="wsTaTTiers",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private WsTaTiers wsTaTiers;


    public WsTaTTiers() {
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

    public String getCodeTTiers() {
        return codeTTiers;
    }

    public void setCodeTTiers(String codeTTiers) {
        this.codeTTiers = codeTTiers;
    }

    public String getCompteTTiers() {
        return compteTTiers;
    }

    public void setCompteTTiers(String compteTTiers) {
        this.compteTTiers = compteTTiers;
    }

    public String getLibelleTLigne() {
        return libelleTLigne;
    }

    public void setLibelleTLigne(String libelleTLigne) {
        this.libelleTLigne = libelleTLigne;
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
        if (!(object instanceof WsTaTTiers)) {
            return false;
        }
        WsTaTTiers other = (WsTaTTiers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaTTiers[id=" + id + "]";
    }

}
