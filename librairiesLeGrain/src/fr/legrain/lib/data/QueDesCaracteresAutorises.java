package fr.legrain.lib.data;

import java.util.LinkedList;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class QueDesCaracteresAutorises implements VerifyListener{
    LinkedList<String> listeCaracteres = new LinkedList<String>();
    
    
	public QueDesCaracteresAutorises(String[] listeCaracteres) {
		super();
		for (int i = 0; i < listeCaracteres.length; i++) {
			this.listeCaracteres.add(listeCaracteres[i]) ;	
		}
		
	}


	public void verifyText(VerifyEvent e) {
		if(!e.text.equals(""))
		  e.doit = getListeCaracteres().indexOf(e.text)>-1;
	}

	
	public LinkedList<String> getListeCaracteres() {
		return listeCaracteres;
	}

}
