package fr.legrain.liasseFiscale.wizards;

public enum EnumRegimeFiscal {
	agricole, 
	BIC;
	
	public String valeurSQL() {
		String resultat = null;
		switch(this) {
			case agricole : 
				resultat = "A";
				break;
			case BIC : 
				resultat = "I";
				break;
			default : 
				break;
		}
		return resultat;
	}

}
