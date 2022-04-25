package fr.legrain.exportation.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.exportation.ExportationPlugin;

public class ExportationPreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	public ExportationPreferencePage() {
		super(GRID);
//		setPreferenceStore(ExportationPlugin.getDefault().getPreferenceStore());
		setPreferenceStore(ExportationPlugin.getDefault().getPreferenceStoreProject()); 
		setDescription("Paramètres de l'exportation.");
	}

	@Override
	protected void createFieldEditors() {

		addField(
				new DirectoryFieldEditor(PreferenceConstants.REPERTOIRE_EXPORTATION, "Répertoire d'exportation", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFFICHAGE_CTRL_ESPACE, "Affichage du (Ctrl+Espace)."+"\n"+
						"Cela peut engendrer des ralentissements sur les dossiers volumineux.", getFieldEditorParent()));
		
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.TYPE_REGLEMENT, "Gérer tous les types de réglements", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.ACOMPTES, "Gérer les acomptes", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.REGLEMENT_SIMPLE, "Gérer les réglements simples", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.REMISE, "Gérer les remises", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.REGLEMENTS_LIES, "Transférer les réglements liés au document", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.DOCUMENTS_LIES, "Transférer les documents liés au réglement", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.POINTAGES, "Transférer les pointages", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.CENTRALISATION, "Centraliser les écritures", getFieldEditorParent()));		
	    //Tous les types de réglements
		//Acomptes
		//Réglement simple
		//Remise
//		Transférer les réglements liés au document
//		Transférer les documents liés au réglement
//		Transférer les pointages
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		setPreferenceStore(ExportationPlugin.getDefault().getPreferenceStoreProject()); 
	}



}
