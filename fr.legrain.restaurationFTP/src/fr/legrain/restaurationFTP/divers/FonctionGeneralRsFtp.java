package fr.legrain.restaurationFTP.divers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.sauvegarde.handlers.FonctionSauvegarde;



public class FonctionGeneralRsFtp /*implements IRunnableWithProgress, LgrWorkListener*/ {

	static Logger logger = Logger.getLogger(FonctionGeneralRsFtp.class.getName());
			
	private String pathServerFTP = null;
	private String portServerFTP = null;
	private String loginServerFTP = null;
	private String passwordServerFTP = null;
	private String folderFtpSauvegarde = null;
	private Properties props;
	public static List<String> retList = new ArrayList<String>();
	private String nameFileChoixRestauration = null;
	private String pathLocalFileRestauration = null;
	
	
	private Shell shell;
	private FtpUtil ftpUtil;
	
	public boolean getInfosFileProperties(String pathFileProperties){
		boolean flag = false; 
		props = PropertiesFilePreference.getProperties();
		InputStream in;
		
		try {
			in = new BufferedInputStream(new FileInputStream(pathFileProperties));
			props.load(in);
			in.close();

			pathServerFTP = props.getProperty(ConstantsPlugin.PATH_SERVER_FTP);
			portServerFTP = props.getProperty(ConstantsPlugin.PORT_SERVER_FTP);
			loginServerFTP = props.getProperty(ConstantsPlugin.LOGIN_SERVER_FTP);
			passwordServerFTP = props.getProperty(ConstantsPlugin.PASSWORD_SERVER_FTP);
			folderFtpSauvegarde = props.getProperty(ConstantsPlugin.FOLDER_FTP_SAUVEGARDE);
			
			flag = pathServerFTP.length()!= 0 && portServerFTP.length()!= 0 && loginServerFTP.length()!= 0 &&
				   passwordServerFTP.length()!= 0; 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
		return flag;
	}
	
	public boolean getListFileFtp(String pathFile) {
		
		ftpUtil = new FtpUtil();
		boolean flag = ftpUtil.connectServer(pathServerFTP, Integer.valueOf(portServerFTP), loginServerFTP, 
				passwordServerFTP,"");
		if(flag){
			ftpUtil.setFileType(FTP.BINARY_FILE_TYPE);
			ftpUtil.setBufferSize(256);
			List<String> listFile = ftpUtil.getFileList(pathFile); 
			getFileTypeZBG(listFile);
			ftpUtil.changeDirectory(pathFile);
		}
		return flag;
	}

	public List<String> getFileTypeZBG(List<String> listFile) {
		retList.clear();
		for (String nameFile : listFile) {
			if(nameFile.endsWith(ConstantsPlugin.FILE_EXTENTION_ZBG)){
				retList.add(nameFile);
			}
		}
		return retList;
	}
	

	/**
	 * pour ajouter des elements dans Composite 
	 * en fonction de la nombre de file restauration
	 */
	public void makeInterfaceChoixFileRst(Shell shell) {
		Shell shellDialogChoixFileRst = new Shell(shell,
				SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
		shellDialogChoixFileRst.setText("Choix Fichier Restauration");
		shellDialogChoixFileRst.setLayout(new FillLayout());
		
		SwtCompositeRstFtp swtCompositeRstFtp = new SwtCompositeRstFtp(shellDialogChoixFileRst, SWT.NULL);
		Composite compositeFileFtp = swtCompositeRstFtp.getCompositeFileFtp();
		for (int i = 0; i < retList.size(); i++) {
			Button btRadio = new Button(compositeFileFtp, SWT.RADIO |SWT.LEFT);
			String nameFile = retList.get(i);
			if(i == 0){
				btRadio.setSelection(true);
				nameFileChoixRestauration = nameFile;
			}
			btRadio.setText(nameFile);
			addActionButtonRadio(btRadio);
		}
		
		ScrolledComposite scrolledComposite = swtCompositeRstFtp.getScrolledCompositeFileFtp();
		scrolledComposite.setContent(compositeFileFtp);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(compositeFileFtp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		initImageBouton(swtCompositeRstFtp);
		
		addActionBtValiderAndAnnule(swtCompositeRstFtp,shellDialogChoixFileRst);
		shellDialogChoixFileRst.open();
	}
	
	public void downloadFileRestauration() {
		String localFileName = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+
						       nameFileChoixRestauration;
		try {
			ftpUtil.downloadFile(nameFileChoixRestauration, localFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addActionBtValiderAndAnnule(SwtCompositeRstFtp swtCompositeRstFtp,
			final Shell shellDialogChoixFileRst){
		Button btAnnule = swtCompositeRstFtp.getBtAnnuler();
		
		btAnnule.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("valider imprimer");
				shellDialogChoixFileRst.close();
			}
		});
		
		Button btValider = swtCompositeRstFtp.getBtValider();
		btValider.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				shellDialogChoixFileRst.close();
				
				//File fileLocateSauvegard = new File(Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+nameFileChoixRestauration);
				String folderTmp = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP;
				File fileLocateSauvegard = new File(folderTmp+"/"+ConstantsPlugin.NAME_FOLDER_FTP);
				if(!fileLocateSauvegard.exists()){
					fileLocateSauvegard.mkdir();
				}
				String localFileName = fileLocateSauvegard.getPath()+"/"+nameFileChoixRestauration;
				PgsRestaurationFTP pgsRestaurationFTP = new PgsRestaurationFTP(nameFileChoixRestauration,localFileName,
						ftpUtil,shell);
				try {
					PlatformUI.getWorkbench().getProgressService().run(true, false, pgsRestaurationFTP);
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					logger.error("", e1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					logger.error("", e1);
				}
				
				FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
//				downloadFileRestauration();
//				fonctionSauvegarde.nouveau(Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/", shell);
				fonctionSauvegarde.nouveau(fileLocateSauvegard.getPath(), shell,nameFileChoixRestauration);
//				fonctionSauvegarde.nouveau(localFileName, shell);
			}
		});
	}
	public void initImageBouton(SwtCompositeRstFtp swtCompositeRstFtp){
		final String C_IMAGE_VALIDER = "/icons/accept.png";
		final String C_IMAGE_ANNULER = "/icons/cancel.png";
		if(swtCompositeRstFtp instanceof SwtCompositeRstFtp){
			swtCompositeRstFtp.getBtAnnuler().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ANNULER).createImage());
			swtCompositeRstFtp.getBtValider().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_VALIDER).createImage());
		}
	}
			
	public void addActionButtonRadio(final Button btRadio) {
		btRadio.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(btRadio.getSelection()){
					nameFileChoixRestauration = btRadio.getText();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
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

	public String getLoginServerFTP() {
		return loginServerFTP;
	}

	public void setLoginServerFTP(String loginServerFTP) {
		this.loginServerFTP = loginServerFTP;
	}

	public String getPasswordServerFTP() {
		return passwordServerFTP;
	}

	public void setPasswordServerFTP(String passwordServerFTP) {
		this.passwordServerFTP = passwordServerFTP;
	}

	public String getFolderFtpSauvegarde() {
		return folderFtpSauvegarde;
	}

	public void setFolderFtpSauvegarde(String folderFtpSauvegarde) {
		this.folderFtpSauvegarde = folderFtpSauvegarde;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
}
