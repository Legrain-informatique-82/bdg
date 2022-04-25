package fr.legrain.reglement.divers;

import java.util.Date;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.gestCom.librairiesEcran.swt.DateTimeCellEditor;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public final class DateTimeEditingSupport extends EditingSupport /*ObservableValueEditingSupport*/ {

	private DateTimeCellEditor cellEditor = null;
	private int column = 0;

	private final LgrTableViewer viewer;

	public DateTimeEditingSupport(LgrTableViewer viewer, int column) {
		super(viewer);
		this.viewer = viewer;
		this.column = column;

		cellEditor = new DateTimeCellEditor((Composite) getViewer().getControl());

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
		case 3:
			return person.getDateDocument();
		case 4:
			return person.getDateLivDocument();
		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		IHMReglement pers = (IHMReglement) element;
		switch (column) {
		case 3:
			System.out.println("date règlement : "+value);
			pers.setDateDocument((Date) value);
			//changeEtat(pers,"com",LibConversion.StringToBoolean(value.toString()));
			break;
		case 4:
			System.out.println("date encaissement : "+value);
			pers.setDateLivDocument((Date) value);
			//changeEtat(pers,"com",LibConversion.StringToBoolean(value.toString()));
			break;
		default:
			break;
		}
		viewer.update(element, null);
	}

}
