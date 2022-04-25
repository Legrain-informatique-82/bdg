package fr.legrain.articles.preferences;

import java.io.File;

import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.articles.dao.TaImageArticle;
import fr.legrain.gestCom.Appli.Const;


public class UtilPreferenceImage {
	
	/**
	 * Retourne le chemin ou doivent être stocké les images des articles en fonction du choix fait dans les préférences,
	 * retourne NULL si les images ne doivent pas être copié.
	 * @return
	 */
	public String getPathStockageImage() {
		String retour = null;
		String paramStock = ArticlesPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.STOCKAGE_IMAGES);
		if(paramStock.equals(PreferenceConstants.VALEUR_STOCKAGE_IMAGES_ORIGINE)) {
			retour = null;
		} else if(paramStock.equals(PreferenceConstants.VALEUR_STOCKAGE_IMAGES_COPIE_DOSSIER_BDG)) {
			retour = Const.C_CHEMIN_REP_DOSSIER_COMPLET+"/"+Const.FOLDER_IMAGES;
		} else if(paramStock.equals(PreferenceConstants.VALEUR_STOCKAGE_IMAGES_COPIE_REPERTOIRE)) {
			retour = ArticlesPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.REPERTOIRE_STOCKAGE_IMAGES);
		}
		if(retour!=null) {
			initStructure(retour);
		}
		return retour;
	}
	
	public String getPathStockageImageGenere() {
		String retour = null;

		retour = Const.C_CHEMIN_REP_DOSSIER_COMPLET+"/"+Const.FOLDER_IMAGES;
	
		if(retour!=null) {
			initStructure(retour);
		}
		return retour;
	}

	public String cheminCompletImageArticle(TaImageArticle img) {
		return cheminCompletImageArticle(img, false);
	}
	
	/**
	 * Retourne le chemin absolu vers l'image.
	 * @param img - image dont on veut le chemin
	 * @param genere - vrai si c'est une image genere par le programme (redimensionnement auto,...)
	 * @return
	 */
	public String cheminCompletImageArticle(TaImageArticle img, boolean genere) {
		String retour = null;
		String baseCheminImg = null;
		if(genere) {
			baseCheminImg = getPathStockageImageGenere();
		} else {
			baseCheminImg = getPathStockageImage();
		}
		
		if(baseCheminImg!=null) {
			if(img.getCheminImageArticle().startsWith(Const.FOLDER_IMAGES_ARTICLES))
				retour = baseCheminImg+"/"+img.getCheminImageArticle();
			else
				retour = baseCheminImg+"/"+Const.FOLDER_IMAGES_ARTICLES+"/"+img.getNomImageArticle();
		} else {
			retour = img.getCheminImageArticle();
		}
		return retour;
	}
	
	
	private void initStructure(String path) {
		File base = new File(path);
		if(base.exists()) {
			File rep_img_article = new File(path+"/"+Const.FOLDER_IMAGES_ARTICLES);
			File rep_img_categories = new File(path+"/"+Const.FOLDER_IMAGES_CATEGORIES);
			File rep_img_labels = new File(path+"/"+Const.FOLDER_IMAGES_LABELS);
			
			File[] listeRep = new File[] {rep_img_article,rep_img_categories,rep_img_labels};
			
			for (int i = 0; i < listeRep.length; i++) {
				if(!listeRep[i].exists()) {
					listeRep[i].mkdir();
				}
			}

		}
	}

}
