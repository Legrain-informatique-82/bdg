package fr.legrain.article.export.catalogue;

import java.util.ArrayList;
import java.util.List;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.tiers.dao.TaTiers;

public class ResultatImportation {
	
	private List<TaTiers> listeTiers = null;
	private List<TaBoncde> listeCommande = null;
	
	public List<TaTiers> getListeTiers() {
		return listeTiers;
	}
	public void setListeTiers(List<TaTiers> listeTiers) {
		this.listeTiers = listeTiers;
	}
	public List<TaBoncde> getListeCommande() {
		return listeCommande;
	}
	public void setListeCommande(List<TaBoncde> listeCommande) {
		this.listeCommande = listeCommande;
	}
	
}
