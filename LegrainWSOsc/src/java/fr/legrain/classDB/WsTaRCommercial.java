/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_r_commercial",
       uniqueConstraints=@UniqueConstraint(columnNames={"id_tiers","id_tiers_com"}))
public class WsTaRCommercial implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private IdWsTaRCommercial idWsTaRCommercial = new IdWsTaRCommercial();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers",insertable=false,updatable=false)
    private WsTaTiers wsTaTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers_com",insertable=false,updatable=false)
    private WsTaTiers wsTaTiersCom;

    public WsTaRCommercial() {
    }

    public IdWsTaRCommercial getIdWsTaRCommercial() {
        return idWsTaRCommercial;
    }

    public void setIdWsTaRCommercial(IdWsTaRCommercial idWsTaRCommercial) {
        this.idWsTaRCommercial = idWsTaRCommercial;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

    public WsTaTiers getWsTaTiersCom() {
        return wsTaTiersCom;
    }

    public void setWsTaTiersCom(WsTaTiers wsTaTiersCom) {
        this.wsTaTiersCom = wsTaTiersCom;
    }

    public IdWsTaRCommercial getId() {
        return idWsTaRCommercial;
    }

    public void setId(IdWsTaRCommercial idWsTaRCommercial) {
        this.idWsTaRCommercial = idWsTaRCommercial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWsTaRCommercial != null ? idWsTaRCommercial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaRCommercial)) {
            return false;
        }
        WsTaRCommercial other = (WsTaRCommercial) object;
        if ((this.idWsTaRCommercial == null && other.idWsTaRCommercial != null) || (this.idWsTaRCommercial != null && !this.idWsTaRCommercial.equals(other.idWsTaRCommercial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaRCommercial[id=" + idWsTaRCommercial + "]";
    }

}
