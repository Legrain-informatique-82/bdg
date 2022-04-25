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

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_r_adr")
public class WsTaRAdr implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    IdWsTaRAdr idWsTaRAdr = new IdWsTaRAdr();


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers",insertable=false,updatable=false)
    private WsTaTiers wsTaTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_adresse",insertable=false,updatable=false)
    private WsTaAdresse wsTaAdresse;


    public WsTaRAdr() {
    }

    public IdWsTaRAdr getIdWsTaRAdr() {
        return idWsTaRAdr;
    }

    public void setIdWsTaRAdr(IdWsTaRAdr idWsTaRAdr) {
        this.idWsTaRAdr = idWsTaRAdr;
    }

    public WsTaAdresse getWsTaAdresse() {
        return wsTaAdresse;
    }

    public void setWsTaAdresse(WsTaAdresse wsTaAdresse) {
        this.wsTaAdresse = wsTaAdresse;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

}
