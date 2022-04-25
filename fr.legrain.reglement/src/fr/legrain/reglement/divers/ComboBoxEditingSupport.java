package fr.legrain.reglement.divers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public final class ComboBoxEditingSupport extends EditingSupport /*ObservableValueEditingSupport*/ {

	private ComboBoxViewerCellEditor cellEditor = null;
	private int column = 0;

	private final LgrTableViewer viewer;

	public ComboBoxEditingSupport(LgrTableViewer viewer, int column) {
		super(viewer);
		this.viewer = viewer;
		this.column = column;

		cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
		TaTPaiementDAO daoTPaiementDAO = new TaTPaiementDAO();
		LinkedList<String> listeIHM = new LinkedList<String>();
		List<TaTPaiement>liste =daoTPaiementDAO.selectAll();
		for (TaTPaiement taTPaiement : liste) {
			listeIHM.add(taTPaiement.getCodeTPaiement());
		}
		cellEditor.setContenProvider(new ArrayContentProvider());
		cellEditor.setInput(new WritableList(listeIHM,String.class));
		cellEditor.addListener(new ICellEditorListener() {

			@Override
			public void applyEditorValue() {
				String v = cellEditor.getValue().toString();
			}

			@Override
			public void cancelEditor() {
			}

			@Override
			public void editorValueChanged(boolean oldValidState, boolean newValidState) {					
			}

		});
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		IHMReglement person = (IHMReglement) element;
		if(person!=null && person.getId()!=null)
			return true;
		else
			return false;
	}

	@Override
	protected Object getValue(Object element) {
		IHMReglement person = (IHMReglement) element;
		switch (column) {
		case 7:
			return person.getCodeTPaiement();

		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		IHMReglement pers = (IHMReglement) element;
		switch (column) {

		case 7:
			System.out.println("code paiement : "+value);
			pers.setCodeTPaiement((String) value);
			//changeEtat(pers,"com",LibConversion.StringToBoolean(value.toString()));
			break;

		default:
			break;
		}
		viewer.update(element, null);
	}

}
