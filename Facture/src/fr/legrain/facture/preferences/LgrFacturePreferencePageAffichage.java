package fr.legrain.facture.preferences;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.ui.IWorkbench;

import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.facture.FacturePlugin;

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

public class LgrFacturePreferencePageAffichage extends FieldEditorPreferencePageLGR{
	
//	private RadioGroupFieldEditor radioGroupChoixDestinataire = null;
//	private String pathEditionFragement = null;
//	private String pathEditionSpecifiquesClient = null;
//	
//	private ListEditorAddRemoveEditLgr listeTypeTiers = null;
//	
//
//
//	static private PropertiesConfiguration listeGestCode = new PropertiesConfiguration();
	 static Logger logger = Logger.getLogger(LgrFacturePreferencePageAffichage.class.getName());
	  
	public LgrFacturePreferencePageAffichage() {
		super(GRID);
		//String idPlugin = FacturePlugin.getDefault().getBundle().getSymbolicName();
		setPreferenceStore(FacturePlugin.getDefault().getPreferenceStore());

//		setDescription("Paramètres de codification des factures");
		
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new BooleanFieldEditor(PreferenceConstants.IMPRIMER_AUTO, "Impression automatique", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT, "Impression automatique des courriers", getFieldEditorParent()));
//		addField(
//				new BooleanFieldEditor(PreferenceConstants.AFFECTATION_STRICTE, "Affectation stricte", getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(PreferenceConstants.AFF_AFFECTATION_ACOMPTE, "Affichage des affectations des acomptes", getFieldEditorParent()));
	}		
	@Override
	protected void performApply() {
		super.performApply();	
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		setPreferenceStore(FacturePlugin.getDefault().getPreferenceStore()); 
	}

}