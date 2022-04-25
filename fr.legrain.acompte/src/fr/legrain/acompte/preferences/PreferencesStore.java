package fr.legrain.acompte.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.acompte.pluginAcompte;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return pluginAcompte.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return pluginAcompte.getDefault().getPreferenceStore();
	}



}
