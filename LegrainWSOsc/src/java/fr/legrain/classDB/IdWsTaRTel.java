/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author lee
 */
@Embeddable
public class IdWsTaRTel implements Serializable {

    @Column(name = "id_r_tel")
    private Integer idRTel;
    @Column(name = "id_tiers")
    private Integer idTiers;
    @Column(name = "id_telephone")
    private Integer idTelephone;

    public IdWsTaRTel() {
    }

    public Integer getIdRTel() {
        return idRTel;
    }

    public void setIdRTel(Integer idRTel) {
        this.idRTel = idRTel;
    }

    public Integer getIdTelephone() {
        return idTelephone;
    }

    public void setIdTelephone(Integer idTelephone) {
        this.idTelephone = idTelephone;
    }

    public Integer getIdTiers() {
        return idTiers;
    }

    public void setIdTiers(Integer idTiers) {
        this.idTiers = idTiers;
    }

    
}
