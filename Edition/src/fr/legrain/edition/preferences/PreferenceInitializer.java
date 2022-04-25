package fr.legrain.edition.preferences;


import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


import fr.legrain.edition.Activator;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.windows.WinRegistry;



public class PreferenceInitializer extends AbstractPreferenceInitializer{

	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.CHOIX_FORMAT,true);
		
		String pathAcrobat = "";
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			try {
				pathAcrobat = WinRegistry.readString(
				          WinRegistry.HKEY_LOCAL_MACHINE,
				          WinRegistry.KEY_REGISTR_WIN_ADOBE,
				          "");
				store.setDefault(PreferenceConstants.PATH_ACROBAT_READER, 
						new File(pathAcrobat).getParent());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		store.setDefault(PreferenceConstants.PATH_SAVE_PDF, 
				new File(Const.C_REPERTOIRE_BASE+Const.C_NOM_PROJET_TMP).getPath());
	}

}
