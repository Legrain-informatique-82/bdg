package fr.legrain.gestCom.Module_Document;


import java.util.EventListener;


public interface ModificationDocumentListener extends EventListener {
	
	public void modificationDocument(ModificationDocumentEvent evt) throws Exception;
	
}