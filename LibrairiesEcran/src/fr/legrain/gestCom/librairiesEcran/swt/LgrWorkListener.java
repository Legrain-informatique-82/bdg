package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.EventListener;

public interface LgrWorkListener extends EventListener {

	public void work(LgrWorkEvent evt);
	public void beginWork(LgrWorkEvent evt);
	public void endWork(LgrWorkEvent evt);
	public void beginSubtask(LgrWorkEvent evt);
	
}
