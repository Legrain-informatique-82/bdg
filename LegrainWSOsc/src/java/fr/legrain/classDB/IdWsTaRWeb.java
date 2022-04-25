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
public class IdWsTaRWeb implements Serializable{

    @Column(name="id_r_web")
    private Integer idRWeb;

    @Column(name="id_tiers")
    private Integer idTiers;

    @Column(name="id_web")
    private Integer idWeb;

    public IdWsTaRWeb() {
    }

    public Integer getIdRWeb() {
        return idRWeb;
    }

    public void setIdRWeb(Integer idRWeb) {
        this.idRWeb = idRWeb;
    }

    public Integer getIdTiers() {
        return idTiers;
    }

    public void setIdTiers(Integer idTiers) {
        this.idTiers = idTiers;
    }

    public Integer getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(Integer idWeb) {
        this.idWeb = idWeb;
    }

}
