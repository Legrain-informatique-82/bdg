package fr.legrain.liasseFiscale.wizards;

public enum EnumTypeDoc {
	liasse, 
	TVA;
	
	public String valeurSQL() {
		String resultat = null;
		switch(this) {
			case liasse : 
				resultat = "L";
				break;
			case TVA : 
				resultat = "T";
				break;
			default : 
				break;
		}
		return resultat;
	}
}
