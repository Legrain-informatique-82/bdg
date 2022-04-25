/**
 * Requete.java
 */
package fr.legrain.recherchermulticrit;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.ListUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.lib.data.LibDate;
import fr.legrain.tiers.dao.TaTiers;

/**
 * Classe permettant la génération de requêtes
 * @author nicolas²
 *
 */
public class Requete {

	private Map<String,String> corresDocTable = new LinkedHashMap<String, String>();
	private Map<String,String> corresDocPrefixe = new LinkedHashMap<String, String>();
	private List<GroupeLigne> groupe = new ArrayList<GroupeLigne>();
	private List<LigneArticle> lignesArt = new ArrayList<LigneArticle>();
	private List<LigneDocument> lignesDoc = new ArrayList<LigneDocument>();
	private List<LigneTiers> lignesTiers = new ArrayList<LigneTiers>();
	private List<ArrayList<String>> listeDifferentsDocs = new ArrayList<ArrayList<String>>();
	private List<List<Object>> listeResultats = new ArrayList<List<Object>>();
	private List<Object> listeFinale = new ArrayList<Object>();
	private boolean isSimplifie = false;    // Boolean permettant d'évaluer si la requête à été simplifiée ou non
	private String resultat = null;
	private String[] selects = null;
	private String[] froms = null;
	private String[] joins = null;
	private String[] wheres = null;
	private String[] havings = null;
	private String[] groupbys = null;
	private String[] rqtGroupes = null;
	private boolean whereIsDeclared = false;
	// On récupère uniquement l'entity manager de TaArticleDAO
	private EntityManager masterDAO = new TaArticleDAO().getEntityManager();

	/** 
	 * Constructeur de la requête
	 * @param resultat
	 * @param groupe
	 */
	public Requete(String resultat,List<GroupeLigne> groupe){
		// Le résultat doit être valide
		// Le groupe doit contenir au moins une ligne
		if (resultat != null && groupe.size() != 0) {
			this.groupe = groupe; 		// Affectation du groupe
			this.resultat = resultat; 	// Affectation du résultat
			selects = new String[groupe.size()]; // Initialisation des Selects
			froms = new String[groupe.size()];	 // Initialisation des Froms
			joins = new String[groupe.size()]; 	 // Initialisation des Joins
			wheres = new String[groupe.size()];	 // Initialisation des Wheres
			havings = new String[groupe.size()];	 // Initialisation des Having
			groupbys = new String[groupe.size()];	 // Initialisation des GB
			rqtGroupes = new String[groupe.size()]; // Initialisation requêtes
			initMaps(); 	  // Initialisation des Maps
			typeResultat();   // Initialisation du SFW ( Select From Where )
			analyseGroupes(); // Démarrage de l'analyse de la requête
			initRequete();	  // Lance la requête
			traiteRequete();  // Traite la requête par groupes

		} 
	}

