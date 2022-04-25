package fr.legrain.article.export.catalogue.handlers;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import fr.legrain.article.export.catalogue.IImportationTiersCommandeWeb;
import fr.legrain.article.export.catalogue.ResultatImportation;

public class ImportationTiersCommandeWebProgress implements IRunnableWithProgress {

	static Logger logger = Logger.getLogger(ImportationTiersCommandeWebProgress.class.getName());
	
	private IImportationTiersCommandeWeb imp = null;
	private ResultatImportation resultatImportation = null;
	
	public ImportationTiersCommandeWebProgress(IImportationTiersCommandeWeb imp) {
		this.imp = imp;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.beginTask("Importation des données de la boutique dans votre logiciel ...", 100);
		
		runSubTaskExportSite(new SubProgressMonitor(monitor,50));
		
		runSubTaskRecuptFTP(new SubProgressMonitor(monitor,25));
		
		runSubTaskMAJSite(new SubProgressMonitor(monitor,25));
		
		monitor.done();
	}
	
	private void runSubTaskExportSite(IProgressMonitor monitor) {
		monitor.beginTask("Exportation des données du site",IProgressMonitor.UNKNOWN);
		monitor.subTask("Exportation des données du site.");
		imp.declencheExportSite();
		monitor.done();
	}
	
	private void runSubTaskRecuptFTP(IProgressMonitor monitor) {
		monitor.beginTask("Récupération des données du site",IProgressMonitor.UNKNOWN);
		monitor.subTask("Récupération des donées du site");
		imp.recuperationFTPTiersCommandeWeb();
		resultatImportation = imp.importWeb();
		monitor.done();
	}
	
	private void runSubTaskMAJSite(IProgressMonitor monitor) {
		monitor.beginTask("Envoi de la liste des commandes et des tiers importés à la boutique",IProgressMonitor.UNKNOWN);
		monitor.subTask("Envoi de la liste des commandes et des tiers importés à la boutique");
		imp.transfertMAJSite();
		imp.declencheMAJSite();
		monitor.done();
	}

	public ResultatImportation getResultatImportation() {
		return resultatImportation;
	}

}
