/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_acces")
public class WsTaAcces implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_acces",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="password_acces",length=50,nullable=false)
    private String passwoerAcces;
    
    @Column(name="ip_acces",length=50,nullable=false)
    private String ipAcces;

    @Column(name="user_acces",length=50,nullable=false)
    private String userAcces;

    public WsTaAcces() {
    }

    public WsTaAcces(String passwoerAcces, String ipAcces, String userAcces) {
        this.passwoerAcces = passwoerAcces;
        this.ipAcces = ipAcces;
        this.userAcces = userAcces;
    }

    
    public String getIpAcces() {
        return ipAcces;
    }

    public void setIpAcces(String ipAcces) {
        this.ipAcces = ipAcces;
    }

    public String getPasswoerAcces() {
        return passwoerAcces;
    }

    public void setPasswoerAcces(String passwoerAcces) {
        this.passwoerAcces = passwoerAcces;
    }

    public String getUserAcces() {
        return userAcces;
    }

    public void setUserAcces(String userAcces) {
        this.userAcces = userAcces;
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
        if (!(object instanceof WsTaAcces)) {
            return false;
        }
        WsTaAcces other = (WsTaAcces) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaAcces[id=" + id + "]";
    }

}
