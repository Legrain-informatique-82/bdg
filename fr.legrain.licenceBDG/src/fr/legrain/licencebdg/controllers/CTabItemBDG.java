package fr.legrain.licencebdg.controllers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.licence.divers.CTabItemSupport;
import fr.legrain.licence.divers.ICTabItem;
import fr.legrain.licencebdg.ecrans.PaBDGSWT;

public class CTabItemBDG extends CTabItemSupport implements ICTabItem {
//	private CTabItem tabItem;
//	private CTabFolder tabFolder;
//	private SWTPaBDGController controller;
//	private PaBDGSWT vueOnglet;

	public CTabItemBDG(){
		
	}
	public CTabItemSupport creationTabItem(CTabFolder tabFolder, int style){
		super.tabFolder = tabFolder;
		tabItem=new CTabItem(tabFolder,style);
		this.vueOnglet = new PaBDGSWT(tabFolder, style);
		this.controller = new SWTPaBDGController((PaBDGSWT) this.vueOnglet);
		tabItem.setControl(vueOnglet);
		tabItem.setText("Licence BDG");
		tabFolder.setSelection(tabItem);
		((PaBDGSWT)this.vueOnglet).getLaTitreFenetre().setVisible(false);
		((PaBDGSWT)this.vueOnglet).getLaTitreFormulaire().setVisible(false);
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
//	public SWTPaBDGController getController() {
//		return controller;
//	}
//
//
//
//	public void setController(SWTPaBDGController controller) {
//		this.controller = controller;
//	}
//
//
//
//	public PaBDGSWT getVueOnglet() {
//		return vueOnglet;
//	}
//
//
//
//	public void setVueOnglet(PaBDGSWT vueOnglet) {
//		this.vueOnglet = vueOnglet;
//	}
	
}
