package fr.legrain.avoir.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.avoir.avoirPLugin;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return avoirPLugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return avoirPLugin.getDefault().getPreferenceStore();
	}



}
