package fr.legrain.majcomptelegrainfr.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.majcomptelegrainfr.Activator;

public class MAJCompteLegrainFrPreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	public MAJCompteLegrainFrPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStoreProject()); 
		setDescription(PreferenceConstantsProject.DESCRIPTION);
	}

	@Override
	protected void createFieldEditors() {

		addField(
				new StringFieldEditor(PreferenceConstantsProject.PATH_SERVER_FTP, PreferenceConstantsProject.MESSAGE_PATH_SERVER_FTP, getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstantsProject.PORT_SERVER_FTP, PreferenceConstantsProject.MESSAGE_PORT_SERVER_FTP, getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstantsProject.LOGIN_SERVER_FTP, PreferenceConstantsProject.MESSAGE_LOGIN_SERVER_FTP, getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstantsProject.PASSWORD_SERVER_FTP, PreferenceConstantsProject.MESSAGE_PASSWORD_SERVER_FTP, getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstantsProject.PATH_FICHIER, "Nom du fichier", getFieldEditorParent()));		
		addField(
				new StringFieldEditor(PreferenceConstantsProject.URL, PreferenceConstantsProject.MESSAGE_URL, getFieldEditorParent()));
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
		PreferenceInitializerProject.initDefautProperties();
	}



}
