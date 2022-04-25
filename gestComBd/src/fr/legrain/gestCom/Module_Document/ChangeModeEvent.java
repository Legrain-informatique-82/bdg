package fr.legrain.gestCom.Module_Document;

import java.util.EventObject;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

public class ChangeModeEvent extends EventObject {
	
	private EnumModeObjet nouveauMode = null;
	private EnumModeObjet ancienMode  = null;
	
	public ChangeModeEvent(Object source, EnumModeObjet modeLigne) {
		super(source);
		this.nouveauMode = modeLigne;
	}
	
	public ChangeModeEvent(Object source, EnumModeObjet ancienMode, EnumModeObjet nouveauMode) {
		super(source);
		this.nouveauMode = nouveauMode;
		this.ancienMode = ancienMode;
	}

	public EnumModeObjet getAncienMode() {
		return ancienMode;
	}

	public void setAncienMode(EnumModeObjet ancienMode) {
		this.ancienMode = ancienMode;
	}

	public EnumModeObjet getNouveauMode() {
		return nouveauMode;
	}

	public void setNouveauMode(EnumModeObjet nouveauMode) {
		this.nouveauMode = nouveauMode;
	}

}