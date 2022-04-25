package fr.legrain.article.export.catalogue.handlers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.GestionModulePHP;
import fr.legrain.article.export.catalogue.IExportationArticle;
import fr.legrain.article.export.catalogue.ResultatExportation;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;

public class ExportationArticleProgress implements IRunnableWithProgress {

	static Logger logger = Logger.getLogger(ExportationArticleProgress.class.getName());
	
	private static final String typeSvgAvantMaj = "avant_update";
	private static final String typeSvgApresMaj = "apres_update";
	
	private IExportationArticle exp = null;
	private ResultatExportation res = null;
	
	private GestionModulePHP gestionModulePHP = new GestionModulePHP();
	
	public ExportationArticleProgress(IExportationArticle exp) {
		this.exp = exp;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.beginTask("Exportation de vos données vers la boutique ...", 100);	
		if(Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.SAUVEGARDE_AUTOMATIQUE_BOUTIQUE)) {
			
			runSubTaskSauvegardeAvantMaj(new SubProgressMonitor(monitor,10));
			runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,40));
			runSubTaskUpdateSite(new SubProgressMonitor(monitor,40));
			runSubTaskSauvegardeApresMaj(new SubProgressMonitor(monitor,10));
			
		} else {

			runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,50));
			runSubTaskUpdateSite(new SubProgressMonitor(monitor,50));
			
		}

		//		monitor.beginTask("Mise à jour du site",IProgressMonitor.UNKNOWN);
		//		monitor.subTask("Mise à jour du site .");
		//		new ExportationArticle().declencheMAJSite();
		monitor.done();
	}
	
	//private boolean runSubTaskEnvoyerFTP(IProgressMonitor monitor) {
	private void runSubTaskEnvoyerFTP(IProgressMonitor monitor) {
		//File f = new File(path);
		//monitor.beginTask("",(int)f.length());
		int taille = 0;
		String pathRepExportWebTmp = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_TRAVAIL_LOC);
		taille += UtilProgress.getFileSize(new File(pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_CATEGORIE)));
		taille += UtilProgress.getFileSize(new File(pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_LABEL)));
		taille += UtilProgress.getFileSize(new File(pathRepExportWebTmp+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REP_FTP_EXPORT_IMG_ARTICLE)));
		
		System.err.println("Taille rep img = "+taille);
		monitor.beginTask("",taille);
		monitor.subTask("Transfert des données sur le serveur.");
		//boolean flag = transfert(monitor);
		exp.transfert(monitor);
		monitor.done();
		
		//f.delete();
		//return flag;
	}
	
	private void runSubTaskUpdateSite(IProgressMonitor monitor) {
		monitor.beginTask("Mise à jour du site",IProgressMonitor.UNKNOWN);
		monitor.subTask("Mise à jour de la boutique.");
		exp.declencheMAJSite();
		monitor.done();
	}
	
	private void runSubTaskSauvegardeAvantMaj(IProgressMonitor monitor) {
		monitor.beginTask("Sauvegarde de la boutique avant mise à jour",IProgressMonitor.UNKNOWN);
		monitor.subTask("Sauvegarde de la boutique avant mise à jour.");
		gestionModulePHP.sauvegardeBoutique(typeSvgAvantMaj);
		monitor.done();
	}

	private void runSubTaskSauvegardeApresMaj(IProgressMonitor monitor) {
		monitor.beginTask("Sauvegarde de la boutique après mise à jour",IProgressMonitor.UNKNOWN);
		monitor.subTask("Sauvegarde de la boutique après mise à jour.");
		gestionModulePHP.sauvegardeBoutique(typeSvgApresMaj);
		monitor.done();
	}
	


}
