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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lee
 */
@Entity
@Table(name = "ws_ta_tiers")
public class WsTaTiers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_tiers", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "id_tiers_osc", nullable = false)
    private Integer idTiersOsc;
    @Column(name = "code_tiers", length = 20, nullable = false)
    private String codeTiers;
    @Column(name = "code_compta", length = 7)
    private String codeCompta;
    @Column(name = "compte", length = 8)
    private String compte;
    @Column(name = "nom_tiers", length = 100, nullable = false)
    private String nomTiers;
    @Column(name = "prenom_tiers", length = 100)
    private String prenomTiers;
    @Column(name = "surnom_tiers", length = 20)
    private String surnomTiers;
    @Column(name = "actif_tiers")
    private Integer actifTiers;
    @Column(name = "ttc_tiers")
    private Integer ttcTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_civilite")
    private WsTaTCivilite wsTaTCivilite;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_tiers")
    private WsTaTTiers wsTaTTiers;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_entite")
    private WsTaTEntite wsTaTEntite;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="ws_ta_r_tiers_banque",joinColumns=@JoinColumn(name="id_tiers"),inverseJoinColumns=@JoinColumn(name="id_banque"))
    private Set<WsTaBanque> wsTaBanques = new HashSet<WsTaBanque>();

    @Column(name = "quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    @OneToMany(mappedBy = "wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaBonliv> wsTaBonlivs = new HashSet<WsTaBonliv>();

    @OneToOne(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaCPaiement wsTaCPaiement;

    @OneToOne(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaCommentaire WsTaCommentaire;

    @OneToOne(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaCompl WsTaCompl;

    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaEntreprise> wsTaEntreprises = new HashSet<WsTaEntreprise>();
   
    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaDevis> wsTaDevis = new HashSet<WsTaDevis>();

    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaFacture> wsTaFactures = new HashSet<WsTaFacture>();

    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaRAdr> wsTaRAdr = new HashSet<WsTaRAdr>();

    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaREmail> wsTaREmails = new HashSet<WsTaREmail>();
    
    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaRTel> wsTaRTel = new HashSet<WsTaRTel>();

    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaRCommercial> WsTaRCommercials = new HashSet<WsTaRCommercial>();

    @OneToMany(mappedBy="wsTaTiersCom", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaRCommercial> WsTaRCommercialComs = new HashSet<WsTaRCommercial>();

    @OneToMany(mappedBy="wsTaTiers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaRWeb> wsTaRWeb = new HashSet<WsTaRWeb>();

    public WsTaTCivilite getWsTaTCivilite() {
        return wsTaTCivilite;
    }

    public void setWsTaTCivilite(WsTaTCivilite wsTaTCivilite) {
        this.wsTaTCivilite = wsTaTCivilite;
    }

    public Set<WsTaBanque> getWsTaBanques() {
        return wsTaBanques;
    }

    public void setWsTaBanques(Set<WsTaBanque> wsTaBanques) {
        this.wsTaBanques = wsTaBanques;
    }

    public WsTaTEntite getWsTaTEntite() {
        return wsTaTEntite;
    }

    public WsTaTTiers getWsTaTTiers() {
        return wsTaTTiers;
    }

    public void setWsTaTTiers(WsTaTTiers wsTaTTiers) {
        this.wsTaTTiers = wsTaTTiers;
    }

    
    public void setWsTaTEntite(WsTaTEntite wsTaTEntite) {
        this.wsTaTEntite = wsTaTEntite;
    }


    public Set<WsTaBanque> getWsTaBanque() {
        return wsTaBanques;
    }

    public void setWsTaBanque(Set<WsTaBanque> wsTaBanques) {
        this.wsTaBanques = wsTaBanques;
    }
    
    public Set<WsTaRWeb> getWsTaRWeb() {
        return wsTaRWeb;
    }

    public void setWsTaRWeb(Set<WsTaRWeb> wsTaRWeb) {
        this.wsTaRWeb = wsTaRWeb;
    }

    public Set<WsTaRCommercial> getWsTaRCommercialComs() {
        return WsTaRCommercialComs;
    }

    public void setWsTaRCommercialComs(Set<WsTaRCommercial> WsTaRCommercialComs) {
        this.WsTaRCommercialComs = WsTaRCommercialComs;
    }

    public Set<WsTaRCommercial> getWsTaRCommercials() {
        return WsTaRCommercials;
    }

    public void setWsTaRCommercials(Set<WsTaRCommercial> WsTaRCommercials) {
        this.WsTaRCommercials = WsTaRCommercials;
    }

    public Set<WsTaRTel> getWsTaRTel() {
        return wsTaRTel;
    }

    public void setWsTaRTel(Set<WsTaRTel> wsTaRTel) {
        this.wsTaRTel = wsTaRTel;
    }

    public Set<WsTaREmail> getWsTaREmails() {
        return wsTaREmails;
    }

    public void setWsTaREmails(Set<WsTaREmail> wsTaREmails) {
        this.wsTaREmails = wsTaREmails;
    }

    public Set<WsTaRAdr> getWsTaRAdr() {
        return wsTaRAdr;
    }

    public void setWsTaRAdr(Set<WsTaRAdr> wsTaRAdr) {
        this.wsTaRAdr = wsTaRAdr;
    }

    public Set<WsTaFacture> getWsTaFactures() {
        return wsTaFactures;
    }

    public void setWsTaFactures(Set<WsTaFacture> wsTaFactures) {
        this.wsTaFactures = wsTaFactures;
    }
    

    public Set<WsTaDevis> getWsTaDevis() {
        return wsTaDevis;
    }

    public void setWsTaDevis(Set<WsTaDevis> wsTaDevis) {
        this.wsTaDevis = wsTaDevis;
    }

    
    public Set<WsTaEntreprise> getWsTaEntreprises() {
        return wsTaEntreprises;
    }

    public void setWsTaEntreprises(Set<WsTaEntreprise> wsTaEntreprises) {
        this.wsTaEntreprises = wsTaEntreprises;
    }

    public WsTaCompl getWsTaCompl() {
        return WsTaCompl;
    }

    public void setWsTaCompl(WsTaCompl WsTaCompl) {
        this.WsTaCompl = WsTaCompl;
    }

    public WsTaCommentaire getWsTaCommentaire() {
        return WsTaCommentaire;
    }

    public void setWsTaCommentaire(WsTaCommentaire WsTaCommentaire) {
        this.WsTaCommentaire = WsTaCommentaire;
    }

    
    public WsTaCPaiement getWsTaCPaiement() {
        return wsTaCPaiement;
    }

    public void setWsTaCPaiement(WsTaCPaiement wsTaCPaiement) {
        this.wsTaCPaiement = wsTaCPaiement;
    }


   
    public Set<WsTaBonliv> getWsTaBonlivs() {
        return wsTaBonlivs;
    }

    public void setWsTaBonlivs(Set<WsTaBonliv> wsTaBonlivs) {
        this.wsTaBonlivs = wsTaBonlivs;
    }

    public Integer getActifTiers() {
        return actifTiers;
    }

    public void setActifTiers(Integer actifTiers) {
        this.actifTiers = actifTiers;
    }

    public String getCodeCompta() {
        return codeCompta;
    }

    public void setCodeCompta(String codeCompta) {
        this.codeCompta = codeCompta;
    }

    public String getCodeTiers() {
        return codeTiers;
    }

    public void setCodeTiers(String codeTiers) {
        this.codeTiers = codeTiers;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public Integer getIdTiersOsc() {
        return idTiersOsc;
    }

    public void setIdTiersOsc(Integer idTiersOsc) {
        this.idTiersOsc = idTiersOsc;
    }

    public String getNomTiers() {
        return nomTiers;
    }

    public void setNomTiers(String nomTiers) {
        this.nomTiers = nomTiers;
    }

    public String getPrenomTiers() {
        return prenomTiers;
    }

    public void setPrenomTiers(String prenomTiers) {
        this.prenomTiers = prenomTiers;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public String getSurnomTiers() {
        return surnomTiers;
    }

    public void setSurnomTiers(String surnomTiers) {
        this.surnomTiers = surnomTiers;
    }

    public Integer getTtcTiers() {
        return ttcTiers;
    }

    public void setTtcTiers(Integer ttcTiers) {
        this.ttcTiers = ttcTiers;
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
        if (!(object instanceof WsTaTiers)) {
            return false;
        }
        WsTaTiers other = (WsTaTiers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTiers[id=" + id + "]";
    }
}
