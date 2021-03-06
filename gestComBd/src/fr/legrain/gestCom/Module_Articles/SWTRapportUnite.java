package fr.legrain.gestCom.Module_Articles;

import java.math.BigDecimal;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTFamille generated by hbm2java
 */
public class SWTRapportUnite extends ModelObject {

	private Integer id;
	private String codeArticle;
	private String codeUnite1;
	private String codeUnite2;
	private BigDecimal rapport=new BigDecimal(0);
	private Integer nbDecimal=new Integer(2);
	private Boolean sens;

	public SWTRapportUnite() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	public String getCodeArticle() {
		return codeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = codeArticle);
	}

	public String getCodeUnite1() {
		return codeUnite1;
	}

	public void setCodeUnite1(String codeUnite1) {
		firePropertyChange("codeUnite1", this.codeUnite1, this.codeUnite1 = codeUnite1);
	}

	public String getCodeUnite2() {
		return codeUnite2;
	}

	public void setCodeUnite2(String codeUnite2) {
		firePropertyChange("codeUnite2", this.codeUnite2, this.codeUnite2 = codeUnite2);
	}

	public BigDecimal getRapport() {
		return rapport;
	}

	public void setRapport(BigDecimal rapport) {
		firePropertyChange("rapport", this.rapport, this.rapport = rapport);
	}

	public Integer getNbDecimal() {
		return nbDecimal;
	}

	public void setNbDecimal(Integer nbDecimal) {
		firePropertyChange("nbDecimal", this.nbDecimal, this.nbDecimal = nbDecimal);
	}
	

	public Boolean getSens() {
		return sens;
	}

	public void setSens(Boolean sens) {
		firePropertyChange("sens", this.sens, this.sens = sens);
	}

	public static SWTRapportUnite copy(SWTRapportUnite swtRapportUnite){
		SWTRapportUnite swtRapportUniteLoc = new SWTRapportUnite();
		swtRapportUniteLoc.codeArticle = swtRapportUnite.codeArticle;
		swtRapportUniteLoc.codeUnite1 = swtRapportUnite.codeUnite1;
		swtRapportUniteLoc.codeUnite2 = swtRapportUnite.codeUnite2;
		swtRapportUniteLoc.rapport = swtRapportUnite.rapport;
		swtRapportUniteLoc.nbDecimal = swtRapportUnite.nbDecimal;
		
		return swtRapportUniteLoc;
		
	}
	public SWTRapportUnite(Integer id, String codeArticle, String codeUnite1,
			String codeUnite2, BigDecimal rapport, Integer nbDecimal) {
		super();
		this.id = id;
		this.codeArticle = codeArticle;
		this.codeUnite1 = codeUnite1;
		this.codeUnite2 = codeUnite2;
		this.rapport = rapport;
		this.nbDecimal = nbDecimal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeArticle == null) ? 0 : codeArticle.hashCode());
		result = prime * result
				+ ((codeUnite1 == null) ? 0 : codeUnite1.hashCode());
		result = prime * result
				+ ((codeUnite2 == null) ? 0 : codeUnite2.hashCode());
		result = prime * result + ((nbDecimal == null) ? 0 : nbDecimal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rapport == null) ? 0 : rapport.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTRapportUnite other = (SWTRapportUnite) obj;
		if (codeArticle == null) {
			if (other.codeArticle != null)
				return false;
		} else if (!codeArticle.equals(other.codeArticle))
			return false;
		if (codeUnite1 == null) {
			if (other.codeUnite1 != null)
				return false;
		} else if (!codeUnite1.equals(other.codeUnite1))
			return false;
		if (codeUnite2 == null) {
			if (other.codeUnite2 != null)
				return false;
		} else if (!codeUnite2.equals(other.codeUnite2))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nbDecimal == null) {
			if (other.nbDecimal != null)
				return false;
		} else if (!nbDecimal.equals(other.nbDecimal))
			return false;
		if (rapport == null) {
			if (other.rapport != null)
				return false;
		} else if (!rapport.equals(other.rapport))
			return false;
		return true;
	}

}
