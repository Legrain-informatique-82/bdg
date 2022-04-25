package fr.legrain.reglement.divers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public final class StringEditingSupport extends EditingSupport /*ObservableValueEditingSupport*/ {

	private TextCellEditor cellEditor = null;
	private int column = 0;

	private final LgrTableViewer viewer;

	public StringEditingSupport(LgrTableViewer viewer, int column) {
		super(viewer);
		this.viewer = viewer;
		this.column = column;

		cellEditor = new TextCellEditor((Composite) getViewer().getControl());

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
		case 8:
			return person.getLibelleDocument();
		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		IHMReglement pers = (IHMReglement) element;
		switch (column) {
		case 8:
			System.out.println("libellé paiement : "+value);
			pers.setLibelleDocument((String) value);
			//changeEtat(pers,"com",LibConversion.StringToBoolean(value.toString()));
			break;
		default:
			break;
		}
		viewer.update(element, null);
	}

}
