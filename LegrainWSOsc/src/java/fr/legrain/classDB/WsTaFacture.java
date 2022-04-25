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
@Table(name="ws_ta_facture")
public class WsTaFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_facture")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_facture_osc",nullable=false)
    private Integer idFactureOsc;

    @Column(name="code_facture",length=20,nullable=false)
    private String codeFacture;

    @Column(name="date_facture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFacture;

    @Column(name="date_ech_facture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEchFacture;

    @Column(name="date_liv_facture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLivFacture;

    @Column(name="libelle_facture",length=255)
    private String libelleFacture;

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

    @Column(name="regle_facture",precision=15,scale=2)
    private BigDecimal regleFacture;

    @Column(name="rem_ht_facture",precision=15,scale=2)
    private BigDecimal remHtFacture;

    @Column(name="tx_rem_ht_facture",precision=15,scale=2)
    private BigDecimal txRemHtFacture;

    @Column(name="rem_ttc_facture",precision=15,scale=2)
    private BigDecimal remTtcFacture;

    @Column(name="tx_rem_ttc_Facture",precision=15,scale=2)
    private BigDecimal txRemTtcFacture;

    @Column(name="nb_e_facture")
    private Integer nbEFacture;

    @Column(name="ttc",nullable=false)
    private Integer ttc;

    @Column(name="export",nullable=false)
    private Integer export;

    @Column(name="commentaire",length=255)
    private String commentaire;

    @Column(name="quand_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quandAdd;

    @OneToOne(mappedBy="wsTaFacture",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaInfosFacture wsTaInfoFacture;

    @OneToMany(mappedBy="wsTaFacture",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaLFacture> wsTaLFactures = new HashSet<WsTaLFacture>();
    
    public WsTaFacture() {
    }

    public WsTaFacture(Integer idFactureOsc, String codeFacture, Integer ttc, Integer export) {
        this.idFactureOsc = idFactureOsc;
        this.codeFacture = codeFacture;
        this.ttc = ttc;
        this.export = export;
    }

    
    public WsTaFacture(Integer idFactureOsc, String codeFacture, Date dateFacture, Date dateEchFacture, Date dateLivFacture, String libelleFacture, WsTaAdresse wsTaAdresse, WsTaAdresse wsTaAdresseLiv, WsTaTiers wsTaTiers, WsTaTPaiement wsTaTPaiement, WsTaCPaiement wsTaCPaiement, BigDecimal regleFacture, BigDecimal remHtFacture, BigDecimal txRemHtFacture, BigDecimal remTtcFacture, BigDecimal txRemTtcFacture, Integer nbEFacture, Integer ttc, Integer export, String commentaire, Date quandAdd) {
        this.idFactureOsc = idFactureOsc;
        this.codeFacture = codeFacture;
        this.dateFacture = dateFacture;
        this.dateEchFacture = dateEchFacture;
        this.dateLivFacture = dateLivFacture;
        this.libelleFacture = libelleFacture;
        this.wsTaAdresse = wsTaAdresse;
        this.wsTaAdresseLiv = wsTaAdresseLiv;
        this.wsTaTiers = wsTaTiers;
        this.wsTaTPaiement = wsTaTPaiement;
        this.wsTaCPaiement = wsTaCPaiement;
        this.regleFacture = regleFacture;
        this.remHtFacture = remHtFacture;
        this.txRemHtFacture = txRemHtFacture;
        this.remTtcFacture = remTtcFacture;
        this.txRemTtcFacture = txRemTtcFacture;
        this.nbEFacture = nbEFacture;
        this.ttc = ttc;
        this.export = export;
        this.commentaire = commentaire;
        this.quandAdd = quandAdd;
    }

    public Set<WsTaLFacture> getWsTaLFactures() {
        return wsTaLFactures;
    }

    public void setWsTaLFactures(Set<WsTaLFacture> wsTaLFactures) {
        this.wsTaLFactures = wsTaLFactures;
    }

    
    public WsTaInfosFacture getWsTaInfoFacture() {
        return wsTaInfoFacture;
    }

    public void setWsTaInfoFacture(WsTaInfosFacture wsTaInfoFacture) {
        this.wsTaInfoFacture = wsTaInfoFacture;
    }


    public String getCodeFacture() {
        return codeFacture;
    }

    public void setCodeFacture(String codeFacture) {
        this.codeFacture = codeFacture;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateEchFacture() {
        return dateEchFacture;
    }

    public void setDateEchFacture(Date dateEchFacture) {
        this.dateEchFacture = dateEchFacture;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Date getDateLivFacture() {
        return dateLivFacture;
    }

    public void setDateLivFacture(Date dateLivFacture) {
        this.dateLivFacture = dateLivFacture;
    }

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    public Integer getIdFactureOsc() {
        return idFactureOsc;
    }

    public void setIdFactureOsc(Integer idFactureOsc) {
        this.idFactureOsc = idFactureOsc;
    }

    public String getLibelleFacture() {
        return libelleFacture;
    }

    public void setLibelleFacture(String libelleFacture) {
        this.libelleFacture = libelleFacture;
    }

    public Integer getNbEFacture() {
        return nbEFacture;
    }

    public void setNbEFacture(Integer nbEFacture) {
        this.nbEFacture = nbEFacture;
    }

    public Date getQuandAdd() {
        return quandAdd;
    }

    public void setQuandAdd(Date quandAdd) {
        this.quandAdd = quandAdd;
    }

    public BigDecimal getRegleFacture() {
        return regleFacture;
    }

    public void setRegleFacture(BigDecimal regleFacture) {
        this.regleFacture = regleFacture;
    }

    public BigDecimal getRemHtFacture() {
        return remHtFacture;
    }

    public void setRemHtFacture(BigDecimal remHtFacture) {
        this.remHtFacture = remHtFacture;
    }

    public BigDecimal getRemTtcFacture() {
        return remTtcFacture;
    }

    public void setRemTtcFacture(BigDecimal remTtcFacture) {
        this.remTtcFacture = remTtcFacture;
    }

    public Integer getTtc() {
        return ttc;
    }

    public void setTtc(Integer ttc) {
        this.ttc = ttc;
    }

    public BigDecimal getTxRemHtFacture() {
        return txRemHtFacture;
    }

    public void setTxRemHtFacture(BigDecimal txRemHtFacture) {
        this.txRemHtFacture = txRemHtFacture;
    }

    public BigDecimal getTxRemTtcFacture() {
        return txRemTtcFacture;
    }

    public void setTxRemTtcFacture(BigDecimal txRemTtcFacture) {
        this.txRemTtcFacture = txRemTtcFacture;
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
        if (!(object instanceof WsTaFacture)) {
            return false;
        }
        WsTaFacture other = (WsTaFacture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaFacture[id=" + id + "]";
    }

}
