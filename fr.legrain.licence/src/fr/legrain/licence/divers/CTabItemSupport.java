package fr.legrain.licence.divers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;

public class CTabItemSupport /*implements ICTabItem*/ {
	protected CTabItem tabItem;
	protected CTabFolder tabFolder;
	protected JPABaseControllerSWTStandard controller;
	protected Composite vueOnglet;

	public CTabItemSupport(){
		
	}

	public CTabItem getTabItem() {
		return tabItem;
	}

	public void setTabItem(CTabItem tabItem) {
		this.tabItem = tabItem;
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}



	public JPABaseControllerSWTStandard getController() {
		return controller;
	}



	public void setController(JPABaseControllerSWTStandard controller) {
		this.controller = controller;
	}



	public Composite getVueOnglet() {
		return vueOnglet;
	}



	public void setVueOnglet(Composite vueOnglet) {
		this.vueOnglet = vueOnglet;
	}
	
}
