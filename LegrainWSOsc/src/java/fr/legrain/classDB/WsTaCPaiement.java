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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_c_paiement")
public class WsTaCPaiement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_c_paiement",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_c_paiement",nullable=false,length=50)
    private String codeCPaiement;

    @Column(name="lib_c_paiement",length=255)
    private String libCPaiement;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers",nullable=false)
    private WsTaTiers wsTaTiers;

    @Column(name="report_c_paiement")
    private Integer reportCPaiement;

    @Column(name="fin_mois_c_paiement")
    private Integer finMoisCPaiement;

    @OneToMany(mappedBy="wsTaCPaiement",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaBonliv> WsTaBonlivs = new HashSet<WsTaBonliv>();

    @OneToMany(mappedBy="wsTaCPaiement",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaDevis> WsTaDevis = new HashSet<WsTaDevis>();

    @OneToMany(mappedBy="wsTaCPaiement",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaFacture> WsTaFactures = new HashSet<WsTaFacture>();

    public Set<WsTaFacture> getWsTaFactures() {
        return WsTaFactures;
    }

    public void setWsTaFactures(Set<WsTaFacture> WsTaFactures) {
        this.WsTaFactures = WsTaFactures;
    }
   
    
    public WsTaCPaiement() {
    }

    public WsTaCPaiement(String codeCPaiement, String libCPaiement, WsTaTiers wsTaTiers, Integer reportCPaiement, Integer finMoisCPaiement) {
        this.codeCPaiement = codeCPaiement;
        this.libCPaiement = libCPaiement;
        this.wsTaTiers = wsTaTiers;
        this.reportCPaiement = reportCPaiement;
        this.finMoisCPaiement = finMoisCPaiement;
    }

    public Set<WsTaBonliv> getWsTaBonlivs() {
        return WsTaBonlivs;
    }

    public void setWsTaBonlivs(Set<WsTaBonliv> WsTaBonlivs) {
        this.WsTaBonlivs = WsTaBonlivs;
    }

    public Set<WsTaDevis> getWsTaDevis() {
        return WsTaDevis;
    }

    public void setWsTaDevis(Set<WsTaDevis> WsTaDevis) {
        this.WsTaDevis = WsTaDevis;
    }

    
    public Integer getFinMoisCPaiement() {
        return finMoisCPaiement;
    }

    public void setFinMoisCPaiement(Integer finMoisCPaiement) {
        this.finMoisCPaiement = finMoisCPaiement;
    }

    public Integer getReportCPaiement() {
        return reportCPaiement;
    }

    public void setReportCPaiement(Integer reportCPaiement) {
        this.reportCPaiement = reportCPaiement;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

    public String getCodeCPaiement() {
        return codeCPaiement;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setCodeCPaiement(String codeCPaiement) {
        this.codeCPaiement = codeCPaiement;
    }

    public String getLibCPaiement() {
        return libCPaiement;
    }

    public void setLibCPaiement(String libCPaiement) {
        this.libCPaiement = libCPaiement;
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
        if (!(object instanceof WsTaCPaiement)) {
            return false;
        }
        WsTaCPaiement other = (WsTaCPaiement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaCPaiement[id=" + id + "]";
    }

}
