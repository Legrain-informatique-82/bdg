package fr.legrain.bonlivraison.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMTotauxBonliv;

public class ModelTotauxBL {

	LinkedList<IHMTotauxBonliv> listeObjet = new LinkedList<IHMTotauxBonliv>();

//	public LinkedList remplirListe(SWTBonLiv swtBonLiv) {
//		IHMTotauxBonliv ihmTotauxBonliv = null;
//		listeObjet.clear();
//		if(swtBonLiv != null) {
//			ihmTotauxBonliv = new IHMTotauxBonliv();
//			ihmTotauxBonliv.setIHMTotauxBONLIV(((SWTEnteteBonLiv)swtBonLiv.getEntete()));
//			listeObjet.add(ihmTotauxBonliv);	
//		}
//		return listeObjet;
//	}
	
	public LinkedList<IHMTotauxBonliv> getListeObjet() {
		return listeObjet;
	}

}
