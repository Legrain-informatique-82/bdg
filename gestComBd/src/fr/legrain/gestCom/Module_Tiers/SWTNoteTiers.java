package fr.legrain.gestCom.Module_Tiers;

import java.util.Date;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

public class SWTNoteTiers extends ModelObject {

	private Integer idNoteTiers;
	private String noteTiers;
	private String codeTNoteTiers;
	private String codeTiers;
	private Integer idTiers;
	private Date dateNoteTiers = new Date();

	public SWTNoteTiers() {
	}

	public void setSWTNote(SWTNoteTiers swtNoteTiers) {
		this.idNoteTiers = swtNoteTiers.idNoteTiers;
		this.noteTiers = swtNoteTiers.noteTiers;
		this.codeTiers = swtNoteTiers.codeTiers;
		this.idTiers = swtNoteTiers.idTiers;
		this.codeTNoteTiers = swtNoteTiers.codeTNoteTiers;
		this.dateNoteTiers = swtNoteTiers.dateNoteTiers;
	}

	public static SWTNoteTiers copy(SWTNoteTiers swtNoteTiers){
		SWTNoteTiers swtNoteTiersLoc = new SWTNoteTiers();
		swtNoteTiersLoc.setIdTiers(swtNoteTiers.getIdTiers());                //1
		swtNoteTiersLoc.setIdNoteTiers(swtNoteTiers.getIdNoteTiers());                //1
		swtNoteTiersLoc.setNoteTiers(swtNoteTiers.getNoteTiers());                //1
		swtNoteTiersLoc.setCodeTiers(swtNoteTiers.getCodeTiers());                //1
		swtNoteTiersLoc.setCodeTNoteTiers(swtNoteTiers.getCodeTNoteTiers());                //1
		swtNoteTiersLoc.setDateNoteTiers(swtNoteTiers.getDateNoteTiers());        
		return swtNoteTiersLoc;
	}
	public Integer getIdNoteTiers() {
		return this.idNoteTiers;
	}

	public void setIdNoteTiers(Integer idNoteTiers) {
		firePropertyChange("idNoteTiers", this.idNoteTiers, this.idNoteTiers = idNoteTiers);
	}

	public String getNoteTiers() {
		return this.noteTiers;
	}

	public void setNoteTiers(String noteTiers) {
		firePropertyChange("noteTiers", this.noteTiers, this.noteTiers = noteTiers);
	}
	
	public String getCodeTNoteTiers() {
		return this.codeTNoteTiers;
	}

	public void setCodeTNoteTiers(String codeTNoteTiers) {
		firePropertyChange("codeTNoteTiers", this.codeTNoteTiers, this.codeTNoteTiers = codeTNoteTiers);
	}

	public String getCodeTiers() {
		return this.codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
	}

	public Integer getIdTiers() {
		return this.idTiers;
	}

	public void setIdTiers(Integer idTiers) {
		firePropertyChange("idTiers", this.idTiers, this.idTiers = idTiers);
	}
	
	public Date getDateNoteTiers() {
		return this.dateNoteTiers;
	}

	public void setDateNoteTiers(Date dateNoteTiers) {
		firePropertyChange("dateNoteTiers", this.dateNoteTiers, this.dateNoteTiers = dateNoteTiers);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SWTNoteTiers))
			return false;
		SWTNoteTiers castOther = (SWTNoteTiers) other;

		return ((this.getIdNoteTiers() == castOther.getIdNoteTiers()) || (this
				.getIdNoteTiers() != null
				&& castOther.getIdNoteTiers() != null && this.getIdNoteTiers().equals(
				castOther.getIdNoteTiers())))
				&& ((this.getNoteTiers() == castOther.getNoteTiers()) || (this
						.getNoteTiers() != null
						&& castOther.getNoteTiers() != null && this
						.getNoteTiers().equals(castOther.getNoteTiers())))
				&& ((this.getCodeTiers() == castOther.getCodeTiers()) || (this
						.getCodeTiers() != null
						&& castOther.getCodeTiers() != null && this
						.getCodeTiers().equals(castOther.getCodeTiers())))
				&& ((this.getDateNoteTiers() == castOther.getDateNoteTiers()) || (this
						.getDateNoteTiers() != null
						&& castOther.getDateNoteTiers() != null && this
						.getDateNoteTiers().equals(castOther.getDateNoteTiers())))
				&& ((this.getIdTiers() == castOther.getIdTiers()) || (this
						.getIdTiers() != null
						&& castOther.getIdTiers() != null && this
						.getIdTiers().equals(castOther.getIdTiers())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdNoteTiers() == null ? 0 : this.getIdNoteTiers().hashCode());
		result = 37
				* result
				+ (getNoteTiers() == null ? 0 : this.getNoteTiers()
						.hashCode());
		result = 37
				* result
				+ (getDateNoteTiers() == null ? 0 : this.getDateNoteTiers()
						.hashCode());
		result = 37
				* result
				+ (getCodeTiers() == null ? 0 : this.getCodeTiers()
						.hashCode());
		result = 37 * result
				+ (getIdTiers() == null ? 0 : this.getIdTiers().hashCode());
		return result;
	}



}
