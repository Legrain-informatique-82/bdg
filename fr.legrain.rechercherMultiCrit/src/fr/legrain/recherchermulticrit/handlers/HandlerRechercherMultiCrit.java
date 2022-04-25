package fr.legrain.recherchermulticrit.handlers;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.persistence.EntityTransaction;
import org.apache.log4j.Logger;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.recherchermulticrit.editors.FormEditorInputRechercherMultiCrit;
import fr.legrain.recherchermulticrit.editors.FormEditorRechercherMultiCrit;






public class HandlerRechercherMultiCrit extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerRechercherMultiCrit.class.getName());
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.err.println("Etats paramétrés . OK");
		try {
						IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FormEditorInputRechercherMultiCrit(), FormEditorRechercherMultiCrit.ID);

					} catch (PartInitException e) {
						logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}



}
