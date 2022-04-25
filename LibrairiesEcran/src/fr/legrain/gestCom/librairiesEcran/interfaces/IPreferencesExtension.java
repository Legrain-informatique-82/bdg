package fr.legrain.gestCom.librairiesEcran.interfaces;

import java.util.Collection;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public interface IPreferencesExtension  {
		
	
	public String getPluginName();
//	public String getPluginId();
	public IPreferenceStore getPreferenceStore();
	
}
