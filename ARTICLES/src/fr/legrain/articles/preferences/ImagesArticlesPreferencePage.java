package fr.legrain.articles.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;


import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.boutique.dao.TaSynchroBoutique;
import fr.legrain.boutique.dao.TaSynchroBoutiqueDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.preferences.LgrRadioGroupFieldEditor;


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

public class ImagesArticlesPreferencePage
extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	final private List<FieldEditor> listeCompoTout = new ArrayList<FieldEditor>();

	final private static String LIBELLE_STOCKAGE_IMAGES_ORIGINE = "Laisser les images à leur emplacement d'origine";
	final private static String LIBELLE_STOCKAGE_IMAGES_COPIE_DOSSIER_BDG = "Copier les images dans le dossier";
	final private static String LIBELLE_STOCKAGE_IMAGES_COPIE_REPERTOIRE = "Copier les images à l'emplacement indiqué";

	public ImagesArticlesPreferencePage() {
		super(GRID);
		setPreferenceStore(ArticlesPlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres de stockage des images");
	}

	private void initEtat(Button b) {
		System.out.println(b.getText()+" "+b.getSelection());
		if(b.getText().equals(LIBELLE_STOCKAGE_IMAGES_COPIE_REPERTOIRE)
				&& b.getSelection()) {
			enableList(true);
		} else {
			enableList(false);
		}
	}

	private void enableList(boolean enabled) {
		for (FieldEditor fieldEditor : listeCompoTout) {
			fieldEditor.setEnabled(enabled, getFieldEditorParent());
		}
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {

		LgrRadioGroupFieldEditor radioStockageImage = new LgrRadioGroupFieldEditor(PreferenceConstants.STOCKAGE_IMAGES,
				"Stockage des images",1,new String[][] {
				{LIBELLE_STOCKAGE_IMAGES_ORIGINE, PreferenceConstants.VALEUR_STOCKAGE_IMAGES_ORIGINE},
				{LIBELLE_STOCKAGE_IMAGES_COPIE_DOSSIER_BDG, PreferenceConstants.VALEUR_STOCKAGE_IMAGES_COPIE_DOSSIER_BDG},
				{LIBELLE_STOCKAGE_IMAGES_COPIE_REPERTOIRE, PreferenceConstants.VALEUR_STOCKAGE_IMAGES_COPIE_REPERTOIRE}
		},getFieldEditorParent(),true);
		addField(radioStockageImage);

		final Button b[] = radioStockageImage.getRadioButton(getFieldEditorParent());
		for (int i = 0; i < b.length; i++) {
			b[i].addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					initEtat((Button)e.getSource());
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}
			});
		}

		DirectoryFieldEditor strRepStockageImage = new DirectoryFieldEditor(PreferenceConstants.REPERTOIRE_STOCKAGE_IMAGES, "Répertoire de stockage", getFieldEditorParent());
		addField(strRepStockageImage);
		listeCompoTout.add(strRepStockageImage);
		
//		addField(new BooleanFieldEditor(PreferenceConstants.RETAILLE_IMAGE_TROP_GROSSE_EXPORT, "Généré une image plus petite à l'enregistrement", getFieldEditorParent()));
//		addField(new StringFieldEditor(PreferenceConstants.LONGUEUR_MAX_IMAGE, "Longueur maximum", getFieldEditorParent()));
//		addField(new StringFieldEditor(PreferenceConstants.HAUTEUR_MAX_IMAGE, "Hauteur maximum", getFieldEditorParent()));
//		addField(new StringFieldEditor(PreferenceConstants.POIDS_MAX_IMAGE, "Taille maximum (en Ko)", getFieldEditorParent()));

		if(!getPreferenceStore().getString(PreferenceConstants.STOCKAGE_IMAGES).equals(PreferenceConstants.VALEUR_STOCKAGE_IMAGES_COPIE_REPERTOIRE))
			enableList(false);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(ArticlesPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public boolean performOk() {
		//		if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.UTILISE_BOUTIQUE)) {
		//			System.err.println("activation boutique");
		//			BoutiqueInitializer bi = new BoutiqueInitializer();
		//			bi.initialize();
		//		}
		return super.performOk();
	}
}