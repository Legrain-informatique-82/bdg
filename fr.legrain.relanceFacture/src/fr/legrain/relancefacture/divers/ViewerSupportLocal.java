package fr.legrain.relancefacture.divers;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import fr.legrain.gestCom.Module_Document.IHMLRelance;
import fr.legrain.gestCom.Module_Document.IHMRelance;
import fr.legrain.gestCom.Module_Tiers.SWTTRelance;
import fr.legrain.lib.gui.grille.LgrViewerSupport;

public class ViewerSupportLocal extends LgrViewerSupport {
	
	public static void bind(StructuredViewer viewer, IObservableList input,
			IValueProperty[] labelProperties) {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		if (viewer.getInput() != null)
			viewer.setInput(null);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new MyLabelProviderLoc(Properties
				.observeEach(contentProvider.getKnownElements(),
						labelProperties)));
		if (input != null)
			viewer.setInput(input);
	}
	public static class MyLabelProviderLoc extends fr.legrain.lib.gui.grille.MyLabelProvider {
		public MyLabelProviderLoc(IObservableMap[] attributeMaps) {
			super(attributeMaps);
		}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		if(element instanceof SWTTRelance){
			if(((SWTTRelance)element).getActif())
				return null;
			else
				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		if(element instanceof IHMLRelance){
			if(((IHMLRelance)element).getAccepte())
				return null;
			else
				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return null;
	}
}
}



