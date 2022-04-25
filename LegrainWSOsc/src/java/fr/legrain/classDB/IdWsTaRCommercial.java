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
public class IdWsTaRCommercial implements Serializable{
    @Column(name="id_r_commercial")
    private Integer idRCommercial;

    @Column(name="id_tiers")
    private Integer idTiers;

    @Column(name="id_tiers_com")
    private Integer idTiersCom;

    public IdWsTaRCommercial() {
    }

    public Integer getIdRCommercial() {
        return idRCommercial;
    }

    public void setIdRCommercial(Integer idRCommercial) {
        this.idRCommercial = idRCommercial;
    }

    public Integer getIdTiers() {
        return idTiers;
    }

    public void setIdTiers(Integer idTiers) {
        this.idTiers = idTiers;
    }

    public Integer getIdTiersCom() {
        return idTiersCom;
    }

    public void setIdTiersCom(Integer idTiersCom) {
        this.idTiersCom = idTiersCom;
    }
    
}
