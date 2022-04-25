package fr.legrain.analyseeconomique.actions;

import org.apache.log4j.Logger;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.analyseeconomique.preferences.PreferenceConstants;
import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.Compte;
import fr.legrain.liasseFiscale.actions.InfosCompta;
import fr.legrain.liasseFiscale.actions.Repart;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.WizardDocumentFiscalModel;


public class ModelAnalyseEconomique extends WizardDocumentFiscalModel {
	
	static Logger logger = Logger.getLogger(ModelAnalyseEconomique.class.getName());
	
	//nom ou chemin dossier liasse fiscale
		//=> chemin fichier repartFinal.xml
	//fichier compta
	
	public void test() {
		
	}

	@Override
	public WizardDocumentFiscalModel lectureXML(String xmlFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Repart repartitionDocument() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void traitementAnalyseEco() {
		traitementAnalyseEco(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_AGENCE));
	}
	
	public void traitementAnalyseEco(String agence) {
		logger.info("Traitement des donnees pour l'analyse economique");
		String nomFichierXMLAnalyseEco = ConstAnalyseEco.C_FICHIER_XML_AE;
		//recuperation du chemin du document choisi dans le modele

		//lecture du fichier xml representant la compta
		InfosCompta infosCompta = new InfosCompta();
		infosCompta = infosCompta.lectureXML(getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_COMPTA_FINAL);
		//lecture du fichier xml representant la liasse
		Repart repart = new Repart();
		repart = repart.lectureXML(getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_REPART_FINAL);
		//creation de la fin de l'objet
		DonneesAnalyseEco donneesAnalyseEco = new DonneesAnalyseEco();
//		for (Compte compte : infosCompta.getListeCompte()) {
//		if(compte.getOrigine().equals("Bilan")) {
//		} else if(compte.getOrigine().equals("Analytique")) {
//		} else if(compte.getOrigine().equals("Stocks")) {
//		}
//		}
		InfosAnalyseEconomiqueTxtEpicea i = new InfosAnalyseEconomiqueTxtEpicea();
		i.setFichierTxt(getCheminDocument()+"/"+ConstLiasse.C_FICHIER_COMPTA_LOCAL);
		i.readTxt();
		donneesAnalyseEco = i.getDonneesAnalyseEco();
		InfosLiasse infosLiasse;
		for (Cle c : repart.getListeRepartition().keySet()) {
			if(c.getSousCle().equals("balance")) { //"balance"=>4 premieres pages de la liasse
				infosLiasse = new InfosLiasse(c,repart.getListeRepartition().get(c));
				for (Compte cpt : infosLiasse.getR().getDetail()) {
					//TODO a supprimer
					//modification plus nécessaire quand les detail seront enregistrés dans des CompteSimple et non plus de Compte
					//on met les valeurs dans xxx3 pour avoir les bonnes balises dans le fichier xml généré
					cpt.setMtDebit3(cpt.getMtDebit());
					cpt.setMtCredit3(cpt.getMtCredit());
					//on efface les valeur
					cpt.setMtDebit(cpt.getMtDebit2());
					cpt.setMtCredit(cpt.getMtCredit2());
				}
				donneesAnalyseEco.getListeInfosLiasse().add(infosLiasse);
			} else if(c.getSousCle().equals("")) {

			}
		}
		donneesAnalyseEco.setAgence(agence);
		//mapping xml
		logger.info("Generation du fichier de l'analyse economique");
		donneesAnalyseEco.sortieXML(getCheminDocument()+"/"+nomFichierXMLAnalyseEco);
		logger.info("Fichier de l'analyse economique termine");
	}
}
