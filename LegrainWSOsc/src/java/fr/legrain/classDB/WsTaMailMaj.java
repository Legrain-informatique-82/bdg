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
@Table(name="ws_ta_mail_maj")
public class WsTaMailMaj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_mail_maj")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="sujet_mail_maj",length=255)
    private String sujetMailMaj;

    @Column(name="nom_site_mail_maj",length=255,nullable=false)
    private String nomSiteMailMaj;

    @Column(name="url_mail_maj",length=255,nullable=false)
    private String urlMailMaj;

    @Column(name="fait_mail_maj")
    @Temporal(TemporalType.TIMESTAMP)
    private Date faitMailMaj;

    @Column(name="a_faire_mail_maj",nullable=false)
    private Integer aFaireMailMaj;

    @Column(name="from_mail_maj",length=255)
    private String fromMailMaj;
    
    @Column(name="date_mail_maj")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateMailMaj;

    public WsTaMailMaj() {
    }

    public Integer getAFaireMailMaj() {
        return aFaireMailMaj;
    }

    public void setAFaireMailMaj(Integer aFaireMailMaj) {
        this.aFaireMailMaj = aFaireMailMaj;
    }

    public Date getDateMailMaj() {
        return dateMailMaj;
    }

    public void setDateMailMaj(Date dateMailMaj) {
        this.dateMailMaj = dateMailMaj;
    }

    public Date getFaitMailMaj() {
        return faitMailMaj;
    }

    public void setFaitMailMaj(Date faitMailMaj) {
        this.faitMailMaj = faitMailMaj;
    }

    public String getFromMailMaj() {
        return fromMailMaj;
    }

    public void setFromMailMaj(String fromMailMaj) {
        this.fromMailMaj = fromMailMaj;
    }

    public String getNomSiteMailMaj() {
        return nomSiteMailMaj;
    }

    public void setNomSiteMailMaj(String nomSiteMailMaj) {
        this.nomSiteMailMaj = nomSiteMailMaj;
    }

    public String getSujetMailMaj() {
        return sujetMailMaj;
    }

    public void setSujetMailMaj(String sujetMailMaj) {
        this.sujetMailMaj = sujetMailMaj;
    }

    public String getUrlMailMaj() {
        return urlMailMaj;
    }

    public void setUrlMailMaj(String urlMailMaj) {
        this.urlMailMaj = urlMailMaj;
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
        if (!(object instanceof WsTaMailMaj)) {
            return false;
        }
        WsTaMailMaj other = (WsTaMailMaj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaMailMaj[id=" + id + "]";
    }

}
