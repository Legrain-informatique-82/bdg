package fr.legrain.gestCom.Module_Document;


public interface IInfosDocumentTiers {
	
	public int getIdInfosDocument();;
	public void setIdInfosDocument(int idInfosDevis);

	public String getAdresse1();
	public void setAdresse1(String adresse1);
	
	public String getAdresse2();
	public void setAdresse2(String adresse2);

	public String getAdresse3();
	public void setAdresse3(String adresse3);

	public String getCodepostal();
	public void setCodepostal(String codepostal);

	public String getVille();
	public void setVille(String ville);
	
	public String getPays();
	public void setPays(String pays);
	
	public String getAdresse1Liv();
	public void setAdresse1Liv(String adresse1Liv);
	
	public String getAdresse2Liv();
	public void setAdresse2Liv(String adresse2Liv);
	
	public String getAdresse3Liv();
	public void setAdresse3Liv(String adresse3Liv);

	public String getCodepostalLiv();
	public void setCodepostalLiv(String codepostalLiv);
	
	public String getVilleLiv();
	public void setVilleLiv(String villeLiv);
	
	public String getPaysLiv();
	public void setPaysLiv(String paysLiv);
	
	public String getCodeCompta();
	public void setCodeCompta(String codeCompta);
	
	public String getCompte();
	public void setCompte(String compte);

	public String getNomTiers();
	public void setNomTiers(String nomTiers);
	
	public String getPrenomTiers();
	public void setPrenomTiers(String prenomTiers);

	public String getSurnomTiers();
	public void setSurnomTiers(String surnomTiers);

	public String getCodeTCivilite();
	public void setCodeTCivilite(String codeTCivilite);

	public String getCodeTEntite();
	public void setCodeTEntite(String codeTEntite);
	
	public String getTvaIComCompl();
	public void setTvaIComCompl(String tvaIComCompl);
	
	public String getCodeCPaiement();
	public void setCodeCPaiement(String codeCPaiement);
	
	public String getLibCPaiement();
	public void setLibCPaiement(String libCPaiement);
	
	public Integer getReportCPaiement();
	public void setReportCPaiement(Integer reportCPaiement);
	
	public Integer getFinMoisCPaiement();
	public void setFinMoisCPaiement(Integer finMoisCPaiement);

	public String getNomEntreprise();
	public void setNomEntreprise(String nomEntreprise);
	
	public String getCodeTTvaDoc();
	public void setCodeTTvaDoc(String codeTTvaDoc);
}
