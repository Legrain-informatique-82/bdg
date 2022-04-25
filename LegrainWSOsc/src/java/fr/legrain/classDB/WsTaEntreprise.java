/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_entreprise")
public class WsTaEntreprise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_entreprise",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_entreprise",length=20,nullable=false)
    private String codeEntreprise;

    @Column(name="libl_entreprise",length=100)
    private String liblEntreprise;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_entite")
    private WsTaTEntite wsTaTEntite;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_i_resp_tiers")
    private WsTaTiers wsTaTiers;

    public WsTaEntreprise() {
    }

    public WsTaEntreprise(String codeEntreprise, String liblEntreprise) {
        this.codeEntreprise = codeEntreprise;
        this.liblEntreprise = liblEntreprise;
    }
    
    public WsTaEntreprise(String codeEntreprise, String liblEntreprise, WsTaTEntite wsTaTEntite, WsTaTiers wsTaTiers) {
        this.codeEntreprise = codeEntreprise;
        this.liblEntreprise = liblEntreprise;
        this.wsTaTEntite = wsTaTEntite;
        this.wsTaTiers = wsTaTiers;
    }


    public String getCodeEntreprise() {
        return codeEntreprise;
    }

    public void setCodeEntreprise(String codeEntreprise) {
        this.codeEntreprise = codeEntreprise;
    }

    public String getLiblEntreprise() {
        return liblEntreprise;
    }

    public void setLiblEntreprise(String liblEntreprise) {
        this.liblEntreprise = liblEntreprise;
    }

    public WsTaTEntite getWsTaTEntite() {
        return wsTaTEntite;
    }

    public void setWsTaTEntite(WsTaTEntite wsTaTEntite) {
        this.wsTaTEntite = wsTaTEntite;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
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
        if (!(object instanceof WsTaEntreprise)) {
            return false;
        }
        WsTaEntreprise other = (WsTaEntreprise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaEntreprise[id=" + id + "]";
    }

}
