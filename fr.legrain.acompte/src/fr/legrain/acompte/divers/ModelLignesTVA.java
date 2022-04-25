package fr.legrain.acompte.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaAcompte;

public class ModelLignesTVA {

	LinkedList<IHMLignesTVA> listeObjet = new LinkedList<IHMLignesTVA>();

	public LinkedList<IHMLignesTVA> remplirListe(TaAcompte taAcompte) {
		IHMLignesTVA ihmLignesTVA = null;
		listeObjet.clear();
		if(taAcompte != null) {
			for(LigneTva ligneTVA : taAcompte.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVA();
				ihmLignesTVA.setIHMFactureLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVA> getListeObjet() {
		return listeObjet;
	}

}
