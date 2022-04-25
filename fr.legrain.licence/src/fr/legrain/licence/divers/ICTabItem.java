package fr.legrain.licence.divers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;


public interface ICTabItem {

	
	public CTabItemSupport creationTabItem(CTabFolder tabFolder, int style);
	public void setTabItem(CTabItem tabItem);
	public void setTabFolder(CTabFolder tabFolder);
	
}
