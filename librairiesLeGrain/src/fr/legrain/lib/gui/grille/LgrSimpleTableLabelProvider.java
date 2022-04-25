package fr.legrain.lib.gui.grille;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import fr.legrain.librairiesLeGrain.LibrairiesLeGrainPlugin;



public class LgrSimpleTableLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {
	
	static Logger logger = Logger.getLogger(LgrSimpleTableLabelProvider.class.getName());
	
	private String[] listeChamp = null;

	public LgrSimpleTableLabelProvider(String[] listeChamp) {
		this.listeChamp = listeChamp;
		LibrairiesLeGrainPlugin.initCheckImage(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	}

//	public void addListener(ILabelProviderListener listener) {
//	}
//
//	public void dispose() {
//	}
//
//	public boolean isLabelProperty(Object element, String property) {
//		return true;
//	}
//
//	public void removeListener(ILabelProviderListener listener) {
//	}

	public Image getColumnImage(Object element, int columnIndex) {
		Object result = null;
		try {
			if(element!=null) {
				if (columnIndex < listeChamp.length) {
					result = BeanUtils.getSimpleProperty(element, listeChamp[columnIndex]);
				}

				if(result!=null) {
					if(result.equals("true"))
						return LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.CHECK);
						//return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
					else if(result.equals("false"))
						return LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.UNCHECK);
				}
			}
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		Object result = null;
		try {
			if(element!=null) {
				if (columnIndex < listeChamp.length) {

					result = BeanUtils.getSimpleProperty(element, listeChamp[columnIndex]);
				}
//				if(result instanceof Date) {
//					return LibDate.dateToString((Date)result);
//				} else {
					if(result!=null && (result.equals("true") || result.equals("false"))) {
						return null;
//						if(result.equals("true"))
//							return "X";
//						else if(result.equals("false"))
//							return "-";
					} else {
						return result==null?"":result.toString();
						//return result==null?"?":result.toString();
					}
//				}
			}
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
		return null;
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
