package fr.legrain.document.RechercheDocument.editor;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;



public class FormEditorInputRechercheDocument implements IEditorInput{


	static Logger logger = Logger.getLogger(FormEditorInputRechercheDocument.class.getName());
	
	String typeDoc = null;
	
	public FormEditorInputRechercheDocument(String typeDoc) {
		super();
		this.typeDoc = typeDoc;
	}	
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "";
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}


	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}

	public String getTypeDoc() {
		return typeDoc;
	}

}
