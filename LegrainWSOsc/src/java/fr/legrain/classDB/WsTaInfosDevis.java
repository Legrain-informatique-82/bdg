/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_infos_devis")
public class WsTaInfosDevis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_infos_devis",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_infos_devis_osc")
    private Integer idInfosDevisOsc;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_devis",nullable=false)
    private WsTaDevis wsTaDevis;

    @Column(name="adresse1",length=100)
    private String adresse1;

    @Column(name="adresse2",length=100)
    private String adresse2;

    @Column(name="adresse3",length=100)
    private String adresse3;

    @Column(name="codepostal",length=5)
    private String codePostal;

    @Column(name="ville",length=100)
    private String ville;

    @Column(name="pays",length=100)
    private String pays;

    @Column(name="adresse1_liv",length=100)
    private String adresse1Liv;

    @Column(name="adresse2_liv",length=100)
    private String adresse2Liv;

    @Column(name="adresse3_liv",length=100)
    private String adresse3Liv;

    @Column(name="codepostal_liv",length=5)
    private String codePostalLiv;

    @Column(name="ville_liv",length=100)
    private String villeLiv;

    @Column(name="pays_liv",length=100)
    private String pays_Liv;

    @Column(name="code_compta",length=8,nullable=false)
    private String codeCompta;

    @Column(name="compte",length=8,nullable=false)
    private String compte;

    @Column(name="nom_tiers",length=100)
    private String nomTiers;

    @Column(name="prenom_tiers",length=100)
    private String prenomTiers;

    @Column(name="surnom_tiers",length=20)
    private String surnomTiers;

    @Column(name="code_t_civilite",length=20,nullable=false)
    private String codeTCivilite;

    @Column(name="code_t_entite",length=20,nullable=false)
    private String codeTEntite;

    @Column(name="tva_i_com_compl",length=50)
    private String tvaIComCompl;

    @Column(name="code_c_paiement",length=20,nullable=false)
    private String codeCPaiement;

    @Column(name="lib_c_paiement",length=255)
    private String libCPaiement;

    @Column(name="report_c_paiement")
    private Integer reportCPaiement;

    @Column(name="fin_mois_c_paiement")
    private Integer finMoisCPaiement;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    public WsTaInfosDevis() {
    }

    public WsTaInfosDevis(Integer idInfosDevisOsc, WsTaDevis wsTaDevis, String adresse1,String adresse2, String adresse3, String codePostal, String ville, String pays, String adresse1Liv, String adresse2Liv, String adresse3Liv, String codePostalLiv, String villeLiv, String pays_Liv, String codeCompta, String compte, String nomTiers, String prenomTiers, String surnomTiers, String codeTCivilite, String codeTEntite, String tvaIComCompl, String codeCPaiement, String libCPaiement, Integer reportCPaiement, Integer finMoisCPaiement, Date quandAdd) {
        this.idInfosDevisOsc = idInfosDevisOsc;
        this.wsTaDevis = wsTaDevis;
        this.adresse1 = adresse1;
        this.adresse2 = adresse2;
        this.adresse3 = adresse3;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.adresse1Liv = adresse1Liv;
        this.adresse2Liv = adresse2Liv;
        this.adresse3Liv = adresse3Liv;
        this.codePostalLiv = codePostalLiv;
        this.villeLiv = villeLiv;
        this.pays_Liv = pays_Liv;
        this.codeCompta = codeCompta;
        this.compte = compte;
        this.nomTiers = nomTiers;
        this.prenomTiers = prenomTiers;
        this.surnomTiers = surnomTiers;
        this.codeTCivilite = codeTCivilite;
        this.codeTEntite = codeTEntite;
        this.tvaIComCompl = tvaIComCompl;
        this.codeCPaiement = codeCPaiement;
        this.libCPaiement = libCPaiement;
        this.reportCPaiement = reportCPaiement;
        this.finMoisCPaiement = finMoisCPaiement;
        this.quandAdd = quandAdd;
    }

    public WsTaInfosDevis(Integer idInfosDevisOsc, String codeCompta, String compte, String codeTCivilite, String codeTEntite, String codeCPaiement) {
        this.idInfosDevisOsc = idInfosDevisOsc;
        this.codeCompta = codeCompta;
        this.compte = compte;
        this.codeTCivilite = codeTCivilite;
        this.codeTEntite = codeTEntite;
        this.codeCPaiement = codeCPaiement;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdresse1Liv() {
        return adresse1Liv;
    }

    public void setAdresse1Liv(String adresse1Liv) {
        this.adresse1Liv = adresse1Liv;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public String getAdresse2Liv() {
        return adresse2Liv;
    }

    public void setAdresse2Liv(String adresse2Liv) {
        this.adresse2Liv = adresse2Liv;
    }

    public String getAdresse3() {
        return adresse3;
    }

    public void setAdresse3(String adresse3) {
        this.adresse3 = adresse3;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getAdresse3Liv() {
        return adresse3Liv;
    }

    public void setAdresse3Liv(String adresse3Liv) {
        this.adresse3Liv = adresse3Liv;
    }

    public String getCodeCPaiement() {
        return codeCPaiement;
    }

    public void setCodeCPaiement(String codeCPaiement) {
        this.codeCPaiement = codeCPaiement;
    }

    public String getCodeCompta() {
        return codeCompta;
    }

    public void setCodeCompta(String codeCompta) {
        this.codeCompta = codeCompta;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCodePostalLiv() {
        return codePostalLiv;
    }

    public void setCodePostalLiv(String codePostalLiv) {
        this.codePostalLiv = codePostalLiv;
    }

    public String getCodeTCivilite() {
        return codeTCivilite;
    }

    public void setCodeTCivilite(String codeTCivilite) {
        this.codeTCivilite = codeTCivilite;
    }

    public String getCodeTEntite() {
        return codeTEntite;
    }

    public void setCodeTEntite(String codeTEntite) {
        this.codeTEntite = codeTEntite;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public Integer getFinMoisCPaiement() {
        return finMoisCPaiement;
    }

    public void setFinMoisCPaiement(Integer finMoisCPaiement) {
        this.finMoisCPaiement = finMoisCPaiement;
    }

    public Integer getIdInfosDevisOsc() {
        return idInfosDevisOsc;
    }

    public void setIdInfosDevisOsc(Integer idInfosDevisOsc) {
        this.idInfosDevisOsc = idInfosDevisOsc;
    }

    public String getLibCPaiement() {
        return libCPaiement;
    }

    public void setLibCPaiement(String libCPaiement) {
        this.libCPaiement = libCPaiement;
    }

    public String getNomTiers() {
        return nomTiers;
    }

    public void setNomTiers(String nomTiers) {
        this.nomTiers = nomTiers;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getPays_Liv() {
        return pays_Liv;
    }

    public void setPays_Liv(String pays_Liv) {
        this.pays_Liv = pays_Liv;
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

    public Integer getReportCPaiement() {
        return reportCPaiement;
    }

    public void setReportCPaiement(Integer reportCPaiement) {
        this.reportCPaiement = reportCPaiement;
    }

    public String getSurnomTiers() {
        return surnomTiers;
    }

    public void setSurnomTiers(String surnomTiers) {
        this.surnomTiers = surnomTiers;
    }

    public String getTvaIComCompl() {
        return tvaIComCompl;
    }

    public void setTvaIComCompl(String tvaIComCompl) {
        this.tvaIComCompl = tvaIComCompl;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVilleLiv() {
        return villeLiv;
    }

    public void setVilleLiv(String villeLiv) {
        this.villeLiv = villeLiv;
    }

    public WsTaDevis getWsTaDevis() {
        return wsTaDevis;
    }

    public void setWsTaDevis(WsTaDevis wsTaDevis) {
        this.wsTaDevis = wsTaDevis;
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
        if (!(object instanceof WsTaInfosDevis)) {
            return false;
        }
        WsTaInfosDevis other = (WsTaInfosDevis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaInfosDevis[id=" + id + "]";
    }

}
