package fr.legrain.document.editor;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInputCreationDoc implements IEditorInput {
	
	static Logger logger = Logger.getLogger(EditorInputCreationDoc.class.getName());

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}
//
//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return true;
//	}

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

}
