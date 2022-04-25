package fr.legrain.lib.gui.grille;

import java.util.Date;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import fr.legrain.lib.data.LibDate;

public class LgrViewerSupport/*extends ViewerSupport*/ {
	
	/**
	 * @see org.eclipse.jface.databinding.viewers.ViewerSupport#bind(StructuredViewer, org.eclipse.core.databinding.observable.set.IObservableSet, IValueProperty[])
	 * @param viewer
	 * @param input
	 * @param labelProperties
	 */
	public static void bind(StructuredViewer viewer, IObservableList input,
			IValueProperty[] labelProperties) {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		if (viewer.getInput() != null)
			viewer.setInput(null);
		viewer.setContentProvider(contentProvider);
		//viewer.setLabelProvider(new ObservableMapLabelProvider(Properties
		viewer.setLabelProvider(new MyLabelProvider(Properties
				.observeEach(contentProvider.getKnownElements(),
						labelProperties)));
		if (input != null)
			viewer.setInput(input);
	}
	
	public static void bind(AbstractTreeViewer viewer, Object input,
			IListProperty childrenProperty, IValueProperty[] labelProperties) {
		Realm realm = SWTObservables.getRealm(viewer.getControl().getDisplay());
		ObservableListTreeContentProvider contentProvider = new ObservableListTreeContentProvider(
				childrenProperty.listFactory(realm), null);
		if (viewer.getInput() != null)
			viewer.setInput(null);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new MyLabelProvider(Properties
				.observeEach(contentProvider.getKnownElements(),
						labelProperties)));
		if (input != null)
			viewer.setInput(input);
	}
	
//	static class MyLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider, ITableColorProvider {
//	   	
//		private IObservableMap[] attributeMaps = null;
//		
//        public MyLabelProvider(IObservableMap attributeMap) {
//			super(attributeMap);
//		}
//
//		public MyLabelProvider(IObservableMap[] attributeMaps) {
//			super(attributeMaps);
//			this.attributeMaps = attributeMaps;
//		}
//
//		public Image getColumnImage(Object element, int columnIndex) {
//            return super.getColumnImage(element,columnIndex);
//        }
// 
//        public String getColumnText(Object element, int columnIndex) {
//        	Object result = null;
//        	if (columnIndex < attributeMaps.length) {
//    			result = attributeMaps[columnIndex].get(element);
//    		}
//        	if(result instanceof Date) {
//        		return LibDate.dateToString((Date)result);
//        	} else {
//        		return super.getColumnText(element,columnIndex);
//        	}
//        }
//
//		@Override
//		public Color getBackground(Object element, int columnIndex) {
//			return null;
//		}
//
//		@Override
//		public Color getForeground(Object element, int columnIndex) {
//			return null;
//		}
//	}


}
