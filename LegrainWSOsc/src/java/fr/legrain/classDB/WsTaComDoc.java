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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_com_doc")
public class WsTaComDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_com_doc",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="id_document",nullable=false)
    private Integer idDocuemnt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_t_doc")
    private WsTaTDoc wsTaTDoc;

    @Column(name="note_com_doc",length=255)
    private String noteComDoc;

    public WsTaComDoc() {
    }

    public WsTaComDoc(Integer idDocuemnt, WsTaTDoc wsTaTDoc, String noteComDoc) {
        this.idDocuemnt = idDocuemnt;
        this.wsTaTDoc = wsTaTDoc;
        this.noteComDoc = noteComDoc;
    }

    public Integer getIdDocuemnt() {
        return idDocuemnt;
    }

    public void setIdDocuemnt(Integer idDocuemnt) {
        this.idDocuemnt = idDocuemnt;
    }

    public String getNoteComDoc() {
        return noteComDoc;
    }

    public void setNoteComDoc(String noteComDoc) {
        this.noteComDoc = noteComDoc;
    }

    public WsTaTDoc getWsTaTDoc() {
        return wsTaTDoc;
    }

    public void setWsTaTDoc(WsTaTDoc wsTaTDoc) {
        this.wsTaTDoc = wsTaTDoc;
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
        if (!(object instanceof WsTaComDoc)) {
            return false;
        }
        WsTaComDoc other = (WsTaComDoc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaComDoc[id=" + id + "]";
    }

}
