package fr.legrain.gestCom.Module_Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.legrain.lib.data.ModelObject;

public class SWTCreationDocLigne extends ModelObject {
	private int id;
	private String codeTiersParent;
	private String codeTiers;
	private String codeDocument;
	private String libelleDocument;
	private String libelleLigne;
	private Boolean accepte;
	private String codeDocumentRecup;
	private Date dateDocument;
	private BigDecimal netTtc;
//	private Boolean actif;
//	private Boolean defaut;

	
	public int getId() {
		return id;
	}

	public SWTCreationDocLigne(int id) {
		super();
		this.id = id;
	}

	public void setId(int id) {
		firePropertyChange("id", this.id, this.id = id);
	}
	
	private List<SWTCreationDocLigne> list = null;
	//private List<SWTTDoc> taTDoc = null;
	
	public List<SWTCreationDocLigne> getList() {
		if(list==null)
			list = new ArrayList<SWTCreationDocLigne>();
		return list;
	}

	public void setList(List<SWTCreationDocLigne> list) {
		firePropertyChange("list", this.list, this.list = list);
	}
	


	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		firePropertyChange("codeDocument", this.codeDocument, this.codeDocument = codeDocument);
	}

	public String getLibelleDocument() {
		return libelleDocument;
	}

	public void setLibelleDocument(String libelleDocument) {
		firePropertyChange("libelleDocument", this.libelleDocument, this.libelleDocument = libelleDocument);
	}

	public String getLibelleLigne() {
		return libelleLigne;
	}

	public void setLibelleLigne(String libelleLigne) {
		firePropertyChange("libelleLigne", this.libelleLigne, this.libelleLigne = libelleLigne);
	}


	public Boolean getAccepte() {
		return accepte;
	}

	public void setAccepte(Boolean accepte) {
		firePropertyChange("accepte", this.accepte, this.accepte = accepte);
	}
	
	public String getCodeDocumentRecup() {
		return codeDocumentRecup;
	}

	public void setCodeDocumentRecup(String codeDocumentRecup) {
		firePropertyChange("codeDocumentRecup", this.codeDocumentRecup, this.codeDocumentRecup = codeDocumentRecup);
	}
	
	public String getCodeTiersParent() {
		return codeTiersParent;
	}

	public void setCodeTiersParent(String codeTiersParent) {
		firePropertyChange("codeTiersParent", this.codeTiersParent, this.codeTiersParent = codeTiersParent);
	}
	
	
	public Date getDateDocument() {
		return dateDocument;
	}

	public void setDateDocument(Date dateDocument) {
		firePropertyChange("dateDocument", this.dateDocument, this.dateDocument = dateDocument);
	}
	
	public BigDecimal getNetTtc() {
		return netTtc;
	}

	public void setNetTtc(BigDecimal netTtc) {
		firePropertyChange("netTtc", this.netTtc, this.netTtc = netTtc);
	}
	
	public static SWTCreationDocLigne copy(SWTCreationDocLigne swtCreationDocLigne){
		SWTCreationDocLigne swtDocumentEditableLoc = new SWTCreationDocLigne(0);
		swtDocumentEditableLoc.setId(swtCreationDocLigne.id);
		swtDocumentEditableLoc.setCodeDocument(swtCreationDocLigne.codeDocument);
		swtDocumentEditableLoc.setCodeTiers(swtCreationDocLigne.codeTiers);
		swtDocumentEditableLoc.setCodeTiersParent(swtCreationDocLigne.codeTiersParent);
		swtDocumentEditableLoc.setLibelleDocument(swtCreationDocLigne.libelleDocument);
		swtDocumentEditableLoc.setLibelleLigne(swtCreationDocLigne.libelleLigne);
		swtDocumentEditableLoc.setAccepte(swtCreationDocLigne.accepte);
		swtDocumentEditableLoc.setCodeDocumentRecup(swtCreationDocLigne.codeDocumentRecup);
		swtDocumentEditableLoc.setDateDocument(swtCreationDocLigne.dateDocument);
		swtDocumentEditableLoc.setNetTtc(swtCreationDocLigne.netTtc);
		return swtDocumentEditableLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accepte == null) ? 0 : accepte.hashCode());
		result = prime * result
				+ ((codeDocument == null) ? 0 : codeDocument.hashCode());
		result = prime
				* result
				+ ((codeDocumentRecup == null) ? 0 : codeDocumentRecup
						.hashCode());
		result = prime * result
				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
		result = prime * result
				+ ((codeTiersParent == null) ? 0 : codeTiersParent.hashCode());
		result = prime * result
				+ ((dateDocument == null) ? 0 : dateDocument.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((libelleDocument == null) ? 0 : libelleDocument.hashCode());
		result = prime * result
				+ ((libelleLigne == null) ? 0 : libelleLigne.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((netTtc == null) ? 0 : netTtc.hashCode());
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
		SWTCreationDocLigne other = (SWTCreationDocLigne) obj;
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
		if (codeDocumentRecup == null) {
			if (other.codeDocumentRecup != null)
				return false;
		} else if (!codeDocumentRecup.equals(other.codeDocumentRecup))
			return false;
		if (codeTiers == null) {
			if (other.codeTiers != null)
				return false;
		} else if (!codeTiers.equals(other.codeTiers))
			return false;
		if (codeTiersParent == null) {
			if (other.codeTiersParent != null)
				return false;
		} else if (!codeTiersParent.equals(other.codeTiersParent))
			return false;
		if (dateDocument == null) {
			if (other.dateDocument != null)
				return false;
		} else if (!dateDocument.equals(other.dateDocument))
			return false;
		if (id != other.id)
			return false;
		if (libelleDocument == null) {
			if (other.libelleDocument != null)
				return false;
		} else if (!libelleDocument.equals(other.libelleDocument))
			return false;
		if (libelleLigne == null) {
			if (other.libelleLigne != null)
				return false;
		} else if (!libelleLigne.equals(other.libelleLigne))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (netTtc == null) {
			if (other.netTtc != null)
				return false;
		} else if (!netTtc.equals(other.netTtc))
			return false;
		return true;
	}



}
