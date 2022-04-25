/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_r_email")
public class WsTaREmail implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private IdWsTaREmail idWsTaREmail = new IdWsTaREmail();


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers",insertable=false,updatable=false)
    private WsTaTiers wsTaTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_email",insertable=false,updatable=false)
    private WsTaEmail wsTaEmail;
    

    public WsTaREmail() {
    }

    public IdWsTaREmail getIdWsTaREmail() {
        return idWsTaREmail;
    }

    public void setIdWsTaREmail(IdWsTaREmail idWsTaREmail) {
        this.idWsTaREmail = idWsTaREmail;
    }

    public WsTaEmail getWsTaEmail() {
        return wsTaEmail;
    }

    public void setWsTaEmail(WsTaEmail wsTaEmail) {
        this.wsTaEmail = wsTaEmail;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }
        @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWsTaREmail != null ? idWsTaREmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaREmail)) {
            return false;
        }
        WsTaREmail other = (WsTaREmail) object;
        if ((this.idWsTaREmail == null && other.idWsTaREmail != null) || (this.idWsTaREmail != null && !this.idWsTaREmail.equals(other.idWsTaREmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaRAdrTAdr[id=" + idWsTaREmail + "]";
    }
}
