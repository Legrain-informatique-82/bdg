package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventListener;

public interface IChangementDeSelectionListener extends EventListener {
	
	public void changementDeSelection(ChangementDeSelectionEvent evt);

}
