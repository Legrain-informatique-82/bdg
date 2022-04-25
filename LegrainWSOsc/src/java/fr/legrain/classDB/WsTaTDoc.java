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
@Table(name="ws_ta_t_doc")
public class WsTaTDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_t_doc",nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code_t_doc",length=50,nullable=false)
    private String codeTDoc;

    @Column(name="lib_t_doc",length=255)
    private String libTDoc;

    @OneToMany(mappedBy="wsTaTDoc",cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<WsTaComDoc> wsTaComDoc = new HashSet<WsTaComDoc>();

    public WsTaTDoc(){
        
    }
    public WsTaTDoc(String codeTDoc, String libTDoc) {
        this.codeTDoc = codeTDoc;
        this.libTDoc = libTDoc;
    }

    public String getCodeTDoc() {
        return codeTDoc;
    }

    public void setCodeTDoc(String codeTDoc) {
        this.codeTDoc = codeTDoc;
    }

    public String getLibTDoc() {
        return libTDoc;
    }

    public void setLibTDoc(String libTDoc) {
        this.libTDoc = libTDoc;
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
        if (!(object instanceof WsTaTDoc)) {
            return false;
        }
        WsTaTDoc other = (WsTaTDoc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaTDoc[id=" + id + "]";
    }

}
