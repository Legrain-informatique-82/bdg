package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1


public class TaRelanceDocument implements java.io.Serializable {

	public TaRelanceDocument(int idTRelance, int idDocument) {
		super();
		this.idTRelance = idTRelance;
		this.idDocument = idDocument;
	}
	private int idTRelance;
	private int idDocument;
	
	
	public int getIdTRelance() {
		return idTRelance;
	}
	public void setIdTRelance(int idTRelance) {
		this.idTRelance = idTRelance;
	}
	public int getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}


}
