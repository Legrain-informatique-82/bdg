package fr.legrain.gestionCommerciale.preferences;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.gestionCommerciale.divers.ListEditorGestionAlias;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.libMessageLGR.LgrMess;


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

public class GestionCommercialePreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public GestionCommercialePreferencePage() {
		super(GRID);
		setPreferenceStore(GestionCommercialePlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres de gestion des dossiers.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		Realm realm = SWTObservables.getRealm(getFieldEditorParent().getDisplay());

		ListEditorGestionAlias listeAlias = new ListEditorGestionAlias(
				PreferenceConstants.ALIAS,"Liste des alias","Ajouter",
				"Supprimer",getFieldEditorParent(),realm);
		addField(listeAlias);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(GestionCommercialePlugin.getDefault().getPreferenceStore());
	}


	@Override
	protected void performApply() {
		// TODO Auto-generated method stub
		super.performApply();
//		LgrMess.setAfficheAideRemplie(GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.TYPE_AFFICHAGE_AIDE));
		LgrMess.setAfficheAideRemplie(false);
		UtilWorkspace util = new UtilWorkspace();
		IProject projetCourant=util.findOpenProject();
		String preferences=GestionCommercialePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.ALIAS);
		if(preferences.contains(","+projetCourant.getName()+",")){
			String[] listeAlias=preferences.split(";");
			for (int i = 0; i < listeAlias.length; i++) {
				if(listeAlias[i].contains(","+projetCourant.getName()+",")){
					if(listeAlias[i].split(",").length>=3){
						String reseau =listeAlias[i].split(",")[3];
						LgrMess.setDOSSIER_EN_RESEAU(LibConversion.StringToBoolean(reseau));
					}
				}
			}
		}		

	}		
	
}