package fr.legrain.document.divers;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.tiers.dao.TaTiers;

public class TaCreationDoc implements java.io.Serializable {

	private String codeDocument;
	private String libelleDocument;
	private String codeTiers;
	private List<IDocumentTiers> listeDoc=new LinkedList<IDocumentTiers>();
	private Boolean accepte;
	
	
	public String getCodeDocument() {
		return codeDocument;
	}
	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}
	public String getLibelleDocument() {
		return libelleDocument;
	}
	public void setLibelleDocument(String libelleDocument) {
		this.libelleDocument = libelleDocument;
	}
	public List<IDocumentTiers> getListeDoc() {
		return listeDoc;
	}
	public void setListeDoc(List<IDocumentTiers> listeDoc) {
		this.listeDoc = listeDoc;
	}
	public Boolean getAccepte() {
		return accepte;
	}
	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}
	
	public String getCodeTiers() {
		return codeTiers;
	}
	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accepte == null) ? 0 : accepte.hashCode());
		result = prime * result
				+ ((codeDocument == null) ? 0 : codeDocument.hashCode());
		result = prime * result
				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
		result = prime * result
				+ ((libelleDocument == null) ? 0 : libelleDocument.hashCode());
		result = prime * result
				+ ((listeDoc == null) ? 0 : listeDoc.hashCode());
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
		TaCreationDoc other = (TaCreationDoc) obj;
		if (accepte == null) {
			if (other.accepte != null)
				return false;
		} else if (!accepte.equals(other.accepte))
			return false;
		if (codeDocument == null) {
			if (other.codeDocument != null)
				return false;
		} else if (!codeDocument.equals(other.codeDocument))
			return false;
		if (codeTiers == null) {
			if (other.codeTiers != null)
				return false;
		} else if (!codeTiers.equals(other.codeTiers))
			return false;
		if (libelleDocument == null) {
			if (other.libelleDocument != null)
				return false;
		} else if (!libelleDocument.equals(other.libelleDocument))
			return false;
		if (listeDoc == null) {
			if (other.listeDoc != null)
				return false;
		} else if (!listeDoc.equals(other.listeDoc))
			return false;
		return true;
	}
	
	
	
}
