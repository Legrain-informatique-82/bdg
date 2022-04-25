/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_compl")
public class WsTaCompl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_compl",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers")
    private WsTaTiers wsTaTiers;

    @Column(name="tva_i_com_compl",length=50)
    private String tvaIComCompl;

    @Column(name="siret_compl",length=50)
    private String siretCompl;

    public WsTaCompl() {
    }

    public WsTaCompl(WsTaTiers wsTaTiers, String tvaIComCompl, String siretCompl) {
        this.wsTaTiers = wsTaTiers;
        this.tvaIComCompl = tvaIComCompl;
        this.siretCompl = siretCompl;
    }
    
    public String getSiretCompl() {
        return siretCompl;
    }

    public void setSiretCompl(String siretCompl) {
        this.siretCompl = siretCompl;
    }

    public String getTvaIComCompl() {
        return tvaIComCompl;
    }

    public void setTvaIComCompl(String tvaIComCompl) {
        this.tvaIComCompl = tvaIComCompl;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
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
        if (!(object instanceof WsTaCompl)) {
            return false;
        }
        WsTaCompl other = (WsTaCompl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaCompl[id=" + id + "]";
    }

}
