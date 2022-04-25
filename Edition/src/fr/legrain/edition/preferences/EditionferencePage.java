package fr.legrain.edition.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.edition.Activator;

public class EditionferencePage extends FieldEditorPreferencePage
								implements IWorkbenchPreferencePage {
	
	public EditionferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Paramètres éditions :");
	}

	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		addField(
				new BooleanFieldEditor(PreferenceConstants.CHOIX_FORMAT,"Choix format par defaut (PDF) -- "+
						PreferenceConstants.COMMENTAIRE,
						getFieldEditorParent()));
		
//		Label l = new Label(getFieldEditorParent(),SWT.NONE);
//		l.setText(PreferenceConstants.COMMENTAIRE);
		
		addField(new DirectoryFieldEditor(PreferenceConstants.PATH_ACROBAT_READER,
				"Répertoire de l'exécutable Acrobat Reader :",getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.PATH_SAVE_PDF,
				"Répertoire de stockage des fichiers PDF :",getFieldEditorParent()));
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}
