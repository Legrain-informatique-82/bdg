package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTypeTvaDoc extends ModelObject {

	private Integer idTTvaDoc;
	private String journalTTvaDoc;
	private String codeTTvaDoc;
	private String libelleTTvaDoc;
	private String libelleEdition;

	

	
	
	
	public void setSwtTypeTvaDoc(SWTTypeTvaDoc swtTypeTvaDoc){
		this.setIdTTvaDoc(swtTypeTvaDoc.getIdTTvaDoc());                //1
		this.setCodeTTvaDoc(swtTypeTvaDoc.getCodeTTvaDoc());            //2
		this.setJournalTTvaDoc(swtTypeTvaDoc.getJournalTTvaDoc());        //3
		this.setLibelleTTvaDoc(swtTypeTvaDoc.getLibelleTTvaDoc());      //4
		this.setLibelleEdition(swtTypeTvaDoc.getLibelleEdition());
	}
	
	public static SWTTypeTvaDoc copy(SWTTypeTvaDoc swtTypeTvaDoc){
		SWTTypeTvaDoc swtTypeTvaDocLoc = new SWTTypeTvaDoc();
		swtTypeTvaDocLoc.setIdTTvaDoc(swtTypeTvaDoc.getIdTTvaDoc());                //1
		swtTypeTvaDocLoc.setJournalTTvaDoc(swtTypeTvaDoc.getJournalTTvaDoc());        //2
		swtTypeTvaDocLoc.setCodeTTvaDoc(swtTypeTvaDoc.getCodeTTvaDoc());            //3
		swtTypeTvaDocLoc.setLibelleTTvaDoc(swtTypeTvaDoc.getLibelleTTvaDoc());      //4
		swtTypeTvaDocLoc.setLibelleEdition(swtTypeTvaDoc.getLibelleEdition());
		return swtTypeTvaDocLoc;
	}
	

	public String getCodeTTvaDoc() {
		return codeTTvaDoc;
	}

	public void setCodeTTvaDoc(String codeTTvaDoc) {
		firePropertyChange("codeTTvaDoc", this.codeTTvaDoc, this.codeTTvaDoc = codeTTvaDoc);
	}

	public String getJournalTTvaDoc() {
		return journalTTvaDoc;
	}

	public void setJournalTTvaDoc(String journalTTvaDoc) {
		firePropertyChange("journalTTvaDoc", this.journalTTvaDoc, this.journalTTvaDoc = journalTTvaDoc);
	}

	public Integer getIdTTvaDoc() {
		return idTTvaDoc;
	}

	public void setIdTTvaDoc(Integer idTTvaDoc) {
		firePropertyChange("idTTvaDoc", this.idTTvaDoc, this.idTTvaDoc = idTTvaDoc);
	}

	public String getLibelleTTvaDoc() {
		return libelleTTvaDoc;
	}

	public void setLibelleTTvaDoc(String libelleTTvaDoc) {
		firePropertyChange("libelleTTvaDoc", this.libelleTTvaDoc, this.libelleTTvaDoc = libelleTTvaDoc);
	}


	public String getLibelleEdition() {
		return libelleEdition;
	}

	public void setLibelleEdition(String libelleEdition) {
		firePropertyChange("libelleEdition", this.libelleEdition, this.libelleEdition = libelleEdition);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTTvaDoc == null) ? 0 : codeTTvaDoc.hashCode());
		result = prime * result
				+ ((idTTvaDoc == null) ? 0 : idTTvaDoc.hashCode());
		result = prime * result
				+ ((journalTTvaDoc == null) ? 0 : journalTTvaDoc.hashCode());
		result = prime * result
				+ ((libelleEdition == null) ? 0 : libelleEdition.hashCode());
		result = prime * result
				+ ((libelleTTvaDoc == null) ? 0 : libelleTTvaDoc.hashCode());
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
		SWTTypeTvaDoc other = (SWTTypeTvaDoc) obj;
		if (codeTTvaDoc == null) {
			if (other.codeTTvaDoc != null)
				return false;
		} else if (!codeTTvaDoc.equals(other.codeTTvaDoc))
			return false;
		if (idTTvaDoc == null) {
			if (other.idTTvaDoc != null)
				return false;
		} else if (!idTTvaDoc.equals(other.idTTvaDoc))
			return false;
		if (journalTTvaDoc == null) {
			if (other.journalTTvaDoc != null)
				return false;
		} else if (!journalTTvaDoc.equals(other.journalTTvaDoc))
			return false;
		if (libelleEdition == null) {
			if (other.libelleEdition != null)
				return false;
		} else if (!libelleEdition.equals(other.libelleEdition))
			return false;
		if (libelleTTvaDoc == null) {
			if (other.libelleTTvaDoc != null)
				return false;
		} else if (!libelleTTvaDoc.equals(other.libelleTTvaDoc))
			return false;
		return true;
	}
	

}
