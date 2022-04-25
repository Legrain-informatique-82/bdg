package fr.legrain.sauvegardeFTP.divers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import fr.legrain.sauvegarde.handlers.FonctionSauvegarde;
import fr.legrain.sauvegardeFTP.preferences.CompositePreferencePage;
import fr.legrain.sauvegardeFTP.preferences.PreferenceConstants;
import fr.legrain.sauvegardeFTP.preferences.PropertiesFilePreference;



public class FonctionGeneral implements IRunnableWithProgress, LgrWorkListener {

	static Logger logger = Logger.getLogger(FonctionGeneral.class.getName());
			
	private String pathServerFTP = null;
	private String portServerFTP = null;
	private String loginServerFTP = null;
	private String passwordServerFTP = null;
	private String folderFtpSauvegarde = null;
	private Properties props;
	private boolean flagSauvegardeFTP;
	
	
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
			String floderFtpSauvegarde) {
		super();
		this.pathServerFTP = pathServerFTP;
		this.portServerFTP = portServerFTP;
		this.loginServerFTP = loginServerFTP;
		this.passwordServerFTP = passwordServerFTP;
		this.folderFtpSauvegarde = floderFtpSauvegarde;
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
				ftpUtil.createDirectory(folderFtpSauvegarde);
				ftpUtil.changeDirectory(folderFtpSauvegarde);
				ftpUtil.removeContenuDirectory(folderFtpSauvegarde);
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
		monitor.beginTask("Sauvegarde du dossier et envoie vers le serveur distant ...", 100);
		String pathFileZBG = runSubTaskSaveDossier(new SubProgressMonitor(monitor,20));		  
		flagSauvegardeFTP = runSubTaskEnvoyerFTP(new SubProgressMonitor(monitor,80),pathFileZBG);
		monitor.done();

	}

	private String runSubTaskSaveDossier(IProgressMonitor monitor) {
		String pathSauvegardeTmp = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/";
		FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
		monitor.beginTask("",monitor.UNKNOWN);
		monitor.subTask("Sauvegarde du dossier .");
		String fileSauvegarde = fonctionSauvegarde.sauvegarde(pathSauvegardeTmp, true,null,".zbg",false);
		monitor.done();
		return fileSauvegarde;
	}

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
		
	public boolean getInfosFileProperties(String pathFileProperties){
		boolean flag = false; 
		props = PropertiesFilePreference.getProperties();
		InputStream in;
		
		try {
			in = new BufferedInputStream(new FileInputStream(pathFileProperties));
			props.load(in);
			in.close();

			pathServerFTP = props.getProperty(PreferenceConstants.PATH_SERVER_FTP);
			portServerFTP = props.getProperty(PreferenceConstants.PORT_SERVER_FTP);
			loginServerFTP = props.getProperty(PreferenceConstants.LOGIN_SERVER_FTP);
			passwordServerFTP = props.getProperty(PreferenceConstants.PASSWORD_SERVER_FTP);
			folderFtpSauvegarde = props.getProperty(PreferenceConstants.FOLDER_FTP_SAUVEGARDE);
			
			flag = pathServerFTP.length()!= 0 && portServerFTP.length()!= 0 && loginServerFTP.length()!= 0 &&
				   passwordServerFTP.length()!= 0; 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
		return flag;
	}
	
	public void updateValueProperties(Properties props,String pathServerFTP,String portServerFTP,String loginServerFTP,
									  String passwordServerFTP,String floderFtpSauvegarde){
		
		props.setProperty(PreferenceConstants.PATH_SERVER_FTP,pathServerFTP);
		props.setProperty(PreferenceConstants.PORT_SERVER_FTP,portServerFTP);
		props.setProperty(PreferenceConstants.LOGIN_SERVER_FTP,loginServerFTP);
		props.setProperty(PreferenceConstants.PASSWORD_SERVER_FTP,passwordServerFTP);
		props.setProperty(PreferenceConstants.FOLDER_FTP_SAUVEGARDE,floderFtpSauvegarde);
		
	}
	
	public void updateValueTextCompositePreferencePage(CompositePreferencePage preferencePage){
		
		preferencePage.getTextNameServerFTP().setText(pathServerFTP);
		preferencePage.getTextPortServerFTP().setText(portServerFTP);
		preferencePage.getTextLoginServerFTP().setText(loginServerFTP);
		preferencePage.getTextPasswordServerFTP().setText(passwordServerFTP);
		
	}
	
	public void sauvegardeInfosValueProperties(String pathFileProperties,CompositePreferencePage compositePreferencePage){
		props = PropertiesFilePreference.getProperties();
		FileOutputStream out;
		
		pathServerFTP = compositePreferencePage.getTextNameServerFTP().getText();
		portServerFTP = compositePreferencePage.getTextPortServerFTP().getText();
		loginServerFTP = compositePreferencePage.getTextLoginServerFTP().getText();
		passwordServerFTP = compositePreferencePage.getTextPasswordServerFTP().getText();
		folderFtpSauvegarde = PreferenceConstants.PATH_SAUVEGARDE_BDG +Const.C_REPERTOIRE_PROJET;

		updateValueProperties(props,pathServerFTP,portServerFTP,loginServerFTP,passwordServerFTP,folderFtpSauvegarde);
		
		try {
			out = new FileOutputStream(pathFileProperties);
			props.store(out, null);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		
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

//	@Override
//	public void run(IProgressMonitor monitor) throws InvocationTargetException,
//			InterruptedException {
//		// TODO Auto-generated method stub
//		
//	}

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
		
	
}
