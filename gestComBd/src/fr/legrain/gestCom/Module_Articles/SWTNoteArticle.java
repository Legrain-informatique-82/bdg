package fr.legrain.gestCom.Module_Articles;

import java.util.Date;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

public class SWTNoteArticle extends ModelObject {

	private Integer idNoteArticle;
	private String noteArticle;
	private String codeTNoteArticle;
	private String codeArticle;
	private Integer idArticle;
	private Date dateNoteArticle = new Date();

	public SWTNoteArticle() {
	}

	public void setSWTNote(SWTNoteArticle swtNoteArticle) {
		this.idNoteArticle = swtNoteArticle.idNoteArticle;
		this.noteArticle = swtNoteArticle.noteArticle;
		this.codeArticle = swtNoteArticle.codeArticle;
		this.idArticle = swtNoteArticle.idArticle;
		this.codeTNoteArticle = swtNoteArticle.codeTNoteArticle;
		this.dateNoteArticle = swtNoteArticle.dateNoteArticle;
	}

	public static SWTNoteArticle copy(SWTNoteArticle swtNoteArticle){
		SWTNoteArticle swtNoteTiersLoc = new SWTNoteArticle();
		swtNoteTiersLoc.setIdArticle(swtNoteArticle.getIdArticle());                //1
		swtNoteTiersLoc.setIdNoteArticle(swtNoteArticle.getIdNoteArticle());                //1
		swtNoteTiersLoc.setNoteArticle(swtNoteArticle.getNoteArticle());                //1
		swtNoteTiersLoc.setCodeArticle(swtNoteArticle.getCodeArticle());                //1
		swtNoteTiersLoc.setCodeTNoteArticle(swtNoteArticle.getCodeTNoteArticle());                //1
		swtNoteTiersLoc.setDateNoteArticle(swtNoteArticle.getDateNoteArticle());        
		return swtNoteTiersLoc;
	}
	public Integer getIdNoteArticle() {
		return this.idNoteArticle;
	}

	public void setIdNoteArticle(Integer idNoteArticle) {
		firePropertyChange("idNoteArticle", this.idNoteArticle, this.idNoteArticle = idNoteArticle);
	}

	public String getNoteArticle() {
		return this.noteArticle;
	}

	public void setNoteArticle(String noteArticle) {
		firePropertyChange("noteArticle", this.noteArticle, this.noteArticle = noteArticle);
	}
	
	public String getCodeTNoteArticle() {
		return this.codeTNoteArticle;
	}

	public void setCodeTNoteArticle(String codeTNoteArticle) {
		firePropertyChange("codeTNoteArticle", this.codeTNoteArticle, this.codeTNoteArticle = codeTNoteArticle);
	}

	public String getCodeArticle() {
		return this.codeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = codeArticle);
	}

	public Integer getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(Integer idArticle) {
		firePropertyChange("idArticle", this.idArticle, this.idArticle = idArticle);
	}
	
	public Date getDateNoteArticle() {
		return this.dateNoteArticle;
	}

	public void setDateNoteArticle(Date dateNoteArticle) {
		firePropertyChange("dateNoteArticle", this.dateNoteArticle, this.dateNoteArticle = dateNoteArticle);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SWTNoteArticle))
			return false;
		SWTNoteArticle castOther = (SWTNoteArticle) other;

		return ((this.getIdNoteArticle() == castOther.getIdNoteArticle()) || (this
				.getIdNoteArticle() != null
				&& castOther.getIdNoteArticle() != null && this.getIdNoteArticle().equals(
				castOther.getIdNoteArticle())))
				&& ((this.getNoteArticle() == castOther.getNoteArticle()) || (this
						.getNoteArticle() != null
						&& castOther.getNoteArticle() != null && this
						.getNoteArticle().equals(castOther.getNoteArticle())))
				&& ((this.getCodeArticle() == castOther.getCodeArticle()) || (this
						.getCodeArticle() != null
						&& castOther.getCodeArticle() != null && this
						.getCodeArticle().equals(castOther.getCodeArticle())))
				&& ((this.getDateNoteArticle() == castOther.getDateNoteArticle()) || (this
						.getDateNoteArticle() != null
						&& castOther.getDateNoteArticle() != null && this
						.getDateNoteArticle().equals(castOther.getDateNoteArticle())))
				&& ((this.getIdArticle() == castOther.getIdArticle()) || (this
						.getIdArticle() != null
						&& castOther.getIdArticle() != null && this
						.getIdArticle().equals(castOther.getIdArticle())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdNoteArticle() == null ? 0 : this.getIdNoteArticle().hashCode());
		result = 37
				* result
				+ (getNoteArticle() == null ? 0 : this.getNoteArticle()
						.hashCode());
		result = 37
				* result
				+ (getDateNoteArticle() == null ? 0 : this.getDateNoteArticle()
						.hashCode());
		result = 37
				* result
				+ (getCodeArticle() == null ? 0 : this.getCodeArticle()
						.hashCode());
		result = 37 * result
				+ (getIdArticle() == null ? 0 : this.getIdArticle().hashCode());
		return result;
	}



}
