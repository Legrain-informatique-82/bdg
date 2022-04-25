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
@Table(name="ws_ta_adresse")
public class WsTaAdresse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_adresse",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_adresse_osc",nullable=false)
    private Integer idAdresseOsc;

    @Column(name="adresse1_adresse",length=100)
    private String adresse1Adresse;

    @Column(name="adresse2_adresse",length=100)
    private String adresse2Adresse;

    @Column(name="adresse3_adresse",length=100)
    private String adresse3Adresse;

    @Column(name="codepostal_adresse",length=5)
    private String codepostalAdresse;

    @Column(name="ville_adresse",length=100,nullable=false)
    private String villeAdresse;

    @Column(name="pays_adresse",length=100,nullable=false)
    private String paysAdresse;


    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaBonliv> wsTaBonliv = new HashSet<WsTaBonliv>();

    @OneToMany(mappedBy="wsTaAdresseLiv",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaBonliv> wsTaBonlivAdresse = new HashSet<WsTaBonliv>();

    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaDevis> wsTaDevisAdresse = new HashSet<WsTaDevis>();

    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaDevis> wsTaDevisAdresseLiv = new HashSet<WsTaDevis>();

    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaFacture> wsTaFactureAdresse = new HashSet<WsTaFacture>();

    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaFacture> wsTaFacturesAdresseLiv = new HashSet<WsTaFacture>();

    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaRAdr> wsTaRAdr = new HashSet<WsTaRAdr>();

    @OneToMany(mappedBy="wsTaAdresse",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaRAdrTAdr> wsTaRAdrTAdrs = new HashSet<WsTaRAdrTAdr>();

    public Integer getIdAdresseOsc() {
        return idAdresseOsc;
    }

    public void setIdAdresseOsc(Integer idAdresseOsc) {
        this.idAdresseOsc = idAdresseOsc;
    }

    public Set<WsTaRAdrTAdr> getWsTaRAdrTAdrs() {
        return wsTaRAdrTAdrs;
    }

    public void setWsTaRAdrTAdrs(Set<WsTaRAdrTAdr> wsTaRAdrTAdrs) {
        this.wsTaRAdrTAdrs = wsTaRAdrTAdrs;
    }
    
    public Set<WsTaRAdr> getWsTaRAdr() {
        return wsTaRAdr;
    }

    public void setWsTaRAdr(Set<WsTaRAdr> wsTaRAdr) {
        this.wsTaRAdr = wsTaRAdr;
    }
    
    public Set<WsTaFacture> getWsTaFactureAdresse() {
        return wsTaFactureAdresse;
    }

    public void setWsTaFactureAdresse(Set<WsTaFacture> wsTaFactureAdresse) {
        this.wsTaFactureAdresse = wsTaFactureAdresse;
    }

    public Set<WsTaFacture> getWsTaFacturesAdresseLiv() {
        return wsTaFacturesAdresseLiv;
    }

    public void setWsTaFacturesAdresseLiv(Set<WsTaFacture> wsTaFacturesAdresseLiv) {
        this.wsTaFacturesAdresseLiv = wsTaFacturesAdresseLiv;
    }
    

    public Set<WsTaDevis> getWsTaDevisAdresseLiv() {
        return wsTaDevisAdresseLiv;
    }

    public void setWsTaDevisAdresseLiv(Set<WsTaDevis> wsTaDevisAdresseLiv) {
        this.wsTaDevisAdresseLiv = wsTaDevisAdresseLiv;
    }
    

    public Set<WsTaDevis> getWsTaDevisAdresse() {
        return wsTaDevisAdresse;
    }

    public void setWsTaDevisAdresse(Set<WsTaDevis> wsTaDevisAdresse) {
        this.wsTaDevisAdresse = wsTaDevisAdresse;
    }


    public Set<WsTaBonliv> getWsTaBonlivAdresse() {
        return wsTaBonlivAdresse;
    }

    public void setWsTaBonlivAdresse(Set<WsTaBonliv> wsTaBonlivAdresse) {
        this.wsTaBonlivAdresse = wsTaBonlivAdresse;
    }


    public WsTaAdresse() {
    }

    public WsTaAdresse(String villeAdresse, String paysAdresse) {
        this.villeAdresse = villeAdresse;
        this.paysAdresse = paysAdresse;
    }

    public WsTaAdresse(String adresse1Adresse, String adresse2Adresse, String adresse3Adresse, String codepostalAdresse, String villeAdresse, String paysAdresse) {
        this.adresse1Adresse = adresse1Adresse;
        this.adresse2Adresse = adresse2Adresse;
        this.adresse3Adresse = adresse3Adresse;
        this.codepostalAdresse = codepostalAdresse;
        this.villeAdresse = villeAdresse;
        this.paysAdresse = paysAdresse;
    }

    public Set<WsTaBonliv> getWsTaBonliv() {
        return wsTaBonliv;
    }

    public void setWsTaBonliv(Set<WsTaBonliv> wsTaBonliv) {
        this.wsTaBonliv = wsTaBonliv;
    }
    
    public String getCodepostalAdresse() {
        return codepostalAdresse;
    }

    public void setCodepostalAdresse(String codepostalAdresse) {
        this.codepostalAdresse = codepostalAdresse;
    }

    public String getPaysAdresse() {
        return paysAdresse;
    }

    public void setPaysAdresse(String paysAdresse) {
        this.paysAdresse = paysAdresse;
    }

    public String getVilleAdresse() {
        return villeAdresse;
    }

    public void setVilleAdresse(String villeAdresse) {
        this.villeAdresse = villeAdresse;
    }

    public String getAdresse1Adresse() {
        return adresse1Adresse;
    }

    public void setAdresse1Adresse(String adresse1Adresse) {
        this.adresse1Adresse = adresse1Adresse;
    }

    public String getAdresse2Adresse() {
        return adresse2Adresse;
    }

    public void setAdresse2Adresse(String adresse2Adresse) {
        this.adresse2Adresse = adresse2Adresse;
    }

    public String getAdresse3Adresse() {
        return adresse3Adresse;
    }

    public void setAdresse3Adresse(String adresse3Adresse) {
        this.adresse3Adresse = adresse3Adresse;
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
        if (!(object instanceof WsTaAdresse)) {
            return false;
        }
        WsTaAdresse other = (WsTaAdresse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaAdresse[id=" + id + "]";
    }

}
