package fr.legrain.bonlivraison.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.bonlivraison.BonLivraisonPlugin;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public class PreferencesStore implements IPreferencesExtension{

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return BonLivraisonPlugin.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		// TODO Auto-generated method stub
		return BonLivraisonPlugin.getDefault().getPreferenceStore();
	}



}
