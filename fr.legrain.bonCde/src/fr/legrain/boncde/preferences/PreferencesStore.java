package fr.legrain.boncde.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.boncde.boncdePlugin;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return boncdePlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return boncdePlugin.getDefault().getPreferenceStore();
	}



}
