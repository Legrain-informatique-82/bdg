package fr.legrain.boutique.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "TA_CORRESPONDANCE_ID_BOUTIQUE")
@SequenceGenerator(name = "GEN_CORRESPONDANCE_ID_BOUTIQUE", sequenceName = "NUM_ID_CORRESP_ID_BOUTIQUE", allocationSize = 1)
@NamedQueries(value = { 
		@NamedQuery(name=TaCorrespondanceIDBoutique.QN.FIND_BY_TYPE_TABLE, query="select a from TaCorrespondanceIDBoutique a where a.typeTable = ? "),
		@NamedQuery(name=TaCorrespondanceIDBoutique.QN.FIND_BY_ID_BOUTIQUE, query="select a from TaCorrespondanceIDBoutique a where a.typeTable = ? and a.idBoutique = ? "),
		@NamedQuery(name=TaCorrespondanceIDBoutique.QN.FIND_BY_ID_BDG, query="select a from TaCorrespondanceIDBoutique a where a.typeTable = ? and a.idBdg = ? ")
		})
public class TaCorrespondanceIDBoutique implements java.io.Serializable {

	public static class QN {
		public static final String FIND_BY_TYPE_TABLE = "TaCorrespondanceIDBoutique.findByTypeTable";
		public static final String FIND_BY_ID_BOUTIQUE = "TaCorrespondanceIDBoutique.findByIdBoutique";
		public static final String FIND_BY_ID_BDG = "TaCorrespondanceIDBoutique.findByIdBdg";
	}
	
	private int id;
	private int idBdg;
	private int idBoutique;
	private String typeTable;
	private Date creation;
	private Integer versionObj;
	
	public TaCorrespondanceIDBoutique() {
		super();
	}
	
	public TaCorrespondanceIDBoutique(String typeTable,int idBdg,int idBoutique) {
		super();
		this.typeTable = typeTable;
		this.idBdg = idBdg;
		this.idBoutique = idBoutique;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CORRESPONDANCE_ID_BOUTIQUE")
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "CREATION")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Column(name = "ID_BDG", nullable = false)
	public int getIdBdg() {
		return idBdg;
	}

	public void setIdBdg(int idBdg) {
		this.idBdg = idBdg;
	}

	@Column(name = "ID_BOUTIQUE", nullable = false)
	public int getIdBoutique() {
		return idBoutique;
	}

	public void setIdBoutique(int idBoutique) {
		this.idBoutique = idBoutique;
	}

	@Column(name = "TYPE_TABLE", unique = true, nullable = false)
	public String getTypeTable() {
		return typeTable;
	}

	public void setTypeTable(String typeTable) {
		this.typeTable = typeTable;
	}
	
	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	static public int chercheIdPresta(List<TaCorrespondanceIDBoutique> ids,int idBdg) {
		Iterator<TaCorrespondanceIDBoutique> ite = ids.iterator();
		boolean trouve = false;
		TaCorrespondanceIDBoutique tmp = null;
		int retour = 0;
		while(!trouve && ite.hasNext()) {
			tmp = ite.next();
			if(tmp.idBdg == idBdg) {
				retour = tmp.idBoutique;
				trouve = true;
			}
		}
		return retour;	
	}
	
	static public int chercheIdBdg(List<TaCorrespondanceIDBoutique> ids,int idPresta) {
		Iterator<TaCorrespondanceIDBoutique> ite = ids.iterator();
		boolean trouve = false;
		TaCorrespondanceIDBoutique tmp = null;
		int retour = 0;
		while(!trouve && ite.hasNext()) {
			tmp = ite.next();
			if(tmp.idBoutique == idPresta) {
				retour = tmp.idBdg;
				trouve = true;
			}
		}
		return retour;	
	}

}
