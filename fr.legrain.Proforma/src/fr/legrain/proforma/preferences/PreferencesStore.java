package fr.legrain.proforma.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;
import fr.legrain.proforma.proformaPlugin;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return proformaPlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return proformaPlugin.getDefault().getPreferenceStore();
	}



}
