package fr.legrain.apporteur.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxApporteur;

public class ModelTotaux {

	LinkedList<IHMTotauxApporteur> listeObjet = new LinkedList<IHMTotauxApporteur>();

//	public LinkedList remplirListe(SWTFacture swtFacture) {
//		IHMTotauxApporteur ihmTotauxApporteur = null;
//		listeObjet.clear();
//		if(swtFacture != null) {
//			ihmTotauxApporteur = new IHMTotauxApporteur();
//			ihmTotauxApporteur.setIHMTotauxApporteur(((SWTEnteteFacture)swtFacture.getEntete()));
//			listeObjet.add(ihmTotauxApporteur);	
//		}
//		return listeObjet;
//	}
	
	public LinkedList<IHMTotauxApporteur> getListeObjet() {
		return listeObjet;
	}

}
