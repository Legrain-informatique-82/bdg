package fr.legrain.document.controller;

import java.io.File;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import fr.legrain.gestCom.Module_Document.SWTDocumentEditable;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.lib.gui.grille.MyLabelProvider;
import fr.legrain.librairiesLeGrain.LibrairiesLeGrainPlugin;

public class ViewerSupportDocumentEditable extends LgrViewerSupport {

	public static void bind(AbstractTreeViewer viewer, Object input,
			IListProperty childrenProperty, IValueProperty[] labelProperties) {
		Realm realm = SWTObservables.getRealm(viewer.getControl().getDisplay());
		ObservableListTreeContentProvider contentProvider = new ObservableListTreeContentProvider(
				childrenProperty.listFactory(realm), null);
		if (viewer.getInput() != null)
			viewer.setInput(null);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new MyLabelProviderLoc(Properties
				.observeEach(contentProvider.getKnownElements(),
						labelProperties)));
		if (input != null)
			viewer.setInput(input);
	}
	
	public static class MyLabelProviderLoc extends MyLabelProvider implements IFontProvider {
		
		private Font f = null;
		
		public MyLabelProviderLoc(IObservableMap[] attributeMaps) {
			super(attributeMaps);
			f = Display.getCurrent().getSystemFont();
			f = new Font(Display.getCurrent(),
					f.getFontData()[0].getName()
					,f.getFontData()[0].getHeight()
					,SWT.BOLD);
		}
		
		@Override
		public Color getBackground(Object element, int columnIndex) {
//			if(element instanceof SWTDocumentEditable){
//				if(((SWTDocumentEditable)element).getCodeTypeDoc()!=null)
//					return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
//				else
//					return null;
//			}
			return null;
		}

		@Override
		public Color getForeground(Object element, int columnIndex) {
			return null;
		}

		@Override
		public Font getFont(Object element) {
			if(element instanceof SWTDocumentEditable){
				if(((SWTDocumentEditable)element).getCodeTypeDoc()!=null)
					return f;
				else
					return null;
			}
			return null;
		}
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if(columnIndex==0) {
	    		if(element instanceof SWTDocumentEditable){
					if(((SWTDocumentEditable)element).getCodeTypeDoc()==null)
						if(((SWTDocumentEditable)element).getCheminModelDocumentDoc()!=null
								&& !new File(((SWTDocumentEditable)element).getCheminModelDocumentDoc()).exists())
							return LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXCLAMATION);
						else
							return super.getColumnImage(element,columnIndex);
					else
						return super.getColumnImage(element,columnIndex);
				}
			} else{ 
	    		return super.getColumnImage(element,columnIndex);
	    	}
			return super.getColumnImage(element,columnIndex);
	    }
	}
}



