package fr.legrain.pointsbonus.divers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.pointLgr.dao.SWTArticlePoint;

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
		case 0:
			return person.getCodeArticle();
		case 1:
			return person.getLibelleArticle();
			
		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		SWTArticlePoint pers = (SWTArticlePoint) element;
		switch (column) {
		case 0:
			pers.setCodeArticle((String) value);
			break;
		case 1:
			pers.setLibelleArticle((String) value);
			break;			
		default:
			break;
		}
		viewer.update(element, null);
	}

}
