package fr.legrain.boncde.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaBoncde;


public class ModelLignesTVABoncde {

	LinkedList<IHMLignesTVABoncde> listeObjet = new LinkedList<IHMLignesTVABoncde>();

	public LinkedList remplirListe(TaBoncde taBoncde) {
		IHMLignesTVABoncde ihmLignesTVA = null;
		listeObjet.clear();
		if(taBoncde != null) {
			for(LigneTva ligneTVA : taBoncde.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVABoncde();
				ihmLignesTVA.setIHMBoncdeLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVABoncde> getListeObjet() {
		return listeObjet;
	}

}
