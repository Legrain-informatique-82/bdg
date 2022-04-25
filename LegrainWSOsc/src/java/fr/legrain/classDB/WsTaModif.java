/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lee
 */
@Entity
@Table(name="ws_ta_modif")
public class WsTaModif implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id_modif")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="table_modif",length=255)
    private String tableModif;

    @Column(name="champ_modif",length=255)
    private String champModif;

    @Column(name="valeur_modif",length=255)
    private String valeurModif;

    public WsTaModif() {
    }

    public String getChampModif() {
        return champModif;
    }

    public void setChampModif(String champModif) {
        this.champModif = champModif;
    }

    public String getTableModif() {
        return tableModif;
    }

    public void setTableModif(String tableModif) {
        this.tableModif = tableModif;
    }

    public String getValeurModif() {
        return valeurModif;
    }

    public void setValeurModif(String valeurModif) {
        this.valeurModif = valeurModif;
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
        if (!(object instanceof WsTaModif)) {
            return false;
        }
        WsTaModif other = (WsTaModif) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.legrain.classDB.WsTaModif[id=" + id + "]";
    }

}
