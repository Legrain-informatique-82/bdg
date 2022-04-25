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
@Table(name="ws_ta_r_tel_t_tel",
       uniqueConstraints=@UniqueConstraint(columnNames={"id_telephone","id_t_tel"}))
public class WsTaRTelTTel implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private IdWsTaRTelTTel idWsTaRTelTTel = new IdWsTaRTelTTel();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_telephone",insertable=false,updatable=false)
    private WsTaTelephone wsTaTelephone;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_tel",insertable=false,updatable=false)
    private WsTaTTel wsTaTTel;

    public WsTaRTelTTel() {
    }

    public IdWsTaRTelTTel getIdWsTaRTelTTel() {
        return idWsTaRTelTTel;
    }

    public void setIdWsTaRTelTTel(IdWsTaRTelTTel idWsTaRTelTTel) {
        this.idWsTaRTelTTel = idWsTaRTelTTel;
    }

    public WsTaTTel getWsTaTTel() {
        return wsTaTTel;
    }

    public void setWsTaTTel(WsTaTTel wsTaTTel) {
        this.wsTaTTel = wsTaTTel;
    }

    public WsTaTelephone getWsTaTelephone() {
        return wsTaTelephone;
    }

    public void setWsTaTelephone(WsTaTelephone wsTaTelephone) {
        this.wsTaTelephone = wsTaTelephone;
    }
    
    public IdWsTaRTelTTel getId() {
        return idWsTaRTelTTel;
    }

    public void setId(IdWsTaRTelTTel idWsTaRTelTTel) {
        this.idWsTaRTelTTel = idWsTaRTelTTel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWsTaRTelTTel != null ? idWsTaRTelTTel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaRTelTTel)) {
            return false;
        }
        WsTaRTelTTel other = (WsTaRTelTTel) object;
        if ((this.idWsTaRTelTTel == null && other.idWsTaRTelTTel != null) || (this.idWsTaRTelTTel != null && !this.idWsTaRTelTTel.equals(other.idWsTaRTelTTel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaRTelTTel[id=" + idWsTaRTelTTel + "]";
    }

}
