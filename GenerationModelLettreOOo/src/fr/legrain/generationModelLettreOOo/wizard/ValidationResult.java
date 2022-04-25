package fr.legrain.generationModelLettreOOo.wizard;

public class ValidationResult {

	private String error = null;
	private String info  = null;
	private String codeRetour = null;
	
	public ValidationResult(){}
	
	public ValidationResult(String error, String info) {
		super();
		this.error = error;
		this.info = info;
	}
	
	public boolean isEmpty() {
		if(error==null && info==null)
			return true;
		else 
			return false;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public String getCodeRetour() {
		return codeRetour;
	}

	public void setCodeRetour(String codeRetour) {
		this.codeRetour = codeRetour;
	}
}
