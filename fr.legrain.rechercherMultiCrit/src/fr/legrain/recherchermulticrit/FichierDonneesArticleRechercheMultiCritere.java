package fr.legrain.recherchermulticrit;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;

/** 
 * Version contact "crado", à remplacer par un ensemble de points d'extension
 * 
 * Copie de fr.legrain.articles.divers.FichierDonneesArticle pour éviter l'unique dépendance vers le plugin Article,
 * et qui empécher la création de la version Contact.
 * Classe à supprimer, la seule classe qui devrait exister est "fr.legrain.articles.divers.FichierDonneesArticle".
 * 
 */
public class FichierDonneesArticleRechercheMultiCritere implements fr.legrain.lib.data.IFichierDonnees<TaArticle>{
	
	static Logger logger = Logger.getLogger(FichierDonneesArticleRechercheMultiCritere.class.getName());
	
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	
	public void creationFichierDonnees(List<TaArticle>liste, String repertoireModele, String nomFichier, boolean html) {
		if(liste!=null && liste.size()>0){
//			FileWriter fw = null;
			BufferedWriter bw = null;
			String cheminFichier="";
			try {
				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
				//cheminFichier=new File(repertoireModele+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
				cheminFichier=nomFichier;
//				fw = new FileWriter(cheminFichier);

				//param.setCheminFichierMotCle(new File(Const.pathRepertoireSpecifiques("fr.legrain.publipostageTiers", "Modeles")+"/motcle.properties").getPath());

				String encoding = "UTF-8";
				//String encoding = "ISO-8859-1";

				//bw = new BufferedWriter(fw);
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cheminFichier),encoding));
				bw.write("codeArticle;libellecArticle;libellelArticle;commentaireArticle;diversArticle" +
						";prixPrix;prixttcPrix" 
						//+";solde_total_pts;solde_pts_2010"
						+separateur+finDeLigne);

				for (TaArticle article : liste) {
					//if(tiers.getAccepte()){
						String ligne="";

						ligne=article.getCodeArticle()+separateur;
						if(article.getLibellecArticle()!=null)ligne+=article.getLibellecArticle()+separateur;else ligne+=separateur;
						if(article.getLibellelArticle()!=null)ligne+=article.getLibellelArticle()+separateur;else ligne+=separateur;
						if(html) {
							if(article.getCommentaireArticle()!=null)ligne+=article.getCommentaireArticle().replaceAll("\\r\\n|\\r|\\n", "<br>")+separateur;else ligne+=separateur;
							if(article.getDiversArticle()!=null)ligne+=article.getDiversArticle().replaceAll("\\r\\n|\\r|\\n", "<br>")+separateur;else ligne+=separateur;
						} else {
							if(article.getCommentaireArticle()!=null)ligne+=article.getCommentaireArticle()+separateur;else ligne+=separateur;
							if(article.getDiversArticle()!=null)ligne+=article.getDiversArticle()+separateur;else ligne+=separateur;
						}
						
						if(article.getTaPrix()!=null)
							ligne+=article.getTaPrix().getPrixPrix()+separateur;else ligne+=separateur;
						if(article.getTaPrix()!=null)
							ligne+=article.getTaPrix().getPrixttcPrix()+separateur;else ligne+=separateur;
		
						//					ligne+="200"+separateur;
						//					ligne+="200"+separateur;
						bw.write(ligne);
						bw.write(finDeLigne);
					//}
				}

				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
			} catch(IOException ioe){
				logger.error("",ioe);
			} 
			finally{
				try {
					if(bw!=null) bw.close();
//					if(fw!=null) fw.close();

				} catch (Exception e) {
					
				}
			}
		}
	}
}
