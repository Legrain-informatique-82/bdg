package fr.legrain.generationModelLettreOOo.preferences;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.windows.WinRegistry;
import generationmodellettreooo.Activator;


import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceInitializerOOo extends AbstractPreferenceInitializer {

	
	public PreferenceInitializerOOo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.PORT_SERVER_OPEN_OFFICE, "8100");
		String pathOpenOffice = "";
		try {
			if(Platform.getOS().equals(Platform.OS_WIN32)){
				pathOpenOffice = WinRegistry.readString(
				          WinRegistry.HKEY_LOCAL_MACHINE,
				          WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
				          "");
			}
			if(!pathOpenOffice.equals(""))
				store.setDefault(PreferenceConstants.PATH_OPEN_OFFICE, new File(pathOpenOffice).getParent());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		store.setDefault(PreferenceConstants.PATH_SAVE_PUBLIPOSTAGE_OO, 
				new File(Const.C_REPERTOIRE_BASE+Const.C_NOM_PROJET_TMP).getPath());
//		store.setDefault(PreferenceConstants.OPTION_AFFICHAGE, true);
	}

}
