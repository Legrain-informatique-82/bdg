package fr.legrain.boutique.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TA_SYNCHRO_BOUTIQUE")
@SequenceGenerator(name = "GEN_SYNCHRO_BOUTIQUE", sequenceName = "NUM_ID_SYNCHRO_BOUTIQUE", allocationSize = 1)
public class TaSynchroBoutique implements java.io.Serializable {
	
	private int id;
	private Date derniereSynchro;
	private Date derniereExport;
	
	public TaSynchroBoutique() {
		super();
	}
	
	public TaSynchroBoutique(int id, Date derniereSynchro) {
		super();
		this.id = id;
		this.derniereSynchro = derniereSynchro;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SYNCHRO_BOUTIQUE")
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "DERNIERE_SYNCHRO", unique = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDerniereSynchro() {
		return derniereSynchro;
	}
	public void setDerniereSynchro(Date derniereSynchro) {
		this.derniereSynchro = derniereSynchro;
	}
	
	@Column(name = "DERNIERE_EXPORT", unique = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDerniereExport() {
		return derniereExport;
	}
	public void setDerniereExport(Date derniereExport) {
		this.derniereExport = derniereExport;
	}

}
