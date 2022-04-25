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
public class IdWsTaRTelTTel implements Serializable{

    @Column(name="id_r_tel_t_tel")
    private Integer idRTelTTel;

    @Column(name="id_telephone")
    private Integer idTelephone;

    @Column(name="id_t_tel")
    private Integer idTTel;

    public IdWsTaRTelTTel() {
    }

    public Integer getIdRTelTTel() {
        return idRTelTTel;
    }

    public void setIdRTelTTel(Integer idRTelTTel) {
        this.idRTelTTel = idRTelTTel;
    }

    public Integer getIdTTel() {
        return idTTel;
    }

    public void setIdTTel(Integer idTTel) {
        this.idTTel = idTTel;
    }

    public Integer getIdTelephone() {
        return idTelephone;
    }

    public void setIdTelephone(Integer idTelephone) {
        this.idTelephone = idTelephone;
    }

}
