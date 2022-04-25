package fr.legrain.licencesara.controllers;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.licence.divers.CTabItemSupport;
import fr.legrain.licence.divers.ICTabItem;
import fr.legrain.licencesara.ecrans.PaSaraSWT;

public class CTabItemSara extends CTabItemSupport implements ICTabItem {

	public CTabItemSara(){
		
	}
	public CTabItemSupport creationTabItem(CTabFolder tabFolder, int style){
		super.tabFolder = tabFolder;
		tabItem=new CTabItem(tabFolder,style);
		this.vueOnglet = new PaSaraSWT(tabFolder, style);
		this.controller = new SWTPaSaraController((PaSaraSWT) this.vueOnglet);
		tabItem.setControl(vueOnglet);
		tabItem.setText("Licence Sara");
		tabFolder.setSelection(tabItem);
		((PaSaraSWT)this.vueOnglet).getLaTitreFenetre().setVisible(false);
		((PaSaraSWT)this.vueOnglet).getLaTitreFormulaire().setVisible(false);
		return this;
	}



	
}
