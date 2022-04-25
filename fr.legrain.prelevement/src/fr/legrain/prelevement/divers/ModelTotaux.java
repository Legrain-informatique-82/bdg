package fr.legrain.prelevement.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxBoncde;
import fr.legrain.gestCom.Module_Document.IHMTotauxPrelevement;
import fr.legrain.gestCom.Module_Document.IHMTotauxProforma;

public class ModelTotaux {

	LinkedList<IHMTotauxPrelevement> listeObjet = new LinkedList<IHMTotauxPrelevement>();

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
	
	public LinkedList<IHMTotauxPrelevement> getListeObjet() {
		return listeObjet;
	}

}
