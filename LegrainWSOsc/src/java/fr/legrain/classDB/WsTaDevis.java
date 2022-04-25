/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="ws_ta_devis")
public class WsTaDevis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_devis",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_devis",length=20,nullable=false)
    private String codeDevis;

    @Column(name="date_devis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDevis;

    @Column(name="date_ech_devis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEchDevis;

    @Column(name="date_liv_devis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLivDevis;

    @Column(name="libelle_devis",length=255)
    private String libelle_devis;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_adresse")
    private WsTaAdresse wsTaAdresse;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_adresse_liv")
    private WsTaAdresse wsTaAdresseLiv;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_tiers")
    private WsTaTiers wsTaTiers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_paiement")
    private WsTaTPaiement wsTaTPaiement;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_c_paiement")
    private WsTaCPaiement wsTaCPaiement;

    @Column(name="rem_ht_devis",precision=15,scale=2)
    private BigDecimal remHtDevis;

    @Column(name="tx_rem_ht_devis",precision=15,scale=2)
    private BigDecimal txRemHtDevis;

    @Column(name="rem_ttc_devis",precision=15,scale=2)
    private BigDecimal remTtcDevis;

    @Column(name="tx_rem_ttc_devis",precision=15,scale=2)
    private BigDecimal txRemTtcDevis;

    @Column(name="nb_e_devis")
    private Integer nbEDevis;

    @Column(name="ttc",nullable=false)
    private Integer ttc;

    @Column(name="export",nullable=false)
    private Integer export;

    @Column(name="commentaire",length=255)
    private String commentaire;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    @OneToOne(mappedBy="wsTaDevis",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaInfosDevis wsTaInfosDevis;

    @OneToMany(mappedBy="wsTaDevis", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaLDevis> wsTaLDevis = new HashSet<WsTaLDevis>();
    public WsTaDevis() {
    }

    public WsTaDevis(String codeDevis, Date dateDevis, Date dateEchDevis, Date dateLivDevis, String libelle_devis, WsTaAdresse wsTaAdresse, WsTaAdresse wsTaAdresseLiv, WsTaTiers wsTaTiers, WsTaTPaiement wsTaTPaiement, WsTaCPaiement wsTaCPaiement, BigDecimal remHtDevis, BigDecimal txRemHtDevis, BigDecimal remTtcDevis, BigDecimal txRemTtcDevis, Integer nbEDevis, Integer ttc, Integer export, String commentaire, Date quandAdd) {
        this.codeDevis = codeDevis;
        this.dateDevis = dateDevis;
        this.dateEchDevis = dateEchDevis;
        this.dateLivDevis = dateLivDevis;
        this.libelle_devis = libelle_devis;
        this.wsTaAdresse = wsTaAdresse;
        this.wsTaAdresseLiv = wsTaAdresseLiv;
        this.wsTaTiers = wsTaTiers;
        this.wsTaTPaiement = wsTaTPaiement;
        this.wsTaCPaiement = wsTaCPaiement;
        this.remHtDevis = remHtDevis;
        this.txRemHtDevis = txRemHtDevis;
        this.remTtcDevis = remTtcDevis;
        this.txRemTtcDevis = txRemTtcDevis;
        this.nbEDevis = nbEDevis;
        this.ttc = ttc;
        this.export = export;
        this.commentaire = commentaire;
        this.quandAdd = quandAdd;
    }

    public WsTaInfosDevis getWsTaInfosDevis() {
        return wsTaInfosDevis;
    }

    public void setWsTaInfosDevis(WsTaInfosDevis wsTaInfosDevis) {
        this.wsTaInfosDevis = wsTaInfosDevis;
    }

    public Set<WsTaLDevis> getWsTaLDevis() {
        return wsTaLDevis;
    }

    public void setWsTaLDevis(Set<WsTaLDevis> wsTaLDevis) {
        this.wsTaLDevis = wsTaLDevis;
    }

    
    public WsTaAdresse getWsTaAdresse() {
        return wsTaAdresse;
    }

    public void setWsTaAdresse(WsTaAdresse wsTaAdresse) {
        this.wsTaAdresse = wsTaAdresse;
    }

    public WsTaAdresse getWsTaAdresseLiv() {
        return wsTaAdresseLiv;
    }

    public void setWsTaAdresseLiv(WsTaAdresse wsTaAdresseLiv) {
        this.wsTaAdresseLiv = wsTaAdresseLiv;
    }

    public WsTaCPaiement getWsTaCPaiement() {
        return wsTaCPaiement;
    }

    public void setWsTaCPaiement(WsTaCPaiement wsTaCPaiement) {
        this.wsTaCPaiement = wsTaCPaiement;
    }

    public WsTaTPaiement getWsTaTPaiement() {
        return wsTaTPaiement;
    }

    public void setWsTaTPaiement(WsTaTPaiement wsTaTPaiement) {
        this.wsTaTPaiement = wsTaTPaiement;
    }

    public WsTaTiers getWsTaTiers() {
        return wsTaTiers;
    }

    public void setWsTaTiers(WsTaTiers wsTaTiers) {
        this.wsTaTiers = wsTaTiers;
    }

    public String getCodeDevis() {
        return codeDevis;
    }

    public void setCodeDevis(String codeDevis) {
        this.codeDevis = codeDevis;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(Date dateDevis) {
        this.dateDevis = dateDevis;
    }

    public Date getDateEchDevis() {
        return dateEchDevis;
    }

    public void setDateEchDevis(Date dateEchDevis) {
        this.dateEchDevis = dateEchDevis;
    }

    public Date getDateLivDevis() {
        return dateLivDevis;
    }

    public void setDateLivDevis(Date dateLivDevis) {
        this.dateLivDevis = dateLivDevis;
    }

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    public String getLibelle_devis() {
        return libelle_devis;
    }

    public void setLibelle_devis(String libelle_devis) {
        this.libelle_devis = libelle_devis;
    }

    public Integer getNbEDevis() {
        return nbEDevis;
    }

    public void setNbEDevis(Integer nbEDevis) {
        this.nbEDevis = nbEDevis;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public BigDecimal getRemHtDevis() {
        return remHtDevis;
    }

    public void setRemHtDevis(BigDecimal remHtDevis) {
        this.remHtDevis = remHtDevis;
    }

    public BigDecimal getRemTtcDevis() {
        return remTtcDevis;
    }

    public void setRemTtcDevis(BigDecimal remTtcDevis) {
        this.remTtcDevis = remTtcDevis;
    }

    public Integer getTtc() {
        return ttc;
    }

    public void setTtc(Integer ttc) {
        this.ttc = ttc;
    }

    public BigDecimal getTxRemHtDevis() {
        return txRemHtDevis;
    }

    public void setTxRemHtDevis(BigDecimal txRemHtDevis) {
        this.txRemHtDevis = txRemHtDevis;
    }

    public BigDecimal getTxRemTtcDevis() {
        return txRemTtcDevis;
    }

    public void setTxRemTtcDevis(BigDecimal txRemTtcDevis) {
        this.txRemTtcDevis = txRemTtcDevis;
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
        if (!(object instanceof WsTaDevis)) {
            return false;
        }
        WsTaDevis other = (WsTaDevis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaDevis[id=" + id + "]";
    }

}
