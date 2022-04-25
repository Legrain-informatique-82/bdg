package fr.legrain.pointsbonus.divers;

import java.math.BigDecimal;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.gestCom.librairiesEcran.swt.BigDecimalCellEditor;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.pointLgr.dao.SWTArticlePoint;


public final class BigDecimalEditingSupport extends EditingSupport /*ObservableValueEditingSupport*/ {

	private BigDecimalCellEditor cellEditor = null;
	private int column = 0;

	private final LgrTableViewer viewer;

	public BigDecimalEditingSupport(LgrTableViewer viewer, int column) {
		super(viewer);
		this.viewer = viewer;
		this.column = column;

		cellEditor = new BigDecimalCellEditor((Composite) getViewer().getControl());

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
		SWTArticlePoint person = (SWTArticlePoint) element;
		if(person!=null && person.getIdArticlePoint()!=null)
			return true;
		else
			return false;
	}

	@Override
	protected Object getValue(Object element) {
		SWTArticlePoint person = (SWTArticlePoint) element;
		switch (column) {
		case 3:
			return person.getPrixReference();
		case 4:
			return person.getPoints();
		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		SWTArticlePoint pers = (SWTArticlePoint) element;
		switch (column) {
		case 3:
			pers.setPrixReference((BigDecimal) value);
			break;
		case 4:
			pers.setPoints((BigDecimal) value);
			break;
		default:
			break;
		}
		viewer.update(element, null);
	}

}