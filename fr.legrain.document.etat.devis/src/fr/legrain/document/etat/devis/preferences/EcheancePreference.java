package fr.legrain.document.etat.devis.preferences;

import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.document.etat.devis.Activator;
import fr.legrain.document.etat.devis.controllers.ParamControllerMini;
import fr.legrain.documents.dao.TaEtat;
import fr.legrain.documents.dao.TaEtatDAO;

public class EcheancePreference extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {
	
	static public String[][] getListeEtat() {
		TaEtatDAO dao = new TaEtatDAO();
		List<TaEtat> listeEtat =  dao.selectAll();
		
		String[][] listeEtatCombo = new String[listeEtat.size()+1][2];
		
		listeEtatCombo[0][0]=ParamControllerMini.etatEnCoursLibelle;
		listeEtatCombo[0][1]=ParamControllerMini.etatEnCoursCode;
		
		int i = 1;
		for (TaEtat e : listeEtat) {
			listeEtatCombo[i][0]=e.getLibEtat();
			listeEtatCombo[i][1]=e.getCodeEtat();
			i++;
		}
		return listeEtatCombo;
	}

	public EcheancePreference() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {

		
		addField(
				new BooleanFieldEditor(PreferenceConstants.P_AFFICHAGE_AU_LANCEMENT, "Afficher au lancement", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}
