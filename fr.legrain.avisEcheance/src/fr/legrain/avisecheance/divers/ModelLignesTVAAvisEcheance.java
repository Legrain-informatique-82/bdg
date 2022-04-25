package fr.legrain.avisecheance.divers;

import java.util.LinkedList;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaAvisEcheance;

public class ModelLignesTVAAvisEcheance {

	LinkedList<IHMLignesTVAAvisEcheance> listeObjet = new LinkedList<IHMLignesTVAAvisEcheance>();

	public LinkedList remplirListe(TaAvisEcheance taAvisEcheance) {
		IHMLignesTVAAvisEcheance ihmLignesTVA = null;
		listeObjet.clear();
		if(taAvisEcheance != null) {
			for(LigneTva ligneTVA : taAvisEcheance.getLignesTVA()){
				ihmLignesTVA = new IHMLignesTVAAvisEcheance();
				ihmLignesTVA.setIHMAvisEcheanceLignesTVA(ligneTVA);
				listeObjet.add(ihmLignesTVA);
			}
		}
		return listeObjet;
	}
	
	public LinkedList<IHMLignesTVAAvisEcheance> getListeObjet() {
		return listeObjet;
	}

}
