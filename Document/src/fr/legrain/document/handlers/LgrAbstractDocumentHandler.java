package fr.legrain.document.handlers;

import org.apache.log4j.Logger;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.editor.AbstractMultiPageDocumentEditor;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;

public abstract class LgrAbstractDocumentHandler extends LgrAbstractHandler{
	
	static Logger logger = Logger.getLogger(LgrAbstractDocumentHandler.class.getName());
	
}
