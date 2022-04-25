/**
 * PaFormPageOngletDevis.java						03/05/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.statistiques.ecrans.a_supprimer;

import org.eclipse.ui.forms.editor.FormEditor;

import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPageCommandes extends PaFormPage {
	
	public static String id = "fr.legrain.statistiques.ecrans.PaFormPageOngletCommandes";
	public static String title = "Commandes";

	public PaFormPageCommandes(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}


	@Override
	public void createSections() {
		/* Section paramètres : identique @super */
		createSectionParam(toolkit,form);
		
		/* Section montant */
		createSectionMontant(toolkit,form);
		sctnMontant.setText("Montant des Bons de Commande HT");
		compositeSectionMontant.getInfolabel().setText("Montant des Bons de Commande HT sur la période");
		
		/* Section documents */
		createSectionDoc(toolkit, form);
		sctnDocuments.setText("Transformation des Bons de Commande");
		compositeSectionDoc.getLblNew1().setText("En Factures: ");
		compositeSectionDoc.getLblNew2().setText("En Bons de Livraison : ");
		compositeSectionDoc.getLblNew3().setText("Bons de commande non transformés : ");
		
		/* Section Jauge */
		createSectionJauge(toolkit, form);
		sctnJauge.setText("Suivi des Bons de Commande");
		compositeSectionJauge.getLblNew1().setText("Nombre de bons de commande non transformés : ");
		compositeSectionJauge.getLblNew2().setText("Montant des bons de commande non transformés : ");
		
		/* Section Graph */
		createSectionEvolution(toolkit, form);
		sctnEvolutionDuChiffre.setText("Evolution bons commande transformés et non transformés");
		
		/* Section Repartition */
		createSectionRepartition(toolkit, form);
		sctnRepartition.setText("Répartition des bons de commande sur la période");
		
		/* Section Tableaux */
		createSectionTableauGauche(toolkit, form);
		createSectionTableauDroit(toolkit, form);
		sctnTableauGauche.setText("Bons de commande transformés sur la période");
		sctnTableauDroit.setText("Bons de commande non transformés sur la période");
		
		
	}
	
	




}
