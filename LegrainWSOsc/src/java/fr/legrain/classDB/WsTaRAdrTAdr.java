/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_r_adr_t_adr")
public class WsTaRAdrTAdr implements Serializable {
    private static final long serialVersionUID = 1L;
    

    @EmbeddedId
    private IdWsTaRAdrTAdr idWsTaRAdrTAdr = new IdWsTaRAdrTAdr();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_adr",insertable=false,updatable=false)
    private WsTaTAdr wsTaTAdr;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_adresse",insertable=false,updatable=false)
    private WsTaAdresse wsTaAdresse;
    

   
    public WsTaRAdrTAdr() {
    }

    public WsTaAdresse getWsTaAdresse() {
        return wsTaAdresse;
    }

    public void setWsTaAdresse(WsTaAdresse wsTaAdresse) {
        this.wsTaAdresse = wsTaAdresse;
    }

    public WsTaTAdr getWsTaTAdr() {
        return wsTaTAdr;
    }

    public void setWsTaTAdr(WsTaTAdr wsTaTAdr) {
        this.wsTaTAdr = wsTaTAdr;
    }
 
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWsTaRAdrTAdr != null ? idWsTaRAdrTAdr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaRAdrTAdr)) {
            return false;
        }
        WsTaRAdrTAdr other = (WsTaRAdrTAdr) object;
        if ((this.idWsTaRAdrTAdr == null && other.idWsTaRAdrTAdr != null) || (this.idWsTaRAdrTAdr != null && !this.idWsTaRAdrTAdr.equals(other.idWsTaRAdrTAdr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaRAdrTAdr[id=" + idWsTaRAdrTAdr + "]";
    }

}
