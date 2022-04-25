package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTCompteBanque extends ModelObject {
	private Integer idCompteBanque;
	private Integer idTiers;
	private String nomBanque;
	private String compte;
	private String codeBanque;
	private String codeGuichet;
	private String cleRib;
	private String adresse1Banque;
	private String adresse2Banque;
	private String cpBanque;
	private String villeBanque;
	private String iban;
	private String codeBIC;
	private String nomCompte;
	private String titulaire;
	private Integer idTBanque;
	private String codeTBanque;
	private String cptcomptable;
	
	public SWTCompteBanque() {
		
	}
	public Integer getIdTiers() {
		return idTiers;
	}

	public void setIdTiers(Integer ID_TIERS) {
		firePropertyChange("idTiers", this.idTiers, this.idTiers = ID_TIERS);
	}
	
	public Integer getIdCompteBanque() {
		return idCompteBanque;
	}
	public void setIdCompteBanque(Integer idCompteBanque) {
		firePropertyChange("idCompteBanque", this.idCompteBanque, this.idCompteBanque = idCompteBanque);
	}
	public String getCodeTBanque() {
		return codeTBanque;
	}
	public void setCodeTBanque(String codeTBanque) {
		firePropertyChange("codeTBanque", this.codeTBanque, this.codeTBanque = codeTBanque);
	}
	public String getNomBanque() {
		return nomBanque;
	}
	public void setNomBanque(String nomBanque) {
		firePropertyChange("nomBanque", this.nomBanque, this.nomBanque = nomBanque);
	}
	public String getCompte() {
		return compte;
	}
	public void setCompte(String compte) {
		firePropertyChange("compte", this.compte, this.compte = compte);
	}
	public String getCodeBanque() {
		return codeBanque;
	}
	public void setCodeBanque(String codeBanque) {
		firePropertyChange("codeBanque", this.codeBanque, this.codeBanque = codeBanque);
	}
	public String getCodeGuichet() {
		return codeGuichet;
	}
	public void setCodeGuichet(String codeGuichet) {
		firePropertyChange("codeGuichet", this.codeGuichet, this.codeGuichet = codeGuichet);
	}
	public String getCleRib() {
		return cleRib;
	}
	public void setCleRib(String cleRib) {
		firePropertyChange("cleRib", this.cleRib, this.cleRib = cleRib);
	}
	public String getAdresse1Banque() {
		return adresse1Banque;
	}
	public void setAdresse1Banque(String adresse1Banque) {
		firePropertyChange("adresse1Banque", this.adresse1Banque, this.adresse1Banque = adresse1Banque);
	}
	public String getAdresse2Banque() {
		return adresse2Banque;
	}
	public void setAdresse2Banque(String adresse2Banque) {
		firePropertyChange("adresse2Banque", this.adresse2Banque, this.adresse2Banque = adresse2Banque);
	}
	public String getCpBanque() {
		return cpBanque;
	}
	public void setCpBanque(String cpBanque) {
		firePropertyChange("cpBanque", this.cpBanque, this.cpBanque = cpBanque);
	}
	public String getVilleBanque() {
		return villeBanque;
	}
	public void setVilleBanque(String villeBanque) {
		firePropertyChange("villeBanque", this.villeBanque, this.villeBanque = villeBanque);
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		firePropertyChange("iban", this.iban, this.iban = iban);
	}
	public String getCodeBIC() {
		return codeBIC;
	}
	public void setCodeBIC(String codeBIC) {
		firePropertyChange("codeBIC", this.codeBIC, this.codeBIC = codeBIC);
	}
	public String getTitulaire() {
		return titulaire;
	}
	public void setTitulaire(String titulaire) {
		firePropertyChange("titulaire", this.titulaire, this.titulaire = titulaire);
	}

	public Integer getIdTBanque() {
		return idTBanque;
	}

	public void setIdTBanque(Integer ID_T_BANQUE) {
		firePropertyChange("idTBanque", this.idTBanque, this.idTBanque = ID_T_BANQUE);
	}
	
	public String getCptcomptable() {
		return cptcomptable;
	}
	public void setCptcomptable(String cptcomptable) {
		firePropertyChange("cptcomptable", this.cptcomptable, this.cptcomptable = cptcomptable);
	}
	
	public static SWTCompteBanque copy(SWTCompteBanque swtCompteBanque){
		SWTCompteBanque swtCompteBanqueLoc = new SWTCompteBanque();
		swtCompteBanqueLoc.setIdCompteBanque(swtCompteBanque.getIdCompteBanque());                //1
		swtCompteBanqueLoc.setIdTiers(swtCompteBanque.getIdTiers());                //1
		swtCompteBanqueLoc.setNomBanque(swtCompteBanque.getNomBanque());                //1
		swtCompteBanqueLoc.setCompte(swtCompteBanque.getCompte());                //1
		swtCompteBanqueLoc.setCodeBanque(swtCompteBanque.getCodeBanque());                //1
		swtCompteBanqueLoc.setCodeGuichet(swtCompteBanque.getCodeGuichet());                //1
		swtCompteBanqueLoc.setCleRib(swtCompteBanque.getCleRib());                //1
		swtCompteBanqueLoc.setAdresse1Banque(swtCompteBanque.getAdresse1Banque());                //1
		swtCompteBanqueLoc.setAdresse2Banque(swtCompteBanque.getAdresse2Banque());                //1
		swtCompteBanqueLoc.setCpBanque(swtCompteBanque.getCpBanque());                //1
		swtCompteBanqueLoc.setVilleBanque(swtCompteBanque.getVilleBanque());                //1
		swtCompteBanqueLoc.setIban(swtCompteBanque.getIban());                //1
		swtCompteBanqueLoc.setCodeBIC(swtCompteBanque.getCodeBIC());                //1
		swtCompteBanqueLoc.setNomCompte(swtCompteBanque.getNomCompte()); 
		swtCompteBanqueLoc.setTitulaire(swtCompteBanque.getTitulaire());                //1
		swtCompteBanqueLoc.setIdTBanque(swtCompteBanque.getIdTBanque());                //1
		swtCompteBanqueLoc.setCodeTBanque(swtCompteBanque.getCodeTBanque()); 
		swtCompteBanqueLoc.setCptcomptable(swtCompteBanque.getCptcomptable());//1
		return swtCompteBanqueLoc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adresse1Banque == null) ? 0 : adresse1Banque.hashCode());
		result = prime * result
				+ ((adresse2Banque == null) ? 0 : adresse2Banque.hashCode());
		result = prime * result + ((cleRib == null) ? 0 : cleRib.hashCode());
		result = prime * result + ((codeBIC == null) ? 0 : codeBIC.hashCode());
		result = prime * result
				+ ((codeBanque == null) ? 0 : codeBanque.hashCode());
		result = prime * result
				+ ((codeGuichet == null) ? 0 : codeGuichet.hashCode());
		result = prime * result
				+ ((codeTBanque == null) ? 0 : codeTBanque.hashCode());
		result = prime * result + ((compte == null) ? 0 : compte.hashCode());
		result = prime * result
				+ ((cpBanque == null) ? 0 : cpBanque.hashCode());
		result = prime * result
				+ ((cptcomptable == null) ? 0 : cptcomptable.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result
				+ ((idCompteBanque == null) ? 0 : idCompteBanque.hashCode());
		result = prime * result
				+ ((idTBanque == null) ? 0 : idTBanque.hashCode());
		result = prime * result + ((idTiers == null) ? 0 : idTiers.hashCode());
		result = prime * result
				+ ((nomBanque == null) ? 0 : nomBanque.hashCode());
		result = prime * result
				+ ((titulaire == null) ? 0 : titulaire.hashCode());
		result = prime * result
				+ ((villeBanque == null) ? 0 : villeBanque.hashCode());
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
		SWTCompteBanque other = (SWTCompteBanque) obj;
		if (adresse1Banque == null) {
			if (other.adresse1Banque != null)
				return false;
		} else if (!adresse1Banque.equals(other.adresse1Banque))
			return false;
		if (adresse2Banque == null) {
			if (other.adresse2Banque != null)
				return false;
		} else if (!adresse2Banque.equals(other.adresse2Banque))
			return false;
		if (cleRib == null) {
			if (other.cleRib != null)
				return false;
		} else if (!cleRib.equals(other.cleRib))
			return false;
		if (codeBIC == null) {
			if (other.codeBIC != null)
				return false;
		} else if (!codeBIC.equals(other.codeBIC))
			return false;
		if (codeBanque == null) {
			if (other.codeBanque != null)
				return false;
		} else if (!codeBanque.equals(other.codeBanque))
			return false;
		if (codeGuichet == null) {
			if (other.codeGuichet != null)
				return false;
		} else if (!codeGuichet.equals(other.codeGuichet))
			return false;
		if (codeTBanque == null) {
			if (other.codeTBanque != null)
				return false;
		} else if (!codeTBanque.equals(other.codeTBanque))
			return false;
		if (compte == null) {
			if (other.compte != null)
				return false;
		} else if (!compte.equals(other.compte))
			return false;
		if (cpBanque == null) {
			if (other.cpBanque != null)
				return false;
		} else if (!cpBanque.equals(other.cpBanque))
			return false;
		if (cptcomptable == null) {
			if (other.cptcomptable != null)
				return false;
		} else if (!cptcomptable.equals(other.cptcomptable))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (idCompteBanque == null) {
			if (other.idCompteBanque != null)
				return false;
		} else if (!idCompteBanque.equals(other.idCompteBanque))
			return false;
		if (idTBanque == null) {
			if (other.idTBanque != null)
				return false;
		} else if (!idTBanque.equals(other.idTBanque))
			return false;
		if (idTiers == null) {
			if (other.idTiers != null)
				return false;
		} else if (!idTiers.equals(other.idTiers))
			return false;
		if (nomBanque == null) {
			if (other.nomBanque != null)
				return false;
		} else if (!nomBanque.equals(other.nomBanque))
			return false;
		if (titulaire == null) {
			if (other.titulaire != null)
				return false;
		} else if (!titulaire.equals(other.titulaire))
			return false;
		if (villeBanque == null) {
			if (other.villeBanque != null)
				return false;
		} else if (!villeBanque.equals(other.villeBanque))
			return false;
		return true;
	}
	public String getNomCompte() {
		return nomCompte;
	}
	public void setNomCompte(String nomCompte) {
		firePropertyChange("nomCompte", this.nomCompte, this.nomCompte = nomCompte);
	}




}
