package fr.legrain.generationModelLettreWS.preferences;

import generationmodellettrems.Activator;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GenerationModelLettreWSPreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	
	public GenerationModelLettreWSPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(PreferenceConstants.DESCRIPTION_WO);
	}

	public GenerationModelLettreWSPreferencePage(int style) {
		super(style);
	}

	public GenerationModelLettreWSPreferencePage(String title, ImageDescriptor image,
			int style) {
		super(title,image,style);
	}
	public GenerationModelLettreWSPreferencePage(String title, int style) {
		super(title,style);
	}

	@Override
	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor(PreferenceConstants.PATH_WORD_OFFICE,
				"Répertoire de l'exécutable Microsoft Office :",getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.PATH_SAVE_PUBLIPOSTAGE_WO,
				"Répertoire de stockage des publipostages :",getFieldEditorParent()));
//		addField(new BooleanFieldEditor(PreferenceConstants.OPTION_AFFICHAGE,
//				"Imprimer Directement",getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}

}
