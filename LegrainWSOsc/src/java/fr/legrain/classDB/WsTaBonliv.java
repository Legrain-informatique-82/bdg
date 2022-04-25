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
@Table(name="ws_ta_bonliv")
public class WsTaBonliv implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_bonliv",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_bon_liv",length=20,nullable=false)
    private String codeBonliv;

    @Column(name="date_bonliv")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBonliv;

    @Column(name="date_liv_bonliv")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLivBonliv;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_adresse")
    //@Column(name="id_adresse")
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

    @Column(name="rem_ht_bonliv",precision=15,scale=2)
    private BigDecimal remHtBonliv;

    @Column(name="tx_rem_ht_bonliv",precision=15,scale=2)
    private BigDecimal txRemHtBonliv;

    @Column(name="rem_ttc_bonliv",precision=15,scale=2)
    private BigDecimal remTccBonliv;

    @Column(name="tx_rem_ttc_bonliv",precision=15,scale=2)
    private BigDecimal txRemTtcBonliv;

    @Column(name="nb_e_bonliv")
    private Integer nbEBonliv;

    @Column(name="ttc")
    private Integer Ttc;

    @Column(name="commentaire",length=255)
    private String commentaire;

    @OneToOne(mappedBy="wsTaBonliv", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private WsTaInfosBonliv wsTaInfosBonliv;
    
    @OneToMany(mappedBy="wsTaBonliv", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WsTaLBonliv> wsTaLBonlivs = new HashSet<WsTaLBonliv>();

    public WsTaBonliv(){
        
    }

    public WsTaBonliv(String codeBonliv, Date dateBonliv, Date dateLivBonliv, WsTaAdresse wsTaAdresse, WsTaAdresse wsTaAdresseLiv, WsTaTiers wsTaTiers, WsTaTPaiement wsTaTPaiement, WsTaCPaiement wsTaCPaiement, BigDecimal remHtBonliv, BigDecimal txRemHtBonliv, BigDecimal remTccBonliv, BigDecimal txRemTtcBonliv, Integer nbEBonliv, Integer Ttc, String commentaire) {
        this.codeBonliv = codeBonliv;
        this.dateBonliv = dateBonliv;
        this.dateLivBonliv = dateLivBonliv;
        this.wsTaAdresse = wsTaAdresse;
        this.wsTaAdresseLiv = wsTaAdresseLiv;
        this.wsTaTiers = wsTaTiers;
        this.wsTaTPaiement = wsTaTPaiement;
        this.wsTaCPaiement = wsTaCPaiement;
        this.remHtBonliv = remHtBonliv;
        this.txRemHtBonliv = txRemHtBonliv;
        this.remTccBonliv = remTccBonliv;
        this.txRemTtcBonliv = txRemTtcBonliv;
        this.nbEBonliv = nbEBonliv;
        this.Ttc = Ttc;
        this.commentaire = commentaire;
    }

    public Set<WsTaLBonliv> getWsTaLBonlivs() {
        return wsTaLBonlivs;
    }

    public void setWsTaLBonlivs(Set<WsTaLBonliv> wsTaLBonlivs) {
        this.wsTaLBonlivs = wsTaLBonlivs;
    }

    public WsTaInfosBonliv getWsTaInfosBonliv() {
        return wsTaInfosBonliv;
    }

    public void setWsTaInfosBonliv(WsTaInfosBonliv wsTaInfosBonliv) {
        this.wsTaInfosBonliv = wsTaInfosBonliv;
    }

    
    public Integer getTtc() {
        return Ttc;
    }

    public void setTtc(Integer Ttc) {
        this.Ttc = Ttc;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getNbEBonliv() {
        return nbEBonliv;
    }

    public void setNbEBonliv(Integer nbEBonliv) {
        this.nbEBonliv = nbEBonliv;
    }

    public BigDecimal getRemHtBonliv() {
        return remHtBonliv;
    }

    public void setRemHtBonliv(BigDecimal remHtBonliv) {
        this.remHtBonliv = remHtBonliv;
    }

    public BigDecimal getRemTccBonliv() {
        return remTccBonliv;
    }

    public void setRemTccBonliv(BigDecimal remTccBonliv) {
        this.remTccBonliv = remTccBonliv;
    }

    public BigDecimal getTxRemHtBonliv() {
        return txRemHtBonliv;
    }

    public void setTxRemHtBonliv(BigDecimal txRemHtBonliv) {
        this.txRemHtBonliv = txRemHtBonliv;
    }

    public BigDecimal getTxRemTtcBonliv() {
        return txRemTtcBonliv;
    }

    public void setTxRemTtcBonliv(BigDecimal txRemTtcBonliv) {
        this.txRemTtcBonliv = txRemTtcBonliv;
    }

    public WsTaTPaiement getWsTaTPaiement() {
        return wsTaTPaiement;
    }

    public WsTaCPaiement getWsTaCPaiement() {
        return wsTaCPaiement;
    }

    public void setWsTaCPaiement(WsTaCPaiement wsTaCPaiement) {
        this.wsTaCPaiement = wsTaCPaiement;
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

    public String getCodeBonliv() {
        return codeBonliv;
    }

    public void setCodeBonliv(String codeBonliv) {
        this.codeBonliv = codeBonliv;
    }

    public Date getDateBonliv() {
        return dateBonliv;
    }

    public void setDateBonliv(Date dateBonliv) {
        this.dateBonliv = dateBonliv;
    }

    public Date getDateLivBonliv() {
        return dateLivBonliv;
    }

    public void setDateLivBonliv(Date dateLivBonliv) {
        this.dateLivBonliv = dateLivBonliv;
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
        if (!(object instanceof WsTaBonliv)) {
            return false;
        }
        WsTaBonliv other = (WsTaBonliv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaBonliv[id=" + id + "]";
    }

}
