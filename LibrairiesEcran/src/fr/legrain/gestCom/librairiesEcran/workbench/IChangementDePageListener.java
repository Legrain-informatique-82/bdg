package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventListener;

public interface IChangementDePageListener extends EventListener {
	
	public void changementDePage(ChangementDePageEvent evt);

}
