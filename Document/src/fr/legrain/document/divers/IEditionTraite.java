package fr.legrain.document.divers;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.edition.actions.BaseImpressionEdition;

public interface IEditionTraite {	
	
	public void ImpressionTraite(Collection<TaRReglement> listeTraite, BaseImpressionEdition baseImpressionEdition);
	
	public String getTypeTraite();
	public void setShell(Shell shell);
}
