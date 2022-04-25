/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.legrain.classDB;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name = "ws_ta_banque")
public class WsTaBanque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_banque", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "code_banque", length = 20, nullable = false)
    private String codeBanque;
    @Column(name = "libc_banque", length = 100, nullable = false)
    private String libcBanque;

    @Column(name = "libl_banque", length = 100)
    private String liblBanque;

    @ManyToMany(mappedBy="wsTaBanques",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaTiers> wsTaTiers = new HashSet<WsTaTiers>();

    public Set<WsTaTiers> getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(Set<WsTaTiers> wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }
    
    public String getCodeBanque() {
        return codeBanque;
    }

    public String getLibcBanque() {
        return libcBanque;
    }

    public String getLiblBanque() {
        return liblBanque;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public void setLibcBanque(String libcBanque) {
        this.libcBanque = libcBanque;
    }

    public void setLiblBanque(String liblBanque) {
        this.liblBanque = liblBanque;
    }

    public WsTaBanque() {
    }

    public WsTaBanque(String codeBanque,String libcBanque){
        this.codeBanque = codeBanque;
        this.libcBanque = libcBanque;
    }
    public WsTaBanque(String codeBanque, String libcBanque, String liblBanque) {
        this.codeBanque = codeBanque;
        this.libcBanque = libcBanque;
        this.liblBanque = liblBanque;
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
        if (!(object instanceof WsTaBanque)) {
            return false;
        }
        WsTaBanque other = (WsTaBanque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaBanque[id=" + id + "]";
    }
}
