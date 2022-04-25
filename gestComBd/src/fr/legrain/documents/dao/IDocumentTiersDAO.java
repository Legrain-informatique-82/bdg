package fr.legrain.documents.dao;

import java.util.Date;
import java.util.List;

public interface IDocumentTiersDAO<T> {
	
	public List<T> rechercheDocument(Date dateDeb, Date dateFin);
	
	public List<Object[]> rechercheDocument(Date dateDeb, Date dateFin,Boolean light);
	
	public List<T> rechercheDocument(String codeDeb, String codeFin);
	
	public List<T> rechercheDocument(Date dateDeb, Date dateFin,String codeTiers);
	
	public List<Object[]> rechercheDocumentLight(Date dateDeb, Date dateFin,String codeTiers);
	
	public List<Object[]> rechercheDocumentLight(String codeDoc, String codeTiers);
	
	public List<T> rechercheDocument(String codeDeb, String codeFin,String codeTiers);
	
	public List<T> rechercheDocument(Date dateDeb, Date dateFin,String codeTiers,Boolean export);
	
	public List<T> rechercheDocument(String codeDeb, String codeFin,String codeTiers,Boolean export);

	public T findById(int id) ;
}
