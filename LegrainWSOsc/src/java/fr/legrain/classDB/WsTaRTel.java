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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_r_tel",
       uniqueConstraints=@UniqueConstraint(columnNames={"id_tiers","id_telephone"}))
public class WsTaRTel implements Serializable {
//    @Embeddable
//     public static class IdWsTaRTel implements Serializable{
//        @Column(name="id_r_tel")
//        private Integer idRTel;
//
//        @Column(name="id_tiers")
//        private Integer idTiers;
//
//        @Column(name="id_telephone")
//        private Integer idTelephone;
//
//        public IdWsTaRTel() {
//        }
//
//        public IdWsTaRTel(Integer idRTel, Integer idTiers, Integer idTelephone) {
//            this.idRTel = idRTel;
//            this.idTiers = idTiers;
//            this.idTelephone = idTelephone;
//        }
//
//        public Integer getIdRTel() {
//            return idRTel;
//        }
//
//        public void setIdRTel(Integer idRTel) {
//            this.idRTel = idRTel;
//        }
//
//        public Integer getIdTelephone() {
//            return idTelephone;
//        }
//
//        public void setIdTelephone(Integer idTelephone) {
//            this.idTelephone = idTelephone;
//        }
//
//        public Integer getIdTiers() {
//            return idTiers;
//        }
//
//        public void setIdTiers(Integer idTiers) {
//            this.idTiers = idTiers;
//        }
//
//    }

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private IdWsTaRTel idWsTaRTel = new IdWsTaRTel();
   
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers",insertable=false,updatable=false)
    private WsTaTiers wsTaTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_telephone",insertable=false,updatable=false)
    private WsTaTelephone wsTaTelephone;

    public WsTaRTel() {
    }

    public IdWsTaRTel getId() {
        return idWsTaRTel;
    }

    public void setId(IdWsTaRTel idWsTaRTel) {
        this.idWsTaRTel = idWsTaRTel;
    }

    public WsTaTelephone getWsTaTelephone() {
        return wsTaTelephone;
    }

    public void setWsTaTelephone(WsTaTelephone wsTaTelephone) {
        this.wsTaTelephone = wsTaTelephone;
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
        hash += (idWsTaRTel != null ? idWsTaRTel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaRTel)) {
            return false;
        }
        WsTaRTel other = (WsTaRTel) object;
        if ((this.idWsTaRTel == null && other.idWsTaRTel != null) || (this.idWsTaRTel != null && !this.idWsTaRTel.equals(other.idWsTaRTel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaTadr[id=" + idWsTaRTel + "]";
    }
       
}
