package fr.legrain.edition.actions;

public class InfosEmail {
	
	private String[] destinataire;
	private String sujet;
	private String nomPieceJointe;
	private String messageDefaut;
	
	public InfosEmail(String[] destinataire, String sujet, String nomPieceJointe) {
		super();
		this.destinataire = destinataire;
		this.sujet = sujet;
		this.nomPieceJointe = nomPieceJointe;
	}
	
	public String getMessageDefaut() {
		return messageDefaut;
	}

	public void setMessageDefaut(String messageDefaut) {
		this.messageDefaut = messageDefaut;
	}

	public String[] getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String[] destinataire) {
		this.destinataire = destinataire;
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getNomPieceJointe() {
		return nomPieceJointe;
	}

	public void setNomPieceJointe(String nomPieceJointe) {
		this.nomPieceJointe = nomPieceJointe;
	} 

}
