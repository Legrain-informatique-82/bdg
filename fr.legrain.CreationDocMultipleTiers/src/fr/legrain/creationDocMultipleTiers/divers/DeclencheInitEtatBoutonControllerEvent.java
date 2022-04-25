package fr.legrain.creationDocMultipleTiers.divers;

import java.util.EventObject;


public class DeclencheInitEtatBoutonControllerEvent extends EventObject {
	
//	public static final int INSERER = 1;
//	public static final int MODIFIER = 2;
//	public static final int ENREGISTRER = 3;
//	public static final int SUPPRIMER = 4;
//	public static final int ANNLUER = 5;
//	public static final int FERMER = 6;
//	public static final int IMPRIMER = 7;
//	public static final int RAFRAICHIR = 8;
//	public static final int SUIVANT = 9;
//	public static final int PRECEDENT = 10;

	
	//private OptionExportation option = null;
	private String option = null;
	public DeclencheInitEtatBoutonControllerEvent(Object source) {
		super(source);
	}
	
	public DeclencheInitEtatBoutonControllerEvent(Object source, String option) {
		super(source);
		this.option = option;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

}
