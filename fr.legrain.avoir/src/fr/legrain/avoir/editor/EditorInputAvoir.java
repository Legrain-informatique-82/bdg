package fr.legrain.avoir.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInputAvoir implements IEditorInput {
	
	private String id = "fr.legrain.avoir.perspective.EditorInputAvoir";

	public EditorInputAvoir() {}
	
	public EditorInputAvoir(String id) {
		this.id = id;
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
		return id;
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub
		return id;
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		//return super.equals(obj);
		return false;
	}

}
