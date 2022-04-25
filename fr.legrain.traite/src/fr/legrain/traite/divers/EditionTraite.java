package fr.legrain.traite.divers;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import fr.legrain.document.divers.IEditionTraite;
import fr.legrain.document.preferences.PreferenceConstants;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.traite.Activator;


public class EditionTraite implements IEditionTraite {

	private Shell shell;
	String typeTraite;
	
	@Override
	public void ImpressionTraite(Collection<TaRReglement> listeTraite, BaseImpressionEdition baseImpressionEdition) {
		if(listeTraite!=null && listeTraite.size()>0){
		baseImpressionEdition = new BaseImpressionEdition(shell,null);
		String simpleNameClass = TaRReglement.class.getSimpleName();//#JPA  

		TaRReglement rReglement =listeTraite.iterator().next();

		ConstEdition constEdition = new ConstEdition(null); 
		constEdition.setFlagEditionMultiple(true);


		Bundle bundleCourant = Activator.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();


		//baseImpressionEdition.setObject(rReglement);
		baseImpressionEdition.setConstEdition(constEdition);
		baseImpressionEdition.setCollection(listeTraite);
		//baseImpressionEdition.setIdEntity(rReglement.getId());
		
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

		baseImpressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,
				namePlugin,ConstEdition.FICHE_FILE_REPORT_TRAITE,true,
				false,true,true,true,false,"");
		
//
//		String reportFileLocationDefaut = constEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_TRAITE, namePlugin);
//		baseImpressionEdition.impressionEditionTypeEntity(reportFileLocationDefaut,"Traites");
	}
}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public String getTypeTraite() {
		typeTraite =Activator.getDefault().getPreferenceStore().getDefaultString(
				fr.legrain.traite.preferences.PreferenceConstants.TYPE_TRAITE);
		return typeTraite;
	}


		
		
	


}
