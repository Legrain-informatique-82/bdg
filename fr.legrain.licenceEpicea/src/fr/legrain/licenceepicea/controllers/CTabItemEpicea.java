package fr.legrain.licenceepicea.controllers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.licence.divers.CTabItemSupport;
import fr.legrain.licence.divers.ICTabItem;
import fr.legrain.licenceepicea.ecrans.PaEpiceaSWT;

public class CTabItemEpicea extends CTabItemSupport implements ICTabItem {
//	private CTabItem tabItem;
//	private CTabFolder tabFolder;
//	private SWTPaEpiceaController controller;
//	private PaEpiceaSWT vueOnglet;

	public CTabItemEpicea(){
		
	}
	public CTabItemSupport creationTabItem(CTabFolder tabFolder, int style){
		super.tabFolder = tabFolder;
		tabItem=new CTabItem(tabFolder,style);
		this.vueOnglet = new PaEpiceaSWT(tabFolder, style);
		this.controller = new SWTPaEpiceaController((PaEpiceaSWT) this.vueOnglet);
		tabItem.setControl(vueOnglet);
		tabItem.setText("Licence Epicéa");
		tabFolder.setSelection(tabItem);
		((PaEpiceaSWT)this.vueOnglet).getLaTitreFenetre().setVisible(false);
		((PaEpiceaSWT)this.vueOnglet).getLaTitreFormulaire().setVisible(false);
		return this;
	}


//	public CTabItem getTabItem() {
//		return tabItem;
//	}
//
//	public void setTabItem(CTabItem tabItem) {
//		this.tabItem = tabItem;
//	}
//
//	public CTabFolder getTabFolder() {
//		return tabFolder;
//	}
//
//	public void setTabFolder(CTabFolder tabFolder) {
//		this.tabFolder = tabFolder;
//	}
//
//
//
//	public SWTPaEpiceaController getController() {
//		return controller;
//	}
//
//
//
//	public void setController(SWTPaEpiceaController controller) {
//		this.controller = controller;
//	}
//
//
//
//	public PaEpiceaSWT getVueOnglet() {
//		return vueOnglet;
//	}
//
//
//
//	public void setVueOnglet(PaEpiceaSWT vueOnglet) {
//		this.vueOnglet = vueOnglet;
//	}
	
}
