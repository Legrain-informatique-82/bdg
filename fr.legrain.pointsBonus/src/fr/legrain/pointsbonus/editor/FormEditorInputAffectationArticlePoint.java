/**
 * EditorInputStatistiques.java										11/04/11
 */
package fr.legrain.pointsbonus.editor;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * Editor plugin statistiques
 * @author nicolas²
 *
 */

public class FormEditorInputAffectationArticlePoint implements IEditorInput{
	static Logger logger = Logger.getLogger(FormEditorInputAffectationArticlePoint.class.getName());

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

}
