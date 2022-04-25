/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.legrain.classDB;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lee
 */
@Entity
@Table(name = "ws_ta_nombre_connect")
public class WsTaNombreConnect implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "nombre_connect", nullable = false)
    private Integer nombreConnect;
    @Column(name = "login_user", length = 20)
    private String loginUser;
    @Column(name = "password_user", length = 20)
    private String passwordUser;
    @Column(name = "quand_connect")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandConnect;

    public WsTaNombreConnect() {
    }

    public WsTaNombreConnect(Integer id,Integer nombreConnect,String loginUser,String passwordUser,Date quandConnect){
        this.id = id;
        this.nombreConnect = nombreConnect;
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.quandConnect = quandConnect;
        
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Integer getNombreConnect() {
        return nombreConnect;
    }

    public void setNombreConnect(Integer nombreConnect) {
        this.nombreConnect = nombreConnect;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Date getQuandConnect() {
        return quandConnect;
    }

    public void setQuandConnect(Date quandConnect) {
        this.quandConnect = quandConnect;
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
        if (!(object instanceof WsTaNombreConnect)) {
            return false;
        }
        WsTaNombreConnect other = (WsTaNombreConnect) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaNombreConnect[id=" + id + "]";
    }
}
