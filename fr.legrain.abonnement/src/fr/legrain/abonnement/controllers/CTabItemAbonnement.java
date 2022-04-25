package fr.legrain.abonnement.controllers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.abonnement.ecrans.PaAbonnementSWT;
import fr.legrain.licence.divers.CTabItemSupport;
import fr.legrain.licence.divers.ICTabItem;
import fr.legrain.licence.ecrans.*;

public class CTabItemAbonnement extends CTabItemSupport implements ICTabItem {
//	private CTabItem tabItem;
//	private CTabFolder tabFolder;
//	private SWTPaEpiceaController controller;
//	private PaEpiceaSWT vueOnglet;

	public CTabItemAbonnement(){
		
	}
	public CTabItemSupport creationTabItem(CTabFolder tabFolder, int style){
//		super.tabFolder = tabFolder;
//		tabItem=new CTabItem(tabFolder,style);
//		this.vueOnglet = new PaAbonnementSWT(tabFolder, style);
//		this.controller = new SWTPaAbonnementController((PaAbonnementSWT) this.vueOnglet);
//		tabItem.setControl(vueOnglet);
//		tabItem.setText("Licence Epicéa");
//		tabFolder.setSelection(tabItem);
//		((PaAbonnementSWT)this.vueOnglet).getLaTitreFenetre().setVisible(false);
//		((PaAbonnementSWT)this.vueOnglet).getLaTitreFormulaire().setVisible(false);
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
