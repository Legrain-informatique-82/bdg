package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventListener;

public interface IAnnuleToutListener extends EventListener {
	
	public void annuleTout(AnnuleToutEvent evt);
	
	public void enregistreTout(AnnuleToutEvent evt);
	
//	public void EvenementTout(AnnuleToutEvent evt);

}
