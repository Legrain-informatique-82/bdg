/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_ref_prix")
public class WsTaRefPrix implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_ref_prix")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_article")
    private WsTaArticle wsTaArticle;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_prix")
    private WsTaPrix wsTaPrix;

    public WsTaRefPrix() {
    }

    public WsTaRefPrix(WsTaArticle wsTaArticle, WsTaPrix wsTaPrix) {
        this.wsTaArticle = wsTaArticle;
        this.wsTaPrix = wsTaPrix;
    }

    public WsTaArticle getWsTaArticle() {
        return wsTaArticle;
    }

    public void setWsTaArticle(WsTaArticle wsTaArticle) {
        this.wsTaArticle = wsTaArticle;
    }

    public WsTaPrix getWsTaPrix() {
        return wsTaPrix;
    }

    public void setWsTaPrix(WsTaPrix wsTaPrix) {
        this.wsTaPrix = wsTaPrix;
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
        if (!(object instanceof WsTaRefPrix)) {
            return false;
        }
        WsTaRefPrix other = (WsTaRefPrix) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaRefPrix[id=" + id + "]";
    }

}
