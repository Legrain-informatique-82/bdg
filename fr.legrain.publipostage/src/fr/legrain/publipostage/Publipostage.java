package fr.legrain.publipostage;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import fr.legrain.publipostage.divers.ParamPublipostage;

public abstract class Publipostage {

	static Logger logger = Logger.getLogger(Publipostage.class.getName());

	protected List<ParamPublipostage> listeParamPublipostages = new LinkedList<ParamPublipostage>();

	protected String nomFichierFinalFusionne = null;
	
	protected boolean supprimerFichierIntermediaires = false;

	public Publipostage(List<ParamPublipostage> listeParamPublipostages) {
		this.listeParamPublipostages = listeParamPublipostages;
	}

	public void lectureDonnees() {
		for (ParamPublipostage param : listeParamPublipostages) {
			lectureDonnees(param);
		}
	}

	public void lectureDonnees(ParamPublipostage param) {
		param.intialiseDonnees();
		param.verityFileExtractionAndModelLettre();
	}

	/**
	 * 
	 * @param src ==> model office
	 * @param dest ==> file office de publipostage
	 */
	public void copyModelOffice(ParamPublipostage param){

		Calendar calendar = Calendar.getInstance();
		String nameFileOffice = String.valueOf(calendar.getTimeInMillis());

		String nameFile = null;
		String typeFile = null;

		int positionLastPoint = param.getCheminFichierModelLettre().lastIndexOf(".");
		if(positionLastPoint != -1){
			nameFile = param.getCheminFichierModelLettre().substring(0, positionLastPoint);
			typeFile = param.getCheminFichierModelLettre().substring(positionLastPoint);
		}

		param.setCheminFichierFinal(param.getCheminRepertoireFinal()+"/"+nameFileOffice+typeFile);
		File fileScr = new File(param.getCheminFichierModelLettre());
		File fileDest = new File(param.getCheminFichierFinal());
		//FileUtils fileUtils = new FileUtils();
		try {
			FileUtils.copyFile(fileScr, fileDest);
		} catch (IOException e) {
			logger.error("",e);
		}

	}

	public abstract void initTraitementDeTexte();

	public abstract void fermeTraitementDeTexte();

	public void publipostages() {
		lectureDonnees();
		for (ParamPublipostage param : listeParamPublipostages) {
			publipostage(param);
			enregistrePublipostage(param);
		}
	
		fusionnePublipostage(listeParamPublipostages);
		
		if(supprimerFichierIntermediaires) {
			File fichierASupprimer = null;
			for (ParamPublipostage param : listeParamPublipostages) {
				fichierASupprimer = new File(param.getCheminFichierFinal());
				if(fichierASupprimer.exists()) {
					fichierASupprimer.delete();
				}
			}
		}
	}



	public abstract void publipostage(ParamPublipostage param);

	public abstract void publipostages(String nomFichierFinalFusionne);

	public void remplaceChamp(ParamPublipostage param) {
		String valueRemplacement = "";
		String motCleLettre = "";

		//on parcours les lignes du fichier de données à partir de la 2eme
		int count = 0;
		for (String marqueurGroupe : param.getDetails().keySet()) {

			ajoutPageModeleTraitementDeTexte(param,count);
			count++;
			if(param.isAfficheDetail()) {
				remplaceChampDetail(param,marqueurGroupe,count);
			}else{
				
			}

			String[] arrayValuesChamp = param.getDetails().get(marqueurGroupe).getFirst();
			for (int j = 0; j < arrayValuesChamp.length; j++) {
				//pour chaque valeur de la ligne
				if(param.getMotCleLettreEtPostion().containsKey(j)){
					motCleLettre = param.getMotCleLettreEtPostion().get(j);
					valueRemplacement = arrayValuesChamp[j];

					repositionneCurseurAvantRecherche(count);
					if(param.getListeValeurNonAffichable().contains(valueRemplacement)) {
						valueRemplacement = "";
					}
					remplaceChampTraitementDeTexte(param,motCleLettre,valueRemplacement);
				}
			}

		}
	}
	
	public abstract void repositionneCurseurAvantRecherche(int indexTable);

	public void remplaceChampDetail(ParamPublipostage param, String marqueurGroupe ,int indexTable) {
		remplaceChampDetailTraitementDeTexte(param, marqueurGroupe,indexTable);
	}

	public abstract void remplaceChampDetailTraitementDeTexte(ParamPublipostage param, String marqueurGroupe,int indexTable);

	public abstract void remplaceChampTraitementDeTexte(ParamPublipostage param, String valeurRecherche, String nouvelleValeur);

	public abstract void ajoutPageModeleTraitementDeTexte(ParamPublipostage param,int positionPage);

	public abstract void enregistrePublipostage(ParamPublipostage param);

	public abstract void fusionnePublipostage(List<ParamPublipostage> listePublipostage);

	public void nettoyerPublipostage () {

		for (ParamPublipostage param : listeParamPublipostages) {
			new File(param.getCheminFichierFinal()).delete();
		}
	}

	public List<ParamPublipostage> getListeParamPublipostages() {
		return listeParamPublipostages;
	}

	public void setListeParamPublipostages(
			List<ParamPublipostage> listeParamPublipostages) {
		this.listeParamPublipostages = listeParamPublipostages;
	}

	public String getNomFichierFinalFusionne() {
		return nomFichierFinalFusionne;
	}

	public void setNomFichierFinalFusionne(String nomFichierFinalFusionne) {
		this.nomFichierFinalFusionne = nomFichierFinalFusionne;
	}

	public boolean isSupprimerFichierIntermediaires() {
		return supprimerFichierIntermediaires;
	}

	public void setSupprimerFichierIntermediaires(
			boolean supprimerFichierIntermediaires) {
		this.supprimerFichierIntermediaires = supprimerFichierIntermediaires;
	}

}

