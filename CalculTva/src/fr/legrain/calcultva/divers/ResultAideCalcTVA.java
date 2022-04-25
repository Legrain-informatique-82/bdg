package fr.legrain.calcultva.divers;

import fr.legrain.lib.gui.ResultAffiche;

public class ResultAideCalcTVA extends ResultAffiche {
	private String taux = null;
	private String mtTTC = null;
	private String mtHT = null;
	private String mtTVA = null;
	
	public ResultAideCalcTVA() {}
	
	public ResultAideCalcTVA(String taux, String mtTTC, String mtHT, String mtTVA) {
		this.taux = taux;
		this.mtTTC = mtTTC;
		this.mtHT = mtHT;
		this.mtTVA = mtTVA;
	}
	
	public String getMtHT() {
		return mtHT;
	}
	public void setMtHT(String mtHT) {
		this.mtHT = mtHT;
	}
	public String getMtTTC() {
		return mtTTC;
	}
	public void setMtTTC(String mtTTC) {
		this.mtTTC = mtTTC;
	}
	public String getMtTVA() {
		return mtTVA;
	}
	public void setMtTVA(String mtTVA) {
		this.mtTVA = mtTVA;
	}
	public String getTaux() {
		return taux;
	}
	public void setTaux(String taux) {
		this.taux = taux;
	}
}
