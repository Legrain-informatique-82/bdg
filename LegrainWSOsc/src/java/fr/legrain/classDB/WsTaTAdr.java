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
@Table(name="ws_ta_t_adr")
public class WsTaTAdr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_adr")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_adr",length=20,nullable=false)
    private String codeTAdr;

    @Column(name="libl_t_adr",length=100,nullable=false)
    private String liblTAdr;

    @OneToMany(mappedBy="wsTaTAdr",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaRAdrTAdr> wsTaRAdrTAdr = new HashSet<WsTaRAdrTAdr>();

    public WsTaTAdr() {
    }

    
    public String getCodeTAdr() {
        return codeTAdr;
    }

    public void setCodeTAdr(String codeTAdr) {
        this.codeTAdr = codeTAdr;
    }

    public String getLiblTAdr() {
        return liblTAdr;
    }

    public void setLiblTAdr(String liblTAdr) {
        this.liblTAdr = liblTAdr;
    }

    public Set<WsTaRAdrTAdr> getWsTaRAdrTAdr() {
        return wsTaRAdrTAdr;
    }

    public void setWsTaRAdrTAdr(Set<WsTaRAdrTAdr> wsTaRAdrTAdr) {
        this.wsTaRAdrTAdr = wsTaRAdrTAdr;
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
        if (!(object instanceof WsTaTAdr)) {
            return false;
        }
        WsTaTAdr other = (WsTaTAdr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.wsTaTadr[id=" + id + "]";
    }

}
