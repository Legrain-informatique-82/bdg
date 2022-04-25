package fr.legrain.liasseFiscale.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import fr.legrain.liasseFiscale.LiasseFiscalePlugin;

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

public class LiasseFiscaleCouleurPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public LiasseFiscaleCouleurPreferencePage() {
		super(GRID);
		setPreferenceStore(LiasseFiscalePlugin.getDefault().getPreferenceStore());
		//setDescription("Liasse fiscale");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		Group grp = new Group(getFieldEditorParent(),SWT.NONE);
		grp.setText("Couleurs des cellules");
		
		GridData gridData = new GridData();
	    gridData.horizontalAlignment = GridData.FILL;
	    gridData.grabExcessHorizontalSpace = true;
	    grp.setLayoutData(gridData);
	    
		addField(new ColorFieldEditor(PreferenceConstants.P_COULEUR_A_SAISIR, "Cellule à saisir", grp));
		addField(new ColorFieldEditor(PreferenceConstants.P_COULEUR_SAISIE, "Cellule saisie manuellement", grp));
		addField(new ColorFieldEditor(PreferenceConstants.P_COULEUR_CALCULS, "Cellule remplie automatiquement", grp));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}