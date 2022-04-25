package fr.legrain.document.divers;

import java.util.EventListener;

public interface IDeclencheChangementEtatListener extends EventListener {
	
	public void declencheChangementEtat(DeclencheChangementEtatEvent evt);

}
