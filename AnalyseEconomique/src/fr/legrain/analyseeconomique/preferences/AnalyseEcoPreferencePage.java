package fr.legrain.analyseeconomique.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.analyseeconomique.actions.ConstAnalyseEco;

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

public class AnalyseEcoPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public AnalyseEcoPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		//setDescription("A demonstration of a preference page implementation");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		
		addField(new DirectoryFieldEditor(PreferenceConstants.P_CHEMIN_EXPORT_COMPTA_LIASSE, "Chemin repertoire export comptabilité : ", getFieldEditorParent()));

//		addField(
//			new StringFieldEditor(PreferenceConstants.P_SERVEUR_ANALYSE_ECO, "Adresse du serveur :", getFieldEditorParent()));
		
		addField(
				new BooleanFieldEditor(PreferenceConstants.P_OUVERTURE_SERVEUR_AUTO, "Ouverture automatique du site", getFieldEditorParent()));
		
		addField(
				new BooleanFieldEditor(PreferenceConstants.P_ENVOI_FTP_AUTO, "Transfert automatique sur le serveur", getFieldEditorParent()));
		
		addField(
				new StringFieldEditor(PreferenceConstants.P_CODE_COMPTABLE_SERVEUR, "Code comptable sur le serveur :", getFieldEditorParent()));
		
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_LOGIN_COMPTABLE_SERVEUR, "Login comptable sur le serveur :", getFieldEditorParent()));
//		
		addField(
				new StringFieldEditor(PreferenceConstants.P_PASSWORD_COMPTABLE_SERVEUR, "Mot de passe comptable sur le serveur :", getFieldEditorParent()));
		       
		final ComboFieldEditor registrationPeriod = new ComboFieldEditor(PreferenceConstants.P_AGENCE, "Agence :", ConstAnalyseEco.C_LISTE_AGENCE, getFieldEditorParent());
		addField(registrationPeriod);
		
//		
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_SERVEUR_FTP, "Serveur FTP :", getFieldEditorParent()));
//		
//		addField(
//				new StringFieldEditor(PreferenceConstants.P_LOGIN_FTP, "Identifiant FTP :", getFieldEditorParent()));
//		
//		StringFieldEditor passwordField = new StringFieldEditor(PreferenceConstants.P_PASSWORD_FTP, "Mot de Passe FTP :", getFieldEditorParent());
//		passwordField.getTextControl(getFieldEditorParent()).setEchoChar('*');
//		//passwordField.setEmptyStringAllowed(false);
//		addField(passwordField);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}