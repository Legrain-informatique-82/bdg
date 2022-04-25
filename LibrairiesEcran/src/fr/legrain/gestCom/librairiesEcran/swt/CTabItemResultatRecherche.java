package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.lib.gui.PaResultatVisu;

public class CTabItemResultatRecherche extends CTabItem {
	
	private PaResultatVisu vueOnglet;
	private OngletResultatController controller;

	public CTabItemResultatRecherche(CTabFolder parent, int style) {
		super(parent, style);
	}
	
	public CTabItemResultatRecherche(CTabFolder parent, int style, PaResultatVisu vueOnglet, OngletResultatController controller) {
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

	public OngletResultatController getController() {
		return controller;
	}

	public void setController(OngletResultatController controller) {
		this.controller = controller;
	}
	
}
