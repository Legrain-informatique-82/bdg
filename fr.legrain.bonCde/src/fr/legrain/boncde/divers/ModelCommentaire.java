package fr.legrain.boncde.divers;

import java.util.LinkedList;

import fr.legrain.gestCom.Module_Document.IHMCommentaire;
import fr.legrain.gestCom.Module_Tiers.SWTCommentaire;

public class ModelCommentaire {

	LinkedList<IHMCommentaire> listeObjet = new LinkedList<IHMCommentaire>();

	public LinkedList remplirListe(SWTCommentaire swtCommentaire) {
		IHMCommentaire ihmCommentaire = null;
		listeObjet.clear();
		if(swtCommentaire != null) {
			ihmCommentaire = new IHMCommentaire();
			ihmCommentaire.setSWTCommentaire(swtCommentaire);
			listeObjet.add(ihmCommentaire);	
		}
		return listeObjet;
	}
	
	public LinkedList<IHMCommentaire> getListeObjet() {
		return listeObjet;
	}

}
