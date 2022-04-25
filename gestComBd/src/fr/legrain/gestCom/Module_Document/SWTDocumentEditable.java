package fr.legrain.gestCom.Module_Document;

import java.util.ArrayList;
import java.util.List;

import fr.legrain.lib.data.ModelObject;

public class SWTDocumentEditable extends ModelObject {
	private Integer idDocumentDoc;
	private String codeDocumentDoc;
	private String libelleDocumentDoc;
	private String cheminModelDocumentDoc;
	private String cheminCorrespDocumentDoc;
	private Boolean actif;
	private Boolean defaut;
	private String typeLogiciel;
	private String codeTypeDoc;
	
	private List<SWTDocumentEditable> list = null;
	private List<SWTTDoc> taTDoc = null;
	
	public List<SWTDocumentEditable> getList() {
		if(list==null)
			list = new ArrayList<SWTDocumentEditable>();
		return list;
	}

	public void setList(List<SWTDocumentEditable> list) {
		firePropertyChange("list", this.list, this.list = list);
	}
	
	public List<SWTTDoc> getTaTDoc() {
//		if(liste==null)
//			liste = new ArrayList<SWTTDoc>(2);
//		liste.add(new SWTTDoc("a","b"));
//		liste.add(new SWTTDoc("c","d"));
		return taTDoc;
	}

	public void setTaTDoc(List<SWTTDoc> taTDoc) {
		firePropertyChange("taTDoc", this.taTDoc, this.taTDoc = taTDoc);
	}
	
	public SWTDocumentEditable(){
	}
	
	public Integer getIdDocumentDoc() {
		return idDocumentDoc;
	}
	public void setIdDocumentDoc(Integer idDocumentDoc) {
		firePropertyChange("idDocumentDoc", this.idDocumentDoc, this.idDocumentDoc = idDocumentDoc);
	}
	public String getCodeDocumentDoc() {
		return codeDocumentDoc;
	}
	public void setCodeDocumentDoc(String codeDocumentDoc) {
		firePropertyChange("codeDocumentDoc", this.codeDocumentDoc, this.codeDocumentDoc = codeDocumentDoc);
	}
	public String getCheminModelDocumentDoc() {
		return cheminModelDocumentDoc;
	}
	public void setCheminModelDocumentDoc(String cheminModelDocumentDoc) {
		firePropertyChange("cheminModelDocumentDoc", this.cheminModelDocumentDoc, this.cheminModelDocumentDoc = cheminModelDocumentDoc);
	}
	public String getLibelleDocumentDoc() {
		return libelleDocumentDoc;
	}

	public void setLibelleDocumentDoc(String libelleDocumentDoc) {
		firePropertyChange("libelleDocumentDoc", this.libelleDocumentDoc, this.libelleDocumentDoc = libelleDocumentDoc);
	}

	public String getCheminCorrespDocumentDoc() {
		return cheminCorrespDocumentDoc;
	}

	public void setCheminCorrespDocumentDoc(String cheminCorrespDocumentDoc) {
		firePropertyChange("cheminCorrespDocumentDoc", this.cheminCorrespDocumentDoc, this.cheminCorrespDocumentDoc = cheminCorrespDocumentDoc);
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		firePropertyChange("actif", this.actif, this.actif = actif);
	}
	
	public Boolean getDefaut() {
		return defaut;
	}

	public void setDefaut(Boolean defaut) {
		firePropertyChange("defaut", this.defaut, this.defaut = defaut);
	}
	
	public String getTypeLogiciel() {
		return typeLogiciel;
	}

	public void setTypeLogiciel(String typeLogiciel) {
		firePropertyChange("typeLogiciel", this.typeLogiciel, this.typeLogiciel = typeLogiciel);
	}
	
	public String getCodeTypeDoc() {
		return codeTypeDoc;
	}

	public void setCodeTypeDoc(String codeTypeDoc) {
		firePropertyChange("codeTypeDoc", this.codeTypeDoc, this.codeTypeDoc = codeTypeDoc);
	}
	
	
	public static SWTDocumentEditable copy(SWTDocumentEditable swtDocumentEditable){
		SWTDocumentEditable swtDocumentEditableLoc = new SWTDocumentEditable();
		swtDocumentEditableLoc.setCodeDocumentDoc(swtDocumentEditable.codeDocumentDoc);
		swtDocumentEditableLoc.setIdDocumentDoc(swtDocumentEditable.idDocumentDoc);
		swtDocumentEditableLoc.setCheminModelDocumentDoc(swtDocumentEditable.cheminModelDocumentDoc);
		swtDocumentEditableLoc.setCheminCorrespDocumentDoc(swtDocumentEditable.cheminCorrespDocumentDoc);
		swtDocumentEditableLoc.setLibelleDocumentDoc(swtDocumentEditable.libelleDocumentDoc);
		swtDocumentEditableLoc.setActif(swtDocumentEditable.actif);
		swtDocumentEditableLoc.setDefaut(swtDocumentEditable.defaut);
		swtDocumentEditableLoc.setTypeLogiciel(swtDocumentEditable.typeLogiciel);
		swtDocumentEditableLoc.setCodeTypeDoc(swtDocumentEditable.codeTypeDoc);
		return swtDocumentEditableLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime
				* result
				+ ((cheminCorrespDocumentDoc == null) ? 0 : cheminCorrespDocumentDoc
						.hashCode());
		result = prime
				* result
				+ ((cheminModelDocumentDoc == null) ? 0 : cheminModelDocumentDoc
						.hashCode());
		result = prime * result
				+ ((codeDocumentDoc == null) ? 0 : codeDocumentDoc.hashCode());
		result = prime * result + ((defaut == null) ? 0 : defaut.hashCode());
		result = prime * result
				+ ((idDocumentDoc == null) ? 0 : idDocumentDoc.hashCode());
		result = prime * result
				+ ((libelleDocumentDoc == null) ? 0 : libelleDocumentDoc.hashCode());
		result = prime * result
				+ ((typeLogiciel == null) ? 0 : typeLogiciel.hashCode());
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
		SWTDocumentEditable other = (SWTDocumentEditable) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (cheminCorrespDocumentDoc == null) {
			if (other.cheminCorrespDocumentDoc != null)
				return false;
		} else if (!cheminCorrespDocumentDoc.equals(other.cheminCorrespDocumentDoc))
			return false;
		if (cheminModelDocumentDoc == null) {
			if (other.cheminModelDocumentDoc != null)
				return false;
		} else if (!cheminModelDocumentDoc.equals(other.cheminModelDocumentDoc))
			return false;
		if (codeDocumentDoc == null) {
			if (other.codeDocumentDoc != null)
				return false;
		} else if (!codeDocumentDoc.equals(other.codeDocumentDoc))
			return false;
		if (defaut == null) {
			if (other.defaut != null)
				return false;
		} else if (!defaut.equals(other.defaut))
			return false;
		if (idDocumentDoc == null) {
			if (other.idDocumentDoc != null)
				return false;
		} else if (!idDocumentDoc.equals(other.idDocumentDoc))
			return false;
		if (libelleDocumentDoc == null) {
			if (other.libelleDocumentDoc != null)
				return false;
		} else if (!libelleDocumentDoc.equals(other.libelleDocumentDoc))
			return false;
		if (typeLogiciel == null) {
			if (other.typeLogiciel != null)
				return false;
		} else if (!typeLogiciel.equals(other.typeLogiciel))
			return false;
		return true;
	}

}
