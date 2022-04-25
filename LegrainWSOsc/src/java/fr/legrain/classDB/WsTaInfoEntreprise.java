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
@Table(name="ws_ta_info_entreprise")
public class WsTaInfoEntreprise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_info_entreprise")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_info_entreprise_osc")
    private Integer idInfoEntrepriseOsc;

    @Column(name="nom_info_entreprise",length=100)
    private String nomInfoEntreprise;

    @Column(name="adresse1_info_entreprise",length=100)
    private String adresse1InfoEntrepeise;

    @Column(name="adresse2_info_entreprise",length=100)
    private String adresse2InfoEntrepeise;

    @Column(name="adresse3_info_entreprise",length=100)
    private String adresse3InfoEntrepeise;

    @Column(name="codepostal_info_entreprise",length=5)
    private String codepostalInfoEntreprise;

    @Column(name="ville_info_entreprise",length=100,nullable=false)
    private String villeInfoEntreprise;

    @Column(name="pays_info_entreprise",length=100,nullable=false)
    private String paysInfoEntreprise;

    @Column(name="siret_info_entreprise",length=100)
    private String siretInfoEntreprise;

    @Column(name="capital_info_entreprise",length=100)
    private String capitalInfoEntreprise;

    @Column(name="ape_info_entreprise",length=100)
    private String apeInfoEntreprise;

    @Column(name="tva_intra_info_entreprise",length=100)
    private String tvaIntraInfoEntreprise;

    @Column(name="tel_info_entreprise",length=20,nullable=false)
    private String telInfoEntreprise;

    @Column(name="fax_info_entreprise",length=20,nullable=false)
    private String faxInfoEntreprise;

    @Column(name="email_info_entreprise",length=100)
    private String emaiInfoEntreprise;

    @Column(name="web_info_entreprise",length=100)
    private String webInfoEntreprise;

    @Column(name="rcs_info_entreprise",length=100)
    private String rcsInfoEntreprise;

    @Column(name="datedeb_info_entreprise")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datedebInfoEntreprise;

    @Column(name="datefin_info_entreprise")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datefinInfoEntreprise;

    @Column(name="codexo_info_entreprise",length=2,nullable=false)
    private String codexoInfoEntreprise;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    public WsTaInfoEntreprise() {
    }

    public WsTaInfoEntreprise(Integer idInfoEntrepriseOsc, String nomInfoEntreprise, String adresse1InfoEntrepeise, String adresse2InfoEntrepeise, String adresse3InfoEntrepeise, String codepostalInfoEntreprise, String villeInfoEntreprise, String paysInfoEntreprise, String siretInfoEntreprise, String capitalInfoEntreprise, String apeInfoEntreprise, String tvaIntraInfoEntreprise, String telInfoEntreprise, String faxInfoEntreprise, String emaiInfoEntreprise, String webInfoEntreprise, String rcsInfoEntreprise, Date datedebInfoEntreprise, Date datefinInfoEntreprise, String codexoInfoEntreprise, Date quandAdd) {
        this.idInfoEntrepriseOsc = idInfoEntrepriseOsc;
        this.nomInfoEntreprise = nomInfoEntreprise;
        this.adresse1InfoEntrepeise = adresse1InfoEntrepeise;
        this.adresse2InfoEntrepeise = adresse2InfoEntrepeise;
        this.adresse3InfoEntrepeise = adresse3InfoEntrepeise;
        this.codepostalInfoEntreprise = codepostalInfoEntreprise;
        this.villeInfoEntreprise = villeInfoEntreprise;
        this.paysInfoEntreprise = paysInfoEntreprise;
        this.siretInfoEntreprise = siretInfoEntreprise;
        this.capitalInfoEntreprise = capitalInfoEntreprise;
        this.apeInfoEntreprise = apeInfoEntreprise;
        this.tvaIntraInfoEntreprise = tvaIntraInfoEntreprise;
        this.telInfoEntreprise = telInfoEntreprise;
        this.faxInfoEntreprise = faxInfoEntreprise;
        this.emaiInfoEntreprise = emaiInfoEntreprise;
        this.webInfoEntreprise = webInfoEntreprise;
        this.rcsInfoEntreprise = rcsInfoEntreprise;
        this.datedebInfoEntreprise = datedebInfoEntreprise;
        this.datefinInfoEntreprise = datefinInfoEntreprise;
        this.codexoInfoEntreprise = codexoInfoEntreprise;
        this.quandAdd = quandAdd;
    }

    public String getAdresse1InfoEntrepeise() {
        return adresse1InfoEntrepeise;
    }

    public void setAdresse1InfoEntrepeise(String adresse1InfoEntrepeise) {
        this.adresse1InfoEntrepeise = adresse1InfoEntrepeise;
    }

    public String getAdresse2InfoEntrepeise() {
        return adresse2InfoEntrepeise;
    }

    public void setAdresse2InfoEntrepeise(String adresse2InfoEntrepeise) {
        this.adresse2InfoEntrepeise = adresse2InfoEntrepeise;
    }

    public String getAdresse3InfoEntrepeise() {
        return adresse3InfoEntrepeise;
    }

    public void setAdresse3InfoEntrepeise(String adresse3InfoEntrepeise) {
        this.adresse3InfoEntrepeise = adresse3InfoEntrepeise;
    }

    public String getApeInfoEntreprise() {
        return apeInfoEntreprise;
    }

    public void setApeInfoEntreprise(String apeInfoEntreprise) {
        this.apeInfoEntreprise = apeInfoEntreprise;
    }

    public String getCapitalInfoEntreprise() {
        return capitalInfoEntreprise;
    }

    public void setCapitalInfoEntreprise(String capitalInfoEntreprise) {
        this.capitalInfoEntreprise = capitalInfoEntreprise;
    }

    public String getCodepostalInfoEntreprise() {
        return codepostalInfoEntreprise;
    }

    public void setCodepostalInfoEntreprise(String codepostalInfoEntreprise) {
        this.codepostalInfoEntreprise = codepostalInfoEntreprise;
    }

    public String getCodexoInfoEntreprise() {
        return codexoInfoEntreprise;
    }

    public void setCodexoInfoEntreprise(String codexoInfoEntreprise) {
        this.codexoInfoEntreprise = codexoInfoEntreprise;
    }

    public Date getDatedebInfoEntreprise() {
        return datedebInfoEntreprise;
    }

    public void setDatedebInfoEntreprise(Date datedebInfoEntreprise) {
        this.datedebInfoEntreprise = datedebInfoEntreprise;
    }

    public Date getDatefinInfoEntreprise() {
        return datefinInfoEntreprise;
    }

    public void setDatefinInfoEntreprise(Date datefinInfoEntreprise) {
        this.datefinInfoEntreprise = datefinInfoEntreprise;
    }

    public String getEmaiInfoEntreprise() {
        return emaiInfoEntreprise;
    }

    public void setEmaiInfoEntreprise(String emaiInfoEntreprise) {
        this.emaiInfoEntreprise = emaiInfoEntreprise;
    }

    public String getFaxInfoEntreprise() {
        return faxInfoEntreprise;
    }

    public void setFaxInfoEntreprise(String faxInfoEntreprise) {
        this.faxInfoEntreprise = faxInfoEntreprise;
    }

    public Integer getIdInfoEntrepriseOsc() {
        return idInfoEntrepriseOsc;
    }

    public void setIdInfoEntrepriseOsc(Integer idInfoEntrepriseOsc) {
        this.idInfoEntrepriseOsc = idInfoEntrepriseOsc;
    }

    public String getNomInfoEntreprise() {
        return nomInfoEntreprise;
    }

    public void setNomInfoEntreprise(String nomInfoEntreprise) {
        this.nomInfoEntreprise = nomInfoEntreprise;
    }

    public String getPaysInfoEntreprise() {
        return paysInfoEntreprise;
    }

    public void setPaysInfoEntreprise(String paysInfoEntreprise) {
        this.paysInfoEntreprise = paysInfoEntreprise;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public String getRcsInfoEntreprise() {
        return rcsInfoEntreprise;
    }

    public void setRcsInfoEntreprise(String rcsInfoEntreprise) {
        this.rcsInfoEntreprise = rcsInfoEntreprise;
    }

    public String getSiretInfoEntreprise() {
        return siretInfoEntreprise;
    }

    public void setSiretInfoEntreprise(String siretInfoEntreprise) {
        this.siretInfoEntreprise = siretInfoEntreprise;
    }

    public String getTelInfoEntreprise() {
        return telInfoEntreprise;
    }

    public void setTelInfoEntreprise(String telInfoEntreprise) {
        this.telInfoEntreprise = telInfoEntreprise;
    }

    public String getTvaIntraInfoEntreprise() {
        return tvaIntraInfoEntreprise;
    }

    public void setTvaIntraInfoEntreprise(String tvaIntraInfoEntreprise) {
        this.tvaIntraInfoEntreprise = tvaIntraInfoEntreprise;
    }

    public String getVilleInfoEntreprise() {
        return villeInfoEntreprise;
    }

    public void setVilleInfoEntreprise(String villeInfoEntreprise) {
        this.villeInfoEntreprise = villeInfoEntreprise;
    }

    public String getWebInfoEntreprise() {
        return webInfoEntreprise;
    }

    public void setWebInfoEntreprise(String webInfoEntreprise) {
        this.webInfoEntreprise = webInfoEntreprise;
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
        if (!(object instanceof WsTaInfoEntreprise)) {
            return false;
        }
        WsTaInfoEntreprise other = (WsTaInfoEntreprise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaInfoEntreprise[id=" + id + "]";
    }

}
