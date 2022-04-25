package fr.legrain.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.io.Util;
import org.apache.log4j.Logger;


/**
 * @author Zhaolin
 */
public class FtpUtil {

	private FTPClient ftpClient;
	private CopyStreamListener copyStreamListener;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;   
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;
	static Logger logger = Logger.getLogger(FtpUtil.class.getName());
	private int bufferSize = 512;

	public boolean connectServer(String server, int port,String user,String password) {
		return connectServer(server, port,user,password,"");
	}

	/**
	 * Connection au serveur FTP
	 * @param server - IP ou domaine
	 * @param port - port (en général 21)
	 * @param user - nom d'utilisateur
	 * @param password - mot de passe
	 * @param path - répertoire de travail, relatif ou absolu par rapport au répertoire de connection
	 * @return - faux en cas d'erreur de connection au serveur
	 */
	public boolean connectServer(String server, int port,String user,String password,String path) {
		
		boolean flag = true;
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			flag = ftpClient.login(user, password);
			if(flag){
				logger.info("Connected to " + server + ".");   
				logger.info(ftpClient.getReplyCode());   
				if (path.length() != 0) {   
					ftpClient.changeWorkingDirectory(path);   
				}   
			}
		} catch (Exception e) {
			logger.error("",e);
			flag = false;
		} 
		return flag;
	}

	/**
	 * Inscrit dans les logs le code et le message de retour de la dernière action réalisé sur le serveur FTP
	 */
	public void getReplyCodeServer(){
		logger.info(ftpClient.getReplyCode()+" -- "+ftpClient.getReplyString());
	}

	/**
	 * Change le type de transmission, binaire ou texte
	 * @param fileType - utiliser les constantes : BINARY_FILE_TYPE et ASCII_FILE_TYPE
	 */
	public void setFileType(int fileType){
		try {
			ftpClient.setFileType(fileType);
		} catch (IOException e) {
			logger.error("",e);
		}
	}

	/**
	 * Ferme la connection au serveur
	 */
	public void closeServer() {
		if(ftpClient.isConnected()){
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("",e);
			}
		}
	}
	
	/**
	 * Remonte à la racine du système de fichier distant "/" <br>
	 * Attention, en général, "/" correspond au répertoire de connexion du compte FTP utilisé
	 * mais suivant la configuration du serveur FTP, il peut s'agir de la racine du sytème.
	 * @return
	 */
	public Boolean changeToRootDirectory(){
		try {
			logCurrentFTPDirectory("debut");
			boolean isRoot = false;
			isRoot = ftpClient.changeWorkingDirectory("/");
			logCurrentFTPDirectory("fin");
			return isRoot;
		} catch (IOException e) {
			logger.error("",e);
		}
		return false;
	}

	/**
	 * Change le répertoire de travail sur le serveur
	 * ============== About directory ==============
	 * the following method using for path
	 * @param - nom/chemin du nouveau répertoire de travail
	 * @return faux si le changement de répertoire n'a pas eu lieu
	 */
	public Boolean changeDirectory(String path){
		logCurrentFTPDirectory("debut changeDirectory");
		Boolean isChange = true;
		if(path.contains("/")){
			String pathDirectory = getPathDirectoryFtp(path);
			String[] arrayDirectory = pathDirectory.split("/");
			int i = 0;
			while (i<arrayDirectory.length && isChange ) {
				String type =  arrayDirectory[i];
				try {
					isChange = ftpClient.changeWorkingDirectory(type);
				} catch (IOException e) {
					isChange = false;
					logger.error("",e);
				}
				i++;
			}
		}
		if(!path.contains("/")){
			try {
				isChange = ftpClient.changeWorkingDirectory(path);
			} catch (IOException e) {
				logger.error("",e);
			}
		}
		logCurrentFTPDirectory("fin changeDirectory");

		return isChange;
	}
	
	/**
	 * Indique dans les logs le repertoire FTP courant
	 * @param debut - chaine qui apparait en debut de message dans les logs, permet de différencier les appels
	 */
	private void logCurrentFTPDirectory(String debut) {
		try {
			logger.debug(debut+" Repertoire FTP courant : "+ftpClient.printWorkingDirectory());
		} catch (IOException e) {
			logger.error("",e);
		}
	}

	/**
	 * Création d'un répertoire sur le serveur et se positionne a l'interieur de celui-ci.
	 * @param pathName - nom/chemin du répertoire
	 * @return - faux si le répertoire n'a pas pu être créé
	 */
	public Boolean createDirectory(String pathName){
		return createDirectory(pathName,null);
	}
	
	/**
	 * Creer le repertoire et se positionne a l'interieur de celui-ci.
	 * Si le repertoire exite deja, change de repertoire
	 * @param pathName - nom/chemin du répertoire
	 * @param droit - droits utilisateur qui seront affectés au nouveau répertoire.
	 * @return - faux si le répertoire n'a pas pu être créé
	 */
	public Boolean createDirectory(String pathName,String droit){
		String pathDirectory = getPathDirectoryFtp(pathName);
		logger.debug("Creation du repertoire : "+pathDirectory);
		Boolean isCreate = true;

		if(pathDirectory.contains("/")){
			String[] arrayDirectory = pathDirectory.split("/");
			int i = 0;
			while (i<arrayDirectory.length && isCreate) {
				System.out.println(arrayDirectory[i]);
				if(existDirectory(arrayDirectory[i])==false){
					try {
						isCreate = ftpClient.makeDirectory(arrayDirectory[i]);
						if(droit!=null) {
							updatePermissionDirectory(arrayDirectory[i], droit);
						}
						changeDirectory(arrayDirectory[i]);
					} catch (IOException e) {
						isCreate = false;
						logger.error("",e);
					}
				}
				i++;
			}
		}
		else{
			if(existDirectory(pathDirectory) == false){
				try {
					isCreate = ftpClient.makeDirectory(pathDirectory);
					changeDirectory(pathDirectory);
				} catch (IOException e) {
					isCreate = false;
					logger.error("",e);
				}
			}
		}

//		for (String nameDirectoy : arrayDirectory) {
//			if(!existDirectory(nameDirectoy)){
//				try {
//					isCreate = ftpClient.makeDirectory(nameDirectoy);
//					changeDirectory(nameDirectoy);
//				} catch (IOException e) {
//					isCreate = false;
//					e.printStackTrace();
//				}
//			}else{
//				isCreate = changeDirectory(nameDirectoy);
//			}
//		}
		return isCreate;
	}

	/**
	 * Suppresion d'un répertoire vide sur le serveur
	 * delete a directory
	 * @param path - nom/chemin du répertoire
	 * @return - faux si le répertoire n'a pas pu être supprimé
	 */
	public Boolean removeDirectory(String path){
		String pathDirectory = getPathDirectoryFtp(path);
		Boolean isRemove = true;
		try {
			isRemove = ftpClient.removeDirectory(pathDirectory);
		} catch (IOException e) {
			isRemove = false;
			logger.error("",e);
		}
		return isRemove;
	}

	/**
	 * Suppression de tout le contenu d'un répertoire
	 * delete toutes les contenus dans repertoir de Path
	 * @param path - nom/chemin du répertoire à vider
	 * @return - faux si le contenu du répertoire n'a pas pu être supprimé
	 */
	public boolean removeContenuDirectory(String path){
		boolean flag = false;
		String pathDirectory = getPathDirectoryFtp(path);
		
		try {
			FTPFile[] ftpFileArr = ftpClient.listFiles();
			if(ftpFileArr.length != 0){
				for (FTPFile ftpFile : ftpFileArr) {
					String name = ftpFile.getName();
					if(ftpFile.isDirectory()){
						removeDirectory(pathDirectory+"/"+name, true);
					}
					else if(ftpFile.isFile()){
						deleteFile(name);
					}
					else if(ftpFile.isSymbolicLink()){

					}
					else if(ftpFile.isUnknown()){
						deleteFile(pathDirectory+"/"+name);
					}
				}
			}
//			if(ftpFileArr.length == 0) flag = true;
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return flag;
	}
	
	/**
	 * Suppresion d'un répertoire sur le serveur
	 * delete all subDirectory and files
	 * @param path - nom/chemin du répertoire à supprimer
	 * @param isAll - si vrai, supprime un répertoire non vide (répertoire et contenu), sinon supprimer uniquement un répertoire vide
	 * @return - faux si le répertoire n'a pas pu être supprimé
	 */
	public Boolean removeDirectory(String path, boolean isAll) throws IOException{
		Boolean isRemove = null ;
		String pathDirectory = getPathDirectoryFtp(path);
		if(!isAll){
			isRemove = removeDirectory(pathDirectory);
		}
		if(isAll){
			FTPFile[] ftpFileArr = ftpClient.listFiles(pathDirectory);
			if(ftpFileArr==null && ftpFileArr.length==0){
				isRemove = removeDirectory(pathDirectory);
			}
			else{
				for (FTPFile ftpFile : ftpFileArr) {
					String name = ftpFile.getName();
					if(ftpFile.isDirectory() && !name.equals(".") && !name.equals("..")){ 
						//certains serveur FTP retourne le répertoire courant "." ainsi que le parent "..", ce qui provoque un appel récursif sans fin
						removeDirectory(pathDirectory+"/"+name, true);
					}
					else if(ftpFile.isFile()){
						deleteFile(pathDirectory+"/"+name);
					}
					else if(ftpFile.isSymbolicLink()){

					}
					else if(ftpFile.isUnknown()){

					}
				}
				isRemove = ftpClient.removeDirectory(pathDirectory);
			}

		}
		return isRemove;
	}

	/**
	 * Vérifie l'existance d'un répertoire sur le serveur
	 * check the directory is exist; exist return true, else false
	 * @param path - nom/chemin du répertoire dont on souhaite tester l'existance
	 * @return - faux si le répertoire n'exsite pas
	 */
	public Boolean existDirectory(String path){

		boolean flag = false;
		String pathDirectory = getPathDirectoryFtp(path);
		try {
			flag = ftpClient.changeWorkingDirectory(pathDirectory);
		} catch (IOException e) {
			logger.error("",e);
		}
		//FTPFile[] ftpFileArr = ftpClient.listFiles()
		return flag;
	}

	/**
	 * Vérifie l'existance d'un fichier sur le serveur
	 * check file exsit; exist return true, else false
	 * @param path - nom/chemin du fichier dont on souhaite tester l'existance
	 * @return - faux si le fichier n'exsite pas
	 */
	public Boolean existFile(String path){
		boolean flag = true;
		List<String> retlist = new ArrayList<String>();
		String pathFile = getPathDirectoryFtp(path);
		String pathCurrent = null;
		if(!pathFile.contains("/")){
			pathCurrent = "";
		}else{
			int index = pathFile.lastIndexOf("/");
			pathCurrent = pathFile.substring(0, index);
		}
		retlist = getFileList(pathCurrent);
		flag = retlist.contains(path);
		return flag;
	}

	/**
	 * Supprime un fichier sur le serveur
	 * @param pathName - nom/chemin du fichier à supprimer
	 * @return - faux si le fichier ne peu pas être supprimer
	 */
	public Boolean deleteFile(String pathName){
		Boolean isDeleteFile = true;
		try {
			ftpClient.deleteFile(pathName);
		} catch (IOException e) {
			isDeleteFile = false;
			logger.error("",e);
		}
		return isDeleteFile;
	}

	/**
	 * 
	 * Download and Upload file using
	 * ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) better!   
	 * @param path
	 * @return
	 */  
	public List<String> getFileList(String path){
		List<String> retList = new ArrayList<String>();
		try {
			FTPFile[] ftpFiles= ftpClient.listFiles(path);
			if (ftpFiles == null || ftpFiles.length == 0) {  
				return retList;   
			}else{
				for (FTPFile ftpFile : ftpFiles) {  
//					System.out.println("==>"+ftpFile.getName());
					if (ftpFile.isFile()) {   
						retList.add(ftpFile.getName());   
					}   
				}
			}

		} catch (IOException e) {
			logger.error("",e);
		}
		return retList;
	}

	/**
	 * Upload d'un fichier vers le serveur en changeant son nom
	 * @param pathFile - chemin du fichier à envoyer sur le serveur FTP
	 * @param newName - nouveau nom du fichier sur le serveur
	 * @param path - chemin/emplacement du fichier sur le serveur
	 * @return - faux si le fichier ne peu pas être envoyer
	 */
	public Boolean uploadFile(String pathFile, String newName,String path) throws IOException{
		boolean flag = false; 
		InputStream iStream = null;
		OutputStream oStream = null;
		String pathFileName = "";
		String cheminDestinationComplet = "";
		try {
			if(path!=null) {
				pathFileName = getPathDirectoryFtp(path);
			}
			iStream = new FileInputStream(pathFile);

			if(!pathFileName.equals("")){
				cheminDestinationComplet = pathFileName+"/"+newName;
			} else { //repertoire courant (rep de connexion)
				cheminDestinationComplet = newName;
			}
			//flag = ftpClient.storeFile(cheminDestinationComplet, iStream);

			File f = new File(pathFile);
			oStream = ftpClient.storeFileStream(cheminDestinationComplet);
			if(oStream==null) {
				getReplyCodeServer(); 
			}
			if(copyStreamListener!=null) {
				Util.copyStream(iStream, oStream, bufferSize, f.length(),copyStreamListener);
			} else {
				Util.copyStream(iStream, oStream, bufferSize);
			}
			
			oStream.flush();
			
			iStream.close();
			oStream.close();
			if(!ftpClient.completePendingCommand()) {
				logger.error("File transfer failed.");
			}

			flag = true;
		} catch (FileNotFoundException e) {
			logger.error("",e);
			flag = false;
			return flag;
		}finally{
			if(iStream != null){
				iStream.close();
			}
			if(oStream != null){
				oStream.close();
			}
		}

		return flag;
	}

	/**
	 * Upload d'un fichier vers le serveur (sans changer son nom)
	 * @param pathFile - chemin du fichier à envoyer sur le serveur FTP
	 * @param path - chemin/emplacement du fichier sur le serveur
	 * @throws IOException 
	 * @return - faux si le fichier ne peu pas être envoyer
	 */
	public Boolean uploadFile(String pathFile,String path) throws IOException{
		File file = new File(pathFile);
		String fileName = file.getName();
		return uploadFile(pathFile,fileName,path);
	}

	/**
	 * Upload d'un fichier vers le serveur (dans le répertoire de travail courant)
	 * @param pathFile - chemin du fichier à envoyer sur le serveur FTP
	 * @throws IOException 
	 * @return - faux si le fichier ne peu pas être envoyer
	 */
	public Boolean uploadFile(String pathFile) throws IOException{
		File file = new File(pathFile);
		String fileName = file.getName();
		return uploadFile(pathFile,fileName,null);
	}

	/**
	 * Modification des permission d'un répertoire
	 * @param path - chemin du répertoire sur le serveur
	 * @param permission ==> permissions en octal(ex:0777 ou 0666) ou bien avec des chaines de caractères 
	 * mais certains serveur FTP semble ne pas pouvoir les prendre en compte (ex:o-rwx,g+rw,u+rwx)
	 * @return - faux si les permissions ne peuvent pas être modifiées
	 */
	public Boolean updatePermissionDirectory(String path,String permission){
		boolean flag = true;

		String pathDirectory = getPathDirectoryFtp(path);
		if(!pathDirectory.contains("/")){
			try {
				logCurrentFTPDirectory("updatePermissionDirectory");
				flag = ftpClient.sendSiteCommand("chmod "+permission+" "+pathDirectory);
			} catch (IOException e) {
				flag = false;
				logger.error("",e);
			}
		}
		else{
			String[] nameFile = pathDirectory.split("/");
			int i = 0;
			while (i<nameFile.length) {
				if(changeDirectory(nameFile[i])){
					try {
						logger.info("chmod "+permission+" "+"../"+nameFile[i]);
						flag = ftpClient.sendSiteCommand("chmod "+permission+" "+"../"+nameFile[i]);
					} catch (IOException e) {
						flag = false;
						logger.error("",e);
					}
				}
				i++;
			}
		}
		return flag;
	}

	/**
	 * Modification des permission d'un fichier
	 * @param path - chemin du fichier sur le serveur
	 * @param permission ==> permissions en octal(ex:0777 ou 0666) ou bien avec des chaines de caractères 
	 * mais certains serveur FTP semble ne pas pouvoir les prendre en compte (ex:o-rwx,g+rw,u+rwx)
	 * @return - faux si les permissions ne peuvent pas être modifiées
	 */
	public Boolean updatePermissionFile(String path, String permission){
		boolean flag = true;
		String pathFile = getPathDirectoryFtp(path);
		if(!pathFile.contains("/")){
			try {
				flag = ftpClient.sendSiteCommand("chmod "+permission+" "+pathFile);
			} catch (IOException e) {
				flag = false;
				logger.error("",e);
			}
		}else{
//			String[] nameFile = pathFile.split("/");
//			int i = 0;
//			while (i<nameFile.length) {
//				if(i!=(nameFile.length-1)){
//					if(changeDirectory(nameFile[i])){
//						try {
//							System.out.println("chmod "+permission+" "+"../"+nameFile[i]);
//							flag = ftpClient.sendSiteCommand("chmod "+permission+" "+"../"+nameFile[i]);
//						} catch (IOException e) {
//							flag = false;
//							e.printStackTrace();
//						}
//					}
//				}
//				else{
//					try {
//						flag = ftpClient.sendSiteCommand("chmod "+permission+" "+nameFile[i]);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				i++;
//			}
			String[] nameFile = pathFile.split("/");
			int i = 0;
			while (i<nameFile.length) {
				if(changeDirectory(nameFile[i])){
					try {
						System.out.println("chmod "+permission+" "+"../"+nameFile[i]);
						flag = ftpClient.sendSiteCommand("chmod "+permission+" "+"../"+nameFile[i]);
					} catch (IOException e) {
						flag = false;
						logger.error("",e);
					}
				}else{
					try {
						flag = ftpClient.sendSiteCommand("chmod "+permission+" "+nameFile[i]);
					} catch (IOException e) {
						logger.error("",e);
					}
				}
				i++;
			}
		}
		return flag;
	}

	/** 
	 * Upload d'un répertoire sur le serveur FTP
	 * @param dirtectoyName - chemin du répertoire local à envoyer
	 * @return - faux si le répertoire ne peut pas être envoyer
	 */
	public Boolean uploadListFile(String dirtectoyName){
		boolean flag = false;
		InputStream iStream = null;
		File files = new File(dirtectoyName);
		File[] lisFiles = files.listFiles();
		for (File file : lisFiles) {
			if(file.isFile()){
				try {
					uploadFile(file.getAbsolutePath());
//					iStream = new FileInputStream(file.getAbsolutePath());
//					String newName = file.getName();
//					flag = ftpClient.storeFile(newName,iStream);

				} catch (FileNotFoundException e) {
					logger.error("",e);
				} catch (Exception e) {
					logger.error("",e);
				}finally{
					if(iStream != null){
						try {
							iStream.close();
						} catch (IOException e) {
							logger.error("",e);
						}
					}
				}
			} else if(file.isDirectory()){
				//if(!file.getName().equals(".svn")) { //évite l'envoi des répertoire .svn, problème qui arrive uniquement en développement
					uploadDirectory(dirtectoyName+"/"+file.getName(), file.getName());
				//}
				try {
					ftpClient.changeToParentDirectory();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
		return flag;
	}

	/** 
	 * Upload d'un répertoire sur le serveur FTP
	 * @param dirtectoyName - chemin du répertoire local à envoyer
	 * @param path - emplacement sur le serveur FTP ou sera envoyé le répertoire local
	 * @return - faux si le répertoire ne peut pas être envoyer
	 */
	public Boolean uploadDirectory(String dirtectoyName,String path){
		boolean flag = false;
		String pathName = getPathDirectoryFtp(path);
		if(existDirectory(path)){
			flag = uploadListFile(dirtectoyName);
		}else{
			if(createDirectory(pathName)){
				flag = uploadListFile(dirtectoyName);
			}
		}
		return flag;

	}

	/** 
	 * Téléchargement d'un fichier à partir du serveur FTP
	 * @param remoteFileName - nom du fichier distant
	 * @param localFileName - nom du fichier local
	 * @return - faux si le fichier ne peut pas être télécharger
	 * ================ download file from ftp server ===============
	 */
	public boolean downloadFile(String remoteFileName, String localFileName)   
	throws IOException {   
		boolean flag = false;   
		String pathFileName = getPathDirectoryFtp(remoteFileName);
		File outfile = new File(localFileName);   
		OutputStream oStream = null;   
		try {   
			oStream = new FileOutputStream(outfile);   
			flag = ftpClient.retrieveFile(pathFileName, oStream);  

		} catch (IOException e) {   
			flag = false;   
			return flag;   
		} finally {   
			if(oStream!=null)
				oStream.close();   
		}   
		return flag;   
	}   

	/**
	 * Téléchargement d'un répertoire à partir du serveur FTP
	 * @param remoteDirectoryName - nom du fichier distant
	 * @param localFileName -  nom du fichier local
	 * @param isAll - si vrai, supprime le répertoire local avant le téléchargement s'il existe
	 * @return faux si le répertoire ne peut pas être télécharger
	 */
	public Boolean downloadDirectory(String remoteDirectoryName, String localDirectoryName ,boolean isAll){
		boolean flag = true;
		String pathDirectoryName = getPathDirectoryFtp(remoteDirectoryName);
		if(isAll){
			removeFileLocal(localDirectoryName);
		}
		if(changeDirectory(pathDirectoryName)){
			try {
				FTPFile[] ListFtpFile = ftpClient.listFiles();
				for (FTPFile ftpFile : ListFtpFile) {
					String ftpFileName = ftpFile.getName();
					File outFile = new File(localDirectoryName+File.separator+ftpFileName);
					OutputStream oStream = null;
					if(ftpFile.isFile()){
						oStream = new FileOutputStream(outFile); 
						flag = ftpClient.retrieveFile(ftpFileName, oStream);
						oStream.close();
					}
					if(ftpFile.isDirectory()){
						if(!outFile.exists()){
							outFile.mkdir();
						}
						downloadDirectory(ftpFileName, localDirectoryName+File.separator+ftpFileName, false);
					}
				}
			} catch (IOException e) {
				logger.error("",e);
			}
		}


		return flag;
	}

	/**
	 * Supprime un répertoire/fichier sur la machine locale
	 * @param path - chemin du fichier/répertoire à supprimer
	 */
	public void removeFileLocal(String path){

		File pathFileLocal = new File(path);
		if(pathFileLocal.exists()){
			File[] listFiles = pathFileLocal.listFiles();
			if(listFiles.length!=0){
				for (File file : listFiles) {
					if(file.isFile()){
						file.delete();
					}
					if(file.isDirectory()){
						removeFileLocal(path+File.separator+file.getName());
						file.delete();
					}
				}
			}
		}
	}

	/**
	 * Supprime le "/" initial dans le chemin s'il y en a un : /aa/bb => aa/bb <br>
	 * et supprime le "/" final dans le chemin s'il y en a un : aa/bb/ => aa/bb
	 */
	public String getPathDirectoryFtp(String path){

		String pathDirectory = path;
		if(pathDirectory.startsWith("/")){
			pathDirectory = pathDirectory.substring(1);
		}
		if(pathDirectory.endsWith("/")){
			pathDirectory = pathDirectory.substring(0, pathDirectory.length()-1);
		}
		return pathDirectory;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public CopyStreamListener getCopyStreamListener() {
		return copyStreamListener;
	}

	public void setCopyStreamListener(CopyStreamListener copyStreamListener) {
		this.copyStreamListener = copyStreamListener;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
}
