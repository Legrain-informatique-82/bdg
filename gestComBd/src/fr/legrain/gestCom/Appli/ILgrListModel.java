package fr.legrain.gestCom.Appli;

import org.eclipse.jface.viewers.TableViewer;

import fr.legrain.lib.gui.grille.LgrTableViewer;

public interface ILgrListModel<T> {
	public void addToModel(final TableViewer viewer, T t);
	
	public void refreshModel(final TableViewer viewer, T t);
	
	public void removeFromModel(final TableViewer viewer, T t);
}
