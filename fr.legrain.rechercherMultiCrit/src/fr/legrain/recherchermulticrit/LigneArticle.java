/**
 * 
 */
package fr.legrain.recherchermulticrit;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.recherchermulticrit.controllers.FormPageController;

/**
 * @author nicolas²
 *
 */
public class LigneArticle extends Ligne {
	
	private Map<String,String> listeCorrespondanceArticles=null;
	private Map<String,String> listeTitreChampsArticles=null;
	private Map<String,Object> listeObjetArticles=null;
	
	private Map<String,String> listeCorrespondanceComparateurs=null;
	private Map<String,String> listeTitreChampsComparateurs=null;

	/**
	 * @param parent
	 * @param toolkit
	 * @param masterController 
	 */
	public LigneArticle(int numGroupe,Composite parent, FormToolkit toolkit, FormPageController masterController) {
		super(numGroupe,parent, toolkit,masterController);
		super.getType().setText("Article");
		initChamps();
	}
	
	private void initChamps() {
		// *****************************************
		// ** Initialisation du champ de critères **
		// *****************************************
		listeCorrespondanceArticles=new LinkedHashMap<String, String>();
		listeTitreChampsArticles=new LinkedHashMap<String, String>();
		listeObjetArticles=new LinkedHashMap<String,Object>();

		listeTitreChampsArticles.put("Code Article","codeArticle");
		listeTitreChampsArticles.put("Libellé","libellecArticle");
		listeTitreChampsArticles.put("Description","libellelArticle");
		listeTitreChampsArticles.put("Code Famille","codeFamille");
		listeTitreChampsArticles.put("Libellé Famille","libcFamille");
		listeTitreChampsArticles.put("Code TVA","codeTva");
		listeTitreChampsArticles.put("Tarif HT","prixPrix");
		listeTitreChampsArticles.put("Tarif TTC","prixttcPrix");
		listeTitreChampsArticles.put("Compte","numcptTva");
		listeTitreChampsArticles.put("Stock Mini","stockMinArticle");
		
		TaArticle article = new TaArticle();
		TaFamille famille = new TaFamille();
		TaTva tva = new TaTva();
		TaPrix prix = new TaPrix();
		listeObjetArticles.put("codeArticle",article);
		listeObjetArticles.put("libellecArticle",article);
		listeObjetArticles.put("libellelArticle",article);
		listeObjetArticles.put("codeFamille",famille);
		listeObjetArticles.put("libcFamille",famille);
		listeObjetArticles.put("prixPrix",prix);
		listeObjetArticles.put("prixttcPrix",prix);
		listeObjetArticles.put("codeTva",tva);
		listeObjetArticles.put("numcptTva",tva);
		listeObjetArticles.put("stockMinArticle",article);
		
		listeCorrespondanceArticles.put("codeArticle","codeArticle");
		listeCorrespondanceArticles.put("libellecArticle","libellecArticle");
		listeCorrespondanceArticles.put("libellelArticle","libellelArticle");
		listeCorrespondanceArticles.put("codeFamille","taFamille.codeFamille");
		listeCorrespondanceArticles.put("libcFamille","taFamille.libcFamille");
		listeCorrespondanceArticles.put("prixPrix","taPrix.prixPrix");
		listeCorrespondanceArticles.put("prixttcPrix","taPrix.prixttcPrix");
		listeCorrespondanceArticles.put("codeTva","taTva.numcptTva");
		listeCorrespondanceArticles.put("numcptTva","taTva.numcptTva");
		listeCorrespondanceArticles.put("stockMinArticle","stockMinArticle");

		
		// -- Insertion des critères dans la combo de critères --
		getCritere().add("<Choisir>");
		for (String champs : listeTitreChampsArticles.keySet()) {
			getCritere().add(champs);
		}
		getCritere().setVisibleItemCount(getCritere().getItemCount());
		getCritere().select(0);
		
		// *********************************************
		// ** Initialisation du champ de comparateurs **
		// *********************************************
		listeTitreChampsComparateurs=new LinkedHashMap<String, String>();
		
		listeTitreChampsComparateurs.put("est égal à","=");
		listeTitreChampsComparateurs.put("n'est pas égal à","<>");
		listeTitreChampsComparateurs.put("est supérieur à",">");
		listeTitreChampsComparateurs.put("est inférieur à","<");
		listeTitreChampsComparateurs.put("est compris entre","BETWEEN");
		listeTitreChampsComparateurs.put("contient","LIKE");
		listeTitreChampsComparateurs.put("ne contient pas","NOT LIKE");
		listeTitreChampsComparateurs.put("commence par","LIKE");
		listeTitreChampsComparateurs.put("ne commence pas par","NOT LIKE");
		listeTitreChampsComparateurs.put("fini par","LIKE");
		listeTitreChampsComparateurs.put("ne fini pas par","NOT LIKE");
		
		// -- Insertion des critères dans la combo de comparateurs --
		getComparaison().add("<Choisir>");
		for (String champs : listeTitreChampsComparateurs.keySet()) {
			getComparaison().add(champs);
		}
		getComparaison().setVisibleItemCount(getComparaison().getItemCount());
		getComparaison().select(0);
		
	}
	
	public String transformSQLArticle(String champ) {
		return listeCorrespondanceArticles.get(listeTitreChampsArticles.get(champ));
	}
	
	public String transformSQLcomparateur(String champ) {
		return listeTitreChampsComparateurs.get(champ);
	}
	

}
