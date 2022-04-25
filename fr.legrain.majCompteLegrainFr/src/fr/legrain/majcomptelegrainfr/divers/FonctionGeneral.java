package fr.legrain.majcomptelegrainfr.divers;

import java.io.File;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.majcomptelegrainfr.Activator;
import fr.legrain.majcomptelegrainfr.preferences.PreferenceConstantsProject;
import fr.legrain.majcomptelegrainfr.preferences.PreferenceInitializerProject;



public class FonctionGeneral implements IRunnableWithProgress, LgrWorkListener {

	static Logger logger = Logger.getLogger(FonctionGeneral.class.getName());
			
	private String pathServerFTP = null;
	private String portServerFTP = null;
	private String loginServerFTP = null;
	private String passwordServerFTP = null;
	private String folderFtpSauvegarde = null;
	private boolean flagSauvegardeFTP;
	private String cheminFichier;
	private String url;
	
	
	public FonctionGeneral() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FonctionGeneral(String portServerFTP) {
		super();
		// TODO Auto-generated constructor stub
		this.portServerFTP = portServerFTP;
	}
	public FonctionGeneral(String pathServerFTP, String portServerFTP,
			String loginServerFTP, String passwordServerFTP,
			String floderFtpSauvegarde, String cheminFichier , String url) {
		super();
		this.pathServerFTP = pathServerFTP;
		this.portServerFTP = portServerFTP;
		this.loginServerFTP = loginServerFTP;
		this.passwordServerFTP = passwordServerFTP;
		this.folderFtpSauvegarde = floderFtpSauvegarde;
		this.cheminFichier = cheminFichier;
		this.url = url;
	}

//	public boolean actionSauvegardeFTP(String pathFile){
	public boolean actionSauvegardeFTP(String pathFile,final IProgressMonitor monitor){
		boolean flag = false;
		FtpUtil ftpUtil = new FtpUtil();
		flag = ftpUtil.connectServer(pathServerFTP, Integer.valueOf(portServerFTP), loginServerFTP, 
				passwordServerFTP,"");
		
		if(flag){
			ftpUtil.setFileType(FTP.BINARY_FILE_TYPE);
			ftpUtil.setBufferSize(128);

			/** pour servir SubProgressMonitor **/
			ftpUtil.setCopyStreamListener(new CopyStreamListener(){
				public void bytesTransferred(CopyStreamEvent arg0) {
					// TODO Auto-generated method stub
				}
				public void bytesTransferred(long arg0, int arg1, long arg2) {
					// TODO Auto-generated method stub
					monitor.worked(arg1);
				}
			});
			/***********************************/
			try {
				//ftpUtil.changeDirectory(folderFtpSauvegarde);
//				ftpUtil.createDirectory(folderFtpSauvegarde);
//				ftpUtil.changeDirectory(folderFtpSauvegarde);
//				ftpUtil.removeContenuDirectory(folderFtpSauvegarde);
				flag = ftpUtil.uploadFile(pathFile);
				ftpUtil.getReplyCodeServer();
				ftpUtil.closeServer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
		}		
		return flag;
	}

	@Override
	public void run(final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

		monitor.beginTask("Envoie vers le serveur distant ...", 100);
//		String pathFileZBG = runSubTaskSaveDossier(new SubProgressMonitor(monitor,20));		  
		flagSauvegardeFTP = runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,80),cheminFichier);
		monitor.done();

	}

//	private String runSubTaskSaveDossier(IProgressMonitor monitor) {
//		String pathSauvegardeTmp = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/";
//		FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
//		monitor.beginTask("",monitor.UNKNOWN);
//		monitor.subTask("Sauvegarde du dossier .");
//		String fileSauvegarde = fonctionSauvegarde.sauvegarde(pathSauvegardeTmp, true,null,".zbg");
//		monitor.done();
//		return fileSauvegarde;
//	}

	/**
	 *
	 * @param path ==> path of file *.zbg  (..../tmp/*.zbg)
	 * @return
	 */
	private boolean runSubTaskEnvoyerFTP(IProgressMonitor monitor,String path) {
		File f = new File(path);
		monitor.beginTask("",(int)f.length());
		monitor.subTask("Envoie vers le serveur FTP .");
		boolean flag = actionSauvegardeFTP(path,monitor);
		monitor.done();
		f.delete();
		return flag;
	}
		
   public void getInfosPreferencesProject(){
	   PreferenceInitializerProject.initDefautProperties();
	   pathServerFTP=Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PATH_SERVER_FTP);
	   portServerFTP=Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PORT_SERVER_FTP);
	   loginServerFTP=Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN_SERVER_FTP);
	   passwordServerFTP=Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD_SERVER_FTP);
	   cheminFichier=Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+Activator.getDefault().getPreferenceStoreProject().
			   getString(PreferenceConstantsProject.PATH_FICHIER);
	   url=Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.URL);	   
   }

	
	public String getPathServerFTP() {
		return pathServerFTP;
	}
	public void setPathServerFTP(String pathServerFTP) {
		this.pathServerFTP = pathServerFTP;
	}
	public String getPortServerFTP() {
		return portServerFTP;
	}
	public void setPortServerFTP(String portServerFTP) {
		this.portServerFTP = portServerFTP;
	}

	public String getPasswordServerFTP() {
		return passwordServerFTP;
	}
	public void setPasswordServerFTP(String passwordServerFTP) {
		this.passwordServerFTP = passwordServerFTP;
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

	public String getLoginServerFTP() {
		return loginServerFTP;
	}

	public void setLoginServerFTP(String loginServerFTP) {
		this.loginServerFTP = loginServerFTP;
	}

	public String getFolderFtpSauvegarde() {
		return folderFtpSauvegarde;
	}

	public void setFolderFtpSauvegarde(String folderFtpSauvegarde) {
		this.folderFtpSauvegarde = folderFtpSauvegarde;
	}


	public boolean isFlagSauvegardeFTP() {
		return flagSauvegardeFTP;
	}

	public void setFlagSauvegardeFTP(boolean flagSauvegardeFTP) {
		this.flagSauvegardeFTP = flagSauvegardeFTP;
	}

	public String getCheminFichier() {
		return cheminFichier;
	}

	public void setCheminFichier(String cheminFichier) {
		this.cheminFichier = cheminFichier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


		
	
}
