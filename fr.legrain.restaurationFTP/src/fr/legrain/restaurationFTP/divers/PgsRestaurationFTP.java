package fr.legrain.restaurationFTP.divers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.sauvegarde.handlers.FonctionSauvegarde;


public class PgsRestaurationFTP implements IRunnableWithProgress, LgrWorkListener{

	private String nameFileChoixRestauration;
	private String localFileName;
	private FtpUtil ftpUtil;
	private Shell shell;
	
	
	public PgsRestaurationFTP(String nameFileChoixRestauration,
			String localFileName, FtpUtil ftpUtil,Shell shell) {
		super();
		this.nameFileChoixRestauration = nameFileChoixRestauration;
		this.localFileName = localFileName;
		this.ftpUtil = ftpUtil;
		this.shell = shell;
	}
	public void downloadFileRestauration() {
		try {
			ftpUtil.downloadFile(nameFileChoixRestauration, localFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		// TODO Auto-generated method stub
		monitor.beginTask("Téléchargement de la sauvegarde à partir du serveur distant ...", monitor.UNKNOWN);
		downloadFileRestauration();
//		FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
//		fonctionSauvegarde.nouveau(localFileName,shell);
		monitor.done();
	}

	@Override
	public void beginSubtask(LgrWorkEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginWork(LgrWorkEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endWork(LgrWorkEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void work(LgrWorkEvent evt) {
		// TODO Auto-generated method stub
		
	}



}
