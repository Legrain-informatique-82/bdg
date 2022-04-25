package fr.legrain.libLgrMail.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.libLgrMail.LibLgrMailPlugin;

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

public class LgrMailPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public LgrMailPreferencePage() {
		super(GRID);
		setPreferenceStore(LibLgrMailPlugin.getDefault().getPreferenceStore());
		setDescription("Configuration pour l'envoie d'e-mail directement à partir du programme.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new StringFieldEditor(PreferenceConstants.P_SERVEUR_SMTP, "Serveur SMTP :", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.P_USER, "Nom d'utilisateur :", getFieldEditorParent()));

		StringFieldEditor passwordField = new StringFieldEditor(PreferenceConstants.P_PASSWORD, "Mot de passe:", getFieldEditorParent());
		passwordField.getTextControl(getFieldEditorParent()).setEchoChar('*');
		//passwordField.setEmptyStringAllowed(false);
		addField(passwordField);

		addField(
				new BooleanFieldEditor(PreferenceConstants.P_SSL, "SSL", getFieldEditorParent()));	

		addField(
				new StringFieldEditor(PreferenceConstants.P_ADRESSE_MAIL_EXP, "Expéditeur :", getFieldEditorParent()));	
		addField(
				new StringFieldEditor(PreferenceConstants.P_ADRESSE_MAIL_DEST, "Destinataire :", getFieldEditorParent()));		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(LibLgrMailPlugin.getDefault().getPreferenceStore());
	}
	
}