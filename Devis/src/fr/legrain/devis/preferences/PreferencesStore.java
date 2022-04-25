package fr.legrain.devis.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.devis.DevisPlugin;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return DevisPlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return DevisPlugin.getDefault().getPreferenceStore();
	}



}
