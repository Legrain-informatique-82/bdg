package fr.legrain.facture.preferences;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.facture.FacturePlugin;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return FacturePlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return FacturePlugin.getDefault().getPreferenceStore();
	}



}
