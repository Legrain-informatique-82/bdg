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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_t_tel")
public class WsTaTTel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_tel")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_tel",length=20,nullable=false)
    private String codeTTel;

    @Column(name="libl_t_tel",length=100)
    private String liblTTel;

    @OneToMany(mappedBy="wsTaTTel",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaRTelTTel> wsTaRTelTTel = new HashSet<WsTaRTelTTel>();

    public WsTaTTel() {
    }

    public Set<WsTaRTelTTel> getWsTaRTelTTel() {
        return wsTaRTelTTel;
    }

    public void setWsTaRTelTTel(Set<WsTaRTelTTel> wsTaRTelTTel) {
        this.wsTaRTelTTel = wsTaRTelTTel;
    }

    public String getCodeTTel() {
        return codeTTel;
    }

    public void setCodeTTel(String codeTTel) {
        this.codeTTel = codeTTel;
    }

    public String getLiblTTel() {
        return liblTTel;
    }

    public void setLiblTTel(String liblTTel) {
        this.liblTTel = liblTTel;
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
        if (!(object instanceof WsTaTTel)) {
            return false;
        }
        WsTaTTel other = (WsTaTTel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTTel[id=" + id + "]";
    }

}
