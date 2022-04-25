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
public class IdWsTaRAdr implements Serializable{

    @Column(name="id_r_adr")
    private Integer idRAdr;

    @Column(name="id_tiers")
    private Integer idTiers;

    @Column(name="id_adresse")
    private Integer idAdresse;

    public IdWsTaRAdr() {
    }

    public Integer getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    public Integer getIdRAdr() {
        return idRAdr;
    }

    public void setIdRAdr(Integer idRAdr) {
        this.idRAdr = idRAdr;
    }

    public Integer getIdTiers() {
        return idTiers;
    }

    public void setIdTiers(Integer idTiers) {
        this.idTiers = idTiers;
    }
    
}
