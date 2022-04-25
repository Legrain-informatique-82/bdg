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
@Table(name="ws_ta_r_web",
       uniqueConstraints=@UniqueConstraint(columnNames={"id_tiers","id_web"}))
public class WsTaRWeb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private IdWsTaRWeb idWsTaRWeb = new IdWsTaRWeb();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers",insertable=false,updatable=false)
    private WsTaTiers wsTaTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_web",insertable=false,updatable=false)
    private WsTaWeb wsTaWeb;

    public WsTaRWeb() {
    }

    public IdWsTaRWeb getIdWsTaRWeb() {
        return idWsTaRWeb;
    }

    public void setIdWsTaRWeb(IdWsTaRWeb idWsTaRWeb) {
        this.idWsTaRWeb = idWsTaRWeb;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

    public WsTaWeb getWsTaWeb() {
        return wsTaWeb;
    }

    public void setWsTaWeb(WsTaWeb wsTaWeb) {
        this.wsTaWeb = wsTaWeb;
    }

    public IdWsTaRWeb getId() {
        return idWsTaRWeb;
    }

    public void setId(IdWsTaRWeb idWsTaRWeb) {
        this.idWsTaRWeb = idWsTaRWeb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWsTaRWeb != null ? idWsTaRWeb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the idWsTaRWeb fields are not set
        if (!(object instanceof WsTaRWeb)) {
            return false;
        }
        WsTaRWeb other = (WsTaRWeb) object;
        if ((this.idWsTaRWeb == null && other.idWsTaRWeb != null) || (this.idWsTaRWeb != null && !this.idWsTaRWeb.equals(other.idWsTaRWeb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaRWeb[idWsTaRWeb=" + idWsTaRWeb + "]";
    }

}
