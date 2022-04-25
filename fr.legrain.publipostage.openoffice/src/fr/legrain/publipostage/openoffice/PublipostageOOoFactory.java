package fr.legrain.publipostage.openoffice;

import java.util.List;

import org.eclipse.core.runtime.Platform;

import fr.legrain.publipostage.divers.ParamPublipostage;

public class PublipostageOOoFactory {
	
	static public AbstractPublipostageOOo createPublipostageOOo(List<ParamPublipostage> listeParamPublipostages) {
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			return new PublipostageOOoWin32(listeParamPublipostages);
		} else if(Platform.getOS().equals(Platform.OS_LINUX)){
			return new PublipostageOOoLinux(listeParamPublipostages);
		}
		return null;
	}

}
