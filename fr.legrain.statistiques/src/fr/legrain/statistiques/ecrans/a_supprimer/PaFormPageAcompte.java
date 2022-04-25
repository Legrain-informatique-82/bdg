/**
 * PaFormPageAcompte.java						03/05/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.statistiques.ecrans.a_supprimer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPageAcompte extends PaFormPage {
	
	public static String id = "fr.legrain.statistiques.ecrans.PaFormPageOngletAcompte";
	public static String title = "Acomptes";

	public PaFormPageAcompte(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}


	@Override
	public void createSections() {
		/* Section paramètres : identique @super */
		createSectionParam(toolkit,form);
		
		/* Section montant */
		createSectionMontant(toolkit,form);
		sctnMontant.setText("Montant des Factures d'Acompte HT");
		sctnMontant.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));
		compositeSectionMontant.getInfolabel().setText("Montant des Factures d'acompte HT sur la période");
		
		/* Section documents */
		createSectionDoc(toolkit, form);
		sctnDocuments.setText("Transformation des Factures d'Acompte");
		compositeSectionDoc.getLblNew1().setText("Aucune Transformation pour les factures d'acompte.");
		compositeSectionDoc.getLblNew2().setText("");
		compositeSectionDoc.getLblNew3().setText("");
//		
//		/* Section Jauge */
//		createSectionJauge(toolkit, form);
//		sctnJauge.setText("Suivi des Bons de Commande");
//		compositeSectionJauge.getLblNew1().setText("Nombre de bons de commande non transformés : ");
//		compositeSectionJauge.getLblNew2().setText("Montant des bons de commande non transformés : ");
//		
		/* Section Graph */
		createSectionEvolution(toolkit, form);
		GridData gd_evolution = new GridData(SWT.FILL,SWT.FILL,true,true,4,1);
		gd_evolution.minimumHeight = 300;
		gd_evolution.heightHint = 300;
		sctnEvolutionDuChiffre.setText("Evolution des factures d'acompte sur la période");
		sctnEvolutionDuChiffre.setLayoutData(gd_evolution);
		
		/* Section Repartition */
		createSectionRepartition(toolkit, form);
		sctnRepartition.setText("Répartition des factures d'acompte sur la période");
		
		/* Section Tableaux */
		createSectionTableauGauche(toolkit, form);
		createSectionTableauDroit(toolkit, form);
		sctnTableauGauche.setText("Tiers classés par montant d'acomptes");
		sctnTableauDroit.setText("Articles classés par montant dans les acomptes");
		
		
	}
	
	




}
