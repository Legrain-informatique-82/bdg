package fr.legrain.openmail.mail.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.openmail.mail.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class OpenMailPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public OpenMailPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
		setDescription("Compte OpenMail");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceConstantsProject.USE_OPENMAIL, "Utiliser le service OpenMail pour l'envoie d'email", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstantsProject.LOGIN, "Login", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstantsProject.PASSWORD, "Password", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstantsProject.EXPEDITEUR_EMAIL, "Expéditeur email", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstantsProject.REPONDRE_A_EMAIL, "Adresse réponse email", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstantsProject.EXPEDITEUR_SMS, "Expéditeur SMS", getFieldEditorParent()));
		
		addField(new StringFieldEditor(PreferenceConstantsProject.TYPE_TELEPHONE_SMS, "Type téléphone SMS", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStoreProject());
	}
	
}