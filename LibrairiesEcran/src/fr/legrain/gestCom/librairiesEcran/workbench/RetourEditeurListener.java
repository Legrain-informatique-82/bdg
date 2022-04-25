package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventListener;

public interface RetourEditeurListener extends EventListener {
	
	public void retourEditeur(RetourEditeurEvent evt)/* throws Exception*/;
	
	//public void addRetourEditeurListener(RetourEditeurListener l);
	
	//public void removeRetourEditeurListener(RetourEditeurListener l);

}
