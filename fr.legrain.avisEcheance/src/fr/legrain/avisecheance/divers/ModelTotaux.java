package fr.legrain.avisecheance.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxAvisEcheance;

public class ModelTotaux {

	LinkedList<IHMTotauxAvisEcheance> listeObjet = new LinkedList<IHMTotauxAvisEcheance>();

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
	
	public LinkedList<IHMTotauxAvisEcheance> getListeObjet() {
		return listeObjet;
	}

}