	/**
	 * Méthode d'initialisation de la requête
	 * Permet d'établir le SFW de base
	 */
	private void initRequete(){
		for (int i =0 ; i < groupe.size(); i++){
			// On recompose la requête
			List<Object> listeresult = new ArrayList<Object>();
			rqtGroupes[i] 	= "";
			rqtGroupes[i]	+= selects[i];
			rqtGroupes[i]  	+= froms[i];
			rqtGroupes[i]  	+= joins[i];
			rqtGroupes[i]  	+= wheres[i];
			rqtGroupes[i]   += groupbys[i];
			rqtGroupes[i] 	+= havings[i];
			System.err.println(rqtGroupes[i]);
			Query requetePrincipale = masterDAO.createQuery(rqtGroupes[i]);
			listeresult =requetePrincipale.getResultList();
			int nbresult = listeresult.size();
			boolean tropComplexe = false ; 	// Boolean permettant de mettre fin à la requête
											// en cas de complexité trop élevée
			boolean isComplexe = false;     // Boolean permettant d'évaluer la complexité de la requête
			System.err.println(nbresult);	
			// Cas d'une requête complexe
			// Résultat sélectionné : Tiers | Critères chiffre d'affaires : multiples
			if (!havings[i].equals(" ") && resultat.equals("Tiers") && nbresult !=0 && listeDifferentsDocs.get(i).size()!=1){
				for(int tabres = 0; tabres < listeresult.size();tabres++){ // On parcourt les différents tableaux de résultat
					for(int res=0; res < ((Object[])listeresult.get(0)).length;res++){ // On parcourt les différents résultat
						if (tabres == 0 && res ==0){ // Premier résultat, construction de la requête
							rqtGroupes[i] = "SELECT DISTINCT t FROM TaTiers t WHERE t.codeTiers ='"+(String)((Object[])listeresult.get(tabres))[res]+"'";
						} else { // Ajout du critère à la requête
							rqtGroupes[i]+=" OR t.codeTiers ='"+(String)((Object[])listeresult.get(tabres))[res]+"'";
						}

					}
				}
				isSimplifie = true ; // Requête simplifiée ( un seul objet dans le select )
				isComplexe = true; // Booléen de confirmation de complexité
				// Résultat sélectionné : Tiers | Critères chiffre d'affaires : simple
			} else if (!havings[i].equals(" ") && resultat.equals("Tiers") && nbresult !=0){
				for(int res=0; res < listeresult.size();res++){
					if (res ==0){
						rqtGroupes[i] = "SELECT DISTINCT t FROM TaTiers t WHERE t.codeTiers ='"+listeresult.get(res)+"'";
					} else {
						rqtGroupes[i]+=" OR t.codeTiers ='"+listeresult.get(res)+"'";
					}

				}
				isSimplifie = true ; // Requête simplifiée ( un seul objet dans le select )
				isComplexe = true;
				// Résultat sélectionné : Articles | Critères chiffre d'affaires : multiples
			} else if (!havings[i].equals(" ") && resultat.equals("Articles") && nbresult !=0 && listeDifferentsDocs.get(i).size() !=1) {
				for(int tabres = 0; tabres < listeresult.size();tabres++){
					for(int res=0; res< ((Object[])listeresult.get(0)).length;res++){
						if (tabres == 0 && res ==0){
							rqtGroupes[i] = "SELECT DISTINCT a FROM TaArticle a WHERE a.codeArticle ='"+(String)((Object[])listeresult.get(tabres))[res]+"'";
						} else {
							rqtGroupes[i]+=" OR a.codeArticle ='"+(String)((Object[])listeresult.get(tabres))[res]+"'";
						}

					}
				}
				isSimplifie = true ; // Requête simplifiée ( un seul objet dans le select )
				isComplexe = true;
				// Résultat sélectionné : Articles | Critères chiffre d'affaires : simples
			} else if (!havings[i].equals(" ") && resultat.equals("Articles") && nbresult !=0) {
				for(int res=0; res< listeresult.size();res++){
					if (res ==0){
						rqtGroupes[i] = "SELECT DISTINCT a FROM TaArticle a WHERE a.codeArticle ='"+listeresult.get(res)+"'";
					} else {
						rqtGroupes[i]+=" OR a.codeArticle ='"+listeresult.get(res)+"'";
					}

				}
				isSimplifie = true ; // Requête simplifiée ( un seul objet dans le select )
				isComplexe = true;
				// Résultat sélectionné : Documents | Critères chiffre d'affaires : simples
			} else if (!havings[i].equals(" ") && resultat.equals("Documents")&& nbresult>0 && listeDifferentsDocs.get(i).size()==1) {
				rqtGroupes[i] = " SELECT DISTINCT  ";
				rqtGroupes[i]+= corresDocPrefixe.get(listeDifferentsDocs.get(i).get(0));
				for(int doc=1;doc<listeDifferentsDocs.get(i).size();doc++){
					rqtGroupes[i]+= ", "+corresDocPrefixe.get(listeDifferentsDocs.get(i).get(doc));
				}
				rqtGroupes[i] += " FROM ";
				rqtGroupes[i]+= "Ta"+corresDocTable.get(listeDifferentsDocs.get(i).get(0));
				rqtGroupes[i]+= " "+corresDocPrefixe.get(listeDifferentsDocs.get(i).get(0));
				for(int doc=1;doc<listeDifferentsDocs.get(i).size();doc++){
					rqtGroupes[i]+= ", Ta"+corresDocTable.get(listeDifferentsDocs.get(i).get(doc));
					rqtGroupes[i]+= " "+corresDocPrefixe.get(listeDifferentsDocs.get(i).get(doc));
				}

				for(int res=0; res< listeresult.size();res++){
					if (res ==0){
						rqtGroupes[i] += " WHERE "+corresDocPrefixe.get(listeDifferentsDocs.get(i).get(0))+".codeDocument ='"+listeresult.get(res)+"'";
						for(int doc=1;doc<listeDifferentsDocs.get(i).size();doc++){
							rqtGroupes[i]+= "OR "+corresDocPrefixe.get(listeDifferentsDocs.get(i).get(doc))+".codeDocument ='"+listeresult.get(res)+"'";;
						}
					} else {
						for(int doc=0;doc<listeDifferentsDocs.get(i).size();doc++){
							rqtGroupes[i]+= "OR "+corresDocPrefixe.get(listeDifferentsDocs.get(i).get(doc))+".codeDocument ='"+listeresult.get(res)+"'";;
						}
					}

				}
				isComplexe = true;


				// Résultat sélectionné : Documents | Critères chiffre d'affaires : trop complexes
			} else if (!havings[i].equals(" ") && resultat.equals("Documents")&& nbresult>0 && listeDifferentsDocs.get(i).size()!=1) {
				int groupe = i+1;
				String ttl = "Complexité de la recherche trop élevée";
				String msg = "Le niveau de complexité de la recherche est trop élevé.\n"
					+"Une recherche portant sur des documents (Etape 1) et incluant un critère sur chiffre d'affaire (Groupe "+
					+groupe+") ne peut porter que sur un seul document.";

				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttl, msg);
				tropComplexe = true;
				isComplexe = true;
			}

			if (nbresult != 0 && !tropComplexe && isComplexe){
				System.err.println(rqtGroupes[i]);
				Query query = masterDAO.createQuery(rqtGroupes[i]);
				listeresult = query.getResultList();
				System.err.println(listeresult.size());
			}

			// Simplification des éléments de la liste : Elimination des doublons, rassemblement des listes
			if (listeDifferentsDocs.get(i).size()!=1 && !isSimplifie ){
				List<Object> listeresultcopy = new ArrayList<Object>();
				for (int k=0; k<listeresult.size();k++){

					for (int j=0; j < ((Object[])listeresult.get(k)).length;j++){
						if (!listeresultcopy.contains(((Object[])listeresult.get(k))[j])){
							listeresultcopy.add(((Object[])listeresult.get(k))[j]);
						}
					}

				}	
				listeresult = listeresultcopy;
			}


			if (!tropComplexe){
				listeResultats.add(listeresult);
			}




		}


	}


	/**
	 * Génère un select et un from de base en fonction du type de resultat
	 */
	private void typeResultat() {
		if (resultat.equals("Tiers")){ 			// Résultat contenant Tiers
			for (int i = 0; i< groupe.size();i++){
				listeDifferentsDocs.add(new ArrayList<String>());
				selects[i] = "SELECT t";
				froms[i] = " ";
				joins[i] = " ";
				wheres[i] = " ";
				havings[i] = " ";
				groupbys[i] = " ";
			}
		} else if (resultat.equals("Articles")){ // Résultat contenant Articles
			for (int i = 0; i< groupe.size();i++){
				listeDifferentsDocs.add(new ArrayList<String>());
				selects[i] = "SELECT a";
				froms[i] = " ";
				joins[i] = " ";
				wheres[i] = " ";
				havings[i] = " ";
				groupbys[i] = " ";
			}
		} else { // Document
			for (int i = 0; i< groupe.size();i++){ // Résultat contenant Documents
				listeDifferentsDocs.add(new ArrayList<String>());
				selects[i] = "SELECT ";
				froms[i] = " ";
				joins[i] = " ";
				wheres[i] = " ";
				havings[i] = " ";
				groupbys[i] = " ";
			}

		}
	}





	/**
	 * Génère un select de base en fonction du type de resultat
	 * @param typeRes 	t pour tiers
	 * 					a pour articles
	 * 					"" pour documents
	 */
	private void genereSelect(String typeRes, String suffixe,int legroupe) {
		if(listeDifferentsDocs.get(legroupe).size()!=0){ // Précaution : critère document existant
			selects[legroupe] = "SELECT DISTINCT "+typeRes+corresDocPrefixe.get(listeDifferentsDocs.get(legroupe).get(0))+suffixe;
			for(int j=1;j<listeDifferentsDocs.get(legroupe).size();j++){
				String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(legroupe).get(j));
				selects[legroupe] += ", "+typeRes+prefixe+suffixe;
			}
		}


	}

	/**
	 * Génère un group by de base en fonction du type de resultat
	 * @param typeRes 	t pour tiers
	 * 					a pour articles
	 * 					"" pour documents
	 */
	private void genereGB(String typeRes, String suffixe, int legroupe) {
		if(listeDifferentsDocs.get(legroupe).size()!=0){ // Précaution : critère document existant
			groupbys[legroupe] = " GROUP BY "+typeRes+corresDocPrefixe.get(listeDifferentsDocs.get(legroupe).get(0))+suffixe;
			for(int j=1;j<listeDifferentsDocs.get(legroupe).size();j++){
				String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(legroupe).get(j));
				groupbys[legroupe] += ", "+typeRes+prefixe+suffixe;
			}
		}


	}


	/**
	 * Méthode renvoyant une liste d'object
	 * contenant le résultat de la requête
	 * @return la liste d'object contenant le résultat
	 */
	public ArrayList<Object> getResultat() {
		return (ArrayList<Object>) listeFinale;
	}


	/**
	 * Méthode permettant d'appliquer le format avec % pour les Like
	 * @param comparateur le comparateur pour le type de format à appliquer
	 * @param valeur la valeur sur laquelle on doit appliquer le format
	 * @return la valeur formatée
	 */
	private String appliTransfo(String comparateur, String valeur) {
		if (comparateur.equals("contient") || comparateur.equals("ne contient pas")){
			valeur = "%"+valeur+"%";
		} else if (comparateur.equals("commence par") || comparateur.equals("ne commence pas par")){
			valeur = valeur+"%";
		} else if (comparateur.equals("fini par") || comparateur.equals("ne fini pas par") ){
			valeur = "%"+valeur;
		} else if (valeur.equals(Ligne.BOOLEAN_TRUE_TEXT)) {
			valeur = Ligne.BOOLEAN_TRUE_VALUE;
		} else if (valeur.equals(Ligne.BOOLEAN_FALSE_TEXT)) {
			valeur = Ligne.BOOLEAN_FALSE_VALUE;
		}
		return valeur;

	}


	/*** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Méthode permettant l'analyse des groupes afin d'établir la requête  *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  
	 * * * * Première étape : Phase de tri * * * * * * * * * * * * * * * * *
	 * Lors de cette étape, les lignes sont triées par type pour faciliter *
	 * le découpage de la requête. Avant leur tri, leurs informations      *
	 * sont récupérées afin de les ajoutées à la clause WHERE de la requête*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * * * * Deuxième étape : Froms et Jointures * * * * * * * * * * * * * *
	 * Durant cette seconde étape, en fonction des critères rentrés, la    *
	 * méthode va procéder à la construction du from et des joins de la    *
	 * requête.                                                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 */
	private void analyseGroupes() {
		// On parcourt les groupes
		for (int igroupe =0; igroupe<groupe.size(); igroupe++){
			whereIsDeclared=false;
			// * ----------- PHASE DE TRI ----------- * //
			// Tri les lignes du groupe
			// On parcourt les lignes
			// * ----------- PHASE DE TRI ----------- * //
			// Tri les lignes du groupe
			// On parcourt les lignes
			for (int iligne = 0; iligne<groupe.get(igroupe).getGroupe().size();
			iligne++){
				// On récupère la ligne traitée
				// Et on l'ajoute dans la liste qui convient
				Ligne laligne =  groupe.get(igroupe).getGroupe().get(iligne);		
				if (laligne instanceof LigneTiers){
					lignesTiers.add((LigneTiers) laligne);
				} else if (laligne instanceof LigneArticle) { 
					lignesArt.add((LigneArticle) laligne);
				} else {
					lignesDoc.add((LigneDocument) laligne);
					ajouteSiNonPresent((LigneDocument) laligne, igroupe);
				}
			}
			// On vérifie s'il y a des incohérence dans la requête
			// ex : requête sur tiers avec articles en argument
			verifIncoherences();

			if(resultat.equals("Documents")){
				genereSelect("","",igroupe);
			} else if (resultat.equals("Tiers") && lignesDoc.size()!=0){
				genereSelect("t","",igroupe);
			} else if (resultat.equals("Articles") && lignesDoc.size()!=0){
				genereSelect("a","",igroupe);
			}

			// On refait le tour des lignes pour traiter les selects / where
			for (int iligne = 0; iligne<groupe.get(igroupe).getGroupe().size();
			iligne++){
				// On récupère la ligne traitée
				Ligne laligne =  groupe.get(igroupe).getGroupe().get(iligne);		
				if (laligne instanceof LigneTiers){
					// La ligne est une ligne de tiers
					if (listeDifferentsDocs.get(igroupe).size() == 0){
						ajoutWhereTiers(laligne,"", iligne, igroupe);
					}
					for(int i = 0; i<listeDifferentsDocs.get(igroupe).size();i++){
						String prefix = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
						ajoutWhereTiers(laligne,prefix, iligne, igroupe);
					}

				} else if (laligne instanceof LigneArticle) { 
					// La ligne est une ligne d'articles 
					if (listeDifferentsDocs.get(igroupe).size() == 0){
						ajoutWhereArticle(laligne,"", iligne, igroupe);
					}
					for(int i = 0; i<listeDifferentsDocs.get(igroupe).size();i++){
						String prefix = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
						ajoutWhereArticle(laligne,prefix, iligne, igroupe);
					}
				} else {
					// La ligne est une ligne de document
					ajoutWhereDocument(laligne, laligne.getType().getText(),
							iligne, igroupe);
				}
			}

			// * ----------- PHASE DE PRE-CONSTRUCTION ----------- * //
			// Création du SFW
			// -- CAS TIERS + DOC + ART --
			if (lignesTiers.size() != 0 && lignesArt.size() != 0 
					&& lignesDoc.size() != 0 ){ 
				// Construction FROMS
				// On ajoute les tables utiles pour les lignes et les docs
				froms[igroupe] += " FROM TaL"
					+corresDocTable.get(listeDifferentsDocs.get(igroupe).get(0))
					+" l"+corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(0));
				for(int i=1;i<listeDifferentsDocs.get(igroupe).size();i++){
					String table = corresDocTable.get(listeDifferentsDocs.get(igroupe).get(i));
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					froms[igroupe] += ", TaL"+table+" l"+prefixe;
				}
				// Construction JOINS
				for(int i=0;i<listeDifferentsDocs.get(igroupe).size();i++){
					String table = corresDocTable.get(listeDifferentsDocs.get(igroupe).get(i));
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					joins[igroupe] += " LEFT JOIN "
						+" l"+prefixe+".taDocument "+prefixe
						+" LEFT JOIN "
						+" l"+prefixe+".taArticle a"+prefixe
						+" LEFT JOIN "
						+" "+prefixe+".taTiers t"+prefixe;
				}
				
				// Cas requête simplifiée
				if (!resultat.equals("Documents")){
					isSimplifie = true;
				}

				// -- CAS TIERS + DOC --
			} else if (lignesTiers.size() != 0
					&& lignesDoc.size() != 0 
					&& lignesArt.size() == 0){ 
				// Construction FROMS
				froms[igroupe] += " FROM Ta"
					+corresDocTable.get(listeDifferentsDocs.get(igroupe).get(0))+" "
					+corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(0));
				// On ajoute les tables utiles pour les lignes et les docs
				for(int i=1;i<listeDifferentsDocs.get(igroupe).size();i++){
					String table = corresDocTable.get(listeDifferentsDocs.get(igroupe).get(i));
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					froms[igroupe] += ", Ta"+table+" "+prefixe;
				}
				// Construction JOINS
				for(int i=0;i<listeDifferentsDocs.get(igroupe).size();i++){
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					joins[igroupe] += " LEFT JOIN "
						+" "+prefixe+".taTiers t"+prefixe;
				}
				

				// -- CAS ARTICLES + DOC --
			} else if (lignesArt.size() != 0
					&& lignesDoc.size() != 0 
					&& lignesTiers.size() == 0){ 
				// Construction FROMS
				froms[igroupe] += " FROM TaL"+corresDocTable.get(listeDifferentsDocs.get(igroupe).get(0))
				+" l"+corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(0));
				// On ajoute les tables utiles pour les lignes et les docs
				for(int i=1;i<listeDifferentsDocs.get(igroupe).size();i++){
					String table = corresDocTable.get(listeDifferentsDocs.get(igroupe).get(i));
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					froms[igroupe] += " , TaL"+table+" l"+prefixe;
				}
				// Construction JOINS
				for(int i=0;i<listeDifferentsDocs.get(igroupe).size();i++){
					String table = corresDocTable.get(listeDifferentsDocs.get(igroupe).get(i));
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					joins[igroupe] += " LEFT JOIN "
						+" l"+prefixe+".taDocument "+prefixe
						+" LEFT JOIN "
						+" l"+prefixe+".taArticle a"+prefixe;
				}

				// -- CAS TIERS --
			} else if (lignesTiers.size() != 0
					&& lignesDoc.size() == 0 
					&& lignesArt.size() == 0){ 
				// Construction FROMS
				froms[igroupe] += " FROM TaTiers t";
				isSimplifie = true;
				// -- CAS ARTICLES --
			} else if (lignesTiers.size() == 0
					&& lignesDoc.size() == 0 
					&& lignesArt.size() != 0){ 
				// Construction FROMS
				froms[igroupe] += " FROM TaArticle a";
				isSimplifie = true;
				// -- CAS DOCUMENTS --
			} 	else if (lignesTiers.size() == 0
					&& lignesDoc.size() != 0 
					&& lignesArt.size() == 0){ 
				// Construction FROMS
				froms[igroupe] += " FROM  Ta"
					+corresDocTable.get(listeDifferentsDocs.get(igroupe).get(0))
					+" "+corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(0));
				for(int i=1;i<listeDifferentsDocs.get(igroupe).size();i++){
					String table = corresDocTable.get(listeDifferentsDocs.get(igroupe).get(i));
					String prefixe = corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(i));
					froms[igroupe] += ", Ta"+table+" "+prefixe;
				}
			}

			if(resultat.equals("Documents")){

			} else if (resultat.equals("Tiers")){
				for(int typedoc = 1; typedoc < listeDifferentsDocs.get(igroupe).size() ; typedoc++){
					wheres[igroupe] += " AND t"+ corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(0))+" = t"
					+ corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(typedoc));
				}
			} else if (resultat.equals("Articles")){
				for(int typedoc = 1; typedoc < listeDifferentsDocs.get(igroupe).size() ; typedoc++){
					wheres[igroupe] += " AND a"+ corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(0))+" = a"
					+ corresDocPrefixe.get(listeDifferentsDocs.get(igroupe).get(typedoc));
				}
			}

		}
	}

	/**
	 * Méthode permettant l'ajout du type de document
	 * A la liste des types de documents différents
	 * @param laligne la ligne contenant le type qui sera ajouté
	 *  	  Si celui ci n'est pas déjà présent
	 */
	private void ajouteSiNonPresent(LigneDocument laligne, int legroupe){
		boolean ajoutOK = true;
		for(int i=0;i<listeDifferentsDocs.get(legroupe).size() && ajoutOK;i++){
			// On parcourt les différents types de documents
			ajoutOK = laligne.getType().getText() !=  listeDifferentsDocs.get(legroupe).get(i);
		}
		if (ajoutOK){ // La ligne n'est pas déjà présente dans la liste
			listeDifferentsDocs.get(legroupe).add(laligne.getType().getText());
		}
	}

	/**
	 * Méthode permettant l'initialisation des maps pour les différents docs
	 */
	private void initMaps() {
		corresDocTable.put("Facture","Facture");
		corresDocTable.put("Devis","Devis");
		corresDocTable.put("Commande","Boncde");
		corresDocTable.put("Avoir","Avoir");		
		corresDocTable.put("Apporteur","Apporteur");
		corresDocTable.put("Acompte","Acompte");
		corresDocTable.put("Proforma","Proforma");
		corresDocTable.put("Livraison","Bonliv");

		corresDocPrefixe.put("Facture","fa");
		corresDocPrefixe.put("Devis","de");
		corresDocPrefixe.put("Commande","co");
		corresDocPrefixe.put("Avoir","av");
		corresDocPrefixe.put("Apporteur","ap");
		corresDocPrefixe.put("Acompte","ac");
		corresDocPrefixe.put("Proforma","pr");
		corresDocPrefixe.put("Livraison","li");
	}


	/**
	 * Méthode permettant l'analyse d'une ligne tiers dans le where
	 * @param laligne la ligne à analyser
	 * @param iligne l'indice de la ligne dans le groupe
	 * @param igroupe l'indice du groupe
	 */
	private void ajoutWhereTiers(Ligne laligne,String prefixe,int iligne,int igroupe) {
		String critere = laligne.getCritere().getText();	
		String comparateur = laligne.getComparaison().getText();
		String andOr = laligne.getAndOr().getText();
		//String valeur1 = laligne.getValeur1().getText();
		String valeur1 = laligne.getValeur1().isVisible() ? laligne.getValeur1().getText() : laligne.getBooleen().getText();
		valeur1 = appliTransfo(comparateur,valeur1);
		String valeur2 = laligne.getValeur2().getText();
		boolean critereIsNull = "est vide".equals(comparateur)?true:false;

		if (!whereIsDeclared){
			wheres[igroupe] = " WHERE t"+prefixe+"."+((LigneTiers) laligne).transformSQLTiers(critere,critereIsNull)+
			" "+((LigneTiers) laligne).transformSQLcomparateur(comparateur);
//			" '"+valeur1+"'";
			wheres[igroupe]+= (laligne.getValeur1().isVisible()||laligne.getBooleen().isVisible()) ? " '"+valeur1+"'" : "";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
			whereIsDeclared = true;
		} else if (iligne==0 && whereIsDeclared){
			wheres[igroupe] += " AND t"+prefixe+"."+((LigneTiers) laligne).transformSQLTiers(critere,critereIsNull)+
			" "+((LigneTiers) laligne).transformSQLcomparateur(comparateur);
//			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur1().isVisible() ? " '"+valeur1+"'" : "";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
		} else {
			wheres[igroupe] += " "+laligne.transformSQLandOr(andOr)+
			" t"+prefixe+"."+((LigneTiers) laligne).transformSQLTiers(critere,critereIsNull)+
			" "+((LigneTiers) laligne).transformSQLcomparateur(comparateur);
//			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur1().isVisible() ? " '"+valeur1+"'" : "";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
		}
	}

	/**
	 * Méthode permettant l'analyse d'une ligne article dans le where
	 * @param laligne la ligne à analyser
	 * @param iligne l'indice de la ligne dans le groupe
	 * @param igroupe l'indice du groupe
	 */
	private void ajoutWhereArticle(Ligne laligne,String prefixe,int iligne,int igroupe) {
		String critere = laligne.getCritere().getText();	
		String comparateur = laligne.getComparaison().getText();
		String andOr = laligne.getAndOr().getText();
		String valeur1 = laligne.getValeur1().getText();
		valeur1 = appliTransfo(comparateur,valeur1);
		String valeur2 = laligne.getValeur2().getText();

		if (!whereIsDeclared){
			wheres[igroupe] = " WHERE a"+prefixe+"."+((LigneArticle) laligne).transformSQLArticle(critere)+
			" "+((LigneArticle) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
			whereIsDeclared = true;
		} else if (iligne==0 && whereIsDeclared){
			wheres[igroupe] += " AND a"+prefixe+"."+((LigneArticle) laligne).transformSQLArticle(critere)+
			" "+((LigneArticle) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
			whereIsDeclared = true;
		}else {
			wheres[igroupe] += " "+laligne.transformSQLandOr(andOr)+
			" a"+prefixe+"."+((LigneArticle) laligne).transformSQLArticle(critere)+
			" "+((LigneArticle) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
		}
	}

	/**
	 * Méthode permettant l'analyse d'une ligne document dans le where
	 * @param laligne la ligne à analyser
	 * @param iligne l'indice de la ligne dans le groupe
	 * @param igroupe l'indice du groupe
	 */
	private void ajoutWhereDocument(Ligne laligne,String type, int iligne,int igroupe) {
		String critere = laligne.getCritere().getText();	
		String comparateur = laligne.getComparaison().getText();
		String andOr = laligne.getAndOr().getText();
		String valeur1 = laligne.getValeur1().getText();
		valeur1 = appliTransfo(comparateur,valeur1);
		String valeur2 = laligne.getValeur2().getText();
		String prefixe = corresDocPrefixe.get(type);
		boolean complexe = false;

		if(critere.equals("Date Document") || critere.equals("Date Echéance")
				|| critere.equals("Date Livraison")){
			String[] temp = new String[3];
			temp = valeur1.split("/", 3);
			valeur1 = temp[2]+"-"+temp[1]+"-"+temp[0];
			if (comparateur.equals("est compris entre")){
				temp = valeur2.split("/", 3);
				valeur2 = temp[2]+"-"+temp[1]+"-"+temp[0];
			}
		}


		if (!whereIsDeclared && !critere.equals("Chiffre d'Affaires")){
			wheres[igroupe] = " WHERE "+((LigneDocument) laligne).transformSQLDoc(critere)+
			" "+((LigneDocument) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
			whereIsDeclared = true;
		} else if (!critere.equals("Chiffre d'Affaires")) {
			wheres[igroupe] += " "+laligne.transformSQLandOr(andOr)+
			" "+((LigneDocument) laligne).transformSQLDoc(critere)+
			" "+((LigneDocument) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			wheres[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
		} else if (havings[igroupe].equals(" ")){
			complexe=true;
			havings[igroupe] = " HAVING "+((LigneDocument) laligne).transformSQLDoc(critere)+
			" "+((LigneDocument) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			havings[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
		} else {
			complexe=true;
			havings[igroupe] += " "+laligne.transformSQLandOr(andOr)+
			" "+((LigneDocument) laligne).transformSQLDoc(critere)+
			" "+((LigneDocument) laligne).transformSQLcomparateur(comparateur)+
			" '"+valeur1+"'";
			havings[igroupe]+= laligne.getValeur2().isVisible() ? " AND '"+valeur2+"'" : "";
		}

		// On requiert un Group By Having, requête complexe,
		// Modification des éléments correspondants
		if(complexe){
			if(resultat.equals("Documents")){
				genereSelect("",".codeDocument",igroupe);
				genereGB("",".codeDocument",igroupe);
			} else if (resultat.equals("Tiers") && lignesDoc.size()!=0){
				genereSelect("t",".codeTiers",igroupe);
				genereGB("t",".codeTiers",igroupe);
			} else if (resultat.equals("Articles") && lignesDoc.size()!=0){
				genereSelect("a",".codeArticle",igroupe);
				genereGB("a",".codeArticle",igroupe);
			}
		}
	}

	/**
	 * Méthode de traite de la requête
	 * Effectue les Unions/Intersection sur les groupes
	 */
	private void traiteRequete() {
		List<Object> laListe = new ArrayList<Object>();		
		laListe = listeResultats.get(0);
		for(int i=1;i<listeResultats.size();i++){
			if (groupe.get(i).getAndOrGroup().getText().equals("ET")){
				laListe = ListUtils.intersection(laListe, listeResultats.get(i));
			} else {
				laListe = ListUtils.sum(laListe, listeResultats.get(i));
			}
		}
		listeFinale = laListe;

	}

	/**
	 * Méthode permettant de vérifier si des incohérences subsistent
	 * entre le résultat demandé et les critères
	 */
	private void verifIncoherences() {
		String ttl = null; // titre de la fenêtre
		String msg = null ; // message de la fenêtre
		if (lignesTiers.size() != 0 && lignesArt.size() !=0
				&& lignesDoc.size() == 0 ) {
			ttl = "Incohérence détectée";
			msg = "Les articles et les tiers sont liés par les documents.\n"
				+"Pour rendre votre recherche valide, veuillez y intégrer un critère sur les documents.";
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttl, msg);
		} else if (resultat.equals("Tiers") && lignesTiers.size() == 0) {
			ttl = "Incohérence détectée";
			msg = "Afin d'éffectuer une requête sur les tiers, veuillez renseigner au moins un critère sur ceux ci.\n";

			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttl, msg);
		} else if (resultat.equals("Articles") && lignesArt.size() == 0) {
			ttl = "Incohérence détectée";
			msg = "Afin d'éffectuer une requête sur les articles, veuillez renseigner au moins un critère sur ceux ci.\n";

			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttl, msg);
		} else if (resultat.equals("Documents") && lignesDoc.size() == 0) {
			ttl = "Incohérence détectée";
			msg = "Afin d'éffectuer une requête sur les documents, veuillez renseigner au moins un critère sur ceux ci.\n";

			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttl, msg);
		}
	}






}
