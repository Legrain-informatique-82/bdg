package fr.legrain.avisecheance.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.avisecheance.PlugInAvisEcheance;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return PlugInAvisEcheance.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return PlugInAvisEcheance.getDefault().getPreferenceStore();
	}



}
