package fr.legrain.ajouteredition.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;

public class HandlerBtAnnuler extends LgrAbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		System.out.println("dddd");
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isHandled() {
		return true;
	}

}
