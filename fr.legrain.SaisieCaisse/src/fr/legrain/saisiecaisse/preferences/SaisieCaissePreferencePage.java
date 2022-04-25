package fr.legrain.saisiecaisse.preferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.saisiecaisse.saisieCaissePlugin;


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

public class SaisieCaissePreferencePage	extends FieldEditorPreferencePageLGR
	implements IWorkbenchPreferencePage {
	
	static Logger logger = Logger.getLogger(SaisieCaissePreferencePage.class.getName());
	ConstEdition constEdition ;
		
	public SaisieCaissePreferencePage() {
		super(GRID);
		constEdition = new ConstEdition();
		this.setFolderEdition(constEdition.FOLDER_EDITION);
		this.setFolderEditionClients(constEdition.FOLDER_EDITION_CLIENT);
		this.setFolderEditionReportPlugin(constEdition.FICHE_FILE_ETAT_SAISIECAISSE_JOUR);
		this.setNamePlugin(saisieCaissePlugin.PLUGIN_ID);
		reportPlugin =ConstEdition.pathRepertoireEditionsSpecifiques()+"//"+saisieCaissePlugin.getDefault().getBundle().getSymbolicName();
		reportPluginClients=ConstEdition.pathRepertoireEditionsSpecifiquesClient()+"//"+saisieCaissePlugin.getDefault().getBundle().getSymbolicName();
		
		setPreferenceStore(saisieCaissePlugin.getDefault().getPreferenceStore());
		setDescription("Paramètrage de la saisie de caisse");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new StringFieldEditor(PreferenceConstants.COMPTE_ACHAT, "Compte d'achat à exporter", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.FIXE_OP_ACHAT, "Début fixe type achat", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TIERS, "compte de Tiers à exporter", getFieldEditorParent()));
		addField(
				new IntegerFieldEditor(PreferenceConstants.FOCUS_INSERTION, "Positionnement du focus", getFieldEditorParent()));
		createFieldParamEdition();

	}


	@Override
	protected void performApply() {
		super.performApply();
	}	
	
	 @Override
		protected void performDefaults() {
			PreferenceInitializer.initDefautProperties();
			super.performDefaults();
		}
	 
	 

}