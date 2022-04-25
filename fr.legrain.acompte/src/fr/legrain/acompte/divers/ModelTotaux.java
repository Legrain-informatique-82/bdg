package fr.legrain.acompte.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxAcompte;

public class ModelTotaux {

	LinkedList<IHMTotauxAcompte> listeObjet = new LinkedList<IHMTotauxAcompte>();

//	public LinkedList remplirListe(SWTFacture swtFacture) {
//		IHMTotauxFacture ihmTotauxFacture = null;
//		listeObjet.clear();
//		if(swtFacture != null) {
//			ihmTotauxFacture = new IHMTotauxFacture();
//			ihmTotauxFacture.setIHMTotauxFacture(((SWTEnteteFacture)swtFacture.getEntete()));
//			listeObjet.add(ihmTotauxFacture);	
//		}
//		return listeObjet;
//	}
	
	public LinkedList<IHMTotauxAcompte> getListeObjet() {
		return listeObjet;
	}

}
