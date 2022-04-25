package fr.legrain.apporteur.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.apporteur.apporteurPlugin;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return apporteurPlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return apporteurPlugin.getDefault().getPreferenceStore();
	}



}
