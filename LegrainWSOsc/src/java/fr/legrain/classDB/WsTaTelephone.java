/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_telephone")
public class WsTaTelephone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_telephone")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_telephone_osc",nullable=false)
    private Integer idTelephoneOsc;

    @Column(name="numero_telephone",nullable=false,length=20)
    private String numeroTelephone;

    @Column(name="poste_telephone",nullable=false,length=20)
    private String posteTelephone;

    @Column(name="id_i_tiers")
    private Integer idITiers;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    @OneToMany(mappedBy="wsTaTelephone", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaRTel> wsTaRTel = new HashSet<WsTaRTel>();

    @OneToMany(mappedBy="wsTaTelephone",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaRTelTTel> wsTaRTelTTel = new HashSet<WsTaRTelTTel>();

    public WsTaTelephone() {
    }

    public Set<WsTaRTelTTel> getWsTaRTelTTel() {
        return wsTaRTelTTel;
    }

    public void setWsTaRTelTTel(Set<WsTaRTelTTel> wsTaRTelTTel) {
        this.wsTaRTelTTel = wsTaRTelTTel;
    }

    public Set<WsTaRTel> getWsTaRTel() {
        return wsTaRTel;
    }

    public void setWsTaRTel(Set<WsTaRTel> wsTaRTel) {
        this.wsTaRTel = wsTaRTel;
    }

    public Integer getIdITiers() {
        return idITiers;
    }

    public void setIdITiers(Integer idITiers) {
        this.idITiers = idITiers;
    }

    public Integer getIdTelephoneOsc() {
        return idTelephoneOsc;
    }

    public void setIdTelephoneOsc(Integer idTelephoneOsc) {
        this.idTelephoneOsc = idTelephoneOsc;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getPosteTelephone() {
        return posteTelephone;
    }

    public void setPosteTelephone(String posteTelephone) {
        this.posteTelephone = posteTelephone;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsTaTelephone)) {
            return false;
        }
        WsTaTelephone other = (WsTaTelephone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTelephone[id=" + id + "]";
    }

}
