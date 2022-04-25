package fr.legrain.publipostage.msword;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import fr.legrain.publipostage.Publipostage;
import fr.legrain.publipostage.divers.ParamPublipostage;

/**
 * Publipostage Microsoft Word. Fonctionne uniquement sur Windows (COM,DLL,JNI).<br>
 * Limitations :<br>
 * <li>Le style des lignes de détail est perdu pendant la génération du document :<br>
 * 		le remplacement des mot clé ce fait en Java et non avec les fonction Word, <br>
 * 		car on ne peu pas rechercher/remplacer uniquement sur une sélection).<br>
 * <li>Le modèle de document ne doit pas contenir de table (sauf pour les lignes de détail) :<br>
 * 		On accède au table par leur index/position dans le document<br>
 *
 */
public class PublipostageMsWord extends Publipostage {
	
	private WordBean wordBean = new WordBean();
	
	public PublipostageMsWord(List<ParamPublipostage> listeParamPublipostages) {
		super(listeParamPublipostages);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enregistrePublipostage(ParamPublipostage param) {
		wordBean.save(new File(param.getCheminFichierFinal()).getAbsolutePath());
	}

	@Override
	public void fermeTraitementDeTexte() {
		// TODO Auto-generated method stub
		wordBean.close();
	}

	@Override
	public void initTraitementDeTexte() {
		// TODO Auto-generated method stub

	}

	@Override
	public void publipostage(ParamPublipostage param) {
//		lectureDonnees(param);
//		copyModelOffice(param);
		//wordBean.openDocument(param.getCheminFichierFinal());
		wordBean.createNewDocument();
		remplaceChamp(param);
		
		
	}
	
	public void OuvreDocument(String fichier) {
		wordBean.openDocument(fichier);
	}
	
	public void remplaceChampTraitementDeTexte(ParamPublipostage param, String valeurRecherche, String nouvelleValeur) {
//		wordBean.replaceAllText(valeurRecherche, nouvelleValeur);
		wordBean.replaceAllTextBalise(valeurRecherche, nouvelleValeur);
	}
	
	public void ajoutPageModeleTraitementDeTexte(ParamPublipostage param,int positionPage) {
		if(positionPage != 0){
			wordBean.pageBreak();
		}
		wordBean.insertPageWord(param.getCheminFichierModelLettre(),true);
	}

	@Override
	public void fusionnePublipostage(List<ParamPublipostage> listePublipostage) {
		wordBean.createNewDocument();
		int positionPage = 0;
		for (ParamPublipostage param : listePublipostage) {
			if(positionPage != 0){
				wordBean.pageBreak();
			}
			wordBean.insertPageWord(new File(param.getCheminFichierFinal()).getAbsolutePath(),true);
			positionPage++;
		}
		wordBean.save(new File(listePublipostage.get(0).getCheminRepertoireFinal()+"\\"+getNomFichierFinalFusionne()).getAbsolutePath());
		wordBean.moveStart();
	}

	@Override
	public void publipostages(String nomFichierFinal) {
	}

	@Override
	public void remplaceChampDetailTraitementDeTexte(ParamPublipostage param, String marqueurGroupe,int indexTable) {
//		remplaceDetailTable(param, marqueurGroupe,indexTable);
		remplaceDetailBalise(param, marqueurGroupe,indexTable);
	}
	
	/**
	 * Pour utiliser cette fonction, le modèle doit contenir une table avec une seule ligne 
	 * et une seule colonne, contenant le modèle de texte pour les lignes de détails. 
	 * S'il n'y a pas de table dans le modèle, la fonction ne marche pas.
	 * @param param
	 * @param marqueurGroupe
	 * @param indexTable
	 */
	public void remplaceDetailTable(ParamPublipostage param, String marqueurGroupe,int indexTable) {
		String motCleLigneDetail = "";
		String valueRmplace = "";
		String ligneFinale = "";
		
		String texteModele = wordBean.getTextTable(indexTable, 1, 1);
		if(texteModele.indexOf("\r")!=-1)
			texteModele = texteModele.substring(0, texteModele.lastIndexOf("\r")); 
		
		int count = 1;
		for (String[] detail : param.getDetails().get(marqueurGroupe)) {
			ligneFinale = texteModele;
			for (int j = 0; j < detail.length; j++) {
				if(param.getMotCleLettreEtPostion().containsKey(j)){
					motCleLigneDetail = param.getMotCleLettreEtPostion().get(j);
					if(param.getMotCleLettreEtPostion().containsKey(j)){
						valueRmplace = detail[j];
					}

					ligneFinale = ligneFinale.replaceAll(motCleLigneDetail, valueRmplace);
					
				}
			}
			if(count != 1){
				wordBean.addRow(indexTable);
			}
			wordBean.putTxtToCell(indexTable, count, 1, ligneFinale);
			count++;
		}
		
		if(indexTable == 1 ){
			wordBean.moveStart();
		}else{
			wordBean.moveToPreviousTable();
		}		
	}
	
	/**
	 * Pour utiliser cette fonction, le modèle doit contenir 2 balises : une ouvrante et une fermante 
	 * définies dans l'objet <code>param</code>. Entre ces 2 balises ont doit avoir le modèle de texte pour les lignes de détails. 
	 * S'il n'y a pas ces 2 balises, la fonction ne marche pas.
	 * @param param
	 * @param marqueurGroupe
	 * @param indexTable
	 */
	public void remplaceDetailBalise(ParamPublipostage param, String marqueurGroupe,int indexTable) {
		boolean tableOuTexte = true; //si vrai table sinon texte

		String motCleLigneDetail = "";
		String valueRmplace = "";
		String ligneFinale = "";

		boolean baliseDetailTrouve = wordBean.findMoveWildCard(param.getBaliseDebutDetail()+"*"+param.getBaliseFinDetail());
		if(baliseDetailTrouve) {
			wordBean.moveStart(-1);
			wordBean.moveEnd(1);
			//		
			String texteModele = wordBean.getSelectionText();
			texteModele = texteModele.substring(param.getBaliseDebutDetail().length());
			texteModele = texteModele.substring(0,texteModele.lastIndexOf(param.getBaliseFinDetail()));

			if(texteModele.endsWith("\r"))
				texteModele = texteModele.substring(0, texteModele.lastIndexOf("\r")); 
			if(texteModele.startsWith("\r"))
				texteModele = texteModele.replaceFirst("\r","");

			if(tableOuTexte) { //table
				wordBean.createTable(String.valueOf(indexTable), 1, 1);
			}

			int count = 1;
			for (String[] detail : param.getDetails().get(marqueurGroupe)) {
				ligneFinale = texteModele;
				for (int j = 0; j < detail.length; j++) {
					if(param.getMotCleLettreEtPostion().containsKey(j)){
						motCleLigneDetail = param.getMotCleLettreEtPostion().get(j);
						if(param.getMotCleLettreEtPostion().containsKey(j)){
							valueRmplace = detail[j];
						}

						ligneFinale = ligneFinale.replaceAll(motCleLigneDetail, valueRmplace);

					}
				}

				if(tableOuTexte) { //table
					if(count != 1){
						wordBean.addRow(indexTable);
					}
					wordBean.putTxtToCell(indexTable, count, 1, ligneFinale);
				} else { //texte
					wordBean.insertText(ligneFinale);
					wordBean.moveEnd(1);
				}

				count++;
			}
			if(indexTable == 1 ){
				wordBean.moveStart();
			}else{
				wordBean.moveToPreviousTable();
			}

			if(!tableOuTexte) { //texte
				wordBean.moveStart();
			}
		} else {
			System.err.println("Pas de balise détail dans ce document");
		}
	}

//	@Override
	public void insereLigneDetail(int indexTable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repositionneCurseurAvantRecherche(int indexTable) {
		if(indexTable == 1 ){
			wordBean.moveStart();
		}else{
			wordBean.moveToPreviousTable();
		}
		
	}

}
