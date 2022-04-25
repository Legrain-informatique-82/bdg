package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.EventObject;


public class DeclencheCommandeControllerEvent extends EventObject {
	
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
	
	private String idCommande = null;

	public DeclencheCommandeControllerEvent(Object source) {
		super(source);
	}
	
	public DeclencheCommandeControllerEvent(Object source, String idCommande) {
		super(source);
		this.idCommande = idCommande;
	}

	public String getCommande() {
		return idCommande;
	}

	public void setCommande(String sens) {
		this.idCommande = sens;
	}

}
