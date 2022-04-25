package fr.legrain.edition.actions;

public class InfosFax {
	
	private String[] destinataire;
	private String nomPieceJointe;
	
	public InfosFax(String[] destinataire, String nomPieceJointe) {
		super();
		this.destinataire = destinataire;
		this.nomPieceJointe = nomPieceJointe;
	}

	public String[] getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String[] destinataire) {
		this.destinataire = destinataire;
	}

	public String getNomPieceJointe() {
		return nomPieceJointe;
	}

	public void setNomPieceJointe(String nomPieceJointe) {
		this.nomPieceJointe = nomPieceJointe;
	} 

}
