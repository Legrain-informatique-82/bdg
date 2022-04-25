package fr.legrain.article.export.catalogue.handlers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.ExportationArticle;
import fr.legrain.article.export.catalogue.GestionModulePHP;
import fr.legrain.article.export.catalogue.IExportationArticle;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;

public class GestionModulePHPProgress implements IRunnableWithProgress {

	static Logger logger = Logger.getLogger(GestionModulePHPProgress.class.getName());

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.beginTask("Vérification du module serveur ...", 100);	  
		runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,100));
		
		monitor.done();
	}

	private void runSubTaskEnvoyerFTP(IProgressMonitor monitor) {

		int taille = 0;
		//GestionModulePHP.tailleModule(version);
		taille = 456000; //taille du repertoire /php/2_0_6/php 
		
		monitor.beginTask("",taille);
		monitor.subTask("Envoie vers le serveur FTP .");

		//GestionModulePHP.miseAJourModulePHP(monitor);
		GestionModulePHP.miseAJourModulePHP(monitor,false);
		monitor.done();

	}

}
