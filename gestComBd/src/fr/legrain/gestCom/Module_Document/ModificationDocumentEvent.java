package fr.legrain.gestCom.Module_Document;

import java.util.EventObject;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

public class ModificationDocumentEvent extends EventObject {
	
	private EnumModeObjet mode;
	private SWTLigneDocument ligne;
	
	public ModificationDocumentEvent(Object source) {
		super(source);
	}
	
	public ModificationDocumentEvent(Object source, EnumModeObjet mode) {
		super(source);
		this.mode = mode;
	}
	
	public ModificationDocumentEvent(Object source, SWTLigneDocument ligne) {
		super(source);
		this.ligne = ligne;
	}
	
	public ModificationDocumentEvent(Object source, EnumModeObjet mode, SWTLigneDocument ligne) {
		super(source);
		this.mode = mode;
		this.ligne = ligne;
	}

	public EnumModeObjet getMode() {
		return mode;
	}

	public void setMode(EnumModeObjet mode) {
		this.mode = mode;
	}

	public SWTLigneDocument getLigne() {
		return ligne;
	}

	public void setLigne(SWTLigneDocument ligne) {
		this.ligne = ligne;
	}

}