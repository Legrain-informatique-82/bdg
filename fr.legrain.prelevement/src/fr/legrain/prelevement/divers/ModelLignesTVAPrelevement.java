package fr.legrain.prelevement.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;

public class ModelLignesTVAPrelevement {

	LinkedList<IHMLignesTVAPrelevement> listeObjet = new LinkedList<IHMLignesTVAPrelevement>();

	public LinkedList remplirListe(TaPrelevement taPrelevement) {
		IHMLignesTVAPrelevement ihmLignesTVA = null;
		listeObjet.clear();
		if(taPrelevement != null) {
			for(LigneTva ligneTVA : taPrelevement.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVAPrelevement();
				ihmLignesTVA.setIHMPrelevementLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVAPrelevement> getListeObjet() {
		return listeObjet;
	}

}
