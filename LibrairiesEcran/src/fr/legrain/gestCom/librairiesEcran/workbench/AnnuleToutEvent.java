package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventObject;


public class AnnuleToutEvent extends EventObject {
	
	private boolean silencieu = false;

	public AnnuleToutEvent(Object source) {
		super(source);
	}
	
	public AnnuleToutEvent(Object source,boolean silencieu) {
		super(source);
		this.silencieu = silencieu;
	}

	public boolean isSilencieu() {
		return silencieu;
	}

	public void setSilencieu(boolean silencieu) {
		this.silencieu = silencieu;
	}

}
