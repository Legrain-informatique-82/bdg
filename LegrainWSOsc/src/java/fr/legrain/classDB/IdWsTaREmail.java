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
public class IdWsTaREmail implements Serializable{

    @Column(name="id_r_email")
    private Integer idREmail;
    
    @Column(name="id_tiers")
    private Integer idTiers;

    @Column(name="id_email")
    private Integer idEmail;

    public IdWsTaREmail() {
    }

    public Integer getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public Integer getIdREmail() {
        return idREmail;
    }

    public void setIdREmail(Integer idREmail) {
        this.idREmail = idREmail;
    }

    public Integer getIdTiers() {
        return idTiers;
    }

    public void setIdTiers(Integer idTiers) {
        this.idTiers = idTiers;
    }


}
