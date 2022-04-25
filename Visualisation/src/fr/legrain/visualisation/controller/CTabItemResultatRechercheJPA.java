package fr.legrain.visualisation.controller;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.lib.gui.PaResultatVisu;

public class CTabItemResultatRechercheJPA extends CTabItem {
	
	private PaResultatVisu vueOnglet;
	private OngletResultatControllerJPA controller;

	public CTabItemResultatRechercheJPA(CTabFolder parent, int style) {
		super(parent, style);
	}
	
	public CTabItemResultatRechercheJPA(CTabFolder parent, int style, PaResultatVisu vueOnglet, OngletResultatControllerJPA controller) {
		super(parent, style);
		this.vueOnglet = vueOnglet;
		this.controller = controller;
		setControl(vueOnglet);
	}

	public PaResultatVisu getVueOnglet() {
		return vueOnglet;
	}

	public void setVueOnglet(PaResultatVisu vueOnglet) {
		this.vueOnglet = vueOnglet;
	}

	public OngletResultatControllerJPA getController() {
		return controller;
	}

	public void setController(OngletResultatControllerJPA controller) {
		this.controller = controller;
	}
	
}
