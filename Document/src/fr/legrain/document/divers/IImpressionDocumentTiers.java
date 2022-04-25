package fr.legrain.document.divers;

import java.util.Collection;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;

public interface IImpressionDocumentTiers extends IPreferencesExtension {
			
	public void imprimerSelection(int idDoc,final String codeDoc,boolean preview,String fileEditionDefaut,String nameOnglet, String nomEntity);

	@SuppressWarnings("unchecked")
	public void imprimerChoix(String fileEditionDefaut,String nameOnglet,Collection collection,String nomEntity,
			boolean flagPreview,boolean flagPrint);
}
