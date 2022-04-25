/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_unite")
public class WsTaUnite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_unite")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_unite",length=20,nullable=false)
    private String codeUnite;

    @Column(name="libl_unite",length=255)
    private String liblUnite;

    @OneToOne(mappedBy="wsTaUnite",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaPrix wsTaPrix;

    public WsTaUnite() {
    }

    public WsTaUnite(String codeUnite, String liblUnite, WsTaPrix wsTaPrix) {
        this.codeUnite = codeUnite;
        this.liblUnite = liblUnite;
        this.wsTaPrix = wsTaPrix;
    }

    public String getCodeUnite() {
        return codeUnite;
    }

    public void setCodeUnite(String codeUnite) {
        this.codeUnite = codeUnite;
    }

    public String getLiblUnite() {
        return liblUnite;
    }

    public void setLiblUnite(String liblUnite) {
        this.liblUnite = liblUnite;
    }

    public WsTaPrix getWsTaPrix() {
        return wsTaPrix;
    }

    public void setWsTaPrix(WsTaPrix wsTaPrix) {
        this.wsTaPrix = wsTaPrix;
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
        if (!(object instanceof WsTaUnite)) {
            return false;
        }
        WsTaUnite other = (WsTaUnite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaUnite[id=" + id + "]";
    }

}
