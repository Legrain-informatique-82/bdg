package fr.legrain.generationLabelEtiquette.handlers;

public class ParamWizardEtiquettes {
	
	//controle des pages de l'assistant
	private int numPageWizardDebut = 0;
	private boolean modeIntegre = false;
	
	//mise en forme
	private String modelePredefini = null;
	
	//donnees
	private String cheminFichierDonnees = null;
	private String cheminFichierMotsCle = null;
	private String separateur = null;
	
	private String type = null;
	
	private boolean changeStartingPage = false;
	
	
	
	
	public int getNumPageWizardDebut() {
		return numPageWizardDebut;
	}
	
	public void setNumPageWizardDebut(int numPageWizardDebut) {
		this.numPageWizardDebut = numPageWizardDebut;
	}
	
	public String getModelePredefini() {
		return modelePredefini;
	}
	
	public void setModelePredefini(String modelePredefini) {
		this.modelePredefini = modelePredefini;
	}
	
	public String getCheminFichierDonnees() {
		return cheminFichierDonnees;
	}
	
	public void setCheminFichierDonnees(String cheminFichierDonnees) {
		this.cheminFichierDonnees = cheminFichierDonnees;
	}
	
	public String getCheminFichierMotsCle() {
		return cheminFichierMotsCle;
	}
	
	public void setCheminFichierMotsCle(String cheminFichierMotsCle) {
		this.cheminFichierMotsCle = cheminFichierMotsCle;
	}
	
	public String getSeparateur() {
		return separateur;
	}
	
	public void setSeparateur(String separateur) {
		this.separateur = separateur;
	}

	public boolean isModeIntegre() {
		return modeIntegre;
	}

	public void setModeIntegre(boolean modeIntegre) {
		this.modeIntegre = modeIntegre;
	}

	public boolean isChangeStartingPage() {
		return changeStartingPage;
	}

	public void setChangeStartingPage(boolean changeStartingPage) {
		this.changeStartingPage = changeStartingPage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
