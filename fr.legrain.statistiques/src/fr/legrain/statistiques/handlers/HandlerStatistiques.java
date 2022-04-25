package fr.legrain.statistiques.handlers;
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

import fr.legrain.departement.*;
import fr.legrain.departement.dao.TaDepartements;
import fr.legrain.departement.dao.TaDepartementsDAO;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.statistiques.editors.*;
import fr.legrain.tiers.dao.TaTiersDAO;




public class HandlerStatistiques extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerStatistiques.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.err.println("Tableau de bord . OK");
		try {
						IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FormEditorInputStatistiques(), FormEditorStatistiques.ID);

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
