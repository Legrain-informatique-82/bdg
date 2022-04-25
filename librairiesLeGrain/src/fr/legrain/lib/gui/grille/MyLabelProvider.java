package fr.legrain.lib.gui.grille;

import java.util.Date;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import fr.legrain.lib.data.LibDate;
import fr.legrain.librairiesLeGrain.LibrairiesLeGrainPlugin;

public class MyLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider, ITableColorProvider {
   	
	private IObservableMap[] attributeMaps = null;
//	  private static final String CHECK = "CHECK";
//	  private static final String UNCHECK = "UNCHECK";
//
//	  protected String CH_IMAGE_CHECK = "/icons/green-check.png";
//	protected String CH_IMAGE_NO_CHECK ="/icons/red-check.png";
	
//    private ImageRegistry ir = new ImageRegistry();
    

    
//	protected Image imageCheck = ImageCache.getImage(C_IMAGE_CHECK);
//		//LibrairiesLeGrainPlugin.getImageDescriptor(C_IMAGE_CHECK).createImage();
//	protected Image imageUncheck =ImageCache.getImage(C_IMAGE_NO_CHECK);
		//LibrairiesLeGrainPlugin.getImageDescriptor(C_IMAGE_NO_CHECK).createImage();
	
    public MyLabelProvider(IObservableMap attributeMap) {
		super(attributeMap);
	}

    		
	public MyLabelProvider(IObservableMap[] attributeMaps) {
		super(attributeMaps);
		this.attributeMaps = attributeMaps;
//	    ir.put(CHECK, LibrairiesLeGrainPlugin.getImageDescriptor(CH_IMAGE_CHECK));
//	    ir.put(UNCHECK, LibrairiesLeGrainPlugin.getImageDescriptor(CH_IMAGE_NO_CHECK));
		
		LibrairiesLeGrainPlugin.initCheckImage(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	}

	public Image getColumnImage(Object element, int columnIndex) {
    	Object result = null;
    	if (columnIndex < attributeMaps.length) {
			result = attributeMaps[columnIndex].get(element);
		}
    	if(result instanceof Boolean) {
    		if(((Boolean) result).booleanValue())
    		return LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.CHECK);
    		else
    			return LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.UNCHECK);
    	} 
    	else 
    	{
    		return super.getColumnImage(element,columnIndex);
    	}
    }

    public String getColumnText(Object element, int columnIndex) {
    	Object result = null;
    	if (columnIndex < attributeMaps.length) {
			result = attributeMaps[columnIndex].get(element);
		}
    	if(result instanceof Date) {
    		return LibDate.dateToString((Date)result);
    	} else 
    		if(result instanceof Boolean) {
    			return null;
//    			if(((Boolean) result).booleanValue())return "oui";
//    			else return "non";
    	} else{
    		return super.getColumnText(element,columnIndex);
    	}
    }

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}
}
