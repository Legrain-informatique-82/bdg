package fr.legrain.facture.editor;

import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;

public class SWTEditorFactureSelection {
//	private SWTFacture swtFacture;
	private IHMEnteteFacture ihmEnteteFacture;
	private Integer idTiers;
	private Integer idInfosFacture;
	private Integer idAdresse;
	private Integer idCPaiement;
	private boolean refresh;
	
//	public SWTFacture getFacture() {
//		return swtFacture;
//	}
//	public void setFacture(SWTFacture swtFacture) {
//		this.facture = swtFacture;
//	}
	public Integer getIdTiers() {
		return idTiers;
	}
	public void setIdTiers(Integer idTiers) {
		this.idTiers = idTiers;
	}
	public boolean isRefresh() {
		return refresh;
	}
	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}
	public Integer getIdInfosFacture() {
		return idInfosFacture;
	}
	public void setIdInfosFacture(Integer idInfosFacture) {
		this.idInfosFacture = idInfosFacture;
	}
	public Integer getIdAdresse() {
		return idAdresse;
	}
	public void setIdAdresse(Integer idAdresse) {
		this.idAdresse = idAdresse;
	}
	public Integer getIdCPaiement() {
		return idCPaiement;
	}
	public void setIdCPaiement(Integer idCPaiement) {
		this.idCPaiement = idCPaiement;
	}
	public IHMEnteteFacture getIhmEnteteFacture() {
		return ihmEnteteFacture;
	}
	public void setIhmEnteteFacture(IHMEnteteFacture ihmEnteteFacture) {
		this.ihmEnteteFacture = ihmEnteteFacture;
	}
//	public SWTFacture getSwtFacture() {
//		return swtFacture;
//	}
//	public void setSwtFacture(SWTFacture swtFacture) {
//		this.swtFacture = swtFacture;
//	}
	
	
}
