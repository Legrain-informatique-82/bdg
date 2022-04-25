package fr.legrain.prelevement.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;
import fr.legrain.prelevement.prelevementPlugin;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return prelevementPlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return prelevementPlugin.getDefault().getPreferenceStore();
	}



}
