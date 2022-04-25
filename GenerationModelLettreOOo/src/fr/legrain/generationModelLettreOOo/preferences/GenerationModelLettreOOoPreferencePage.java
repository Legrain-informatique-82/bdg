package fr.legrain.generationModelLettreOOo.preferences;


import generationmodellettreooo.Activator;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GenerationModelLettreOOoPreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	
	public GenerationModelLettreOOoPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(PreferenceConstants.DESCRIPTION_WO);
	}

	public GenerationModelLettreOOoPreferencePage(int style) {
		super(style);
	}

	public GenerationModelLettreOOoPreferencePage(String title, ImageDescriptor image,
			int style) {
		super(title,image,style);
	}
	public GenerationModelLettreOOoPreferencePage(String title, int style) {
		super(title,style);
	}

	@Override
	protected void createFieldEditors() {
		addField(
				new StringFieldEditor(PreferenceConstants.PORT_SERVER_OPEN_OFFICE,PreferenceConstants.MESSAGE_PORT_SERVER_OPEN_OFFICE,
						getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.PATH_OPEN_OFFICE,
				"Répertoire de l'exécutable Open Office :",getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.PATH_SAVE_PUBLIPOSTAGE_OO,
				"Répertoire de stockage des publipostages :",getFieldEditorParent()));
//		addField(new BooleanFieldEditor(PreferenceConstants.OPTION_AFFICHAGE,
//				"Option Affichage",getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
