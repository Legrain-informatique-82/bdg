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
@Table(name="ws_ta_email")
public class WsTaEmail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_email",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_email_osc",nullable=false)
    private Integer idEmailOsc;

    @Column(name="adresse_email",length=255)
    private String adresseEmail;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    @OneToMany(mappedBy="wsTaEmail", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaREmail> wsTaREmails = new HashSet<WsTaREmail>();
    
    public WsTaEmail() {
    }

    public WsTaEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public Integer getIdEmailOsc() {
        return idEmailOsc;
    }

    public void setIdEmailOsc(Integer idEmailOsc) {
        this.idEmailOsc = idEmailOsc;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public Set<WsTaREmail> getWsTaREmails() {
        return wsTaREmails;
    }

    public void setWsTaREmails(Set<WsTaREmail> wsTaREmails) {
        this.wsTaREmails = wsTaREmails;
    }

    
    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
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
        if (!(object instanceof WsTaEmail)) {
            return false;
        }
        WsTaEmail other = (WsTaEmail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaEmail[id=" + id + "]";
    }

}
