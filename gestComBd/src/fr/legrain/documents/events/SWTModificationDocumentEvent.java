package fr.legrain.documents.events;

import java.util.EventObject;

import fr.legrain.gestCom.Module_Document.SWTLigneDocument;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

public class SWTModificationDocumentEvent extends EventObject {
	
	private EnumModeObjet mode;
	private SWTLigneDocument ligne;
	private String propertyName;
	
	public SWTModificationDocumentEvent(Object source) {
		super(source);
	}
	
	public SWTModificationDocumentEvent(Object source, String propertyName) {
		super(source);
		this.propertyName = propertyName;
	}
	
	public SWTModificationDocumentEvent(Object source, EnumModeObjet mode) {
		super(source);
		this.mode = mode;
	}
	
	public SWTModificationDocumentEvent(Object source, SWTLigneDocument ligne) {
		super(source);
		this.ligne = ligne;
	}
	
	public SWTModificationDocumentEvent(Object source, EnumModeObjet mode, SWTLigneDocument ligne) {
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

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}