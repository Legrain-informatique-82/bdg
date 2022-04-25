package fr.legrain.prelevement.exportation.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;
import fr.legrain.prelevement.exportation.pluginPrelevement;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return pluginPrelevement.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return pluginPrelevement.getDefault().getPreferenceStore();
	}



}
