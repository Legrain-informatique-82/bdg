/**
 * TaRequete.java 
 */
package fr.legrain.requete.dao;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaTiers;

/**
 * @author Nicolas²
 *
 */

@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_REQUETE")
@SequenceGenerator(name = "GEN_RQT", sequenceName = "NUM_ID_RQT", allocationSize = 1)
@XmlRootElement(name="requete")


public class TaRequete implements java.io.Serializable {
	private int idRqt;
	private String libRqt;
	private String descRqt;
	private int typeResultat;
	private Set<TaLigneRequete> taLigneRequete = new LinkedHashSet<TaLigneRequete>(0);

	public TaRequete() {
	}


	public TaRequete(int idRqt, String libRqt, String descRqt, int typeResultat) {
		
		this.idRqt = idRqt;
		this.libRqt = libRqt;
		this.descRqt = descRqt;
		this.typeResultat = typeResultat;
		
	}
	
	public TaRequete(String libRqt, String descRqt, int typeResultat) {
		
		this.libRqt = libRqt;
		this.libRqt = libRqt;
		this.descRqt = descRqt;
		this.typeResultat = typeResultat;
		
	}


	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_RQT")
	@Column(name = "ID_RQT", unique = true, nullable = false)
	public int getIdRqt() {
		return idRqt;
	}


	public void setIdRqt(int idRqt) {
		this.idRqt = idRqt;
	}


	@Column(name = "LIB_RQT", nullable = false)
	public String getLibRqt() {
		return libRqt;
	}
	
	public void setLibRqt(String libRqt) {
		this.libRqt = libRqt;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rqt")
	@OrderBy("rqt")
	public Set<TaLigneRequete> getTaLigneRequete() {
		return taLigneRequete;
	}


	public void setTaLigneRequete(Set<TaLigneRequete> taLigneRequete) {
		this.taLigneRequete = taLigneRequete;
	}

	@Column(name = "DESC_RQT")
	public String getDescRqt() {
		return descRqt;
	}


	public void setDescRqt(String descRqt) {
		this.descRqt = descRqt;
	}

	@Column(name = "TYPE_RESULTAT", nullable = false)
	public int getTypeResultat() {
		return typeResultat;
	}


	public void setTypeResultat(int typeResultat) {
		this.typeResultat = typeResultat;
	}



}
