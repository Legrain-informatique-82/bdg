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
public class IdWsTaRAdrTAdr implements Serializable{

    @Column(name="id_r_adr_t_adr")
    private Integer idRAdrTAdr;

    @Column(name="id_t_adr")
    private Integer idTAdr;

    @Column(name="id_adresse")
    private Integer idAdresse;

    public IdWsTaRAdrTAdr() {
    }

    public Integer getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    public Integer getIdRAdrTAdr() {
        return idRAdrTAdr;
    }

    public void setIdRAdrTAdr(Integer idRAdrTAdr) {
        this.idRAdrTAdr = idRAdrTAdr;
    }

    public Integer getIdTAdr() {
        return idTAdr;
    }

    public void setIdTAdr(Integer idTAdr) {
        this.idTAdr = idTAdr;
    }
    
}
