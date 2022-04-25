package fr.legrain.supporthebergement.controllers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.licence.divers.CTabItemSupport;
import fr.legrain.licence.divers.ICTabItem;
import fr.legrain.supporthebergement.ecrans.PaHebergementSWT;

public class CTabItemHebergement extends CTabItemSupport implements ICTabItem {
//	private CTabItem tabItem;
//	private CTabFolder tabFolder;
//	private SWTPaHebergementController controller;
//	private PaHebergementSWT vueOnglet;

	public CTabItemHebergement(){
		
	}
	public CTabItemSupport creationTabItem(CTabFolder tabFolder, int style){
		super.tabFolder = tabFolder;
		tabItem=new CTabItem(tabFolder,style);
		this.vueOnglet = new PaHebergementSWT(tabFolder, style);
		this.controller = new SWTPaHebergementController((PaHebergementSWT) this.vueOnglet);
		tabItem.setControl(vueOnglet);
		tabItem.setText("Hébergement");
		tabFolder.setSelection(tabItem);
		((PaHebergementSWT)this.vueOnglet).getLaTitreFenetre().setVisible(false);
		((PaHebergementSWT)this.vueOnglet).getLaTitreFormulaire().setVisible(false);
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
//	public SWTPaHebergementController getController() {
//		return controller;
//	}
//
//
//
//	public void setController(SWTPaHebergementController controller) {
//		this.controller = controller;
//	}
//
//
//
//	public PaHebergementSWT getVueOnglet() {
//		return vueOnglet;
//	}
//
//
//
//	public void setVueOnglet(PaHebergementSWT vueOnglet) {
//		this.vueOnglet = vueOnglet;
//	}
	
}
