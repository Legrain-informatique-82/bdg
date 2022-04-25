package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventListener;

public interface IChangementMasterEntityListener extends EventListener {
	
	public void changementMasterEntity(ChangementMasterEntityEvent evt);

}
