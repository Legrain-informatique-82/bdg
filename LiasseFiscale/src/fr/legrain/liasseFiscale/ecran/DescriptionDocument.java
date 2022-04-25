package fr.legrain.liasseFiscale.ecran;

import java.util.ArrayList;

public class DescriptionDocument {
	private String titre;
	private ArrayList<Page> pages = new ArrayList<Page>(); //liste des pages
	
	public DescriptionDocument() {}
	
	public ArrayList<Page> getPages() {
		return pages;
	}
	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
}
