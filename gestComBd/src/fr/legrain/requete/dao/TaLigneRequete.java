/**
 * TaLigneRequete.java 
 */
package fr.legrain.requete.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaTiers;

/**
 * @author Nicolas²
 *
 */

@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_LIGNE_REQUETE")
@SequenceGenerator(name = "GEN_LIGNE_RQT", sequenceName = "NUM_ID_LIGNE_RQT", allocationSize = 1)


public class TaLigneRequete implements java.io.Serializable {
	private int id;
	private TaRequete rqt;
	private int idGroupe;
	private int andOrGroupe;
	private int idLigne;
	private String typeLigne;
	private int andOrLigne;
	private int combo1;
	private int combo2;
	private String valeur1;
	private String valeur2;

	public TaLigneRequete() {
	}


	public TaLigneRequete(int id,TaRequete rqt, int idGroupe, int andOrGroupe, int idLigne
			, String typeLigne, int andOrLigne, int combo1, int combo2
			, String valeur1, String valeur2) {
		
		this.id = id;
		this.rqt = rqt;
		this.idGroupe = idGroupe;
		this.andOrGroupe = andOrGroupe;
		this.idLigne = idLigne;
		this.typeLigne = typeLigne;
		this.andOrLigne = andOrLigne;
		this.combo1 = combo1;
		this.combo2 = combo2;
		this.valeur1 = valeur1;
		this.valeur2 = valeur2;
		
	}
	
	public TaLigneRequete(TaRequete rqt, int idGroupe, int andOrGroupe, int idLigne
			, String typeLigne, int andOrLigne, int combo1, int combo2
			, String valeur1, String valeur2) {
		
		this.rqt = rqt;
		this.idGroupe = idGroupe;
		this.andOrGroupe = andOrGroupe;
		this.idLigne = idLigne;
		this.typeLigne = typeLigne;
		this.andOrLigne = andOrLigne;
		this.combo1 = combo1;
		this.combo2 = combo2;
		this.valeur1 = valeur1;
		this.valeur2 = valeur2;
		
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_LIGNE_RQT")
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH} )
	@JoinColumn(name = "ID_RQT")
	public TaRequete getRqt() {
		return rqt;
	}


	public void setRqt(TaRequete rqt) {
		this.rqt = rqt;
	}


	@Column(name = "ID_GROUPE", nullable = false)
	public int getIdGroupe() {
		return idGroupe;
	}


	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}

	@Column(name = "ANDOR_GROUPE", nullable = false)
	public int getAndOrGroupe() {
		return andOrGroupe;
	}

	
	public void setAndOrGroupe(int andOrGroupe) {
		this.andOrGroupe = andOrGroupe;
	}

	@Column(name = "ID_LIGNE", nullable = false)
	public int getIdLigne() {
		return idLigne;
	}


	public void setIdLigne(int idLigne) {
		this.idLigne = idLigne;
	}

	@Column(name = "TYPE_LIGNE", nullable = false)
	public String getTypeLigne() {
		return typeLigne;
	}


	public void setTypeLigne(String typeLigne) {
		this.typeLigne = typeLigne;
	}

	@Column(name = "ANDOR_LIGNE", nullable = false)
	public int getAndOrLigne() {
		return andOrLigne;
	}


	public void setAndOrLigne(int andOrLigne) {
		this.andOrLigne = andOrLigne;
	}

	@Column(name = "COMBO_1", nullable = false)
	public int getCombo1() {
		return combo1;
	}


	public void setCombo1(int combo1) {
		this.combo1 = combo1;
	}

	@Column(name = "COMBO_2", nullable = false)
	public int getCombo2() {
		return combo2;
	}


	public void setCombo2(int combo2) {
		this.combo2 = combo2;
	}


	@Column(name = "VALEUR_1")
	public String getValeur1() {
		return valeur1;
	}


	public void setValeur1(String valeur1) {
		this.valeur1 = valeur1;
	}

	@Column(name = "VALEUR_2")
	public String getValeur2() {
		return valeur2;
	}


	public void setValeur2(String valeur2) {
		this.valeur2 = valeur2;
	}

}
