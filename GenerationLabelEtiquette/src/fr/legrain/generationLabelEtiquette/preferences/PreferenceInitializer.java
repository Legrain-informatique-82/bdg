package fr.legrain.generationLabelEtiquette.preferences;

import generationlabeletiquette.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;


public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub

		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.LEFT_MARGIN,PreferenceConstants.VALUE_DEFAUT_LEFT_MARGIN);
		store.setDefault(PreferenceConstants.RIGHT_MARGIN,PreferenceConstants.VALUE_DEFAUT_RIGHT_MARGIN);
		store.setDefault(PreferenceConstants.TOP_MARGIN,PreferenceConstants.VALUE_DEFAUT_TOP_MARGIN);
		store.setDefault(PreferenceConstants.BOTTOM_MARGIN,PreferenceConstants.VALUE_DEFAUT_BOTTOM_MARGIN);
		
		store.setDefault(PreferenceConstants.LEFT_PADDING,PreferenceConstants.VALUE_DEFAUT_LEFT_PADDING);
		store.setDefault(PreferenceConstants.RIGHT_PADDING,PreferenceConstants.VALUE_DEFAUT_RIGHT_PADDING);
		store.setDefault(PreferenceConstants.TOP_PADDING,PreferenceConstants.VALUE_DEFAUT_TOP_PADDING);
		store.setDefault(PreferenceConstants.BOTTOM_PADDING,PreferenceConstants.VALUE_DEFAUT_BOTTOM_PADDING);

		store.setDefault(PreferenceConstants.ROWS_ETIQUETTE,PreferenceConstants.VALUE_DEFAUT_ROWS_ETIQUETTE);
		store.setDefault(PreferenceConstants.COLUMS_ETIQUETTE,PreferenceConstants.VALUE_DEFAUT_COLUMS_ETIQUETTE);
		
		store.setDefault(PreferenceConstants.LARGEUR_ESPACE,PreferenceConstants.VALUE_DEFAUT_LARGEUR_ESPACE);
		store.setDefault(PreferenceConstants.HAUTEUR_ESPACE,PreferenceConstants.VALUE_DEFAUT_HAUTEUR_ESPACE);
		
		store.setDefault(PreferenceConstants.CELL_BORDER,true);
		
	}

}
